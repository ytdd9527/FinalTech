package io.taraxacum.finaltech.menu;

import io.github.thebusybiscuit.slimefun4.libraries.dough.items.CustomItemStack;
import io.github.thebusybiscuit.slimefun4.utils.ChestMenuUtils;
import io.taraxacum.finaltech.abstractItem.FinalMachine;
import io.taraxacum.finaltech.abstractItem.MachineMenu;
import io.taraxacum.finaltech.util.FinalUtil;

import javax.annotation.Nonnull;

public class AdvancedMachineMenu extends MachineMenu {
    private static final int[] BORDER = new int[]{3, 4, 5, 12, 14, 21, 23, 30, 32, 39, 40, 41, 48, 49, 50}; // 机器容器边框
    private static final int[] INPUT_BORDER = new int[]{2, 11, 20, 29, 38, 47}; // 机器输入槽边框
    private static final int[] OUTPUT_BORDER = new int[]{6, 15, 24, 33, 42, 51}; // 机器输出槽边框
    private static final int[] INPUT_SLOTS = new int[]{0, 1, 9, 10, 18, 19, 27, 28, 36, 37, 45, 46}; // 输入槽
    private static final int[] OUTPUT_SLOTS = new int[]{7, 8, 16, 17, 25, 26, 34, 35, 43, 44, 52, 53}; // 输出槽

    public static final int ICON_SLOT = 22;
    public static final int MODULE_SLOT = 31;

    public AdvancedMachineMenu(@Nonnull String id, @Nonnull String title, @Nonnull FinalMachine finalMachine) {
        super(id, title, finalMachine);
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

    @Override
    public void init() {
        super.init();
        this.addItem(ICON_SLOT, new CustomItemStack(FinalUtil.BORDER), ChestMenuUtils.getEmptyClickHandler());
    }
}
