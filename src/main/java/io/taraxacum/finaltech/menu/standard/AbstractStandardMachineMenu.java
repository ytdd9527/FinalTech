package io.taraxacum.finaltech.menu.standard;

import io.github.thebusybiscuit.slimefun4.libraries.dough.items.CustomItemStack;
import io.github.thebusybiscuit.slimefun4.utils.ChestMenuUtils;
import io.taraxacum.finaltech.machine.AbstractMachine;
import io.taraxacum.finaltech.menu.AbstractMachineMenu;
import io.taraxacum.finaltech.util.ItemStackUtil;
import io.taraxacum.finaltech.util.cargo.Icon;
import me.mrCookieSlime.Slimefun.api.BlockStorage;
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
    public static final int QUANTITY_SLOT = 13;

    public AbstractStandardMachineMenu(@Nonnull String id, @Nonnull String title, AbstractMachine abstractMachine) {
        super(id, title, abstractMachine);
    }

    @Override
    public void init() {
        super.init();
        this.addItem(QUANTITY_SLOT, new CustomItemStack(Icon.MACHINE_MAX_STACK_ICON));
        this.addMenuClickHandler(QUANTITY_SLOT, ChestMenuUtils.getEmptyClickHandler());
    }

    @Override
    public void newInstance(BlockMenu blockMenu, Block block) {
        super.newInstance(blockMenu, block);
        blockMenu.addMenuClickHandler(QUANTITY_SLOT, (player, i, itemStack, clickAction) -> {
            int quantity = Integer.parseInt(BlockStorage.getLocationInfo(block.getLocation(), Icon.MACHINE_MAX_STACK));
            quantity = (quantity + 1) % (getInputSlots().length / 2 + 1);
            ItemStack item = blockMenu.getItemInSlot(QUANTITY_SLOT);
            if (quantity == 0) {
                ItemStackUtil.setLastLore(item, "§7未限制");
                item.setType(Material.CHEST);
                item.setAmount(1);
            } else {
                ItemStackUtil.setLastLore(item, "§7限制数量=" + quantity);
                item.setType(Material.HOPPER);
                item.setAmount(quantity);
            }
            BlockStorage.addBlockInfo(block, Icon.MACHINE_MAX_STACK, String.valueOf(quantity));
            return false;
        });
    }

    @Override
    public int[] getSlotsAccessedByItemTransport(ItemTransportFlow itemTransportFlow) {
        if (itemTransportFlow.equals(ItemTransportFlow.WITHDRAW)) {
            return getOutputSlots();
        } else if(itemTransportFlow.equals(ItemTransportFlow.INSERT)) {
            return getInputSlots();
        }
        return new int[0];
    }

    @Override
    public int[] getSlotsAccessedByItemTransport(DirtyChestMenu menu, ItemTransportFlow flow, ItemStack item) {
        if (flow.equals(ItemTransportFlow.WITHDRAW)) {
            return getOutputSlots();
        } else if(flow == null) {
            return new int[0];
        }

        ArrayList<Integer> itemList = new ArrayList<>();
        ArrayList<Integer> nullList = new ArrayList<>();

        int full = 0;
        if(menu.getItemInSlot(QUANTITY_SLOT) == null) {
            menu.addItem(QUANTITY_SLOT, new CustomItemStack(Icon.MACHINE_MAX_STACK_ICON));
        }
        if(menu.getItemInSlot(QUANTITY_SLOT).getType().equals(Material.CHEST)) {
            return getInputSlots();
        }

        int inputLimit = menu.getItemInSlot(QUANTITY_SLOT).getAmount();
        for (int slot : getInputSlots()) {
            ItemStack existedItem = menu.getItemInSlot(slot);
            if(existedItem == null) {
                nullList.add(slot);
            } else if (ItemStackUtil.isItemSimilar(item, existedItem)) {
                if (existedItem.getAmount() < existedItem.getMaxStackSize()) {
                    itemList.add(slot);
                } else {
                    full++;
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
    public void updateMenu(BlockMenu blockMenu, Block block) {
        if(BlockStorage.getLocationInfo(block.getLocation(), Icon.MACHINE_MAX_STACK) == null) {
            BlockStorage.addBlockInfo(block.getLocation(), Icon.MACHINE_MAX_STACK, "0");
        }
        int quantity = Integer.parseInt(BlockStorage.getLocationInfo(block.getLocation(), Icon.MACHINE_MAX_STACK));
        ItemStack item = blockMenu.getItemInSlot(QUANTITY_SLOT);
        if(quantity == 0) {
            item.setType(Material.CHEST);
            item.setAmount(1);
            ItemStackUtil.setLastLore(item, "§7未限制");
        } else {
            item.setType(Material.HOPPER);
            item.setAmount(quantity);
            ItemStackUtil.setLastLore(item, "§7限制数量=" + quantity);
        }
    }
}
