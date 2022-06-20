package rice.utils;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.inventory.Container;

public interface MCHook 
{
	FontRenderer _fr = Minecraft.getMinecraft().fontRendererObj;
	GameSettings _gs = Minecraft.getMinecraft().gameSettings;
	KeybindUtil _ku = new KeybindUtil();
	Minecraft mc = Minecraft.getMinecraft();
	MouseUtil _mu = new MouseUtil();
}