package rice.modules.utility;

import org.lwjgl.input.Keyboard;

import net.minecraft.block.Block;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.item.ItemAxe;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C07PacketPlayerDigging;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MovingObjectPosition.MovingObjectType;
import rice.events.Event;
import rice.events.listeners.EventPacket;
import rice.events.listeners.EventUpdate;
import rice.modules.Module;
import rice.settings.BooleanSetting;
import rice.settings.NumberSetting;

public class SpeedMine extends Module
{
	public SpeedMine() 
	{
		super("SpeedMine", Keyboard.KEY_NONE, Category.UTILITY, "");
		this.addSettings(airValue, waterValue);
	}
	public static BooleanSetting airValue = new BooleanSetting("Air", true);
	public static BooleanSetting waterValue = new BooleanSetting("Water", false);
}