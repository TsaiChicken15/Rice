package rice.modules.combat;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.lwjgl.input.Keyboard;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityGolem;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.play.client.C02PacketUseEntity;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import net.minecraft.util.MovingObjectPosition.MovingObjectType;
import rice.Client;
import rice.events.Event;
import rice.events.listeners.EventAttack;
import rice.events.listeners.EventUpdate;
import rice.modules.Module;
import rice.settings.BooleanSetting;
import rice.settings.Description;
import rice.settings.ModeSetting;
import rice.settings.NewLine;
import rice.settings.NumberSetting;
import rice.utils.MCHook;
import rice.utils.RandomUtil;


public class AimAssist extends Module implements MCHook
{
	public AimAssist() 
	{
		super("AimAssist", Keyboard.KEY_NONE, Category.COMBAT, "Smoothly aims to closet target");
		this.addSettings(rangeValue,FOVValue,jitterValue,forceValue,turnSpeedValue,horizontalTurnSpeedValue,verticalTurnSpeedValue,aimOnClickValue,aimWhenTargetValue,strafeIncreaseValue,checkBlockBreakValue
				,a,aimModeValue,priorityValue,targetDes,playerValue,animalValue,golemValue,mobValue,villagerValue,invisibleValue);
	}
	public static NumberSetting rangeValue = new NumberSetting("Range", 5, 1, 10, 0.1);//
	public static NumberSetting horizontalTurnSpeedValue = new NumberSetting("Horizontal", 8, 0, 180, 1);//
	public static NumberSetting verticalTurnSpeedValue = new NumberSetting("Vertical", 0, 0, 180, 1);//
	public static NumberSetting forceValue = new NumberSetting("Force", 90, 1, 100, 1);//
	public static Description turnSpeedValue = new Description("TurnSpeed");//
	public static NumberSetting FOVValue = new NumberSetting("FOV", 180, 0, 180, 1);//
	public static BooleanSetting aimOnClickValue = new BooleanSetting("OnlyClick",true);//
	public static BooleanSetting aimWhenTargetValue = new BooleanSetting("OnlyOnTarget",false);//
	public static NumberSetting jitterValue = new NumberSetting("Jitter", 0, 1, 3, 1);
	public static BooleanSetting strafeIncreaseValue = new BooleanSetting("StrafeIncrease",false);
	public static BooleanSetting checkBlockBreakValue = new BooleanSetting("NoBlockBreak",true);//
	public static Description targetDes = new Description("Target");
	public static BooleanSetting playerValue = new BooleanSetting("Player", true);
	public static BooleanSetting animalValue = new BooleanSetting("Animal", false);
	public static BooleanSetting golemValue = new BooleanSetting("Golem", false);
	public static BooleanSetting mobValue = new BooleanSetting("Mob", false);
	public static BooleanSetting villagerValue = new BooleanSetting("Villager", false);
	public static BooleanSetting invisibleValue = new BooleanSetting("Invisible", false);
	public static ModeSetting priorityValue = new ModeSetting("Priority","Distance","Distance","Angle","Health","Armor");
	public static ModeSetting aimModeValue = new ModeSetting("AimMode", "Head", "Head", "Body", "Feet");//
	public static NewLine a = new NewLine();
	private EntityLivingBase target;
	public void onEnable() 
    {
    	target = null;
    }
	public void onEvent2(Event e)
	{
		if (e instanceof EventUpdate) 
		{
			this.additionalInfo = String.valueOf(horizontalTurnSpeedValue.get()) + ", " + String.valueOf(verticalTurnSpeedValue.get());
		}
	}
	public void onEvent(Event e) 
	{
		if (e instanceof EventUpdate) 
		{
			if((aimOnClickValue.get() && !_gs.keyBindAttack.getIsKeyPressed()) || 
					(aimWhenTargetValue.get() && mc.objectMouseOver.entityHit == null) ||
					(checkBlockBreakValue.get() && mc.objectMouseOver.typeOfHit == MovingObjectType.BLOCK)) return;
			target = null;
	        target = (EntityLivingBase) getEntity(rangeValue.get());
	        if (target != null) 
	        {
	        	if(target.getDistanceToEntity(mc.thePlayer) <= rangeValue.get())
	        	{
	        		float diff = Math.abs(MathHelper.wrapAngleTo180_float(getAngleBody(target)[0] - mc.thePlayer.rotationYaw));

			        if(diff > FOVValue.get() && target.getDistanceToEntity(mc.thePlayer) > 0.689)
			        {
			        	target = null;
			        	return;
			        }

        			if(aimModeValue.is("Head")) 
        			{
        				if(strafeIncreaseValue.get())
        					mc.thePlayer.rotationYaw += updateRotation(mc.thePlayer.rotationYaw, getAngleHead(target)[0], (float) horizontalTurnSpeedValue.get() * 1.1f) * forceValue.get() / 100 + shake();
        				else
        					mc.thePlayer.rotationYaw += updateRotation(mc.thePlayer.rotationYaw, getAngleHead(target)[0], (float) horizontalTurnSpeedValue.get()) * forceValue.get() / 100 + shake();
        				
        				mc.thePlayer.rotationPitch += updateRotation(mc.thePlayer.rotationPitch, getAngleHead(target)[1], (float) verticalTurnSpeedValue.get()) * forceValue.get() / 100 + shake();
        			}
        			else if(aimModeValue.is("Body"))
        			{
        				if(strafeIncreaseValue.get())
        					mc.thePlayer.rotationYaw += updateRotation(mc.thePlayer.rotationYaw, getAngleBody(target)[0], (float) horizontalTurnSpeedValue.get() * 1.1f) * forceValue.get() / 100 + shake();
        				else
        					mc.thePlayer.rotationYaw += updateRotation(mc.thePlayer.rotationYaw, getAngleBody(target)[0], (float) horizontalTurnSpeedValue.get()) * forceValue.get() / 100 + shake();
        				
        				mc.thePlayer.rotationPitch += updateRotation(mc.thePlayer.rotationPitch, getAngleBody(target)[1], (float) verticalTurnSpeedValue.get()) * forceValue.get() / 100 + shake();
        			}
        			else if(aimModeValue.is("Feet"))
        			{
        				if(strafeIncreaseValue.get())
        					mc.thePlayer.rotationYaw += updateRotation(mc.thePlayer.rotationYaw, getAngleFeet(target)[0], (float) horizontalTurnSpeedValue.get() * 1.1f) * forceValue.get() / 100 + shake();
        				else
        					mc.thePlayer.rotationYaw += updateRotation(mc.thePlayer.rotationYaw, getAngleFeet(target)[0], (float) horizontalTurnSpeedValue.get()) * forceValue.get() / 100 + shake();
        				
        				mc.thePlayer.rotationPitch += updateRotation(mc.thePlayer.rotationPitch, getAngleFeet(target)[1], (float) verticalTurnSpeedValue.get()) * forceValue.get() / 100 + shake();
        			}

	        	}
	        }
		} 
    }
	
