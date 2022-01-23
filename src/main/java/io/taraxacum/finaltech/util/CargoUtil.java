package io.taraxacum.finaltech.util;

import io.github.thebusybiscuit.slimefun4.libraries.dough.items.CustomItemStack;
import io.github.thebusybiscuit.slimefun4.libraries.paperlib.PaperLib;
import io.github.thebusybiscuit.slimefun4.utils.SlimefunUtils;
import io.taraxacum.finaltech.cargo.PipeGroup;
import io.taraxacum.finaltech.menu.PipeGroupMenu;
import io.taraxacum.finaltech.setup.FinalTechItems;
import me.mrCookieSlime.Slimefun.api.BlockStorage;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import me.mrCookieSlime.Slimefun.api.item_transport.ItemTransportFlow;
import org.bukkit.Axis;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.BlockState;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.Directional;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

public class CargoUtil {
    public static final int BLOCK_SEARCH_LIMIT = 16;

    public static final ItemStack INPUT_BORDER_ICON = new CustomItemStack(Material.BLUE_STAINED_GLASS_PANE, "&9输入侧");
    public static final ItemStack OUTPUT_BORDER_ICON = new CustomItemStack(Material.ORANGE_STAINED_GLASS_PANE, "&6输出侧");


    public static final ItemStack ERROR_ICON = new CustomItemStack(Material.BARRIER, "&c错误", "&c你不应该看到此图标", "&c请通知FinalTech的开发者修复该bug");


    public static final String INPUT_SIZE = "input-size";
    public static final String OUTPUT_SIZE = "output-size";

    public static final String SIZE_INPUTS_ONLY = "inputs-only";
    public static final String SIZE_OUTPUTS_ONLY = "outputs-only";
    public static final String SIZE_INPUTS_AND_OUTPUTS = "inputs-and-outputs";

    public static final ItemStack SIZE_INPUTS_ONLY_ICON = new CustomItemStack(Material.SOUL_TORCH, "&9仅搜索输入槽", "&7对箱子、桶、漏斗等原版容器，该设置无效");
    public static final ItemStack SIZE_OUTPUTS_ONLY_ICON = new CustomItemStack(Material.TORCH, "&6仅搜索输出槽", "&7对箱子、桶、漏斗等原版容器，该设置无效");
    public static final ItemStack SIZE_INPUTS_AND_OUTPUTS_ICON = new CustomItemStack(Material.REDSTONE_TORCH, "&d搜索输入槽输出槽", "&7对箱子、桶、漏斗等原版容器，该设置无效");


    public static final String INPUT_ORDER = "input-order";
    public static final String OUTPUT_ORDER = "output-order";

    public static final String ORDER_ASCENT = "asc";
    public static final String ORDER_DESCEND = "desc";
    public static final String ORDER_FIRST_ONLY = "first-only";
    public static final String ORDER_LAST_ONLY = "last-only";

    public static final ItemStack ORDER_ASCENT_ICON = new CustomItemStack(Material.BLUE_WOOL, "&9顺序搜索","&7按照正向顺序搜索物品");
    public static final ItemStack ORDER_DESCEND_ICON = new CustomItemStack(Material.ORANGE_WOOL, "&6逆序搜索","&7按照逆向顺序搜索物品");
    public static final ItemStack ORDER_FIRST_ONLY_ICON = new CustomItemStack(Material.BLUE_CARPET, "&9仅搜索第一格");
    public static final ItemStack ORDER_LAST_ONLY_ICON = new CustomItemStack(Material.ORANGE_CARPET, "&6仅搜索最后一格");


    public static final String FILTER_MODE = "filter-mode";

    public static final String FILTER_MODE_WHITE_LIST = "white";
    public static final String FILTER_MODE_BLACK_LIST = "black";

    public static final ItemStack FILTER_MODE_WHITE_LIST_ICON = new CustomItemStack(Material.WHITE_WOOL, "&f白名单模式", "&7仅当物品匹配下方9个格子的物品时", "&7才会进行运输");
    public static final ItemStack FILTER_MODE_BLACK_LIST_ICON = new CustomItemStack(Material.BLACK_WOOL, "&7黑名单模式", "&7仅当物品不匹配下方9个格子的物品时", "&7才会进行运输");


