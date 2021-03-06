package rice.modules.render;

import org.lwjgl.input.Keyboard;

import net.minecraft.util.MovingObjectPosition;
import rice.events.Event;
import rice.events.listeners.EventUpdate;
import rice.modules.Module;
import rice.modules.Module.Category;
import rice.settings.ModeSetting;
import rice.utils.AnimationUtil;
import rice.utils.MCHook;

public class Animations extends Module implements MCHook
{
	public Animations() 
	{
		super("Animations", Keyboard.KEY_NONE, Category.RENDER, "");
		this.addSettings(modeValue);
		this.toggled = true;
	}
	public static ModeSetting modeValue = new ModeSetting("Mode","Tap","None","1.7","Tap");
	public void onEvent(Event e) 
	{
		if(e instanceof EventUpdate)
		{
			if(!modeValue.is("None"))
			{
				if(mc.objectMouseOver.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK && _gs.keyBindAttack.getIsKeyPressed())
				{
					if (!mc.thePlayer.isSwingInProgress || mc.thePlayer.swingProgressInt >= mc.thePlayer.getArmSwingAnimationEnd() / 2 || mc.thePlayer.swingProgressInt < 0)
			        {
						mc.thePlayer.swingProgressInt = -1;
						mc.thePlayer.isSwingInProgress = true;
			        }
				}
			}
		}
	}
	public static void renderAnimation(float f)
	{
		if(modeValue.is("None"))
		{
			
		}
		else if(modeValue.is("1.7"))
		{
			AnimationUtil.anim17(f);
		}
		else if(modeValue.is("Tap"))
		{
			AnimationUtil.animTap(f);
		}
	}
}