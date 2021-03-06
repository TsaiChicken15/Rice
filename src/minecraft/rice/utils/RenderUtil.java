package rice.utils;

import java.awt.Color;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderGlobal;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.util.AxisAlignedBB;

public class RenderUtil implements MCHook 
{
	public static void drawPlayerBox(double posX, double posY, double posZ, int color) 
	{
		double x =
			posX - 0.5
				- mc.getRenderManager().renderPosX;
		double y =
			posY
				- mc.getRenderManager().renderPosY;
		double z =
			posZ - 0.5
				- mc.getRenderManager().renderPosZ;
		GL11.glBlendFunc(770, 771);
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glLineWidth(2.0F);
		GL11.glColor4d(0, 1, 0, 0.15F);
		GL11.glDisable(GL11.GL_TEXTURE_2D);
		GL11.glDisable(GL11.GL_DEPTH_TEST);
		GL11.glDepthMask(false);
		//drawColorBox(new AxisAlignedBB(x, y, z, x + 1.0, y + 1.0, z + 1.0));
		GL11.glColor4d(1, 1, 1, 0.5F);
		RenderGlobal.drawOutlinedBoundingBox(new AxisAlignedBB(x + 0.2, y, z + 0.2,
			x + 0.8, y + 1.8, z + 0.8), color);
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		GL11.glEnable(GL11.GL_DEPTH_TEST);
		GL11.glDepthMask(true);	
		GL11.glDisable(GL11.GL_BLEND);
		GL11.glColor4f(1, 1, 1, 1);
    }
	private static void quickPolygonCircle(float x, float y, float xRadius, float yRadius, int start, int end, int split) {
		Tessellator var9 = Tessellator.getInstance();
        WorldRenderer var10 = var9.getWorldRenderer();
        for(int i = end; i >= start; i -= split) {
        	var10.addVertex(x + Math.sin(i * Math.PI / 180.0D) * xRadius, y + Math.cos(i * Math.PI / 180.0D) * yRadius, 0.0D);
        }
    }	
	public static void circle(final float x, final float y, final float radius, final int fill) {
        arc(x, y, 0.0f, 360.0f, radius, fill);
    }
	public static void arc(final float x, final float y, final float start, final float end, final float radius,
            final int color) {
		arcEllipse(x, y, start, end, radius, radius, color);
	}
	public static void arcEllipse(final float x, final float y, float start, float end, final float w, final float h,
            final int color) {
		GlStateManager.color(0.0f, 0.0f, 0.0f);
		GL11.glColor4f(0.0f, 0.0f, 0.0f, 0.0f);
		float temp = 0.0f;
		if (start > end) {
			temp = end;
			end = start;
			start = temp;
		}
		final float var11 = (color >> 24 & 0xFF) / 255.0f;
		final float var12 = (color >> 16 & 0xFF) / 255.0f;
		final float var13 = (color >> 8 & 0xFF) / 255.0f;
		final float var14 = (color & 0xFF) / 255.0f;
		final Tessellator var15 = Tessellator.getInstance();
		var15.getWorldRenderer();
		GlStateManager.enableBlend();
		GlStateManager.func_179090_x();
		GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
		GlStateManager.color(var12, var13, var14, var11);
		if (var11 > 0.5f) {
			GL11.glEnable(2848);
			GL11.glLineWidth(2.0f);
			GL11.glBegin(3);
				for (float i = end; i >= start; i -= 4.0f) {
					final float ldx = (float) Math.cos(i * Math.PI / 180.0) * w * 1.001f;
					final float ldy = (float) Math.sin(i * Math.PI / 180.0) * h * 1.001f;
					GL11.glVertex2f(x + ldx, y + ldy);
				}
			GL11.glEnd();
			GL11.glDisable(2848);
		}
		GL11.glBegin(6);
		for (float i = end; i >= start; i -= 4.0f) {
			final float ldx = (float) Math.cos(i * Math.PI / 180.0) * w;
			final float ldy = (float) Math.sin(i * Math.PI / 180.0) * h;
			GL11.glVertex2f(x + ldx, y + ldy);
		}
		GL11.glEnd();
		GlStateManager.func_179098_w();
		GlStateManager.disableBlend();
	}
	public static void drawRoundedRect(float x, float y, float x2, float y2, final float round, final int color) {
        x += (float) (round / 2.0f + 0.5);
        y += (float) (round / 2.0f + 0.5);
        x2 -= (float) (round / 2.0f + 0.5);
        y2 -= (float) (round / 2.0f + 0.5);
        Gui.drawRect(x, y, x2, y2, color);
        circle(x2 - round / 2.0f, y + round / 2.0f, round, color);
        circle(x + round / 2.0f, y2 - round / 2.0f, round, color);
        circle(x + round / 2.0f, y + round / 2.0f, round, color);
        circle(x2 - round / 2.0f, y2 - round / 2.0f, round, color);
        Gui.drawRect((x - round / 2.0f - 0.5f), (y + round / 2.0f), x2, (y2 - round / 2.0f),
                color);
        Gui.drawRect(x, (y + round / 2.0f), (x2 + round / 2.0f + 0.5f), (y2 - round / 2.0f),
                color);
        Gui.drawRect((x + round / 2.0f), (y - round / 2.0f - 0.5f), (x2 - round / 2.0f),
                (y2 - round / 2.0f), color);
        Gui.drawRect((x + round / 2.0f), y, (x2 - round / 2.0f), (y2 + round / 2.0f + 0.5f),
                color);
    }
	public static void smoothCircle(final float x, final float y, final float radius, final int c) {
//      GL11.glEnable(GL_MULTISAMPLE);
		GL11.glEnable(GL11.GL_POLYGON_SMOOTH);
		for (int i2 = 0; i2 < 3; i2++) {// TODO: 2021/7/19  ??????????????????????????????????????????????????????????????? GL_POLYGON_SMOOTH????????? ??????????????????(???????????????disable glAlpha????????????????)
		float alpha = (float) (c >> 24 & 255) / 255.0f;
		float red = (float) (c >> 16 & 255) / 255.0f;
		float green = (float) (c >> 8 & 255) / 255.0f;
		float blue = (float) (c & 255) / 255.0f;
		boolean blend = GL11.glIsEnabled((int) 3042);
		boolean line = GL11.glIsEnabled((int) 2848);
		boolean texture = GL11.glIsEnabled((int) 3553);
		if (!blend) {
		    GL11.glEnable((int) 3042);
		}
		if (!line) {
		    GL11.glEnable((int) 2848);
		}
		if (texture) {
		    GL11.glDisable((int) 3553);
		}
		GL11.glBlendFunc((int) 770, (int) 771);
		GL11.glColor4f((float) red, (float) green, (float) blue, (float) alpha);
		GL11.glBegin((int) 9);
		int i = 0;
		while (i <= 360) {
		    GL11.glVertex2d(
		            (double) ((double) x + Math.sin((double) ((double) i * 3.141526 / 180.0)) * (double) radius),
		            (double) ((double) y + Math.cos((double) ((double) i * 3.141526 / 180.0)) * (double) radius));
		    ++i;
			}
			GL11.glEnd();
	      	if (texture) {
	      		GL11.glEnable((int) 3553);
	      	}
	      	if (!line) {
	          	GL11.glDisable((int) 2848);
	      	}
	      	if (!blend) {
	      		GL11.glDisable((int) 3042);
	      	}
      	}
      	GL11.glDisable(GL11.GL_POLYGON_SMOOTH);
      	GL11.glClear(0);
	}
}