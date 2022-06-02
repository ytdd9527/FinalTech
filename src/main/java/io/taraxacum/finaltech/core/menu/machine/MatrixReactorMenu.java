package io.taraxacum.finaltech.core.menu.machine;

import io.github.thebusybiscuit.slimefun4.libraries.dough.items.CustomItemStack;
import io.github.thebusybiscuit.slimefun4.utils.ChestMenuUtils;
import io.taraxacum.finaltech.core.items.unusable.ItemPhony;
import io.taraxacum.finaltech.core.items.machine.AbstractMachine;
import io.taraxacum.finaltech.core.menu.AbstractMachineMenu;
import io.taraxacum.finaltech.setup.FinalTechItems;
import io.taraxacum.finaltech.util.ItemStackUtil;
import io.taraxacum.finaltech.core.helper.Icon;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import me.mrCookieSlime.Slimefun.api.inventory.DirtyChestMenu;
import me.mrCookieSlime.Slimefun.api.item_transport.ItemTransportFlow;
import org.bukkit.block.Block;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;

/**
 * @author Final_ROOT
 * @since 2.0
 */
public class MatrixReactorMenu extends AbstractMachineMenu {
    private static final int[] BORDER = new int[] {0, 2, 3, 5, 6, 8, 12, 13, 14, 21, 23};
    private static final int[] INPUT_BORDER = new int[] {9, 10, 11, 18, 20, 27, 29, 36, 38, 45, 46, 47, 15, 16, 17, 24, 26, 33, 35, 42, 44, 51, 52, 53};
    private static final int[] OUTPUT_BORDER = new int[] {30, 31, 32, 39, 41, 48, 50};
    private static final int[] INPUT_SLOT = new int[] {19, 28, 37, 25, 34, 43, 22};
    private static final int[] OUTPUT_SLOT = new int[] {40};

    private static final int ORDERED_DUST_SLOT = 1;
    private static final ItemStack ORDERED_DUST_ICON = new CustomItemStack(FinalTechItems.UNORDERED_DUST);
    public static final int[] ORDERED_DUST_INPUT_SLOT = new int[] {25, 34, 43};

    private static final int UNORDERED_DUST_SLOT = 7;
    private static final ItemStack UNORDERED_DUST_ICON = new CustomItemStack(FinalTechItems.ORDERED_DUST);
    public static final int[] UNORDERED_DUST_INPUT_SLOT = new int[] {19, 28, 37};

    public static final int[] ITEM_PHONY_INPUT_SLOT = new int[] {4};
    public static final int[] OTHER_ITEM_INPUT_SLOT = new int[] {22};

    public static final int STATUS_SLOT = 49;

    public MatrixReactorMenu(@Nonnull AbstractMachine machine) {
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
        this.addItem(ORDERED_DUST_SLOT, ORDERED_DUST_ICON);
        this.addMenuClickHandler(ORDERED_DUST_SLOT, ChestMenuUtils.getEmptyClickHandler());
        this.addItem(UNORDERED_DUST_SLOT, UNORDERED_DUST_ICON);
        this.addMenuClickHandler(UNORDERED_DUST_SLOT, ChestMenuUtils.getEmptyClickHandler());
        this.addItem(STATUS_SLOT, Icon.STATUS_ICON);
        this.addMenuClickHandler(STATUS_SLOT, ChestMenuUtils.getEmptyClickHandler());
    }

    @Override
    public void newInstance(@Nonnull BlockMenu blockMenu, @Nonnull Block block) {
        super.newInstance(blockMenu, block);
    }

    @Override
    public int[] getSlotsAccessedByItemTransport(ItemTransportFlow itemTransportFlow) {
        return super.getSlotsAccessedByItemTransport(itemTransportFlow);
    }

    @Override
    public int[] getSlotsAccessedByItemTransport(DirtyChestMenu menu, ItemTransportFlow flow, ItemStack item) {
        if (ItemTransportFlow.WITHDRAW.equals(flow)) {
            return OUTPUT_SLOT;
        } else if (flow == null) {
            return new int[0];
        }
        if (ItemStackUtil.isItemSimilar(item, FinalTechItems.ORDERED_DUST)) {
            return ORDERED_DUST_INPUT_SLOT;
        } else if (ItemStackUtil.isItemSimilar(item, FinalTechItems.UNORDERED_DUST)) {
            return UNORDERED_DUST_INPUT_SLOT;
        } else if (ItemPhony.isValid(item)) {
            return ITEM_PHONY_INPUT_SLOT;
        } else {
            return OTHER_ITEM_INPUT_SLOT;
        }
    }

    @Override
    protected void updateMenu(@Nonnull BlockMenu blockMenu, @Nonnull Block block) {

    }
}
