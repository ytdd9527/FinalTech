package io.taraxacum.finaltech.machine.standard.advanced;

import io.github.thebusybiscuit.slimefun4.api.SlimefunAddon;
import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.core.handlers.BlockBreakHandler;
import io.github.thebusybiscuit.slimefun4.implementation.handlers.SimpleBlockBreakHandler;
import io.github.thebusybiscuit.slimefun4.implementation.operations.CraftingOperation;
import io.github.thebusybiscuit.slimefun4.libraries.dough.inventory.InvUtils;
import io.github.thebusybiscuit.slimefun4.utils.itemstack.ItemStackWrapper;
import io.taraxacum.finaltech.machine.standard.AbstractStandardMachine;
import io.taraxacum.finaltech.menu.AbstractMachineMenu;
import io.taraxacum.finaltech.menu.standard.AdvancedMachineMenu;
import io.taraxacum.finaltech.util.ItemStackUtil;
import io.taraxacum.finaltech.util.ItemStackWithWrapper;
import io.taraxacum.finaltech.util.MachineUtil;
import me.mrCookieSlime.CSCoreLibPlugin.Configuration.Config;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.abstractItems.MachineRecipe;
import me.mrCookieSlime.Slimefun.api.BlockStorage;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import org.bukkit.block.Block;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

import javax.annotation.Nonnull;
import java.util.*;

import static org.bukkit.Bukkit.getServer;

/**
 * @author Final_ROOT
 */
public abstract class AbstractAdvanceMachine extends AbstractStandardMachine {
    private static final String OFFSET_KEY = "offset";

    protected AbstractAdvanceMachine(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
        super(itemGroup, item, recipeType, recipe);
    }

    @Nonnull
    @Override
    protected final AbstractMachineMenu setMachineMenu() {
        return new AdvancedMachineMenu(this.getId(), this.getItemName(), this);
    }

    @Nonnull
    @Override
    protected BlockBreakHandler onBlockBreak() {
        return new SimpleBlockBreakHandler() {
            @Override
            public void onBlockBreak(@Nonnull Block block) {
                BlockMenu inv = BlockStorage.getInventory(block);
                if (inv != null) {
                    inv.dropItems(block.getLocation(), getInputSlots());
                    inv.dropItems(block.getLocation(), getOutputSlots());
                    inv.dropItems(block.getLocation(), AdvancedMachineMenu.MODULE_SLOT);
                }
                AbstractAdvanceMachine.this.getMachineProcessor().endOperation(block);
            }
        };
    }

    @Override
    public void register(@Nonnull SlimefunAddon addon) {
        super.register(addon);
        getServer().getScheduler().runTask((Plugin)addon, this::registerDefaultRecipes);
    }

    @Override
    protected final void tick(@Nonnull Block block, @Nonnull SlimefunItem slimefunItem, Config config) {
        BlockMenu blockMenu = BlockStorage.getInventory(block);
        CraftingOperation currentOperation = (CraftingOperation) this.getMachineProcessor().getOperation(block);

        int offset = config.contains(OFFSET_KEY) ? Integer.parseInt(config.getString(OFFSET_KEY)) : 0;

        if(currentOperation == null) {
            MachineUtil.stockSlots(blockMenu, this.getInputSlots());
            MachineRecipe machineRecipe = this.matchRecipe(blockMenu, offset);
            if (machineRecipe != null) {
                if(machineRecipe.getTicks() == 0) {
                    ItemStack[] outputItems = machineRecipe.getOutput();
                    for (ItemStack output : outputItems) {
                        if(output instanceof ItemStackWrapper) {
                            blockMenu.pushItem(new ItemStack(output), this.getOutputSlots());
                        } else {
                            blockMenu.pushItem(output.clone(), this.getOutputSlots());
                        }
                    }
                    MachineUtil.stockSlots(blockMenu, this.getOutputSlots());
                } else {
                    this.getMachineProcessor().startOperation(block, new CraftingOperation(machineRecipe));
                }
            }
        } else {
            if (currentOperation.isFinished()) {
                ItemStack[] outputItems = currentOperation.getResults();
                if(InvUtils.fitAll(blockMenu.toInventory(), outputItems, getOutputSlots())) {
                    for (ItemStack output : outputItems) {
                        if(output instanceof ItemStackWrapper) {
                            blockMenu.pushItem(new ItemStack(output), this.getOutputSlots());
                        } else {
                            blockMenu.pushItem(output.clone(), this.getOutputSlots());
                        }
                    }
                    this.getMachineProcessor().endOperation(block);
                }
                MachineUtil.stockSlots(blockMenu, this.getOutputSlots());
            } else {
                currentOperation.addProgress(1);
            }
        }
    }

