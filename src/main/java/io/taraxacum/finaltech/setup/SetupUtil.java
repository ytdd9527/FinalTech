package io.taraxacum.finaltech.setup;

import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.implementation.Slimefun;
import io.github.thebusybiscuit.slimefun4.implementation.SlimefunItems;
import io.taraxacum.common.util.ReflectionUtil;
import io.taraxacum.common.util.StringUtil;
import io.taraxacum.finaltech.FinalTech;
import io.taraxacum.finaltech.core.command.ShowItemInfo;
import io.taraxacum.finaltech.core.command.TransferToCopyCardItem;
import io.taraxacum.finaltech.core.command.TransferToStorageItem;
import io.taraxacum.finaltech.core.enchantment.NullEnchantment;
import io.taraxacum.finaltech.core.item.machine.function.*;
import io.taraxacum.finaltech.core.item.machine.logic.LogicAmountComparator;
import io.taraxacum.finaltech.core.item.machine.logic.LogicEqualComparator;
import io.taraxacum.finaltech.core.item.machine.logic.LogicNotNullComparator;
import io.taraxacum.finaltech.core.item.machine.logic.LogicSimilarComparator;
import io.taraxacum.finaltech.core.item.machine.range.point.face.*;
import io.taraxacum.finaltech.core.item.machine.template.extraction.DigitalExtraction;
import io.taraxacum.finaltech.core.item.tool.*;
import io.taraxacum.finaltech.core.item.machine.*;
import io.taraxacum.finaltech.core.item.machine.unit.*;
import io.taraxacum.finaltech.core.item.machine.electric.VariableWireCapacitor;
import io.taraxacum.finaltech.core.item.machine.electric.VariableWireResistance;
import io.taraxacum.finaltech.core.item.machine.manual.EquivalentExchangeTable;
import io.taraxacum.finaltech.core.item.machine.manual.MatrixCraftingTable;
import io.taraxacum.finaltech.core.item.machine.operation.*;
import io.taraxacum.finaltech.core.item.machine.range.point.EquivalentConcept;
import io.taraxacum.finaltech.core.item.machine.template.basic.*;
import io.taraxacum.finaltech.core.item.machine.template.conversion.*;
import io.taraxacum.finaltech.core.item.machine.template.extraction.OreExtraction;
import io.taraxacum.finaltech.core.item.machine.template.generator.*;
import io.taraxacum.finaltech.core.item.machine.tower.CureTower;
import io.taraxacum.finaltech.core.item.machine.tower.PurifyLevelTower;
import io.taraxacum.finaltech.core.item.machine.tower.PurifyTimeTower;
import io.taraxacum.finaltech.core.item.unusable.*;
import io.taraxacum.finaltech.core.item.unusable.digital.*;
import io.taraxacum.finaltech.core.item.unusable.liquid.LavaCard;
import io.taraxacum.finaltech.core.item.unusable.liquid.MilkCard;
import io.taraxacum.finaltech.core.item.unusable.liquid.WaterCard;
import io.taraxacum.finaltech.core.item.unusable.logic.LogicFalse;
import io.taraxacum.finaltech.core.item.unusable.logic.LogicTrue;
import io.taraxacum.finaltech.core.item.usable.*;
import io.taraxacum.finaltech.core.item.usable.machine.*;
import io.taraxacum.finaltech.core.item.machine.electric.capacitor.AdvancedChargeIncreaseCapacitor;
import io.taraxacum.finaltech.core.item.machine.electric.capacitor.AdvancedConsumeReduceCapacitor;
import io.taraxacum.finaltech.core.item.machine.range.cube.EnergizedAccelerator;
import io.taraxacum.finaltech.core.item.machine.range.cube.MatrixAccelerator;
import io.taraxacum.finaltech.core.item.machine.range.cube.OverloadedAccelerator;
import io.taraxacum.finaltech.core.item.machine.range.cube.generator.*;
import io.taraxacum.finaltech.core.item.machine.electric.capacitor.BasicChargeIncreaseCapacitor;
import io.taraxacum.finaltech.core.item.machine.electric.capacitor.BasicConsumeReduceCapacitor;
import io.taraxacum.finaltech.core.item.machine.electric.capacitor.expanded.*;
import io.taraxacum.finaltech.core.item.machine.cargo.*;
import io.taraxacum.finaltech.core.item.machine.cargo.storage.StorageInsertPort;
import io.taraxacum.finaltech.core.item.machine.cargo.storage.StorageInteractPort;
import io.taraxacum.finaltech.core.item.machine.cargo.storage.StorageWithdrawPort;
import io.taraxacum.finaltech.core.item.machine.manual.CardOperationTable;
import io.taraxacum.finaltech.core.item.machine.manual.craft.*;
import io.taraxacum.finaltech.core.item.machine.range.line.shooter.EnergizedElectricityShootPile;
import io.taraxacum.finaltech.core.item.machine.range.line.shooter.NormalElectricityShootPile;
import io.taraxacum.finaltech.core.item.machine.range.line.shooter.OverloadedElectricityShootPile;
import io.taraxacum.finaltech.core.item.machine.template.advanced.*;
import io.taraxacum.finaltech.util.ConstantTableUtil;
import io.taraxacum.libs.plugin.util.ItemStackUtil;
import io.taraxacum.finaltech.util.BlockTickerUtil;
import io.taraxacum.libs.slimefun.util.ResearchUtil;
import io.taraxacum.libs.plugin.util.TextUtil;
import io.taraxacum.libs.plugin.dto.ConfigFileManager;
import io.taraxacum.libs.plugin.dto.LanguageManager;
import me.mrCookieSlime.Slimefun.Objects.handlers.BlockTicker;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;
import java.lang.reflect.Field;
import java.util.List;
import java.util.function.Function;

/**
 * @author Final_ROOT
 * @since 1.0
 */
