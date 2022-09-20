package io.taraxacum.finaltech.core.items.machine.cargo.storage;

import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.libraries.paperlib.PaperLib;
import io.github.thebusybiscuit.slimefun4.utils.itemstack.ItemStackWrapper;
import io.taraxacum.common.util.JavaUtil;
import io.taraxacum.finaltech.FinalTech;
import io.taraxacum.finaltech.api.dto.ItemWrapper;
import io.taraxacum.finaltech.api.interfaces.RecipeItem;
import io.taraxacum.finaltech.core.items.unusable.StorageCardItem;
import io.taraxacum.finaltech.core.items.machine.cargo.AbstractCargo;
import io.taraxacum.finaltech.core.menu.AbstractMachineMenu;
import io.taraxacum.finaltech.core.menu.machine.StorageInteractPortMenu;
import io.taraxacum.finaltech.util.*;
import io.taraxacum.finaltech.util.slimefun.ConfigUtil;
import io.taraxacum.finaltech.util.slimefun.RecipeUtil;
import me.mrCookieSlime.CSCoreLibPlugin.Configuration.Config;
import me.mrCookieSlime.Slimefun.api.BlockStorage;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import org.bukkit.Bukkit;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.BlockState;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

import javax.annotation.Nonnull;
import java.util.*;
import java.util.concurrent.ExecutionException;

/**
 * @author Final_ROOT
 * @since 1.0
 */
