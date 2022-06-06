package io.taraxacum.finaltech.setup;

import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.utils.HeadTexture;
import io.taraxacum.finaltech.core.items.unusable.StorageCardItem;
import io.taraxacum.finaltech.util.TextUtil;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

/**
 * @author Final_ROOT
 */
public final class FinalTechItems {
    // items
    public static final SlimefunItemStack WATER_CARD = new SlimefunItemStack("FINALTECH_WATER_CARD", Material.PAPER, "§9水卡");
    public static final SlimefunItemStack LAVA_CARD = new SlimefunItemStack("FINALTECH_LAVAL_CARD", Material.PAPER, "§6岩浆卡");
    public static final SlimefunItemStack MILK_CARD = new SlimefunItemStack("FINALTECH_MILK_CARD", Material.PAPER, "§f牛奶卡");
    public static final SlimefunItemStack GEARWHEEL = new SlimefunItemStack("FINALTECH_GEARWHEEL", Material.REDSTONE, TextUtil.colorPseudorandomString("齿轮"));
    public static final SlimefunItemStack UNORDERED_DUST = new SlimefunItemStack("FINALTECH_UNORDERED_DUST", Material.WHEAT_SEEDS, TextUtil.colorPseudorandomString("无序尘埃"));
    public static final SlimefunItemStack ORDERED_DUST = new SlimefunItemStack("FINALTECH_ORDERED_DUST", Material.SLIME_BALL, TextUtil.colorPseudorandomString("有序尘埃"));
    public static final SlimefunItemStack BUG = new SlimefunItemStack("FINALTECH_BUG", Material.BONE_MEAL, TextUtil.colorPseudorandomString("锟斤拷"));
    public static final SlimefunItemStack ENTROPY = new SlimefunItemStack("FINALTECH_ENTROPY", Material.WHEAT_SEEDS, "§0熵");
    public static final SlimefunItemStack QUANTITY_MODULE = new SlimefunItemStack("FINALTECH_QUANTITY_MODULE", Material.AMETHYST_SHARD, TextUtil.colorPseudorandomString("数量组件"));
    public static final SlimefunItemStack COPY_CARD = new SlimefunItemStack("FINALTECH_COPY_CARD", Material.FLINT, TextUtil.colorPseudorandomString("复制卡"));
    public static final SlimefunItemStack ANNULAR = new SlimefunItemStack("FINALTECH_ANNULAR", Material.BOWL, TextUtil.colorPseudorandomString("环"));
    public static final SlimefunItemStack QUANTITY_MODULE_INFINITY = new SlimefunItemStack("FINALTECH_QUANTITY_MODULE_INFINITY", Material.AMETHYST_SHARD, TextUtil.colorPseudorandomString("数量组件-无限"));
    public static final SlimefunItemStack SINGULARITY = new SlimefunItemStack("FINALTECH_SINGULARITY", Material.NETHER_STAR, TextUtil.colorPseudorandomString("奇点"));
    public static final SlimefunItemStack SPIROCHETE = new SlimefunItemStack("FINALTECH_SPIROCHETE", Material.STRING, TextUtil.colorPseudorandomString("螺旋体"));
    public static final SlimefunItemStack SHELL = new SlimefunItemStack("FINALTECH_SHELL", Material.PRISMARINE_SHARD, TextUtil.colorPseudorandomString("壳"));
    public static final SlimefunItemStack PHONY = new SlimefunItemStack("FINALTECH_PHONY", Material.END_CRYSTAL, TextUtil.colorPseudorandomString("伪物"));

    // tool
    public static final SlimefunItemStack MACHINE_CHARGE_CARD_L1 = new SlimefunItemStack("FINALTECH_MACHINE_CHARGE_CARD_L1", Material.BRICK, TextUtil.colorPseudorandomString("充能卡L1"));
    public static final SlimefunItemStack MACHINE_CHARGE_CARD_L2 = new SlimefunItemStack("FINALTECH_MACHINE_CHARGE_CARD_L2", Material.BRICK, TextUtil.colorPseudorandomString("充能卡L2"));
    public static final SlimefunItemStack MACHINE_CHARGE_CARD_L3 = new SlimefunItemStack("FINALTECH_MACHINE_CHARGE_CARD_L3", Material.BRICK, TextUtil.colorPseudorandomString("充能卡L3"));
    public static final SlimefunItemStack MACHINE_ACCELERATE_CARD_L1 = new SlimefunItemStack("FINALTECH_MACHINE_ACCELERATE_CARD_L1", Material.NETHER_BRICK, TextUtil.colorPseudorandomString("过载卡L1"));
    public static final SlimefunItemStack MACHINE_ACCELERATE_CARD_L2 = new SlimefunItemStack("FINALTECH_MACHINE_ACCELERATE_CARD_L2", Material.NETHER_BRICK, TextUtil.colorPseudorandomString("过载卡L2"));
    public static final SlimefunItemStack MACHINE_ACCELERATE_CARD_L3 = new SlimefunItemStack("FINALTECH_MACHINE_ACCELERATE_CARD_L3", Material.NETHER_BRICK, TextUtil.colorPseudorandomString("过载卡L3"));
    public static final SlimefunItemStack MACHINE_ACTIVATE_CARD_L1 = new SlimefunItemStack("FINALTECH_MACHINE_ACTIVATE_CARD_L1", Material.COPPER_INGOT, TextUtil.colorPseudorandomString("带电超频卡L1"));
    public static final SlimefunItemStack MACHINE_ACTIVATE_CARD_L2 = new SlimefunItemStack("FINALTECH_MACHINE_ACTIVATE_CARD_L2", Material.COPPER_INGOT, TextUtil.colorPseudorandomString("带电超频卡L2"));
    public static final SlimefunItemStack MACHINE_ACTIVATE_CARD_L3 = new SlimefunItemStack("FINALTECH_MACHINE_ACTIVATE_CARD_L3", Material.COPPER_INGOT, TextUtil.colorPseudorandomString("带电超频卡L3"));
    public static final SlimefunItemStack MENU_VIEWER = new SlimefunItemStack("FINALTECH_MENU_VIEWER", Material.SPYGLASS, TextUtil.colorPseudorandomString("容器透镜"));
    public static final SlimefunItemStack LOCATION_RECORDER = new SlimefunItemStack("FINALTECH_LOCATION_RECORDER", Material.COMPASS, TextUtil.colorPseudorandomString("坐标记录器"));

