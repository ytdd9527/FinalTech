package io.taraxacum.finaltech.setup;

import io.github.thebusybiscuit.slimefun4.api.SlimefunAddon;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.implementation.Slimefun;
import io.github.thebusybiscuit.slimefun4.implementation.SlimefunItems;
import io.taraxacum.common.util.ReflectionUtil;
import io.taraxacum.common.util.StringUtil;
import io.taraxacum.finaltech.FinalTech;
import io.taraxacum.finaltech.core.command.ShowItemInfo;
import io.taraxacum.finaltech.core.command.TransformToCopyCardItem;
import io.taraxacum.finaltech.core.command.TransformToStorageItem;
import io.taraxacum.finaltech.core.command.TransformToValidItem;
import io.taraxacum.finaltech.core.enchantment.NullEnchantment;
import io.taraxacum.finaltech.core.item.machine.AbstractMachine;
import io.taraxacum.finaltech.util.ConstantTableUtil;
import io.taraxacum.libs.plugin.util.ItemStackUtil;
import io.taraxacum.finaltech.util.BlockTickerUtil;
import io.taraxacum.libs.slimefun.dto.LocationInfo;
import io.taraxacum.libs.slimefun.util.ResearchUtil;
import io.taraxacum.libs.plugin.util.TextUtil;
import io.taraxacum.libs.plugin.dto.ConfigFileManager;
import io.taraxacum.libs.plugin.dto.LanguageManager;
import me.mrCookieSlime.Slimefun.Objects.handlers.BlockTicker;
import me.mrCookieSlime.Slimefun.api.BlockStorage;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.enchantments.Enchantment;