    public static final String CARGO_MODE = "cargo-mode";

    public static final String CARGO_MODE_INPUT_MAIN = "input-main";
    public static final String CARGO_MODE_OUTPUT_MAIN = "output-main";
    public static final String CARGO_MODE_BALANCE = "balance";

    public static final ItemStack CARGO_MODE_INPUT_MAIN_ICON = new CustomItemStack(Material.WATER_BUCKET, "&9主输入侧", "&7以输入侧物品为主要搜索顺序");
    public static final ItemStack CARGO_MODE_OUTPUT_MAIN_ICON = new CustomItemStack(Material.LAVA_BUCKET, "&6主输出侧", "&7以输出侧物品为主要搜索顺序");
    public static final ItemStack CARGO_MODE_BALANCE_ICON = new CustomItemStack(Material.CLOCK, "&d平衡模式", "&8(暂时无用");


    public static final String POSITION_INFO = "position-info";

    public static final String POSITION_UP = "up";
    public static final String POSITION_DOWN = "down";
    public static final String POSITION_EAST = "east";
    public static final String POSITION_SOUTH = "south";
    public static final String POSITION_WEST = "west";
    public static final String POSITION_NORTH = "north";

    public static final String POSITION_TYPE_NULL = "null";
    public static final String POSITION_TYPE_INPUT = "input";
    public static final String POSITION_TYPE_OUTPUT = "output";
    public static final String POSITION_TYPE_IN_AND_OUT = "and";

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


    public static final String ITEM_COUNT = "item-count";
    public static final String INPUT_ITEM_COUNT = "input-item-count";
    public static final String OUTPUT_ITEM_COUNT = "output-item-count";

    public static final ItemStack ITEM_COUNT_ICON = new CustomItemStack(Material.TARGET, "&d数量限制", "&7当前数量限制: &f64");
    public static final ItemStack ITEM_ADD_ICON = new CustomItemStack(Material.GREEN_CONCRETE, "&6增加数量限制");
    public static final ItemStack ITEM_SUB_ICON = new CustomItemStack(Material.RED_CONCRETE, "&9减少数量限制");


    public static final String ITEM_COUNT_MODE = "item-count-mode";
    public static final String INPUT_ITEM_COUNT_MODE = "input-item-count-mode";
    public static final String OUTPUT_ITEM_COUNT_MODE = "output-item-count-mode";

    public static final String ITEM_COUNT_MODE_UNIVERSAL = "universal";
    public static final String ITEM_COUNT_MODE_STANDALONE = "standalone";

    public static final String ITEM_COUNT_MODE_UNIVERSAL_LORE = "§7数量限制模式：§f通用模式";
    public static final String ITEM_COUNT_MODE_STANDALONE_LORE = "§7数量限制模式：§f独立模式";


    public static final String INPUT_ITEM_MODE = "input-mode";
    public static final String OUTPUT_ITEM_MODE = "output-mode";

    public static final String ITEM_MODE_ALL = "all";
    public static final String ITEM_MODE_TYPE = "type";
    public static final String ITEM_MODE_STACK = "stack";

    public static final ItemStack ITEM_MODE_ALL_ICON = new CustomItemStack(Material.PUMPKIN, "&7传输种类限制-否", "&7无效果");
    public static final ItemStack ITEM_MODE_TYPE_ICON = new CustomItemStack(Material.CARVED_PUMPKIN, "&7传输种类限制-仅一种", "&7每次仅会传输一种物品");
    public static final ItemStack ITEM_MODE_STACK_ICON = new CustomItemStack(Material.JACK_O_LANTERN, "&7传输种类限制-仅一组", "&7每次仅会传输一组物品，并至多改变一个格子的物品数量");


    public static final String INPUT_BLOCK_SEARCH_MODE = "input-search-mode";
    public static final String OUTPUT_BLOCK_SEARCH_MODE = "output-search-mode";

