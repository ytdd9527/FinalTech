package io.taraxacum.finaltech.util.menu;

import io.github.thebusybiscuit.slimefun4.libraries.dough.items.CustomItemStack;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Final_ROOT
 */
public class CargoNumber {
    public static final String KEY = "cargo-number";
    public static final String KEY_INPUT = "input-cargo-number";
    public static final String KEY_OUTPUT = "output-cargo-number";

    public static final ItemStack CARGO_NUMBER_ICON = new CustomItemStack(Material.TARGET, "&d数量限制", "&7当前数量限制: &f64");
    public static final ItemStack CARGO_NUMBER_ADD_ICON = new CustomItemStack(Material.GREEN_CONCRETE, "&6增加数量限制");
    public static final ItemStack CARGO_NUMBER_SUB_ICON = new CustomItemStack(Material.RED_CONCRETE, "&9减少数量限制");

    public static final void setIcon(ItemStack item, int amount) {
        if (item == null) {
            return;
        }
        if (amount <= item.getMaxStackSize()) {
            item.setAmount(amount);
        } else {
            item.setAmount(item.getMaxStackSize());
        }
        ItemMeta itemMeta = item.getItemMeta();
        List<String> lore = itemMeta.getLore();
        if (lore == null || lore.size() < 1) {
            lore = new ArrayList<>();
            lore.add(0, "§7当前数量限制: §f" + amount);
        }
        lore.set(0, "§7当前数量限制: §f" + amount);
        itemMeta.setLore(lore);
        item.setItemMeta(itemMeta);
    }
}
