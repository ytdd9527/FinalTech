package io.taraxacum.finaltech.setup;

import io.github.thebusybiscuit.slimefun4.api.SlimefunAddon;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.implementation.Slimefun;
import io.github.thebusybiscuit.slimefun4.implementation.SlimefunItems;
import io.taraxacum.common.api.RunnableLockFactory;
import io.taraxacum.common.util.StringUtil;
import io.taraxacum.finaltech.FinalTech;
import io.taraxacum.finaltech.api.factory.ConfigFileManager;
import io.taraxacum.finaltech.api.factory.ItemValueTable;
import io.taraxacum.finaltech.api.factory.LanguageManager;
import io.taraxacum.finaltech.core.command.ShowItemValue;
import io.taraxacum.finaltech.core.command.TransferToCopyCardItem;
import io.taraxacum.finaltech.core.handler.ShineListener;
import io.taraxacum.finaltech.core.items.machine.*;
import io.taraxacum.finaltech.core.items.machine.cargo.unit.*;
import io.taraxacum.finaltech.core.items.machine.manual.EquivalentExchangeTable;
import io.taraxacum.finaltech.core.items.machine.manual.craft.MatrixCraftingTable;
import io.taraxacum.finaltech.core.items.machine.operation.*;
import io.taraxacum.finaltech.core.items.machine.range.point.EquivalentConcept;
import io.taraxacum.finaltech.core.items.machine.range.point.face.EnergizedChargeBase;
import io.taraxacum.finaltech.core.items.machine.range.point.face.FuelCharger;
import io.taraxacum.finaltech.core.items.machine.range.point.face.FuelOperator;
import io.taraxacum.finaltech.core.items.machine.range.point.face.OverloadChargeBase;
import io.taraxacum.finaltech.core.items.machine.template.basic.*;
import io.taraxacum.finaltech.core.items.machine.template.conversion.*;
import io.taraxacum.finaltech.core.items.machine.template.extraction.OreExtraction;
import io.taraxacum.finaltech.core.items.machine.template.generator.*;
import io.taraxacum.finaltech.core.items.machine.tower.CureTower;
import io.taraxacum.finaltech.core.items.machine.tower.PurifyLevelTower;
import io.taraxacum.finaltech.core.items.machine.tower.PurifyTimeTower;
import io.taraxacum.finaltech.core.items.unusable.*;
import io.taraxacum.finaltech.core.items.unusable.liquid.LavaCard;
import io.taraxacum.finaltech.core.items.unusable.liquid.MilkCard;
import io.taraxacum.finaltech.core.items.unusable.liquid.WaterCard;
import io.taraxacum.finaltech.core.items.usable.*;
import io.taraxacum.finaltech.core.items.usable.accelerate.*;
import io.taraxacum.finaltech.core.items.machine.capacitor.AdvancedChargeIncreaseCapacitor;
import io.taraxacum.finaltech.core.items.machine.capacitor.AdvancedConsumeReduceCapacitor;
import io.taraxacum.finaltech.core.items.machine.range.cube.EnergizedAccelerator;
import io.taraxacum.finaltech.core.items.machine.range.cube.MatrixAccelerator;
import io.taraxacum.finaltech.core.items.machine.range.cube.OverloadedAccelerator;
import io.taraxacum.finaltech.core.items.machine.range.cube.EscapeCapacitor;
import io.taraxacum.finaltech.core.items.machine.range.cube.generator.*;
import io.taraxacum.finaltech.core.items.machine.capacitor.BasicChargeIncreaseCapacitor;
import io.taraxacum.finaltech.core.items.machine.capacitor.BasicConsumeReduceCapacitor;
import io.taraxacum.finaltech.core.items.machine.capacitor.expanded.*;
import io.taraxacum.finaltech.core.items.machine.cargo.*;
import io.taraxacum.finaltech.core.items.machine.cargo.storage.StorageInsertPort;
import io.taraxacum.finaltech.core.items.machine.cargo.storage.StorageInteractPort;
import io.taraxacum.finaltech.core.items.machine.cargo.storage.StorageWithdrawPort;
import io.taraxacum.finaltech.core.items.machine.manual.CardOperationTable;
import io.taraxacum.finaltech.core.items.machine.manual.craft.*;
import io.taraxacum.finaltech.core.items.machine.range.ray.shooter.EnergizedElectricityShootPile;
import io.taraxacum.finaltech.core.items.machine.range.ray.shooter.NormalElectricityShootPile;
import io.taraxacum.finaltech.core.items.machine.range.ray.shooter.OverloadedElectricityShootPile;
import io.taraxacum.finaltech.core.items.machine.template.advanced.*;
import io.taraxacum.finaltech.core.items.weapon.DustWoodenSword;
import io.taraxacum.finaltech.util.AntiAccelerationUtil;
import io.taraxacum.finaltech.util.PerformanceLimitUtil;
import io.taraxacum.finaltech.util.SlimefunUtil;
import io.taraxacum.finaltech.util.TextUtil;
import me.mrCookieSlime.CSCoreLibPlugin.Configuration.Config;
import me.mrCookieSlime.Slimefun.Objects.handlers.BlockTicker;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.plugin.PluginManager;

import javax.annotation.Nonnull;
import java.util.function.Function;

/**
 * @author Final_ROOT
 * @since 1.0
 */
public final class SetupUtil {
    public static void init(@Nonnull SlimefunAddon slimefunAddon) {
        FinalTech finalTech = FinalTech.getInstance();

        // setup config

        ConfigFileManager config = FinalTech.getConfigManager();

        AbstractManualCraftMachine.COUNT_THRESHOLD = config.getOrDefault(10, "manual", "count-threshold");
        if(AbstractManualCraftMachine.COUNT_THRESHOLD == -1) {
            AbstractManualCraftMachine.COUNT_THRESHOLD = Slimefun.getTickerTask().getTickRate();
        }

        // setup menu

        FinalTechMenus.MAIN_MENU.setTier(0);
        FinalTechMenus.MAIN_MENU.register(finalTech);

        FinalTechMenus.MENU_MATERIAL.setTier(0);
        FinalTechMenus.MENU_TOOL.setTier(0);
        FinalTechMenus.MENU_WEAPON.setTier(0);
        FinalTechMenus.MENU_CARGO.setTier(0);
        FinalTechMenus.MENU_ELECTRIC.setTier(0);
        FinalTechMenus.MENU_FUNCTIONAL_MACHINE.setTier(0);
        FinalTechMenus.MENU_MANUAL_MACHINE.setTier(0);
        FinalTechMenus.MENU_BASIC_MACHINE.setTier(0);
        FinalTechMenus.MENU_ADVANCED_MACHINE.setTier(0);
        FinalTechMenus.MENU_CONVERSION_MACHINE.setTier(0);
        FinalTechMenus.MENU_EXTRACTION_MACHINE.setTier(0);
        FinalTechMenus.MENU_GENERATOR_MACHINE.setTier(0);
        FinalTechMenus.MENU_FINAL_ITEM.setTier(0);

        /* command */

        finalTech.getCommand("finaltech-copy-card").setExecutor(new TransferToCopyCardItem());
        finalTech.getCommand("finaltech-item-value").setExecutor(new ShowItemValue());

        SetupUtil.setupItems(slimefunAddon);
    }

