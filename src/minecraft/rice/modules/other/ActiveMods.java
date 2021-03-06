package rice.modules.other;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Comparator;

import org.lwjgl.input.Keyboard;

import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import rice.Client;
import rice.events.Event;
import rice.events.listeners.EventRenderGUI;
import rice.events.listeners.EventUpdate;
import rice.modules.Module;
import rice.settings.BooleanSetting;
import rice.settings.ModeSetting;
import rice.utils.ColorUtil;

public class ActiveMods extends Module
{
	public ActiveMods() 
	{
		super("ActiveMods", Keyboard.KEY_NONE, Category.OTHER, "Shows a list of all currently enabled modules");
		this.addSettings(colorValue, backGroundValue, antiAchievementValue);
		this.toggled = true;
	}
	public static ModeSetting colorValue = new ModeSetting("Color","White","White","Light Gray","Gray","Dark Gray","Black","Red","Pink","Orange","Yellow","Green","Magenta","Cyan","Blue","Rainbow");
	private BooleanSetting backGroundValue =  new BooleanSetting("Background", true);
	private BooleanSetting antiAchievementValue =  new BooleanSetting("AntiAchievement", false);
	private ArrayList<Module> toggledMods = new ArrayList<Module>();
	private ArrayList<Module> modsList = new ArrayList<Module>();
	public int color = 0;
	public void onEvent(Event e) 
	{
		if(e instanceof EventUpdate) 
		{
			if(colorValue.is("White"))
			{
				color = Color.WHITE.getRGB();
			}
			else if(colorValue.is("Light Gray"))
			{
				color = Color.LIGHT_GRAY.getRGB();
			}
			else if(colorValue.is("Gray"))
			{
				color = Color.GRAY.getRGB();
			}
			else if(colorValue.is("Dark Gray"))
			{
				color = Color.DARK_GRAY.getRGB();
			}
			else if(colorValue.is("Black"))
			{
				color = Color.BLACK.getRGB();
			}
			else if(colorValue.is("Red"))
			{
				color = Color.RED.getRGB();
			}
			else if(colorValue.is("Pink"))
			{
				color = Color.PINK.getRGB();
			}
			else if(colorValue.is("Orange"))
			{
				color = Color.ORANGE.getRGB();
			}
			else if(colorValue.is("Yellow"))
			{
				color = Color.YELLOW.getRGB();
			}
			else if(colorValue.is("Green"))
			{
				color = Color.GREEN.getRGB();
			}
			else if(colorValue.is("Magenta"))
			{
				color = Color.MAGENTA.getRGB();
			}
			else if(colorValue.is("Cyan"))
			{
				color = Color.CYAN.getRGB();
			}
			else if(colorValue.is("Blue"))
			{
				color = Color.BLUE.getRGB();
			}
			else if(colorValue.is("Rainbow"))
			{
				color = ColorUtil.getRainbow(4, 0.8f, 1);
			}
		}
		if(e instanceof EventRenderGUI) 
		{
			ScaledResolution sr = new ScaledResolution(mc, mc.displayWidth, mc.displayHeight);
	    	FontRenderer fr = mc.fontRendererObj;
	    	modsList = new ArrayList<Module>();
			for(Module m: Client.getModule()) 
			{
				modsList.add(m);
			}
    		modsList.sort(Comparator.comparingInt(m -> mc.fontRendererObj.getStringWidth(String.valueOf(((Module)m).name + (((Module)m).additionalInfo != "" ? " " + ((Module)m).additionalInfo : "")))).reversed());
	    	int count = 0;
	    	int antiAchievementOffset = 0;
	    	if(antiAchievementValue.get()) 
	    	{
	    		antiAchievementOffset = fr.FONT_HEIGHT * 4;
	    	}
	    	else 
	    	{
	    		antiAchievementOffset = 0;
	    	}
	    	toggledMods = new ArrayList<Module>();
	    	for(Module m : modsList)
	    	{
	    		if(m.category.equals(category.OTHER) || m.category.equals(category.RENDER)) 
	    		{
	    			continue;
	    		}
	    		if(m.toggled && !toggledMods.contains(m))
	    		{
	    			toggledMods.add(m);
	    		}
	    	}
	    	if(!toggledMods.isEmpty())
	    	{
		    	for (Module m : toggledMods) 
		    	{
		    		if (!m.toggled) 
		    		{
		    			toggledMods.remove(m);
		    		}
		    		float offset = (count * (fr.FONT_HEIGHT + 4));
	    			if(backGroundValue.get()) 
	    			{
	    				//RenderUtil.smoothCircle(sr.getScaledWidth()/2,sr.getScaledHeight()/2, 100, 0x90000000);
	    				/*RenderUtil.drawRoundedRect((sr.getScaledWidth() - fr.getStringWidth(m.name + (m.additionalInfo != "" ? " ??7" + m.additionalInfo : "")) - 7), 
				    			antiAchievementOffset + offset + 1, 
				    			sr.getScaledWidth(), 
				    			antiAchievementOffset + offset + 14, 
				    			4,
				    			0xee000000);*/
			    		Gui.drawRect((sr.getScaledWidth() - fr.getStringWidth(m.name + (m.additionalInfo != "" ? " ??7" + m.additionalInfo : "")) - 7), 
			    			antiAchievementOffset + offset + 1, 
			    			sr.getScaledWidth(), 
			    			antiAchievementOffset + offset + 14, 
			    			0x90000000);
			    		Gui.drawRect((sr.getScaledWidth() - fr.getStringWidth(m.name + (m.additionalInfo != "" ? " ??7" + m.additionalInfo : "")) - 7), 
			    			antiAchievementOffset + offset + 1, 
			        		sr.getScaledWidth() - fr.getStringWidth(m.name + (m.additionalInfo != "" ? " ??7" + m.additionalInfo : "")) - 8, 
			        		antiAchievementOffset + offset + 14, 
			        		color);
	
		    			if(toggledMods.indexOf(m) != toggledMods.size() - 1)
		    			{
		    				Gui.drawRect((sr.getScaledWidth() - fr.getStringWidth(m.name + (m.additionalInfo != "" ? " ??7" + m.additionalInfo : "")) - 8), 
					    			antiAchievementOffset + offset + 14, 
					        		sr.getScaledWidth() - fr.getStringWidth(toggledMods.get(toggledMods.indexOf(m) + 1).getName() + (toggledMods.get(toggledMods.indexOf(m) + 1).additionalInfo != "" ? " ??7" + toggledMods.get(toggledMods.indexOf(m) + 1).additionalInfo : "")) - 7, 
					        		antiAchievementOffset + offset + 15, 
					        		color);
		    			}
		    			else 
		    			{
		    				Gui.drawRect((sr.getScaledWidth() - fr.getStringWidth(m.name + (m.additionalInfo != "" ? " ??7" + m.additionalInfo : "")) - 8), 
					    			antiAchievementOffset + offset + 14, 
					        		sr.getScaledWidth(), 
					        		antiAchievementOffset + offset + 15, 
					        		color);
						}
		    		}
	    			fr.drawStringWithShadow(m.name + (m.additionalInfo != "" ? " ??7" + m.additionalInfo : ""), 
			    			(sr.getScaledWidth() - fr.getStringWidth(m.name + (m.additionalInfo != "" ? " ??7" + m.additionalInfo : "")) - 3), 
			    			antiAchievementOffset + 4 + offset, 
			    			color);
		    		count++;
		    	}
			}
		}
	}
}