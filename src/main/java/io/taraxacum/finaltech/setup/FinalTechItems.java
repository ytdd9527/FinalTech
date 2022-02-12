package io.taraxacum.finaltech.setup;

import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.utils.HeadTexture;
import io.taraxacum.finaltech.cargo.Pipe;
import io.taraxacum.finaltech.electric.capacitor.*;
import io.taraxacum.finaltech.machine.AllCompression;
import io.taraxacum.finaltech.machine.UnOrderedDustFactory;
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
    public static final SlimefunItemStack QUANTITY_MODULE = new SlimefunItemStack("FINALTECH_QUANTITY_MODULE", Material.AMETHYST_SHARD, "&3数量组件");
    public static final SlimefunItemStack SINGULARITY = new SlimefunItemStack("FINALTECH_SINGULARITY", Material.NETHER_STAR, "&e奇点",
            "&7纯粹意义上的奇点",
            "&d数量&7的堆积物");
    public static final SlimefunItemStack BUG = new SlimefunItemStack("FINALTECH_BUG", Material.INFESTED_COBBLESTONE, "&8BUG",
            "&8??!");
    public static final SlimefunItemStack CODE_ADDITION = new SlimefunItemStack("FINALTECH_CODE_ADDITION0", Material.PAPER, "&b概念<加法>");
    public static final SlimefunItemStack CODE_SYMMETRY = new SlimefunItemStack("FINALTECH_CODE_SYMMETRY", Material.PAPER, "&b概念<对称>");
    public static final SlimefunItemStack CODE_RANDOM = new SlimefunItemStack("FINALTECH_CODE_RANDOM", Material.PAPER, "&b概念<随机>");
    public static final SlimefunItemStack CODE_CYCLE = new SlimefunItemStack("FINALTECH_CODE_CYCLE", Material.PAPER, "&b概念<循环>");
    public static final SlimefunItemStack CODE_REVERSE = new SlimefunItemStack("FINALTECH_CODE_REVERSE", Material.PAPER, "&b概念<逆向>");
    public static final SlimefunItemStack CODE_INFINITE = new SlimefunItemStack("FINALTECH_CODE_INFINITE", Material.PAPER, "&b概念<无限>");
    public static final SlimefunItemStack CODE_NULL = new SlimefunItemStack("FINALTECH_CODE_NULL", Material.PAPER, "&b概念<空值>");
    public static final SlimefunItemStack CODE_CREATE = new SlimefunItemStack("FINALTECH_CODE_CREATE", Material.PAPER, "&9高级概念<创造>",
            "&7繁荣的基础");
