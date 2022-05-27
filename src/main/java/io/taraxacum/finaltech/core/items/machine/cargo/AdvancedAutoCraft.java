package io.taraxacum.finaltech.core.items.machine.cargo;

import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.core.handlers.BlockBreakHandler;
import io.github.thebusybiscuit.slimefun4.core.handlers.BlockPlaceHandler;
import io.github.thebusybiscuit.slimefun4.implementation.handlers.SimpleBlockBreakHandler;
import io.taraxacum.finaltech.api.dto.AdvancedCraft;
import io.taraxacum.finaltech.api.dto.AdvancedMachineRecipe;
import io.taraxacum.finaltech.core.factory.BlockTaskFactory;
import io.taraxacum.finaltech.core.factory.LocationRecipeRegistry;
import io.taraxacum.finaltech.core.menu.AbstractMachineMenu;
import io.taraxacum.finaltech.api.interfaces.RecipeItem;
import io.taraxacum.finaltech.core.menu.function.AdvancedAutoCraftMenu;
import io.taraxacum.finaltech.api.dto.InvWithSlots;
import io.taraxacum.finaltech.util.ItemStackUtil;
import io.taraxacum.finaltech.util.MachineUtil;
import io.taraxacum.finaltech.util.CargoUtil;
import io.taraxacum.finaltech.core.storage.SlotSearchOrder;
import io.taraxacum.finaltech.core.storage.SlotSearchSize;
import me.mrCookieSlime.CSCoreLibPlugin.Configuration.Config;
import me.mrCookieSlime.Slimefun.api.BlockStorage;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import javax.annotation.Nonnull;
import java.util.List;

/**
 * @author Final_ROOT
 * @since 2.0
 */
public class AdvancedAutoCraft extends AbstractCargo implements RecipeItem {
    public AdvancedAutoCraft(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
        super(itemGroup, item, recipeType, recipe);
    }

    @Nonnull
    @Override
    protected BlockPlaceHandler onBlockPlace() {
        return new BlockPlaceHandler(false) {
            @Override
            public void onPlayerPlace(@Nonnull BlockPlaceEvent blockPlaceEvent) {
                SlotSearchSize.INPUT_HELPER.checkOrSetBlockStorage(blockPlaceEvent.getBlock().getLocation());
                SlotSearchSize.OUTPUT_HELPER.checkOrSetBlockStorage(blockPlaceEvent.getBlock().getLocation());
            }
        };
    }

    @Nonnull
    @Override
    protected BlockBreakHandler onBlockBreak() {
        return MachineUtil.simpleBlockBreakerHandler(this, AdvancedAutoCraftMenu.PARSE_ITEM_SLOT, AdvancedAutoCraftMenu.MODULE_SLOT);
    }

    @Nonnull
    @Override
    protected AbstractMachineMenu setMachineMenu() {
        return new AdvancedAutoCraftMenu(this);
    }

    @Override
    protected void tick(@Nonnull Block block, @Nonnull SlimefunItem slimefunItem, @Nonnull Config config) {
        Location location = block.getLocation();
        BlockMenu blockMenu = BlockStorage.getInventory(location);
        JavaPlugin javaPlugin = this.getAddon().getJavaPlugin();
        boolean primaryThread = javaPlugin.getServer().isPrimaryThread();

        AdvancedMachineRecipe machineRecipe = LocationRecipeRegistry.getRecipe(location);
        if (machineRecipe == null) {
            return;
        }

        Block containerBlock = block.getRelative(BlockFace.DOWN);
        if (!BlockStorage.hasInventory(containerBlock)) {
            return;
        }

        InvWithSlots inputMap = CargoUtil.getInvAsync(containerBlock, SlotSearchSize.INPUT_HELPER.getOrDefaultValue(config), SlotSearchOrder.VALUE_ASCENT);
        InvWithSlots outputMap = CargoUtil.getInvAsync(containerBlock, SlotSearchSize.OUTPUT_HELPER.getOrDefaultValue(config), SlotSearchOrder.VALUE_ASCENT);
        if (inputMap.getSlots().length == 0 || outputMap.getSlots().length == 0) {
            return;
        }

        BlockMenu containerMenu = BlockStorage.getInventory(containerBlock);
        int[] inputSlots = inputMap.getSlots();
        int[] outputSlots = outputMap.getSlots();

        int quantity = MachineUtil.updateQuantityModule(blockMenu, AdvancedAutoCraftMenu.MODULE_SLOT, AdvancedAutoCraftMenu.STATUS_SLOT);

        Runnable runnable = () -> {
            AdvancedCraft craft = AdvancedCraft.craftAsc(containerMenu, inputSlots, List.of(machineRecipe), quantity, 0);
            if (craft != null) {
                craft.setMatchCount(Math.min(craft.getMatchCount(), MachineUtil.calMaxMatch(containerMenu, outputSlots, craft.getOutputItemList())));
                if (craft.getMatchCount() > 0) {
                    craft.consumeItem(containerMenu);
                    for (ItemStack item : craft.calMachineRecipe(0).getOutput()) {
                        containerMenu.pushItem(ItemStackUtil.cloneItem(item), outputSlots);
                    }
                }
            }
        };

        if(primaryThread) {
            runnable.run();
        } else {
            BlockTaskFactory.getInstance().registerRunnable(slimefunItem, false, runnable, location, containerBlock.getLocation());
        }
    }

    @Override
    protected boolean isSynchronized() {
        return true;
    }

    @Override
    public void registerDefaultRecipes() {
        for (ItemStack item : AdvancedAutoCraftMenu.RECIPE_MAP.keySet()) {
            this.registerDescriptiveRecipe(item);
        }
    }
}
