package io.taraxacum.finaltech.abstractItem.machine;

import io.github.thebusybiscuit.slimefun4.api.SlimefunAddon;
import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.core.handlers.BlockBreakHandler;
import io.github.thebusybiscuit.slimefun4.core.handlers.BlockPlaceHandler;
import io.github.thebusybiscuit.slimefun4.implementation.handlers.SimpleBlockBreakHandler;
import io.github.thebusybiscuit.slimefun4.utils.SlimefunUtils;
import io.taraxacum.finaltech.abstractItem.menu.AbstractMachineMenu;
import io.taraxacum.finaltech.interfaces.RecipeItem;
import io.taraxacum.finaltech.menu.ManualMachineMenu;
import io.taraxacum.finaltech.util.ItemStackUtil;
import io.taraxacum.finaltech.util.cargo.Icon;
import me.mrCookieSlime.CSCoreLibPlugin.Configuration.Config;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.abstractItems.MachineRecipe;
import me.mrCookieSlime.Slimefun.Objects.handlers.BlockTicker;
import me.mrCookieSlime.Slimefun.api.BlockStorage;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import org.bukkit.block.Block;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

import static org.bukkit.Bukkit.getServer;

/**
 * @author Final_ROOT
 */
public abstract class AbstractManualMachine extends AbstractMachine implements RecipeItem {
    private List<MachineRecipe> recipes;
    private ManualMachineMenu menu;
    public static final String KEY = "update";
    public static final String VALUE_ALLOW = "1";
    public static final String VALUE_DENY = "0";
    public static final String VALUE_UPDATE = "2";
    public AbstractManualMachine(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
        super(itemGroup, item, recipeType, recipe);
        recipes = new ArrayList<>();
    }

    @Override
    protected AbstractMachineMenu setMachineMenu() {
        this.menu = new ManualMachineMenu(this.getId(), this.getItemName(), this);
        return this.menu;
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
                    BlockStorage.clearBlockInfo(block.getLocation());
                }
            }
        };
    }

    @Override
    public void preRegister() {
        super.preRegister();
        this.addItemHandler(new BlockTicker() {
            @Override
            public void tick(Block block, SlimefunItem sf, Config data) {
                AbstractManualMachine.this.tick(block);
            }
            @Override
            public boolean isSynchronized() {
                return false;
            }
        });
        this.addItemHandler(new BlockPlaceHandler(false) {
            @Override
            public void onPlayerPlace(@Nonnull BlockPlaceEvent blockPlaceEvent) {
                BlockStorage.addBlockInfo(blockPlaceEvent.getBlock().getLocation(), KEY, VALUE_ALLOW);
                BlockStorage.addBlockInfo(blockPlaceEvent.getBlock().getLocation(), ManualMachineMenu.KEY, "0");
            }
        });
    }

    @Override
    public void register(@Nonnull SlimefunAddon addon) {
        this.addon = addon;
        super.register(addon);
        getServer().getScheduler().runTask((Plugin)addon, this::registerDefaultRecipes);
        this.menu.setMachineRecipes(this.recipes);
    }

    @Override
    protected void tick(Block block) {
        String update = BlockStorage.getLocationInfo(block.getLocation(), KEY);
        if(VALUE_DENY.equals(update)) {
            BlockStorage.addBlockInfo(block.getLocation(), KEY, VALUE_ALLOW);
        } else if(VALUE_UPDATE.equals(update)) {
            menu.updateMenu(BlockStorage.getInventory(block.getLocation()), block);
        }
    }

    @Override
    public List<MachineRecipe> getMachineRecipes() {
        return this.recipes;
    }
}
