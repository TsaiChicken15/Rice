package rice.utils;

import java.awt.Color;

import net.minecraft.client.renderer.GlStateManager;

public class ColorUtil implements MCHook
{
	public static int getRainbow(float second, float saturation, float brightness) 
	{
		float hue = (System.currentTimeMillis() % (int)(second * 1000)) / (float)(second * 1000);
		int color = Color.HSBtoRGB(hue, saturation, brightness);
		return color;
	}
	
	public static void glColor(final int hex) {
        final float alpha = (hex >> 24 & 0xFF) / 255F;
        final float red = (hex >> 16 & 0xFF) / 255F;
        final float green = (hex >> 8 & 0xFF) / 255F;
        final float blue = (hex & 0xFF) / 255F;

        GlStateManager.color(red, green, blue, alpha);
    }
}