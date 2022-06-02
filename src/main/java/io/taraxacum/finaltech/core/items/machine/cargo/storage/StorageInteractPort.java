package io.taraxacum.finaltech.core.items.machine.cargo.storage;

import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.libraries.paperlib.PaperLib;
import io.github.thebusybiscuit.slimefun4.utils.itemstack.ItemStackWrapper;
import io.taraxacum.finaltech.api.interfaces.PerformanceLimitMachine;
import io.taraxacum.finaltech.api.interfaces.RecipeItem;
import io.taraxacum.finaltech.api.dto.ItemStackWithWrapper;
import io.taraxacum.finaltech.core.items.unusable.StorageCardItem;
import io.taraxacum.finaltech.core.items.machine.cargo.AbstractCargo;
import io.taraxacum.finaltech.core.menu.AbstractMachineMenu;
import io.taraxacum.finaltech.core.menu.machine.StorageInteractPortMenu;
import io.taraxacum.finaltech.util.*;
import me.mrCookieSlime.CSCoreLibPlugin.Configuration.Config;
import me.mrCookieSlime.Slimefun.api.BlockStorage;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.BlockState;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import javax.annotation.Nonnull;
import java.util.*;

/**
 * @author Final_ROOT
 * @since 1.0
 */
public class StorageInteractPort extends AbstractCargo implements PerformanceLimitMachine, RecipeItem {
    private static final int SEARCH_LIMIT = 3;

    public StorageInteractPort(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
        super(itemGroup, item, recipeType, recipe);
    }

    @Nonnull
    @Override
    protected AbstractMachineMenu setMachineMenu() {
        return new StorageInteractPortMenu(this);
    }

