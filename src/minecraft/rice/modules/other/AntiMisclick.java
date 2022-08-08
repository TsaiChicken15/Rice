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

public class AntiMisclick extends Module
{
	public AntiMisclick() 
	{
		super("AntiMisclick", Keyboard.KEY_NONE, Category.OTHER, "");
		this.toggled = true;
	}
}