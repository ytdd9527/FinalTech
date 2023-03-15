package io.taraxacum.finaltech.setup;

import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.taraxacum.finaltech.core.item.machine.*;
import io.taraxacum.finaltech.core.item.machine.cargo.*;
import io.taraxacum.finaltech.core.item.machine.cargo.storage.StorageInsertPort;
import io.taraxacum.finaltech.core.item.machine.cargo.storage.StorageInteractPort;
import io.taraxacum.finaltech.core.item.machine.cargo.storage.StorageWithdrawPort;
import io.taraxacum.finaltech.core.item.machine.clicker.*;
import io.taraxacum.finaltech.core.item.machine.electric.VariableWireCapacitor;
import io.taraxacum.finaltech.core.item.machine.electric.VariableWireResistance;
import io.taraxacum.finaltech.core.item.machine.electric.capacitor.expanded.*;
import io.taraxacum.finaltech.core.item.machine.logic.LogicAmountComparator;
import io.taraxacum.finaltech.core.item.machine.logic.LogicEqualComparator;
import io.taraxacum.finaltech.core.item.machine.logic.LogicNotNullComparator;
import io.taraxacum.finaltech.core.item.machine.logic.LogicSimilarComparator;
import io.taraxacum.finaltech.core.item.machine.manual.CardOperationTable;
import io.taraxacum.finaltech.core.item.machine.manual.EquivalentExchangeTable;
import io.taraxacum.finaltech.core.item.machine.manual.ItemDismantleTable;
import io.taraxacum.finaltech.core.item.machine.manual.MatrixCraftingTable;
import io.taraxacum.finaltech.core.item.machine.manual.craft.*;
import io.taraxacum.finaltech.core.item.machine.operation.DustFactoryDirt;
import io.taraxacum.finaltech.core.item.machine.operation.ItemSerializationConstructor;
import io.taraxacum.finaltech.core.item.machine.operation.MatrixItemSerializationConstructor;
import io.taraxacum.finaltech.core.item.machine.range.cube.EnergizedAccelerator;
import io.taraxacum.finaltech.core.item.machine.range.cube.MatrixAccelerator;
import io.taraxacum.finaltech.core.item.machine.range.cube.OverloadedAccelerator;
import io.taraxacum.finaltech.core.item.machine.range.cube.generator.*;
import io.taraxacum.finaltech.core.item.machine.range.line.ConfigurationCopier;
import io.taraxacum.finaltech.core.item.machine.range.line.ConfigurationPaster;
import io.taraxacum.finaltech.core.item.machine.range.line.pile.EnergizedElectricityShootPile;
import io.taraxacum.finaltech.core.item.machine.range.line.pile.NormalElectricityShootPile;
import io.taraxacum.finaltech.core.item.machine.range.line.pile.OverloadedElectricityShootPile;
import io.taraxacum.finaltech.core.item.machine.range.point.EquivalentConcept;
import io.taraxacum.finaltech.core.item.machine.range.point.NormalConfigurableElectricityShootPile;
import io.taraxacum.finaltech.core.item.machine.range.point.NormalConsumableElectricityShootPile;
import io.taraxacum.finaltech.core.item.machine.range.point.face.*;
import io.taraxacum.finaltech.core.item.machine.template.advanced.*;
import io.taraxacum.finaltech.core.item.machine.template.basic.BasicLogicFactory;
import io.taraxacum.finaltech.core.item.machine.template.conversion.GravelConversion;
import io.taraxacum.finaltech.core.item.machine.template.conversion.LogicToDigitalConversion;
import io.taraxacum.finaltech.core.item.machine.template.conversion.SoulSandConversion;
import io.taraxacum.finaltech.core.item.machine.template.extraction.DigitalExtraction;
import io.taraxacum.finaltech.core.item.machine.template.generator.DigitalGenerator;
import io.taraxacum.finaltech.core.item.machine.template.generator.LiquidCardGenerator;
import io.taraxacum.finaltech.core.item.machine.template.generator.LogicGenerator;
import io.taraxacum.finaltech.core.item.machine.tower.*;
import io.taraxacum.finaltech.core.item.machine.unit.*;
import io.taraxacum.finaltech.core.item.tool.*;
import io.taraxacum.finaltech.core.item.unusable.*;
import io.taraxacum.finaltech.core.item.unusable.digital.*;
import io.taraxacum.finaltech.core.item.unusable.liquid.LavaCard;
import io.taraxacum.finaltech.core.item.unusable.liquid.MilkCard;
import io.taraxacum.finaltech.core.item.unusable.liquid.WaterCard;
import io.taraxacum.finaltech.core.item.unusable.logic.LogicFalse;
import io.taraxacum.finaltech.core.item.unusable.logic.LogicTrue;
import io.taraxacum.finaltech.core.item.unusable.trophy.Meawerful;
import io.taraxacum.finaltech.core.item.unusable.trophy.Shixinzia;
import io.taraxacum.finaltech.core.item.usable.*;
import io.taraxacum.finaltech.core.item.usable.machine.*;
import io.taraxacum.libs.plugin.util.ItemStackUtil;
import org.bukkit.inventory.ItemStack;

public final class FinalTechItems {
    /* items */
    // material
    public static final CopyCard COPY_CARD = new CopyCard(FinalTechMenus.MENU_ITEMS, FinalTechItemStacks.COPY_CARD, RecipeType.NULL, new ItemStack[] {});

    public static final ReplaceableCard WATER_CARD = new ReplaceableCard(FinalTechMenus.MENU_ITEMS, FinalTechItemStacks.WATER_CARD, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.WATER_CARD, Material.WATER_BUCKET, Material.BUCKET);
    public static final ReplaceableCard LAVA_CARD = new ReplaceableCard(FinalTechMenus.MENU_ITEMS, FinalTechItemStacks.LAVA_CARD, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.LAVA_CARD, Material.LAVA_BUCKET, Material.BUCKET);
    public static final ReplaceableCard MILK_CARD = new ReplaceableCard(FinalTechMenus.MENU_ITEMS, FinalTechItemStacks.MILK_CARD, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.MILK_CARD, Material.MILK_BUCKET, Material.BUCKET);

    public static final Gearwheel GEARWHEEL = new Gearwheel(FinalTechMenus.MENU_ITEMS, FinalTechItemStacks.GEARWHEEL, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.GEARWHEEL, ItemStackUtil.cloneItem(FinalTechItemStacks.GEARWHEEL, 4));
    public static final UnorderedDust UNORDERED_DUST = new UnorderedDust(FinalTechMenus.MENU_ITEMS, FinalTechItemStacks.UNORDERED_DUST,  FinalTechRecipes.RECIPE_TYPE_ORDERED_DUST_FACTORY, FinalTechRecipes.UNORDERED_DUST);
    public static final OrderedDust ORDERED_DUST = new OrderedDust(FinalTechMenus.MENU_ITEMS, FinalTechItemStacks.ORDERED_DUST, FinalTechRecipes.RECIPE_TYPE_ORDERED_DUST_FACTORY, FinalTechRecipes.ORDERED_DUST);
    public static final Bug BUG = new Bug(FinalTechMenus.MENU_ITEMS, FinalTechItemStacks.BUG, FinalTechRecipes.RECIPE_TYPE_EQUIVALENT_EXCHANGE_TABLE, FinalTechRecipes.BUG);
    public static final Entropy ENTROPY = new Entropy(FinalTechMenus.MENU_ITEMS, FinalTechItemStacks.ENTROPY, FinalTechRecipes.RECIPE_TYPE_ENTROPY_CONSTRUCTOR, FinalTechRecipes.ENTROPY);
    public static final Box BOX = new Box(FinalTechMenus.MENU_ITEMS, FinalTechItemStacks.BOX, RecipeType.NULL, FinalTechRecipes.BOX);
    public static final Shine SHINE = new Shine(FinalTechMenus.MENU_ITEMS, FinalTechItemStacks.SHINE, FinalTechRecipes.RECIPE_TYPE_BOX, FinalTechRecipes.SHINE);

