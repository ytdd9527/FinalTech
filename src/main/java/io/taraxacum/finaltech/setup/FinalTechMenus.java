package io.taraxacum.finaltech.setup;

import io.github.thebusybiscuit.slimefun4.api.items.groups.NestedItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.groups.SubItemGroup;
import io.github.thebusybiscuit.slimefun4.api.player.PlayerProfile;
import io.github.thebusybiscuit.slimefun4.core.guide.SlimefunGuideMode;
import io.github.thebusybiscuit.slimefun4.libraries.dough.items.CustomItemStack;
import io.taraxacum.finaltech.FinalTech;
import io.taraxacum.finaltech.core.group.MainItemGroup;
import io.taraxacum.finaltech.core.group.SubFlexItemGroup;
import io.taraxacum.finaltech.util.TextUtil;
import io.taraxacum.finaltech.util.slimefun.ConfigUtil;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;

import javax.annotation.Nonnull;

/**
 * @author Final_ROOT
 */
public final class FinalTechMenus {
    // TODO head texture

    /* Slimefun item group */
    public static final NestedItemGroup MAIN_MENU = new NestedItemGroup(new NamespacedKey(FinalTech.getInstance(), "FINALTECH_CATEGORY_MAIN"), new CustomItemStack(Material.OBSERVER, FinalTech.getLanguageString("categories", "MAIN", "name"))) {
        @Override
        public boolean isVisible(Player p, PlayerProfile profile, SlimefunGuideMode mode) {
            return false;
        }
    };
    public static final SubItemGroup MENU_ITEMS = new SubItemGroup(new NamespacedKey(FinalTech.getInstance(), "FINALTECH_ITEM"), MAIN_MENU , new CustomItemStack(Material.AMETHYST_SHARD, FinalTech.getLanguageString("categories", "MATERIAL", "name")));
    public static final SubItemGroup MENU_ELECTRICITY_SYSTEM = new SubItemGroup(new NamespacedKey(FinalTech.getInstance(), "FINALTECH_ELECTRIC_SYSTEM"), MAIN_MENU, new CustomItemStack(Material.MAGMA_BLOCK, FinalTech.getLanguageString("categories", "ELECTRIC", "name")));
    public static final SubItemGroup MENU_CARGO_SYSTEM = new SubItemGroup(new NamespacedKey(FinalTech.getInstance(), "FINALTECH_CARGO_SYSTEM"), MAIN_MENU, new CustomItemStack(Material.CHAIN, FinalTech.getLanguageString("categories", "CARGO", "name")));
    public static final SubItemGroup MENU_FUNCTIONAL_MACHINE = new SubItemGroup(new NamespacedKey(FinalTech.getInstance(), "FINALTECH_FUNCTIONAL_MACHINE"), MAIN_MENU, new CustomItemStack(Material.STONE, FinalTech.getLanguageString("categories", "FUNCTION_MACHINE", "name")));
    public static final SubItemGroup MENU_PRODUCTIVE_MACHINE = new SubItemGroup(new NamespacedKey(FinalTech.getInstance(), "FINALTECH_PRODUCTIVE_MACHINE"), MAIN_MENU, new CustomItemStack(Material.STONE, FinalTech.getLanguageString("categories", "PRODUCTIVE_MACHINE", "name")));
    public static final SubItemGroup MENU_FINAL_ITEM = new SubItemGroup(new NamespacedKey(FinalTech.getInstance(), "FINALTECH_FINAL_ITEM"), MAIN_MENU, new CustomItemStack(Material.AMETHYST_BLOCK, FinalTech.getLanguageString("categories", "FINAL_ITEM", "name")));

