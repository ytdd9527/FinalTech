package io.taraxacum.finaltech.machine.standard.advanced;

import io.github.thebusybiscuit.slimefun4.api.SlimefunAddon;
import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.core.handlers.BlockBreakHandler;
import io.github.thebusybiscuit.slimefun4.implementation.operations.CraftingOperation;
import io.github.thebusybiscuit.slimefun4.libraries.dough.inventory.InvUtils;
import io.github.thebusybiscuit.slimefun4.utils.itemstack.ItemStackWrapper;
import io.taraxacum.finaltech.dto.AdvancedCraftV2;
import io.taraxacum.finaltech.factory.MachineRecipeFactory;
import io.taraxacum.finaltech.machine.standard.AbstractStandardMachine;
import io.taraxacum.finaltech.menu.AbstractMachineMenu;
import io.taraxacum.finaltech.menu.standard.AdvancedMachineMenu;
import io.taraxacum.finaltech.util.ItemStackUtil;
import io.taraxacum.finaltech.util.MachineUtil;
import me.mrCookieSlime.CSCoreLibPlugin.Configuration.Config;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.abstractItems.MachineRecipe;
import me.mrCookieSlime.Slimefun.api.BlockStorage;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import org.bukkit.Bukkit;
import org.bukkit.block.Block;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

import javax.annotation.Nonnull;

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
        return new AdvancedMachineMenu(this);
    }

    @Nonnull
    @Override
    protected BlockBreakHandler onBlockBreak() {
        return MachineUtil.simpleBlockBreakerHandler(this, AdvancedMachineMenu.MODULE_SLOT);
    }

    @Override
    public void register(@Nonnull SlimefunAddon addon) {
        super.register(addon);
        Bukkit.getServer().getScheduler().runTask((Plugin)addon, () -> {
            AbstractAdvanceMachine.this.registerDefaultRecipes();
            MachineRecipeFactory.initAdvancedRecipeMap(AbstractAdvanceMachine.this.getClass());
        });
    }

    @Override
    protected final void tick(@Nonnull Block block, @Nonnull SlimefunItem slimefunItem, Config config) {
        BlockMenu blockMenu = BlockStorage.getInventory(block);
        CraftingOperation currentOperation = (CraftingOperation) this.getMachineProcessor().getOperation(block);

        int offset = config.contains(OFFSET_KEY) ? Integer.parseInt(config.getString(OFFSET_KEY)) : 0;

        if (currentOperation == null) {
            MachineUtil.stockSlots(blockMenu, this.getInputSlots());
            MachineRecipe machineRecipe = this.matchRecipe(blockMenu, offset);
            if (machineRecipe != null) {
                if (machineRecipe.getTicks() == 0) {
                    ItemStack[] outputItems = machineRecipe.getOutput();
                    for (ItemStack output : outputItems) {
                        blockMenu.pushItem(ItemStackUtil.cloneItem(output), this.getOutputSlots());
                    }
                    MachineUtil.stockSlots(blockMenu, this.getOutputSlots());
                } else {
                    this.getMachineProcessor().startOperation(block, new CraftingOperation(machineRecipe));
                }
            }
        } else {
            if (currentOperation.isFinished()) {
                ItemStack[] outputItems = currentOperation.getResults();
                if (InvUtils.fitAll(blockMenu.toInventory(), outputItems, getOutputSlots())) {
                    for (ItemStack output : outputItems) {
                        if (output instanceof ItemStackWrapper) {
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
        int quantityModule = MachineUtil.updateQuantityModule(blockMenu, AdvancedMachineMenu.MODULE_SLOT, AdvancedMachineMenu.STATUS_SLOT);

//        if (MachineUtil.isEmpty(blockMenu.toInventory(), getInputSlots()) || MachineUtil.isFull(blockMenu.toInventory(), getOutputSlots())) {
//            BlockStorage.addBlockInfo(blockMenu.getLocation(), OFFSET_KEY, null);
//            return null;
//        }

        AdvancedCraftV2 craft = AdvancedCraftV2.craft(blockMenu, this.getInputSlots(), MachineRecipeFactory.getAdvancedRecipe(this.getClass()), quantityModule, offset);
        if(craft != null) {
            craft.setMatchCount(Math.min(craft.getMatchCount(), MachineUtil.calMaxMatch(blockMenu, this.getOutputSlots(), craft.getOutputItemList())));
            if(craft.getMatchCount() > 0) {
                BlockStorage.addBlockInfo(blockMenu.getLocation(), OFFSET_KEY, String.valueOf(craft.getOffset()));
                craft.consumeItem(blockMenu);
                return craft.calMachineRecipe(this.getMachineRecipes().get(offset).getTicks());
            }
        }
        BlockStorage.addBlockInfo(blockMenu.getLocation(), OFFSET_KEY, null);
        return null;
    }
}
