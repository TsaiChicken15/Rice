package rice.modules.render;

import org.lwjgl.input.Keyboard;

import rice.events.Event;
import rice.events.listeners.EventHurtCam;
import rice.events.listeners.EventUpdate;
import rice.modules.Module;
import rice.settings.BooleanSetting;
import rice.settings.NumberSetting;
import rice.utils.MCHook;

public class NoRender extends Module implements MCHook
{
	public NoRender() {
		super("NoRender", Keyboard.KEY_NONE, Category.RENDER, "");
		this.toggled = true;
		this.addSettings(achieveValue,blindValue,hurtValue,nauseaValue,pumpkinValue,heightValue);
	}
	public static BooleanSetting achieveValue = new BooleanSetting("NoAchievement",true);
	public static BooleanSetting blindValue = new BooleanSetting("NoBlindness",true);
	public static BooleanSetting hurtValue = new BooleanSetting("NoHurtCam",true);
	public static BooleanSetting nauseaValue = new BooleanSetting("NoNausea",true);
	public static BooleanSetting pumpkinValue = new BooleanSetting("NoPumpkin",true);
	public static NumberSetting heightValue = new NumberSetting("FireHeight", -0.3D, -1.0D, 0.0D, 0.05D);
	public void onEvent(Event e) {
		if(e instanceof EventUpdate) {
			if(achieveValue.get()) {
				mc.guiAchievement.clearAchievements();
			}
		}
		if(e instanceof EventHurtCam) {
			if(hurtValue.get()) {
				e.cancelEvent();
			}
		}
	}
}