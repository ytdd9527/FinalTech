package io.taraxacum.finaltech.setup;

import io.github.thebusybiscuit.slimefun4.api.items.groups.NestedItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.groups.SubItemGroup;
import io.github.thebusybiscuit.slimefun4.libraries.dough.items.CustomItemStack;
import io.taraxacum.finaltech.FinalTech;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;

public final class FinalTechMenus {
    public static final NestedItemGroup MAIN_MENU = new NestedItemGroup(new NamespacedKey(FinalTech.getPlugin(FinalTech.class), "FINALTECH_CATEGORY_MAIN"), new CustomItemStack(Material.OBSERVER, "&fFinalTech科技"));
    public static final SubItemGroup MENU_MATERIAL = new SubItemGroup(new NamespacedKey(FinalTech.getPlugin(FinalTech.class), "FINALTECH_CATEGORY_MATERIAL"), MAIN_MENU , new CustomItemStack(Material.AMETHYST_SHARD, "&f材料-组件"));
    public static final SubItemGroup MENU_BASIC_MACHINE = new SubItemGroup(new NamespacedKey(FinalTech.getPlugin(FinalTech.class), "FINALTECH_CATEGORY_BASIC_MACHINE"), MAIN_MENU, new CustomItemStack(Material.PISTON, "&f基础机器"));
    public static final SubItemGroup MENU_ADVANCED_MACHINE = new SubItemGroup(new NamespacedKey(FinalTech.getPlugin(FinalTech.class), "FINALTECH_CATEGORY_ADVANCED_MACHINE"), MAIN_MENU, new CustomItemStack(Material.STICKY_PISTON, "&f高级机器"));
    public static final SubItemGroup MENU_FINAL_MACHINE = new SubItemGroup(new NamespacedKey(FinalTech.getPlugin(FinalTech.class), "FINALTECH_CATEGORY_FINAL_MACHINE"), MAIN_MENU, new CustomItemStack(Material.DIRT, "&f终极机器"));

}
