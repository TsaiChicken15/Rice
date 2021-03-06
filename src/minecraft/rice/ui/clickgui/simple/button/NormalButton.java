package rice.ui.clickgui.simple.button;

import net.minecraft.client.gui.Gui;
import rice.utils.MCHook;

public class NormalButton extends Button implements MCHook
{
    protected int w;
    protected int h;
    public int x;
    public int y;
    public String s;

    public boolean hovered;

    public NormalButton(String s, int x, int y, int widthIn, int heightIn)
    {
    	this.s = s;
        this.x = x;
        this.y = y;
        this.w = widthIn;
        this.h = heightIn;
    }
    public void draw(int mouseX, int mouseY) {
    	this.hovered = mouseX >= this.x && mouseY >= this.y && mouseX < this.x + this.w + 4 && mouseY < this.y + this.h + 4;
    	Gui.drawRect(x, y, x + w + 4, y + h + 4, 0x00000000);
    	/*Gui.drawRect(x - 0.1f, y - 1.1f, x + w + 4.1f, y - 0.1f, color);
    	Gui.drawRect(x - 0.1f, y + h + 4.1f, x + w + 4.1f, y + h + 5.1f, color);
    	Gui.drawRect(x - 1.1f, y - 1.1f, x - 0.1f, y + h + 5.1f, color);
    	Gui.drawRect(x + w + 4.1f, y - 1.1f, x + w + 5.1f, y + h + 5.1f, color);*/
    	mc.fontRendererObj.drawStringWithShadow(s, x + w / 2 - mc.fontRendererObj.getStringWidth(s) / 2 + 2, y + h / 2 - 2, color);
    }
}