package io.taraxacum.finaltech.setup;

import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.implementation.SlimefunItems;
import io.github.thebusybiscuit.slimefun4.libraries.dough.items.CustomItemStack;
import io.taraxacum.finaltech.FinalTech;
import io.taraxacum.finaltech.core.items.unusable.CopyCard;
import io.taraxacum.libs.plugin.util.ItemStackUtil;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;

import java.util.Locale;

/**
 * @author Final_ROOT
 */
public final class FinalTechRecipes {
    // RecipesType
    public static final RecipeType RECIPE_TYPE_ORDERED_DUST_FACTORY = new RecipeType(new NamespacedKey(FinalTech.getInstance(), FinalTechItems.ORDERED_DUST_FACTORY_DIRT.getItemId().toLowerCase(Locale.ROOT)), FinalTechItems.ORDERED_DUST_FACTORY_DIRT);
    public static final RecipeType RECIPE_TYPE_ITEM_SERIALIZATION_CONSTRUCTOR = new RecipeType(new NamespacedKey(FinalTech.getInstance(), FinalTechItems.ITEM_SERIALIZATION_CONSTRUCTOR.getItemId().toLowerCase(Locale.ROOT)), FinalTechItems.ITEM_SERIALIZATION_CONSTRUCTOR);
    public static final RecipeType RECIPE_TYPE_MATRIX_CRAFTING_TABLE = new RecipeType(new NamespacedKey(FinalTech.getInstance(), FinalTechItems.MATRIX_CRAFTING_TABLE.getItemId().toLowerCase(Locale.ROOT)), FinalTechItems.MATRIX_CRAFTING_TABLE);
    public static final RecipeType RECIPE_TYPE_BOX = new RecipeType(new NamespacedKey(FinalTech.getInstance(), FinalTechItems.BOX.getItemId().toLowerCase(Locale.ROOT)), FinalTechItems.BOX);
    public static final RecipeType RECIPE_TYPE_EQUIVALENT_EXCHANGE_TABLE = new RecipeType(new NamespacedKey(FinalTech.getInstance(), FinalTechItems.EQUIVALENT_EXCHANGE_TABLE.getItemId().toLowerCase(Locale.ROOT)), FinalTechItems.EQUIVALENT_EXCHANGE_TABLE);
    public static final RecipeType RECIPE_TYPE_ENTROPY_SEED = new RecipeType(new NamespacedKey(FinalTech.getInstance(), FinalTechItems.ENTROPY_SEED.getItemId().toLowerCase(Locale.ROOT)), FinalTechItems.ENTROPY_SEED);
    public static final RecipeType RECIPE_TYPE_LOGIC_CRAFTER = new RecipeType(new NamespacedKey(FinalTech.getInstance(), FinalTechItems.LOGIC_CRAFTER.getItemId().toLowerCase(Locale.ROOT)), FinalTechItems.LOGIC_CRAFTER);
    public static final RecipeType RECIPE_TYPE_LOGIC_GENERATOR = new RecipeType(new NamespacedKey(FinalTech.getInstance(), FinalTechItems.LOGIC_GENERATOR.getItemId().toLowerCase(Locale.ROOT)), FinalTechItems.LOGIC_GENERATOR);
    public static final RecipeType RECIPE_TYPE_CARD_OPERATION_TABLE = new RecipeType(new NamespacedKey(FinalTech.getInstance(), FinalTechItems.CARD_OPERATION_TABLE.getItemId().toLowerCase(Locale.ROOT)), FinalTechItems.CARD_OPERATION_TABLE);
    public static final RecipeType RECIPE_TYPE_ENTROPY_CONSTRUCTOR = new RecipeType(new NamespacedKey(FinalTech.getInstance(), FinalTechItems.ENTROPY_CONSTRUCTOR.getItemId().toLowerCase(Locale.ROOT)), FinalTechItems.ENTROPY_CONSTRUCTOR);

    /* items */
    // material
    public static final ItemStack[] WATER_CARD = new ItemStack[] {
            null, null, null,
            null, new ItemStack(Material.WATER_BUCKET), null,
            null, null, null
    };
    public static final ItemStack[] LAVA_CARD = new ItemStack[] {
            null, null, null,
            null, new ItemStack(Material.LAVA_BUCKET), null,
            null, null, null
    };
    public static final ItemStack[] MILK_CARD = new ItemStack[] {
            null, null, null,
            null, new ItemStack(Material.MILK_BUCKET), null,
            null, null, null
    };
    public static final ItemStack[] GEARWHEEL = new ItemStack[] {
            new ItemStack(Material.POLISHED_GRANITE), new ItemStack(Material.POLISHED_GRANITE), new ItemStack(Material.POLISHED_GRANITE),
            new ItemStack(Material.POLISHED_DIORITE), new ItemStack(Material.POLISHED_DIORITE), new ItemStack(Material.POLISHED_DIORITE),
            new ItemStack(Material.POLISHED_ANDESITE), new ItemStack(Material.POLISHED_ANDESITE), new ItemStack(Material.POLISHED_ANDESITE)
    };
    public static final ItemStack[] UNORDERED_DUST = new ItemStack[] {};
    public static final ItemStack[] ORDERED_DUST = new ItemStack[] {};
    public static final ItemStack[] BUG = new ItemStack[] {};
    public static final ItemStack[] ENTROPY = new ItemStack[] {};
    public static final ItemStack[] BOX = new ItemStack[] {};
    public static final ItemStack[] SHINE = new ItemStack[] {};
    public static final ItemStack[] COPY_CARD = new ItemStack[] {};
    public static final ItemStack[] ANNULAR = new ItemStack[] {};
    public static final ItemStack[] QUANTITY_MODULE = new ItemStack[] {
            null, ItemStackUtil.cloneItem(FinalTechItems.GEARWHEEL, 24), null, ItemStackUtil.cloneItem(FinalTechItems.GEARWHEEL, 24), null, null,
            ItemStackUtil.cloneItem(FinalTechItems.GEARWHEEL, 24), null, ItemStackUtil.cloneItem(FinalTechItems.GEARWHEEL, 32), null, ItemStackUtil.cloneItem(FinalTechItems.GEARWHEEL, 24), null,
            null, ItemStackUtil.cloneItem(FinalTechItems.GEARWHEEL, 32), FinalTechItems.ANNULAR, ItemStackUtil.cloneItem(FinalTechItems.GEARWHEEL, 32), null, null,
            ItemStackUtil.cloneItem(FinalTechItems.GEARWHEEL, 24), null, ItemStackUtil.cloneItem(FinalTechItems.GEARWHEEL, 32), null, ItemStackUtil.cloneItem(FinalTechItems.GEARWHEEL, 24), null,
            null, ItemStackUtil.cloneItem(FinalTechItems.GEARWHEEL, 24), null, ItemStackUtil.cloneItem(FinalTechItems.GEARWHEEL, 24), null, null
    };
    public static final ItemStack[] QUANTITY_MODULE_INFINITY = new ItemStack[] {
            ItemStackUtil.cloneItem(FinalTechItems.QUANTITY_MODULE, 12), null, ItemStackUtil.cloneItem(FinalTechItems.QUANTITY_MODULE, 20), null, ItemStackUtil.cloneItem(FinalTechItems.QUANTITY_MODULE, 12), null,
            null, ItemStackUtil.cloneItem(FinalTechItems.QUANTITY_MODULE, 32), null, ItemStackUtil.cloneItem(FinalTechItems.QUANTITY_MODULE, 32), null, null,
            ItemStackUtil.cloneItem(FinalTechItems.QUANTITY_MODULE, 20), null, ItemStackUtil.cloneItem(FinalTechItems.QUANTITY_MODULE, 64), null, ItemStackUtil.cloneItem(FinalTechItems.QUANTITY_MODULE, 20), null,
            null, ItemStackUtil.cloneItem(FinalTechItems.QUANTITY_MODULE, 32), null, ItemStackUtil.cloneItem(FinalTechItems.QUANTITY_MODULE, 32), null, null,
            ItemStackUtil.cloneItem(FinalTechItems.QUANTITY_MODULE, 12), null, ItemStackUtil.cloneItem(FinalTechItems.QUANTITY_MODULE, 20), null, ItemStackUtil.cloneItem(FinalTechItems.QUANTITY_MODULE, 12), null,
    };
    public static final ItemStack[] SINGULARITY = new ItemStack[] {};
    public static final ItemStack[] SPIROCHETE = new ItemStack[] {};
    public static final ItemStack[] SHELL = new ItemStack[] {};
    public static final ItemStack[] PHONY = new ItemStack[] {};
    public static final ItemStack[] JUSTIFIABILITY = new ItemStack[] {};
    public static final ItemStack[] EQUIVALENT_CONCEPT = new ItemStack[] {};
    // logic item
    public static final ItemStack[] LOGIC_FALSE = new ItemStack[] {};
    public static final ItemStack[] LOGIC_TRUE = new ItemStack[] {};
    public static final ItemStack[] DIGITAL_ZERO = new ItemStack[] {
            FinalTechItems.LOGIC_FALSE, FinalTechItems.LOGIC_FALSE, FinalTechItems.LOGIC_FALSE, FinalTechItems.LOGIC_FALSE
    };
    public static final ItemStack[] DIGITAL_ONE = new ItemStack[] {
            FinalTechItems.LOGIC_FALSE, FinalTechItems.LOGIC_FALSE, FinalTechItems.LOGIC_FALSE, FinalTechItems.LOGIC_TRUE
    };
    public static final ItemStack[] DIGITAL_TWO = new ItemStack[] {
            FinalTechItems.LOGIC_FALSE, FinalTechItems.LOGIC_FALSE, FinalTechItems.LOGIC_TRUE, FinalTechItems.LOGIC_FALSE
    };
    public static final ItemStack[] DIGITAL_THREE = new ItemStack[] {
            FinalTechItems.LOGIC_FALSE, FinalTechItems.LOGIC_FALSE, FinalTechItems.LOGIC_TRUE, FinalTechItems.LOGIC_TRUE
    };
    public static final ItemStack[] DIGITAL_FOUR = new ItemStack[] {
            FinalTechItems.LOGIC_FALSE, FinalTechItems.LOGIC_TRUE, FinalTechItems.LOGIC_FALSE, FinalTechItems.LOGIC_FALSE
    };
    public static final ItemStack[] DIGITAL_FIVE = new ItemStack[] {
            FinalTechItems.LOGIC_FALSE, FinalTechItems.LOGIC_TRUE, FinalTechItems.LOGIC_FALSE, FinalTechItems.LOGIC_TRUE
    };
    public static final ItemStack[] DIGITAL_SIX = new ItemStack[] {
            FinalTechItems.LOGIC_FALSE, FinalTechItems.LOGIC_TRUE, FinalTechItems.LOGIC_TRUE, FinalTechItems.LOGIC_FALSE
    };
    public static final ItemStack[] DIGITAL_SEVEN = new ItemStack[] {
            FinalTechItems.LOGIC_FALSE, FinalTechItems.LOGIC_TRUE, FinalTechItems.LOGIC_TRUE, FinalTechItems.LOGIC_TRUE
    };
    public static final ItemStack[] DIGITAL_EIGHT = new ItemStack[] {
            FinalTechItems.LOGIC_TRUE, FinalTechItems.LOGIC_FALSE, FinalTechItems.LOGIC_FALSE, FinalTechItems.LOGIC_FALSE
    };
    public static final ItemStack[] DIGITAL_NINE = new ItemStack[] {
            FinalTechItems.LOGIC_TRUE, FinalTechItems.LOGIC_FALSE, FinalTechItems.LOGIC_FALSE, FinalTechItems.LOGIC_TRUE
    };
    public static final ItemStack[] DIGITAL_TEN = new ItemStack[] {
            FinalTechItems.LOGIC_TRUE, FinalTechItems.LOGIC_FALSE, FinalTechItems.LOGIC_TRUE, FinalTechItems.LOGIC_FALSE
    };
    public static final ItemStack[] DIGITAL_ELEVEN = new ItemStack[] {
            FinalTechItems.LOGIC_TRUE, FinalTechItems.LOGIC_FALSE, FinalTechItems.LOGIC_TRUE, FinalTechItems.LOGIC_TRUE
    };
    public static final ItemStack[] DIGITAL_TWELVE = new ItemStack[] {
            FinalTechItems.LOGIC_TRUE, FinalTechItems.LOGIC_TRUE, FinalTechItems.LOGIC_FALSE, FinalTechItems.LOGIC_FALSE
    };
    public static final ItemStack[] DIGITAL_THIRTEEN = new ItemStack[] {
            FinalTechItems.LOGIC_TRUE, FinalTechItems.LOGIC_TRUE, FinalTechItems.LOGIC_FALSE, FinalTechItems.LOGIC_TRUE
    };
    public static final ItemStack[] DIGITAL_FOURTEEN = new ItemStack[] {
            FinalTechItems.LOGIC_TRUE, FinalTechItems.LOGIC_TRUE, FinalTechItems.LOGIC_TRUE, FinalTechItems.LOGIC_FALSE
    };
    public static final ItemStack[] DIGITAL_FIFTEEN = new ItemStack[] {
            FinalTechItems.LOGIC_TRUE, FinalTechItems.LOGIC_TRUE, FinalTechItems.LOGIC_TRUE, FinalTechItems.LOGIC_TRUE
    };
    // consumable
    public static final ItemStack[] MACHINE_CHARGE_CARD_L1 = new ItemStack[] {
            null, null, null,
            FinalTechItems.UNORDERED_DUST, FinalTechItems.GEARWHEEL, FinalTechItems.UNORDERED_DUST,
            null, null, null
    };
    public static final ItemStack[] MACHINE_CHARGE_CARD_L2 = new ItemStack[] {
            null, null, null, null, null, null,
            FinalTechItems.UNORDERED_DUST, FinalTechItems.UNORDERED_DUST, FinalTechItems.UNORDERED_DUST, FinalTechItems.UNORDERED_DUST, FinalTechItems.UNORDERED_DUST, null,
            null, FinalTechItems.UNORDERED_DUST, FinalTechItems.GEARWHEEL, FinalTechItems.UNORDERED_DUST, null, null,
            FinalTechItems.UNORDERED_DUST, FinalTechItems.UNORDERED_DUST, FinalTechItems.UNORDERED_DUST, FinalTechItems.UNORDERED_DUST, FinalTechItems.UNORDERED_DUST, null
    };
    public static final ItemStack[] MACHINE_CHARGE_CARD_L3 = new ItemStack[] {
            null, null, null, null, null, null,
            FinalTechItems.MACHINE_CHARGE_CARD_L1, FinalTechItems.MACHINE_CHARGE_CARD_L1, FinalTechItems.MACHINE_CHARGE_CARD_L1, FinalTechItems.MACHINE_CHARGE_CARD_L1, FinalTechItems.MACHINE_CHARGE_CARD_L1, null,
            null, FinalTechItems.MACHINE_CHARGE_CARD_L2, FinalTechItems.GEARWHEEL, FinalTechItems.MACHINE_CHARGE_CARD_L2, null, null,
            FinalTechItems.MACHINE_CHARGE_CARD_L1, FinalTechItems.MACHINE_CHARGE_CARD_L1, FinalTechItems.MACHINE_CHARGE_CARD_L1, FinalTechItems.MACHINE_CHARGE_CARD_L1, FinalTechItems.MACHINE_CHARGE_CARD_L1, null
    };
    public static final ItemStack[] MACHINE_ACCELERATE_CARD_L1 = new ItemStack[] {
            FinalTechItems.UNORDERED_DUST, FinalTechItems.UNORDERED_DUST, FinalTechItems.UNORDERED_DUST,
            null, FinalTechItems.GEARWHEEL, null,
            FinalTechItems.UNORDERED_DUST, FinalTechItems.UNORDERED_DUST, FinalTechItems.UNORDERED_DUST
    };
    public static final ItemStack[] MACHINE_ACCELERATE_CARD_L2 = new ItemStack[] {
            FinalTechItems.UNORDERED_DUST, FinalTechItems.UNORDERED_DUST, null, FinalTechItems.UNORDERED_DUST, FinalTechItems.UNORDERED_DUST, null,
            FinalTechItems.UNORDERED_DUST, null, FinalTechItems.UNORDERED_DUST, null, FinalTechItems.UNORDERED_DUST, null,
            null, FinalTechItems.UNORDERED_DUST, FinalTechItems.SHINE, FinalTechItems.UNORDERED_DUST, null, null,
            FinalTechItems.UNORDERED_DUST, null, FinalTechItems.UNORDERED_DUST, null, FinalTechItems.UNORDERED_DUST, null,
            FinalTechItems.UNORDERED_DUST, FinalTechItems.UNORDERED_DUST, null, FinalTechItems.UNORDERED_DUST, FinalTechItems.UNORDERED_DUST, null
    };
    public static final ItemStack[] MACHINE_ACCELERATE_CARD_L3 = new ItemStack[] {
            FinalTechItems.MACHINE_ACCELERATE_CARD_L1, FinalTechItems.MACHINE_ACCELERATE_CARD_L1, null, FinalTechItems.MACHINE_ACCELERATE_CARD_L1, FinalTechItems.MACHINE_ACCELERATE_CARD_L1, null,
            FinalTechItems.MACHINE_ACCELERATE_CARD_L1, null, FinalTechItems.MACHINE_ACCELERATE_CARD_L2, null, FinalTechItems.MACHINE_ACCELERATE_CARD_L1, null,
            null, FinalTechItems.MACHINE_ACCELERATE_CARD_L2, FinalTechItems.ANNULAR, FinalTechItems.MACHINE_ACCELERATE_CARD_L2, null, null,
            FinalTechItems.MACHINE_ACCELERATE_CARD_L1, null, FinalTechItems.MACHINE_ACCELERATE_CARD_L2, null, FinalTechItems.MACHINE_ACCELERATE_CARD_L1, null,
            FinalTechItems.MACHINE_ACCELERATE_CARD_L1, FinalTechItems.MACHINE_ACCELERATE_CARD_L1, null, FinalTechItems.MACHINE_ACCELERATE_CARD_L1, FinalTechItems.MACHINE_ACCELERATE_CARD_L1, null
    };
    public static final ItemStack[] MACHINE_ACTIVATE_CARD_L1 = new ItemStack[] {
            null, FinalTechItems.UNORDERED_DUST, null,
            FinalTechItems.UNORDERED_DUST, FinalTechItems.GEARWHEEL, FinalTechItems.UNORDERED_DUST,
            null, FinalTechItems.UNORDERED_DUST, null
    };
    public static final ItemStack[] MACHINE_ACTIVATE_CARD_L2 = new ItemStack[] {
            null, FinalTechItems.UNORDERED_DUST, FinalTechItems.UNORDERED_DUST, FinalTechItems.UNORDERED_DUST, null, null,
            FinalTechItems.UNORDERED_DUST, null, null, null, FinalTechItems.UNORDERED_DUST, null,
            FinalTechItems.UNORDERED_DUST, FinalTechItems.UNORDERED_DUST, FinalTechItems.SHINE, FinalTechItems.UNORDERED_DUST, FinalTechItems.UNORDERED_DUST, null,
            FinalTechItems.UNORDERED_DUST, null, null, null, FinalTechItems.UNORDERED_DUST, null,
            null, FinalTechItems.UNORDERED_DUST, FinalTechItems.UNORDERED_DUST, FinalTechItems.UNORDERED_DUST, null, null
    };
    public static final ItemStack[] MACHINE_ACTIVATE_CARD_L3 = new ItemStack[] {
            null, FinalTechItems.MACHINE_ACTIVATE_CARD_L2, FinalTechItems.MACHINE_ACTIVATE_CARD_L2, FinalTechItems.MACHINE_ACTIVATE_CARD_L2, null, null,
            FinalTechItems.MACHINE_ACTIVATE_CARD_L1, null, null, null, FinalTechItems.MACHINE_ACTIVATE_CARD_L1, null,
            FinalTechItems.MACHINE_ACTIVATE_CARD_L1, FinalTechItems.MACHINE_ACTIVATE_CARD_L1, FinalTechItems.ANNULAR, FinalTechItems.MACHINE_ACTIVATE_CARD_L1, FinalTechItems.MACHINE_ACTIVATE_CARD_L1, null,
            FinalTechItems.MACHINE_ACTIVATE_CARD_L1, null, null, null, FinalTechItems.MACHINE_ACTIVATE_CARD_L1, null,
            null, FinalTechItems.MACHINE_ACTIVATE_CARD_L1, FinalTechItems.MACHINE_ACTIVATE_CARD_L1, FinalTechItems.MACHINE_ACTIVATE_CARD_L1, null, null
    };
    public static final ItemStack[] MAGIC_HYPNOTIC = new ItemStack[] {
            null, SlimefunItems.FORTUNE_COOKIE, null,
            null, FinalTechItems.SHINE, null,
            null, SlimefunItems.FORTUNE_COOKIE, null
    };
    public static final ItemStack[] RESEARCH_UNLOCK_TICKET = new ItemStack[] {
            FinalTechItems.BUG, FinalTechItems.BUG, FinalTechItems.BUG,
            FinalTechItems.BUG, FinalTechItems.SHINE, FinalTechItems.BUG,
            FinalTechItems.BUG, FinalTechItems.BUG, FinalTechItems.BUG
    };
    // tool
    public static final ItemStack[] STAFF_ELEMENTAL_LINE = new ItemStack[] {
            null, FinalTechItems.SHINE, null,
            null, SlimefunItems.STAFF_ELEMENTAL, FinalTechItems.SHINE,
            SlimefunItems.STAFF_ELEMENTAL, null, null
    };
    public static final ItemStack[] POTION_EFFECT_COMPRESSOR = new ItemStack[] {
            FinalTechItems.UNORDERED_DUST, FinalTechItems.UNORDERED_DUST, null, null, FinalTechItems.UNORDERED_DUST, FinalTechItems.UNORDERED_DUST,
            FinalTechItems.UNORDERED_DUST, null, null, null, null, FinalTechItems.UNORDERED_DUST,
            null, null, FinalTechItems.SHINE, FinalTechItems.SHINE, null, null,
            null, null, FinalTechItems.SHINE, FinalTechItems.SHINE, null, null,
            FinalTechItems.UNORDERED_DUST, null, null, null, null, FinalTechItems.UNORDERED_DUST,
            FinalTechItems.UNORDERED_DUST, FinalTechItems.UNORDERED_DUST, null, null, FinalTechItems.UNORDERED_DUST, FinalTechItems.UNORDERED_DUST
    };
    public static final ItemStack[] POTION_EFFECT_DILATOR = new ItemStack[] {
            null, FinalTechItems.UNORDERED_DUST, FinalTechItems.UNORDERED_DUST, FinalTechItems.UNORDERED_DUST, FinalTechItems.UNORDERED_DUST, null,
            FinalTechItems.UNORDERED_DUST, null, null, null, null, FinalTechItems.UNORDERED_DUST,
            FinalTechItems.UNORDERED_DUST, null, FinalTechItems.SHINE, FinalTechItems.SHINE, null, FinalTechItems.UNORDERED_DUST,
            FinalTechItems.UNORDERED_DUST, null, FinalTechItems.SHINE, FinalTechItems.SHINE, null, FinalTechItems.UNORDERED_DUST,
            FinalTechItems.UNORDERED_DUST, null, null, null, null, FinalTechItems.UNORDERED_DUST,
            null, FinalTechItems.UNORDERED_DUST, FinalTechItems.UNORDERED_DUST, FinalTechItems.UNORDERED_DUST, FinalTechItems.UNORDERED_DUST, null
    };
    public static final ItemStack[] POTION_EFFECT_PURIFIER = new ItemStack[] {
            null, null, null, null, null, null,
            null, FinalTechItems.UNORDERED_DUST, FinalTechItems.UNORDERED_DUST, FinalTechItems.UNORDERED_DUST, FinalTechItems.UNORDERED_DUST, null,
            null, FinalTechItems.UNORDERED_DUST, FinalTechItems.SHINE, FinalTechItems.SHINE, FinalTechItems.UNORDERED_DUST, null,
            null, FinalTechItems.UNORDERED_DUST, FinalTechItems.SHINE, FinalTechItems.SHINE, FinalTechItems.UNORDERED_DUST, null,
            null, FinalTechItems.UNORDERED_DUST, FinalTechItems.UNORDERED_DUST, FinalTechItems.UNORDERED_DUST, FinalTechItems.UNORDERED_DUST, null,
            null, null, null, null, null, null
    };
    public static final ItemStack[] MENU_VIEWER = new ItemStack[] {
            null, FinalTechItems.GEARWHEEL, null,
            null, new ItemStack(Material.SPYGLASS), null,
            null, null, null
    };
    public static final ItemStack[] LOCATION_RECORDER = new ItemStack[] {
            null, FinalTechItems.GEARWHEEL, null,
            null, new ItemStack(Material.COMPASS), null,
            null, null, null
    };
    public static final ItemStack[] MACHINE_CONFIGURATOR = new ItemStack[] {
            null, FinalTechItems.GEARWHEEL, null,
            null, new ItemStack(Material.ARROW), null,
            null, null, null
    };
    public static final ItemStack[] PORTABLE_ENERGY_STORAGE = new ItemStack[] {
            null, FinalTechItems.GEARWHEEL, null,
            null, new ItemStack(Material.FLINT_AND_STEEL), null,
            null, null, null
    };
    // weapon
    public static final ItemStack[] SUPER_PICKAXE = new ItemStack[] {
            FinalTechItems.SHINE, FinalTechItems.SHINE, FinalTechItems.SHINE,
            null, new ItemStack(Material.STICK), null,
            null, new ItemStack(Material.STICK), null
    };
    public static final ItemStack[] SWORD1 = new ItemStack[] {
            null, new ItemStack(Material.IRON_INGOT), null,
            null, new ItemStack(Material.IRON_INGOT), null,
            null, FinalTechItems.BUG, null
    };
    public static final ItemStack[] SWORD2 = new ItemStack[] {
            null, new ItemStack(Material.GOLD_INGOT), null,
            null, new ItemStack(Material.GOLD_INGOT), null,
            null, FinalTechItems.BUG, null
    };
    public static final ItemStack[] SWORD3 = new ItemStack[] {
            null, new ItemStack(Material.DIAMOND), null,
            null, new ItemStack(Material.DIAMOND), null,
            null, FinalTechItems.BUG, null
    };
    public static final ItemStack[] SWORD4 = new ItemStack[] {
            null, new ItemStack(Material.OAK_WOOD), null,
            null, new ItemStack(Material.OAK_WOOD), null,
            null, FinalTechItems.BUG, null
    };