//    public static final SlimefunItemStack ORDERED_DUST = new SlimefunItemStack("FINALTECH_ORDERED_DUST", Material.);
    public static final SlimefunItemStack CODE_FINAL = new SlimefunItemStack("FINALTECH_CODE_FINAL", Material.BOOK, "&f概念<Final>",
        "&7无数概念交织在一起",
        "&7于是他们无法再创造新事物");

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
    public static final SlimefunItemStack UNORDERED_DUST_FACTORY = new SlimefunItemStack("FINALTECH_ORDERED_DUST_FACTORY", Material.DIRT, "&7无序尘埃制造机",
            "",
            "&7输入 " + UnOrderedDustFactory.getMatchDifficulty() + " &d种&7不同的物品",
            "&7输入共 " + UnOrderedDustFactory.getInputDifficulty() + " &d个&7物品",
            "&7从而产生一个&f无序尘埃");

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
    public static final SlimefunItemStack ADVANCED_ELECTRIC_PRESS = new SlimefunItemStack("FINALTECH_ADVANCED_ELECTRIC_PRESS", new SlimefunItemStack("", HeadTexture.ELECTRIC_PRESS, new String(), new String()), "&c高级压缩机");
    public static final SlimefunItemStack ADVANCED_FOOD_FACTORY = new SlimefunItemStack("FINALTECH_ADVANCED_FOOD_FACTORY", Material.GREEN_TERRACOTTA, "&c高级作物加工厂");
    public static final SlimefunItemStack ADVANCED_FREEZER = new SlimefunItemStack("FINALTECH_ADVANCED_FREEZER", Material.LIGHT_BLUE_STAINED_GLASS, "&c高级冰箱");
    public static final SlimefunItemStack ADVANCED_CARBON_PRESS = new SlimefunItemStack("FINALTECH_ADVANCED_CARBON_PRESS", Material.BLACK_STAINED_GLASS, "&c高级碳压机");
    public static final SlimefunItemStack ADVANCED_ELECTRIC_SMELTERY = new SlimefunItemStack("FINALTECH_ADVANCED_ELECTRIC_SMELTERY", Material.FURNACE, "&c高级冶炼炉");
    public static final SlimefunItemStack ADVANCED_ORE_FACTORY = new SlimefunItemStack("FINALTECH_ADVANCED_ORE_FACTORY", Material.PURPUR_PILLAR, "&c高级矿石工厂");
    public static final SlimefunItemStack ADVANCED_DUST_FACTORY = new SlimefunItemStack("FINALTECH_ADVANCED_DUST_FACTORY", Material.RED_NETHER_BRICKS, "&c高级矿粉工厂");
    public static final SlimefunItemStack ADVANCED_FARM_FACTORY = new SlimefunItemStack("FINALTECH_ADVANCED_FARM_FACTORY", new ItemStack(Material.MOSS_BLOCK), "&c高级作物工厂");
    public static final SlimefunItemStack ADVANCED_AUTO_CRAFT = new SlimefunItemStack("FINALTECH_ADVANCED_AUTO_CRAFT", Material.BEACON, "&c高级自动合成机",
            "&7自动合成大部分粘液科技物品",
            "&7支持多级合成");

    // best machines
    public static final SlimefunItemStack ALL_COMPRESSION = new SlimefunItemStack("FINALTECH_ALL_COMPRESSION", Material.AMETHYST_BLOCK, "&9万物压缩器",
            "",
            "&7输入" + AllCompression.DIFFICULTY + "个相同物品",
            "&7从而制成&4带有特定签名的&7物品");
    public static final SlimefunItemStack ALL_FACTORY = new SlimefunItemStack("FINALTECH_ALL_FACTORY", Material.BUDDING_AMETHYST, "&9万物工厂",
            "",
            "&7在中间放入&4带有特定签名的&7物品",
            "&7从而 无 限 复 制 该物品",
            "&8支持放入堆叠的物品，从而一次复制多个");
    public static final SlimefunItemStack OVERCLOCK_FRAME_MACHINE = new SlimefunItemStack("FINALTECH_OVERCLOCK_FRAME_MACHINE", Material.LECTERN, "&9超频框架",
            "&7当放入相同的机器物品时",
            "&7根据放入物品的数量",
            "&7使其下方的机器工作效率提升",
            "&6最大可存储电量 &e" + 536870912 + "J",
            "&8这也会同时提升其耗电量",
            "&8仅当放入的物品数量大于1个时生效");

    // cargos
    public static final SlimefunItemStack BASIC_FRAME_MACHINE = new SlimefunItemStack("FINALTECH_BASIC_FRAME_MACHINE", Material.STONE, "&f基础机器框架", "&8可放置");
    public static final SlimefunItemStack BASIC_NORMAL_STORAGE_UNIT = new SlimefunItemStack("FINALTECH_BASIC_NORMAL_STORAGE_UNIT", Material.GLASS, "&f普通存储单元", "&7可通过粘液货运进行交互", "&8其实就是更大的桶");
    public static final SlimefunItemStack BASIC_LINKED_STORAGE_UNIT = new SlimefunItemStack("FINALTECH_BASIC_LINKED_STORAGE_UNIT", Material.GLASS, "&f可识别存储单元", "&7上面三排被识别为输入槽", "&7下面三排被识别为输出槽");
    public static final SlimefunItemStack BASIC_CHARGEABLE_STORAGE_UNIT = new SlimefunItemStack("FINALTECH_BASIC_CHARGEABLE_STORAGE_UNIT", Material.GLASS, "&f可充电存储单元",
            "&7该机器充电后自身并不会产生任何效果",
            "&6最大可存储电量 &e" + (536870912) + "J");
    public static final SlimefunItemStack PIPE = new SlimefunItemStack("FINALTECH_CARGO_PIPE", Material.END_ROD, "&f导管",
            "",
            "&7把物品从一个容器中运输到另一个容器中",
            "&7最大可以直线传输" + Pipe.BLOCK_SEARCH_LIMIT + "格距离",
            "&7需设置搜索模式并通过指向另一个导管或同材质物品以延长其搜索范围",
            "&8即放即用");
    public static final SlimefunItemStack TRANSFER_STATION = new SlimefunItemStack("FINALTECH_CARGO_TRANSFER_STATION", Material.TINTED_GLASS, "&f中转器",
            "",
            "&7把自身缓存中的物品输出到指定的输出方向的容器",
            "&7从指定的输入方向的容器输入物品至自身的缓存中",
            "&7设置搜索模式后，可以通过邻接锁链或中转器正向地无限延长其搜索范围");

    // electric
    public static final SlimefunItemStack BASIC_CHARGE_INCREASE_CAPACITOR = new SlimefunItemStack("FINALTECH_BASIC_CHARGE_INCREASE_CAPACITOR", Material.RED_STAINED_GLASS, "&7基础充电增益电容",
            "&7存储电量为空时，提高充电效率至" + BasicChargeIncreaseCapacitor.EFFICIENT * 100 + "%",
            "&7当自身电量充满时，充电效率降至 0%",
            "&7充电效率随自身存储电量百分比线性变化",
            "&6最大可存储电量 &e" + BasicChargeIncreaseCapacitor.CAPACITOR + "J");
    public static final SlimefunItemStack BASIC_CONSUME_REDUCE_CAPACITOR = new SlimefunItemStack("FINALTECH_BASIC_CONSUME_REDUCE_CAPACITOR", Material.RED_STAINED_GLASS, "&7基础耗电减免电容",
            "&7存储电量为空时，耗电效率降至 " + (100 / (double) BasicConsumeReduceCapacitor.EFFICIENT) + "%",
            "&7耗电效率随存储电量提高而提高",
            "&7蓄电满时耗电效率升至 " + BasicConsumeReduceCapacitor.EFFICIENT * 100 + "%",
            "&6最大可存储电量 &e" + BasicConsumeReduceCapacitor.CAPACITOR + "J");
    public static final SlimefunItemStack BASIC_SELF_GENERATE_CAPACITOR = new SlimefunItemStack("FINALTECH_BASIC_SELF_GENERATE_CAPACITOR", Material.RED_STAINED_GLASS, "&7基础自发电电容",
            "&7每粘液刻，使自身电量增长存储电量的 1/" + BasicSelfGenerateCapacitor.EFFICIENT,
            "&6最大可存储电量 &e" + BasicSelfGenerateCapacitor.CAPACITOR + "J");
    public static final SlimefunItemStack BASIC_VOID_GENERATE_CAPACITOR = new SlimefunItemStack("FINALTECH_BASIC_VOID_GENERATE_CAPACITOR", Material.RED_STAINED_GLASS, "&7基础空发电电容",
            "&7存储电量为空时，立即为自己充电，充电量为自身最大存储电量的的 1/" + BasicVoidGenerateCapacitor.EFFICIENT,
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

    public static final SlimefunItemStack BASIC_GENERATOR = new SlimefunItemStack("FINALTECH_BASIC_GENERATOR", Material.GLOWSTONE, "&2基础供电机",
            "&e供电量 1J");
    public static final SlimefunItemStack ADVANCED_GENERATOR = new SlimefunItemStack("FINALTECH_ADVANCED_GENERATOR", Material.GLOWSTONE, "&2高级供电机",
            "&e供电量 4J");
    public static final SlimefunItemStack REINFORCED_GENERATOR = new SlimefunItemStack("FINALTECH_REINFORCED_GENERATOR", Material.GLOWSTONE, "&2强化合金供电机",
            "&e供电量 16J");
    public static final SlimefunItemStack CARBONADO_GENERATOR = new SlimefunItemStack("FINALTECH_CARBONADO_GENERATOR", Material.GLOWSTONE, "&2黑金刚石供电机",
            "&e供电量 64J");
    public static final SlimefunItemStack ENERGIZED_GENERATOR = new SlimefunItemStack("FINALTECH_ENERGIZED_GENERATOR", Material.GLOWSTONE, "&2充能供电机",
            "&e供电量 512J");
    public static final SlimefunItemStack MATRIX_GENERATOR = new SlimefunItemStack("FINALTECH_MATRIX_GENERATOR", Material.SEA_LANTERN, "&2矩阵供电机",
            "&e供电量 4096J");
}
