package rice.modules.render;

import org.lwjgl.input.Keyboard;

import rice.modules.Module;
import rice.modules.Module.Category;
import rice.utils.MCHook;

public class CameraClip extends Module implements MCHook
{
	public CameraClip() {
		super("CameraClip", Keyboard.KEY_NONE, Category.RENDER, "");
	}
}