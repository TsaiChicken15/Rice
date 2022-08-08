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
import rice.settings.NumberSetting;
import rice.ui.clickgui.simple.button.Button;
import rice.utils.ColorUtil;
import rice.utils.MSTimer;

public class ClickGui extends Module
{
	public ClickGui() 
	{
		super("ClickGui", Keyboard.KEY_RSHIFT, Category.OTHER, "");
		this.addSettings(colorValue, colorRedValue, colorGreenValue, colorBlueValue, colorAlphaValue);
	}
	public static ModeSetting colorValue = new ModeSetting("Color","Custom","Custom","Rainbow");
	public static NumberSetting colorRedValue = new NumberSetting("Red", 255, 0, 255, 1);
	public static NumberSetting colorGreenValue = new NumberSetting("Green", 255, 0, 255, 1);
	public static NumberSetting colorBlueValue = new NumberSetting("Blue", 255, 0, 255, 1);
	public static NumberSetting colorAlphaValue = new NumberSetting("Alpha", 200, 0, 255, 1);
	private MSTimer timer = new MSTimer();
	public void onEnable() 
	{
		timer.reset();
		if(mc.currentScreen == null) 
		{
			if(Client.isEnabled("AntiMisclick") != null && GuiScreen.isCtrlKeyDown())
			{
				mc.displayGuiScreen(Client.ClickGui);
				ToggleSound.playButtonPressSound();
			}
			else if(Client.isEnabled("AntiMisclick") == null)
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
			if(colorValue.is("Rainbow"))
			{
				Button.color = ColorUtil.getRainbow(4, 0.8f, 1);
			}
			else if(colorValue.is("Custom"))
			{
				Button.color = new Color((int)colorRedValue.get(), (int)colorGreenValue.get(), (int)colorBlueValue.get(), (int)colorAlphaValue.get()).getRGB();
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