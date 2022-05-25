package io.taraxacum.finaltech.core.storage;

import io.github.thebusybiscuit.slimefun4.libraries.dough.items.CustomItemStack;
import org.bukkit.Axis;
import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Final_ROOT
 */
//TODO
// yeah sometime I should update this class.
public class PositionInfo {
    public static final String KEY = "pi";

    public static final String POSITION_UP = "u";
    public static final String POSITION_DOWN = "d";
    public static final String POSITION_EAST = "e";
    public static final String POSITION_SOUTH = "s";
    public static final String POSITION_WEST = "w";
    public static final String POSITION_NORTH = "m";

    public static final String POSITION_TYPE_NULL = "n";
    public static final String POSITION_TYPE_INPUT = "i";
    public static final String POSITION_TYPE_OUTPUT = "o";
    public static final String POSITION_TYPE_IN_AND_OUT = "a";

    public static final ItemStack POSITION_UP_NULL_ICON = new CustomItemStack(Material.BLACK_STAINED_GLASS_PANE, "&7上", "&7未分配");
    public static final ItemStack POSITION_DOWN_NULL_ICON = new CustomItemStack(Material.BLACK_STAINED_GLASS_PANE, "&7下", "&7未分配");
    public static final ItemStack POSITION_EAST_NULL_ICON = new CustomItemStack(Material.BLACK_STAINED_GLASS_PANE, "&7东", "&7未分配");
    public static final ItemStack POSITION_SOUTH_NULL_ICON = new CustomItemStack(Material.BLACK_STAINED_GLASS_PANE, "&7南", "&7未分配");
    public static final ItemStack POSITION_WEST_NULL_ICON = new CustomItemStack(Material.BLACK_STAINED_GLASS_PANE, "&7西", "&7未分配");
    public static final ItemStack POSITION_NORTH_NULL_ICON = new CustomItemStack(Material.BLACK_STAINED_GLASS_PANE, "&7北", "&7未分配");

    public static final String POSITION_TYPE_NULL_LORE = "§7未分配";
    public static final String POSITION_TYPE_INPUT_LORE = "§9输入";
    public static final String POSITION_TYPE_OUTPUT_LORE = "§6输出";
    public static final String POSITION_TYPE_IN_AND_OUT_LORE = "§d输入且输出";

    public static void setIcon(ItemStack item, String type, int amount) {
        ItemMeta itemMeta = item.getItemMeta();
        List<String> lore = itemMeta.getLore();
        if (lore == null) {
            lore = new ArrayList<>();
        }
        switch (type) {
            case POSITION_TYPE_NULL:
                lore.set(0, POSITION_TYPE_NULL_LORE);
                item.setType(Material.BLACK_STAINED_GLASS_PANE);
                break;
            case POSITION_TYPE_INPUT:
                lore.set(0, POSITION_TYPE_INPUT_LORE);
                item.setType(Material.BLUE_STAINED_GLASS_PANE);
                break;
            case POSITION_TYPE_OUTPUT:
                lore.set(0, POSITION_TYPE_OUTPUT_LORE);
                item.setType(Material.ORANGE_STAINED_GLASS_PANE);
                break;
            case POSITION_TYPE_IN_AND_OUT:
                lore.set(0, POSITION_TYPE_IN_AND_OUT_LORE);
                item.setType(Material.PURPLE_STAINED_GLASS_PANE);
                break;
            default:
                item.setItemMeta(Icon.ERROR_ICON.getItemMeta());
                item.setType(Icon.ERROR_ICON.getType());
                item.setAmount(1);
                item.setData(Icon.ERROR_ICON.getData());
                return;
        }
        item.setAmount(amount);
        itemMeta.setLore(lore);
        item.setItemMeta(itemMeta);
    }

    public static BlockFace getBlockFaceByPosition(String position) {
        if (position == null) {
            return BlockFace.SELF;
        }
        switch (position) {
            case POSITION_UP:
                return BlockFace.UP;
            case POSITION_DOWN:
                return BlockFace.DOWN;
            case POSITION_EAST:
                return BlockFace.EAST;
            case POSITION_SOUTH:
                return BlockFace.SOUTH;
            case POSITION_WEST:
                return BlockFace.WEST;
            case POSITION_NORTH:
                return BlockFace.NORTH;
            default:
                return BlockFace.SELF;
        }
    }

    public static boolean ifAxisMatchFace(Axis axis, BlockFace blockFace) {
        if (Axis.X.equals(axis)) {
            if (BlockFace.EAST.equals(blockFace) || BlockFace.WEST.equals(blockFace)) {
                return true;
            }
        }
        if (Axis.Y.equals(axis)) {
            if (BlockFace.UP.equals(blockFace) || BlockFace.DOWN.equals(blockFace)) {
                return true;
            }
        }
        if (Axis.Z.equals(axis)) {
            if (BlockFace.NORTH.equals(blockFace) || BlockFace.SOUTH.equals(blockFace)) {
                return true;
            }
        }
        return false;
    }
}
