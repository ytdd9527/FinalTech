package io.taraxacum.finaltech.setup.register;

import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.implementation.SlimefunItems;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

/**
 * @author Final_ROOT
 */
public final class FinalTechRecipes {

    // RecipesType
    public static final RecipeType RECIPE_TYPE_ORDERED_DUST_FACTORY = new RecipeType(FinalTechItems.ORDERED_DUST_FACTORY, "FINALTECH_UNORDERED_DUST_FACTORY");
    public static final RecipeType RECIPE_TYPE_ALL_COMPRESSION = new RecipeType(FinalTechItems.ALL_COMPRESSION, "FINALTECH_ALL_COMPRESSION");

    // items
    public static final ItemStack[] GEARWHEEL = new ItemStack[] {
            new ItemStack(Material.GRANITE), new ItemStack(Material.GRANITE), new ItemStack(Material.GRANITE),
            new ItemStack(Material.DIORITE), new ItemStack(Material.DIORITE), new ItemStack(Material.DIORITE),
            new ItemStack(Material.ANDESITE), new ItemStack(Material.ANDESITE), new ItemStack(Material.ANDESITE)
    };
    public static final ItemStack[] QUANTITY_MODULE = new ItemStack[] {
            SlimefunItems.FILLED_FLASK_OF_KNOWLEDGE, FinalTechItems.ORDERED_DUST, SlimefunItems.FILLED_FLASK_OF_KNOWLEDGE,
            SlimefunItems.ANDROID_INTERFACE_FUEL, SlimefunItems.ANDROID_MEMORY_CORE, SlimefunItems.ANDROID_INTERFACE_ITEMS,
            new ItemStack(Material.AMETHYST_BLOCK), SlimefunItems.STEEL_THRUSTER, new ItemStack(Material.SNOW_BLOCK)
    };
    public static final ItemStack[] QUANTITY_MODULE_V2 = new ItemStack[] {
            FinalTechItems.QUANTITY_MODULE, FinalTechItems.QUANTITY_MODULE, FinalTechItems.QUANTITY_MODULE,
            FinalTechItems.QUANTITY_MODULE, FinalTechItems.ORDERED_DUST, FinalTechItems.QUANTITY_MODULE,
            FinalTechItems.QUANTITY_MODULE, FinalTechItems.QUANTITY_MODULE, FinalTechItems.QUANTITY_MODULE
    };
    public static final ItemStack[] FAKE = new ItemStack[] {
            FinalTechItems.SINGULARITY, FinalTechItems.SPIROCHETE, null,
            null, null, null,
            null, null, null
    };

    // basic machines
    public static final ItemStack[] BASIC_COBBLE_FACTORY = new ItemStack[] {
            new ItemStack(Material.OBSIDIAN), new ItemStack(Material.LAVA_BUCKET), new ItemStack(Material.OBSIDIAN),
            new ItemStack(Material.WATER_BUCKET), FinalTechItems.GEARWHEEL, new ItemStack(Material.WATER_BUCKET),
            SlimefunItems.COBALT_PICKAXE, new ItemStack(Material.PISTON), SlimefunItems.COBALT_PICKAXE
    };

    public static final ItemStack[] BASIC_ORE_FACTORY = new ItemStack[] {
            SlimefunItems.URANIUM, new ItemStack(Material.PISTON), SlimefunItems.URANIUM,
            SlimefunItems.EARTH_RUNE, FinalTechItems.BASIC_COBBLE_FACTORY, SlimefunItems.EARTH_RUNE,
            SlimefunItems.ENHANCED_FURNACE, new ItemStack(Material.DROPPER), SlimefunItems.ENHANCED_FURNACE
    };

    public static final ItemStack[] BASIC_DUST_FACTORY = new ItemStack[] {
            SlimefunItems.ELECTRIC_ORE_GRINDER, new ItemStack(Material.PISTON), SlimefunItems.ELECTRIC_ORE_GRINDER,
            SlimefunItems.ELECTRIC_GOLD_PAN, FinalTechItems.BASIC_COBBLE_FACTORY, SlimefunItems.ELECTRIC_GOLD_PAN,
            SlimefunItems.ELECTRIC_DUST_WASHER, new ItemStack(Material.DROPPER), SlimefunItems.ELECTRIC_DUST_WASHER
    };

    public static final ItemStack[] BASIC_FARM_FACTORY = new ItemStack[] {
            SlimefunItems.FOOD_FABRICATOR, new ItemStack(Material.PISTON), SlimefunItems.FOOD_FABRICATOR,
            SlimefunItems.FOOD_COMPOSTER, FinalTechItems.BASIC_COBBLE_FACTORY, SlimefunItems.FOOD_COMPOSTER,
            SlimefunItems.CROP_GROWTH_ACCELERATOR, new ItemStack(Material.DROPPER), SlimefunItems.CROP_GROWTH_ACCELERATOR
    };

    public static final ItemStack[] MANUAL_ENHANCED_CRAFTING_TABLE = new ItemStack[] {
            null, new ItemStack(Material.CRAFTING_TABLE), null,
            null, new ItemStack(Material.DISPENSER), null,
            null, FinalTechItems.GEARWHEEL, null
    };

    public static final ItemStack[] MANUAL_GRIND_STONE = new ItemStack[] {
            null, new ItemStack(Material.OAK_FENCE), null,
            null, new ItemStack(Material.DISPENSER), null,
            null, FinalTechItems.GEARWHEEL, null
    };

    public static final ItemStack[] MANUAL_ARMOR_FORGE = new ItemStack[] {
            null, new ItemStack(Material.ANVIL), null,
            null, new ItemStack(Material.DISPENSER), null,
            null, FinalTechItems.GEARWHEEL, null
    };