    /*electricity system*/
    // electric generator
    public static final ItemStack[] BASIC_GENERATOR = new ItemStack[] {
            SlimefunItems.SOLAR_PANEL, SlimefunItems.SOLAR_PANEL, SlimefunItems.SOLAR_PANEL,
            FinalTechItems.GEARWHEEL, FinalTechItems.ORDERED_DUST, FinalTechItems.GEARWHEEL,
            null, FinalTechItems.GEARWHEEL, null
    };
    public static final ItemStack[] ADVANCED_GENERATOR = new ItemStack[] {
            FinalTechItems.BASIC_GENERATOR, FinalTechItems.GEARWHEEL, FinalTechItems.BASIC_GENERATOR,
            FinalTechItems.GEARWHEEL, new ItemStack(Material.REDSTONE), FinalTechItems.GEARWHEEL,
            FinalTechItems.BASIC_GENERATOR, FinalTechItems.GEARWHEEL, FinalTechItems.BASIC_GENERATOR
    };
    public static final ItemStack[] CARBONADO_GENERATOR = new ItemStack[] {
            FinalTechItems.ADVANCED_GENERATOR, FinalTechItems.GEARWHEEL, FinalTechItems.ADVANCED_GENERATOR,
            FinalTechItems.GEARWHEEL, SlimefunItems.CARBONADO, FinalTechItems.GEARWHEEL,
            FinalTechItems.ADVANCED_GENERATOR, FinalTechItems.GEARWHEEL, FinalTechItems.ADVANCED_GENERATOR
    };
    public static final ItemStack[] ENERGIZED_GENERATOR = new ItemStack[] {
            FinalTechItems.CARBONADO_GENERATOR, FinalTechItems.BUG, FinalTechItems.CARBONADO_GENERATOR,
            FinalTechItems.BUG, FinalTechItems.UNORDERED_DUST, FinalTechItems.BUG,
            FinalTechItems.CARBONADO_GENERATOR, FinalTechItems.BUG, FinalTechItems.CARBONADO_GENERATOR
    };
    public static final ItemStack[] ENERGIZED_STACK_GENERATOR = new ItemStack[] {
            ItemStackUtil.cloneItem(FinalTechItems.GEARWHEEL, 20), ItemStackUtil.cloneItem(FinalTechItems.GEARWHEEL, 20), ItemStackUtil.cloneItem(FinalTechItems.GEARWHEEL, 20), ItemStackUtil.cloneItem(FinalTechItems.GEARWHEEL, 20), ItemStackUtil.cloneItem(FinalTechItems.GEARWHEEL, 20), ItemStackUtil.cloneItem(FinalTechItems.GEARWHEEL, 20),
            ItemStackUtil.cloneItem(FinalTechItems.GEARWHEEL, 20), FinalTechItems.ENERGIZED_GENERATOR, ItemStackUtil.cloneItem(FinalTechItems.ORDERED_DUST, 32), ItemStackUtil.cloneItem(FinalTechItems.ORDERED_DUST, 32), FinalTechItems.ENERGIZED_GENERATOR, ItemStackUtil.cloneItem(FinalTechItems.GEARWHEEL, 20),
            ItemStackUtil.cloneItem(FinalTechItems.GEARWHEEL, 20), ItemStackUtil.cloneItem(FinalTechItems.ORDERED_DUST, 32), FinalTechItems.ENERGIZED_GENERATOR, FinalTechItems.ENERGIZED_GENERATOR, ItemStackUtil.cloneItem(FinalTechItems.ORDERED_DUST, 32), ItemStackUtil.cloneItem(FinalTechItems.GEARWHEEL, 20),
            ItemStackUtil.cloneItem(FinalTechItems.GEARWHEEL, 20), ItemStackUtil.cloneItem(FinalTechItems.ORDERED_DUST, 32), FinalTechItems.ENERGIZED_GENERATOR, FinalTechItems.ENERGIZED_GENERATOR, ItemStackUtil.cloneItem(FinalTechItems.ORDERED_DUST, 32), ItemStackUtil.cloneItem(FinalTechItems.GEARWHEEL, 20),
            ItemStackUtil.cloneItem(FinalTechItems.GEARWHEEL, 20), FinalTechItems.ENERGIZED_GENERATOR, ItemStackUtil.cloneItem(FinalTechItems.ORDERED_DUST, 32), ItemStackUtil.cloneItem(FinalTechItems.ORDERED_DUST, 32), FinalTechItems.ENERGIZED_GENERATOR, ItemStackUtil.cloneItem(FinalTechItems.GEARWHEEL, 20),
            ItemStackUtil.cloneItem(FinalTechItems.GEARWHEEL, 20), ItemStackUtil.cloneItem(FinalTechItems.GEARWHEEL, 20), ItemStackUtil.cloneItem(FinalTechItems.GEARWHEEL, 20), ItemStackUtil.cloneItem(FinalTechItems.GEARWHEEL, 20), ItemStackUtil.cloneItem(FinalTechItems.GEARWHEEL, 20), ItemStackUtil.cloneItem(FinalTechItems.GEARWHEEL, 20)
    };
    public static final ItemStack[] OVERLOADED_GENERATOR = new ItemStack[] {
            ItemStackUtil.cloneItem(FinalTechItems.GEARWHEEL, 20), ItemStackUtil.cloneItem(FinalTechItems.GEARWHEEL, 20), ItemStackUtil.cloneItem(FinalTechItems.GEARWHEEL, 20), ItemStackUtil.cloneItem(FinalTechItems.GEARWHEEL, 20), ItemStackUtil.cloneItem(FinalTechItems.GEARWHEEL, 20), ItemStackUtil.cloneItem(FinalTechItems.GEARWHEEL, 20),
            ItemStackUtil.cloneItem(FinalTechItems.GEARWHEEL, 20), ItemStackUtil.cloneItem(FinalTechItems.ORDERED_DUST, 32), FinalTechItems.ENERGIZED_GENERATOR, FinalTechItems.ENERGIZED_GENERATOR, ItemStackUtil.cloneItem(FinalTechItems.ORDERED_DUST, 32), ItemStackUtil.cloneItem(FinalTechItems.GEARWHEEL, 20),
            ItemStackUtil.cloneItem(FinalTechItems.GEARWHEEL, 20), FinalTechItems.ENERGIZED_GENERATOR, ItemStackUtil.cloneItem(FinalTechItems.ORDERED_DUST, 32), ItemStackUtil.cloneItem(FinalTechItems.ORDERED_DUST, 32), FinalTechItems.ENERGIZED_GENERATOR, ItemStackUtil.cloneItem(FinalTechItems.GEARWHEEL, 20),
            ItemStackUtil.cloneItem(FinalTechItems.GEARWHEEL, 20), FinalTechItems.ENERGIZED_GENERATOR, ItemStackUtil.cloneItem(FinalTechItems.ORDERED_DUST, 32), ItemStackUtil.cloneItem(FinalTechItems.ORDERED_DUST, 32), FinalTechItems.ENERGIZED_GENERATOR, ItemStackUtil.cloneItem(FinalTechItems.GEARWHEEL, 20),
            ItemStackUtil.cloneItem(FinalTechItems.GEARWHEEL, 20), ItemStackUtil.cloneItem(FinalTechItems.ORDERED_DUST, 32), FinalTechItems.ENERGIZED_GENERATOR, FinalTechItems.ENERGIZED_GENERATOR, ItemStackUtil.cloneItem(FinalTechItems.ORDERED_DUST, 32), ItemStackUtil.cloneItem(FinalTechItems.GEARWHEEL, 20),
            ItemStackUtil.cloneItem(FinalTechItems.GEARWHEEL, 20), ItemStackUtil.cloneItem(FinalTechItems.GEARWHEEL, 20), ItemStackUtil.cloneItem(FinalTechItems.GEARWHEEL, 20), ItemStackUtil.cloneItem(FinalTechItems.GEARWHEEL, 20), ItemStackUtil.cloneItem(FinalTechItems.GEARWHEEL, 20), ItemStackUtil.cloneItem(FinalTechItems.GEARWHEEL, 20)
    };
    public static final ItemStack[] ORDERED_DUST_GENERATOR = new ItemStack[] {
            FinalTechItems.ENTROPY, FinalTechItems.ENTROPY, FinalTechItems.ENTROPY, FinalTechItems.ENTROPY, FinalTechItems.ENTROPY, null,
            FinalTechItems.ENTROPY, null, null, null, null, null,
            FinalTechItems.ENTROPY, FinalTechItems.ENTROPY, FinalTechItems.ENTROPY, null, FinalTechItems.ENTROPY, null,
            FinalTechItems.ENTROPY, null, null, null, null, null,
            FinalTechItems.ENTROPY, FinalTechItems.ENTROPY, FinalTechItems.ENTROPY, FinalTechItems.ENTROPY, FinalTechItems.ENTROPY, null
    };
    public static final ItemStack[] ENERGIZED_CHARGE_BASE = new ItemStack[]{
            FinalTechItems.BUG, FinalTechItems.BUG, FinalTechItems.BUG, FinalTechItems.BUG, FinalTechItems.BUG, null,
            null, FinalTechItems.GEARWHEEL, FinalTechItems.ENERGIZED_GENERATOR, FinalTechItems.GEARWHEEL, null, null,
            null, null, FinalTechItems.GEARWHEEL, null, null, null
    };
    public static final ItemStack[] OVERLOADED_CHARGE_BASE = new ItemStack[] {
            null, null, FinalTechItems.GEARWHEEL, null, null, null,
            null, FinalTechItems.GEARWHEEL, FinalTechItems.ENERGIZED_GENERATOR, FinalTechItems.GEARWHEEL, null, null,
            FinalTechItems.BUG, FinalTechItems.BUG, FinalTechItems.BUG, FinalTechItems.BUG, FinalTechItems.BUG, null,
    };
    // electric storage
    public static final ItemStack[] BASIC_CHARGE_INCREASE_CAPACITOR = new ItemStack[] {
            FinalTechItems.GEARWHEEL, FinalTechItems.BUG, FinalTechItems.GEARWHEEL,
            null, SlimefunItems.ENERGIZED_CAPACITOR, null,
            FinalTechItems.GEARWHEEL, null, FinalTechItems.GEARWHEEL
    };
    public static final ItemStack[] BASIC_CONSUME_REDUCE_CAPACITOR = new ItemStack[] {
            FinalTechItems.GEARWHEEL, null, FinalTechItems.GEARWHEEL,
            null, SlimefunItems.ENERGIZED_CAPACITOR, null,
            FinalTechItems.GEARWHEEL, FinalTechItems.BUG, FinalTechItems.GEARWHEEL
    };
    public static final ItemStack[] SMALL_EXPANDED_CAPACITOR = new ItemStack[] {
            SlimefunItems.DURALUMIN_INGOT, FinalTechItems.BASIC_CHARGE_INCREASE_CAPACITOR, SlimefunItems.DURALUMIN_INGOT,
            FinalTechItems.GEARWHEEL, SlimefunItems.ENERGIZED_CAPACITOR, FinalTechItems.GEARWHEEL,
            SlimefunItems.DURALUMIN_INGOT, FinalTechItems.BASIC_CONSUME_REDUCE_CAPACITOR, SlimefunItems.DURALUMIN_INGOT
    };
    public static final ItemStack[] MEDIUM_EXPANDED_CAPACITOR = new ItemStack[] {
            SlimefunItems.BILLON_INGOT, FinalTechItems.BASIC_CHARGE_INCREASE_CAPACITOR, SlimefunItems.BILLON_INGOT,
            FinalTechItems.GEARWHEEL, FinalTechItems.SMALL_EXPANDED_CAPACITOR, FinalTechItems.GEARWHEEL,
            SlimefunItems.BILLON_INGOT, FinalTechItems.BASIC_CONSUME_REDUCE_CAPACITOR, SlimefunItems.BILLON_INGOT
    };
    public static final ItemStack[] BIG_EXPANDED_CAPACITOR = new ItemStack[] {
            SlimefunItems.STEEL_INGOT, FinalTechItems.BASIC_CHARGE_INCREASE_CAPACITOR, SlimefunItems.STEEL_INGOT,
            FinalTechItems.GEARWHEEL, FinalTechItems.MEDIUM_EXPANDED_CAPACITOR, FinalTechItems.GEARWHEEL,
            SlimefunItems.STEEL_INGOT, FinalTechItems.BASIC_CONSUME_REDUCE_CAPACITOR, SlimefunItems.STEEL_INGOT
    };
    public static final ItemStack[] LARGE_EXPANDED_CAPACITOR = new ItemStack[] {
            SlimefunItems.REINFORCED_ALLOY_INGOT, FinalTechItems.BASIC_CHARGE_INCREASE_CAPACITOR, SlimefunItems.REINFORCED_ALLOY_INGOT,
            FinalTechItems.GEARWHEEL, FinalTechItems.BIG_EXPANDED_CAPACITOR, FinalTechItems.GEARWHEEL,
            SlimefunItems.REINFORCED_ALLOY_INGOT, FinalTechItems.BASIC_CONSUME_REDUCE_CAPACITOR, SlimefunItems.REINFORCED_ALLOY_INGOT
    };
    public static final ItemStack[] CARBONADO_EXPANDED_CAPACITOR = new ItemStack[] {
            SlimefunItems.CARBONADO, FinalTechItems.BASIC_CHARGE_INCREASE_CAPACITOR, SlimefunItems.CARBONADO,
            FinalTechItems.GEARWHEEL, FinalTechItems.LARGE_EXPANDED_CAPACITOR, FinalTechItems.GEARWHEEL,
            SlimefunItems.CARBONADO, FinalTechItems.BASIC_CONSUME_REDUCE_CAPACITOR, SlimefunItems.CARBONADO
    };
    public static final ItemStack[] ENERGIZED_EXPANDED_CAPACITOR = new ItemStack[] {
            SlimefunItems.CARBONADO, FinalTechItems.BASIC_CHARGE_INCREASE_CAPACITOR, SlimefunItems.CARBONADO,
            FinalTechItems.BUG, FinalTechItems.CARBONADO_EXPANDED_CAPACITOR, FinalTechItems.BUG,
            SlimefunItems.CARBONADO, FinalTechItems.BASIC_CONSUME_REDUCE_CAPACITOR, SlimefunItems.CARBONADO
    };
    public static final ItemStack[] ENERGIZED_STACK_EXPANDED_CAPACITOR = new ItemStack[] {
            ItemStackUtil.cloneItem(FinalTechItems.GEARWHEEL, 12), null, null, null, null, ItemStackUtil.cloneItem(FinalTechItems.GEARWHEEL, 12),
            ItemStackUtil.cloneItem(FinalTechItems.GEARWHEEL, 12), FinalTechItems.ENERGIZED_EXPANDED_CAPACITOR, ItemStackUtil.cloneItem(FinalTechItems.ORDERED_DUST, 20), ItemStackUtil.cloneItem(FinalTechItems.ORDERED_DUST, 20), FinalTechItems.ENERGIZED_EXPANDED_CAPACITOR, ItemStackUtil.cloneItem(FinalTechItems.GEARWHEEL, 12),
            ItemStackUtil.cloneItem(FinalTechItems.GEARWHEEL, 12), ItemStackUtil.cloneItem(FinalTechItems.ORDERED_DUST, 20), FinalTechItems.ENERGIZED_EXPANDED_CAPACITOR, FinalTechItems.ENERGIZED_EXPANDED_CAPACITOR, ItemStackUtil.cloneItem(FinalTechItems.ORDERED_DUST, 20), ItemStackUtil.cloneItem(FinalTechItems.GEARWHEEL, 12),
            ItemStackUtil.cloneItem(FinalTechItems.GEARWHEEL, 12), ItemStackUtil.cloneItem(FinalTechItems.ORDERED_DUST, 20), FinalTechItems.ENERGIZED_EXPANDED_CAPACITOR, FinalTechItems.ENERGIZED_EXPANDED_CAPACITOR, ItemStackUtil.cloneItem(FinalTechItems.ORDERED_DUST, 20), ItemStackUtil.cloneItem(FinalTechItems.GEARWHEEL, 12),
            ItemStackUtil.cloneItem(FinalTechItems.GEARWHEEL, 12), FinalTechItems.ENERGIZED_EXPANDED_CAPACITOR, ItemStackUtil.cloneItem(FinalTechItems.ORDERED_DUST, 20), ItemStackUtil.cloneItem(FinalTechItems.ORDERED_DUST, 20), FinalTechItems.ENERGIZED_EXPANDED_CAPACITOR, ItemStackUtil.cloneItem(FinalTechItems.GEARWHEEL, 12),
            ItemStackUtil.cloneItem(FinalTechItems.GEARWHEEL, 12), null, null, null, null, ItemStackUtil.cloneItem(FinalTechItems.GEARWHEEL, 12)
    };
    public static final ItemStack[] OVERLOADED_EXPANDED_CAPACITOR = new ItemStack[] {
            ItemStackUtil.cloneItem(FinalTechItems.GEARWHEEL, 12), null, null, null, null, ItemStackUtil.cloneItem(FinalTechItems.GEARWHEEL, 12),
            ItemStackUtil.cloneItem(FinalTechItems.GEARWHEEL, 12), ItemStackUtil.cloneItem(FinalTechItems.ORDERED_DUST, 20), FinalTechItems.ENERGIZED_EXPANDED_CAPACITOR, FinalTechItems.ENERGIZED_EXPANDED_CAPACITOR, ItemStackUtil.cloneItem(FinalTechItems.ORDERED_DUST, 20), ItemStackUtil.cloneItem(FinalTechItems.GEARWHEEL, 12),
            ItemStackUtil.cloneItem(FinalTechItems.GEARWHEEL, 12), FinalTechItems.ENERGIZED_EXPANDED_CAPACITOR, ItemStackUtil.cloneItem(FinalTechItems.ORDERED_DUST, 20), ItemStackUtil.cloneItem(FinalTechItems.ORDERED_DUST, 20), FinalTechItems.ENERGIZED_EXPANDED_CAPACITOR, ItemStackUtil.cloneItem(FinalTechItems.GEARWHEEL, 12),
            ItemStackUtil.cloneItem(FinalTechItems.GEARWHEEL, 12), FinalTechItems.ENERGIZED_EXPANDED_CAPACITOR, ItemStackUtil.cloneItem(FinalTechItems.ORDERED_DUST, 20), ItemStackUtil.cloneItem(FinalTechItems.ORDERED_DUST, 20), FinalTechItems.ENERGIZED_EXPANDED_CAPACITOR, ItemStackUtil.cloneItem(FinalTechItems.GEARWHEEL, 12),
            ItemStackUtil.cloneItem(FinalTechItems.GEARWHEEL, 12), ItemStackUtil.cloneItem(FinalTechItems.ORDERED_DUST, 20), FinalTechItems.ENERGIZED_EXPANDED_CAPACITOR, FinalTechItems.ENERGIZED_EXPANDED_CAPACITOR, ItemStackUtil.cloneItem(FinalTechItems.ORDERED_DUST, 20), ItemStackUtil.cloneItem(FinalTechItems.GEARWHEEL, 12),
            ItemStackUtil.cloneItem(FinalTechItems.GEARWHEEL, 12), null, null, null, null, ItemStackUtil.cloneItem(FinalTechItems.GEARWHEEL, 12)
    };
    public static final ItemStack[] ADVANCED_CHARGE_INCREASE_CAPACITOR = new ItemStack[] {
            ItemStackUtil.cloneItem(FinalTechItems.GEARWHEEL, 12), ItemStackUtil.cloneItem(FinalTechItems.ORDERED_DUST, 20), ItemStackUtil.cloneItem(FinalTechItems.ORDERED_DUST, 20), ItemStackUtil.cloneItem(FinalTechItems.ORDERED_DUST, 20), ItemStackUtil.cloneItem(FinalTechItems.ORDERED_DUST, 20), ItemStackUtil.cloneItem(FinalTechItems.GEARWHEEL, 12),
            ItemStackUtil.cloneItem(FinalTechItems.GEARWHEEL, 12), ItemStackUtil.cloneItem(FinalTechItems.ORDERED_DUST, 20), ItemStackUtil.cloneItem(FinalTechItems.ORDERED_DUST, 20), ItemStackUtil.cloneItem(FinalTechItems.ORDERED_DUST, 20), ItemStackUtil.cloneItem(FinalTechItems.ORDERED_DUST, 20), ItemStackUtil.cloneItem(FinalTechItems.GEARWHEEL, 12),
            ItemStackUtil.cloneItem(FinalTechItems.GEARWHEEL, 12), FinalTechItems.ENERGIZED_EXPANDED_CAPACITOR, FinalTechItems.ENERGIZED_EXPANDED_CAPACITOR, FinalTechItems.ENERGIZED_EXPANDED_CAPACITOR, FinalTechItems.ENERGIZED_EXPANDED_CAPACITOR, ItemStackUtil.cloneItem(FinalTechItems.GEARWHEEL, 12),
            ItemStackUtil.cloneItem(FinalTechItems.GEARWHEEL, 12), FinalTechItems.ENERGIZED_EXPANDED_CAPACITOR, FinalTechItems.ENERGIZED_EXPANDED_CAPACITOR, FinalTechItems.ENERGIZED_EXPANDED_CAPACITOR, FinalTechItems.ENERGIZED_EXPANDED_CAPACITOR, ItemStackUtil.cloneItem(FinalTechItems.GEARWHEEL, 12),
            ItemStackUtil.cloneItem(FinalTechItems.GEARWHEEL, 12), null, null, null, null, ItemStackUtil.cloneItem(FinalTechItems.GEARWHEEL, 12),
            ItemStackUtil.cloneItem(FinalTechItems.GEARWHEEL, 12), null, null, null, null, ItemStackUtil.cloneItem(FinalTechItems.GEARWHEEL, 12)
    };
    public static final ItemStack[] ADVANCED_CONSUME_REDUCE_CAPACITOR = new ItemStack[] {
            ItemStackUtil.cloneItem(FinalTechItems.GEARWHEEL, 12), null, null, null, null, ItemStackUtil.cloneItem(FinalTechItems.GEARWHEEL, 12),
            ItemStackUtil.cloneItem(FinalTechItems.GEARWHEEL, 12), null, null, null, null, ItemStackUtil.cloneItem(FinalTechItems.GEARWHEEL, 12),
            ItemStackUtil.cloneItem(FinalTechItems.GEARWHEEL, 12), FinalTechItems.ENERGIZED_EXPANDED_CAPACITOR, FinalTechItems.ENERGIZED_EXPANDED_CAPACITOR, FinalTechItems.ENERGIZED_EXPANDED_CAPACITOR, FinalTechItems.ENERGIZED_EXPANDED_CAPACITOR, ItemStackUtil.cloneItem(FinalTechItems.GEARWHEEL, 12),
            ItemStackUtil.cloneItem(FinalTechItems.GEARWHEEL, 12), FinalTechItems.ENERGIZED_EXPANDED_CAPACITOR, FinalTechItems.ENERGIZED_EXPANDED_CAPACITOR, FinalTechItems.ENERGIZED_EXPANDED_CAPACITOR, FinalTechItems.ENERGIZED_EXPANDED_CAPACITOR, ItemStackUtil.cloneItem(FinalTechItems.GEARWHEEL, 12),
            ItemStackUtil.cloneItem(FinalTechItems.GEARWHEEL, 12), ItemStackUtil.cloneItem(FinalTechItems.ORDERED_DUST, 20), ItemStackUtil.cloneItem(FinalTechItems.ORDERED_DUST, 20), ItemStackUtil.cloneItem(FinalTechItems.ORDERED_DUST, 20), ItemStackUtil.cloneItem(FinalTechItems.ORDERED_DUST, 20), ItemStackUtil.cloneItem(FinalTechItems.GEARWHEEL, 12),
            ItemStackUtil.cloneItem(FinalTechItems.GEARWHEEL, 12), ItemStackUtil.cloneItem(FinalTechItems.ORDERED_DUST, 20), ItemStackUtil.cloneItem(FinalTechItems.ORDERED_DUST, 20), ItemStackUtil.cloneItem(FinalTechItems.ORDERED_DUST, 20), ItemStackUtil.cloneItem(FinalTechItems.ORDERED_DUST, 20), ItemStackUtil.cloneItem(FinalTechItems.GEARWHEEL, 12)
    };
    // electric transmission
    public static final ItemStack[] NORMAL_ELECTRICITY_SHOOT_PILE = new ItemStack[] {
            FinalTechItems.GEARWHEEL, FinalTechItems.GEARWHEEL, FinalTechItems.GEARWHEEL,
            FinalTechItems.GEARWHEEL, SlimefunItems.ENERGY_CONNECTOR, FinalTechItems.GEARWHEEL,
            FinalTechItems.GEARWHEEL, FinalTechItems.GEARWHEEL, FinalTechItems.GEARWHEEL
    };
    public static final ItemStack[] ENERGIZED_ELECTRICITY_SHOOT_PILE = new ItemStack[] {
            FinalTechItems.ORDERED_DUST, FinalTechItems.GEARWHEEL, FinalTechItems.ORDERED_DUST,
            FinalTechItems.GEARWHEEL, FinalTechItems.NORMAL_ELECTRICITY_SHOOT_PILE, FinalTechItems.GEARWHEEL,
            FinalTechItems.ORDERED_DUST, FinalTechItems.GEARWHEEL, FinalTechItems.ORDERED_DUST
    };
    public static final ItemStack[] OVERLOADED_ELECTRICITY_SHOOT_PILE = new ItemStack[] {
            FinalTechItems.GEARWHEEL, FinalTechItems.ORDERED_DUST, FinalTechItems.GEARWHEEL,
            FinalTechItems.ORDERED_DUST, FinalTechItems.NORMAL_ELECTRICITY_SHOOT_PILE, FinalTechItems.ORDERED_DUST,
            FinalTechItems.GEARWHEEL, FinalTechItems.ORDERED_DUST, FinalTechItems.GEARWHEEL
    };
    public static final ItemStack[] DISPERSAL_CAPACITOR = new ItemStack[] {
            SlimefunItems.ENERGY_CONNECTOR, SlimefunItems.ENERGY_CONNECTOR, SlimefunItems.ENERGY_CONNECTOR, SlimefunItems.ENERGY_CONNECTOR, SlimefunItems.ENERGY_CONNECTOR, SlimefunItems.ENERGY_CONNECTOR,
            SlimefunItems.ENERGY_CONNECTOR, FinalTechItems.CARBONADO_EXPANDED_CAPACITOR, null, null, FinalTechItems.CARBONADO_EXPANDED_CAPACITOR, SlimefunItems.ENERGY_CONNECTOR,
            SlimefunItems.ENERGY_CONNECTOR, null, null, null, null, SlimefunItems.ENERGY_CONNECTOR,
            SlimefunItems.ENERGY_CONNECTOR, null, null, null, null, SlimefunItems.ENERGY_CONNECTOR,
            SlimefunItems.ENERGY_CONNECTOR, FinalTechItems.CARBONADO_EXPANDED_CAPACITOR, null, null, FinalTechItems.CARBONADO_EXPANDED_CAPACITOR, SlimefunItems.ENERGY_CONNECTOR,
            SlimefunItems.ENERGY_CONNECTOR, SlimefunItems.ENERGY_CONNECTOR, SlimefunItems.ENERGY_CONNECTOR, SlimefunItems.ENERGY_CONNECTOR, SlimefunItems.ENERGY_CONNECTOR, SlimefunItems.ENERGY_CONNECTOR
    };
    public static final ItemStack[] VARIABLE_WIRE_RESISTANCE = new ItemStack[] {
            FinalTechItems.GEARWHEEL, SlimefunItems.ENERGY_CONNECTOR, FinalTechItems.GEARWHEEL,
            SlimefunItems.ENERGY_CONNECTOR, SlimefunItems.CARBONADO_EDGED_CAPACITOR, SlimefunItems.ENERGY_CONNECTOR,
            FinalTechItems.GEARWHEEL, SlimefunItems.ENERGY_CONNECTOR, FinalTechItems.GEARWHEEL
    };
    public static final ItemStack[] VARIABLE_WIRE_CAPACITOR = new ItemStack[] {
            SlimefunItems.ENERGY_CONNECTOR, FinalTechItems.GEARWHEEL, SlimefunItems.ENERGY_CONNECTOR,
            FinalTechItems.GEARWHEEL, SlimefunItems.CARBONADO_EDGED_CAPACITOR, FinalTechItems.GEARWHEEL,
            SlimefunItems.ENERGY_CONNECTOR, FinalTechItems.GEARWHEEL, SlimefunItems.ENERGY_CONNECTOR
    };
    // electric accelerator
    public static final ItemStack[] ENERGIZED_ACCELERATOR = new ItemStack[] {
            FinalTechItems.ANNULAR, FinalTechItems.ANNULAR, FinalTechItems.ANNULAR, FinalTechItems.ANNULAR, FinalTechItems.ANNULAR, FinalTechItems.ANNULAR,
            FinalTechItems.ANNULAR, ItemStackUtil.cloneItem(FinalTechItems.MACHINE_CHARGE_CARD_L2, 20), ItemStackUtil.cloneItem(FinalTechItems.MACHINE_ACCELERATE_CARD_L2, 20), ItemStackUtil.cloneItem(FinalTechItems.MACHINE_ACCELERATE_CARD_L2, 20), ItemStackUtil.cloneItem(FinalTechItems.MACHINE_CHARGE_CARD_L2, 20), FinalTechItems.ANNULAR,
            FinalTechItems.ANNULAR, ItemStackUtil.cloneItem(FinalTechItems.MACHINE_ACCELERATE_CARD_L2, 20), ItemStackUtil.cloneItem(FinalTechItems.MACHINE_CHARGE_CARD_L2, 20), ItemStackUtil.cloneItem(FinalTechItems.MACHINE_CHARGE_CARD_L2, 20), ItemStackUtil.cloneItem(FinalTechItems.MACHINE_ACCELERATE_CARD_L2, 20), FinalTechItems.ANNULAR,
            FinalTechItems.ANNULAR, ItemStackUtil.cloneItem(FinalTechItems.MACHINE_ACCELERATE_CARD_L2, 20), ItemStackUtil.cloneItem(FinalTechItems.MACHINE_CHARGE_CARD_L2, 20), ItemStackUtil.cloneItem(FinalTechItems.MACHINE_CHARGE_CARD_L2, 20), ItemStackUtil.cloneItem(FinalTechItems.MACHINE_ACCELERATE_CARD_L2, 20), FinalTechItems.ANNULAR,
            FinalTechItems.ANNULAR, ItemStackUtil.cloneItem(FinalTechItems.MACHINE_CHARGE_CARD_L2, 20), ItemStackUtil.cloneItem(FinalTechItems.MACHINE_ACCELERATE_CARD_L2, 20), ItemStackUtil.cloneItem(FinalTechItems.MACHINE_ACCELERATE_CARD_L2, 20), ItemStackUtil.cloneItem(FinalTechItems.MACHINE_CHARGE_CARD_L2, 20), FinalTechItems.ANNULAR,
            FinalTechItems.ANNULAR, FinalTechItems.ANNULAR, FinalTechItems.ANNULAR, FinalTechItems.ANNULAR, FinalTechItems.ANNULAR, FinalTechItems.ANNULAR
    };
    public static final ItemStack[] OVERLOADED_ACCELERATOR = new ItemStack[] {
            FinalTechItems.ANNULAR, FinalTechItems.ANNULAR, FinalTechItems.ANNULAR, FinalTechItems.ANNULAR, FinalTechItems.ANNULAR, FinalTechItems.ANNULAR,
            FinalTechItems.ANNULAR, ItemStackUtil.cloneItem(FinalTechItems.MACHINE_ACCELERATE_CARD_L2, 20), ItemStackUtil.cloneItem(FinalTechItems.MACHINE_CHARGE_CARD_L2, 20), ItemStackUtil.cloneItem(FinalTechItems.MACHINE_CHARGE_CARD_L2, 20), ItemStackUtil.cloneItem(FinalTechItems.MACHINE_ACCELERATE_CARD_L2, 20), FinalTechItems.ANNULAR,
            FinalTechItems.ANNULAR, ItemStackUtil.cloneItem(FinalTechItems.MACHINE_CHARGE_CARD_L2, 20), ItemStackUtil.cloneItem(FinalTechItems.MACHINE_ACCELERATE_CARD_L2, 20), ItemStackUtil.cloneItem(FinalTechItems.MACHINE_ACCELERATE_CARD_L2, 20), ItemStackUtil.cloneItem(FinalTechItems.MACHINE_CHARGE_CARD_L2, 20), FinalTechItems.ANNULAR,
            FinalTechItems.ANNULAR, ItemStackUtil.cloneItem(FinalTechItems.MACHINE_CHARGE_CARD_L2, 20), ItemStackUtil.cloneItem(FinalTechItems.MACHINE_ACCELERATE_CARD_L2, 20), ItemStackUtil.cloneItem(FinalTechItems.MACHINE_ACCELERATE_CARD_L2, 20), ItemStackUtil.cloneItem(FinalTechItems.MACHINE_CHARGE_CARD_L2, 20), FinalTechItems.ANNULAR,
            FinalTechItems.ANNULAR, ItemStackUtil.cloneItem(FinalTechItems.MACHINE_ACCELERATE_CARD_L2, 20), ItemStackUtil.cloneItem(FinalTechItems.MACHINE_CHARGE_CARD_L2, 20), ItemStackUtil.cloneItem(FinalTechItems.MACHINE_CHARGE_CARD_L2, 20), ItemStackUtil.cloneItem(FinalTechItems.MACHINE_ACCELERATE_CARD_L2, 20), FinalTechItems.ANNULAR,
            FinalTechItems.ANNULAR, FinalTechItems.ANNULAR, FinalTechItems.ANNULAR, FinalTechItems.ANNULAR, FinalTechItems.ANNULAR, FinalTechItems.ANNULAR
    };

