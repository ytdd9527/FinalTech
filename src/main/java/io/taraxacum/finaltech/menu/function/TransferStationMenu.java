package io.taraxacum.finaltech.menu.function;

import io.github.thebusybiscuit.slimefun4.libraries.dough.items.CustomItemStack;
import io.github.thebusybiscuit.slimefun4.utils.ChestMenuUtils;
import io.taraxacum.finaltech.machine.AbstractMachine;
import io.taraxacum.finaltech.menu.AbstractMachineMenu;
import io.taraxacum.finaltech.setup.register.FinalTechItems;
import io.taraxacum.finaltech.dto.PositionHelper;
import io.taraxacum.finaltech.util.menu.*;
import me.mrCookieSlime.Slimefun.api.BlockStorage;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import me.mrCookieSlime.Slimefun.api.inventory.DirtyChestMenu;
import me.mrCookieSlime.Slimefun.api.item_transport.ItemTransportFlow;
import org.bukkit.block.Block;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;
import java.util.*;

/**
 * @author Final_ROOT
 */
public class TransferStationMenu extends AbstractMachineMenu {
    private static final int[] BORDER = new int[0];

    public static final int[] ITEM_MATCH = new int[] {27, 28, 29, 36, 37, 38, 45, 46, 47};

    public static final int[] INPUT_SLOT = new int[] {6, 7, 8, 15, 16, 17, 24, 25, 26};

    public static final int[] OUTPUT_SLOT = new int[] {33, 34, 35, 42, 43, 44, 51, 52, 53};

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

    private static final int INPUT_BLOCK_SEARCH_MODE_SLOT = 2;
    private static final int OUTPUT_BLOCK_SEARCH_MODE_SLOT = 20;

    private static final int ITEM_COUNT_LIMIT = 576;

    private static final Map<Integer, String> POSITION;
    static {
        POSITION = new HashMap<>();
        POSITION.put(0, PositionInfo.POSITION_UP);
        POSITION.put(1, PositionInfo.POSITION_NORTH);
        POSITION.put(9, PositionInfo.POSITION_WEST);
        POSITION.put(11, PositionInfo.POSITION_EAST);
        POSITION.put(18, PositionInfo.POSITION_DOWN);
        POSITION.put(19, PositionInfo.POSITION_SOUTH);
    }

    public TransferStationMenu(@Nonnull String id, @Nonnull String title, AbstractMachine machine) {
        super(id, title, machine);
    }

    @Override
    protected int[] getBorder() {
        return BORDER;
    }

    @Override
    protected int[] getInputBorder() {
        return INPUT_BORDER;
    }

    @Override
    protected int[] getOutputBorder() {
        return OUTPUT_BORDER;
    }

    @Override
    public int[] getInputSlot() {
        return INPUT_SLOT;
    }

    @Override
    public int[] getOutputSlot() {
        return OUTPUT_SLOT;
    }

