package io.taraxacum.finaltech.machine.cargo;

import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.core.handlers.BlockBreakHandler;
import io.github.thebusybiscuit.slimefun4.core.handlers.BlockPlaceHandler;
import io.github.thebusybiscuit.slimefun4.implementation.handlers.SimpleBlockBreakHandler;
import io.github.thebusybiscuit.slimefun4.utils.itemstack.ItemStackWrapper;
import io.taraxacum.finaltech.menu.AbstractMachineMenu;
import io.taraxacum.finaltech.interfaces.RecipeItem;
import io.taraxacum.finaltech.menu.AdvancedAutoCraftMenu;
import io.taraxacum.finaltech.dto.InvWithSlots;
import io.taraxacum.finaltech.util.ItemStackUtil;
import io.taraxacum.finaltech.dto.ItemStackWithWrapper;
import io.taraxacum.finaltech.util.MachineUtil;
import io.taraxacum.finaltech.util.CargoUtil;
import io.taraxacum.finaltech.util.menu.Icon;
import io.taraxacum.finaltech.util.menu.SlotSearchOrder;
import io.taraxacum.finaltech.util.menu.SlotSearchSize;
import me.mrCookieSlime.CSCoreLibPlugin.Configuration.Config;
import me.mrCookieSlime.Slimefun.api.BlockStorage;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Final_ROOT
 */
public class AdvancedAutoCraft extends AbstractCargo implements RecipeItem {
    public AdvancedAutoCraft(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
        super(itemGroup, item, recipeType, recipe);
    }

    @Nonnull
    @Override
    protected AbstractMachineMenu setMachineMenu() {
        return new AdvancedAutoCraftMenu(this.getId(), this.getItemName(), this);
    }

    @Nonnull
    @Override
    protected BlockBreakHandler onBlockBreak() {
        return new SimpleBlockBreakHandler() {
            @Override
            public void onBlockBreak(@Nonnull Block block) {
                BlockMenu blockMenu = BlockStorage.getInventory(block);
                blockMenu.dropItems(block.getLocation(), AdvancedAutoCraftMenu.PARSE_ITEM_SLOT);
                blockMenu.dropItems(block.getLocation(), AdvancedAutoCraftMenu.MACHINE_SLOT);
            }
        };
    }

    @Nonnull
    @Override
    protected BlockPlaceHandler onBlockPlace() {
        return new BlockPlaceHandler(false) {
            @Override
            public void onPlayerPlace(@Nonnull BlockPlaceEvent blockPlaceEvent) {
                BlockStorage.addBlockInfo(blockPlaceEvent.getBlock().getLocation(), SlotSearchSize.KEY_INPUT, SlotSearchSize.VALUE_INPUTS_ONLY);
                BlockStorage.addBlockInfo(blockPlaceEvent.getBlock().getLocation(), SlotSearchSize.KEY_OUTPUT, SlotSearchSize.VALUE_OUTPUTS_ONLY);
            }
        };
    }

