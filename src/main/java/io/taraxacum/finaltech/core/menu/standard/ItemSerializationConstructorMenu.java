package io.taraxacum.finaltech.core.menu.standard;

import io.taraxacum.finaltech.core.items.machine.AbstractMachine;

import javax.annotation.Nonnull;

/**
 * @author Final_ROOT
 * @since 1.0
 */
public class ItemSerializationConstructorMenu extends AbstractStandardMachineMenu {
    private static final int[] BORDER = new int[] {3, 4, 5, 12, 14, 21, 22, 23};
    private static final int[] INPUT_BORDER = new int[] {2, 6, 11, 15, 20, 24, 29, 33, 38, 42, 47, 51};
    private static final int[] OUTPUT_BORDER = new int[] {30, 31, 32, 39, 41, 48, 49, 50};
    private static final int[] INPUT_SLOT = new int[] {0, 1, 7, 8, 9, 10, 16, 17, 18, 19, 25, 26, 27, 28, 34, 35, 36, 37, 43, 44, 45, 46, 52, 53};
    private static final int[] OUTPUT_SLOT = new int[] {40};
    public static final int STATUS_SLOT = 22;

    public ItemSerializationConstructorMenu(@Nonnull AbstractMachine abstractMachine) {
        super(abstractMachine);
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
