package io.taraxacum.finaltech.util.menu;

import io.github.thebusybiscuit.slimefun4.libraries.dough.items.CustomItemStack;
import io.taraxacum.finaltech.util.ItemStackUtil;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;

/**
 * @author Final_ROOT
 */
public class MaxStackHelper {
    public static final String KEY = "max-stack";

    public static final ItemStack ICON = new CustomItemStack(Material.CHEST, "&7输入数量限制", "&7未限制");

    public static void setIcon(@Nonnull ItemStack item, int quantity) {
        if (quantity == 0) {
            item.setType(Material.CHEST);
            item.setAmount(1);
            ItemStackUtil.setLastLore(item, "§7未限制");
        } else {
            item.setType(Material.HOPPER);
            item.setAmount(quantity);
            ItemStackUtil.setLastLore(item, "§7限制数量=" + quantity);
        }
    }
}
