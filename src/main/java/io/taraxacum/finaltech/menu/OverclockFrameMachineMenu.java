package io.taraxacum.finaltech.menu;

import io.taraxacum.finaltech.machine.AbstractMachine;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import org.bukkit.block.Block;

import javax.annotation.Nonnull;

/**
 * @author Final_ROOT
 */
public class OverclockFrameMachineMenu extends AbstractMachineMenu {
    private static final int[] BORDER = new int[]{0, 1, 2, 3, 5, 6, 7, 8};
    private static final int[] INPUT_SLOTS = new int[]{4};
    private static final int[] OUTPUT_SLOTS = new int[]{4};

    public static final int MACHINE_SLOT = 4;

    public OverclockFrameMachineMenu(@Nonnull String id, @Nonnull String title, @Nonnull AbstractMachine machine) {
        super(id, title, machine);
    }

    @Override
    public int[] getBorder() {
        return BORDER;
    }

    @Override
    public int[] getInputBorder() {
        return new int[0];
    }

    @Override
    public int[] getOutputBorder() {
        return new int[0];
    }

    @Override
    public int[] getInputSlots() {
        return INPUT_SLOTS;
    }

    @Override
    public int[] getOutputSlots() {
        return OUTPUT_SLOTS;
    }

    @Override
    public void updateMenu(BlockMenu menu, Block block) {}
}
