package rice.ui.clickgui.simple.button;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

import net.minecraft.client.gui.Gui;
import rice.modules.Module;
import rice.utils.MCHook;

public class ModuleButton extends Button implements MCHook
{
    protected int w;
    protected int h;
    public int x;
    public int y;
    public Module m;

    public boolean hovered;
    public boolean focused;
    public boolean listening;

    public ModuleButton(Module m, int x, int y, int widthIn, int heightIn)
    {
    	this.m = m;
        this.x = x;
        this.y = y;
        this.w = widthIn;
        this.h = heightIn;
        this.focused = false;
        this.listening = false;
    }
    public void draw(int mouseX, int mouseY) 
    {
    	this.hovered = mouseX >= this.x + 4 && mouseY >= this.y - 2 && mouseX < this.x + this.w - 4 && mouseY < this.y + this.h + 2;
    	Gui.drawRect(x + 4, y - 2, x + w - 4, y + h + 2, hovered ? 0x64000000 : 0x00000000);
    	/*Gui.drawRect(x + 3.9f, y - 4.5f, x + w - 3.9f, y - 3.5f, color);
    	Gui.drawRect(x + 3.9f, y + h + 0.5f, x + w - 3.9f, y + h + 1.5f, color);
    	Gui.drawRect(x + 4.9f, y - 4.5f, x + 3.9f, y + h + 1.5f, color);
    	Gui.drawRect(x + w - 3.9f, y - 4.5f, x + w - 2.9f, y + h + 1.5f, color);*/
    	Gui.drawRect(x + 7.3f, (y + h / 2) - 0.7f, x + 10.3f, (y + h / 2) + 2.3f, 0xff000000);
    	Gui.drawRect(x + 7, (y + h / 2) - 1, x + 10, (y + h / 2) + 2, m.toggled ? 0xff00ff00 : 0xffff0000);
    	mc.fontRendererObj.drawStringWithShadow(m.getName() , x + 14, y, color);
    	mc.fontRendererObj.drawStringWithShadow(listening ? "..." : Keyboard.getKeyName(m.getKey()), ((x + w - 24) - mc.fontRendererObj.getStringWidth(listening ? "..." : Keyboard.getKeyName(m.getKey())) / 2), y, color);
		if(this.hovered && this.m.descript != "")
		{
			GL11.glEnable(32823);
            GL11.glPolygonOffset(1.0f, -1100000.0f);
			Gui.drawRect(mouseX + 10, mouseY - 1, mouseX + mc.fontRendererObj.getStringWidth(this.m.descript) + 12, mouseY + mc.fontRendererObj.FONT_HEIGHT + 1, 0xd0000000);
			Gui.drawRect(mouseX + 9, mouseY - 2, mouseX + mc.fontRendererObj.getStringWidth(this.m.descript) + 13, mouseY - 1, Button.color);
			Gui.drawRect(mouseX + 9, mouseY + mc.fontRendererObj.FONT_HEIGHT, mouseX + mc.fontRendererObj.getStringWidth(this.m.descript) + 13, mouseY + mc.fontRendererObj.FONT_HEIGHT + 1, Button.color);
			Gui.drawRect(mouseX + 9, mouseY - 2, mouseX + 10, mouseY + mc.fontRendererObj.FONT_HEIGHT + 1, Button.color);
			Gui.drawRect(mouseX + mc.fontRendererObj.getStringWidth(this.m.descript) + 12, mouseY - 1, mouseX + mc.fontRendererObj.getStringWidth(this.m.descript) + 13, mouseY + mc.fontRendererObj.FONT_HEIGHT + 1, Button.color);
			mc.fontRendererObj.drawStringWithShadow(this.m.descript, mouseX + 11, mouseY, Button.color);
			GL11.glDisable(32823);
            GL11.glPolygonOffset(1.0f, 1100000.0f);
		}
    }
}