    public static final ItemStack[] MANUAL_ORE_CRUSHER = new ItemStack[] {
            null, new ItemStack(Material.NETHER_BRICK_FENCE), null,
            new ItemStack(Material.IRON_BARS), new ItemStack(Material.DISPENSER), new ItemStack(Material.IRON_BARS),
            null, FinalTechItems.GEARWHEEL, null
    };

    public static final ItemStack[] MANUAL_COMPRESSOR = new ItemStack[] {
            null, new ItemStack(Material.NETHER_BRICK_FENCE), null,
            new ItemStack(Material.PISTON), new ItemStack(Material.DISPENSER), new ItemStack(Material.PISTON),
            null, FinalTechItems.GEARWHEEL, null
    };

    public static final ItemStack[] MANUAL_SMELTERY = new ItemStack[] {
            FinalTechItems.GEARWHEEL, new ItemStack(Material.NETHER_BRICK_FENCE), FinalTechItems.GEARWHEEL,
            new ItemStack(Material.NETHER_BRICK), new ItemStack(Material.DISPENSER), new ItemStack(Material.NETHER_BRICK),
            new ItemStack(Material.FLINT_AND_STEEL), SlimefunItems.IGNITION_CHAMBER, new ItemStack(Material.FLINT_AND_STEEL)
    };

    public static final ItemStack[] MANUAL_PRESSURE_CHAMBER = new ItemStack[] {
            FinalTechItems.GEARWHEEL, new ItemStack(Material.DISPENSER), FinalTechItems.GEARWHEEL,
            new ItemStack(Material.PISTON), new ItemStack(Material.GLASS), new ItemStack(Material.PISTON),
            new ItemStack(Material.PISTON), new ItemStack(Material.CAULDRON), new ItemStack(Material.PISTON)
    };

    public static final ItemStack[] MANUAL_MAGIC_WORKBENCH = new ItemStack[] {
            FinalTechItems.GEARWHEEL, null, FinalTechItems.GEARWHEEL,
            new ItemStack(Material.BOOKSHELF), new ItemStack(Material.CRAFTING_TABLE), new ItemStack(Material.DISPENSER),
            FinalTechItems.GEARWHEEL, null, FinalTechItems.GEARWHEEL
    };

    public static final ItemStack[] MANUAL_JUICER = new ItemStack[] {
            null, new ItemStack(Material.GLASS), null,
            FinalTechItems.GEARWHEEL, new ItemStack(Material.NETHER_BRICK_FENCE), FinalTechItems.GEARWHEEL,
            null, new ItemStack(Material.DISPENSER), null
    };

    public static final ItemStack[] MANUAL_ANCIENT_ALTAR = new ItemStack[] {
            SlimefunItems.ANCIENT_PEDESTAL, FinalTechItems.GEARWHEEL, SlimefunItems.ANCIENT_PEDESTAL,
            FinalTechItems.GEARWHEEL, SlimefunItems.ANCIENT_ALTAR, FinalTechItems.GEARWHEEL,
            SlimefunItems.ANCIENT_PEDESTAL, FinalTechItems.GEARWHEEL, SlimefunItems.ANCIENT_PEDESTAL
    };

    public static final ItemStack[] MANUAL_HEATED_PRESSURE_CHAMBER = new ItemStack[] {
            FinalTechItems.GEARWHEEL, FinalTechItems.GEARWHEEL, FinalTechItems.GEARWHEEL,
            FinalTechItems.GEARWHEEL, SlimefunItems.HEATED_PRESSURE_CHAMBER, FinalTechItems.GEARWHEEL,
            FinalTechItems.GEARWHEEL, FinalTechItems.GEARWHEEL, FinalTechItems.GEARWHEEL
    };

    public static final ItemStack[] ORDERED_DUST_FACTORY = new ItemStack[] {
            SlimefunItems.TRASH_CAN, SlimefunItems.TRASH_CAN, SlimefunItems.TRASH_CAN,
            SlimefunItems.TRASH_CAN, new ItemStack(Material.DIRT), SlimefunItems.TRASH_CAN,
            SlimefunItems.TRASH_CAN, SlimefunItems.TRASH_CAN, SlimefunItems.TRASH_CAN
    };

    // advanced machines
    public static final ItemStack[] ADVANCED_ELECTRIC_FURANCE = new ItemStack[] {
            FinalTechItems.ORDERED_DUST, SlimefunItems.ELECTRIC_FURNACE, FinalTechItems.ORDERED_DUST,
            SlimefunItems.ELECTRIC_FURNACE, FinalTechItems.BASIC_FRAME_MACHINE, SlimefunItems.ELECTRIC_FURNACE,
            FinalTechItems.ORDERED_DUST, SlimefunItems.ELECTRIC_FURNACE, FinalTechItems.ORDERED_DUST
    };

    public static final ItemStack[] ADVANCED_GOLD_PAN = new ItemStack[] {
            FinalTechItems.ORDERED_DUST, SlimefunItems.ELECTRIC_GOLD_PAN, FinalTechItems.ORDERED_DUST,
            SlimefunItems.ELECTRIC_GOLD_PAN, FinalTechItems.BASIC_FRAME_MACHINE, SlimefunItems.ELECTRIC_GOLD_PAN,
            FinalTechItems.ORDERED_DUST, SlimefunItems.ELECTRIC_GOLD_PAN, FinalTechItems.ORDERED_DUST
    };

    public static final ItemStack[] ADVANCED_ELECTRIC_DUST_WASHER = new ItemStack[] {
            FinalTechItems.ORDERED_DUST, SlimefunItems.ELECTRIC_DUST_WASHER, FinalTechItems.ORDERED_DUST,
            SlimefunItems.ELECTRIC_DUST_WASHER, FinalTechItems.BASIC_FRAME_MACHINE, SlimefunItems.ELECTRIC_DUST_WASHER,
            FinalTechItems.ORDERED_DUST, SlimefunItems.ELECTRIC_DUST_WASHER, FinalTechItems.ORDERED_DUST
    };

