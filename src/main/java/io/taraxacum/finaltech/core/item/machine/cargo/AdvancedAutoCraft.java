package io.taraxacum.finaltech.core.item.machine.cargo;

import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.core.handlers.BlockBreakHandler;
import io.github.thebusybiscuit.slimefun4.core.handlers.BlockPlaceHandler;
import io.taraxacum.finaltech.FinalTech;
import io.taraxacum.finaltech.core.interfaces.LocationMachine;
import io.taraxacum.libs.slimefun.dto.AdvancedCraft;
import io.taraxacum.libs.plugin.dto.AdvancedMachineRecipe;
import io.taraxacum.libs.plugin.dto.LocationRecipeRegistry;
import io.taraxacum.finaltech.core.helper.Icon;
import io.taraxacum.finaltech.core.menu.AbstractMachineMenu;
import io.taraxacum.finaltech.core.interfaces.RecipeItem;
import io.taraxacum.finaltech.core.menu.function.AdvancedAutoCraftMenu;
import io.taraxacum.libs.plugin.dto.InvWithSlots;
import io.taraxacum.libs.plugin.util.ItemStackUtil;
import io.taraxacum.finaltech.util.MachineUtil;
import io.taraxacum.finaltech.util.CargoUtil;
import io.taraxacum.finaltech.core.helper.SlotSearchOrder;
import io.taraxacum.finaltech.core.helper.SlotSearchSize;
import io.taraxacum.finaltech.util.ConstantTableUtil;
import me.mrCookieSlime.CSCoreLibPlugin.Configuration.Config;
import me.mrCookieSlime.Slimefun.api.BlockStorage;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;
import java.util.List;

/**
 * @author Final_ROOT
 * @since 2.0
 */
public class AdvancedAutoCraft extends AbstractCargo implements RecipeItem, LocationMachine {
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

        AdvancedMachineRecipe machineRecipe = LocationRecipeRegistry.getInstance().getRecipe(location);
        if (machineRecipe == null) {
            return;
        }

        Block containerBlock = block.getRelative(BlockFace.DOWN);
        if (!BlockStorage.hasBlockInfo(containerBlock) || !BlockStorage.hasInventory(containerBlock)) {
            return;
        }

        Config locationInfo = BlockStorage.getLocationInfo(containerBlock.getLocation());
        if (locationInfo.contains(ConstantTableUtil.CONFIG_ID)) {
            String id = locationInfo.getString(ConstantTableUtil.CONFIG_ID);
            Runnable runnable = () -> {
                InvWithSlots inputMap = CargoUtil.getInvWithSlots(containerBlock, SlotSearchSize.INPUT_HELPER.getOrDefaultValue(config), SlotSearchOrder.VALUE_ASCENT);
                InvWithSlots outputMap = CargoUtil.getInvWithSlots(containerBlock, SlotSearchSize.OUTPUT_HELPER.getOrDefaultValue(config), SlotSearchOrder.VALUE_ASCENT);
                if (inputMap == null || outputMap == null || inputMap.getSlots().length == 0 || outputMap.getSlots().length == 0) {
                    return;
                }

                BlockMenu containerMenu = BlockStorage.getInventory(containerBlock);
                int[] inputSlots = inputMap.getSlots();
                int[] outputSlots = outputMap.getSlots();

                int quantity = Icon.updateQuantityModule(blockMenu, AdvancedAutoCraftMenu.MODULE_SLOT, AdvancedAutoCraftMenu.STATUS_SLOT);

                AdvancedCraft craft = AdvancedCraft.craftAsc(containerMenu.toInventory(), inputSlots, List.of(machineRecipe), quantity, 0);
                if (craft != null) {
                    craft.setMatchCount(Math.min(craft.getMatchCount(), MachineUtil.calMaxMatch(containerMenu.toInventory(), outputSlots, craft.getOutputItemList())));
                    if (craft.getMatchCount() > 0) {
                        craft.consumeItem(containerMenu.toInventory());
                        for (ItemStack item : craft.calMachineRecipe(0).getOutput()) {
                            containerMenu.pushItem(ItemStackUtil.cloneItem(item), outputSlots);
                        }
                    }
                }
            };
            if (FinalTech.isAsyncSlimefunItem(id)) {
                FinalTech.getLocationRunnableFactory().waitThenRun(runnable, block.getLocation(), containerBlock.getLocation());
            } else {
                runnable.run();
            }
        }
    }

    @Override
    protected boolean isSynchronized() {
        return false;
    }

    @Override
    public void registerDefaultRecipes() {
        AdvancedAutoCraftMenu.registerRecipe();
        for (String id : AdvancedAutoCraftMenu.RECIPE_MAP.keySet()) {
            SlimefunItem slimefunItem = SlimefunItem.getById(id);
            if (slimefunItem != null) {
                ItemStack itemStack = slimefunItem.getItem();
                if (!ItemStackUtil.isItemNull(itemStack)) {
                    this.registerDescriptiveRecipe(itemStack);
                }
            }
        }
    }

    @Override
    public Location[] getLocation(@Nonnull Location sourceLocation) {
        return new Location[] {new Location(sourceLocation.getWorld(), sourceLocation.getX(), sourceLocation.getY() - 1, sourceLocation.getZ())};
    }
}