    public static final Annular ANNULAR = new Annular(FinalTechMenus.MENU_ITEMS, FinalTechItemStacks.ANNULAR, FinalTechRecipes.RECIPE_TYPE_CARD_OPERATION_TABLE, FinalTechRecipes.ANNULAR);
    public static final QuantityModule QUANTITY_MODULE = new QuantityModule(FinalTechMenus.MENU_ITEMS, FinalTechItemStacks.QUANTITY_MODULE, FinalTechRecipes.RECIPE_TYPE_MATRIX_CRAFTING_TABLE, FinalTechRecipes.QUANTITY_MODULE);
    public static final QuantityModuleInfinity QUANTITY_MODULE_INFINITY = new QuantityModuleInfinity(FinalTechMenus.MENU_ITEMS, FinalTechItemStacks.QUANTITY_MODULE_INFINITY, FinalTechRecipes.RECIPE_TYPE_MATRIX_CRAFTING_TABLE, FinalTechRecipes.QUANTITY_MODULE_INFINITY);
    public static final Singularity SINGULARITY = new Singularity(FinalTechMenus.MENU_ITEMS, FinalTechItemStacks.SINGULARITY, FinalTechRecipes.RECIPE_TYPE_ITEM_SERIALIZATION_CONSTRUCTOR, FinalTechRecipes.SINGULARITY);
    public static final Spirochete SPIROCHETE = new Spirochete(FinalTechMenus.MENU_ITEMS, FinalTechItemStacks.SPIROCHETE, FinalTechRecipes.RECIPE_TYPE_ITEM_SERIALIZATION_CONSTRUCTOR, FinalTechRecipes.SPIROCHETE);
    public static final Shell SHELL = new Shell(FinalTechMenus.MENU_ITEMS, FinalTechItemStacks.SHELL, FinalTechRecipes.RECIPE_TYPE_CARD_OPERATION_TABLE, FinalTechRecipes.SHELL);
    public static final ItemPhony ITEM_PHONY = new ItemPhony(FinalTechMenus.MENU_ITEMS, FinalTechItemStacks.PHONY, FinalTechRecipes.RECIPE_TYPE_CARD_OPERATION_TABLE, FinalTechRecipes.PHONY);
    public static final Justifiability JUSTIFIABILITY = new Justifiability(FinalTechMenus.MENU_ITEMS, FinalTechItemStacks.JUSTIFIABILITY, FinalTechRecipes.RECIPE_TYPE_ENTROPY_SEED, FinalTechRecipes.JUSTIFIABILITY);
    public static final EquivalentConcept EQUIVALENT_CONCEPT = new EquivalentConcept(FinalTechMenus.MENU_ITEMS, FinalTechItemStacks.EQUIVALENT_CONCEPT, FinalTechRecipes.RECIPE_TYPE_ENTROPY_SEED, FinalTechRecipes.EQUIVALENT_CONCEPT);

    public static final Logic LOGIC_FALSE = new Logic(FinalTechMenus.MENU_ITEMS, FinalTechItemStacks.LOGIC_FALSE, FinalTechRecipes.RECIPE_TYPE_LOGIC_GENERATOR, FinalTechRecipes.LOGIC_FALSE, false);
    public static final Logic LOGIC_TRUE = new Logic(FinalTechMenus.MENU_ITEMS, FinalTechItemStacks.LOGIC_TRUE, FinalTechRecipes.RECIPE_TYPE_LOGIC_GENERATOR, FinalTechRecipes.LOGIC_TRUE, true);
    public static final DigitalNumber DIGITAL_ZERO = new DigitalNumber(FinalTechMenus.MENU_ITEMS, FinalTechItemStacks.DIGITAL_ZERO, FinalTechRecipes.RECIPE_TYPE_LOGIC_CRAFTER, FinalTechRecipes.DIGITAL_ZERO, 0);
    public static final DigitalNumber DIGITAL_ONE = new DigitalNumber(FinalTechMenus.MENU_ITEMS, FinalTechItemStacks.DIGITAL_ONE, FinalTechRecipes.RECIPE_TYPE_LOGIC_CRAFTER, FinalTechRecipes.DIGITAL_ONE, 1);
    public static final DigitalNumber DIGITAL_TWO = new DigitalNumber(FinalTechMenus.MENU_ITEMS, FinalTechItemStacks.DIGITAL_TWO, FinalTechRecipes.RECIPE_TYPE_LOGIC_CRAFTER, FinalTechRecipes.DIGITAL_TWO, 2);
    public static final DigitalNumber DIGITAL_THREE = new DigitalNumber(FinalTechMenus.MENU_ITEMS, FinalTechItemStacks.DIGITAL_THREE, FinalTechRecipes.RECIPE_TYPE_LOGIC_CRAFTER, FinalTechRecipes.DIGITAL_THREE, 3);
    public static final DigitalNumber DIGITAL_FOUR = new DigitalNumber(FinalTechMenus.MENU_ITEMS, FinalTechItemStacks.DIGITAL_FOUR, FinalTechRecipes.RECIPE_TYPE_LOGIC_CRAFTER, FinalTechRecipes.DIGITAL_FOUR, 4);
    public static final DigitalNumber DIGITAL_FIVE = new DigitalNumber(FinalTechMenus.MENU_ITEMS, FinalTechItemStacks.DIGITAL_FIVE, FinalTechRecipes.RECIPE_TYPE_LOGIC_CRAFTER, FinalTechRecipes.DIGITAL_FIVE, 5);
    public static final DigitalNumber DIGITAL_SIX = new DigitalNumber(FinalTechMenus.MENU_ITEMS, FinalTechItemStacks.DIGITAL_SIX, FinalTechRecipes.RECIPE_TYPE_LOGIC_CRAFTER, FinalTechRecipes.DIGITAL_SIX, 6);
    public static final DigitalNumber DIGITAL_SEVEN = new DigitalNumber(FinalTechMenus.MENU_ITEMS, FinalTechItemStacks.DIGITAL_SEVEN, FinalTechRecipes.RECIPE_TYPE_LOGIC_CRAFTER, FinalTechRecipes.DIGITAL_SEVEN, 7);
    public static final DigitalNumber DIGITAL_EIGHT = new DigitalNumber(FinalTechMenus.MENU_ITEMS, FinalTechItemStacks.DIGITAL_EIGHT, FinalTechRecipes.RECIPE_TYPE_LOGIC_CRAFTER, FinalTechRecipes.DIGITAL_EIGHT, 8);
    public static final DigitalNumber DIGITAL_NINE = new DigitalNumber(FinalTechMenus.MENU_ITEMS, FinalTechItemStacks.DIGITAL_NINE, FinalTechRecipes.RECIPE_TYPE_LOGIC_CRAFTER, FinalTechRecipes.DIGITAL_NINE, 9);
    public static final DigitalNumber DIGITAL_TEN = new DigitalNumber(FinalTechMenus.MENU_ITEMS, FinalTechItemStacks.DIGITAL_TEN, FinalTechRecipes.RECIPE_TYPE_LOGIC_CRAFTER, FinalTechRecipes.DIGITAL_TEN, 10);
    public static final DigitalNumber DIGITAL_ELEVEN = new DigitalNumber(FinalTechMenus.MENU_ITEMS, FinalTechItemStacks.DIGITAL_ELEVEN, FinalTechRecipes.RECIPE_TYPE_LOGIC_CRAFTER, FinalTechRecipes.DIGITAL_ELEVEN, 11);
    public static final DigitalNumber DIGITAL_TWELVE = new DigitalNumber(FinalTechMenus.MENU_ITEMS, FinalTechItemStacks.DIGITAL_TWELVE, FinalTechRecipes.RECIPE_TYPE_LOGIC_CRAFTER, FinalTechRecipes.DIGITAL_TWELVE, 12);
    public static final DigitalNumber DIGITAL_THIRTEEN = new DigitalNumber(FinalTechMenus.MENU_ITEMS, FinalTechItemStacks.DIGITAL_THIRTEEN, FinalTechRecipes.RECIPE_TYPE_LOGIC_CRAFTER, FinalTechRecipes.DIGITAL_THIRTEEN, 13);
    public static final DigitalNumber DIGITAL_FOURTEEN = new DigitalNumber(FinalTechMenus.MENU_ITEMS, FinalTechItemStacks.DIGITAL_FOURTEEN, FinalTechRecipes.RECIPE_TYPE_LOGIC_CRAFTER, FinalTechRecipes.DIGITAL_FOURTEEN, 14);
    public static final DigitalNumber DIGITAL_FIFTEEN = new DigitalNumber(FinalTechMenus.MENU_ITEMS, FinalTechItemStacks.DIGITAL_FIFTEEN, FinalTechRecipes.RECIPE_TYPE_LOGIC_CRAFTER, FinalTechRecipes.DIGITAL_FIFTEEN, 15);

    public static final MachineChargeCardL1 MACHINE_CHARGE_CARD_L1 = new MachineChargeCardL1(FinalTechMenus.MENU_ITEMS, FinalTechItemStacks.MACHINE_CHARGE_CARD_L1, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.MACHINE_CHARGE_CARD_L1);
    public static final MachineChargeCardL2 MACHINE_CHARGE_CARD_L2 = new MachineChargeCardL2(FinalTechMenus.MENU_ITEMS, FinalTechItemStacks.MACHINE_CHARGE_CARD_L2, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.MACHINE_CHARGE_CARD_L2);
    public static final MachineChargeCardL3 MACHINE_CHARGE_CARD_L3 = new MachineChargeCardL3(FinalTechMenus.MENU_ITEMS, FinalTechItemStacks.MACHINE_CHARGE_CARD_L3, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.MACHINE_CHARGE_CARD_L3);
    public static final MachineAccelerateCardL1 MACHINE_ACCELERATE_CARD_L1 = new MachineAccelerateCardL1(FinalTechMenus.MENU_ITEMS, FinalTechItemStacks.MACHINE_ACCELERATE_CARD_L1, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.MACHINE_ACCELERATE_CARD_L1);
    public static final MachineAccelerateCardL2 MACHINE_ACCELERATE_CARD_L2 = new MachineAccelerateCardL2(FinalTechMenus.MENU_ITEMS, FinalTechItemStacks.MACHINE_ACCELERATE_CARD_L2, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.MACHINE_ACCELERATE_CARD_L2);
    public static final MachineAccelerateCardL3 MACHINE_ACCELERATE_CARD_L3 = new MachineAccelerateCardL3(FinalTechMenus.MENU_ITEMS, FinalTechItemStacks.MACHINE_ACCELERATE_CARD_L3, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.MACHINE_ACCELERATE_CARD_L3);
    public static final MachineActivateCardL1 MACHINE_ACTIVATE_CARD_L1 = new MachineActivateCardL1(FinalTechMenus.MENU_ITEMS, FinalTechItemStacks.MACHINE_ACTIVATE_CARD_L1, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.MACHINE_ACTIVATE_CARD_L1);
    public static final MachineActivateCardL2 MACHINE_ACTIVATE_CARD_L2 = new MachineActivateCardL2(FinalTechMenus.MENU_ITEMS, FinalTechItemStacks.MACHINE_ACTIVATE_CARD_L2, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.MACHINE_ACTIVATE_CARD_L2);
    public static final MachineActivateCardL3 MACHINE_ACTIVATE_CARD_L3 = new MachineActivateCardL3(FinalTechMenus.MENU_ITEMS, FinalTechItemStacks.MACHINE_ACTIVATE_CARD_L3, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.MACHINE_ACTIVATE_CARD_L3);

