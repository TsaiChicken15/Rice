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

public class AntiBlatant extends Module
{
	public AntiBlatant() 
	{
		super("AntiBlatant", Keyboard.KEY_NONE, Category.OTHER, "");
		this.toggled = true;
	}
	public void onEvent(Event e)
	{
		if (e instanceof EventUpdate) 
		{
			for(Module m: Client.getModule())
			{
				if(m.isToggled() && m.getCategory() == Category.BLATANT)
				{
					m.toggled = false;
					NotificationManager.show(new Notification(NotificationType.INFO, "��l[AntiBlatant]", "Turn me off", 2));
				}
			}
		}
	}
}