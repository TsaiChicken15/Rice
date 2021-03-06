package rice.modules.utility;

import java.util.ArrayList;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import rice.Client;
import rice.events.Event;
import rice.events.listeners.EventMove;
import rice.events.listeners.EventUpdate;
import rice.modules.Module;
import rice.settings.BooleanSetting;
import rice.settings.ModeSetting;
import rice.utils.ItemUtil;
import rice.utils.KeybindUtil;
import rice.utils.MCHook;

public class Scaffold extends Module implements MCHook
{
	public Scaffold() 
	{
		super("Scaffold", Keyboard.KEY_Z, Category.UTILITY,"Assists with bridging");
		this.addSettings(modeValue,autoSwitchValue);
	}
	public static ModeSetting modeValue = new ModeSetting("Mode","Legit","Legit","ZeroXZ");
	public static BooleanSetting autoSwitchValue =  new BooleanSetting("AutoSwitch", true);
	private boolean placeTried = false;
	private double lastY;
	private int oldSlot = -1;
	public void onEnable() 
	{
		if(autoSwitchValue.get())
		{
			oldSlot = mc.thePlayer.inventory.currentItem;
			for (int index = 36; index <= 44; ++index) 
			{
	            final ItemStack stack = mc.thePlayer.inventoryContainer.getSlot(index).getStack();
	            if(stack == null)
	            {
	            	continue;
	            }
	            if (stack.getItem() instanceof ItemBlock && !ItemUtil.isBadBlock(stack)) 
	            {
	            	_ku.onTick(_gs.keyBindsHotbar.clone()[index - 36].getKeyCode());
	            	//mc.thePlayer.inventory.currentItem = index - 36;
	            }
	        }
		}
		placeTried = false;
	}
	public void onDisable() 
	{
		_ku.state(_gs.keyBindSneak.getKeyCode(), _ku.isDown(_gs.keyBindSneak.getKeyCode()));
		if(autoSwitchValue.get() && oldSlot > -1)
		{
			_ku.onTick(_gs.keyBindsHotbar.clone()[oldSlot].getKeyCode());
		}
	}
	public void onEvent2(Event e)
    {
		this.additionalInfo = modeValue.get();
    }
	public void onEvent(Event e) 
	{
	    if (e instanceof EventUpdate)
	    {
	    	if(_ku.isDown(_gs.keyBindJump.getKeyCode()) && mc.thePlayer.movementInput.moveForward > 0) 
			{
				_gs.keyBindSneak.pressed = false;
			}
	    	if (mc.thePlayer.onGround) 
	    	{
	    		lastY = mc.thePlayer.posY;
	    	}
	    	if (!mc.thePlayer.onGround && mc.thePlayer.fallDistance == 0 && mc.thePlayer.rotationPitch > 0 && mc.thePlayer.movementInput.jump && placeTried)
	    	{
	    		_ku.onTick(_gs.keyBindUseItem.getKeyCode(), 50);
    			placeTried = true;
	    	}
	    	else if(mc.thePlayer.onGround)
	    	{
				placeTried = false;
	    	}
	    	
	    	if(modeValue.is("Legit"))
			{
		    	for (double x = -0.25D; x <= 0.25D; x += 0.25D) 
		    	{
		    		for (double z = -0.25D; z <= 0.25D; z += 0.25D) 
		    		{
		    			if (mc.theWorld.getCollidingBoundingBoxes(mc.thePlayer, mc.thePlayer.getEntityBoundingBox().offset(0.0D, -0.5D, 0.0D).expand(x, 0.0D, z)).isEmpty()) 
		    			{
		    				for (double x1 = -0.045D; x1 <= 0.045D; x1 += 0.045D) 
		    				{
		    					for (double z1 = -0.045D; z1 <= 0.045D; z1 += 0.045D) 
		    					{
		    						if (mc.thePlayer.getHeldItem() == null || !(mc.thePlayer.getHeldItem().getItem() instanceof ItemBlock)) 
		    						{
		    							break;
		    						}
		    						if (mc.theWorld.getCollidingBoundingBoxes(mc.thePlayer, mc.thePlayer.getEntityBoundingBox().offset(0.0D, -0.5D, 0.0D).expand(x1, 0.0D, z1)).isEmpty() && !placeTried)
		    						{
		    							_ku.onTick(_gs.keyBindUseItem.getKeyCode(), 50);
		    		        			placeTried = true;
		    						}
		    						else 
		    						{
		    							placeTried = false;
		    						}
		    					}
		    				}
		    			}
		    			if (mc.theWorld.getCollidingBoundingBoxes(mc.thePlayer, mc.thePlayer.getEntityBoundingBox().offset(0.0D, -0.5D, 0.0D).expand(x, 0.0D, z)).isEmpty() && mc.thePlayer.posY % 0.015625 < 0.0001) 
		    			{
		    				_ku.state(_gs.keyBindSneak.getKeyCode(), true);
		    				return;
		    			}
		    			if (!mc.theWorld.getCollidingBoundingBoxes(mc.thePlayer, mc.thePlayer.getEntityBoundingBox().offset(0.0D, -0.125D, 0.0D)).isEmpty() && !_ku.isDown(_gs.keyBindSneak.getKeyCode())) 
		    			{
		    				_ku.state(_gs.keyBindSneak.getKeyCode(), false);
		    			}
		    		} 
		    	} 
			}
	    } 
	    if (e instanceof EventMove) 
		{	
			if(modeValue.is("ZeroXZ"))
			{
				if(mc.thePlayer.onGround)
					((EventMove)e).isSafewalk = true;
				for (double x = -0.2D; x <= 0.2D; x += 0.2D) 
		    	{
		    		for (double z = -0.2D; z <= 0.2D; z += 0.2D) 
		    		{
						if (mc.theWorld.getCollidingBoundingBoxes(mc.thePlayer, mc.thePlayer.getEntityBoundingBox().offset(0.0D, -0.5D, 0.0D).expand(x, 0.0D, z)).isEmpty() && !placeTried)
						{
							_ku.onTick(_gs.keyBindUseItem.getKeyCode(), 49);
		        			placeTried = true;
						}
						else 
						{
							placeTried = false;
						}
		    		} 
		    	} 
			}
		}
	}
}