package io.taraxacum.finaltech.setup.register;

import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.utils.HeadTexture;
import io.taraxacum.finaltech.item.StorageCardItem;
import io.taraxacum.finaltech.machine.area.*;
import io.taraxacum.finaltech.machine.capacitor.expanded.*;
import io.taraxacum.finaltech.machine.capacitor.*;
import io.taraxacum.finaltech.machine.generator.OrderedDustGenerator;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

/**
 * @author Final_ROOT
 */
public final class FinalTechItems {
    // items
    public static final SlimefunItemStack GEARWHEEL = new SlimefunItemStack("FINALTECH_GEARWHEEL", Material.REDSTONE, "&7齿轮");
    public static final SlimefunItemStack UNORDERED_DUST = new SlimefunItemStack("FINALTECH_UNORDERED_DUST", Material.WHEAT_SEEDS, "&f无序尘埃",
            "&8它被感知到了");
    public static final SlimefunItemStack ORDERED_DUST = new SlimefunItemStack("FINALTECH_ORDERED_DUST", Material.SLIME_BALL, "&f有序尘埃",
            "&8至此",
            "&8万物将被重构");
    public static final SlimefunItemStack QUANTITY_MODULE = new SlimefunItemStack("FINALTECH_QUANTITY_MODULE", Material.AMETHYST_SHARD, "&3数量组件",
            "&8线性改变机器效率");
    public static final SlimefunItemStack QUANTITY_MODULE_V2 = new SlimefunItemStack("FINALTECH_QUANTITY_MODULE_V2", Material.AMETHYST_SHARD, "&3数量组件v2",
            "&8递归地改变机器效率");
    public static final SlimefunItemStack SINGULARITY = new SlimefunItemStack("FINALTECH_SINGULARITY", Material.NETHER_STAR, "&e奇点",
            "&7纯粹意义上的奇点",
            "&d数量&7的堆积物");
    public static final SlimefunItemStack SPIROCHETE = new SlimefunItemStack("FINALTECH_SPIROCHETE", Material.ARMOR_STAND, "&e螺旋体",
            "&7凝聚着多种物质",
            "&d结构&7的聚合物");
    public static final SlimefunItemStack SHELL = new SlimefunItemStack("FINALTECH_SHELL", Material.PRISMARINE_SHARD, "&e壳",
            "&7内在空白之物",
            "&7具备&d转化&7为其他物质的可能");
    public static final SlimefunItemStack FAKE = new SlimefunItemStack("FINALTECH_FAKE", Material.END_CRYSTAL, "§x§f§0§f§0§f§0伪物",
            "&7模仿&d世界&7之物",
            "&7蕴含&d创造&7的可能");
    public static final SlimefunItemStack BUG = new SlimefunItemStack("FINALTECH_BUG", Material.INFESTED_COBBLESTONE, "&8BUG",
            "&8??!");
    public static final SlimefunItemStack CODE_ADDITION = new SlimefunItemStack("FINALTECH_CODE_ADDITION0", Material.PAPER, "&b概念<加法>");
    public static final SlimefunItemStack CODE_SYMMETRY = new SlimefunItemStack("FINALTECH_CODE_SYMMETRY", Material.PAPER, "&b概念<对称>");
    public static final SlimefunItemStack CODE_RANDOM = new SlimefunItemStack("FINALTECH_CODE_RANDOM", Material.PAPER, "&b概念<随机>");
    public static final SlimefunItemStack CODE_NULL = new SlimefunItemStack("FINALTECH_CODE_NULL", Material.PAPER, "&b概念<空值>");
    public static final SlimefunItemStack CODE_CREATE = new SlimefunItemStack("FINALTECH_CODE_CREATE", Material.PAPER, "&9高级概念<创造>",
            "&7繁荣的基础");
    public static final SlimefunItemStack CODE_FINAL = new SlimefunItemStack("FINALTECH_CODE_FINAL", Material.ENCHANTED_BOOK, "&f概念<Final>",
        "&7无数概念交织在一起",
        "&7于是他们无法再创造新事物");
    public static final SlimefunItemStack CREATION_GENEALOGY = new SlimefunItemStack("FINALTECH_CREATION_GENEALOGY", Material.BOOK,
            "§x§7§8§7§8§7§8造" +
            "§x§8§0§8§0§8§0物" +
            "§x§9§0§9§0§9§0系" +
            "§x§9§8§9§8§9§8谱");
    public static final SlimefunItemStack IMAGINARY_TRUTH = new SlimefunItemStack("FINALTECH_IMAGINARY_TRUTH", Material.PAPER,
            "§x§b§b§b§b§b§b虚" +
            "§x§b§b§b§b§b§b构" +
            "§x§b§b§b§b§b§b真" +
            "§x§b§b§b§b§b§b理");

