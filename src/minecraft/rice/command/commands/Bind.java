package rice.command.commands;

import org.lwjgl.input.Keyboard;

import rice.Client;
import rice.command.Command;
import rice.modules.Module;
import rice.modules.other.ToggleSound;
import rice.notification.Notification;
import rice.notification.NotificationManager;
import rice.notification.NotificationType;

public class Bind extends Command{

	public Bind() {
		super("Bind", "Binds a module by name", "bind <name> <key> | clear", "bind", "b");
	}

	@Override
	public void onCommand(String[] args, String command) {
		if(args.length == 2) {
			String moduleName = args[0];
			String keyName = args[1];
			
			for(Module m: Client.getModule()) {
				if(m.getName().equalsIgnoreCase(moduleName)) {
					m.setKey(Keyboard.getKeyIndex(keyName.toUpperCase()));
					ToggleSound.playButtonPressSound();
					NotificationManager.show(new Notification(NotificationType.INFO, "��l[Bind]", "Bound " + moduleName + " to " + Keyboard.getKeyName(m.getKey()), 3));
					break;
				}
			}
		}
		
		if(args.length == 1) {
			if(args[0].equalsIgnoreCase("clear")) {
				for(Module m: Client.getModule()) {
					m.setKey(Keyboard.KEY_NONE);
				}
			}
			ToggleSound.playButtonPressSound();
			NotificationManager.show(new Notification(NotificationType.INFO, "��l[Bind]", "Cleared all binds.", 3));
		}
	}
}