    public static final MagicHypnotic MAGIC_HYPNOTIC = new MagicHypnotic(FinalTechMenus.MENU_ITEMS, FinalTechItemStacks.MAGIC_HYPNOTIC, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.MAGIC_HYPNOTIC);
    public static final ResearchUnlockTicket RESEARCH_UNLOCK_TICKET = new ResearchUnlockTicket(FinalTechMenus.MENU_ITEMS, FinalTechItemStacks.RESEARCH_UNLOCK_TICKET, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.RESEARCH_UNLOCK_TICKET);

    public static final StaffElementalLine STAFF_ELEMENTAL_LINE = new StaffElementalLine(FinalTechMenus.MENU_ITEMS, FinalTechItemStacks.STAFF_ELEMENTAL_LINE, RecipeType.MAGIC_WORKBENCH, FinalTechRecipes.STAFF_ELEMENTAL_LINE);
    public static final PotionEffectCompressor POTION_EFFECT_COMPRESSOR = new PotionEffectCompressor(FinalTechMenus.MENU_ITEMS, FinalTechItemStacks.POTION_EFFECT_COMPRESSOR, FinalTechRecipes.RECIPE_TYPE_MATRIX_CRAFTING_TABLE, FinalTechRecipes.POTION_EFFECT_COMPRESSOR);
    public static final PotionEffectDilator POTION_EFFECT_DILATOR = new PotionEffectDilator(FinalTechMenus.MENU_ITEMS, FinalTechItemStacks.POTION_EFFECT_DILATOR, FinalTechRecipes.RECIPE_TYPE_MATRIX_CRAFTING_TABLE, FinalTechRecipes.POTION_EFFECT_DILATOR);
    public static final PotionEffectPurifier POTION_EFFECT_PURIFIER = new PotionEffectPurifier(FinalTechMenus.MENU_ITEMS, FinalTechItemStacks.POTION_EFFECT_PURIFIER, FinalTechRecipes.RECIPE_TYPE_MATRIX_CRAFTING_TABLE, FinalTechRecipes.POTION_EFFECT_PURIFIER);
    public static final GravityReversalRune GRAVITY_REVERSAL_RUNE = new GravityReversalRune(FinalTechMenus.MENU_ITEMS, FinalTechItemStacks.GRAVITY_REVERSAL_RUNE, FinalTechRecipes.RECIPE_TYPE_MATRIX_CRAFTING_TABLE, FinalTechRecipes.GRAVITY_REVERSAL_RUNE);
    public static final MenuViewer MENU_VIEWER = new MenuViewer(FinalTechMenus.MENU_ITEMS, FinalTechItemStacks.MENU_VIEWER, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.MENU_VIEWER);
    public static final LocationRecorder LOCATION_RECORDER = new LocationRecorder(FinalTechMenus.MENU_ITEMS, FinalTechItemStacks.LOCATION_RECORDER, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.LOCATION_RECORDER);
    public static final MachineConfigurator MACHINE_CONFIGURATOR = new MachineConfigurator(FinalTechMenus.MENU_ITEMS, FinalTechItemStacks.MACHINE_CONFIGURATOR, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.MACHINE_CONFIGURATOR);
    public static final PortableEnergyStorage PORTABLE_ENERGY_STORAGE = new PortableEnergyStorage(FinalTechMenus.MENU_ITEMS, FinalTechItemStacks.PORTABLE_ENERGY_STORAGE, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.PORTABLE_ENERGY_STORAGE);

    public static final SuperShovel SUPER_SHOVEL = new SuperShovel(FinalTechMenus.MENU_ITEMS, FinalTechItemStacks.SUPER_SHOVEL, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.SUPER_SHOVEL);
    public static final UltimateShovel ULTIMATE_SHOVEL = new UltimateShovel(FinalTechMenus.MENU_ITEMS, FinalTechItemStacks.ULTIMATE_SHOVEL, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.ULTIMATE_SHOVEL);
    public static final SuperPickaxe SUPER_PICKAXE = new SuperPickaxe(FinalTechMenus.MENU_ITEMS, FinalTechItemStacks.SUPER_PICKAXE, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.SUPER_PICKAXE);
    public static final UltimatePickaxe ULTIMATE_PICKAXE = new UltimatePickaxe(FinalTechMenus.MENU_ITEMS, FinalTechItemStacks.ULTIMATE_PICKAXE, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.ULTIMATE_PICKAXE);
    public static final SuperAxe SUPER_AXE = new SuperAxe(FinalTechMenus.MENU_ITEMS, FinalTechItemStacks.SUPER_AXE, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.SUPER_AXE);
    public static final UltimateAxe ULTIMATE_AXE = new UltimateAxe(FinalTechMenus.MENU_ITEMS, FinalTechItemStacks.ULTIMATE_AXE, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.ULTIMATE_AXE);
    public static final SuperHoe SUPER_HOE = new SuperHoe(FinalTechMenus.MENU_ITEMS, FinalTechItemStacks.SUPER_HOE, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.SUPER_HOE);
    public static final UltimateHoe ULTIMATE_HOE = new UltimateHoe(FinalTechMenus.MENU_ITEMS, FinalTechItemStacks.ULTIMATE_HOE, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.ULTIMATE_HOE);

    /* electricity system */
    public static final BasicGenerator BASIC_GENERATOR = new BasicGenerator(FinalTechMenus.MENU_ELECTRICITY_SYSTEM, FinalTechItemStacks.BASIC_GENERATOR, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.BASIC_GENERATOR);
    public static final AdvancedGenerator ADVANCED_GENERATOR = new AdvancedGenerator(FinalTechMenus.MENU_ELECTRICITY_SYSTEM, FinalTechItemStacks.ADVANCED_GENERATOR, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.ADVANCED_GENERATOR);
    public static final CarbonadoGenerator CARBONADO_GENERATOR = new CarbonadoGenerator(FinalTechMenus.MENU_ELECTRICITY_SYSTEM, FinalTechItemStacks.CARBONADO_GENERATOR, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.CARBONADO_GENERATOR);
    public static final EnergizedGenerator ENERGIZED_GENERATOR = new EnergizedGenerator(FinalTechMenus.MENU_ELECTRICITY_SYSTEM, FinalTechItemStacks.ENERGIZED_GENERATOR, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.ENERGIZED_GENERATOR);
    public static final EnergizedStackGenerator ENERGIZED_STACK_GENERATOR = new EnergizedStackGenerator(FinalTechMenus.MENU_ELECTRICITY_SYSTEM, FinalTechItemStacks.ENERGIZED_STACK_GENERATOR, FinalTechRecipes.RECIPE_TYPE_MATRIX_CRAFTING_TABLE, FinalTechRecipes.ENERGIZED_STACK_GENERATOR);
    public static final OverloadedGenerator OVERLOADED_GENERATOR = new OverloadedGenerator(FinalTechMenus.MENU_ELECTRICITY_SYSTEM, FinalTechItemStacks.OVERLOADED_GENERATOR, FinalTechRecipes.RECIPE_TYPE_MATRIX_CRAFTING_TABLE, FinalTechRecipes.OVERLOADED_GENERATOR);

    public static final DustGenerator DUST_GENERATOR = new DustGenerator(FinalTechMenus.MENU_ELECTRICITY_SYSTEM, FinalTechItemStacks.ORDERED_DUST_GENERATOR, FinalTechRecipes.RECIPE_TYPE_MATRIX_CRAFTING_TABLE, FinalTechRecipes.ORDERED_DUST_GENERATOR);
    public static final TimeGenerator TIME_GENERATOR = new TimeGenerator(FinalTechMenus.MENU_ELECTRICITY_SYSTEM, FinalTechItemStacks.TIME_GENERATOR, FinalTechRecipes.RECIPE_TYPE_MATRIX_CRAFTING_TABLE, FinalTechRecipes.TIME_GENERATOR);
    public static final EnergizedChargeBase ENERGIZED_CHARGE_BASE = new EnergizedChargeBase(FinalTechMenus.MENU_ELECTRICITY_SYSTEM, FinalTechItemStacks.ENERGIZED_CHARGE_BASE, FinalTechRecipes.RECIPE_TYPE_MATRIX_CRAFTING_TABLE, FinalTechRecipes.ENERGIZED_CHARGE_BASE);
    public static final OverloadedChargeBase OVERLOADED_CHARGE_BASE = new OverloadedChargeBase(FinalTechMenus.MENU_ELECTRICITY_SYSTEM, FinalTechItemStacks.OVERLOADED_CHARGE_BASE, FinalTechRecipes.RECIPE_TYPE_MATRIX_CRAFTING_TABLE, FinalTechRecipes.OVERLOADED_CHARGE_BASE);