    public static final ItemStack[] ADVANCED_ELECTRIC_INGOT_FACTORY = new ItemStack[] {
            FinalTechItems.ORDERED_DUST, SlimefunItems.ELECTRIC_INGOT_FACTORY, FinalTechItems.ORDERED_DUST,
            SlimefunItems.ELECTRIC_INGOT_FACTORY, FinalTechItems.BASIC_FRAME_MACHINE, SlimefunItems.ELECTRIC_INGOT_FACTORY,
            FinalTechItems.ORDERED_DUST, SlimefunItems.ELECTRIC_INGOT_FACTORY, FinalTechItems.ORDERED_DUST
    };

    public static final ItemStack[] ADVANCED_ELECTRIC_CRUCIBLE = new ItemStack[] {
            FinalTechItems.ORDERED_DUST, SlimefunItems.ELECTRIFIED_CRUCIBLE, FinalTechItems.ORDERED_DUST,
            SlimefunItems.ELECTRIFIED_CRUCIBLE, FinalTechItems.BASIC_FRAME_MACHINE, SlimefunItems.ELECTRIFIED_CRUCIBLE,
            FinalTechItems.ORDERED_DUST, SlimefunItems.ELECTRIFIED_CRUCIBLE, FinalTechItems.ORDERED_DUST
    };

    public static final ItemStack[] ADVANCED_ELECTRIC_ORE_GRINDER = new ItemStack[] {
            FinalTechItems.ORDERED_DUST, SlimefunItems.ELECTRIC_ORE_GRINDER, FinalTechItems.ORDERED_DUST,
            SlimefunItems.ELECTRIC_ORE_GRINDER, FinalTechItems.BASIC_FRAME_MACHINE, SlimefunItems.ELECTRIC_ORE_GRINDER,
            FinalTechItems.ORDERED_DUST, SlimefunItems.ELECTRIC_ORE_GRINDER, FinalTechItems.ORDERED_DUST
    };

    public static final ItemStack[] ADVANCED_HEATED_PRESSURE_CHAMBER = new ItemStack[] {
            FinalTechItems.ORDERED_DUST, SlimefunItems.HEATED_PRESSURE_CHAMBER, FinalTechItems.ORDERED_DUST,
            SlimefunItems.HEATED_PRESSURE_CHAMBER, FinalTechItems.BASIC_FRAME_MACHINE, SlimefunItems.HEATED_PRESSURE_CHAMBER,
            FinalTechItems.ORDERED_DUST, SlimefunItems.HEATED_PRESSURE_CHAMBER, FinalTechItems.ORDERED_DUST
    };

    public static final ItemStack[] ADVANCED_ELECTRIC_INGOT_PULVERIZER = new ItemStack[] {
            FinalTechItems.ORDERED_DUST, SlimefunItems.ELECTRIC_INGOT_PULVERIZER, FinalTechItems.ORDERED_DUST,
            SlimefunItems.ELECTRIC_INGOT_PULVERIZER, FinalTechItems.BASIC_FRAME_MACHINE, SlimefunItems.ELECTRIC_INGOT_PULVERIZER,
            FinalTechItems.ORDERED_DUST, SlimefunItems.ELECTRIC_INGOT_PULVERIZER, FinalTechItems.ORDERED_DUST
    };

    public static final ItemStack[] ADVANCED_AUTO_DRIER = new ItemStack[] {
            FinalTechItems.ORDERED_DUST, SlimefunItems.AUTO_DRIER, FinalTechItems.ORDERED_DUST,
            SlimefunItems.AUTO_DRIER, FinalTechItems.BASIC_FRAME_MACHINE, SlimefunItems.AUTO_DRIER,
            FinalTechItems.ORDERED_DUST, SlimefunItems.AUTO_DRIER, FinalTechItems.ORDERED_DUST
    };

    public static final ItemStack[] ADVANCED_ELECTRIC_PRESS = new ItemStack[] {
            FinalTechItems.ORDERED_DUST, SlimefunItems.ELECTRIC_PRESS, FinalTechItems.ORDERED_DUST,
            SlimefunItems.ELECTRIC_PRESS, FinalTechItems.BASIC_FRAME_MACHINE, SlimefunItems.ELECTRIC_PRESS,
            FinalTechItems.ORDERED_DUST, SlimefunItems.ELECTRIC_PRESS, FinalTechItems.ORDERED_DUST
    };

    public static final ItemStack[] ADVANCED_FOOD_FACTORY = new ItemStack[] {
            FinalTechItems.ORDERED_DUST, SlimefunItems.FOOD_FABRICATOR, FinalTechItems.ORDERED_DUST,
            SlimefunItems.FOOD_COMPOSTER, FinalTechItems.BASIC_FRAME_MACHINE, SlimefunItems.FOOD_COMPOSTER,
            FinalTechItems.ORDERED_DUST, SlimefunItems.FOOD_FABRICATOR, FinalTechItems.ORDERED_DUST
    };

    public static final ItemStack[] ADVANCED_FREEZER = new ItemStack[] {
            FinalTechItems.ORDERED_DUST, SlimefunItems.FREEZER, FinalTechItems.ORDERED_DUST,
            SlimefunItems.FREEZER, FinalTechItems.BASIC_FRAME_MACHINE, SlimefunItems.FREEZER,
            FinalTechItems.ORDERED_DUST, SlimefunItems.FREEZER, FinalTechItems.ORDERED_DUST
    };

