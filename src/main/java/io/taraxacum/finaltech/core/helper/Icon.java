package io.taraxacum.finaltech.core.helper;

import io.github.thebusybiscuit.slimefun4.libraries.dough.items.CustomItemStack;
import io.github.thebusybiscuit.slimefun4.utils.ChestMenuUtils;
import io.taraxacum.finaltech.FinalTech;
import io.taraxacum.finaltech.setup.FinalTechItemStacks;
import io.taraxacum.libs.plugin.util.ItemStackUtil;
import io.taraxacum.finaltech.util.ConstantTableUtil;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Final_ROOT
 */
public class Icon {
    public static final ItemStack QUANTITY_MODULE_ICON = new CustomItemStack(Material.REDSTONE, FinalTech.getLanguageString("helper", "ICON", "quantity-module", "name"), FinalTech.getLanguageStringArray("helper", "ICON", "quantity-module", "lore"));

    public static final ItemStack BORDER_ICON = ChestMenuUtils.getBackground();
//    public static final ItemStack INPUT_BORDER_ICON = new CustomItemStack(Material.BLUE_STAINED_GLASS_PANE, FinalTech.getLanguageString("helper", "CARGO_ORDER", "input-border", "name"), FinalTech.getLanguageStringArray("helper", "CARGO_ORDER", "input-border", "lore"));
    public static final ItemStack INPUT_BORDER_ICON = ChestMenuUtils.getInputSlotTexture();
//    public static final ItemStack OUTPUT_BORDER_ICON = new CustomItemStack(Material.ORANGE_STAINED_GLASS_PANE, FinalTech.getLanguageString("helper", "CARGO_ORDER", "output-border", "name"), FinalTech.getLanguageStringArray("helper", "CARGO_ORDER", "output-border", "lore"));
    public static final ItemStack OUTPUT_BORDER_ICON = ChestMenuUtils.getOutputSlotTexture();
    public static final ItemStack SPECIAL_BORDER_ICON = new CustomItemStack(Material.PURPLE_STAINED_GLASS_PANE, " ");

    public static final ItemStack NEXT_PAGE_ICON = new CustomItemStack(Material.GREEN_STAINED_GLASS_PANE, FinalTech.getLanguageString("helper", "ICON", "next-page", "name"), FinalTech.getLanguageStringArray("helper", "ICON", "next-page", "lore"));
    public static final ItemStack PREVIOUS_PAGE_ICON = new CustomItemStack(Material.RED_STAINED_GLASS_PANE, FinalTech.getLanguageString("helper", "ICON", "previous-page", "name"), FinalTech.getLanguageStringArray("helper", "ICON", "previous-page", "lore"));

    public static final ItemStack STATUS_ICON = new CustomItemStack(Material.GREEN_STAINED_GLASS_PANE, FinalTech.getLanguageString("helper", "ICON", "status", "name"), FinalTech.getLanguageStringArray("helper", "ICON", "status", "lore"));

    public static final ItemStack ERROR_ICON = new CustomItemStack(Material.BARRIER, FinalTech.getLanguageString("helper", "ICON", "error", "name"), FinalTech.getLanguageStringArray("helper", "ICON", "error", "lore"));

    public static final ItemStack WIKI_ICON = new CustomItemStack(Material.KNOWLEDGE_BOOK, FinalTech.getLanguageString("helper", "ICON", "wiki-icon", "name"), FinalTech.getLanguageStringArray("helper", "ICON", "wiki-icon", "lore"));

    public static final ItemStack RECIPE_ICON = new CustomItemStack(Material.PAPER, FinalTech.getLanguageString("helper", "ICON", "recipe-icon", "name"), FinalTech.getLanguageStringArray("helper", "ICON", "recipe-icon", "lore"));

    public static final ItemStack CONSUMER_ICON = new CustomItemStack(Material.FURNACE, FinalTech.getLanguageString("helper", "ICON", "consumer", "name"), FinalTech.getLanguageStringArray("helper", "ICON", "consumer", "lore"));

    public static final ItemStack GENERATOR_ICON = new CustomItemStack(Material.DAYLIGHT_DETECTOR, FinalTech.getLanguageString("helper", "ICON", "generator", "name"), FinalTech.getLanguageStringArray("helper", "ICON", "generator", "lore"));

    public static final ItemStack CAPACITOR_ICON = new CustomItemStack(Material.YELLOW_STAINED_GLASS, FinalTech.getLanguageString("helper", "ICON", "capacitor", "name"), FinalTech.getLanguageStringArray("helper", "ICON", "capacitor", "lore"));

    public static int updateQuantityModule(@Nonnull BlockMenu blockMenu, int quantityModuleSlot, int statusSlot) {
        boolean updateLore = blockMenu.hasViewer();
        ItemStack item = blockMenu.getItemInSlot(quantityModuleSlot);
        int amount;
        List<String> lores = null;
        if (ItemStackUtil.isItemSimilar(item, FinalTechItemStacks.QUANTITY_MODULE)) {
            amount = item.getAmount();
            if (updateLore) {
                lores = FinalTech.getLanguageManager().replaceStringList(FinalTech.getLanguageStringList("helper", "ICON", "quantity-module", "amount-lore"),
                        String.valueOf(amount));
            }
        } else if (ItemStackUtil.isItemSimilar(item, FinalTechItemStacks.QUANTITY_MODULE_INFINITY)) {
            amount = Integer.MAX_VALUE / ConstantTableUtil.ITEM_MAX_STACK - 1;
            if (updateLore) {
                lores = FinalTech.getLanguageManager().replaceStringList(FinalTech.getLanguageStringList("helper", "ICON", "quantity-module", "amount-lore"),
                        FinalTech.getLanguageString("helper", "ICON", "quantity-module", "amount-infinity"));
            }
        } else {
            amount = 1;
            if (updateLore) {
                lores = FinalTech.getLanguageManager().replaceStringList(FinalTech.getLanguageStringList("helper", "ICON", "quantity-module", "amount-none-lore"),
                        "1");
            }
        }

        if (!updateLore) {
            return amount;
        }

        List<String> loreList = new ArrayList<>(FinalTech.getLanguageStringList("helper", "ICON", "quantity-module", "lore"));
        loreList.addAll(lores);

        ItemStack infoItem = blockMenu.getItemInSlot(statusSlot);
        ItemStackUtil.setLore(infoItem, loreList);

        return amount;
    }
}
