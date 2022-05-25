package io.taraxacum.finaltech.core.menu.function;

import io.github.thebusybiscuit.slimefun4.utils.ChestMenuUtils;
import io.taraxacum.finaltech.core.items.machine.AbstractMachine;
import io.taraxacum.finaltech.core.menu.AbstractMachineMenu;
import io.taraxacum.finaltech.core.storage.*;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import me.mrCookieSlime.Slimefun.api.inventory.DirtyChestMenu;
import me.mrCookieSlime.Slimefun.api.item_transport.ItemTransportFlow;
import org.bukkit.block.Block;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;

/**
 * @author Final_ROOT
 * @since 2.0
 */
public class LinkTransferMenu extends AbstractMachineMenu {
    private static final int[] BORDER = new int[] {21, 22, 23};
    private static final int[] INPUT_BORDER = new int[] {0, 2, 9, 11, 18, 20, 27, 29, 36, 38, 45, 47};
    private static final int[] OUTPUT_BORDER = new int[] {6, 8, 15, 17, 24, 26, 33, 35, 42, 44, 51, 53};
    private static final int[] INPUT_SLOT = new int[] {30, 31, 32, 39, 40, 41, 48, 49, 50};
    private static final int[] OUTPUT_SLOT = new int[] {30, 31, 32, 39, 40, 41, 48, 49, 50};

    private static final int CARGO_NUMBER_SUB_SLOT = 3;
    private static final int CARGO_NUMBER_SLOT = 4;
    private static final int CARGO_NUMBER_ADD_SLOT = 5;
    private static final int CARGO_FILTER_SLOT = 12;
    private static final int CARGO_MODE_SLOT = 13;
    private static final int CARGO_LIMIT_SLOT = 14;

    private static final int INPUT_SLOT_SEARCH_SIZE_SLOT = 1;
    private static final int INPUT_SLOT_SEARCH_ORDER_SLOT = 10;
    private static final int INPUT_BLOCK_SEARCH_MODE_SLOT = 19;

    private static final int OUTPUT_SLOT_SEARCH_SIZE_SLOT = 7;
    private static final int OUTPUT_SLOT_SEARCH_ORDER_SLOT = 16;
    private static final int OUTPUT_BLOCK_SEARCH_MODE_SLOT = 25;

    public static final int[] ITEM_MATCH = new int[] {30, 31, 32, 39, 40, 41, 48, 49, 50};

    public LinkTransferMenu(@Nonnull AbstractMachine machine) {
        super(machine);
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

        this.addItem(CARGO_NUMBER_SUB_SLOT, CargoNumber.CARGO_NUMBER_SUB_ICON);
        this.addItem(CARGO_NUMBER_SLOT, CargoNumber.CARGO_NUMBER_ICON);
        this.addMenuClickHandler(CARGO_NUMBER_SLOT, ChestMenuUtils.getEmptyClickHandler());
        this.addItem(CARGO_NUMBER_ADD_SLOT, CargoNumber.CARGO_NUMBER_ADD_ICON);
        this.addItem(CARGO_FILTER_SLOT, CargoFilter.HELPER.defaultIcon());
        this.addItem(CARGO_MODE_SLOT, CargoMode.HELPER.defaultIcon());
        this.addItem(CARGO_LIMIT_SLOT, CargoLimit.HELPER.defaultIcon());

        this.addItem(INPUT_SLOT_SEARCH_SIZE_SLOT, SlotSearchSize.INPUT_HELPER.defaultIcon());
        this.addItem(INPUT_SLOT_SEARCH_ORDER_SLOT, SlotSearchOrder.INPUT_HELPER.defaultIcon());
        this.addItem(INPUT_BLOCK_SEARCH_MODE_SLOT, BlockSearchMode.PIPE_INPUT_HELPER.defaultIcon());

        this.addItem(OUTPUT_SLOT_SEARCH_SIZE_SLOT, SlotSearchSize.OUTPUT_HELPER.defaultIcon());
        this.addItem(OUTPUT_SLOT_SEARCH_ORDER_SLOT, SlotSearchOrder.OUTPUT_HELPER.defaultIcon());
        this.addItem(OUTPUT_BLOCK_SEARCH_MODE_SLOT, BlockSearchMode.PIPE_OUTPUT_HELPER.defaultIcon());
    }

