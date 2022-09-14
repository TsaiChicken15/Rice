		package rice.modules.utility;

import javax.swing.JSlider;

import org.lwjgl.input.Keyboard;

import rice.events.Event;
import rice.events.listeners.EventUpdate;
import rice.jui.JClickGUI;
import rice.modules.Module;
import rice.settings.BooleanSetting;
import rice.settings.NumberSetting;
import rice.utils.KeybindUtil;
import rice.utils.MSTimer;
import rice.utils.RandomUtil;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.init.Items;
import net.minecraft.item.*;

public class AutoArmor extends Module
{
	private int[] chestplate, leggings, boots, helmet;
    private boolean best;
    public MSTimer timer = new MSTimer();
	public AutoArmor() 
	{
		super("AutoArmor", Keyboard.KEY_NONE, Category.UTILITY, "Automatically equips armor as needed");
		chestplate = new int[] {311, 307, 315, 303, 299};
        leggings = new int[] {312, 308, 316, 304, 300};
        boots = new int[] {313, 309, 317, 305, 301};
        helmet = new int[] {310, 306, 314, 302, 298};
        best = true;
		this.addSettings(minDelayValue,maxDelayValue,onlyInvValue);
	}
	public static NumberSetting minDelayValue = new NumberSetting("MinDelay", 100, 1, 400, 1);
	public static NumberSetting maxDelayValue = new NumberSetting("MaxDelay", 150, 1, 400, 1);
	public static BooleanSetting onlyInvValue = new BooleanSetting("OnlyInv", true);
	public void onEvent2(Event e)
	{
		if (e instanceof EventUpdate) 
		{
			this.additionalInfo = String.valueOf(minDelayValue.get()) + ", " + String.valueOf(maxDelayValue.get());
			if(minDelayValue.get() > maxDelayValue.get()) 
			{
				double temp = minDelayValue.get();
				for(JSlider js: JClickGUI.sliders) {
					if(js.getName().contains(this.name) && js.getName().contains(minDelayValue.name))
						js.setValue((int) (maxDelayValue.get() * 100));
					if(js.getName().contains(this.name) && js.getName().contains(maxDelayValue.name))
						js.setValue((int) (minDelayValue.get() * 100));
				}
				minDelayValue.setValue(maxDelayValue.get());
				maxDelayValue.setValue(temp);
			}
		}
	}
    public void onEvent(Event e)
    {
    	if(e instanceof EventUpdate && e.isPRE()) 
    	{
    		if(onlyInvValue.get() && (mc.currentScreen == null) && !(mc.currentScreen instanceof GuiInventory)) return;
    		autoArmor();
    		betterArmor();
    	}
    }
    public void autoArmor() 
    {
        if(best) return;
        int item = -1;
        if(timer.hasTimePassed(RandomUtil.randomInt((int)minDelayValue.get(), (int)maxDelayValue.get()))) 
        {
            if(mc.thePlayer.inventory.armorInventory[0] == null) 
            {
                int[] boots;
                int length = (boots = this.boots).length;
                for(int i =0; i < length; i++) 
                {
                    int id = boots[i];
                    if(getItem(id) != -1) 
                    {
                        item = getItem(id);
                        break;
                    }
                }
            }
            if(mc.thePlayer.inventory.armorInventory[1] == null) 
            {
                int[] leggings;
                int length = (leggings = this.leggings).length;
                for(int i = 0; i < length; i++) 
                {
                    int id = leggings[i];
                    if(getItem(id) != -1)
                    {
                        item = getItem(id);
                        break;
                    }
                }
            }
            if(mc.thePlayer.inventory.armorInventory[2] == null) 
            {
                int[] chestplate;
                int length = (chestplate = this.chestplate).length;
                for(int i = 0; i < length; i++) 
                {
                    int id = chestplate[i];
                    if(getItem(id) != -1)
                    {
                        item = getItem(id);
                        break;
                    }
                }
            }
            if(mc.thePlayer.inventory.armorInventory[3] == null) 
            {
                int[] helmet;
                int length = (helmet = this.helmet).length;
                for(int i = 0; i < length; i++) 
                {
                    int id = helmet[i];
                    if(getItem(id) != -1) 
                    {
                        item = getItem(id);
                        break;
                    }
                }
            }
            if(item != -1) mc.playerController.windowClick(0, item, 0, 1, mc.thePlayer);
            timer.reset();
        }
    }

