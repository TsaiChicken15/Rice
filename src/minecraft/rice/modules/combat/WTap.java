package rice.modules.combat;

import org.lwjgl.input.Keyboard;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityGolem;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemSword;
import net.minecraft.network.play.client.C0BPacketEntityAction;
import rice.Client;
import rice.events.Event;
import rice.events.listeners.EventAttack;
import rice.modules.Module;
import rice.settings.BooleanSetting;
import rice.settings.NumberSetting;
import rice.utils.MCHook;
import rice.utils.MSTimer;
import rice.utils.MovementUtil;

public class WTap extends Module implements MCHook{
	public WTap() {
		super("WTap", Keyboard.KEY_NONE, Category.COMBAT,"");
		this.addSettings(onlyMoveValue, onlyGroundValue, delayValue);
	}
	public static BooleanSetting onlyMoveValue = new BooleanSetting("MoveOnly",true);
	public static BooleanSetting onlyGroundValue = new BooleanSetting("GroundOnly",false);
	public static NumberSetting delayValue = new NumberSetting("Delay", 0, 0, 500, 1);
	private MSTimer timer = new MSTimer();
	public void onEvent(Event e) 
	{
	    if(e instanceof EventAttack && ((EventAttack)e).entity != null && Client.nullCheck()) 
	    {
	    	if(!(((EventAttack)e).entity instanceof EntityPlayer || 
	    			((EventAttack)e).entity instanceof EntityMob || 
	    			((EventAttack)e).entity instanceof EntityAnimal || 
	    			((EventAttack)e).entity instanceof EntityGolem || 
	    			((EventAttack)e).entity instanceof EntityVillager))
	    	{
	    		return;
	    	}
	    	EntityLivingBase t = (EntityLivingBase) ((EventAttack)e).entity;
	    	if (!timer.hasTimePassed((long) delayValue.get()) ||
	    			(!MovementUtil.isMoving() && onlyMoveValue.get()) || (!mc.thePlayer.onGround && onlyGroundValue.get())) 
	    	{
	    		return; 
	    	}
	    	
    		if (mc.thePlayer.isSprinting()) 
    		{
    			mc.thePlayer.setSprinting(false); 
    		}
			mc.getNetHandler().addToSendQueue(new C0BPacketEntityAction(mc.thePlayer, C0BPacketEntityAction.Action.START_SPRINTING));
			mc.thePlayer.field_175171_bO = true;
	    }
	}
}