    public static final ItemStack[] ADVANCED_CARBON_PRESS = new ItemStack[] {
            FinalTechItems.ORDERED_DUST, SlimefunItems.CARBON_PRESS, FinalTechItems.ORDERED_DUST,
            SlimefunItems.CARBON_PRESS, FinalTechItems.BASIC_FRAME_MACHINE, SlimefunItems.CARBON_PRESS,
            FinalTechItems.ORDERED_DUST, SlimefunItems.CARBON_PRESS, FinalTechItems.ORDERED_DUST
    };

    public static final ItemStack[] ADVANCED_ELECTRIC_SMELTERY = new ItemStack[] {
            FinalTechItems.ORDERED_DUST, SlimefunItems.ELECTRIC_SMELTERY, FinalTechItems.ORDERED_DUST,
            SlimefunItems.ELECTRIC_SMELTERY, FinalTechItems.BASIC_FRAME_MACHINE, SlimefunItems.ELECTRIC_SMELTERY,
            FinalTechItems.ORDERED_DUST, SlimefunItems.ELECTRIC_SMELTERY, FinalTechItems.ORDERED_DUST
    };

    public static final ItemStack[] ADVANCED_DUST_FACTORY = new ItemStack[] {
            FinalTechItems.ORDERED_DUST, FinalTechItems.BASIC_DUST_FACTORY, FinalTechItems.ORDERED_DUST,
            FinalTechItems.BASIC_DUST_FACTORY, FinalTechItems.BASIC_FRAME_MACHINE, FinalTechItems.BASIC_DUST_FACTORY,
            FinalTechItems.ORDERED_DUST, FinalTechItems.BASIC_DUST_FACTORY, FinalTechItems.ORDERED_DUST
    };

    public static final ItemStack[] ADVANCED_ORE_FACTORY = new ItemStack[] {
            FinalTechItems.ORDERED_DUST, FinalTechItems.BASIC_ORE_FACTORY, FinalTechItems.ORDERED_DUST,
            FinalTechItems.BASIC_ORE_FACTORY, FinalTechItems.BASIC_FRAME_MACHINE, FinalTechItems.BASIC_ORE_FACTORY,
            FinalTechItems.ORDERED_DUST, FinalTechItems.BASIC_ORE_FACTORY, FinalTechItems.ORDERED_DUST
    };

    public static final ItemStack[] ADVANCED_FARM_FACTORY = new ItemStack[] {
            FinalTechItems.ORDERED_DUST, FinalTechItems.BASIC_FARM_FACTORY, FinalTechItems.ORDERED_DUST,
            FinalTechItems.BASIC_FARM_FACTORY, FinalTechItems.BASIC_FRAME_MACHINE, FinalTechItems.BASIC_FARM_FACTORY,
            FinalTechItems.ORDERED_DUST, FinalTechItems.BASIC_FARM_FACTORY, FinalTechItems.ORDERED_DUST
    };

    public static final ItemStack[] ADVANCED_AUTO_CRAFT = new ItemStack[] {
            FinalTechItems.GEARWHEEL, FinalTechItems.GEARWHEEL, FinalTechItems.GEARWHEEL,
            FinalTechItems.GEARWHEEL, FinalTechItems.FAKE, FinalTechItems.GEARWHEEL,
            FinalTechItems.ORDERED_DUST, FinalTechItems.ORDERED_DUST, FinalTechItems.ORDERED_DUST
    };

    // best machines
    public static final ItemStack[] ALL_COMPRESSION = new ItemStack[] {
            FinalTechItems.ORDERED_DUST, FinalTechItems.ORDERED_DUST, FinalTechItems.ORDERED_DUST,
            FinalTechItems.ORDERED_DUST, SlimefunItems.TRASH_CAN, FinalTechItems.ORDERED_DUST,
            FinalTechItems.ORDERED_DUST, FinalTechItems.ORDERED_DUST, FinalTechItems.ORDERED_DUST
    };

    public static final ItemStack[] ALL_FACTORY = new ItemStack[] {
            FinalTechItems.ORDERED_DUST, FinalTechItems.ORDERED_DUST, FinalTechItems.ORDERED_DUST,
            FinalTechItems.ORDERED_DUST, FinalTechItems.ALL_COMPRESSION, FinalTechItems.ORDERED_DUST,
            FinalTechItems.ORDERED_DUST, FinalTechItems.ORDERED_DUST, FinalTechItems.ORDERED_DUST
    };

    public static final ItemStack[] OVERCLOCK_FRAME_MACHINE = new ItemStack[] {
            FinalTechItems.FAKE, FinalTechItems.FAKE, FinalTechItems.FAKE,
            null, FinalTechItems.BASIC_FRAME_MACHINE, null,
            null, FinalTechItems.FAKE, null
    };

    // cargos
    public static final ItemStack[] BASIC_FRAME_MACHINE = new ItemStack[] {
            new ItemStack(Material.STONE), new ItemStack(Material.CHAIN), new ItemStack(Material.STONE),
            new ItemStack(Material.CHAIN), new ItemStack(Material.OBSERVER), new ItemStack(Material.CHAIN),
            new ItemStack(Material.STONE), new ItemStack(Material.CHAIN), new ItemStack(Material.STONE)
    };

    public static final ItemStack[] BASIC_NORMAL_STORAGE_UNIT = new ItemStack[] {
            new ItemStack(Material.GLASS), new ItemStack(Material.CHEST), new ItemStack(Material.GLASS),
            new ItemStack(Material.CHEST), null, new ItemStack(Material.CHEST),
            new ItemStack(Material.GLASS), new ItemStack(Material.CHEST), new ItemStack(Material.GLASS),
    };