    // cargo and storage
    public static final SlimefunItemStack BASIC_FRAME_MACHINE = new SlimefunItemStack("FINALTECH_BASIC_FRAME_MACHINE", Material.STONE, TextUtil.colorPseudorandomString("基础机器框架"), "§8可放置");
    public static final SlimefunItemStack NORMAL_STORAGE_UNIT = new SlimefunItemStack("FINALTECH_NORMAL_STORAGE_UNIT", Material.GLASS, TextUtil.colorPseudorandomString("普通存储单元"));
    public static final SlimefunItemStack DIVIDED_STORAGE_UNIT = new SlimefunItemStack("FINALTECH_DIVIDED_STORAGE_UNIT", Material.GLASS, TextUtil.colorPseudorandomString("存储单元-分层"));
    public static final SlimefunItemStack LIMITED_STORAGE_UNIT = new SlimefunItemStack("FINALTECH_LIMITED_STORAGE_UNIT", Material.GLASS, TextUtil.colorPseudorandomString("存储单元-限制"));
    public static final SlimefunItemStack STACK_STORAGE_UNIT = new SlimefunItemStack("FINALTECH_STACK_STORAGE_UNIT", Material.GLASS, TextUtil.colorPseudorandomString("存储单元-堆叠"));
    public static final SlimefunItemStack DIVIDED_LIMITED_STORAGE_UNIT = new SlimefunItemStack("FINALTECH_DIVIDED_LIMITED_STORAGE_UNIT", Material.GLASS, TextUtil.colorPseudorandomString("存储单元-分层|限制"));
    public static final SlimefunItemStack DIVIDED_STACK_STORAGE_UNIT = new SlimefunItemStack("FINALTECH_DIVIDED_STACK_STORAGE_UNIT", Material.GLASS, TextUtil.colorPseudorandomString("存储单元-分层|堆叠"));
    public static final SlimefunItemStack LIMITED_STACK_STORAGE_UNIT = new SlimefunItemStack("FINALTECH_LIMITED_STACK_STORAGE_UNIT", Material.GLASS, TextUtil.colorPseudorandomString("存储单元-限制|堆叠"));
    public static final SlimefunItemStack CHARGEABLE_STORAGE_UNIT = new SlimefunItemStack("FINALTECH_CHARGEABLE_STORAGE_UNIT", Material.GLASS, TextUtil.colorPseudorandomString("可充电存储单元"));
    public static final SlimefunItemStack RANDOM_INPUT_STORAGE_UNIT = new SlimefunItemStack("FINALTECH_RANDOM_INPUT_STORAGE_UNIT", Material.GLASS, TextUtil.colorPseudorandomString("随机存入单元"));
    public static final SlimefunItemStack RANDOM_OUTPUT_STORAGE_UNIT = new SlimefunItemStack("FINALTECH_RANDOM_OUTPUT_STORAGE_UNIT", Material.GLASS, TextUtil.colorPseudorandomString("随机取出单元"));
    public static final SlimefunItemStack RANDOM_STORAGE_UNIT = new SlimefunItemStack("FINALTECH_RANDOM_STORAGE_UNIT", Material.GLASS, TextUtil.colorPseudorandomString("随机存储单元"));
    public static final SlimefunItemStack DISTRIBUTE_LEFT_STORAGE_UNIT = new SlimefunItemStack("FINALTECH_DISTRIBUTE_LEFT_STORAGE_UNIT", Material.GLASS, TextUtil.colorPseudorandomString("左平均存储单元"));
    public static final SlimefunItemStack DISTRIBUTE_RIGHT_STORAGE_UNIT = new SlimefunItemStack("FINALTECH_DISTRIBUTE_RIGHT_STORAGE_UNIT", Material.GLASS, TextUtil.colorPseudorandomString("右平均存储单元"));
    public static final SlimefunItemStack REMOTE_ACCESSOR = new SlimefunItemStack("FINALTECH_REMOTE_ACCESSOR", Material.OBSERVER, TextUtil.colorPseudorandomString("远程访问器"));
    public static final SlimefunItemStack LINK_TRANSFER = new SlimefunItemStack("FINALTECH_LINK_TRANSFER", Material.END_ROD, TextUtil.colorPseudorandomString("对点传输器"), "§8即放即用");
    public static final SlimefunItemStack LINE_TRANSFER = new SlimefunItemStack("FINALTECH_LINE_TRANSFER", Material.DROPPER, TextUtil.colorPseudorandomString("链式传输器"), "§8即放即用");
    public static final SlimefunItemStack STATION_TRANSFER = new SlimefunItemStack("FINALTECH_STATION_TRANSFER", Material.TINTED_GLASS, TextUtil.colorPseudorandomString("网状传输器"));
    public static final SlimefunItemStack LOCATION_TRANSFER = new SlimefunItemStack("FINALTECH_LOCATION_TRANSFER", Material.NOTE_BLOCK, TextUtil.colorPseudorandomString("坐标传输器"));
    public static final SlimefunItemStack STORAGE_INTERACT_PORT = new SlimefunItemStack("FINALTECH_STORAGE_INTERACT_PORT", Material.BOOKSHELF, TextUtil.colorPseudorandomString("存储交互接口"));
    public static final SlimefunItemStack STORAGE_INSERT_PORT = new SlimefunItemStack("FINALTECH_STORAGE_INSERT_PORT", Material.BOOKSHELF, TextUtil.colorPseudorandomString("高速存入接口"));
    public static final SlimefunItemStack STORAGE_WITHDRAW_PORT = new SlimefunItemStack("FINALTECH_STORAGE_WITHDRAW_PORT", Material.BOOKSHELF, TextUtil.colorPseudorandomString("高速取出接口"));
    public static final SlimefunItemStack STORAGE_CARD = new SlimefunItemStack("FINALTECH_STORAGE_ITEM_UNCOLORED", Material.WATER_BUCKET, TextUtil.colorPseudorandomString("存储卡"), StorageCardItem.ITEM_LORE);
//    public static final SlimefunItemStack STORAGE_ITEM_WHITE = new SlimefunItemStack("FINALTECH_STORAGE_ITEM_WHITE", Material.WHITE_CONCRETE_POWDER, TextUtil.colorPseudorandomString("存储卡-白"), StorageCardItem.ITEM_LORE);
//    public static final SlimefunItemStack STORAGE_ITEM_ORANGE = new SlimefunItemStack("FINALTECH_STORAGE_ITEM_ORANGE", Material.ORANGE_CONCRETE_POWDER, TextUtil.colorPseudorandomString("存储卡-橙"), StorageCardItem.ITEM_LORE);
//    public static final SlimefunItemStack STORAGE_ITEM_MAGENTA = new SlimefunItemStack("FINALTECH_STORAGE_ITEM_MAGENTA", Material.MAGENTA_CONCRETE_POWDER, TextUtil.colorPseudorandomString("存储卡-品红"), StorageCardItem.ITEM_LORE);
//    public static final SlimefunItemStack STORAGE_ITEM_LIGHT_BLUE = new SlimefunItemStack("FINALTECH_STORAGE_ITEM_LIGHT_BLUE", Material.LIGHT_BLUE_CONCRETE_POWDER, TextUtil.colorPseudorandomString("存储卡-淡蓝"), StorageCardItem.ITEM_LORE);
//    public static final SlimefunItemStack STORAGE_ITEM_YELLOW = new SlimefunItemStack("FINALTECH_STORAGE_ITEM_YELLOW", Material.YELLOW_CONCRETE_POWDER, TextUtil.colorPseudorandomString("存储卡-黄"), StorageCardItem.ITEM_LORE);
//    public static final SlimefunItemStack STORAGE_ITEM_LIME = new SlimefunItemStack("FINALTECH_STORAGE_ITEM_LIME", Material.LIME_CONCRETE_POWDER, TextUtil.colorPseudorandomString("存储卡-黄绿"), StorageCardItem.ITEM_LORE);
//    public static final SlimefunItemStack STORAGE_ITEM_PINK = new SlimefunItemStack("FINALTECH_STORAGE_ITEM_PINK", Material.PINK_CONCRETE_POWDER, TextUtil.colorPseudorandomString("存储卡-粉红"), StorageCardItem.ITEM_LORE);
//    public static final SlimefunItemStack STORAGE_ITEM_GRAY = new SlimefunItemStack("FINALTECH_STORAGE_ITEM_GRAY", Material.GRAY_CONCRETE_POWDER, TextUtil.colorPseudorandomString("存储卡-灰"), StorageCardItem.ITEM_LORE);
//    public static final SlimefunItemStack STORAGE_ITEM_LIGHT_GRAY = new SlimefunItemStack("FINALTECH_STORAGE_ITEM_LIGHT_GRAY", Material.LIGHT_GRAY_CONCRETE_POWDER, TextUtil.colorPseudorandomString("存储卡-淡灰"), StorageCardItem.ITEM_LORE);
//    public static final SlimefunItemStack STORAGE_ITEM_CYAN = new SlimefunItemStack("FINALTECH_STORAGE_ITEM_CYAN", Material.CYAN_CONCRETE_POWDER, TextUtil.colorPseudorandomString("存储卡-青"), StorageCardItem.ITEM_LORE);
//    public static final SlimefunItemStack STORAGE_ITEM_PURPLE = new SlimefunItemStack("FINALTECH_STORAGE_ITEM_PURPLE", Material.PURPLE_CONCRETE_POWDER, TextUtil.colorPseudorandomString("存储卡-紫"), StorageCardItem.ITEM_LORE);
//    public static final SlimefunItemStack STORAGE_ITEM_BLUE = new SlimefunItemStack("FINALTECH_STORAGE_ITEM_BLUE", Material.BLUE_CONCRETE_POWDER, TextUtil.colorPseudorandomString("存储卡-蓝"), StorageCardItem.ITEM_LORE);
//    public static final SlimefunItemStack STORAGE_ITEM_BROWN = new SlimefunItemStack("FINALTECH_STORAGE_ITEM_BROWN", Material.BROWN_CONCRETE_POWDER, TextUtil.colorPseudorandomString("存储卡-棕"), StorageCardItem.ITEM_LORE);
//    public static final SlimefunItemStack STORAGE_ITEM_GREEN = new SlimefunItemStack("FINALTECH_STORAGE_ITEM_GREEN", Material.GREEN_CONCRETE_POWDER, TextUtil.colorPseudorandomString("存储卡-绿"), StorageCardItem.ITEM_LORE);
//    public static final SlimefunItemStack STORAGE_ITEM_RED = new SlimefunItemStack("FINALTECH_STORAGE_ITEM_RED", Material.RED_CONCRETE_POWDER, TextUtil.colorPseudorandomString("存储卡-红"), StorageCardItem.ITEM_LORE);
//    public static final SlimefunItemStack STORAGE_ITEM_BLACK = new SlimefunItemStack("FINALTECH_STORAGE_ITEM_BLACK", Material.BLACK_CONCRETE_POWDER, TextUtil.colorPseudorandomString("存储卡-黑"), StorageCardItem.ITEM_LORE);

