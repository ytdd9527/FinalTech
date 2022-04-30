package io.taraxacum.finaltech.setup.register;

import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.utils.HeadTexture;
import io.taraxacum.finaltech.item.unusable.StorageCardItem;
import io.taraxacum.finaltech.machine.range.area.EscapeCapacitor;
import io.taraxacum.finaltech.machine.range.area.generator.*;
import io.taraxacum.finaltech.machine.capacitor.expanded.*;
import io.taraxacum.finaltech.machine.capacitor.*;
import io.taraxacum.finaltech.machine.DustGenerator;
import io.taraxacum.finaltech.machine.range.ray.shooter.EnergizedElectricityShootPile;
import io.taraxacum.finaltech.machine.range.ray.shooter.NormalElectricityShootPile;
import io.taraxacum.finaltech.machine.range.ray.shooter.OverloadedElectricityShootPile;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

/**
 * @author Final_ROOT
 */
public final class FinalTechItems {
    // items
    public static final SlimefunItemStack WATER_CARD = new SlimefunItemStack("FINALTECH_WATER_CARD", Material.PAPER, "§7水卡");
    public static final SlimefunItemStack LAVA_CARD = new SlimefunItemStack("FINALTECH_LAVAL_CARD", Material.PAPER, "§7岩浆卡");
    public static final SlimefunItemStack MILK_CARD = new SlimefunItemStack("FINALTECH_MILK_CARD", Material.PAPER, "§7牛奶卡");
    public static final SlimefunItemStack GEARWHEEL = new SlimefunItemStack("FINALTECH_GEARWHEEL", Material.REDSTONE, "§7齿轮");
    public static final SlimefunItemStack UNORDERED_DUST = new SlimefunItemStack("FINALTECH_UNORDERED_DUST", Material.WHEAT_SEEDS, "§f无序尘埃");
    public static final SlimefunItemStack ORDERED_DUST = new SlimefunItemStack("FINALTECH_ORDERED_DUST", Material.SLIME_BALL, "§f有序尘埃");
    public static final SlimefunItemStack QUANTITY_MODULE = new SlimefunItemStack("FINALTECH_QUANTITY_MODULE", Material.AMETHYST_SHARD, "§3数量组件",
            "§8改变机器效率");
    public static final SlimefunItemStack QUANTITY_MODULE_INFINITY = new SlimefunItemStack("FINALTECH_QUANTITY_MODULE_INFINITY", Material.AMETHYST_SHARD, "§3数量组件-无限");
    public static final SlimefunItemStack COPY_CARD = new SlimefunItemStack("FINALTECH_COPY_CARD", Material.PAPER, "§4复制卡");
    public static final SlimefunItemStack ANNULAR = new SlimefunItemStack("FINALTECH_ANNULAR", Material.PAPER, "§7环");
    public static final SlimefunItemStack SINGULARITY = new SlimefunItemStack("FINALTECH_SINGULARITY", Material.NETHER_STAR, "§e奇点");
    public static final SlimefunItemStack SPIROCHETE = new SlimefunItemStack("FINALTECH_SPIROCHETE", Material.ARMOR_STAND, "§e螺旋体");
    public static final SlimefunItemStack SHELL = new SlimefunItemStack("FINALTECH_SHELL", Material.PRISMARINE_SHARD, "§e壳");
    public static final SlimefunItemStack FAKE = new SlimefunItemStack("FINALTECH_FAKE", Material.END_CRYSTAL, "§x§f§0§f§0§f§0伪物");