    @Override
    protected void tick(@Nonnull Block block, @Nonnull SlimefunItem slimefunItem, @Nonnull Config config) {
        BlockMenu blockMenu = BlockStorage.getInventory(block);
        ItemStack recipeOutput = blockMenu.getItemInSlot(AdvancedAutoCraftMenu.RESULT_SLOT);
        if (ItemStackUtil.isItemNull(recipeOutput) || ItemStackUtil.isItemSimilar(recipeOutput, Icon.PARSE_FAILED_ICON)) {
            return;
        }

        Block containerBlock = block.getRelative(BlockFace.DOWN);
        if (!CargoUtil.hasInventory(containerBlock)) {
            return;
        }
        InvWithSlots inputMap = CargoUtil.getInv(containerBlock, config.getString(SlotSearchSize.KEY_INPUT), SlotSearchOrder.VALUE_ASCENT);
        InvWithSlots outputMap = CargoUtil.getInv(containerBlock, config.getString(SlotSearchSize.KEY_OUTPUT), SlotSearchOrder.VALUE_ASCENT);
        if (inputMap.getSlots().length == 0 || outputMap.getSlots().length == 0) {
            return;
        }
        final ItemStackWithWrapper parseFailedIcon = new ItemStackWithWrapper(Icon.PARSE_FAILED_ICON);
        final ItemStackWithWrapper parseNullIcon = new ItemStackWithWrapper(Icon.PARSE_NULL_ICON);

        boolean useBigInputSlot = ItemStackUtil.isItemSimilar(blockMenu.getItemInSlot(AdvancedAutoCraftMenu.ITEM_INPUT_SLOT_BIG[0]), Icon.INPUT_BORDER_ICON);
        int[] matchSlot = useBigInputSlot ? AdvancedAutoCraftMenu.ITEM_INPUT_SLOT : AdvancedAutoCraftMenu.ITEM_INPUT_SLOT_BIG;
        List<ItemStackWithWrapper> recipeInputList = new ArrayList<>(matchSlot.length);
        for (int slot : matchSlot) {
            ItemStack item = blockMenu.getItemInSlot(slot);
            if (ItemStackUtil.isItemNull(item)) {
                break;
            }
            ItemStackWithWrapper itemStackWithWrapper = new ItemStackWithWrapper(item);
            if (ItemStackUtil.isItemSimilar(itemStackWithWrapper.getItemStackWrapper(), parseFailedIcon.getItemStackWrapper()) || ItemStackUtil.isItemSimilar(itemStackWithWrapper.getItemStackWrapper(), parseNullIcon.getItemStackWrapper())) {
                break;
            }
            boolean existed = false;
            for (ItemStackWithWrapper recipeInput : recipeInputList) {
                if (ItemStackUtil.isItemSimilar(itemStackWithWrapper.getItemStackWrapper(), recipeInput.getItemStackWrapper())) {
                    recipeInput.setAmount(recipeInput.getAmount() + itemStackWithWrapper.getAmount());
                    existed = true;
                    break;
                }
            }
            if (!existed) {
                recipeInputList.add(itemStackWithWrapper);
            }
        }

        BlockMenu containerMenu = BlockStorage.getInventory(containerBlock);
        int[] inputSlots = inputMap.getSlots();
        int[] outputSlots = outputMap.getSlots();
        Inventory containerInv = containerMenu.toInventory();
        Map<Integer, ItemStackWithWrapper> itemMap = MachineUtil.calSlotItemWithWrapperMap(containerInv, inputSlots);
        Map<ItemStackWithWrapper, List<Integer>> searchMap = new HashMap<>(itemMap.size());

        int count = MachineUtil.updateQuantityModule(blockMenu, AdvancedAutoCraftMenu.MODULE_SLOT, AdvancedAutoCraftMenu.INFO_SLOT);
        for (ItemStackWithWrapper recipeInputItemWithWrapper : recipeInputList) {
            int itemMatchAmount = 0;
            for (Map.Entry<Integer, ItemStackWithWrapper> item : itemMap.entrySet()) {
                if (ItemStackUtil.isItemSimilar(item.getValue().getItemStack(), recipeInputItemWithWrapper.getItemStackWrapper())) {
                    itemMatchAmount += item.getValue().getAmount();
                    List<Integer> slotList = searchMap.get(recipeInputItemWithWrapper);
                    if (slotList == null) {
                        slotList = new ArrayList<>(inputSlots.length);
                        searchMap.put(recipeInputItemWithWrapper, slotList);
                    }
                    slotList.add(item.getKey());
                    if (itemMatchAmount / recipeInputItemWithWrapper.getAmount() > count) {
                        break;
                    }
                }
            }
            count = Math.min(count, itemMatchAmount / recipeInputItemWithWrapper.getAmount());
            if (count == 0) {
                break;
            }
        }

        if (searchMap.size() == recipeInputList.size() && count > 0) {
            count = MachineUtil.maxMatch(containerInv, outputSlots, new ItemStack[] {recipeOutput}, count);
            if (count > 0) {
                for (Map.Entry<ItemStackWithWrapper, List<Integer>> searchItem : searchMap.entrySet()) {
                    int number = searchItem.getKey().getAmount() * count;
                    for (Integer n : searchItem.getValue()) {
                        ItemStack item = containerInv.getItem(n);
                        int l = Math.min(item.getAmount(), number);
                        item.setAmount(item.getAmount() - l);
                        number -= l;
                    }
                }
                int maxStackSize = recipeOutput.getMaxStackSize();
                int amount = recipeOutput.getAmount() * count;
                ItemStackWrapper recipeOutputWrapper = ItemStackWrapper.wrap(recipeOutput);
                for (int slot = 0; slot < outputSlots.length; slot++) {
                    if (ItemStackUtil.isItemNull(containerInv.getItem(slot))) {
                        ItemStack item = recipeOutput.clone();
                        int m = Math.min(amount, maxStackSize);
                        item.setAmount(m);
                        containerInv.setItem(slot, item);
                        amount -= m;
                    } else if (ItemStackUtil.isItemSimilar(recipeOutputWrapper, containerInv.getItem(slot))) {
                        int m = Math.min(amount, maxStackSize - containerInv.getItem(slot).getAmount());
                        containerInv.getItem(slot).setAmount(containerInv.getItem(slot).getAmount() + m);
                        amount -= m;
                    }
                    if (amount == 0) {
                        break;
                    }
                }
            }
        }
    }

    @Override
    public void registerDefaultRecipes() {
        for (ItemStack item : AdvancedAutoCraftMenu.RECIPE_MAP.keySet()) {
            this.registerDescriptiveRecipe(item);
        }
    }
}