    /* cargo system */
    // storage unit
    public static final ItemStack[] NORMAL_STORAGE_UNIT = new ItemStack[] {
            new ItemStack(Material.GLASS), new ItemStack(Material.CHEST), new ItemStack(Material.GLASS),
            new ItemStack(Material.CHEST), null, new ItemStack(Material.CHEST),
            new ItemStack(Material.GLASS), new ItemStack(Material.CHEST), new ItemStack(Material.GLASS),
    };
    public static final ItemStack[] DIVIDED_STORAGE_UNIT = new ItemStack[] {
            null, FinalTechItems.GEARWHEEL, null,
            null, FinalTechItems.NORMAL_STORAGE_UNIT, null,
            null, FinalTechItems.GEARWHEEL, null,
    };
    public static final ItemStack[] LIMITED_STORAGE_UNIT = new ItemStack[] {
            null, null, null,
            FinalTechItems.GEARWHEEL, FinalTechItems.NORMAL_STORAGE_UNIT, FinalTechItems.GEARWHEEL,
            null, null, null,
    };
    public static final ItemStack[] STACK_STORAGE_UNIT = new ItemStack[] {
            FinalTechItems.GEARWHEEL, null, FinalTechItems.GEARWHEEL,
            null, FinalTechItems.NORMAL_STORAGE_UNIT, null,
            FinalTechItems.GEARWHEEL, null, FinalTechItems.GEARWHEEL,
    };
    public static final ItemStack[] DIVIDED_LIMITED_STORAGE_UNIT = new ItemStack[] {
            null, FinalTechItems.GEARWHEEL, null,
            FinalTechItems.GEARWHEEL, FinalTechItems.NORMAL_STORAGE_UNIT, FinalTechItems.GEARWHEEL,
            null, FinalTechItems.GEARWHEEL, null,
    };
    public static final ItemStack[] DIVIDED_STACK_STORAGE_UNIT = new ItemStack[] {
            FinalTechItems.GEARWHEEL, FinalTechItems.GEARWHEEL, FinalTechItems.GEARWHEEL,
            null, FinalTechItems.NORMAL_STORAGE_UNIT, null,
            FinalTechItems.GEARWHEEL, FinalTechItems.GEARWHEEL, FinalTechItems.GEARWHEEL,
    };
    public static final ItemStack[] LIMITED_STACK_STORAGE_UNIT = new ItemStack[] {
            FinalTechItems.GEARWHEEL, null, FinalTechItems.GEARWHEEL,
            FinalTechItems.GEARWHEEL, FinalTechItems.NORMAL_STORAGE_UNIT, FinalTechItems.GEARWHEEL,
            FinalTechItems.GEARWHEEL, null, FinalTechItems.GEARWHEEL,
    };
    public static final ItemStack[] RANDOM_INPUT_STORAGE_UNIT = new ItemStack[] {
            null, FinalTechItems.ORDERED_DUST, null,
            null, FinalTechItems.NORMAL_STORAGE_UNIT, null,
            null, null, null,
    };
    public static final ItemStack[] RANDOM_OUTPUT_STORAGE_UNIT = new ItemStack[] {
            null, null, null,
            null, FinalTechItems.NORMAL_STORAGE_UNIT, null,
            null, FinalTechItems.ORDERED_DUST, null,
    };
    public static final ItemStack[] RANDOM_ACCESS_STORAGE_UNIT = new ItemStack[] {
            null, FinalTechItems.ORDERED_DUST, null,
            null, FinalTechItems.NORMAL_STORAGE_UNIT, null,
            null, FinalTechItems.ORDERED_DUST, null,
    };
    public static final ItemStack[] DISTRIBUTE_LEFT_STORAGE_UNIT = new ItemStack[] {
            null, null, null,
            FinalTechItems.ORDERED_DUST, FinalTechItems.NORMAL_STORAGE_UNIT, null,
            null, null, null,
    };
    public static final ItemStack[] DISTRIBUTE_RIGHT_STORAGE_UNIT = new ItemStack[] {
            null, null, null,
            null, FinalTechItems.NORMAL_STORAGE_UNIT, FinalTechItems.ORDERED_DUST,
            null, null, null,
    };
    // advanced storage
    public static final ItemStack[] STORAGE_INTERACT_PORT = new ItemStack[] {
            new ItemStack(Material.STONE), new ItemStack(Material.STONE), new ItemStack(Material.STONE),
            FinalTechItems.GEARWHEEL, FinalTechItems.GEARWHEEL, FinalTechItems.GEARWHEEL,
            new ItemStack(Material.STONE), new ItemStack(Material.STONE), new ItemStack(Material.STONE)
    };
    public static final ItemStack[] STORAGE_INSERT_PORT = new ItemStack[] {
            FinalTechItems.GEARWHEEL, FinalTechItems.GEARWHEEL, FinalTechItems.GEARWHEEL,
            new ItemStack(Material.STONE), new ItemStack(Material.STONE), new ItemStack(Material.STONE),
            new ItemStack(Material.STONE), new ItemStack(Material.STONE), new ItemStack(Material.STONE)
    };
    public static final ItemStack[] STORAGE_WITHDRAW_PORT = new ItemStack[] {
            new ItemStack(Material.STONE), new ItemStack(Material.STONE), new ItemStack(Material.STONE),
            new ItemStack(Material.STONE), new ItemStack(Material.STONE), new ItemStack(Material.STONE),
            FinalTechItems.GEARWHEEL, FinalTechItems.GEARWHEEL, FinalTechItems.GEARWHEEL
    };
    public static final ItemStack[] STORAGE_CARD = new ItemStack[] {
            new ItemStack(Material.TRAPPED_CHEST), new ItemStack(Material.TRAPPED_CHEST), new ItemStack(Material.TRAPPED_CHEST),
            new ItemStack(Material.TRAPPED_CHEST), FinalTechItems.ORDERED_DUST, new ItemStack(Material.TRAPPED_CHEST),
            new ItemStack(Material.TRAPPED_CHEST), new ItemStack(Material.TRAPPED_CHEST), new ItemStack(Material.TRAPPED_CHEST)
    };
    // accessor
    public static final ItemStack[] REMOTE_ACCESSOR = new ItemStack[] {
            FinalTechItems.GEARWHEEL, FinalTechItems.GEARWHEEL, FinalTechItems.GEARWHEEL,
            FinalTechItems.GEARWHEEL, new ItemStack(Material.OBSERVER), FinalTechItems.GEARWHEEL,
            FinalTechItems.GEARWHEEL, FinalTechItems.GEARWHEEL, FinalTechItems.GEARWHEEL
    };
    public static final ItemStack[] AREA_ACCESSOR = new ItemStack[] {
            FinalTechItems.GEARWHEEL, FinalTechItems.GEARWHEEL, FinalTechItems.GEARWHEEL,
            FinalTechItems.GEARWHEEL, new ItemStack(Material.TARGET), FinalTechItems.GEARWHEEL,
            FinalTechItems.GEARWHEEL, FinalTechItems.GEARWHEEL, FinalTechItems.GEARWHEEL
    };
    // cargo
    public static final ItemStack[] BASIC_FRAME_MACHINE = new ItemStack[] {
            new ItemStack(Material.STONE), new ItemStack(Material.CHAIN), new ItemStack(Material.STONE),
            new ItemStack(Material.CHAIN), FinalTechItems.ORDERED_DUST, new ItemStack(Material.CHAIN),
            new ItemStack(Material.STONE), new ItemStack(Material.CHAIN), new ItemStack(Material.STONE)
    };
    public static final ItemStack[] POINT_TRANSFER = new ItemStack[] {
            new ItemStack(Material.TRIPWIRE_HOOK), new ItemStack(Material.CHAIN), new ItemStack(Material.TRIPWIRE_HOOK),
            new ItemStack(Material.STRING), FinalTechItems.BASIC_FRAME_MACHINE, new ItemStack(Material.STRING),
            new ItemStack(Material.TRIPWIRE_HOOK), new ItemStack(Material.CHAIN), new ItemStack(Material.TRIPWIRE_HOOK)
    };
    public static final ItemStack[] MESH_TRANSFER = new ItemStack[] {
            new ItemStack(Material.HOPPER), new ItemStack(Material.TRIPWIRE_HOOK), new ItemStack(Material.CHAIN),
            new ItemStack(Material.HOPPER), FinalTechItems.BASIC_FRAME_MACHINE, new ItemStack(Material.STRING),
            new ItemStack(Material.HOPPER), new ItemStack(Material.TRIPWIRE_HOOK), new ItemStack(Material.CHAIN)
    };
    public static final ItemStack[] LINE_TRANSFER = new ItemStack[] {
            new ItemStack(Material.STRING), new ItemStack(Material.TRIPWIRE_HOOK), new ItemStack(Material.STRING),
            new ItemStack(Material.TRIPWIRE_HOOK), FinalTechItems.BASIC_FRAME_MACHINE, new ItemStack(Material.TRIPWIRE_HOOK),
            new ItemStack(Material.CHEST), new ItemStack(Material.CHEST), new ItemStack(Material.CHEST)
    };
    public static final ItemStack[] LOCATION_TRANSFER = new ItemStack[] {
            new ItemStack(Material.CHAIN), new ItemStack(Material.CHEST), new ItemStack(Material.CHAIN),
            new ItemStack(Material.TRIPWIRE_HOOK), FinalTechItems.BASIC_FRAME_MACHINE, new ItemStack(Material.TRIPWIRE_HOOK),
            new ItemStack(Material.TRIPWIRE_HOOK), new ItemStack(Material.STRING), new ItemStack(Material.TRIPWIRE_HOOK)
    };