    @Override
    public void init() {
        super.init();
        ItemStack itemCountIcon = new CustomItemStack(CargoNumber.CARGO_NUMBER_ICON, 64);
        CargoCountMode.setIcon(itemCountIcon, CargoCountMode.VALUE_UNIVERSAL);
        this.addItem(0, new ItemStack(PositionInfo.POSITION_UP_NULL_ICON));
        this.addItem(1, new ItemStack(PositionInfo.POSITION_NORTH_NULL_ICON));
        this.addItem(9, new ItemStack(PositionInfo.POSITION_WEST_NULL_ICON));
        this.addItem(11, new ItemStack(PositionInfo.POSITION_EAST_NULL_ICON));
        this.addItem(18, new ItemStack(PositionInfo.POSITION_DOWN_NULL_ICON));
        this.addItem(19, new ItemStack(PositionInfo.POSITION_SOUTH_NULL_ICON));

        this.addItem(INPUT_ITEM_COUNT_SUB_SLOT, CargoNumber.CARGO_NUMBER_SUB_ICON);
        this.addItem(INPUT_ITEM_COUNT_SLOT, new CustomItemStack(itemCountIcon), ChestMenuUtils.getEmptyClickHandler());
        this.addItem(INPUT_ITEM_COUNT_ADD_SLOT, CargoNumber.CARGO_NUMBER_ADD_ICON);
        this.addItem(OUTPUT_ITEM_COUNT_SUB_SLOT, CargoNumber.CARGO_NUMBER_SUB_ICON);
        this.addItem(OUTPUT_ITEM_COUNT_SLOT, new CustomItemStack(itemCountIcon), ChestMenuUtils.getEmptyClickHandler());
        this.addItem(OUTPUT_ITEM_COUNT_ADD_SLOT, CargoNumber.CARGO_NUMBER_ADD_ICON);

        this.addItem(INPUT_SIZE_SLOT, SlotSearchSize.OUTPUTS_ONLY_ICON);
        this.addItem(INPUT_ORDER_SLOT, SlotSearchOrder.ASCENT_ICON);
        this.addItem(INPUT_ITEM_MODE_SLOT, CargoItemMode.ALL_ICON);
        this.addItem(OUTPUT_SIZE_SLOT, SlotSearchSize.INPUTS_ONLY_ICON);
        this.addItem(OUTPUT_ORDER_SLOT, SlotSearchOrder.ASCENT_ICON);
        this.addItem(OUTPUT_ITEM_MODE_SLOT, CargoItemMode.ALL_ICON);

        this.addItem(FILTER_MODE_SLOT, FilterMode.FILTER_MODE_BLACK_ICON);

        this.addItem(INPUT_BLOCK_SEARCH_MODE_SLOT, BlockSearchMode.ZERO_ICON);
        this.addItem(OUTPUT_BLOCK_SEARCH_MODE_SLOT, BlockSearchMode.ZERO_ICON);
    }