    public static final String BLOCK_SEARCH_MODE_PENETRATE = "penetrate";
    public static final String BLOCK_SEARCH_MODE_INHERIT = "inherit";

    public static final ItemStack BLOCK_SEARCH_MODE_PENETRATE_ICON = new CustomItemStack(Material.PURPLE_STAINED_GLASS, "&7搜索模式", "&7穿透模式");
    public static final ItemStack BLOCK_SEARCH_MODE_INHERIT_ICON = new CustomItemStack(Material.PURPLE_STAINED_GLASS, "&7搜索模式", "&7继承模式");


    public static final String PIPE_GROUP_SEARCH_MODE = "pipe-group-search-group";

    public static final String PIPE_GROUP_SEARCH_MODE_RESPECT = "respect";
    public static final String PIPE_GROUP_SEARCH_MODE_PENETRATE = "penetrate";

    public static final ItemStack PIPE_GROUP_SEARCH_MODE_RESPECT_ICON = new CustomItemStack(Material.PURPLE_STAINED_GLASS, "&7搜索模式", "&7链接模式");
    public static final ItemStack PIPE_GROUP_SEARCH_MODE_PENETRATE_ICON = new CustomItemStack(Material.PURPLE_STAINED_GLASS, "&7搜索模式", "&7穿透模式");


    public static final String nextSize(String size) {
        if(size == null) {
            return SIZE_INPUTS_ONLY;
        }
        switch (size) {
            case SIZE_INPUTS_ONLY:
                return SIZE_OUTPUTS_ONLY;
            case SIZE_OUTPUTS_ONLY:
                return SIZE_INPUTS_AND_OUTPUTS;
            case SIZE_INPUTS_AND_OUTPUTS:
                return SIZE_INPUTS_ONLY;
            default:
                return SIZE_INPUTS_ONLY;
        }
    }

    public static final String nextOrder(String order) {
        if(order == null) {
            return ORDER_ASCENT;
        }
        switch (order) {
            case ORDER_ASCENT:
                return ORDER_DESCEND;
            case ORDER_DESCEND:
                return ORDER_FIRST_ONLY;
            case ORDER_FIRST_ONLY:
                return ORDER_LAST_ONLY;
            case ORDER_LAST_ONLY:
                return ORDER_ASCENT;
            default:
                return ORDER_ASCENT;
        }
    }

    public static final String nextFilterMode(String filterMode) {
        if(filterMode == null) {
            return FILTER_MODE_WHITE_LIST;
        }
        switch (filterMode) {
            case FILTER_MODE_WHITE_LIST:
                return FILTER_MODE_BLACK_LIST;
            case FILTER_MODE_BLACK_LIST:
                return FILTER_MODE_WHITE_LIST;
            default:
                return FILTER_MODE_WHITE_LIST;
        }
    }

    public static final String nextCargoMode(String cargoMode) {
        if(cargoMode == null) {
            return CARGO_MODE_INPUT_MAIN;
        }
        switch (cargoMode) {
            case CARGO_MODE_INPUT_MAIN:
                return CARGO_MODE_OUTPUT_MAIN;
            case CARGO_MODE_OUTPUT_MAIN:
                return CARGO_MODE_BALANCE;
            case CARGO_MODE_BALANCE:
                return CARGO_MODE_INPUT_MAIN;
            default:
                return CARGO_MODE_INPUT_MAIN;
        }
    }

    public static final String nextItemCountMode(String itemCountMode) {
        if(itemCountMode == null) {
            return ITEM_COUNT_MODE_UNIVERSAL;
        }
        switch (itemCountMode) {
            case ITEM_COUNT_MODE_UNIVERSAL:
                return ITEM_COUNT_MODE_STANDALONE;
            case ITEM_COUNT_MODE_STANDALONE:
                return ITEM_COUNT_MODE_UNIVERSAL;
            default:
                return ITEM_COUNT_MODE_UNIVERSAL;
        }
    }