public final class SetupUtil {
    public static void init() {
        FinalTech finalTech = FinalTech.getInstance();

        /* setup config */

        ConfigFileManager config = FinalTech.getConfigManager();

        AbstractManualCraftMachine.COUNT_THRESHOLD = config.getOrDefault(10, "manual", "count-threshold");
        if (AbstractManualCraftMachine.COUNT_THRESHOLD == -1) {
            AbstractManualCraftMachine.COUNT_THRESHOLD = Slimefun.getTickerTask().getTickRate();
        }

        /* setup menu */

        FinalTechMenus.MAIN_MENU.setTier(0);
        FinalTechMenus.MAIN_MENU.register(finalTech);

        FinalTechMenus.MENU_ITEMS.setTier(0);
        FinalTechMenus.MENU_CARGO_SYSTEM.setTier(0);
        FinalTechMenus.MENU_ELECTRICITY_SYSTEM.setTier(0);
        FinalTechMenus.MENU_FUNCTIONAL_MACHINE.setTier(0);
        FinalTechMenus.MENU_PRODUCTIVE_MACHINE.setTier(0);
        FinalTechMenus.MENU_DISC.setTier(0);

        /* Enchantment */
        ReflectionUtil.setStaticValue(Enchantment.class, "acceptingNew", true);
        Enchantment.registerEnchantment(NullEnchantment.ENCHANTMENT);
        FinalTechItems.ENTROPY.addUnsafeEnchantment(NullEnchantment.ENCHANTMENT, 0);
        FinalTechItems.QUANTITY_MODULE_INFINITY.addUnsafeEnchantment(NullEnchantment.ENCHANTMENT, 0);
        FinalTechItems.OPERATION_ACCELERATOR_INFINITY.addUnsafeEnchantment(NullEnchantment.ENCHANTMENT, 0);
        FinalTechItems.COPY_CARD.addUnsafeEnchantment(NullEnchantment.ENCHANTMENT, 0);
        FinalTechItems.SHINE.addUnsafeEnchantment(NullEnchantment.ENCHANTMENT, 0);
        FinalTechItems.PHONY.addUnsafeEnchantment(NullEnchantment.ENCHANTMENT, 0);
        FinalTechItems.MACHINE_CHARGE_CARD_INFINITY.addUnsafeEnchantment(NullEnchantment.ENCHANTMENT, 0);
        FinalTechItems.MACHINE_ACCELERATE_CARD_INFINITY.addUnsafeEnchantment(NullEnchantment.ENCHANTMENT, 0);
        FinalTechItems.MACHINE_ACTIVATE_CARD_L4.addUnsafeEnchantment(NullEnchantment.ENCHANTMENT, 0);
        FinalTechItems.ADVANCED_AUTO_CRAFT.addUnsafeEnchantment(NullEnchantment.ENCHANTMENT, 0);
        FinalTechItems.MULTI_FRAME_MACHINE.addUnsafeEnchantment(NullEnchantment.ENCHANTMENT, 0);
        FinalTechItems.MATRIX_ITEM_DISMANTLE_TABLE.addUnsafeEnchantment(NullEnchantment.ENCHANTMENT, 0);
        FinalTechItems.MATRIX_EXPANDED_CAPACITOR.addUnsafeEnchantment(NullEnchantment.ENCHANTMENT, 0);
        FinalTechItems.MATRIX_ITEM_DESERIALIZE_PARSER.addUnsafeEnchantment(NullEnchantment.ENCHANTMENT, 0);
        FinalTechItems.ENTROPY_CONSTRUCTOR.addUnsafeEnchantment(NullEnchantment.ENCHANTMENT, 0);
        FinalTechItems.MATRIX_GENERATOR.addUnsafeEnchantment(NullEnchantment.ENCHANTMENT, 0);
        FinalTechItems.MATRIX_ACCELERATOR.addUnsafeEnchantment(NullEnchantment.ENCHANTMENT, 0);
        FinalTechItems.MATRIX_ITEM_SERIALIZATION_CONSTRUCTOR.addUnsafeEnchantment(NullEnchantment.ENCHANTMENT, 0);
        FinalTechItems.MATRIX_REACTOR.addUnsafeEnchantment(NullEnchantment.ENCHANTMENT, 0);
        ItemStackUtil.addLoreToFirst(FinalTechItems.STORAGE_CARD, StorageCard.ITEM_LORE);

        /* items */
        // material
        FinalTechMenus.SUB_MENU_MATERIAL.addTo(
                new WaterCard(FinalTechMenus.MENU_ITEMS, FinalTechItems.WATER_CARD, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.WATER_CARD).register(),
                new LavaCard(FinalTechMenus.MENU_ITEMS, FinalTechItems.LAVA_CARD, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.LAVA_CARD).register(),
                new MilkCard(FinalTechMenus.MENU_ITEMS, FinalTechItems.MILK_CARD, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.MILK_CARD).register());
        FinalTechMenus.SUB_MENU_MATERIAL.addTo(
                new Gearwheel(FinalTechMenus.MENU_ITEMS, FinalTechItems.GEARWHEEL, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.GEARWHEEL, ItemStackUtil.cloneItem(FinalTechItems.GEARWHEEL, 4)).register(),
                new UnorderedDust(FinalTechMenus.MENU_ITEMS, FinalTechItems.UNORDERED_DUST,  FinalTechRecipes.RECIPE_TYPE_ORDERED_DUST_FACTORY, FinalTechRecipes.UNORDERED_DUST).register(),
                new OrderedDust(FinalTechMenus.MENU_ITEMS, FinalTechItems.ORDERED_DUST, FinalTechRecipes.RECIPE_TYPE_ORDERED_DUST_FACTORY, FinalTechRecipes.ORDERED_DUST).register(),
                new Bug(FinalTechMenus.MENU_ITEMS, FinalTechItems.BUG, FinalTechRecipes.RECIPE_TYPE_EQUIVALENT_EXCHANGE_TABLE, FinalTechRecipes.BUG).register(),
                new Entropy(FinalTechMenus.MENU_ITEMS, FinalTechItems.ENTROPY, FinalTechRecipes.RECIPE_TYPE_ENTROPY_CONSTRUCTOR, FinalTechRecipes.ENTROPY).register(),
                new Box(FinalTechMenus.MENU_ITEMS, FinalTechItems.BOX, RecipeType.NULL, FinalTechRecipes.BOX).register(),
                new Shine(FinalTechMenus.MENU_ITEMS, FinalTechItems.SHINE, FinalTechRecipes.RECIPE_TYPE_BOX, FinalTechRecipes.SHINE).register(),
                new CopyCard(FinalTechMenus.MENU_ITEMS, FinalTechItems.COPY_CARD, FinalTechRecipes.RECIPE_TYPE_ITEM_SERIALIZATION_CONSTRUCTOR, FinalTechRecipes.COPY_CARD).register());
        FinalTechMenus.SUB_MENU_MATERIAL.addTo(
                new Annular(FinalTechMenus.MENU_ITEMS, FinalTechItems.ANNULAR, FinalTechRecipes.RECIPE_TYPE_CARD_OPERATION_TABLE, FinalTechRecipes.ANNULAR).register(),
                new QuantityModule(FinalTechMenus.MENU_ITEMS, FinalTechItems.QUANTITY_MODULE, FinalTechRecipes.RECIPE_TYPE_MATRIX_CRAFTING_TABLE, FinalTechRecipes.QUANTITY_MODULE).register(),
                new QuantityModuleInfinity(FinalTechMenus.MENU_ITEMS, FinalTechItems.QUANTITY_MODULE_INFINITY, FinalTechRecipes.RECIPE_TYPE_MATRIX_CRAFTING_TABLE, FinalTechRecipes.QUANTITY_MODULE_INFINITY).register(),
                new Singularity(FinalTechMenus.MENU_ITEMS, FinalTechItems.SINGULARITY, FinalTechRecipes.RECIPE_TYPE_ITEM_SERIALIZATION_CONSTRUCTOR, FinalTechRecipes.SINGULARITY).register(),
                new Spirochete(FinalTechMenus.MENU_ITEMS, FinalTechItems.SPIROCHETE, FinalTechRecipes.RECIPE_TYPE_ITEM_SERIALIZATION_CONSTRUCTOR, FinalTechRecipes.SPIROCHETE).register(),
                new Shell(FinalTechMenus.MENU_ITEMS, FinalTechItems.SHELL, FinalTechRecipes.RECIPE_TYPE_CARD_OPERATION_TABLE, FinalTechRecipes.SHELL).register(),
                new ItemPhony(FinalTechMenus.MENU_ITEMS, FinalTechItems.PHONY, FinalTechRecipes.RECIPE_TYPE_CARD_OPERATION_TABLE, FinalTechRecipes.PHONY).register(),
                new Justifiability(FinalTechMenus.MENU_ITEMS, FinalTechItems.JUSTIFIABILITY, FinalTechRecipes.RECIPE_TYPE_ENTROPY_SEED, FinalTechRecipes.JUSTIFIABILITY).register(),
                new EquivalentConcept(FinalTechMenus.MENU_ITEMS, FinalTechItems.EQUIVALENT_CONCEPT, FinalTechRecipes.RECIPE_TYPE_ENTROPY_SEED, FinalTechRecipes.EQUIVALENT_CONCEPT).register());
        // logic item
        FinalTechMenus.SUB_MENU_LOGIC_ITEM.addTo(
                new LogicFalse(FinalTechMenus.MENU_ITEMS, FinalTechItems.LOGIC_FALSE, FinalTechRecipes.RECIPE_TYPE_LOGIC_GENERATOR, FinalTechRecipes.LOGIC_FALSE).register(),
                new LogicTrue(FinalTechMenus.MENU_ITEMS, FinalTechItems.LOGIC_TRUE, FinalTechRecipes.RECIPE_TYPE_LOGIC_GENERATOR, FinalTechRecipes.LOGIC_TRUE).register(),
                new DigitalZero(FinalTechMenus.MENU_ITEMS, FinalTechItems.DIGITAL_ZERO, FinalTechRecipes.RECIPE_TYPE_LOGIC_CRAFTER, FinalTechRecipes.DIGITAL_ZERO).register(),
                new DigitalOne(FinalTechMenus.MENU_ITEMS, FinalTechItems.DIGITAL_ONE, FinalTechRecipes.RECIPE_TYPE_LOGIC_CRAFTER, FinalTechRecipes.DIGITAL_ONE).register(),
                new DigitalTwo(FinalTechMenus.MENU_ITEMS, FinalTechItems.DIGITAL_TWO, FinalTechRecipes.RECIPE_TYPE_LOGIC_CRAFTER, FinalTechRecipes.DIGITAL_TWO).register(),
                new DigitalThree(FinalTechMenus.MENU_ITEMS, FinalTechItems.DIGITAL_THREE, FinalTechRecipes.RECIPE_TYPE_LOGIC_CRAFTER, FinalTechRecipes.DIGITAL_THREE).register(),
                new DigitalFour(FinalTechMenus.MENU_ITEMS, FinalTechItems.DIGITAL_FOUR, FinalTechRecipes.RECIPE_TYPE_LOGIC_CRAFTER, FinalTechRecipes.DIGITAL_FOUR).register(),
                new DigitalFive(FinalTechMenus.MENU_ITEMS, FinalTechItems.DIGITAL_FIVE, FinalTechRecipes.RECIPE_TYPE_LOGIC_CRAFTER, FinalTechRecipes.DIGITAL_FIVE).register(),
                new DigitalSix(FinalTechMenus.MENU_ITEMS, FinalTechItems.DIGITAL_SIX, FinalTechRecipes.RECIPE_TYPE_LOGIC_CRAFTER, FinalTechRecipes.DIGITAL_SIX).register(),
                new DigitalSeven(FinalTechMenus.MENU_ITEMS, FinalTechItems.DIGITAL_SEVEN, FinalTechRecipes.RECIPE_TYPE_LOGIC_CRAFTER, FinalTechRecipes.DIGITAL_SEVEN).register(),
                new DigitalEight(FinalTechMenus.MENU_ITEMS, FinalTechItems.DIGITAL_EIGHT, FinalTechRecipes.RECIPE_TYPE_LOGIC_CRAFTER, FinalTechRecipes.DIGITAL_EIGHT).register(),
                new DigitalNine(FinalTechMenus.MENU_ITEMS, FinalTechItems.DIGITAL_NINE, FinalTechRecipes.RECIPE_TYPE_LOGIC_CRAFTER, FinalTechRecipes.DIGITAL_NINE).register(),
                new DigitalTen(FinalTechMenus.MENU_ITEMS, FinalTechItems.DIGITAL_TEN, FinalTechRecipes.RECIPE_TYPE_LOGIC_CRAFTER, FinalTechRecipes.DIGITAL_TEN).register(),
                new DigitalEleven(FinalTechMenus.MENU_ITEMS, FinalTechItems.DIGITAL_ELEVEN, FinalTechRecipes.RECIPE_TYPE_LOGIC_CRAFTER, FinalTechRecipes.DIGITAL_ELEVEN).register(),
                new DigitalTwelve(FinalTechMenus.MENU_ITEMS, FinalTechItems.DIGITAL_TWELVE, FinalTechRecipes.RECIPE_TYPE_LOGIC_CRAFTER, FinalTechRecipes.DIGITAL_TWELVE).register(),
                new DigitalThirteen(FinalTechMenus.MENU_ITEMS, FinalTechItems.DIGITAL_THIRTEEN, FinalTechRecipes.RECIPE_TYPE_LOGIC_CRAFTER, FinalTechRecipes.DIGITAL_THIRTEEN).register(),
                new DigitalFourteen(FinalTechMenus.MENU_ITEMS, FinalTechItems.DIGITAL_FOURTEEN, FinalTechRecipes.RECIPE_TYPE_LOGIC_CRAFTER, FinalTechRecipes.DIGITAL_FOURTEEN).register(),
                new DigitalFifteen(FinalTechMenus.MENU_ITEMS, FinalTechItems.DIGITAL_FIFTEEN, FinalTechRecipes.RECIPE_TYPE_LOGIC_CRAFTER, FinalTechRecipes.DIGITAL_FIFTEEN).register());
        // consumable
        FinalTechMenus.SUB_MENU_CONSUMABLE.addTo(
                new MachineChargeCardL1(FinalTechMenus.MENU_ITEMS, FinalTechItems.MACHINE_CHARGE_CARD_L1, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.MACHINE_CHARGE_CARD_L1).register(),
                new MachineChargeCardL2(FinalTechMenus.MENU_ITEMS, FinalTechItems.MACHINE_CHARGE_CARD_L2, FinalTechRecipes.RECIPE_TYPE_MATRIX_CRAFTING_TABLE, FinalTechRecipes.MACHINE_CHARGE_CARD_L2).register(),
                new MachineChargeCardL3(FinalTechMenus.MENU_ITEMS, FinalTechItems.MACHINE_CHARGE_CARD_L3, FinalTechRecipes.RECIPE_TYPE_MATRIX_CRAFTING_TABLE, FinalTechRecipes.MACHINE_CHARGE_CARD_L3).register(),
                new MachineAccelerateCardL1(FinalTechMenus.MENU_ITEMS, FinalTechItems.MACHINE_ACCELERATE_CARD_L1, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.MACHINE_ACCELERATE_CARD_L1).register(),
                new MachineAccelerateCardL2(FinalTechMenus.MENU_ITEMS, FinalTechItems.MACHINE_ACCELERATE_CARD_L2, FinalTechRecipes.RECIPE_TYPE_MATRIX_CRAFTING_TABLE, FinalTechRecipes.MACHINE_ACCELERATE_CARD_L2).register(),
                new MachineAccelerateCardL3(FinalTechMenus.MENU_ITEMS, FinalTechItems.MACHINE_ACCELERATE_CARD_L3, FinalTechRecipes.RECIPE_TYPE_MATRIX_CRAFTING_TABLE, FinalTechRecipes.MACHINE_ACCELERATE_CARD_L3).register(),
                new MachineActivateCardL1(FinalTechMenus.MENU_ITEMS, FinalTechItems.MACHINE_ACTIVATE_CARD_L1, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.MACHINE_ACTIVATE_CARD_L1).register(),
                new MachineActivateCardL2(FinalTechMenus.MENU_ITEMS, FinalTechItems.MACHINE_ACTIVATE_CARD_L2, FinalTechRecipes.RECIPE_TYPE_MATRIX_CRAFTING_TABLE, FinalTechRecipes.MACHINE_ACTIVATE_CARD_L2).register(),
                new MachineActivateCardL3(FinalTechMenus.MENU_ITEMS, FinalTechItems.MACHINE_ACTIVATE_CARD_L3, FinalTechRecipes.RECIPE_TYPE_MATRIX_CRAFTING_TABLE, FinalTechRecipes.MACHINE_ACTIVATE_CARD_L3).register());
        FinalTechMenus.SUB_MENU_CONSUMABLE.addTo(
                new MagicHypnotic(FinalTechMenus.MENU_ITEMS, FinalTechItems.MAGIC_HYPNOTIC, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.MAGIC_HYPNOTIC).register(),
                new ResearchUnlockTicket(FinalTechMenus.MENU_ITEMS, FinalTechItems.RESEARCH_UNLOCK_TICKET, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.RESEARCH_UNLOCK_TICKET).register());
        // tool
        FinalTechMenus.SUB_MENU_TOOL.addTo(
                new StaffElementalLine(FinalTechMenus.MENU_ITEMS, FinalTechItems.STAFF_ELEMENTAL_LINE, RecipeType.MAGIC_WORKBENCH, FinalTechRecipes.STAFF_ELEMENTAL_LINE).register(),
                new PotionEffectCompressor(FinalTechMenus.MENU_ITEMS, FinalTechItems.POTION_EFFECT_COMPRESSOR, FinalTechRecipes.RECIPE_TYPE_MATRIX_CRAFTING_TABLE, FinalTechRecipes.POTION_EFFECT_COMPRESSOR).register(),
                new PotionEffectDilator(FinalTechMenus.MENU_ITEMS, FinalTechItems.POTION_EFFECT_DILATOR, FinalTechRecipes.RECIPE_TYPE_MATRIX_CRAFTING_TABLE, FinalTechRecipes.POTION_EFFECT_DILATOR).register(),
                new PotionEffectPurifier(FinalTechMenus.MENU_ITEMS, FinalTechItems.POTION_EFFECT_PURIFIER, FinalTechRecipes.RECIPE_TYPE_MATRIX_CRAFTING_TABLE, FinalTechRecipes.POTION_EFFECT_PURIFIER).register());
        FinalTechMenus.SUB_MENU_TOOL.addTo(
                new MenuViewer(FinalTechMenus.MENU_ITEMS, FinalTechItems.MENU_VIEWER, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.MENU_VIEWER).register(),
                new LocationRecorder(FinalTechMenus.MENU_ITEMS, FinalTechItems.LOCATION_RECORDER, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.LOCATION_RECORDER).register(),
                new MachineConfigurator(FinalTechMenus.MENU_ITEMS, FinalTechItems.MACHINE_CONFIGURATOR, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.MACHINE_CONFIGURATOR).register(),
                new PortableEnergyStorage(FinalTechMenus.MENU_ITEMS, FinalTechItems.PORTABLE_ENERGY_STORAGE, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.PORTABLE_ENERGY_STORAGE).register());
        // weapon
        FinalTechMenus.SUB_MENU_WEAPON.addTo(
                new SuperShovel(FinalTechMenus.MENU_ITEMS, FinalTechItems.SUPER_SHOVEL, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.SUPER_SHOVEL).register(),
                new UltimateShovel(FinalTechMenus.MENU_ITEMS, FinalTechItems.ULTIMATE_SHOVEL, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.ULTIMATE_SHOVEL).register(),
                new SuperPickaxe(FinalTechMenus.MENU_ITEMS, FinalTechItems.SUPER_PICKAXE, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.SUPER_PICKAXE).register(),
                new UltimatePickaxe(FinalTechMenus.MENU_ITEMS, FinalTechItems.ULTIMATE_PICKAXE, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.ULTIMATE_PICKAXE).register(),
                new SuperAxe(FinalTechMenus.MENU_ITEMS, FinalTechItems.SUPER_AXE, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.SUPER_AXE).register(),
                new UltimateAxe(FinalTechMenus.MENU_ITEMS, FinalTechItems.ULTIMATE_AXE, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.ULTIMATE_AXE).register(),
                new SuperHoe(FinalTechMenus.MENU_ITEMS, FinalTechItems.SUPER_HOE, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.SUPER_HOE).register(),
                new UltimateHoe(FinalTechMenus.MENU_ITEMS, FinalTechItems.ULTIMATE_HOE, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.ULTIMATE_HOE).register()
//                new SlimefunItem(FinalTechMenus.MENU_ITEMS, FinalTechItems.SWORD1, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.SWORD1).register(FinalTech.getInstance()),
//                new SlimefunItem(FinalTechMenus.MENU_ITEMS, FinalTechItems.SWORD2, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.SWORD2).register(FinalTech.getInstance()),
//                new SlimefunItem(FinalTechMenus.MENU_ITEMS, FinalTechItems.SWORD3, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.SWORD3).register(FinalTech.getInstance()),
//                new SlimefunItem(FinalTechMenus.MENU_ITEMS, FinalTechItems.SWORD4, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.SWORD4).register(FinalTech.getInstance())
        );

        /* electricity system */
        // electric generator
        FinalTechMenus.SUB_MENU_ELECTRIC_GENERATOR.addTo(
                new BasicGenerator(FinalTechMenus.MENU_ELECTRICITY_SYSTEM, FinalTechItems.BASIC_GENERATOR, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.BASIC_GENERATOR).register(),
                new AdvancedGenerator(FinalTechMenus.MENU_ELECTRICITY_SYSTEM, FinalTechItems.ADVANCED_GENERATOR, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.ADVANCED_GENERATOR).register(),
                new CarbonadoGenerator(FinalTechMenus.MENU_ELECTRICITY_SYSTEM, FinalTechItems.CARBONADO_GENERATOR, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.CARBONADO_GENERATOR).register(),
                new EnergizedGenerator(FinalTechMenus.MENU_ELECTRICITY_SYSTEM, FinalTechItems.ENERGIZED_GENERATOR, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.ENERGIZED_GENERATOR).register(),
                new EnergizedStackGenerator(FinalTechMenus.MENU_ELECTRICITY_SYSTEM, FinalTechItems.ENERGIZED_STACK_GENERATOR, FinalTechRecipes.RECIPE_TYPE_MATRIX_CRAFTING_TABLE, FinalTechRecipes.ENERGIZED_STACK_GENERATOR).register(),
                new OverloadedGenerator(FinalTechMenus.MENU_ELECTRICITY_SYSTEM, FinalTechItems.OVERLOADED_GENERATOR, FinalTechRecipes.RECIPE_TYPE_MATRIX_CRAFTING_TABLE, FinalTechRecipes.OVERLOADED_GENERATOR).register());
        FinalTechMenus.SUB_MENU_ELECTRIC_GENERATOR.addTo(
                new DustGenerator(FinalTechMenus.MENU_ELECTRICITY_SYSTEM, FinalTechItems.ORDERED_DUST_GENERATOR, FinalTechRecipes.RECIPE_TYPE_MATRIX_CRAFTING_TABLE, FinalTechRecipes.ORDERED_DUST_GENERATOR).register(),
                new TimeGenerator(FinalTechMenus.MENU_ELECTRICITY_SYSTEM, FinalTechItems.TIME_GENERATOR, FinalTechRecipes.RECIPE_TYPE_MATRIX_CRAFTING_TABLE, FinalTechRecipes.TIME_GENERATOR).register(),
                new EnergizedChargeBase(FinalTechMenus.MENU_ELECTRICITY_SYSTEM, FinalTechItems.ENERGIZED_CHARGE_BASE, FinalTechRecipes.RECIPE_TYPE_MATRIX_CRAFTING_TABLE, FinalTechRecipes.ENERGIZED_CHARGE_BASE).register(),
                new OverloadedChargeBase(FinalTechMenus.MENU_ELECTRICITY_SYSTEM, FinalTechItems.OVERLOADED_CHARGE_BASE, FinalTechRecipes.RECIPE_TYPE_MATRIX_CRAFTING_TABLE, FinalTechRecipes.OVERLOADED_CHARGE_BASE).register());
        // electric storage
        FinalTechMenus.SUB_MENU_ELECTRIC_STORAGE.addTo(
                new SmallExpandedCapacitor(FinalTechMenus.MENU_ELECTRICITY_SYSTEM, FinalTechItems.SMALL_EXPANDED_CAPACITOR, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.SMALL_EXPANDED_CAPACITOR).register(),
                new MediumExpandedCapacitor(FinalTechMenus.MENU_ELECTRICITY_SYSTEM, FinalTechItems.MEDIUM_EXPANDED_CAPACITOR, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.MEDIUM_EXPANDED_CAPACITOR).register(),
                new BigExpandedCapacitor(FinalTechMenus.MENU_ELECTRICITY_SYSTEM, FinalTechItems.BIG_EXPANDED_CAPACITOR, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.BIG_EXPANDED_CAPACITOR).register(),
                new LargeExpandedCapacitor(FinalTechMenus.MENU_ELECTRICITY_SYSTEM, FinalTechItems.LARGE_EXPANDED_CAPACITOR, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.LARGE_EXPANDED_CAPACITOR).register(),
                new CarbonadoExpandedCapacitor(FinalTechMenus.MENU_ELECTRICITY_SYSTEM, FinalTechItems.CARBONADO_EXPANDED_CAPACITOR, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.CARBONADO_EXPANDED_CAPACITOR).register(),
                new EnergizedExpandedCapacitor(FinalTechMenus.MENU_ELECTRICITY_SYSTEM, FinalTechItems.ENERGIZED_EXPANDED_CAPACITOR, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.ENERGIZED_EXPANDED_CAPACITOR).register());
        FinalTechMenus.SUB_MENU_ELECTRIC_STORAGE.addTo(
                new EnergizedStackExpandedCapacitor(FinalTechMenus.MENU_ELECTRICITY_SYSTEM, FinalTechItems.ENERGIZED_STACK_EXPANDED_CAPACITOR, FinalTechRecipes.RECIPE_TYPE_MATRIX_CRAFTING_TABLE, FinalTechRecipes.ENERGIZED_STACK_EXPANDED_CAPACITOR).register(),
                new OverloadedExpandedCapacitor(FinalTechMenus.MENU_ELECTRICITY_SYSTEM, FinalTechItems.OVERLOADED_EXPANDED_CAPACITOR, FinalTechRecipes.RECIPE_TYPE_MATRIX_CRAFTING_TABLE, FinalTechRecipes.OVERLOADED_EXPANDED_CAPACITOR).register(),
                new TimeCapacitor(FinalTechMenus.MENU_ELECTRICITY_SYSTEM, FinalTechItems.TIME_CAPACITOR, FinalTechRecipes.RECIPE_TYPE_MATRIX_CRAFTING_TABLE, FinalTechRecipes.TIME_CAPACITOR).register());
        // electric transmission
        FinalTechMenus.SUB_MENU_ELECTRIC_TRANSMISSION.addTo(
                new NormalElectricityShootPile(FinalTechMenus.MENU_ELECTRICITY_SYSTEM, FinalTechItems.NORMAL_ELECTRICITY_SHOOT_PILE, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.NORMAL_ELECTRICITY_SHOOT_PILE).register(),
                new EnergizedElectricityShootPile(FinalTechMenus.MENU_ELECTRICITY_SYSTEM, FinalTechItems.ENERGIZED_ELECTRICITY_SHOOT_PILE, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.ENERGIZED_ELECTRICITY_SHOOT_PILE).register(),
                new OverloadedElectricityShootPile(FinalTechMenus.MENU_ELECTRICITY_SYSTEM, FinalTechItems.OVERLOADED_ELECTRICITY_SHOOT_PILE, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.OVERLOADED_ELECTRICITY_SHOOT_PILE).register(),
//                new DispersalCapacitor(FinalTechMenus.MENU_ELECTRICITY_SYSTEM, FinalTechItems.DISPERSAL_CAPACITOR, FinalTechRecipes.RECIPE_TYPE_MATRIX_CRAFTING_TABLE, FinalTechRecipes.DISPERSAL_CAPACITOR).register(),
                new VariableWireResistance(FinalTechMenus.MENU_ELECTRICITY_SYSTEM, FinalTechItems.VARIABLE_WIRE_RESISTANCE, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.VARIABLE_WIRE_RESISTANCE).register(),
                new VariableWireCapacitor(FinalTechMenus.MENU_ELECTRICITY_SYSTEM, FinalTechItems.VARIABLE_WIRE_CAPACITOR, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.VARIABLE_WIRE_CAPACITOR).register());
        // electric accelerator
        FinalTechMenus.SUB_MENU_ELECTRIC_ACCELERATOR.addTo(
                new EnergizedAccelerator(FinalTechMenus.MENU_ELECTRICITY_SYSTEM, FinalTechItems.ENERGIZED_ACCELERATOR, FinalTechRecipes.RECIPE_TYPE_MATRIX_CRAFTING_TABLE, FinalTechRecipes.ENERGIZED_ACCELERATOR).register(),
                new OverloadedAccelerator(FinalTechMenus.MENU_ELECTRICITY_SYSTEM, FinalTechItems.OVERLOADED_ACCELERATOR, FinalTechRecipes.RECIPE_TYPE_MATRIX_CRAFTING_TABLE, FinalTechRecipes.OVERLOADED_ACCELERATOR).register());

        /* cargo system */
        // storage unit
        FinalTechMenus.SUB_MENU_STORAGE_UNIT.addTo(
                new NormalStorageUnit(FinalTechMenus.MENU_CARGO_SYSTEM, FinalTechItems.NORMAL_STORAGE_UNIT, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.NORMAL_STORAGE_UNIT).register(),
                new DividedStorageUnit(FinalTechMenus.MENU_CARGO_SYSTEM, FinalTechItems.DIVIDED_STORAGE_UNIT, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.DIVIDED_STORAGE_UNIT).register(),
                new LimitedStorageUnit(FinalTechMenus.MENU_CARGO_SYSTEM, FinalTechItems.LIMITED_STORAGE_UNIT, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.LIMITED_STORAGE_UNIT).register(),
                new StackStorageUnit(FinalTechMenus.MENU_CARGO_SYSTEM, FinalTechItems.STACK_STORAGE_UNIT, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.STACK_STORAGE_UNIT).register(),
                new DividedLimitedStorageUnit(FinalTechMenus.MENU_CARGO_SYSTEM, FinalTechItems.DIVIDED_LIMITED_STORAGE_UNIT, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.DIVIDED_LIMITED_STORAGE_UNIT).register(),
                new DividedStackStorageUnit(FinalTechMenus.MENU_CARGO_SYSTEM, FinalTechItems.DIVIDED_STACK_STORAGE_UNIT, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.DIVIDED_STACK_STORAGE_UNIT).register(),
                new LimitedStackStorageUnit(FinalTechMenus.MENU_CARGO_SYSTEM, FinalTechItems.LIMITED_STACK_STORAGE_UNIT, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.LIMITED_STACK_STORAGE_UNIT).register());
        FinalTechMenus.SUB_MENU_STORAGE_UNIT.addTo(
                new RandomInputStorageUnit(FinalTechMenus.MENU_CARGO_SYSTEM, FinalTechItems.RANDOM_INPUT_STORAGE_UNIT, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.RANDOM_INPUT_STORAGE_UNIT).register(),
                new RandomOutputStorageUnit(FinalTechMenus.MENU_CARGO_SYSTEM, FinalTechItems.RANDOM_OUTPUT_STORAGE_UNIT, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.RANDOM_OUTPUT_STORAGE_UNIT).register(),
                new RandomAccessStorageUnit(FinalTechMenus.MENU_CARGO_SYSTEM, FinalTechItems.RANDOM_ACCESS_STORAGE_UNIT, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.RANDOM_ACCESS_STORAGE_UNIT).register(),
                new DistributeLeftStorageUnit(FinalTechMenus.MENU_CARGO_SYSTEM, FinalTechItems.DISTRIBUTE_LEFT_STORAGE_UNIT, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.DISTRIBUTE_LEFT_STORAGE_UNIT).register(),
                new DistributeRightStorageUnit(FinalTechMenus.MENU_CARGO_SYSTEM, FinalTechItems.DISTRIBUTE_RIGHT_STORAGE_UNIT, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.DISTRIBUTE_RIGHT_STORAGE_UNIT).register());
        // advanced storage
        FinalTechMenus.SUB_MENU_ADVANCED_STORAGE.addTo(
                new StorageInteractPort(FinalTechMenus.MENU_CARGO_SYSTEM, FinalTechItems.STORAGE_INTERACT_PORT, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.STORAGE_INTERACT_PORT).register(),
                new StorageInsertPort(FinalTechMenus.MENU_CARGO_SYSTEM, FinalTechItems.STORAGE_INSERT_PORT, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.STORAGE_INSERT_PORT).register(),
                new StorageWithdrawPort(FinalTechMenus.MENU_CARGO_SYSTEM, FinalTechItems.STORAGE_WITHDRAW_PORT, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.STORAGE_WITHDRAW_PORT).register(),
                new StorageCard(FinalTechMenus.MENU_CARGO_SYSTEM, FinalTechItems.STORAGE_CARD, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.STORAGE_CARD).register());
        // accessor
        FinalTechMenus.SUB_MENU_ACCESSOR.addTo(
                new RemoteAccessor(FinalTechMenus.MENU_CARGO_SYSTEM, FinalTechItems.REMOTE_ACCESSOR, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.REMOTE_ACCESSOR).register(),
                new ConsumableRemoteAccessor(FinalTechMenus.MENU_CARGO_SYSTEM, FinalTechItems.CONSUMABLE_REMOTE_ACCESSOR, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.CONSUMABLE_REMOTE_ACCESSOR).register(),
                new ConfigurableRemoteAccessor(FinalTechMenus.MENU_CARGO_SYSTEM, FinalTechItems.CONFIGURABLE_REMOTE_ACCESSOR, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.CONFIGURABLE_REMOTE_ACCESSOR).register(),
                new ExpandedConsumableRemoteAccessor(FinalTechMenus.MENU_CARGO_SYSTEM, FinalTechItems.EXPANDED_CONSUMABLE_REMOTE_ACCESSOR, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.EXPANDED_CONSUMABLE_REMOTE_ACCESSOR).register(),
                new ExpandedConfigurableRemoteAccessor(FinalTechMenus.MENU_CARGO_SYSTEM, FinalTechItems.EXPANDED_CONFIGURABLE_REMOTE_ACCESSOR, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.EXPANDED_CONFIGURABLE_REMOTE_ACCESSOR).register(),
                new AreaAccessor(FinalTechMenus.MENU_CARGO_SYSTEM, FinalTechItems.AREA_ACCESSOR, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.AREA_ACCESSOR).register());
        FinalTechMenus.SUB_MENU_ACCESSOR.addTo(
                new Transporter(FinalTechMenus.MENU_CARGO_SYSTEM, FinalTechItems.TRANSPORTER, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.TRANSPORTER).register(),
                new ConsumableTransporter(FinalTechMenus.MENU_CARGO_SYSTEM, FinalTechItems.CONSUMABLE_TRANSPORTER, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.CONSUMABLE_TRANSPORTER).register(),
                new ConfigurableTransporter(FinalTechMenus.MENU_CARGO_SYSTEM, FinalTechItems.CONFIGURABLE_TRANSPORTER, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.CONFIGURABLE_TRANSPORTER).register(),
                new ExpandedConsumableTransporter(FinalTechMenus.MENU_CARGO_SYSTEM, FinalTechItems.EXPANDED_CONSUMABLE_TRANSPORTER, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.EXPANDED_CONSUMABLE_TRANSPORTER).register(),
                new ExpandedConfigurableTransporter(FinalTechMenus.MENU_CARGO_SYSTEM, FinalTechItems.EXPANDED_CONFIGURABLE_TRANSPORTER, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.EXPANDED_CONFIGURABLE_TRANSPORTER).register());
        // logic
        FinalTechMenus.SUB_MENU_LOGIC.addTo(
                new LogicNotNullComparator(FinalTechMenus.MENU_CARGO_SYSTEM, FinalTechItems.LOGIC_COMPARATOR_NOTNULL, FinalTechRecipes.RECIPE_TYPE_MATRIX_CRAFTING_TABLE, FinalTechRecipes.LOGIC_COMPARATOR_NOTNULL).register(),
                new LogicAmountComparator(FinalTechMenus.MENU_CARGO_SYSTEM, FinalTechItems.LOGIC_COMPARATOR_AMOUNT, FinalTechRecipes.RECIPE_TYPE_MATRIX_CRAFTING_TABLE, FinalTechRecipes.LOGIC_COMPARATOR_AMOUNT).register(),
                new LogicSimilarComparator(FinalTechMenus.MENU_CARGO_SYSTEM, FinalTechItems.LOGIC_COMPARATOR_SIMILAR, FinalTechRecipes.RECIPE_TYPE_MATRIX_CRAFTING_TABLE, FinalTechRecipes.LOGIC_COMPARATOR_SIMILAR).register(),
                new LogicEqualComparator(FinalTechMenus.MENU_CARGO_SYSTEM, FinalTechItems.LOGIC_COMPARATOR_EQUAL, FinalTechRecipes.RECIPE_TYPE_MATRIX_CRAFTING_TABLE, FinalTechRecipes.LOGIC_COMPARATOR_EQUAL).register(),
                new LogicCrafter(FinalTechMenus.MENU_CARGO_SYSTEM, FinalTechItems.LOGIC_CRAFTER, FinalTechRecipes.RECIPE_TYPE_MATRIX_CRAFTING_TABLE, FinalTechRecipes.LOGIC_CRAFTER).register(),
                new DigitAdder(FinalTechMenus.MENU_CARGO_SYSTEM, FinalTechItems.DIGIT_ADDER, FinalTechRecipes.RECIPE_TYPE_MATRIX_CRAFTING_TABLE, FinalTechRecipes.DIGIT_ADDER).register());
        // cargo
        FinalTechMenus.SUB_MENU_CARGO.addTo(
                new BasicFrameMachine(FinalTechMenus.MENU_CARGO_SYSTEM, FinalTechItems.BASIC_FRAME_MACHINE, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.BASIC_FRAME_MACHINE).register(),
                new PointTransfer(FinalTechMenus.MENU_CARGO_SYSTEM, FinalTechItems.POINT_TRANSFER, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.POINT_TRANSFER).register(),
                new MeshTransfer(FinalTechMenus.MENU_CARGO_SYSTEM, FinalTechItems.MESH_TRANSFER, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.MESH_TRANSFER).register(),
                new LineTransfer(FinalTechMenus.MENU_CARGO_SYSTEM, FinalTechItems.LINE_TRANSFER, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.LINE_TRANSFER).register(),
                new LocationTransfer(FinalTechMenus.MENU_CARGO_SYSTEM, FinalTechItems.LOCATION_TRANSFER, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.LOCATION_TRANSFER).register());

        /* functional machines */
        // core machines
        FinalTechMenus.SUB_MENU_CORE_MACHINE.addTo(
                new DustFactoryDirt(FinalTechMenus.MENU_FUNCTIONAL_MACHINE, FinalTechItems.ORDERED_DUST_FACTORY_DIRT, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.ORDERED_DUST_FACTORY_DIRT).register(),
                new DustFactoryStone(FinalTechMenus.MENU_FUNCTIONAL_MACHINE, FinalTechItems.ORDERED_DUST_FACTORY_STONE, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.ORDERED_DUST_FACTORY_STONE).register(),
                new MatrixCraftingTable(FinalTechMenus.MENU_FUNCTIONAL_MACHINE, FinalTechItems.MATRIX_CRAFTING_TABLE, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.MATRIX_CRAFTING_TABLE).register());
        FinalTechMenus.SUB_MENU_CORE_MACHINE.addTo(
                new ItemDismantleTable(FinalTechMenus.MENU_FUNCTIONAL_MACHINE, FinalTechItems.ITEM_DISMANTLE_TABLE, FinalTechRecipes.RECIPE_TYPE_MATRIX_CRAFTING_TABLE, FinalTechRecipes.ITEM_DISMANTLE_TABLE).register(),
                new EquivalentExchangeTable(FinalTechMenus.MENU_FUNCTIONAL_MACHINE, FinalTechItems.EQUIVALENT_EXCHANGE_TABLE, FinalTechRecipes.RECIPE_TYPE_MATRIX_CRAFTING_TABLE, FinalTechRecipes.EQUIVALENT_EXCHANGE_TABLE).register(),
                new ItemSerializationConstructor(FinalTechMenus.MENU_FUNCTIONAL_MACHINE, FinalTechItems.ITEM_SERIALIZATION_CONSTRUCTOR, FinalTechRecipes.RECIPE_TYPE_MATRIX_CRAFTING_TABLE, FinalTechRecipes.ITEM_SERIALIZATION_CONSTRUCTOR).register(),
                new ItemDeserializeParser(FinalTechMenus.MENU_FUNCTIONAL_MACHINE, FinalTechItems.ITEM_DESERIALIZE_PARSER, FinalTechRecipes.RECIPE_TYPE_MATRIX_CRAFTING_TABLE, FinalTechRecipes.ITEM_DESERIALIZE_PARSER).register(),
                new CardOperationTable(FinalTechMenus.MENU_FUNCTIONAL_MACHINE, FinalTechItems.CARD_OPERATION_TABLE, FinalTechRecipes.RECIPE_TYPE_MATRIX_CRAFTING_TABLE, FinalTechRecipes.CARD_OPERATION_TABLE).register());
        // special machines
        FinalTechMenus.SUB_MENU_SPECIAL_MACHINE.addTo(
                new ItemFixer(FinalTechMenus.MENU_FUNCTIONAL_MACHINE, FinalTechItems.ITEM_FIXER, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.ITEM_FIXER).register(),
                new CobbleStoneFactory(FinalTechMenus.MENU_FUNCTIONAL_MACHINE, FinalTechItems.COBBLESTONE_FACTORY, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.COBBLESTONE_FACTORY).register(),
                new FuelCharger(FinalTechMenus.MENU_FUNCTIONAL_MACHINE, FinalTechItems.FUEL_CHARGER, FinalTechRecipes.RECIPE_TYPE_MATRIX_CRAFTING_TABLE, FinalTechRecipes.FUEL_CHARGER).register(),
                new FuelAccelerator(FinalTechMenus.MENU_FUNCTIONAL_MACHINE, FinalTechItems.FUEL_ACCELERATOR, FinalTechRecipes.RECIPE_TYPE_MATRIX_CRAFTING_TABLE, FinalTechRecipes.FUEL_ACCELERATOR).register(),
                new FuelOperator(FinalTechMenus.MENU_FUNCTIONAL_MACHINE, FinalTechItems.FUEL_OPERATOR, FinalTechRecipes.RECIPE_TYPE_MATRIX_CRAFTING_TABLE, FinalTechRecipes.FUEL_OPERATOR).register(),
                new OperationAccelerator(FinalTechMenus.MENU_FUNCTIONAL_MACHINE, FinalTechItems.OPERATION_ACCELERATOR, FinalTechRecipes.RECIPE_TYPE_MATRIX_CRAFTING_TABLE, FinalTechRecipes.OPERATION_ACCELERATOR).register(),
                new OperationAcceleratorInfinity(FinalTechMenus.MENU_FUNCTIONAL_MACHINE, FinalTechItems.OPERATION_ACCELERATOR_INFINITY, FinalTechRecipes.RECIPE_TYPE_MATRIX_CRAFTING_TABLE, FinalTechRecipes.OPERATION_ACCELERATOR_INFINITY).register());
        // tower
        FinalTechMenus.SUB_MENU_TOWER.addTo(
                new CureTower(FinalTechMenus.MENU_FUNCTIONAL_MACHINE, FinalTechItems.CURE_TOWER, FinalTechRecipes.RECIPE_TYPE_MATRIX_CRAFTING_TABLE, FinalTechRecipes.CURE_TOWER).register(),
                new PurifyLevelTower(FinalTechMenus.MENU_FUNCTIONAL_MACHINE, FinalTechItems.PURIFY_LEVEL_TOWER, FinalTechRecipes.RECIPE_TYPE_MATRIX_CRAFTING_TABLE, FinalTechRecipes.PURIFY_LEVEL_TOWER).register(),
                new PurifyTimeTower(FinalTechMenus.MENU_FUNCTIONAL_MACHINE, FinalTechItems.PURIFY_TIME_TOWER, FinalTechRecipes.RECIPE_TYPE_MATRIX_CRAFTING_TABLE, FinalTechRecipes.PURIFY_TIME_TOWER).register());

        /* productive machine */
        // manual machine
        FinalTechMenus.SUB_MENU_MANUAL_MACHINE.addTo(
                new ManualCraftingTable(FinalTechMenus.MENU_PRODUCTIVE_MACHINE, FinalTechItems.MANUAL_CRAFTING_TABLE, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.MANUAL_CRAFTING_TABLE).register(),
                new ManualEnhancedCraftingTable(FinalTechMenus.MENU_PRODUCTIVE_MACHINE, FinalTechItems.MANUAL_ENHANCED_CRAFTING_TABLE, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.MANUAL_ENHANCED_CRAFTING_TABLE).register(),
                new ManualGrindStone(FinalTechMenus.MENU_PRODUCTIVE_MACHINE, FinalTechItems.MANUAL_GRIND_STONE, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.MANUAL_GRIND_STONE).register(),
                new ManualArmorForge(FinalTechMenus.MENU_PRODUCTIVE_MACHINE, FinalTechItems.MANUAL_ARMOR_FORGE, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.MANUAL_ARMOR_FORGE).register(),
                new ManualOreCrusher(FinalTechMenus.MENU_PRODUCTIVE_MACHINE, FinalTechItems.MANUAL_ORE_CRUSHER, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.MANUAL_ORE_CRUSHER).register(),
                new ManualCompressor(FinalTechMenus.MENU_PRODUCTIVE_MACHINE, FinalTechItems.MANUAL_COMPRESSOR, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.MANUAL_COMPRESSOR).register(),
                new ManualSmeltery(FinalTechMenus.MENU_PRODUCTIVE_MACHINE, FinalTechItems.MANUAL_SMELTERY, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.MANUAL_SMELTERY).register(),
                new ManualPressureChamber(FinalTechMenus.MENU_PRODUCTIVE_MACHINE, FinalTechItems.MANUAL_PRESSURE_CHAMBER, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.MANUAL_PRESSURE_CHAMBER).register(),
                new ManualMagicWorkbench(FinalTechMenus.MENU_PRODUCTIVE_MACHINE, FinalTechItems.MANUAL_MAGIC_WORKBENCH, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.MANUAL_MAGIC_WORKBENCH).register(),
                new ManualOreWasher(FinalTechMenus.MENU_PRODUCTIVE_MACHINE, FinalTechItems.MANUAL_ORE_WASHER, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.MANUAL_ORE_WASHER).register(),
                new ManualComposter(FinalTechMenus.MENU_PRODUCTIVE_MACHINE, FinalTechItems.MANUAL_COMPOSTER, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.MANUAL_COMPOSTER).register(),
                new ManualGoldPan(FinalTechMenus.MENU_PRODUCTIVE_MACHINE, FinalTechItems.MANUAL_GOLD_PAN, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.MANUAL_GOLD_PAN).register(),
                new ManualCrucible(FinalTechMenus.MENU_PRODUCTIVE_MACHINE, FinalTechItems.MANUAL_CRUCIBLE, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.MANUAL_CRUCIBLE).register(),
                new ManualJuicer(FinalTechMenus.MENU_PRODUCTIVE_MACHINE, FinalTechItems.MANUAL_JUICER, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.MANUAL_JUICER).register(),
                new ManualAncientAltar(FinalTechMenus.MENU_PRODUCTIVE_MACHINE, FinalTechItems.MANUAL_ANCIENT_ALTAR, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.MANUAL_ANCIENT_ALTAR).register(),
                new ManualHeatedPressureChamber(FinalTechMenus.MENU_PRODUCTIVE_MACHINE, FinalTechItems.MANUAL_HEATED_PRESSURE_CHAMBER, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.MANUAL_HEATED_PRESSURE_CHAMBER).register());
        // basic machines
        FinalTechMenus.SUB_MENU_BASIC_MACHINE.addTo(
                new BasicLogicFactory(FinalTechMenus.MENU_PRODUCTIVE_MACHINE, FinalTechItems.BASIC_LOGIC_FACTORY, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.BASIC_LOGIC_FACTORY).register());
        // advanced machine
        FinalTechMenus.SUB_MENU_ADVANCED_MACHINE.addTo(
                new AdvancedComposter(FinalTechMenus.MENU_PRODUCTIVE_MACHINE, FinalTechItems.ADVANCED_COMPOSTER, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.ADVANCED_COMPOSTER).register(),
                new AdvancedJuicer(FinalTechMenus.MENU_PRODUCTIVE_MACHINE, FinalTechItems.ADVANCED_JUICER, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.ADVANCED_JUICER).register(),
                new AdvancedFurnace(FinalTechMenus.MENU_PRODUCTIVE_MACHINE, FinalTechItems.ADVANCED_FURNACE, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.ADVANCED_FURNACE).register(),
                new AdvancedGoldPan(FinalTechMenus.MENU_PRODUCTIVE_MACHINE, FinalTechItems.ADVANCED_GOLD_PAN, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.ADVANCED_GOLD_PAN).register(),
                new AdvancedDustWasher(FinalTechMenus.MENU_PRODUCTIVE_MACHINE, FinalTechItems.ADVANCED_DUST_WASHER, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.ADVANCED_DUST_WASHER).register(),
                new AdvancedIngotFactory(FinalTechMenus.MENU_PRODUCTIVE_MACHINE, FinalTechItems.ADVANCED_INGOT_FACTORY, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.ADVANCED_INGOT_FACTORY).register(),
                new AdvancedCrucible(FinalTechMenus.MENU_PRODUCTIVE_MACHINE, FinalTechItems.ADVANCED_CRUCIBLE, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.ADVANCED_CRUCIBLE).register(),
                new AdvancedOreGrinder(FinalTechMenus.MENU_PRODUCTIVE_MACHINE, FinalTechItems.ADVANCED_ORE_GRINDER, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.ADVANCED_ORE_GRINDER).register(),
                new AdvancedHeatedPressureChamber(FinalTechMenus.MENU_PRODUCTIVE_MACHINE, FinalTechItems.ADVANCED_HEATED_PRESSURE_CHAMBER, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.ADVANCED_HEATED_PRESSURE_CHAMBER).register(),
                new AdvancedIngotPulverizer(FinalTechMenus.MENU_PRODUCTIVE_MACHINE, FinalTechItems.ADVANCED_INGOT_PULVERIZER, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.ADVANCED_INGOT_PULVERIZER).register(),
                new AdvancedAutoDrier(FinalTechMenus.MENU_PRODUCTIVE_MACHINE, FinalTechItems.ADVANCED_AUTO_DRIER, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.ADVANCED_AUTO_DRIER).register(),
                new AdvancedPress(FinalTechMenus.MENU_PRODUCTIVE_MACHINE, FinalTechItems.ADVANCED_PRESS, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.ADVANCED_PRESS).register(),
                new AdvancedFoodFactory(FinalTechMenus.MENU_PRODUCTIVE_MACHINE, FinalTechItems.ADVANCED_FOOD_FACTORY, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.ADVANCED_FOOD_FACTORY).register(),
                new AdvancedFreezer(FinalTechMenus.MENU_PRODUCTIVE_MACHINE, FinalTechItems.ADVANCED_FREEZER, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.ADVANCED_FREEZER).register(),
                new AdvancedCarbonPress(FinalTechMenus.MENU_PRODUCTIVE_MACHINE, FinalTechItems.ADVANCED_CARBON_PRESS, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.ADVANCED_CARBON_PRESS).register(),
                new AdvancedSmeltery(FinalTechMenus.MENU_PRODUCTIVE_MACHINE, FinalTechItems.ADVANCED_SMELTERY, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.ADVANCED_SMELTERY).register());
        // conversion
        FinalTechMenus.SUB_MENU_CONVERSION.addTo(
                new GravelConversion(FinalTechMenus.MENU_PRODUCTIVE_MACHINE, FinalTechItems.GRAVEL_CONVERSION, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.GRAVEL_CONVERSION).register(),
                new SoulSandConversion(FinalTechMenus.MENU_PRODUCTIVE_MACHINE, FinalTechItems.SOUL_SAND_CONVERSION, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.SOUL_SAND_CONVERSION).register(),
                new LogicToDigitalConversion(FinalTechMenus.MENU_PRODUCTIVE_MACHINE, FinalTechItems.LOGIC_TO_DIGITAL_CONVERSION, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.LOGIC_TO_DIGITAL_CONVERSION).register());
        // extraction
        FinalTechMenus.SUB_MENU_EXTRACTION.addTo(
                new DigitalExtraction(FinalTechMenus.MENU_PRODUCTIVE_MACHINE, FinalTechItems.DIGITAL_EXTRACTION, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.DIGITAL_EXTRACTION).register());
        // generator
        FinalTechMenus.SUB_MENU_GENERATOR.addTo(
                new LiquidCardGenerator(FinalTechMenus.MENU_PRODUCTIVE_MACHINE, FinalTechItems.LIQUID_CARD_GENERATOR, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.LIQUID_CARD_GENERATOR).register(),
                new LogicGenerator(FinalTechMenus.MENU_PRODUCTIVE_MACHINE, FinalTechItems.LOGIC_GENERATOR, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.LOGIC_GENERATOR).register(),
                new DigitalGenerator(FinalTechMenus.MENU_PRODUCTIVE_MACHINE, FinalTechItems.DIGITAL_GENERATOR, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.DIGITAL_GENERATOR).register());

        /* final stage item */
        FinalTechMenus.SUB_MENU_FINAL_ITEM.addTo(
                new EntropySeed(FinalTechMenus.MENU_DISC, FinalTechItems.ENTROPY_SEED, FinalTechRecipes.RECIPE_TYPE_MATRIX_CRAFTING_TABLE, FinalTechRecipes.ENTROPY_SEED).register(),
                new InfinityMachineChargeCard(FinalTechMenus.MENU_DISC, FinalTechItems.MACHINE_CHARGE_CARD_INFINITY, FinalTechRecipes.RECIPE_TYPE_MATRIX_CRAFTING_TABLE, FinalTechRecipes.MACHINE_CHARGE_CARD_INFINITY).register(),
                new InfinityMachineAccelerateCard(FinalTechMenus.MENU_DISC, FinalTechItems.MACHINE_ACCELERATE_CARD_INFINITY, FinalTechRecipes.RECIPE_TYPE_MATRIX_CRAFTING_TABLE, FinalTechRecipes.MACHINE_ACCELERATE_CARD_INFINITY).register(),
                new MatrixMachineActivateCard(FinalTechMenus.MENU_DISC, FinalTechItems.MACHINE_ACTIVATE_CARD_L4, FinalTechRecipes.RECIPE_TYPE_MATRIX_CRAFTING_TABLE, FinalTechRecipes.MACHINE_ACTIVATE_CARD_L4).register());
        FinalTechMenus.SUB_MENU_FINAL_ITEM.addTo(
                new AdvancedAutoCraft(FinalTechMenus.MENU_DISC, FinalTechItems.ADVANCED_AUTO_CRAFT, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.ADVANCED_AUTO_CRAFT).register(),
                new MultiFrameMachine(FinalTechMenus.MENU_DISC, FinalTechItems.MULTI_FRAME_MACHINE, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.MULTI_FRAME_MACHINE).register(),
                new MatrixItemDismantleTable(FinalTechMenus.MENU_DISC, FinalTechItems.MATRIX_ITEM_DISMANTLE_TABLE, FinalTechRecipes.RECIPE_TYPE_MATRIX_CRAFTING_TABLE, FinalTechRecipes.MATRIX_ITEM_DISMANTLE_TABLE).register(),
                new MatrixExpandedCapacitor(FinalTechMenus.MENU_DISC, FinalTechItems.MATRIX_EXPANDED_CAPACITOR, FinalTechRecipes.RECIPE_TYPE_MATRIX_CRAFTING_TABLE, FinalTechRecipes.MATRIX_EXPANDED_CAPACITOR).register(),
                new MatrixItemDeserializeParser(FinalTechMenus.MENU_DISC, FinalTechItems.MATRIX_ITEM_DESERIALIZE_PARSER, FinalTechRecipes.RECIPE_TYPE_MATRIX_CRAFTING_TABLE, FinalTechRecipes.MATRIX_ITEM_DESERIALIZE_PARSER).register(),
                new EntropyConstructor(FinalTechMenus.MENU_DISC, FinalTechItems.ENTROPY_CONSTRUCTOR, FinalTechRecipes.RECIPE_TYPE_MATRIX_CRAFTING_TABLE, FinalTechRecipes.ENTROPY_CONSTRUCTOR).register(),
                new MatrixGenerator(FinalTechMenus.MENU_DISC, FinalTechItems.MATRIX_GENERATOR, FinalTechRecipes.RECIPE_TYPE_MATRIX_CRAFTING_TABLE, FinalTechRecipes.MATRIX_GENERATOR).register(),
                new MatrixAccelerator(FinalTechMenus.MENU_DISC, FinalTechItems.MATRIX_ACCELERATOR, FinalTechRecipes.RECIPE_TYPE_MATRIX_CRAFTING_TABLE, FinalTechRecipes.MATRIX_ACCELERATOR).register(),
                new MatrixItemSerializationConstructor(FinalTechMenus.MENU_DISC, FinalTechItems.MATRIX_ITEM_SERIALIZATION_CONSTRUCTOR, FinalTechRecipes.RECIPE_TYPE_MATRIX_CRAFTING_TABLE, FinalTechRecipes.MATRIX_ITEM_SERIALIZATION_CONSTRUCTOR).register(),
                new MatrixReactor(FinalTechMenus.MENU_DISC, FinalTechItems.MATRIX_REACTOR, FinalTechRecipes.RECIPE_TYPE_MATRIX_CRAFTING_TABLE, FinalTechRecipes.MATRIX_REACTOR).register());
        FinalTechMenus.SUB_MENU_DEPRECATED.addTo(
                new BasicChargeIncreaseCapacitor(FinalTechMenus.MENU_DISC, FinalTechItems.BASIC_CHARGE_INCREASE_CAPACITOR, RecipeType.NULL, new ItemStack[0]).register(),
                new BasicConsumeReduceCapacitor(FinalTechMenus.MENU_DISC, FinalTechItems.BASIC_CONSUME_REDUCE_CAPACITOR, RecipeType.NULL, new ItemStack[0]).register(),
                new AdvancedChargeIncreaseCapacitor(FinalTechMenus.MENU_DISC, FinalTechItems.ADVANCED_CHARGE_INCREASE_CAPACITOR, RecipeType.NULL, new ItemStack[0]).register(),
                new AdvancedConsumeReduceCapacitor(FinalTechMenus.MENU_DISC, FinalTechItems.ADVANCED_CONSUME_REDUCE_CAPACITOR, RecipeType.NULL, new ItemStack[0]).register(),
                new BasicCobbleFactory(FinalTechMenus.MENU_DISC, FinalTechItems.BASIC_COBBLE_FACTORY, RecipeType.NULL, new ItemStack[0]).register(),
                new BasicStoneFactory(FinalTechMenus.MENU_DISC, FinalTechItems.BASIC_STONE_FACTORY, RecipeType.NULL, new ItemStack[0]).register(),
                new BasicDustFactory(FinalTechMenus.MENU_DISC, FinalTechItems.BASIC_DUST_FACTORY, RecipeType.NULL, new ItemStack[0]).register(),
                new DustConversion(FinalTechMenus.MENU_DISC, FinalTechItems.DUST_CONVERSION, RecipeType.NULL, new ItemStack[0]).register(),
                new AdvancedDustFactory(FinalTechMenus.MENU_PRODUCTIVE_MACHINE, FinalTechItems.ADVANCED_DUST_FACTORY, RecipeType.NULL, new ItemStack[0]).register(),
                new ConcreteConversion(FinalTechMenus.MENU_DISC, FinalTechItems.CONCRETE_CONVERSION, RecipeType.NULL, new ItemStack[0]).register(),
                new WoolConversion(FinalTechMenus.MENU_DISC, FinalTechItems.WOOL_CONVERSION, RecipeType.NULL, new ItemStack[0]).register(),
                new OreExtraction(FinalTechMenus.MENU_DISC, FinalTechItems.ORE_EXTRACTION, RecipeType.NULL, new ItemStack[0]).register(),
                new PlankGenerator(FinalTechMenus.MENU_DISC, FinalTechItems.PLANK_GENERATOR, RecipeType.NULL, new ItemStack[0]).register(),
                new SandGenerator(FinalTechMenus.MENU_DISC, FinalTechItems.SAND_GENERATOR, RecipeType.NULL, new ItemStack[0]).register(),
                new StoneGenerator(FinalTechMenus.MENU_DISC, FinalTechItems.STONE_GENERATOR, RecipeType.NULL, new ItemStack[0]).register(),
                new RawStoneGenerator(FinalTechMenus.MENU_DISC, FinalTechItems.RAW_STONE_GENERATOR, RecipeType.NULL, new ItemStack[0]).register(),
                new NetherStoneGenerator(FinalTechMenus.MENU_DISC, FinalTechItems.NETHER_STONE_GENERATOR, RecipeType.NULL, new ItemStack[0]).register());

        for(SlimefunItem slimefunItem : FinalTechMenus.SUB_MENU_DEPRECATED.getSlimefunItems()) {
            try {
                Class<SlimefunItem> clazz = SlimefunItem.class;
                Field declaredField = clazz.getDeclaredField("blockTicker");
                declaredField.setAccessible(true);
                declaredField.set(slimefunItem, null);
                declaredField.setAccessible(false);

                Field ticking = clazz.getDeclaredField("ticking");
                ticking.setAccessible(true);
                ticking.set(slimefunItem, false);
                ticking.setAccessible(false);
            } catch (NoSuchFieldException | IllegalAccessException e) {
                e.printStackTrace();
            }
        }

        /* Menus */
        // item
        FinalTechMenus.MAIN_ITEM_GROUP.addTo(FinalTechMenus.MAIN_MENU_ITEM,
                FinalTechMenus.SUB_MENU_MATERIAL,
                FinalTechMenus.SUB_MENU_LOGIC_ITEM,
                FinalTechMenus.SUB_MENU_CONSUMABLE,
                FinalTechMenus.SUB_MENU_TOOL,
                FinalTechMenus.SUB_MENU_WEAPON);
        FinalTechMenus.MAIN_MENU_ITEM.addFrom(
                FinalTechMenus.SUB_MENU_MATERIAL,
                FinalTechMenus.SUB_MENU_LOGIC_ITEM,
                FinalTechMenus.SUB_MENU_CONSUMABLE,
                FinalTechMenus.SUB_MENU_TOOL,
                FinalTechMenus.SUB_MENU_WEAPON);
        // electricity system
        FinalTechMenus.MAIN_ITEM_GROUP.addTo(FinalTechMenus.MAIN_MENU_ELECTRICITY_SYSTEM,
                FinalTechMenus.SUB_MENU_ELECTRIC_GENERATOR,
                FinalTechMenus.SUB_MENU_ELECTRIC_STORAGE,
                FinalTechMenus.SUB_MENU_ELECTRIC_TRANSMISSION,
                FinalTechMenus.SUB_MENU_ELECTRIC_ACCELERATOR);
        FinalTechMenus.MAIN_MENU_ELECTRICITY_SYSTEM.addFrom(
                FinalTechMenus.SUB_MENU_ELECTRIC_GENERATOR,
                FinalTechMenus.SUB_MENU_ELECTRIC_STORAGE,
                FinalTechMenus.SUB_MENU_ELECTRIC_TRANSMISSION,
                FinalTechMenus.SUB_MENU_ELECTRIC_ACCELERATOR);
        // cargo system
        FinalTechMenus.MAIN_ITEM_GROUP.addTo(FinalTechMenus.MAIN_MENU_CARGO_SYSTEM,
                FinalTechMenus.SUB_MENU_STORAGE_UNIT,
                FinalTechMenus.SUB_MENU_ADVANCED_STORAGE,
                FinalTechMenus.SUB_MENU_ACCESSOR,
                FinalTechMenus.SUB_MENU_LOGIC,
                FinalTechMenus.SUB_MENU_CARGO);
        FinalTechMenus.MAIN_MENU_CARGO_SYSTEM.addFrom(
                FinalTechMenus.SUB_MENU_STORAGE_UNIT,
                FinalTechMenus.SUB_MENU_ADVANCED_STORAGE,
                FinalTechMenus.SUB_MENU_ACCESSOR,
                FinalTechMenus.SUB_MENU_LOGIC,
                FinalTechMenus.SUB_MENU_CARGO);
        // functional machine
        FinalTechMenus.MAIN_ITEM_GROUP.addTo(FinalTechMenus.MAIN_MENU_FUNCTIONAL_MACHINE,
                FinalTechMenus.SUB_MENU_CORE_MACHINE,
                FinalTechMenus.SUB_MENU_SPECIAL_MACHINE,
                FinalTechMenus.SUB_MENU_TOWER);
        FinalTechMenus.MAIN_MENU_FUNCTIONAL_MACHINE.addFrom(
                FinalTechMenus.SUB_MENU_CORE_MACHINE,
                FinalTechMenus.SUB_MENU_SPECIAL_MACHINE,
                FinalTechMenus.SUB_MENU_TOWER);
        // productive machine
        FinalTechMenus.MAIN_ITEM_GROUP.addTo(FinalTechMenus.MAIN_MENU_PRODUCTIVE_MACHINE,
                FinalTechMenus.SUB_MENU_MANUAL_MACHINE,
                FinalTechMenus.SUB_MENU_BASIC_MACHINE,
                FinalTechMenus.SUB_MENU_ADVANCED_MACHINE,
                FinalTechMenus.SUB_MENU_CONVERSION,
                FinalTechMenus.SUB_MENU_EXTRACTION,
                FinalTechMenus.SUB_MENU_GENERATOR);
        FinalTechMenus.MAIN_MENU_PRODUCTIVE_MACHINE.addFrom(
                FinalTechMenus.SUB_MENU_MANUAL_MACHINE,
                FinalTechMenus.SUB_MENU_BASIC_MACHINE,
                FinalTechMenus.SUB_MENU_ADVANCED_MACHINE,
                FinalTechMenus.SUB_MENU_CONVERSION,
                FinalTechMenus.SUB_MENU_EXTRACTION,
                FinalTechMenus.SUB_MENU_GENERATOR);
        // disc
        FinalTechMenus.MAIN_ITEM_GROUP.addTo(FinalTechMenus.MAIN_MENU_DISC,
                FinalTechMenus.SUB_MENU_FINAL_ITEM,
                FinalTechMenus.SUB_MENU_TROPHY,
                FinalTechMenus.SUB_MENU_DEPRECATED);
        FinalTechMenus.MAIN_MENU_DISC.addFrom(
                FinalTechMenus.SUB_MENU_FINAL_ITEM,
                FinalTechMenus.SUB_MENU_TROPHY,
                FinalTechMenus.SUB_MENU_DEPRECATED);

        FinalTechMenus.MAIN_ITEM_GROUP.setTier(0);
        FinalTechMenus.MAIN_ITEM_GROUP.register(finalTech);

        /* Researches */
        ResearchUtil.setResearches(FinalTech.getLanguageManager(), "LIQUID_CARD", 1, false,
                FinalTechItems.WATER_CARD,
                FinalTechItems.LAVA_CARD,
                FinalTechItems.MILK_CARD,
                FinalTechItems.LIQUID_CARD_GENERATOR);
        ResearchUtil.setResearches(FinalTech.getLanguageManager(), "ORDER_DUST", Slimefun.getInstalledAddons().size(), true,
                FinalTechItems.ORDERED_DUST,
                FinalTechItems.UNORDERED_DUST,
                FinalTechItems.ORDERED_DUST_FACTORY_DIRT,
                FinalTechItems.ORDERED_DUST_FACTORY_STONE,
                FinalTechItems.ORDERED_DUST_GENERATOR);
        ResearchUtil.setResearches(FinalTech.getLanguageManager(), "BUG", (int)Math.pow(Slimefun.getRegistry().getEnabledSlimefunItems().size(), 0.5), true,
                FinalTechItems.BUG,
                FinalTechItems.EQUIVALENT_EXCHANGE_TABLE);
        ResearchUtil.setResearches(FinalTech.getLanguageManager(), "ENTROPY", (int)Math.pow(Slimefun.getRegistry().getResearches().size(), 0.5), true,
                FinalTechItems.ENTROPY,
                FinalTechItems.ENTROPY_CONSTRUCTOR,
                FinalTechItems.ENTROPY_SEED);
        ResearchUtil.setResearches(FinalTech.getLanguageManager(), "BOX", Math.abs((int)FinalTech.getSeed()) % 20, true,
                FinalTechItems.BOX,
                FinalTechItems.SHINE);
        ResearchUtil.setResearches(FinalTech.getLanguageManager(), "ANNULAR", (int)Math.pow(ConstantTableUtil.ITEM_COPY_CARD_AMOUNT, 0.25), true,
                FinalTechItems.COPY_CARD,
                FinalTechItems.ANNULAR,
                FinalTechItems.QUANTITY_MODULE,
                FinalTechItems.QUANTITY_MODULE_INFINITY,
                FinalTechItems.ITEM_SERIALIZATION_CONSTRUCTOR,
                FinalTechItems.ITEM_DESERIALIZE_PARSER);
        ResearchUtil.setResearches(FinalTech.getLanguageManager(), "PHONY", (int)Math.pow(ConstantTableUtil.ITEM_SINGULARITY_AMOUNT * ConstantTableUtil.ITEM_SPIROCHETE_AMOUNT, 0.6), true,
                FinalTechItems.SINGULARITY,
                FinalTechItems.SPIROCHETE,
                FinalTechItems.SHELL,
                FinalTechItems.PHONY,
                FinalTechItems.JUSTIFIABILITY,
                FinalTechItems.EQUIVALENT_CONCEPT,
                FinalTechItems.ENTROPY_SEED,
                FinalTechItems.MACHINE_CHARGE_CARD_INFINITY,
                FinalTechItems.MACHINE_ACCELERATE_CARD_INFINITY,
                FinalTechItems.MACHINE_ACTIVATE_CARD_L4,
                FinalTechItems.ADVANCED_AUTO_CRAFT,
                FinalTechItems.MATRIX_ITEM_DISMANTLE_TABLE,
                FinalTechItems.MATRIX_EXPANDED_CAPACITOR,
                FinalTechItems.MATRIX_ITEM_SERIALIZATION_CONSTRUCTOR,
                FinalTechItems.MATRIX_GENERATOR,
                FinalTechItems.MATRIX_ACCELERATOR,
                FinalTechItems.MATRIX_ITEM_DESERIALIZE_PARSER,
                FinalTechItems.MATRIX_REACTOR);
        ResearchUtil.setResearches(FinalTech.getLanguageManager(), "LOGIC", 1, false,
                FinalTechItems.LOGIC_FALSE,
                FinalTechItems.LOGIC_TRUE,
                FinalTechItems.DIGITAL_ZERO,
                FinalTechItems.DIGITAL_ONE,
                FinalTechItems.DIGITAL_TWO,
                FinalTechItems.DIGITAL_THREE,
                FinalTechItems.DIGITAL_FOUR,
                FinalTechItems.DIGITAL_FIVE,
                FinalTechItems.DIGITAL_SIX,
                FinalTechItems.DIGITAL_SEVEN,
                FinalTechItems.DIGITAL_EIGHT,
                FinalTechItems.DIGITAL_NINE,
                FinalTechItems.DIGITAL_TEN,
                FinalTechItems.DIGITAL_ELEVEN,
                FinalTechItems.DIGITAL_TWELVE,
                FinalTechItems.DIGITAL_THIRTEEN,
                FinalTechItems.DIGITAL_FOURTEEN,
                FinalTechItems.DIGITAL_FIFTEEN,
                FinalTechItems.LOGIC_COMPARATOR_NOTNULL,
                FinalTechItems.LOGIC_COMPARATOR_AMOUNT,
                FinalTechItems.LOGIC_COMPARATOR_SIMILAR,
                FinalTechItems.LOGIC_COMPARATOR_EQUAL,
                FinalTechItems.LOGIC_CRAFTER,
                FinalTechItems.DIGIT_ADDER,
                FinalTechItems.LOGIC_GENERATOR,
                FinalTechItems.DIGITAL_GENERATOR,
                FinalTechItems.LOGIC_TO_DIGITAL_CONVERSION);
        ResearchUtil.setResearches(FinalTech.getLanguageManager(), "MACHINE_CARD", 10, false,
                FinalTechItems.MACHINE_CHARGE_CARD_L1,
                FinalTechItems.MACHINE_CHARGE_CARD_L2,
                FinalTechItems.MACHINE_CHARGE_CARD_L3,
                FinalTechItems.MACHINE_ACCELERATE_CARD_L1,
                FinalTechItems.MACHINE_ACCELERATE_CARD_L2,
                FinalTechItems.MACHINE_ACCELERATE_CARD_L3,
                FinalTechItems.MACHINE_ACTIVATE_CARD_L1,
                FinalTechItems.MACHINE_ACTIVATE_CARD_L2,
                FinalTechItems.MACHINE_ACTIVATE_CARD_L3);
        ResearchUtil.setResearches(FinalTech.getLanguageManager(), "CONSUMABLE_ITEM", 10, false,
                FinalTechItems.MAGIC_HYPNOTIC,
                FinalTechItems.RESEARCH_UNLOCK_TICKET);
        ResearchUtil.setResearches(FinalTech.getLanguageManager(), "SIMPLE_TOOL", 10, false,
                FinalTechItems.STAFF_ELEMENTAL_LINE,
                FinalTechItems.POTION_EFFECT_COMPRESSOR,
                FinalTechItems.POTION_EFFECT_DILATOR,
                FinalTechItems.POTION_EFFECT_PURIFIER);
        ResearchUtil.setResearches(FinalTech.getLanguageManager(), "MACHINE_TOOL", 10, false,
                FinalTechItems.MENU_VIEWER,
                FinalTechItems.LOCATION_RECORDER,
                FinalTechItems.MACHINE_CONFIGURATOR,
                FinalTechItems.PORTABLE_ENERGY_STORAGE);

        ResearchUtil.setResearchBySlimefunItems(FinalTechItems.SUPER_SHOVEL, SlimefunItems.SOULBOUND_SHOVEL);
        ResearchUtil.setResearchBySlimefunItems(FinalTechItems.ULTIMATE_SHOVEL, SlimefunItems.SOULBOUND_SHOVEL);
        ResearchUtil.setResearchBySlimefunItems(FinalTechItems.SUPER_PICKAXE, SlimefunItems.SOULBOUND_PICKAXE);
        ResearchUtil.setResearchBySlimefunItems(FinalTechItems.ULTIMATE_PICKAXE, SlimefunItems.SOULBOUND_PICKAXE);
        ResearchUtil.setResearchBySlimefunItems(FinalTechItems.SUPER_AXE, SlimefunItems.SOULBOUND_AXE);
        ResearchUtil.setResearchBySlimefunItems(FinalTechItems.ULTIMATE_AXE, SlimefunItems.SOULBOUND_AXE);
        ResearchUtil.setResearchBySlimefunItems(FinalTechItems.SUPER_HOE, SlimefunItems.SOULBOUND_HOE);
        ResearchUtil.setResearchBySlimefunItems(FinalTechItems.ULTIMATE_HOE, SlimefunItems.SOULBOUND_HOE);

        ResearchUtil.setResearchBySlimefunItems(FinalTechItems.BASIC_GENERATOR, SlimefunItems.SOLAR_GENERATOR);
        ResearchUtil.setResearchBySlimefunItems(FinalTechItems.ADVANCED_GENERATOR, SlimefunItems.SOLAR_GENERATOR_2);
        ResearchUtil.setResearchBySlimefunItems(FinalTechItems.CARBONADO_GENERATOR, SlimefunItems.SOLAR_GENERATOR_3);
        ResearchUtil.setResearchBySlimefunItems(FinalTechItems.ENERGIZED_GENERATOR, SlimefunItems.SOLAR_GENERATOR_4);
        ResearchUtil.setResearchBySlimefunItems(FinalTechItems.ENERGIZED_STACK_GENERATOR, SlimefunItems.SOLAR_GENERATOR_4);
        ResearchUtil.setResearchBySlimefunItems(FinalTechItems.OVERLOADED_GENERATOR, SlimefunItems.SOLAR_GENERATOR_4);
        ResearchUtil.setResearchBySlimefunItems(FinalTechItems.ENERGIZED_CHARGE_BASE, SlimefunItems.SOLAR_GENERATOR_4);
        ResearchUtil.setResearchBySlimefunItems(FinalTechItems.OVERLOADED_CHARGE_BASE, SlimefunItems.SOLAR_GENERATOR_4);

        ResearchUtil.setResearchBySlimefunItems(FinalTechItems.BASIC_CHARGE_INCREASE_CAPACITOR, SlimefunItems.SMALL_CAPACITOR);
        ResearchUtil.setResearchBySlimefunItems(FinalTechItems.BASIC_CONSUME_REDUCE_CAPACITOR, SlimefunItems.SMALL_CAPACITOR);
        ResearchUtil.setResearchBySlimefunItems(FinalTechItems.SMALL_EXPANDED_CAPACITOR, SlimefunItems.SMALL_CAPACITOR);
        ResearchUtil.setResearchBySlimefunItems(FinalTechItems.MEDIUM_EXPANDED_CAPACITOR, SlimefunItems.MEDIUM_CAPACITOR);
        ResearchUtil.setResearchBySlimefunItems(FinalTechItems.BIG_EXPANDED_CAPACITOR, SlimefunItems.BIG_CAPACITOR);
        ResearchUtil.setResearchBySlimefunItems(FinalTechItems.LARGE_EXPANDED_CAPACITOR, SlimefunItems.LARGE_CAPACITOR);
        ResearchUtil.setResearchBySlimefunItems(FinalTechItems.CARBONADO_EXPANDED_CAPACITOR, SlimefunItems.CARBONADO_EDGED_CAPACITOR);
        ResearchUtil.setResearchBySlimefunItems(FinalTechItems.ENERGIZED_EXPANDED_CAPACITOR, SlimefunItems.ENERGIZED_CAPACITOR);
        ResearchUtil.setResearchBySlimefunItems(FinalTechItems.ENERGIZED_STACK_EXPANDED_CAPACITOR, SlimefunItems.ENERGIZED_CAPACITOR);
        ResearchUtil.setResearchBySlimefunItems(FinalTechItems.OVERLOADED_EXPANDED_CAPACITOR, SlimefunItems.ENERGIZED_CAPACITOR);
        ResearchUtil.setResearchBySlimefunItems(FinalTechItems.ADVANCED_CHARGE_INCREASE_CAPACITOR, SlimefunItems.ENERGIZED_CAPACITOR);
        ResearchUtil.setResearchBySlimefunItems(FinalTechItems.ADVANCED_CONSUME_REDUCE_CAPACITOR, SlimefunItems.ENERGIZED_CAPACITOR);

        ResearchUtil.setResearchBySlimefunItems(FinalTechItems.NORMAL_ELECTRICITY_SHOOT_PILE, SlimefunItems.ENERGY_REGULATOR);
        ResearchUtil.setResearchBySlimefunItems(FinalTechItems.ENERGIZED_ELECTRICITY_SHOOT_PILE, SlimefunItems.ENERGY_REGULATOR);
        ResearchUtil.setResearchBySlimefunItems(FinalTechItems.OVERLOADED_ELECTRICITY_SHOOT_PILE, SlimefunItems.ENERGY_REGULATOR);

        ResearchUtil.setResearchBySlimefunItems(FinalTechItems.VARIABLE_WIRE_RESISTANCE, SlimefunItems.ENERGY_CONNECTOR);
        ResearchUtil.setResearchBySlimefunItems(FinalTechItems.VARIABLE_WIRE_CAPACITOR, SlimefunItems.ENERGY_CONNECTOR);

        ResearchUtil.setResearchBySlimefunItems(FinalTechItems.ENERGIZED_ACCELERATOR, SlimefunItems.NETHER_STAR_REACTOR);
        ResearchUtil.setResearchBySlimefunItems(FinalTechItems.OVERLOADED_ACCELERATOR, SlimefunItems.NETHER_STAR_REACTOR);

        ResearchUtil.setResearchBySlimefunItems(FinalTechItems.NORMAL_STORAGE_UNIT, SlimefunItems.BACKPACK_SMALL);
        ResearchUtil.setResearchBySlimefunItems(FinalTechItems.DIVIDED_STORAGE_UNIT, SlimefunItems.WOVEN_BACKPACK);
        ResearchUtil.setResearchBySlimefunItems(FinalTechItems.LIMITED_STORAGE_UNIT, SlimefunItems.WOVEN_BACKPACK);
        ResearchUtil.setResearchBySlimefunItems(FinalTechItems.STACK_STORAGE_UNIT, SlimefunItems.WOVEN_BACKPACK);
        ResearchUtil.setResearchBySlimefunItems(FinalTechItems.DIVIDED_LIMITED_STORAGE_UNIT, SlimefunItems.GILDED_BACKPACK);
        ResearchUtil.setResearchBySlimefunItems(FinalTechItems.DIVIDED_STACK_STORAGE_UNIT, SlimefunItems.GILDED_BACKPACK);
        ResearchUtil.setResearchBySlimefunItems(FinalTechItems.LIMITED_STACK_STORAGE_UNIT, SlimefunItems.GILDED_BACKPACK);
        ResearchUtil.setResearchBySlimefunItems(FinalTechItems.RANDOM_INPUT_STORAGE_UNIT, SlimefunItems.RADIANT_BACKPACK);
        ResearchUtil.setResearchBySlimefunItems(FinalTechItems.RANDOM_OUTPUT_STORAGE_UNIT, SlimefunItems.RADIANT_BACKPACK);
        ResearchUtil.setResearchBySlimefunItems(FinalTechItems.RANDOM_ACCESS_STORAGE_UNIT, SlimefunItems.RADIANT_BACKPACK);
        ResearchUtil.setResearchBySlimefunItems(FinalTechItems.DISTRIBUTE_LEFT_STORAGE_UNIT, SlimefunItems.ANDROID_INTERFACE_FUEL);
        ResearchUtil.setResearchBySlimefunItems(FinalTechItems.DISTRIBUTE_RIGHT_STORAGE_UNIT, SlimefunItems.ANDROID_INTERFACE_ITEMS);

        ResearchUtil.setResearchBySlimefunItems(FinalTechItems.STORAGE_INTERACT_PORT, SlimefunItems.TRASH_CAN);
        ResearchUtil.setResearchBySlimefunItems(FinalTechItems.STORAGE_INSERT_PORT, SlimefunItems.TRASH_CAN);
        ResearchUtil.setResearchBySlimefunItems(FinalTechItems.STORAGE_WITHDRAW_PORT, SlimefunItems.TRASH_CAN);
        ResearchUtil.setResearchBySlimefunItems(FinalTechItems.STORAGE_CARD, SlimefunItems.TRASH_CAN);

        ResearchUtil.setResearchBySlimefunItems(FinalTechItems.REMOTE_ACCESSOR, SlimefunItems.REACTOR_ACCESS_PORT);
        ResearchUtil.setResearchBySlimefunItems(FinalTechItems.AREA_ACCESSOR, SlimefunItems.REACTOR_ACCESS_PORT);

        ResearchUtil.setResearchBySlimefunItems(FinalTechItems.BASIC_FRAME_MACHINE, SlimefunItems.CARGO_CONNECTOR_NODE);
        ResearchUtil.setResearchBySlimefunItems(FinalTechItems.POINT_TRANSFER, SlimefunItems.CARGO_CONNECTOR_NODE);
        ResearchUtil.setResearchBySlimefunItems(FinalTechItems.MESH_TRANSFER, SlimefunItems.CARGO_CONNECTOR_NODE);
        ResearchUtil.setResearchBySlimefunItems(FinalTechItems.LINE_TRANSFER, SlimefunItems.CARGO_CONNECTOR_NODE);
        ResearchUtil.setResearchBySlimefunItems(FinalTechItems.LOCATION_TRANSFER, SlimefunItems.CARGO_CONNECTOR_NODE);

        ResearchUtil.setResearchBySlimefunItems(FinalTechItems.MATRIX_CRAFTING_TABLE, SlimefunItems.PROGRAMMABLE_ANDROID_2);

        ResearchUtil.setResearchBySlimefunItems(FinalTechItems.ITEM_DISMANTLE_TABLE, SlimefunItems.NUCLEAR_REACTOR);

        ResearchUtil.setResearchBySlimefunItems(FinalTechItems.CARD_OPERATION_TABLE, SlimefunItems.IRON_GOLEM_ASSEMBLER);

        ResearchUtil.setResearchBySlimefunItems(FinalTechItems.COBBLESTONE_FACTORY, SlimefunItems.PROGRAMMABLE_ANDROID);

        ResearchUtil.setResearchBySlimefunItems(FinalTechItems.FUEL_CHARGER, SlimefunItems.PROGRAMMABLE_ANDROID_3);
        ResearchUtil.setResearchBySlimefunItems(FinalTechItems.FUEL_OPERATOR, SlimefunItems.PROGRAMMABLE_ANDROID_3);
        ResearchUtil.setResearchBySlimefunItems(FinalTechItems.FUEL_ACCELERATOR, SlimefunItems.PROGRAMMABLE_ANDROID_3);
        ResearchUtil.setResearchBySlimefunItems(FinalTechItems.OPERATION_ACCELERATOR, SlimefunItems.PROGRAMMABLE_ANDROID_3);
        ResearchUtil.setResearchBySlimefunItems(FinalTechItems.OPERATION_ACCELERATOR_INFINITY, SlimefunItems.PROGRAMMABLE_ANDROID_3);

        ResearchUtil.setResearchBySlimefunItems(FinalTechItems.CURE_TOWER, SlimefunItems.GPS_TRANSMITTER_4);
        ResearchUtil.setResearchBySlimefunItems(FinalTechItems.PURIFY_LEVEL_TOWER, SlimefunItems.GPS_TRANSMITTER_3);
        ResearchUtil.setResearchBySlimefunItems(FinalTechItems.PURIFY_TIME_TOWER, SlimefunItems.GPS_TRANSMITTER_3);

        ResearchUtil.setResearchBySlimefunItems(FinalTechItems.MANUAL_GRIND_STONE, SlimefunItems.GRIND_STONE);
        ResearchUtil.setResearchBySlimefunItems(FinalTechItems.MANUAL_ARMOR_FORGE, SlimefunItems.ARMOR_FORGE);
        ResearchUtil.setResearchBySlimefunItems(FinalTechItems.MANUAL_ORE_CRUSHER, SlimefunItems.ORE_CRUSHER);
        ResearchUtil.setResearchBySlimefunItems(FinalTechItems.MANUAL_COMPRESSOR, SlimefunItems.COMPRESSOR);
        ResearchUtil.setResearchBySlimefunItems(FinalTechItems.MANUAL_SMELTERY, SlimefunItems.SMELTERY);
        ResearchUtil.setResearchBySlimefunItems(FinalTechItems.MANUAL_PRESSURE_CHAMBER, SlimefunItems.PRESSURE_CHAMBER);
        ResearchUtil.setResearchBySlimefunItems(FinalTechItems.MANUAL_MAGIC_WORKBENCH, SlimefunItems.MAGIC_WORKBENCH);
        ResearchUtil.setResearchBySlimefunItems(FinalTechItems.MANUAL_ORE_WASHER, SlimefunItems.ORE_WASHER);
        ResearchUtil.setResearchBySlimefunItems(FinalTechItems.MANUAL_COMPOSTER, SlimefunItems.COMPOSTER);
        ResearchUtil.setResearchBySlimefunItems(FinalTechItems.MANUAL_GOLD_PAN, SlimefunItems.GOLD_PAN);
        ResearchUtil.setResearchBySlimefunItems(FinalTechItems.MANUAL_CRUCIBLE, SlimefunItems.CRUCIBLE);
        ResearchUtil.setResearchBySlimefunItems(FinalTechItems.MANUAL_JUICER, SlimefunItems.JUICER);
        ResearchUtil.setResearchBySlimefunItems(FinalTechItems.MANUAL_ANCIENT_ALTAR, SlimefunItems.ANCIENT_ALTAR);
        ResearchUtil.setResearchBySlimefunItems(FinalTechItems.MANUAL_HEATED_PRESSURE_CHAMBER, SlimefunItems.HEATED_PRESSURE_CHAMBER);

        ResearchUtil.setResearchBySlimefunItems(FinalTechItems.BASIC_STONE_FACTORY, SlimefunItems.PRODUCE_COLLECTOR);
        ResearchUtil.setResearchBySlimefunItems(FinalTechItems.BASIC_DUST_FACTORY, SlimefunItems.PRODUCE_COLLECTOR);

        ResearchUtil.setResearchBySlimefunItems(FinalTechItems.ADVANCED_COMPOSTER, SlimefunItems.FOOD_COMPOSTER_2);
        ResearchUtil.setResearchBySlimefunItems(FinalTechItems.ADVANCED_JUICER, SlimefunItems.JUICER);
        ResearchUtil.setResearchBySlimefunItems(FinalTechItems.ADVANCED_FURNACE, SlimefunItems.ELECTRIC_FURNACE_3);
        ResearchUtil.setResearchBySlimefunItems(FinalTechItems.ADVANCED_GOLD_PAN, SlimefunItems.ELECTRIC_GOLD_PAN_3);
        ResearchUtil.setResearchBySlimefunItems(FinalTechItems.ADVANCED_DUST_WASHER, SlimefunItems.ELECTRIC_DUST_WASHER_3);
        ResearchUtil.setResearchBySlimefunItems(FinalTechItems.ADVANCED_INGOT_FACTORY, SlimefunItems.ELECTRIC_INGOT_FACTORY_3);
        ResearchUtil.setResearchBySlimefunItems(FinalTechItems.ADVANCED_CRUCIBLE, SlimefunItems.ELECTRIFIED_CRUCIBLE_3);
        ResearchUtil.setResearchBySlimefunItems(FinalTechItems.ADVANCED_ORE_GRINDER, SlimefunItems.ELECTRIC_ORE_GRINDER_3);
        ResearchUtil.setResearchBySlimefunItems(FinalTechItems.ADVANCED_HEATED_PRESSURE_CHAMBER, SlimefunItems.HEATED_PRESSURE_CHAMBER_2);
        ResearchUtil.setResearchBySlimefunItems(FinalTechItems.ADVANCED_INGOT_PULVERIZER, SlimefunItems.ELECTRIC_INGOT_PULVERIZER);
        ResearchUtil.setResearchBySlimefunItems(FinalTechItems.ADVANCED_AUTO_DRIER, SlimefunItems.AUTO_DRIER);
        ResearchUtil.setResearchBySlimefunItems(FinalTechItems.ADVANCED_PRESS, SlimefunItems.ELECTRIC_PRESS_2);
        ResearchUtil.setResearchBySlimefunItems(FinalTechItems.ADVANCED_FOOD_FACTORY, SlimefunItems.FOOD_FABRICATOR_2);
        ResearchUtil.setResearchBySlimefunItems(FinalTechItems.ADVANCED_FREEZER, SlimefunItems.FREEZER_2);
        ResearchUtil.setResearchBySlimefunItems(FinalTechItems.ADVANCED_CARBON_PRESS, SlimefunItems.CARBON_PRESS_3);
        ResearchUtil.setResearchBySlimefunItems(FinalTechItems.ADVANCED_SMELTERY, SlimefunItems.ELECTRIC_SMELTERY_2);
        ResearchUtil.setResearchBySlimefunItems(FinalTechItems.ADVANCED_COMPOSTER, SlimefunItems.FOOD_COMPOSTER_2);
        ResearchUtil.setResearchBySlimefunItems(FinalTechItems.ADVANCED_DUST_FACTORY, SlimefunItems.PRODUCE_COLLECTOR);

        ResearchUtil.setResearchBySlimefunItems(FinalTechItems.DUST_CONVERSION, SlimefunItems.COBALT_PICKAXE);
        ResearchUtil.setResearchBySlimefunItems(FinalTechItems.GRAVEL_CONVERSION, SlimefunItems.GOLD_PAN);
        ResearchUtil.setResearchBySlimefunItems(FinalTechItems.SOUL_SAND_CONVERSION, SlimefunItems.NETHER_GOLD_PAN);
        ResearchUtil.setResearchBySlimefunItems(FinalTechItems.CONCRETE_CONVERSION, SlimefunItems.BLANK_RUNE);
        ResearchUtil.setResearchBySlimefunItems(FinalTechItems.WOOL_CONVERSION, SlimefunItems.BLANK_RUNE);

        ResearchUtil.setResearchBySlimefunItems(FinalTechItems.ORE_EXTRACTION, SlimefunItems.PICKAXE_OF_THE_SEEKER);

        ResearchUtil.setResearchBySlimefunItems(FinalTechItems.STONE_GENERATOR, SlimefunItems.CLIMBING_PICK);
        ResearchUtil.setResearchBySlimefunItems(FinalTechItems.RAW_STONE_GENERATOR, SlimefunItems.CLIMBING_PICK);
        ResearchUtil.setResearchBySlimefunItems(FinalTechItems.NETHER_STONE_GENERATOR, SlimefunItems.CLIMBING_PICK);
        ResearchUtil.setResearchBySlimefunItems(FinalTechItems.PLANK_GENERATOR, SlimefunItems.LUMBER_AXE);
        ResearchUtil.setResearchBySlimefunItems(FinalTechItems.SAND_GENERATOR, SlimefunItems.SMELTERS_PICKAXE);


        /* command */
        finalTech.getCommand("finaltech-copy-card").setExecutor(new TransferToCopyCardItem());
        finalTech.getCommand("finaltech-storage-card").setExecutor(new TransferToStorageItem());
        finalTech.getCommand("finaltech-info").setExecutor(new ShowItemInfo());
    }

