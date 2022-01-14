package io.taraxacum.finaltech.setup;

import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.implementation.SlimefunItems;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public final class FinalTechRecipes {

    // items
    public static final ItemStack[] QUANTITY_MODULE = new ItemStack[] {
            new ItemStack(Material.COMPARATOR), SlimefunItems.STEEL_THRUSTER, new ItemStack(Material.COMPARATOR),
            SlimefunItems.ANDROID_INTERFACE_FUEL, SlimefunItems.ANDROID_MEMORY_CORE, SlimefunItems.ANDROID_INTERFACE_ITEMS,
            new ItemStack(Material.NETHERITE_SCRAP), SlimefunItems.ENERGY_REGULATOR, new ItemStack(Material.NETHERITE_SCRAP)
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
            SlimefunItems.AUTO_DRIER, SlimefunItems.AUTO_DRIER, SlimefunItems.AUTO_DRIER,
            SlimefunItems.AUTO_DRIER, new ItemStack(Material.DISPENSER), SlimefunItems.AUTO_DRIER,
            SlimefunItems.AUTO_DRIER, SlimefunItems.AUTO_DRIER, SlimefunItems.AUTO_DRIER
    };

    public static final ItemStack[] ADVANCED_CARBON_PRESS = new ItemStack[] {
            SlimefunItems.CARBON_PRESS, SlimefunItems.CARBON_PRESS, SlimefunItems.CARBON_PRESS,
            SlimefunItems.CARBON_PRESS, new ItemStack(Material.DISPENSER), SlimefunItems.CARBON_PRESS,
            SlimefunItems.CARBON_PRESS, SlimefunItems.CARBON_PRESS, SlimefunItems.CARBON_PRESS
    };

    public static final ItemStack[] AVANCED_DUST_FACTORY = new ItemStack[] {
            null, null, new ItemStack(Material.BEDROCK),
            null, null, null,
            null, new ItemStack(Material.BEDROCK),
    };

    public static final ItemStack[] ADVANCED_ELECTRIC_CRUCIBLE = new ItemStack[] {
            SlimefunItems.CRUCIBLE, SlimefunItems.CRUCIBLE, SlimefunItems.CRUCIBLE,
            SlimefunItems.CRUCIBLE, new ItemStack(Material.DISPENSER), SlimefunItems.CRUCIBLE,
            SlimefunItems.CRUCIBLE, SlimefunItems.CRUCIBLE, SlimefunItems.CRUCIBLE
    };

    public static final ItemStack[] ADVANCED_ELECTRIC_DUST_WASHER = new ItemStack[] {
            SlimefunItems.ELECTRIC_DUST_WASHER, SlimefunItems.ELECTRIC_DUST_WASHER, SlimefunItems.ELECTRIC_DUST_WASHER,
            SlimefunItems.ELECTRIC_DUST_WASHER, new ItemStack(Material.DISPENSER), SlimefunItems.ELECTRIC_DUST_WASHER,
            SlimefunItems.ELECTRIC_DUST_WASHER, SlimefunItems.ELECTRIC_DUST_WASHER, SlimefunItems.ELECTRIC_DUST_WASHER
    };

    public static final ItemStack[] ADVANCED_ELECTRIC_FURANCE = new ItemStack[] {
            SlimefunItems.ELECTRIC_FURNACE, SlimefunItems.ELECTRIC_FURNACE, SlimefunItems.ELECTRIC_FURNACE,
            SlimefunItems.ELECTRIC_FURNACE, new ItemStack(Material.DISPENSER), SlimefunItems.ELECTRIC_FURNACE,
            SlimefunItems.ELECTRIC_FURNACE, SlimefunItems.ELECTRIC_FURNACE, SlimefunItems.ELECTRIC_FURNACE
    };

    public static final ItemStack[] ADVANCED_ELECTRIC_INGOT_FACTORY = new ItemStack[] {
            SlimefunItems.ELECTRIC_INGOT_FACTORY, SlimefunItems.ELECTRIC_INGOT_FACTORY, SlimefunItems.ELECTRIC_INGOT_FACTORY,
            SlimefunItems.ELECTRIC_INGOT_FACTORY, new ItemStack(Material.DISPENSER), SlimefunItems.ELECTRIC_INGOT_FACTORY,
            SlimefunItems.ELECTRIC_INGOT_FACTORY, SlimefunItems.ELECTRIC_INGOT_FACTORY, SlimefunItems.ELECTRIC_INGOT_FACTORY
    };

    public static final ItemStack[] ADVANCED_ELECTRIC_INGOT_PULVERIZER = new ItemStack[] {
            SlimefunItems.ELECTRIC_INGOT_PULVERIZER, SlimefunItems.ELECTRIC_INGOT_PULVERIZER, SlimefunItems.ELECTRIC_INGOT_PULVERIZER,
            SlimefunItems.ELECTRIC_INGOT_PULVERIZER, new ItemStack(Material.DISPENSER), SlimefunItems.ELECTRIC_INGOT_PULVERIZER,
            SlimefunItems.ELECTRIC_INGOT_PULVERIZER, SlimefunItems.ELECTRIC_INGOT_PULVERIZER, SlimefunItems.ELECTRIC_INGOT_PULVERIZER
    };

    public static final ItemStack[] ADVANCED_ELECTRIC_ORE_GRINDER = new ItemStack[] {
            SlimefunItems.ELECTRIC_ORE_GRINDER, SlimefunItems.ELECTRIC_ORE_GRINDER, SlimefunItems.ELECTRIC_ORE_GRINDER,
            SlimefunItems.ELECTRIC_ORE_GRINDER, new ItemStack(Material.DISPENSER), SlimefunItems.ELECTRIC_ORE_GRINDER,
            SlimefunItems.ELECTRIC_ORE_GRINDER, SlimefunItems.ELECTRIC_ORE_GRINDER, SlimefunItems.ELECTRIC_ORE_GRINDER
    };

    public static final ItemStack[] ADVANCED_ELECTRIC_PRESS = new ItemStack[] {
            SlimefunItems.ELECTRIC_PRESS, SlimefunItems.ELECTRIC_PRESS, SlimefunItems.ELECTRIC_PRESS,
            SlimefunItems.ELECTRIC_PRESS, new ItemStack(Material.DISPENSER), SlimefunItems.ELECTRIC_PRESS,
            SlimefunItems.ELECTRIC_PRESS, SlimefunItems.ELECTRIC_PRESS, SlimefunItems.ELECTRIC_PRESS
    };

    public static final ItemStack[] ADVANCED_ELECTRIC_SMELTERY = new ItemStack[] {
            SlimefunItems.ELECTRIC_SMELTERY, SlimefunItems.ELECTRIC_SMELTERY, SlimefunItems.ELECTRIC_SMELTERY,
            SlimefunItems.ELECTRIC_SMELTERY, new ItemStack(Material.DISPENSER), SlimefunItems.ELECTRIC_SMELTERY,
            SlimefunItems.ELECTRIC_SMELTERY, SlimefunItems.ELECTRIC_SMELTERY, SlimefunItems.ELECTRIC_SMELTERY
    };

    public static final ItemStack[] ADVANCED_FOODFACTORY = new ItemStack[] {
            SlimefunItems.FOOD_COMPOSTER, SlimefunItems.FOOD_FABRICATOR, SlimefunItems.FOOD_COMPOSTER,
            SlimefunItems.FOOD_FABRICATOR, new ItemStack(Material.DISPENSER), SlimefunItems.FOOD_FABRICATOR,
            SlimefunItems.FOOD_COMPOSTER, SlimefunItems.FOOD_FABRICATOR, SlimefunItems.FOOD_COMPOSTER
    };

    public static final ItemStack[] ADVANCED_FREEZER = new ItemStack[] {
            SlimefunItems.FREEZER, SlimefunItems.FREEZER, SlimefunItems.FREEZER,
            SlimefunItems.FREEZER, new ItemStack(Material.DISPENSER), SlimefunItems.FREEZER,
            SlimefunItems.FREEZER, SlimefunItems.FREEZER, SlimefunItems.FREEZER
    };

    public static final ItemStack[] ADVANCED_GOLD_PAN = new ItemStack[] {
            SlimefunItems.ELECTRIC_GOLD_PAN, SlimefunItems.ELECTRIC_GOLD_PAN, SlimefunItems.ELECTRIC_GOLD_PAN,
            SlimefunItems.ELECTRIC_GOLD_PAN, new ItemStack(Material.DISPENSER), SlimefunItems.ELECTRIC_GOLD_PAN,
            SlimefunItems.ELECTRIC_GOLD_PAN, SlimefunItems.ELECTRIC_GOLD_PAN, SlimefunItems.ELECTRIC_GOLD_PAN
    };

    public static final ItemStack[] ADVANCED_HEATED_PRESSURE_CHAMBER = new ItemStack[] {
            SlimefunItems.HEATED_PRESSURE_CHAMBER, SlimefunItems.HEATED_PRESSURE_CHAMBER, SlimefunItems.HEATED_PRESSURE_CHAMBER,
            SlimefunItems.HEATED_PRESSURE_CHAMBER, new ItemStack(Material.DISPENSER), SlimefunItems.HEATED_PRESSURE_CHAMBER,
            SlimefunItems.HEATED_PRESSURE_CHAMBER, SlimefunItems.HEATED_PRESSURE_CHAMBER, SlimefunItems.HEATED_PRESSURE_CHAMBER
    };

    public static final ItemStack[] ORDERED_DUST_FACTORY = new ItemStack[] {
            SlimefunItems.TRASH_CAN, SlimefunItems.TRASH_CAN, SlimefunItems.TRASH_CAN,
            SlimefunItems.TRASH_CAN, new ItemStack(Material.DISPENSER), SlimefunItems.TRASH_CAN,
            SlimefunItems.TRASH_CAN, SlimefunItems.TRASH_CAN, SlimefunItems.TRASH_CAN
    };
}
