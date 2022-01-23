package io.taraxacum.finaltech.setup;

import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.utils.HeadTexture;
import io.taraxacum.finaltech.machine.AllCompression;
import io.taraxacum.finaltech.machine.UnOrderedDustFactory;
import io.taraxacum.finaltech.util.CargoUtil;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public final class FinalTechItems {

    // items
    public static final SlimefunItemStack QUANTITY_MODULE = new SlimefunItemStack("FINALTECH_QUANTITY_MODULE", Material.AMETHYST_SHARD, "&3数量组件");
    public static final SlimefunItemStack UNORDERED_DUST = new SlimefunItemStack("FINALTECH_UNORDERED_DUST", Material.WHEAT_SEEDS, "&f无序尘埃",
            "&8它被感知到了");

    // basic machines
    public static final SlimefunItemStack BASIC_COBBLE_FACTORY = new SlimefunItemStack("FINALTECH_BASIC_COBBLE_FACTORY", Material.CHISELED_STONE_BRICKS, "&7基础刷石工厂");
    public static final SlimefunItemStack BASIC_ORE_FACTORY = new SlimefunItemStack("FINALTECH_BASIC_ORE_FACTORY", Material.CHISELED_DEEPSLATE, "&7基础矿石工厂");
    public static final SlimefunItemStack BASIC_DUST_FACTORY = new SlimefunItemStack("FINALTECH_BASIC_DUST_FACTORY", Material.POLISHED_DEEPSLATE, "&7基础矿粉工厂");
    public static final SlimefunItemStack BASIC_FARM_FACTORY = new SlimefunItemStack("FINALTECH_BASIC_FARM_FACTORY", Material.MOSS_BLOCK, "&7基础作物工厂");

    // advanced machines
    public static final SlimefunItemStack UNORDERED_DUST_FACTORY = new SlimefunItemStack("FINALTECH_ORDERED_DUST_FACTORY", Material.DIRT, "&7无序尘埃制造机",
            "&8输入" + UnOrderedDustFactory.getMatchDifficulty() + "种不同的物品",
            "&8输入共" + UnOrderedDustFactory.getInputDifficulty() + "个物品",
            "&8从而产生一个无序尘埃");
    public static final SlimefunItemStack ADVANCED_AUTO_DRIER = new SlimefunItemStack("FINALTECH_ADVANCED_AUTO_DRIER", Material.SMOKER, "&6高级烘干机");
    public static final SlimefunItemStack ADVANCED_CARBON_PRESS = new SlimefunItemStack("FINALTECH_ADVANCED_CARBON_PRESS", Material.BLACK_STAINED_GLASS, "&7高级碳压机");
    public static final SlimefunItemStack ADVANCED_DUST_FACTORY = new SlimefunItemStack("FINALTECH_ADVANCED_DUST_FACTORY", Material.RED_NETHER_BRICKS, "&c高级矿粉工厂");
    public static final SlimefunItemStack ADVANCED_ELECTRIC_CRUCIBLE = new SlimefunItemStack("FINALTECH_ADVANCED_ELECTRIC_CRUCIBLE", Material.RED_TERRACOTTA, "&c高级坩埚");
    public static final SlimefunItemStack ADVANCED_ELECTRIC_DUST_WASHER = new SlimefunItemStack("FINALTECH_ADVANCED_DUST_WASHER", Material.BLUE_STAINED_GLASS, "&3高级洗矿机");
    public static final SlimefunItemStack ADVANCED_ELECTRIC_FURANCE = new SlimefunItemStack("FINALTECH_ADVANCED_ELECTRIC_FURANCE", Material.FURNACE, "&c高级电炉");
    public static final SlimefunItemStack ADVANCED_ELECTRIC_INGOT_FACTORY = new SlimefunItemStack("FINALTECH_ADVANCED_ELECTRIC_INGOT_FACTORY", Material.RED_TERRACOTTA, "&c高级铸锭机");
    public static final SlimefunItemStack ADVANCED_ELECTRIC_INGOT_PULVERIZER = new SlimefunItemStack("FINALTECH_ADVANCED_ELECTRIC_INGOT_PULVERIZER", Material.FURNACE, "&c高级打粉机");
    public static final SlimefunItemStack ADVANCED_ELECTRIC_ORE_GRINDER = new SlimefunItemStack("FINALTECH_ADVANCED_ELECTRIC_ORE_GRINDER", Material.FURNACE, "&c高级碎矿机");
    public static final SlimefunItemStack ADVANCED_ELECTRIC_PRESS = new SlimefunItemStack("FINALTECH_ADVANCED_ELECTRIC_PRESS", new SlimefunItemStack("", HeadTexture.ELECTRIC_PRESS, new String(), new String()), "&e高级压缩机");
    public static final SlimefunItemStack ADVANCED_ELECTRIC_SMELTERY = new SlimefunItemStack("FINALTECH_ADVANCED_ELECTRIC_SMELTERY", Material.FURNACE, "&c高级冶炼炉");
    public static final SlimefunItemStack ADVANCED_FARM_FACTORY = new SlimefunItemStack("FINALTECH_ADVANCED_FARM_FACTORY", new ItemStack(Material.MOSS_BLOCK), "&7高级作物工厂");
    public static final SlimefunItemStack ADVANCED_FOOD_FACTORY = new SlimefunItemStack("FINALTECH_FOOD_FACTORY", Material.GREEN_TERRACOTTA, "&c高级作物加工厂");
    public static final SlimefunItemStack ADVANCED_FREEZER = new SlimefunItemStack("FINALTECH_FREEZER", Material.LIGHT_BLUE_STAINED_GLASS, "&b高级冰箱");
    public static final SlimefunItemStack ADVANCED_GOLD_PAN = new SlimefunItemStack("FINALTECH_ADVANCED_GOLD_PAN", Material.BROWN_TERRACOTTA, "&6高级淘金机");
    public static final SlimefunItemStack ADVANCED_HEATED_PRESSURE_CHAMBER = new SlimefunItemStack("FINALTECH_HEATED_PRESSURE_CHAMBER", Material.LIGHT_GRAY_STAINED_GLASS, "&c高级加热压力仓");

    // final machines
    // 'final' means it's the best machines
    public static final SlimefunItemStack ALL_COMPRESSION = new SlimefunItemStack("FINALTECH_ALL_COMPRESSION", Material.AMETHYST_BLOCK, "&7万物压缩器",
            "&8输入" + AllCompression.DIFFICULTY + "个相同物品",
            "&8从而制成带有特定签名的物品");
    public static final SlimefunItemStack ALL_FACTORY = new SlimefunItemStack("FINALTECH_ALL_FACTORY", Material.BUDDING_AMETHYST, "&7万物工厂",
            "&8在中间放入带有特定签名的物品",
            "&8从而 无 限 复 制 该物品");

    // cargos
    public static final SlimefunItemStack PIPE = new SlimefunItemStack("FINALTECH_CARGO_PIPE", Material.END_ROD, "&7导管",
            "&7把物品从一个容器中运输到另一个容器中",
            "&7可以直线" + CargoUtil.BLOCK_SEARCH_LIMIT + "格距离的容器",
            "&7通过指向另一个导管以延申其搜索范围",
            "&8即放即用");
    public static final SlimefunItemStack PIPE_GROUP = new SlimefunItemStack("FINALTECH_CARGO_PIPE_GROUP", Material.TINTED_GLASS, "&7中转器",
            "&7把物品从指定的输入位置，传输到指定的输出位置",
            "&7通过连接对应方向的锁链",
            "&7可延申其搜索范围");
    public static final SlimefunItemStack BASIC_FRAME_MACHINE = new SlimefunItemStack("FINALTECH_BASIC_FRAME_MACHINE", Material.STONE, "&7基础机器框架", "&8可放置");
    public static final SlimefunItemStack DOUBLE_NORMAL_BARREL = new SlimefunItemStack("FINALTECH_DOUBLE_NORMAL_BARREL", Material.GLASS, "&7普通存储单元", "&7可通过粘液货运进行交互", "&8其实就是更大的桶");
    public static final SlimefunItemStack DOUBLE_LINKED_BARREL = new SlimefunItemStack("FINALTECH_DOUBLE_LINKED_BARREL", Material.GLASS, "&7可识别存储单元", "&7上面三排被识别为输入槽", "&7下面三排被识别为输出槽");
}

