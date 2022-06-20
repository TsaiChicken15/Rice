package rice.ui;

import rice.Client;
import rice.events.listeners.EventRenderGUI;
import rice.notification.NotificationManager;
import rice.utils.MCHook;

public class Hud implements MCHook
{
	public void draw() 
	{
	    Client.onEvent(new EventRenderGUI());
	    NotificationManager.render();
	}
}