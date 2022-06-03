package io.taraxacum.finaltech.setup;

import io.github.thebusybiscuit.slimefun4.api.SlimefunAddon;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.implementation.Slimefun;
import io.github.thebusybiscuit.slimefun4.libraries.dough.config.Config;
import io.taraxacum.finaltech.FinalTech;
import io.taraxacum.finaltech.api.command.ShowItemValue;
import io.taraxacum.finaltech.api.command.TransferToCopyCardItem;
import io.taraxacum.finaltech.core.items.machine.*;
import io.taraxacum.finaltech.core.items.machine.cargo.unit.*;
import io.taraxacum.finaltech.core.items.machine.manual.EquivalentExchangeTable;
import io.taraxacum.finaltech.core.items.machine.manual.craft.MatrixCraftingTable;
import io.taraxacum.finaltech.core.items.machine.standard.*;
import io.taraxacum.finaltech.core.items.machine.standard.basic.*;
import io.taraxacum.finaltech.core.items.unusable.*;
import io.taraxacum.finaltech.core.items.unusable.laquid.LavaCard;
import io.taraxacum.finaltech.core.items.unusable.laquid.MilkCard;
import io.taraxacum.finaltech.core.items.unusable.laquid.WaterCard;
import io.taraxacum.finaltech.core.items.usable.LocationRecorder;
import io.taraxacum.finaltech.core.items.usable.MenuViewer;
import io.taraxacum.finaltech.core.items.usable.accelerate.*;
import io.taraxacum.finaltech.core.items.machine.capacitor.AdvancedChargeIncreaseCapacitor;
import io.taraxacum.finaltech.core.items.machine.capacitor.AdvancedConsumeReduceCapacitor;
import io.taraxacum.finaltech.core.items.machine.range.area.EnergizedAccelerator;
import io.taraxacum.finaltech.core.items.machine.range.area.MatrixAccelerator;
import io.taraxacum.finaltech.core.items.machine.range.area.OverloadedAccelerator;
import io.taraxacum.finaltech.core.items.machine.range.area.EscapeCapacitor;
import io.taraxacum.finaltech.core.items.machine.range.area.generator.*;
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
import io.taraxacum.finaltech.core.items.machine.standard.advanced.*;
import io.taraxacum.finaltech.util.TextUtil;

import javax.annotation.Nonnull;

/**
 * @author Final_ROOT
 * @since 1.0
 */
