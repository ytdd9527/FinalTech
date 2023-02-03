package io.taraxacum.finaltech.core.item.machine.cargo;

import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.core.handlers.BlockBreakHandler;
import io.github.thebusybiscuit.slimefun4.core.handlers.BlockPlaceHandler;
import io.taraxacum.finaltech.core.helper.Icon;
import io.taraxacum.finaltech.core.interfaces.LocationMachine;
import io.taraxacum.finaltech.core.interfaces.RecipeItem;
import io.taraxacum.finaltech.core.item.machine.range.point.face.AbstractFaceMachine;
import io.taraxacum.finaltech.core.menu.AbstractMachineMenu;
import io.taraxacum.finaltech.core.menu.cargo.AdvancedAutoCraftFrameMenu;
import io.taraxacum.finaltech.util.MachineUtil;
import io.taraxacum.libs.plugin.util.ItemStackUtil;
import me.mrCookieSlime.CSCoreLibPlugin.Configuration.Config;
import me.mrCookieSlime.Slimefun.api.BlockStorage;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;

/**
 * @author Final_ROOT
 * @since 2.0
 */
public class AdvancedAutoCraftFrame extends AbstractFaceMachine implements RecipeItem, LocationMachine {
    public AdvancedAutoCraftFrame(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
        super(itemGroup, item, recipeType, recipe);
    }

    @Nonnull
    @Override
    protected BlockPlaceHandler onBlockPlace() {
        return MachineUtil.BLOCK_PLACE_HANDLER_PLACER_DENY;
    }

    @Nonnull
    @Override
    protected BlockBreakHandler onBlockBreak() {
        return MachineUtil.simpleBlockBreakerHandler(this, AdvancedAutoCraftFrameMenu.PARSE_ITEM_SLOT, AdvancedAutoCraftFrameMenu.MODULE_SLOT);
    }

    @Nonnull
    @Override
    protected AbstractMachineMenu setMachineMenu() {
        return new AdvancedAutoCraftFrameMenu(this);
    }

    @Override
    protected void tick(@Nonnull Block block, @Nonnull SlimefunItem slimefunItem, @Nonnull Config config) {
        Location location = block.getLocation();
        BlockMenu blockMenu = BlockStorage.getInventory(location);

        if(blockMenu.hasViewer()) {
            Icon.updateQuantityModule(blockMenu, AdvancedAutoCraftFrameMenu.MODULE_SLOT, AdvancedAutoCraftFrameMenu.STATUS_SLOT);
        }
    }

    @Override
    protected boolean isSynchronized() {
        return false;
    }

    @Override
    public void registerDefaultRecipes() {
        AdvancedAutoCraftFrameMenu.registerRecipe();
        for (String id : AdvancedAutoCraftFrameMenu.RECIPE_MAP.keySet()) {
            SlimefunItem slimefunItem = SlimefunItem.getById(id);
            if (slimefunItem != null) {
                ItemStack itemStack = slimefunItem.getItem();
                if (!ItemStackUtil.isItemNull(itemStack)) {
                    this.registerDescriptiveRecipe(itemStack);
                }
            }
        }
    }

    @Nonnull
    @Override
    protected BlockFace getBlockFace() {
        return BlockFace.DOWN;
    }

    @Override
    public Location[] getLocation(@Nonnull Location sourceLocation) {
        return new Location[] {new Location(sourceLocation.getWorld(), sourceLocation.getX(), sourceLocation.getY() - 1, sourceLocation.getZ())};
    }
}
