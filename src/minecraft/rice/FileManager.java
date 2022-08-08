package rice;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.google.gson.Gson;

import net.minecraft.client.settings.KeyBinding;
import rice.modules.Module;
import rice.settings.BooleanSetting;
import rice.settings.ModeSetting;
import rice.settings.NumberSetting;
import rice.settings.Setting;

public class FileManager {
	
	private static final Logger logger = LogManager.getLogger();
	private static Gson gson = new Gson();
	
	public static File FOLDER_DIR = new File("rice");
	public static File SETTING_DIR;
	
	public void init() {
		if(!FOLDER_DIR.exists()) FOLDER_DIR.mkdirs();
		SETTING_DIR = new File(FOLDER_DIR, "rice.txt");
		loadSettings();
	}
	
	public void saveSettings() {
		try {
			PrintWriter var9 = new PrintWriter(new FileWriter(this.SETTING_DIR));
			for(Module m: Client.getModule()) {
				var9.println(m.getName() + " Enabled " + String.valueOf(m.isToggled()));
				for(Setting s: m.settings) {
					if(s instanceof ModeSetting) 
						var9.println(m.getName() + " " + ((ModeSetting)s).name + " " + String.valueOf(((ModeSetting)s).get()));
					else if(s instanceof BooleanSetting) 
						var9.println(m.getName() + " " + ((BooleanSetting)s).name + " " + String.valueOf(((BooleanSetting)s).get()));
					else if(s instanceof NumberSetting) 
						var9.println(m.getName() + " " + ((NumberSetting)s).name + " " + String.valueOf(((NumberSetting)s).get()));
				}
				var9.println(" ");
			}
			var9.close();
		}catch(Exception e) {
			logger.error("Failed to save Render options", e);
		}
	}
	
	public void loadSettings() {
		try{
			//System.out.println("false");
			if (!this.SETTING_DIR.exists()) return;
			//System.out.println("true");

			BufferedReader var9 = new BufferedReader(new FileReader(this.SETTING_DIR));
			String var2 = "";

			while ((var2 = var9.readLine()) != null){
				//System.out.println("loaded");
				try{
					if(var2.equals(" ")) continue;
					String[] var8 = var2.split(" ");

					for(Module m: Client.getModule()) {
						if (m.getName().equals(var8[0])) {
							if(var8[1].equals("Enabled")) m.toggled = var8[2].equals("true");
							else {
								for(Setting s: m.settings) {
									if(var8[1].equals(s.name)) {
										if(s instanceof ModeSetting) ((ModeSetting)s).index = ((ModeSetting)s).modes.indexOf(var8[2]);
										else if(s instanceof BooleanSetting) ((BooleanSetting)s).setEnabled(var8[2].equals("true"));
										else if(s instanceof NumberSetting) ((NumberSetting)s).setValue(Double.valueOf(var8[2]));
									}
								}
							}
						}
					}
                }catch (Exception var101) {
                    logger.warn("Skipping bad option: " + var2);
                    var101.printStackTrace();
                }
            }
            KeyBinding.resetKeyBindingArrayAndHash();
            var9.close();
        }catch (Exception var111) {
            logger.error("Failed to load Render options", var111);
        }
	}
}
