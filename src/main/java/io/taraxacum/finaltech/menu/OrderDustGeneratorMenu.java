package io.taraxacum.finaltech.menu;

import io.github.thebusybiscuit.slimefun4.libraries.dough.items.CustomItemStack;
import io.github.thebusybiscuit.slimefun4.utils.ChestMenuUtils;
import io.taraxacum.finaltech.machine.AbstractMachine;
import io.taraxacum.finaltech.machine.generator.OrderedDustGenerator;
import io.taraxacum.finaltech.util.ItemStackUtil;
import me.mrCookieSlime.Slimefun.api.BlockStorage;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;

public class OrderDustGeneratorMenu extends AbstractMachineMenu{
    public static final int[] INPUT_SLOT = new int[] {21, 22, 23};
    public static final int[] INPUT_BORDER = new int[] {11, 12, 13, 14, 15, 20, 24, 29, 30, 31, 32, 33};
    public static final int[] BORDER = new int[] {
             0,  1,  2,  3,      5,  6,  7,  8,
             9, 10,                     16, 17,
            18, 19,                     25, 26,
            27, 28,                     34, 35,
            36, 37, 38, 39, 40, 41, 42, 43, 44
    };

    public static final int INFO_SLOT = 4;

    public OrderDustGeneratorMenu(@Nonnull String id, @Nonnull String title, AbstractMachine machine) {
        super(id, title, machine);
    }

    public static final ItemStack INFO_ICON = new CustomItemStack(Material.GRASS_BLOCK, "&f状态",
            "",
            "");

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

        this.addItem(INFO_SLOT, INFO_ICON.clone());
        this.addMenuClickHandler(INFO_SLOT, ChestMenuUtils.getEmptyClickHandler());
    }

    @Override
    public void updateMenu(BlockMenu blockMenu, Block block) {
        ItemStack item = blockMenu.getItemInSlot(OrderDustGeneratorMenu.INFO_SLOT);
        ItemStackUtil.setLore(item,
                "§7当前发电量= §e" + BlockStorage.getLocationInfo(block.getLocation(), OrderedDustGenerator.KEY_COUNT) + "J/t",
                "§7最大发电量= §e" + BlockStorage.getLocationInfo(block.getLocation(), OrderedDustGenerator.KEY_MAX) + "J/t");

    }
}