    public static final ItemStack[] BASIC_LINKED_STORAGE_UNIT = new ItemStack[] {
            new ItemStack(Material.GLASS), new ItemStack(Material.CHEST), new ItemStack(Material.GLASS),
            new ItemStack(Material.CHEST), new ItemStack(Material.ENDER_EYE), new ItemStack(Material.CHEST),
            new ItemStack(Material.GLASS), new ItemStack(Material.CHEST), new ItemStack(Material.GLASS),
    };

    public static final ItemStack[] BASIC_CHARGEABLE_STORAGE_UNIT = new ItemStack[] {
            new ItemStack(Material.GLASS), new ItemStack(Material.CHEST), new ItemStack(Material.GLASS),
            new ItemStack(Material.CHEST), SlimefunItems.SMALL_CAPACITOR, new ItemStack(Material.CHEST),
            new ItemStack(Material.GLASS), new ItemStack(Material.CHEST), new ItemStack(Material.GLASS),
    };

    public static final ItemStack[] TRANSFER_PIPE = new ItemStack[] {
            new ItemStack(Material.TRIPWIRE_HOOK), new ItemStack(Material.CHAIN), new ItemStack(Material.TRIPWIRE_HOOK),
            new ItemStack(Material.ENDER_PEARL), FinalTechItems.BASIC_FRAME_MACHINE, new ItemStack(Material.ENDER_PEARL),
            new ItemStack(Material.TRIPWIRE_HOOK), new ItemStack(Material.CHAIN), new ItemStack(Material.TRIPWIRE_HOOK)
    };

    public static final ItemStack[] TRANSFER_STATION = new ItemStack[] {
            new ItemStack(Material.HOPPER), new ItemStack(Material.HOPPER), new ItemStack(Material.CHAIN),
            new ItemStack(Material.HOPPER), new ItemStack(Material.HOPPER), FinalTechItems.BASIC_FRAME_MACHINE,
            new ItemStack(Material.HOPPER), new ItemStack(Material.HOPPER), new ItemStack(Material.CHAIN)
    };

    public static final ItemStack[] TRANSFER_LINE = new ItemStack[] {
            new ItemStack(Material.TRIPWIRE_HOOK), new ItemStack(Material.TRIPWIRE_HOOK), new ItemStack(Material.SLIME_BALL),
            new ItemStack(Material.TRIPWIRE_HOOK), new ItemStack(Material.TRIPWIRE_HOOK), FinalTechItems.BASIC_FRAME_MACHINE,
            new ItemStack(Material.CHEST), new ItemStack(Material.CHEST), new ItemStack(Material.CHEST)
    };

    public static final ItemStack[] STORAGE_INTERACT_PORT = new ItemStack[] {
            new ItemStack(Material.STONE), new ItemStack(Material.STONE), new ItemStack(Material.STONE),
            FinalTechItems.GEARWHEEL, FinalTechItems.GEARWHEEL, FinalTechItems.GEARWHEEL,
            new ItemStack(Material.STONE), new ItemStack(Material.STONE), new ItemStack(Material.STONE)
    };

    public static final ItemStack[] STORAGE_ITEM_WHITE = new ItemStack[] {
            FinalTechItems.ORDERED_DUST, new ItemStack(Material.WHITE_CONCRETE_POWDER), new ItemStack(Material.WHITE_CONCRETE_POWDER),
            new ItemStack(Material.WHITE_CONCRETE_POWDER), new ItemStack(Material.WHITE_CONCRETE_POWDER), new ItemStack(Material.WHITE_CONCRETE),
            new ItemStack(Material.WHITE_CONCRETE), new ItemStack(Material.WHITE_CONCRETE), new ItemStack(Material.WHITE_CONCRETE)
    };

    public static final ItemStack[] STORAGE_ITEM_ORANGE = new ItemStack[] {
            FinalTechItems.ORDERED_DUST, new ItemStack(Material.ORANGE_CONCRETE_POWDER), new ItemStack(Material.ORANGE_CONCRETE_POWDER),
            new ItemStack(Material.ORANGE_CONCRETE_POWDER), new ItemStack(Material.ORANGE_CONCRETE_POWDER), new ItemStack(Material.ORANGE_CONCRETE),
            new ItemStack(Material.ORANGE_CONCRETE), new ItemStack(Material.ORANGE_CONCRETE), new ItemStack(Material.ORANGE_CONCRETE)
    };

    public static final ItemStack[] STORAGE_ITEM_MAGENTA = new ItemStack[] {
            FinalTechItems.ORDERED_DUST, new ItemStack(Material.MAGENTA_CONCRETE_POWDER), new ItemStack(Material.MAGENTA_CONCRETE_POWDER),
            new ItemStack(Material.MAGENTA_CONCRETE_POWDER), new ItemStack(Material.MAGENTA_CONCRETE_POWDER), new ItemStack(Material.MAGENTA_CONCRETE),
            new ItemStack(Material.MAGENTA_CONCRETE), new ItemStack(Material.MAGENTA_CONCRETE), new ItemStack(Material.MAGENTA_CONCRETE)
    };

    public static final ItemStack[] STORAGE_ITEM_LIGHT_BLUE = new ItemStack[] {
            FinalTechItems.ORDERED_DUST, new ItemStack(Material.LIGHT_BLUE_CONCRETE_POWDER), new ItemStack(Material.LIGHT_BLUE_CONCRETE_POWDER),
            new ItemStack(Material.LIGHT_BLUE_CONCRETE_POWDER), new ItemStack(Material.LIGHT_BLUE_CONCRETE_POWDER), new ItemStack(Material.LIGHT_BLUE_CONCRETE),
            new ItemStack(Material.LIGHT_BLUE_CONCRETE), new ItemStack(Material.LIGHT_BLUE_CONCRETE), new ItemStack(Material.LIGHT_BLUE_CONCRETE)
    };

