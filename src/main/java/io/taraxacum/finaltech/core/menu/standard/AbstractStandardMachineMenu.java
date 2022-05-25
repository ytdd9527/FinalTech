package io.taraxacum.finaltech.core.menu.standard;

import io.github.thebusybiscuit.slimefun4.utils.ChestMenuUtils;
import io.github.thebusybiscuit.slimefun4.utils.itemstack.ItemStackWrapper;
import io.taraxacum.finaltech.core.items.machine.AbstractMachine;
import io.taraxacum.finaltech.core.menu.AbstractMachineMenu;
import io.taraxacum.finaltech.util.ItemStackUtil;
import io.taraxacum.finaltech.core.storage.MachineMaxStack;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import me.mrCookieSlime.Slimefun.api.inventory.DirtyChestMenu;
import me.mrCookieSlime.Slimefun.api.item_transport.ItemTransportFlow;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;
import java.util.ArrayList;

/**
 * @author Final_ROOT
 */
public abstract class AbstractStandardMachineMenu extends AbstractMachineMenu {
    public static final int MACHINE_MAX_STACK_SLOT = 13;

    public AbstractStandardMachineMenu(@Nonnull AbstractMachine abstractMachine) {
        super(abstractMachine);
    }

    @Override
    public void init() {
        super.init();
        this.addItem(MACHINE_MAX_STACK_SLOT, MachineMaxStack.ICON);
        this.addMenuClickHandler(MACHINE_MAX_STACK_SLOT, ChestMenuUtils.getEmptyClickHandler());
    }

    @Override
    public void newInstance(@Nonnull BlockMenu blockMenu, @Nonnull Block block) {
        super.newInstance(blockMenu, block);
        blockMenu.addMenuClickHandler(MACHINE_MAX_STACK_SLOT, MachineMaxStack.getHandler(blockMenu, block, this, MACHINE_MAX_STACK_SLOT));
    }

    @Override
    public int[] getSlotsAccessedByItemTransport(DirtyChestMenu menu, ItemTransportFlow flow, ItemStack item) {
        if (ItemTransportFlow.WITHDRAW.equals(flow)) {
            return this.getOutputSlot();
        } else if (flow == null) {
            return new int[0];
        }

        int full = 0;
        if (menu.getItemInSlot(MACHINE_MAX_STACK_SLOT).getType().equals(Material.CHEST)) {
            return this.getInputSlot();
        }

        ArrayList<Integer> itemList = new ArrayList<>();
        ArrayList<Integer> nullList = new ArrayList<>();
        ItemStackWrapper itemStackWrapper = ItemStackWrapper.wrap(item);
        int inputLimit = menu.getItemInSlot(MACHINE_MAX_STACK_SLOT).getAmount();
        for (int slot : this.getInputSlot()) {
            ItemStack existedItem = menu.getItemInSlot(slot);
            if (ItemStackUtil.isItemNull(existedItem)) {
                nullList.add(slot);
            } else if (ItemStackUtil.isItemSimilar(itemStackWrapper, existedItem)) {
                if (existedItem.getAmount() < existedItem.getMaxStackSize()) {
                    itemList.add(slot);
                } else {
                    full++;
                }
                if(itemList.size() + full >= inputLimit) {
                    break;
                }
            }
        }

        int[] slots = new int[Math.max(inputLimit - full, 0)];
        int i;
        for (i = 0; i < itemList.size() && i < slots.length; i++) {
            slots[i] = itemList.get(i);
        }
        for (int j = 0; j < nullList.size() && j < slots.length - i; j++) {
            slots[i + j] = nullList.get(j);
        }
        return slots;
    }

    @Override
    protected void updateMenu(@Nonnull BlockMenu blockMenu, @Nonnull Block block) {
        MachineMaxStack.HELPER.checkOrSetBlockStorage(block.getLocation());
        String quantity = MachineMaxStack.HELPER.getOrDefaultValue(block.getLocation());
        ItemStack item = blockMenu.getItemInSlot(MACHINE_MAX_STACK_SLOT);
        MachineMaxStack.HELPER.setIcon(item, quantity);
    }
}
