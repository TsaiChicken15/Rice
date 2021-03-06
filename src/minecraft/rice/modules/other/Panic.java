package rice.modules.other;

import org.lwjgl.input.Keyboard;

import rice.Client;
import rice.modules.Module;
import rice.notification.Notification;
import rice.notification.NotificationManager;
import rice.notification.NotificationType;

public class Panic extends Module
{
	public Panic()
	{
		super("Panic", Keyboard.KEY_NONE, Category.OTHER, "Disables all currently enabled modules");
	}
	public void onEnable() 
	{
		int a = -1;
		for(Module m: Client.getModule())
		{
			if(m.getName() == "Auto" || m.getName() == "ClickGui" || m.getName() == "ToggleSound" || m.getName() == "Alts" || m.getName() == "ActiveMods") 
			{
				continue;
			}
			if(m.isToggled())
			{
				a++;
				m.toggled = false;
				m.onDisable();
			}
		}
		if(a > 0)
		{
			NotificationManager.show(new Notification(NotificationType.INFO, "??l" + a + " module(s) ??ldisabled", 2));
			ToggleSound.playButtonPressSound();
		}
	}
}