    public static final SmallExpandedCapacitor SMALL_EXPANDED_CAPACITOR = new SmallExpandedCapacitor(FinalTechMenus.MENU_ELECTRICITY_SYSTEM, FinalTechItemStacks.SMALL_EXPANDED_CAPACITOR, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.SMALL_EXPANDED_CAPACITOR);
    public static final MediumExpandedCapacitor MEDIUM_EXPANDED_CAPACITOR = new MediumExpandedCapacitor(FinalTechMenus.MENU_ELECTRICITY_SYSTEM, FinalTechItemStacks.MEDIUM_EXPANDED_CAPACITOR, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.MEDIUM_EXPANDED_CAPACITOR);
    public static final BigExpandedCapacitor BIG_EXPANDED_CAPACITOR = new BigExpandedCapacitor(FinalTechMenus.MENU_ELECTRICITY_SYSTEM, FinalTechItemStacks.BIG_EXPANDED_CAPACITOR, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.BIG_EXPANDED_CAPACITOR);
    public static final LargeExpandedCapacitor LARGE_EXPANDED_CAPACITOR = new LargeExpandedCapacitor(FinalTechMenus.MENU_ELECTRICITY_SYSTEM, FinalTechItemStacks.LARGE_EXPANDED_CAPACITOR, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.LARGE_EXPANDED_CAPACITOR);
    public static final CarbonadoExpandedCapacitor CARBONADO_EXPANDED_CAPACITOR = new CarbonadoExpandedCapacitor(FinalTechMenus.MENU_ELECTRICITY_SYSTEM, FinalTechItemStacks.CARBONADO_EXPANDED_CAPACITOR, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.CARBONADO_EXPANDED_CAPACITOR);
    public static final EnergizedExpandedCapacitor ENERGIZED_EXPANDED_CAPACITOR = new EnergizedExpandedCapacitor(FinalTechMenus.MENU_ELECTRICITY_SYSTEM, FinalTechItemStacks.ENERGIZED_EXPANDED_CAPACITOR, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.ENERGIZED_EXPANDED_CAPACITOR);

    public static final EnergizedStackExpandedCapacitor ENERGIZED_STACK_EXPANDED_CAPACITOR = new EnergizedStackExpandedCapacitor(FinalTechMenus.MENU_ELECTRICITY_SYSTEM, FinalTechItemStacks.ENERGIZED_STACK_EXPANDED_CAPACITOR, FinalTechRecipes.RECIPE_TYPE_MATRIX_CRAFTING_TABLE, FinalTechRecipes.ENERGIZED_STACK_EXPANDED_CAPACITOR);
    public static final OverloadedExpandedCapacitor OVERLOADED_EXPANDED_CAPACITOR = new OverloadedExpandedCapacitor(FinalTechMenus.MENU_ELECTRICITY_SYSTEM, FinalTechItemStacks.OVERLOADED_EXPANDED_CAPACITOR, FinalTechRecipes.RECIPE_TYPE_MATRIX_CRAFTING_TABLE, FinalTechRecipes.OVERLOADED_EXPANDED_CAPACITOR);
    public static final TimeCapacitor TIME_CAPACITOR = new TimeCapacitor(FinalTechMenus.MENU_ELECTRICITY_SYSTEM, FinalTechItemStacks.TIME_CAPACITOR, FinalTechRecipes.RECIPE_TYPE_MATRIX_CRAFTING_TABLE, FinalTechRecipes.TIME_CAPACITOR);

    public static final NormalElectricityShootPile NORMAL_ELECTRICITY_SHOOT_PILE = new NormalElectricityShootPile(FinalTechMenus.MENU_ELECTRICITY_SYSTEM, FinalTechItemStacks.NORMAL_ELECTRICITY_SHOOT_PILE, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.NORMAL_ELECTRICITY_SHOOT_PILE);
    public static final NormalConsumableElectricityShootPile NORMAL_CONSUMABLE_ELECTRICITY_SHOOT_PILE = new NormalConsumableElectricityShootPile(FinalTechMenus.MENU_ELECTRICITY_SYSTEM, FinalTechItemStacks.NORMAL_CONSUMABLE_ELECTRICITY_SHOOT_PILE, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.NORMAL_CONSUMABLE_ELECTRICITY_SHOOT_PILE);
    public static final NormalConfigurableElectricityShootPile NORMAL_CONFIGURABLE_ELECTRICITY_SHOOT_PILE = new NormalConfigurableElectricityShootPile(FinalTechMenus.MENU_ELECTRICITY_SYSTEM, FinalTechItemStacks.NORMAL_CONFIGURABLE_ELECTRICITY_SHOOT_PILE, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.NORMAL_CONFIGURABLE_ELECTRICITY_SHOOT_PILE);
    public static final EnergizedElectricityShootPile ENERGIZED_ELECTRICITY_SHOOT_PILE = new EnergizedElectricityShootPile(FinalTechMenus.MENU_ELECTRICITY_SYSTEM, FinalTechItemStacks.ENERGIZED_ELECTRICITY_SHOOT_PILE, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.ENERGIZED_ELECTRICITY_SHOOT_PILE);
    public static final OverloadedElectricityShootPile OVERLOADED_ELECTRICITY_SHOOT_PILE = new OverloadedElectricityShootPile(FinalTechMenus.MENU_ELECTRICITY_SYSTEM, FinalTechItemStacks.OVERLOADED_ELECTRICITY_SHOOT_PILE, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.OVERLOADED_ELECTRICITY_SHOOT_PILE);
    public static final VariableWireResistance VARIABLE_WIRE_RESISTANCE = new VariableWireResistance(FinalTechMenus.MENU_ELECTRICITY_SYSTEM, FinalTechItemStacks.VARIABLE_WIRE_RESISTANCE, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.VARIABLE_WIRE_RESISTANCE);
    public static final VariableWireCapacitor VARIABLE_WIRE_CAPACITOR = new VariableWireCapacitor(FinalTechMenus.MENU_ELECTRICITY_SYSTEM, FinalTechItemStacks.VARIABLE_WIRE_CAPACITOR, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.VARIABLE_WIRE_CAPACITOR);

    public static final EnergizedAccelerator ENERGIZED_ACCELERATOR = new EnergizedAccelerator(FinalTechMenus.MENU_ELECTRICITY_SYSTEM, FinalTechItemStacks.ENERGIZED_ACCELERATOR, FinalTechRecipes.RECIPE_TYPE_MATRIX_CRAFTING_TABLE, FinalTechRecipes.ENERGIZED_ACCELERATOR);
    public static final OverloadedAccelerator OVERLOADED_ACCELERATOR = new OverloadedAccelerator(FinalTechMenus.MENU_ELECTRICITY_SYSTEM, FinalTechItemStacks.OVERLOADED_ACCELERATOR, FinalTechRecipes.RECIPE_TYPE_MATRIX_CRAFTING_TABLE, FinalTechRecipes.OVERLOADED_ACCELERATOR);

    public static final NormalStorageUnit NORMAL_STORAGE_UNIT = new NormalStorageUnit(FinalTechMenus.MENU_CARGO_SYSTEM, FinalTechItemStacks.NORMAL_STORAGE_UNIT, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.NORMAL_STORAGE_UNIT);
    public static final DividedStorageUnit DIVIDED_STORAGE_UNIT = new DividedStorageUnit(FinalTechMenus.MENU_CARGO_SYSTEM, FinalTechItemStacks.DIVIDED_STORAGE_UNIT, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.DIVIDED_STORAGE_UNIT);
    public static final LimitedStorageUnit LIMITED_STORAGE_UNIT = new LimitedStorageUnit(FinalTechMenus.MENU_CARGO_SYSTEM, FinalTechItemStacks.LIMITED_STORAGE_UNIT, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.LIMITED_STORAGE_UNIT);
    public static final StackStorageUnit STACK_STORAGE_UNIT = new StackStorageUnit(FinalTechMenus.MENU_CARGO_SYSTEM, FinalTechItemStacks.STACK_STORAGE_UNIT, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.STACK_STORAGE_UNIT);
    public static final DividedLimitedStorageUnit DIVIDED_LIMITED_STORAGE_UNIT = new DividedLimitedStorageUnit(FinalTechMenus.MENU_CARGO_SYSTEM, FinalTechItemStacks.DIVIDED_LIMITED_STORAGE_UNIT, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.DIVIDED_LIMITED_STORAGE_UNIT);
    public static final DividedStackStorageUnit DIVIDED_STACK_STORAGE_UNIT = new DividedStackStorageUnit(FinalTechMenus.MENU_CARGO_SYSTEM, FinalTechItemStacks.DIVIDED_STACK_STORAGE_UNIT, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.DIVIDED_STACK_STORAGE_UNIT);
    public static final LimitedStackStorageUnit LIMITED_STACK_STORAGE_UNIT = new LimitedStackStorageUnit(FinalTechMenus.MENU_CARGO_SYSTEM, FinalTechItemStacks.LIMITED_STACK_STORAGE_UNIT, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.LIMITED_STACK_STORAGE_UNIT);