    // electric
    public static final SlimefunItemStack BASIC_CHARGE_INCREASE_CAPACITOR = new SlimefunItemStack("FINALTECH_BASIC_CHARGE_INCREASE_CAPACITOR", Material.RED_STAINED_GLASS, TextUtil.colorPseudorandomString("基础充电增益电容"));
    public static final SlimefunItemStack BASIC_CONSUME_REDUCE_CAPACITOR = new SlimefunItemStack("FINALTECH_BASIC_CONSUME_REDUCE_CAPACITOR", Material.RED_STAINED_GLASS, TextUtil.colorPseudorandomString("基础耗电减免电容"));
    public static final SlimefunItemStack SMALL_EXPANDED_CAPACITOR = new SlimefunItemStack("FINALTECH_SMALL_EXPANDED_CAPACITOR", Material.YELLOW_STAINED_GLASS, TextUtil.colorPseudorandomString("小型扩展电容"));
    public static final SlimefunItemStack MEDIUM_EXPANDED_CAPACITOR = new SlimefunItemStack("FINALTECH_MEDIUM_EXPANDED_CAPACITOR", Material.YELLOW_STAINED_GLASS, TextUtil.colorPseudorandomString("中型扩展电容"));
    public static final SlimefunItemStack BIG_EXPANDED_CAPACITOR = new SlimefunItemStack("FINALTECH_BIG_EXPANDED_CAPACITOR", Material.YELLOW_STAINED_GLASS, TextUtil.colorPseudorandomString("大型扩展电容"));
    public static final SlimefunItemStack LARGE_EXPANDED_CAPACITOR = new SlimefunItemStack("FINALTECH_LARGE_EXPANDED_CAPACITOR", Material.YELLOW_STAINED_GLASS, TextUtil.colorPseudorandomString("巨型扩展电容"));
    public static final SlimefunItemStack CARBONADO_EXPANDED_CAPACITOR = new SlimefunItemStack("FINALTECH_CARBONADO_EXPANDED_CAPACITOR", Material.YELLOW_STAINED_GLASS, TextUtil.colorPseudorandomString("黑金刚石扩展电容"));
    public static final SlimefunItemStack ENERGIZED_EXPANDED_CAPACITOR = new SlimefunItemStack("FINALTECH_ENERGIZED_EXPANDED_CAPACITOR", Material.YELLOW_STAINED_GLASS, TextUtil.colorPseudorandomString("终极扩展电容"));
    public static final SlimefunItemStack ENERGIZED_STACK_EXPANDED_CAPACITOR = new SlimefunItemStack("FINALTECH_ENERGIZED_STACK_EXPANDED_CAPACITOR", Material.YELLOW_STAINED_GLASS, TextUtil.colorPseudorandomString("终极扩展电容组"));
    public static final SlimefunItemStack OVERLOADED_EXPANDED_CAPACITOR = new SlimefunItemStack("FINALTECH_OVERLOADED_EXPANDED_CAPACITOR", Material.YELLOW_STAINED_GLASS, TextUtil.colorPseudorandomString("过载扩展电容"));
    public static final SlimefunItemStack ADVANCED_CHARGE_INCREASE_CAPACITOR = new SlimefunItemStack("FINALTECH_ADVANCED_CHARGE_INCREASE_CAPACITOR", Material.RED_STAINED_GLASS, TextUtil.colorPseudorandomString("高级充电增益电容"));
    public static final SlimefunItemStack ADVANCED_CONSUME_REDUCE_CAPACITOR = new SlimefunItemStack("FINALTECH_ADVANCED_CONSUME_REDUCE_CAPACITOR", Material.RED_STAINED_GLASS, TextUtil.colorPseudorandomString("高级耗电减免电容"));
    public static final SlimefunItemStack ESCAPE_CAPACITOR = new SlimefunItemStack("FINALTECH_ESCAPE_CAPACITOR", Material.GREEN_STAINED_GLASS, TextUtil.colorPseudorandomString("逸散电容"));
    public static final SlimefunItemStack NORMAL_ELECTRICITY_SHOOT_PILE = new SlimefunItemStack("FINALTECH_NORMAL_ELECTRICITY_SHOOT_PILE", Material.DISPENSER, TextUtil.colorPseudorandomString("普通射电桩"));
    public static final SlimefunItemStack ENERGIZED_ELECTRICITY_SHOOT_PILE = new SlimefunItemStack("FINALTECH_ENERGIZED_ELECTRICITY_SHOOT_PILE", Material.DISPENSER, TextUtil.colorPseudorandomString("充能射电桩"));
    public static final SlimefunItemStack OVERLOADED_ELECTRICITY_SHOOT_PILE = new SlimefunItemStack("FINALTECH_EXCESS_LOAD_ELECTRICITY_SHOOT_PILE", Material.DISPENSER, TextUtil.colorPseudorandomString("过载射电桩"));
    public static final SlimefunItemStack ORDERED_DUST_GENERATOR = new SlimefunItemStack("FINALTECH_ORDERED_DUST_GENERATOR", Material.BROWN_MUSHROOM_BLOCK, TextUtil.colorPseudorandomString("尘埃发电机"));
    public static final SlimefunItemStack BASIC_GENERATOR = new SlimefunItemStack("FINALTECH_BASIC_GENERATOR", Material.GLOWSTONE, TextUtil.colorPseudorandomString("基础供电机"));
    public static final SlimefunItemStack ADVANCED_GENERATOR = new SlimefunItemStack("FINALTECH_ADVANCED_GENERATOR", Material.GLOWSTONE, TextUtil.colorPseudorandomString("高级供电机"));
    public static final SlimefunItemStack CARBONADO_GENERATOR = new SlimefunItemStack("FINALTECH_CARBONADO_GENERATOR", Material.GLOWSTONE, TextUtil.colorPseudorandomString("黑金刚石供电机"));
    public static final SlimefunItemStack ENERGIZED_GENERATOR = new SlimefunItemStack("FINALTECH_ENERGIZED_GENERATOR", Material.GLOWSTONE, TextUtil.colorPseudorandomString("充能供电机"));
    public static final SlimefunItemStack ENERGIZED_STACK_GENERATOR = new SlimefunItemStack("FINALTECH_ENERGIZED_STACK_GENERATOR", Material.GLOWSTONE, TextUtil.colorPseudorandomString("充能供电机组"));
    public static final SlimefunItemStack OVERLOADED_GENERATOR = new SlimefunItemStack("FINALTECH_OVERLOADED_GENERATOR", Material.GLOWSTONE, TextUtil.colorPseudorandomString("过载供电机"));
    public static final SlimefunItemStack ENERGIZED_ACCELERATOR = new SlimefunItemStack("FINALTECH_ENERGY_ACCELERATOR", Material.TARGET, TextUtil.colorPseudorandomString("充能加速器"));
    public static final SlimefunItemStack OVERLOADED_ACCELERATOR = new SlimefunItemStack("FINALTECH_OVERLOADED_ACCELERATOR", Material.TARGET, TextUtil.colorPseudorandomString("过载加速器"));