    @Override
    public void newInstance(@Nonnull BlockMenu blockMenu, @Nonnull Block block) {
        updateMenu(blockMenu, block);
        blockMenu.addMenuClickHandler(INPUT_ITEM_COUNT_ADD_SLOT, (player, i, itemStack, clickAction) -> {
            int itemCount = Integer.parseInt(BlockStorage.getLocationInfo(block.getLocation(), CargoNumber.KEY_INPUT));
            itemCount = itemCount % ITEM_COUNT_LIMIT + 1;
            CargoNumber.setIcon(blockMenu.getItemInSlot(INPUT_ITEM_COUNT_SLOT), itemCount);
            BlockStorage.addBlockInfo(block.getLocation(), CargoNumber.KEY_INPUT, String.valueOf(itemCount));
            return false;
        });
        blockMenu.addMenuClickHandler(INPUT_ITEM_COUNT_SUB_SLOT, (player, i, itemStack, clickAction) -> {
            int itemCount = Integer.parseInt(BlockStorage.getLocationInfo(block.getLocation(), CargoNumber.KEY_INPUT));
            itemCount = (itemCount - 2 + ITEM_COUNT_LIMIT) % ITEM_COUNT_LIMIT + 1;
            CargoNumber.setIcon(blockMenu.getItemInSlot(INPUT_ITEM_COUNT_SLOT), itemCount);
            BlockStorage.addBlockInfo(block.getLocation(), CargoNumber.KEY_INPUT, String.valueOf(itemCount));
            return false;
        });
        blockMenu.addMenuClickHandler(INPUT_ITEM_COUNT_SLOT, (player, i, itemStack, clickAction) -> {
            String itemCountMode = CargoCountMode.next(BlockStorage.getLocationInfo(block.getLocation(), CargoCountMode.KEY_INPUT));
            CargoCountMode.setIcon(blockMenu.getItemInSlot(INPUT_ITEM_COUNT_SLOT), itemCountMode);
            BlockStorage.addBlockInfo(block.getLocation(), CargoCountMode.KEY_INPUT, itemCountMode);
            return false;
        });
        blockMenu.addMenuClickHandler(INPUT_SIZE_SLOT, (player, i, itemStack, clickAction) -> {
            String size = SlotSearchSize.next(BlockStorage.getLocationInfo(block.getLocation(), SlotSearchSize.KEY_INPUT));
            blockMenu.replaceExistingItem(INPUT_SIZE_SLOT, SlotSearchSize.getIcon(size));
            BlockStorage.addBlockInfo(block.getLocation(), SlotSearchSize.KEY_INPUT, size);
            return false;
        });
        blockMenu.addMenuClickHandler(INPUT_ORDER_SLOT, (player, i, itemStack, clickAction) -> {
            String order = SlotSearchOrder.next(BlockStorage.getLocationInfo(block.getLocation(), SlotSearchOrder.KEY_INPUT));
            blockMenu.replaceExistingItem(INPUT_ORDER_SLOT, SlotSearchOrder.getIcon(order));
            BlockStorage.addBlockInfo(block.getLocation(), SlotSearchOrder.KEY_INPUT, order);
            return false;
        });
        blockMenu.addMenuClickHandler(INPUT_ITEM_MODE_SLOT, (player, i, itemStack, clickAction) -> {
            String itemMode = CargoItemMode.next(BlockStorage.getLocationInfo(block.getLocation(), CargoItemMode.KEY_INPUT));
            blockMenu.replaceExistingItem(INPUT_ITEM_MODE_SLOT, CargoItemMode.getIcon(itemMode));
            BlockStorage.addBlockInfo(block.getLocation(), CargoItemMode.KEY_INPUT, itemMode);
            return false;
        });
        blockMenu.addMenuClickHandler(OUTPUT_ITEM_COUNT_ADD_SLOT, (player, i, itemStack, clickAction) -> {
            int itemCount = Integer.parseInt(BlockStorage.getLocationInfo(block.getLocation(), CargoNumber.KEY_OUTPUT));
            itemCount = itemCount % ITEM_COUNT_LIMIT + 1;
            CargoNumber.setIcon(blockMenu.getItemInSlot(OUTPUT_ITEM_COUNT_SLOT), itemCount);
            BlockStorage.addBlockInfo(block.getLocation(), CargoNumber.KEY_OUTPUT, String.valueOf(itemCount));
            return false;
        });
        blockMenu.addMenuClickHandler(OUTPUT_ITEM_COUNT_SUB_SLOT, (player, i, itemStack, clickAction) -> {
            int itemCount = Integer.parseInt(BlockStorage.getLocationInfo(block.getLocation(), CargoNumber.KEY_OUTPUT));
            itemCount = (itemCount - 2 + ITEM_COUNT_LIMIT) % ITEM_COUNT_LIMIT + 1;
            CargoNumber.setIcon(blockMenu.getItemInSlot(OUTPUT_ITEM_COUNT_SLOT), itemCount);
            BlockStorage.addBlockInfo(block.getLocation(), CargoNumber.KEY_OUTPUT, String.valueOf(itemCount));
            return false;
        });
        blockMenu.addMenuClickHandler(OUTPUT_ITEM_COUNT_SLOT, (player, i, itemStack, clickAction) -> {
            String itemCountMode = CargoCountMode.next(BlockStorage.getLocationInfo(block.getLocation(), CargoCountMode.KEY_OUTPUT));
            CargoCountMode.setIcon(blockMenu.getItemInSlot(OUTPUT_ITEM_COUNT_SLOT), itemCountMode);
            BlockStorage.addBlockInfo(block.getLocation(), CargoCountMode.KEY_OUTPUT, itemCountMode);
            return false;
        });
        blockMenu.addMenuClickHandler(OUTPUT_SIZE_SLOT, (player, i, itemStack, clickAction) -> {
            String size = SlotSearchSize.next(BlockStorage.getLocationInfo(block.getLocation(), SlotSearchSize.KEY_OUTPUT));
            blockMenu.replaceExistingItem(OUTPUT_SIZE_SLOT, SlotSearchSize.getIcon(size));
            BlockStorage.addBlockInfo(block.getLocation(), SlotSearchSize.KEY_OUTPUT, size);
            return false;
        });
        blockMenu.addMenuClickHandler(OUTPUT_ORDER_SLOT, (player, i, itemStack, clickAction) -> {
            String order = SlotSearchOrder.next(BlockStorage.getLocationInfo(block.getLocation(), SlotSearchOrder.KEY_OUTPUT));
            blockMenu.replaceExistingItem(OUTPUT_ORDER_SLOT, SlotSearchOrder.getIcon(order));
            BlockStorage.addBlockInfo(block.getLocation(), SlotSearchOrder.KEY_OUTPUT, order);
            return false;
        });
        blockMenu.addMenuClickHandler(OUTPUT_ITEM_MODE_SLOT, (player, i, itemStack, clickAction) -> {
            String itemMode = CargoItemMode.next(BlockStorage.getLocationInfo(block.getLocation(), CargoItemMode.KEY_OUTPUT));
            blockMenu.replaceExistingItem(OUTPUT_ITEM_MODE_SLOT, CargoItemMode.getIcon(itemMode));
            BlockStorage.addBlockInfo(block.getLocation(), CargoItemMode.KEY_OUTPUT, itemMode);
            return false;
        });
        blockMenu.addMenuClickHandler(FILTER_MODE_SLOT, (player, i, itemStack, clickAction) -> {
            String filterMode = FilterMode.next(BlockStorage.getLocationInfo(block.getLocation(), FilterMode.KEY));
            blockMenu.replaceExistingItem(FILTER_MODE_SLOT, FilterMode.getIcon(filterMode));
            BlockStorage.addBlockInfo(block.getLocation(), FilterMode.KEY, filterMode);
            return false;
        });
        blockMenu.addMenuClickHandler(INPUT_BLOCK_SEARCH_MODE_SLOT, (player, i, itemStack, clickAction) -> {
            String searchMode = BlockSearchMode.next(BlockStorage.getLocationInfo(block.getLocation(), BlockSearchMode.KEY_INPUT), FinalTechItems.TRANSFER_STATION.getItemId());
            blockMenu.replaceExistingItem(INPUT_BLOCK_SEARCH_MODE_SLOT, BlockSearchMode.getIcon(searchMode));
            BlockStorage.addBlockInfo(block.getLocation(), BlockSearchMode.KEY_INPUT, searchMode);
            return false;
        });
        blockMenu.addMenuClickHandler(OUTPUT_BLOCK_SEARCH_MODE_SLOT, (player, i, itemStack, clickAction) -> {
            String searchMode = BlockSearchMode.next(BlockStorage.getLocationInfo(block.getLocation(), BlockSearchMode.KEY_OUTPUT), FinalTechItems.TRANSFER_STATION.getItemId());
            blockMenu.replaceExistingItem(OUTPUT_BLOCK_SEARCH_MODE_SLOT, BlockSearchMode.getIcon(searchMode));
            BlockStorage.addBlockInfo(block.getLocation(), BlockSearchMode.KEY_OUTPUT, searchMode);
            return false;
        });
        Iterator<Integer> iterator = POSITION.keySet().iterator();
        while(iterator.hasNext()) {
            int slot = iterator.next();
            String position = POSITION.get(slot);
            blockMenu.addMenuClickHandler(slot, (player, i, itemStack, clickAction) -> {
                String infos = BlockStorage.getLocationInfo(block.getLocation(), PositionInfo.KEY);
                PositionHelper p = new PositionHelper(infos);
                String type = p.nextType(position);
                int order = p.getOrder(position);
                PositionInfo.setIcon(blockMenu.getItemInSlot(slot), type, order == -1 ? 1 : order);
                BlockStorage.addBlockInfo(block.getLocation(), PositionInfo.KEY, p.toString());
                return false;
            });
        }
    }

