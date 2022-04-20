package io.taraxacum.finaltech.menu;

import io.github.thebusybiscuit.slimefun4.libraries.dough.items.CustomItemStack;
import io.github.thebusybiscuit.slimefun4.utils.ChestMenuUtils;
import io.taraxacum.finaltech.machine.AbstractMachine;
import io.taraxacum.finaltech.util.menu.Icon;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;

/**
 * @author Final_ROOT
 */
public class ElectricCapacitorMenu extends AbstractMachineMenu {
    private static final int[] BORDER = new int[] {0, 1, 2, 3, 5, 6, 7, 8};
    public static final int INFO_SLOT = 4;

    public static final ItemStack CAPACITOR_ICON = new CustomItemStack(Material.YELLOW_STAINED_GLASS_PANE, "&e存电量",
            "&7当前存电量= &60J");

    public ElectricCapacitorMenu(@Nonnull String id, @Nonnull String title, AbstractMachine machine) {
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
    public void init() {
        this.addItem(INFO_SLOT, CAPACITOR_ICON);
        this.addMenuClickHandler(INFO_SLOT, ChestMenuUtils.getEmptyClickHandler());
        for (int slot : BORDER) {
            this.addItem(slot, Icon.BORDER_ICON);
            this.addMenuClickHandler(slot, ChestMenuUtils.getEmptyClickHandler());
        }
    }

    @Override
    public void updateMenu(BlockMenu menu, Block block) {}
}
