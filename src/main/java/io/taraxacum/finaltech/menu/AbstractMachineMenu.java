package io.taraxacum.finaltech.menu;

import io.github.thebusybiscuit.slimefun4.implementation.Slimefun;
import io.github.thebusybiscuit.slimefun4.libraries.dough.protection.Interaction;
import io.github.thebusybiscuit.slimefun4.utils.ChestMenuUtils;
import io.taraxacum.finaltech.machine.AbstractMachine;
import io.taraxacum.finaltech.util.menu.Icon;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenuPreset;
import me.mrCookieSlime.Slimefun.api.inventory.DirtyChestMenu;
import me.mrCookieSlime.Slimefun.api.item_transport.ItemTransportFlow;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;

/**
 * @author Final_ROOT
 */
public abstract class AbstractMachineMenu extends BlockMenuPreset {
    private final AbstractMachine machine;

    public AbstractMachineMenu(@Nonnull String id, @Nonnull String title, @Nonnull AbstractMachine machine) {
        super(id, title);
        this.machine = machine;
    }

    public AbstractMachineMenu(@Nonnull AbstractMachine machine) {
        this(machine.getId(), machine.getItemName(), machine);
    }

    /**
     * 机器边框位置
     * @return
     */
    public abstract int[] getBorder();

    /**
     * 机器的输入侧边框位置
     * @return
     */
    public abstract int[] getInputBorder();

    /**
     * 机器的输出侧边框位置
     * @return
     */
    public abstract int[] getOutputBorder();

    /**
     * 机器的输入槽位置
     * @return
     */
    public abstract int[] getInputSlots();

    /**
     * 机器的输出槽位置
     * @return
     */
    public abstract int[] getOutputSlots();

    @Override
    public void newInstance(BlockMenu menu, Block block) {
        super.newInstance(menu, block);
        updateMenu(menu, block);
    }

    @Override
    public void init() {
        for (int slot : getBorder()) {
            this.addItem(slot, Icon.BORDER_ICON);
            this.addMenuClickHandler(slot, ChestMenuUtils.getEmptyClickHandler());
        }
        for (int slot : getInputBorder()) {
            this.addItem(slot, Icon.INPUT_BORDER_ICON);
            this.addMenuClickHandler(slot, ChestMenuUtils.getEmptyClickHandler());
        }
        for (int slot : getOutputBorder()) {
            this.addItem(slot, Icon.OUTPUT_BORDER_ICON);
            this.addMenuClickHandler(slot, ChestMenuUtils.getEmptyClickHandler());
        }
    }

    @Override
    public boolean canOpen(@Nonnull Block block, @Nonnull Player player) {
        return player.hasPermission("slimefun.inventory.bypass") || machine.canUse(player, false) && Slimefun.getProtectionManager().hasPermission(player, block.getLocation(), Interaction.INTERACT_BLOCK);
    }

    @Override
    public int[] getSlotsAccessedByItemTransport(ItemTransportFlow itemTransportFlow) {
        if (itemTransportFlow.equals(ItemTransportFlow.WITHDRAW)) {
            return getOutputSlots();
        } else if (itemTransportFlow.equals(ItemTransportFlow.INSERT)) {
            return getInputSlots();
        }
        return new int[0];
    }

    @Override
    public int[] getSlotsAccessedByItemTransport(DirtyChestMenu menu, ItemTransportFlow flow, ItemStack item) {
        return getSlotsAccessedByItemTransport(flow);
    }

    /**
     * 更新机器菜单内容
     * 通常，在服务器重启时、放下方块时，会调用该方法
     * @param menu
     * @param block
     */
    public abstract void updateMenu(BlockMenu menu, Block block);
}
