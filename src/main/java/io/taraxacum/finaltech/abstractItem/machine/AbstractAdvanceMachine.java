package io.taraxacum.finaltech.abstractItem.machine;

import io.github.thebusybiscuit.slimefun4.api.SlimefunAddon;
import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.core.handlers.BlockBreakHandler;
import io.github.thebusybiscuit.slimefun4.implementation.handlers.SimpleBlockBreakHandler;
import io.github.thebusybiscuit.slimefun4.implementation.operations.CraftingOperation;
import io.github.thebusybiscuit.slimefun4.utils.SlimefunUtils;
import io.taraxacum.finaltech.abstractItem.menu.AbstractMachineMenu;
import io.taraxacum.finaltech.menu.AdvancedMachineMenu;
import io.taraxacum.finaltech.setup.FinalTechItems;
import io.taraxacum.finaltech.util.MachineUtil;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.abstractItems.MachineRecipe;
import me.mrCookieSlime.Slimefun.api.BlockStorage;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;

import javax.annotation.Nonnull;
import java.util.*;

import static org.bukkit.Bukkit.getServer;

/**
 * @author Final_ROOT
 */
public abstract class AbstractAdvanceMachine extends AbstractStandardMachine {
    protected AbstractAdvanceMachine(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
        super(itemGroup, item, recipeType, recipe);
    }

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
        this.addon = addon;
        super.register(addon);
        getServer().getScheduler().runTask((Plugin)addon, this::registerDefaultRecipes);
    }

    @Override
    protected final void tick(Block b) {
        BlockMenu inv = BlockStorage.getInventory(b);
        CraftingOperation currentOperation = this.getMachineProcessor().getOperation(b);

        if(currentOperation == null) {
            MachineUtil.stockSlots(inv, this.getInputSlots());
            MachineRecipe next = this.findNextRecipe(inv);
            if (next != null) {
                if(next.getTicks() == 0) {
                    ItemStack[] outputItems = next.getOutput();
                    for (ItemStack output : outputItems) {
                        inv.pushItem(output.clone(), this.getOutputSlots());
                    }
                    MachineUtil.stockSlots(inv, this.getOutputSlots());
                } else {
                    this.getMachineProcessor().startOperation(b, new CraftingOperation(next));
                }
            }
        } else {
            if (currentOperation.isFinished()) {
                ItemStack[] outputItems = currentOperation.getResults();
                for (ItemStack output : outputItems) {
                    inv.pushItem(output.clone(), this.getOutputSlots());
                }
                this.getMachineProcessor().endOperation(b);
            } else {
                currentOperation.addProgress(1);
            }
        }
    }

    protected MachineRecipe findNextRecipe(BlockMenu inv) {
        ItemStack quantityModule = inv.getItemInSlot(AdvancedMachineMenu.MODULE_SLOT);
        int amount = 1;
        if (quantityModule != null && SlimefunUtils.isItemSimilar(FinalTechItems.QUANTITY_MODULE, quantityModule, true, false)) {
            amount = quantityModule.getAmount();
        }

        ItemStack moduleIcon = inv.getItemInSlot(AdvancedMachineMenu.INFO_SLOT);
        ItemMeta itemMeta = moduleIcon.getItemMeta();
        List<String> lore = itemMeta.getLore();
        if(lore == null || lore.size() < 2) {
            lore = new ArrayList<>();
            lore.add(0, "§7该机器可以进行升级");
            lore.add(1, "§7当前效率：" + amount);
        } else {
            lore.set(1, "§7当前效率：" + amount);
        }
        itemMeta.setLore(lore);
        moduleIcon.setItemMeta(itemMeta);

        ArrayList<ItemStack> inputs = new ArrayList<>();
        for (int slot : this.getInputSlots()) {
            ItemStack item = inv.getItemInSlot(slot);
            if (item != null && item.getType() != Material.AIR && item.getAmount() != 0) {
                inputs.add(item);
            }
        }

        for (MachineRecipe recipe : recipes) {
            MachineRecipe result = MachineUtil.matchRecipe(inputs, recipe, inv.toInventory(), this.getOutputSlots(), amount);
            if(result != null) {
                return result;
            }
        }
        return null;
    }
}
