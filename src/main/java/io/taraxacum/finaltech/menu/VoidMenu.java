package io.taraxacum.finaltech.menu;

import io.taraxacum.finaltech.machine.AbstractMachine;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import org.bukkit.block.Block;

import javax.annotation.Nonnull;

/**
 * @author Final_ROOT
 */
public class VoidMenu extends AbstractMachineMenu{
    private static final int[] BORDER = new int[] {0, 1, 2, 3, 4 ,5 ,6 ,7, 8};
    public VoidMenu(@Nonnull String id, @Nonnull String title, AbstractMachine machine) {
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
        return new int[0];
    }

    @Override
    public int[] getOutputSlots() {
        return new int[0];
    }

    @Override
    public void updateMenu(BlockMenu menu, Block block) {

    }
}
