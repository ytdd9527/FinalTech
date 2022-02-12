package io.taraxacum.finaltech.machine;

import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.core.attributes.RecipeDisplayItem;
import io.github.thebusybiscuit.slimefun4.core.handlers.BlockBreakHandler;
import io.github.thebusybiscuit.slimefun4.core.handlers.BlockPlaceHandler;
import io.github.thebusybiscuit.slimefun4.implementation.handlers.SimpleBlockBreakHandler;
import io.github.thebusybiscuit.slimefun4.utils.SlimefunUtils;
import io.taraxacum.finaltech.abstractItem.machine.AbstractMachine;
import io.taraxacum.finaltech.abstractItem.menu.AbstractMachineMenu;
import io.taraxacum.finaltech.interfaces.RecipeItem;
import io.taraxacum.finaltech.menu.AdvancedAutoCraftMenu;
import io.taraxacum.finaltech.setup.FinalTechItems;
import io.taraxacum.finaltech.util.InvWithSlots;
import io.taraxacum.finaltech.util.ItemStackUtil;
import io.taraxacum.finaltech.util.MachineUtil;
import io.taraxacum.finaltech.util.cargo.CargoUtil;
import io.taraxacum.finaltech.util.cargo.Icon;
import io.taraxacum.finaltech.util.cargo.SlotSearchOrder;
import io.taraxacum.finaltech.util.cargo.SlotSearchSize;
import me.mrCookieSlime.CSCoreLibPlugin.Configuration.Config;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.abstractItems.MachineRecipe;
import me.mrCookieSlime.Slimefun.Objects.handlers.BlockTicker;
import me.mrCookieSlime.Slimefun.api.BlockStorage;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import me.mrCookieSlime.Slimefun.api.item_transport.ItemTransportFlow;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Final_ROOT
 */
public class AdvancedAutoCraft extends AbstractMachine implements RecipeItem {
    public final List<MachineRecipe> recipes = new ArrayList<>();
    public AdvancedAutoCraft(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
        super(itemGroup, item, recipeType, recipe);
    }

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

    @Override
    public void preRegister() {
        super.preRegister();
        this.addItemHandler(new BlockTicker() {
            @Override
            public void tick(Block block, SlimefunItem sf, Config data) {
                AdvancedAutoCraft.this.tick(block);
            }
            @Override
            public boolean isSynchronized() {
                return true;
            }
        });
        this.addItemHandler(new BlockPlaceHandler(false) {
            @Override
            public void onPlayerPlace(@Nonnull BlockPlaceEvent blockPlaceEvent) {
                BlockStorage.addBlockInfo(blockPlaceEvent.getBlock().getLocation(), SlotSearchSize.KEY_INPUT, SlotSearchSize.VALUE_INPUTS_ONLY);
                BlockStorage.addBlockInfo(blockPlaceEvent.getBlock().getLocation(), SlotSearchSize.KEY_OUTPUT, SlotSearchSize.VALUE_OUTPUTS_ONLY);
            }
        });
    }

