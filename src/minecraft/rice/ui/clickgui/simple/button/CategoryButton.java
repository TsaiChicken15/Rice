package rice.ui.clickgui.simple.button;

import net.minecraft.client.gui.Gui;
import rice.modules.Module.Category;
import rice.utils.MCHook;

public class CategoryButton extends Button implements MCHook
{
    protected int w;
    protected int h;
    public int x;
    public int y;
    public Category c;

    public boolean hovered;
    public boolean focused;

    public CategoryButton(Category c, int x, int y, int widthIn, int heightIn)
    {
    	this.c= c;
        this.x = x;
        this.y = y;
        this.w = widthIn;
        this.h = heightIn;
        this.focused = false;
    }
    public void draw(int mouseX, int mouseY) {
    	//this.hovered = mouseX >= this.x + 4 && mouseY >= this.y - 4 && mouseX < this.x + this.w - 4 && mouseY < this.y + this.h + 4;
    	this.hovered = mouseX >= this.x + 4 && mouseY >= this.y - 2 && mouseX < this.x + this.w - 4 && mouseY < this.y + this.h + 2;
    	//Gui.drawRect(x + 4, y - 4, x + w - 4, y + h, hovered ? focused ? 0xc055FF55 : 0xc05555FF : focused ? 0x9055FF55 : 0x905555FF);
    	Gui.drawRect(x + 4, y - 2, x + w - 4, y + h + 2, hovered ? focused ? 0xc055FF55 : 0xc05555FF : focused ? 0x9055FF55 : 0x905555FF);
    	Gui.drawRect(x + 4, y - 3, x + w - 4, y - 2, color);
    	Gui.drawRect(x + 4, y + h + 2, x + w - 4, y + h + 3, color);
    	Gui.drawRect(x + 3, y - 3, x + 4, y + h + 3, color);
    	Gui.drawRect(x + w - 5, y - 2, x + w - 4, y + h + 3, color);
    	mc.fontRendererObj.drawStringWithShadow(c.name(), x + w / 2 - mc.fontRendererObj.getStringWidth(c.name()) / 2, (y + h / 2) - _fr.FONT_HEIGHT / 2, color);
    }
}