    // basic machines
    public static final SlimefunItemStack BASIC_COBBLE_FACTORY = new SlimefunItemStack("FINALTECH_BASIC_COBBLE_FACTORY", Material.CHISELED_STONE_BRICKS, "&7基础刷石工厂");
    public static final SlimefunItemStack BASIC_ORE_FACTORY = new SlimefunItemStack("FINALTECH_BASIC_ORE_FACTORY", Material.CHISELED_DEEPSLATE, "&7基础矿石工厂");
    public static final SlimefunItemStack BASIC_DUST_FACTORY = new SlimefunItemStack("FINALTECH_BASIC_DUST_FACTORY", Material.POLISHED_DEEPSLATE, "&7基础矿粉工厂");
    public static final SlimefunItemStack BASIC_FARM_FACTORY = new SlimefunItemStack("FINALTECH_BASIC_FARM_FACTORY", Material.MOSS_BLOCK, "&7基础作物工厂");
    public static final SlimefunItemStack MANUAL_ENHANCED_CRAFTING_TABLE = new SlimefunItemStack("FINALTECH_MANUAL_ENHANCED_CRAFTING_TABLE", Material.CRAFTING_TABLE, "&6快捷增强型工作台");
    public static final SlimefunItemStack MANUAL_GRIND_STONE = new SlimefunItemStack("FINALTECH_MANUAL_GRIND_STONE", Material.DISPENSER, "&6快捷磨石");
    public static final SlimefunItemStack MANUAL_ARMOR_FORGE = new SlimefunItemStack("FINALTECH_MANUAL_ARMOR_FORGE", Material.IRON_BLOCK, "&6快捷盔甲锻造台");
    public static final SlimefunItemStack MANUAL_ORE_CRUSHER = new SlimefunItemStack("FINALTECH_MANUAL_ORE_CRUSHER", Material.DROPPER, "&6快捷矿石粉碎机");
    public static final SlimefunItemStack MANUAL_COMPRESSOR = new SlimefunItemStack("FINALTECH_MANUAL_COMPRESSOR", Material.PISTON, "&6快捷压缩机");
    public static final SlimefunItemStack MANUAL_SMELTERY = new SlimefunItemStack("FINALTECH_MANUAL_SMELTERY", Material.BLAST_FURNACE, "&6快捷冶炼炉");
    public static final SlimefunItemStack MANUAL_PRESSURE_CHAMBER = new SlimefunItemStack("FINALTECH_MANUAL_PRESSURE_CHAMBER", Material.STICKY_PISTON, "&6快捷压力机");
    public static final SlimefunItemStack MANUAL_MAGIC_WORKBENCH = new SlimefunItemStack("FINALTECH_MANUAL_MAGIC_WORKBENCH", Material.BOOKSHELF, "&6快捷魔法工作台");
    public static final SlimefunItemStack MANUAL_JUICER = new SlimefunItemStack("FINALTECH_MANUAL_JUICER", Material.GLASS, "&6快捷榨汁机");
    public static final SlimefunItemStack MANUAL_ANCIENT_ALTAR = new SlimefunItemStack("FINALTECH_MANUAL_ANCIENT_ALTAR", Material.ENCHANTING_TABLE, "&6快捷古代祭坛");
    public static final SlimefunItemStack MANUAL_HEATED_PRESSURE_CHAMBER = new SlimefunItemStack("FINALTECH_MANUAL_HEATED_PRESSURE_CHAMBER", Material.LIGHT_GRAY_STAINED_GLASS, "&6快捷加热压力舱");
    public static final SlimefunItemStack ORDERED_DUST_FACTORY = new SlimefunItemStack("FINALTECH_ORDERED_DUST_FACTORY", Material.DIRT, "&7尘埃制造机");