    public static final RandomInputStorageUnit RANDOM_INPUT_STORAGE_UNIT = new RandomInputStorageUnit(FinalTechMenus.MENU_CARGO_SYSTEM, FinalTechItemStacks.RANDOM_INPUT_STORAGE_UNIT, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.RANDOM_INPUT_STORAGE_UNIT);
    public static final RandomOutputStorageUnit RANDOM_OUTPUT_STORAGE_UNIT = new RandomOutputStorageUnit(FinalTechMenus.MENU_CARGO_SYSTEM, FinalTechItemStacks.RANDOM_OUTPUT_STORAGE_UNIT, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.RANDOM_OUTPUT_STORAGE_UNIT);
    public static final RandomAccessStorageUnit RANDOM_ACCESS_STORAGE_UNIT = new RandomAccessStorageUnit(FinalTechMenus.MENU_CARGO_SYSTEM, FinalTechItemStacks.RANDOM_ACCESS_STORAGE_UNIT, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.RANDOM_ACCESS_STORAGE_UNIT);
    public static final DistributeLeftStorageUnit DISTRIBUTE_LEFT_STORAGE_UNIT = new DistributeLeftStorageUnit(FinalTechMenus.MENU_CARGO_SYSTEM, FinalTechItemStacks.DISTRIBUTE_LEFT_STORAGE_UNIT, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.DISTRIBUTE_LEFT_STORAGE_UNIT);
    public static final DistributeRightStorageUnit DISTRIBUTE_RIGHT_STORAGE_UNIT = new DistributeRightStorageUnit(FinalTechMenus.MENU_CARGO_SYSTEM, FinalTechItemStacks.DISTRIBUTE_RIGHT_STORAGE_UNIT, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.DISTRIBUTE_RIGHT_STORAGE_UNIT);

    public static final StorageInteractPort STORAGE_INTERACT_PORT = new StorageInteractPort(FinalTechMenus.MENU_CARGO_SYSTEM, FinalTechItemStacks.STORAGE_INTERACT_PORT, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.STORAGE_INTERACT_PORT);
    public static final StorageInsertPort STORAGE_INSERT_PORT = new StorageInsertPort(FinalTechMenus.MENU_CARGO_SYSTEM, FinalTechItemStacks.STORAGE_INSERT_PORT, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.STORAGE_INSERT_PORT);
    public static final StorageWithdrawPort STORAGE_WITHDRAW_PORT = new StorageWithdrawPort(FinalTechMenus.MENU_CARGO_SYSTEM, FinalTechItemStacks.STORAGE_WITHDRAW_PORT, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.STORAGE_WITHDRAW_PORT);
    public static final StorageCard STORAGE_CARD = new StorageCard(FinalTechMenus.MENU_CARGO_SYSTEM, FinalTechItemStacks.STORAGE_CARD, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.STORAGE_CARD);

    public static final RemoteAccessor REMOTE_ACCESSOR = new RemoteAccessor(FinalTechMenus.MENU_CARGO_SYSTEM, FinalTechItemStacks.REMOTE_ACCESSOR, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.REMOTE_ACCESSOR);
    public static final ConsumableRemoteAccessor CONSUMABLE_REMOTE_ACCESSOR = new ConsumableRemoteAccessor(FinalTechMenus.MENU_CARGO_SYSTEM, FinalTechItemStacks.CONSUMABLE_REMOTE_ACCESSOR, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.CONSUMABLE_REMOTE_ACCESSOR);
    public static final ConfigurableRemoteAccessor CONFIGURABLE_REMOTE_ACCESSOR = new ConfigurableRemoteAccessor(FinalTechMenus.MENU_CARGO_SYSTEM, FinalTechItemStacks.CONFIGURABLE_REMOTE_ACCESSOR, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.CONFIGURABLE_REMOTE_ACCESSOR);
    public static final ExpandedConsumableRemoteAccessor EXPANDED_CONSUMABLE_REMOTE_ACCESSOR = new ExpandedConsumableRemoteAccessor(FinalTechMenus.MENU_CARGO_SYSTEM, FinalTechItemStacks.EXPANDED_CONSUMABLE_REMOTE_ACCESSOR, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.EXPANDED_CONSUMABLE_REMOTE_ACCESSOR);
    public static final ExpandedConfigurableRemoteAccessor EXPANDED_CONFIGURABLE_REMOTE_ACCESSOR = new ExpandedConfigurableRemoteAccessor(FinalTechMenus.MENU_CARGO_SYSTEM, FinalTechItemStacks.EXPANDED_CONFIGURABLE_REMOTE_ACCESSOR, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.EXPANDED_CONFIGURABLE_REMOTE_ACCESSOR);
    public static final AreaAccessor AREA_ACCESSOR = new AreaAccessor(FinalTechMenus.MENU_CARGO_SYSTEM, FinalTechItemStacks.AREA_ACCESSOR, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.AREA_ACCESSOR);

    public static final Transporter TRANSPORTER = new Transporter(FinalTechMenus.MENU_CARGO_SYSTEM, FinalTechItemStacks.TRANSPORTER, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.TRANSPORTER);
    public static final ConsumableTransporter CONSUMABLE_TRANSPORTER = new ConsumableTransporter(FinalTechMenus.MENU_CARGO_SYSTEM, FinalTechItemStacks.CONSUMABLE_TRANSPORTER, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.CONSUMABLE_TRANSPORTER);
    public static final ConfigurableTransporter CONFIGURABLE_TRANSPORTER = new ConfigurableTransporter(FinalTechMenus.MENU_CARGO_SYSTEM, FinalTechItemStacks.CONFIGURABLE_TRANSPORTER, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.CONFIGURABLE_TRANSPORTER);
    public static final ExpandedConsumableTransporter EXPANDED_CONSUMABLE_TRANSPORTER = new ExpandedConsumableTransporter(FinalTechMenus.MENU_CARGO_SYSTEM, FinalTechItemStacks.EXPANDED_CONSUMABLE_TRANSPORTER, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.EXPANDED_CONSUMABLE_TRANSPORTER);
    public static final ExpandedConfigurableTransporter EXPANDED_CONFIGURABLE_TRANSPORTER = new ExpandedConfigurableTransporter(FinalTechMenus.MENU_CARGO_SYSTEM, FinalTechItemStacks.EXPANDED_CONFIGURABLE_TRANSPORTER, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.EXPANDED_CONFIGURABLE_TRANSPORTER);

    public static final LogicNotNullComparator LOGIC_COMPARATOR_NOTNULL = new LogicNotNullComparator(FinalTechMenus.MENU_CARGO_SYSTEM, FinalTechItemStacks.LOGIC_COMPARATOR_NOTNULL, FinalTechRecipes.RECIPE_TYPE_MATRIX_CRAFTING_TABLE, FinalTechRecipes.LOGIC_COMPARATOR_NOTNULL);
    public static final LogicAmountComparator LOGIC_COMPARATOR_AMOUNT = new LogicAmountComparator(FinalTechMenus.MENU_CARGO_SYSTEM, FinalTechItemStacks.LOGIC_COMPARATOR_AMOUNT, FinalTechRecipes.RECIPE_TYPE_MATRIX_CRAFTING_TABLE, FinalTechRecipes.LOGIC_COMPARATOR_AMOUNT);
    public static final LogicSimilarComparator LOGIC_COMPARATOR_SIMILAR = new LogicSimilarComparator(FinalTechMenus.MENU_CARGO_SYSTEM, FinalTechItemStacks.LOGIC_COMPARATOR_SIMILAR, FinalTechRecipes.RECIPE_TYPE_MATRIX_CRAFTING_TABLE, FinalTechRecipes.LOGIC_COMPARATOR_SIMILAR);
    public static final LogicEqualComparator LOGIC_COMPARATOR_EQUAL = new LogicEqualComparator(FinalTechMenus.MENU_CARGO_SYSTEM, FinalTechItemStacks.LOGIC_COMPARATOR_EQUAL, FinalTechRecipes.RECIPE_TYPE_MATRIX_CRAFTING_TABLE, FinalTechRecipes.LOGIC_COMPARATOR_EQUAL);
    public static final LogicCrafter LOGIC_CRAFTER = new LogicCrafter(FinalTechMenus.MENU_CARGO_SYSTEM, FinalTechItemStacks.LOGIC_CRAFTER, FinalTechRecipes.RECIPE_TYPE_MATRIX_CRAFTING_TABLE, FinalTechRecipes.LOGIC_CRAFTER);
    public static final DigitAdder DIGIT_ADDER = new DigitAdder(FinalTechMenus.MENU_CARGO_SYSTEM, FinalTechItemStacks.DIGIT_ADDER, FinalTechRecipes.RECIPE_TYPE_MATRIX_CRAFTING_TABLE, FinalTechRecipes.DIGIT_ADDER);

    public static final BasicFrameMachine BASIC_FRAME_MACHINE = new BasicFrameMachine(FinalTechMenus.MENU_CARGO_SYSTEM, FinalTechItemStacks.BASIC_FRAME_MACHINE, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.BASIC_FRAME_MACHINE);
    public static final PointTransfer POINT_TRANSFER = new PointTransfer(FinalTechMenus.MENU_CARGO_SYSTEM, FinalTechItemStacks.POINT_TRANSFER, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.POINT_TRANSFER);
    public static final MeshTransfer MESH_TRANSFER = new MeshTransfer(FinalTechMenus.MENU_CARGO_SYSTEM, FinalTechItemStacks.MESH_TRANSFER, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.MESH_TRANSFER);
    public static final LineTransfer LINE_TRANSFER = new LineTransfer(FinalTechMenus.MENU_CARGO_SYSTEM, FinalTechItemStacks.LINE_TRANSFER, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.LINE_TRANSFER);
    public static final LocationTransfer LOCATION_TRANSFER = new LocationTransfer(FinalTechMenus.MENU_CARGO_SYSTEM, FinalTechItemStacks.LOCATION_TRANSFER, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.LOCATION_TRANSFER);

