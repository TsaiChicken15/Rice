package rice.notification;

import java.awt.Color;
import java.text.DecimalFormat;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;

public class Notification {
    private NotificationType type;
    private String messsage;
    private long start;

    private long fadedIn;
    private long fadeOut;
    private long end;

    private int length;

    public Notification(NotificationType type, String messsage, int length) {
        this.type = type;
        this.messsage = messsage;
        this.length = length;
        fadedIn = 200 * length;
        fadeOut = fadedIn + 500 * length;
        end = fadeOut + fadedIn;
    }

    public void show() {
        start = System.currentTimeMillis();
    }

    public boolean isShown() {
        return getTime() <= end;
    }

    private long getTime() {
        return System.currentTimeMillis() - start;
    }

    public void render() 
    {
    	FontRenderer fontRenderer = Minecraft.getMinecraft().fontRendererObj;
    	DecimalFormat frmt = new DecimalFormat("#.#");
        double offset = 0;
        int width = 0;
        if(width <= fontRenderer.getStringWidth(messsage + " ()" + frmt.format(length - getTime() / 1000f)))
        {
        	width = fontRenderer.getStringWidth(messsage + " ()" + frmt.format(length - getTime() / 1000f)) + 16;
        }
        int height = 20;
        long time = getTime();

        if (time < fadedIn) {
            offset = Math.tanh(time / (double) (fadedIn) * 3.0) * width;
        } else if (time > fadeOut) {
            offset = (Math.tanh(3.0 - (time - fadeOut) / (double) (end - fadeOut) * 3.0) * width);
        } else {
            offset = width;
        }

        

        if (type == NotificationType.INFO)
        {
	        drawRect(GuiScreen.width - offset, GuiScreen.height - 5 - height, GuiScreen.width - offset + offset * time / end, GuiScreen.height - 5, 0xa000ff00);
	        drawRect(GuiScreen.width - offset + offset * time / end, GuiScreen.height - 5 - height, GuiScreen.width, GuiScreen.height - 5, 0x4000ff00);
        }
        else if (type == NotificationType.WARNING)
        {
        	drawRect(GuiScreen.width - offset, GuiScreen.height - 5 - height, GuiScreen.width - offset + offset * time / end, GuiScreen.height - 5, 0xa0ff9600);
	        drawRect(GuiScreen.width - offset + offset * time / end, GuiScreen.height - 5 - height, GuiScreen.width, GuiScreen.height - 5, 0x40ff9600);
        }
        else 
        {
        	drawRect(GuiScreen.width - offset, GuiScreen.height - 5 - height, GuiScreen.width - offset + offset * time / end, GuiScreen.height - 5, 0xa0ff0000);
	        drawRect(GuiScreen.width - offset + offset * time / end, GuiScreen.height - 5 - height, GuiScreen.width, GuiScreen.height - 5, 0x40ff0000);
        }
        drawRect(GuiScreen.width - offset, GuiScreen.height - 5 - height, GuiScreen.width - offset + 1, GuiScreen.height - 5, -1);
        drawRect(GuiScreen.width - offset, GuiScreen.height - 5 - height, GuiScreen.width, GuiScreen.height - 6 - height, -1);
        drawRect(GuiScreen.width - offset, GuiScreen.height - 4, GuiScreen.width, GuiScreen.height - 5, -1);

        fontRenderer.drawStringWithShadow(messsage + " (" + frmt.format(length - getTime() / 1000f) + ")", (int) (GuiScreen.width - offset + 8), GuiScreen.height - height / 2 - fontRenderer.FONT_HEIGHT, -1);
    }

    public static void drawRect(double left, double top, double right, double bottom, int color) {
        if (left < right) {
            double i = left;
            left = right;
            right = i;
        }

        if (top < bottom) {
            double j = top;
            top = bottom;
            bottom = j;
        }

        float f3 = (float) (color >> 24 & 255) / 255.0F;
        float f = (float) (color >> 16 & 255) / 255.0F;
        float f1 = (float) (color >> 8 & 255) / 255.0F;
        float f2 = (float) (color & 255) / 255.0F;
        Tessellator tessellator = Tessellator.getInstance();
        WorldRenderer worldrenderer = tessellator.getWorldRenderer();
        GlStateManager.enableBlend();
        GlStateManager.func_179090_x();
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
        GlStateManager.color(f, f1, f2, f3);
        worldrenderer.startDrawingQuads();
        worldrenderer.addVertex((double)left, (double)bottom, 0.0D);
        worldrenderer.addVertex((double)right, (double)bottom, 0.0D);
        worldrenderer.addVertex((double)right, (double)top, 0.0D);
        worldrenderer.addVertex((double)left, (double)top, 0.0D);
        tessellator.draw();
        GlStateManager.func_179098_w();
        GlStateManager.disableBlend();
    }
}