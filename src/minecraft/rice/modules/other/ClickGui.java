package rice.modules.other;

import java.awt.Color;

import org.lwjgl.input.Keyboard;

import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiScreen;
import rice.Client;
import rice.events.Event;
import rice.events.listeners.EventUpdate;
import rice.modules.Module;
import rice.settings.BooleanSetting;
import rice.settings.ModeSetting;
import rice.ui.clickgui.simple.button.Button;
import rice.utils.ColorUtil;
import rice.utils.MSTimer;

public class ClickGui extends Module
{
	public ClickGui() 
	{
		super("ClickGui", Keyboard.KEY_RSHIFT, Category.OTHER, "");
		this.addSettings(colorValue,safetyValue);
	}
	public static ModeSetting colorValue = new ModeSetting("Color","White","White","Light Gray","Gray","Dark Gray","Black","Red","Pink","Orange","Yellow","Green","Magenta","Cyan","Blue","Rainbow");
	private BooleanSetting safetyValue = new BooleanSetting("Safety", true);
	private MSTimer timer = new MSTimer();
	public void onEnable() 
	{
		timer.reset();
		if(mc.currentScreen == null) 
		{
			if(safetyValue.get() && GuiScreen.isCtrlKeyDown())
			{
				mc.displayGuiScreen(Client.ClickGui);
				ToggleSound.playButtonPressSound();
			}
			else if(!safetyValue.get())
			{
				mc.displayGuiScreen(Client.ClickGui);
				ToggleSound.playButtonPressSound();
			}
			else
			{
				this.toggle();
			}
		}
	}
	public void onEvent(Event e) 
	{
		if(e instanceof EventUpdate) 
		{
			FontRenderer fr = mc.fontRendererObj;
			if(colorValue.is("White"))
			{
				Button.color = Color.WHITE.getRGB();
			}
			else if(colorValue.is("Light Gray"))
			{
				Button.color = Color.LIGHT_GRAY.getRGB();
			}
			else if(colorValue.is("Gray"))
			{
				Button.color = Color.GRAY.getRGB();
			}
			else if(colorValue.is("Dark Gray"))
			{
				Button.color = Color.DARK_GRAY.getRGB();
			}
			else if(colorValue.is("Black"))
			{
				Button.color = Color.BLACK.getRGB();
			}
			else if(colorValue.is("Red"))
			{
				Button.color = Color.RED.getRGB();
			}
			else if(colorValue.is("Pink"))
			{
				Button.color = Color.PINK.getRGB();
			}
			else if(colorValue.is("Orange"))
			{
				Button.color = Color.ORANGE.getRGB();
			}
			else if(colorValue.is("Yellow"))
			{
				Button.color = Color.YELLOW.getRGB();
			}
			else if(colorValue.is("Green"))
			{
				Button.color = Color.GREEN.getRGB();
			}
			else if(colorValue.is("Magenta"))
			{
				Button.color = Color.MAGENTA.getRGB();
			}
			else if(colorValue.is("Cyan"))
			{
				Button.color = Color.CYAN.getRGB();
			}
			else if(colorValue.is("Blue"))
			{
				Button.color = Color.BLUE.getRGB();
			}
			else if(colorValue.is("Rainbow"))
			{
				Button.color = ColorUtil.getRainbow(4, 0.8f, 1);
			}
		}
	}
	public static void disable()
	{
        for (Module m : Client.getModule()) 
        {
            if (m.getName() == "ClickGui")
            {
            	m.toggle();
            }
        } 
	}
}