    public static final AdvancedPointTransfer ADVANCED_POINT_TRANSFER = new AdvancedPointTransfer(FinalTechMenus.MENU_CARGO_SYSTEM, FinalTechItemStacks.ADVANCED_POINT_TRANSFER, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.ADVANCED_POINT_TRANSFER);
    public static final AdvancedMeshTransfer ADVANCED_MESH_TRANSFER = new AdvancedMeshTransfer(FinalTechMenus.MENU_CARGO_SYSTEM, FinalTechItemStacks.ADVANCED_MESH_TRANSFER, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.ADVANCED_MESH_TRANSFER);
    public static final AdvancedLineTransfer ADVANCED_LINE_TRANSFER = new AdvancedLineTransfer(FinalTechMenus.MENU_CARGO_SYSTEM, FinalTechItemStacks.ADVANCED_LINE_TRANSFER, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.ADVANCED_LINE_TRANSFER);
    public static final AdvancedLocationTransfer ADVANCED_LOCATION_TRANSFER = new AdvancedLocationTransfer(FinalTechMenus.MENU_CARGO_SYSTEM, FinalTechItemStacks.ADVANCED_LOCATION_TRANSFER, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.ADVANCED_LOCATION_TRANSFER);

    public static final ConfigurationCopier CONFIGURATION_COPIER = new ConfigurationCopier(FinalTechMenus.MENU_CARGO_SYSTEM, FinalTechItemStacks.CONFIGURATION_COPIER, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.CONFIGURATION_COPIER);
    public static final ConfigurationPaster CONFIGURATION_PASTER = new ConfigurationPaster(FinalTechMenus.MENU_CARGO_SYSTEM, FinalTechItemStacks.CONFIGURATION_PASTER, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.CONFIGURATION_PASTER);
    public static final ClickWorkMachine CLICK_WORK_MACHINE = new ClickWorkMachine(FinalTechMenus.MENU_CARGO_SYSTEM, FinalTechItemStacks.CLICK_WORK_MACHINE, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.CLICK_WORK_MACHINE);
    public static final SimulateClickMachine SIMULATE_CLICK_MACHINE = new SimulateClickMachine(FinalTechMenus.MENU_CARGO_SYSTEM, FinalTechItemStacks.SIMULATE_CLICK_MACHINE, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.SIMULATE_CLICK_MACHINE);
    public static final ConsumableSimulateClickMachine CONSUMABLE_SIMULATE_CLICK_MACHINE = new ConsumableSimulateClickMachine(FinalTechMenus.MENU_CARGO_SYSTEM, FinalTechItemStacks.CONSUMABLE_SIMULATE_CLICK_MACHINE, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.CONSUMABLE_SIMULATE_CLICK_MACHINE);

    public static final DustFactoryDirt DUST_FACTORY_DIRT = new DustFactoryDirt(FinalTechMenus.MENU_FUNCTIONAL_MACHINE, FinalTechItemStacks.ORDERED_DUST_FACTORY_DIRT, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.ORDERED_DUST_FACTORY_DIRT);
    public static final DustFactoryStone DUST_FACTORY_STONE = new DustFactoryStone(FinalTechMenus.MENU_FUNCTIONAL_MACHINE, FinalTechItemStacks.ORDERED_DUST_FACTORY_STONE, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.ORDERED_DUST_FACTORY_STONE);
    public static final MatrixCraftingTable MATRIX_CRAFTING_TABLE = new MatrixCraftingTable(FinalTechMenus.MENU_FUNCTIONAL_MACHINE, FinalTechItemStacks.MATRIX_CRAFTING_TABLE, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.MATRIX_CRAFTING_TABLE);

    public static final ItemDismantleTable ITEM_DISMANTLE_TABLE = new ItemDismantleTable(FinalTechMenus.MENU_FUNCTIONAL_MACHINE, FinalTechItemStacks.ITEM_DISMANTLE_TABLE, FinalTechRecipes.RECIPE_TYPE_MATRIX_CRAFTING_TABLE, FinalTechRecipes.ITEM_DISMANTLE_TABLE);
    public static final AutoItemDismantleTable AUTO_ITEM_DISMANTLE_TABLE = new AutoItemDismantleTable(FinalTechMenus.MENU_FUNCTIONAL_MACHINE, FinalTechItemStacks.AUTO_ITEM_DISMANTLE_TABLE, FinalTechRecipes.RECIPE_TYPE_MATRIX_CRAFTING_TABLE, FinalTechRecipes.AUTO_ITEM_DISMANTLE_TABLE);
    public static final EquivalentExchangeTable EQUIVALENT_EXCHANGE_TABLE = new EquivalentExchangeTable(FinalTechMenus.MENU_FUNCTIONAL_MACHINE, FinalTechItemStacks.EQUIVALENT_EXCHANGE_TABLE, FinalTechRecipes.RECIPE_TYPE_MATRIX_CRAFTING_TABLE, FinalTechRecipes.EQUIVALENT_EXCHANGE_TABLE);
    public static final ItemSerializationConstructor ITEM_SERIALIZATION_CONSTRUCTOR = new ItemSerializationConstructor(FinalTechMenus.MENU_FUNCTIONAL_MACHINE, FinalTechItemStacks.ITEM_SERIALIZATION_CONSTRUCTOR, FinalTechRecipes.RECIPE_TYPE_MATRIX_CRAFTING_TABLE, FinalTechRecipes.ITEM_SERIALIZATION_CONSTRUCTOR);
    public static final ItemDeserializeParser ITEM_DESERIALIZE_PARSER = new ItemDeserializeParser(FinalTechMenus.MENU_FUNCTIONAL_MACHINE, FinalTechItemStacks.ITEM_DESERIALIZE_PARSER, FinalTechRecipes.RECIPE_TYPE_MATRIX_CRAFTING_TABLE, FinalTechRecipes.ITEM_DESERIALIZE_PARSER);
    public static final CardOperationTable CARD_OPERATION_TABLE = new CardOperationTable(FinalTechMenus.MENU_FUNCTIONAL_MACHINE, FinalTechItemStacks.CARD_OPERATION_TABLE, FinalTechRecipes.RECIPE_TYPE_MATRIX_CRAFTING_TABLE, FinalTechRecipes.CARD_OPERATION_TABLE);
    public static final AdvancedAutoCraftFrame ADVANCED_AUTO_CRAFT_FRAME = new AdvancedAutoCraftFrame(FinalTechMenus.MENU_FUNCTIONAL_MACHINE, FinalTechItemStacks.ADVANCED_AUTO_CRAFT_FRAME, FinalTechRecipes.RECIPE_TYPE_MATRIX_CRAFTING_TABLE, FinalTechRecipes.ADVANCED_AUTO_CRAFT_FRAME);

    public static final ItemFixer ITEM_FIXER = new ItemFixer(FinalTechMenus.MENU_FUNCTIONAL_MACHINE, FinalTechItemStacks.ITEM_FIXER, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.ITEM_FIXER);
    public static final CobbleStoneFactory COBBLESTONE_FACTORY = new CobbleStoneFactory(FinalTechMenus.MENU_FUNCTIONAL_MACHINE, FinalTechItemStacks.COBBLESTONE_FACTORY, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.COBBLESTONE_FACTORY);
    public static final FuelCharger FUEL_CHARGER = new FuelCharger(FinalTechMenus.MENU_FUNCTIONAL_MACHINE, FinalTechItemStacks.FUEL_CHARGER, FinalTechRecipes.RECIPE_TYPE_MATRIX_CRAFTING_TABLE, FinalTechRecipes.FUEL_CHARGER);
    public static final FuelAccelerator FUEL_ACCELERATOR = new FuelAccelerator(FinalTechMenus.MENU_FUNCTIONAL_MACHINE, FinalTechItemStacks.FUEL_ACCELERATOR, FinalTechRecipes.RECIPE_TYPE_MATRIX_CRAFTING_TABLE, FinalTechRecipes.FUEL_ACCELERATOR);
    public static final FuelOperator FUEL_OPERATOR = new FuelOperator(FinalTechMenus.MENU_FUNCTIONAL_MACHINE, FinalTechItemStacks.FUEL_OPERATOR, FinalTechRecipes.RECIPE_TYPE_MATRIX_CRAFTING_TABLE, FinalTechRecipes.FUEL_OPERATOR);
    public static final OperationAccelerator OPERATION_ACCELERATOR = new OperationAccelerator(FinalTechMenus.MENU_FUNCTIONAL_MACHINE, FinalTechItemStacks.OPERATION_ACCELERATOR, FinalTechRecipes.RECIPE_TYPE_MATRIX_CRAFTING_TABLE, FinalTechRecipes.OPERATION_ACCELERATOR);
    public static final OperationAcceleratorInfinity OPERATION_ACCELERATOR_INFINITY = new OperationAcceleratorInfinity(FinalTechMenus.MENU_FUNCTIONAL_MACHINE, FinalTechItemStacks.OPERATION_ACCELERATOR_INFINITY, FinalTechRecipes.RECIPE_TYPE_MATRIX_CRAFTING_TABLE, FinalTechRecipes.OPERATION_ACCELERATOR_INFINITY);