    // function machine
    public static final SlimefunItemStack ORDERED_DUST_FACTORY_DIRT = new SlimefunItemStack("FINALTECH_ORDERED_DUST_FACTORY_DIRT", Material.DIRT, TextUtil.colorPseudorandomString("尘埃制造机"));
    public static final SlimefunItemStack ORDERED_DUST_FACTORY_STONE = new SlimefunItemStack("FINALTECH_ORDERED_DUST_FACTORY_STONE", Material.COBBLESTONE, TextUtil.colorPseudorandomString("尘埃制造机"));
    public static final SlimefunItemStack ITEM_SERIALIZATION_CONSTRUCTOR = new SlimefunItemStack("FINALTECH_ITEM_SERIALIZATION_CONSTRUCTOR", Material.AMETHYST_BLOCK, TextUtil.colorPseudorandomString("序列化构造器"));
    public static final SlimefunItemStack ITEM_DESERIALIZE_PARSER = new SlimefunItemStack("FINALTECH_ITEM_DESERIALIZE_PARSER", Material.BUDDING_AMETHYST, TextUtil.colorPseudorandomString("反序列化解析器"));
    public static final SlimefunItemStack MATRIX_CRAFTING_TABLE = new SlimefunItemStack("FINALTECH_MATRIX_CRAFTING_TABLE", Material.CONDUIT, TextUtil.colorPseudorandomString("矩阵合成台"));
    public static final SlimefunItemStack CARD_OPERATION_PORT = new SlimefunItemStack("FINALTECH_CARD_OPERATION_PORT", Material.CARTOGRAPHY_TABLE, TextUtil.colorPseudorandomString("物品卡操作台"));
    public static final SlimefunItemStack ITEM_DISMANTLE_TABLE = new SlimefunItemStack("FINALTECH_ITEM_DISMANTLE_TABLE", Material.CUT_COPPER, TextUtil.colorPseudorandomString("反向合成台"));
    public static final SlimefunItemStack EQUIVALENT_EXCHANGE_TABLE = new SlimefunItemStack("FINALTECH_EQUIVALENT_EXCHANGE_TABLE", Material.REDSTONE_LAMP, TextUtil.colorPseudorandomString("等价交换台"));
    public static final SlimefunItemStack ENTROPY_CONSTRUCTOR = new SlimefunItemStack("FINALTECH_ENTROPY_CONSTRUCTOR", Material.INFESTED_STONE, "§x§2§2§2§2§2§2熵化构造器");