    public static void initLanguageManager(@Nonnull LanguageManager languageManager) {
        // Color normal
        languageManager.addFunction(new Function<>() {
            @Override
            public String apply(String s) {
                String[] split = StringUtil.split(s, "{color:", "}");
                if (split.length == 3) {
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append(split[0]);
                    switch (split[1]) {
                        case "normal" -> stringBuilder.append(TextUtil.COLOR_NORMAL);
                        case "stress" -> stringBuilder.append(TextUtil.COLOR_STRESS);
                        case "action" -> stringBuilder.append(TextUtil.COLOR_ACTION);
                        case "initiative" -> stringBuilder.append(TextUtil.COLOR_INITIATIVE);
                        case "passive" -> stringBuilder.append(TextUtil.COLOR_PASSIVE);
                        case "number" -> stringBuilder.append(TextUtil.COLOR_NUMBER);
                        case "positive" -> stringBuilder.append(TextUtil.COLOR_POSITIVE);
                        case "negative" -> stringBuilder.append(TextUtil.COLOR_NEGATIVE);
                        case "conceal" -> stringBuilder.append(TextUtil.COLOR_CONCEAL);
                        case "input" -> stringBuilder.append(TextUtil.COLOR_INPUT);
                        case "output" -> stringBuilder.append(TextUtil.COLOR_OUTPUT);
                        case "random" -> stringBuilder.append(TextUtil.getRandomColor());
                        case "prandom" -> stringBuilder.append(TextUtil.getPseudorandomColor(FinalTech.getSeed()));
                        default -> stringBuilder.append(split[1]);
                    }
                    return stringBuilder.append(this.apply(split[2])).toString();
                } else {
                    return s;
                }
            }
        });
        // SlimefunItem Name by id
        languageManager.addFunction(new Function<>() {
            @Override
            public String apply(String s) {
                String[] split = StringUtil.split(s, "{id:", "}");
                if (split.length == 3) {
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append(split[0]);
                    SlimefunItem slimefunItem = SlimefunItem.getById(split[1]);
                    if (slimefunItem != null) {
                        stringBuilder.append(slimefunItem.getItemName());
                    } else {
                        stringBuilder.append(split[1]);
                    }
                    return stringBuilder.append(this.apply(split[2])).toString();
                } else {
                    return s;
                }
            }
        });
        // Color random
        languageManager.addFunction(new Function<>() {
            @Override
            public String apply(String s) {
                String[] split = StringUtil.split(s, "{random-color:start}", "{random-color:end}");
                if (split.length == 3) {
                    return split[0] + TextUtil.colorRandomString(split[1]) + this.apply(split[2]);
                } else {
                    return s;
                }
            }
        });
    }

