package io.taraxacum.finaltech.setup;

import io.github.thebusybiscuit.slimefun4.api.items.groups.NestedItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.groups.SubItemGroup;
import io.github.thebusybiscuit.slimefun4.libraries.dough.items.CustomItemStack;
import io.taraxacum.finaltech.FinalTech;
import io.taraxacum.finaltech.core.group.MainItemGroup;
import io.taraxacum.finaltech.core.group.SubFlexItemGroup;
import io.taraxacum.finaltech.util.TextUtil;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;

/**
 * @author Final_ROOT
 */
public final class FinalTechMenus {
    // TODO 头颅材质
    public static final NestedItemGroup MAIN_MENU = new NestedItemGroup(new NamespacedKey(FinalTech.getPlugin(FinalTech.class), "FINALTECH_CATEGORY_MAIN"), new CustomItemStack(Material.OBSERVER, FinalTech.getLanguageString("category", "main", "name")));
    public static final SubItemGroup MENU_MATERIAL = new SubItemGroup(new NamespacedKey(FinalTech.getPlugin(FinalTech.class), "FINALTECH_CATEGORY_MATERIAL"), MAIN_MENU , new CustomItemStack(Material.AMETHYST_SHARD, FinalTech.getLanguageString("category", "material", "name")));
    public static final SubItemGroup MENU_TOOL = new SubItemGroup(new NamespacedKey(FinalTech.getPlugin(FinalTech.class), "FINALTECH_TOOL"), MAIN_MENU, new CustomItemStack(Material.KNOWLEDGE_BOOK, FinalTech.getLanguageString("category", "tool", "name")));
    public static final SubItemGroup MENU_WEAPON = new SubItemGroup(new NamespacedKey(FinalTech.getPlugin(FinalTech.class), "FINALTECH_WEAPON"), MAIN_MENU, new CustomItemStack(Material.ARROW, FinalTech.getLanguageString("category", "weapon", "name")));
    public static final SubItemGroup MENU_ELECTRIC = new SubItemGroup(new NamespacedKey(FinalTech.getPlugin(FinalTech.class), "FINALTECH_ELECTRIC"), MAIN_MENU, new CustomItemStack(Material.MAGMA_BLOCK, FinalTech.getLanguageString("category", "electric", "name")));
    public static final SubItemGroup MENU_CARGO = new SubItemGroup(new NamespacedKey(FinalTech.getPlugin(FinalTech.class), "FINALTECH_CARGO"), MAIN_MENU, new CustomItemStack(Material.CHAIN, FinalTech.getLanguageString("category", "cargo", "name")));
    public static final SubItemGroup MENU_FUNCTIONAL_MACHINE = new SubItemGroup(new NamespacedKey(FinalTech.getPlugin(FinalTech.class), "FINALTECH_FUNCTIONAL_MACHINE"), MAIN_MENU, new CustomItemStack(Material.STONE, FinalTech.getLanguageString("category", "functional-machine", "name")));
    public static final SubItemGroup MENU_TOWER_MACHINE = new SubItemGroup(new NamespacedKey(FinalTech.getInstance(), "FINALTECH_TOWER_MACHINE"), MAIN_MENU, new CustomItemStack(Material.BEACON, FinalTech.getLanguageString("category", "tower-machine", "name")));
    public static final SubItemGroup MENU_MANUAL_MACHINE = new SubItemGroup(new NamespacedKey(FinalTech.getPlugin(FinalTech.class), "FINALTECH_MANUAL_MACHINE"), MAIN_MENU, new CustomItemStack(Material.STONE, FinalTech.getLanguageString("category", "manual-machine", "name")));
    public static final SubItemGroup MENU_BASIC_MACHINE = new SubItemGroup(new NamespacedKey(FinalTech.getPlugin(FinalTech.class), "FINALTECH_BASIC_MACHINE"), MAIN_MENU, new CustomItemStack(Material.PISTON, FinalTech.getLanguageString("category", "productive-machine-basic", "name")));
    public static final SubItemGroup MENU_ADVANCED_MACHINE = new SubItemGroup(new NamespacedKey(FinalTech.getPlugin(FinalTech.class), "FINALTECH_ADVANCED_MACHINE"), MAIN_MENU, new CustomItemStack(Material.STICKY_PISTON, FinalTech.getLanguageString("category", "productive-machine-advanced", "name")));
    public static final SubItemGroup MENU_CONVERSION_MACHINE = new SubItemGroup(new NamespacedKey(FinalTech.getPlugin(FinalTech.class), "FINALTECH_CONVERSION_MACHINE"), MAIN_MENU, new CustomItemStack(Material.STICKY_PISTON, FinalTech.getLanguageString("category", "productive-machine-conversion", "name")));
    public static final SubItemGroup MENU_EXTRACTION_MACHINE = new SubItemGroup(new NamespacedKey(FinalTech.getPlugin(FinalTech.class), "FINALTECH_EXTRACTION_MACHINE"), MAIN_MENU, new CustomItemStack(Material.STICKY_PISTON, FinalTech.getLanguageString("category", "productive-machine-extraction", "name")));
    public static final SubItemGroup MENU_GENERATOR_MACHINE = new SubItemGroup(new NamespacedKey(FinalTech.getPlugin(FinalTech.class), "FINALTECH_GENERATOR_MACHINE"), MAIN_MENU, new CustomItemStack(Material.STICKY_PISTON, FinalTech.getLanguageString("category", "productive-machine-generator", "name")));
    public static final SubItemGroup MENU_FINAL_ITEM = new SubItemGroup(new NamespacedKey(FinalTech.getPlugin(FinalTech.class), "FINALTECH_FINAL_ITEM"), MAIN_MENU, new CustomItemStack(Material.AMETHYST_BLOCK, FinalTech.getLanguageString("category", "final-item", "name")));

    public static final MainItemGroup MAIN_MENU_T = new MainItemGroup(new NamespacedKey(FinalTech.getInstance(), "FINALTECH_CATEGORY_MAIN_T"), new CustomItemStack(Material.OBSERVER, FinalTech.getLanguageString("category", "main", "name")), 0);
    public static final SubFlexItemGroup SUB_MENU_T = new SubFlexItemGroup(new NamespacedKey(FinalTech.getInstance(), "FINALTECH_CATEGORY_MAIN_T1"), new CustomItemStack(Material.STONE, TextUtil.colorRandomString("测试-付")), 1);
    public static final SubFlexItemGroup SUB_MENU_T2 = new SubFlexItemGroup(new NamespacedKey(FinalTech.getInstance(), "FINALTECH_CATEGORY_MAIN_T2"), new CustomItemStack(Material.STONE, TextUtil.colorRandomString("测试-子")), 1);
    public static final SubFlexItemGroup SUB_MENU_T3 = new SubFlexItemGroup(new NamespacedKey(FinalTech.getInstance(), "FINALTECH_CATEGORY_MAIN_T3"), new CustomItemStack(Material.STONE, TextUtil.colorRandomString("测试-子2")), 1);
}