    // basic machines
    public static final SlimefunItemStack BASIC_COBBLE_FACTORY = new SlimefunItemStack("FINALTECH_BASIC_COBBLE_FACTORY", Material.CHISELED_STONE_BRICKS, TextUtil.colorPseudorandomString("基础刷石工厂"));
    public static final SlimefunItemStack BASIC_ORE_FACTORY = new SlimefunItemStack("FINALTECH_BASIC_ORE_FACTORY", Material.CHISELED_DEEPSLATE, TextUtil.colorPseudorandomString("基础矿石工厂"));
    public static final SlimefunItemStack BASIC_DUST_FACTORY = new SlimefunItemStack("FINALTECH_BASIC_DUST_FACTORY", Material.POLISHED_DEEPSLATE, TextUtil.colorPseudorandomString("基础矿粉工厂"));
    public static final SlimefunItemStack BASIC_FARM_FACTORY = new SlimefunItemStack("FINALTECH_BASIC_FARM_FACTORY", Material.MOSS_BLOCK, TextUtil.colorPseudorandomString("基础作物工厂"));
    public static final SlimefunItemStack BASIC_LIQUID_FACTORY = new SlimefunItemStack("FINALTECH_BASIC_LIQUID_FACTORY", Material.BLUE_TERRACOTTA, TextUtil.colorPseudorandomString("基础流体工厂"));
    public static final SlimefunItemStack MANUAL_CRAFTING_TABLE = new SlimefunItemStack("FINALTECH_MANUAL_CRAFTING_TABLE", Material.CRAFTING_TABLE, TextUtil.colorPseudorandomString("快捷原版工作台"));
    public static final SlimefunItemStack MANUAL_ENHANCED_CRAFTING_TABLE = new SlimefunItemStack("FINALTECH_MANUAL_ENHANCED_CRAFTING_TABLE", Material.CRAFTING_TABLE, TextUtil.colorPseudorandomString("快捷增强型工作台"));
    public static final SlimefunItemStack MANUAL_GRIND_STONE = new SlimefunItemStack("FINALTECH_MANUAL_GRIND_STONE", Material.DISPENSER, TextUtil.colorPseudorandomString("快捷磨石"));
    public static final SlimefunItemStack MANUAL_ARMOR_FORGE = new SlimefunItemStack("FINALTECH_MANUAL_ARMOR_FORGE", Material.IRON_BLOCK, TextUtil.colorPseudorandomString("快捷盔甲锻造台"));
    public static final SlimefunItemStack MANUAL_ORE_CRUSHER = new SlimefunItemStack("FINALTECH_MANUAL_ORE_CRUSHER", Material.DROPPER, TextUtil.colorPseudorandomString("快捷矿石粉碎机"));
    public static final SlimefunItemStack MANUAL_COMPRESSOR = new SlimefunItemStack("FINALTECH_MANUAL_COMPRESSOR", Material.PISTON, TextUtil.colorPseudorandomString("快捷压缩机"));
    public static final SlimefunItemStack MANUAL_SMELTERY = new SlimefunItemStack("FINALTECH_MANUAL_SMELTERY", Material.BLAST_FURNACE, TextUtil.colorPseudorandomString("快捷冶炼炉"));
    public static final SlimefunItemStack MANUAL_PRESSURE_CHAMBER = new SlimefunItemStack("FINALTECH_MANUAL_PRESSURE_CHAMBER", Material.STICKY_PISTON, TextUtil.colorPseudorandomString("快捷压力机"));
    public static final SlimefunItemStack MANUAL_MAGIC_WORKBENCH = new SlimefunItemStack("FINALTECH_MANUAL_MAGIC_WORKBENCH", Material.BOOKSHELF, TextUtil.colorPseudorandomString("快捷魔法工作台"));
    public static final SlimefunItemStack MANUAL_ORE_WASHER = new SlimefunItemStack("FINALTECH_MANUAL_ORE_WASHER", Material.BLUE_STAINED_GLASS, TextUtil.colorPseudorandomString("快捷洗矿机"));
    public static final SlimefunItemStack MANUAL_COMPOSTER = new SlimefunItemStack("FINALTECH_MANUAL_COMPOSTER", Material.CAULDRON, TextUtil.colorPseudorandomString("快捷搅拌机"));
    public static final SlimefunItemStack MANUAL_GOLD_PAN = new SlimefunItemStack("FINALTECH_MANUAL_GOLD_PAN", Material.BROWN_TERRACOTTA, TextUtil.colorPseudorandomString("快捷淘金机"));
    public static final SlimefunItemStack MANUAL_CRUCIBLE = new SlimefunItemStack("FINALTECH_CRUCIBLE", Material.RED_TERRACOTTA, TextUtil.colorPseudorandomString("快捷坩埚"));
    public static final SlimefunItemStack MANUAL_JUICER = new SlimefunItemStack("FINALTECH_MANUAL_JUICER", Material.GLASS, TextUtil.colorPseudorandomString("快捷榨汁机"));
    public static final SlimefunItemStack MANUAL_ANCIENT_ALTAR = new SlimefunItemStack("FINALTECH_MANUAL_ANCIENT_ALTAR", Material.ENCHANTING_TABLE, TextUtil.colorPseudorandomString("快捷古代祭坛"));
    public static final SlimefunItemStack MANUAL_HEATED_PRESSURE_CHAMBER = new SlimefunItemStack("FINALTECH_MANUAL_HEATED_PRESSURE_CHAMBER", Material.LIGHT_GRAY_STAINED_GLASS, TextUtil.colorPseudorandomString("快捷加热压力舱"));

