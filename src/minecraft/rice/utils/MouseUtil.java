package rice.utils;

import org.lwjgl.input.Mouse;

public class MouseUtil implements MCHook
{
	public static boolean isDown(int key)
	{
		return Mouse.isButtonDown(key);
	}
}
