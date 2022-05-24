package io.taraxacum.finaltech.setup;

import io.github.thebusybiscuit.slimefun4.api.items.groups.NestedItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.groups.SubItemGroup;
import io.github.thebusybiscuit.slimefun4.libraries.dough.items.CustomItemStack;
import io.taraxacum.finaltech.FinalTech;
import io.taraxacum.finaltech.util.TextUtil;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;

/**
 * @author Final_ROOT
 */
public final class FinalTechMenus {
    public static final NestedItemGroup MAIN_MENU = new NestedItemGroup(new NamespacedKey(FinalTech.getPlugin(FinalTech.class), "FINALTECH_CATEGORY_MAIN"), new CustomItemStack(Material.OBSERVER, TextUtil.colorRandomString("乱序技艺-2.0")));
    public static final SubItemGroup MENU_MATERIAL = new SubItemGroup(new NamespacedKey(FinalTech.getPlugin(FinalTech.class), "FINALTECH_CATEGORY_MATERIAL"), MAIN_MENU , new CustomItemStack(Material.AMETHYST_SHARD, TextUtil.colorRandomString("合成材料")));
    public static final SubItemGroup MENU_TOOL = new SubItemGroup(new NamespacedKey(FinalTech.getPlugin(FinalTech.class), "FINALTECH_TOOL"), MAIN_MENU, new CustomItemStack(Material.KNOWLEDGE_BOOK, TextUtil.colorRandomString("工具")));
    public static final SubItemGroup MENU_ELECTRIC = new SubItemGroup(new NamespacedKey(FinalTech.getPlugin(FinalTech.class), "FINALTECH_ELECTRIC"), MAIN_MENU, new CustomItemStack(Material.MAGMA_BLOCK, TextUtil.colorRandomString("电力系统")));
    public static final SubItemGroup MENU_CARGO = new SubItemGroup(new NamespacedKey(FinalTech.getPlugin(FinalTech.class), "FINALTECH_CARGOS"), MAIN_MENU, new CustomItemStack(Material.CHAIN, TextUtil.colorRandomString("运输与存储")));
    public static final SubItemGroup MENU_FUNCTION_MACHINE = new SubItemGroup(new NamespacedKey(FinalTech.getPlugin(FinalTech.class), "FINALTECH_CATEGORY_FUNCTION_MACHINE"), MAIN_MENU, new CustomItemStack(Material.STONE, TextUtil.colorRandomString("核心机器")));
    public static final SubItemGroup MENU_BASIC_MACHINE = new SubItemGroup(new NamespacedKey(FinalTech.getPlugin(FinalTech.class), "FINALTECH_CATEGORY_BASIC_MACHINE"), MAIN_MENU, new CustomItemStack(Material.PISTON, TextUtil.colorRandomString("基础机器")));
    public static final SubItemGroup MENU_ADVANCED_MACHINE = new SubItemGroup(new NamespacedKey(FinalTech.getPlugin(FinalTech.class), "FINALTECH_CATEGORY_ADVANCED_MACHINE"), MAIN_MENU, new CustomItemStack(Material.STICKY_PISTON, TextUtil.colorRandomString("高级机器")));
    public static final SubItemGroup MENU_FINAL_ITEM = new SubItemGroup(new NamespacedKey(FinalTech.getPlugin(FinalTech.class), "FINALTECH_CATEGORY_FINAL_ITEM"), MAIN_MENU, new CustomItemStack(Material.AMETHYST_BLOCK, TextUtil.colorRandomString("终极物品")));
}
