package rice.utils;

import java.awt.Color;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.util.MovingObjectPosition;

public class AnimationUtil implements MCHook
{
	public static void anim17(float partialTicks) 
	{
		ItemRenderer ir = mc.getItemRenderer();
		float f = 1.0F - (ir.prevEquippedProgress + (ir.equippedProgress - ir.prevEquippedProgress) * partialTicks);
		float swingProgress = mc.thePlayer.getSwingProgress(partialTicks);
		float swingProgressReversed = 1.0f - swingProgress;
		ir.func_178096_b(f, swingProgress);
		ir.func_178103_d();
	}
	public static void animTap(float partialTicks) 
	{
	    ItemRenderer ir = mc.getItemRenderer();
	    float swingProgress = mc.thePlayer.getSwingProgress(partialTicks);
	    ir.func_178096_b(0.0F, swingProgress - 1.0F);
	    ir.func_178103_d();
	}
}	