public class StorageInteractPort extends AbstractCargo implements RecipeItem {
    private final int searchLimit = ConfigUtil.getOrDefaultItemSetting(3, this, "search-limit");

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
            JavaPlugin javaPlugin = this.getAddon().getJavaPlugin();
            boolean primaryThread = Bukkit.isPrimaryThread();
            Inventory targetInventory = null;
            BlockState blockState;
            blockState = block.getState();
            if(primaryThread) {
                blockState = PaperLib.getBlockState(targetBlock, false).getState();

            } else {
                blockState = null;
                try {
                    blockState = javaPlugin.getServer().getScheduler().callSyncMethod(javaPlugin, () -> PaperLib.getBlockState(targetBlock, false).getState()).get();
                } catch (InterruptedException | ExecutionException e) {
                    e.printStackTrace();
                }
            }
            if(blockState instanceof InventoryHolder) {
                targetInventory = ((InventoryHolder) blockState).getInventory();
            }
            if(targetInventory != null) {
                Inventory finalTargetInventory = targetInventory;
                FinalTech.getLocationRunnableFactory().waitThenRun(() -> {
                    List<ItemStackWrapper> unOutputItem = new LinkedList<>();
                    List<ItemStackWrapper> unInputItem = new LinkedList<>();
                    for(int slot : JavaUtil.shuffle(StorageInteractPort.this.getInputSlot())) {
                        ItemStack item = blockMenu.getItemInSlot(slot);
                        if(!ItemStackUtil.isItemNull(item) && !StorageCardItem.storableItem(item)) {
                            blockMenu.dropItems(block.getLocation(), slot);
                            return;
                        }
                    }
                    boolean canInput = !MachineUtil.isEmpty(blockMenu.toInventory(), StorageInteractPort.this.getInputSlot()) && MachineUtil.itemCount(blockMenu.toInventory(), StorageInteractPort.this.getInputSlot()) >= StorageInteractPort.this.getInputSlot().length / 2;
                    boolean canOutput = !MachineUtil.isFull(blockMenu.toInventory(), StorageInteractPort.this.getOutputSlot()) && MachineUtil.itemCount(blockMenu.toInventory(), StorageInteractPort.this.getOutputSlot()) < StorageInteractPort.this.getOutputSlot().length / 2;
                    if (!canOutput && !canInput) {
                        return;
                    }
                    if (canOutput) {
                        MachineUtil.stockSlots(blockMenu.toInventory(), StorageInteractPort.this.getOutputSlot());
                    }
                    if (canInput) {
                        MachineUtil.stockSlots(blockMenu.toInventory(), StorageInteractPort.this.getInputSlot());
                    }
                    int pushItemAmount = 0;
                    List<ItemWrapper> storageCardItemList = new ArrayList<>(Math.min(finalTargetInventory.getSize(), StorageInteractPort.this.searchLimit));
                    for (int i = 0, size = Math.min(finalTargetInventory.getSize(), StorageInteractPort.this.searchLimit); i < size; i++) {
                        ItemStack item = finalTargetInventory.getItem(i);
                        if (ItemStackUtil.isItemNull(item) || !item.hasItemMeta()) {
                            continue;
                        }
                        ItemMeta itemMeta = item.getItemMeta();
                        if (StorageCardItem.isValid(itemMeta)) {
                            storageCardItemList.add(new ItemWrapper(item));
                            if (item.getAmount() == 1) {
                                pushItemAmount++;
                            }
                        }
                    }
                    storageCardItemList = JavaUtil.shuffle(storageCardItemList);
                    for (ItemWrapper storageCardItem : storageCardItemList) {
                        if (!canInput && !canOutput) {
                            continue;
                        }
                        ItemMeta itemMeta = storageCardItem.getItemMeta();
                        ItemStack stringItemStack = StringItemUtil.parseItemInCard(itemMeta);
                        ItemWrapper stringItem = stringItemStack == null ? null : new ItemWrapper(stringItemStack);
                        boolean work = true;
                        int pushCount = 0;
                        int stackCount = 0;
                        if (canOutput && storageCardItem.getItemStack().getAmount() == 1 && stringItem != null) {
                            for (ItemStackWrapper unWorkItem : unOutputItem) {
                                if (ItemStackUtil.isItemSimilar(stringItem, unWorkItem)) {
                                    work = false;
                                    break;
                                }
                            }
                            if (work) {
                                pushItemAmount--;
                                pushCount = StringItemUtil.pullItemFromCard(itemMeta, stringItem, blockMenu.toInventory(), JavaUtil.shuffle(StorageInteractPort.this.getOutputSlot()));
                                if (pushCount == 0) {
                                    unOutputItem.add(ItemStackWrapper.wrap(stringItem.getItemStack()));
                                } else {
                                    MachineUtil.stockSlots(blockMenu.toInventory(), StorageInteractPort.this.getOutputSlot());
                                    canOutput = !MachineUtil.isFull(blockMenu.toInventory(), StorageInteractPort.this.getOutputSlot());
                                }
                                if (pushItemAmount == 0) {
                                    canOutput = false;
                                }
                            }
                        }
                        if (canInput) {
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
                                stackCount = StringItemUtil.storageItemToCard(itemMeta, stringItem, storageCardItem.getItemStack().getAmount(), blockMenu.toInventory(), JavaUtil.shuffle(StorageInteractPort.this.getInputSlot()));
                                if (stackCount == 0) {
                                    if (stringItem != null) {
                                        unInputItem.add(ItemStackWrapper.wrap(stringItem.getItemStack()));
                                    }
                                } else {
                                    canInput = !MachineUtil.isEmpty(blockMenu.toInventory(), StorageInteractPort.this.getInputSlot());
                                    if (stringItem == null) {
                                        stringItem = new ItemWrapper(StringItemUtil.parseItemInCard(itemMeta));
                                    }
                                }
                            }
                        }
                        if (pushCount != 0 || stackCount != 0) {
                            StorageCardItem.updateLore(itemMeta, stringItem.getItemStack());
                            storageCardItem.getItemStack().setItemMeta(itemMeta);
                        }
                    }
                }, targetBlock.getLocation(), block.getLocation());
            }
        }
    }

    @Override
    protected boolean isSynchronized() {
        return true;
    }

    @Override
    public void registerDefaultRecipes() {
        RecipeUtil.registerDescriptiveRecipe(FinalTech.getLanguageManager(), this,
                String.valueOf(this.searchLimit));
    }
}
