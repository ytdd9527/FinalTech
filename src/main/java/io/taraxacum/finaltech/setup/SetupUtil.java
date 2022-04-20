package io.taraxacum.finaltech.setup;

import io.github.thebusybiscuit.slimefun4.api.SlimefunAddon;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.implementation.Slimefun;
import io.github.thebusybiscuit.slimefun4.libraries.dough.items.CustomItemStack;
import io.taraxacum.finaltech.item.unusable.CopyCardItem;
import io.taraxacum.finaltech.item.unusable.StorageCardItem;
import io.taraxacum.finaltech.item.unusable.UnusableSlimefunItem;
import io.taraxacum.finaltech.item.usable.accelerate.*;
import io.taraxacum.finaltech.machine.OrderedDustFactoryV2;
import io.taraxacum.finaltech.machine.OverclockFrameMachine;
import io.taraxacum.finaltech.machine.range.area.EnergizedAccelerator;
import io.taraxacum.finaltech.machine.range.area.MatrixAccelerator;
import io.taraxacum.finaltech.machine.range.area.OverloadedAccelerator;
import io.taraxacum.finaltech.machine.range.area.EscapeCapacitor;
import io.taraxacum.finaltech.machine.range.area.generator.*;
import io.taraxacum.finaltech.machine.capacitor.BasicChargeIncreaseCapacitor;
import io.taraxacum.finaltech.machine.capacitor.BasicConsumeReduceCapacitor;
import io.taraxacum.finaltech.machine.capacitor.BasicSelfGenerateCapacitor;
import io.taraxacum.finaltech.machine.capacitor.BasicVoidGenerateCapacitor;
import io.taraxacum.finaltech.machine.capacitor.expanded.*;
import io.taraxacum.finaltech.machine.cargo.*;
import io.taraxacum.finaltech.machine.cargo.storage.StorageInsertPort;
import io.taraxacum.finaltech.machine.cargo.storage.StorageInteractPort;
import io.taraxacum.finaltech.machine.cargo.storage.StorageWithdrawPort;
import io.taraxacum.finaltech.machine.generator.OrderedDustGenerator;
import io.taraxacum.finaltech.machine.manual.CardOperationPort;
import io.taraxacum.finaltech.machine.manual.craft.*;
import io.taraxacum.finaltech.machine.range.ray.shooter.EnergizedElectricityShootPile;
import io.taraxacum.finaltech.machine.range.ray.shooter.NormalElectricityShootPile;
import io.taraxacum.finaltech.machine.range.ray.shooter.OverloadedElectricityShootPile;
import io.taraxacum.finaltech.machine.standard.ItemSerializationConstructor;
import io.taraxacum.finaltech.machine.standard.ItemDeserializeParser;
import io.taraxacum.finaltech.machine.standard.BasicFrameMachine;
import io.taraxacum.finaltech.machine.standard.OrderedDustFactory;
import io.taraxacum.finaltech.machine.standard.advanced.*;
import io.taraxacum.finaltech.machine.standard.basic.BasicCobbleFactory;
import io.taraxacum.finaltech.machine.standard.basic.BasicDustFactory;
import io.taraxacum.finaltech.machine.standard.basic.BasicFarmFactory;
import io.taraxacum.finaltech.machine.standard.basic.BasicOreFactory;
import io.taraxacum.finaltech.setup.register.FinalTechItems;
import io.taraxacum.finaltech.setup.register.FinalTechMenus;
import io.taraxacum.finaltech.setup.register.FinalTechRecipes;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

/**
 * @author Final_ROOT
 */
public final class SetupUtil {