    public static final String nextItemMode(String itemMode) {
        if(itemMode == null) {
            return ITEM_MODE_ALL;
        }
        switch (itemMode) {
            case ITEM_MODE_ALL:
                return ITEM_MODE_TYPE;
            case ITEM_MODE_TYPE:
                return ITEM_MODE_STACK;
            case ITEM_MODE_STACK:
                return ITEM_MODE_ALL;
            default:
                return ITEM_MODE_ALL;
        }
    }

    public static final String nextBlockSearchMode(String blockSearchMode) {
        if(blockSearchMode == null) {
            return BLOCK_SEARCH_MODE_PENETRATE;
        }
        switch (blockSearchMode) {
            case BLOCK_SEARCH_MODE_PENETRATE:
                return BLOCK_SEARCH_MODE_INHERIT;
            case BLOCK_SEARCH_MODE_INHERIT:
                return BLOCK_SEARCH_MODE_PENETRATE;
            default:
                return BLOCK_SEARCH_MODE_PENETRATE;
        }
    }

    public static final String nextPipeGroupSearchMode(String pipeGroupSearchMode) {
        if(pipeGroupSearchMode == null) {
            return PIPE_GROUP_SEARCH_MODE_RESPECT;
        }
        switch (pipeGroupSearchMode) {
            case PIPE_GROUP_SEARCH_MODE_RESPECT:
                return PIPE_GROUP_SEARCH_MODE_PENETRATE;
            case PIPE_GROUP_SEARCH_MODE_PENETRATE:
                return PIPE_GROUP_SEARCH_MODE_RESPECT;
            default:
                return PIPE_GROUP_SEARCH_MODE_RESPECT;
        }
    }

    public static final ItemStack getSIzeIcon(String size) {
        if(size == null) {
            return ERROR_ICON;
        }
        switch (size) {
            case SIZE_INPUTS_ONLY:
                return SIZE_INPUTS_ONLY_ICON;
            case SIZE_OUTPUTS_ONLY:
                return SIZE_OUTPUTS_ONLY_ICON;
            case SIZE_INPUTS_AND_OUTPUTS:
                return SIZE_INPUTS_AND_OUTPUTS_ICON;
            default:
                return ERROR_ICON;
        }
    }

    public static final ItemStack getOrderIcon(String order) {
        if(order == null) {
            return ERROR_ICON;
        }
        switch (order) {
            case ORDER_ASCENT:
                return ORDER_ASCENT_ICON;
            case ORDER_DESCEND:
                return ORDER_DESCEND_ICON;
            case ORDER_FIRST_ONLY:
                return ORDER_FIRST_ONLY_ICON;
            case ORDER_LAST_ONLY:
                return ORDER_LAST_ONLY_ICON;
            default:
                return ERROR_ICON;
        }
    }

    public static final ItemStack getFilterModeIcon(String filterMode) {
        if(filterMode == null) {
            return ERROR_ICON;
        }
        switch (filterMode) {
            case FILTER_MODE_WHITE_LIST:
                return FILTER_MODE_WHITE_LIST_ICON;
            case FILTER_MODE_BLACK_LIST:
                return FILTER_MODE_BLACK_LIST_ICON;
            default:
                return ERROR_ICON;
        }
    }

    public static final ItemStack getCargoModeIcon(String cargoMode) {
        if(cargoMode == null) {
            return ERROR_ICON;
        }
        switch (cargoMode) {
            case CARGO_MODE_INPUT_MAIN:
                return CARGO_MODE_INPUT_MAIN_ICON;
            case CARGO_MODE_OUTPUT_MAIN:
                return CARGO_MODE_OUTPUT_MAIN_ICON;
            case CARGO_MODE_BALANCE:
                return CARGO_MODE_BALANCE_ICON;
            default:
                return ERROR_ICON;
        }
    }

    public static final void setPositionTypeIcon(ItemStack item, String type, int amount) {
        ItemMeta itemMeta = item.getItemMeta();
        List<String> lore = itemMeta.getLore();
        if(lore == null) {
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
        }
        item.setAmount(amount);
        itemMeta.setLore(lore);
        item.setItemMeta(itemMeta);
    }

