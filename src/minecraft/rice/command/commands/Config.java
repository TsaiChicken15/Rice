package rice.command.commands;

import java.io.File;

import org.lwjgl.input.Keyboard;

import rice.Client;
import rice.FileManager;
import rice.command.Command;
import rice.modules.Module;
import rice.modules.other.ToggleSound;
import rice.notification.Notification;
import rice.notification.NotificationManager;
import rice.notification.NotificationType;

public class Config extends Command{

	public Config() {
		super("Config", "Saves configs", "config load|save <name>", "config");
	}

	@Override
	public void onCommand(String[] args, String command) {
		if(args.length == 2) {
			if(args[0].equalsIgnoreCase("save")) {
				Client.fileManager.SETTING_DIR = new File(Client.fileManager.FOLDER_DIR, args[1] + ".txt");
				Client.fileManager.saveSettings();
				ToggleSound.playButtonPressSound();
				NotificationManager.show(new Notification(NotificationType.INFO, "¡±l[Config]", "Config saved.", 3));
			}
			if(args[0].equalsIgnoreCase("load")) {
				Client.fileManager.SETTING_DIR = new File(Client.fileManager.FOLDER_DIR, args[1] + ".txt");
				if (!Client.fileManager.SETTING_DIR.exists()) {
					ToggleSound.playButtonPressSound();
					NotificationManager.show(new Notification(NotificationType.WARNING, "¡±l[Config]", "Could not find the config.", 3));
					return;
				}
				Client.fileManager.loadSettings();
				ToggleSound.playButtonPressSound();
				NotificationManager.show(new Notification(NotificationType.INFO, "¡±l[Config]", "Config loaded.", 3));
			}
		}
	}
}