    /* functional machines */
    // core machine
    public static final ItemStack[] ORDERED_DUST_FACTORY_DIRT = new ItemStack[] {
            SlimefunItems.PORTABLE_DUSTBIN, SlimefunItems.PORTABLE_DUSTBIN, SlimefunItems.PORTABLE_DUSTBIN,
            SlimefunItems.PORTABLE_DUSTBIN, new ItemStack(Material.DIRT), SlimefunItems.PORTABLE_DUSTBIN,
            SlimefunItems.PORTABLE_DUSTBIN, SlimefunItems.PORTABLE_DUSTBIN, SlimefunItems.PORTABLE_DUSTBIN
    };
    public static final ItemStack[] ORDERED_DUST_FACTORY_STONE = new ItemStack[] {
            SlimefunItems.PORTABLE_DUSTBIN, SlimefunItems.PORTABLE_DUSTBIN, SlimefunItems.PORTABLE_DUSTBIN,
            SlimefunItems.PORTABLE_DUSTBIN, new ItemStack(Material.COBBLESTONE), SlimefunItems.PORTABLE_DUSTBIN,
            SlimefunItems.PORTABLE_DUSTBIN, SlimefunItems.PORTABLE_DUSTBIN, SlimefunItems.PORTABLE_DUSTBIN
    };
    public static final ItemStack[] MATRIX_CRAFTING_TABLE = new ItemStack[] {
            FinalTechItems.ORDERED_DUST, FinalTechItems.ORDERED_DUST, FinalTechItems.ORDERED_DUST,
            FinalTechItems.ORDERED_DUST, new ItemStack(Material.ENDER_CHEST), FinalTechItems.ORDERED_DUST,
            FinalTechItems.ORDERED_DUST, FinalTechItems.ORDERED_DUST, FinalTechItems.ORDERED_DUST
    };
    public static final ItemStack[] ITEM_DISMANTLE_TABLE = new ItemStack[] {
            FinalTechItems.ORDERED_DUST, FinalTechItems.ORDERED_DUST, FinalTechItems.ORDERED_DUST, FinalTechItems.ORDERED_DUST, FinalTechItems.ORDERED_DUST, FinalTechItems.ORDERED_DUST,
            FinalTechItems.ORDERED_DUST, null, null, null, null, FinalTechItems.ORDERED_DUST,
            FinalTechItems.ORDERED_DUST, null, new CustomItemStack(Material.CRAFTING_TABLE), new CustomItemStack(Material.CRAFTING_TABLE), null, FinalTechItems.ORDERED_DUST,
            FinalTechItems.ORDERED_DUST, null, new CustomItemStack(Material.CRAFTING_TABLE), new CustomItemStack(Material.CRAFTING_TABLE), null, FinalTechItems.ORDERED_DUST,
            FinalTechItems.ORDERED_DUST, null, null, null, null, FinalTechItems.ORDERED_DUST,
            FinalTechItems.ORDERED_DUST, FinalTechItems.ORDERED_DUST, FinalTechItems.ORDERED_DUST, FinalTechItems.ORDERED_DUST, FinalTechItems.ORDERED_DUST, FinalTechItems.ORDERED_DUST
    };
    public static final ItemStack[] EQUIVALENT_EXCHANGE_TABLE = new ItemStack[] {
            FinalTechItems.ORDERED_DUST, FinalTechItems.ORDERED_DUST, FinalTechItems.ORDERED_DUST, FinalTechItems.ORDERED_DUST, FinalTechItems.ORDERED_DUST, FinalTechItems.ORDERED_DUST,
            FinalTechItems.ORDERED_DUST, null, null, null, null, FinalTechItems.ORDERED_DUST,
            FinalTechItems.ORDERED_DUST, null, new CustomItemStack(Material.CHEST), new CustomItemStack(Material.CHEST), null, FinalTechItems.ORDERED_DUST,
            FinalTechItems.ORDERED_DUST, null, new CustomItemStack(Material.CHEST), new CustomItemStack(Material.CHEST), null, FinalTechItems.ORDERED_DUST,
            FinalTechItems.ORDERED_DUST, null, null, null, null, FinalTechItems.ORDERED_DUST,
            FinalTechItems.ORDERED_DUST, FinalTechItems.ORDERED_DUST, FinalTechItems.ORDERED_DUST, FinalTechItems.ORDERED_DUST, FinalTechItems.ORDERED_DUST, FinalTechItems.ORDERED_DUST
    };
    public static final ItemStack[] ITEM_SERIALIZATION_CONSTRUCTOR = new ItemStack[] {
            FinalTechItems.ORDERED_DUST, FinalTechItems.ORDERED_DUST, FinalTechItems.ORDERED_DUST, FinalTechItems.ORDERED_DUST, FinalTechItems.ORDERED_DUST, FinalTechItems.ORDERED_DUST,
            FinalTechItems.ORDERED_DUST, FinalTechItems.SHINE, FinalTechItems.SHINE, FinalTechItems.SHINE, FinalTechItems.SHINE, FinalTechItems.ORDERED_DUST,
            FinalTechItems.ORDERED_DUST, FinalTechItems.SHINE, SlimefunItems.TRASH_CAN, SlimefunItems.TRASH_CAN, FinalTechItems.SHINE, FinalTechItems.ORDERED_DUST,
            FinalTechItems.ORDERED_DUST, FinalTechItems.SHINE, SlimefunItems.TRASH_CAN, SlimefunItems.TRASH_CAN, FinalTechItems.SHINE, FinalTechItems.ORDERED_DUST,
            FinalTechItems.ORDERED_DUST, FinalTechItems.SHINE, FinalTechItems.SHINE, FinalTechItems.SHINE, FinalTechItems.SHINE, FinalTechItems.ORDERED_DUST,
            FinalTechItems.ORDERED_DUST, FinalTechItems.ORDERED_DUST, FinalTechItems.ORDERED_DUST, FinalTechItems.ORDERED_DUST, FinalTechItems.ORDERED_DUST, FinalTechItems.ORDERED_DUST
    };
    public static final ItemStack[] ITEM_DESERIALIZE_PARSER = new ItemStack[] {
            FinalTechItems.ORDERED_DUST, FinalTechItems.ORDERED_DUST, FinalTechItems.ORDERED_DUST, FinalTechItems.ORDERED_DUST, FinalTechItems.ORDERED_DUST, FinalTechItems.ORDERED_DUST,
            FinalTechItems.ORDERED_DUST, FinalTechItems.SHINE, FinalTechItems.SHINE, FinalTechItems.SHINE, FinalTechItems.SHINE, FinalTechItems.ORDERED_DUST,
            FinalTechItems.ORDERED_DUST, FinalTechItems.SHINE, FinalTechItems.ITEM_SERIALIZATION_CONSTRUCTOR, FinalTechItems.ITEM_SERIALIZATION_CONSTRUCTOR, FinalTechItems.SHINE, FinalTechItems.ORDERED_DUST,
            FinalTechItems.ORDERED_DUST, FinalTechItems.SHINE, FinalTechItems.ITEM_SERIALIZATION_CONSTRUCTOR, FinalTechItems.ITEM_SERIALIZATION_CONSTRUCTOR, FinalTechItems.SHINE, FinalTechItems.ORDERED_DUST,
            FinalTechItems.ORDERED_DUST, FinalTechItems.SHINE, FinalTechItems.SHINE, FinalTechItems.SHINE, FinalTechItems.SHINE, FinalTechItems.ORDERED_DUST,
            FinalTechItems.ORDERED_DUST, FinalTechItems.ORDERED_DUST, FinalTechItems.ORDERED_DUST, FinalTechItems.ORDERED_DUST, FinalTechItems.ORDERED_DUST, FinalTechItems.ORDERED_DUST
    };
    public static final ItemStack[] CARD_OPERATION_TABLE = new ItemStack[] {
            FinalTechItems.GEARWHEEL, FinalTechItems.GEARWHEEL, FinalTechItems.GEARWHEEL, FinalTechItems.GEARWHEEL, FinalTechItems.GEARWHEEL, FinalTechItems.GEARWHEEL,
            FinalTechItems.GEARWHEEL, null, null, null, null, FinalTechItems.GEARWHEEL,
            FinalTechItems.GEARWHEEL, null, new ItemStack(Material.CRAFTING_TABLE), new ItemStack(Material.CRAFTING_TABLE), null, FinalTechItems.GEARWHEEL,
            FinalTechItems.GEARWHEEL, null, new ItemStack(Material.CRAFTING_TABLE), new ItemStack(Material.CRAFTING_TABLE), null, FinalTechItems.GEARWHEEL,
            FinalTechItems.GEARWHEEL, null, null, null, null, FinalTechItems.GEARWHEEL,
            FinalTechItems.GEARWHEEL, FinalTechItems.GEARWHEEL, FinalTechItems.GEARWHEEL, FinalTechItems.GEARWHEEL, FinalTechItems.GEARWHEEL, FinalTechItems.GEARWHEEL
    };
    public static final ItemStack[] LOGIC_CRAFTER = new ItemStack[] {
            null, null, null, null, null, null,
            null, ItemStackUtil.cloneItem(FinalTechItems.LOGIC_TRUE, 16), ItemStackUtil.cloneItem(FinalTechItems.LOGIC_FALSE, 16), ItemStackUtil.cloneItem(FinalTechItems.LOGIC_TRUE, 16), ItemStackUtil.cloneItem(FinalTechItems.LOGIC_FALSE, 16), null,
            null, ItemStackUtil.cloneItem(FinalTechItems.LOGIC_FALSE, 16), ItemStackUtil.cloneItem(FinalTechItems.LOGIC_TRUE, 16), ItemStackUtil.cloneItem(FinalTechItems.LOGIC_FALSE, 16), ItemStackUtil.cloneItem(FinalTechItems.LOGIC_TRUE, 16), null,
            null, ItemStackUtil.cloneItem(FinalTechItems.LOGIC_TRUE, 16), ItemStackUtil.cloneItem(FinalTechItems.LOGIC_FALSE, 16), ItemStackUtil.cloneItem(FinalTechItems.LOGIC_TRUE, 16), ItemStackUtil.cloneItem(FinalTechItems.LOGIC_FALSE, 16), null,
            null, ItemStackUtil.cloneItem(FinalTechItems.LOGIC_FALSE, 16), ItemStackUtil.cloneItem(FinalTechItems.LOGIC_TRUE, 16), ItemStackUtil.cloneItem(FinalTechItems.LOGIC_FALSE, 16), ItemStackUtil.cloneItem(FinalTechItems.LOGIC_TRUE, 16), null,
    };
    public static final ItemStack[] DIGIT_ADDER = new ItemStack[] {
            null, null, null, null, null, null,
            null, ItemStackUtil.cloneItem(FinalTechItems.DIGITAL_ZERO, 16), ItemStackUtil.cloneItem(FinalTechItems.DIGITAL_ONE, 16), ItemStackUtil.cloneItem(FinalTechItems.DIGITAL_TWO, 16), ItemStackUtil.cloneItem(FinalTechItems.DIGITAL_THREE, 16), null,
            null, ItemStackUtil.cloneItem(FinalTechItems.DIGITAL_FOUR, 16), ItemStackUtil.cloneItem(FinalTechItems.DIGITAL_FIVE, 16), ItemStackUtil.cloneItem(FinalTechItems.DIGITAL_SIX, 16), ItemStackUtil.cloneItem(FinalTechItems.DIGITAL_SEVEN, 16), null,
            null, ItemStackUtil.cloneItem(FinalTechItems.DIGITAL_EIGHT, 16), ItemStackUtil.cloneItem(FinalTechItems.DIGITAL_NINE, 16), ItemStackUtil.cloneItem(FinalTechItems.DIGITAL_TEN, 16), ItemStackUtil.cloneItem(FinalTechItems.DIGITAL_ELEVEN, 16), null,
            null, ItemStackUtil.cloneItem(FinalTechItems.DIGITAL_TWELVE, 16), ItemStackUtil.cloneItem(FinalTechItems.DIGITAL_THIRTEEN, 16), ItemStackUtil.cloneItem(FinalTechItems.DIGITAL_FOURTEEN, 16), ItemStackUtil.cloneItem(FinalTechItems.DIGITAL_FIFTEEN, 16), null,
    };
    // special machine
    public static final ItemStack[] ITEM_FIXER = new ItemStack[] {
            null, SlimefunItems.AUTO_ANVIL_2, null,
            FinalTechItems.ORDERED_DUST, FinalTechItems.BASIC_FRAME_MACHINE, FinalTechItems.ORDERED_DUST,
            SlimefunItems.DUCT_TAPE, SlimefunItems.DUCT_TAPE, SlimefunItems.DUCT_TAPE
    };
    public static final ItemStack[] COBBLESTONE_FACTORY = new ItemStack[] {
            FinalTechItems.ANNULAR, FinalTechItems.ANNULAR, FinalTechItems.ANNULAR,
            FinalTechItems.BASIC_COBBLE_FACTORY, FinalTechItems.BASIC_FRAME_MACHINE, FinalTechItems.BASIC_COBBLE_FACTORY,
            FinalTechItems.ANNULAR, FinalTechItems.ANNULAR, FinalTechItems.ANNULAR
    };
    public static final ItemStack[] FUEL_CHARGER = new ItemStack[] {
            FinalTechItems.GEARWHEEL, FinalTechItems.GEARWHEEL, FinalTechItems.ANNULAR, FinalTechItems.GEARWHEEL, FinalTechItems.GEARWHEEL, null,
            FinalTechItems.GEARWHEEL, FinalTechItems.ORDERED_DUST, FinalTechItems.ORDERED_DUST, FinalTechItems.ORDERED_DUST, FinalTechItems.GEARWHEEL, null,
            FinalTechItems.GEARWHEEL, FinalTechItems.GEARWHEEL, FinalTechItems.GEARWHEEL, FinalTechItems.GEARWHEEL, FinalTechItems.GEARWHEEL
    };
    public static final ItemStack[] FUEL_ACCELERATOR = new ItemStack[] {
            FinalTechItems.GEARWHEEL, FinalTechItems.GEARWHEEL, FinalTechItems.ANNULAR, FinalTechItems.GEARWHEEL, FinalTechItems.GEARWHEEL, null,
            FinalTechItems.GEARWHEEL, FinalTechItems.BUG, FinalTechItems.BUG, FinalTechItems.BUG, FinalTechItems.GEARWHEEL, null,
            FinalTechItems.GEARWHEEL, FinalTechItems.GEARWHEEL, FinalTechItems.GEARWHEEL, FinalTechItems.GEARWHEEL, FinalTechItems.GEARWHEEL

    };
    public static final ItemStack[] FUEL_OPERATOR = new ItemStack[] {
            FinalTechItems.GEARWHEEL, FinalTechItems.GEARWHEEL, FinalTechItems.GEARWHEEL, FinalTechItems.GEARWHEEL, FinalTechItems.GEARWHEEL, null,
            FinalTechItems.GEARWHEEL, FinalTechItems.ANNULAR, FinalTechItems.BASIC_FRAME_MACHINE, FinalTechItems.ANNULAR, FinalTechItems.GEARWHEEL, null,
            FinalTechItems.GEARWHEEL, FinalTechItems.GEARWHEEL, FinalTechItems.GEARWHEEL, FinalTechItems.GEARWHEEL, FinalTechItems.GEARWHEEL
    };
    public static final ItemStack[] OPERATION_ACCELERATOR = new ItemStack[] {
            FinalTechItems.GEARWHEEL, FinalTechItems.GEARWHEEL, FinalTechItems.ANNULAR, FinalTechItems.GEARWHEEL, FinalTechItems.GEARWHEEL, null,
            FinalTechItems.GEARWHEEL, FinalTechItems.ANNULAR, FinalTechItems.BASIC_FRAME_MACHINE, FinalTechItems.ANNULAR, FinalTechItems.GEARWHEEL, null,
            FinalTechItems.GEARWHEEL, FinalTechItems.GEARWHEEL, FinalTechItems.ANNULAR, FinalTechItems.GEARWHEEL, FinalTechItems.GEARWHEEL
    };
    // tower
    public static final ItemStack[] CURE_TOWER = new ItemStack[] {
            FinalTechItems.BASIC_FRAME_MACHINE, FinalTechItems.UNORDERED_DUST, FinalTechItems.UNORDERED_DUST, FinalTechItems.UNORDERED_DUST, FinalTechItems.UNORDERED_DUST, FinalTechItems.BASIC_FRAME_MACHINE,
            FinalTechItems.UNORDERED_DUST, FinalTechItems.GEARWHEEL, FinalTechItems.GEARWHEEL, FinalTechItems.GEARWHEEL, FinalTechItems.GEARWHEEL, FinalTechItems.UNORDERED_DUST,
            FinalTechItems.UNORDERED_DUST, FinalTechItems.GEARWHEEL, FinalTechItems.ANNULAR, FinalTechItems.ANNULAR, FinalTechItems.GEARWHEEL, FinalTechItems.UNORDERED_DUST,
            FinalTechItems.UNORDERED_DUST, FinalTechItems.GEARWHEEL, FinalTechItems.ANNULAR, FinalTechItems.ANNULAR, FinalTechItems.GEARWHEEL, FinalTechItems.UNORDERED_DUST,
            FinalTechItems.UNORDERED_DUST, FinalTechItems.GEARWHEEL, FinalTechItems.GEARWHEEL, FinalTechItems.GEARWHEEL, FinalTechItems.GEARWHEEL, FinalTechItems.UNORDERED_DUST,
            FinalTechItems.BASIC_FRAME_MACHINE, FinalTechItems.UNORDERED_DUST, FinalTechItems.UNORDERED_DUST, FinalTechItems.UNORDERED_DUST, FinalTechItems.UNORDERED_DUST, FinalTechItems.BASIC_FRAME_MACHINE,
    };
    public static final ItemStack[] PURIFY_LEVEL_TOWER = new ItemStack[] {
            FinalTechItems.BASIC_FRAME_MACHINE, null, FinalTechItems.UNORDERED_DUST, FinalTechItems.UNORDERED_DUST, null, FinalTechItems.BASIC_FRAME_MACHINE,
            null, FinalTechItems.GEARWHEEL, FinalTechItems.GEARWHEEL, FinalTechItems.GEARWHEEL, FinalTechItems.GEARWHEEL, null,
            FinalTechItems.UNORDERED_DUST, FinalTechItems.GEARWHEEL, FinalTechItems.SHINE, FinalTechItems.SHINE, FinalTechItems.GEARWHEEL, FinalTechItems.UNORDERED_DUST,
            FinalTechItems.UNORDERED_DUST, FinalTechItems.GEARWHEEL, FinalTechItems.SHINE, FinalTechItems.SHINE, FinalTechItems.GEARWHEEL, FinalTechItems.UNORDERED_DUST,
            null, FinalTechItems.GEARWHEEL, FinalTechItems.GEARWHEEL, FinalTechItems.GEARWHEEL, FinalTechItems.GEARWHEEL, null,
            FinalTechItems.BASIC_FRAME_MACHINE, null, FinalTechItems.UNORDERED_DUST, FinalTechItems.UNORDERED_DUST, null, FinalTechItems.BASIC_FRAME_MACHINE,
    };
    public static final ItemStack[] PURIFY_TIME_TOWER = new ItemStack[] {
            FinalTechItems.BASIC_FRAME_MACHINE, FinalTechItems.UNORDERED_DUST, null, null, FinalTechItems.UNORDERED_DUST, FinalTechItems.BASIC_FRAME_MACHINE,
            FinalTechItems.UNORDERED_DUST, FinalTechItems.GEARWHEEL, FinalTechItems.GEARWHEEL, FinalTechItems.GEARWHEEL, FinalTechItems.GEARWHEEL, FinalTechItems.UNORDERED_DUST,
            null, FinalTechItems.GEARWHEEL, FinalTechItems.SHINE, FinalTechItems.SHINE, FinalTechItems.GEARWHEEL, null,
            null, FinalTechItems.GEARWHEEL, FinalTechItems.SHINE, FinalTechItems.SHINE, FinalTechItems.GEARWHEEL, null,
            FinalTechItems.UNORDERED_DUST, FinalTechItems.GEARWHEEL, FinalTechItems.GEARWHEEL, FinalTechItems.GEARWHEEL, FinalTechItems.GEARWHEEL, FinalTechItems.UNORDERED_DUST,
            FinalTechItems.BASIC_FRAME_MACHINE, FinalTechItems.UNORDERED_DUST, null, null, FinalTechItems.UNORDERED_DUST, FinalTechItems.BASIC_FRAME_MACHINE,
    };

