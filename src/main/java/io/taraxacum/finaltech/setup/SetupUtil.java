package io.taraxacum.finaltech.setup;

import io.github.thebusybiscuit.slimefun4.api.SlimefunAddon;
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
import io.taraxacum.finaltech.core.item.machine.clicker.*;
import io.taraxacum.finaltech.core.item.machine.logic.LogicAmountComparator;
import io.taraxacum.finaltech.core.item.machine.logic.LogicEqualComparator;
import io.taraxacum.finaltech.core.item.machine.logic.LogicNotNullComparator;
import io.taraxacum.finaltech.core.item.machine.logic.LogicSimilarComparator;
import io.taraxacum.finaltech.core.item.machine.manual.ItemDismantleTable;
import io.taraxacum.finaltech.core.item.machine.range.line.ConfigurationCopier;
import io.taraxacum.finaltech.core.item.machine.range.line.ConfigurationPaster;
import io.taraxacum.finaltech.core.item.machine.range.point.NormalConfigurableElectricityShootPile;
import io.taraxacum.finaltech.core.item.machine.range.point.NormalConsumableElectricityShootPile;
import io.taraxacum.finaltech.core.item.machine.range.point.face.*;
import io.taraxacum.finaltech.core.item.machine.template.extraction.DigitalExtraction;
import io.taraxacum.finaltech.core.item.machine.tower.*;
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
import io.taraxacum.finaltech.core.item.machine.range.line.pile.EnergizedElectricityShootPile;
import io.taraxacum.finaltech.core.item.machine.range.line.pile.NormalElectricityShootPile;
import io.taraxacum.finaltech.core.item.machine.range.line.pile.OverloadedElectricityShootPile;
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
    public static void setupLanguageManager(@Nonnull LanguageManager languageManager) {
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
        // SlimefunItem name by id
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

    private static void setupEnchantment() {
        try {
            ReflectionUtil.setStaticValue(Enchantment.class, "acceptingNew", true);
            Enchantment.registerEnchantment(NullEnchantment.ENCHANTMENT);

            NullEnchantment.addAndHidden(FinalTechItemStacks.ADVANCED_POINT_TRANSFER);
            NullEnchantment.addAndHidden(FinalTechItemStacks.ADVANCED_MESH_TRANSFER);
            NullEnchantment.addAndHidden(FinalTechItemStacks.ADVANCED_LINE_TRANSFER);
            NullEnchantment.addAndHidden(FinalTechItemStacks.ADVANCED_LOCATION_TRANSFER);

            NullEnchantment.addAndHidden(FinalTechItemStacks.ENTROPY);
            NullEnchantment.addAndHidden(FinalTechItemStacks.QUANTITY_MODULE_INFINITY);
            NullEnchantment.addAndHidden(FinalTechItemStacks.OPERATION_ACCELERATOR_INFINITY);
            NullEnchantment.addAndHidden(FinalTechItemStacks.COPY_CARD);
            NullEnchantment.addAndHidden(FinalTechItemStacks.SHINE);
            NullEnchantment.addAndHidden(FinalTechItemStacks.PHONY);
            NullEnchantment.addAndHidden(FinalTechItemStacks.MACHINE_CHARGE_CARD_INFINITY);
            NullEnchantment.addAndHidden(FinalTechItemStacks.MACHINE_ACCELERATE_CARD_INFINITY);
            NullEnchantment.addAndHidden(FinalTechItemStacks.MACHINE_ACTIVATE_CARD_L4);
            NullEnchantment.addAndHidden(FinalTechItemStacks.ADVANCED_AUTO_CRAFT);
            NullEnchantment.addAndHidden(FinalTechItemStacks.MULTI_FRAME_MACHINE);
            NullEnchantment.addAndHidden(FinalTechItemStacks.MATRIX_ITEM_DISMANTLE_TABLE);
            NullEnchantment.addAndHidden(FinalTechItemStacks.MATRIX_EXPANDED_CAPACITOR);
            NullEnchantment.addAndHidden(FinalTechItemStacks.MATRIX_ITEM_DESERIALIZE_PARSER);
            NullEnchantment.addAndHidden(FinalTechItemStacks.ENTROPY_CONSTRUCTOR);
            NullEnchantment.addAndHidden(FinalTechItemStacks.MATRIX_GENERATOR);
            NullEnchantment.addAndHidden(FinalTechItemStacks.MATRIX_ACCELERATOR);
            NullEnchantment.addAndHidden(FinalTechItemStacks.MATRIX_ITEM_SERIALIZATION_CONSTRUCTOR);
            NullEnchantment.addAndHidden(FinalTechItemStacks.MATRIX_REACTOR);
        } catch (Exception e) {
            e.printStackTrace();
            FinalTech.logger().warning("Some error occurred while registering enchantment");
        }
    }

    public static void setupItem() {
        ItemStackUtil.setLore(FinalTechItemStacks.TROPHY_MEAWERFUL,
                "§fThanks for some good idea");
        ItemStackUtil.setLore(FinalTechItemStacks.TROPHY_SHIXINZIA,
                "§fThanks for some test work");

        ItemStackUtil.addLoreToFirst(FinalTechItemStacks.STORAGE_CARD, StorageCard.ITEM_LORE);

        /* items */
        // material
        FinalTechMenus.SUB_MENU_MATERIAL.addTo(
                new WaterCard(FinalTechMenus.MENU_ITEMS, FinalTechItemStacks.WATER_CARD, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.WATER_CARD).register(),
                new LavaCard(FinalTechMenus.MENU_ITEMS, FinalTechItemStacks.LAVA_CARD, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.LAVA_CARD).register(),
                new MilkCard(FinalTechMenus.MENU_ITEMS, FinalTechItemStacks.MILK_CARD, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.MILK_CARD).register());
        FinalTechMenus.SUB_MENU_MATERIAL.addTo(
                new Gearwheel(FinalTechMenus.MENU_ITEMS, FinalTechItemStacks.GEARWHEEL, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.GEARWHEEL, ItemStackUtil.cloneItem(FinalTechItemStacks.GEARWHEEL, 4)).register(),
                new UnorderedDust(FinalTechMenus.MENU_ITEMS, FinalTechItemStacks.UNORDERED_DUST,  FinalTechRecipes.RECIPE_TYPE_ORDERED_DUST_FACTORY, FinalTechRecipes.UNORDERED_DUST).register(),
                new OrderedDust(FinalTechMenus.MENU_ITEMS, FinalTechItemStacks.ORDERED_DUST, FinalTechRecipes.RECIPE_TYPE_ORDERED_DUST_FACTORY, FinalTechRecipes.ORDERED_DUST).register(),
                new Bug(FinalTechMenus.MENU_ITEMS, FinalTechItemStacks.BUG, FinalTechRecipes.RECIPE_TYPE_EQUIVALENT_EXCHANGE_TABLE, FinalTechRecipes.BUG).register(),
                new Entropy(FinalTechMenus.MENU_ITEMS, FinalTechItemStacks.ENTROPY, FinalTechRecipes.RECIPE_TYPE_ENTROPY_CONSTRUCTOR, FinalTechRecipes.ENTROPY).register(),
                new Box(FinalTechMenus.MENU_ITEMS, FinalTechItemStacks.BOX, RecipeType.NULL, FinalTechRecipes.BOX).register(),
                new Shine(FinalTechMenus.MENU_ITEMS, FinalTechItemStacks.SHINE, FinalTechRecipes.RECIPE_TYPE_BOX, FinalTechRecipes.SHINE).register(),
                new CopyCard(FinalTechMenus.MENU_ITEMS, FinalTechItemStacks.COPY_CARD, FinalTechRecipes.RECIPE_TYPE_ITEM_SERIALIZATION_CONSTRUCTOR, FinalTechRecipes.COPY_CARD).register());
        FinalTechMenus.SUB_MENU_MATERIAL.addTo(
                new Annular(FinalTechMenus.MENU_ITEMS, FinalTechItemStacks.ANNULAR, FinalTechRecipes.RECIPE_TYPE_CARD_OPERATION_TABLE, FinalTechRecipes.ANNULAR).register(),
                new QuantityModule(FinalTechMenus.MENU_ITEMS, FinalTechItemStacks.QUANTITY_MODULE, FinalTechRecipes.RECIPE_TYPE_MATRIX_CRAFTING_TABLE, FinalTechRecipes.QUANTITY_MODULE).register(),
                new QuantityModuleInfinity(FinalTechMenus.MENU_ITEMS, FinalTechItemStacks.QUANTITY_MODULE_INFINITY, FinalTechRecipes.RECIPE_TYPE_MATRIX_CRAFTING_TABLE, FinalTechRecipes.QUANTITY_MODULE_INFINITY).register(),
                new Singularity(FinalTechMenus.MENU_ITEMS, FinalTechItemStacks.SINGULARITY, FinalTechRecipes.RECIPE_TYPE_ITEM_SERIALIZATION_CONSTRUCTOR, FinalTechRecipes.SINGULARITY).register(),
                new Spirochete(FinalTechMenus.MENU_ITEMS, FinalTechItemStacks.SPIROCHETE, FinalTechRecipes.RECIPE_TYPE_ITEM_SERIALIZATION_CONSTRUCTOR, FinalTechRecipes.SPIROCHETE).register(),
                new Shell(FinalTechMenus.MENU_ITEMS, FinalTechItemStacks.SHELL, FinalTechRecipes.RECIPE_TYPE_CARD_OPERATION_TABLE, FinalTechRecipes.SHELL).register(),
                new ItemPhony(FinalTechMenus.MENU_ITEMS, FinalTechItemStacks.PHONY, FinalTechRecipes.RECIPE_TYPE_CARD_OPERATION_TABLE, FinalTechRecipes.PHONY).register(),
                new Justifiability(FinalTechMenus.MENU_ITEMS, FinalTechItemStacks.JUSTIFIABILITY, FinalTechRecipes.RECIPE_TYPE_ENTROPY_SEED, FinalTechRecipes.JUSTIFIABILITY).register(),
                new EquivalentConcept(FinalTechMenus.MENU_ITEMS, FinalTechItemStacks.EQUIVALENT_CONCEPT, FinalTechRecipes.RECIPE_TYPE_ENTROPY_SEED, FinalTechRecipes.EQUIVALENT_CONCEPT).register());
        // logic item
        FinalTechMenus.SUB_MENU_LOGIC_ITEM.addTo(
                new LogicFalse(FinalTechMenus.MENU_ITEMS, FinalTechItemStacks.LOGIC_FALSE, FinalTechRecipes.RECIPE_TYPE_LOGIC_GENERATOR, FinalTechRecipes.LOGIC_FALSE).register(),
                new LogicTrue(FinalTechMenus.MENU_ITEMS, FinalTechItemStacks.LOGIC_TRUE, FinalTechRecipes.RECIPE_TYPE_LOGIC_GENERATOR, FinalTechRecipes.LOGIC_TRUE).register(),
                new DigitalZero(FinalTechMenus.MENU_ITEMS, FinalTechItemStacks.DIGITAL_ZERO, FinalTechRecipes.RECIPE_TYPE_LOGIC_CRAFTER, FinalTechRecipes.DIGITAL_ZERO).register(),
                new DigitalOne(FinalTechMenus.MENU_ITEMS, FinalTechItemStacks.DIGITAL_ONE, FinalTechRecipes.RECIPE_TYPE_LOGIC_CRAFTER, FinalTechRecipes.DIGITAL_ONE).register(),
                new DigitalTwo(FinalTechMenus.MENU_ITEMS, FinalTechItemStacks.DIGITAL_TWO, FinalTechRecipes.RECIPE_TYPE_LOGIC_CRAFTER, FinalTechRecipes.DIGITAL_TWO).register(),
                new DigitalThree(FinalTechMenus.MENU_ITEMS, FinalTechItemStacks.DIGITAL_THREE, FinalTechRecipes.RECIPE_TYPE_LOGIC_CRAFTER, FinalTechRecipes.DIGITAL_THREE).register(),
                new DigitalFour(FinalTechMenus.MENU_ITEMS, FinalTechItemStacks.DIGITAL_FOUR, FinalTechRecipes.RECIPE_TYPE_LOGIC_CRAFTER, FinalTechRecipes.DIGITAL_FOUR).register(),
                new DigitalFive(FinalTechMenus.MENU_ITEMS, FinalTechItemStacks.DIGITAL_FIVE, FinalTechRecipes.RECIPE_TYPE_LOGIC_CRAFTER, FinalTechRecipes.DIGITAL_FIVE).register(),
                new DigitalSix(FinalTechMenus.MENU_ITEMS, FinalTechItemStacks.DIGITAL_SIX, FinalTechRecipes.RECIPE_TYPE_LOGIC_CRAFTER, FinalTechRecipes.DIGITAL_SIX).register(),
                new DigitalSeven(FinalTechMenus.MENU_ITEMS, FinalTechItemStacks.DIGITAL_SEVEN, FinalTechRecipes.RECIPE_TYPE_LOGIC_CRAFTER, FinalTechRecipes.DIGITAL_SEVEN).register(),
                new DigitalEight(FinalTechMenus.MENU_ITEMS, FinalTechItemStacks.DIGITAL_EIGHT, FinalTechRecipes.RECIPE_TYPE_LOGIC_CRAFTER, FinalTechRecipes.DIGITAL_EIGHT).register(),
                new DigitalNine(FinalTechMenus.MENU_ITEMS, FinalTechItemStacks.DIGITAL_NINE, FinalTechRecipes.RECIPE_TYPE_LOGIC_CRAFTER, FinalTechRecipes.DIGITAL_NINE).register(),
                new DigitalTen(FinalTechMenus.MENU_ITEMS, FinalTechItemStacks.DIGITAL_TEN, FinalTechRecipes.RECIPE_TYPE_LOGIC_CRAFTER, FinalTechRecipes.DIGITAL_TEN).register(),
                new DigitalEleven(FinalTechMenus.MENU_ITEMS, FinalTechItemStacks.DIGITAL_ELEVEN, FinalTechRecipes.RECIPE_TYPE_LOGIC_CRAFTER, FinalTechRecipes.DIGITAL_ELEVEN).register(),
                new DigitalTwelve(FinalTechMenus.MENU_ITEMS, FinalTechItemStacks.DIGITAL_TWELVE, FinalTechRecipes.RECIPE_TYPE_LOGIC_CRAFTER, FinalTechRecipes.DIGITAL_TWELVE).register(),
                new DigitalThirteen(FinalTechMenus.MENU_ITEMS, FinalTechItemStacks.DIGITAL_THIRTEEN, FinalTechRecipes.RECIPE_TYPE_LOGIC_CRAFTER, FinalTechRecipes.DIGITAL_THIRTEEN).register(),
                new DigitalFourteen(FinalTechMenus.MENU_ITEMS, FinalTechItemStacks.DIGITAL_FOURTEEN, FinalTechRecipes.RECIPE_TYPE_LOGIC_CRAFTER, FinalTechRecipes.DIGITAL_FOURTEEN).register(),
                new DigitalFifteen(FinalTechMenus.MENU_ITEMS, FinalTechItemStacks.DIGITAL_FIFTEEN, FinalTechRecipes.RECIPE_TYPE_LOGIC_CRAFTER, FinalTechRecipes.DIGITAL_FIFTEEN).register());
        // consumable
        FinalTechMenus.SUB_MENU_CONSUMABLE.addTo(
                new MachineChargeCardL1(FinalTechMenus.MENU_ITEMS, FinalTechItemStacks.MACHINE_CHARGE_CARD_L1, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.MACHINE_CHARGE_CARD_L1).register(),
                new MachineChargeCardL2(FinalTechMenus.MENU_ITEMS, FinalTechItemStacks.MACHINE_CHARGE_CARD_L2, FinalTechRecipes.RECIPE_TYPE_MATRIX_CRAFTING_TABLE, FinalTechRecipes.MACHINE_CHARGE_CARD_L2).register(),
                new MachineChargeCardL3(FinalTechMenus.MENU_ITEMS, FinalTechItemStacks.MACHINE_CHARGE_CARD_L3, FinalTechRecipes.RECIPE_TYPE_MATRIX_CRAFTING_TABLE, FinalTechRecipes.MACHINE_CHARGE_CARD_L3).register(),
                new MachineAccelerateCardL1(FinalTechMenus.MENU_ITEMS, FinalTechItemStacks.MACHINE_ACCELERATE_CARD_L1, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.MACHINE_ACCELERATE_CARD_L1).register(),
                new MachineAccelerateCardL2(FinalTechMenus.MENU_ITEMS, FinalTechItemStacks.MACHINE_ACCELERATE_CARD_L2, FinalTechRecipes.RECIPE_TYPE_MATRIX_CRAFTING_TABLE, FinalTechRecipes.MACHINE_ACCELERATE_CARD_L2).register(),
                new MachineAccelerateCardL3(FinalTechMenus.MENU_ITEMS, FinalTechItemStacks.MACHINE_ACCELERATE_CARD_L3, FinalTechRecipes.RECIPE_TYPE_MATRIX_CRAFTING_TABLE, FinalTechRecipes.MACHINE_ACCELERATE_CARD_L3).register(),
                new MachineActivateCardL1(FinalTechMenus.MENU_ITEMS, FinalTechItemStacks.MACHINE_ACTIVATE_CARD_L1, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.MACHINE_ACTIVATE_CARD_L1).register(),
                new MachineActivateCardL2(FinalTechMenus.MENU_ITEMS, FinalTechItemStacks.MACHINE_ACTIVATE_CARD_L2, FinalTechRecipes.RECIPE_TYPE_MATRIX_CRAFTING_TABLE, FinalTechRecipes.MACHINE_ACTIVATE_CARD_L2).register(),
                new MachineActivateCardL3(FinalTechMenus.MENU_ITEMS, FinalTechItemStacks.MACHINE_ACTIVATE_CARD_L3, FinalTechRecipes.RECIPE_TYPE_MATRIX_CRAFTING_TABLE, FinalTechRecipes.MACHINE_ACTIVATE_CARD_L3).register());
        FinalTechMenus.SUB_MENU_CONSUMABLE.addTo(
                new MagicHypnotic(FinalTechMenus.MENU_ITEMS, FinalTechItemStacks.MAGIC_HYPNOTIC, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.MAGIC_HYPNOTIC).register(),
                new ResearchUnlockTicket(FinalTechMenus.MENU_ITEMS, FinalTechItemStacks.RESEARCH_UNLOCK_TICKET, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.RESEARCH_UNLOCK_TICKET).register());
        // tool
        FinalTechMenus.SUB_MENU_TOOL.addTo(
                new StaffElementalLine(FinalTechMenus.MENU_ITEMS, FinalTechItemStacks.STAFF_ELEMENTAL_LINE, RecipeType.MAGIC_WORKBENCH, FinalTechRecipes.STAFF_ELEMENTAL_LINE).register(),
                new PotionEffectCompressor(FinalTechMenus.MENU_ITEMS, FinalTechItemStacks.POTION_EFFECT_COMPRESSOR, FinalTechRecipes.RECIPE_TYPE_MATRIX_CRAFTING_TABLE, FinalTechRecipes.POTION_EFFECT_COMPRESSOR).register(),
                new PotionEffectDilator(FinalTechMenus.MENU_ITEMS, FinalTechItemStacks.POTION_EFFECT_DILATOR, FinalTechRecipes.RECIPE_TYPE_MATRIX_CRAFTING_TABLE, FinalTechRecipes.POTION_EFFECT_DILATOR).register(),
                new PotionEffectPurifier(FinalTechMenus.MENU_ITEMS, FinalTechItemStacks.POTION_EFFECT_PURIFIER, FinalTechRecipes.RECIPE_TYPE_MATRIX_CRAFTING_TABLE, FinalTechRecipes.POTION_EFFECT_PURIFIER).register(),
                new GravityReversalRune(FinalTechMenus.MENU_ITEMS, FinalTechItemStacks.GRAVITY_REVERSAL_RUNE, FinalTechRecipes.RECIPE_TYPE_MATRIX_CRAFTING_TABLE, FinalTechRecipes.GRAVITY_REVERSAL_RUNE).register());
        FinalTechMenus.SUB_MENU_TOOL.addTo(
                new MenuViewer(FinalTechMenus.MENU_ITEMS, FinalTechItemStacks.MENU_VIEWER, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.MENU_VIEWER).register(),
                new LocationRecorder(FinalTechMenus.MENU_ITEMS, FinalTechItemStacks.LOCATION_RECORDER, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.LOCATION_RECORDER).register(),
                new MachineConfigurator(FinalTechMenus.MENU_ITEMS, FinalTechItemStacks.MACHINE_CONFIGURATOR, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.MACHINE_CONFIGURATOR).register(),
                new PortableEnergyStorage(FinalTechMenus.MENU_ITEMS, FinalTechItemStacks.PORTABLE_ENERGY_STORAGE, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.PORTABLE_ENERGY_STORAGE).register());
        // weapon
        FinalTechMenus.SUB_MENU_WEAPON.addTo(
                new SuperShovel(FinalTechMenus.MENU_ITEMS, FinalTechItemStacks.SUPER_SHOVEL, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.SUPER_SHOVEL).register(),
                new UltimateShovel(FinalTechMenus.MENU_ITEMS, FinalTechItemStacks.ULTIMATE_SHOVEL, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.ULTIMATE_SHOVEL).register(),
                new SuperPickaxe(FinalTechMenus.MENU_ITEMS, FinalTechItemStacks.SUPER_PICKAXE, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.SUPER_PICKAXE).register(),
                new UltimatePickaxe(FinalTechMenus.MENU_ITEMS, FinalTechItemStacks.ULTIMATE_PICKAXE, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.ULTIMATE_PICKAXE).register(),
                new SuperAxe(FinalTechMenus.MENU_ITEMS, FinalTechItemStacks.SUPER_AXE, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.SUPER_AXE).register(),
                new UltimateAxe(FinalTechMenus.MENU_ITEMS, FinalTechItemStacks.ULTIMATE_AXE, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.ULTIMATE_AXE).register(),
                new SuperHoe(FinalTechMenus.MENU_ITEMS, FinalTechItemStacks.SUPER_HOE, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.SUPER_HOE).register(),
                new UltimateHoe(FinalTechMenus.MENU_ITEMS, FinalTechItemStacks.ULTIMATE_HOE, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.ULTIMATE_HOE).register()
//                new SlimefunItem(FinalTechMenus.MENU_ITEMS, FinalTechItemStacks.SWORD1, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.SWORD1).register(FinalTech.getInstance()),
//                new SlimefunItem(FinalTechMenus.MENU_ITEMS, FinalTechItemStacks.SWORD2, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.SWORD2).register(FinalTech.getInstance()),
//                new SlimefunItem(FinalTechMenus.MENU_ITEMS, FinalTechItemStacks.SWORD3, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.SWORD3).register(FinalTech.getInstance()),
//                new SlimefunItem(FinalTechMenus.MENU_ITEMS, FinalTechItemStacks.SWORD4, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.SWORD4).register(FinalTech.getInstance())
        );

        /* electricity system */
        // electric generator
        FinalTechMenus.SUB_MENU_ELECTRIC_GENERATOR.addTo(
                new BasicGenerator(FinalTechMenus.MENU_ELECTRICITY_SYSTEM, FinalTechItemStacks.BASIC_GENERATOR, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.BASIC_GENERATOR).register(),
                new AdvancedGenerator(FinalTechMenus.MENU_ELECTRICITY_SYSTEM, FinalTechItemStacks.ADVANCED_GENERATOR, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.ADVANCED_GENERATOR).register(),
                new CarbonadoGenerator(FinalTechMenus.MENU_ELECTRICITY_SYSTEM, FinalTechItemStacks.CARBONADO_GENERATOR, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.CARBONADO_GENERATOR).register(),
                new EnergizedGenerator(FinalTechMenus.MENU_ELECTRICITY_SYSTEM, FinalTechItemStacks.ENERGIZED_GENERATOR, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.ENERGIZED_GENERATOR).register(),
                new EnergizedStackGenerator(FinalTechMenus.MENU_ELECTRICITY_SYSTEM, FinalTechItemStacks.ENERGIZED_STACK_GENERATOR, FinalTechRecipes.RECIPE_TYPE_MATRIX_CRAFTING_TABLE, FinalTechRecipes.ENERGIZED_STACK_GENERATOR).register(),
                new OverloadedGenerator(FinalTechMenus.MENU_ELECTRICITY_SYSTEM, FinalTechItemStacks.OVERLOADED_GENERATOR, FinalTechRecipes.RECIPE_TYPE_MATRIX_CRAFTING_TABLE, FinalTechRecipes.OVERLOADED_GENERATOR).register());
        FinalTechMenus.SUB_MENU_ELECTRIC_GENERATOR.addTo(
                new DustGenerator(FinalTechMenus.MENU_ELECTRICITY_SYSTEM, FinalTechItemStacks.ORDERED_DUST_GENERATOR, FinalTechRecipes.RECIPE_TYPE_MATRIX_CRAFTING_TABLE, FinalTechRecipes.ORDERED_DUST_GENERATOR).register(),
                new TimeGenerator(FinalTechMenus.MENU_ELECTRICITY_SYSTEM, FinalTechItemStacks.TIME_GENERATOR, FinalTechRecipes.RECIPE_TYPE_MATRIX_CRAFTING_TABLE, FinalTechRecipes.TIME_GENERATOR).register(),
                new EnergizedChargeBase(FinalTechMenus.MENU_ELECTRICITY_SYSTEM, FinalTechItemStacks.ENERGIZED_CHARGE_BASE, FinalTechRecipes.RECIPE_TYPE_MATRIX_CRAFTING_TABLE, FinalTechRecipes.ENERGIZED_CHARGE_BASE).register(),
                new OverloadedChargeBase(FinalTechMenus.MENU_ELECTRICITY_SYSTEM, FinalTechItemStacks.OVERLOADED_CHARGE_BASE, FinalTechRecipes.RECIPE_TYPE_MATRIX_CRAFTING_TABLE, FinalTechRecipes.OVERLOADED_CHARGE_BASE).register());
        // electric storage
        FinalTechMenus.SUB_MENU_ELECTRIC_STORAGE.addTo(
                new SmallExpandedCapacitor(FinalTechMenus.MENU_ELECTRICITY_SYSTEM, FinalTechItemStacks.SMALL_EXPANDED_CAPACITOR, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.SMALL_EXPANDED_CAPACITOR).register(),
                new MediumExpandedCapacitor(FinalTechMenus.MENU_ELECTRICITY_SYSTEM, FinalTechItemStacks.MEDIUM_EXPANDED_CAPACITOR, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.MEDIUM_EXPANDED_CAPACITOR).register(),
                new BigExpandedCapacitor(FinalTechMenus.MENU_ELECTRICITY_SYSTEM, FinalTechItemStacks.BIG_EXPANDED_CAPACITOR, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.BIG_EXPANDED_CAPACITOR).register(),
                new LargeExpandedCapacitor(FinalTechMenus.MENU_ELECTRICITY_SYSTEM, FinalTechItemStacks.LARGE_EXPANDED_CAPACITOR, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.LARGE_EXPANDED_CAPACITOR).register(),
                new CarbonadoExpandedCapacitor(FinalTechMenus.MENU_ELECTRICITY_SYSTEM, FinalTechItemStacks.CARBONADO_EXPANDED_CAPACITOR, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.CARBONADO_EXPANDED_CAPACITOR).register(),
                new EnergizedExpandedCapacitor(FinalTechMenus.MENU_ELECTRICITY_SYSTEM, FinalTechItemStacks.ENERGIZED_EXPANDED_CAPACITOR, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.ENERGIZED_EXPANDED_CAPACITOR).register());
        FinalTechMenus.SUB_MENU_ELECTRIC_STORAGE.addTo(
                new EnergizedStackExpandedCapacitor(FinalTechMenus.MENU_ELECTRICITY_SYSTEM, FinalTechItemStacks.ENERGIZED_STACK_EXPANDED_CAPACITOR, FinalTechRecipes.RECIPE_TYPE_MATRIX_CRAFTING_TABLE, FinalTechRecipes.ENERGIZED_STACK_EXPANDED_CAPACITOR).register(),
                new OverloadedExpandedCapacitor(FinalTechMenus.MENU_ELECTRICITY_SYSTEM, FinalTechItemStacks.OVERLOADED_EXPANDED_CAPACITOR, FinalTechRecipes.RECIPE_TYPE_MATRIX_CRAFTING_TABLE, FinalTechRecipes.OVERLOADED_EXPANDED_CAPACITOR).register(),
                new TimeCapacitor(FinalTechMenus.MENU_ELECTRICITY_SYSTEM, FinalTechItemStacks.TIME_CAPACITOR, FinalTechRecipes.RECIPE_TYPE_MATRIX_CRAFTING_TABLE, FinalTechRecipes.TIME_CAPACITOR).register());
        // electric transmission
        FinalTechMenus.SUB_MENU_ELECTRIC_TRANSMISSION.addTo(
                new NormalElectricityShootPile(FinalTechMenus.MENU_ELECTRICITY_SYSTEM, FinalTechItemStacks.NORMAL_ELECTRICITY_SHOOT_PILE, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.NORMAL_ELECTRICITY_SHOOT_PILE).register(),
                new NormalConsumableElectricityShootPile(FinalTechMenus.MENU_ELECTRICITY_SYSTEM, FinalTechItemStacks.NORMAL_CONSUMABLE_ELECTRICITY_SHOOT_PILE, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.NORMAL_CONSUMABLE_ELECTRICITY_SHOOT_PILE).register(),
                new NormalConfigurableElectricityShootPile(FinalTechMenus.MENU_ELECTRICITY_SYSTEM, FinalTechItemStacks.NORMAL_CONFIGURABLE_ELECTRICITY_SHOOT_PILE, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.NORMAL_CONFIGURABLE_ELECTRICITY_SHOOT_PILE).register(),
                new EnergizedElectricityShootPile(FinalTechMenus.MENU_ELECTRICITY_SYSTEM, FinalTechItemStacks.ENERGIZED_ELECTRICITY_SHOOT_PILE, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.ENERGIZED_ELECTRICITY_SHOOT_PILE).register(),
                new OverloadedElectricityShootPile(FinalTechMenus.MENU_ELECTRICITY_SYSTEM, FinalTechItemStacks.OVERLOADED_ELECTRICITY_SHOOT_PILE, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.OVERLOADED_ELECTRICITY_SHOOT_PILE).register(),
//                new DispersalCapacitor(FinalTechMenus.MENU_ELECTRICITY_SYSTEM, FinalTechItemStacks.DISPERSAL_CAPACITOR, FinalTechRecipes.RECIPE_TYPE_MATRIX_CRAFTING_TABLE, FinalTechRecipes.DISPERSAL_CAPACITOR).register(),
                new VariableWireResistance(FinalTechMenus.MENU_ELECTRICITY_SYSTEM, FinalTechItemStacks.VARIABLE_WIRE_RESISTANCE, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.VARIABLE_WIRE_RESISTANCE).register(),
                new VariableWireCapacitor(FinalTechMenus.MENU_ELECTRICITY_SYSTEM, FinalTechItemStacks.VARIABLE_WIRE_CAPACITOR, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.VARIABLE_WIRE_CAPACITOR).register());
        // electric accelerator
        FinalTechMenus.SUB_MENU_ELECTRIC_ACCELERATOR.addTo(
                new EnergizedAccelerator(FinalTechMenus.MENU_ELECTRICITY_SYSTEM, FinalTechItemStacks.ENERGIZED_ACCELERATOR, FinalTechRecipes.RECIPE_TYPE_MATRIX_CRAFTING_TABLE, FinalTechRecipes.ENERGIZED_ACCELERATOR).register(),
                new OverloadedAccelerator(FinalTechMenus.MENU_ELECTRICITY_SYSTEM, FinalTechItemStacks.OVERLOADED_ACCELERATOR, FinalTechRecipes.RECIPE_TYPE_MATRIX_CRAFTING_TABLE, FinalTechRecipes.OVERLOADED_ACCELERATOR).register());

        /* cargo system */
        // storage unit
        FinalTechMenus.SUB_MENU_STORAGE_UNIT.addTo(
                new NormalStorageUnit(FinalTechMenus.MENU_CARGO_SYSTEM, FinalTechItemStacks.NORMAL_STORAGE_UNIT, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.NORMAL_STORAGE_UNIT).register(),
                new DividedStorageUnit(FinalTechMenus.MENU_CARGO_SYSTEM, FinalTechItemStacks.DIVIDED_STORAGE_UNIT, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.DIVIDED_STORAGE_UNIT).register(),
                new LimitedStorageUnit(FinalTechMenus.MENU_CARGO_SYSTEM, FinalTechItemStacks.LIMITED_STORAGE_UNIT, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.LIMITED_STORAGE_UNIT).register(),
                new StackStorageUnit(FinalTechMenus.MENU_CARGO_SYSTEM, FinalTechItemStacks.STACK_STORAGE_UNIT, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.STACK_STORAGE_UNIT).register(),
                new DividedLimitedStorageUnit(FinalTechMenus.MENU_CARGO_SYSTEM, FinalTechItemStacks.DIVIDED_LIMITED_STORAGE_UNIT, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.DIVIDED_LIMITED_STORAGE_UNIT).register(),
                new DividedStackStorageUnit(FinalTechMenus.MENU_CARGO_SYSTEM, FinalTechItemStacks.DIVIDED_STACK_STORAGE_UNIT, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.DIVIDED_STACK_STORAGE_UNIT).register(),
                new LimitedStackStorageUnit(FinalTechMenus.MENU_CARGO_SYSTEM, FinalTechItemStacks.LIMITED_STACK_STORAGE_UNIT, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.LIMITED_STACK_STORAGE_UNIT).register());
        FinalTechMenus.SUB_MENU_STORAGE_UNIT.addTo(
                new RandomInputStorageUnit(FinalTechMenus.MENU_CARGO_SYSTEM, FinalTechItemStacks.RANDOM_INPUT_STORAGE_UNIT, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.RANDOM_INPUT_STORAGE_UNIT).register(),
                new RandomOutputStorageUnit(FinalTechMenus.MENU_CARGO_SYSTEM, FinalTechItemStacks.RANDOM_OUTPUT_STORAGE_UNIT, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.RANDOM_OUTPUT_STORAGE_UNIT).register(),
                new RandomAccessStorageUnit(FinalTechMenus.MENU_CARGO_SYSTEM, FinalTechItemStacks.RANDOM_ACCESS_STORAGE_UNIT, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.RANDOM_ACCESS_STORAGE_UNIT).register(),
                new DistributeLeftStorageUnit(FinalTechMenus.MENU_CARGO_SYSTEM, FinalTechItemStacks.DISTRIBUTE_LEFT_STORAGE_UNIT, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.DISTRIBUTE_LEFT_STORAGE_UNIT).register(),
                new DistributeRightStorageUnit(FinalTechMenus.MENU_CARGO_SYSTEM, FinalTechItemStacks.DISTRIBUTE_RIGHT_STORAGE_UNIT, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.DISTRIBUTE_RIGHT_STORAGE_UNIT).register());
        // advanced storage
        FinalTechMenus.SUB_MENU_ADVANCED_STORAGE.addTo(
                new StorageInteractPort(FinalTechMenus.MENU_CARGO_SYSTEM, FinalTechItemStacks.STORAGE_INTERACT_PORT, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.STORAGE_INTERACT_PORT).register(),
                new StorageInsertPort(FinalTechMenus.MENU_CARGO_SYSTEM, FinalTechItemStacks.STORAGE_INSERT_PORT, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.STORAGE_INSERT_PORT).register(),
                new StorageWithdrawPort(FinalTechMenus.MENU_CARGO_SYSTEM, FinalTechItemStacks.STORAGE_WITHDRAW_PORT, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.STORAGE_WITHDRAW_PORT).register(),
                new StorageCard(FinalTechMenus.MENU_CARGO_SYSTEM, FinalTechItemStacks.STORAGE_CARD, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.STORAGE_CARD).register());
        // accessor
        FinalTechMenus.SUB_MENU_ACCESSOR.addTo(
                new RemoteAccessor(FinalTechMenus.MENU_CARGO_SYSTEM, FinalTechItemStacks.REMOTE_ACCESSOR, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.REMOTE_ACCESSOR).register(),
                new ConsumableRemoteAccessor(FinalTechMenus.MENU_CARGO_SYSTEM, FinalTechItemStacks.CONSUMABLE_REMOTE_ACCESSOR, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.CONSUMABLE_REMOTE_ACCESSOR).register(),
                new ConfigurableRemoteAccessor(FinalTechMenus.MENU_CARGO_SYSTEM, FinalTechItemStacks.CONFIGURABLE_REMOTE_ACCESSOR, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.CONFIGURABLE_REMOTE_ACCESSOR).register(),
                new ExpandedConsumableRemoteAccessor(FinalTechMenus.MENU_CARGO_SYSTEM, FinalTechItemStacks.EXPANDED_CONSUMABLE_REMOTE_ACCESSOR, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.EXPANDED_CONSUMABLE_REMOTE_ACCESSOR).register(),
                new ExpandedConfigurableRemoteAccessor(FinalTechMenus.MENU_CARGO_SYSTEM, FinalTechItemStacks.EXPANDED_CONFIGURABLE_REMOTE_ACCESSOR, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.EXPANDED_CONFIGURABLE_REMOTE_ACCESSOR).register(),
                new AreaAccessor(FinalTechMenus.MENU_CARGO_SYSTEM, FinalTechItemStacks.AREA_ACCESSOR, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.AREA_ACCESSOR).register());
        FinalTechMenus.SUB_MENU_ACCESSOR.addTo(
                new Transporter(FinalTechMenus.MENU_CARGO_SYSTEM, FinalTechItemStacks.TRANSPORTER, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.TRANSPORTER).register(),
                new ConsumableTransporter(FinalTechMenus.MENU_CARGO_SYSTEM, FinalTechItemStacks.CONSUMABLE_TRANSPORTER, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.CONSUMABLE_TRANSPORTER).register(),
                new ConfigurableTransporter(FinalTechMenus.MENU_CARGO_SYSTEM, FinalTechItemStacks.CONFIGURABLE_TRANSPORTER, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.CONFIGURABLE_TRANSPORTER).register(),
                new ExpandedConsumableTransporter(FinalTechMenus.MENU_CARGO_SYSTEM, FinalTechItemStacks.EXPANDED_CONSUMABLE_TRANSPORTER, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.EXPANDED_CONSUMABLE_TRANSPORTER).register(),
                new ExpandedConfigurableTransporter(FinalTechMenus.MENU_CARGO_SYSTEM, FinalTechItemStacks.EXPANDED_CONFIGURABLE_TRANSPORTER, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.EXPANDED_CONFIGURABLE_TRANSPORTER).register());
        // logic
        FinalTechMenus.SUB_MENU_LOGIC.addTo(
                new LogicNotNullComparator(FinalTechMenus.MENU_CARGO_SYSTEM, FinalTechItemStacks.LOGIC_COMPARATOR_NOTNULL, FinalTechRecipes.RECIPE_TYPE_MATRIX_CRAFTING_TABLE, FinalTechRecipes.LOGIC_COMPARATOR_NOTNULL).register(),
                new LogicAmountComparator(FinalTechMenus.MENU_CARGO_SYSTEM, FinalTechItemStacks.LOGIC_COMPARATOR_AMOUNT, FinalTechRecipes.RECIPE_TYPE_MATRIX_CRAFTING_TABLE, FinalTechRecipes.LOGIC_COMPARATOR_AMOUNT).register(),
                new LogicSimilarComparator(FinalTechMenus.MENU_CARGO_SYSTEM, FinalTechItemStacks.LOGIC_COMPARATOR_SIMILAR, FinalTechRecipes.RECIPE_TYPE_MATRIX_CRAFTING_TABLE, FinalTechRecipes.LOGIC_COMPARATOR_SIMILAR).register(),
                new LogicEqualComparator(FinalTechMenus.MENU_CARGO_SYSTEM, FinalTechItemStacks.LOGIC_COMPARATOR_EQUAL, FinalTechRecipes.RECIPE_TYPE_MATRIX_CRAFTING_TABLE, FinalTechRecipes.LOGIC_COMPARATOR_EQUAL).register(),
                new LogicCrafter(FinalTechMenus.MENU_CARGO_SYSTEM, FinalTechItemStacks.LOGIC_CRAFTER, FinalTechRecipes.RECIPE_TYPE_MATRIX_CRAFTING_TABLE, FinalTechRecipes.LOGIC_CRAFTER).register(),
                new DigitAdder(FinalTechMenus.MENU_CARGO_SYSTEM, FinalTechItemStacks.DIGIT_ADDER, FinalTechRecipes.RECIPE_TYPE_MATRIX_CRAFTING_TABLE, FinalTechRecipes.DIGIT_ADDER).register());
        // cargo
        FinalTechMenus.SUB_MENU_CARGO.addTo(
                new BasicFrameMachine(FinalTechMenus.MENU_CARGO_SYSTEM, FinalTechItemStacks.BASIC_FRAME_MACHINE, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.BASIC_FRAME_MACHINE).register(),
                new PointTransfer(FinalTechMenus.MENU_CARGO_SYSTEM, FinalTechItemStacks.POINT_TRANSFER, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.POINT_TRANSFER).register(),
                new MeshTransfer(FinalTechMenus.MENU_CARGO_SYSTEM, FinalTechItemStacks.MESH_TRANSFER, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.MESH_TRANSFER).register(),
                new LineTransfer(FinalTechMenus.MENU_CARGO_SYSTEM, FinalTechItemStacks.LINE_TRANSFER, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.LINE_TRANSFER).register(),
                new LocationTransfer(FinalTechMenus.MENU_CARGO_SYSTEM, FinalTechItemStacks.LOCATION_TRANSFER, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.LOCATION_TRANSFER).register());
        FinalTechMenus.SUB_MENU_CARGO.addTo(
                new AdvancedPointTransfer(FinalTechMenus.MENU_CARGO_SYSTEM, FinalTechItemStacks.ADVANCED_POINT_TRANSFER, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.ADVANCED_POINT_TRANSFER).register(),
                new AdvancedMeshTransfer(FinalTechMenus.MENU_CARGO_SYSTEM, FinalTechItemStacks.ADVANCED_MESH_TRANSFER, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.ADVANCED_MESH_TRANSFER).register(),
                new AdvancedLineTransfer(FinalTechMenus.MENU_CARGO_SYSTEM, FinalTechItemStacks.ADVANCED_LINE_TRANSFER, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.ADVANCED_LINE_TRANSFER).register(),
                new AdvancedLocationTransfer(FinalTechMenus.MENU_CARGO_SYSTEM, FinalTechItemStacks.ADVANCED_LOCATION_TRANSFER, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.ADVANCED_LOCATION_TRANSFER).register());
        FinalTechMenus.SUB_MENU_CARGO.addTo(
                new ConfigurationCopier(FinalTechMenus.MENU_CARGO_SYSTEM, FinalTechItemStacks.CONFIGURATION_COPIER, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.CONFIGURATION_COPIER).register(),
                new ConfigurationPaster(FinalTechMenus.MENU_CARGO_SYSTEM, FinalTechItemStacks.CONFIGURATION_PASTER, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.CONFIGURATION_PASTER).register(),
                new ClickWorkMachine(FinalTechMenus.MENU_CARGO_SYSTEM, FinalTechItemStacks.CLICK_WORK_MACHINE, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.CLICK_WORK_MACHINE).register(),
                new SimulateClickMachine(FinalTechMenus.MENU_CARGO_SYSTEM, FinalTechItemStacks.SIMULATE_CLICK_MACHINE, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.SIMULATE_CLICK_MACHINE).register(),
                new ConsumableSimulateClickMachine(FinalTechMenus.MENU_CARGO_SYSTEM, FinalTechItemStacks.CONSUMABLE_SIMULATE_CLICK_MACHINE, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.CONSUMABLE_SIMULATE_CLICK_MACHINE).register());

        /* functional machines */
        // core machines
        FinalTechMenus.SUB_MENU_CORE_MACHINE.addTo(
                new DustFactoryDirt(FinalTechMenus.MENU_FUNCTIONAL_MACHINE, FinalTechItemStacks.ORDERED_DUST_FACTORY_DIRT, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.ORDERED_DUST_FACTORY_DIRT).register(),
                new DustFactoryStone(FinalTechMenus.MENU_FUNCTIONAL_MACHINE, FinalTechItemStacks.ORDERED_DUST_FACTORY_STONE, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.ORDERED_DUST_FACTORY_STONE).register(),
                new MatrixCraftingTable(FinalTechMenus.MENU_FUNCTIONAL_MACHINE, FinalTechItemStacks.MATRIX_CRAFTING_TABLE, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.MATRIX_CRAFTING_TABLE).register());
        FinalTechMenus.SUB_MENU_CORE_MACHINE.addTo(
                new ItemDismantleTable(FinalTechMenus.MENU_FUNCTIONAL_MACHINE, FinalTechItemStacks.ITEM_DISMANTLE_TABLE, FinalTechRecipes.RECIPE_TYPE_MATRIX_CRAFTING_TABLE, FinalTechRecipes.ITEM_DISMANTLE_TABLE).register(),
                new AutoItemDismantleTable(FinalTechMenus.MENU_FUNCTIONAL_MACHINE, FinalTechItemStacks.AUTO_ITEM_DISMANTLE_TABLE, FinalTechRecipes.RECIPE_TYPE_MATRIX_CRAFTING_TABLE, FinalTechRecipes.AUTO_ITEM_DISMANTLE_TABLE).register(),
                new EquivalentExchangeTable(FinalTechMenus.MENU_FUNCTIONAL_MACHINE, FinalTechItemStacks.EQUIVALENT_EXCHANGE_TABLE, FinalTechRecipes.RECIPE_TYPE_MATRIX_CRAFTING_TABLE, FinalTechRecipes.EQUIVALENT_EXCHANGE_TABLE).register(),
                new ItemSerializationConstructor(FinalTechMenus.MENU_FUNCTIONAL_MACHINE, FinalTechItemStacks.ITEM_SERIALIZATION_CONSTRUCTOR, FinalTechRecipes.RECIPE_TYPE_MATRIX_CRAFTING_TABLE, FinalTechRecipes.ITEM_SERIALIZATION_CONSTRUCTOR).register(),
                new ItemDeserializeParser(FinalTechMenus.MENU_FUNCTIONAL_MACHINE, FinalTechItemStacks.ITEM_DESERIALIZE_PARSER, FinalTechRecipes.RECIPE_TYPE_MATRIX_CRAFTING_TABLE, FinalTechRecipes.ITEM_DESERIALIZE_PARSER).register(),
                new CardOperationTable(FinalTechMenus.MENU_FUNCTIONAL_MACHINE, FinalTechItemStacks.CARD_OPERATION_TABLE, FinalTechRecipes.RECIPE_TYPE_MATRIX_CRAFTING_TABLE, FinalTechRecipes.CARD_OPERATION_TABLE).register(),
                new AdvancedAutoCraftFrame(FinalTechMenus.MENU_FUNCTIONAL_MACHINE, FinalTechItemStacks.ADVANCED_AUTO_CRAFT_FRAME, FinalTechRecipes.RECIPE_TYPE_MATRIX_CRAFTING_TABLE, FinalTechRecipes.ADVANCED_AUTO_CRAFT_FRAME).register());
        // special machines
        FinalTechMenus.SUB_MENU_SPECIAL_MACHINE.addTo(
                new ItemFixer(FinalTechMenus.MENU_FUNCTIONAL_MACHINE, FinalTechItemStacks.ITEM_FIXER, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.ITEM_FIXER).register(),
                new CobbleStoneFactory(FinalTechMenus.MENU_FUNCTIONAL_MACHINE, FinalTechItemStacks.COBBLESTONE_FACTORY, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.COBBLESTONE_FACTORY).register(),
                new FuelCharger(FinalTechMenus.MENU_FUNCTIONAL_MACHINE, FinalTechItemStacks.FUEL_CHARGER, FinalTechRecipes.RECIPE_TYPE_MATRIX_CRAFTING_TABLE, FinalTechRecipes.FUEL_CHARGER).register(),
                new FuelAccelerator(FinalTechMenus.MENU_FUNCTIONAL_MACHINE, FinalTechItemStacks.FUEL_ACCELERATOR, FinalTechRecipes.RECIPE_TYPE_MATRIX_CRAFTING_TABLE, FinalTechRecipes.FUEL_ACCELERATOR).register(),
                new FuelOperator(FinalTechMenus.MENU_FUNCTIONAL_MACHINE, FinalTechItemStacks.FUEL_OPERATOR, FinalTechRecipes.RECIPE_TYPE_MATRIX_CRAFTING_TABLE, FinalTechRecipes.FUEL_OPERATOR).register(),
                new OperationAccelerator(FinalTechMenus.MENU_FUNCTIONAL_MACHINE, FinalTechItemStacks.OPERATION_ACCELERATOR, FinalTechRecipes.RECIPE_TYPE_MATRIX_CRAFTING_TABLE, FinalTechRecipes.OPERATION_ACCELERATOR).register(),
                new OperationAcceleratorInfinity(FinalTechMenus.MENU_FUNCTIONAL_MACHINE, FinalTechItemStacks.OPERATION_ACCELERATOR_INFINITY, FinalTechRecipes.RECIPE_TYPE_MATRIX_CRAFTING_TABLE, FinalTechRecipes.OPERATION_ACCELERATOR_INFINITY).register());
        // tower
        FinalTechMenus.SUB_MENU_TOWER.addTo(
                new CureTower(FinalTechMenus.MENU_FUNCTIONAL_MACHINE, FinalTechItemStacks.CURE_TOWER, FinalTechRecipes.RECIPE_TYPE_MATRIX_CRAFTING_TABLE, FinalTechRecipes.CURE_TOWER).register(),
                new PurifyLevelTower(FinalTechMenus.MENU_FUNCTIONAL_MACHINE, FinalTechItemStacks.PURIFY_LEVEL_TOWER, FinalTechRecipes.RECIPE_TYPE_MATRIX_CRAFTING_TABLE, FinalTechRecipes.PURIFY_LEVEL_TOWER).register(),
                new PurifyTimeTower(FinalTechMenus.MENU_FUNCTIONAL_MACHINE, FinalTechItemStacks.PURIFY_TIME_TOWER, FinalTechRecipes.RECIPE_TYPE_MATRIX_CRAFTING_TABLE, FinalTechRecipes.PURIFY_TIME_TOWER).register());

        /* productive machine */
        // manual machine
        FinalTechMenus.SUB_MENU_MANUAL_MACHINE.addTo(
                new ManualCraftingTable(FinalTechMenus.MENU_PRODUCTIVE_MACHINE, FinalTechItemStacks.MANUAL_CRAFTING_TABLE, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.MANUAL_CRAFTING_TABLE).register(),
                new ManualEnhancedCraftingTable(FinalTechMenus.MENU_PRODUCTIVE_MACHINE, FinalTechItemStacks.MANUAL_ENHANCED_CRAFTING_TABLE, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.MANUAL_ENHANCED_CRAFTING_TABLE).register(),
                new ManualGrindStone(FinalTechMenus.MENU_PRODUCTIVE_MACHINE, FinalTechItemStacks.MANUAL_GRIND_STONE, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.MANUAL_GRIND_STONE).register(),
                new ManualArmorForge(FinalTechMenus.MENU_PRODUCTIVE_MACHINE, FinalTechItemStacks.MANUAL_ARMOR_FORGE, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.MANUAL_ARMOR_FORGE).register(),
                new ManualOreCrusher(FinalTechMenus.MENU_PRODUCTIVE_MACHINE, FinalTechItemStacks.MANUAL_ORE_CRUSHER, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.MANUAL_ORE_CRUSHER).register(),
                new ManualCompressor(FinalTechMenus.MENU_PRODUCTIVE_MACHINE, FinalTechItemStacks.MANUAL_COMPRESSOR, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.MANUAL_COMPRESSOR).register(),
                new ManualSmeltery(FinalTechMenus.MENU_PRODUCTIVE_MACHINE, FinalTechItemStacks.MANUAL_SMELTERY, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.MANUAL_SMELTERY).register(),
                new ManualPressureChamber(FinalTechMenus.MENU_PRODUCTIVE_MACHINE, FinalTechItemStacks.MANUAL_PRESSURE_CHAMBER, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.MANUAL_PRESSURE_CHAMBER).register(),
                new ManualMagicWorkbench(FinalTechMenus.MENU_PRODUCTIVE_MACHINE, FinalTechItemStacks.MANUAL_MAGIC_WORKBENCH, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.MANUAL_MAGIC_WORKBENCH).register(),
                new ManualOreWasher(FinalTechMenus.MENU_PRODUCTIVE_MACHINE, FinalTechItemStacks.MANUAL_ORE_WASHER, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.MANUAL_ORE_WASHER).register(),
                new ManualComposter(FinalTechMenus.MENU_PRODUCTIVE_MACHINE, FinalTechItemStacks.MANUAL_COMPOSTER, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.MANUAL_COMPOSTER).register(),
                new ManualGoldPan(FinalTechMenus.MENU_PRODUCTIVE_MACHINE, FinalTechItemStacks.MANUAL_GOLD_PAN, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.MANUAL_GOLD_PAN).register(),
                new ManualCrucible(FinalTechMenus.MENU_PRODUCTIVE_MACHINE, FinalTechItemStacks.MANUAL_CRUCIBLE, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.MANUAL_CRUCIBLE).register(),
                new ManualJuicer(FinalTechMenus.MENU_PRODUCTIVE_MACHINE, FinalTechItemStacks.MANUAL_JUICER, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.MANUAL_JUICER).register(),
                new ManualAncientAltar(FinalTechMenus.MENU_PRODUCTIVE_MACHINE, FinalTechItemStacks.MANUAL_ANCIENT_ALTAR, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.MANUAL_ANCIENT_ALTAR).register(),
                new ManualHeatedPressureChamber(FinalTechMenus.MENU_PRODUCTIVE_MACHINE, FinalTechItemStacks.MANUAL_HEATED_PRESSURE_CHAMBER, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.MANUAL_HEATED_PRESSURE_CHAMBER).register());
        // basic machines
        FinalTechMenus.SUB_MENU_BASIC_MACHINE.addTo(
                new BasicLogicFactory(FinalTechMenus.MENU_PRODUCTIVE_MACHINE, FinalTechItemStacks.BASIC_LOGIC_FACTORY, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.BASIC_LOGIC_FACTORY).register());
        // advanced machine
        FinalTechMenus.SUB_MENU_ADVANCED_MACHINE.addTo(
                new AdvancedComposter(FinalTechMenus.MENU_PRODUCTIVE_MACHINE, FinalTechItemStacks.ADVANCED_COMPOSTER, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.ADVANCED_COMPOSTER).register(),
                new AdvancedJuicer(FinalTechMenus.MENU_PRODUCTIVE_MACHINE, FinalTechItemStacks.ADVANCED_JUICER, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.ADVANCED_JUICER).register(),
                new AdvancedFurnace(FinalTechMenus.MENU_PRODUCTIVE_MACHINE, FinalTechItemStacks.ADVANCED_FURNACE, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.ADVANCED_FURNACE).register(),
                new AdvancedGoldPan(FinalTechMenus.MENU_PRODUCTIVE_MACHINE, FinalTechItemStacks.ADVANCED_GOLD_PAN, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.ADVANCED_GOLD_PAN).register(),
                new AdvancedDustWasher(FinalTechMenus.MENU_PRODUCTIVE_MACHINE, FinalTechItemStacks.ADVANCED_DUST_WASHER, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.ADVANCED_DUST_WASHER).register(),
                new AdvancedIngotFactory(FinalTechMenus.MENU_PRODUCTIVE_MACHINE, FinalTechItemStacks.ADVANCED_INGOT_FACTORY, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.ADVANCED_INGOT_FACTORY).register(),
                new AdvancedCrucible(FinalTechMenus.MENU_PRODUCTIVE_MACHINE, FinalTechItemStacks.ADVANCED_CRUCIBLE, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.ADVANCED_CRUCIBLE).register(),
                new AdvancedOreGrinder(FinalTechMenus.MENU_PRODUCTIVE_MACHINE, FinalTechItemStacks.ADVANCED_ORE_GRINDER, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.ADVANCED_ORE_GRINDER).register(),
                new AdvancedHeatedPressureChamber(FinalTechMenus.MENU_PRODUCTIVE_MACHINE, FinalTechItemStacks.ADVANCED_HEATED_PRESSURE_CHAMBER, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.ADVANCED_HEATED_PRESSURE_CHAMBER).register(),
                new AdvancedIngotPulverizer(FinalTechMenus.MENU_PRODUCTIVE_MACHINE, FinalTechItemStacks.ADVANCED_INGOT_PULVERIZER, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.ADVANCED_INGOT_PULVERIZER).register(),
                new AdvancedAutoDrier(FinalTechMenus.MENU_PRODUCTIVE_MACHINE, FinalTechItemStacks.ADVANCED_AUTO_DRIER, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.ADVANCED_AUTO_DRIER).register(),
                new AdvancedPress(FinalTechMenus.MENU_PRODUCTIVE_MACHINE, FinalTechItemStacks.ADVANCED_PRESS, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.ADVANCED_PRESS).register(),
                new AdvancedFoodFactory(FinalTechMenus.MENU_PRODUCTIVE_MACHINE, FinalTechItemStacks.ADVANCED_FOOD_FACTORY, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.ADVANCED_FOOD_FACTORY).register(),
                new AdvancedFreezer(FinalTechMenus.MENU_PRODUCTIVE_MACHINE, FinalTechItemStacks.ADVANCED_FREEZER, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.ADVANCED_FREEZER).register(),
                new AdvancedCarbonPress(FinalTechMenus.MENU_PRODUCTIVE_MACHINE, FinalTechItemStacks.ADVANCED_CARBON_PRESS, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.ADVANCED_CARBON_PRESS).register(),
                new AdvancedSmeltery(FinalTechMenus.MENU_PRODUCTIVE_MACHINE, FinalTechItemStacks.ADVANCED_SMELTERY, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.ADVANCED_SMELTERY).register());
        // conversion
        FinalTechMenus.SUB_MENU_CONVERSION.addTo(
                new GravelConversion(FinalTechMenus.MENU_PRODUCTIVE_MACHINE, FinalTechItemStacks.GRAVEL_CONVERSION, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.GRAVEL_CONVERSION).register(),
                new SoulSandConversion(FinalTechMenus.MENU_PRODUCTIVE_MACHINE, FinalTechItemStacks.SOUL_SAND_CONVERSION, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.SOUL_SAND_CONVERSION).register(),
                new LogicToDigitalConversion(FinalTechMenus.MENU_PRODUCTIVE_MACHINE, FinalTechItemStacks.LOGIC_TO_DIGITAL_CONVERSION, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.LOGIC_TO_DIGITAL_CONVERSION).register());
        // extraction
        FinalTechMenus.SUB_MENU_EXTRACTION.addTo(
                new DigitalExtraction(FinalTechMenus.MENU_PRODUCTIVE_MACHINE, FinalTechItemStacks.DIGITAL_EXTRACTION, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.DIGITAL_EXTRACTION).register());
        // generator
        FinalTechMenus.SUB_MENU_GENERATOR.addTo(
                new LiquidCardGenerator(FinalTechMenus.MENU_PRODUCTIVE_MACHINE, FinalTechItemStacks.LIQUID_CARD_GENERATOR, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.LIQUID_CARD_GENERATOR).register(),
                new LogicGenerator(FinalTechMenus.MENU_PRODUCTIVE_MACHINE, FinalTechItemStacks.LOGIC_GENERATOR, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.LOGIC_GENERATOR).register(),
                new DigitalGenerator(FinalTechMenus.MENU_PRODUCTIVE_MACHINE, FinalTechItemStacks.DIGITAL_GENERATOR, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.DIGITAL_GENERATOR).register());

        /* final stage item */
        FinalTechMenus.SUB_MENU_FINAL_ITEM.addTo(
                new EntropySeed(FinalTechMenus.MENU_DISC, FinalTechItemStacks.ENTROPY_SEED, FinalTechRecipes.RECIPE_TYPE_MATRIX_CRAFTING_TABLE, FinalTechRecipes.ENTROPY_SEED).register(),
                new InfinityMachineChargeCard(FinalTechMenus.MENU_DISC, FinalTechItemStacks.MACHINE_CHARGE_CARD_INFINITY, FinalTechRecipes.RECIPE_TYPE_MATRIX_CRAFTING_TABLE, FinalTechRecipes.MACHINE_CHARGE_CARD_INFINITY).register(),
                new InfinityMachineAccelerateCard(FinalTechMenus.MENU_DISC, FinalTechItemStacks.MACHINE_ACCELERATE_CARD_INFINITY, FinalTechRecipes.RECIPE_TYPE_MATRIX_CRAFTING_TABLE, FinalTechRecipes.MACHINE_ACCELERATE_CARD_INFINITY).register(),
                new MatrixMachineActivateCard(FinalTechMenus.MENU_DISC, FinalTechItemStacks.MACHINE_ACTIVATE_CARD_L4, FinalTechRecipes.RECIPE_TYPE_MATRIX_CRAFTING_TABLE, FinalTechRecipes.MACHINE_ACTIVATE_CARD_L4).register());
        FinalTechMenus.SUB_MENU_FINAL_ITEM.addTo(
                new AdvancedAutoCraft(FinalTechMenus.MENU_DISC, FinalTechItemStacks.ADVANCED_AUTO_CRAFT, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.ADVANCED_AUTO_CRAFT).register(),
                new MultiFrameMachine(FinalTechMenus.MENU_DISC, FinalTechItemStacks.MULTI_FRAME_MACHINE, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.MULTI_FRAME_MACHINE).register(),
                new MatrixItemDismantleTable(FinalTechMenus.MENU_DISC, FinalTechItemStacks.MATRIX_ITEM_DISMANTLE_TABLE, FinalTechRecipes.RECIPE_TYPE_MATRIX_CRAFTING_TABLE, FinalTechRecipes.MATRIX_ITEM_DISMANTLE_TABLE).register(),
                new MatrixExpandedCapacitor(FinalTechMenus.MENU_DISC, FinalTechItemStacks.MATRIX_EXPANDED_CAPACITOR, FinalTechRecipes.RECIPE_TYPE_MATRIX_CRAFTING_TABLE, FinalTechRecipes.MATRIX_EXPANDED_CAPACITOR).register(),
                new MatrixItemDeserializeParser(FinalTechMenus.MENU_DISC, FinalTechItemStacks.MATRIX_ITEM_DESERIALIZE_PARSER, FinalTechRecipes.RECIPE_TYPE_MATRIX_CRAFTING_TABLE, FinalTechRecipes.MATRIX_ITEM_DESERIALIZE_PARSER).register(),
                new EntropyConstructor(FinalTechMenus.MENU_DISC, FinalTechItemStacks.ENTROPY_CONSTRUCTOR, FinalTechRecipes.RECIPE_TYPE_MATRIX_CRAFTING_TABLE, FinalTechRecipes.ENTROPY_CONSTRUCTOR).register(),
                new MatrixGenerator(FinalTechMenus.MENU_DISC, FinalTechItemStacks.MATRIX_GENERATOR, FinalTechRecipes.RECIPE_TYPE_MATRIX_CRAFTING_TABLE, FinalTechRecipes.MATRIX_GENERATOR).register(),
                new MatrixAccelerator(FinalTechMenus.MENU_DISC, FinalTechItemStacks.MATRIX_ACCELERATOR, FinalTechRecipes.RECIPE_TYPE_MATRIX_CRAFTING_TABLE, FinalTechRecipes.MATRIX_ACCELERATOR).register(),
                new MatrixItemSerializationConstructor(FinalTechMenus.MENU_DISC, FinalTechItemStacks.MATRIX_ITEM_SERIALIZATION_CONSTRUCTOR, FinalTechRecipes.RECIPE_TYPE_MATRIX_CRAFTING_TABLE, FinalTechRecipes.MATRIX_ITEM_SERIALIZATION_CONSTRUCTOR).register(),
                new MatrixReactor(FinalTechMenus.MENU_DISC, FinalTechItemStacks.MATRIX_REACTOR, FinalTechRecipes.RECIPE_TYPE_MATRIX_CRAFTING_TABLE, FinalTechRecipes.MATRIX_REACTOR).register());
        FinalTechMenus.SUB_MENU_TROPHY.addTo(
                new Meawerful(FinalTechMenus.MENU_DISC, FinalTechItemStacks.TROPHY_MEAWERFUL, RecipeType.NULL, new ItemStack[0]).register(),
                new Shixinzia(FinalTechMenus.MENU_DISC, FinalTechItemStacks.TROPHY_SHIXINZIA, RecipeType.NULL, FinalTechRecipes.TROPHY_SHIXINZIA).register());
        FinalTechMenus.SUB_MENU_DEPRECATED.addTo(
                new BasicChargeIncreaseCapacitor(FinalTechMenus.MENU_DISC, FinalTechItemStacks.BASIC_CHARGE_INCREASE_CAPACITOR, RecipeType.NULL, new ItemStack[0]).register(),
                new BasicConsumeReduceCapacitor(FinalTechMenus.MENU_DISC, FinalTechItemStacks.BASIC_CONSUME_REDUCE_CAPACITOR, RecipeType.NULL, new ItemStack[0]).register(),
                new AdvancedChargeIncreaseCapacitor(FinalTechMenus.MENU_DISC, FinalTechItemStacks.ADVANCED_CHARGE_INCREASE_CAPACITOR, RecipeType.NULL, new ItemStack[0]).register(),
                new AdvancedConsumeReduceCapacitor(FinalTechMenus.MENU_DISC, FinalTechItemStacks.ADVANCED_CONSUME_REDUCE_CAPACITOR, RecipeType.NULL, new ItemStack[0]).register(),
                new BasicCobbleFactory(FinalTechMenus.MENU_DISC, FinalTechItemStacks.BASIC_COBBLE_FACTORY, RecipeType.NULL, new ItemStack[0]).register(),
                new BasicStoneFactory(FinalTechMenus.MENU_DISC, FinalTechItemStacks.BASIC_STONE_FACTORY, RecipeType.NULL, new ItemStack[0]).register(),
                new BasicDustFactory(FinalTechMenus.MENU_DISC, FinalTechItemStacks.BASIC_DUST_FACTORY, RecipeType.NULL, new ItemStack[0]).register(),
                new DustConversion(FinalTechMenus.MENU_DISC, FinalTechItemStacks.DUST_CONVERSION, RecipeType.NULL, new ItemStack[0]).register(),
                new AdvancedDustFactory(FinalTechMenus.MENU_PRODUCTIVE_MACHINE, FinalTechItemStacks.ADVANCED_DUST_FACTORY, RecipeType.NULL, new ItemStack[0]).register(),
                new ConcreteConversion(FinalTechMenus.MENU_DISC, FinalTechItemStacks.CONCRETE_CONVERSION, RecipeType.NULL, new ItemStack[0]).register(),
                new WoolConversion(FinalTechMenus.MENU_DISC, FinalTechItemStacks.WOOL_CONVERSION, RecipeType.NULL, new ItemStack[0]).register(),
                new OreExtraction(FinalTechMenus.MENU_DISC, FinalTechItemStacks.ORE_EXTRACTION, RecipeType.NULL, new ItemStack[0]).register(),
                new PlankGenerator(FinalTechMenus.MENU_DISC, FinalTechItemStacks.PLANK_GENERATOR, RecipeType.NULL, new ItemStack[0]).register(),
                new SandGenerator(FinalTechMenus.MENU_DISC, FinalTechItemStacks.SAND_GENERATOR, RecipeType.NULL, new ItemStack[0]).register(),
                new StoneGenerator(FinalTechMenus.MENU_DISC, FinalTechItemStacks.STONE_GENERATOR, RecipeType.NULL, new ItemStack[0]).register(),
                new RawStoneGenerator(FinalTechMenus.MENU_DISC, FinalTechItemStacks.RAW_STONE_GENERATOR, RecipeType.NULL, new ItemStack[0]).register(),
                new NetherStoneGenerator(FinalTechMenus.MENU_DISC, FinalTechItemStacks.NETHER_STONE_GENERATOR, RecipeType.NULL, new ItemStack[0]).register());
    }

    private static void setupMenu() {
        FinalTech finalTech = FinalTech.getInstance();

        FinalTechMenus.MAIN_MENU.setTier(0);

        FinalTechMenus.MENU_ITEMS.setTier(0);
        FinalTechMenus.MENU_ELECTRICITY_SYSTEM.setTier(0);
        FinalTechMenus.MENU_CARGO_SYSTEM.setTier(0);
        FinalTechMenus.MENU_FUNCTIONAL_MACHINE.setTier(0);
        FinalTechMenus.MENU_PRODUCTIVE_MACHINE.setTier(0);
        FinalTechMenus.MENU_DISC.setTier(0);

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
    }

    private static void setupResearch() {
        ResearchUtil.setResearches(FinalTech.getLanguageManager(), "LIQUID_CARD", 1, false,
                FinalTechItemStacks.WATER_CARD,
                FinalTechItemStacks.LAVA_CARD,
                FinalTechItemStacks.MILK_CARD,
                FinalTechItemStacks.LIQUID_CARD_GENERATOR);
        ResearchUtil.setResearches(FinalTech.getLanguageManager(), "ORDER_DUST", Slimefun.getInstalledAddons().size(), true,
                FinalTechItemStacks.ORDERED_DUST,
                FinalTechItemStacks.UNORDERED_DUST,
                FinalTechItemStacks.ORDERED_DUST_FACTORY_DIRT,
                FinalTechItemStacks.ORDERED_DUST_FACTORY_STONE,
                FinalTechItemStacks.ORDERED_DUST_GENERATOR);
        ResearchUtil.setResearches(FinalTech.getLanguageManager(), "BUG", (int)Math.pow(Slimefun.getRegistry().getEnabledSlimefunItems().size(), 0.5), true,
                FinalTechItemStacks.BUG,
                FinalTechItemStacks.EQUIVALENT_EXCHANGE_TABLE,
                FinalTechItemStacks.BASIC_LOGIC_FACTORY);
        ResearchUtil.setResearches(FinalTech.getLanguageManager(), "ENTROPY", (int)Math.pow(Slimefun.getRegistry().getResearches().size(), 0.5), true,
                FinalTechItemStacks.ENTROPY,
                FinalTechItemStacks.ENTROPY_CONSTRUCTOR,
                FinalTechItemStacks.ENTROPY_SEED);
        ResearchUtil.setResearches(FinalTech.getLanguageManager(), "BOX", Math.abs((int)FinalTech.getSeed()) % 20, true,
                FinalTechItemStacks.BOX,
                FinalTechItemStacks.SHINE);
        ResearchUtil.setResearches(FinalTech.getLanguageManager(), "ANNULAR", (int)Math.pow(ConstantTableUtil.ITEM_COPY_CARD_AMOUNT, 0.25), true,
                FinalTechItemStacks.COPY_CARD,
                FinalTechItemStacks.ANNULAR,
                FinalTechItemStacks.QUANTITY_MODULE,
                FinalTechItemStacks.QUANTITY_MODULE_INFINITY,
                FinalTechItemStacks.ITEM_SERIALIZATION_CONSTRUCTOR,
                FinalTechItemStacks.ITEM_DESERIALIZE_PARSER);
        ResearchUtil.setResearches(FinalTech.getLanguageManager(), "PHONY", (int)Math.pow(ConstantTableUtil.ITEM_SINGULARITY_AMOUNT * ConstantTableUtil.ITEM_SPIROCHETE_AMOUNT, 0.6), true,
                FinalTechItemStacks.SINGULARITY,
                FinalTechItemStacks.SPIROCHETE,
                FinalTechItemStacks.SHELL,
                FinalTechItemStacks.PHONY,
                FinalTechItemStacks.JUSTIFIABILITY,
                FinalTechItemStacks.EQUIVALENT_CONCEPT,
                FinalTechItemStacks.ENTROPY_SEED,
                FinalTechItemStacks.MACHINE_CHARGE_CARD_INFINITY,
                FinalTechItemStacks.MACHINE_ACCELERATE_CARD_INFINITY,
                FinalTechItemStacks.MACHINE_ACTIVATE_CARD_L4,
                FinalTechItemStacks.ADVANCED_AUTO_CRAFT,
                FinalTechItemStacks.MULTI_FRAME_MACHINE,
                FinalTechItemStacks.MATRIX_ITEM_DISMANTLE_TABLE,
                FinalTechItemStacks.MATRIX_EXPANDED_CAPACITOR,
                FinalTechItemStacks.MATRIX_ITEM_SERIALIZATION_CONSTRUCTOR,
                FinalTechItemStacks.MATRIX_GENERATOR,
                FinalTechItemStacks.MATRIX_ACCELERATOR,
                FinalTechItemStacks.MATRIX_ITEM_DESERIALIZE_PARSER,
                FinalTechItemStacks.MATRIX_REACTOR);
        ResearchUtil.setResearches(FinalTech.getLanguageManager(), "LOGIC", 1, false,
                FinalTechItemStacks.LOGIC_FALSE,
                FinalTechItemStacks.LOGIC_TRUE,
                FinalTechItemStacks.DIGITAL_ZERO,
                FinalTechItemStacks.DIGITAL_ONE,
                FinalTechItemStacks.DIGITAL_TWO,
                FinalTechItemStacks.DIGITAL_THREE,
                FinalTechItemStacks.DIGITAL_FOUR,
                FinalTechItemStacks.DIGITAL_FIVE,
                FinalTechItemStacks.DIGITAL_SIX,
                FinalTechItemStacks.DIGITAL_SEVEN,
                FinalTechItemStacks.DIGITAL_EIGHT,
                FinalTechItemStacks.DIGITAL_NINE,
                FinalTechItemStacks.DIGITAL_TEN,
                FinalTechItemStacks.DIGITAL_ELEVEN,
                FinalTechItemStacks.DIGITAL_TWELVE,
                FinalTechItemStacks.DIGITAL_THIRTEEN,
                FinalTechItemStacks.DIGITAL_FOURTEEN,
                FinalTechItemStacks.DIGITAL_FIFTEEN,
                FinalTechItemStacks.LOGIC_COMPARATOR_NOTNULL,
                FinalTechItemStacks.LOGIC_COMPARATOR_AMOUNT,
                FinalTechItemStacks.LOGIC_COMPARATOR_SIMILAR,
                FinalTechItemStacks.LOGIC_COMPARATOR_EQUAL,
                FinalTechItemStacks.LOGIC_CRAFTER,
                FinalTechItemStacks.DIGIT_ADDER,
                FinalTechItemStacks.LOGIC_GENERATOR,
                FinalTechItemStacks.DIGITAL_GENERATOR,
                FinalTechItemStacks.LOGIC_TO_DIGITAL_CONVERSION,
                FinalTechItemStacks.DIGITAL_EXTRACTION);
        ResearchUtil.setResearches(FinalTech.getLanguageManager(), "MACHINE_CARD", 10, false,
                FinalTechItemStacks.MACHINE_CHARGE_CARD_L1,
                FinalTechItemStacks.MACHINE_CHARGE_CARD_L2,
                FinalTechItemStacks.MACHINE_CHARGE_CARD_L3,
                FinalTechItemStacks.MACHINE_ACCELERATE_CARD_L1,
                FinalTechItemStacks.MACHINE_ACCELERATE_CARD_L2,
                FinalTechItemStacks.MACHINE_ACCELERATE_CARD_L3,
                FinalTechItemStacks.MACHINE_ACTIVATE_CARD_L1,
                FinalTechItemStacks.MACHINE_ACTIVATE_CARD_L2,
                FinalTechItemStacks.MACHINE_ACTIVATE_CARD_L3);
        ResearchUtil.setResearches(FinalTech.getLanguageManager(), "CONSUMABLE_ITEM", 10, false,
                FinalTechItemStacks.MAGIC_HYPNOTIC,
                FinalTechItemStacks.RESEARCH_UNLOCK_TICKET);
        ResearchUtil.setResearches(FinalTech.getLanguageManager(), "SIMPLE_TOOL", 10, false,
                FinalTechItemStacks.STAFF_ELEMENTAL_LINE,
                FinalTechItemStacks.POTION_EFFECT_COMPRESSOR,
                FinalTechItemStacks.POTION_EFFECT_DILATOR,
                FinalTechItemStacks.POTION_EFFECT_PURIFIER,
                FinalTechItemStacks.GRAVITY_REVERSAL_RUNE);
        ResearchUtil.setResearches(FinalTech.getLanguageManager(), "MACHINE_TOOL", 10, false,
                FinalTechItemStacks.MENU_VIEWER,
                FinalTechItemStacks.LOCATION_RECORDER,
                FinalTechItemStacks.MACHINE_CONFIGURATOR,
                FinalTechItemStacks.PORTABLE_ENERGY_STORAGE);

        ResearchUtil.setResearchBySlimefunItems(FinalTechItemStacks.SUPER_SHOVEL, SlimefunItems.SOULBOUND_SHOVEL);
        ResearchUtil.setResearchBySlimefunItems(FinalTechItemStacks.ULTIMATE_SHOVEL, SlimefunItems.SOULBOUND_SHOVEL);
        ResearchUtil.setResearchBySlimefunItems(FinalTechItemStacks.SUPER_PICKAXE, SlimefunItems.SOULBOUND_PICKAXE);
        ResearchUtil.setResearchBySlimefunItems(FinalTechItemStacks.ULTIMATE_PICKAXE, SlimefunItems.SOULBOUND_PICKAXE);
        ResearchUtil.setResearchBySlimefunItems(FinalTechItemStacks.SUPER_AXE, SlimefunItems.SOULBOUND_AXE);
        ResearchUtil.setResearchBySlimefunItems(FinalTechItemStacks.ULTIMATE_AXE, SlimefunItems.SOULBOUND_AXE);
        ResearchUtil.setResearchBySlimefunItems(FinalTechItemStacks.SUPER_HOE, SlimefunItems.SOULBOUND_HOE);
        ResearchUtil.setResearchBySlimefunItems(FinalTechItemStacks.ULTIMATE_HOE, SlimefunItems.SOULBOUND_HOE);

        ResearchUtil.setResearchBySlimefunItems(FinalTechItemStacks.BASIC_GENERATOR, SlimefunItems.SOLAR_GENERATOR);
        ResearchUtil.setResearchBySlimefunItems(FinalTechItemStacks.ADVANCED_GENERATOR, SlimefunItems.SOLAR_GENERATOR_2);
        ResearchUtil.setResearchBySlimefunItems(FinalTechItemStacks.CARBONADO_GENERATOR, SlimefunItems.SOLAR_GENERATOR_3);
        ResearchUtil.setResearchBySlimefunItems(FinalTechItemStacks.ENERGIZED_GENERATOR, SlimefunItems.SOLAR_GENERATOR_4);
        ResearchUtil.setResearchBySlimefunItems(FinalTechItemStacks.ENERGIZED_STACK_GENERATOR, SlimefunItems.SOLAR_GENERATOR_4);
        ResearchUtil.setResearchBySlimefunItems(FinalTechItemStacks.OVERLOADED_GENERATOR, SlimefunItems.SOLAR_GENERATOR_4);
        ResearchUtil.setResearchBySlimefunItems(FinalTechItemStacks.TIME_GENERATOR, SlimefunItems.SOLAR_GENERATOR_4);
        ResearchUtil.setResearchBySlimefunItems(FinalTechItemStacks.ENERGIZED_CHARGE_BASE, SlimefunItems.SOLAR_GENERATOR_4);
        ResearchUtil.setResearchBySlimefunItems(FinalTechItemStacks.OVERLOADED_CHARGE_BASE, SlimefunItems.SOLAR_GENERATOR_4);

        ResearchUtil.setResearchBySlimefunItems(FinalTechItemStacks.SMALL_EXPANDED_CAPACITOR, SlimefunItems.SMALL_CAPACITOR);
        ResearchUtil.setResearchBySlimefunItems(FinalTechItemStacks.MEDIUM_EXPANDED_CAPACITOR, SlimefunItems.MEDIUM_CAPACITOR);
        ResearchUtil.setResearchBySlimefunItems(FinalTechItemStacks.BIG_EXPANDED_CAPACITOR, SlimefunItems.BIG_CAPACITOR);
        ResearchUtil.setResearchBySlimefunItems(FinalTechItemStacks.LARGE_EXPANDED_CAPACITOR, SlimefunItems.LARGE_CAPACITOR);
        ResearchUtil.setResearchBySlimefunItems(FinalTechItemStacks.CARBONADO_EXPANDED_CAPACITOR, SlimefunItems.CARBONADO_EDGED_CAPACITOR);
        ResearchUtil.setResearchBySlimefunItems(FinalTechItemStacks.ENERGIZED_EXPANDED_CAPACITOR, SlimefunItems.ENERGIZED_CAPACITOR);
        ResearchUtil.setResearchBySlimefunItems(FinalTechItemStacks.ENERGIZED_STACK_EXPANDED_CAPACITOR, SlimefunItems.ENERGIZED_CAPACITOR);
        ResearchUtil.setResearchBySlimefunItems(FinalTechItemStacks.OVERLOADED_EXPANDED_CAPACITOR, SlimefunItems.ENERGIZED_CAPACITOR);
        ResearchUtil.setResearchBySlimefunItems(FinalTechItemStacks.TIME_CAPACITOR, SlimefunItems.ENERGIZED_CAPACITOR);

        ResearchUtil.setResearchBySlimefunItems(FinalTechItemStacks.NORMAL_ELECTRICITY_SHOOT_PILE, SlimefunItems.ENERGY_REGULATOR);
        ResearchUtil.setResearchBySlimefunItems(FinalTechItemStacks.NORMAL_CONSUMABLE_ELECTRICITY_SHOOT_PILE, SlimefunItems.ENERGY_REGULATOR);
        ResearchUtil.setResearchBySlimefunItems(FinalTechItemStacks.NORMAL_CONFIGURABLE_ELECTRICITY_SHOOT_PILE, SlimefunItems.ENERGY_REGULATOR);
        ResearchUtil.setResearchBySlimefunItems(FinalTechItemStacks.ENERGIZED_ELECTRICITY_SHOOT_PILE, SlimefunItems.ENERGY_REGULATOR);
        ResearchUtil.setResearchBySlimefunItems(FinalTechItemStacks.OVERLOADED_ELECTRICITY_SHOOT_PILE, SlimefunItems.ENERGY_REGULATOR);

        ResearchUtil.setResearchBySlimefunItems(FinalTechItemStacks.VARIABLE_WIRE_RESISTANCE, SlimefunItems.ENERGY_CONNECTOR);
        ResearchUtil.setResearchBySlimefunItems(FinalTechItemStacks.VARIABLE_WIRE_CAPACITOR, SlimefunItems.ENERGY_CONNECTOR);

        ResearchUtil.setResearchBySlimefunItems(FinalTechItemStacks.ENERGIZED_ACCELERATOR, SlimefunItems.NETHER_STAR_REACTOR);
        ResearchUtil.setResearchBySlimefunItems(FinalTechItemStacks.OVERLOADED_ACCELERATOR, SlimefunItems.NETHER_STAR_REACTOR);

        ResearchUtil.setResearchBySlimefunItems(FinalTechItemStacks.NORMAL_STORAGE_UNIT, SlimefunItems.BACKPACK_SMALL);
        ResearchUtil.setResearchBySlimefunItems(FinalTechItemStacks.DIVIDED_STORAGE_UNIT, SlimefunItems.WOVEN_BACKPACK);
        ResearchUtil.setResearchBySlimefunItems(FinalTechItemStacks.LIMITED_STORAGE_UNIT, SlimefunItems.WOVEN_BACKPACK);
        ResearchUtil.setResearchBySlimefunItems(FinalTechItemStacks.STACK_STORAGE_UNIT, SlimefunItems.WOVEN_BACKPACK);
        ResearchUtil.setResearchBySlimefunItems(FinalTechItemStacks.DIVIDED_LIMITED_STORAGE_UNIT, SlimefunItems.GILDED_BACKPACK);
        ResearchUtil.setResearchBySlimefunItems(FinalTechItemStacks.DIVIDED_STACK_STORAGE_UNIT, SlimefunItems.GILDED_BACKPACK);
        ResearchUtil.setResearchBySlimefunItems(FinalTechItemStacks.LIMITED_STACK_STORAGE_UNIT, SlimefunItems.GILDED_BACKPACK);
        ResearchUtil.setResearchBySlimefunItems(FinalTechItemStacks.RANDOM_INPUT_STORAGE_UNIT, SlimefunItems.WOVEN_BACKPACK);
        ResearchUtil.setResearchBySlimefunItems(FinalTechItemStacks.RANDOM_OUTPUT_STORAGE_UNIT, SlimefunItems.WOVEN_BACKPACK);
        ResearchUtil.setResearchBySlimefunItems(FinalTechItemStacks.RANDOM_ACCESS_STORAGE_UNIT, SlimefunItems.WOVEN_BACKPACK);
        ResearchUtil.setResearchBySlimefunItems(FinalTechItemStacks.DISTRIBUTE_LEFT_STORAGE_UNIT, SlimefunItems.ANDROID_INTERFACE_FUEL);
        ResearchUtil.setResearchBySlimefunItems(FinalTechItemStacks.DISTRIBUTE_RIGHT_STORAGE_UNIT, SlimefunItems.ANDROID_INTERFACE_ITEMS);

        ResearchUtil.setResearchBySlimefunItems(FinalTechItemStacks.STORAGE_INTERACT_PORT, SlimefunItems.RADIANT_BACKPACK);
        ResearchUtil.setResearchBySlimefunItems(FinalTechItemStacks.STORAGE_INSERT_PORT, SlimefunItems.RADIANT_BACKPACK);
        ResearchUtil.setResearchBySlimefunItems(FinalTechItemStacks.STORAGE_WITHDRAW_PORT, SlimefunItems.RADIANT_BACKPACK);
        ResearchUtil.setResearchBySlimefunItems(FinalTechItemStacks.STORAGE_CARD, SlimefunItems.RADIANT_BACKPACK);

        ResearchUtil.setResearchBySlimefunItems(FinalTechItemStacks.REMOTE_ACCESSOR, SlimefunItems.REACTOR_ACCESS_PORT);
        ResearchUtil.setResearchBySlimefunItems(FinalTechItemStacks.CONSUMABLE_REMOTE_ACCESSOR, SlimefunItems.REACTOR_ACCESS_PORT);
        ResearchUtil.setResearchBySlimefunItems(FinalTechItemStacks.CONFIGURABLE_REMOTE_ACCESSOR, SlimefunItems.REACTOR_ACCESS_PORT);
        ResearchUtil.setResearchBySlimefunItems(FinalTechItemStacks.EXPANDED_CONSUMABLE_REMOTE_ACCESSOR, SlimefunItems.REACTOR_ACCESS_PORT);
        ResearchUtil.setResearchBySlimefunItems(FinalTechItemStacks.EXPANDED_CONFIGURABLE_REMOTE_ACCESSOR, SlimefunItems.REACTOR_ACCESS_PORT);
        ResearchUtil.setResearchBySlimefunItems(FinalTechItemStacks.AREA_ACCESSOR, SlimefunItems.REACTOR_ACCESS_PORT);

        ResearchUtil.setResearchBySlimefunItems(FinalTechItemStacks.TRANSPORTER, SlimefunItems.GPS_TELEPORTATION_MATRIX);
        ResearchUtil.setResearchBySlimefunItems(FinalTechItemStacks.CONSUMABLE_TRANSPORTER, SlimefunItems.GPS_TELEPORTATION_MATRIX);
        ResearchUtil.setResearchBySlimefunItems(FinalTechItemStacks.CONFIGURABLE_TRANSPORTER, SlimefunItems.GPS_TELEPORTATION_MATRIX);
        ResearchUtil.setResearchBySlimefunItems(FinalTechItemStacks.EXPANDED_CONSUMABLE_TRANSPORTER, SlimefunItems.GPS_TELEPORTATION_MATRIX);
        ResearchUtil.setResearchBySlimefunItems(FinalTechItemStacks.EXPANDED_CONFIGURABLE_TRANSPORTER, SlimefunItems.GPS_TELEPORTATION_MATRIX);

        ResearchUtil.setResearchBySlimefunItems(FinalTechItemStacks.BASIC_FRAME_MACHINE, SlimefunItems.CARGO_CONNECTOR_NODE);
        ResearchUtil.setResearchBySlimefunItems(FinalTechItemStacks.POINT_TRANSFER, SlimefunItems.CARGO_MANAGER);
        ResearchUtil.setResearchBySlimefunItems(FinalTechItemStacks.MESH_TRANSFER, SlimefunItems.CARGO_MANAGER);
        ResearchUtil.setResearchBySlimefunItems(FinalTechItemStacks.LINE_TRANSFER, SlimefunItems.CARGO_MANAGER);
        ResearchUtil.setResearchBySlimefunItems(FinalTechItemStacks.LOCATION_TRANSFER, SlimefunItems.CARGO_MANAGER);

        ResearchUtil.setResearchBySlimefunItems(FinalTechItemStacks.ADVANCED_POINT_TRANSFER, SlimefunItems.CARGO_MANAGER);
        ResearchUtil.setResearchBySlimefunItems(FinalTechItemStacks.ADVANCED_MESH_TRANSFER, SlimefunItems.CARGO_MANAGER);
        ResearchUtil.setResearchBySlimefunItems(FinalTechItemStacks.ADVANCED_LINE_TRANSFER, SlimefunItems.CARGO_MANAGER);
        ResearchUtil.setResearchBySlimefunItems(FinalTechItemStacks.ADVANCED_LOCATION_TRANSFER, SlimefunItems.CARGO_MANAGER);

        ResearchUtil.setResearchBySlimefunItems(FinalTechItemStacks.CONFIGURATION_COPIER, SlimefunItems.CRAFTING_MOTOR);
        ResearchUtil.setResearchBySlimefunItems(FinalTechItemStacks.CONFIGURATION_PASTER, SlimefunItems.CRAFTING_MOTOR);
        ResearchUtil.setResearchBySlimefunItems(FinalTechItemStacks.CLICK_WORK_MACHINE, SlimefunItems.GPS_ACTIVATION_DEVICE_PERSONAL);
        ResearchUtil.setResearchBySlimefunItems(FinalTechItemStacks.SIMULATE_CLICK_MACHINE, SlimefunItems.GPS_ACTIVATION_DEVICE_SHARED);
        ResearchUtil.setResearchBySlimefunItems(FinalTechItemStacks.CONSUMABLE_SIMULATE_CLICK_MACHINE, SlimefunItems.GPS_ACTIVATION_DEVICE_SHARED);

        ResearchUtil.setResearchBySlimefunItems(FinalTechItemStacks.MATRIX_CRAFTING_TABLE, SlimefunItems.PROGRAMMABLE_ANDROID_2);

        ResearchUtil.setResearchBySlimefunItems(FinalTechItemStacks.ITEM_DISMANTLE_TABLE, SlimefunItems.NUCLEAR_REACTOR);
        ResearchUtil.setResearchBySlimefunItems(FinalTechItemStacks.AUTO_ITEM_DISMANTLE_TABLE, SlimefunItems.NUCLEAR_REACTOR);

        ResearchUtil.setResearchBySlimefunItems(FinalTechItemStacks.CARD_OPERATION_TABLE, SlimefunItems.WITHER_ASSEMBLER);

        ResearchUtil.setResearchBySlimefunItems(FinalTechItemStacks.ADVANCED_AUTO_CRAFT_FRAME, SlimefunItems.ENHANCED_AUTO_CRAFTER);

        ResearchUtil.setResearchBySlimefunItems(FinalTechItemStacks.ITEM_FIXER, SlimefunItems.IRON_GOLEM_ASSEMBLER);

        ResearchUtil.setResearchBySlimefunItems(FinalTechItemStacks.COBBLESTONE_FACTORY, SlimefunItems.PROGRAMMABLE_ANDROID);

        ResearchUtil.setResearchBySlimefunItems(FinalTechItemStacks.FUEL_CHARGER, SlimefunItems.PROGRAMMABLE_ANDROID_3);
        ResearchUtil.setResearchBySlimefunItems(FinalTechItemStacks.FUEL_OPERATOR, SlimefunItems.PROGRAMMABLE_ANDROID_3);
        ResearchUtil.setResearchBySlimefunItems(FinalTechItemStacks.FUEL_ACCELERATOR, SlimefunItems.PROGRAMMABLE_ANDROID_3);
        ResearchUtil.setResearchBySlimefunItems(FinalTechItemStacks.OPERATION_ACCELERATOR, SlimefunItems.PROGRAMMABLE_ANDROID_3);
        ResearchUtil.setResearchBySlimefunItems(FinalTechItemStacks.OPERATION_ACCELERATOR_INFINITY, SlimefunItems.PROGRAMMABLE_ANDROID_3);

        ResearchUtil.setResearchBySlimefunItems(FinalTechItemStacks.CURE_TOWER, SlimefunItems.GPS_TRANSMITTER_4);
        ResearchUtil.setResearchBySlimefunItems(FinalTechItemStacks.PURIFY_LEVEL_TOWER, SlimefunItems.GPS_TRANSMITTER_3);
        ResearchUtil.setResearchBySlimefunItems(FinalTechItemStacks.PURIFY_TIME_TOWER, SlimefunItems.GPS_TRANSMITTER_3);

        ResearchUtil.setResearchBySlimefunItems(FinalTechItemStacks.MANUAL_GRIND_STONE, SlimefunItems.GRIND_STONE);
        ResearchUtil.setResearchBySlimefunItems(FinalTechItemStacks.MANUAL_ARMOR_FORGE, SlimefunItems.ARMOR_FORGE);
        ResearchUtil.setResearchBySlimefunItems(FinalTechItemStacks.MANUAL_ORE_CRUSHER, SlimefunItems.ORE_CRUSHER);
        ResearchUtil.setResearchBySlimefunItems(FinalTechItemStacks.MANUAL_COMPRESSOR, SlimefunItems.COMPRESSOR);
        ResearchUtil.setResearchBySlimefunItems(FinalTechItemStacks.MANUAL_SMELTERY, SlimefunItems.SMELTERY);
        ResearchUtil.setResearchBySlimefunItems(FinalTechItemStacks.MANUAL_PRESSURE_CHAMBER, SlimefunItems.PRESSURE_CHAMBER);
        ResearchUtil.setResearchBySlimefunItems(FinalTechItemStacks.MANUAL_MAGIC_WORKBENCH, SlimefunItems.MAGIC_WORKBENCH);
        ResearchUtil.setResearchBySlimefunItems(FinalTechItemStacks.MANUAL_ORE_WASHER, SlimefunItems.ORE_WASHER);
        ResearchUtil.setResearchBySlimefunItems(FinalTechItemStacks.MANUAL_COMPOSTER, SlimefunItems.COMPOSTER);
        ResearchUtil.setResearchBySlimefunItems(FinalTechItemStacks.MANUAL_GOLD_PAN, SlimefunItems.GOLD_PAN);
        ResearchUtil.setResearchBySlimefunItems(FinalTechItemStacks.MANUAL_CRUCIBLE, SlimefunItems.CRUCIBLE);
        ResearchUtil.setResearchBySlimefunItems(FinalTechItemStacks.MANUAL_JUICER, SlimefunItems.JUICER);
        ResearchUtil.setResearchBySlimefunItems(FinalTechItemStacks.MANUAL_ANCIENT_ALTAR, SlimefunItems.ANCIENT_ALTAR);
        ResearchUtil.setResearchBySlimefunItems(FinalTechItemStacks.MANUAL_HEATED_PRESSURE_CHAMBER, SlimefunItems.HEATED_PRESSURE_CHAMBER);

        ResearchUtil.setResearchBySlimefunItems(FinalTechItemStacks.ADVANCED_COMPOSTER, SlimefunItems.FOOD_COMPOSTER_2);
        ResearchUtil.setResearchBySlimefunItems(FinalTechItemStacks.ADVANCED_JUICER, SlimefunItems.JUICER);
        ResearchUtil.setResearchBySlimefunItems(FinalTechItemStacks.ADVANCED_FURNACE, SlimefunItems.ELECTRIC_FURNACE_3);
        ResearchUtil.setResearchBySlimefunItems(FinalTechItemStacks.ADVANCED_GOLD_PAN, SlimefunItems.ELECTRIC_GOLD_PAN_3);
        ResearchUtil.setResearchBySlimefunItems(FinalTechItemStacks.ADVANCED_DUST_WASHER, SlimefunItems.ELECTRIC_DUST_WASHER_3);
        ResearchUtil.setResearchBySlimefunItems(FinalTechItemStacks.ADVANCED_INGOT_FACTORY, SlimefunItems.ELECTRIC_INGOT_FACTORY_3);
        ResearchUtil.setResearchBySlimefunItems(FinalTechItemStacks.ADVANCED_CRUCIBLE, SlimefunItems.ELECTRIFIED_CRUCIBLE_3);
        ResearchUtil.setResearchBySlimefunItems(FinalTechItemStacks.ADVANCED_ORE_GRINDER, SlimefunItems.ELECTRIC_ORE_GRINDER_3);
        ResearchUtil.setResearchBySlimefunItems(FinalTechItemStacks.ADVANCED_HEATED_PRESSURE_CHAMBER, SlimefunItems.HEATED_PRESSURE_CHAMBER_2);
        ResearchUtil.setResearchBySlimefunItems(FinalTechItemStacks.ADVANCED_INGOT_PULVERIZER, SlimefunItems.ELECTRIC_INGOT_PULVERIZER);
        ResearchUtil.setResearchBySlimefunItems(FinalTechItemStacks.ADVANCED_AUTO_DRIER, SlimefunItems.AUTO_DRIER);
        ResearchUtil.setResearchBySlimefunItems(FinalTechItemStacks.ADVANCED_PRESS, SlimefunItems.ELECTRIC_PRESS_2);
        ResearchUtil.setResearchBySlimefunItems(FinalTechItemStacks.ADVANCED_FOOD_FACTORY, SlimefunItems.FOOD_FABRICATOR_2);
        ResearchUtil.setResearchBySlimefunItems(FinalTechItemStacks.ADVANCED_FREEZER, SlimefunItems.FREEZER_2);
        ResearchUtil.setResearchBySlimefunItems(FinalTechItemStacks.ADVANCED_CARBON_PRESS, SlimefunItems.CARBON_PRESS_3);
        ResearchUtil.setResearchBySlimefunItems(FinalTechItemStacks.ADVANCED_SMELTERY, SlimefunItems.ELECTRIC_SMELTERY_2);
        ResearchUtil.setResearchBySlimefunItems(FinalTechItemStacks.ADVANCED_COMPOSTER, SlimefunItems.FOOD_COMPOSTER_2);

        ResearchUtil.setResearchBySlimefunItems(FinalTechItemStacks.GRAVEL_CONVERSION, SlimefunItems.GOLD_PAN);
        ResearchUtil.setResearchBySlimefunItems(FinalTechItemStacks.SOUL_SAND_CONVERSION, SlimefunItems.NETHER_GOLD_PAN);
    }

    private static void setupCommand() {
        FinalTech finalTech = FinalTech.getInstance();

        finalTech.getCommand("finaltech-copy-card").setExecutor(new TransferToCopyCardItem());
        finalTech.getCommand("finaltech-storage-card").setExecutor(new TransferToStorageItem());
        finalTech.getCommand("finaltech-info").setExecutor(new ShowItemInfo());
    }

    public static void init() {
        ConfigFileManager configManager = FinalTech.getConfigManager();

        if(configManager.getOrDefault(true, "enable", "item")) {
            // Yeah, you may not want new items from this plugin.
            setupEnchantment();
            setupItem();
            setupMenu();
            setupResearch();
        }

        setupCommand();
    }

    public static void registerBlockTicker() {
        SetupUtil.registerBlockTicker(0);
    }

    private static void registerBlockTicker(int begin) {
        try {
            ConfigFileManager configManager = FinalTech.getConfigManager();
            List<SlimefunItem> slimefunItemList = Slimefun.getRegistry().getAllSlimefunItems();
            for(int size = slimefunItemList.size(); begin < size; begin++) {
                SlimefunItem slimefunItem = slimefunItemList.get(begin);
                SlimefunAddon slimefunAddon = slimefunItem.getAddon();
                if (slimefunItem.getBlockTicker() != null) {
                    BlockTicker blockTicker = slimefunItem.getBlockTicker();

                    if(FinalTech.debugMode()) {
                        blockTicker = BlockTickerUtil.getDebugModeBlockTicker(blockTicker, slimefunItem);
                    }

                    if(configManager.containPath("tweak", "interval", "general", slimefunItem.getId())) {
                        int interval = configManager.getOrDefault(-1, "tweak", "interval", "general", slimefunItem.getId());
                        if(interval > 0) {
                            blockTicker = BlockTickerUtil.getGeneralIntervalBlockTicker(blockTicker, interval);
                            FinalTech.logger().info(slimefunItem.getId() + " is tweaked for general interval limit");
                        } else {
                            FinalTech.logger().warning("wrong value of tweak.interval.general." + slimefunItem.getId() + " in config file");
                        }
                    }
                    if(configManager.containPath("tweak", "interval", "independent", slimefunItem.getId())) {
                        int interval = configManager.getOrDefault(-1, "tweak", "interval", "independent", slimefunItem.getId());
                        if(interval > 1) {
                            blockTicker = BlockTickerUtil.getIndependentIntervalBlockTicker(blockTicker, interval);
                            FinalTech.logger().info(slimefunItem.getId() + " is tweaked for independent interval limit");
                        } else {
                            FinalTech.logger().warning("wrong value of tweak.interval.independent." + slimefunItem.getId() + " in config file");
                        }
                    }

                    if(configManager.containPath("tweak", "range-limit", slimefunItem.getId(), "range")) {
                        int range = configManager.getOrDefault(-1, "tweak", "range-limit", slimefunItem.getId(), "range");
                        if(range > 0) {
                            int mulRange = configManager.getOrDefault(0, "tweak", "range-limit", slimefunItem.getId(), "mul-range");
                            boolean dropSelf = configManager.getOrDefault(false, "tweak", "range-limit", slimefunItem.getId(), "drop-self");
                            String message = configManager.getOrDefault("{1} is not allowed to be placed too closely", "tweak", "range-limit", slimefunItem.getId(), "message");
                            blockTicker = BlockTickerUtil.getRangeLimitBlockTicker(blockTicker, range, mulRange, dropSelf, message);
                            FinalTech.logger().info(slimefunItem.getId() + " is tweaked for range limit");

                            if(dropSelf) {
                                FinalTech.logger().warning("Please be carefully if you installed slimefun addon '基岩科技'(BedrockTechnology) and you set drop-self as true.");
                                FinalTech.logger().warning("There is a duplication bug, and we may fix it in next version");
                            }
                        } else {
                            FinalTech.logger().warning("wrong value of tweak.range." + slimefunItem.getId() + " in config file");
                        }
                    }

                    boolean forceAsync = !blockTicker.isSynchronized() && (FinalTech.getForceSlimefunMultiThread() || FinalTech.isAsyncSlimefunItem(slimefunItem.getId()) || FinalTech.getAsyncSlimefunPluginSet().contains(slimefunAddon.getName()));
                    boolean antiAccelerate = FinalTech.isAntiAccelerateSlimefunItem(slimefunItem.getId()) || FinalTech.getAntiAccelerateSlimefunPluginSet().contains(slimefunAddon.getName());
                    boolean performanceLimit = FinalTech.isPerformanceLimitSlimefunItem(slimefunItem.getId()) || FinalTech.getPerformanceLimitSlimefunPluginSet().contains(slimefunAddon.getName());
                    if(forceAsync || antiAccelerate || performanceLimit) {
                        blockTicker = BlockTickerUtil.generateBlockTicker(blockTicker, forceAsync, antiAccelerate, performanceLimit);
                        if(antiAccelerate) {
                            FinalTech.addAntiAccelerateSlimefunItem(slimefunItem.getId());
                            FinalTech.logger().info(slimefunItem.getId() + " is tweaked for anti accelerate");
                        }
                        if(performanceLimit) {
                            FinalTech.addPerformanceLimitSlimefunItem(slimefunItem.getId());
                            FinalTech.logger().info(slimefunItem.getId() + " is tweaked for performance limit");
                        }
                    }

                    if (configManager.getOrDefault(false, "super-ban") && slimefunItem.isDisabled()) {
                        blockTicker = null;
                        FinalTech.logger().info(slimefunItem.getId() + " is tweaked to remove block ticker");
                    } else if(FinalTech.isNoBlockTickerSlimefunItem(slimefunItem.getId())) {
                        blockTicker = null;
                        FinalTech.logger().info(slimefunItem.getId() + " is tweaked to remove block ticker");
                    } else if(FinalTech.getNoBlockTickerSlimefunPluginSet().contains(slimefunAddon.getJavaPlugin().getName())){
                        blockTicker = null;
                        FinalTech.logger().info(slimefunItem.getId() + " is tweaked to remove block ticker");
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
