package io.taraxacum.finaltech.menu;

import io.taraxacum.finaltech.abstractItem.FinalMachine;
import io.taraxacum.finaltech.abstractItem.MachineMenu;

import javax.annotation.Nonnull;

public class UnOrderedDustFactoryMenu extends MachineMenu {
    private static final int[] BORDER = new int[] {3, 4, 5, 12,     14, 21, 22, 23, 30, 31, 32};
    private static final int[] INPUT_BORDER = new int[] {0, 1, 2, 11, 20, 27, 28, 29};
    private static final int[] OUTPUT_BORDER = new int[] {6, 7, 8, 15, 24, 33, 34, 35};
    private static final int[] INPUT_SLOTS = new int[] {9, 10, 18, 19};
    private static final int[] OUTPUT_SLOTS = new int[] {16, 17, 25, 26};

    public UnOrderedDustFactoryMenu(@Nonnull String id, @Nonnull String title, @Nonnull FinalMachine finalMachine) {
        super(id, title, finalMachine);
    }

    @Override
    public int[] getBorder() {
        return this.BORDER;
    }

    @Override
    public int[] getInputBorder() {
        return this.INPUT_BORDER;
    }

    @Override
    public int[] getOutputBorder() {
        return this.OUTPUT_BORDER;
    }

    @Override
    public int[] getInputSlots() {
        return this.INPUT_SLOTS;
    }

    @Override
    public int[] getOutputSlots() {
        return this.OUTPUT_SLOTS;
    }
}
