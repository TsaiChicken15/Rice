package rice.ui.clickgui.simple.button;

import rice.utils.MCHook;

public class Description extends Button implements MCHook
{
    protected float w;
    protected float h;
    public float x;
    public float y;
    public rice.settings.Description s;

    public boolean hovered;

    public Description(rice.settings.Description s, float x, float y, float widthIn, float heightIn)
    {
    	this.s= s;
        this.x = x;
        this.y = y;
        this.w = widthIn;
        this.h = heightIn;
    }
    public void draw(int mouseX, int mouseY)
    {
    	this.hovered = mouseX >= this.x && mouseY >= this.y && mouseX < this.x + this.w + 4 && mouseY < this.y + this.h + 4;
    	//Gui.drawRect(x, y, x + w + 4, y + h + 4, hovered ? 0x50000000 : 0x00000000);
    	
    	/*Gui.drawRect(x - 0.1f, y - 1.1f, x + w + 4.1f, y - 0.1f, color);
    	Gui.drawRect(x - 0.1f, y + h + 4.1f, x + w + 4.1f, y + h + 5.1f, color);
    	Gui.drawRect(x - 1.1f, y - 1.1f, x - 0.1f, y + h + 5.1f, color);
    	Gui.drawRect(x + w + 4.1f, y - 1.1f, x + w + 5.1f, y + h + 5.1f, color);*/
    	
    	mc.fontRendererObj.drawStringWithShadow("---", x + 4, (y + h / 2) - _fr.FONT_HEIGHT / 2, color);
    	mc.fontRendererObj.drawStringWithShadow("---", x + w - mc.fontRendererObj.getStringWidth("---") - 4, (y + h / 2) - _fr.FONT_HEIGHT / 2, color);
    	mc.fontRendererObj.drawStringWithShadow(s.name, (x + x + w) / 2 - mc.fontRendererObj.getStringWidth(s.name) / 2, (y + h / 2) - _fr.FONT_HEIGHT / 2, color);
    }
}