    public static final ItemStack[] STORAGE_ITEM_YELLOW = new ItemStack[] {
            FinalTechItems.ORDERED_DUST, new ItemStack(Material.YELLOW_CONCRETE_POWDER), new ItemStack(Material.YELLOW_CONCRETE_POWDER),
            new ItemStack(Material.YELLOW_CONCRETE_POWDER), new ItemStack(Material.YELLOW_CONCRETE_POWDER), new ItemStack(Material.YELLOW_CONCRETE),
            new ItemStack(Material.YELLOW_CONCRETE), new ItemStack(Material.YELLOW_CONCRETE), new ItemStack(Material.YELLOW_CONCRETE)
    };

    public static final ItemStack[] STORAGE_ITEM_LIME = new ItemStack[] {
            FinalTechItems.ORDERED_DUST, new ItemStack(Material.LIME_CONCRETE_POWDER), new ItemStack(Material.LIME_CONCRETE_POWDER),
            new ItemStack(Material.LIME_CONCRETE_POWDER), new ItemStack(Material.LIME_CONCRETE_POWDER), new ItemStack(Material.LIME_CONCRETE),
            new ItemStack(Material.LIME_CONCRETE), new ItemStack(Material.LIME_CONCRETE), new ItemStack(Material.LIME_CONCRETE)
    };

    public static final ItemStack[] STORAGE_ITEM_PINK = new ItemStack[] {
            FinalTechItems.ORDERED_DUST, new ItemStack(Material.PINK_CONCRETE_POWDER), new ItemStack(Material.PINK_CONCRETE_POWDER),
            new ItemStack(Material.PINK_CONCRETE_POWDER), new ItemStack(Material.PINK_CONCRETE_POWDER), new ItemStack(Material.PINK_CONCRETE),
            new ItemStack(Material.PINK_CONCRETE), new ItemStack(Material.PINK_CONCRETE), new ItemStack(Material.PINK_CONCRETE)
    };

    public static final ItemStack[] STORAGE_ITEM_GRAY = new ItemStack[] {
            FinalTechItems.ORDERED_DUST, new ItemStack(Material.GRAY_CONCRETE_POWDER), new ItemStack(Material.GRAY_CONCRETE_POWDER),
            new ItemStack(Material.GRAY_CONCRETE_POWDER), new ItemStack(Material.GRAY_CONCRETE_POWDER), new ItemStack(Material.GRAY_CONCRETE),
            new ItemStack(Material.GRAY_CONCRETE), new ItemStack(Material.GRAY_CONCRETE), new ItemStack(Material.GRAY_CONCRETE)
    };

    public static final ItemStack[] STORAGE_ITEM_LIGHT_GRAY = new ItemStack[] {
            FinalTechItems.ORDERED_DUST, new ItemStack(Material.LIGHT_GRAY_CONCRETE_POWDER), new ItemStack(Material.LIGHT_GRAY_CONCRETE_POWDER),
            new ItemStack(Material.LIGHT_GRAY_CONCRETE_POWDER), new ItemStack(Material.LIGHT_GRAY_CONCRETE_POWDER), new ItemStack(Material.LIGHT_GRAY_CONCRETE),
            new ItemStack(Material.LIGHT_GRAY_CONCRETE), new ItemStack(Material.LIGHT_GRAY_CONCRETE), new ItemStack(Material.LIGHT_GRAY_CONCRETE)
    };

    public static final ItemStack[] STORAGE_ITEM_CYAN = new ItemStack[] {
            FinalTechItems.ORDERED_DUST, new ItemStack(Material.CYAN_CONCRETE_POWDER), new ItemStack(Material.CYAN_CONCRETE_POWDER),
            new ItemStack(Material.CYAN_CONCRETE_POWDER), new ItemStack(Material.CYAN_CONCRETE_POWDER), new ItemStack(Material.CYAN_CONCRETE),
            new ItemStack(Material.CYAN_CONCRETE), new ItemStack(Material.CYAN_CONCRETE), new ItemStack(Material.CYAN_CONCRETE)
    };

    public static final ItemStack[] STORAGE_ITEM_PURPLE = new ItemStack[] {
            FinalTechItems.ORDERED_DUST, new ItemStack(Material.PURPLE_CONCRETE_POWDER), new ItemStack(Material.PURPLE_CONCRETE_POWDER),
            new ItemStack(Material.PURPLE_CONCRETE_POWDER), new ItemStack(Material.PURPLE_CONCRETE_POWDER), new ItemStack(Material.PURPLE_CONCRETE),
            new ItemStack(Material.PURPLE_CONCRETE), new ItemStack(Material.PURPLE_CONCRETE), new ItemStack(Material.PURPLE_CONCRETE)
    };

    public static final ItemStack[] STORAGE_ITEM_BLUE = new ItemStack[] {
            FinalTechItems.ORDERED_DUST, new ItemStack(Material.BLUE_CONCRETE_POWDER), new ItemStack(Material.BLUE_CONCRETE_POWDER),
            new ItemStack(Material.BLUE_CONCRETE_POWDER), new ItemStack(Material.BLUE_CONCRETE_POWDER), new ItemStack(Material.BLUE_CONCRETE),
            new ItemStack(Material.BLUE_CONCRETE), new ItemStack(Material.BLUE_CONCRETE), new ItemStack(Material.BLUE_CONCRETE)
    };

    public static final ItemStack[] STORAGE_ITEM_BROWN = new ItemStack[] {
            FinalTechItems.ORDERED_DUST, new ItemStack(Material.BROWN_CONCRETE_POWDER), new ItemStack(Material.BROWN_CONCRETE_POWDER),
            new ItemStack(Material.BROWN_CONCRETE_POWDER), new ItemStack(Material.BROWN_CONCRETE_POWDER), new ItemStack(Material.BROWN_CONCRETE),
            new ItemStack(Material.BROWN_CONCRETE), new ItemStack(Material.BROWN_CONCRETE), new ItemStack(Material.BROWN_CONCRETE)
    };

