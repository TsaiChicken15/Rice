package rice.modules.render;

import org.lwjgl.input.Keyboard;

import rice.modules.Module;
import rice.utils.MCHook;

public class FullBright extends Module implements MCHook
{
	public FullBright() 
	{
		super("FullBright", Keyboard.KEY_NONE, Category.RENDER, "Removes darkness");
	}
	public void onEnable() 
	{
		_gs.gammaSetting = 100.0f;
	}
}