    // advanced machines
    public static final SlimefunItemStack ADVANCED_ELECTRIC_FURANCE = new SlimefunItemStack("FINALTECH_ADVANCED_ELECTRIC_FURANCE", Material.FURNACE, "&c高级电炉");
    public static final SlimefunItemStack ADVANCED_GOLD_PAN = new SlimefunItemStack("FINALTECH_ADVANCED_GOLD_PAN", Material.BROWN_TERRACOTTA, "&c高级淘金机");
    public static final SlimefunItemStack ADVANCED_ELECTRIC_DUST_WASHER = new SlimefunItemStack("FINALTECH_ADVANCED_DUST_WASHER", Material.BLUE_STAINED_GLASS, "&c高级洗矿机");
    public static final SlimefunItemStack ADVANCED_ELECTRIC_INGOT_FACTORY = new SlimefunItemStack("FINALTECH_ADVANCED_ELECTRIC_INGOT_FACTORY", Material.RED_TERRACOTTA, "&c高级铸锭机");
    public static final SlimefunItemStack ADVANCED_ELECTRIC_CRUCIBLE = new SlimefunItemStack("FINALTECH_ADVANCED_ELECTRIC_CRUCIBLE", Material.RED_TERRACOTTA, "&c高级坩埚");
    public static final SlimefunItemStack ADVANCED_ELECTRIC_ORE_GRINDER = new SlimefunItemStack("FINALTECH_ADVANCED_ELECTRIC_ORE_GRINDER", Material.FURNACE, "&c高级碎矿机");
    public static final SlimefunItemStack ADVANCED_HEATED_PRESSURE_CHAMBER = new SlimefunItemStack("FINALTECH_ADVANCED_HEATED_PRESSURE_CHAMBER", Material.LIGHT_GRAY_STAINED_GLASS, "&c高级加热压力仓");
    public static final SlimefunItemStack ADVANCED_ELECTRIC_INGOT_PULVERIZER = new SlimefunItemStack("FINALTECH_ADVANCED_ELECTRIC_INGOT_PULVERIZER", Material.FURNACE, "&c高级打粉机");
    public static final SlimefunItemStack ADVANCED_AUTO_DRIER = new SlimefunItemStack("FINALTECH_ADVANCED_AUTO_DRIER", Material.SMOKER, "&c高级烘干机");
    public static final SlimefunItemStack ADVANCED_ELECTRIC_PRESS = new SlimefunItemStack("FINALTECH_ADVANCED_ELECTRIC_PRESS", new SlimefunItemStack("", HeadTexture.ELECTRIC_PRESS, "", ""), "&c高级压缩机");
    public static final SlimefunItemStack ADVANCED_FOOD_FACTORY = new SlimefunItemStack("FINALTECH_ADVANCED_FOOD_FACTORY", Material.GREEN_TERRACOTTA, "&c高级作物加工厂");
    public static final SlimefunItemStack ADVANCED_FREEZER = new SlimefunItemStack("FINALTECH_ADVANCED_FREEZER", Material.LIGHT_BLUE_STAINED_GLASS, "&c高级冰箱");
    public static final SlimefunItemStack ADVANCED_CARBON_PRESS = new SlimefunItemStack("FINALTECH_ADVANCED_CARBON_PRESS", Material.BLACK_STAINED_GLASS, "&c高级碳压机");
    public static final SlimefunItemStack ADVANCED_ELECTRIC_SMELTERY = new SlimefunItemStack("FINALTECH_ADVANCED_ELECTRIC_SMELTERY", Material.FURNACE, "&c高级冶炼炉");
    public static final SlimefunItemStack ADVANCED_ORE_FACTORY = new SlimefunItemStack("FINALTECH_ADVANCED_ORE_FACTORY", Material.PURPUR_PILLAR, "&c高级矿石工厂");
    public static final SlimefunItemStack ADVANCED_DUST_FACTORY = new SlimefunItemStack("FINALTECH_ADVANCED_DUST_FACTORY", Material.RED_NETHER_BRICKS, "&c高级矿粉工厂");
    public static final SlimefunItemStack ADVANCED_FARM_FACTORY = new SlimefunItemStack("FINALTECH_ADVANCED_FARM_FACTORY", new ItemStack(Material.MOSS_BLOCK), "&c高级作物工厂");
    public static final SlimefunItemStack ADVANCED_AUTO_CRAFT = new SlimefunItemStack("FINALTECH_ADVANCED_AUTO_CRAFT", Material.BEACON, "&c高级自动合成机");

