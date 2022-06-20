package rice.utils;

import org.lwjgl.input.Keyboard;

import net.minecraft.client.settings.KeyBinding;

public class KeybindUtil implements MCHook
{
	public static long lastClick;
	public static void state(int key, boolean state) 
	{
		KeyBinding.setKeyBindState( key , state );
	}
	public static void onTick(int key) 
	{
		KeyBinding.onTick(key);
	}
	public static void onTick(int key, long delay) 
	{
		if(System.currentTimeMillis() - lastClick >= delay) 
		{
			KeyBinding.onTick(key);
			lastClick = System.currentTimeMillis();
		}
	}
	public static boolean isDown(int key)
	{
		return Keyboard.isKeyDown(key);
	}
}
