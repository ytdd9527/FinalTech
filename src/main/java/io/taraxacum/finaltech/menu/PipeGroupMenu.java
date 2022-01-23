package io.taraxacum.finaltech.menu;

import io.github.thebusybiscuit.slimefun4.libraries.dough.items.CustomItemStack;
import io.github.thebusybiscuit.slimefun4.utils.ChestMenuUtils;
import io.taraxacum.finaltech.util.CargoUtil;
import me.mrCookieSlime.Slimefun.api.BlockStorage;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenuPreset;
import me.mrCookieSlime.Slimefun.api.inventory.DirtyChestMenu;
import me.mrCookieSlime.Slimefun.api.item_transport.ItemTransportFlow;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;
import java.util.*;

public abstract class PipeGroupMenu extends BlockMenuPreset {

    private static final int[] BORDER = new int[] {20};

    public static final int[] ITEM_MATCH = new int[] {27, 28, 29, 36, 37, 38, 45, 46, 47};

    public static final int[] INPUT_SLOTS = new int[] {6, 7, 8, 15, 16, 17, 24, 25, 26};

    public static final int[] OUTPUT_SLOTS = new int[] {33, 34, 35, 42, 43, 44, 51, 52, 53};

    private static final int FILTER_MODE_SLOT = 10;

    private static final int INPUT_ITEM_COUNT_SUB_SLOT = 3;
    private static final int INPUT_ITEM_COUNT_SLOT = 12;
    private static final int INPUT_ITEM_COUNT_ADD_SLOT = 21;
    private static final int OUTPUT_ITEM_COUNT_SUB_SLOT = 30;
    private static final int OUTPUT_ITEM_COUNT_SLOT = 39;
    private static final int OUTPUT_ITEM_COUNT_ADD_SLOT = 48;

    private static final int INPUT_SIZE_SLOT = 4;
    private static final int INPUT_ORDER_SLOT = 13;
    private static final int INPUT_ITEM_MODE_SLOT = 22;

    private static final int OUTPUT_SIZE_SLOT = 31;
    private static final int OUTPUT_ORDER_SLOT = 40;
    private static final int OUTPUT_ITEM_MODE_SLOT = 49;

    private static final int[] INPUT_BORDER = new int[] {5, 14, 23};
    private static final int[] OUTPUT_BORDER = new int[] {32, 41, 50};

    private static final int PIPE_GROUP_SEARCH_MODE_SLOT = 2;

    private static final int ITEM_COUNT_LIMIT = 576;

    private static final Map<Integer, String> POSITION;
    static {
        POSITION = new HashMap<>();
        POSITION.put(0, CargoUtil.POSITION_UP);
        POSITION.put(1, CargoUtil.POSITION_NORTH);
        POSITION.put(9, CargoUtil.POSITION_WEST);
        POSITION.put(11, CargoUtil.POSITION_EAST);
        POSITION.put(18, CargoUtil.POSITION_DOWN);
        POSITION.put(19, CargoUtil.POSITION_SOUTH);
    }

    public PipeGroupMenu(@Nonnull String id, @Nonnull String title, boolean universal) {
        super(id, title, universal);
    }