    // basic machines
    public static final SlimefunItemStack BASIC_COBBLE_FACTORY = new SlimefunItemStack("FINALTECH_BASIC_COBBLE_FACTORY", Material.CHISELED_STONE_BRICKS, "§7基础刷石工厂");
    public static final SlimefunItemStack BASIC_ORE_FACTORY = new SlimefunItemStack("FINALTECH_BASIC_ORE_FACTORY", Material.CHISELED_DEEPSLATE, "§7基础矿石工厂");
    public static final SlimefunItemStack BASIC_DUST_FACTORY = new SlimefunItemStack("FINALTECH_BASIC_DUST_FACTORY", Material.POLISHED_DEEPSLATE, "§7基础矿粉工厂");
    public static final SlimefunItemStack BASIC_FARM_FACTORY = new SlimefunItemStack("FINALTECH_BASIC_FARM_FACTORY", Material.MOSS_BLOCK, "§7基础作物工厂");
    public static final SlimefunItemStack MANUAL_CRAFTING_TABLE = new SlimefunItemStack("FINALTECH_MANUAL_CRAFTING_TABLE", Material.CRAFTING_TABLE, "§6快捷原版工作台");
    public static final SlimefunItemStack MANUAL_ENHANCED_CRAFTING_TABLE = new SlimefunItemStack("FINALTECH_MANUAL_ENHANCED_CRAFTING_TABLE", Material.CRAFTING_TABLE, "§6快捷增强型工作台");
    public static final SlimefunItemStack MANUAL_GRIND_STONE = new SlimefunItemStack("FINALTECH_MANUAL_GRIND_STONE", Material.DISPENSER, "§6快捷磨石");
    public static final SlimefunItemStack MANUAL_ARMOR_FORGE = new SlimefunItemStack("FINALTECH_MANUAL_ARMOR_FORGE", Material.IRON_BLOCK, "§6快捷盔甲锻造台");
    public static final SlimefunItemStack MANUAL_ORE_CRUSHER = new SlimefunItemStack("FINALTECH_MANUAL_ORE_CRUSHER", Material.DROPPER, "§6快捷矿石粉碎机");
    public static final SlimefunItemStack MANUAL_COMPRESSOR = new SlimefunItemStack("FINALTECH_MANUAL_COMPRESSOR", Material.PISTON, "§6快捷压缩机");
    public static final SlimefunItemStack MANUAL_SMELTERY = new SlimefunItemStack("FINALTECH_MANUAL_SMELTERY", Material.BLAST_FURNACE, "§6快捷冶炼炉");
    public static final SlimefunItemStack MANUAL_PRESSURE_CHAMBER = new SlimefunItemStack("FINALTECH_MANUAL_PRESSURE_CHAMBER", Material.STICKY_PISTON, "§6快捷压力机");
    public static final SlimefunItemStack MANUAL_MAGIC_WORKBENCH = new SlimefunItemStack("FINALTECH_MANUAL_MAGIC_WORKBENCH", Material.BOOKSHELF, "§6快捷魔法工作台");
    public static final SlimefunItemStack MANUAL_ORE_WASHER = new SlimefunItemStack("FINALTECH_MANUAL_ORE_WASHER", Material.CRAFTING_TABLE, "§6快捷洗矿机");
    public static final SlimefunItemStack MANUAL_COMPOSTER = new SlimefunItemStack("FINALTECH_MANUAL_COMPOSTER", Material.CRAFTING_TABLE, "§6快捷搅拌机");
    public static final SlimefunItemStack MANUAL_GOLD_PAN = new SlimefunItemStack("FINALTECH_MANUAL_GOLD_PAN", Material.CRAFTING_TABLE, "§6快捷淘金机");
    public static final SlimefunItemStack MANUAL_CRUCIBLE = new SlimefunItemStack("FINALTECH_CRUCIBLE", Material.CRAFTING_TABLE, "§6快捷坩埚");
    public static final SlimefunItemStack MANUAL_JUICER = new SlimefunItemStack("FINALTECH_MANUAL_JUICER", Material.GLASS, "§6快捷榨汁机");
    public static final SlimefunItemStack MANUAL_ANCIENT_ALTAR = new SlimefunItemStack("FINALTECH_MANUAL_ANCIENT_ALTAR", Material.ENCHANTING_TABLE, "§6快捷古代祭坛");
    public static final SlimefunItemStack MANUAL_HEATED_PRESSURE_CHAMBER = new SlimefunItemStack("FINALTECH_MANUAL_HEATED_PRESSURE_CHAMBER", Material.LIGHT_GRAY_STAINED_GLASS, "§6快捷加热压力舱");
    public static final SlimefunItemStack ORDERED_DUST_FACTORY_DIRT = new SlimefunItemStack("FINALTECH_ORDERED_DUST_FACTORY_DIRT", Material.DIRT, "§7尘埃制造机");
    public static final SlimefunItemStack ORDERED_DUST_FACTORY_STONE = new SlimefunItemStack("FINALTECH_ORDERED_DUST_FACTORY_STONE", Material.COBBLESTONE, "§7尘埃制造机");
    public static final SlimefunItemStack CARD_OPERATION_PORT = new SlimefunItemStack("FINALTECH_CARD_OPERATION_PORT", Material.CARTOGRAPHY_TABLE, "§f物品卡操作台");
    public static final SlimefunItemStack ITEM_DISMANTLE_TABLE = new SlimefunItemStack("FINALTECH_ITEM_DISMANTLE_TABLE", Material.CUT_COPPER, "§7物品拆解台");

