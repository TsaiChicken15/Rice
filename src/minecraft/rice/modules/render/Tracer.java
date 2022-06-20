package rice.modules.render;

import java.awt.Color;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

import net.minecraft.entity.player.EntityPlayer;
import rice.events.Event;
import rice.events.listeners.EventRenderPlayer;
import rice.events.listeners.EventUpdate;
import rice.modules.Module;
import rice.settings.BooleanSetting;
import rice.settings.ModeSetting;
import rice.settings.NumberSetting;
import rice.utils.ColorUtil;
import rice.utils.RenderUtil;

public class Tracer extends Module
{
	public Tracer() 
	{
		super("Tracer", Keyboard.KEY_NONE, Category.RENDER,"");
		this.addSettings(invisibleValue);
	}
	public static BooleanSetting invisibleValue = new BooleanSetting("Invisible", true);
	public void onEvent(Event e) 
	{	
		if (e instanceof EventRenderPlayer) 
		{
			if(((EventRenderPlayer)e).entity instanceof EntityPlayer)
			{
				EntityPlayer p = (EntityPlayer) ((EventRenderPlayer)e).entity;
				if(invisibleValue.get() && (p.isInvisible() || p.isInvisibleToPlayer(mc.thePlayer)))
				{
					return;
				}
				double xPos = (p.lastTickPosX + (p.posX - p.lastTickPosX) * mc.timer.renderPartialTicks) - mc.getRenderManager().renderPosX;
				double yPos = (p.lastTickPosY + (p.posY - p.lastTickPosY) * mc.timer.renderPartialTicks) - mc.getRenderManager().renderPosY + p.getEyeHeight();
				double zPos = (p.lastTickPosZ + (p.posZ - p.lastTickPosZ) * mc.timer.renderPartialTicks) - mc.getRenderManager().renderPosZ;
				GL11.glPushMatrix();
				GL11.glLoadIdentity();
				mc.entityRenderer.orientCamera(mc.timer.renderPartialTicks);
				GL11.glEnable(GL11.GL_BLEND);
				GL11.glEnable(GL11.GL_LINE_SMOOTH);
				GL11.glDisable(GL11.GL_DEPTH_TEST);
				GL11.glDisable(GL11.GL_TEXTURE_2D);
				GL11.glBlendFunc(770, 771);
				GL11.glEnable(GL11.GL_BLEND);
				GL11.glLineWidth(2.0f);
				GL11.glBegin(2);
				GL11.glVertex3d(0.0D, 0.0D + mc.thePlayer.getEyeHeight(), 0.0D);
				GL11.glVertex3d(xPos, yPos, zPos);
				GL11.glEnd();
				GL11.glDisable(GL11.GL_BLEND);
				GL11.glEnable(GL11.GL_TEXTURE_2D);
				GL11.glEnable(GL11.GL_DEPTH_TEST);
				GL11.glDisable(GL11.GL_LINE_SMOOTH);
				GL11.glDisable(GL11.GL_BLEND);
				GL11.glPopMatrix();
			}
		}
	}
}