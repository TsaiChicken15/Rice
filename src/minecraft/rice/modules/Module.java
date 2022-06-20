package rice.modules;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import rice.events.Event;
import rice.modules.Module.Category;
import rice.modules.other.Auto;
import rice.modules.other.ToggleSound;
import rice.notification.Notification;
import rice.notification.NotificationManager;
import rice.notification.NotificationType;
import rice.settings.KeybindSetting;
import rice.settings.Setting;
import rice.utils.MCHook;

public class Module implements MCHook 
{
    public int index;
    public boolean toggled, expanded;
    public String name, descript, additionalInfo;;
    public KeybindSetting keyCode = new KeybindSetting(0);
    public Category category;
	public List<Setting> settings = new ArrayList();
    public Module(String name, int key, Category category, String descript) 
    {
        this.name = name;
        keyCode.code = key;
        this.category = category;
        this.descript = descript;
        this.additionalInfo = "";
        this.toggled = false;
        this.addSettings(keyCode);
    }
    public void addSettings(Setting... settings) 
    {
    	this.settings.addAll(Arrays.asList(settings));
    }
    public boolean isToggled() 
    {
    	return this.toggled;
    }
    public String getName() 
    {
    	return this.name;
    }
    public int getKey() 
    {
    	return keyCode.code;
    }
    public Category getCategory() 
    {
    	return this.category;
    }
    public void setName(String name) 
    {
    	this.name = name;
    }
    public void setKey(int key) 
    {
    	keyCode.code = key;
    }
    public void setCategory(Category category) 
    {
    	this.category = category;
    }
    public void setToggled(boolean toggled) 
    {
		this.toggled = toggled;
		handleToggled();
	}
    public void toggle() 
    {
        this.toggled = !this.toggled;
        handleToggled();
    }
    public void handleToggled() 
    {
    	if (this.toggled) {
            onEnable();
            if(this.name == "ClickGui") return;
            if(Auto.modeValue.is("Ghost") && this.getCategory() == Category.BLATANT) return;
            NotificationManager.show(new Notification(NotificationType.INFO, "¡±l" + this.name + " ¡±lEnabled", 1));
        } else {
            onDisable();
            if(this.name == "ClickGui") return;
            if(Auto.modeValue.is("Ghost") && this.getCategory() == Category.BLATANT) return;
            NotificationManager.show(new Notification(NotificationType.INFO, "¡±l" + this.name + " ¡±lDisabled", 1));
        }
    	if(this.name != "ClickGui")
    		ToggleSound.playButtonPressSound();
    }
    public void onEnable() {}
    public void onDisable() {}
    public void setup() {}
    public void onEvent(Event e) {}
    public void onEvent2(Event e) {}
    public static enum Category 
    {
        COMBAT("Combat"),
        BLATANT("Blatant"),
        RENDER("Render"),
        UTILITY("Utility"),
        OTHER("Other");
        public String name;
        public int moduleIndex;
        private Category(String name) 
        {
        	this.name = name;
        }
    }	
}
