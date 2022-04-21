package io.taraxacum.finaltech.menu.special;

import io.github.thebusybiscuit.slimefun4.libraries.dough.items.CustomItemStack;
import io.github.thebusybiscuit.slimefun4.utils.ChestMenuUtils;
import io.taraxacum.finaltech.machine.AbstractMachine;
import io.taraxacum.finaltech.menu.AbstractMachineMenu;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;

/**
 * @author Final_ROOT
 */
public class OrderDustGeneratorMenu extends AbstractMachineMenu {
    private static final int[] BORDER = new int[] {0, 1, 2, 3, 5, 6, 7, 8, 9, 10, 16, 17, 18, 19, 25, 26, 27, 28, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44};
    private static final int[] INPUT_BORDER = new int[] {11, 12, 13, 14, 15, 20, 24, 29, 30, 31, 32, 33};
    private static final int[] OUTPUT_BORDER = new int[0];
    private static final int[] INPUT_SLOT = new int[] {21, 22, 23};
    private static final int[] OUTPUT_SLOT = new int[0];

    public static final int STATUS_SLOT = 4;

    public OrderDustGeneratorMenu(@Nonnull AbstractMachine machine) {
        super(machine);
    }

    public static final ItemStack INFO_ICON = new CustomItemStack(Material.GRASS_BLOCK, "&f状态");

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
        this.addItem(STATUS_SLOT, INFO_ICON);
        this.addMenuClickHandler(STATUS_SLOT, ChestMenuUtils.getEmptyClickHandler());
    }

    @Override
    public void updateMenu(@Nonnull BlockMenu blockMenu, @Nonnull Block block) {

    }
}
