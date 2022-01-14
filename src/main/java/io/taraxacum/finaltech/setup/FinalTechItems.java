package io.taraxacum.finaltech.setup;

import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.implementation.SlimefunItems;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public final class FinalTechItems {

    // items
    public static final SlimefunItemStack QUANTITY_MODULE = new SlimefunItemStack("FINALTECH_QUANTITY_MODULE", Material.AMETHYST_SHARD, "&3数量组件");

    // basic machines
    public static final SlimefunItemStack BASIC_COBBLE_FACTORY = new SlimefunItemStack("FINALTECH_BASIC_COBBLE_FACTORY", Material.CHISELED_STONE_BRICKS, "&7基础刷石工厂");
    public static final SlimefunItemStack BASIC_ORE_FACTORY = new SlimefunItemStack("FINALTECH_BASIC_ORE_FACTORY", Material.CHISELED_DEEPSLATE, "&7基础矿石工厂");
    public static final SlimefunItemStack BASIC_DUST_FACTORY = new SlimefunItemStack("FINALTECH_BASIC_DUST_FACTORY", Material.POLISHED_DEEPSLATE, "&7基础矿粉工厂");
    public static final SlimefunItemStack BASIC_FARM_FACTORY = new SlimefunItemStack("FINALTECH_BASIC_FARM_FACTORY", Material.MOSS_BLOCK, "&7基础作物工厂");

    // advanced machines
    public static final SlimefunItemStack ADVANCED_AUTO_DRIER = new SlimefunItemStack("FINALTECH_ADVANCED_AUTO_DRIER", Material.SMOKER, "&6高级烘干机");
    public static final SlimefunItemStack ADVANCED_CARBON_PRESS = new SlimefunItemStack("FINALTECH_ADVANCED_CARBON_PRESS", Material.BLACK_STAINED_GLASS, "&7高级碳压机");
    public static final SlimefunItemStack ADVANCED_ELECTRIC_FURANCE = new SlimefunItemStack("FINALTECH_ADVANCED_ELECTRIC_FURANCE", Material.FURNACE, "&c高级电炉");
    public static final SlimefunItemStack ADVANCED_ELECTRIC_INGOT_FACTORY = new SlimefunItemStack("FINALTECH_ADVANCED_ELECTRIC_INGOT_FACTORY", Material.RED_TERRACOTTA, "&c高级铸锭机");
    public static final SlimefunItemStack ADVANCED_ELECTRIC_INGOT_PULVERIZER = new SlimefunItemStack("FINALTECH_ADVANCED_ELECTRIC_INGOT_PULVERIZER", Material.FURNACE, "&c高级打粉机");
    public static final SlimefunItemStack ADVANCED_ELECTRIC_ORE_GRINDER = new SlimefunItemStack("FINALTECH_ADVANCED_ELECTRIC_ORE_GRINDER", Material.FURNACE, "&c高级碎矿机");
    public static final SlimefunItemStack ADVANCED_GOLD_PAN = new SlimefunItemStack("FINALTECH_ADVANCED_GOLD_PAN", Material.BROWN_TERRACOTTA, "&6高级淘金机");



}

