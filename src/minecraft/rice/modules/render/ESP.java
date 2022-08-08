package rice.modules.render;

import java.awt.Color;
import java.util.Map;
import java.util.WeakHashMap;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import optifine.Config;
import rice.events.Event;
import rice.events.listeners.EventRenderPlayer;
import rice.modules.Module;
import rice.settings.BooleanSetting;
import rice.settings.NumberSetting;
import rice.utils.RenderUtil;

public class ESP extends Module
{
	public ESP() {
		super("ESP", Keyboard.KEY_NONE, Category.RENDER, "Renders a box around players");
		this.addSettings(invisibleValue);
	}
	private final Map playerRotationMap = new WeakHashMap();
	public static BooleanSetting invisibleValue  = new BooleanSetting("Invisible",true);
	public void onEvent(Event e) {	
		if (e instanceof EventRenderPlayer) {
			this.drawPlayerFrame();
		}
	}
	
	public static void drawPlayerFrame() 
	{
		for (Object en : mc.theWorld.playerEntities) {
			EntityLivingBase entity = ((EntityLivingBase)en);
			if (!ESP.isValid(entity)) continue;
			if(!invisibleValue.get() && (entity.isInvisible() || entity.isInvisibleToPlayer(mc.thePlayer))) continue;
			if(mc.currentScreen instanceof GuiContainer) continue;
	        GL11.glPushMatrix();
	        GL11.glEnable(3042);
	        GL11.glDisable(2929);
	        GL11.glNormal3f(0.0f, 1.0f, 0.0f);
	        GlStateManager.enableBlend();
	        GL11.glBlendFunc(770, 771);
	        GL11.glDisable(3553);
	        float partialTicks = mc.timer.renderPartialTicks;
	        double x2 = entity.lastTickPosX + (entity.posX - entity.lastTickPosX) * (double) partialTicks - mc.getRenderManager().renderPosX;
	        double y2 = entity.lastTickPosY + (entity.posY - entity.lastTickPosY) * (double) partialTicks - mc.getRenderManager().renderPosY;
	        double z2 = entity.lastTickPosZ + (entity.posZ - entity.lastTickPosZ) * (double) partialTicks - mc.getRenderManager().renderPosZ;
	        float DISTANCE = mc.thePlayer.getDistanceToEntity(entity);
	        float DISTANCE_SCALE = Math.min(DISTANCE * 0.15f, 0.15f);
	        float SCALE = 0.035f;
	        float xMid = (float) x2;
	        float yMid = (float) y2 + entity.height + 0.5f - (entity.isChild() ? entity.height / 2.0f : 0.0f);
	        float zMid = (float) z2;
	        GlStateManager.translate((float) x2, (float) y2 + entity.height + 0.5f - (entity.isChild() ? entity.height / 2.0f : 0.0f), (float) z2);
	        GL11.glNormal3f(0.0f, 1.0f, 0.0f);
	        mc.getRenderManager();
	        GlStateManager.rotate(-mc.getRenderManager().playerViewY, 0.0f, 1.0f, 0.0f);
	        GL11.glScalef(-SCALE, -SCALE, -(SCALE /= 2.0f));
	        Tessellator tesselator = Tessellator.getInstance();
	        WorldRenderer worldRenderer = tesselator.getWorldRenderer();
	        float HEALTH = entity.getHealth();
	        int COLOR = -1;
	        COLOR = (double) HEALTH > 20.0 ? -65292 : ((double) HEALTH >= 10.0 ? -16711936 : ((double) HEALTH >= 5.0 ? -23296 : -65536));
	        Color gray = new Color(0, 0, 0);
	        double thickness = 1 + DISTANCE * 0.01f;
	        double xLeft = -15.0;
	        double xRight = 15.0;
	        double yUp = 10.0;
	        double yDown = 70.0;
	        double size = 10.0;
	        Color color = new Color(255, 255, 255);
	        if (entity.hurtTime > 0) {
	            color = new Color(255, 0, 0);
	        } else if (entity.isInvisible()) {
	            color = new Color(255, 255, 0);
	        }
	        RenderUtil.drawBorderedRect((float) xLeft, (float) yUp, (float) xRight, (float) yDown, (float) thickness + 0.2f, Color.BLACK.getRGB(), 0);
	        RenderUtil.drawBorderedRect((float) xLeft, (float) yUp, (float) xRight, (float) yDown, (float) thickness, color.getRGB(), 0);
	        RenderUtil.drawBorderedRect((float) xLeft - 2.0f - DISTANCE * 0.1f, (float) yDown - (float) (yDown - yUp), (float) xLeft - 2.0f, (float) yDown, 0.15f, new Color(100, 100, 100).getRGB(), new Color(100, 100, 100).getRGB());
	        /*Color c1 = new Color(Color.HSBtoRGB(mc.thePlayer.ticksExisted / 25f, 0.7f, 1));
	        Color c2 = new Color(Color.HSBtoRGB(mc.thePlayer.ticksExisted / 50f, 0.7f, 1));*/
	        RenderUtil.drawRect((float) xLeft - 2.0f - DISTANCE * (Config.zoomMode ? 0.06f : 0.18f), (float) yDown - (float) (yDown - yUp) * Math.min(1.0f, entity.getHealth() / 20.0f), (float) xLeft - 2.0f, (float) yDown, COLOR);
	        GL11.glEnable(3553);
	        GL11.glEnable(2929);
	        GlStateManager.disableBlend();
	        GL11.glDisable(3042);
	        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
	        GL11.glNormal3f(1.0f, 1.0f, 1.0f);
	        GL11.glPopMatrix();
		}
    }
	
	private static boolean isValid(EntityLivingBase entity) {
        if (entity instanceof EntityPlayer && entity.getHealth() >= 0.0f && ((entity == mc.thePlayer && mc.gameSettings.thirdPersonView != 0) || entity != mc.thePlayer)) {
            return true;
        }
        return false;
    }
	
	private void drawLine(Entity entity, double[] color, double x, double y, double z) {
        float distance = mc.thePlayer.getDistanceToEntity(entity);
        float xD = distance / 48.0f;
        if (xD >= 1.0f) {
            xD = 1.0f;
        }
        boolean entityesp = false;
        GL11.glEnable((int)2848);
        if (color.length >= 4) {
            if (color[3] <= 0.1) {
                return;
            }
            GL11.glColor4d((double)color[0], (double)color[1], (double)color[2], (double)color[3]);
        } else {
            GL11.glColor3d((double)color[0], (double)color[1], (double)color[2]);
        }
        GL11.glLineWidth((float)2.0f);
        GL11.glBegin((int)1);
        GL11.glVertex3d((double)0.0, (double)mc.thePlayer.getEyeHeight(), (double)0.0);
        GL11.glVertex3d((double)x, (double)y, (double)z);
        GL11.glEnd();
        GL11.glDisable((int)2848);
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
    }
	
	private void setupRender(boolean start) {

        if (start) {
            RenderUtil.startSmooth();
            GL11.glDisable(2929);
            GL11.glDisable(3553);
        } else {
            GL11.glEnable(3553);
            GL11.glEnable(2929);
            RenderUtil.endSmooth();
        }
        GL11.glDepthMask((!start ? 1 : 0) != 0);
    }

    private boolean contain(EntityPlayer var0) {
        return !mc.theWorld.playerEntities.contains(var0);
    }

    public static double interpolate(double current, double old, double scale) {
        return old + (current - old) * scale;
    }
}