    // advanced machines
    public static final SlimefunItemStack ADVANCED_COMPOSTER = new SlimefunItemStack("FINALTECH_COMPOSTER", Material.CRAFTING_TABLE, "§c高级搅拌机");
    public static final SlimefunItemStack ADVANCED_JUICER = new SlimefunItemStack("FINALTECH_JUICER", Material.CRAFTING_TABLE, "§c高级榨汁机");
    public static final SlimefunItemStack ADVANCED_ELECTRIC_FURNACE = new SlimefunItemStack("FINALTECH_ADVANCED_ELECTRIC_FURNACE", Material.FURNACE, "§c高级电炉");
    public static final SlimefunItemStack ADVANCED_GOLD_PAN = new SlimefunItemStack("FINALTECH_ADVANCED_GOLD_PAN", Material.BROWN_TERRACOTTA, "§c高级淘金机");
    public static final SlimefunItemStack ADVANCED_ELECTRIC_DUST_WASHER = new SlimefunItemStack("FINALTECH_ADVANCED_DUST_WASHER", Material.BLUE_STAINED_GLASS, "§c高级洗矿机");
    public static final SlimefunItemStack ADVANCED_ELECTRIC_INGOT_FACTORY = new SlimefunItemStack("FINALTECH_ADVANCED_ELECTRIC_INGOT_FACTORY", Material.RED_TERRACOTTA, "§c高级铸锭机");
    public static final SlimefunItemStack ADVANCED_ELECTRIC_CRUCIBLE = new SlimefunItemStack("FINALTECH_ADVANCED_ELECTRIC_CRUCIBLE", Material.RED_TERRACOTTA, "§c高级坩埚");
    public static final SlimefunItemStack ADVANCED_ELECTRIC_ORE_GRINDER = new SlimefunItemStack("FINALTECH_ADVANCED_ELECTRIC_ORE_GRINDER", Material.FURNACE, "§c高级碎矿机");
    public static final SlimefunItemStack ADVANCED_HEATED_PRESSURE_CHAMBER = new SlimefunItemStack("FINALTECH_ADVANCED_HEATED_PRESSURE_CHAMBER", Material.LIGHT_GRAY_STAINED_GLASS, "§c高级加热压力仓");
    public static final SlimefunItemStack ADVANCED_ELECTRIC_INGOT_PULVERIZER = new SlimefunItemStack("FINALTECH_ADVANCED_ELECTRIC_INGOT_PULVERIZER", Material.FURNACE, "§c高级打粉机");
    public static final SlimefunItemStack ADVANCED_AUTO_DRIER = new SlimefunItemStack("FINALTECH_ADVANCED_AUTO_DRIER", Material.SMOKER, "§c高级烘干机");
    public static final SlimefunItemStack ADVANCED_ELECTRIC_PRESS = new SlimefunItemStack("FINALTECH_ADVANCED_ELECTRIC_PRESS", new SlimefunItemStack("", HeadTexture.ELECTRIC_PRESS, "", ""), "§c高级压缩机");
    public static final SlimefunItemStack ADVANCED_FOOD_FACTORY = new SlimefunItemStack("FINALTECH_ADVANCED_FOOD_FACTORY", Material.GREEN_TERRACOTTA, "§c高级作物加工厂");
    public static final SlimefunItemStack ADVANCED_FREEZER = new SlimefunItemStack("FINALTECH_ADVANCED_FREEZER", Material.LIGHT_BLUE_STAINED_GLASS, "§c高级冰箱");
    public static final SlimefunItemStack ADVANCED_CARBON_PRESS = new SlimefunItemStack("FINALTECH_ADVANCED_CARBON_PRESS", Material.BLACK_STAINED_GLASS, "§c高级碳压机");
    public static final SlimefunItemStack ADVANCED_ELECTRIC_SMELTERY = new SlimefunItemStack("FINALTECH_ADVANCED_ELECTRIC_SMELTERY", Material.FURNACE, "§c高级冶炼炉");
    public static final SlimefunItemStack ADVANCED_ORE_FACTORY = new SlimefunItemStack("FINALTECH_ADVANCED_ORE_FACTORY", Material.PURPUR_PILLAR, "§c高级矿石工厂");
    public static final SlimefunItemStack ADVANCED_DUST_FACTORY = new SlimefunItemStack("FINALTECH_ADVANCED_DUST_FACTORY", Material.RED_NETHER_BRICKS, "§c高级矿粉工厂");
    public static final SlimefunItemStack ADVANCED_FARM_FACTORY = new SlimefunItemStack("FINALTECH_ADVANCED_FARM_FACTORY", new ItemStack(Material.MOSS_BLOCK), "§c高级作物工厂");
    public static final SlimefunItemStack ADVANCED_AUTO_CRAFT = new SlimefunItemStack("FINALTECH_ADVANCED_AUTO_CRAFT", Material.BEACON, "§c高级自动合成机");

