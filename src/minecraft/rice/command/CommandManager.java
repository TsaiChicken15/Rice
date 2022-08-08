package rice.command;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import rice.command.commands.Bind;
import rice.command.commands.Config;
import rice.command.commands.Toggle;
import rice.events.listeners.EventChat;
import rice.notification.Notification;
import rice.notification.NotificationManager;
import rice.notification.NotificationType;

public class CommandManager {

	public List<Command> commands = new ArrayList<Command>();
	public String prefix = ".";
	
	public void setup() {
		commands.add(new Bind());
		commands.add(new Config());
		commands.add(new Toggle());
	}
	public void handleChat(EventChat chat) {
		String message = chat.getMessage();
		
		if(!message.startsWith(prefix)) return;
		
		message = message.substring(prefix.length());
		
		chat.cancelEvent();
		
		boolean foundCommand = false;
		
		if(message.split(" ").length > 0) {
			String commandName = message.split(" ")[0];
			for(Command c: commands) {
				if(c.aliases.contains(commandName)) {
					c.onCommand(Arrays.copyOfRange(message.split(" "), 1, message.split(" ").length), message);
					foundCommand = true;
					break;
				}
			}
		}
		if(!foundCommand)
			NotificationManager.show(new Notification(NotificationType.WARNING, "��l[CommandManager]", "Could not find command.", 3));
	}
}
