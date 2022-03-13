package io.taraxacum.finaltech.menu.standard;

import io.taraxacum.finaltech.machine.AbstractMachine;
import io.taraxacum.finaltech.menu.standard.AbstractStandardMachineMenu;

import javax.annotation.Nonnull;

/**
 * @author Final_ROOT
 */
public class AllFactoryMenu extends AbstractStandardMachineMenu {
    private static final int[] BORDER = new int[]{3, 4, 5, 12, 14, 21, 22, 23};
    private static final int[] INPUT_BORDER = new int[]{30, 31, 32, 39, 41, 48, 49, 50};
    private static final int[] OUTPUT_BORDER = new int[]{2, 6, 11, 15, 20, 24, 29, 33, 38, 42, 47, 51};
    private static final int[] INPUT_SLOTS = new int[]{40};
    private static final int[] OUTPUT_SLOTS = new int[]{0, 1, 7, 8, 9, 10, 16, 17, 18, 19, 25, 26, 27, 28, 34, 35, 36, 37, 43, 44, 45, 46, 52, 53};

    public AllFactoryMenu(@Nonnull String id, @Nonnull String title, AbstractMachine abstractMachine) {
        super(id, title, abstractMachine);
    }

    @Override
    public int[] getBorder() {
        return BORDER;
    }

    @Override
    public int[] getInputBorder() {
        return INPUT_BORDER;
    }

    @Override
    public int[] getOutputBorder() {
        return OUTPUT_BORDER;
    }

    @Override
    public int[] getInputSlots() {
        return INPUT_SLOTS;
    }

    @Override
    public int[] getOutputSlots() {
        return OUTPUT_SLOTS;
    }
}
