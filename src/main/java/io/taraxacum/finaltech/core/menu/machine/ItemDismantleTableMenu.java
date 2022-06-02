package io.taraxacum.finaltech.core.menu.machine;

import io.taraxacum.finaltech.core.items.machine.AbstractMachine;
import io.taraxacum.finaltech.core.menu.AbstractMachineMenu;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import org.bukkit.block.Block;

import javax.annotation.Nonnull;

/**
 * @author Final_ROOT
 * @since 2.0
 */
public class ItemDismantleTableMenu extends AbstractMachineMenu {
    private static final int[] BORDER = new int[] {3, 4, 12, 13, 21, 22};
    private static final int[] INPUT_BORDER = new int[] {0, 1, 2, 9, 11, 18, 19, 20};
    private static final int[] OUTPUT_BORDER = new int[] {5, 14, 23};
    private static final int[] INPUT_SLOT = new int[] {10};
    private static final int[] OUTPUT_SLOT = new int[] {6, 7, 8, 15, 16, 17, 24, 25, 26};

    public ItemDismantleTableMenu(@Nonnull AbstractMachine machine) {
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
    protected void updateMenu(@Nonnull BlockMenu blockMenu, @Nonnull Block block) {

    }
}
