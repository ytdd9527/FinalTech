package io.taraxacum.finaltech.core.menu.storage;

import io.taraxacum.finaltech.core.items.machine.AbstractMachine;
import io.taraxacum.finaltech.core.menu.AbstractMachineMenu;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import org.bukkit.block.Block;

import javax.annotation.Nonnull;

public class OneLineStorageUnitMenu extends AbstractMachineMenu {
    private static final int[] BORDER = new int[0];
    private static final int[] INPUT_BORDER = new int[0];
    private static final int[] OUTPUT_BORDER = new int[0];
    private static final int[] INPUT_SLOT = new int[] {0, 1, 2, 3, 4, 5, 6, 7, 8};
    private static final int[] OUTPUT_SLOT = new int[] {0, 1, 2, 3, 4, 5, 6, 7, 8};


    public OneLineStorageUnitMenu(@Nonnull AbstractMachine machine) {
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
    public void init() {
        super.init();
        setSize(9);
    }

    @Override
    protected void updateMenu(@Nonnull BlockMenu blockMenu, @Nonnull Block block) {

    }
}