public final class SetupUtil {
    public static void init() {
        FinalTech finalTech = FinalTech.getInstance();

        // setup config

        Config configFile = finalTech.getConfigFile();

        if(configFile.contains("machine.multi-thread-level")) {
            AbstractMachine.MULTI_THREAD_LEVEL = configFile.getInt("machine.multi-thread-level");
        } else {
            configFile.setValue("machine.multi-thread-level", AbstractMachine.MULTI_THREAD_LEVEL);
        }

        if(AbstractMachine.MULTI_THREAD_LEVEL == 0) {
            finalTech.getServer().getLogger().info(TextUtil.COLOR_STRESS + "[FINALTECH]" + TextUtil.COLOR_NEGATIVE + "已禁用多线程优化");
        } else if(AbstractMachine.MULTI_THREAD_LEVEL == 1) {
            finalTech.getServer().getLogger().info(TextUtil.COLOR_STRESS + "[FINALTECH]" + TextUtil.COLOR_NEGATIVE + "已启用一级多线程优化");
        } else if(AbstractMachine.MULTI_THREAD_LEVEL == 2) {
            finalTech.getServer().getLogger().info(TextUtil.COLOR_STRESS + "[FINALTECH]" + TextUtil.COLOR_NEGATIVE + "二级多线程优化暂不支持");
        } else {
            AbstractMachine.MULTI_THREAD_LEVEL = 0;
            finalTech.getServer().getLogger().info(TextUtil.COLOR_STRESS + "[FINALTECH]" + TextUtil.COLOR_NEGATIVE + "已禁用多线程优化");
        }

        if(configFile.contains("manual.count-threshold")) {
            AbstractManualCraftMachine.COUNT_THRESHOLD = configFile.getInt("manual.count-threshold");
        } else {
            configFile.setValue("manual.count-threshold", AbstractManualCraftMachine.COUNT_THRESHOLD);
        }

        // setup values

        Config valueConfig = finalTech.getValueFile();
        int singularityDifficulty = Singularity.DEFAULT_SINGULARITY_DIFFICULTY;
        if (valueConfig.contains("constructor.singularity")) {
            singularityDifficulty = valueConfig.getInt("constructor.singularity");
        } else {
            valueConfig.setValue("constructor.singularity", Singularity.DEFAULT_SINGULARITY_DIFFICULTY);
        }
        singularityDifficulty += Slimefun.getInstalledAddons().size() * 16;
        Singularity.SINGULARITY_DIFFICULTY = singularityDifficulty;

        int spirocheteDifficulty = Spirochete.DEFAULT_SPIROCHETE_DIFFICULTY;
        if (valueConfig.contains("constructor.spirochete")) {
            spirocheteDifficulty = valueConfig.getInt("constructor.spirochete");
        } else {
            valueConfig.setValue("constructor.spirochete", Spirochete.DEFAULT_SPIROCHETE_DIFFICULTY);
        }
        spirocheteDifficulty += ((Double)(Math.pow(Slimefun.getRegistry().getAllSlimefunItems().size(), 0.5))).intValue() * 2;
        Spirochete.SPIROCHETE_DIFFICULTY = spirocheteDifficulty;

        int copyCardDifficulty = CopyCardItem.DIFFICULTY;
        if (valueConfig.contains("constructor.copy-card")) {
            copyCardDifficulty = valueConfig.getInt("constructor.copy-card");
        } else {
            valueConfig.setValue("constructor.copy-card", CopyCardItem.DIFFICULTY);
        }
        CopyCardItem.DIFFICULTY = copyCardDifficulty;

        if (valueConfig.contains("cargo.link-search-distance")) {
            LinkTransfer.BLOCK_SEARCH_LIMIT = valueConfig.getInt("cargo.link-search-distance");
        } else {
            valueConfig.setValue("cargo.link-search-distance", LinkTransfer.BLOCK_SEARCH_LIMIT);
        }

        if (valueConfig.contains("matrix-reactor.difficulty")) {
            MatrixReactor.DIFFICULTY = valueConfig.getInt("matrix-reactor.difficulty");
        } else {
            valueConfig.setValue("matrix-reactor.difficulty", MatrixReactor.DIFFICULTY);
        }

        // setup menu

        FinalTechMenus.MAIN_MENU.setTier(0);
        FinalTechMenus.MAIN_MENU.register(finalTech);

        FinalTechMenus.MENU_MATERIAL.setTier(0);
        FinalTechMenus.MENU_TOOL.setTier(0);
        FinalTechMenus.MENU_CARGO.setTier(0);
        FinalTechMenus.MENU_ELECTRIC.setTier(0);
        FinalTechMenus.MENU_FUNCTION_MACHINE.setTier(0);
        FinalTechMenus.MENU_BASIC_MACHINE.setTier(0);
        FinalTechMenus.MENU_ADVANCED_MACHINE.setTier(0);
        FinalTechMenus.MENU_FINAL_ITEM.setTier(0);

        // setup command

        finalTech.getCommand("finaltech-copy-card").setExecutor(new TransferToCopyCardItem());
        finalTech.getCommand("finaltech-item-value").setExecutor(new ShowItemValue());
    }

