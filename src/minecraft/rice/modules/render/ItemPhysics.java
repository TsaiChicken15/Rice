package rice.modules.render;

import org.lwjgl.input.Keyboard;

import rice.modules.Module;
import rice.modules.Module.Category;

public class ItemPhysics extends Module
{
	public ItemPhysics() {
		super("ItemPhysics", Keyboard.KEY_NONE, Category.RENDER, "");
	}
}
