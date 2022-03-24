package io.taraxacum.finaltech.menu.storage;

import io.taraxacum.finaltech.machine.AbstractMachine;
import io.taraxacum.finaltech.menu.AbstractMachineMenu;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import org.bukkit.block.Block;

import javax.annotation.Nonnull;

public class StorageInsertPortMenu extends AbstractMachineMenu {
    public static final int[] INPUT_SLOT = new int[]{
            0,  1,  2,  3,  4,  5,  6,  7,  8,
            9, 10, 11, 12, 13, 14, 15, 16, 17,
            18, 19, 20, 21, 22, 23, 24, 25, 26,
            27, 28, 29, 30, 31, 32, 33, 34, 35,
            36, 37, 38, 39, 40, 41, 42, 43, 44,
            45, 46, 47, 48, 49, 50, 51, 52, 53
    };

    public StorageInsertPortMenu(@Nonnull String id, @Nonnull String title, AbstractMachine machine) {
        super(id, title, machine);
    }

    @Override
    public int[] getBorder() {
        return new int[0];
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
        return INPUT_SLOT;
    }

    @Override
    public int[] getOutputSlots() {
        return new int[0];
    }

    @Override
    public void init() {
        super.init();
        this.setSize(54);
    }

    @Override
    protected void updateMenu(BlockMenu menu, Block block) {

    }
}