    // best machines
    public static final SlimefunItemStack ALL_COMPRESSION = new SlimefunItemStack("FINALTECH_ALL_COMPRESSION", Material.AMETHYST_BLOCK, "&9万物压缩器");
    public static final SlimefunItemStack ALL_FACTORY = new SlimefunItemStack("FINALTECH_ALL_FACTORY", Material.BUDDING_AMETHYST, "&9万物工厂");
    public static final SlimefunItemStack COPY_CARD = new SlimefunItemStack("FINALTECH_COPY_CARD", Material.PAPER, "&4复制卡");
    public static final SlimefunItemStack OVERCLOCK_FRAME_MACHINE = new SlimefunItemStack("FINALTECH_OVERCLOCK_FRAME_MACHINE", Material.LECTERN, "&9超频框架",
            "&6最大可存储电量 &e" + 536870912 + "J");

    // cargos
    public static final SlimefunItemStack BASIC_FRAME_MACHINE = new SlimefunItemStack("FINALTECH_BASIC_FRAME_MACHINE", Material.STONE, "&f基础机器框架", "&8可放置");
    public static final SlimefunItemStack BASIC_NORMAL_STORAGE_UNIT = new SlimefunItemStack("FINALTECH_BASIC_NORMAL_STORAGE_UNIT", Material.GLASS, "&f普通存储单元");
    public static final SlimefunItemStack BASIC_LINKED_STORAGE_UNIT = new SlimefunItemStack("FINALTECH_BASIC_LINKED_STORAGE_UNIT", Material.GLASS, "&f可识别存储单元");
    public static final SlimefunItemStack BASIC_CHARGEABLE_STORAGE_UNIT = new SlimefunItemStack("FINALTECH_BASIC_CHARGEABLE_STORAGE_UNIT", Material.GLASS, "&f可充电存储单元");
    public static final SlimefunItemStack TRANSFER_PIPE = new SlimefunItemStack("FINALTECH_CARGO_TRANSFER_PIPE", Material.END_ROD, "&f导管",
            "&8即放即用");
    public static final SlimefunItemStack TRANSFER_LINE = new SlimefunItemStack("FINALTECH_CARGO_TRANSFER_LINE", Material.DROPPER, "&f支架",
            "&8即放即用");
    public static final SlimefunItemStack TRANSFER_STATION = new SlimefunItemStack("FINALTECH_CARGO_TRANSFER_STATION", Material.TINTED_GLASS, "&f中转器");
    public static final SlimefunItemStack STORAGE_INTERACT_PORT = new SlimefunItemStack("FINALTECH_STORAGE_INTERACT_PORT", Material.BOOKSHELF, "&f存储交互接口");
    public static final SlimefunItemStack STORAGE_INSERT_PORT = new SlimefunItemStack("FINALTECH_STORAGE_INSERT_PORT", Material.BOOKSHELF, "&f高速存入接口");
    public static final SlimefunItemStack STORAGE_WITHDRAW_PORT = new SlimefunItemStack("FINALTECH_STORAGE_WITHDRAW_PORT", Material.BOOKSHELF, "&f高速取出接口");
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
    public static final SlimefunItemStack BASIC_CHARGE_INCREASE_CAPACITOR = new SlimefunItemStack("FINALTECH_BASIC_CHARGE_INCREASE_CAPACITOR", Material.RED_STAINED_GLASS, "&7基础充电增益电容",
            "&6最大可存储电量 &e" + BasicChargeIncreaseCapacitor.CAPACITOR + "J");
    public static final SlimefunItemStack BASIC_CONSUME_REDUCE_CAPACITOR = new SlimefunItemStack("FINALTECH_BASIC_CONSUME_REDUCE_CAPACITOR", Material.RED_STAINED_GLASS, "&7基础耗电减免电容",
            "&6最大可存储电量 &e" + BasicConsumeReduceCapacitor.CAPACITOR + "J");
    public static final SlimefunItemStack BASIC_SELF_GENERATE_CAPACITOR = new SlimefunItemStack("FINALTECH_BASIC_SELF_GENERATE_CAPACITOR", Material.RED_STAINED_GLASS, "&7基础自发电电容",
            "&6最大可存储电量 &e" + BasicSelfGenerateCapacitor.CAPACITOR + "J");
    public static final SlimefunItemStack BASIC_VOID_GENERATE_CAPACITOR = new SlimefunItemStack("FINALTECH_BASIC_VOID_GENERATE_CAPACITOR", Material.RED_STAINED_GLASS, "&7基础空发电电容",
            "&6最大可存储电量 &e" + BasicVoidGenerateCapacitor.CAPACITOR + "J");

