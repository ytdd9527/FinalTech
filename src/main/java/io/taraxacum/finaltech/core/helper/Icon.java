package io.taraxacum.finaltech.core.helper;

import io.github.thebusybiscuit.slimefun4.libraries.dough.items.CustomItemStack;
import io.github.thebusybiscuit.slimefun4.utils.ChestMenuUtils;
import io.taraxacum.finaltech.FinalTech;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

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

    public static final ItemStack WIKI_ICON = new CustomItemStack(Material.KNOWLEDGE_BOOK, FinalTech.getLanguageString("helper", "ICON", "wiki-icon", "name"));
}
