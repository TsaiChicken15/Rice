package rice.modules.combat;

import javax.swing.JSlider;

import org.lwjgl.input.Keyboard;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityGolem;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemAxe;
import net.minecraft.item.ItemSword;
import net.minecraft.util.MovingObjectPosition;
import rice.events.Event;
import rice.events.listeners.EventAttack;
import rice.events.listeners.EventRender3D;
import rice.events.listeners.EventUpdate;
import rice.jui.JArrayList;
import rice.jui.JClickGUI;
import rice.modules.Module;
import rice.settings.BooleanSetting;
import rice.settings.ModeSetting;
import rice.settings.NumberSetting;
import rice.utils.MCHook;
import rice.utils.RandomUtil;


public class AutoClicker extends Module implements MCHook
{
	public AutoClicker() 
	{
		super("AutoClicker", Keyboard.KEY_NONE, Category.COMBAT, "Automatically clicks for you while holding down the attack button");
		this.addSettings(itemValue, blockHitValue, minCPSValue, maxCPSValue, jitterValue, blockHitDurationValue);
	}
	public static NumberSetting minCPSValue = new NumberSetting("MinCPS", 5, 1, 50, 1);
	public static NumberSetting maxCPSValue = new NumberSetting("MaxCPS", 8, 1, 50, 1);
	public static NumberSetting jitterValue = new NumberSetting("Jitter", 0, 0, 5, 0.5);
	public static BooleanSetting blockHitValue	 = new BooleanSetting("BlockHit",false);
	public static NumberSetting blockHitDurationValue = new NumberSetting("Duration", 200, 0, 1000, 1);
	public static ModeSetting itemValue = new ModeSetting("LimitItem","None","None","Sword","Axe","Sword & Axe");
	private long rightDelay = 1000 / RandomUtil.randomInt((int)minCPSValue.get(), (int)maxCPSValue.get());
    private long rightLastSwing = 0L;
    private long leftDelay = 1000 / RandomUtil.randomInt((int)minCPSValue.get(), (int)maxCPSValue.get());
    private long leftLastSwing = 0L;
    private long blockHitDelay = (long)(blockHitDurationValue.get() * 2);
    private long lastblockHit = 0L;
	private boolean blocking;
	public void onEvent2(Event e)
	{
		if (e instanceof EventUpdate) 
		{
			this.additionalInfo = String.valueOf(minCPSValue.get()) + ", " + String.valueOf(maxCPSValue.get());
			if(minCPSValue.get() > maxCPSValue.get()) 
			{
				double temp = minCPSValue.get();
				for(JSlider js: JClickGUI.sliders) {
					if(js.getName().contains(this.name) && js.getName().contains(minCPSValue.name))
						js.setValue((int) (maxCPSValue.get() * 100));
					if(js.getName().contains(this.name) && js.getName().contains(maxCPSValue.name))
						js.setValue((int) (minCPSValue.get() * 100));
				}
				minCPSValue.setValue(maxCPSValue.get());
				maxCPSValue.setValue(temp);
			}
		}
	}
	public void onEvent(Event e) 
	{
		if (e instanceof EventRender3D) 
		{
			if(_mu.isDown(1) && !blocking)
			{
				blocking = true;
			}
			else if(!_mu.isDown(1) && blocking)
			{
				blocking = false;
			}
			
			if(!blocking && mc.thePlayer.isBlocking() && (mc.thePlayer.getHeldItem().getItem() instanceof ItemSword) && System.currentTimeMillis() - lastblockHit >= blockHitDelay)
			{
				_ku.state(_gs.keyBindUseItem.getKeyCode(), false);
				lastblockHit = System.currentTimeMillis();
				leftLastSwing = System.currentTimeMillis();
			}
			
			if(itemValue.is("None")) {}
			else if(mc.thePlayer.inventory.getCurrentItem() != null)
			{
				if(itemValue.is("Sword & Axe")) 
				{
					if((!(mc.thePlayer.getHeldItem().getItem() instanceof ItemSword) && !(mc.thePlayer.getHeldItem().getItem() instanceof ItemAxe)) || mc.thePlayer.getHeldItem().getItem() == null)
					{
						return;
					}
				}
				else if(itemValue.is("Sword")) 
				{
					if(!(mc.thePlayer.getHeldItem().getItem() instanceof ItemSword) || mc.thePlayer.getHeldItem().getItem() == null)
					{
						return;
					}
				}
				else if(itemValue.is("Axe")) 
				{
					if(!(mc.thePlayer.getHeldItem().getItem() instanceof ItemAxe) || mc.thePlayer.getHeldItem().getItem() == null)
					{
						return;
					}
				}
			}
			else
			{
				return;
			}
			
			if (!_gs.keyBindUseItem.getIsKeyPressed() && _gs.keyBindAttack.getIsKeyPressed() && System.currentTimeMillis() - leftLastSwing >= leftDelay && 
					!mc.thePlayer.isBlocking() && !mc.thePlayer.isUsingItem()) 
			{
				if(mc.objectMouseOver.typeOfHit == MovingObjectPosition.MovingObjectType.ENTITY || mc.objectMouseOver.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK)
					_ku.onTick(_gs.keyBindAttack.getKeyCode());
				/*else
					mc.thePlayer.swingItem();*/
				
				leftLastSwing = System.currentTimeMillis();
			    leftDelay = 1000 / RandomUtil.randomInt((int)minCPSValue.get(), (int)maxCPSValue.get());
	            if (jitterValue.get() > 0 && mc.playerController.curBlockDamageMP == 0f && 
	            		mc.thePlayer.rotationPitch - jitterValue.get() * 2 - jitterValue.get() > -90 && 
	            		mc.thePlayer.rotationPitch + jitterValue.get() * 2 - jitterValue.get() < 90) 
	            {
	            	mc.thePlayer.rotationYaw += shake();
					mc.thePlayer.rotationPitch += shake();
				}
	        }
		} 
		if(e instanceof EventAttack)
		{
			if(((EventAttack)e).entity instanceof EntityPlayer || 
					((EventAttack)e).entity instanceof EntityMob || 
					((EventAttack)e).entity instanceof EntityAnimal || 
					((EventAttack)e).entity instanceof EntityVillager || 
					((EventAttack)e).entity instanceof EntityGolem)
			{
				EntityLivingBase t = (EntityLivingBase) ((EventAttack)e).entity;
				if(blockHitValue.get())
				{
					if(t != null)
					{
						if(mc.thePlayer.inventory.getCurrentItem() != null)
						{
							if(!mc.thePlayer.isBlocking() && (mc.thePlayer.getHeldItem().getItem() instanceof ItemSword) && System.currentTimeMillis() - lastblockHit >= blockHitDelay / 2)
							{
								_ku.state(_gs.keyBindUseItem.getKeyCode(), true);
								leftLastSwing = System.currentTimeMillis();
								t = null;
							}
						}
					}
				}
			}
		}
    }
	
	public double shake() 
	{
		return Math.random() * jitterValue.get() * 2 - jitterValue.get();
	}
}