    public static void registerBlockTicker() {
        SetupUtil.registerBlockTicker(0);
    }

    private static void registerBlockTicker(int begin) {
        try {
            List<SlimefunItem> slimefunItemList = Slimefun.getRegistry().getAllSlimefunItems();
            for(int size = slimefunItemList.size(); begin < size; begin++) {
                SlimefunItem slimefunItem = slimefunItemList.get(begin);
                if (!slimefunItem.getAddon().getJavaPlugin().equals(FinalTech.getInstance()) && slimefunItem.getBlockTicker() != null) {
                    BlockTicker blockTicker = slimefunItem.getBlockTicker();
                    boolean forceAsync = false;
                    if (FinalTech.getConfigManager().getOrDefault(false, "super-ban") && slimefunItem.isDisabled()) {
                        blockTicker = null;
                    } else {
                        if(FinalTech.getConfigManager().containPath("interval", "general", slimefunItem.getId())) {
                            int interval = Integer.parseInt(FinalTech.getConfigManager().getString("interval", "general", slimefunItem.getId()));
                            if(interval > 0) {
                                blockTicker = BlockTickerUtil.getGeneralIntervalBlockTicker(blockTicker, interval);
                            } else {
                                FinalTech.logger().warning("wrong value of interval.general." + slimefunItem.getId() + " in config file");
                            }
                        }
                        if(FinalTech.getConfigManager().containPath("interval", "independent", slimefunItem.getId())) {
                            int interval = Integer.parseInt(FinalTech.getConfigManager().getString("interval", "independent", slimefunItem.getId()));
                            if(interval > 1) {
                                blockTicker = BlockTickerUtil.getIndependentIntervalBlockTicker(blockTicker, interval);
                            } else {
                                FinalTech.logger().warning("wrong value of interval.independent." + slimefunItem.getId() + " in config file");
                            }
                        }
                        forceAsync = !blockTicker.isSynchronized() && (FinalTech.getForceSlimefunMultiThread() || FinalTech.isAsyncSlimefunItem(slimefunItem.getId()));
                        boolean antiAccelerate = FinalTech.isAntiAccelerateSlimefunItem(slimefunItem.getId());
                        boolean performanceLimit = FinalTech.isPerformanceLimitSlimefunItem(slimefunItem.getId());
                        if(forceAsync || antiAccelerate || performanceLimit) {
                            blockTicker = BlockTickerUtil.generateBlockTicker(blockTicker, forceAsync, antiAccelerate, performanceLimit);
                        }
                    }
                    if(FinalTech.debugMode()) {
                        blockTicker = BlockTickerUtil.getDebugModeBlockTicker(blockTicker, slimefunItem);
                    }
                    if(slimefunItem.getBlockTicker() != blockTicker) {
                        Class<SlimefunItem> clazz = SlimefunItem.class;
                        Field declaredField = clazz.getDeclaredField("blockTicker");
                        declaredField.setAccessible(true);
                        declaredField.set(slimefunItem, blockTicker);
                        declaredField.setAccessible(false);
                        if(blockTicker == null) {
                            Field ticking = clazz.getDeclaredField("ticking");
                            ticking.setAccessible(true);
                            ticking.set(slimefunItem, false);
                            ticking.setAccessible(false);
                        }
                        if (forceAsync) {
                            FinalTech.logger().info(slimefunItem.getId() + "(" + slimefunItem.getItemName() + ")" + " is optimized for multi-thread！！！");
                            FinalTech.addAsyncSlimefunItem(slimefunItem.getId());
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            SetupUtil.registerBlockTicker(++begin);
        }
    }
}
