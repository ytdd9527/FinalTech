package io.taraxacum.finaltech.core.menu.function;

import io.taraxacum.finaltech.core.items.machine.AbstractMachine;
import io.taraxacum.finaltech.core.menu.AbstractMachineMenu;
import io.taraxacum.finaltech.core.helper.*;
import io.taraxacum.finaltech.api.dto.PositionHelper;
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
 * @since 2.0
 */
public class MeshTransferMenu extends AbstractMachineMenu {
    private static final int[] BORDER = new int[0];
    private static final int[] INPUT_BORDER = new int[] {5, 14, 23};
    private static final int[] OUTPUT_BORDER = new int[] {32, 41, 50};
    private static final int[] INPUT_SLOT = new int[] {6, 7, 8, 15, 16, 17, 24, 25, 26};
    private static final int[] OUTPUT_SLOT = new int[] {33, 34, 35, 42, 43, 44, 51, 52, 53};

    private static final int POSITION_UP_SLOT = 0;
    private static final int POSITION_NORTH_SLOT = 1;
    private static final int POSITION_WEST_SLOT = 9;
    private static final int POSITION_EAST_SLOT = 11;
    private static final int POSITION_DOWN_SLOT = 18;
    private static final int POSITION_SOUTH_SLOT = 19;

    private static final int CARGO_FILTER_SLOT = 10;
    private static final int INPUT_BLOCK_SEARCH_MODE_SLOT = 2;
    private static final int OUTPUT_BLOCK_SEARCH_MODE_SLOT = 20;

    private static final int INPUT_CARGO_NUMBER_SUB_SLOT = 3;
    private static final int INPUT_CARGO_NUMBER_MODE_SLOT = 12;
    private static final int INPUT_CARGO_NUMBER_ADD_SLOT = 21;
    private static final int INPUT_SLOT_SEARCH_SIZE_SLOT = 4;
    private static final int INPUT_SLOT_SEARCH_ORDER_SLOT = 13;
    private static final int INPUT_CARGO_LIMIT_SLOT = 22;

    private static final int OUTPUT_CARGO_NUMBER_SUB_SLOT = 30;
    private static final int OUTPUT_CARGO_NUMBER_MODE_SLOT = 39;
    private static final int OUTPUT_CARGO_NUMBER_ADD_SLOT = 48;
    private static final int OUTPUT_SLOT_SEARCH_SIZE_SLOT = 31;
    private static final int OUTPUT_SLOT_SEARCH_ORDER_SLOT = 40;
    private static final int OUTPUT_CARGO_LIMIT_SLOT = 49;

    public static final int[] ITEM_MATCH = new int[] {27, 28, 29, 36, 37, 38, 45, 46, 47};

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

    public MeshTransferMenu(@Nonnull String id, @Nonnull String title, AbstractMachine machine) {
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

        this.addItem(POSITION_UP_SLOT, PositionInfo.POSITION_UP_NULL_ICON);
        this.addItem(POSITION_NORTH_SLOT, PositionInfo.POSITION_NORTH_NULL_ICON);
        this.addItem(POSITION_WEST_SLOT, PositionInfo.POSITION_WEST_NULL_ICON);
        this.addItem(POSITION_EAST_SLOT, PositionInfo.POSITION_EAST_NULL_ICON);
        this.addItem(POSITION_DOWN_SLOT, PositionInfo.POSITION_DOWN_NULL_ICON);
        this.addItem(POSITION_SOUTH_SLOT, PositionInfo.POSITION_SOUTH_NULL_ICON);

        this.addItem(CARGO_FILTER_SLOT, CargoFilter.HELPER.defaultIcon());
        this.addItem(INPUT_BLOCK_SEARCH_MODE_SLOT, BlockSearchMode.STATION_INPUT_HELPER.defaultIcon());
        this.addItem(OUTPUT_BLOCK_SEARCH_MODE_SLOT, BlockSearchMode.STATION_OUTPUT_HELPER.defaultIcon());

        this.addItem(INPUT_CARGO_NUMBER_SUB_SLOT, CargoNumber.CARGO_NUMBER_SUB_ICON);
        this.addItem(INPUT_CARGO_NUMBER_MODE_SLOT, CargoNumber.CARGO_NUMBER_ICON);
        this.addItem(INPUT_CARGO_NUMBER_ADD_SLOT, CargoNumber.CARGO_NUMBER_ADD_ICON);
        this.addItem(INPUT_SLOT_SEARCH_SIZE_SLOT, SlotSearchSize.INPUT_HELPER.defaultIcon());
        this.addItem(INPUT_SLOT_SEARCH_ORDER_SLOT, SlotSearchOrder.INPUT_HELPER.defaultIcon());
        this.addItem(INPUT_CARGO_LIMIT_SLOT, CargoLimit.INPUT_HELPER.defaultIcon());

        this.addItem(OUTPUT_CARGO_NUMBER_SUB_SLOT, CargoNumber.CARGO_NUMBER_SUB_ICON);
        this.addItem(OUTPUT_CARGO_NUMBER_MODE_SLOT, CargoNumber.CARGO_NUMBER_ICON);
        this.addItem(OUTPUT_CARGO_NUMBER_ADD_SLOT, CargoNumber.CARGO_NUMBER_ADD_ICON);
        this.addItem(OUTPUT_SLOT_SEARCH_SIZE_SLOT, SlotSearchSize.OUTPUT_HELPER.defaultIcon());
        this.addItem(OUTPUT_SLOT_SEARCH_ORDER_SLOT, SlotSearchOrder.OUTPUT_HELPER.defaultIcon());
        this.addItem(OUTPUT_CARGO_LIMIT_SLOT, CargoLimit.OUTPUT_HELPER.defaultIcon());
    }