    public static final ItemStack[] STORAGE_ITEM_GREEN = new ItemStack[] {
            FinalTechItems.ORDERED_DUST, new ItemStack(Material.GREEN_CONCRETE_POWDER), new ItemStack(Material.GREEN_CONCRETE_POWDER),
            new ItemStack(Material.GREEN_CONCRETE_POWDER), new ItemStack(Material.GREEN_CONCRETE_POWDER), new ItemStack(Material.GREEN_CONCRETE),
            new ItemStack(Material.GREEN_CONCRETE), new ItemStack(Material.GREEN_CONCRETE), new ItemStack(Material.GREEN_CONCRETE)
    };

    public static final ItemStack[] STORAGE_ITEM_RED = new ItemStack[] {
            FinalTechItems.ORDERED_DUST, new ItemStack(Material.RED_CONCRETE_POWDER), new ItemStack(Material.RED_CONCRETE_POWDER),
            new ItemStack(Material.RED_CONCRETE_POWDER), new ItemStack(Material.RED_CONCRETE_POWDER), new ItemStack(Material.RED_CONCRETE),
            new ItemStack(Material.RED_CONCRETE), new ItemStack(Material.RED_CONCRETE), new ItemStack(Material.RED_CONCRETE)
    };

    public static final ItemStack[] STORAGE_ITEM_BLACK = new ItemStack[] {
            FinalTechItems.ORDERED_DUST, new ItemStack(Material.BLACK_CONCRETE_POWDER), new ItemStack(Material.BLACK_CONCRETE_POWDER),
            new ItemStack(Material.BLACK_CONCRETE_POWDER), new ItemStack(Material.BLACK_CONCRETE_POWDER), new ItemStack(Material.BLACK_CONCRETE),
            new ItemStack(Material.BLACK_CONCRETE), new ItemStack(Material.BLACK_CONCRETE), new ItemStack(Material.BLACK_CONCRETE)
    };

    // electric
    public static final ItemStack[] BASIC_CHARGE_INCREASE_CAPACITOR = new ItemStack[] {
            FinalTechItems.ORDERED_DUST, SlimefunItems.REDSTONE_ALLOY, SlimefunItems.REINFORCED_PLATE,
            FinalTechItems.GEARWHEEL, SlimefunItems.ENERGIZED_CAPACITOR, FinalTechItems.GEARWHEEL,
            SlimefunItems.REINFORCED_PLATE, SlimefunItems.REDSTONE_ALLOY, SlimefunItems.REINFORCED_PLATE
    };
    public static final ItemStack[] BASIC_CONSUME_REDUCE_CAPACITOR = new ItemStack[] {
            SlimefunItems.REINFORCED_PLATE, SlimefunItems.REDSTONE_ALLOY, FinalTechItems.ORDERED_DUST,
            FinalTechItems.GEARWHEEL, SlimefunItems.ENERGIZED_CAPACITOR, FinalTechItems.GEARWHEEL,
            SlimefunItems.REINFORCED_PLATE, SlimefunItems.REDSTONE_ALLOY, SlimefunItems.REINFORCED_PLATE
    };
    public static final ItemStack[] BASIC_SELF_GENERATE_CAPACITOR = new ItemStack[] {
            SlimefunItems.REINFORCED_PLATE, SlimefunItems.REDSTONE_ALLOY, SlimefunItems.REINFORCED_PLATE,
            FinalTechItems.GEARWHEEL, SlimefunItems.ENERGIZED_CAPACITOR, FinalTechItems.GEARWHEEL,
            FinalTechItems.ORDERED_DUST, SlimefunItems.REDSTONE_ALLOY, SlimefunItems.REINFORCED_PLATE
    };
    public static final ItemStack[] BASIC_VOID_GENERATE_CAPACITOR = new ItemStack[] {
            SlimefunItems.REINFORCED_PLATE, SlimefunItems.REDSTONE_ALLOY, SlimefunItems.REINFORCED_PLATE,
            FinalTechItems.GEARWHEEL, SlimefunItems.ENERGIZED_CAPACITOR, FinalTechItems.GEARWHEEL,
            SlimefunItems.REINFORCED_PLATE, SlimefunItems.REDSTONE_ALLOY, FinalTechItems.ORDERED_DUST
    };

    public static final ItemStack[] SMALL_EXPANDED_CAPACITOR = new ItemStack[] {
            SlimefunItems.DURALUMIN_INGOT, FinalTechItems.QUANTITY_MODULE, SlimefunItems.DURALUMIN_INGOT,
            FinalTechItems.GEARWHEEL, SlimefunItems.ENERGIZED_CAPACITOR, FinalTechItems.GEARWHEEL,
            SlimefunItems.DURALUMIN_INGOT, FinalTechItems.QUANTITY_MODULE, SlimefunItems.DURALUMIN_INGOT
    };

    public static final ItemStack[] MEDIUM_EXPANDED_CAPACITOR = new ItemStack[] {
            SlimefunItems.BILLON_INGOT, FinalTechItems.QUANTITY_MODULE, SlimefunItems.BILLON_INGOT,
            FinalTechItems.GEARWHEEL, FinalTechItems.SMALL_EXPANDED_CAPACITOR, FinalTechItems.GEARWHEEL,
            SlimefunItems.BILLON_INGOT, FinalTechItems.QUANTITY_MODULE, SlimefunItems.BILLON_INGOT
    };