    /* My item group */
    public static final MainItemGroup MAIN_ITEM_GROUP = ConfigUtil.getMainItemGroup(FinalTech.getLanguageManager(), "FINALTECH_ITEM_GROUP", Material.STONE, "Main Group");
    // item
    public static final SubFlexItemGroup MAIN_MENU_ITEM = ConfigUtil.getSubFlexItemGroup(FinalTech.getLanguageManager(), "FINALTECH_MAIN_MENU_ITEM", Material.STONE, "Item");
    public static final SubFlexItemGroup SUB_MENU_MATERIAL = ConfigUtil.getSubFlexItemGroup(FinalTech.getLanguageManager(), "FINALTECH_SUB_MENU_MATERIAL", Material.STONE, "Material");
    public static final SubFlexItemGroup SUB_MENU_LOGIC_ITEM = ConfigUtil.getSubFlexItemGroup(FinalTech.getLanguageManager(), "FINALTECH_SUB_MENU_LOGIC_ITEM", Material.STONE, "Logic Item");
    public static final SubFlexItemGroup SUB_MENU_CONSUMABLE = ConfigUtil.getSubFlexItemGroup(FinalTech.getLanguageManager(), "FINALTECH_SUB_MENU_CONSUMABLE", Material.STONE, "Consumable");
    public static final SubFlexItemGroup SUB_MENU_TOOL = ConfigUtil.getSubFlexItemGroup(FinalTech.getLanguageManager(), "FINALTECH_SUB_MENU_TOOL", Material.STONE, "Tool");
    public static final SubFlexItemGroup SUB_MENU_WEAPON = ConfigUtil.getSubFlexItemGroup(FinalTech.getLanguageManager(), "FINALTECH_SUB_MENU_WEAPON", Material.STONE, "Weapon");

    // electricity system
    public static final SubFlexItemGroup MAIN_MENU_ELECTRICITY_SYSTEM = ConfigUtil.getSubFlexItemGroup(FinalTech.getLanguageManager(), "FINALTECH_MAIN_MENU_ELECTRICITY_SYSTEM", Material.STONE, "Electric System");
    public static final SubFlexItemGroup SUB_MENU_ELECTRIC_GENERATOR = ConfigUtil.getSubFlexItemGroup(FinalTech.getLanguageManager(), "FINALTECH_SUB_MENU_ELECTRIC_GENERATOR", Material.STONE, "Electric Generator");
    public static final SubFlexItemGroup SUB_MENU_ELECTRIC_STORAGE = ConfigUtil.getSubFlexItemGroup(FinalTech.getLanguageManager(), "FINALTECH_SUB_MENU_ELECTRIC_STORAGE", Material.STONE, "Electric Storage");
    public static final SubFlexItemGroup SUB_MENU_ELECTRIC_TRANSMISSION = ConfigUtil.getSubFlexItemGroup(FinalTech.getLanguageManager(), "FINALTECH_SUB_MENU_ELECTRIC_TRANSMISSION", Material.STONE, "Electric Transmission");
    public static final SubFlexItemGroup SUB_MENU_ELECTRIC_ACCELERATOR = ConfigUtil.getSubFlexItemGroup(FinalTech.getLanguageManager(), "FINALTECH_SUB_MENU_ELECTRIC_ACCELERATOR", Material.STONE, "Electric Accelerator");

    // cargo system
    public static final SubFlexItemGroup MAIN_MENU_CARGO_SYSTEM = ConfigUtil.getSubFlexItemGroup(FinalTech.getLanguageManager(), "FINALTECH_MAIN_MENU_CARGO_SYSTEM", Material.STONE, "Cargo System");
    public static final SubFlexItemGroup SUB_MENU_STORAGE_UNIT = ConfigUtil.getSubFlexItemGroup(FinalTech.getLanguageManager(), "FINALTECH_SUB_MENU_STORAGE_UNIT", Material.STONE, "Storage Unit");
    public static final SubFlexItemGroup SUB_MENU_ADVANCED_STORAGE = ConfigUtil.getSubFlexItemGroup(FinalTech.getLanguageManager(), "FINALTECH_SUB_MENU_ADVANCED_STORAGE", Material.STONE, "Advanced Storage");
    public static final SubFlexItemGroup SUB_MENU_ACCESSOR = ConfigUtil.getSubFlexItemGroup(FinalTech.getLanguageManager(), "FINALTECH_SUB_MENU_ACCESSOR", Material.STONE, "Accessor");
    public static final SubFlexItemGroup SUB_MENU_CARGO = ConfigUtil.getSubFlexItemGroup(FinalTech.getLanguageManager(), "FINALTECH_SUB_MENU_CARGO", Material.STONE, "Cargo");