    /* productive machine */
    // manual machine
    public static final ItemStack[] MANUAL_CRAFTING_TABLE = new ItemStack[] {
            FinalTechItems.GEARWHEEL, SlimefunItems.PORTABLE_CRAFTER, null,
            null, null, null,
            null, null, null
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
    public static final ItemStack[] MANUAL_ORE_WASHER = new ItemStack[] {
            null, new ItemStack(Material.DISPENSER), null,
            FinalTechItems.GEARWHEEL, new ItemStack(Material.OAK_FENCE), FinalTechItems.GEARWHEEL,
            null, new ItemStack(Material.CAULDRON), null
    };
    public static final ItemStack[] MANUAL_COMPOSTER = new ItemStack[] {
            new ItemStack(Material.OAK_SLAB), FinalTechItems.GEARWHEEL, new ItemStack(Material.OAK_SLAB),
            new ItemStack(Material.OAK_SLAB), FinalTechItems.GEARWHEEL, new ItemStack(Material.OAK_SLAB),
            new ItemStack(Material.OAK_SLAB), new ItemStack(Material.CAULDRON), new ItemStack(Material.OAK_SLAB)
    };
    public static final ItemStack[] MANUAL_GOLD_PAN = new ItemStack[] {
            null, new ItemStack(Material.OAK_TRAPDOOR), null,
            null, new ItemStack(Material.CAULDRON), null,
            null, FinalTechItems.GEARWHEEL, null
    };
    public static final ItemStack[] MANUAL_CRUCIBLE = new ItemStack[] {
            new ItemStack(Material.TERRACOTTA), FinalTechItems.GEARWHEEL, new ItemStack(Material.TERRACOTTA),
            new ItemStack(Material.TERRACOTTA), FinalTechItems.GEARWHEEL, new ItemStack(Material.TERRACOTTA),
            new ItemStack(Material.TERRACOTTA), new ItemStack(Material.FLINT_AND_STEEL), new ItemStack(Material.TERRACOTTA)
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
    // basic machine
    public static final ItemStack[] BASIC_COBBLE_FACTORY = new ItemStack[] {
            new ItemStack(Material.LAVA_BUCKET), new ItemStack(Material.NAME_TAG), new ItemStack(Material.LAVA_BUCKET),
            new ItemStack(Material.WATER_BUCKET), FinalTechItems.BASIC_FRAME_MACHINE, new ItemStack(Material.WATER_BUCKET),
            SlimefunItems.COBALT_PICKAXE, FinalTechItems.ANNULAR, SlimefunItems.COBALT_PICKAXE
    };
    public static final ItemStack[] BASIC_DUST_FACTORY = new ItemStack[] {
            SlimefunItems.ELECTRIC_ORE_GRINDER, new ItemStack(Material.NAME_TAG), SlimefunItems.ELECTRIC_ORE_GRINDER,
            SlimefunItems.ELECTRIC_GOLD_PAN, FinalTechItems.BASIC_FRAME_MACHINE, SlimefunItems.ELECTRIC_GOLD_PAN,
            SlimefunItems.ELECTRIC_DUST_WASHER, FinalTechItems.ANNULAR, SlimefunItems.ELECTRIC_DUST_WASHER
    };
    public static final ItemStack[] BASIC_LOGIC_FACTORY = new ItemStack[] {
            FinalTechItems.ORDERED_DUST_FACTORY_DIRT, new ItemStack(Material.NAME_TAG), FinalTechItems.ORDERED_DUST_FACTORY_DIRT,
            FinalTechItems.GEARWHEEL, FinalTechItems.BASIC_FRAME_MACHINE, FinalTechItems.GEARWHEEL,
            FinalTechItems.ORDERED_DUST_FACTORY_STONE, FinalTechItems.ANNULAR, FinalTechItems.ORDERED_DUST_FACTORY_STONE
    };
    // advanced machine
    public static final ItemStack[] ADVANCED_COMPOSTER = new ItemStack[] {
            FinalTechItems.MANUAL_COMPOSTER, FinalTechItems.ANNULAR, FinalTechItems.MANUAL_COMPOSTER,
            FinalTechItems.GEARWHEEL, FinalTechItems.BASIC_FRAME_MACHINE, FinalTechItems.GEARWHEEL,
            FinalTechItems.MANUAL_COMPOSTER, FinalTechItems.ANNULAR, FinalTechItems.MANUAL_COMPOSTER
    };
    public static final ItemStack[] ADVANCED_JUICER = new ItemStack[] {
            FinalTechItems.MANUAL_JUICER, FinalTechItems.ANNULAR, FinalTechItems.MANUAL_JUICER,
            FinalTechItems.GEARWHEEL, FinalTechItems.BASIC_FRAME_MACHINE, FinalTechItems.GEARWHEEL,
            FinalTechItems.MANUAL_JUICER, FinalTechItems.ANNULAR, FinalTechItems.MANUAL_JUICER
    };
    public static final ItemStack[] ADVANCED_FURNACE = new ItemStack[] {
            SlimefunItems.ELECTRIC_FURNACE, FinalTechItems.ANNULAR, SlimefunItems.ELECTRIC_FURNACE,
            FinalTechItems.GEARWHEEL, FinalTechItems.BASIC_FRAME_MACHINE, FinalTechItems.GEARWHEEL,
            SlimefunItems.ELECTRIC_FURNACE, FinalTechItems.ANNULAR, SlimefunItems.ELECTRIC_FURNACE
    };
    public static final ItemStack[] ADVANCED_GOLD_PAN = new ItemStack[] {
            SlimefunItems.ELECTRIC_GOLD_PAN, FinalTechItems.ANNULAR, SlimefunItems.ELECTRIC_GOLD_PAN,
            FinalTechItems.GEARWHEEL, FinalTechItems.BASIC_FRAME_MACHINE, FinalTechItems.GEARWHEEL,
            SlimefunItems.ELECTRIC_GOLD_PAN, FinalTechItems.ANNULAR, SlimefunItems.ELECTRIC_GOLD_PAN
    };
    public static final ItemStack[] ADVANCED_DUST_WASHER = new ItemStack[] {
            SlimefunItems.ELECTRIC_DUST_WASHER, FinalTechItems.ANNULAR, SlimefunItems.ELECTRIC_DUST_WASHER,
            FinalTechItems.GEARWHEEL, FinalTechItems.BASIC_FRAME_MACHINE, FinalTechItems.GEARWHEEL,
            SlimefunItems.ELECTRIC_DUST_WASHER, FinalTechItems.ANNULAR, SlimefunItems.ELECTRIC_DUST_WASHER
    };
    public static final ItemStack[] ADVANCED_INGOT_FACTORY = new ItemStack[] {
            SlimefunItems.ELECTRIC_INGOT_FACTORY, FinalTechItems.ANNULAR, SlimefunItems.ELECTRIC_INGOT_FACTORY,
            FinalTechItems.GEARWHEEL, FinalTechItems.BASIC_FRAME_MACHINE, FinalTechItems.GEARWHEEL,
            SlimefunItems.ELECTRIC_INGOT_FACTORY, FinalTechItems.ANNULAR, SlimefunItems.ELECTRIC_INGOT_FACTORY
    };
    public static final ItemStack[] ADVANCED_CRUCIBLE = new ItemStack[] {
            SlimefunItems.ELECTRIFIED_CRUCIBLE, FinalTechItems.ANNULAR, SlimefunItems.ELECTRIFIED_CRUCIBLE,
            FinalTechItems.GEARWHEEL, FinalTechItems.BASIC_FRAME_MACHINE, FinalTechItems.GEARWHEEL,
            SlimefunItems.ELECTRIFIED_CRUCIBLE, FinalTechItems.ANNULAR, SlimefunItems.ELECTRIFIED_CRUCIBLE
    };
    public static final ItemStack[] ADVANCED_ORE_GRINDER = new ItemStack[] {
            SlimefunItems.ELECTRIC_ORE_GRINDER, FinalTechItems.ANNULAR, SlimefunItems.ELECTRIC_ORE_GRINDER,
            FinalTechItems.GEARWHEEL, FinalTechItems.BASIC_FRAME_MACHINE, FinalTechItems.GEARWHEEL,
            SlimefunItems.ELECTRIC_ORE_GRINDER, FinalTechItems.ANNULAR, SlimefunItems.ELECTRIC_ORE_GRINDER
    };
    public static final ItemStack[] ADVANCED_HEATED_PRESSURE_CHAMBER = new ItemStack[] {
            SlimefunItems.HEATED_PRESSURE_CHAMBER, FinalTechItems.ANNULAR, SlimefunItems.HEATED_PRESSURE_CHAMBER,
            FinalTechItems.GEARWHEEL, FinalTechItems.BASIC_FRAME_MACHINE, FinalTechItems.GEARWHEEL,
            SlimefunItems.HEATED_PRESSURE_CHAMBER, FinalTechItems.ANNULAR, SlimefunItems.HEATED_PRESSURE_CHAMBER
    };
    public static final ItemStack[] ADVANCED_INGOT_PULVERIZER = new ItemStack[] {
            SlimefunItems.ELECTRIC_INGOT_PULVERIZER, FinalTechItems.ANNULAR, SlimefunItems.ELECTRIC_INGOT_PULVERIZER,
            FinalTechItems.GEARWHEEL, FinalTechItems.BASIC_FRAME_MACHINE, FinalTechItems.GEARWHEEL,
            SlimefunItems.ELECTRIC_INGOT_PULVERIZER, FinalTechItems.ANNULAR, SlimefunItems.ELECTRIC_INGOT_PULVERIZER
    };
    public static final ItemStack[] ADVANCED_AUTO_DRIER = new ItemStack[] {
            SlimefunItems.AUTO_DRIER, FinalTechItems.ANNULAR, SlimefunItems.AUTO_DRIER,
            FinalTechItems.GEARWHEEL, FinalTechItems.BASIC_FRAME_MACHINE, FinalTechItems.GEARWHEEL,
            SlimefunItems.AUTO_DRIER, FinalTechItems.ANNULAR, SlimefunItems.AUTO_DRIER
    };
    public static final ItemStack[] ADVANCED_PRESS = new ItemStack[] {
            SlimefunItems.ELECTRIC_PRESS, FinalTechItems.ANNULAR, SlimefunItems.ELECTRIC_PRESS,
            FinalTechItems.GEARWHEEL, FinalTechItems.BASIC_FRAME_MACHINE, FinalTechItems.GEARWHEEL,
            SlimefunItems.ELECTRIC_PRESS, FinalTechItems.ANNULAR, SlimefunItems.ELECTRIC_PRESS
    };
    public static final ItemStack[] ADVANCED_FOOD_FACTORY = new ItemStack[] {
            SlimefunItems.FOOD_COMPOSTER, FinalTechItems.ANNULAR, SlimefunItems.FOOD_COMPOSTER,
            FinalTechItems.GEARWHEEL, FinalTechItems.BASIC_FRAME_MACHINE, FinalTechItems.GEARWHEEL,
            SlimefunItems.FOOD_FABRICATOR, FinalTechItems.ANNULAR, SlimefunItems.FOOD_FABRICATOR
    };
    public static final ItemStack[] ADVANCED_FREEZER = new ItemStack[] {
            SlimefunItems.FREEZER, FinalTechItems.ANNULAR, SlimefunItems.FREEZER,
            FinalTechItems.GEARWHEEL, FinalTechItems.BASIC_FRAME_MACHINE, FinalTechItems.GEARWHEEL,
            SlimefunItems.FREEZER, FinalTechItems.ANNULAR, SlimefunItems.FREEZER
    };
    public static final ItemStack[] ADVANCED_CARBON_PRESS = new ItemStack[] {
            SlimefunItems.CARBON_PRESS, FinalTechItems.ANNULAR, SlimefunItems.CARBON_PRESS,
            FinalTechItems.GEARWHEEL, FinalTechItems.BASIC_FRAME_MACHINE, FinalTechItems.GEARWHEEL,
            SlimefunItems.CARBON_PRESS, FinalTechItems.ANNULAR, SlimefunItems.CARBON_PRESS
    };
    public static final ItemStack[] ADVANCED_SMELTERY = new ItemStack[] {
            SlimefunItems.ELECTRIC_SMELTERY, FinalTechItems.ANNULAR, SlimefunItems.ELECTRIC_SMELTERY,
            FinalTechItems.GEARWHEEL, FinalTechItems.BASIC_FRAME_MACHINE, FinalTechItems.GEARWHEEL,
            SlimefunItems.ELECTRIC_SMELTERY, FinalTechItems.ANNULAR, SlimefunItems.ELECTRIC_SMELTERY
    };
    public static final ItemStack[] ADVANCED_DUST_FACTORY = new ItemStack[] {
            FinalTechItems.BASIC_DUST_FACTORY, FinalTechItems.ANNULAR, FinalTechItems.BASIC_DUST_FACTORY,
            FinalTechItems.GEARWHEEL, FinalTechItems.BASIC_FRAME_MACHINE, FinalTechItems.GEARWHEEL,
            FinalTechItems.BASIC_DUST_FACTORY, FinalTechItems.ANNULAR, FinalTechItems.BASIC_DUST_FACTORY
    };
    // conversion
    public static final ItemStack[] DUST_CONVERSION = new ItemStack[] {
            FinalTechItems.BASIC_DUST_FACTORY, FinalTechItems.GEARWHEEL, FinalTechItems.BASIC_DUST_FACTORY,
            FinalTechItems.ANNULAR, FinalTechItems.BASIC_FRAME_MACHINE, FinalTechItems.ANNULAR,
            FinalTechItems.BASIC_DUST_FACTORY, FinalTechItems.ANNULAR, FinalTechItems.BASIC_DUST_FACTORY
    };
    public static final ItemStack[] GRAVEL_CONVERSION = new ItemStack[] {
            FinalTechItems.MANUAL_GOLD_PAN, FinalTechItems.WATER_CARD, FinalTechItems.MANUAL_GOLD_PAN,
            FinalTechItems.ANNULAR, FinalTechItems.BASIC_FRAME_MACHINE, FinalTechItems.ANNULAR,
            FinalTechItems.MANUAL_GOLD_PAN, FinalTechItems.ANNULAR, FinalTechItems.MANUAL_GOLD_PAN
    };
    public static final ItemStack[] SOUL_SAND_CONVERSION = new ItemStack[] {
            FinalTechItems.MANUAL_GOLD_PAN, FinalTechItems.LAVA_CARD, FinalTechItems.MANUAL_GOLD_PAN,
            FinalTechItems.ANNULAR, FinalTechItems.BASIC_FRAME_MACHINE, FinalTechItems.ANNULAR,
            FinalTechItems.MANUAL_GOLD_PAN, FinalTechItems.ANNULAR, FinalTechItems.MANUAL_GOLD_PAN
    };
    public static final ItemStack[] CONCRETE_CONVERSION = new ItemStack[] {
            FinalTechItems.WATER_CARD, FinalTechItems.GEARWHEEL, FinalTechItems.WATER_CARD,
            FinalTechItems.ANNULAR, FinalTechItems.BASIC_FRAME_MACHINE, FinalTechItems.ANNULAR,
            FinalTechItems.WATER_CARD, FinalTechItems.ANNULAR, FinalTechItems.WATER_CARD
    };
    public static final ItemStack[] WOOL_CONVERSION = new ItemStack[] {
            FinalTechItems.LAVA_CARD, FinalTechItems.GEARWHEEL, FinalTechItems.LAVA_CARD,
            FinalTechItems.ANNULAR, FinalTechItems.BASIC_FRAME_MACHINE, FinalTechItems.ANNULAR,
            FinalTechItems.LAVA_CARD, FinalTechItems.ANNULAR, FinalTechItems.LAVA_CARD
    };
    public static final ItemStack[] LOGIC_TO_DIGITAL_CONVERSION = new ItemStack[] {
            FinalTechItems.BUG, FinalTechItems.GEARWHEEL, FinalTechItems.BUG,
            FinalTechItems.ANNULAR, FinalTechItems.BASIC_FRAME_MACHINE, FinalTechItems.ANNULAR,
            FinalTechItems.BUG, FinalTechItems.ANNULAR, FinalTechItems.BUG
    };
    // extraction
    public static final ItemStack[] ORE_EXTRACTION = new ItemStack[] {
            new ItemStack(Material.NETHERITE_BLOCK), FinalTechItems.ANNULAR, new ItemStack(Material.NETHERITE_BLOCK),
            FinalTechItems.ANNULAR, FinalTechItems.BASIC_FRAME_MACHINE, FinalTechItems.ANNULAR,
            new ItemStack(Material.NETHERITE_BLOCK), FinalTechItems.ANNULAR, new ItemStack(Material.NETHERITE_BLOCK)
    };
    // generator
    public static final ItemStack[] STONE_GENERATOR = new ItemStack[] {
            new ItemStack(Material.GRANITE), new ItemStack(Material.DIORITE), new ItemStack(Material.ANDESITE),
            FinalTechItems.ANNULAR, FinalTechItems.BASIC_FRAME_MACHINE, FinalTechItems.ANNULAR,
            FinalTechItems.ANNULAR, FinalTechItems.ANNULAR, FinalTechItems.ANNULAR
    };
    public static final ItemStack[] RAW_STONE_GENERATOR = new ItemStack[] {
            new ItemStack(Material.COBBLESTONE), new ItemStack(Material.COBBLED_DEEPSLATE), new ItemStack(Material.BLACKSTONE),
            FinalTechItems.ANNULAR, FinalTechItems.BASIC_FRAME_MACHINE, FinalTechItems.ANNULAR,
            FinalTechItems.ANNULAR, FinalTechItems.ANNULAR, FinalTechItems.ANNULAR
    };
    public static final ItemStack[] NETHER_STONE_GENERATOR = new ItemStack[] {
            new ItemStack(Material.NETHERRACK), new ItemStack(Material.BLACKSTONE), new ItemStack(Material.BASALT),
            FinalTechItems.ANNULAR, FinalTechItems.BASIC_FRAME_MACHINE, FinalTechItems.ANNULAR,
            FinalTechItems.ANNULAR, FinalTechItems.ANNULAR, FinalTechItems.ANNULAR
    };
    public static final ItemStack[] PLANK_GENERATOR = new ItemStack[] {
            new ItemStack(Material.OAK_SAPLING), new ItemStack(Material.OAK_SAPLING), new ItemStack(Material.OAK_SAPLING),
            FinalTechItems.ANNULAR, FinalTechItems.BASIC_FRAME_MACHINE, FinalTechItems.ANNULAR,
            FinalTechItems.ANNULAR, FinalTechItems.ANNULAR, FinalTechItems.ANNULAR
    };
    public static final ItemStack[] SAND_GENERATOR = new ItemStack[] {
            new ItemStack(Material.SAND), new ItemStack(Material.RED_SAND), new ItemStack(Material.GRAVEL),
            FinalTechItems.ANNULAR, FinalTechItems.BASIC_FRAME_MACHINE, FinalTechItems.ANNULAR,
            FinalTechItems.ANNULAR, FinalTechItems.ANNULAR, FinalTechItems.ANNULAR
    };
    public static final ItemStack[] LIQUID_CARD_GENERATOR = new ItemStack[] {
            new ItemStack(Material.WATER_BUCKET), new ItemStack(Material.LAVA_BUCKET), new ItemStack(Material.MILK_BUCKET),
            FinalTechItems.ANNULAR, FinalTechItems.BASIC_FRAME_MACHINE, FinalTechItems.ANNULAR,
            FinalTechItems.ANNULAR, FinalTechItems.ANNULAR, FinalTechItems.ANNULAR
    };
    public static final ItemStack[] LOGIC_GENERATOR = new ItemStack[] {
            FinalTechItems.BUG, FinalTechItems.SHINE, FinalTechItems.BUG,
            FinalTechItems.ANNULAR, FinalTechItems.BASIC_FRAME_MACHINE, FinalTechItems.ANNULAR,
            FinalTechItems.ANNULAR, FinalTechItems.ANNULAR, FinalTechItems.ANNULAR
    };
    public static final ItemStack[] DIGITAL_GENERATOR = new ItemStack[] {
            FinalTechItems.LOGIC_TRUE, FinalTechItems.BUG, FinalTechItems.LOGIC_FALSE,
            FinalTechItems.ANNULAR, FinalTechItems.BASIC_FRAME_MACHINE, FinalTechItems.ANNULAR,
            FinalTechItems.ANNULAR, FinalTechItems.ANNULAR, FinalTechItems.ANNULAR
    };

    /* final stage item */
    // 1
    public static final ItemStack[] ENTROPY_SEED = new ItemStack[] {
            ItemStackUtil.cloneItem(FinalTechItems.ENTROPY, 64), ItemStackUtil.cloneItem(FinalTechItems.ENTROPY, 64), ItemStackUtil.cloneItem(FinalTechItems.ENTROPY, 64), ItemStackUtil.cloneItem(FinalTechItems.ENTROPY, 64), ItemStackUtil.cloneItem(FinalTechItems.ENTROPY, 64), null,
            ItemStackUtil.cloneItem(FinalTechItems.ENTROPY, 64), ItemStackUtil.cloneItem(FinalTechItems.ENTROPY, 64), ItemStackUtil.cloneItem(FinalTechItems.ENTROPY, 64), ItemStackUtil.cloneItem(FinalTechItems.ENTROPY, 64), ItemStackUtil.cloneItem(FinalTechItems.ENTROPY, 64), null,
            ItemStackUtil.cloneItem(FinalTechItems.ENTROPY, 64), ItemStackUtil.cloneItem(FinalTechItems.ENTROPY, 64), FinalTechItems.PHONY, ItemStackUtil.cloneItem(FinalTechItems.ENTROPY, 64), ItemStackUtil.cloneItem(FinalTechItems.ENTROPY, 64), null,
            ItemStackUtil.cloneItem(FinalTechItems.ENTROPY, 64), ItemStackUtil.cloneItem(FinalTechItems.ENTROPY, 64), ItemStackUtil.cloneItem(FinalTechItems.ENTROPY, 64), ItemStackUtil.cloneItem(FinalTechItems.ENTROPY, 64), ItemStackUtil.cloneItem(FinalTechItems.ENTROPY, 64), null,
            ItemStackUtil.cloneItem(FinalTechItems.ENTROPY, 64), ItemStackUtil.cloneItem(FinalTechItems.ENTROPY, 64), ItemStackUtil.cloneItem(FinalTechItems.ENTROPY, 64), ItemStackUtil.cloneItem(FinalTechItems.ENTROPY, 64), ItemStackUtil.cloneItem(FinalTechItems.ENTROPY, 64), null
    };
    // 2
    public static final ItemStack[] MACHINE_CHARGE_CARD_INFINITY = new ItemStack[] {
            ItemStackUtil.cloneItem(FinalTechItems.MACHINE_CHARGE_CARD_L1, 64), ItemStackUtil.cloneItem(FinalTechItems.MACHINE_CHARGE_CARD_L2, 16), ItemStackUtil.cloneItem(FinalTechItems.MACHINE_CHARGE_CARD_L3, 4), ItemStackUtil.cloneItem(FinalTechItems.MACHINE_CHARGE_CARD_L2, 16), ItemStackUtil.cloneItem(FinalTechItems.MACHINE_CHARGE_CARD_L1, 64), null,
            ItemStackUtil.cloneItem(FinalTechItems.MACHINE_CHARGE_CARD_L2, 16), ItemStackUtil.cloneItem(FinalTechItems.MACHINE_CHARGE_CARD_L3, 4), null, ItemStackUtil.cloneItem(FinalTechItems.MACHINE_CHARGE_CARD_L3, 4), ItemStackUtil.cloneItem(FinalTechItems.MACHINE_CHARGE_CARD_L2, 16), null,
            ItemStackUtil.cloneItem(FinalTechItems.MACHINE_CHARGE_CARD_L3, 4), null, ItemStackUtil.cloneItem(FinalTechItems.PHONY, 2), null, ItemStackUtil.cloneItem(FinalTechItems.MACHINE_CHARGE_CARD_L3, 4), null,
            ItemStackUtil.cloneItem(FinalTechItems.MACHINE_CHARGE_CARD_L2, 16), ItemStackUtil.cloneItem(FinalTechItems.MACHINE_CHARGE_CARD_L3, 4), null, ItemStackUtil.cloneItem(FinalTechItems.MACHINE_CHARGE_CARD_L3, 4), ItemStackUtil.cloneItem(FinalTechItems.MACHINE_CHARGE_CARD_L2, 16), null,
            ItemStackUtil.cloneItem(FinalTechItems.MACHINE_CHARGE_CARD_L1, 64), ItemStackUtil.cloneItem(FinalTechItems.MACHINE_CHARGE_CARD_L2, 16), ItemStackUtil.cloneItem(FinalTechItems.MACHINE_CHARGE_CARD_L3, 4), ItemStackUtil.cloneItem(FinalTechItems.MACHINE_CHARGE_CARD_L2, 16), ItemStackUtil.cloneItem(FinalTechItems.MACHINE_CHARGE_CARD_L1, 64), null,
    };
    // 2
    public static final ItemStack[] MACHINE_ACCELERATE_CARD_INFINITY = new ItemStack[] {
            ItemStackUtil.cloneItem(FinalTechItems.MACHINE_ACCELERATE_CARD_L1, 64), ItemStackUtil.cloneItem(FinalTechItems.MACHINE_ACCELERATE_CARD_L2, 16), ItemStackUtil.cloneItem(FinalTechItems.MACHINE_ACCELERATE_CARD_L3, 4), ItemStackUtil.cloneItem(FinalTechItems.MACHINE_ACCELERATE_CARD_L2, 16), ItemStackUtil.cloneItem(FinalTechItems.MACHINE_ACCELERATE_CARD_L1, 64), null,
            ItemStackUtil.cloneItem(FinalTechItems.MACHINE_ACCELERATE_CARD_L2, 16), ItemStackUtil.cloneItem(FinalTechItems.MACHINE_ACCELERATE_CARD_L3, 4), null, ItemStackUtil.cloneItem(FinalTechItems.MACHINE_ACCELERATE_CARD_L3, 4), ItemStackUtil.cloneItem(FinalTechItems.MACHINE_ACCELERATE_CARD_L2, 16), null,
            ItemStackUtil.cloneItem(FinalTechItems.MACHINE_ACCELERATE_CARD_L3, 4), null, ItemStackUtil.cloneItem(FinalTechItems.PHONY, 2), null, ItemStackUtil.cloneItem(FinalTechItems.MACHINE_ACCELERATE_CARD_L3, 4), null,
            ItemStackUtil.cloneItem(FinalTechItems.MACHINE_ACCELERATE_CARD_L2, 16), ItemStackUtil.cloneItem(FinalTechItems.MACHINE_ACCELERATE_CARD_L3, 4), null, ItemStackUtil.cloneItem(FinalTechItems.MACHINE_ACCELERATE_CARD_L3, 4), ItemStackUtil.cloneItem(FinalTechItems.MACHINE_ACCELERATE_CARD_L2, 16), null,
            ItemStackUtil.cloneItem(FinalTechItems.MACHINE_ACCELERATE_CARD_L1, 64), ItemStackUtil.cloneItem(FinalTechItems.MACHINE_ACCELERATE_CARD_L2, 16), ItemStackUtil.cloneItem(FinalTechItems.MACHINE_ACCELERATE_CARD_L3, 4), ItemStackUtil.cloneItem(FinalTechItems.MACHINE_ACCELERATE_CARD_L2, 16), ItemStackUtil.cloneItem(FinalTechItems.MACHINE_ACCELERATE_CARD_L1, 64), null,
    };
    // 2
    public static final ItemStack[] MACHINE_ACTIVATE_CARD_L4 = new ItemStack[] {
            ItemStackUtil.cloneItem(FinalTechItems.MACHINE_ACTIVATE_CARD_L1, 64), ItemStackUtil.cloneItem(FinalTechItems.MACHINE_ACTIVATE_CARD_L2, 16), ItemStackUtil.cloneItem(FinalTechItems.MACHINE_ACTIVATE_CARD_L3, 4), ItemStackUtil.cloneItem(FinalTechItems.MACHINE_ACTIVATE_CARD_L2, 16), ItemStackUtil.cloneItem(FinalTechItems.MACHINE_ACTIVATE_CARD_L1, 64), null,
            ItemStackUtil.cloneItem(FinalTechItems.MACHINE_ACTIVATE_CARD_L2, 16), ItemStackUtil.cloneItem(FinalTechItems.MACHINE_ACTIVATE_CARD_L3, 4), null, ItemStackUtil.cloneItem(FinalTechItems.MACHINE_ACTIVATE_CARD_L3, 4), ItemStackUtil.cloneItem(FinalTechItems.MACHINE_ACTIVATE_CARD_L2, 16), null,
            ItemStackUtil.cloneItem(FinalTechItems.MACHINE_ACTIVATE_CARD_L3, 4), null, ItemStackUtil.cloneItem(FinalTechItems.PHONY, 2), null, ItemStackUtil.cloneItem(FinalTechItems.MACHINE_ACTIVATE_CARD_L3, 4), null,
            ItemStackUtil.cloneItem(FinalTechItems.MACHINE_ACTIVATE_CARD_L2, 16), ItemStackUtil.cloneItem(FinalTechItems.MACHINE_ACTIVATE_CARD_L3, 4), null, ItemStackUtil.cloneItem(FinalTechItems.MACHINE_ACTIVATE_CARD_L3, 4), ItemStackUtil.cloneItem(FinalTechItems.MACHINE_ACTIVATE_CARD_L2, 16), null,
            ItemStackUtil.cloneItem(FinalTechItems.MACHINE_ACTIVATE_CARD_L1, 64), ItemStackUtil.cloneItem(FinalTechItems.MACHINE_ACTIVATE_CARD_L2, 16), ItemStackUtil.cloneItem(FinalTechItems.MACHINE_ACTIVATE_CARD_L3, 4), ItemStackUtil.cloneItem(FinalTechItems.MACHINE_ACTIVATE_CARD_L2, 16), ItemStackUtil.cloneItem(FinalTechItems.MACHINE_ACTIVATE_CARD_L1, 64), null,
    };
    // 4
    public static final ItemStack[] ADVANCED_AUTO_CRAFT = new ItemStack[] {
            FinalTechItems.PHONY, FinalTechItems.ANNULAR, FinalTechItems.PHONY,
            FinalTechItems.GEARWHEEL, FinalTechItems.BASIC_FRAME_MACHINE, FinalTechItems.GEARWHEEL,
            FinalTechItems.PHONY, FinalTechItems.ANNULAR, FinalTechItems.PHONY
    };
    // 8
    public static final ItemStack[] MATRIX_ITEM_DISMANTLE_TABLE = new ItemStack[] {
            FinalTechItems.ITEM_DISMANTLE_TABLE, FinalTechItems.ITEM_DISMANTLE_TABLE, FinalTechItems.ITEM_DISMANTLE_TABLE, FinalTechItems.ITEM_DISMANTLE_TABLE, FinalTechItems.ITEM_DISMANTLE_TABLE, FinalTechItems.ITEM_DISMANTLE_TABLE,
            FinalTechItems.ITEM_DISMANTLE_TABLE, null, null, null, null, FinalTechItems.ITEM_DISMANTLE_TABLE,
            FinalTechItems.ITEM_DISMANTLE_TABLE, null, ItemStackUtil.cloneItem(FinalTechItems.PHONY, 2), ItemStackUtil.cloneItem(FinalTechItems.PHONY, 2), null, FinalTechItems.ITEM_DISMANTLE_TABLE,
            FinalTechItems.ITEM_DISMANTLE_TABLE, null, ItemStackUtil.cloneItem(FinalTechItems.PHONY, 2), ItemStackUtil.cloneItem(FinalTechItems.PHONY, 2), null, FinalTechItems.ITEM_DISMANTLE_TABLE,
            FinalTechItems.ITEM_DISMANTLE_TABLE, null, null, null, null, FinalTechItems.ITEM_DISMANTLE_TABLE,
            FinalTechItems.ITEM_DISMANTLE_TABLE, FinalTechItems.ITEM_DISMANTLE_TABLE, FinalTechItems.ITEM_DISMANTLE_TABLE, FinalTechItems.ITEM_DISMANTLE_TABLE, FinalTechItems.ITEM_DISMANTLE_TABLE, FinalTechItems.ITEM_DISMANTLE_TABLE
    };
    // 12
    public static final ItemStack[] MATRIX_EXPANDED_CAPACITOR = new ItemStack[] {
            FinalTechItems.PHONY, FinalTechItems.ADVANCED_CHARGE_INCREASE_CAPACITOR, FinalTechItems.ADVANCED_CHARGE_INCREASE_CAPACITOR, FinalTechItems.ADVANCED_CHARGE_INCREASE_CAPACITOR, FinalTechItems.ADVANCED_CHARGE_INCREASE_CAPACITOR, FinalTechItems.PHONY,
            FinalTechItems.PHONY, FinalTechItems.ENERGIZED_STACK_EXPANDED_CAPACITOR, FinalTechItems.OVERLOADED_EXPANDED_CAPACITOR, FinalTechItems.OVERLOADED_EXPANDED_CAPACITOR, FinalTechItems.ENERGIZED_STACK_EXPANDED_CAPACITOR, FinalTechItems.PHONY,
            FinalTechItems.PHONY, FinalTechItems.OVERLOADED_EXPANDED_CAPACITOR, FinalTechItems.ENERGIZED_STACK_EXPANDED_CAPACITOR, FinalTechItems.ENERGIZED_STACK_EXPANDED_CAPACITOR, FinalTechItems.OVERLOADED_EXPANDED_CAPACITOR, FinalTechItems.PHONY,
            FinalTechItems.PHONY, FinalTechItems.OVERLOADED_EXPANDED_CAPACITOR, FinalTechItems.ENERGIZED_STACK_EXPANDED_CAPACITOR, FinalTechItems.ENERGIZED_STACK_EXPANDED_CAPACITOR, FinalTechItems.OVERLOADED_EXPANDED_CAPACITOR, FinalTechItems.PHONY,
            FinalTechItems.PHONY, FinalTechItems.ENERGIZED_STACK_EXPANDED_CAPACITOR, FinalTechItems.OVERLOADED_EXPANDED_CAPACITOR, FinalTechItems.OVERLOADED_EXPANDED_CAPACITOR, FinalTechItems.ENERGIZED_STACK_EXPANDED_CAPACITOR, FinalTechItems.PHONY,
            FinalTechItems.PHONY, FinalTechItems.ADVANCED_CONSUME_REDUCE_CAPACITOR, FinalTechItems.ADVANCED_CONSUME_REDUCE_CAPACITOR, FinalTechItems.ADVANCED_CONSUME_REDUCE_CAPACITOR, FinalTechItems.ADVANCED_CONSUME_REDUCE_CAPACITOR, FinalTechItems.PHONY,
    };
    // 12
    public static final ItemStack[] MATRIX_ITEM_DESERIALIZE_PARSER = new ItemStack[] {
            FinalTechItems.SHINE, FinalTechItems.SHINE, FinalTechItems.SHINE, FinalTechItems.SHINE, FinalTechItems.SHINE, FinalTechItems.SHINE,
            FinalTechItems.SHINE, FinalTechItems.PHONY, FinalTechItems.PHONY, FinalTechItems.PHONY, FinalTechItems.PHONY, FinalTechItems.SHINE,
            FinalTechItems.SHINE, FinalTechItems.PHONY, CopyCard.newItem(FinalTechItems.UNORDERED_DUST, "1"), CopyCard.newItem(FinalTechItems.UNORDERED_DUST, "1"), FinalTechItems.PHONY, FinalTechItems.SHINE,
            FinalTechItems.SHINE, FinalTechItems.PHONY, CopyCard.newItem(FinalTechItems.UNORDERED_DUST, "1"), CopyCard.newItem(FinalTechItems.UNORDERED_DUST, "1"), FinalTechItems.PHONY, FinalTechItems.SHINE,
            FinalTechItems.SHINE, FinalTechItems.PHONY, FinalTechItems.PHONY, FinalTechItems.PHONY, FinalTechItems.PHONY, FinalTechItems.SHINE,
            FinalTechItems.SHINE, FinalTechItems.SHINE, FinalTechItems.SHINE, FinalTechItems.SHINE, FinalTechItems.SHINE, FinalTechItems.SHINE
    };
    // 16
    public static final ItemStack[] ENTROPY_CONSTRUCTOR = new ItemStack[] {
            null, null, null, null, null, null,
            FinalTechItems.PHONY, FinalTechItems.PHONY, FinalTechItems.PHONY, FinalTechItems.PHONY, FinalTechItems.PHONY, FinalTechItems.PHONY,
            null, FinalTechItems.PHONY, null, FinalTechItems.PHONY, null, null,
            null, FinalTechItems.PHONY, null, FinalTechItems.PHONY, null, null,
            null, FinalTechItems.PHONY, null, FinalTechItems.PHONY, null, FinalTechItems.PHONY,
            FinalTechItems.PHONY, null, null, FinalTechItems.PHONY, FinalTechItems.PHONY, null
    };
    // 20
    public static final ItemStack[] MATRIX_GENERATOR = new ItemStack[] {
            FinalTechItems.PHONY, FinalTechItems.PHONY, FinalTechItems.PHONY, FinalTechItems.PHONY, FinalTechItems.PHONY, FinalTechItems.PHONY,
            FinalTechItems.PHONY, FinalTechItems.ENERGIZED_STACK_GENERATOR, FinalTechItems.OVERLOADED_GENERATOR, FinalTechItems.OVERLOADED_GENERATOR, FinalTechItems.ENERGIZED_STACK_GENERATOR, FinalTechItems.PHONY,
            FinalTechItems.PHONY, FinalTechItems.OVERLOADED_GENERATOR, FinalTechItems.ENERGIZED_STACK_GENERATOR, FinalTechItems.ENERGIZED_STACK_GENERATOR, FinalTechItems.OVERLOADED_GENERATOR, FinalTechItems.PHONY,
            FinalTechItems.PHONY, FinalTechItems.OVERLOADED_GENERATOR, FinalTechItems.ENERGIZED_STACK_GENERATOR, FinalTechItems.ENERGIZED_STACK_GENERATOR, FinalTechItems.OVERLOADED_GENERATOR, FinalTechItems.PHONY,
            FinalTechItems.PHONY, FinalTechItems.ENERGIZED_STACK_GENERATOR, FinalTechItems.OVERLOADED_GENERATOR, FinalTechItems.OVERLOADED_GENERATOR, FinalTechItems.ENERGIZED_STACK_GENERATOR, FinalTechItems.PHONY,
            FinalTechItems.PHONY, FinalTechItems.PHONY, FinalTechItems.PHONY, FinalTechItems.PHONY, FinalTechItems.PHONY, FinalTechItems.PHONY,
    };
    // 20
    public static final ItemStack[] MATRIX_ACCELERATOR = new ItemStack[] {
            FinalTechItems.PHONY, FinalTechItems.PHONY, FinalTechItems.PHONY, FinalTechItems.PHONY, FinalTechItems.PHONY, FinalTechItems.PHONY,
            FinalTechItems.PHONY, FinalTechItems.ENERGIZED_ACCELERATOR, FinalTechItems.OVERLOADED_ACCELERATOR, FinalTechItems.OVERLOADED_ACCELERATOR, FinalTechItems.ENERGIZED_ACCELERATOR, FinalTechItems.PHONY,
            FinalTechItems.PHONY, FinalTechItems.OVERLOADED_ACCELERATOR, FinalTechItems.ENERGIZED_ACCELERATOR, FinalTechItems.ENERGIZED_ACCELERATOR, FinalTechItems.OVERLOADED_ACCELERATOR, FinalTechItems.PHONY,
            FinalTechItems.PHONY, FinalTechItems.OVERLOADED_ACCELERATOR, FinalTechItems.ENERGIZED_ACCELERATOR, FinalTechItems.ENERGIZED_ACCELERATOR, FinalTechItems.OVERLOADED_ACCELERATOR, FinalTechItems.PHONY,
            FinalTechItems.PHONY, FinalTechItems.ENERGIZED_ACCELERATOR, FinalTechItems.OVERLOADED_ACCELERATOR, FinalTechItems.OVERLOADED_ACCELERATOR, FinalTechItems.ENERGIZED_ACCELERATOR, FinalTechItems.PHONY,
            FinalTechItems.PHONY, FinalTechItems.PHONY, FinalTechItems.PHONY, FinalTechItems.PHONY, FinalTechItems.PHONY, FinalTechItems.PHONY,
    };
    // 48
    public static final ItemStack[] MATRIX_ITEM_SERIALIZATION_CONSTRUCTOR = new ItemStack[] {
            FinalTechItems.SHINE, FinalTechItems.SHINE, FinalTechItems.SHINE, FinalTechItems.SHINE, FinalTechItems.SHINE, FinalTechItems.SHINE,
            FinalTechItems.SHINE, CopyCard.newItem(FinalTechItems.ORDERED_DUST, "1"), CopyCard.newItem(FinalTechItems.ORDERED_DUST, "1"), CopyCard.newItem(FinalTechItems.ORDERED_DUST, "1"), CopyCard.newItem(FinalTechItems.ORDERED_DUST, "1"), FinalTechItems.SHINE,
            FinalTechItems.SHINE, CopyCard.newItem(FinalTechItems.ORDERED_DUST, "1"), FinalTechItems.MATRIX_ITEM_DESERIALIZE_PARSER, FinalTechItems.MATRIX_ITEM_DESERIALIZE_PARSER, CopyCard.newItem(FinalTechItems.ORDERED_DUST, "1"), FinalTechItems.SHINE,
            FinalTechItems.SHINE, CopyCard.newItem(FinalTechItems.ORDERED_DUST, "1"), FinalTechItems.MATRIX_ITEM_DESERIALIZE_PARSER, FinalTechItems.MATRIX_ITEM_DESERIALIZE_PARSER, CopyCard.newItem(FinalTechItems.ORDERED_DUST, "1"), FinalTechItems.SHINE,
            FinalTechItems.SHINE, CopyCard.newItem(FinalTechItems.ORDERED_DUST, "1"), CopyCard.newItem(FinalTechItems.ORDERED_DUST, "1"), CopyCard.newItem(FinalTechItems.ORDERED_DUST, "1"), CopyCard.newItem(FinalTechItems.ORDERED_DUST, "1"), FinalTechItems.SHINE,
            FinalTechItems.SHINE, FinalTechItems.SHINE, FinalTechItems.SHINE, FinalTechItems.SHINE, FinalTechItems.SHINE, FinalTechItems.SHINE
    };
    // 64
    public static final ItemStack[] MATRIX_REACTOR = new ItemStack[] {
            null, null, null, null, null, null,
            null, CopyCard.newItem(FinalTechItems.UNORDERED_DUST, "1"), null, null, CopyCard.newItem(FinalTechItems.ORDERED_DUST, "1"), null,
            CopyCard.newItem(FinalTechItems.UNORDERED_DUST, "1"), ItemStackUtil.cloneItem(FinalTechItems.PHONY, 16), CopyCard.newItem(FinalTechItems.ORDERED_DUST, "1"), CopyCard.newItem(FinalTechItems.UNORDERED_DUST, "1"), ItemStackUtil.cloneItem(FinalTechItems.PHONY, 16), CopyCard.newItem(FinalTechItems.ORDERED_DUST, "1"),
            CopyCard.newItem(FinalTechItems.UNORDERED_DUST, "1"), ItemStackUtil.cloneItem(FinalTechItems.PHONY, 16), CopyCard.newItem(FinalTechItems.UNORDERED_DUST, "1"), CopyCard.newItem(FinalTechItems.ORDERED_DUST, "1"), ItemStackUtil.cloneItem(FinalTechItems.PHONY, 16), CopyCard.newItem(FinalTechItems.ORDERED_DUST, "1"),
            null, CopyCard.newItem(FinalTechItems.UNORDERED_DUST, "1"), null, null, CopyCard.newItem(FinalTechItems.ORDERED_DUST, "1"), null,
            null, null, null, null, null, null,
    };
}