	public double shake() 
	{
		return Math.random() * jitterValue.get() * 2 - jitterValue.get();
	}
	public static float updateRotation(float current, float intended, float factor) 
	{
		float var4 = MathHelper.wrapAngleTo180_float(intended - current);

		if (var4 > factor) 
		{
			var4 = factor;
		}

		if (var4 < -factor) 
		{
			var4 = -factor;
		}

		return var4;
	}
	public static float[] getAngleHead(Entity e) 
	{
		float yaw, pitch;
		Vec3 targetPos = new Vec3(e.posX, e.posY + e.getEyeHeight(), e.posZ);
		return getAngle(targetPos);
	}	
	public static float[] getAngleBody(Entity e) 
	{
		float yaw, pitch;
		Vec3 targetPos = new Vec3(e.posX, e.posY + (e.getEntityBoundingBox().maxY - e.getEntityBoundingBox().minY) / 2, e.posZ);
	    return getAngle(targetPos);
	}
	public static float[] getAngleFeet(Entity e) 
	{
		float yaw, pitch;
		Vec3 targetPos = new Vec3(e.posX, e.posY, e.posZ);
		return getAngle(targetPos);
	}
	public static float[] getAngle(Vec3 targetPos) 
	{
		float yaw, pitch;
		Vec3 playerPos = new Vec3(mc.thePlayer.posX, mc.thePlayer.posY + mc.thePlayer.getEyeHeight(), mc.thePlayer.posZ);
	    double d0 = targetPos.xCoord - playerPos.xCoord;
	    double d1 = targetPos.yCoord - playerPos.yCoord;
	    double d2 = targetPos.zCoord - playerPos.zCoord;
	    double d3 = (double)MathHelper.sqrt_double(d0 * d0 + d2 * d2);
	    float f = (float)(Math.atan2(d2, d0) * 180.0D / Math.PI) - 90.0F;
	    float f1 = (float)(-(Math.atan2(d1, d3) * 180.0D / Math.PI));
	    yaw = f;
	    pitch = f1;
	    return new float[] {yaw, pitch};
	}
	public static Entity getEntity(double d) 
	{
    	Minecraft mc = Minecraft.getMinecraft();
		EntityLivingBase target = null;
		List<Entity> targets = (List<Entity>) mc.theWorld.loadedEntityList.stream().filter(EntityLivingBase.class :: isInstance).collect(Collectors.toList());
		targets = targets.stream().filter(entity -> entity.getDistanceToEntity(mc.thePlayer) <= d && entity.getName() != mc.thePlayer.getName() && entity != mc.thePlayer && entity.isEntityAlive() /*&& mc.thePlayer.canEntityBeSeen(entity)*/).collect(Collectors.toList());
		targets = targets.stream().filter(entity -> 
				(entity instanceof EntityPlayer && playerValue.get()) || 
        		(entity instanceof EntityAnimal && animalValue.get()) || 
        		(entity instanceof EntityGolem && golemValue.get()) || 
        		(entity instanceof EntityMob && mobValue.get()) || 
        		(entity instanceof EntityVillager && villagerValue.get())
        		).collect(Collectors.toList());
		targets = targets.stream().filter(e -> 
				((!e.isInvisibleToPlayer(mc.thePlayer) || !e.isInvisible()) ||
				((e.isInvisibleToPlayer(mc.thePlayer) || e.isInvisible()) && invisibleValue.get()))
				).collect(Collectors.toList());
		if(priorityValue.is("Distance"))
			targets.sort(Comparator.comparingDouble(entity -> ((EntityLivingBase)entity).getDistanceToEntity(mc.thePlayer)));
		else if(priorityValue.is("Angle"))
			targets.sort(Comparator.comparingDouble(entity -> Math.abs(MathHelper.wrapAngleTo180_float(getAngleHead(entity)[0] - mc.thePlayer.rotationYaw))));
		else if(priorityValue.is("Health"))
			targets.sort(Comparator.comparingDouble(entity -> ((EntityLivingBase)entity).getHealth()));
		else if(priorityValue.is("Armor"))
			targets.sort(Comparator.comparingDouble(entity -> ((EntityLivingBase)entity).getTotalArmorValue()));
		//targets = targets.stream().filter(EntityPlayer.class::isInstance).collect(Collectors.toList());
		if(!targets.isEmpty()) 
		{
	        target = (EntityLivingBase) targets.get(0);
		}
		return target;
    }
}