    // best machines
    public static final SlimefunItemStack ALL_COMPRESSION = new SlimefunItemStack("FINALTECH_ALL_COMPRESSION", Material.AMETHYST_BLOCK, "§9序列化构造器");
    public static final SlimefunItemStack ALL_FACTORY = new SlimefunItemStack("FINALTECH_ALL_FACTORY", Material.BUDDING_AMETHYST, "§9反序列化解析器");
    public static final SlimefunItemStack MATRIX_REACTOR = new SlimefunItemStack("FINALTECH_MATRIX_REACTOR", Material.COAL_BLOCK, "§9尘埃反应堆");

    // cargos
    public static final SlimefunItemStack BASIC_FRAME_MACHINE = new SlimefunItemStack("FINALTECH_BASIC_FRAME_MACHINE", Material.STONE, "§f基础机器框架", "§8可放置");
    public static final SlimefunItemStack BASIC_NORMAL_STORAGE_UNIT = new SlimefunItemStack("FINALTECH_BASIC_NORMAL_STORAGE_UNIT", Material.GLASS, "§f普通存储单元");
    public static final SlimefunItemStack BASIC_LINKED_STORAGE_UNIT = new SlimefunItemStack("FINALTECH_BASIC_LINKED_STORAGE_UNIT", Material.GLASS, "§f可识别存储单元");
    public static final SlimefunItemStack BASIC_CHARGEABLE_STORAGE_UNIT = new SlimefunItemStack("FINALTECH_BASIC_CHARGEABLE_STORAGE_UNIT", Material.GLASS, "§f可充电存储单元");
    public static final SlimefunItemStack TRANSFER_PIPE = new SlimefunItemStack("FINALTECH_CARGO_TRANSFER_PIPE", Material.END_ROD, "§f导管", "§8即放即用");
    public static final SlimefunItemStack TRANSFER_LINE = new SlimefunItemStack("FINALTECH_CARGO_TRANSFER_LINE", Material.DROPPER, "§f支架", "§8即放即用");
    public static final SlimefunItemStack TRANSFER_STATION = new SlimefunItemStack("FINALTECH_CARGO_TRANSFER_STATION", Material.TINTED_GLASS, "§f中转器");
    public static final SlimefunItemStack STORAGE_INTERACT_PORT = new SlimefunItemStack("FINALTECH_STORAGE_INTERACT_PORT", Material.BOOKSHELF, "§f存储交互接口");
    public static final SlimefunItemStack STORAGE_INSERT_PORT = new SlimefunItemStack("FINALTECH_STORAGE_INSERT_PORT", Material.BOOKSHELF, "§f高速存入接口");
    public static final SlimefunItemStack STORAGE_WITHDRAW_PORT = new SlimefunItemStack("FINALTECH_STORAGE_WITHDRAW_PORT", Material.BOOKSHELF, "§f高速取出接口");
    public static final SlimefunItemStack STORAGE_ITEM_WHITE = new SlimefunItemStack("FINALTECH_STORAGE_ITEM_WHITE", Material.WHITE_CONCRETE_POWDER, "§x§f§0§f§0§f§0存储卡-白",
            StorageCardItem.ITEM_LORE,
            "§7未存储物品");
    public static final SlimefunItemStack STORAGE_ITEM_ORANGE = new SlimefunItemStack("FINALTECH_STORAGE_ITEM_ORANGE", Material.ORANGE_CONCRETE_POWDER, "§x§f§0§f§0§f§0存储卡-橙",
            StorageCardItem.ITEM_LORE,
            "§7未存储物品");
    public static final SlimefunItemStack STORAGE_ITEM_MAGENTA = new SlimefunItemStack("FINALTECH_STORAGE_ITEM_MAGENTA", Material.MAGENTA_CONCRETE_POWDER, "§x§f§0§f§0§f§0存储卡-品红",
            StorageCardItem.ITEM_LORE,
            "§7未存储物品");
    public static final SlimefunItemStack STORAGE_ITEM_LIGHT_BLUE = new SlimefunItemStack("FINALTECH_STORAGE_ITEM_LIGHT_BLUE", Material.LIGHT_BLUE_CONCRETE_POWDER, "§x§f§0§f§0§f§0存储卡-淡蓝",
            StorageCardItem.ITEM_LORE,
            "§7未存储物品");
    public static final SlimefunItemStack STORAGE_ITEM_YELLOW = new SlimefunItemStack("FINALTECH_STORAGE_ITEM_YELLOW", Material.YELLOW_CONCRETE_POWDER, "§x§f§0§f§0§f§0存储卡-黄",
            StorageCardItem.ITEM_LORE,
            "§7未存储物品");
    public static final SlimefunItemStack STORAGE_ITEM_LIME = new SlimefunItemStack("FINALTECH_STORAGE_ITEM_LIME", Material.LIME_CONCRETE_POWDER, "§x§f§0§f§0§f§0存储卡-黄绿",
            StorageCardItem.ITEM_LORE,
            "§7未存储物品");
    public static final SlimefunItemStack STORAGE_ITEM_PINK = new SlimefunItemStack("FINALTECH_STORAGE_ITEM_PINK", Material.PINK_CONCRETE_POWDER, "§x§f§0§f§0§f§0存储卡-粉红",
            StorageCardItem.ITEM_LORE,
            "§7未存储物品");
    public static final SlimefunItemStack STORAGE_ITEM_GRAY = new SlimefunItemStack("FINALTECH_STORAGE_ITEM_GRAY", Material.GRAY_CONCRETE_POWDER, "§x§f§0§f§0§f§0存储卡-灰",
            StorageCardItem.ITEM_LORE,
            "§7未存储物品");
    public static final SlimefunItemStack STORAGE_ITEM_LIGHT_GRAY = new SlimefunItemStack("FINALTECH_STORAGE_ITEM_LIGHT_GRAY", Material.LIGHT_GRAY_CONCRETE_POWDER, "§x§f§0§f§0§f§0存储卡-淡灰",
            StorageCardItem.ITEM_LORE,
            "§7未存储物品");
    public static final SlimefunItemStack STORAGE_ITEM_CYAN = new SlimefunItemStack("FINALTECH_STORAGE_ITEM_CYAN", Material.CYAN_CONCRETE_POWDER, "§x§f§0§f§0§f§0存储卡-青",
            StorageCardItem.ITEM_LORE,
            "§7未存储物品");
    public static final SlimefunItemStack STORAGE_ITEM_PURPLE = new SlimefunItemStack("FINALTECH_STORAGE_ITEM_PURPLE", Material.PURPLE_CONCRETE_POWDER, "§x§f§0§f§0§f§0存储卡-紫",
            StorageCardItem.ITEM_LORE,
            "§7未存储物品");
    public static final SlimefunItemStack STORAGE_ITEM_BLUE = new SlimefunItemStack("FINALTECH_STORAGE_ITEM_BLUE", Material.BLUE_CONCRETE_POWDER, "§x§f§0§f§0§f§0存储卡-蓝",
            StorageCardItem.ITEM_LORE,
            "§7未存储物品");
    public static final SlimefunItemStack STORAGE_ITEM_BROWN = new SlimefunItemStack("FINALTECH_STORAGE_ITEM_BROWN", Material.BROWN_CONCRETE_POWDER, "§x§f§0§f§0§f§0存储卡-棕",
            StorageCardItem.ITEM_LORE,
            "§7未存储物品");
    public static final SlimefunItemStack STORAGE_ITEM_GREEN = new SlimefunItemStack("FINALTECH_STORAGE_ITEM_GREEN", Material.GREEN_CONCRETE_POWDER, "§x§f§0§f§0§f§0存储卡-绿",
            StorageCardItem.ITEM_LORE,
            "§7未存储物品");
    public static final SlimefunItemStack STORAGE_ITEM_RED = new SlimefunItemStack("FINALTECH_STORAGE_ITEM_RED", Material.RED_CONCRETE_POWDER, "§x§f§0§f§0§f§0存储卡-红",
            StorageCardItem.ITEM_LORE,
            "§7未存储物品");
    public static final SlimefunItemStack STORAGE_ITEM_BLACK = new SlimefunItemStack("FINALTECH_STORAGE_ITEM_BLACK", Material.BLACK_CONCRETE_POWDER, "§x§f§0§f§0§f§0存储卡-黑",
            StorageCardItem.ITEM_LORE,
            "§7未存储物品");

