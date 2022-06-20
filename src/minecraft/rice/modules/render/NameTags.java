package rice.modules.render;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

import net.minecraft.entity.monster.EntityGolem;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import rice.events.Event;
import rice.events.listeners.EventRenderLivingLabel;
import rice.events.listeners.EventUpdate;
import rice.modules.Module;
import rice.settings.BooleanSetting;
import rice.utils.MCHook;

public class NameTags extends Module implements MCHook
{
	public NameTags() 
	{
		super("NameTags", Keyboard.KEY_NONE, Category.RENDER, "Renders name plates of entities through walls");
		this.addSettings(invisibleValue);
	}
	public static BooleanSetting invisibleValue = new BooleanSetting("Invisible",true);
	public void onEvent(Event e) {
		if (e instanceof EventRenderLivingLabel) 
		{
			EventRenderLivingLabel nametag = (EventRenderLivingLabel)e;
			if(nametag.e instanceof EntityPlayer || nametag.e instanceof EntityMob || nametag.e instanceof EntityAnimal || nametag.e instanceof EntityVillager || nametag.e instanceof EntityGolem) 
			{
				if (e.isPRE()) 
				{
	                GL11.glEnable(32823);
	                GL11.glPolygonOffset(1.0f, -1100000.0f);
	                nametag.maxDistance = 1000;
				}
				else if (e.isPOST()) 
				{
	                GL11.glDisable(32823);
	                GL11.glPolygonOffset(1.0f, 1100000.0f);
				}
			}
		}
		if (e instanceof EventUpdate && e.isPRE()) 
		{
			try {
				for (Object maybeEntity : mc.theWorld.loadedEntityList) 
				{
					if (maybeEntity instanceof EntityPlayer) 
					{
						EntityPlayer entity = ((EntityPlayer)maybeEntity);
						entity.setAlwaysRenderNameTag(true);
						entity.setCustomNameTag(entity.getName());
					}
					if (maybeEntity instanceof EntityMob) 
					{
						EntityMob entity = ((EntityMob)maybeEntity);
						entity.setAlwaysRenderNameTag(true);
						entity.setCustomNameTag(entity.getName());
					}
					if (maybeEntity instanceof EntityAnimal) 
					{
						EntityAnimal entity = ((EntityAnimal)maybeEntity);
						entity.setAlwaysRenderNameTag(true);
						entity.setCustomNameTag(entity.getName());
					}
					if (maybeEntity instanceof EntityVillager) 
					{
						EntityVillager entity = ((EntityVillager)maybeEntity);
						entity.setAlwaysRenderNameTag(true);
						entity.setCustomNameTag(entity.getName());
					}
					if (maybeEntity instanceof EntityGolem) 
					{
						EntityGolem entity = ((EntityGolem)maybeEntity);
						entity.setAlwaysRenderNameTag(true);
						entity.setCustomNameTag(entity.getName());
					}
				}
			} catch (Exception e2) {}
		}
	}
}