    @Override
    public void init() {
        Material border = Material.LIGHT_GRAY_STAINED_GLASS_PANE;
        ItemStack itemCountIcon = new CustomItemStack(CargoUtil.ITEM_COUNT_ICON, 64);
        CargoUtil.setItemCountModeIcon(itemCountIcon, CargoUtil.ITEM_COUNT_MODE_UNIVERSAL);
        for(int slot : BORDER) {
            this.addItem(slot, new CustomItemStack(border, ""), ChestMenuUtils.getEmptyClickHandler());
        }
        for(int slot : INPUT_BORDER) {
            this.addItem(slot, CargoUtil.INPUT_BORDER_ICON, ChestMenuUtils.getEmptyClickHandler());
        }
        for(int slot : OUTPUT_BORDER) {
            this.addItem(slot, CargoUtil.OUTPUT_BORDER_ICON, ChestMenuUtils.getEmptyClickHandler());
        }
        this.addItem(0, new ItemStack(CargoUtil.POSITION_UP_NULL_ICON));
        this.addItem(1, new ItemStack(CargoUtil.POSITION_NORTH_NULL_ICON));
        this.addItem(9, new ItemStack(CargoUtil.POSITION_WEST_NULL_ICON));
        this.addItem(11, new ItemStack(CargoUtil.POSITION_EAST_NULL_ICON));
        this.addItem(18, new ItemStack(CargoUtil.POSITION_DOWN_NULL_ICON));
        this.addItem(19, new ItemStack(CargoUtil.POSITION_SOUTH_NULL_ICON));

        this.addItem(INPUT_ITEM_COUNT_SUB_SLOT, CargoUtil.ITEM_SUB_ICON);
        this.addItem(INPUT_ITEM_COUNT_SLOT, new CustomItemStack(itemCountIcon), ChestMenuUtils.getEmptyClickHandler());
        this.addItem(INPUT_ITEM_COUNT_ADD_SLOT, CargoUtil.ITEM_ADD_ICON);
        this.addItem(OUTPUT_ITEM_COUNT_SUB_SLOT, CargoUtil.ITEM_SUB_ICON);
        this.addItem(OUTPUT_ITEM_COUNT_SLOT, new CustomItemStack(itemCountIcon), ChestMenuUtils.getEmptyClickHandler());
        this.addItem(OUTPUT_ITEM_COUNT_ADD_SLOT, CargoUtil.ITEM_ADD_ICON);

        this.addItem(INPUT_SIZE_SLOT, CargoUtil.SIZE_OUTPUTS_ONLY_ICON);
        this.addItem(INPUT_ORDER_SLOT, CargoUtil.ORDER_ASCENT_ICON);
        this.addItem(INPUT_ITEM_MODE_SLOT, CargoUtil.ITEM_MODE_ALL_ICON);
        this.addItem(OUTPUT_SIZE_SLOT, CargoUtil.SIZE_INPUTS_ONLY_ICON);
        this.addItem(OUTPUT_ORDER_SLOT, CargoUtil.ORDER_ASCENT_ICON);
        this.addItem(OUTPUT_ITEM_MODE_SLOT, CargoUtil.ITEM_MODE_ALL_ICON);

        this.addItem(FILTER_MODE_SLOT, CargoUtil.FILTER_MODE_BLACK_LIST_ICON);

        this.addItem(PIPE_GROUP_SEARCH_MODE_SLOT, CargoUtil.PIPE_GROUP_SEARCH_MODE_RESPECT_ICON);
    }