    public static final void setItemCountModeIcon(@Nonnull ItemStack item, String mode) {
        ItemMeta itemMeta = item.getItemMeta();
        List<String> lore = itemMeta.getLore();
        switch (mode) {
            case ITEM_COUNT_MODE_UNIVERSAL:
                item.setType(Material.TARGET);
                if(lore == null) {
                    lore = new ArrayList<>();
                }
                if(lore.size() < 1) {
                    lore.set(0, "§7当前数量限制: §f" + item.getAmount());
                }
                if(lore.size() < 2) {
                    lore.add(1, ITEM_COUNT_MODE_UNIVERSAL_LORE);
                } else {
                    lore.set(1, ITEM_COUNT_MODE_UNIVERSAL_LORE);
                }
                break;
            case ITEM_COUNT_MODE_STANDALONE:
                item.setType(Material.TARGET);
                if(lore == null) {
                    lore = new ArrayList<>();
                }
                if(lore.size() < 1) {
                    lore.set(0, "§7当前数量限制: §f" + item.getAmount());
                }
                if(lore.size() < 2) {
                    lore.add(1, ITEM_COUNT_MODE_STANDALONE_LORE);
                } else {
                    lore.set(1, ITEM_COUNT_MODE_STANDALONE_LORE);
                }
        }
        itemMeta.setLore(lore);
        item.setItemMeta(itemMeta);
    }

    public static final ItemStack getItemModeIcon(String itemMode) {
        if(itemMode == null) {
            return ERROR_ICON;
        }
        switch (itemMode) {
            case ITEM_MODE_ALL:
                return ITEM_MODE_ALL_ICON;
            case ITEM_MODE_TYPE:
                return ITEM_MODE_TYPE_ICON;
            case ITEM_MODE_STACK:
                return ITEM_MODE_STACK_ICON;
            default:
                return ERROR_ICON;
        }
    }

    public static final ItemStack getBlockSearchModeIcon(String blockSearchMode) {
        if(blockSearchMode == null) {
            return ERROR_ICON;
        }
        switch (blockSearchMode) {
            case BLOCK_SEARCH_MODE_PENETRATE:
                return BLOCK_SEARCH_MODE_PENETRATE_ICON;
            case BLOCK_SEARCH_MODE_INHERIT:
                return BLOCK_SEARCH_MODE_INHERIT_ICON;
            default:
                return ERROR_ICON;
        }
    }

    public static final ItemStack getPipeGroupSearchModeIcon(String pipeGroupSearchMode) {
        if(pipeGroupSearchMode == null) {
            return ERROR_ICON;
        }
        switch (pipeGroupSearchMode) {
            case PIPE_GROUP_SEARCH_MODE_RESPECT:
                return PIPE_GROUP_SEARCH_MODE_RESPECT_ICON;
            case PIPE_GROUP_SEARCH_MODE_PENETRATE:
                return PIPE_GROUP_SEARCH_MODE_PENETRATE_ICON;
            default:
                return ERROR_ICON;
        }
    }

    public static final void setIconAmount(int amount, ItemStack item) {
        if(item == null) {
            return;
        }
        if(amount <= 64) {
            item.setAmount(amount);
        } else {
            item.setAmount(64);
        }
        ItemMeta itemMeta = item.getItemMeta();
        List<String> lore = itemMeta.getLore();
        if (lore == null || lore.size() < 1) {
            lore = new ArrayList<>();
            lore.add(0, "§7当前数量限制: §f" + amount);
        }
        lore.set(0, "§7当前数量限制: §f" + amount);
        itemMeta.setLore(lore);
        item.setItemMeta(itemMeta);
    }

    public static final void changeItemAmount(@Nonnull ItemStack inputItem, @Nonnull ItemStack outputItem, int amount) {
        inputItem.setAmount(inputItem.getAmount() - amount);
        outputItem.setAmount(outputItem.getAmount() + amount);
    }

