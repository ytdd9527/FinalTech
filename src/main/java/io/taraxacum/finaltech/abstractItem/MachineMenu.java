package io.taraxacum.finaltech.abstractItem;

import io.github.thebusybiscuit.slimefun4.implementation.Slimefun;
import io.github.thebusybiscuit.slimefun4.libraries.dough.items.CustomItemStack;
import io.github.thebusybiscuit.slimefun4.libraries.dough.protection.Interaction;
import io.github.thebusybiscuit.slimefun4.utils.ChestMenuUtils;
import io.github.thebusybiscuit.slimefun4.utils.SlimefunUtils;
import io.taraxacum.finaltech.util.FinalUtil;
import me.mrCookieSlime.Slimefun.api.BlockStorage;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenuPreset;
import me.mrCookieSlime.Slimefun.api.inventory.DirtyChestMenu;
import me.mrCookieSlime.Slimefun.api.item_transport.ItemTransportFlow;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

public abstract class MachineMenu extends BlockMenuPreset {
    private final FinalMachine finalMachine;

    private Location location;
    public static final int QUANTITY_SLOT = 13;

    public MachineMenu(@Nonnull String id, @Nonnull String title, FinalMachine finalMachine) {
        super(id, title);
        this.finalMachine = finalMachine;
    }

    public abstract int[] getBorder();

    public abstract int[] getInputBorder();

    public abstract int[] getOutputBorder();

    public abstract int[] getInputSlots();

    public abstract int[] getOutputSlots();

    @Override
    public void init() {
        for(int slot : getBorder()) {
            this.addItem(slot, FinalUtil.BORDER, ChestMenuUtils.getEmptyClickHandler());
        }
        for(int slot : getInputBorder()) {
            this.addItem(slot, FinalUtil.INPUT_BORDER, ChestMenuUtils.getEmptyClickHandler());
        }
        for(int slot : getOutputBorder()) {
            this.addItem(slot, FinalUtil.OUTPUT_BORDER, ChestMenuUtils.getEmptyClickHandler());
        }
        this.addItem(QUANTITY_SLOT, new CustomItemStack(FinalUtil.MACHINE_MAX_STACK_ICON, "&7输入数量限制", "&7未限制"));
    }

    @Override
    public void newInstance(BlockMenu blockMenu, Block block) {
        updateMenu(blockMenu, block);
        this.location = blockMenu.getLocation();
        blockMenu.addMenuClickHandler(QUANTITY_SLOT, (player, i, itemStack, clickAction) -> {
            int quantity = Integer.parseInt(BlockStorage.getLocationInfo(block.getLocation(), FinalUtil.MACHINE_MAX_STACK));
            quantity = (quantity + 1) % (getInputSlots().length / 2 + 1);
            ItemStack item = blockMenu.getItemInSlot(QUANTITY_SLOT);
            ItemMeta itemMeta = item.getItemMeta();
            List<String> lore = itemMeta.getLore();
            if (quantity == 0) {
                if(lore == null || lore.size() < 1) {
                    lore = new ArrayList<>();
                    lore.add(0, "§7未限制");
                } else {
                    lore.set(0, "§7未限制");
                }
                item.setType(Material.CHEST);
                item.setAmount(1);
            } else {
                if(lore == null || lore.size() < 1) {
                    lore = new ArrayList<>();
                    lore.add(0, "§7限制数量-" + quantity);
                } else {
                    lore.set(0, "§7限制数量-" + quantity);
                }
                item.setType(Material.HOPPER);
                item.setAmount(quantity);
            }
            itemMeta.setLore(lore);
            item.setItemMeta(itemMeta);
            BlockStorage.addBlockInfo(block, FinalUtil.MACHINE_MAX_STACK, String.valueOf(quantity));
            return false;
        });
    }

    @Override
    public boolean canOpen(@Nonnull Block block, @Nonnull Player player) {
        return player.hasPermission("slimefun.inventory.bypass") ? true : finalMachine.canUse(player, false) && Slimefun.getProtectionManager().hasPermission(player, block.getLocation(), Interaction.INTERACT_BLOCK);
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
        int inputLimit = Integer.parseInt(BlockStorage.getLocationInfo(location, FinalUtil.MACHINE_MAX_STACK));
        if(inputLimit == 0) {
            return getInputSlots();
        }

        for (int slot : getInputSlots()) {
            ItemStack existedItem = menu.getItemInSlot(slot);
            if(existedItem == null) {
                nullList.add(slot);
            } else if (SlimefunUtils.isItemSimilar(item, existedItem, true, false)) {
                if (existedItem.getAmount() < existedItem.getMaxStackSize()) {
                    itemList.add(slot);
                } else {
                    full++;
                }
            }
        }

        int[] slots = new int[inputLimit - full > 0 ? inputLimit - full : 0];
        int i = 0;
        for (i = 0; i < itemList.size() && i < slots.length; i++) {
            slots[i] = itemList.get(i);
        }
        for (int j = 0; j < nullList.size() && j < slots.length - i; j++) {
            slots[i + j] = nullList.get(j);
        }

        return slots;
    }

    public void updateMenu(BlockMenu blockMenu, Block block) {
        if(BlockStorage.getLocationInfo(block.getLocation(), FinalUtil.MACHINE_MAX_STACK) == null) {
            BlockStorage.addBlockInfo(block.getLocation(), FinalUtil.MACHINE_MAX_STACK, "0");
        }
        int quantity = Integer.parseInt(BlockStorage.getLocationInfo(block.getLocation(), FinalUtil.MACHINE_MAX_STACK));
        ItemStack item = blockMenu.getItemInSlot(QUANTITY_SLOT);
        if(quantity == 0) {
            item.getItemMeta().setDisplayName("§7限制输入数量-未限制");
        } else {
            item.getItemMeta().setDisplayName("§7限制输入数量-" + quantity);
        }
    }
}
