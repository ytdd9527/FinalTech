package io.taraxacum.finaltech.core.items.machine;

import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.core.handlers.BlockBreakHandler;
import io.github.thebusybiscuit.slimefun4.core.handlers.BlockPlaceHandler;
import io.github.thebusybiscuit.slimefun4.implementation.Slimefun;
import io.taraxacum.finaltech.FinalTech;
import io.taraxacum.finaltech.api.interfaces.RecipeItem;
import io.taraxacum.finaltech.core.items.machine.range.point.EquivalentConcept;
import io.taraxacum.finaltech.core.menu.AbstractMachineMenu;
import io.taraxacum.finaltech.setup.FinalTechItems;
import io.taraxacum.finaltech.util.slimefun.ConfigUtil;
import io.taraxacum.finaltech.util.slimefun.ConstantTableUtil;
import io.taraxacum.finaltech.util.slimefun.RecipeUtil;
import me.mrCookieSlime.CSCoreLibPlugin.Configuration.Config;
import me.mrCookieSlime.Slimefun.api.BlockStorage;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import javax.annotation.Nonnull;
import java.util.List;

/**
 * @author Final_ROOT
 * @since 2.0
 */
public class EntropySeed extends AbstractMachine implements RecipeItem {
    private final double equivalentConceptLife = ConfigUtil.getOrDefaultItemSetting(8.0, this, "life");
    private final int equivalentConceptRange = ConfigUtil.getOrDefaultItemSetting(2, this, "range");
    private final String key = "key";
    private final String value = "value";

    public EntropySeed(@Nonnull ItemGroup itemGroup, @Nonnull SlimefunItemStack item, @Nonnull RecipeType recipeType, @Nonnull ItemStack[] recipe) {
        super(itemGroup, item, recipeType, recipe);
    }

    @Nonnull
    @Override
    protected BlockPlaceHandler onBlockPlace() {
        return new BlockPlaceHandler(false) {
            @Override
            public void onPlayerPlace(@Nonnull BlockPlaceEvent e) {
                Location location = e.getBlock().getLocation();
                BlockStorage.addBlockInfo(location, EntropySeed.this.key, EntropySeed.this.value);
            }
        };
    }

    @Nonnull
    @Override
    protected BlockBreakHandler onBlockBreak() {
        return new BlockBreakHandler(false, true) {
            @Override
            public void onPlayerBreak(@Nonnull BlockBreakEvent blockBreakEvent, @Nonnull ItemStack item, @Nonnull List<ItemStack> drops) {
                blockBreakEvent.setDropItems(false);
                drops.clear();
            }
        };
    }

    @Nonnull
    @Override
    protected AbstractMachineMenu setMachineMenu() {
        return null;
    }

    @Override
    protected void tick(@Nonnull Block block, @Nonnull SlimefunItem slimefunItem, @Nonnull Config config) {
        Location location = block.getLocation();
        if(config.contains(this.key) && this.value.equals(config.getValue(this.key))) {
            BlockStorage.addBlockInfo(location, this.key, null);
            SlimefunItem sfItem = SlimefunItem.getByItem(FinalTechItems.EQUIVALENT_CONCEPT);
            if(sfItem != null) {
                BlockStorage.clearBlockInfo(location);
                JavaPlugin javaPlugin = this.getAddon().getJavaPlugin();
                javaPlugin.getServer().getScheduler().runTaskLaterAsynchronously(javaPlugin, () -> {
                    if(location.getBlock().getType().equals(EntropySeed.this.getItem().getType())) {
                        BlockStorage.addBlockInfo(location, ConstantTableUtil.CONFIG_ID, FinalTechItems.EQUIVALENT_CONCEPT.getItemId(), true);
                        BlockStorage.addBlockInfo(location, EquivalentConcept.KEY_LIFE, String.valueOf(EntropySeed.this.equivalentConceptLife));
                        BlockStorage.addBlockInfo(location, EquivalentConcept.KEY_RANGE, String.valueOf(EntropySeed.this.equivalentConceptRange));
                    }
                }, Slimefun.getTickerTask().getTickRate() + 1);
            }
        } else {
            BlockStorage.clearBlockInfo(location);
            JavaPlugin javaPlugin = this.getAddon().getJavaPlugin();
            javaPlugin.getServer().getScheduler().runTaskLaterAsynchronously(javaPlugin, () -> {
                if(location.getBlock().getType().equals(EntropySeed.this.getItem().getType())) {
                    BlockStorage.addBlockInfo(location, ConstantTableUtil.CONFIG_ID, FinalTechItems.JUSTIFIABILITY.getItemId(), true);
                }
            }, Slimefun.getTickerTask().getTickRate() + 1);
        }
    }

    @Override
    protected boolean isSynchronized() {
        return false;
    }

    @Override
    public void registerDefaultRecipes() {
        RecipeUtil.registerDescriptiveRecipe(FinalTech.getLanguageManager(), this);
    }
}
