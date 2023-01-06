package io.taraxacum.finaltech.core.item.machine;

import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.core.handlers.BlockBreakHandler;
import io.github.thebusybiscuit.slimefun4.core.handlers.BlockPlaceHandler;
import io.taraxacum.finaltech.FinalTech;
import io.taraxacum.finaltech.core.item.AbstractMySlimefunItem;
import io.taraxacum.finaltech.core.menu.AbstractMachineMenu;
import io.taraxacum.finaltech.util.BlockTickerUtil;
import me.mrCookieSlime.CSCoreLibPlugin.Configuration.Config;
import me.mrCookieSlime.Slimefun.Objects.handlers.BlockTicker;
import org.bukkit.block.Block;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * @author Final_ROOT
 * @since 1.0
 */
// TODO: Optimization
public abstract class AbstractMachine extends AbstractMySlimefunItem {
    private final AbstractMachineMenu menu;

    public AbstractMachine(@Nonnull ItemGroup itemGroup, @Nonnull SlimefunItemStack item, @Nonnull RecipeType recipeType, @Nonnull ItemStack[] recipe) {
        super(itemGroup, item, recipeType, recipe);
        this.menu = this.setMachineMenu();
    }

    @Override
    public void preRegister() {
        super.preRegister();
        this.addItemHandler(this.onBlockBreak());
        this.addItemHandler(this.onBlockPlace());

        if (FinalTech.getMultiThreadLevel() == 2) {
            this.getAddon().getJavaPlugin().getLogger().info(this.getId() + "(" + this.getItemName() + ")" + " is optimized for multi-thread！！！");
        } else if (!this.isSynchronized() && (FinalTech.getMultiThreadLevel() == 1 || FinalTech.isAsyncSlimefunItem(this.getId()))) {
            this.getAddon().getJavaPlugin().getLogger().info(this.getId() + "(" + this.getItemName() + ")" + " is optimized for multi-thread！！！");
        }

        BlockTicker blockTicker;
        if (FinalTech.getMultiThreadLevel() == 2) {
            blockTicker = new BlockTicker() {
                @Override
                public boolean isSynchronized() {
                    return false;
                }

                @Override
                public void tick(Block b, SlimefunItem item, Config data) {
                    AbstractMachine.this.tick(b, item, data);
                }
            };
            if(FinalTech.debugMode()) {
                blockTicker = BlockTickerUtil.getDebugModeBlockTicker(blockTicker, this);
            }
            this.addItemHandler(BlockTickerUtil.generateBlockTicker(blockTicker, true, FinalTech.isAntiAccelerateSlimefunItem(this.getId()), FinalTech.isPerformanceLimitSlimefunItem(this.getId())));
        } else {
            blockTicker = new BlockTicker() {
                @Override
                public boolean isSynchronized() {
                    return AbstractMachine.this.isSynchronized();
                }

                @Override
                public void tick(Block b, SlimefunItem item, Config data) {
                    AbstractMachine.this.tick(b, item, data);
                }
            };
            if(FinalTech.debugMode()) {
                blockTicker = BlockTickerUtil.getDebugModeBlockTicker(blockTicker, this);
            }
            this.addItemHandler(BlockTickerUtil.generateBlockTicker(blockTicker, !this.isSynchronized() && (FinalTech.getMultiThreadLevel() == 1 || FinalTech.isAsyncSlimefunItem(this.getId())), FinalTech.isAntiAccelerateSlimefunItem(this.getId()), FinalTech.isPerformanceLimitSlimefunItem(this.getId())));
        }
    }

    @Nonnull
    public final int[] getInputSlot() {
        return this.menu == null ? new int[0] : this.menu.getInputSlot();
    }

    @Nonnull
    public final int[] getOutputSlot() {
        return this.menu == null ? new int[0] : this.menu.getOutputSlot();
    }

    @Nonnull
    protected abstract BlockPlaceHandler onBlockPlace();

    @Nonnull
    protected abstract BlockBreakHandler onBlockBreak();

    @Nullable
    protected abstract AbstractMachineMenu setMachineMenu();

    protected abstract void tick(@Nonnull Block block, @Nonnull SlimefunItem slimefunItem, @Nonnull Config config);

    protected abstract boolean isSynchronized();
}
