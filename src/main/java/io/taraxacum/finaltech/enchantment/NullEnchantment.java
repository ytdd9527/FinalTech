package io.taraxacum.finaltech.enchantment;

import io.taraxacum.finaltech.FinalTech;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.enchantments.EnchantmentTarget;
import org.bukkit.inventory.ItemStack;

/**
 * @author Final_ROOT
 */
public class NullEnchantment extends Enchantment {
    public static final Enchantment ENCHANTMENT = new NullEnchantment(new NamespacedKey(FinalTech.getPlugin(FinalTech.class), "FINALTECH_NULL_ENCHANTMENT"));
    private NullEnchantment(NamespacedKey key) {
        super(key);
    }

    @Override
    public String getName() {
        return "";
    }

    @Override
    public int getMaxLevel() {
        return 0;
    }

    @Override
    public int getStartLevel() {
        return 0;
    }

    @Override
    public EnchantmentTarget getItemTarget() {
        return EnchantmentTarget.ALL;
    }

    @Override
    public boolean isTreasure() {
        return false;
    }

    @Override
    public boolean isCursed() {
        return false;
    }

    @Override
    public boolean conflictsWith(Enchantment enchantment) {
        return false;
    }

    @Override
    public boolean canEnchantItem(ItemStack itemStack) {
        return true;
    }
}