    public void betterArmor() 
    {
        if(!best) return;
        if(timer.hasTimePassed(RandomUtil.randomInt((int)minDelayValue.get(), (int)maxDelayValue.get())))
        {
            boolean switchArmor = false;
            int item = -1;
            int[] array;
            int i;
            if(mc.thePlayer.inventory.armorInventory[0] == null)
            {
                int j = (array = this.boots).length;
                for(i = 0; i < j; i++) {
                    int id = array[i];
                    if(getItem(id) != -1) 
                    {
                        item = getItem(id);
                        break;
                    }
                }
            }
            if (isBetterArmor(0, this.boots)) 
            {
                item = 8;
                switchArmor = true;
            }
            if(mc.thePlayer.inventory.armorInventory[3] == null) 
            {
                int j = (array = this.helmet).length;
                for(i = 0; i < j; i++) 
                {
                    int id = array[i];
                    if(getItem(id) != -1) 
                    {
                        item = getItem(id);
                        break;
                    }
                }
            }
            if (isBetterArmor(3, this.helmet)) 
            {
                item = 5;
                switchArmor = true;
            }
            if(mc.thePlayer.inventory.armorInventory[1] == null) 
            {
                int j = (array = this.leggings).length;
                for(i = 0; i < j; i++) 
                {
                    int id = array[i];
                    if(getItem(id) != -1) 
                    {
                        item = getItem(id);
                        break;
                    }
                }
            }
            if (isBetterArmor(1, this.leggings)) 
            {
                item = 7;
                switchArmor = true;
            }
            if(mc.thePlayer.inventory.armorInventory[2] == null) 
            {
                int j = (array = this.chestplate).length;
                for(i = 0; i < j; i++) 
                {
                    int id = array[i];
                    if(getItem(id) != -1) 
                    {
                        item = getItem(id);
                        break;
                    }
                }
            }
            if (isBetterArmor(2, this.chestplate))
            {
                item = 6;
                switchArmor = true;
            }
            boolean b = false;
            ItemStack[] stackArray;
            int k = (stackArray = mc.thePlayer.inventory.mainInventory).length;
            for(int j = 0; j < k; j++) 
            {
                ItemStack stack = stackArray[j];
                if(stack == null) {
                    b = true;
                    break;
                }
            }
            switchArmor = switchArmor && !b;
            if(item != -1) mc.playerController.windowClick(0, item, 0, switchArmor ? 4 : 1, mc.thePlayer);
            timer.reset();
        }
    }
    public static boolean isBetterArmor(int slot, int[] armorType) 
    {
        if(mc.thePlayer.inventory.armorInventory[slot] != null) 
        {
            int currentIndex = 0;
            int invIndex = 0;
            int finalCurrentIndex = -1;
            int finalInvIndex = -1;
            int[] array;
            int j = (array = armorType).length;
            for(int i = 0; i < j; i++) {
                int armor = array[i];
                if(Item.getIdFromItem(mc.thePlayer.inventory.armorInventory[slot].getItem()) == armor)
                {
                    finalCurrentIndex = currentIndex;
                    break;
                }
                currentIndex++;
            }
            j = (array = armorType).length;
            for(int i = 0; i < j; i++)
            {
                int armor = array[i];
                if(getItem(armor) != -1) 
                {
                    finalInvIndex = invIndex;
                    break;
                }
                invIndex++;
            }
            if(finalInvIndex > -1) return finalInvIndex < finalCurrentIndex;
        }
        return false;
    }

    public static int getItem(int id) 
    {
        for(int i = 9; i < 45; i++) 
        {
            ItemStack item = mc.thePlayer.inventoryContainer.getSlot(i).getStack();
            if(item != null && Item.getIdFromItem(item.getItem()) == id) return i;
        }
        return -1;
    }
}