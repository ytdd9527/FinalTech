package io.taraxacum.finaltech.setup;

import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.implementation.SlimefunItems;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public final class FinalTechRecipes {

    // items
    public static final ItemStack[] QUANTITY_MODULE = new ItemStack[] {
            SlimefunItems.ADVANCED_CIRCUIT_BOARD, new ItemStack(Material.AMETHYST_BLOCK), SlimefunItems.ADVANCED_CIRCUIT_BOARD,
            SlimefunItems.ANDROID_INTERFACE_FUEL, SlimefunItems.ANDROID_MEMORY_CORE, SlimefunItems.ANDROID_INTERFACE_ITEMS,
            FinalTechItems.UNORDERED_DUST, SlimefunItems.STEEL_THRUSTER, FinalTechItems.UNORDERED_DUST
    };

    // basic machines
    public static final ItemStack[] BASIC_COBBLE_FACTORY = new ItemStack[] {
            new ItemStack(Material.OBSIDIAN), new ItemStack(Material.LAVA_BUCKET), new ItemStack(Material.OBSIDIAN),
            new ItemStack(Material.WATER_BUCKET), new ItemStack(Material.REDSTONE), new ItemStack(Material.WATER_BUCKET),
            SlimefunItems.COBALT_PICKAXE, new ItemStack(Material.PISTON), SlimefunItems.COBALT_PICKAXE
    };

    public static final ItemStack[] BASIC_ORE_FACTORY = new ItemStack[] {
            new ItemStack(Material.PISTON), SlimefunItems.LAVA_CRYSTAL, new ItemStack(Material.PISTON),
            SlimefunItems.EARTH_RUNE, FinalTechItems.BASIC_COBBLE_FACTORY, SlimefunItems.EARTH_RUNE,
            SlimefunItems.ENHANCED_FURNACE, SlimefunItems.URANIUM, SlimefunItems.ENHANCED_FURNACE
    };

    public static final ItemStack[] BASIC_DUST_FACTORY = new ItemStack[] {
            SlimefunItems.ELECTRIC_ORE_GRINDER, new ItemStack(Material.OBSERVER), SlimefunItems.ELECTRIC_ORE_GRINDER,
            SlimefunItems.ELECTRIC_GOLD_PAN, FinalTechItems.BASIC_COBBLE_FACTORY, SlimefunItems.ELECTRIC_GOLD_PAN,
            SlimefunItems.ELECTRIC_DUST_WASHER, new ItemStack(Material.DROPPER), SlimefunItems.ELECTRIC_DUST_WASHER
    };

    public static final ItemStack[] BASIC_FARM_FACTORY = new ItemStack[] {
            SlimefunItems.FOOD_FABRICATOR, new ItemStack(Material.OBSERVER), SlimefunItems.FOOD_FABRICATOR,
            SlimefunItems.FOOD_COMPOSTER, FinalTechItems.BASIC_COBBLE_FACTORY, SlimefunItems.FOOD_COMPOSTER,
            SlimefunItems.CROP_GROWTH_ACCELERATOR, new ItemStack(Material.DROPPER), SlimefunItems.CROP_GROWTH_ACCELERATOR
    };

    // advanced machines
    public static final ItemStack[] ADVANCED_AUTO_DRIER = new ItemStack[] {
            FinalTechItems.UNORDERED_DUST, SlimefunItems.AUTO_DRIER, FinalTechItems.UNORDERED_DUST,
            SlimefunItems.AUTO_DRIER, FinalTechItems.BASIC_FRAME_MACHINE, SlimefunItems.AUTO_DRIER,
            FinalTechItems.UNORDERED_DUST, SlimefunItems.AUTO_DRIER, FinalTechItems.UNORDERED_DUST
    };

    public static final ItemStack[] ADVANCED_CARBON_PRESS = new ItemStack[] {
            FinalTechItems.UNORDERED_DUST, SlimefunItems.CARBON_PRESS, FinalTechItems.UNORDERED_DUST,
            SlimefunItems.CARBON_PRESS, FinalTechItems.BASIC_FRAME_MACHINE, SlimefunItems.CARBON_PRESS,
            FinalTechItems.UNORDERED_DUST, SlimefunItems.CARBON_PRESS, FinalTechItems.UNORDERED_DUST
    };

    public static final ItemStack[] ADVANCED_DUST_FACTORY = new ItemStack[] {
            FinalTechItems.UNORDERED_DUST, FinalTechItems.BASIC_DUST_FACTORY, FinalTechItems.UNORDERED_DUST,
            FinalTechItems.BASIC_DUST_FACTORY, FinalTechItems.BASIC_FRAME_MACHINE, FinalTechItems.BASIC_DUST_FACTORY,
            FinalTechItems.UNORDERED_DUST, FinalTechItems.BASIC_DUST_FACTORY, FinalTechItems.UNORDERED_DUST
    };

    public static final ItemStack[] ADVANCED_ELECTRIC_CRUCIBLE = new ItemStack[] {
            FinalTechItems.UNORDERED_DUST, SlimefunItems.ELECTRIFIED_CRUCIBLE, FinalTechItems.UNORDERED_DUST,
            SlimefunItems.ELECTRIFIED_CRUCIBLE, FinalTechItems.BASIC_FRAME_MACHINE, SlimefunItems.ELECTRIFIED_CRUCIBLE,
            FinalTechItems.UNORDERED_DUST, SlimefunItems.ELECTRIFIED_CRUCIBLE, FinalTechItems.UNORDERED_DUST
    };

    public static final ItemStack[] ADVANCED_ELECTRIC_DUST_WASHER = new ItemStack[] {
            FinalTechItems.UNORDERED_DUST, SlimefunItems.ELECTRIC_DUST_WASHER, FinalTechItems.UNORDERED_DUST,
            SlimefunItems.ELECTRIC_DUST_WASHER, FinalTechItems.BASIC_FRAME_MACHINE, SlimefunItems.ELECTRIC_DUST_WASHER,
            FinalTechItems.UNORDERED_DUST, SlimefunItems.ELECTRIC_DUST_WASHER, FinalTechItems.UNORDERED_DUST
    };

    public static final ItemStack[] ADVANCED_ELECTRIC_FURANCE = new ItemStack[] {
            FinalTechItems.UNORDERED_DUST, SlimefunItems.ELECTRIC_FURNACE, FinalTechItems.UNORDERED_DUST,
            SlimefunItems.ELECTRIC_FURNACE, FinalTechItems.BASIC_FRAME_MACHINE, SlimefunItems.ELECTRIC_FURNACE,
            FinalTechItems.UNORDERED_DUST, SlimefunItems.ELECTRIC_FURNACE, FinalTechItems.UNORDERED_DUST
    };

    public static final ItemStack[] ADVANCED_ELECTRIC_INGOT_FACTORY = new ItemStack[] {
            FinalTechItems.UNORDERED_DUST, SlimefunItems.ELECTRIC_INGOT_FACTORY, FinalTechItems.UNORDERED_DUST,
            SlimefunItems.ELECTRIC_INGOT_FACTORY, FinalTechItems.BASIC_FRAME_MACHINE, SlimefunItems.ELECTRIC_INGOT_FACTORY,
            FinalTechItems.UNORDERED_DUST, SlimefunItems.ELECTRIC_INGOT_FACTORY, FinalTechItems.UNORDERED_DUST
    };

    public static final ItemStack[] ADVANCED_ELECTRIC_INGOT_PULVERIZER = new ItemStack[] {
            FinalTechItems.UNORDERED_DUST, SlimefunItems.ELECTRIC_INGOT_PULVERIZER, FinalTechItems.UNORDERED_DUST,
            SlimefunItems.ELECTRIC_INGOT_PULVERIZER, FinalTechItems.BASIC_FRAME_MACHINE, SlimefunItems.ELECTRIC_INGOT_PULVERIZER,
            FinalTechItems.UNORDERED_DUST, SlimefunItems.ELECTRIC_INGOT_PULVERIZER, FinalTechItems.UNORDERED_DUST
    };

    public static final ItemStack[] ADVANCED_ELECTRIC_ORE_GRINDER = new ItemStack[] {
            FinalTechItems.UNORDERED_DUST, SlimefunItems.ELECTRIC_ORE_GRINDER, FinalTechItems.UNORDERED_DUST,
            SlimefunItems.ELECTRIC_ORE_GRINDER, FinalTechItems.BASIC_FRAME_MACHINE, SlimefunItems.ELECTRIC_ORE_GRINDER,
            FinalTechItems.UNORDERED_DUST, SlimefunItems.ELECTRIC_ORE_GRINDER, FinalTechItems.UNORDERED_DUST
    };

    public static final ItemStack[] ADVANCED_ELECTRIC_PRESS = new ItemStack[] {
            FinalTechItems.UNORDERED_DUST, SlimefunItems.ELECTRIC_PRESS, FinalTechItems.UNORDERED_DUST,
            SlimefunItems.ELECTRIC_PRESS, FinalTechItems.BASIC_FRAME_MACHINE, SlimefunItems.ELECTRIC_PRESS,
            FinalTechItems.UNORDERED_DUST, SlimefunItems.ELECTRIC_PRESS, FinalTechItems.UNORDERED_DUST
    };

    public static final ItemStack[] ADVANCED_ELECTRIC_SMELTERY = new ItemStack[] {
            FinalTechItems.UNORDERED_DUST, SlimefunItems.ELECTRIC_SMELTERY, FinalTechItems.UNORDERED_DUST,
            SlimefunItems.ELECTRIC_SMELTERY, FinalTechItems.BASIC_FRAME_MACHINE, SlimefunItems.ELECTRIC_SMELTERY,
            FinalTechItems.UNORDERED_DUST, SlimefunItems.ELECTRIC_SMELTERY, FinalTechItems.UNORDERED_DUST
    };

    public static final ItemStack[] ADVANCED_FARM_FACTORY = new ItemStack[] {
            FinalTechItems.UNORDERED_DUST, FinalTechItems.BASIC_FARM_FACTORY, FinalTechItems.UNORDERED_DUST,
            FinalTechItems.BASIC_FARM_FACTORY, FinalTechItems.BASIC_FRAME_MACHINE, FinalTechItems.BASIC_FARM_FACTORY,
            FinalTechItems.UNORDERED_DUST, FinalTechItems.BASIC_FARM_FACTORY, FinalTechItems.UNORDERED_DUST
    };

    public static final ItemStack[] ADVANCED_FOOD_FACTORY = new ItemStack[] {
            FinalTechItems.UNORDERED_DUST, SlimefunItems.FOOD_FABRICATOR, FinalTechItems.UNORDERED_DUST,
            SlimefunItems.FOOD_COMPOSTER, FinalTechItems.BASIC_FRAME_MACHINE, SlimefunItems.FOOD_COMPOSTER,
            FinalTechItems.UNORDERED_DUST, SlimefunItems.FOOD_FABRICATOR, FinalTechItems.UNORDERED_DUST
    };

    public static final ItemStack[] ADVANCED_FREEZER = new ItemStack[] {
            FinalTechItems.UNORDERED_DUST, SlimefunItems.FREEZER, FinalTechItems.UNORDERED_DUST,
            SlimefunItems.FREEZER, FinalTechItems.BASIC_FRAME_MACHINE, SlimefunItems.FREEZER,
            FinalTechItems.UNORDERED_DUST, SlimefunItems.FREEZER, FinalTechItems.UNORDERED_DUST
    };

    public static final ItemStack[] ADVANCED_GOLD_PAN = new ItemStack[] {
            FinalTechItems.UNORDERED_DUST, SlimefunItems.ELECTRIC_GOLD_PAN, FinalTechItems.UNORDERED_DUST,
            SlimefunItems.ELECTRIC_GOLD_PAN, FinalTechItems.BASIC_FRAME_MACHINE, SlimefunItems.ELECTRIC_GOLD_PAN,
            FinalTechItems.UNORDERED_DUST, SlimefunItems.ELECTRIC_GOLD_PAN, FinalTechItems.UNORDERED_DUST
    };

    public static final ItemStack[] ADVANCED_HEATED_PRESSURE_CHAMBER = new ItemStack[] {
            FinalTechItems.UNORDERED_DUST, SlimefunItems.HEATED_PRESSURE_CHAMBER, FinalTechItems.UNORDERED_DUST,
            SlimefunItems.HEATED_PRESSURE_CHAMBER, FinalTechItems.BASIC_FRAME_MACHINE, SlimefunItems.HEATED_PRESSURE_CHAMBER,
            FinalTechItems.UNORDERED_DUST, SlimefunItems.HEATED_PRESSURE_CHAMBER, FinalTechItems.UNORDERED_DUST
    };

    public static final ItemStack[] UNORDERED_DUST_FACTORY = new ItemStack[] {
            SlimefunItems.TRASH_CAN, SlimefunItems.TRASH_CAN, SlimefunItems.TRASH_CAN,
            SlimefunItems.TRASH_CAN, new ItemStack(Material.DIRT), SlimefunItems.TRASH_CAN,
            SlimefunItems.TRASH_CAN, SlimefunItems.TRASH_CAN, SlimefunItems.TRASH_CAN
    };

    // final machines
    public static final ItemStack[] ALL_COMPRESSION = new ItemStack[] {
            FinalTechItems.UNORDERED_DUST, FinalTechItems.UNORDERED_DUST, FinalTechItems.UNORDERED_DUST,
            FinalTechItems.UNORDERED_DUST, SlimefunItems.TRASH_CAN, FinalTechItems.UNORDERED_DUST,
            FinalTechItems.UNORDERED_DUST, FinalTechItems.UNORDERED_DUST, FinalTechItems.UNORDERED_DUST
    };

    public static final ItemStack[] ALL_FACTORY = new ItemStack[] {
            FinalTechItems.UNORDERED_DUST, FinalTechItems.UNORDERED_DUST, FinalTechItems.UNORDERED_DUST,
            FinalTechItems.UNORDERED_DUST, FinalTechItems.ALL_FACTORY, FinalTechItems.UNORDERED_DUST,
            FinalTechItems.UNORDERED_DUST, FinalTechItems.UNORDERED_DUST, FinalTechItems.UNORDERED_DUST
    };

    // cargos
    public static final ItemStack[] PIPE = new ItemStack[] {
            new ItemStack(Material.HOPPER), new ItemStack(Material.CHAIN), new ItemStack(Material.HOPPER),
            new ItemStack(Material.CHAIN), new ItemStack(Material.ENDER_PEARL), new ItemStack(Material.CHAIN),
            new ItemStack(Material.HOPPER), new ItemStack(Material.CHAIN), new ItemStack(Material.HOPPER)
    };

    public static final ItemStack[] PIPE_GROUP = new ItemStack[] {
            FinalTechItems.PIPE, FinalTechItems.PIPE, FinalTechItems.PIPE,
            FinalTechItems.PIPE, null, FinalTechItems.PIPE,
            FinalTechItems.PIPE, FinalTechItems.PIPE, FinalTechItems.PIPE
    };

    public static final ItemStack[] BASIC_FRAME_MACHINE = new ItemStack[] {
            new ItemStack(Material.STONE), new ItemStack(Material.CHAIN), new ItemStack(Material.STONE),
            new ItemStack(Material.CHAIN), new ItemStack(Material.GLASS), new ItemStack(Material.CHAIN),
            new ItemStack(Material.STONE), new ItemStack(Material.CHAIN), new ItemStack(Material.STONE)
    };

    public static final ItemStack[] DOUBLE_NORMAL_BARREL = new ItemStack[] {
            new ItemStack(Material.GLASS), new ItemStack(Material.CHEST), new ItemStack(Material.GLASS),
            new ItemStack(Material.CHEST), null, new ItemStack(Material.CHEST),
            new ItemStack(Material.GLASS), new ItemStack(Material.CHEST), new ItemStack(Material.GLASS),
    };

    public static final ItemStack[] DOUBLE_LINKED_BARREL = new ItemStack[] {
            new ItemStack(Material.GLASS), new ItemStack(Material.CHEST), new ItemStack(Material.GLASS),
            new ItemStack(Material.CHEST), new ItemStack(Material.ENDER_EYE), new ItemStack(Material.CHEST),
            new ItemStack(Material.GLASS), new ItemStack(Material.CHEST), new ItemStack(Material.GLASS),
    };
}