    public static final CureTower CURE_TOWER = new CureTower(FinalTechMenus.MENU_FUNCTIONAL_MACHINE, FinalTechItemStacks.CURE_TOWER, FinalTechRecipes.RECIPE_TYPE_MATRIX_CRAFTING_TABLE, FinalTechRecipes.CURE_TOWER);
    public static final PurifyLevelTower PURIFY_LEVEL_TOWER = new PurifyLevelTower(FinalTechMenus.MENU_FUNCTIONAL_MACHINE, FinalTechItemStacks.PURIFY_LEVEL_TOWER, FinalTechRecipes.RECIPE_TYPE_MATRIX_CRAFTING_TABLE, FinalTechRecipes.PURIFY_LEVEL_TOWER);
    public static final PurifyTimeTower PURIFY_TIME_TOWER = new PurifyTimeTower(FinalTechMenus.MENU_FUNCTIONAL_MACHINE, FinalTechItemStacks.PURIFY_TIME_TOWER, FinalTechRecipes.RECIPE_TYPE_MATRIX_CRAFTING_TABLE, FinalTechRecipes.PURIFY_TIME_TOWER);

    public static final ManualCraftingTable MANUAL_CRAFTING_TABLE = new ManualCraftingTable(FinalTechMenus.MENU_PRODUCTIVE_MACHINE, FinalTechItemStacks.MANUAL_CRAFTING_TABLE, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.MANUAL_CRAFTING_TABLE);
    public static final ManualEnhancedCraftingTable MANUAL_ENHANCED_CRAFTING_TABLE = new ManualEnhancedCraftingTable(FinalTechMenus.MENU_PRODUCTIVE_MACHINE, FinalTechItemStacks.MANUAL_ENHANCED_CRAFTING_TABLE, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.MANUAL_ENHANCED_CRAFTING_TABLE);
    public static final ManualGrindStone MANUAL_GRIND_STONE = new ManualGrindStone(FinalTechMenus.MENU_PRODUCTIVE_MACHINE, FinalTechItemStacks.MANUAL_GRIND_STONE, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.MANUAL_GRIND_STONE);
    public static final ManualArmorForge MANUAL_ARMOR_FORGE = new ManualArmorForge(FinalTechMenus.MENU_PRODUCTIVE_MACHINE, FinalTechItemStacks.MANUAL_ARMOR_FORGE, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.MANUAL_ARMOR_FORGE);
    public static final ManualOreCrusher MANUAL_ORE_CRUSHER = new ManualOreCrusher(FinalTechMenus.MENU_PRODUCTIVE_MACHINE, FinalTechItemStacks.MANUAL_ORE_CRUSHER, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.MANUAL_ORE_CRUSHER);
    public static final ManualCompressor MANUAL_COMPRESSOR = new ManualCompressor(FinalTechMenus.MENU_PRODUCTIVE_MACHINE, FinalTechItemStacks.MANUAL_COMPRESSOR, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.MANUAL_COMPRESSOR);
    public static final ManualSmeltery MANUAL_SMELTERY = new ManualSmeltery(FinalTechMenus.MENU_PRODUCTIVE_MACHINE, FinalTechItemStacks.MANUAL_SMELTERY, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.MANUAL_SMELTERY);
    public static final ManualPressureChamber MANUAL_PRESSURE_CHAMBER = new ManualPressureChamber(FinalTechMenus.MENU_PRODUCTIVE_MACHINE, FinalTechItemStacks.MANUAL_PRESSURE_CHAMBER, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.MANUAL_PRESSURE_CHAMBER);
    public static final ManualMagicWorkbench MANUAL_MAGIC_WORKBENCH = new ManualMagicWorkbench(FinalTechMenus.MENU_PRODUCTIVE_MACHINE, FinalTechItemStacks.MANUAL_MAGIC_WORKBENCH, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.MANUAL_MAGIC_WORKBENCH);
    public static final ManualOreWasher MANUAL_ORE_WASHER = new ManualOreWasher(FinalTechMenus.MENU_PRODUCTIVE_MACHINE, FinalTechItemStacks.MANUAL_ORE_WASHER, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.MANUAL_ORE_WASHER);
    public static final ManualComposter MANUAL_COMPOSTER = new ManualComposter(FinalTechMenus.MENU_PRODUCTIVE_MACHINE, FinalTechItemStacks.MANUAL_COMPOSTER, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.MANUAL_COMPOSTER);
    public static final ManualGoldPan MANUAL_GOLD_PAN = new ManualGoldPan(FinalTechMenus.MENU_PRODUCTIVE_MACHINE, FinalTechItemStacks.MANUAL_GOLD_PAN, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.MANUAL_GOLD_PAN);
    public static final ManualCrucible MANUAL_CRUCIBLE = new ManualCrucible(FinalTechMenus.MENU_PRODUCTIVE_MACHINE, FinalTechItemStacks.MANUAL_CRUCIBLE, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.MANUAL_CRUCIBLE);
    public static final ManualJuicer MANUAL_JUICER = new ManualJuicer(FinalTechMenus.MENU_PRODUCTIVE_MACHINE, FinalTechItemStacks.MANUAL_JUICER, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.MANUAL_JUICER);
    public static final ManualAncientAltar MANUAL_ANCIENT_ALTAR = new ManualAncientAltar(FinalTechMenus.MENU_PRODUCTIVE_MACHINE, FinalTechItemStacks.MANUAL_ANCIENT_ALTAR, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.MANUAL_ANCIENT_ALTAR);
    public static final ManualHeatedPressureChamber MANUAL_HEATED_PRESSURE_CHAMBER = new ManualHeatedPressureChamber(FinalTechMenus.MENU_PRODUCTIVE_MACHINE, FinalTechItemStacks.MANUAL_HEATED_PRESSURE_CHAMBER, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.MANUAL_HEATED_PRESSURE_CHAMBER);

    public static final BasicLogicFactory BASIC_LOGIC_FACTORY = new BasicLogicFactory(FinalTechMenus.MENU_PRODUCTIVE_MACHINE, FinalTechItemStacks.BASIC_LOGIC_FACTORY, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.BASIC_LOGIC_FACTORY);

    public static final AdvancedComposter ADVANCED_COMPOSTER = new AdvancedComposter(FinalTechMenus.MENU_PRODUCTIVE_MACHINE, FinalTechItemStacks.ADVANCED_COMPOSTER, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.ADVANCED_COMPOSTER);
    public static final AdvancedJuicer ADVANCED_JUICER = new AdvancedJuicer(FinalTechMenus.MENU_PRODUCTIVE_MACHINE, FinalTechItemStacks.ADVANCED_JUICER, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.ADVANCED_JUICER);
    public static final AdvancedFurnace ADVANCED_FURNACE = new AdvancedFurnace(FinalTechMenus.MENU_PRODUCTIVE_MACHINE, FinalTechItemStacks.ADVANCED_FURNACE, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.ADVANCED_FURNACE);
    public static final AdvancedGoldPan ADVANCED_GOLD_PAN = new AdvancedGoldPan(FinalTechMenus.MENU_PRODUCTIVE_MACHINE, FinalTechItemStacks.ADVANCED_GOLD_PAN, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.ADVANCED_GOLD_PAN);
    public static final AdvancedDustWasher ADVANCED_DUST_WASHER = new AdvancedDustWasher(FinalTechMenus.MENU_PRODUCTIVE_MACHINE, FinalTechItemStacks.ADVANCED_DUST_WASHER, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.ADVANCED_DUST_WASHER);
    public static final AdvancedIngotFactory ADVANCED_INGOT_FACTORY = new AdvancedIngotFactory(FinalTechMenus.MENU_PRODUCTIVE_MACHINE, FinalTechItemStacks.ADVANCED_INGOT_FACTORY, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.ADVANCED_INGOT_FACTORY);
    public static final AdvancedCrucible ADVANCED_CRUCIBLE = new AdvancedCrucible(FinalTechMenus.MENU_PRODUCTIVE_MACHINE, FinalTechItemStacks.ADVANCED_CRUCIBLE, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.ADVANCED_CRUCIBLE);
    public static final AdvancedOreGrinder ADVANCED_ORE_GRINDER = new AdvancedOreGrinder(FinalTechMenus.MENU_PRODUCTIVE_MACHINE, FinalTechItemStacks.ADVANCED_ORE_GRINDER, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.ADVANCED_ORE_GRINDER);
    public static final AdvancedHeatedPressureChamber ADVANCED_HEATED_PRESSURE_CHAMBER = new AdvancedHeatedPressureChamber(FinalTechMenus.MENU_PRODUCTIVE_MACHINE, FinalTechItemStacks.ADVANCED_HEATED_PRESSURE_CHAMBER, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.ADVANCED_HEATED_PRESSURE_CHAMBER);
    public static final AdvancedIngotPulverizer ADVANCED_INGOT_PULVERIZER = new AdvancedIngotPulverizer(FinalTechMenus.MENU_PRODUCTIVE_MACHINE, FinalTechItemStacks.ADVANCED_INGOT_PULVERIZER, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.ADVANCED_INGOT_PULVERIZER);
    public static final AdvancedAutoDrier ADVANCED_AUTO_DRIER = new AdvancedAutoDrier(FinalTechMenus.MENU_PRODUCTIVE_MACHINE, FinalTechItemStacks.ADVANCED_AUTO_DRIER, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.ADVANCED_AUTO_DRIER);
    public static final AdvancedPress ADVANCED_PRESS = new AdvancedPress(FinalTechMenus.MENU_PRODUCTIVE_MACHINE, FinalTechItemStacks.ADVANCED_PRESS, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.ADVANCED_PRESS);
    public static final AdvancedFoodFactory ADVANCED_FOOD_FACTORY = new AdvancedFoodFactory(FinalTechMenus.MENU_PRODUCTIVE_MACHINE, FinalTechItemStacks.ADVANCED_FOOD_FACTORY, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.ADVANCED_FOOD_FACTORY);
    public static final AdvancedFreezer ADVANCED_FREEZER = new AdvancedFreezer(FinalTechMenus.MENU_PRODUCTIVE_MACHINE, FinalTechItemStacks.ADVANCED_FREEZER, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.ADVANCED_FREEZER);
    public static final AdvancedCarbonPress ADVANCED_CARBON_PRESS = new AdvancedCarbonPress(FinalTechMenus.MENU_PRODUCTIVE_MACHINE, FinalTechItemStacks.ADVANCED_CARBON_PRESS, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.ADVANCED_CARBON_PRESS);
    public static final AdvancedSmeltery ADVANCED_SMELTERY = new AdvancedSmeltery(FinalTechMenus.MENU_PRODUCTIVE_MACHINE, FinalTechItemStacks.ADVANCED_SMELTERY, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.ADVANCED_SMELTERY);