    // functional machine
    public static final SubFlexItemGroup MAIN_MENU_FUNCTIONAL_MACHINE = ConfigUtil.getSubFlexItemGroup(FinalTech.getLanguageManager(), "FINALTECH_MAIN_MENU_FUNCTIONAL_MACHINE", Material.STONE, "Functional Machine");
    public static final SubFlexItemGroup SUB_MENU_CORE_MACHINE = ConfigUtil.getSubFlexItemGroup(FinalTech.getLanguageManager(), "FINALTECH_SUB_MENU_CORE_MACHINE", Material.STONE, "Core Machine");
    public static final SubFlexItemGroup SUB_MENU_SPECIAL_MACHINE = ConfigUtil.getSubFlexItemGroup(FinalTech.getLanguageManager(), "FINALTECH_SUB_MENU_SPECIAL_MACHINE", Material.STONE, "Special Machine");
    public static final SubFlexItemGroup SUB_MENU_TOWER = ConfigUtil.getSubFlexItemGroup(FinalTech.getLanguageManager(), "FINALTECH_SUB_MENU_TOWER", Material.STONE, "Tower");

    // productive machine
    public static final SubFlexItemGroup MAIN_MENU_PRODUCTIVE_MACHINE = ConfigUtil.getSubFlexItemGroup(FinalTech.getLanguageManager(), "FINALTECH_MAIN_MENU_PRODUCTIVE_MACHINE", Material.STONE, "Productive Machine");
    public static final SubFlexItemGroup SUB_MENU_MANUAL_MACHINE = ConfigUtil.getSubFlexItemGroup(FinalTech.getLanguageManager(), "FINALTECH_SUB_MENU_MANUAL_MACHINE", Material.STONE, "Manual Machine");
    public static final SubFlexItemGroup SUB_MENU_BASIC_MACHINE = ConfigUtil.getSubFlexItemGroup(FinalTech.getLanguageManager(), "FINALTECH_SUB_MENU_BASIC_MACHINE", Material.STONE, "Basic Machine");
    public static final SubFlexItemGroup SUB_MENU_ADVANCED_MACHINE = ConfigUtil.getSubFlexItemGroup(FinalTech.getLanguageManager(), "FINALTECH_SUB_MENU_ADVANCED_MACHINE", Material.STONE, "Advanced Machine");
    public static final SubFlexItemGroup SUB_MENU_CONVERSION = ConfigUtil.getSubFlexItemGroup(FinalTech.getLanguageManager(), "FINALTECH_SUB_MENU_CONVERSION", Material.STONE, "Conversion");
    public static final SubFlexItemGroup SUB_MENU_EXTRACTION = ConfigUtil.getSubFlexItemGroup(FinalTech.getLanguageManager(), "FINALTECH_SUB_MENU_EXTRACTION", Material.STONE, "Extraction");
    public static final SubFlexItemGroup SUB_MENU_GENERATOR = ConfigUtil.getSubFlexItemGroup(FinalTech.getLanguageManager(), "FINALTECH_SUB_MENU_GENERATOR", Material.STONE, "Generator");

    // final stage item
    public static final SubFlexItemGroup MAIN_MENU_FINAL_ITEM = ConfigUtil.getSubFlexItemGroup(FinalTech.getLanguageManager(), "FINALTECH_MAIN_MENU_FINAL_ITEM", Material.STONE, "Final Item");
}