    @Override
    public void newInstance(@Nonnull BlockMenu blockMenu, @Nonnull Block block) {
        updateMenu(blockMenu, block);

        blockMenu.addMenuClickHandler(CARGO_FILTER_SLOT, CargoFilter.HELPER.getHandler(blockMenu, block, this, CARGO_FILTER_SLOT));
        blockMenu.addMenuClickHandler(INPUT_BLOCK_SEARCH_MODE_SLOT, BlockSearchMode.STATION_INPUT_HELPER.getHandler(blockMenu, block, this, INPUT_BLOCK_SEARCH_MODE_SLOT));
        blockMenu.addMenuClickHandler(OUTPUT_BLOCK_SEARCH_MODE_SLOT, BlockSearchMode.STATION_OUTPUT_HELPER.getHandler(blockMenu, block, this, OUTPUT_BLOCK_SEARCH_MODE_SLOT));

        blockMenu.addMenuClickHandler(INPUT_CARGO_NUMBER_ADD_SLOT, CargoNumber.INPUT_HELPER.getNextHandler(blockMenu, block, this, INPUT_CARGO_NUMBER_MODE_SLOT));
        blockMenu.addMenuClickHandler(INPUT_CARGO_NUMBER_SUB_SLOT, CargoNumber.INPUT_HELPER.getPreviousHandler(blockMenu, block, this, INPUT_CARGO_NUMBER_MODE_SLOT));
        blockMenu.addMenuClickHandler(INPUT_CARGO_NUMBER_MODE_SLOT, CargoNumberMode.INPUT_HELPER.getHandler(blockMenu, block, this, INPUT_CARGO_NUMBER_MODE_SLOT));
        blockMenu.addMenuClickHandler(INPUT_SLOT_SEARCH_SIZE_SLOT, SlotSearchSize.INPUT_HELPER.getHandler(blockMenu, block, this, INPUT_SLOT_SEARCH_SIZE_SLOT));
        blockMenu.addMenuClickHandler(INPUT_SLOT_SEARCH_ORDER_SLOT, SlotSearchOrder.INPUT_HELPER.getHandler(blockMenu, block, this, INPUT_SLOT_SEARCH_ORDER_SLOT));
        blockMenu.addMenuClickHandler(INPUT_CARGO_LIMIT_SLOT, CargoLimit.INPUT_HELPER.getHandler(blockMenu, block, this, INPUT_CARGO_LIMIT_SLOT));

        blockMenu.addMenuClickHandler(OUTPUT_CARGO_NUMBER_ADD_SLOT, CargoNumber.OUTPUT_HELPER.getNextHandler(blockMenu, block, this, OUTPUT_CARGO_NUMBER_MODE_SLOT));
        blockMenu.addMenuClickHandler(OUTPUT_CARGO_NUMBER_SUB_SLOT, CargoNumber.OUTPUT_HELPER.getPreviousHandler(blockMenu, block, this, OUTPUT_CARGO_NUMBER_MODE_SLOT));
        blockMenu.addMenuClickHandler(OUTPUT_CARGO_NUMBER_MODE_SLOT, CargoNumberMode.OUTPUT_HELPER.getHandler(blockMenu, block, this, OUTPUT_CARGO_NUMBER_MODE_SLOT));
        blockMenu.addMenuClickHandler(OUTPUT_SLOT_SEARCH_SIZE_SLOT, SlotSearchSize.OUTPUT_HELPER.getHandler(blockMenu, block, this, OUTPUT_SLOT_SEARCH_SIZE_SLOT));
        blockMenu.addMenuClickHandler(OUTPUT_SLOT_SEARCH_ORDER_SLOT, SlotSearchOrder.OUTPUT_HELPER.getHandler(blockMenu, block, this, OUTPUT_SLOT_SEARCH_ORDER_SLOT));
        blockMenu.addMenuClickHandler(OUTPUT_CARGO_LIMIT_SLOT, CargoLimit.OUTPUT_HELPER.getHandler(blockMenu, block, this, OUTPUT_CARGO_LIMIT_SLOT));

        //TODO
        for (int slot : POSITION.keySet()) {
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
        return switch (itemTransportFlow) {
            case WITHDRAW -> OUTPUT_SLOT;
            case INSERT -> INPUT_SLOT;
            default -> ITEM_MATCH;
        };
    }

    @Override
    public int[] getSlotsAccessedByItemTransport(DirtyChestMenu menu, ItemTransportFlow flow, ItemStack item) {
        return this.getSlotsAccessedByItemTransport(flow);
    }

    @Override
    public void updateMenu(@Nonnull BlockMenu blockMenu, @Nonnull Block block) {
        CargoFilter.HELPER.checkAndUpdateIcon(blockMenu, CARGO_FILTER_SLOT);
        BlockSearchMode.STATION_INPUT_HELPER.checkAndUpdateIcon(blockMenu, INPUT_BLOCK_SEARCH_MODE_SLOT);
        BlockSearchMode.STATION_OUTPUT_HELPER.checkAndUpdateIcon(blockMenu, OUTPUT_BLOCK_SEARCH_MODE_SLOT);

        CargoNumber.INPUT_HELPER.checkAndUpdateIcon(blockMenu, INPUT_CARGO_NUMBER_MODE_SLOT);
        CargoNumberMode.INPUT_HELPER.checkAndUpdateIcon(blockMenu, INPUT_CARGO_NUMBER_MODE_SLOT);
        SlotSearchSize.INPUT_HELPER.checkAndUpdateIcon(blockMenu, INPUT_SLOT_SEARCH_SIZE_SLOT);
        SlotSearchOrder.INPUT_HELPER.checkAndUpdateIcon(blockMenu, INPUT_SLOT_SEARCH_ORDER_SLOT);
        CargoLimit.INPUT_HELPER.checkAndUpdateIcon(blockMenu, INPUT_CARGO_LIMIT_SLOT);

        CargoNumber.OUTPUT_HELPER.checkAndUpdateIcon(blockMenu, OUTPUT_CARGO_NUMBER_MODE_SLOT);
        CargoNumberMode.OUTPUT_HELPER.checkAndUpdateIcon(blockMenu, OUTPUT_CARGO_NUMBER_MODE_SLOT);
        SlotSearchSize.OUTPUT_HELPER.checkAndUpdateIcon(blockMenu, OUTPUT_SLOT_SEARCH_SIZE_SLOT);
        SlotSearchOrder.OUTPUT_HELPER.checkAndUpdateIcon(blockMenu, OUTPUT_SLOT_SEARCH_ORDER_SLOT);
        CargoLimit.OUTPUT_HELPER.checkAndUpdateIcon(blockMenu, OUTPUT_CARGO_LIMIT_SLOT);

        //TODO
        for (int slot : POSITION.keySet()) {
            String position = POSITION.get(slot);
            String infos = BlockStorage.getLocationInfo(block.getLocation(), PositionInfo.KEY);
            PositionHelper p = new PositionHelper(infos);
            String type = p.getType(position);
            int order = p.getOrder(position);
            PositionInfo.setIcon(blockMenu.getItemInSlot(slot), type, order == -1 ? 1 : order);
        }
    }
}
