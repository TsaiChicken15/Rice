package rice.command.commands;

import rice.Client;
import rice.command.Command;
import rice.modules.Module;
import rice.notification.Notification;
import rice.notification.NotificationManager;
import rice.notification.NotificationType;

public class Toggle extends Command{

	public Toggle() {
		super("Toggle", "Toggles a module by name", "toggle <name>", "t", "toggle");
	}

	@Override
	public void onCommand(String[] args, String command) {
		if(args.length > 0) {
			String moduleName = args[0];
			
			boolean foundModule = false;
			
			for(Module m: Client.getModule()) {
				if(m.getName().equalsIgnoreCase(moduleName)) {
					m.toggle();
					foundModule = true;
					break;
				}
			}
			
			if(!foundModule)
				NotificationManager.show(new Notification(NotificationType.WARNING, "��l[Toggle]", "Could not find module.", 3));
		}
	}
}