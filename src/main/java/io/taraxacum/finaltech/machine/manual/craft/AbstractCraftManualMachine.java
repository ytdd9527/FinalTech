package io.taraxacum.finaltech.machine.manual.craft;

import io.github.thebusybiscuit.slimefun4.api.SlimefunAddon;
import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.core.handlers.BlockBreakHandler;
import io.github.thebusybiscuit.slimefun4.core.handlers.BlockPlaceHandler;
import io.taraxacum.finaltech.machine.manual.AbstractManualMachine;
import io.taraxacum.finaltech.menu.AbstractMachineMenu;
import io.taraxacum.finaltech.interfaces.RecipeItem;
import io.taraxacum.finaltech.menu.ManualMachineMenu;
import io.taraxacum.finaltech.util.MachineUtil;
import me.mrCookieSlime.CSCoreLibPlugin.Configuration.Config;
import me.mrCookieSlime.Slimefun.api.BlockStorage;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import org.bukkit.Bukkit;
import org.bukkit.block.Block;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

import javax.annotation.Nonnull;

/**
 * @author Final_ROOT
 */
public abstract class AbstractCraftManualMachine extends AbstractManualMachine implements RecipeItem {
    public AbstractCraftManualMachine(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
        super(itemGroup, item, recipeType, recipe);
    }

    @Nonnull
    @Override
    protected AbstractMachineMenu newMachineMenu() {
        return new ManualMachineMenu(this.getId(), this.getItemName(), this);
    }

    @Nonnull
    @Override
    protected BlockBreakHandler onBlockBreak() {
        return MachineUtil.simpleBlockBreakerHandler(this);
    }

    @Nonnull
    @Override
    protected BlockPlaceHandler onBlockPlace() {
        return new BlockPlaceHandler(false) {
            @Override
            public void onPlayerPlace(@Nonnull BlockPlaceEvent blockPlaceEvent) {
                BlockStorage.addBlockInfo(blockPlaceEvent.getBlock().getLocation(), ManualMachineMenu.KEY, "0");
            }
        };
    }

    @Override
    public void register(@Nonnull SlimefunAddon addon) {
        this.addon = addon;
        super.register(addon);
        Bukkit.getServer().getScheduler().runTask((Plugin)addon, this::registerDefaultRecipes);

        ((ManualMachineMenu)this.getMachineMenu()).setMachineRecipeList(this.getMachineRecipes());
    }

    @Override
    protected void tick(@Nonnull Block block, @Nonnull SlimefunItem slimefunItem, Config config) {
        BlockMenu blockMenu = BlockStorage.getInventory(block);
        if (!blockMenu.toInventory().getViewers().isEmpty()) {
            this.getMachineMenu().updateMenu(BlockStorage.getInventory(block.getLocation()), block);
        }
    }
}