import javax.annotation.Nonnull;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
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
            NullEnchantment.addAndHidden(FinalTechItemStacks.QUANTITY_MODULE_MATRIX);
            NullEnchantment.addAndHidden(FinalTechItemStacks.MATRIX_OPERATION_ACCELERATOR);
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

        /* items */
        // material
        FinalTechMenus.SUB_MENU_MATERIAL.addTo(
                FinalTechItems.WATER_CARD.register(),
                FinalTechItems.LAVA_CARD.register(),
                FinalTechItems.MILK_CARD.register());
        FinalTechMenus.SUB_MENU_MATERIAL.addTo(
                FinalTechItems.GEARWHEEL.register(),
                FinalTechItems.UNORDERED_DUST.register(),
                FinalTechItems.ORDERED_DUST.register(),
                FinalTechItems.BUG.register(),
                FinalTechItems.ENTROPY.register(),
                FinalTechItems.BOX.register(),
                FinalTechItems.SHINE.register(),
                FinalTechItems.COPY_CARD.register());
        FinalTechMenus.SUB_MENU_MATERIAL.addTo(
                FinalTechItems.ANNULAR.register(),
                FinalTechItems.QUANTITY_MODULE.register(),
                FinalTechItems.QUANTITY_MODULE_ENERGIZED.register(),
                FinalTechItems.QUANTITY_MODULE_OVERLOADED.register(),
                FinalTechItems.SINGULARITY.register(),
                FinalTechItems.SPIROCHETE.register(),
                FinalTechItems.SHELL.register(),
                FinalTechItems.ITEM_PHONY.register(),
                FinalTechItems.JUSTIFIABILITY.register(),
                FinalTechItems.EQUIVALENT_CONCEPT.register());
        // logic item
        FinalTechMenus.SUB_MENU_LOGIC_ITEM.addTo(
                FinalTechItems.LOGIC_FALSE.register(),
                FinalTechItems.LOGIC_TRUE.register(),
                FinalTechItems.DIGITAL_ZERO.register(),
                FinalTechItems.DIGITAL_ONE.register(),
                FinalTechItems.DIGITAL_TWO.register(),
                FinalTechItems.DIGITAL_THREE.register(),
                FinalTechItems.DIGITAL_FOUR.register(),
                FinalTechItems.DIGITAL_FIVE.register(),
                FinalTechItems.DIGITAL_SIX.register(),
                FinalTechItems.DIGITAL_SEVEN.register(),
                FinalTechItems.DIGITAL_EIGHT.register(),
                FinalTechItems.DIGITAL_NINE.register(),
                FinalTechItems.DIGITAL_TEN.register(),
                FinalTechItems.DIGITAL_ELEVEN.register(),
                FinalTechItems.DIGITAL_TWELVE.register(),
                FinalTechItems.DIGITAL_THIRTEEN.register(),
                FinalTechItems.DIGITAL_FOURTEEN.register(),
                FinalTechItems.DIGITAL_FIFTEEN.register());
        // consumable
        FinalTechMenus.SUB_MENU_CONSUMABLE.addTo(
                FinalTechItems.MACHINE_CHARGE_CARD_L1.register(),
                FinalTechItems.MACHINE_CHARGE_CARD_L2.register(),
                FinalTechItems.MACHINE_CHARGE_CARD_L3.register(),
                FinalTechItems.MACHINE_ACCELERATE_CARD_L1.register(),
                FinalTechItems.MACHINE_ACCELERATE_CARD_L2.register(),
                FinalTechItems.MACHINE_ACCELERATE_CARD_L3.register(),
                FinalTechItems.MACHINE_ACTIVATE_CARD_L1.register(),
                FinalTechItems.MACHINE_ACTIVATE_CARD_L2.register(),
                FinalTechItems.MACHINE_ACTIVATE_CARD_L3.register(),
                FinalTechItems.ENERGY_CARD_K.register(),
                FinalTechItems.ENERGY_CARD_M.register(),
                FinalTechItems.ENERGY_CARD_B.register(),
                FinalTechItems.ENERGY_CARD_T.register(),
                FinalTechItems.ENERGY_CARD_Q.register());
        FinalTechMenus.SUB_MENU_CONSUMABLE.addTo(
                FinalTechItems.MAGIC_HYPNOTIC.register(),
                FinalTechItems.RESEARCH_UNLOCK_TICKET.register());
        // tool
        FinalTechMenus.SUB_MENU_TOOL.addTo(
                FinalTechItems.STAFF_ELEMENTAL_LINE.register(),
                FinalTechItems.POTION_EFFECT_COMPRESSOR.register(),
                FinalTechItems.POTION_EFFECT_DILATOR.register(),
                FinalTechItems.POTION_EFFECT_PURIFIER.register(),
                FinalTechItems.GRAVITY_REVERSAL_RUNE.register());
        FinalTechMenus.SUB_MENU_TOOL.addTo(
                FinalTechItems.MENU_VIEWER.register(),
                FinalTechItems.ROUTE_VIEWER.register(),
                FinalTechItems.LOCATION_RECORDER.register(),
                FinalTechItems.MACHINE_CONFIGURATOR.register(),
                FinalTechItems.PORTABLE_ENERGY_STORAGE.register());
        // weapon
        FinalTechMenus.SUB_MENU_WEAPON.addTo(
                FinalTechItems.SUPER_SHOVEL.register(),
                FinalTechItems.ULTIMATE_SHOVEL.register(),
                FinalTechItems.SUPER_PICKAXE.register(),
                FinalTechItems.ULTIMATE_PICKAXE.register(),
                FinalTechItems.SUPER_AXE.register(),
                FinalTechItems.ULTIMATE_AXE.register(),
                FinalTechItems.SUPER_HOE.register(),
                FinalTechItems.ULTIMATE_HOE.register()
        );

        /* electricity system */
        // electric generator
        FinalTechMenus.SUB_MENU_ELECTRIC_GENERATOR.addTo(
                FinalTechItems.BASIC_GENERATOR.register(),
                FinalTechItems.ADVANCED_GENERATOR.register(),
                FinalTechItems.CARBONADO_GENERATOR.register(),
                FinalTechItems.ENERGIZED_GENERATOR.register(),
                FinalTechItems.ENERGIZED_STACK_GENERATOR.register(),
                FinalTechItems.OVERLOADED_GENERATOR.register());
        FinalTechMenus.SUB_MENU_ELECTRIC_GENERATOR.addTo(
                FinalTechItems.DUST_GENERATOR.register(),
                FinalTechItems.TIME_GENERATOR.register(),
                FinalTechItems.ENERGIZED_CHARGE_BASE.register(),
                FinalTechItems.OVERLOADED_CHARGE_BASE.register());
        // electric storage
        FinalTechMenus.SUB_MENU_ELECTRIC_STORAGE.addTo(
                FinalTechItems.SMALL_EXPANDED_CAPACITOR.register(),
                FinalTechItems.MEDIUM_EXPANDED_CAPACITOR.register(),
                FinalTechItems.BIG_EXPANDED_CAPACITOR.register(),
                FinalTechItems.LARGE_EXPANDED_CAPACITOR.register(),
                FinalTechItems.CARBONADO_EXPANDED_CAPACITOR.register(),
                FinalTechItems.ENERGIZED_EXPANDED_CAPACITOR.register());
        FinalTechMenus.SUB_MENU_ELECTRIC_STORAGE.addTo(
                FinalTechItems.ENERGIZED_STACK_EXPANDED_CAPACITOR.register(),
                FinalTechItems.OVERLOADED_EXPANDED_CAPACITOR.register(),
                FinalTechItems.TIME_CAPACITOR.register());
        // electric transmission
        FinalTechMenus.SUB_MENU_ELECTRIC_TRANSMISSION.addTo(
                FinalTechItems.NORMAL_ELECTRICITY_SHOOT_PILE.register(),
                FinalTechItems.NORMAL_CONSUMABLE_ELECTRICITY_SHOOT_PILE.register(),
                FinalTechItems.NORMAL_CONFIGURABLE_ELECTRICITY_SHOOT_PILE.register(),
                FinalTechItems.ENERGIZED_ELECTRICITY_SHOOT_PILE.register(),
                FinalTechItems.OVERLOADED_ELECTRICITY_SHOOT_PILE.register(),
//                FinalTechItems.DISPERSAL_CAPACITOR.register(),
                FinalTechItems.VARIABLE_WIRE_RESISTANCE.register(),
                FinalTechItems.VARIABLE_WIRE_CAPACITOR.register());
        // electric accelerator
        FinalTechMenus.SUB_MENU_ELECTRIC_ACCELERATOR.addTo(
                FinalTechItems.ENERGIZED_ACCELERATOR.register(),
                FinalTechItems.OVERLOADED_ACCELERATOR.register());

        /* cargo system */
        // storage unit
        FinalTechMenus.SUB_MENU_STORAGE_UNIT.addTo(
                FinalTechItems.NORMAL_STORAGE_UNIT.register(),
                FinalTechItems.DIVIDED_STORAGE_UNIT.register(),
                FinalTechItems.LIMITED_STORAGE_UNIT.register(),
                FinalTechItems.STACK_STORAGE_UNIT.register(),
                FinalTechItems.DIVIDED_LIMITED_STORAGE_UNIT.register(),
                FinalTechItems.DIVIDED_STACK_STORAGE_UNIT.register(),
                FinalTechItems.LIMITED_STACK_STORAGE_UNIT.register());
        FinalTechMenus.SUB_MENU_STORAGE_UNIT.addTo(
                FinalTechItems.RANDOM_INPUT_STORAGE_UNIT.register(),
                FinalTechItems.RANDOM_OUTPUT_STORAGE_UNIT.register(),
                FinalTechItems.RANDOM_ACCESS_STORAGE_UNIT.register(),
                FinalTechItems.DISTRIBUTE_LEFT_STORAGE_UNIT.register(),
                FinalTechItems.DISTRIBUTE_RIGHT_STORAGE_UNIT.register());
        // advanced storage
        FinalTechMenus.SUB_MENU_ADVANCED_STORAGE.addTo(
                FinalTechItems.STORAGE_INTERACT_PORT.register(),
                FinalTechItems.STORAGE_INSERT_PORT.register(),
                FinalTechItems.STORAGE_WITHDRAW_PORT.register(),
                FinalTechItems.STORAGE_CARD.register());
        // accessor
        FinalTechMenus.SUB_MENU_ACCESSOR.addTo(
                FinalTechItems.REMOTE_ACCESSOR.register(),
                FinalTechItems.CONSUMABLE_REMOTE_ACCESSOR.register(),
                FinalTechItems.CONFIGURABLE_REMOTE_ACCESSOR.register(),
                FinalTechItems.EXPANDED_CONSUMABLE_REMOTE_ACCESSOR.register(),
                FinalTechItems.EXPANDED_CONFIGURABLE_REMOTE_ACCESSOR.register(),
                FinalTechItems.RANDOM_ACCESSOR.register(),
                FinalTechItems.AREA_ACCESSOR.register());
        FinalTechMenus.SUB_MENU_ACCESSOR.addTo(
                FinalTechItems.TRANSPORTER.register(),
                FinalTechItems.CONSUMABLE_TRANSPORTER.register(),
                FinalTechItems.CONFIGURABLE_TRANSPORTER.register(),
                FinalTechItems.EXPANDED_CONSUMABLE_TRANSPORTER.register(),
                FinalTechItems.EXPANDED_CONFIGURABLE_TRANSPORTER.register());
        // logic
        FinalTechMenus.SUB_MENU_LOGIC.addTo(
                FinalTechItems.LOGIC_COMPARATOR_NOTNULL.register(),
                FinalTechItems.LOGIC_COMPARATOR_AMOUNT.register(),
                FinalTechItems.LOGIC_COMPARATOR_SIMILAR.register(),
                FinalTechItems.LOGIC_COMPARATOR_EQUAL.register(),
                FinalTechItems.LOGIC_CRAFTER.register(),
                FinalTechItems.DIGIT_ADDER.register());
        // cargo
        FinalTechMenus.SUB_MENU_CARGO.addTo(
                FinalTechItems.BASIC_FRAME_MACHINE.register(),
                FinalTechItems.POINT_TRANSFER.register(),
                FinalTechItems.MESH_TRANSFER.register(),
                FinalTechItems.LINE_TRANSFER.register(),
                FinalTechItems.LOCATION_TRANSFER.register());
        FinalTechMenus.SUB_MENU_CARGO.addTo(
                FinalTechItems.ADVANCED_POINT_TRANSFER.register(),
                FinalTechItems.ADVANCED_MESH_TRANSFER.register(),
                FinalTechItems.ADVANCED_LINE_TRANSFER.register(),
                FinalTechItems.ADVANCED_LOCATION_TRANSFER.register());
        FinalTechMenus.SUB_MENU_CARGO.addTo(
                FinalTechItems.CONFIGURATION_COPIER.register(),
                FinalTechItems.CONFIGURATION_PASTER.register(),
                FinalTechItems.CLICK_WORK_MACHINE.register(),
                FinalTechItems.SIMULATE_CLICK_MACHINE.register(),
                FinalTechItems.CONSUMABLE_SIMULATE_CLICK_MACHINE.register());

        /* functional machines */
        // core machines
        FinalTechMenus.SUB_MENU_CORE_MACHINE.addTo(
                FinalTechItems.BEDROCK_CRAFT_TABLE.register(),
                FinalTechItems.DUST_FACTORY_DIRT.register(),
                FinalTechItems.DUST_FACTORY_STONE.register(),
                FinalTechItems.MATRIX_CRAFTING_TABLE.register());
        FinalTechMenus.SUB_MENU_CORE_MACHINE.addTo(
                FinalTechItems.ITEM_DISMANTLE_TABLE.register(),
                FinalTechItems.AUTO_ITEM_DISMANTLE_TABLE.register(),
                FinalTechItems.EQUIVALENT_EXCHANGE_TABLE.register(),
                FinalTechItems.ITEM_SERIALIZATION_CONSTRUCTOR.register(),
                FinalTechItems.ITEM_DESERIALIZE_PARSER.register(),
                FinalTechItems.CARD_OPERATION_TABLE.register(),
                FinalTechItems.ADVANCED_AUTO_CRAFT_FRAME.register(),
                FinalTechItems.ENERGY_TABLE.register(),
                FinalTechItems.ENERGY_INPUT_TABLE.register(),
                FinalTechItems.ENERGY_OUTPUT_TABLE.register());
        // special machines
        FinalTechMenus.SUB_MENU_SPECIAL_MACHINE.addTo(
                FinalTechItems.ITEM_FIXER.register(),
                FinalTechItems.COBBLESTONE_FACTORY.register(),
                FinalTechItems.FUEL_CHARGER.register(),
                FinalTechItems.FUEL_ACCELERATOR.register(),
                FinalTechItems.FUEL_OPERATOR.register(),
                FinalTechItems.OPERATION_ACCELERATOR.register(),
                FinalTechItems.ENERGIZED_OPERATION_ACCELERATOR.register(),
                FinalTechItems.OVERLOADED_OPERATION_ACCELERATOR.register());
        // tower
        FinalTechMenus.SUB_MENU_TOWER.addTo(
                FinalTechItems.CURE_TOWER.register(),
                FinalTechItems.PURIFY_LEVEL_TOWER.register(),
                FinalTechItems.PURIFY_TIME_TOWER.register());

        /* productive machine */
        // manual machine
        FinalTechMenus.SUB_MENU_MANUAL_MACHINE.addTo(
                FinalTechItems.MANUAL_CRAFT_MACHINE,
                FinalTechItems.MANUAL_CRAFTING_TABLE.register(),
                FinalTechItems.MANUAL_ENHANCED_CRAFTING_TABLE.register(),
                FinalTechItems.MANUAL_GRIND_STONE.register(),
                FinalTechItems.MANUAL_ARMOR_FORGE.register(),
                FinalTechItems.MANUAL_ORE_CRUSHER.register(),
                FinalTechItems.MANUAL_COMPRESSOR.register(),
                FinalTechItems.MANUAL_SMELTERY.register(),
                FinalTechItems.MANUAL_PRESSURE_CHAMBER.register(),
                FinalTechItems.MANUAL_MAGIC_WORKBENCH.register(),
                FinalTechItems.MANUAL_ORE_WASHER.register(),
                FinalTechItems.MANUAL_COMPOSTER.register(),
                FinalTechItems.MANUAL_GOLD_PAN.register(),
                FinalTechItems.MANUAL_CRUCIBLE.register(),
                FinalTechItems.MANUAL_JUICER.register(),
                FinalTechItems.MANUAL_ANCIENT_ALTAR.register(),
                FinalTechItems.MANUAL_HEATED_PRESSURE_CHAMBER.register());
        // basic machines
        FinalTechMenus.SUB_MENU_BASIC_MACHINE.addTo(
                FinalTechItems.BASIC_LOGIC_FACTORY.register());
        // advanced machine
        FinalTechMenus.SUB_MENU_ADVANCED_MACHINE.addTo(
                FinalTechItems.ADVANCED_COMPOSTER.register(),
                FinalTechItems.ADVANCED_JUICER.register(),
                FinalTechItems.ADVANCED_FURNACE.register(),
                FinalTechItems.ADVANCED_GOLD_PAN.register(),
                FinalTechItems.ADVANCED_DUST_WASHER.register(),
                FinalTechItems.ADVANCED_INGOT_FACTORY.register(),
                FinalTechItems.ADVANCED_CRUCIBLE.register(),
                FinalTechItems.ADVANCED_ORE_GRINDER.register(),
                FinalTechItems.ADVANCED_HEATED_PRESSURE_CHAMBER.register(),
                FinalTechItems.ADVANCED_INGOT_PULVERIZER.register(),
                FinalTechItems.ADVANCED_AUTO_DRIER.register(),
                FinalTechItems.ADVANCED_PRESS.register(),
                FinalTechItems.ADVANCED_FOOD_FACTORY.register(),
                FinalTechItems.ADVANCED_FREEZER.register(),
                FinalTechItems.ADVANCED_CARBON_PRESS.register(),
                FinalTechItems.ADVANCED_SMELTERY.register());
        // conversion
        FinalTechMenus.SUB_MENU_CONVERSION.addTo(
                FinalTechItems.GRAVEL_CONVERSION.register(),
                FinalTechItems.SOUL_SAND_CONVERSION.register(),
                FinalTechItems.LOGIC_TO_DIGITAL_CONVERSION.register());
        // extraction
        FinalTechMenus.SUB_MENU_EXTRACTION.addTo(
                FinalTechItems.DIGITAL_EXTRACTION.register());
        // generator
        FinalTechMenus.SUB_MENU_GENERATOR.addTo(
                FinalTechItems.LIQUID_CARD_GENERATOR.register(),
                FinalTechItems.LOGIC_GENERATOR.register(),
                FinalTechItems.DIGITAL_GENERATOR.register());

        /* final stage item */
        FinalTechMenus.SUB_MENU_FINAL_ITEM.addTo(
                FinalTechItems.ENTROPY_SEED.register(),
                FinalTechItems.MACHINE_CHARGE_CARD_INFINITY.register(),
                FinalTechItems.MACHINE_ACCELERATE_CARD_INFINITY.register(),
                FinalTechItems.MACHINE_ACTIVATE_CARD_L4.register(),
                FinalTechItems.QUANTITY_MODULE_MATRIX.register(),
                FinalTechItems.OPERATION_ACCELERATOR_MATRIX.register());
        FinalTechMenus.SUB_MENU_FINAL_ITEM.addTo(
                FinalTechItems.ADVANCED_AUTO_CRAFT.register(),
                FinalTechItems.MULTI_FRAME_MACHINE.register(),
                FinalTechItems.MATRIX_ITEM_DISMANTLE_TABLE.register(),
                FinalTechItems.MATRIX_EXPANDED_CAPACITOR.register(),
                FinalTechItems.MATRIX_ITEM_DESERIALIZE_PARSER.register(),
                FinalTechItems.ENTROPY_CONSTRUCTOR.register(),
                FinalTechItems.MATRIX_GENERATOR.register(),
                FinalTechItems.MATRIX_ACCELERATOR.register(),
                FinalTechItems.MATRIX_ITEM_SERIALIZATION_CONSTRUCTOR.register(),
                FinalTechItems.MATRIX_REACTOR.register());
        FinalTechMenus.SUB_MENU_TROPHY.addTo(
                FinalTechItems.TROPHY_MEAWERFUL,
                FinalTechItems.TROPHY_SHIXINZIA);
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
                FinalTechItemStacks.QUANTITY_MODULE_MATRIX,
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
        ResearchUtil.setResearchBySlimefunItems(FinalTechItemStacks.MATRIX_OPERATION_ACCELERATOR, SlimefunItems.PROGRAMMABLE_ANDROID_3);

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

        finalTech.getCommand("finaltech-copy-card").setExecutor(new TransformToCopyCardItem());
        finalTech.getCommand("finaltech-storage-card").setExecutor(new TransformToStorageItem());
        finalTech.getCommand("finaltech-info").setExecutor(new ShowItemInfo());
        finalTech.getCommand("finaltech-valid-item").setExecutor(new TransformToValidItem());
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

    public static void dataLossFix() {
        for(World world : FinalTech.getInstance().getServer().getWorlds()) {
            BlockStorage storage = BlockStorage.getStorage(world);
            if(storage != null) {
                try {
                    // todo test it
                    Map<Location, BlockMenu> inventories = ReflectionUtil.getProperty(storage, Map.class, "inventories");
                    if(inventories != null) {
                        for(Map.Entry<Location, BlockMenu> entry : inventories.entrySet()) {
                            Location location = entry.getKey();
                            LocationInfo locationInfo = LocationInfo.get(location);
                            if(locationInfo == null) {
                                String id = entry.getValue().getPreset().getID();
                                FinalTech.logger().warning("Data Loss Fix: location " + location + " seems loss its data. There should be " + id);
                                SlimefunItem slimefunItem = SlimefunItem.getById(id);
                                if(!(slimefunItem instanceof AbstractMachine)) {
                                    Map<String, String> configMap = FinalTech.getDataLossFixCustomMap(id);
                                    if(configMap == null) {
                                        FinalTech.logger().warning("Data Loss Fix: I don't know how to fix it. Config me in config.yml with path: " + "data-loss-fix-custom" + "." + "config" + "." + id);
                                        continue;
                                    }

                                    BlockStorage.addBlockInfo(location, ConstantTableUtil.CONFIG_ID, id);
                                    for(Map.Entry<String, String> configEntry : configMap.entrySet()) {
                                        BlockStorage.addBlockInfo(location, configEntry.getKey(), configEntry.getValue());
                                    }
                                }
                            }
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