    @Override
    protected void tick(Block block) {
        BlockMenu blockMenu = BlockStorage.getInventory(block);
        ItemStack recipeOutput = blockMenu.getItemInSlot(AdvancedAutoCraftMenu.RESULT_SLOT);
        if(ItemStackUtil.isItemNull(recipeOutput) || SlimefunUtils.isItemSimilar(recipeOutput, Icon.PARSE_FAILED_ICON, true, false)) {
            return;
        }

        Block containerBlock = block.getRelative(BlockFace.DOWN);
        if(!BlockStorage.hasInventory(containerBlock)) {
            return;
        }

        List<ItemStack> recipeInputList = new ArrayList<>();
        if(SlimefunUtils.isItemSimilar(blockMenu.getItemInSlot(AdvancedAutoCraftMenu.ITEM_INPUT_SLOT_BIG[0]), Icon.INPUT_BORDER_ICON, true, false)) {
            for(int slot : AdvancedAutoCraftMenu.ITEM_INPUT_SLOT) {
                ItemStack item = blockMenu.getItemInSlot(slot);
                if(ItemStackUtil.isItemNull(item) || SlimefunUtils.isItemSimilar(item, Icon.PARSE_FAILED_ICON, true, false) || SlimefunUtils.isItemSimilar(item, Icon.PARSE_NULL_ICON, true, false)) {
                    break;
                }
                recipeInputList.add(item);
            }
        } else {
            for(int slot : AdvancedAutoCraftMenu.ITEM_INPUT_SLOT_BIG) {
                ItemStack item = blockMenu.getItemInSlot(slot);
                if(ItemStackUtil.isItemNull(item) || SlimefunUtils.isItemSimilar(item, Icon.PARSE_FAILED_ICON, true, false) || SlimefunUtils.isItemSimilar(item, Icon.PARSE_NULL_ICON, true, false) || SlimefunUtils.isItemSimilar(item, Icon.INPUT_BORDER_ICON, true, false)) {
                    break;
                }
                recipeInputList.add(item);
            }
        }
        MachineRecipe recipe = new MachineRecipe(0, ItemStackUtil.toArray(recipeInputList, true), new ItemStack[] {recipeOutput});

        BlockMenu containerMenu = BlockStorage.getInventory(containerBlock);
        String size = BlockStorage.getLocationInfo(block.getLocation(), SlotSearchSize.KEY_INPUT);
        int[] inputSlots = new int[0];
        int[] outputSlots = new int[0];
        switch (size) {
            case SlotSearchSize.VALUE_INPUTS_ONLY:
                inputSlots = containerMenu.getPreset().getSlotsAccessedByItemTransport(ItemTransportFlow.INSERT);
                break;
            case SlotSearchSize.VALUE_OUTPUTS_ONLY:
                inputSlots = containerMenu.getPreset().getSlotsAccessedByItemTransport(ItemTransportFlow.WITHDRAW);
                break;
            case SlotSearchSize.VALUE_INPUTS_AND_OUTPUTS:
                int[] in = containerMenu.getPreset().getSlotsAccessedByItemTransport(ItemTransportFlow.INSERT);
                int[] out = containerMenu.getPreset().getSlotsAccessedByItemTransport(ItemTransportFlow.WITHDRAW);
                inputSlots = new int[in.length + out.length];
                int i;
                for(i = 0; i < in.length; i++) {
                    inputSlots[i] = in[i];
                }
                for(int j = 0; i + j < inputSlots.length; j++) {
                    inputSlots[i + j] = out[j];
                }
                break;
            default:
                break;
        }
        size = BlockStorage.getLocationInfo(block.getLocation(), SlotSearchSize.KEY_OUTPUT);
        switch (size) {
            case SlotSearchSize.VALUE_INPUTS_ONLY:
                outputSlots = containerMenu.getPreset().getSlotsAccessedByItemTransport(ItemTransportFlow.INSERT);
                break;
            case SlotSearchSize.VALUE_OUTPUTS_ONLY:
                outputSlots = containerMenu.getPreset().getSlotsAccessedByItemTransport(ItemTransportFlow.WITHDRAW);
                break;
            case SlotSearchSize.VALUE_INPUTS_AND_OUTPUTS:
                int[] in = containerMenu.getPreset().getSlotsAccessedByItemTransport(ItemTransportFlow.INSERT);
                int[] out = containerMenu.getPreset().getSlotsAccessedByItemTransport(ItemTransportFlow.WITHDRAW);
                outputSlots = new int[in.length + out.length];
                int i;
                for(i = 0; i < in.length; i++) {
                    outputSlots[i] = in[i];
                }
                for(int j = 0; i + j < outputSlots.length; j++) {
                    outputSlots[i + j] = out[j];
                }
                break;
            default:
                break;
        }
        Inventory containerInv = containerMenu.toInventory();
        List<ItemStack> itemList = new ArrayList<>();
        for(int slot : inputSlots) {
            ItemStack item = containerInv.getItem(slot);
            if(!ItemStackUtil.isItemNull(item)) {
                itemList.add(item);
            }
        }

        int amount = MachineUtil.updateQuantityModule(blockMenu, AdvancedAutoCraftMenu.MODULE_SLOT, AdvancedAutoCraftMenu.INFO_SLOT);

        MachineRecipe resultRecipe = MachineUtil.matchRecipe(itemList, recipe, containerInv, outputSlots, amount);
        if(resultRecipe == null) {
            return;
        }
        for(ItemStack item : resultRecipe.getOutput()) {
            containerMenu.pushItem(item, outputSlots);
        }
    }

    @Override
    public List<MachineRecipe> getMachineRecipes() {
        return recipes;
    }

    @Override
    public void registerDefaultRecipes() {}

    @Nonnull
    @Override
    public List<ItemStack> getDisplayRecipes() {
        List<ItemStack> displayRecipe = new ArrayList<>();
        for(ItemStack item : AdvancedAutoCraftMenu.recipeMap.keySet()) {
            displayRecipe.add(item);
            displayRecipe.add(new ItemStack(Material.AIR));
        }
        return displayRecipe;
    }
}