    public static void setupItems(@Nonnull SlimefunAddon slimefunAddon) {

        // material
        new WaterCard(FinalTechMenus.MENU_MATERIAL, FinalTechItems.WATER_CARD, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.WATER_CARD).register();
        new LavaCard(FinalTechMenus.MENU_MATERIAL, FinalTechItems.LAVA_CARD, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.LAVA_CARD).register();
        new MilkCard(FinalTechMenus.MENU_MATERIAL, FinalTechItems.MILK_CARD, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.MILK_CARD).register();
        new Gearwheel(FinalTechMenus.MENU_MATERIAL, FinalTechItems.GEARWHEEL, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.GEARWHEEL, new SlimefunItemStack(FinalTechItems.GEARWHEEL, 4)).register(slimefunAddon);
        new UnorderedDust(FinalTechMenus.MENU_MATERIAL, FinalTechItems.UNORDERED_DUST,  FinalTechRecipes.RECIPE_TYPE_ORDERED_DUST_FACTORY, FinalTechRecipes.UNORDERED_DUST).register();
        new OrderedDust(FinalTechMenus.MENU_MATERIAL, FinalTechItems.ORDERED_DUST, FinalTechRecipes.RECIPE_TYPE_ORDERED_DUST_FACTORY, FinalTechRecipes.ORDERED_DUST).register();
        new Bug(FinalTechMenus.MENU_MATERIAL, FinalTechItems.BUG, FinalTechRecipes.RECIPE_TYPE_EQUIVALENT_EXCHANGE_TABLE, FinalTechRecipes.BUG).register();
        new QuantityModule(FinalTechMenus.MENU_MATERIAL, FinalTechItems.QUANTITY_MODULE, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.QUANTITY_MODULE).register();
        new CopyCardItem(FinalTechMenus.MENU_MATERIAL, FinalTechItems.COPY_CARD, FinalTechRecipes.RECIPE_TYPE_ITEM_SERIALIZATION_CONSTRUCTOR, FinalTechRecipes.COPY_CARD).register();
        new Annular(FinalTechMenus.MENU_MATERIAL, FinalTechItems.ANNULAR, FinalTechRecipes.RECIPE_TYPE_CARD_OPERATION_PORT, FinalTechRecipes.ANNULAR).register();
        new QuantityModuleInfinity(FinalTechMenus.MENU_MATERIAL, FinalTechItems.QUANTITY_MODULE_INFINITY, FinalTechRecipes.RECIPE_TYPE_MATRIX_CRAFTING_TABLE, FinalTechRecipes.QUANTITY_MODULE_INFINITY).register();
        new Singularity(FinalTechMenus.MENU_MATERIAL, FinalTechItems.SINGULARITY, FinalTechRecipes.RECIPE_TYPE_ITEM_SERIALIZATION_CONSTRUCTOR, FinalTechRecipes.SINGULARITY).register(slimefunAddon);
        new Spirochete(FinalTechMenus.MENU_MATERIAL, FinalTechItems.SPIROCHETE, FinalTechRecipes.RECIPE_TYPE_ITEM_SERIALIZATION_CONSTRUCTOR, FinalTechRecipes.SPIROCHETE).register(slimefunAddon);
        new Shell(FinalTechMenus.MENU_MATERIAL, FinalTechItems.SHELL, FinalTechRecipes.RECIPE_TYPE_CARD_OPERATION_PORT, FinalTechRecipes.SHELL).register(slimefunAddon);
        new ItemPhony(FinalTechMenus.MENU_MATERIAL, FinalTechItems.PHONY, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.PHONY).register(slimefunAddon);

        // tool
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

        // cargo and storage
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

        // electric
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
        new EnergizedAccelerator(FinalTechMenus.MENU_ELECTRIC, FinalTechItems.ENERGIZED_ACCELERATOR, FinalTechRecipes.RECIPE_TYPE_MATRIX_CRAFTING_TABLE, FinalTechRecipes.ENERGIZED_ACCELERATOR).register();
        new OverloadedAccelerator(FinalTechMenus.MENU_ELECTRIC, FinalTechItems.OVERLOADED_ACCELERATOR, FinalTechRecipes.RECIPE_TYPE_MATRIX_CRAFTING_TABLE, FinalTechRecipes.OVERLOADED_ACCELERATOR).register();

        // function machine
        new DustFactoryDirt(FinalTechMenus.MENU_FUNCTION_MACHINE, FinalTechItems.ORDERED_DUST_FACTORY_DIRT, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.ORDERED_DUST_FACTORY_DIRT).register();
        new DustFactoryStone(FinalTechMenus.MENU_FUNCTION_MACHINE, FinalTechItems.ORDERED_DUST_FACTORY_STONE, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.ORDERED_DUST_FACTORY_STONE).register();
        new ItemSerializationConstructor(FinalTechMenus.MENU_FUNCTION_MACHINE, FinalTechItems.ITEM_SERIALIZATION_CONSTRUCTOR, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.ALL_COMPRESSION).register();
        new ItemDeserializeParser(FinalTechMenus.MENU_FUNCTION_MACHINE, FinalTechItems.ITEM_DESERIALIZE_PARSER, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.ALL_FACTORY).register();
        new MatrixCraftingTable(FinalTechMenus.MENU_FUNCTION_MACHINE, FinalTechItems.MATRIX_CRAFTING_TABLE, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.MATRIX_CRAFTING_TABLE).register();
        new CardOperationTable(FinalTechMenus.MENU_FUNCTION_MACHINE, FinalTechItems.CARD_OPERATION_PORT, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.CARD_OPERATION_PORT).register();
        new ItemDismantleTable(FinalTechMenus.MENU_FUNCTION_MACHINE, FinalTechItems.ITEM_DISMANTLE_TABLE, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.ITEM_DISMANTLE_TABLE).register();
        new EquivalentExchangeTable(FinalTechMenus.MENU_FUNCTION_MACHINE, FinalTechItems.EQUIVALENT_EXCHANGE_TABLE, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.EQUIVALENT_EXCHANGE_TABLE).register();

        // basic machine
        new BasicCobbleFactory(FinalTechMenus.MENU_BASIC_MACHINE, FinalTechItems.BASIC_COBBLE_FACTORY, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.BASIC_COBBLE_FACTORY).register();
        new BasicOreFactory(FinalTechMenus.MENU_BASIC_MACHINE, FinalTechItems.BASIC_ORE_FACTORY, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.BASIC_ORE_FACTORY).register();
        new BasicDustFactory(FinalTechMenus.MENU_BASIC_MACHINE, FinalTechItems.BASIC_DUST_FACTORY, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.BASIC_DUST_FACTORY).register();
        new BasicFarmFactory(FinalTechMenus.MENU_BASIC_MACHINE, FinalTechItems.BASIC_FARM_FACTORY, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.BASIC_FARM_FACTORY).register();
        new BasicLiquidFactory(FinalTechMenus.MENU_BASIC_MACHINE, FinalTechItems.BASIC_LIQUID_FACTORY, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.BASIC_LIQUID_FACTORY).register();
        new ManualCraftingTable(FinalTechMenus.MENU_BASIC_MACHINE, FinalTechItems.MANUAL_CRAFTING_TABLE, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.MANUAL_CRAFTING_TABLE).register();
        new ManualEnhancedCraftingTable(FinalTechMenus.MENU_BASIC_MACHINE, FinalTechItems.MANUAL_ENHANCED_CRAFTING_TABLE, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.MANUAL_ENHANCED_CRAFTING_TABLE).register();
        new ManualGrindStone(FinalTechMenus.MENU_BASIC_MACHINE, FinalTechItems.MANUAL_GRIND_STONE, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.MANUAL_GRIND_STONE).register();
        new ManualArmorForge(FinalTechMenus.MENU_BASIC_MACHINE, FinalTechItems.MANUAL_ARMOR_FORGE, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.MANUAL_ARMOR_FORGE).register();
        new ManualOreCrusher(FinalTechMenus.MENU_BASIC_MACHINE, FinalTechItems.MANUAL_ORE_CRUSHER, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.MANUAL_ORE_CRUSHER).register();
        new ManualCompressor(FinalTechMenus.MENU_BASIC_MACHINE, FinalTechItems.MANUAL_COMPRESSOR, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.MANUAL_COMPRESSOR).register();
        new ManualSmeltery(FinalTechMenus.MENU_BASIC_MACHINE, FinalTechItems.MANUAL_SMELTERY, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.MANUAL_SMELTERY).register();
        new ManualPressureChamber(FinalTechMenus.MENU_BASIC_MACHINE, FinalTechItems.MANUAL_PRESSURE_CHAMBER, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.MANUAL_PRESSURE_CHAMBER).register();
        new ManualMagicWorkbench(FinalTechMenus.MENU_BASIC_MACHINE, FinalTechItems.MANUAL_MAGIC_WORKBENCH, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.MANUAL_MAGIC_WORKBENCH).register();
        new ManualOreWasher(FinalTechMenus.MENU_BASIC_MACHINE, FinalTechItems.MANUAL_ORE_WASHER, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.MANUAL_ORE_WASHER).register();
        new ManualComposter(FinalTechMenus.MENU_BASIC_MACHINE, FinalTechItems.MANUAL_COMPOSTER, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.MANUAL_COMPOSTER).register();
        new ManualGoldPan(FinalTechMenus.MENU_BASIC_MACHINE, FinalTechItems.MANUAL_GOLD_PAN, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.MANUAL_GOLD_PAN).register();
        new ManualCrucible(FinalTechMenus.MENU_BASIC_MACHINE, FinalTechItems.MANUAL_CRUCIBLE, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.MANUAL_CRUCIBLE).register();
        new ManualJuicer(FinalTechMenus.MENU_BASIC_MACHINE, FinalTechItems.MANUAL_JUICER, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.MANUAL_JUICER).register();
        new ManualAncientAltar(FinalTechMenus.MENU_BASIC_MACHINE, FinalTechItems.MANUAL_ANCIENT_ALTAR, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.MANUAL_ANCIENT_ALTAR).register();
        new ManualHeatedPressureChamber(FinalTechMenus.MENU_BASIC_MACHINE, FinalTechItems.MANUAL_HEATED_PRESSURE_CHAMBER, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.MANUAL_HEATED_PRESSURE_CHAMBER).register();

        // advanced machine
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
        new AdvancedOreFactory(FinalTechMenus.MENU_ADVANCED_MACHINE, FinalTechItems.ADVANCED_ORE_FACTORY, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.ADVANCED_ORE_FACTORY).register();
        new AdvancedDustFactory(FinalTechMenus.MENU_ADVANCED_MACHINE, FinalTechItems.ADVANCED_DUST_FACTORY, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.ADVANCED_DUST_FACTORY).register();
        new AdvancedFarmFactory(FinalTechMenus.MENU_ADVANCED_MACHINE, FinalTechItems.ADVANCED_FARM_FACTORY, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.ADVANCED_FARM_FACTORY).register();

        // most powerful item
        new InfinityMachineChargeCard(FinalTechMenus.MENU_FINAL_ITEM, FinalTechItems.MACHINE_CHARGE_CARD_INFINITY, FinalTechRecipes.RECIPE_TYPE_MATRIX_CRAFTING_TABLE, FinalTechRecipes.MACHINE_CHARGE_CARD_INFINITY).register();
        new InfinityMachineAccelerateCard(FinalTechMenus.MENU_FINAL_ITEM, FinalTechItems.MACHINE_ACCELERATE_CARD_INFINITY, FinalTechRecipes.RECIPE_TYPE_MATRIX_CRAFTING_TABLE, FinalTechRecipes.MACHINE_ACCELERATE_CARD_INFINITY).register();
        new MatrixMachineActivateCard(FinalTechMenus.MENU_FINAL_ITEM, FinalTechItems.MACHINE_ACTIVATE_CARD_L4, FinalTechRecipes.RECIPE_TYPE_MATRIX_CRAFTING_TABLE, FinalTechRecipes.MACHINE_ACTIVATE_CARD_L4).register();
        new AdvancedAutoCraft(FinalTechMenus.MENU_FINAL_ITEM, FinalTechItems.ADVANCED_AUTO_CRAFT, FinalTechRecipes.RECIPE_TYPE_MATRIX_CRAFTING_TABLE, FinalTechRecipes.ADVANCED_AUTO_CRAFT).register();
        new MatrixExpandedCapacitor(FinalTechMenus.MENU_FINAL_ITEM, FinalTechItems.MATRIX_EXPANDED_CAPACITOR, FinalTechRecipes.RECIPE_TYPE_MATRIX_CRAFTING_TABLE, FinalTechRecipes.MATRIX_EXPANDED_CAPACITOR).register();
        new MatrixGenerator(FinalTechMenus.MENU_FINAL_ITEM, FinalTechItems.MATRIX_GENERATOR, FinalTechRecipes.RECIPE_TYPE_MATRIX_CRAFTING_TABLE, FinalTechRecipes.MATRIX_GENERATOR).register();
        new MatrixAccelerator(FinalTechMenus.MENU_FINAL_ITEM, FinalTechItems.MATRIX_ACCELERATOR, FinalTechRecipes.RECIPE_TYPE_MATRIX_CRAFTING_TABLE, FinalTechRecipes.MATRIX_ACCELERATOR).register();
        new MatrixReactor(FinalTechMenus.MENU_FINAL_ITEM, FinalTechItems.MATRIX_REACTOR, FinalTechRecipes.RECIPE_TYPE_MATRIX_CRAFTING_TABLE, FinalTechRecipes.MATRIX_REACTOR).register();
    }
}