    // advanced machines
    public static final SlimefunItemStack ADVANCED_COMPOSTER = new SlimefunItemStack("FINALTECH_COMPOSTER", Material.CAULDRON, TextUtil.colorPseudorandomString("高级搅拌机"));
    public static final SlimefunItemStack ADVANCED_JUICER = new SlimefunItemStack("FINALTECH_JUICER", Material.GLASS, TextUtil.colorPseudorandomString("高级榨汁机"));
    public static final SlimefunItemStack ADVANCED_ELECTRIC_FURNACE = new SlimefunItemStack("FINALTECH_ADVANCED_ELECTRIC_FURNACE", Material.FURNACE, TextUtil.colorPseudorandomString("高级电炉"));
    public static final SlimefunItemStack ADVANCED_GOLD_PAN = new SlimefunItemStack("FINALTECH_ADVANCED_GOLD_PAN", Material.BROWN_TERRACOTTA, TextUtil.colorPseudorandomString("高级淘金机"));
    public static final SlimefunItemStack ADVANCED_ELECTRIC_DUST_WASHER = new SlimefunItemStack("FINALTECH_ADVANCED_DUST_WASHER", Material.BLUE_STAINED_GLASS, TextUtil.colorPseudorandomString("高级洗矿机"));
    public static final SlimefunItemStack ADVANCED_ELECTRIC_INGOT_FACTORY = new SlimefunItemStack("FINALTECH_ADVANCED_ELECTRIC_INGOT_FACTORY", Material.RED_TERRACOTTA, TextUtil.colorPseudorandomString("高级铸锭机"));
    public static final SlimefunItemStack ADVANCED_ELECTRIC_CRUCIBLE = new SlimefunItemStack("FINALTECH_ADVANCED_ELECTRIC_CRUCIBLE", Material.RED_TERRACOTTA, TextUtil.colorPseudorandomString("高级坩埚"));
    public static final SlimefunItemStack ADVANCED_ELECTRIC_ORE_GRINDER = new SlimefunItemStack("FINALTECH_ADVANCED_ELECTRIC_ORE_GRINDER", Material.FURNACE, TextUtil.colorPseudorandomString("高级碎矿机"));
    public static final SlimefunItemStack ADVANCED_HEATED_PRESSURE_CHAMBER = new SlimefunItemStack("FINALTECH_ADVANCED_HEATED_PRESSURE_CHAMBER", Material.LIGHT_GRAY_STAINED_GLASS, TextUtil.colorPseudorandomString("高级加热压力仓"));
    public static final SlimefunItemStack ADVANCED_ELECTRIC_INGOT_PULVERIZER = new SlimefunItemStack("FINALTECH_ADVANCED_ELECTRIC_INGOT_PULVERIZER", Material.FURNACE, TextUtil.colorPseudorandomString("高级打粉机"));
    public static final SlimefunItemStack ADVANCED_AUTO_DRIER = new SlimefunItemStack("FINALTECH_ADVANCED_AUTO_DRIER", Material.SMOKER, TextUtil.colorPseudorandomString("高级烘干机"));
    public static final SlimefunItemStack ADVANCED_ELECTRIC_PRESS = new SlimefunItemStack("FINALTECH_ADVANCED_ELECTRIC_PRESS", new SlimefunItemStack("", HeadTexture.ELECTRIC_PRESS, "", ""), TextUtil.colorPseudorandomString("高级压缩机"));
    public static final SlimefunItemStack ADVANCED_FOOD_FACTORY = new SlimefunItemStack("FINALTECH_ADVANCED_FOOD_FACTORY", Material.GREEN_TERRACOTTA, TextUtil.colorPseudorandomString("高级作物加工厂"));
    public static final SlimefunItemStack ADVANCED_FREEZER = new SlimefunItemStack("FINALTECH_ADVANCED_FREEZER", Material.LIGHT_BLUE_STAINED_GLASS, TextUtil.colorPseudorandomString("高级冰箱"));
    public static final SlimefunItemStack ADVANCED_CARBON_PRESS = new SlimefunItemStack("FINALTECH_ADVANCED_CARBON_PRESS", Material.BLACK_STAINED_GLASS, TextUtil.colorPseudorandomString("高级碳压机"));
    public static final SlimefunItemStack ADVANCED_ELECTRIC_SMELTERY = new SlimefunItemStack("FINALTECH_ADVANCED_ELECTRIC_SMELTERY", Material.FURNACE, TextUtil.colorPseudorandomString("高级冶炼炉"));
    public static final SlimefunItemStack ADVANCED_ORE_FACTORY = new SlimefunItemStack("FINALTECH_ADVANCED_ORE_FACTORY", Material.PURPUR_PILLAR, TextUtil.colorPseudorandomString("高级矿石工厂"));
    public static final SlimefunItemStack ADVANCED_DUST_FACTORY = new SlimefunItemStack("FINALTECH_ADVANCED_DUST_FACTORY", Material.RED_NETHER_BRICKS, TextUtil.colorPseudorandomString("高级矿粉工厂"));
    public static final SlimefunItemStack ADVANCED_FARM_FACTORY = new SlimefunItemStack("FINALTECH_ADVANCED_FARM_FACTORY", new ItemStack(Material.MOSS_BLOCK), TextUtil.colorPseudorandomString("高级作物工厂"));