    public static void init(SlimefunAddon slimefunAddon) {

//        FinalTechItems.CODE_CREATE.addUnsafeEnchantment(NullEnchantment.ENCHANTMENT, 0);
//        FinalTechItems.CREATION_GENEALOGY.addUnsafeEnchantment(NullEnchantment.ENCHANTMENT, 0);
//        FinalTechItems.IMAGINARY_TRUTH.addUnsafeEnchantment(NullEnchantment.ENCHANTMENT, 0);

        CopyCardItem.initDifficulty(CopyCardItem.SINGULARITY_DIFFICULTY + ((Double)(Math.pow(Slimefun.getRegistry().getAllSlimefunItems().size(), 0.5))).intValue(), CopyCardItem.SPIROCHETE_DIFFICULTY + Slimefun.getInstalledAddons().size());
    }

    public static void setupMenus(SlimefunAddon slimefunAddon) {
        FinalTechMenus.MAIN_MENU.setTier(0);
        FinalTechMenus.MAIN_MENU.register(slimefunAddon);

        FinalTechMenus.MENU_MATERIAL.setTier(0);
        FinalTechMenus.MENU_BASIC_MACHINE.setTier(0);
        FinalTechMenus.MENU_ADVANCED_MACHINE.setTier(0);
        FinalTechMenus.MENU_FINAL_MACHINE.setTier(0);
        FinalTechMenus.MENU_CARGO.setTier(0);
        FinalTechMenus.MENU_ELECTRIC.setTier(0);
        FinalTechMenus.MENU_TOOL.setTier(0);
    }