    // electric
    public static final SlimefunItemStack BASIC_CHARGE_INCREASE_CAPACITOR = new SlimefunItemStack("FINALTECH_BASIC_CHARGE_INCREASE_CAPACITOR", Material.RED_STAINED_GLASS, "§7基础充电增益电容",
            "§6最大可存储电量 §e" + BasicChargeIncreaseCapacitor.CAPACITOR + "J");
    public static final SlimefunItemStack BASIC_CONSUME_REDUCE_CAPACITOR = new SlimefunItemStack("FINALTECH_BASIC_CONSUME_REDUCE_CAPACITOR", Material.RED_STAINED_GLASS, "§7基础耗电减免电容",
            "§6最大可存储电量 §e" + BasicConsumeReduceCapacitor.CAPACITOR + "J");
    public static final SlimefunItemStack ADVANCED_CHARGE_INCREASE_CAPACITOR = new SlimefunItemStack("FINALTECH_ADVANCED_CHARGE_INCREASE_CAPACITOR", Material.RED_STAINED_GLASS, "§7高级充电增益电容",
            "§6最大可存储电量 §e" + BasicChargeIncreaseCapacitor.CAPACITOR + "J");
    public static final SlimefunItemStack ADVANCED_CONSUME_REDUCE_CAPACITOR = new SlimefunItemStack("FINALTECH_ADVANCED_CONSUME_REDUCE_CAPACITOR", Material.RED_STAINED_GLASS, "§7高级耗电减免电容",
            "§6最大可存储电量 §e" + BasicConsumeReduceCapacitor.CAPACITOR + "J");
    public static final SlimefunItemStack NORMAL_ELECTRICITY_SHOOT_PILE = new SlimefunItemStack("FINALTECH_NORMAL_ELECTRICITY_SHOOT_PILE", Material.DISPENSER, "§e普通射电桩",
            "§6传输半径§b " + NormalElectricityShootPile.RANGE + "格");
    public static final SlimefunItemStack ENERGIZED_ELECTRICITY_SHOOT_PILE = new SlimefunItemStack("FINALTECH_ENERGIZED_ELECTRICITY_SHOOT_PILE", Material.DISPENSER, "§e充能射电桩",
            "§6传输半径§b " + EnergizedElectricityShootPile.RANGE + "格");
    public static final SlimefunItemStack OVERLOADED_ELECTRICITY_SHOOT_PILE = new SlimefunItemStack("FINALTECH_EXCESS_LOAD_ELECTRICITY_SHOOT_PILE", Material.DISPENSER, "§e过载射电桩",
            "§6传输半径§b " + OverloadedElectricityShootPile.RANGE + "格");