    public static final SlimefunItemStack SMALL_EXPANDED_CAPACITOR = new SlimefunItemStack("FINALTECH_SMALL_EXPANDED_CAPACITOR", Material.YELLOW_STAINED_GLASS, "&a小型扩展电容",
            "",
            "&6单组流转电量&e " + SmallExpandedCapacitor.CAPACITY + "J",
            "&6电容组数&3 " + SmallExpandedCapacitor.STACK + "组",
            "&6实际最大存储电量&e " + (long) SmallExpandedCapacitor.CAPACITY * SmallExpandedCapacitor.STACK + "J");
    public static final SlimefunItemStack MEDIUM_EXPANDED_CAPACITOR = new SlimefunItemStack("FINALTECH_MEDIUM_EXPANDED_CAPACITOR", Material.YELLOW_STAINED_GLASS, "&a中型扩展电容",
            "",
            "&6单组流转电量&e " + MediumExpandedCapacitor.CAPACITY + "J",
            "&6电容组数&3 " + MediumExpandedCapacitor.STACK + "组",
            "&6实际最大存储电量&e " + (long) MediumExpandedCapacitor.CAPACITY * MediumExpandedCapacitor.STACK + "J");
    public static final SlimefunItemStack BIG_EXPANDED_CAPACITOR = new SlimefunItemStack("FINALTECH_BIG_EXPANDED_CAPACITOR", Material.YELLOW_STAINED_GLASS, "&a大型扩展电容",
            "",
            "&6单组流转电量&e " + BigExpandedCapacitor.CAPACITY + "J",
            "&6电容组数&3 " + BigExpandedCapacitor.STACK + "组",
            "&6实际最大存储电量&e " + (long) BigExpandedCapacitor.CAPACITY * BigExpandedCapacitor.STACK + "J");
    public static final SlimefunItemStack LARGE_EXPANDED_CAPACITOR = new SlimefunItemStack("FINALTECH_LARGE_EXPANDED_CAPACITOR", Material.YELLOW_STAINED_GLASS, "&a巨型扩展电容",
            "",
            "&6单组流转电量&e " + LargeExpandedCapacitor.CAPACITY + "J",
            "&6电容组数&3 " + LargeExpandedCapacitor.STACK + "组",
            "&6实际最大存储电量&e " + (long) LargeExpandedCapacitor.CAPACITY * LargeExpandedCapacitor.STACK + "J");
    public static final SlimefunItemStack CARBONADO_EXPANDED_CAPACITOR = new SlimefunItemStack("FINALTECH_CARBONADO_EXPANDED_CAPACITOR", Material.YELLOW_STAINED_GLASS, "&a黑金刚石扩展电容",
            "",
            "&6单组流转电量&e " + CarbonadoExpandedCapacitor.CAPACITY + "J",
            "&6电容组数&3 " + CarbonadoExpandedCapacitor.STACK + "组",
            "&6实际最大存储电量&e " + (long) CarbonadoExpandedCapacitor.CAPACITY * CarbonadoExpandedCapacitor.STACK + "J");
    public static final SlimefunItemStack ENERGIZED_EXPANDED_CAPACITOR = new SlimefunItemStack("FINALTECH_ENERGIZED_EXPANDED_CAPACITOR", Material.YELLOW_STAINED_GLASS, "&a终极扩展电容",
            "",
            "&6单组流转电量&e " + EnergizedExpandedCapacitor.CAPACITY + "J",
            "&6电容组数&3 " + EnergizedExpandedCapacitor.STACK + "组",
            "&6实际最大存储电量&e " + (long) EnergizedExpandedCapacitor.CAPACITY * EnergizedExpandedCapacitor.STACK + "J");
    public static final SlimefunItemStack MATRIX_EXPANDED_CAPACITOR = new SlimefunItemStack("FINALTECH_MATRIX_EXPANDED_CAPACITOR", Material.YELLOW_STAINED_GLASS, "&a矩阵电容",
            "",
            "&6单组流转电量&e " + MatrixExpandedCapacitor.CAPACITY + "J",
            "&6电容组数&3 " + "∞" + "组",
            "&6实际最大存储电量&e " + "∞" + "J");

