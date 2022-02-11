package io.taraxacum.finaltech.abstractItem.machine;

import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.core.attributes.MachineProcessHolder;
import io.github.thebusybiscuit.slimefun4.core.attributes.RecipeDisplayItem;
import io.github.thebusybiscuit.slimefun4.core.handlers.BlockBreakHandler;
import io.github.thebusybiscuit.slimefun4.core.handlers.BlockPlaceHandler;
import io.github.thebusybiscuit.slimefun4.core.machines.MachineProcessor;
import io.github.thebusybiscuit.slimefun4.implementation.handlers.SimpleBlockBreakHandler;
import io.github.thebusybiscuit.slimefun4.implementation.operations.CraftingOperation;
import io.github.thebusybiscuit.slimefun4.libraries.dough.items.CustomItemStack;
import io.taraxacum.finaltech.core.RandomMachineRecipe;
import io.taraxacum.finaltech.interfaces.RecipeItem;
import io.taraxacum.finaltech.util.cargo.Icon;
import me.mrCookieSlime.CSCoreLibPlugin.Configuration.Config;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.abstractItems.MachineRecipe;
import me.mrCookieSlime.Slimefun.Objects.handlers.BlockTicker;
import me.mrCookieSlime.Slimefun.api.BlockStorage;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

/**
 * Standard标准机器
 * 指不与其他方块接触或交互的机器
 * 通常用于加工物品，即把输入的物品在特定条件下按照特定形式转化为输出物品
 * @author Final_ROOT
 */
public abstract class AbstractStandardMachine extends AbstractMachine implements MachineProcessHolder<CraftingOperation>, RecipeItem {
    private final MachineProcessor<CraftingOperation> processor;
    protected final List<MachineRecipe> recipes = new ArrayList<>();
    public AbstractStandardMachine(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
        super(itemGroup, item, recipeType, recipe);
        this.processor = new MachineProcessor<>(this);
        this.processor.setProgressBar(new CustomItemStack(Material.REDSTONE, "工作状态"));
    }

    /**
     * 方块被破坏时，中止其工作计数器
     */
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
                    AbstractStandardMachine.this.processor.endOperation(block);
                }
            }
        };
    }

    @Override
    public final List<MachineRecipe> getMachineRecipes() {
        return this.recipes;
    }

    @Nonnull
    @Override
    public final MachineProcessor<CraftingOperation> getMachineProcessor() {
        return this.processor;
    }

    @Override
    public void preRegister() {
        super.preRegister();
        this.addItemHandler(new BlockTicker() {
            @Override
            public void tick(Block block, SlimefunItem sf, Config data) {
                AbstractStandardMachine.this.tick(block);
            }
            @Override
            public boolean isSynchronized() {
                return false;
            }
        });
        this.addItemHandler(new BlockPlaceHandler(false) {
            @Override
            public void onPlayerPlace(@Nonnull BlockPlaceEvent blockPlaceEvent) {
                BlockStorage.addBlockInfo(blockPlaceEvent.getBlock().getLocation(), Icon.MACHINE_MAX_STACK, "0");
            }
        });
    }
}