    protected MachineRecipe matchRecipe(BlockMenu blockMenu, int offset) {
        int quantityModule = MachineUtil.updateQuantityModule(blockMenu, AdvancedMachineMenu.MODULE_SLOT, AdvancedMachineMenu.INFO_SLOT);

        if(MachineUtil.isEmpty(blockMenu.toInventory(), getInputSlots()) || MachineUtil.isFull(blockMenu.toInventory(), getOutputSlots())) {
            BlockStorage.addBlockInfo(blockMenu.getLocation(), OFFSET_KEY, null);
            return null;
        }

        Map<Integer, ItemStackWithWrapper> invInputItemSlotMap = new HashMap<>(getInputSlots().length);
        for (int slot : this.getInputSlots()) {
            ItemStack item = blockMenu.getItemInSlot(slot);
            if (!ItemStackUtil.isItemNull(item)) {
                invInputItemSlotMap.put(slot, new ItemStackWithWrapper(item));
            }
        }

        List<List<Integer>> searchList = new ArrayList<>(getInputSlots().length);

        for(int i = 0, length = this.getMachineRecipes().size(); i < length; i++) {
            MachineRecipe machineRecipe = getMachineRecipes().get((i + offset) % length);
            int count = quantityModule;
            for(ItemStack recipeInputItem : machineRecipe.getInput()) {
                ItemStackWithWrapper recipeInputItemWithWrapper = new ItemStackWithWrapper(recipeInputItem);
                int itemMatchAmount = 0;
                List<Integer> slotList = new ArrayList<>(getInputSlots().length);
                for(Map.Entry<Integer, ItemStackWithWrapper> inputMap : invInputItemSlotMap.entrySet()) {
                    if(ItemStackUtil.isItemSimilar(inputMap.getValue().getItemStackWrapper(), recipeInputItemWithWrapper.getItemStackWrapper())) {
                        itemMatchAmount += inputMap.getValue().getAmount();
                        slotList.add(inputMap.getKey());
                        if(itemMatchAmount / recipeInputItem.getAmount() > count) {
                            break;
                        }
                    }
                }
                searchList.add(slotList);
                count = Math.min(count, itemMatchAmount / recipeInputItem.getAmount());
                if(count == 0) {
                    break;
                }
            }
            if(count > 0) {
                count = MachineUtil.maxMatch(blockMenu.toInventory(), getOutputSlots(), machineRecipe.getOutput(), count);
                if(count > 0) {
                    BlockStorage.addBlockInfo(blockMenu.getLocation(), OFFSET_KEY, String.valueOf((i + offset) % length));
                    ItemStack[] recipeInputs = machineRecipe.getInput();
                    ItemStack[] recipeOutputs = machineRecipe.getOutput();
                    if(count > 1) {
                        recipeInputs = ItemStackUtil.enlargeItemArray(machineRecipe.getInput(), count);
                        recipeOutputs = ItemStackUtil.enlargeItemArray(machineRecipe.getOutput(), count);
                    }
                    for(int j = 0, recipeSize = machineRecipe.getInput().length; j < recipeSize ; j++) {
                        int amount = count * machineRecipe.getInput()[j].getAmount();
                        for(Integer slot : searchList.get(j)) {
                            ItemStack item = invInputItemSlotMap.get(slot).getItemStack();
                            int l = Math.min(item.getAmount(), amount);
                            blockMenu.consumeItem(slot, l);
                            amount -= l;
                        }
                    }
                    return new MachineRecipe(machineRecipe.getTicks(), recipeInputs, recipeOutputs);
                }
            }
            searchList.clear();
        }
        BlockStorage.addBlockInfo(blockMenu.getLocation(), OFFSET_KEY, null);
        return null;
    }
}