    public static final SlimefunItemStack ORDERED_DUST_GENERATOR = new SlimefunItemStack("FINALTECH_ORDERED_DUST_GENERATOR", Material.BROWN_MUSHROOM_BLOCK, "&7尘埃发电机",
            "&e最大发电量 " + OrderedDustGenerator.LIMIT + "J");

    public static final SlimefunItemStack BASIC_GENERATOR = new SlimefunItemStack("FINALTECH_BASIC_GENERATOR", Material.GLOWSTONE, "&2基础供电机",
            "&e供电量 " + BasicGenerator.ELECTRICITY + "J",
            "&b传输半径 " + BasicGenerator.RANGE + "格");
    public static final SlimefunItemStack ADVANCED_GENERATOR = new SlimefunItemStack("FINALTECH_ADVANCED_GENERATOR", Material.GLOWSTONE, "&2高级供电机",
            "&e供电量 " + AdvancedGenerator.ELECTRICITY + "J",
            "&b传输半径 " + AdvancedGenerator.RANGE + "格");
    public static final SlimefunItemStack REINFORCED_GENERATOR = new SlimefunItemStack("FINALTECH_REINFORCED_GENERATOR", Material.GLOWSTONE, "&2强化合金供电机",
            "&e供电量 " + ReinforcedGenerator.ELECTRICITY + "J",
            "&b传输半径 " + ReinforcedGenerator.RANGE + "格");
    public static final SlimefunItemStack CARBONADO_GENERATOR = new SlimefunItemStack("FINALTECH_CARBONADO_GENERATOR", Material.GLOWSTONE, "&2黑金刚石供电机",
            "&e供电量 " + CarbonadoGenerator.ELECTRICITY + "J",
            "&b传输半径 " + CarbonadoGenerator.RANGE + "格");
    public static final SlimefunItemStack ENERGIZED_GENERATOR = new SlimefunItemStack("FINALTECH_ENERGIZED_GENERATOR", Material.GLOWSTONE, "&2充能供电机",
            "&e供电量 " + EnergizedGenerator.ELECTRICITY + "J",
            "&b传输半径 " + EnergizedGenerator.RANGE + "格");
    public static final SlimefunItemStack MATRIX_GENERATOR = new SlimefunItemStack("FINALTECH_MATRIX_GENERATOR", Material.SEA_LANTERN, "&2矩阵供电机",
            "&e供电量 " + MatrixGenerator.ELECTRICITY + "J",
            "&b传输半径 " + MatrixGenerator.RANGE + "格");

    // tool
    public static final SlimefunItemStack UNORDERED_SWORD = new SlimefunItemStack("FINALTECH_UNORDERED_SWORD", Material.WOODEN_SWORD, "&f无序剑",
            "&7无效化受伤触发机制",
            "&7无视受伤保护机制 I",
            "&7无敌穿透 V",
            "&7耐久损耗伤害追加 I");
}