    @Override
    public void newInstance(BlockMenu blockMenu, Block block) {
        updateMenu(blockMenu, block);
        blockMenu.addMenuClickHandler(INPUT_ITEM_COUNT_ADD_SLOT, (player, i, itemStack, clickAction) -> {
            int itemCount = Integer.parseInt(BlockStorage.getLocationInfo(block.getLocation(), CargoUtil.INPUT_ITEM_COUNT));
            itemCount = itemCount % ITEM_COUNT_LIMIT + 1;
            CargoUtil.setIconAmount(itemCount, blockMenu.getItemInSlot(INPUT_ITEM_COUNT_SLOT));
            BlockStorage.addBlockInfo(block.getLocation(), CargoUtil.INPUT_ITEM_COUNT, String.valueOf(itemCount));
            return false;
        });
        blockMenu.addMenuClickHandler(INPUT_ITEM_COUNT_SUB_SLOT, (player, i, itemStack, clickAction) -> {
            int itemCount = Integer.parseInt(BlockStorage.getLocationInfo(block.getLocation(), CargoUtil.INPUT_ITEM_COUNT));
            itemCount = (itemCount - 2 + ITEM_COUNT_LIMIT) % ITEM_COUNT_LIMIT + 1;
            CargoUtil.setIconAmount(itemCount, blockMenu.getItemInSlot(INPUT_ITEM_COUNT_SLOT));
            BlockStorage.addBlockInfo(block.getLocation(), CargoUtil.INPUT_ITEM_COUNT, String.valueOf(itemCount));
            return false;
        });
        blockMenu.addMenuClickHandler(INPUT_ITEM_COUNT_SLOT, (player, i, itemStack, clickAction) -> {
            String itemCountMode = CargoUtil.nextItemCountMode(BlockStorage.getLocationInfo(block.getLocation(), CargoUtil.INPUT_ITEM_COUNT_MODE));
            CargoUtil.setItemCountModeIcon(blockMenu.getItemInSlot(INPUT_ITEM_COUNT_SLOT), itemCountMode);
            BlockStorage.addBlockInfo(block.getLocation(), CargoUtil.INPUT_ITEM_COUNT_MODE, itemCountMode);
            return false;
        });
        blockMenu.addMenuClickHandler(INPUT_SIZE_SLOT, (player, i, itemStack, clickAction) -> {
            String size = CargoUtil.nextSize(BlockStorage.getLocationInfo(block.getLocation(), CargoUtil.INPUT_SIZE));
            blockMenu.replaceExistingItem(INPUT_SIZE_SLOT, CargoUtil.getSIzeIcon(size));
            BlockStorage.addBlockInfo(block.getLocation(), CargoUtil.INPUT_SIZE, size);
            return false;
        });
        blockMenu.addMenuClickHandler(INPUT_ORDER_SLOT, (player, i, itemStack, clickAction) -> {
            String order = CargoUtil.nextOrder(BlockStorage.getLocationInfo(block.getLocation(), CargoUtil.INPUT_ORDER));
            blockMenu.replaceExistingItem(INPUT_ORDER_SLOT, CargoUtil.getOrderIcon(order));
            BlockStorage.addBlockInfo(block.getLocation(), CargoUtil.INPUT_ORDER, order);
            return false;
        });
        blockMenu.addMenuClickHandler(INPUT_ITEM_MODE_SLOT, (player, i, itemStack, clickAction) -> {
            String itemMode = CargoUtil.nextItemMode(BlockStorage.getLocationInfo(block.getLocation(), CargoUtil.INPUT_ITEM_MODE));
            blockMenu.replaceExistingItem(INPUT_ITEM_MODE_SLOT, CargoUtil.getItemModeIcon(itemMode));
            BlockStorage.addBlockInfo(block.getLocation(), CargoUtil.INPUT_ITEM_MODE, itemMode);
            return false;
        });
        blockMenu.addMenuClickHandler(OUTPUT_ITEM_COUNT_ADD_SLOT, (player, i, itemStack, clickAction) -> {
            int itemCount = Integer.parseInt(BlockStorage.getLocationInfo(block.getLocation(), CargoUtil.OUTPUT_ITEM_COUNT));
            itemCount = itemCount % ITEM_COUNT_LIMIT + 1;
            CargoUtil.setIconAmount(itemCount, blockMenu.getItemInSlot(OUTPUT_ITEM_COUNT_SLOT));
            BlockStorage.addBlockInfo(block.getLocation(), CargoUtil.OUTPUT_ITEM_COUNT, String.valueOf(itemCount));
            return false;
        });
        blockMenu.addMenuClickHandler(OUTPUT_ITEM_COUNT_SUB_SLOT, (player, i, itemStack, clickAction) -> {
            int itemCount = Integer.parseInt(BlockStorage.getLocationInfo(block.getLocation(), CargoUtil.OUTPUT_ITEM_COUNT));
            itemCount = (itemCount - 2 + ITEM_COUNT_LIMIT) % ITEM_COUNT_LIMIT + 1;
            CargoUtil.setIconAmount(itemCount, blockMenu.getItemInSlot(OUTPUT_ITEM_COUNT_SLOT));
            BlockStorage.addBlockInfo(block.getLocation(), CargoUtil.OUTPUT_ITEM_COUNT, String.valueOf(itemCount));
            return false;
        });
        blockMenu.addMenuClickHandler(OUTPUT_ITEM_COUNT_SLOT, (player, i, itemStack, clickAction) -> {
            String itemCountMode = CargoUtil.nextItemCountMode(BlockStorage.getLocationInfo(block.getLocation(), CargoUtil.OUTPUT_ITEM_COUNT_MODE));
            CargoUtil.setItemCountModeIcon(blockMenu.getItemInSlot(OUTPUT_ITEM_COUNT_SLOT), itemCountMode);
            BlockStorage.addBlockInfo(block.getLocation(), CargoUtil.OUTPUT_ITEM_COUNT_MODE, itemCountMode);
            return false;
        });
        blockMenu.addMenuClickHandler(OUTPUT_SIZE_SLOT, (player, i, itemStack, clickAction) -> {
            String size = CargoUtil.nextSize(BlockStorage.getLocationInfo(block.getLocation(), CargoUtil.OUTPUT_SIZE));
            blockMenu.replaceExistingItem(OUTPUT_SIZE_SLOT, CargoUtil.getSIzeIcon(size));
            BlockStorage.addBlockInfo(block.getLocation(), CargoUtil.OUTPUT_SIZE, size);
            return false;
        });
        blockMenu.addMenuClickHandler(OUTPUT_ORDER_SLOT, (player, i, itemStack, clickAction) -> {
            String order = CargoUtil.nextOrder(BlockStorage.getLocationInfo(block.getLocation(), CargoUtil.OUTPUT_ORDER));
            blockMenu.replaceExistingItem(OUTPUT_ORDER_SLOT, CargoUtil.getOrderIcon(order));
            BlockStorage.addBlockInfo(block.getLocation(), CargoUtil.OUTPUT_ORDER, order);
            return false;
        });
        blockMenu.addMenuClickHandler(OUTPUT_ITEM_MODE_SLOT, (player, i, itemStack, clickAction) -> {
            String itemMode = CargoUtil.nextItemMode(BlockStorage.getLocationInfo(block.getLocation(), CargoUtil.OUTPUT_ITEM_MODE));
            blockMenu.replaceExistingItem(OUTPUT_ITEM_MODE_SLOT, CargoUtil.getItemModeIcon(itemMode));
            BlockStorage.addBlockInfo(block.getLocation(), CargoUtil.OUTPUT_ITEM_MODE, itemMode);
            return false;
        });
        blockMenu.addMenuClickHandler(FILTER_MODE_SLOT, (player, i, itemStack, clickAction) -> {
            String filterMode = CargoUtil.nextFilterMode(BlockStorage.getLocationInfo(block.getLocation(), CargoUtil.FILTER_MODE));
            blockMenu.replaceExistingItem(FILTER_MODE_SLOT, CargoUtil.getFilterModeIcon(filterMode));
            BlockStorage.addBlockInfo(block.getLocation(), CargoUtil.FILTER_MODE, filterMode);
            return false;
        });
        blockMenu.addMenuClickHandler(PIPE_GROUP_SEARCH_MODE_SLOT, (player, i, itemStack, clickAction) -> {
            String searchMode = CargoUtil.nextPipeGroupSearchMode(BlockStorage.getLocationInfo(block.getLocation(), CargoUtil.PIPE_GROUP_SEARCH_MODE));
            blockMenu.replaceExistingItem(PIPE_GROUP_SEARCH_MODE_SLOT, CargoUtil.getPipeGroupSearchModeIcon(searchMode));
            BlockStorage.addBlockInfo(block.getLocation(), CargoUtil.PIPE_GROUP_SEARCH_MODE, searchMode);
            return false;
        });
        Iterator<Integer> iterator = POSITION.keySet().iterator();
        while(iterator.hasNext()) {
            int slot = iterator.next();
            String position = POSITION.get(slot);
            blockMenu.addMenuClickHandler(slot, (player, i, itemStack, clickAction) -> {
                String infos = BlockStorage.getLocationInfo(block.getLocation(), CargoUtil.POSITION_INFO);
                CargoUtil.Position p = new CargoUtil.Position(infos);
                String type = p.nextType(position);
                int order = p.getOrder(position);
                CargoUtil.setPositionTypeIcon(blockMenu.getItemInSlot(slot), type, order == -1 ? 1 : order);
                BlockStorage.addBlockInfo(block.getLocation(), CargoUtil.POSITION_INFO, p.toString());
                return false;
            });
        }
    }

