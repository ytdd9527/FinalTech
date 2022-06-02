package io.taraxacum.finaltech.core.menu.function;

import io.taraxacum.finaltech.core.items.machine.AbstractMachine;
import io.taraxacum.finaltech.core.menu.AbstractMachineMenu;
import io.taraxacum.finaltech.core.helper.*;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import org.bukkit.block.Block;

import javax.annotation.Nonnull;

/**
 * @author Final_ROOT
 * @since 2.0
 */
public class LocationTransferMenu extends AbstractMachineMenu {
    private static final int[] BORDER = new int[] {27, 28, 29, 30, 31, 32, 33, 34, 35, 39, 41, 44, 48, 49, 50, 51, 52, 53};
    private static final int[] INPUT_BORDER = new int[0];
    private static final int[] OUTPUT_BORDER = new int[0];
    private static final int[] INPUT_SLOT = new int[] {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26};
    private static final int[] OUTPUT_SLOT = new int[] {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26};

    private static final int CARGO_NUMBER_SUB_SLOT = 36;
    private static final int CARGO_NUMBER_SLOT = 37;
    private static final int CARGO_NUMBER_ADD_SLOT = 38;

    private static final int SLOT_SEARCH_SIZE_SLOT = 45;
    private static final int SLOT_SEARCH_ORDER_SLOT = 46;
    private static final int CARGO_LIMIT_SLOT = 47;

    private static final int CARGO_MODE_SLOT = 42;
    private static final int CARGO_ORDER_SLOT = 43;

    private static final int LINE1_SLOT = 51;
    private static final int LINE2_SLOT = 52;
    private static final int LINE3_SLOT = 53;

    public static final int LOCATION_RECORDER_SLOT = 40;

    public LocationTransferMenu(@Nonnull AbstractMachine machine) {
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
        this.addItem(CARGO_NUMBER_ADD_SLOT, CargoNumber.CARGO_NUMBER_ADD_ICON);

        this.addItem(SLOT_SEARCH_SIZE_SLOT, SlotSearchSize.HELPER.defaultIcon());
        this.addItem(SLOT_SEARCH_ORDER_SLOT, SlotSearchOrder.HELPER.defaultIcon());
        this.addItem(CARGO_LIMIT_SLOT, CargoLimit.HELPER.defaultIcon());

        this.addItem(CARGO_MODE_SLOT, CargoMode.HELPER.defaultIcon());
        this.addItem(CARGO_ORDER_SLOT, CargoOrder.HELPER.defaultIcon());

//        this.addItem(LINE1_SLOT, SlotSearchLine.L1_ICON);
//        this.addItem(LINE2_SLOT, SlotSearchLine.L2_ICON);
//        this.addItem(LINE3_SLOT, SlotSearchLine.L3_ICON);
    }

    @Override
    public void newInstance(@Nonnull BlockMenu blockMenu, @Nonnull Block block) {
        super.newInstance(blockMenu, block);
        blockMenu.addMenuClickHandler(CARGO_NUMBER_SUB_SLOT, CargoNumber.HELPER.getPreviousHandler(blockMenu, block, this, CARGO_NUMBER_SLOT));
        blockMenu.addMenuClickHandler(CARGO_NUMBER_ADD_SLOT, CargoNumber.HELPER.getNextHandler(blockMenu, block, this, CARGO_NUMBER_SLOT));
        blockMenu.addMenuClickHandler(CARGO_NUMBER_SLOT, CargoNumberMode.HELPER.getHandler(blockMenu, block, this, CARGO_NUMBER_SLOT));

        blockMenu.addMenuClickHandler(SLOT_SEARCH_SIZE_SLOT, SlotSearchSize.HELPER.getHandler(blockMenu, block, this, SLOT_SEARCH_SIZE_SLOT));
        blockMenu.addMenuClickHandler(SLOT_SEARCH_ORDER_SLOT, SlotSearchOrder.HELPER.getHandler(blockMenu, block, this, SLOT_SEARCH_ORDER_SLOT));
        blockMenu.addMenuClickHandler(CARGO_LIMIT_SLOT, CargoLimit.HELPER.getHandler(blockMenu, block, this, CARGO_LIMIT_SLOT));

        blockMenu.addMenuClickHandler(CARGO_MODE_SLOT, CargoMode.HELPER.getHandler(blockMenu, block, this, CARGO_MODE_SLOT));
        blockMenu.addMenuClickHandler(CARGO_ORDER_SLOT, CargoOrder.HELPER.getHandler(blockMenu, block, this, CARGO_ORDER_SLOT));

//        blockMenu.addMenuClickHandler(LINE1_SLOT, SlotSearchLine.L1_HELPER.getHandler(blockMenu, block, this, LINE1_SLOT));
//        blockMenu.addMenuClickHandler(LINE2_SLOT, SlotSearchLine.L2_HELPER.getHandler(blockMenu, block, this, LINE2_SLOT));
//        blockMenu.addMenuClickHandler(LINE3_SLOT, SlotSearchLine.L3_HELPER.getHandler(blockMenu, block, this, LINE3_SLOT));
    }

    @Override
    protected void updateMenu(@Nonnull BlockMenu blockMenu, @Nonnull Block block) {
        CargoNumber.HELPER.checkAndUpdateIcon(blockMenu, CARGO_NUMBER_SLOT);
        CargoNumberMode.HELPER.checkAndUpdateIcon(blockMenu, CARGO_NUMBER_SLOT);

        SlotSearchSize.HELPER.checkAndUpdateIcon(blockMenu, SLOT_SEARCH_SIZE_SLOT);
        SlotSearchOrder.HELPER.checkAndUpdateIcon(blockMenu, SLOT_SEARCH_ORDER_SLOT);
        CargoLimit.HELPER.checkAndUpdateIcon(blockMenu, CARGO_LIMIT_SLOT);

        CargoMode.HELPER.checkAndUpdateIcon(blockMenu, CARGO_MODE_SLOT);
        CargoOrder.HELPER.checkAndUpdateIcon(blockMenu, CARGO_ORDER_SLOT);

//        SlotSearchLine.L1_HELPER.checkAndUpdateIcon(blockMenu, LINE1_SLOT);
//        SlotSearchLine.L2_HELPER.checkAndUpdateIcon(blockMenu, LINE2_SLOT);
//        SlotSearchLine.L3_HELPER.checkAndUpdateIcon(blockMenu, LINE3_SLOT);
    }
}
