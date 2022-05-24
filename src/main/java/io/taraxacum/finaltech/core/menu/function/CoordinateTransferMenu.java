package io.taraxacum.finaltech.core.menu.function;

import io.github.thebusybiscuit.slimefun4.utils.ChestMenuUtils;
import io.taraxacum.finaltech.core.items.machine.AbstractMachine;
import io.taraxacum.finaltech.core.menu.AbstractMachineMenu;
import io.taraxacum.finaltech.core.storage.*;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import org.bukkit.Location;
import org.bukkit.block.Block;

import javax.annotation.Nonnull;

public class CoordinateTransferMenu extends AbstractMachineMenu {
    private static final int[] BORDER = new int[] {27, 28, 29, 30, 31, 32, 33, 34, 35, 39, 41, 48, 49, 50};
    private static final int[] INPUT_BORDER = new int[0];
    private static final int[] OUTPUT_BORDER = new int[0];
    private static final int[] INPUT_SLOT = new int[] {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26};
    private static final int[] OUTPUT_SLOT = new int[] {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26};

    private static final int ITEM_COUNT_SUB_SLOT = 36;
    private static final int ITEM_COUNT_SLOT = 37;
    private static final int ITEM_COUNT_ADD_SLOT = 38;

    private static final int SIZE_SLOT = 45;
    private static final int ORDER_SLOT = 46;
    private static final int ITEM_MODE_SLOT = 47;

    private static final int CARGO_MODE_SLOT = 42;
    private static final int SIDE_MODE_SLOT = 43;

    private static final int LINE1_SLOT = 51;
    private static final int LINE2_SLOT = 52;
    private static final int LINE3_SLOT = 53;

    private static final int ITEM_COUNT_LIMIT = 3456;

    public CoordinateTransferMenu(@Nonnull AbstractMachine machine) {
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
    public void newInstance(@Nonnull BlockMenu blockMenu, @Nonnull Block block) {
        super.newInstance(blockMenu, block);
    }

    @Override
    public void init() {
        super.init();

        this.addItem(ITEM_COUNT_SUB_SLOT, CargoNumber.CARGO_NUMBER_SUB_ICON);
        this.addMenuClickHandler(ITEM_COUNT_SUB_SLOT, ChestMenuUtils.getEmptyClickHandler());

        this.addMenuClickHandler(ITEM_COUNT_SLOT, ChestMenuUtils.getEmptyClickHandler());
        this.addItem(ITEM_COUNT_SLOT, CargoNumber.CARGO_NUMBER_ICON);

        this.addItem(ITEM_COUNT_ADD_SLOT, CargoNumber.CARGO_NUMBER_ADD_ICON);
        this.addMenuClickHandler(ITEM_COUNT_ADD_SLOT, ChestMenuUtils.getEmptyClickHandler());

        this.addItem(SIZE_SLOT, SlotSearchSize.HELPER.defaultIcon());
        this.addMenuClickHandler(SIZE_SLOT, ChestMenuUtils.getEmptyClickHandler());

        this.addItem(ORDER_SLOT, SlotSearchOrder.HELPER.defaultIcon());
        this.addMenuClickHandler(ORDER_SLOT, ChestMenuUtils.getEmptyClickHandler());

        this.addItem(ITEM_MODE_SLOT, CargoItemMode.HELPER.defaultIcon());
        this.addMenuClickHandler(ITEM_MODE_SLOT, ChestMenuUtils.getEmptyClickHandler());

        this.addItem(CARGO_MODE_SLOT, CargoMode.HELPER.defaultIcon());
        this.addMenuClickHandler(CARGO_MODE_SLOT, ChestMenuUtils.getEmptyClickHandler());
    }

    @Override
    public void newInstance(@Nonnull BlockMenu blockMenu, @Nonnull Location location) {
        super.newInstance(blockMenu, location);
        blockMenu.addMenuClickHandler(SIZE_SLOT, SlotSearchSize.HELPER.getHandler(blockMenu, location.getBlock(), CoordinateTransferMenu.this, SIZE_SLOT));
        blockMenu.addMenuClickHandler(ORDER_SLOT, SlotSearchOrder.HELPER.getHandler(blockMenu, location.getBlock(), CoordinateTransferMenu.this, ORDER_SLOT));
        blockMenu.addMenuClickHandler(CARGO_MODE_SLOT, CargoMode.HELPER.getHandler(blockMenu, location.getBlock(), CoordinateTransferMenu.this, CARGO_MODE_SLOT));
        blockMenu.addMenuClickHandler(ITEM_COUNT_SUB_SLOT, CargoNumber.HELPER.getHandler(blockMenu, location.getBlock(), CoordinateTransferMenu.this, ITEM_COUNT_SLOT));
    }

    @Override
    protected void updateMenu(@Nonnull BlockMenu blockMenu, @Nonnull Block block) {

    }
}
