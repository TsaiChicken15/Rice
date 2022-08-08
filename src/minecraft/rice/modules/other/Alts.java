package rice.modules.other;

import org.lwjgl.input.Keyboard;

import rice.Client;
import rice.events.Event;
import rice.modules.Module;
import rice.settings.ModeSetting;

public class Alts extends Module
{
	public Alts() 
	{
		super("Alts", Keyboard.KEY_NONE, Category.OTHER, "");
		this.toggled = true;
	}
}