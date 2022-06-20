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

public class FastPlace extends Module
{
	public FastPlace() 
	{
		super("FastPlace", Keyboard.KEY_NONE, Category.UTILITY, "Lowers the block place delay");
		this.addSettings(speedValue);
	}
	public static NumberSetting speedValue = new NumberSetting("Speed", 1, 0, 4, 1);
	public static BooleanSetting onlyBlockValue	 = new BooleanSetting("OnlyBlock",false);
	public void onEvent2(Event e) 
	{
		if(e instanceof EventUpdate) 
		{
			this.additionalInfo = String.valueOf(speedValue.get());
		}
	}
	public void onEvent(Event e) 
	{
		if (e instanceof EventUpdate)
		{
			if(mc.thePlayer.inventory.getCurrentItem() != null)
			{
				if(onlyBlockValue.get()) 
				{
					if((!(mc.thePlayer.getHeldItem().getItem() instanceof ItemBlock)) || mc.thePlayer.getHeldItem().getItem() == null)
					{
						return;
					}
				}
			}
			if(mc.rightClickDelayTimer == 4)
				mc.rightClickDelayTimer = (int) speedValue.get();
		}
	}
}