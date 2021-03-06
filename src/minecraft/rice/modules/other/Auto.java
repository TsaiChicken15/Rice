package rice.modules.other;

import org.lwjgl.input.Keyboard;

import rice.Client;
import rice.events.Event;
import rice.events.listeners.EventUpdate;
import rice.modules.Module;
import rice.notification.Notification;
import rice.notification.NotificationManager;
import rice.notification.NotificationType;
import rice.settings.ModeSetting;

public class Auto extends Module
{
	public Auto() 
	{
		super("Auto", Keyboard.KEY_NONE, Category.OTHER, "");
		this.addSettings(modeValue);
		this.toggled = true;
	}
	public static ModeSetting modeValue = new ModeSetting("Cheats","Ghost","Ghost","Blatant");
	public void onEvent2(Event e)
	{
		if (e instanceof EventUpdate) 
		{
			this.additionalInfo = modeValue.get();
		}
	}
	public void onEvent(Event e)
	{
		if (e instanceof EventUpdate) 
		{
			if(modeValue.is("Ghost"))
			{
				for(Module m: Client.getModule())
				{
					if(m.isToggled() && m.getCategory() == Category.BLATANT)
					{
						m.toggled = false;
						NotificationManager.show(new Notification(NotificationType.INFO, "??l" + "Please change Auto mode to Blatant", 2));
					}
				}
			}
		}
	}
}