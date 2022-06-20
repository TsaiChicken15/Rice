package rice.modules.utility;

import org.lwjgl.input.Keyboard;

import net.minecraft.entity.Entity;
import rice.events.Event;
import rice.events.listeners.EventUpdate;
import rice.modules.Module;
import rice.utils.MCHook;


public class Parkour extends Module implements MCHook
{
	public Parkour() {super("Parkour", Keyboard.KEY_NONE, Category.UTILITY, ""); }
	public void onEvent(Event e) 
	{
		if (e instanceof EventUpdate && e.isPRE())
		{
			if (!mc.thePlayer.isSneaking() && mc.thePlayer.onGround &&
					mc.theWorld.getCollidingBoundingBoxes((Entity)mc.thePlayer, mc.thePlayer.getEntityBoundingBox().offset(0.0D, -0.5D, 0.0D).expand(0.0001D, 0.0D, 0.0001D)).isEmpty()) 
			{
				mc.thePlayer.jump();
			}
		}
	}
}
