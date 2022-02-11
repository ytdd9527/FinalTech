package io.taraxacum.finaltech.menu;

import io.taraxacum.finaltech.abstractItem.machine.AbstractMachine;
import io.taraxacum.finaltech.abstractItem.menu.AbstractMachineMenu;
import io.taraxacum.finaltech.abstractItem.menu.AbstractStandardMachineMenu;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import org.bukkit.block.Block;

import javax.annotation.Nonnull;

/**
 * @author Final_ROOT
 */
public class AllFrameMachineMenu extends AbstractStandardMachineMenu {
    private static final int[] BORDER = new int[]{0, 1, 2, 6, 7, 8};
    private static final int[] INPUT_BORDER = new int[]{3};
    private static final int[] OUTPUT_BORDER = new int[]{5};
    private static final int[] INPUT_SLOTS = new int[]{4};
    private static final int[] OUTPUT_SLOTS = new int[]{4};

    public static final int MACHINE_SLOT = 4;

    public AllFrameMachineMenu(@Nonnull String id, @Nonnull String title, @Nonnull AbstractMachine machine) {
        super(id, title, machine);
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