    @Override
    public int[] getSlotsAccessedByItemTransport(ItemTransportFlow itemTransportFlow) {
        switch (itemTransportFlow) {
            case WITHDRAW:
                return OUTPUT_SLOT;
            case INSERT:
                return INPUT_SLOT;
            default:
                return ITEM_MATCH;
        }
    }

    @Override
    public int[] getSlotsAccessedByItemTransport(DirtyChestMenu menu, ItemTransportFlow flow, ItemStack item) {
        return this.getSlotsAccessedByItemTransport(flow);
    }

    @Override
    public void updateMenu(@Nonnull BlockMenu blockMenu, Block block) {
        if (BlockStorage.getLocationInfo(block.getLocation(), CargoNumber.KEY_INPUT) == null) {
            BlockStorage.addBlockInfo(block.getLocation(), CargoNumber.KEY_INPUT, "64");
        }
        if (BlockStorage.getLocationInfo(block.getLocation(), SlotSearchSize.KEY_INPUT) == null) {
            BlockStorage.addBlockInfo(block.getLocation(), SlotSearchSize.KEY_INPUT, SlotSearchSize.VALUE_OUTPUTS_ONLY);
        }
        if (BlockStorage.getLocationInfo(block.getLocation(), SlotSearchOrder.KEY_INPUT) == null) {
            BlockStorage.addBlockInfo(block.getLocation(), SlotSearchOrder.KEY_INPUT, SlotSearchOrder.VALUE_ASCENT);
        }
        if (BlockStorage.getLocationInfo(block.getLocation(), CargoCountMode.KEY_INPUT) == null) {
            BlockStorage.addBlockInfo(block.getLocation(), CargoCountMode.KEY_INPUT, CargoCountMode.VALUE_UNIVERSAL);
        }
        if (BlockStorage.getLocationInfo(block.getLocation(), CargoItemMode.KEY_INPUT) == null) {
            BlockStorage.addBlockInfo(block.getLocation(), CargoItemMode.KEY_INPUT, CargoItemMode.VALUE_ALL);
        }
        if (BlockStorage.getLocationInfo(block.getLocation(), CargoNumber.KEY_OUTPUT) == null) {
            BlockStorage.addBlockInfo(block.getLocation(), CargoNumber.KEY_OUTPUT, "64");
        }
        if (BlockStorage.getLocationInfo(block.getLocation(), SlotSearchSize.KEY_OUTPUT) == null) {
            BlockStorage.addBlockInfo(block.getLocation(), SlotSearchSize.KEY_OUTPUT, SlotSearchSize.VALUE_INPUTS_ONLY);
        }
        if (BlockStorage.getLocationInfo(block.getLocation(), SlotSearchOrder.KEY_OUTPUT) == null) {
            BlockStorage.addBlockInfo(block.getLocation(), SlotSearchOrder.KEY_OUTPUT, SlotSearchOrder.VALUE_ASCENT);
        }
        if (BlockStorage.getLocationInfo(block.getLocation(), CargoCountMode.KEY_OUTPUT) == null) {
            BlockStorage.addBlockInfo(block.getLocation(), CargoCountMode.KEY_OUTPUT, CargoCountMode.VALUE_UNIVERSAL);
        }
        if (BlockStorage.getLocationInfo(block.getLocation(), CargoItemMode.KEY_OUTPUT) == null) {
            BlockStorage.addBlockInfo(block.getLocation(), CargoItemMode.KEY_OUTPUT, CargoItemMode.VALUE_ALL);
        }
        if (BlockStorage.getLocationInfo(block.getLocation(), FilterMode.KEY) == null) {
            BlockStorage.addBlockInfo(block.getLocation(), FilterMode.KEY, FilterMode.VALUE_BLACK);
        }
        if (BlockStorage.getLocationInfo(block.getLocation(), PositionInfo.KEY) == null) {
            BlockStorage.addBlockInfo(block.getLocation(), PositionInfo.KEY, "");
        }
        if (BlockStorage.getLocationInfo(block.getLocation(), BlockSearchMode.KEY_INPUT) == null) {
            BlockStorage.addBlockInfo(block.getLocation(), BlockSearchMode.KEY_INPUT, BlockSearchMode.VALUE_ZERO);
        }
        if (BlockStorage.getLocationInfo(block.getLocation(), BlockSearchMode.KEY_INPUT) == null) {
            BlockStorage.addBlockInfo(block.getLocation(), BlockSearchMode.KEY_INPUT, BlockSearchMode.VALUE_ZERO);
        }
        CargoNumber.setIcon(blockMenu.getItemInSlot(INPUT_ITEM_COUNT_SLOT), Integer.parseInt(BlockStorage.getLocationInfo(block.getLocation(), CargoNumber.KEY_INPUT)));
        CargoCountMode.setIcon(blockMenu.getItemInSlot(INPUT_ITEM_COUNT_SLOT), BlockStorage.getLocationInfo(block.getLocation(), CargoCountMode.KEY_INPUT));
        blockMenu.replaceExistingItem(INPUT_SIZE_SLOT, SlotSearchSize.getIcon(BlockStorage.getLocationInfo(block.getLocation(), SlotSearchSize.KEY_INPUT)));
        blockMenu.replaceExistingItem(INPUT_ORDER_SLOT, SlotSearchOrder.getIcon(BlockStorage.getLocationInfo(block.getLocation(), SlotSearchOrder.KEY_INPUT)));
        blockMenu.replaceExistingItem(INPUT_ITEM_MODE_SLOT, CargoItemMode.getIcon(BlockStorage.getLocationInfo(block.getLocation(), CargoItemMode.KEY_INPUT)));
        blockMenu.replaceExistingItem(INPUT_BLOCK_SEARCH_MODE_SLOT, BlockSearchMode.getIcon(BlockStorage.getLocationInfo(block.getLocation(), BlockSearchMode.KEY_INPUT)));
        CargoNumber.setIcon(blockMenu.getItemInSlot(OUTPUT_ITEM_COUNT_SLOT), Integer.parseInt(BlockStorage.getLocationInfo(block.getLocation(), CargoNumber.KEY_OUTPUT)));
        CargoCountMode.setIcon(blockMenu.getItemInSlot(OUTPUT_ITEM_COUNT_SLOT), BlockStorage.getLocationInfo(block.getLocation(), CargoCountMode.KEY_OUTPUT));
        blockMenu.replaceExistingItem(OUTPUT_SIZE_SLOT, SlotSearchSize.getIcon(BlockStorage.getLocationInfo(block.getLocation(), SlotSearchSize.KEY_OUTPUT)));
        blockMenu.replaceExistingItem(OUTPUT_ORDER_SLOT, SlotSearchOrder.getIcon(BlockStorage.getLocationInfo(block.getLocation(), SlotSearchOrder.KEY_OUTPUT)));
        blockMenu.replaceExistingItem(OUTPUT_ITEM_MODE_SLOT, CargoItemMode.getIcon(BlockStorage.getLocationInfo(block.getLocation(), CargoItemMode.KEY_OUTPUT)));
        blockMenu.replaceExistingItem(OUTPUT_BLOCK_SEARCH_MODE_SLOT, BlockSearchMode.getIcon(BlockStorage.getLocationInfo(block.getLocation(), BlockSearchMode.KEY_OUTPUT)));
        blockMenu.replaceExistingItem(FILTER_MODE_SLOT, FilterMode.getIcon(BlockStorage.getLocationInfo(block.getLocation(), FilterMode.KEY)));

        Iterator<Integer> iterator = POSITION.keySet().iterator();
        while(iterator.hasNext()) {
            int slot = iterator.next();
            String position = POSITION.get(slot);
            String infos = BlockStorage.getLocationInfo(block.getLocation(), PositionInfo.KEY);
            PositionHelper p = new PositionHelper(infos);
            String type = p.getType(position);
            int order = p.getOrder(position);
            PositionInfo.setIcon(blockMenu.getItemInSlot(slot), type, order == -1 ? 1 : order);
        }
    }
}