    // best item
    public static final SlimefunItemStack MACHINE_CHARGE_CARD_INFINITY = new SlimefunItemStack("FINALTECH_MACHINE_CHARGE_CARD_INFINITY", Material.PAPER, TextUtil.colorPseudorandomString("无限充能卡"));
    public static final SlimefunItemStack MACHINE_ACCELERATE_CARD_INFINITY = new SlimefunItemStack("FINALTECH_MACHINE_ACCELERATE_CARD_INFINITY", Material.PAPER, TextUtil.colorPseudorandomString("无限过载卡"));
    public static final SlimefunItemStack MACHINE_ACTIVATE_CARD_L4 = new SlimefunItemStack("FINALTECH_MACHINE_ACTIVATE_CARD_L4", Material.PAPER, TextUtil.colorPseudorandomString("带电超频卡L4"));
    public static final SlimefunItemStack ADVANCED_AUTO_CRAFT = new SlimefunItemStack("FINALTECH_ADVANCED_AUTO_CRAFT", Material.BEACON, TextUtil.colorPseudorandomString("高级自动合成机"));
    public static final SlimefunItemStack MATRIX_EXPANDED_CAPACITOR = new SlimefunItemStack("FINALTECH_MATRIX_EXPANDED_CAPACITOR", Material.YELLOW_STAINED_GLASS, TextUtil.colorPseudorandomString("矩阵电容"));
    public static final SlimefunItemStack MATRIX_GENERATOR = new SlimefunItemStack("FINALTECH_MATRIX_GENERATOR", Material.SEA_LANTERN, TextUtil.colorPseudorandomString("矩阵供电机"));
    public static final SlimefunItemStack MATRIX_ACCELERATOR = new SlimefunItemStack("FINALTECH_MATRIX_ACCELERATOR", Material.TARGET, TextUtil.colorPseudorandomString("矩阵加速器"));
    public static final SlimefunItemStack MATRIX_REACTOR = new SlimefunItemStack("FINALTECH_MATRIX_REACTOR", Material.COAL_BLOCK, TextUtil.colorPseudorandomString("尘埃反应堆"));

//    //TODO
//    // weapon
//    public static final SlimefunItemStack DUST_SWORD = new SlimefunItemStack("FINALTECH_DUST_SWORD", Material.WOODEN_SWORD, TextHelper.colorPseudorandomString("尘埃剑"));
//    public static final SlimefunItemStack DUST_ANNULAR = new SlimefunItemStack("FINALTECH_DUST_ANNULAR", Material.WOODEN_SWORD, TextHelper.colorPseudorandomString("尘埃环"));
//
//
}