    @Override
    protected void tick(Block block, @Nonnull SlimefunItem slimefunItem, @Nonnull Config config) {
        Block targetBlock = block.getRelative(BlockFace.UP);
        BlockMenu blockMenu = BlockStorage.getInventory(block);
        if (!BlockStorage.hasInventory(targetBlock)) {
            BlockState blockState = PaperLib.getBlockState(targetBlock, false).getState();
            if (blockState instanceof InventoryHolder) {
                Inventory inventory = ((InventoryHolder) blockState).getInventory();
                List<ItemStackWrapper> unOutputItem = new LinkedList<>();
                List<ItemStackWrapper> unInputItem = new LinkedList<>();
                for(int slot : this.getInputSlot()) {
                    ItemStack item = blockMenu.getItemInSlot(slot);
                    if(!ItemStackUtil.isItemNull(item) && !StorageCardItem.storableItem(item)) {
                        blockMenu.dropItems(block.getLocation(), slot);
                    }
                }
                boolean outputFull = MachineUtil.isFull(blockMenu.toInventory(), this.getOutputSlot()) || MachineUtil.itemCount(blockMenu.toInventory(), this.getOutputSlot()) >= this.getOutputSlot().length / 2;
                boolean inputEmpty = MachineUtil.isEmpty(blockMenu.toInventory(), this.getInputSlot()) || MachineUtil.itemCount(blockMenu.toInventory(), this.getInputSlot()) < this.getInputSlot().length / 2;
                if (outputFull && inputEmpty) {
                    return;
                }
                if (!outputFull) {
                    MachineUtil.stockSlots(blockMenu, this.getOutputSlot());
                }
                if (!inputEmpty) {
                    MachineUtil.stockSlots(blockMenu, this.getInputSlot());
                }
                int pushItemAmount = 0;
                List<ItemStackWithWrapper> storageCardItemList = new ArrayList<>(Math.min(inventory.getSize(), SEARCH_LIMIT));
                for (int i = 0, size = Math.min(inventory.getSize(), SEARCH_LIMIT); i < size; i++) {
                    ItemStack item = inventory.getItem(i);
                    if (ItemStackUtil.isItemNull(item) || !item.hasItemMeta()) {
                        continue;
                    }
                    ItemMeta itemMeta = item.getItemMeta();
                    if (StorageCardItem.isValid(itemMeta)) {
                        storageCardItemList.add(new ItemStackWithWrapper(item));
                        if (item.getAmount() == 1) {
                            pushItemAmount++;
                        }
                    }
                }
                for (ItemStackWithWrapper storageCardItem : storageCardItemList) {
                    if (inputEmpty && outputFull) {
                        continue;
                    }
                    ItemMeta itemMeta = storageCardItem.getItemStackWrapper().getItemMeta();
                    ItemStack stringItemStack = StringItemUtil.parseItemInCard(itemMeta);
                    ItemStackWithWrapper stringItem = stringItemStack == null ? null : new ItemStackWithWrapper(stringItemStack);
                    boolean work = true;
                    int pushCount = 0;
                    int stackCount = 0;
                    if (!outputFull && storageCardItem.getItemStack().getAmount() == 1 && stringItem != null) {
                        for (ItemStackWrapper unWorkItem : unOutputItem) {
                            if (ItemStackUtil.isItemSimilar(stringItem, unWorkItem)) {
                                work = false;
                                break;
                            }
                        }
                        if (work) {
                            pushItemAmount--;
                            pushCount = StringItemUtil.pushItemFromCard(itemMeta, stringItem, blockMenu.toInventory(), this.getOutputSlot());
                            if (pushCount == 0) {
                                unOutputItem.add(stringItem.getItemStackWrapper());
                            } else {
                                MachineUtil.stockSlots(blockMenu, this.getOutputSlot());
                                outputFull = MachineUtil.isFull(blockMenu.toInventory(), this.getOutputSlot());
                            }
                            if (pushItemAmount == 0) {
                                outputFull = true;
                            }
                        }
                    }
                    if (!inputEmpty) {
                        work = true;
                        if (stringItem != null) {
                            for (ItemStackWrapper unWorkItem : unInputItem) {
                                if (ItemStackUtil.isItemSimilar(stringItem, unWorkItem)) {
                                    work = false;
                                    break;
                                }
                            }
                        }
                        if (work) {
                            stackCount = StringItemUtil.storageItemToCard(itemMeta, stringItem, storageCardItem.getItemStack().getAmount(), blockMenu.toInventory(), this.getInputSlot());
                            if (stackCount == 0) {
                                if (stringItem != null) {
                                    unInputItem.add(stringItem.getItemStackWrapper());
                                }
                            } else {
                                inputEmpty = MachineUtil.isEmpty(blockMenu.toInventory(), this.getInputSlot());
                                if (stringItem == null) {
                                    stringItem = new ItemStackWithWrapper(StringItemUtil.parseItemInCard(itemMeta));
                                }
                            }
                        }
                    }
                    if (pushCount != 0 || stackCount != 0) {
                        StorageCardItem.updateLore(itemMeta, stringItem.getItemStack());
                        storageCardItem.getItemStack().setItemMeta(itemMeta);
                    }
                }
            }
        }
    }

    @Override
    protected boolean isSynchronized() {
        return true;
    }

    @Override
    public void registerDefaultRecipes() {
        this.registerDescriptiveRecipe(TextUtil.COLOR_PASSIVE + "存入物品",
                "",
                TextUtil.COLOR_NORMAL + "需在该机器上方放置原版容器",
                TextUtil.COLOR_NORMAL + "并将存储卡放于前 " + TextUtil.COLOR_NUMBER + SEARCH_LIMIT + "个" + TextUtil.COLOR_NORMAL  + " 格子",
                "",
                TextUtil.COLOR_NORMAL + "当该机器的输入槽有一半以上格子非空时",
                TextUtil.COLOR_NORMAL + "该机器会尝试将物品存入识别到的所有存储卡");
        this.registerDescriptiveRecipe(TextUtil.COLOR_PASSIVE + "取出物品",
                "",
                TextUtil.COLOR_NORMAL + "需在该机器上方放置原版容器",
                TextUtil.COLOR_NORMAL + "并将存储卡放于前 " + TextUtil.COLOR_NUMBER + SEARCH_LIMIT + "个" + TextUtil.COLOR_NORMAL  + " 格子",
                "",
                TextUtil.COLOR_NORMAL + "当该机器的输出槽有一半以上格子为空时",
                TextUtil.COLOR_NORMAL + "该机器会尝试将存储卡中的物品取出至输出槽");
    }
}
