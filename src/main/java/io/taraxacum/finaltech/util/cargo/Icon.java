package io.taraxacum.finaltech.util.cargo;

import io.github.thebusybiscuit.slimefun4.libraries.dough.items.CustomItemStack;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

/**
 * @author Final_ROOT
 */
public class Icon {
    public static final String MACHINE_MAX_STACK = "max-stack";
    public static final ItemStack MACHINE_MAX_STACK_ICON = new CustomItemStack(Material.CHEST, "&7输入数量限制", "&7未限制");

    public static final ItemStack QUANTITY_MODULE_ICON = new CustomItemStack(Material.REDSTONE, "&f可升级模块", "&7该机器可以通过添加[数量组件]进行升级", "&7当前效率=1");

    public static final ItemStack PARSE_FAILED_ICON = new CustomItemStack(Material.PURPLE_STAINED_GLASS_PANE, "&4解析失败", "&kYou_find_me!");
    public static final ItemStack PARSE_SUCCESS_ICON = new CustomItemStack(Material.GREEN_STAINED_GLASS_PANE, "&a解析成功");
    public static final ItemStack PARSE_NULL_ICON = new CustomItemStack(Material.LIGHT_GRAY_STAINED_GLASS_PANE, " ");

    public static final ItemStack AUTO_CRAFT_PARSE_ICON = new CustomItemStack(Material.CRAFTING_TABLE, "&a点击尝试解析");

    public static final ItemStack BORDER_ICON = new CustomItemStack(Material.BLACK_STAINED_GLASS_PANE, " ");
    public static final ItemStack INPUT_BORDER_ICON = new CustomItemStack(Material.BLUE_STAINED_GLASS_PANE, "&9输入侧");
    public static final ItemStack OUTPUT_BORDER_ICON = new CustomItemStack(Material.ORANGE_STAINED_GLASS_PANE, "&6输出侧");

    public static final ItemStack ERROR_ICON = new CustomItemStack(Material.BARRIER, "&c错误", "&c你不应该看到此图标", "&c请通知FinalTech的开发者修复该bug");
}