    @Override
    public int[] getSlotsAccessedByItemTransport(ItemTransportFlow itemTransportFlow) {
        switch (itemTransportFlow) {
            case WITHDRAW:
                return OUTPUT_SLOTS;
            case INSERT:
                return INPUT_SLOTS;
            default:
                return ITEM_MATCH;
        }
    }

    @Override
    public int[] getSlotsAccessedByItemTransport(DirtyChestMenu menu, ItemTransportFlow flow, ItemStack item) {
        return this.getSlotsAccessedByItemTransport(flow);
    }

    public void updateMenu(BlockMenu blockMenu, Block block) {
        CargoUtil.setIconAmount(Integer.parseInt(BlockStorage.getLocationInfo(block.getLocation(), CargoUtil.INPUT_ITEM_COUNT)), blockMenu.getItemInSlot(INPUT_ITEM_COUNT_SLOT));
        CargoUtil.setItemCountModeIcon(blockMenu.getItemInSlot(INPUT_ITEM_COUNT_SLOT), BlockStorage.getLocationInfo(block.getLocation(), CargoUtil.INPUT_ITEM_COUNT_MODE));
        blockMenu.replaceExistingItem(INPUT_SIZE_SLOT, CargoUtil.getSIzeIcon(BlockStorage.getLocationInfo(block.getLocation(), CargoUtil.INPUT_SIZE)));
        blockMenu.replaceExistingItem(INPUT_ORDER_SLOT, CargoUtil.getOrderIcon(BlockStorage.getLocationInfo(block.getLocation(), CargoUtil.INPUT_ORDER)));
        blockMenu.replaceExistingItem(INPUT_ITEM_MODE_SLOT, CargoUtil.getItemModeIcon(BlockStorage.getLocationInfo(block.getLocation(), CargoUtil.INPUT_ITEM_MODE)));
        CargoUtil.setIconAmount(Integer.parseInt(BlockStorage.getLocationInfo(block.getLocation(), CargoUtil.OUTPUT_ITEM_COUNT)), blockMenu.getItemInSlot(OUTPUT_ITEM_COUNT_SLOT));
        CargoUtil.setItemCountModeIcon(blockMenu.getItemInSlot(OUTPUT_ITEM_COUNT_SLOT), BlockStorage.getLocationInfo(block.getLocation(), CargoUtil.OUTPUT_ITEM_COUNT_MODE));
        blockMenu.replaceExistingItem(OUTPUT_SIZE_SLOT, CargoUtil.getSIzeIcon(BlockStorage.getLocationInfo(block.getLocation(), CargoUtil.OUTPUT_SIZE)));
        blockMenu.replaceExistingItem(OUTPUT_ORDER_SLOT, CargoUtil.getOrderIcon(BlockStorage.getLocationInfo(block.getLocation(), CargoUtil.OUTPUT_ORDER)));
        blockMenu.replaceExistingItem(OUTPUT_ITEM_MODE_SLOT, CargoUtil.getItemModeIcon(BlockStorage.getLocationInfo(block.getLocation(), CargoUtil.OUTPUT_ITEM_MODE)));
        blockMenu.replaceExistingItem(FILTER_MODE_SLOT, CargoUtil.getFilterModeIcon(BlockStorage.getLocationInfo(block.getLocation(), CargoUtil.FILTER_MODE)));
        blockMenu.replaceExistingItem(PIPE_GROUP_SEARCH_MODE_SLOT, CargoUtil.getPipeGroupSearchModeIcon(BlockStorage.getLocationInfo(block.getLocation(), CargoUtil.PIPE_GROUP_SEARCH_MODE)));

        Iterator<Integer> iterator = POSITION.keySet().iterator();
        while(iterator.hasNext()) {
            int slot = iterator.next();
            String position = POSITION.get(slot);
            String infos = BlockStorage.getLocationInfo(block.getLocation(), CargoUtil.POSITION_INFO);
            CargoUtil.Position p = new CargoUtil.Position(infos);
            String type = p.getType(position);
            int order = p.getOrder(position);
            CargoUtil.setPositionTypeIcon(blockMenu.getItemInSlot(slot), type, order == -1 ? 1 : order);
        }



    }
}
