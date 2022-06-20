package rice.ui.clickgui.simple.button;

import net.minecraft.client.gui.Gui;
import rice.utils.MCHook;

public class Button extends Gui implements MCHook
{
    public String name;
    public boolean focused;
    public static int time = 0;
    public static int color = -1;
    public void draw(int mouseX, int mouseY) {}
}