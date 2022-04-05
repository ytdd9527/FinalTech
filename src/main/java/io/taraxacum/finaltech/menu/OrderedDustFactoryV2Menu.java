package io.taraxacum.finaltech.menu;

import io.taraxacum.finaltech.machine.AbstractMachine;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import org.bukkit.block.Block;

import javax.annotation.Nonnull;

/**
 * @author Final_ROOT
 */
public class OrderedDustFactoryV2Menu extends AbstractMachineMenu{
    public static final int[] INPUT_BORDER = new int[] {6, 15, 24, 33};
    public static final int[] OUTPUT_BORDER = new int[] {7, 16, 25, 34};
    public static final int[] INPUT_SLOT = new int[] {
             0,  1,  2,  3,  4,  5,
             9, 10, 11, 12, 13, 14,
            18, 19, 20, 21, 22, 23,
            27, 28, 29, 30, 31, 32};
    public static final int[] OUTPUT_SLOT = new int[] {8, 17, 26, 35};
    public OrderedDustFactoryV2Menu(@Nonnull String id, @Nonnull String title, AbstractMachine machine) {
        super(id, title, machine);
    }

    @Override
    public int[] getBorder() {
        return new int[0];
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
        return INPUT_SLOT;
    }

    @Override
    public int[] getOutputSlots() {
        return OUTPUT_SLOT;
    }

    @Override
    public void updateMenu(BlockMenu menu, Block block) {

    }
}
