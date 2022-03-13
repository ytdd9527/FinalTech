package io.taraxacum.finaltech.machine.cargo;

import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.libraries.paperlib.PaperLib;
import io.github.thebusybiscuit.slimefun4.utils.itemstack.ItemStackWrapper;
import io.taraxacum.finaltech.FinalTech;
import io.taraxacum.finaltech.menu.AbstractMachineMenu;
import io.taraxacum.finaltech.menu.BasicLinkedStorageUnitMenu;
import io.taraxacum.finaltech.setup.FinalTechItems;
import io.taraxacum.finaltech.util.*;
import me.mrCookieSlime.CSCoreLibPlugin.Configuration.Config;
import me.mrCookieSlime.Slimefun.api.BlockStorage;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.BlockState;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * @author Final_ROOT
 */
public class StorageInteractPort extends AbstractCargo {
    public StorageInteractPort(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
        super(itemGroup, item, recipeType, recipe);
    }

    @Nonnull
    @Override
    protected AbstractMachineMenu setMachineMenu() {
        return new BasicLinkedStorageUnitMenu(this.getId(), this.getItemName(), this);
    }

    @Override
    protected void tick(Block block, @Nonnull SlimefunItem slimefunItem, @Nonnull Config config) {
        Block targetBlock = block.getRelative(BlockFace.UP);
        BlockMenu blockMenu = BlockStorage.getInventory(block);
        if(!BlockStorage.hasInventory(targetBlock)) {
            BlockState blockState = PaperLib.getBlockState(targetBlock, false).getState();
            if (blockState instanceof InventoryHolder) {
                Inventory inventory = ((InventoryHolder) blockState).getInventory();
                LinkedList<ItemStackWrapper> unPushItem = new LinkedList<>();
                LinkedList<ItemStackWrapper> unStackItem = new LinkedList<>();
                boolean isFull = MachineUtil.isFull(blockMenu.toInventory(), getOutputSlots());
                boolean isEmpty = MachineUtil.isEmpty(blockMenu.toInventory(), getInputSlots());
                MachineUtil.stockSlots(blockMenu, getOutputSlots());
                MachineUtil.stockSlots(blockMenu, getInputSlots());
                int pushItemAmount = 0;
                Map<ItemStack, ItemMeta> map = new HashMap<>(inventory.getSize());
                for(int i = 0; i < Math.min(inventory.getSize(), 9); i++) {
                    ItemStack item = inventory.getItem(i);
                    if(ItemStackUtil.isItemNull(item) || !item.hasItemMeta()) {
                        continue;
                    }
                    ItemMeta itemMeta = item.getItemMeta();
                    if(StringItemUtil.canStack(itemMeta)) {
                        map.put(item, itemMeta);
                        if(item.getAmount() == 1) {
                            pushItemAmount++;
                        }
                    }
                }
                for(Map.Entry<ItemStack, ItemMeta> entry : map.entrySet()) {
                    if(isEmpty && isFull) {
                        continue;
                    }
                    ItemMeta itemMeta = entry.getValue();
                    ItemStack stringItemStack = StringItemUtil.getItem(itemMeta);
                    ItemStackWithWrapper stringItem = stringItemStack == null ? null : new ItemStackWithWrapper(stringItemStack);
                    boolean work = true;
                    int pushCount = 0;
                    int stackCount = 0;
                    if(!isFull && entry.getKey().getAmount() == 1 && stringItem != null) {
                        for(ItemStackWrapper unWorkItem : unPushItem) {
                            if(ItemStackUtil.isItemSimilar(stringItem.getItemStackWrapper(), unWorkItem)) {
                                work = false;
                                break;
                            }
                        }
                        if(work) {
                            pushItemAmount--;
                            pushCount = StringItemUtil.pushItem(itemMeta, stringItem, blockMenu.toInventory(), getOutputSlots());
                            if(pushCount == 0) {
                                unPushItem.add(stringItem.getItemStackWrapper());
                            } else {
                                isFull = MachineUtil.isFull(blockMenu.toInventory(), getOutputSlots());
                                if(!isFull) {
                                    MachineUtil.stockSlots(blockMenu, getOutputSlots());
                                }
                            }
                            if(pushItemAmount == 0) {
                                isFull = true;
                            }
                        }
                    }
                    if(!isEmpty){
                        work = true;
                        if(stringItem != null) {
                            for(ItemStackWrapper unWorkItem : unStackItem) {
                                if(ItemStackUtil.isItemSimilar(stringItem.getItemStackWrapper(), unWorkItem)) {
                                    work = false;
                                    break;
                                }
                            }
                        }
                        if(work) {
                            stackCount = StringItemUtil.stackItem(itemMeta, stringItem, entry.getKey().getAmount(), blockMenu.toInventory(), getInputSlots());
                            if(stackCount == 0) {
                                if(stringItem != null) {
                                    unStackItem.add(stringItem.getItemStackWrapper());
                                }
                            } else {
                                isEmpty = MachineUtil.isEmpty(blockMenu.toInventory(), getInputSlots());
                                if(!isEmpty) {
                                    MachineUtil.stockSlots(blockMenu, getInputSlots());
                                }
                                if(stringItem == null) {
                                    stringItem = new ItemStackWithWrapper(StringItemUtil.getItem(itemMeta));
                                }
                            }
                        }
                    }
                    if(pushCount != 0 || stackCount != 0) {
                        StringItemUtil.updateStorageLore(itemMeta, stringItem.getItemStack());
                        entry.getKey().setItemMeta(itemMeta);
                        StringItemUtil.updateStorageItemType(entry.getKey(), itemMeta);
                    }
                }
//                for(int i = 0; i < inventory.getSize(); i++) {
//                    if(isEmpty && isFull) {
//                        continue;
//                    }
//                    ItemStack item = inventory.getItem(i);
//                    if(ItemStackUtil.isItemNull(item) || !item.hasItemMeta()) {
//                        continue;
//                    }
//                    ItemMeta itemMeta = item.getItemMeta();
//                    if(canStack(itemMeta, item.getType())) {
//                        ItemStack stringItemStack = getItem(itemMeta);
//                        ItemStackWithWrapper stringItem = stringItemStack == null ? null : new ItemStackWithWrapper(stringItemStack);
//                        boolean work = true;
//                        int pushCount = 0;
//                        int stackCount = 0;
//                        if(!isFull && item.getAmount() == 1 && stringItem != null) {
//                            for(ItemStackWrapper unWorkItem : unPushItem) {
//                                if(ItemStackUtil.isItemSimilar(stringItem.getItemStackWrapper(), unWorkItem)) {
//                                    work = false;
//                                    break;
//                                }
//                            }
//                            if(work) {
//                                pushCount = pushItem(itemMeta, blockMenu.toInventory(), getOutputSlots());
//                                if(pushCount == 0) {
//                                    unPushItem.add(stringItem.getItemStackWrapper());
//                                } else {
//                                    isFull = MachineUtil.isFull(blockMenu.toInventory(), getOutputSlots());
//                                    if(!isFull) {
//                                        MachineUtil.stockSlots(blockMenu, getOutputSlots());
//                                    }
//                                }
//                            }
//                        }
//                        if(!isEmpty){
//                            work = true;
//                            if(stringItem != null) {
//                                for(ItemStackWrapper unWorkItem : unStackItem) {
//                                    if(ItemStackUtil.isItemSimilar(stringItem.getItemStackWrapper(), unWorkItem)) {
//                                        work = false;
//                                        break;
//                                    }
//                                }
//                            }
//                            if(work) {
//                                stackCount = stackItem(itemMeta, item.getAmount(), blockMenu.toInventory(), getInputSlots());
//                                if(stackCount == 0) {
//                                    if(stringItem != null) {
//                                        unStackItem.add(stringItem.getItemStackWrapper());
//                                    }
//                                } else {
//                                    isEmpty = MachineUtil.isEmpty(blockMenu.toInventory(), getInputSlots());
//                                    if(!isEmpty) {
//                                        MachineUtil.stockSlots(blockMenu, getInputSlots());
//                                    }
//                                }
//                            }
//                        }
//                        if(pushCount != 0 || stackCount != 0) {
//                            updateStorageLore(itemMeta);
//                            item.setItemMeta(itemMeta);
//                        }
//                    }
//                }
            }
        }
    }
}
