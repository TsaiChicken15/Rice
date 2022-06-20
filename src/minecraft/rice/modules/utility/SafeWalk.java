package rice.modules.utility;

import org.lwjgl.input.Keyboard;

import net.minecraft.entity.Entity;
import rice.events.Event;
import rice.events.listeners.EventMove;
import rice.events.listeners.EventUpdate;
import rice.modules.Module;
import rice.settings.ModeSetting;
import rice.utils.KeybindUtil;

public class SafeWalk extends Module 
{
	public SafeWalk() 
	{
		super("SafeWalk", Keyboard.KEY_NONE, Category.UTILITY, "Prevents you from walking off the edge of blocks"); 
		this.addSettings(modeValue);
	}
	public static ModeSetting modeValue = new ModeSetting("Mode","Legit","Legit","ZeroXZ");
	public void onEnable() 
	{
		_gs.keyBindSneak.pressed = _ku.isDown(_gs.keyBindSneak.getKeyCode()); 
	}
	public void onDisable() 
	{
		_gs.keyBindSneak.pressed = _ku.isDown(_gs.keyBindSneak.getKeyCode()); 
	} 
	public void onEvent2(Event e)
    {
		this.additionalInfo = modeValue.get();
    }
	public void onEvent(Event e) 
	{
		if (e instanceof EventUpdate) 
		{		
			if(modeValue.is("Legit"))
			{
				if(_ku.isDown(_gs.keyBindJump.getKeyCode()) && mc.thePlayer.movementInput.moveForward > 0) 
				{
					_gs.keyBindSneak.pressed = false;
				}
				for(double x = -0.25; x <= 0.25; x += 0.25) 
				{
					for(double z = -0.25; z <= 0.25; z += 0.25) 
					{
						if(mc.theWorld.getCollidingBoundingBoxes(mc.thePlayer, mc.thePlayer.getEntityBoundingBox().offset(0.0D, -0.5D, 0.0D).expand(x, 0.0D, z)).isEmpty() && 
								mc.thePlayer.onGround &&
								!_ku.isDown(_gs.keyBindSneak.getKeyCode())) 
						{
							_gs.keyBindSneak.pressed = true;
							return;
						}
					}
				}
				if(!mc.theWorld.getCollidingBoundingBoxes(mc.thePlayer, mc.thePlayer.getEntityBoundingBox().offset(0.0D, -0.125D, 0.0D)).isEmpty() && 
						!_ku.isDown(_gs.keyBindSneak.getKeyCode())) 
				{
					_gs.keyBindSneak.pressed = false;
				}
			}
		}
		if (e instanceof EventMove) 
		{	
			if(modeValue.is("ZeroXZ") && mc.thePlayer.onGround)
			{
				((EventMove)e).isSafewalk = true;
			}
		}
	}
}