    public static final ItemStack[] BIG_EXPANDED_CAPACITOR = new ItemStack[] {
            SlimefunItems.STEEL_INGOT, FinalTechItems.QUANTITY_MODULE, SlimefunItems.STEEL_INGOT,
            FinalTechItems.GEARWHEEL, FinalTechItems.MEDIUM_EXPANDED_CAPACITOR, FinalTechItems.GEARWHEEL,
            SlimefunItems.STEEL_INGOT, FinalTechItems.QUANTITY_MODULE, SlimefunItems.STEEL_INGOT
    };

    public static final ItemStack[] LARGE_EXPANDED_CAPACITOR = new ItemStack[] {
            SlimefunItems.REINFORCED_ALLOY_INGOT, FinalTechItems.QUANTITY_MODULE, SlimefunItems.REINFORCED_ALLOY_INGOT,
            FinalTechItems.GEARWHEEL, FinalTechItems.BIG_EXPANDED_CAPACITOR, FinalTechItems.GEARWHEEL,
            SlimefunItems.REINFORCED_ALLOY_INGOT, FinalTechItems.QUANTITY_MODULE, SlimefunItems.REINFORCED_ALLOY_INGOT
    };

    public static final ItemStack[] CARBONADO_EXPANDED_CAPACITOR = new ItemStack[] {
            SlimefunItems.CARBONADO, FinalTechItems.QUANTITY_MODULE, SlimefunItems.CARBONADO,
            FinalTechItems.GEARWHEEL, FinalTechItems.LARGE_EXPANDED_CAPACITOR, FinalTechItems.GEARWHEEL,
            SlimefunItems.CARBONADO, FinalTechItems.QUANTITY_MODULE, SlimefunItems.CARBONADO
    };

    public static final ItemStack[] ENERGIZED_EXPANDED_CAPACITOR = new ItemStack[] {
            SlimefunItems.CARBONADO, FinalTechItems.QUANTITY_MODULE, SlimefunItems.CARBONADO,
            FinalTechItems.FAKE, FinalTechItems.CARBONADO_EXPANDED_CAPACITOR, FinalTechItems.FAKE,
            SlimefunItems.CARBONADO, FinalTechItems.QUANTITY_MODULE, SlimefunItems.CARBONADO
    };

    public static final ItemStack[] BASIC_GENERATOR = new ItemStack[] {
            new ItemStack(Material.GLOWSTONE), new ItemStack(Material.GLOWSTONE), new ItemStack(Material.GLOWSTONE),
            SlimefunItems.SOLAR_GENERATOR, SlimefunItems.SOLAR_GENERATOR, SlimefunItems.SOLAR_GENERATOR,
            null, SlimefunItems.SOLAR_GENERATOR, null
    };

    public static final ItemStack[] ADVANCED_GENERATOR = new ItemStack[] {
            FinalTechItems.BASIC_GENERATOR, new ItemStack(Material.CHAIN), FinalTechItems.BASIC_GENERATOR,
            new ItemStack(Material.CHAIN), new ItemStack(Material.REDSTONE), new ItemStack(Material.CHAIN),
            FinalTechItems.BASIC_GENERATOR, new ItemStack(Material.CHAIN), FinalTechItems.BASIC_GENERATOR
    };

    public static final ItemStack[] REINFORCED_GENERATOR = new ItemStack[] {
            FinalTechItems.ADVANCED_GENERATOR, new ItemStack(Material.CHAIN), FinalTechItems.ADVANCED_GENERATOR,
            new ItemStack(Material.CHAIN), SlimefunItems.REINFORCED_ALLOY_INGOT, new ItemStack(Material.CHAIN),
            FinalTechItems.ADVANCED_GENERATOR, new ItemStack(Material.CHAIN), FinalTechItems.ADVANCED_GENERATOR
    };

    public static final ItemStack[] CARBONADO_GENERATOR = new ItemStack[] {
            FinalTechItems.REINFORCED_GENERATOR, new ItemStack(Material.CHAIN), FinalTechItems.REINFORCED_GENERATOR,
            new ItemStack(Material.CHAIN), SlimefunItems.CARBONADO, new ItemStack(Material.CHAIN),
            FinalTechItems.REINFORCED_GENERATOR, new ItemStack(Material.CHAIN), FinalTechItems.REINFORCED_GENERATOR
    };

    public static final ItemStack[] ENERGIZED_GENERATOR = new ItemStack[] {
            FinalTechItems.CARBONADO_GENERATOR, SlimefunItems.BLISTERING_INGOT_3, FinalTechItems.CARBONADO_GENERATOR,
            SlimefunItems.BLISTERING_INGOT_3, SlimefunItems.SOLAR_GENERATOR_4, SlimefunItems.BLISTERING_INGOT_3,
            FinalTechItems.CARBONADO_GENERATOR, SlimefunItems.BLISTERING_INGOT_3, FinalTechItems.CARBONADO_GENERATOR
    };

    public static final ItemStack[] MATRIX_GENERATOR = new ItemStack[] {
            FinalTechItems.ENERGIZED_GENERATOR, SlimefunItems.SOLAR_GENERATOR_4, FinalTechItems.ENERGIZED_GENERATOR,
            SlimefunItems.SOLAR_GENERATOR_4, FinalTechItems.ORDERED_DUST, SlimefunItems.SOLAR_GENERATOR_4,
            FinalTechItems.ENERGIZED_GENERATOR, SlimefunItems.SOLAR_GENERATOR_4, FinalTechItems.ENERGIZED_GENERATOR
    };

    // tool
    public static final ItemStack[] UNORDERED_SWORD = new ItemStack[] {
            null, FinalTechItems.ORDERED_DUST, null,
            null, FinalTechItems.ORDERED_DUST, null,
            null, FinalTechItems.GEARWHEEL, null
    };
}