    public static final SlimefunItemStack SMALL_EXPANDED_CAPACITOR = new SlimefunItemStack("FINALTECH_SMALL_EXPANDED_CAPACITOR", Material.YELLOW_STAINED_GLASS, "§a小型扩展电容",
            "",
            "§6单组流转电量§e " + SmallExpandedCapacitor.CAPACITY + "J",
            "§6电容组数§3 " + SmallExpandedCapacitor.STACK + "组",
            "§6实际最大存储电量§e " + (long) SmallExpandedCapacitor.CAPACITY * SmallExpandedCapacitor.STACK + "J");
    public static final SlimefunItemStack MEDIUM_EXPANDED_CAPACITOR = new SlimefunItemStack("FINALTECH_MEDIUM_EXPANDED_CAPACITOR", Material.YELLOW_STAINED_GLASS, "§a中型扩展电容",
            "",
            "§6单组流转电量§e " + MediumExpandedCapacitor.CAPACITY + "J",
            "§6电容组数§3 " + MediumExpandedCapacitor.STACK + "组",
            "§6实际最大存储电量§e " + (long) MediumExpandedCapacitor.CAPACITY * MediumExpandedCapacitor.STACK + "J");
    public static final SlimefunItemStack BIG_EXPANDED_CAPACITOR = new SlimefunItemStack("FINALTECH_BIG_EXPANDED_CAPACITOR", Material.YELLOW_STAINED_GLASS, "§a大型扩展电容",
            "",
            "§6单组流转电量§e " + BigExpandedCapacitor.CAPACITY + "J",
            "§6电容组数§3 " + BigExpandedCapacitor.STACK + "组",
            "§6实际最大存储电量§e " + (long) BigExpandedCapacitor.CAPACITY * BigExpandedCapacitor.STACK + "J");
    public static final SlimefunItemStack LARGE_EXPANDED_CAPACITOR = new SlimefunItemStack("FINALTECH_LARGE_EXPANDED_CAPACITOR", Material.YELLOW_STAINED_GLASS, "§a巨型扩展电容",
            "",
            "§6单组流转电量§e " + LargeExpandedCapacitor.CAPACITY + "J",
            "§6电容组数§3 " + LargeExpandedCapacitor.STACK + "组",
            "§6实际最大存储电量§e " + (long) LargeExpandedCapacitor.CAPACITY * LargeExpandedCapacitor.STACK + "J");
    public static final SlimefunItemStack CARBONADO_EXPANDED_CAPACITOR = new SlimefunItemStack("FINALTECH_CARBONADO_EXPANDED_CAPACITOR", Material.YELLOW_STAINED_GLASS, "§a黑金刚石扩展电容",
            "",
            "§6单组流转电量§e " + CarbonadoExpandedCapacitor.CAPACITY + "J",
            "§6电容组数§3 " + CarbonadoExpandedCapacitor.STACK + "组",
            "§6实际最大存储电量§e " + (long) CarbonadoExpandedCapacitor.CAPACITY * CarbonadoExpandedCapacitor.STACK + "J");
    public static final SlimefunItemStack ENERGIZED_EXPANDED_CAPACITOR = new SlimefunItemStack("FINALTECH_ENERGIZED_EXPANDED_CAPACITOR", Material.YELLOW_STAINED_GLASS, "§a终极扩展电容",
            "",
            "§6单组流转电量§e " + EnergizedExpandedCapacitor.CAPACITY + "J",
            "§6电容组数§3 " + EnergizedExpandedCapacitor.STACK + "组",
            "§6实际最大存储电量§e " + (long) EnergizedExpandedCapacitor.CAPACITY * EnergizedExpandedCapacitor.STACK + "J");
    public static final SlimefunItemStack ENERGIZED_STACK_EXPANDED_CAPACITOR = new SlimefunItemStack("FINALTECH_ENERGIZED_STACK_EXPANDED_CAPACITOR", Material.YELLOW_STAINED_GLASS, "§a终极扩展电容组",
            "",
            "§6单组流转电量§e " + EnergizedStackExpandedCapacitor.CAPACITY + "J",
            "§6电容组数§3 " + EnergizedStackExpandedCapacitor.STACK + "组",
            "§6实际最大存储电量§e " + (long) EnergizedStackExpandedCapacitor.CAPACITY * EnergizedStackExpandedCapacitor.STACK + "J");
    public static final SlimefunItemStack OVERLOADED_EXPANDED_CAPACITOR = new SlimefunItemStack("FINALTECH_OVERLOADED_EXPANDED_CAPACITOR", Material.YELLOW_STAINED_GLASS, "§a过载扩展电容",
            "",
            "§6单组流转电量§e " + EnergizedStackExpandedCapacitor.CAPACITY + "J",
            "§6电容组数§3 " + EnergizedStackExpandedCapacitor.STACK + "组",
            "§6实际最大存储电量§e " + (long) EnergizedStackExpandedCapacitor.CAPACITY * EnergizedStackExpandedCapacitor.STACK + "J");
    public static final SlimefunItemStack MATRIX_EXPANDED_CAPACITOR = new SlimefunItemStack("FINALTECH_MATRIX_EXPANDED_CAPACITOR", Material.YELLOW_STAINED_GLASS, "§a矩阵电容",
            "",
            "§6单组流转电量§e " + MatrixExpandedCapacitor.CAPACITY + "J",
            "§6电容组数§3 " + "∞" + "组",
            "§6实际最大存储电量§e " + "∞" + "J");
    public static final SlimefunItemStack ESCAPE_CAPACITOR = new SlimefunItemStack("FINALTECH_ESCAPE_CAPACITOR", Material.GREEN_STAINED_GLASS, "§a逸散电容",
            "§6最大存储电量§e " + EscapeCapacitor.CAPACITOR + "J",
            "§6传输半径§b " + EscapeCapacitor.RANGE + "格",
            "§6传输损耗§c " + ((1 - (1 / EscapeCapacitor.LOSS)) * 100) + "%");