    @Override
    public void newInstance(@Nonnull BlockMenu blockMenu, @Nonnull Block block) {
        super.newInstance(blockMenu, block);

        blockMenu.addMenuClickHandler(CARGO_NUMBER_SUB_SLOT, CargoNumber.HELPER.getPreviousHandler(blockMenu, block, this, CARGO_NUMBER_SLOT));
        blockMenu.addMenuClickHandler(CARGO_NUMBER_ADD_SLOT, CargoNumber.HELPER.getNextHandler(blockMenu, block, this, CARGO_NUMBER_SLOT));
        blockMenu.addMenuClickHandler(CARGO_FILTER_SLOT, CargoFilter.HELPER.getHandler(blockMenu, block, this, CARGO_FILTER_SLOT));
        blockMenu.addMenuClickHandler(CARGO_MODE_SLOT, CargoMode.HELPER.getHandler(blockMenu, block, this, CARGO_MODE_SLOT));
        blockMenu.addMenuClickHandler(CARGO_LIMIT_SLOT, CargoLimit.HELPER.getHandler(blockMenu, block, this, CARGO_LIMIT_SLOT));

        blockMenu.addMenuClickHandler(INPUT_SLOT_SEARCH_SIZE_SLOT, SlotSearchSize.INPUT_HELPER.getHandler(blockMenu, block, this, INPUT_SLOT_SEARCH_SIZE_SLOT));
        blockMenu.addMenuClickHandler(INPUT_SLOT_SEARCH_ORDER_SLOT, SlotSearchOrder.INPUT_HELPER.getHandler(blockMenu, block, this, INPUT_SLOT_SEARCH_ORDER_SLOT));
        blockMenu.addMenuClickHandler(INPUT_BLOCK_SEARCH_MODE_SLOT, BlockSearchMode.PIPE_INPUT_HELPER.getHandler(blockMenu, block, this, INPUT_BLOCK_SEARCH_MODE_SLOT));

        blockMenu.addMenuClickHandler(OUTPUT_SLOT_SEARCH_SIZE_SLOT, SlotSearchSize.OUTPUT_HELPER.getHandler(blockMenu, block, this, OUTPUT_SLOT_SEARCH_SIZE_SLOT));
        blockMenu.addMenuClickHandler(OUTPUT_SLOT_SEARCH_ORDER_SLOT, SlotSearchOrder.OUTPUT_HELPER.getHandler(blockMenu, block, this, OUTPUT_SLOT_SEARCH_ORDER_SLOT));
        blockMenu.addMenuClickHandler(OUTPUT_BLOCK_SEARCH_MODE_SLOT, BlockSearchMode.PIPE_OUTPUT_HELPER.getHandler(blockMenu, block, this, OUTPUT_BLOCK_SEARCH_MODE_SLOT));
    }

    @Override
    public int[] getSlotsAccessedByItemTransport(ItemTransportFlow itemTransportFlow) {
        return ITEM_MATCH;
    }

    @Override
    public int[] getSlotsAccessedByItemTransport(DirtyChestMenu menu, ItemTransportFlow flow, ItemStack item) {
        return this.getSlotsAccessedByItemTransport(flow);
    }

    @Override
    public void updateMenu(@Nonnull BlockMenu blockMenu, @Nonnull Block block) {
        CargoNumber.HELPER.checkAndUpdateIcon(blockMenu, CARGO_NUMBER_SLOT);
        CargoFilter.HELPER.checkAndUpdateIcon(blockMenu, CARGO_FILTER_SLOT);
        CargoMode.HELPER.checkAndUpdateIcon(blockMenu, CARGO_MODE_SLOT);
        CargoLimit.HELPER.checkAndUpdateIcon(blockMenu, CARGO_LIMIT_SLOT);

        SlotSearchSize.INPUT_HELPER.checkAndUpdateIcon(blockMenu, INPUT_SLOT_SEARCH_SIZE_SLOT);
        SlotSearchOrder.INPUT_HELPER.checkAndUpdateIcon(blockMenu, INPUT_SLOT_SEARCH_ORDER_SLOT);
        BlockSearchMode.PIPE_INPUT_HELPER.checkAndUpdateIcon(blockMenu, INPUT_BLOCK_SEARCH_MODE_SLOT);

        SlotSearchSize.OUTPUT_HELPER.checkAndUpdateIcon(blockMenu, OUTPUT_SLOT_SEARCH_SIZE_SLOT);
        SlotSearchOrder.OUTPUT_HELPER.checkAndUpdateIcon(blockMenu, OUTPUT_SLOT_SEARCH_ORDER_SLOT);
        BlockSearchMode.PIPE_OUTPUT_HELPER.checkAndUpdateIcon(blockMenu, OUTPUT_BLOCK_SEARCH_MODE_SLOT);
    }
}
