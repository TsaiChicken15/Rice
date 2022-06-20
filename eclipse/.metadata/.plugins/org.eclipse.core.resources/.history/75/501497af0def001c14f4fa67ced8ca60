package rice.ui.clickgui.simple.button;

import net.minecraft.client.gui.Gui;
import rice.settings.BooleanSetting;
import rice.utils.MCHook;

public class BooleanButton extends Button implements MCHook
{
    protected float w;
    protected float h;
    public float x;
    public float y;
    public BooleanSetting s;

    public boolean hovered;

    public BooleanButton(BooleanSetting s, float x, float y, float widthIn, float heightIn)
    {
    	this.s= s;
        this.x = x;
        this.y = y;
        this.w = widthIn;
        this.h = heightIn;
    }
    public void draw(int mouseX, int mouseY)
    {
    	//this.hovered = mouseX >= this.x && mouseY >= this.y && mouseX < this.x + this.w + 4 && mouseY < this.y + this.h + 4;
    	this.hovered = mouseX >= this.x + 4 && mouseY >= this.y - 2 && mouseX < this.x + this.w - 4 && mouseY < this.y + this.h + 2;
    	//Gui.drawRect(x, y, x + w + 4, y + h + 4, hovered ? 0x50000000 : 0x00000000);
    	Gui.drawRect(x + 4, y - 2, x + w - 4, y + h + 2, hovered ? 0x64000000 : 0x00000000);
    	/*Gui.drawRect(x - 0.1f, y - 1.1f, x + w + 4.1f, y - 0.1f, color);
    	Gui.drawRect(x - 0.1f, y + h + 4.1f, x + w + 4.1f, y + h + 5.1f, color);
    	Gui.drawRect(x - 1.1f, y - 1.1f, x - 0.1f, y + h + 5.1f, color);
    	Gui.drawRect(x + w + 4.1f, y - 1.1f, x + w + 5.1f, y + h + 5.1f, color);*/
    	
    	/*Gui.drawRect(x + 4.3f, y + 6.3f, x + 8.3f, y + 10.3f , 0xff000000);
    	Gui.drawRect(x + 4, y + 6, x + 8, y + 10, ((BooleanSetting)s).enabled ? 0xff00ff00 : 0xffff0000);*/
    	Gui.drawRect(x + 7.3f, (y + h / 2) - 0.7f, x + 10.3f, (y + h / 2) + 2.3f, 0xff000000);
    	Gui.drawRect(x + 7, (y + h / 2) - 1, x + 10, (y + h / 2) + 2, ((BooleanSetting)s).enabled ? 0xff00ff00 : 0xffff0000);
    	mc.fontRendererObj.drawStringWithShadow(s.name, (x + x + w + 4) / 2 - mc.fontRendererObj.getStringWidth(s.name) / 2, (y + h / 2) - _fr.FONT_HEIGHT / 2, color);
    }
}