    public static final SlimefunItemStack ORDERED_DUST_GENERATOR = new SlimefunItemStack("FINALTECH_ORDERED_DUST_GENERATOR", Material.BROWN_MUSHROOM_BLOCK, "§7尘埃发电机",
            "§6最大发电量§e " + DustGenerator.LIMIT + "J");

    public static final SlimefunItemStack BASIC_GENERATOR = new SlimefunItemStack("FINALTECH_BASIC_GENERATOR", Material.GLOWSTONE, "§2基础供电机",
            "§6供电量§e " + BasicGenerator.ELECTRICITY + "J",
            "§6传输半径§b " + BasicGenerator.RANGE + "格");
    public static final SlimefunItemStack ADVANCED_GENERATOR = new SlimefunItemStack("FINALTECH_ADVANCED_GENERATOR", Material.GLOWSTONE, "§2高级供电机",
            "§6供电量§e " + AdvancedGenerator.ELECTRICITY + "J",
            "§6传输半径§b " + AdvancedGenerator.RANGE + "格");
    public static final SlimefunItemStack CARBONADO_GENERATOR = new SlimefunItemStack("FINALTECH_CARBONADO_GENERATOR", Material.GLOWSTONE, "§2黑金刚石供电机",
            "§6供电量§e " + CarbonadoGenerator.ELECTRICITY + "J",
            "§6传输半径§b " + CarbonadoGenerator.RANGE + "格");
    public static final SlimefunItemStack ENERGIZED_GENERATOR = new SlimefunItemStack("FINALTECH_ENERGIZED_GENERATOR", Material.GLOWSTONE, "§2充能供电机",
            "§6供电量§e " + EnergizedGenerator.ELECTRICITY + "J",
            "§6传输半径§b " + EnergizedGenerator.RANGE + "格");
    public static final SlimefunItemStack ENERGIZED_STACK_GENERATOR = new SlimefunItemStack("FINALTECH_ENERGIZED_STACK_GENERATOR", Material.GLOWSTONE, "§2充能供电机组",
            "§6供电量§e " + EnergizedStackGenerator.ELECTRICITY + "J",
            "§6传输半径§b " + EnergizedStackGenerator.RANGE + "格");
    public static final SlimefunItemStack OVERLOADED_GENERATOR = new SlimefunItemStack("FINALTECH_OVERLOADED_GENERATOR", Material.GLOWSTONE, "§2过载供电机",
            "§6供电量§e " + OverloadedGenerator.ELECTRICITY + "J",
            "§6传输半径§b " + OverloadedGenerator.RANGE + "格");
    public static final SlimefunItemStack MATRIX_GENERATOR = new SlimefunItemStack("FINALTECH_MATRIX_GENERATOR", Material.SEA_LANTERN, "§2矩阵供电机",
            "§6传输半径§b " + MatrixGenerator.RANGE + "格");

