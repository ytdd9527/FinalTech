package io.taraxacum.finaltech.core.menu.standard.lock;

import io.taraxacum.finaltech.core.items.machine.AbstractMachine;

import javax.annotation.Nonnull;

/**
 * @author Final_ROOT
 */
public class MatrixCraftingTableMenu extends AbstractLockMachineMenu {
    private static final int[] BORDER = new int[] {5, 14, 22, 23};
    private static final int[] INPUT_BORDER = new int[] {3, 12, 21};
    private static final int[] OUTPUT_BORDER = new int[] {6, 7, 8, 15, 17, 24, 25, 26};
    private static final int[] INPUT_SLOT = new int[] {0, 1, 2, 9, 10 ,11, 18, 19, 20};
    private static final int[] OUTPUT_SLOT = new int[] {16};

    public MatrixCraftingTableMenu(@Nonnull AbstractMachine machine) {
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
}
