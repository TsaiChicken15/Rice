package rice.modules.blatant;

import org.lwjgl.input.Keyboard;

import rice.Client;
import rice.events.Event;
import rice.events.listeners.EventGetHitbox;
import rice.events.listeners.EventUpdate;
import rice.modules.Module;
import rice.settings.NumberSetting;
import rice.utils.MCHook;
import rice.utils.RandomUtil;

public class Hitbox extends Module implements MCHook{
	public Hitbox() {
		super("Hitbox", Keyboard.KEY_NONE, Category.BLATANT, "¡±rExpands players hitbox");
		this.addSettings(sizeValue, chanceValue);
	}
	public static NumberSetting sizeValue = new NumberSetting("Size", 0.1, 0, 1, 0.01);
	public static NumberSetting chanceValue = new NumberSetting("Chance", 100, 1, 100, 1);
	public void onEvent(Event e) 
	{
	    if(e instanceof EventGetHitbox) 
	    {
	    	if(RandomUtil.randomInt(0, 100) > chanceValue.get())
	    		return;
	    	e.cancelEvent();
	    }
	}
	public void onEvent2(Event e) 
	{
		if(e instanceof EventUpdate) 
		{
			this.additionalInfo = String.valueOf(sizeValue.get());
		}
	}
}