    public static void setupItems(SlimefunAddon slimefunAddon) {

        new SlimefunItem(FinalTechMenus.MENU_MATERIAL, FinalTechItems.GEARWHEEL, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.GEARWHEEL, new SlimefunItemStack(FinalTechItems.GEARWHEEL, 4)).register(slimefunAddon);
        new UnusableSlimefunItem(FinalTechMenus.MENU_MATERIAL, FinalTechItems.UNORDERED_DUST,  FinalTechRecipes.RECIPE_TYPE_ORDERED_DUST_FACTORY, new ItemStack[] {}).register(slimefunAddon);
        new UnusableSlimefunItem(FinalTechMenus.MENU_MATERIAL, FinalTechItems.ORDERED_DUST, FinalTechRecipes.RECIPE_TYPE_ORDERED_DUST_FACTORY, new ItemStack[] {}).register(slimefunAddon);
        new UnusableSlimefunItem(FinalTechMenus.MENU_MATERIAL, FinalTechItems.QUANTITY_MODULE, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.QUANTITY_MODULE).register(slimefunAddon);
//        new UnusableSlimefunItem(FinalTechMenus.MENU_MATERIAL, FinalTechItems.QUANTITY_MODULE_V2, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.QUANTITY_MODULE_V2).register();
        new CopyCardItem(FinalTechMenus.MENU_MATERIAL, FinalTechItems.COPY_CARD, FinalTechRecipes.RECIPE_TYPE_ALL_COMPRESSION, new ItemStack[] {null, null, null, null, new CustomItemStack(Material.BOOK, "&f合成方式", "&f压缩" + CopyCardItem.COPY_CARD_DIFFICULTY + "个相同物品以获得")}).register(slimefunAddon);
        new UnusableSlimefunItem(FinalTechMenus.MENU_MATERIAL, FinalTechItems.SINGULARITY, FinalTechRecipes.RECIPE_TYPE_ALL_COMPRESSION, new ItemStack[] {null, null, null, null, new CustomItemStack(Material.BOOK, "&f合成方式", "&f对已经压缩过的物品", "&f再进行一次压缩")}).register(slimefunAddon);
        new UnusableSlimefunItem(FinalTechMenus.MENU_MATERIAL, FinalTechItems.SPIROCHETE, FinalTechRecipes.RECIPE_TYPE_ALL_COMPRESSION, new ItemStack[] {null, null, null, null, new CustomItemStack(Material.BOOK, "&f合成方式", "&f对已经压缩过的物品", "&f再进行一次压缩")}).register(slimefunAddon);
        new UnusableSlimefunItem(FinalTechMenus.MENU_MATERIAL, FinalTechItems.SHELL, RecipeType.NULL, new ItemStack[] {}).register(slimefunAddon);
        new UnusableSlimefunItem(FinalTechMenus.MENU_MATERIAL, FinalTechItems.FAKE, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.FAKE).register(slimefunAddon);

        new BasicCobbleFactory(FinalTechMenus.MENU_BASIC_MACHINE, FinalTechItems.BASIC_COBBLE_FACTORY, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.BASIC_COBBLE_FACTORY).register();
        new BasicOreFactory(FinalTechMenus.MENU_BASIC_MACHINE, FinalTechItems.BASIC_ORE_FACTORY, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.BASIC_ORE_FACTORY).register();
        new BasicDustFactory(FinalTechMenus.MENU_BASIC_MACHINE, FinalTechItems.BASIC_DUST_FACTORY, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.BASIC_DUST_FACTORY).register();
        new BasicFarmFactory(FinalTechMenus.MENU_BASIC_MACHINE, FinalTechItems.BASIC_FARM_FACTORY, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.BASIC_FARM_FACTORY).register();
        new ManualEnhancedCraftingTable(FinalTechMenus.MENU_BASIC_MACHINE, FinalTechItems.MANUAL_ENHANCED_CRAFTING_TABLE, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.MANUAL_ENHANCED_CRAFTING_TABLE).register();
        new ManualGrindStone(FinalTechMenus.MENU_BASIC_MACHINE, FinalTechItems.MANUAL_GRIND_STONE, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.MANUAL_GRIND_STONE).register();
        new ManualArmorForge(FinalTechMenus.MENU_BASIC_MACHINE, FinalTechItems.MANUAL_ARMOR_FORGE, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.MANUAL_ARMOR_FORGE).register();
        new ManualOreCrusher(FinalTechMenus.MENU_BASIC_MACHINE, FinalTechItems.MANUAL_ORE_CRUSHER, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.MANUAL_ORE_CRUSHER).register();
        new ManualCompressor(FinalTechMenus.MENU_BASIC_MACHINE, FinalTechItems.MANUAL_COMPRESSOR, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.MANUAL_COMPRESSOR).register();
        new ManualSmeltery(FinalTechMenus.MENU_BASIC_MACHINE, FinalTechItems.MANUAL_SMELTERY, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.MANUAL_SMELTERY).register();
        new ManualPressureChamber(FinalTechMenus.MENU_BASIC_MACHINE, FinalTechItems.MANUAL_PRESSURE_CHAMBER, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.MANUAL_PRESSURE_CHAMBER).register();
        new ManualMagicWorkbench(FinalTechMenus.MENU_BASIC_MACHINE, FinalTechItems.MANUAL_MAGIC_WORKBENCH, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.MANUAL_MAGIC_WORKBENCH).register();
        new ManualJuicer(FinalTechMenus.MENU_BASIC_MACHINE, FinalTechItems.MANUAL_JUICER, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.MANUAL_JUICER).register();
        new ManualAncientAltar(FinalTechMenus.MENU_BASIC_MACHINE, FinalTechItems.MANUAL_ANCIENT_ALTAR, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.MANUAL_ANCIENT_ALTAR).register();
        new ManualHeatedPressureChamber(FinalTechMenus.MENU_BASIC_MACHINE, FinalTechItems.MANUAL_HEATED_PRESSURE_CHAMBER, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.MANUAL_HEATED_PRESSURE_CHAMBER).register();
        new OrderedDustFactory(FinalTechMenus.MENU_BASIC_MACHINE, FinalTechItems.ORDERED_DUST_FACTORY, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.ORDERED_DUST_FACTORY).register();
        new OrderedDustFactoryV2(FinalTechMenus.MENU_BASIC_MACHINE, FinalTechItems.ORDERED_DUST_FACTORY_V2, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.ORDERED_DUST_FACTORY_V2).register();
        new CardOperationPort(FinalTechMenus.MENU_BASIC_MACHINE, FinalTechItems.CARD_OPERATION_PORT, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.CARD_OPERATION_PORT).register();

        new AdvancedElectricFurance(FinalTechMenus.MENU_ADVANCED_MACHINE, FinalTechItems.ADVANCED_ELECTRIC_FURANCE, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.ADVANCED_ELECTRIC_FURANCE).register();
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
        new AdvancedAutoCraft(FinalTechMenus.MENU_ADVANCED_MACHINE, FinalTechItems.ADVANCED_AUTO_CRAFT, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.ADVANCED_AUTO_CRAFT).register();

        new ItemSerializationConstructor(FinalTechMenus.MENU_FINAL_MACHINE, FinalTechItems.ALL_COMPRESSION, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.ALL_COMPRESSION).register();
        new ItemDeserializeParser(FinalTechMenus.MENU_FINAL_MACHINE, FinalTechItems.ALL_FACTORY, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.ALL_FACTORY).register();
        new OverclockFrameMachine(FinalTechMenus.MENU_FINAL_MACHINE, FinalTechItems.OVERCLOCK_FRAME_MACHINE, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.OVERCLOCK_FRAME_MACHINE).register();
//        new OverloadCoreMachine(FinalTechMenus.MENU_FINAL_MACHINE, FinalTechItems.OVERLOAD_CORE_MACHINE, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.OVERLOAD_CORE_MACHINE).register();

        new BasicFrameMachine(FinalTechMenus.MENU_CARGO, FinalTechItems.BASIC_FRAME_MACHINE, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.BASIC_FRAME_MACHINE).register();
        new BasicNormalStorageUnit(FinalTechMenus.MENU_CARGO, FinalTechItems.BASIC_NORMAL_STORAGE_UNIT, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.BASIC_NORMAL_STORAGE_UNIT).register();
        new BasicLinkedStorageUnit(FinalTechMenus.MENU_CARGO, FinalTechItems.BASIC_LINKED_STORAGE_UNIT, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.BASIC_LINKED_STORAGE_UNIT).register();
        new BasicChargeableStorageUnit(FinalTechMenus.MENU_CARGO, FinalTechItems.BASIC_CHARGEABLE_STORAGE_UNIT, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.BASIC_CHARGEABLE_STORAGE_UNIT).register();
        new TransferPipe(FinalTechMenus.MENU_CARGO, FinalTechItems.TRANSFER_PIPE, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.TRANSFER_PIPE).register();
        new TransferLine(FinalTechMenus.MENU_CARGO, FinalTechItems.TRANSFER_LINE, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.TRANSFER_LINE).register();
        new TransferStation(FinalTechMenus.MENU_CARGO, FinalTechItems.TRANSFER_STATION, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.TRANSFER_STATION).register();
//        new Stacker(FinalTechMenus.MENU_CARGO, FinalTechItems.STACKER, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.STACKER).register();
        // register storage system
        new StorageInteractPort(FinalTechMenus.MENU_CARGO, FinalTechItems.STORAGE_INTERACT_PORT, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.STORAGE_INTERACT_PORT).register();
        new StorageInsertPort(FinalTechMenus.MENU_CARGO, FinalTechItems.STORAGE_INSERT_PORT, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.STORAGE_INSERT_PORT).register();
        new StorageWithdrawPort(FinalTechMenus.MENU_CARGO, FinalTechItems.STORAGE_WITHDRAW_PORT, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.STORAGE_WITHDRAW_PORT).register();
        new StorageCardItem(FinalTechMenus.MENU_CARGO, FinalTechItems.STORAGE_ITEM_WHITE, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.STORAGE_ITEM_WHITE).register(slimefunAddon);
        new StorageCardItem(FinalTechMenus.MENU_CARGO, FinalTechItems.STORAGE_ITEM_ORANGE, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.STORAGE_ITEM_ORANGE).register(slimefunAddon);
        new StorageCardItem(FinalTechMenus.MENU_CARGO, FinalTechItems.STORAGE_ITEM_MAGENTA, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.STORAGE_ITEM_MAGENTA).register(slimefunAddon);
        new StorageCardItem(FinalTechMenus.MENU_CARGO, FinalTechItems.STORAGE_ITEM_LIGHT_BLUE, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.STORAGE_ITEM_LIGHT_BLUE).register(slimefunAddon);
        new StorageCardItem(FinalTechMenus.MENU_CARGO, FinalTechItems.STORAGE_ITEM_YELLOW, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.STORAGE_ITEM_YELLOW).register(slimefunAddon);
        new StorageCardItem(FinalTechMenus.MENU_CARGO, FinalTechItems.STORAGE_ITEM_LIME, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.STORAGE_ITEM_LIME).register(slimefunAddon);
        new StorageCardItem(FinalTechMenus.MENU_CARGO, FinalTechItems.STORAGE_ITEM_PINK, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.STORAGE_ITEM_PINK).register(slimefunAddon);
        new StorageCardItem(FinalTechMenus.MENU_CARGO, FinalTechItems.STORAGE_ITEM_GRAY, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.STORAGE_ITEM_GRAY).register(slimefunAddon);
        new StorageCardItem(FinalTechMenus.MENU_CARGO, FinalTechItems.STORAGE_ITEM_LIGHT_GRAY, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.STORAGE_ITEM_LIGHT_GRAY).register(slimefunAddon);
        new StorageCardItem(FinalTechMenus.MENU_CARGO, FinalTechItems.STORAGE_ITEM_CYAN, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.STORAGE_ITEM_CYAN).register(slimefunAddon);
        new StorageCardItem(FinalTechMenus.MENU_CARGO, FinalTechItems.STORAGE_ITEM_PURPLE, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.STORAGE_ITEM_PURPLE).register(slimefunAddon);
        new StorageCardItem(FinalTechMenus.MENU_CARGO, FinalTechItems.STORAGE_ITEM_BLUE, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.STORAGE_ITEM_BLUE).register(slimefunAddon);
        new StorageCardItem(FinalTechMenus.MENU_CARGO, FinalTechItems.STORAGE_ITEM_BROWN, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.STORAGE_ITEM_BROWN).register(slimefunAddon);
        new StorageCardItem(FinalTechMenus.MENU_CARGO, FinalTechItems.STORAGE_ITEM_GREEN, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.STORAGE_ITEM_GREEN).register(slimefunAddon);
        new StorageCardItem(FinalTechMenus.MENU_CARGO, FinalTechItems.STORAGE_ITEM_RED, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.STORAGE_ITEM_RED).register(slimefunAddon);
        new StorageCardItem(FinalTechMenus.MENU_CARGO, FinalTechItems.STORAGE_ITEM_BLACK, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.STORAGE_ITEM_BLACK).register(slimefunAddon);

        // register electric machine
        // now register capacitor
        new BasicChargeIncreaseCapacitor(FinalTechMenus.MENU_ELECTRIC, FinalTechItems.BASIC_CHARGE_INCREASE_CAPACITOR, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.BASIC_CHARGE_INCREASE_CAPACITOR).register();
        new BasicConsumeReduceCapacitor(FinalTechMenus.MENU_ELECTRIC, FinalTechItems.BASIC_CONSUME_REDUCE_CAPACITOR, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.BASIC_CONSUME_REDUCE_CAPACITOR).register();
        new BasicSelfGenerateCapacitor(FinalTechMenus.MENU_ELECTRIC, FinalTechItems.BASIC_SELF_GENERATE_CAPACITOR, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.BASIC_SELF_GENERATE_CAPACITOR).register();
        new BasicVoidGenerateCapacitor(FinalTechMenus.MENU_ELECTRIC, FinalTechItems.BASIC_VOID_GENERATE_CAPACITOR, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.BASIC_VOID_GENERATE_CAPACITOR).register();
        new NormalElectricityShootPile(FinalTechMenus.MENU_ELECTRIC, FinalTechItems.NORMAL_ELECTRICITY_SHOOT_PILE, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.NORMAL_ELECTRICITY_SHOOT_PILE).register();
        new EnergizedElectricityShootPile(FinalTechMenus.MENU_ELECTRIC, FinalTechItems.ENERGIZED_ELECTRICITY_SHOOT_PILE, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.ENERGIZED_ELECTRICITY_SHOOT_PILE).register();
        new OverloadedElectricityShootPile(FinalTechMenus.MENU_ELECTRIC, FinalTechItems.OVERLOADED_ELECTRICITY_SHOOT_PILE, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.OVERLOADED_ELECTRICITY_SHOOT_PILE).register();
        new SmallExpandedCapacitor(FinalTechMenus.MENU_ELECTRIC, FinalTechItems.SMALL_EXPANDED_CAPACITOR, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.SMALL_EXPANDED_CAPACITOR).register();
        new MediumExpandedCapacitor(FinalTechMenus.MENU_ELECTRIC, FinalTechItems.MEDIUM_EXPANDED_CAPACITOR, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.MEDIUM_EXPANDED_CAPACITOR).register();
        new BigExpandedCapacitor(FinalTechMenus.MENU_ELECTRIC, FinalTechItems.BIG_EXPANDED_CAPACITOR, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.BIG_EXPANDED_CAPACITOR).register();
        new LargeExpandedCapacitor(FinalTechMenus.MENU_ELECTRIC, FinalTechItems.LARGE_EXPANDED_CAPACITOR, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.LARGE_EXPANDED_CAPACITOR).register();
        new CarbonadoExpandedCapacitor(FinalTechMenus.MENU_ELECTRIC, FinalTechItems.CARBONADO_EXPANDED_CAPACITOR, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.CARBONADO_EXPANDED_CAPACITOR).register();
        new EnergizedExpandedCapacitor(FinalTechMenus.MENU_ELECTRIC, FinalTechItems.ENERGIZED_EXPANDED_CAPACITOR, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.ENERGIZED_EXPANDED_CAPACITOR).register();
        new EnergizedStackExpandedCapacitor(FinalTechMenus.MENU_ELECTRIC, FinalTechItems.ENERGIZED_STACK_EXPANDED_CAPACITOR, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.ENERGIZED_STACK_EXPANDED_CAPACITOR).register();
        new MatrixExpandedCapacitor(FinalTechMenus.MENU_ELECTRIC, FinalTechItems.MATRIX_EXPANDED_CAPACITOR, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.MATRIX_EXPANDED_CAPACITOR).register();
        new EscapeCapacitor(FinalTechMenus.MENU_ELECTRIC, FinalTechItems.ESCAPE_CAPACITOR, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.ESCAPE_CAPACITOR).register();
        // now register generator
        new OrderedDustGenerator(FinalTechMenus.MENU_ELECTRIC, FinalTechItems.ORDERED_DUST_GENERATOR, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.ORDERED_DUST_GENERATOR).register();
        new BasicGenerator(FinalTechMenus.MENU_ELECTRIC, FinalTechItems.BASIC_GENERATOR, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.BASIC_GENERATOR).register();
        new AdvancedGenerator(FinalTechMenus.MENU_ELECTRIC, FinalTechItems.ADVANCED_GENERATOR, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.ADVANCED_GENERATOR).register();
        new CarbonadoGenerator(FinalTechMenus.MENU_ELECTRIC, FinalTechItems.CARBONADO_GENERATOR, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.CARBONADO_GENERATOR).register();
        new EnergizedGenerator(FinalTechMenus.MENU_ELECTRIC, FinalTechItems.ENERGIZED_GENERATOR, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.ENERGIZED_GENERATOR).register();
        new EnergizedStackGenerator(FinalTechMenus.MENU_ELECTRIC, FinalTechItems.ENERGIZED_STACK_GENERATOR, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.ENERGIZED_STACK_GENERATOR).register();
        new OverloadedGenerator(FinalTechMenus.MENU_ELECTRIC, FinalTechItems.OVERLOADED_GENERATOR, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.OVERLOADED_GENERATOR).register();
        new MatrixGenerator(FinalTechMenus.MENU_ELECTRIC, FinalTechItems.MATRIX_GENERATOR, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.MATRIX_GENERATOR).register();
        new EnergizedAccelerator(FinalTechMenus.MENU_ELECTRIC, FinalTechItems.ENERGIZED_ACCELERATOR, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.ENERGIZED_ACCELERATOR).register();
        new OverloadedAccelerator(FinalTechMenus.MENU_ELECTRIC, FinalTechItems.OVERLOADED_ACCELERATOR, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.OVERLOADED_ACCELERATOR).register();
        new MatrixAccelerator(FinalTechMenus.MENU_ELECTRIC, FinalTechItems.MATRIX_ACCELERATOR, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.MATRIX_ACCELERATOR).register();

        // now register tools
        new MachineChargeCardL1(FinalTechMenus.MENU_TOOL, FinalTechItems.MACHINE_CHARGE_CARD_L1, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.MACHINE_CHARGE_CARD_L1).register(slimefunAddon);
        new MachineChargeCardL2(FinalTechMenus.MENU_TOOL, FinalTechItems.MACHINE_CHARGE_CARD_L2, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.MACHINE_CHARGE_CARD_L2).register(slimefunAddon);
        new MachineChargeCardL3(FinalTechMenus.MENU_TOOL, FinalTechItems.MACHINE_CHARGE_CARD_L3, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.MACHINE_CHARGE_CARD_L3).register(slimefunAddon);
        new MachineAccelerateCardL1(FinalTechMenus.MENU_TOOL, FinalTechItems.MACHINE_ACCELERATE_CARD_L1, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.MACHINE_ACCELERATE_CARD_L1).register(slimefunAddon);
        new MachineAccelerateCardL2(FinalTechMenus.MENU_TOOL, FinalTechItems.MACHINE_ACCELERATE_CARD_L2, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.MACHINE_ACCELERATE_CARD_L2).register(slimefunAddon);
        new MachineAccelerateCardL3(FinalTechMenus.MENU_TOOL, FinalTechItems.MACHINE_ACCELERATE_CARD_L3, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.MACHINE_ACCELERATE_CARD_L3).register(slimefunAddon);
        new MachineActivateCardL1(FinalTechMenus.MENU_TOOL, FinalTechItems.MACHINE_ACTIVATE_CARD_L1, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.MACHINE_ACTIVATE_CARD_L1).register(slimefunAddon);
        new MachineActivateCardL2(FinalTechMenus.MENU_TOOL, FinalTechItems.MACHINE_ACTIVATE_CARD_L2, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.MACHINE_ACTIVATE_CARD_L2).register(slimefunAddon);
        new MachineActivateCardL3(FinalTechMenus.MENU_TOOL, FinalTechItems.MACHINE_ACTIVATE_CARD_L3, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.MACHINE_ACTIVATE_CARD_L3).register(slimefunAddon);
        new MachineActivateCardL4(FinalTechMenus.MENU_TOOL, FinalTechItems.MACHINE_ACTIVATE_CARD_L4, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.MACHINE_ACTIVATE_CARD_L4).register(slimefunAddon);
        new MachineChargeCardMatrix(FinalTechMenus.MENU_TOOL, FinalTechItems.MACHINE_CHARGE_CARD_INFINITY, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.MACHINE_CHARGE_CARD_INFINITY).register(slimefunAddon);
        new MachineAccelerateCardMatrix(FinalTechMenus.MENU_TOOL, FinalTechItems.MACHINE_ACCELERATE_CARD_INFINITY, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.MACHINE_ACCELERATE_CARD_INFINITY).register(slimefunAddon);
    }
}