    public static final GravelConversion GRAVEL_CONVERSION = new GravelConversion(FinalTechMenus.MENU_PRODUCTIVE_MACHINE, FinalTechItemStacks.GRAVEL_CONVERSION, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.GRAVEL_CONVERSION);
    public static final SoulSandConversion SOUL_SAND_CONVERSION = new SoulSandConversion(FinalTechMenus.MENU_PRODUCTIVE_MACHINE, FinalTechItemStacks.SOUL_SAND_CONVERSION, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.SOUL_SAND_CONVERSION);
    public static final LogicToDigitalConversion LOGIC_TO_DIGITAL_CONVERSION = new LogicToDigitalConversion(FinalTechMenus.MENU_PRODUCTIVE_MACHINE, FinalTechItemStacks.LOGIC_TO_DIGITAL_CONVERSION, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.LOGIC_TO_DIGITAL_CONVERSION);

    public static final DigitalExtraction DIGITAL_EXTRACTION = new DigitalExtraction(FinalTechMenus.MENU_PRODUCTIVE_MACHINE, FinalTechItemStacks.DIGITAL_EXTRACTION, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.DIGITAL_EXTRACTION);

    public static final LiquidCardGenerator LIQUID_CARD_GENERATOR = new LiquidCardGenerator(FinalTechMenus.MENU_PRODUCTIVE_MACHINE, FinalTechItemStacks.LIQUID_CARD_GENERATOR, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.LIQUID_CARD_GENERATOR);
    public static final LogicGenerator LOGIC_GENERATOR = new LogicGenerator(FinalTechMenus.MENU_PRODUCTIVE_MACHINE, FinalTechItemStacks.LOGIC_GENERATOR, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.LOGIC_GENERATOR);
    public static final DigitalGenerator DIGITAL_GENERATOR = new DigitalGenerator(FinalTechMenus.MENU_PRODUCTIVE_MACHINE, FinalTechItemStacks.DIGITAL_GENERATOR, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.DIGITAL_GENERATOR);

    public static final EntropySeed ENTROPY_SEED = new EntropySeed(FinalTechMenus.MENU_DISC, FinalTechItemStacks.ENTROPY_SEED, FinalTechRecipes.RECIPE_TYPE_MATRIX_CRAFTING_TABLE, FinalTechRecipes.ENTROPY_SEED);
    public static final InfinityMachineChargeCard MACHINE_CHARGE_CARD_INFINITY = new InfinityMachineChargeCard(FinalTechMenus.MENU_DISC, FinalTechItemStacks.MACHINE_CHARGE_CARD_INFINITY, FinalTechRecipes.RECIPE_TYPE_MATRIX_CRAFTING_TABLE, FinalTechRecipes.MACHINE_CHARGE_CARD_INFINITY);
    public static final InfinityMachineAccelerateCard MACHINE_ACCELERATE_CARD_INFINITY = new InfinityMachineAccelerateCard(FinalTechMenus.MENU_DISC, FinalTechItemStacks.MACHINE_ACCELERATE_CARD_INFINITY, FinalTechRecipes.RECIPE_TYPE_MATRIX_CRAFTING_TABLE, FinalTechRecipes.MACHINE_ACCELERATE_CARD_INFINITY);
    public static final MatrixMachineActivateCard MACHINE_ACTIVATE_CARD_L4 = new MatrixMachineActivateCard(FinalTechMenus.MENU_DISC, FinalTechItemStacks.MACHINE_ACTIVATE_CARD_L4, FinalTechRecipes.RECIPE_TYPE_MATRIX_CRAFTING_TABLE, FinalTechRecipes.MACHINE_ACTIVATE_CARD_L4);

    public static final AdvancedAutoCraft ADVANCED_AUTO_CRAFT = new AdvancedAutoCraft(FinalTechMenus.MENU_DISC, FinalTechItemStacks.ADVANCED_AUTO_CRAFT, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.ADVANCED_AUTO_CRAFT);
    public static final MultiFrameMachine MULTI_FRAME_MACHINE = new MultiFrameMachine(FinalTechMenus.MENU_DISC, FinalTechItemStacks.MULTI_FRAME_MACHINE, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.MULTI_FRAME_MACHINE);
    public static final MatrixItemDismantleTable MATRIX_ITEM_DISMANTLE_TABLE = new MatrixItemDismantleTable(FinalTechMenus.MENU_DISC, FinalTechItemStacks.MATRIX_ITEM_DISMANTLE_TABLE, FinalTechRecipes.RECIPE_TYPE_MATRIX_CRAFTING_TABLE, FinalTechRecipes.MATRIX_ITEM_DISMANTLE_TABLE);
    public static final MatrixExpandedCapacitor MATRIX_EXPANDED_CAPACITOR = new MatrixExpandedCapacitor(FinalTechMenus.MENU_DISC, FinalTechItemStacks.MATRIX_EXPANDED_CAPACITOR, FinalTechRecipes.RECIPE_TYPE_MATRIX_CRAFTING_TABLE, FinalTechRecipes.MATRIX_EXPANDED_CAPACITOR);
    public static final MatrixItemDeserializeParser MATRIX_ITEM_DESERIALIZE_PARSER = new MatrixItemDeserializeParser(FinalTechMenus.MENU_DISC, FinalTechItemStacks.MATRIX_ITEM_DESERIALIZE_PARSER, FinalTechRecipes.RECIPE_TYPE_MATRIX_CRAFTING_TABLE, FinalTechRecipes.MATRIX_ITEM_DESERIALIZE_PARSER);
    public static final EntropyConstructor ENTROPY_CONSTRUCTOR = new EntropyConstructor(FinalTechMenus.MENU_DISC, FinalTechItemStacks.ENTROPY_CONSTRUCTOR, FinalTechRecipes.RECIPE_TYPE_MATRIX_CRAFTING_TABLE, FinalTechRecipes.ENTROPY_CONSTRUCTOR);
    public static final MatrixGenerator MATRIX_GENERATOR = new MatrixGenerator(FinalTechMenus.MENU_DISC, FinalTechItemStacks.MATRIX_GENERATOR, FinalTechRecipes.RECIPE_TYPE_MATRIX_CRAFTING_TABLE, FinalTechRecipes.MATRIX_GENERATOR);
    public static final MatrixAccelerator MATRIX_ACCELERATOR = new MatrixAccelerator(FinalTechMenus.MENU_DISC, FinalTechItemStacks.MATRIX_ACCELERATOR, FinalTechRecipes.RECIPE_TYPE_MATRIX_CRAFTING_TABLE, FinalTechRecipes.MATRIX_ACCELERATOR);
    public static final MatrixItemSerializationConstructor MATRIX_ITEM_SERIALIZATION_CONSTRUCTOR = new MatrixItemSerializationConstructor(FinalTechMenus.MENU_DISC, FinalTechItemStacks.MATRIX_ITEM_SERIALIZATION_CONSTRUCTOR, FinalTechRecipes.RECIPE_TYPE_MATRIX_CRAFTING_TABLE, FinalTechRecipes.MATRIX_ITEM_SERIALIZATION_CONSTRUCTOR);
    public static final MatrixReactor MATRIX_REACTOR = new MatrixReactor(FinalTechMenus.MENU_DISC, FinalTechItemStacks.MATRIX_REACTOR, FinalTechRecipes.RECIPE_TYPE_MATRIX_CRAFTING_TABLE, FinalTechRecipes.MATRIX_REACTOR);

    public static final Meawerful TROPHY_MEAWERFUL = new Meawerful(FinalTechMenus.MENU_DISC, FinalTechItemStacks.TROPHY_MEAWERFUL, RecipeType.NULL, new ItemStack[0]);
    public static final Shixinzia TROPHY_SHIXINZIA = new Shixinzia(FinalTechMenus.MENU_DISC, FinalTechItemStacks.TROPHY_SHIXINZIA, RecipeType.NULL, new ItemStack[0]);
}
