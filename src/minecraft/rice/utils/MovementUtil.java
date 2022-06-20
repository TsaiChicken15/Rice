package rice.utils;

import net.minecraft.client.Minecraft;
import net.minecraft.stats.StatList;

public class MovementUtil implements MCHook
{
	public static boolean isMoving() 
	{
		if (Math.sqrt(mc.thePlayer.motionX * mc.thePlayer.motionX + mc.thePlayer.motionZ * mc.thePlayer.motionZ) > 0.0D) 
		{
			return true;
		}
		return false;
	}
}