    public static final BlockFace getBlockFaceByPosition(String position) {
        if(position == null) {
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

    public static Block searchBlockPiPe(@Nonnull Block begin, String searchMode, BlockFace blockFace, boolean input) {
        Block result = begin.getRelative(blockFace);
        int count = 1;
        while(result != null) {
            if(BlockStorage.hasInventory(result) && !result.getType().equals(FinalTechItems.PIPE.getType())) {
                break;
            }
            if(PaperLib.getBlockState(result, false).getState() instanceof InventoryHolder) {
                break;
            }
            if (result.getType() == FinalTechItems.PIPE.getType()) {
                count = 0;
                if(BLOCK_SEARCH_MODE_INHERIT.equals(searchMode)){
                    BlockData blockData = result.getState().getBlockData();
                    if(blockData instanceof Directional) {
                        blockFace = ((Directional) blockData).getFacing();
                        if(input) {
                            blockFace = blockFace.getOppositeFace();
                        }
                    }
                }
            }
            result = result.getRelative(blockFace);
            if(count++ > BLOCK_SEARCH_LIMIT) {
                return null;
            }
        }
        return result;
    }

    public static Block searchBlockPipeGroup(@Nonnull Block begin, String searchMode, BlockFace blockFace) {
        Block result = begin.getRelative(blockFace);
        while(result != null) {
            if(result.getType() == Material.CHAIN) {
                result = result.getRelative(blockFace);
                continue;
            }
            if(result.getType() == FinalTechItems.PIPE_GROUP.getType() && BlockStorage.hasInventory(result) && PIPE_GROUP_SEARCH_MODE_PENETRATE.equals(searchMode) && BlockStorage.getInventory(result).getPreset().getID().equals("FINALTECH_CARGO_PIPE_GROUP")) {
                result = result.getRelative(blockFace);
                continue;
            }
            break;
        }
        return result;
    }

    public static boolean ifAxisMatchFace(Axis axis, BlockFace blockFace) {
        if(Axis.X.equals(axis)) {
            if(BlockFace.EAST.equals(blockFace) || BlockFace.WEST.equals(blockFace)) {
                return true;
            }
        }
        if(Axis.Y.equals(axis)) {
            if(BlockFace.UP.equals(blockFace) || BlockFace.DOWN.equals(blockFace)) {
                return true;
            }
        }
        if(Axis.Z.equals(axis)) {
            if(BlockFace.NORTH.equals(blockFace) || BlockFace.SOUTH.equals(blockFace)) {
                return true;
            }
        }
        return false;
    }

    public static boolean ifMatch(@Nonnull ItemStack itemStack, BlockMenu blockMenu, String filterMode, int[] slots) {
        if(CargoUtil.FILTER_MODE_WHITE_LIST.equals(filterMode)) {
            for(int slot : slots) {
                boolean itemSimilar = blockMenu.getItemInSlot(slot) == null ? false : SlimefunUtils.isItemSimilar(itemStack, blockMenu.getItemInSlot(slot), true, false);
                if(itemSimilar == true) {
                    return true;
                }
            }
            return false;
        } else if(CargoUtil.FILTER_MODE_BLACK_LIST.equals(filterMode)) {
            for(int slot : slots) {
                boolean itemSimilar = blockMenu.getItemInSlot(slot) == null ? false : SlimefunUtils.isItemSimilar(itemStack, blockMenu.getItemInSlot(slot), true, false);
                if(itemSimilar == true) {
                    return false;
                }
            }
            return true;
        } else {
            return false;
        }
    }

    public static final InvWithSlots getInv(@Nonnull Block block, String size, String order) {
        return getInv(block, size, order, new ItemStack(Material.AIR));
    }

    public static final InvWithSlots getInv(@Nonnull Block block, String size, String order, ItemStack item) {
        Inventory inventory = null;
        int[] slots = null;

        if (BlockStorage.hasInventory(block)) {
            BlockMenu blockMenu = BlockStorage.getInventory(block);
            inventory = blockMenu.toInventory();
            switch (size) {
                case CargoUtil.SIZE_INPUTS_ONLY:
                    if(item == null) {
                        slots = blockMenu.getPreset().getSlotsAccessedByItemTransport(ItemTransportFlow.INSERT);
                    } else {
                        slots = blockMenu.getPreset().getSlotsAccessedByItemTransport(blockMenu, ItemTransportFlow.INSERT, item);
                    }
//                    if(slots == null || slots.length == 0) {
//                        slots = blockMenu.getPreset().getSlotsAccessedByItemTransport(ItemTransportFlow.INSERT);
//                    }
                    break;
                case CargoUtil.SIZE_OUTPUTS_ONLY:
                    if(item == null) {
                        slots = blockMenu.getPreset().getSlotsAccessedByItemTransport(ItemTransportFlow.WITHDRAW);
                    } else {
                        slots = blockMenu.getPreset().getSlotsAccessedByItemTransport(blockMenu, ItemTransportFlow.WITHDRAW, item);
                    }
//                    if(slots == null || slots.length == 0) {
//                        slots = blockMenu.getPreset().getSlotsAccessedByItemTransport(ItemTransportFlow.WITHDRAW);
//                    }
                    break;
                case CargoUtil.SIZE_INPUTS_AND_OUTPUTS:
                    int[] insert;
//                    if(insert == null || insert.length == 0) {
//                        insert = blockMenu.getPreset().getSlotsAccessedByItemTransport(ItemTransportFlow.INSERT);
//                    }
                    int[] withdraw;
//                    if(withdraw == null || withdraw.length == 0) {
//                        withdraw = blockMenu.getPreset().getSlotsAccessedByItemTransport(ItemTransportFlow.WITHDRAW);
//                    }
                    if(item == null) {
                        insert = blockMenu.getPreset().getSlotsAccessedByItemTransport(ItemTransportFlow.INSERT);
                        withdraw = blockMenu.getPreset().getSlotsAccessedByItemTransport(ItemTransportFlow.WITHDRAW);
                    } else {
                        insert = blockMenu.getPreset().getSlotsAccessedByItemTransport(blockMenu, ItemTransportFlow.INSERT, item);
                        withdraw = blockMenu.getPreset().getSlotsAccessedByItemTransport(blockMenu, ItemTransportFlow.WITHDRAW, item);
                    }
                    slots = new int[insert.length + withdraw.length];
                    int i = 0;
                    for (; i < insert.length; i++) {
                        slots[i] = insert[i];
                    }
                    for (int j = 0; j < withdraw.length; j++) {
                        slots[i + j] = withdraw[j];
                    }
                    break;
                default:
                    slots = new int[0];
            }
        } else {
            BlockState blockState = PaperLib.getBlockState(block, false).getState();
            if (blockState instanceof InventoryHolder) {
                inventory = ((InventoryHolder) blockState).getInventory();
                slots = new int[inventory.getSize()];
                for (int i = 0; i < slots.length; i++) {
                    slots[i] = i;
                }
            }
        }

        if (inventory != null && slots != null) {
            switch (order) {
                case CargoUtil.ORDER_ASCENT:
                    break;
                case CargoUtil.ORDER_DESCEND:
                    FinalUtil.reserve(slots);
                    break;
                case CargoUtil.ORDER_FIRST_ONLY:
                    if (slots.length > 0) {
                        slots = new int[] {slots[0]};
                    }
                    break;
                case CargoUtil.ORDER_LAST_ONLY:
                    if (slots.length > 0) {
                        slots = new int[] {slots[slots.length - 1]};
                    }
            }
            return new InvWithSlots(inventory, slots);
        }
        return null;
    }

    public static class InvWithSlots{
        private Inventory inventory;
        private int[] slots;

        InvWithSlots(Inventory inventory, int[] slots) {
            this.inventory = inventory;
            this.slots = slots;
        }

        public Inventory getInventory() {
            return inventory;
        }

        public int[] getSlots() {
            return slots;
        }
    }

    public static class Position{

        private PositionStats[] positionStats = new PositionStats[6];

        public Position(String positions) {
            for(String position : positions.split("-")) {
                String[] info = position.split(":");
                if(info != null && info.length == 3 && !info[1].equals(POSITION_TYPE_NULL)) {
                    positionStats[Integer.parseInt(info[0]) - 1] = new PositionStats(info[2], info[1]);
                }
            }
        }

        public String toString() {
            String s = "";
            for(int i = 0; i < positionStats.length; i++) {
                if (positionStats[i] == null || positionStats[i].type.equals(POSITION_TYPE_NULL)) {
                    continue;
                }
                s += (i+1);
                s += ":";
                s += positionStats[i].type;
                s += ":";
                s += positionStats[i].position;
                s += "-";
            }
            return s;
        }

        public int getOrder(String position) {
            for(int i = 0; i < positionStats.length; i++) {
                if(positionStats[i] != null && positionStats[i].position.equals(position) && !positionStats[i].type.equals(POSITION_TYPE_NULL)) {
                    return i+1;
                }
            }
            return -1;
        }

        public String[] getInputs() {
            List<String> inputs = new ArrayList<>();
            for(PositionStats positionStats : positionStats) {
                if (positionStats != null && (positionStats.type.equals(CargoUtil.POSITION_TYPE_INPUT) || positionStats.type.equals(CargoUtil.POSITION_TYPE_IN_AND_OUT))) {
                    inputs.add(positionStats.position);
                }
            }
            String[] result = new String[inputs.size()];
            for(int i = 0; i < result.length; i++) {
                result[i] = inputs.get(i);
            }
            return result;
        }

        public String[] getOutputs() {
            List<String> outputs = new ArrayList<>();
            for(PositionStats positionStats : positionStats) {
                if (positionStats != null && (positionStats.type.equals(CargoUtil.POSITION_TYPE_OUTPUT) || positionStats.type.equals(CargoUtil.POSITION_TYPE_IN_AND_OUT))) {
                    outputs.add(positionStats.position);
                }
            }
            String[] result = new String[outputs.size()];
            for(int i = 0; i < result.length; i++) {
                result[i] = outputs.get(i);
            }
            return result;
        }

        public String getType(String position) {
            for(PositionStats positionStats : positionStats) {
                if(positionStats != null && positionStats.position.equals(position)) {
                    return positionStats.type;
                }
            }
            return POSITION_TYPE_NULL;
        }

        public void setType(String position, String type) {
            for(PositionStats positionStats : positionStats) {
                if(positionStats != null && positionStats.position.equals(position)) {
                    positionStats.type = type;
                    return;
                }
            }
        }

        public String nextType(String position) {
//            for(PositionStats positionStats : positionStats) {
            for(int i = 0; i < this.positionStats.length; i++) {
                PositionStats positionStats = this.positionStats[i];
                if(positionStats != null && positionStats.position.equals(position)) {
                    switch (positionStats.type) {
                        case CargoUtil.POSITION_TYPE_NULL:
                            positionStats.type = POSITION_TYPE_INPUT;
                            return POSITION_TYPE_INPUT;
                        case CargoUtil.POSITION_TYPE_INPUT:
                            positionStats.type = POSITION_TYPE_OUTPUT;
                            return POSITION_TYPE_OUTPUT;
                        case CargoUtil.POSITION_TYPE_OUTPUT:
                            positionStats.type = POSITION_TYPE_IN_AND_OUT;
                            return POSITION_TYPE_IN_AND_OUT;
//                        case CargoUtil.POSITION_TYPE_IN_AND_OUT:
//                            positionStats.type = POSITION_TYPE_NULL;
//                            return POSITION_TYPE_NULL;
                        default:
                            positionStats.type = POSITION_TYPE_NULL;
                            return POSITION_TYPE_NULL;
                    }
                }
            }
            int i = getFirstNull();
            if(i != -1) {
                positionStats[i-1] = new PositionStats(position, POSITION_TYPE_INPUT);
            }
            return POSITION_TYPE_INPUT;
        }

        public int getFirstNull() {
            for(int i = 0; i < positionStats.length; i++) {
                if(positionStats[i] == null || positionStats[i].type.equals(POSITION_TYPE_NULL)) {
                    return i+1;
                }
            }
            return -1;
        }
    }

    public static class PositionStats{
        // up;down;east;south;west;north
        private String position;

        // input;output
        private String type;

        PositionStats(String position, String type) {
            this.position = position;
            this.type = type;
        }
    }
}