    public static final SlimefunItemStack ENERGIZED_ACCELERATOR = new SlimefunItemStack("FINALTECH_ENERGY_ACCELERATOR", Material.TARGET, "§2充能加速器");
    public static final SlimefunItemStack OVERLOADED_ACCELERATOR = new SlimefunItemStack("FINALTECH_OVERLOADED_ACCELERATOR", Material.TARGET, "§2过载加速器");
    public static final SlimefunItemStack MATRIX_ACCELERATOR = new SlimefunItemStack("FINALTECH_MATRIX_ACCELERATOR", Material.TARGET, "§2矩阵加速器");

    // tool
    public static final SlimefunItemStack UNORDERED_SWORD = new SlimefunItemStack("FINALTECH_UNORDERED_SWORD", Material.WOODEN_SWORD, "§f无序剑");
    public static final SlimefunItemStack MENU_VIEWER = new SlimefunItemStack("FINALTECH_MENU_VIEWER", Material.SPYGLASS, "§7容器透镜", "§8观察粘液科技机器的输入槽和输出槽位置");
    public static final SlimefunItemStack MACHINE_CHARGE_CARD_L1 = new SlimefunItemStack("FINALTECH_MACHINE_CHARGE_CARD_L1", Material.PAPER, "§f充能卡L1");
    public static final SlimefunItemStack MACHINE_CHARGE_CARD_L2 = new SlimefunItemStack("FINALTECH_MACHINE_CHARGE_CARD_L2", Material.PAPER, "§f充能卡L2");
    public static final SlimefunItemStack MACHINE_CHARGE_CARD_L3 = new SlimefunItemStack("FINALTECH_MACHINE_CHARGE_CARD_L3", Material.PAPER, "§f充能卡L3");
    public static final SlimefunItemStack MACHINE_ACCELERATE_CARD_L1 = new SlimefunItemStack("FINALTECH_MACHINE_ACCELERATE_CARD_L1", Material.PAPER, "§f过载卡L1");
    public static final SlimefunItemStack MACHINE_ACCELERATE_CARD_L2 = new SlimefunItemStack("FINALTECH_MACHINE_ACCELERATE_CARD_L2", Material.PAPER, "§f过载卡L2");
    public static final SlimefunItemStack MACHINE_ACCELERATE_CARD_L3 = new SlimefunItemStack("FINALTECH_MACHINE_ACCELERATE_CARD_L3", Material.PAPER, "§f过载卡L3");
    public static final SlimefunItemStack MACHINE_ACTIVATE_CARD_L1 = new SlimefunItemStack("FINALTECH_MACHINE_ACTIVATE_CARD_L1", Material.PAPER, "§f带电超频卡L1");
    public static final SlimefunItemStack MACHINE_ACTIVATE_CARD_L2 = new SlimefunItemStack("FINALTECH_MACHINE_ACTIVATE_CARD_L2", Material.PAPER, "§f带电超频卡L2");
    public static final SlimefunItemStack MACHINE_ACTIVATE_CARD_L3 = new SlimefunItemStack("FINALTECH_MACHINE_ACTIVATE_CARD_L3", Material.PAPER, "§f带电超频卡L3");
    public static final SlimefunItemStack MACHINE_ACTIVATE_CARD_L4 = new SlimefunItemStack("FINALTECH_MACHINE_ACTIVATE_CARD_L4", Material.PAPER, "§f带电超频卡L4");
    public static final SlimefunItemStack MACHINE_CHARGE_CARD_INFINITY = new SlimefunItemStack("FINALTECH_MACHINE_CHARGE_CARD_INFINITY", Material.PAPER, "§f无限充能卡");
    public static final SlimefunItemStack MACHINE_ACCELERATE_CARD_INFINITY = new SlimefunItemStack("FINALTECH_MACHINE_ACCELERATE_CARD_INFINITY", Material.PAPER, "§f无限过载卡");
}