    public static void initLanguageManager(@Nonnull LanguageManager languageManager) {

        // Color normal
        languageManager.addFunction(new Function<>() {
            @Override
            public String apply(String s) {
                String[] split = StringUtil.split(s, "{", "}");
                if (split.length == 3) {
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append(split[0]);
                    String[] center = StringUtil.split(split[1], ":");
                    if (center.length == 2 && "color".equals(center[0])) {
                        switch (center[1]) {
                            case "normal" -> stringBuilder.append(TextUtil.COLOR_NORMAL);
                            case "stress" -> stringBuilder.append(TextUtil.COLOR_STRESS);
                            case "action" -> stringBuilder.append(TextUtil.COLOR_ACTION);
                            case "initiative" -> stringBuilder.append(TextUtil.COLOR_INITIATIVE);
                            case "passive" -> stringBuilder.append(TextUtil.COLOR_PASSIVE);
                            case "number" -> stringBuilder.append(TextUtil.COLOR_NUMBER);
                            case "positive" -> stringBuilder.append(TextUtil.COLOR_POSITIVE);
                            case "negative" -> stringBuilder.append(TextUtil.COLOR_NEGATIVE);
                            case "input" -> stringBuilder.append(TextUtil.COLOR_INPUT);
                            case "output" -> stringBuilder.append(TextUtil.COLOR_OUTPUT);
                            case "random" -> stringBuilder.append(TextUtil.getRandomColor());
                            default -> stringBuilder.append(split[1]);
                        }
                    } else {
                        stringBuilder.append(split[1]);
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
                String[] split = StringUtil.split(s, "{", "}");
                if (split.length == 3) {
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append(split[0]);
                    String[] center = StringUtil.split(split[1], ":");
                    if (center.length == 2 && "id".equals(center[0])) {
                        SlimefunItem slimefunItem = SlimefunItem.getById(center[1]);
                        if (slimefunItem != null) {
                            stringBuilder.append(slimefunItem.getItemName());
                        } else {
                            stringBuilder.append(center[1]);
                        }
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
                String[] split = StringUtil.split(s, "{color:random:start}", "{color:random:end}");
                if (split.length == 3) {
                    return split[0] + TextUtil.colorRandomString(split[1]) + this.apply(split[2]);
                } else {
                    return s;
                }
            }
        });
        // Color pseudorandom
        languageManager.addFunction(new Function<>() {
            @Override
            public String apply(String s) {
                String[] split = StringUtil.split(s, "{color:pseudorandom:start}", "{color:pseudorandom:end}");
                if (split.length == 3) {
                    return split[0] + TextUtil.colorRandomString(split[1]) + this.apply(split[2]);
                } else {
                    return s;
                }
            }
        });
    }

    public static void setupItems(@Nonnull SlimefunAddon slimefunAddon) {

        /* material */
        new WaterCard(FinalTechMenus.MENU_MATERIAL, FinalTechItems.WATER_CARD, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.WATER_CARD).register();
        new LavaCard(FinalTechMenus.MENU_MATERIAL, FinalTechItems.LAVA_CARD, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.LAVA_CARD).register();
        new MilkCard(FinalTechMenus.MENU_MATERIAL, FinalTechItems.MILK_CARD, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.MILK_CARD).register();
        new Gearwheel(FinalTechMenus.MENU_MATERIAL, FinalTechItems.GEARWHEEL, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.GEARWHEEL, new SlimefunItemStack(FinalTechItems.GEARWHEEL, 4)).register(slimefunAddon);
        new UnorderedDust(FinalTechMenus.MENU_MATERIAL, FinalTechItems.UNORDERED_DUST,  FinalTechRecipes.RECIPE_TYPE_ORDERED_DUST_FACTORY, FinalTechRecipes.UNORDERED_DUST).register();
        new OrderedDust(FinalTechMenus.MENU_MATERIAL, FinalTechItems.ORDERED_DUST, FinalTechRecipes.RECIPE_TYPE_ORDERED_DUST_FACTORY, FinalTechRecipes.ORDERED_DUST).register();
        new Bug(FinalTechMenus.MENU_MATERIAL, FinalTechItems.BUG, FinalTechRecipes.RECIPE_TYPE_EQUIVALENT_EXCHANGE_TABLE, FinalTechRecipes.BUG).register();
        new Entropy(FinalTechMenus.MENU_MATERIAL, FinalTechItems.ENTROPY, FinalTechRecipes.RECIPE_TYPE_ENTROPY, FinalTechRecipes.ENTROPY).register();
        new Box(FinalTechMenus.MENU_MATERIAL, FinalTechItems.BOX, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.BOX).register();
        new Shine(FinalTechMenus.MENU_MATERIAL, FinalTechItems.SHINE, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.SHINE).register();
        new CopyCardItem(FinalTechMenus.MENU_MATERIAL, FinalTechItems.COPY_CARD, FinalTechRecipes.RECIPE_TYPE_ITEM_SERIALIZATION_CONSTRUCTOR, FinalTechRecipes.COPY_CARD).register();
        new Annular(FinalTechMenus.MENU_MATERIAL, FinalTechItems.ANNULAR, FinalTechRecipes.RECIPE_TYPE_CARD_OPERATION_PORT, FinalTechRecipes.ANNULAR).register();
        new QuantityModule(FinalTechMenus.MENU_MATERIAL, FinalTechItems.QUANTITY_MODULE, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.QUANTITY_MODULE).register();
        new QuantityModuleInfinity(FinalTechMenus.MENU_MATERIAL, FinalTechItems.QUANTITY_MODULE_INFINITY, FinalTechRecipes.RECIPE_TYPE_MATRIX_CRAFTING_TABLE, FinalTechRecipes.QUANTITY_MODULE_INFINITY).register();
        new Singularity(FinalTechMenus.MENU_MATERIAL, FinalTechItems.SINGULARITY, FinalTechRecipes.RECIPE_TYPE_ITEM_SERIALIZATION_CONSTRUCTOR, FinalTechRecipes.SINGULARITY).register(slimefunAddon);
        new Spirochete(FinalTechMenus.MENU_MATERIAL, FinalTechItems.SPIROCHETE, FinalTechRecipes.RECIPE_TYPE_ITEM_SERIALIZATION_CONSTRUCTOR, FinalTechRecipes.SPIROCHETE).register(slimefunAddon);
        new Shell(FinalTechMenus.MENU_MATERIAL, FinalTechItems.SHELL, FinalTechRecipes.RECIPE_TYPE_CARD_OPERATION_PORT, FinalTechRecipes.SHELL).register(slimefunAddon);
        new ItemPhony(FinalTechMenus.MENU_MATERIAL, FinalTechItems.PHONY, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.PHONY).register(slimefunAddon);
        new EquivalentConcept(FinalTechMenus.MENU_MATERIAL, FinalTechItems.EQUIVALENT_CONCEPT, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.EQUIVALENT_CONCEPT).register();
        new Justifiability(FinalTechMenus.MENU_MATERIAL, FinalTechItems.JUSTIFIABILITY, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.JUSTIFIABILITY).register();

        /* tool */
        new MachineChargeCardL1(FinalTechMenus.MENU_TOOL, FinalTechItems.MACHINE_CHARGE_CARD_L1, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.MACHINE_CHARGE_CARD_L1).register();
        new MachineChargeCardL2(FinalTechMenus.MENU_TOOL, FinalTechItems.MACHINE_CHARGE_CARD_L2, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.MACHINE_CHARGE_CARD_L2).register();
        new MachineChargeCardL3(FinalTechMenus.MENU_TOOL, FinalTechItems.MACHINE_CHARGE_CARD_L3, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.MACHINE_CHARGE_CARD_L3).register();
        new MachineAccelerateCardL1(FinalTechMenus.MENU_TOOL, FinalTechItems.MACHINE_ACCELERATE_CARD_L1, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.MACHINE_ACCELERATE_CARD_L1).register();
        new MachineAccelerateCardL2(FinalTechMenus.MENU_TOOL, FinalTechItems.MACHINE_ACCELERATE_CARD_L2, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.MACHINE_ACCELERATE_CARD_L2).register();
        new MachineAccelerateCardL3(FinalTechMenus.MENU_TOOL, FinalTechItems.MACHINE_ACCELERATE_CARD_L3, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.MACHINE_ACCELERATE_CARD_L3).register();
        new MachineActivateCardL1(FinalTechMenus.MENU_TOOL, FinalTechItems.MACHINE_ACTIVATE_CARD_L1, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.MACHINE_ACTIVATE_CARD_L1).register();
        new MachineActivateCardL2(FinalTechMenus.MENU_TOOL, FinalTechItems.MACHINE_ACTIVATE_CARD_L2, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.MACHINE_ACTIVATE_CARD_L2).register();
        new MachineActivateCardL3(FinalTechMenus.MENU_TOOL, FinalTechItems.MACHINE_ACTIVATE_CARD_L3, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.MACHINE_ACTIVATE_CARD_L3).register();
        new MenuViewer(FinalTechMenus.MENU_TOOL, FinalTechItems.MENU_VIEWER, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.MENU_VIEWER).register();
        new LocationRecorder(FinalTechMenus.MENU_TOOL, FinalTechItems.LOCATION_RECORDER, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.LOCATION_RECORDER).register();
        new MagicHypnotic(FinalTechMenus.MENU_TOOL, FinalTechItems.MAGIC_HYPNOTIC, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.MAGIC_HYPNOTIC).register();
        new PotionEffectCompressor(FinalTechMenus.MENU_TOOL, FinalTechItems.POTION_EFFECT_COMPRESSOR, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.POTION_EFFECT_COMPRESSOR).register();
        new PotionEffectDilator(FinalTechMenus.MENU_TOOL, FinalTechItems.POTION_EFFECT_DILATOR, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.POTION_EFFECT_DILATOR).register();
        new PotionEffectPurifier(FinalTechMenus.MENU_TOOL, FinalTechItems.POTION_EFFECT_PURIFIER, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.POTION_EFFECT_PURIFIER).register();
        new StaffElementalLine(FinalTechMenus.MENU_TOOL, FinalTechItems.STAFF_ELEMENTAL_LINE, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.STAFF_ELEMENTAL_LINE).register();
        new UntreatableRune(FinalTechMenus.MENU_TOOL, FinalTechItems.UNTREATABLE_RUNE, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.UNTREATABLE_RUNE).register();
        new ResearchUnlockTicket(FinalTechMenus.MENU_TOOL, FinalTechItems.RESEARCH_UNLOCK_TICKET, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.RESEARCH_UNLOCK_TICKET).register();

        /* weapon */
        new DustWoodenSword(FinalTechMenus.MENU_WEAPON, FinalTechItems.DUST_WOODEN_SWORD, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.DUST_WOODEN_SWORD).register();

        /* electric */
        new BasicChargeIncreaseCapacitor(FinalTechMenus.MENU_ELECTRIC, FinalTechItems.BASIC_CHARGE_INCREASE_CAPACITOR, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.BASIC_CHARGE_INCREASE_CAPACITOR).register();
        new BasicConsumeReduceCapacitor(FinalTechMenus.MENU_ELECTRIC, FinalTechItems.BASIC_CONSUME_REDUCE_CAPACITOR, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.BASIC_CONSUME_REDUCE_CAPACITOR).register();
        new SmallExpandedCapacitor(FinalTechMenus.MENU_ELECTRIC, FinalTechItems.SMALL_EXPANDED_CAPACITOR, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.SMALL_EXPANDED_CAPACITOR).register();
        new MediumExpandedCapacitor(FinalTechMenus.MENU_ELECTRIC, FinalTechItems.MEDIUM_EXPANDED_CAPACITOR, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.MEDIUM_EXPANDED_CAPACITOR).register();
        new BigExpandedCapacitor(FinalTechMenus.MENU_ELECTRIC, FinalTechItems.BIG_EXPANDED_CAPACITOR, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.BIG_EXPANDED_CAPACITOR).register();
        new LargeExpandedCapacitor(FinalTechMenus.MENU_ELECTRIC, FinalTechItems.LARGE_EXPANDED_CAPACITOR, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.LARGE_EXPANDED_CAPACITOR).register();
        new CarbonadoExpandedCapacitor(FinalTechMenus.MENU_ELECTRIC, FinalTechItems.CARBONADO_EXPANDED_CAPACITOR, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.CARBONADO_EXPANDED_CAPACITOR).register();
        new EnergizedExpandedCapacitor(FinalTechMenus.MENU_ELECTRIC, FinalTechItems.ENERGIZED_EXPANDED_CAPACITOR, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.ENERGIZED_EXPANDED_CAPACITOR).register();
        new EnergizedStackExpandedCapacitor(FinalTechMenus.MENU_ELECTRIC, FinalTechItems.ENERGIZED_STACK_EXPANDED_CAPACITOR, FinalTechRecipes.RECIPE_TYPE_MATRIX_CRAFTING_TABLE, FinalTechRecipes.ENERGIZED_STACK_EXPANDED_CAPACITOR).register();
        new OverloadedExpandedCapacitor(FinalTechMenus.MENU_ELECTRIC, FinalTechItems.OVERLOADED_EXPANDED_CAPACITOR, FinalTechRecipes.RECIPE_TYPE_MATRIX_CRAFTING_TABLE, FinalTechRecipes.OVERLOADED_EXPANDED_CAPACITOR).register();
        new AdvancedChargeIncreaseCapacitor(FinalTechMenus.MENU_ELECTRIC, FinalTechItems.ADVANCED_CHARGE_INCREASE_CAPACITOR, FinalTechRecipes.RECIPE_TYPE_MATRIX_CRAFTING_TABLE, FinalTechRecipes.ADVANCED_CHARGE_INCREASE_CAPACITOR).register();
        new AdvancedConsumeReduceCapacitor(FinalTechMenus.MENU_ELECTRIC, FinalTechItems.ADVANCED_CONSUME_REDUCE_CAPACITOR, FinalTechRecipes.RECIPE_TYPE_MATRIX_CRAFTING_TABLE, FinalTechRecipes.ADVANCED_CONSUME_REDUCE_CAPACITOR).register();
        new EscapeCapacitor(FinalTechMenus.MENU_ELECTRIC, FinalTechItems.ESCAPE_CAPACITOR, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.ESCAPE_CAPACITOR).register();
        new NormalElectricityShootPile(FinalTechMenus.MENU_ELECTRIC, FinalTechItems.NORMAL_ELECTRICITY_SHOOT_PILE, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.NORMAL_ELECTRICITY_SHOOT_PILE).register();
        new EnergizedElectricityShootPile(FinalTechMenus.MENU_ELECTRIC, FinalTechItems.ENERGIZED_ELECTRICITY_SHOOT_PILE, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.ENERGIZED_ELECTRICITY_SHOOT_PILE).register();
        new OverloadedElectricityShootPile(FinalTechMenus.MENU_ELECTRIC, FinalTechItems.OVERLOADED_ELECTRICITY_SHOOT_PILE, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.OVERLOADED_ELECTRICITY_SHOOT_PILE).register();
        new DustGenerator(FinalTechMenus.MENU_ELECTRIC, FinalTechItems.ORDERED_DUST_GENERATOR, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.ORDERED_DUST_GENERATOR).register();
        new BasicGenerator(FinalTechMenus.MENU_ELECTRIC, FinalTechItems.BASIC_GENERATOR, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.BASIC_GENERATOR).register();
        new AdvancedGenerator(FinalTechMenus.MENU_ELECTRIC, FinalTechItems.ADVANCED_GENERATOR, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.ADVANCED_GENERATOR).register();
        new CarbonadoGenerator(FinalTechMenus.MENU_ELECTRIC, FinalTechItems.CARBONADO_GENERATOR, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.CARBONADO_GENERATOR).register();
        new EnergizedGenerator(FinalTechMenus.MENU_ELECTRIC, FinalTechItems.ENERGIZED_GENERATOR, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.ENERGIZED_GENERATOR).register();
        new EnergizedStackGenerator(FinalTechMenus.MENU_ELECTRIC, FinalTechItems.ENERGIZED_STACK_GENERATOR, FinalTechRecipes.RECIPE_TYPE_MATRIX_CRAFTING_TABLE, FinalTechRecipes.ENERGIZED_STACK_GENERATOR).register();
        new OverloadedGenerator(FinalTechMenus.MENU_ELECTRIC, FinalTechItems.OVERLOADED_GENERATOR, FinalTechRecipes.RECIPE_TYPE_MATRIX_CRAFTING_TABLE, FinalTechRecipes.OVERLOADED_GENERATOR).register();
        new EnergizedChargeBase(FinalTechMenus.MENU_ELECTRIC, FinalTechItems.ENERGIZED_CHARGE_BASE, FinalTechRecipes.RECIPE_TYPE_MATRIX_CRAFTING_TABLE, FinalTechRecipes.ENERGIZED_CHARGE_BASE).register();
        new OverloadChargeBase(FinalTechMenus.MENU_ELECTRIC, FinalTechItems.OVERLOAD_CHARGE_BASE, FinalTechRecipes.RECIPE_TYPE_MATRIX_CRAFTING_TABLE, FinalTechRecipes.OVERLOAD_CHARGE_BASE).register();
        new EnergizedAccelerator(FinalTechMenus.MENU_ELECTRIC, FinalTechItems.ENERGIZED_ACCELERATOR, FinalTechRecipes.RECIPE_TYPE_MATRIX_CRAFTING_TABLE, FinalTechRecipes.ENERGIZED_ACCELERATOR).register();
        new OverloadedAccelerator(FinalTechMenus.MENU_ELECTRIC, FinalTechItems.OVERLOADED_ACCELERATOR, FinalTechRecipes.RECIPE_TYPE_MATRIX_CRAFTING_TABLE, FinalTechRecipes.OVERLOADED_ACCELERATOR).register();

        /* cargo and storage */
        new BasicFrameMachine(FinalTechMenus.MENU_CARGO, FinalTechItems.BASIC_FRAME_MACHINE, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.BASIC_FRAME_MACHINE).register();
        new NormalStorageUnit(FinalTechMenus.MENU_CARGO, FinalTechItems.NORMAL_STORAGE_UNIT, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.NORMAL_STORAGE_UNIT).register();
        new DividedStorageUnit(FinalTechMenus.MENU_CARGO, FinalTechItems.DIVIDED_STORAGE_UNIT, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.DIVIDED_STORAGE_UNIT).register();
        new LimitedStorageUnit(FinalTechMenus.MENU_CARGO, FinalTechItems.LIMITED_STORAGE_UNIT, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.LIMITED_STORAGE_UNIT).register();
        new StackStorageUnit(FinalTechMenus.MENU_CARGO, FinalTechItems.STACK_STORAGE_UNIT, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.STACK_STORAGE_UNIT).register();
        new DividedLimitedStorageUnit(FinalTechMenus.MENU_CARGO, FinalTechItems.DIVIDED_LIMITED_STORAGE_UNIT, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.DIVIDED_LIMITED_STORAGE_UNIT).register();
        new DividedStackStorageUnit(FinalTechMenus.MENU_CARGO, FinalTechItems.DIVIDED_STACK_STORAGE_UNIT, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.DIVIDED_STACK_STORAGE_UNIT).register();
        new LimitedStackStorageUnit(FinalTechMenus.MENU_CARGO, FinalTechItems.LIMITED_STACK_STORAGE_UNIT, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.LIMITED_STACK_STORAGE_UNIT).register();
        new ChargeableStorageUnit(FinalTechMenus.MENU_CARGO, FinalTechItems.CHARGEABLE_STORAGE_UNIT, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.CHARGEABLE_STORAGE_UNIT).register();
        new RandomInputStorageUnit(FinalTechMenus.MENU_CARGO, FinalTechItems.RANDOM_INPUT_STORAGE_UNIT, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.RANDOM_INPUT_STORAGE_UNIT).register();
        new RandomOutputStorageUnit(FinalTechMenus.MENU_CARGO, FinalTechItems.RANDOM_OUTPUT_STORAGE_UNIT, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.RANDOM_OUTPUT_STORAGE_UNIT).register();
        new RandomStorageUnit(FinalTechMenus.MENU_CARGO, FinalTechItems.RANDOM_STORAGE_UNIT, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.RANDOM_STORAGE_UNIT).register();
        new DistributeLeftStorageUnit(FinalTechMenus.MENU_CARGO, FinalTechItems.DISTRIBUTE_LEFT_STORAGE_UNIT, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.DISTRIBUTE_LEFT_STORAGE_UNIT).register();
        new DistributeRightStorageUnit(FinalTechMenus.MENU_CARGO, FinalTechItems.DISTRIBUTE_RIGHT_STORAGE_UNIT, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.DISTRIBUTE_RIGHT_STORAGE_UNIT).register();
        new RemoteAccessor(FinalTechMenus.MENU_CARGO, FinalTechItems.REMOTE_ACCESSOR, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.REMOTE_ACCESSOR).register();
        new LinkTransfer(FinalTechMenus.MENU_CARGO, FinalTechItems.LINK_TRANSFER, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.LINK_TRANSFER).register();
        new LineTransfer(FinalTechMenus.MENU_CARGO, FinalTechItems.LINE_TRANSFER, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.LINE_TRANSFER).register();
        new MeshTransfer(FinalTechMenus.MENU_CARGO, FinalTechItems.STATION_TRANSFER, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.STATION_TRANSFER).register();
        new LocationTransfer(FinalTechMenus.MENU_CARGO, FinalTechItems.LOCATION_TRANSFER, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.LOCATION_TRANSFER).register();
        new StorageInteractPort(FinalTechMenus.MENU_CARGO, FinalTechItems.STORAGE_INTERACT_PORT, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.STORAGE_INTERACT_PORT).register();
        new StorageInsertPort(FinalTechMenus.MENU_CARGO, FinalTechItems.STORAGE_INSERT_PORT, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.STORAGE_INSERT_PORT).register();
        new StorageWithdrawPort(FinalTechMenus.MENU_CARGO, FinalTechItems.STORAGE_WITHDRAW_PORT, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.STORAGE_WITHDRAW_PORT).register();
        new StorageCardItem(FinalTechMenus.MENU_CARGO, FinalTechItems.STORAGE_CARD, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.STORAGE_ITEM_UNCOLORED).register(slimefunAddon);
//        new StorageCardItem(FinalTechMenus.MENU_CARGO, FinalTechItems.STORAGE_ITEM_WHITE, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.STORAGE_ITEM_WHITE).register(slimefunAddon);
//        new StorageCardItem(FinalTechMenus.MENU_CARGO, FinalTechItems.STORAGE_ITEM_ORANGE, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.STORAGE_ITEM_ORANGE).register(slimefunAddon);
//        new StorageCardItem(FinalTechMenus.MENU_CARGO, FinalTechItems.STORAGE_ITEM_MAGENTA, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.STORAGE_ITEM_MAGENTA).register(slimefunAddon);
//        new StorageCardItem(FinalTechMenus.MENU_CARGO, FinalTechItems.STORAGE_ITEM_LIGHT_BLUE, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.STORAGE_ITEM_LIGHT_BLUE).register(slimefunAddon);
//        new StorageCardItem(FinalTechMenus.MENU_CARGO, FinalTechItems.STORAGE_ITEM_YELLOW, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.STORAGE_ITEM_YELLOW).register(slimefunAddon);
//        new StorageCardItem(FinalTechMenus.MENU_CARGO, FinalTechItems.STORAGE_ITEM_LIME, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.STORAGE_ITEM_LIME).register(slimefunAddon);
//        new StorageCardItem(FinalTechMenus.MENU_CARGO, FinalTechItems.STORAGE_ITEM_PINK, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.STORAGE_ITEM_PINK).register(slimefunAddon);
//        new StorageCardItem(FinalTechMenus.MENU_CARGO, FinalTechItems.STORAGE_ITEM_GRAY, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.STORAGE_ITEM_GRAY).register(slimefunAddon);
//        new StorageCardItem(FinalTechMenus.MENU_CARGO, FinalTechItems.STORAGE_ITEM_LIGHT_GRAY, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.STORAGE_ITEM_LIGHT_GRAY).register(slimefunAddon);
//        new StorageCardItem(FinalTechMenus.MENU_CARGO, FinalTechItems.STORAGE_ITEM_CYAN, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.STORAGE_ITEM_CYAN).register(slimefunAddon);
//        new StorageCardItem(FinalTechMenus.MENU_CARGO, FinalTechItems.STORAGE_ITEM_PURPLE, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.STORAGE_ITEM_PURPLE).register(slimefunAddon);
//        new StorageCardItem(FinalTechMenus.MENU_CARGO, FinalTechItems.STORAGE_ITEM_BLUE, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.STORAGE_ITEM_BLUE).register(slimefunAddon);
//        new StorageCardItem(FinalTechMenus.MENU_CARGO, FinalTechItems.STORAGE_ITEM_BROWN, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.STORAGE_ITEM_BROWN).register(slimefunAddon);
//        new StorageCardItem(FinalTechMenus.MENU_CARGO, FinalTechItems.STORAGE_ITEM_GREEN, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.STORAGE_ITEM_GREEN).register(slimefunAddon);
//        new StorageCardItem(FinalTechMenus.MENU_CARGO, FinalTechItems.STORAGE_ITEM_RED, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.STORAGE_ITEM_RED).register(slimefunAddon);
//        new StorageCardItem(FinalTechMenus.MENU_CARGO, FinalTechItems.STORAGE_ITEM_BLACK, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.STORAGE_ITEM_BLACK).register(slimefunAddon);

        /* tower machine */
        new CureTower(FinalTechMenus.MENU_TOWER_MACHINE, FinalTechItems.CURE_TOWER, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.CURE_TOWER).register();
        new PurifyLevelTower(FinalTechMenus.MENU_TOWER_MACHINE, FinalTechItems.PURIFY_LEVEL_TOWER, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.PURIFY_LEVEL_TOWER).register();
        new PurifyTimeTower(FinalTechMenus.MENU_TOWER_MACHINE, FinalTechItems.PURIFY_TIME_TOWER, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.PURIFY_TIME_TOWER).register();

        /* function machine */
        new DustFactoryDirt(FinalTechMenus.MENU_FUNCTIONAL_MACHINE, FinalTechItems.ORDERED_DUST_FACTORY_DIRT, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.ORDERED_DUST_FACTORY_DIRT).register();
        new DustFactoryStone(FinalTechMenus.MENU_FUNCTIONAL_MACHINE, FinalTechItems.ORDERED_DUST_FACTORY_STONE, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.ORDERED_DUST_FACTORY_STONE).register();
        new ItemSerializationConstructor(FinalTechMenus.MENU_FUNCTIONAL_MACHINE, FinalTechItems.ITEM_SERIALIZATION_CONSTRUCTOR, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.ALL_COMPRESSION).register();
        new ItemDeserializeParser(FinalTechMenus.MENU_FUNCTIONAL_MACHINE, FinalTechItems.ITEM_DESERIALIZE_PARSER, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.ALL_FACTORY).register();
        new MatrixCraftingTable(FinalTechMenus.MENU_FUNCTIONAL_MACHINE, FinalTechItems.MATRIX_CRAFTING_TABLE, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.MATRIX_CRAFTING_TABLE).register();
        new CardOperationTable(FinalTechMenus.MENU_FUNCTIONAL_MACHINE, FinalTechItems.CARD_OPERATION_PORT, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.CARD_OPERATION_PORT).register();
        new ItemDismantleTable(FinalTechMenus.MENU_FUNCTIONAL_MACHINE, FinalTechItems.ITEM_DISMANTLE_TABLE, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.ITEM_DISMANTLE_TABLE).register();
        new EquivalentExchangeTable(FinalTechMenus.MENU_FUNCTIONAL_MACHINE, FinalTechItems.EQUIVALENT_EXCHANGE_TABLE, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.EQUIVALENT_EXCHANGE_TABLE).register();
        new EntropyConstructor(FinalTechMenus.MENU_FUNCTIONAL_MACHINE, FinalTechItems.ENTROPY_CONSTRUCTOR, FinalTechRecipes.RECIPE_TYPE_MATRIX_CRAFTING_TABLE, FinalTechRecipes.ENTROPY_CONSTRUCTOR).register();
        new FuelCharger(FinalTechMenus.MENU_FUNCTIONAL_MACHINE, FinalTechItems.FUEL_CHARGER, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.FUEL_CHARGER).register();
        new FuelOperator(FinalTechMenus.MENU_FUNCTIONAL_MACHINE, FinalTechItems.FUEL_OPERATOR, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.FUEL_OPERATOR).register();
        new CobbleStoneErupter(FinalTechMenus.MENU_FUNCTIONAL_MACHINE, FinalTechItems.COBBLESTONE_ERUPTER, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.COBBLESTONE_ERUPTER).register();
        new EntropySeed(FinalTechMenus.MENU_FUNCTIONAL_MACHINE, FinalTechItems.ENTROPY_SEED, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.ENTROPY_SEED).register();

        // manual
        new ManualCraftingTable(FinalTechMenus.MENU_MANUAL_MACHINE, FinalTechItems.MANUAL_CRAFTING_TABLE, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.MANUAL_CRAFTING_TABLE).register();
        new ManualEnhancedCraftingTable(FinalTechMenus.MENU_MANUAL_MACHINE, FinalTechItems.MANUAL_ENHANCED_CRAFTING_TABLE, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.MANUAL_ENHANCED_CRAFTING_TABLE).register();
        new ManualGrindStone(FinalTechMenus.MENU_MANUAL_MACHINE, FinalTechItems.MANUAL_GRIND_STONE, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.MANUAL_GRIND_STONE).register();
        new ManualArmorForge(FinalTechMenus.MENU_MANUAL_MACHINE, FinalTechItems.MANUAL_ARMOR_FORGE, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.MANUAL_ARMOR_FORGE).register();
        new ManualOreCrusher(FinalTechMenus.MENU_MANUAL_MACHINE, FinalTechItems.MANUAL_ORE_CRUSHER, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.MANUAL_ORE_CRUSHER).register();
        new ManualCompressor(FinalTechMenus.MENU_MANUAL_MACHINE, FinalTechItems.MANUAL_COMPRESSOR, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.MANUAL_COMPRESSOR).register();
        new ManualSmeltery(FinalTechMenus.MENU_MANUAL_MACHINE, FinalTechItems.MANUAL_SMELTERY, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.MANUAL_SMELTERY).register();
        new ManualPressureChamber(FinalTechMenus.MENU_MANUAL_MACHINE, FinalTechItems.MANUAL_PRESSURE_CHAMBER, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.MANUAL_PRESSURE_CHAMBER).register();
        new ManualMagicWorkbench(FinalTechMenus.MENU_MANUAL_MACHINE, FinalTechItems.MANUAL_MAGIC_WORKBENCH, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.MANUAL_MAGIC_WORKBENCH).register();
        new ManualOreWasher(FinalTechMenus.MENU_MANUAL_MACHINE, FinalTechItems.MANUAL_ORE_WASHER, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.MANUAL_ORE_WASHER).register();
        new ManualComposter(FinalTechMenus.MENU_MANUAL_MACHINE, FinalTechItems.MANUAL_COMPOSTER, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.MANUAL_COMPOSTER).register();
        new ManualGoldPan(FinalTechMenus.MENU_MANUAL_MACHINE, FinalTechItems.MANUAL_GOLD_PAN, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.MANUAL_GOLD_PAN).register();
        new ManualCrucible(FinalTechMenus.MENU_MANUAL_MACHINE, FinalTechItems.MANUAL_CRUCIBLE, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.MANUAL_CRUCIBLE).register();
        new ManualJuicer(FinalTechMenus.MENU_MANUAL_MACHINE, FinalTechItems.MANUAL_JUICER, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.MANUAL_JUICER).register();
        new ManualAncientAltar(FinalTechMenus.MENU_MANUAL_MACHINE, FinalTechItems.MANUAL_ANCIENT_ALTAR, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.MANUAL_ANCIENT_ALTAR).register();
        new ManualHeatedPressureChamber(FinalTechMenus.MENU_MANUAL_MACHINE, FinalTechItems.MANUAL_HEATED_PRESSURE_CHAMBER, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.MANUAL_HEATED_PRESSURE_CHAMBER).register();

        /* basic machine */
        new BasicCobbleFactory(FinalTechMenus.MENU_BASIC_MACHINE, FinalTechItems.BASIC_COBBLE_FACTORY, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.BASIC_COBBLE_FACTORY).register();
        new BasicDustFactory(FinalTechMenus.MENU_BASIC_MACHINE, FinalTechItems.BASIC_DUST_FACTORY, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.BASIC_DUST_FACTORY).register();

        /* advanced machine */
        new AdvancedComposter(FinalTechMenus.MENU_ADVANCED_MACHINE, FinalTechItems.ADVANCED_COMPOSTER, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.ADVANCED_COMPOSTER).register();
        new AdvancedJuicer(FinalTechMenus.MENU_ADVANCED_MACHINE, FinalTechItems.ADVANCED_JUICER, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.ADVANCED_JUICER).register();
        new AdvancedElectricFurnace(FinalTechMenus.MENU_ADVANCED_MACHINE, FinalTechItems.ADVANCED_ELECTRIC_FURNACE, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.ADVANCED_ELECTRIC_FURNACE).register();
        new AdvancedGoldPan(FinalTechMenus.MENU_ADVANCED_MACHINE, FinalTechItems.ADVANCED_GOLD_PAN, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.ADVANCED_GOLD_PAN).register();
        new AdvancedElectricDustWasher(FinalTechMenus.MENU_ADVANCED_MACHINE, FinalTechItems.ADVANCED_ELECTRIC_DUST_WASHER, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.ADVANCED_ELECTRIC_DUST_WASHER).register();
        new AdvancedElectricIngotFactory(FinalTechMenus.MENU_ADVANCED_MACHINE, FinalTechItems.ADVANCED_ELECTRIC_INGOT_FACTORY, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.ADVANCED_ELECTRIC_INGOT_FACTORY).register();
        new AdvancedElectricCrucible(FinalTechMenus.MENU_ADVANCED_MACHINE, FinalTechItems.ADVANCED_ELECTRIC_CRUCIBLE, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.ADVANCED_ELECTRIC_CRUCIBLE).register();
        new AdvancedElectricOreGrinder(FinalTechMenus.MENU_ADVANCED_MACHINE, FinalTechItems.ADVANCED_ELECTRIC_ORE_GRINDER, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.ADVANCED_ELECTRIC_ORE_GRINDER).register();
        new AdvancedHeatedPressureChamber(FinalTechMenus.MENU_ADVANCED_MACHINE, FinalTechItems.ADVANCED_HEATED_PRESSURE_CHAMBER, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.ADVANCED_HEATED_PRESSURE_CHAMBER).register();
        new AdvancedElectricIngotPulverizer(FinalTechMenus.MENU_ADVANCED_MACHINE, FinalTechItems.ADVANCED_ELECTRIC_INGOT_PULVERIZER, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.ADVANCED_ELECTRIC_INGOT_PULVERIZER).register();
        new AdvancedAutoDrier(FinalTechMenus.MENU_ADVANCED_MACHINE, FinalTechItems.ADVANCED_AUTO_DRIER, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.ADVANCED_AUTO_DRIER).register();
        new AdvancedElectricPress(FinalTechMenus.MENU_ADVANCED_MACHINE, FinalTechItems.ADVANCED_ELECTRIC_PRESS, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.ADVANCED_ELECTRIC_PRESS).register();
        new AdvancedFoodFactory(FinalTechMenus.MENU_ADVANCED_MACHINE, FinalTechItems.ADVANCED_FOOD_FACTORY, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.ADVANCED_FOOD_FACTORY).register();
        new AdvancedFreezer(FinalTechMenus.MENU_ADVANCED_MACHINE, FinalTechItems.ADVANCED_FREEZER, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.ADVANCED_FREEZER).register();
        new AdvancedCarbonPress(FinalTechMenus.MENU_ADVANCED_MACHINE, FinalTechItems.ADVANCED_CARBON_PRESS, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.ADVANCED_CARBON_PRESS).register();
        new AdvancedElectricSmeltery(FinalTechMenus.MENU_ADVANCED_MACHINE, FinalTechItems.ADVANCED_ELECTRIC_SMELTERY, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.ADVANCED_ELECTRIC_SMELTERY).register();
        new AdvancedDustFactory(FinalTechMenus.MENU_ADVANCED_MACHINE, FinalTechItems.ADVANCED_DUST_FACTORY, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.ADVANCED_DUST_FACTORY).register();

        // conversion
        new DustConversion(FinalTechMenus.MENU_CONVERSION_MACHINE, FinalTechItems.DUST_CONVERSION, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.DUST_CONVERSION).register();
        new GravelConversion(FinalTechMenus.MENU_CONVERSION_MACHINE, FinalTechItems.GRAVEL_CONVERSION, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.GRAVEL_CONVERSION).register();
        new SoulSandConversion(FinalTechMenus.MENU_CONVERSION_MACHINE, FinalTechItems.SOUL_SAND_CONVERSION, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.SOUL_SAND_CONVERSION).register();
        new ConcreteConversion(FinalTechMenus.MENU_CONVERSION_MACHINE, FinalTechItems.CONCRETE_CONVERSION, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.CONCRETE_CONVERSION).register();
        new GlassConversion(FinalTechMenus.MENU_CONVERSION_MACHINE, FinalTechItems.GLASS_CONVERSION, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.GLASS_CONVERSION).register();
        new WoolConversion(FinalTechMenus.MENU_CONVERSION_MACHINE, FinalTechItems.WOOL_CONVERSION, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.WOOL_CONVERSION).register();
        new WaterConversion(FinalTechMenus.MENU_CONVERSION_MACHINE, FinalTechItems.WATER_CONVERSION, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.WATER_CONVERSION).register();
        new RuneConversion(FinalTechMenus.MENU_CONVERSION_MACHINE, FinalTechItems.RUNE_CONVERSION, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.RUNE_CONVERSION).register();

        // extraction
        new OreExtraction(FinalTechMenus.MENU_EXTRACTION_MACHINE, FinalTechItems.ORE_EXTRACTION, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.ORE_EXTRACTION).register();

        // generator
        new StoneGenerator(FinalTechMenus.MENU_GENERATOR_MACHINE, FinalTechItems.STONE_GENERATOR, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.STONE_GENERATOR).register();
        new RawStoneGenerator(FinalTechMenus.MENU_GENERATOR_MACHINE, FinalTechItems.RAW_STONE_GENERATOR, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.RAW_STONE_GENERATOR).register();
        new NetherStoneGenerator(FinalTechMenus.MENU_GENERATOR_MACHINE, FinalTechItems.NETHER_STONE_GENERATOR, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.NETHER_STONE_GENERATOR).register();
        new PlankGenerator(FinalTechMenus.MENU_GENERATOR_MACHINE, FinalTechItems.PLANK_GENERATOR, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.PLANK_GENERATOR).register();
        new SandGenerator(FinalTechMenus.MENU_GENERATOR_MACHINE, FinalTechItems.SAND_GENERATOR, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.SAND_GENERATOR).register();
        new LiquidCardGenerator(FinalTechMenus.MENU_GENERATOR_MACHINE, FinalTechItems.LIQUID_CARD_GENERATOR, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.LIQUID_CARD_GENERATOR).register();

        /* most powerful item */
        new InfinityMachineChargeCard(FinalTechMenus.MENU_FINAL_ITEM, FinalTechItems.MACHINE_CHARGE_CARD_INFINITY, FinalTechRecipes.RECIPE_TYPE_MATRIX_CRAFTING_TABLE, FinalTechRecipes.MACHINE_CHARGE_CARD_INFINITY).register();
        new InfinityMachineAccelerateCard(FinalTechMenus.MENU_FINAL_ITEM, FinalTechItems.MACHINE_ACCELERATE_CARD_INFINITY, FinalTechRecipes.RECIPE_TYPE_MATRIX_CRAFTING_TABLE, FinalTechRecipes.MACHINE_ACCELERATE_CARD_INFINITY).register();
        new MatrixMachineActivateCard(FinalTechMenus.MENU_FINAL_ITEM, FinalTechItems.MACHINE_ACTIVATE_CARD_L4, FinalTechRecipes.RECIPE_TYPE_MATRIX_CRAFTING_TABLE, FinalTechRecipes.MACHINE_ACTIVATE_CARD_L4).register();
        new AdvancedAutoCraft(FinalTechMenus.MENU_FINAL_ITEM, FinalTechItems.ADVANCED_AUTO_CRAFT, FinalTechRecipes.RECIPE_TYPE_MATRIX_CRAFTING_TABLE, FinalTechRecipes.ADVANCED_AUTO_CRAFT).register();
        new MatrixExpandedCapacitor(FinalTechMenus.MENU_FINAL_ITEM, FinalTechItems.MATRIX_EXPANDED_CAPACITOR, FinalTechRecipes.RECIPE_TYPE_MATRIX_CRAFTING_TABLE, FinalTechRecipes.MATRIX_EXPANDED_CAPACITOR).register();
        new MatrixGenerator(FinalTechMenus.MENU_FINAL_ITEM, FinalTechItems.MATRIX_GENERATOR, FinalTechRecipes.RECIPE_TYPE_MATRIX_CRAFTING_TABLE, FinalTechRecipes.MATRIX_GENERATOR).register();
        new MatrixAccelerator(FinalTechMenus.MENU_FINAL_ITEM, FinalTechItems.MATRIX_ACCELERATOR, FinalTechRecipes.RECIPE_TYPE_MATRIX_CRAFTING_TABLE, FinalTechRecipes.MATRIX_ACCELERATOR).register();
        new MatrixReactor(FinalTechMenus.MENU_FINAL_ITEM, FinalTechItems.MATRIX_REACTOR, FinalTechRecipes.RECIPE_TYPE_MATRIX_CRAFTING_TABLE, FinalTechRecipes.MATRIX_REACTOR).register();

        /* Researches */

        SlimefunUtil.setSingleResearch(FinalTechItems.GEARWHEEL, Integer.parseInt(ItemValueTable.getInstance().BASE_INPUT_VALUE), false);
        SlimefunUtil.setResearches(FinalTech.getInstance(), "ORDER_DUST", "ORDER_DUST".hashCode(), FinalTech.getLanguageString("research", "ORDER_DUST"), Slimefun.getInstalledAddons().size(), true, FinalTechItems.ORDERED_DUST, FinalTechItems.UNORDERED_DUST);

        SlimefunUtil.setResearchBySlimefunItems(FinalTechItems.MANUAL_GRIND_STONE, SlimefunItems.GRIND_STONE);
        SlimefunUtil.setResearchBySlimefunItems(FinalTechItems.MANUAL_ARMOR_FORGE, SlimefunItems.ARMOR_FORGE);
        SlimefunUtil.setResearchBySlimefunItems(FinalTechItems.MANUAL_ORE_CRUSHER, SlimefunItems.ORE_CRUSHER);
        SlimefunUtil.setResearchBySlimefunItems(FinalTechItems.MANUAL_COMPRESSOR, SlimefunItems.COMPRESSOR);
        SlimefunUtil.setResearchBySlimefunItems(FinalTechItems.MANUAL_SMELTERY, SlimefunItems.SMELTERY);
        SlimefunUtil.setResearchBySlimefunItems(FinalTechItems.MANUAL_PRESSURE_CHAMBER, SlimefunItems.PRESSURE_CHAMBER);
        SlimefunUtil.setResearchBySlimefunItems(FinalTechItems.MANUAL_MAGIC_WORKBENCH, SlimefunItems.MAGIC_WORKBENCH);
        SlimefunUtil.setResearchBySlimefunItems(FinalTechItems.MANUAL_ORE_WASHER, SlimefunItems.ORE_WASHER);
        SlimefunUtil.setResearchBySlimefunItems(FinalTechItems.MANUAL_COMPOSTER, SlimefunItems.COMPOSTER);
        SlimefunUtil.setResearchBySlimefunItems(FinalTechItems.MANUAL_GOLD_PAN, SlimefunItems.GOLD_PAN);
        SlimefunUtil.setResearchBySlimefunItems(FinalTechItems.MANUAL_CRUCIBLE, SlimefunItems.CRUCIBLE);
        SlimefunUtil.setResearchBySlimefunItems(FinalTechItems.MANUAL_JUICER, SlimefunItems.JUICER);
        SlimefunUtil.setResearchBySlimefunItems(FinalTechItems.MANUAL_ANCIENT_ALTAR, SlimefunItems.ANCIENT_ALTAR);
        SlimefunUtil.setResearchBySlimefunItems(FinalTechItems.MANUAL_HEATED_PRESSURE_CHAMBER, SlimefunItems.HEATED_PRESSURE_CHAMBER);

        /* Listeners */
        PluginManager pluginManager = FinalTech.getInstance().getServer().getPluginManager();
        pluginManager.registerEvents(new ShineListener(), FinalTech.getInstance());
    }

    @Nonnull
    public static BlockTicker generateBlockTicker(@Nonnull BlockTicker blockTicker, boolean forceAsync, boolean antiAcceleration, boolean performanceLimit) {
        if(forceAsync && antiAcceleration && performanceLimit) {
            return new BlockTicker() {
                private static final RunnableLockFactory<Location> runnableLockFactory = FinalTech.getLocationRunnableFactory();

                @Override
                public boolean isSynchronized() {
                    return blockTicker.isSynchronized();
                }

                @Override
                public void tick(Block b, SlimefunItem item, Config data) {
                    if(AntiAccelerationUtil.isAccelerated(data) && PerformanceLimitUtil.charge(data)) {
                        runnableLockFactory.waitThenRun(() -> blockTicker.tick(b, item, data), b.getLocation());
                    }
                }
            };
        } else if(forceAsync && antiAcceleration && !performanceLimit) {
            return new BlockTicker() {
                private static final RunnableLockFactory<Location> runnableLockFactory = FinalTech.getLocationRunnableFactory();

                @Override
                public boolean isSynchronized() {
                    return blockTicker.isSynchronized();
                }

                @Override
                public void tick(Block b, SlimefunItem item, Config data) {
                    if(AntiAccelerationUtil.isAccelerated(data)) {
                        runnableLockFactory.waitThenRun(() -> blockTicker.tick(b, item, data), b.getLocation());
                    }
                }
            };
        } else if(forceAsync && !antiAcceleration && performanceLimit) {
            return new BlockTicker() {
                private static final RunnableLockFactory<Location> runnableLockFactory = FinalTech.getLocationRunnableFactory();

                @Override
                public boolean isSynchronized() {
                    return blockTicker.isSynchronized();
                }

                @Override
                public void tick(Block b, SlimefunItem item, Config data) {
                    if(PerformanceLimitUtil.charge(data)) {
                        runnableLockFactory.waitThenRun(() -> blockTicker.tick(b, item, data), b.getLocation());
                    }
                }
            };
        } else if(forceAsync && !antiAcceleration && !performanceLimit) {
            return new BlockTicker() {
                private static final RunnableLockFactory<Location> runnableLockFactory = FinalTech.getLocationRunnableFactory();

                @Override
                public boolean isSynchronized() {
                    return blockTicker.isSynchronized();
                }

                @Override
                public void tick(Block b, SlimefunItem item, Config data) {
                    runnableLockFactory.waitThenRun(() -> blockTicker.tick(b, item, data), b.getLocation());
                }
            };
        } else if(!forceAsync && antiAcceleration && performanceLimit) {
            return new BlockTicker() {
                @Override
                public boolean isSynchronized() {
                    return blockTicker.isSynchronized();
                }

                @Override
                public void tick(Block b, SlimefunItem item, Config data) {
                    if(AntiAccelerationUtil.isAccelerated(data) && PerformanceLimitUtil.charge(data)) {
                        blockTicker.tick(b, item, data);
                    }
                }
            };
        } else if(!forceAsync && antiAcceleration && !performanceLimit) {
            return new BlockTicker() {
                @Override
                public boolean isSynchronized() {
                    return blockTicker.isSynchronized();
                }

                @Override
                public void tick(Block b, SlimefunItem item, Config data) {
                    if(AntiAccelerationUtil.isAccelerated(data)) {
                        blockTicker.tick(b, item, data);
                    }
                }
            };
        } else if(!forceAsync && !antiAcceleration && performanceLimit) {
            return new BlockTicker() {
                @Override
                public boolean isSynchronized() {
                    return blockTicker.isSynchronized();
                }

                @Override
                public void tick(Block b, SlimefunItem item, Config data) {
                    if(PerformanceLimitUtil.charge(data)) {
                        blockTicker.tick(b, item, data);
                    }
                }
            };
        } else {
            return blockTicker;
        }
    }
}
