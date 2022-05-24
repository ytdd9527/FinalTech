package io.taraxacum.finaltech.core.menu;

import io.github.thebusybiscuit.slimefun4.implementation.Slimefun;
import io.github.thebusybiscuit.slimefun4.libraries.dough.protection.Interaction;
import io.github.thebusybiscuit.slimefun4.utils.ChestMenuUtils;
import io.taraxacum.finaltech.core.items.machine.AbstractMachine;
import io.taraxacum.finaltech.core.storage.Icon;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenuPreset;
import me.mrCookieSlime.Slimefun.api.inventory.DirtyChestMenu;
import me.mrCookieSlime.Slimefun.api.item_transport.ItemTransportFlow;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;

/**
 * Should use with {@link AbstractMachine}
 * @author Final_ROOT
 */
public abstract class AbstractMachineMenu extends BlockMenuPreset {
    private final AbstractMachine machine;

    public AbstractMachineMenu(@Nonnull String id, @Nonnull String title, @Nonnull AbstractMachine machine) {
        super(id, title);
        this.machine = machine;
    }

    public AbstractMachineMenu(@Nonnull AbstractMachine machine) {
        super(machine.getId(), machine.getItemName());
        this.machine = machine;
    }

    @Override
    public void newInstance(@Nonnull BlockMenu blockMenu, @Nonnull Block block) {
        super.newInstance(blockMenu, block);
        this.updateMenu(blockMenu, block);
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
            return this.getOutputSlot();
        } else if (itemTransportFlow.equals(ItemTransportFlow.INSERT)) {
            return this.getInputSlot();
        }
        return new int[0];
    }

    @Override
    public int[] getSlotsAccessedByItemTransport(DirtyChestMenu menu, ItemTransportFlow flow, ItemStack item) {
        return this.getSlotsAccessedByItemTransport(flow);
    }

    /**
     * The border of the menu
     * These are slots that may have no function.
     * @return
     */
    protected abstract int[] getBorder();

    /**
     * The input border of the menu
     * These are slots that surround the input slots
     * so that player will know where is input slots
     * @return
     */
    protected abstract int[] getInputBorder();

    /**
     * The output border of the menu
     * These are slots that surround the output slots
     * so that player will know where is output slots
     * @return
     */
    protected abstract int[] getOutputBorder();

    /**
     * The input slot of the menu
     * These are slots that player may insert item
     * so that the machine of the menu can work
     * @return
     */
    public abstract int[] getInputSlot();

    /**
     * The output slot of the menu
     * These are slots that player may withdraw item
     * to get the output result of the machine
     * @return
     */
    public abstract int[] getOutputSlot();

    protected AbstractMachine getMachine() {
        return machine;
    }

    /**
     * Update the menu
     * May be used in some specific machine
     * @param blockMenu
     * @param block
     */
    protected abstract void updateMenu(@Nonnull BlockMenu blockMenu, @Nonnull Block block);
}
