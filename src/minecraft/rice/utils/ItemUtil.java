package rice.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemGlassBottle;
import net.minecraft.item.ItemPotion;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;

public class ItemUtil implements MCHook 
{
    public static ItemStack bestSword() 
    {
        ItemStack best = null;
        float swordDamage = 0.0f;
        for (int i = 9; i < 45; ++i) 
        {
            if (mc.thePlayer.inventoryContainer.getSlot(i).getHasStack()) 
            {
                final ItemStack is = mc.thePlayer.inventoryContainer.getSlot(i).getStack();
                if (is.getItem() instanceof ItemSword) 
                {
                    final float swordD = getItemDamage(is);
                    if (swordD > swordDamage) 
                    {
                        swordDamage = swordD;
                        best = is;
                    }
                }
            }
        }
        return best;
    }
	    
    public static ItemStack compareDamage(final ItemStack item1, final ItemStack item2) 
    {
        try 
        {
            if (item1 == null || item2 == null) 
            {
                return null;
            }
            if (!(item1.getItem() instanceof ItemSword) && item2.getItem() instanceof ItemSword) 
            {
                return null;
            }
            if (getItemDamage(item1) > getItemDamage(item2)) 
            {
                return item1;
            }
            if (getItemDamage(item2) > getItemDamage(item1)) 
            {
                return item2;
            }
            return item1;
        }
        catch (NullPointerException e) 
        {
            e.printStackTrace();
            return item1;
        }
    }
    
    public static boolean isBadBlock(final ItemStack item) 
    {
        return (item.getItem().getUnlocalizedName().toLowerCase().contains("sand") && !item.getItem().getUnlocalizedName().toLowerCase().contains("stone")) || 
        		item.getItem().getUnlocalizedName().toLowerCase().contains("gravel") || 
        		item.getItem().getUnlocalizedName().toLowerCase().contains("slab") || 
        		item.getItem().getUnlocalizedName().toLowerCase().contains("stairs") || 
        		item.getItem().getUnlocalizedName().toLowerCase().contains("wall") || 
        		item.getItem().getUnlocalizedName().toLowerCase().contains("ladder") || 
        		item.getItem().getUnlocalizedName().toLowerCase().contains("anvil") || 
        		item.getItem().getUnlocalizedName().toLowerCase().contains("chest") || 
        		item.getItem().getUnlocalizedName().toLowerCase().contains("fence") || 
        		item.getItem().getUnlocalizedName().toLowerCase().contains("portal") || 
        		item.getItem().getUnlocalizedName().toLowerCase().contains("thin") || 
        		item.getItem().getUnlocalizedName().toLowerCase().contains("carpet") || 
        		item.getItem().getUnlocalizedName().toLowerCase().contains("skull") || 
        		item.getItem().getUnlocalizedName().toLowerCase().contains("dispenser") || 
        		item.getItem().getUnlocalizedName().toLowerCase().contains("dropper") || 
        		item.getItem().getUnlocalizedName().toLowerCase().contains("hopper") || 
        		item.getItem().getUnlocalizedName().toLowerCase().contains("music") || 
        		item.getItem().getUnlocalizedName().toLowerCase().contains("door") || 
        		item.getItem().getUnlocalizedName().toLowerCase().contains("workbench") || 
        		item.getItem().getUnlocalizedName().toLowerCase().contains("furnace") || 
        		item.getItem().getUnlocalizedName().toLowerCase().contains("trapdoor") || 
        		item.getItem().getUnlocalizedName().toLowerCase().contains("jukebox") || 
        		item.getItem().getUnlocalizedName().toLowerCase().contains("enchantment") || 
        		item.getItem().getUnlocalizedName().toLowerCase().contains("brewing") || 
        		item.getItem().getUnlocalizedName().toLowerCase().contains("cauldron") || 
        		item.getItem().getUnlocalizedName().toLowerCase().contains("sapling");
    }
    
    public static boolean isBadPotion(final ItemStack stack) 
    {
        if (stack != null && stack.getItem() instanceof ItemPotion) 
        {
            final ItemPotion potion = (ItemPotion)stack.getItem();
            if (ItemPotion.isSplash(stack.getItemDamage())) 
            {
                for (final Object o : potion.getEffects(stack)) 
                {
                    final PotionEffect effect = (PotionEffect)o;
                    if (effect.getPotionID() == Potion.poison.getId() || 
                    		effect.getPotionID() == Potion.harm.getId() || 
                    		effect.getPotionID() == Potion.moveSlowdown.getId() || 
                    		effect.getPotionID() == Potion.weakness.getId()) 
                    {
                        return true;
                    }
                }
            }
        }
        return false;
    }
    
    public static float getItemDamage(final ItemStack itemStack) 
    {
        if (itemStack == null) 
        {
            return 0.0f;
        }
        if (!(itemStack.getItem() instanceof ItemSword)) 
        {
            return 0.0f;
        }
        float damage = ((ItemSword)itemStack.getItem()).func_150931_i();
        damage += EnchantmentHelper.getEnchantmentLevel(Enchantment.field_180314_l.effectId, itemStack) * 1.25f;
        damage += EnchantmentHelper.getEnchantmentLevel(Enchantment.fireAspect.effectId, itemStack) * 0.01f;
        return damage;
    }
}