package io.taraxacum.finaltech.abstractItem;

import io.github.thebusybiscuit.slimefun4.api.SlimefunAddon;
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
import io.taraxacum.finaltech.util.FinalUtil;
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
import java.util.Iterator;
import java.util.List;

public abstract class FinalMachine extends SlimefunItem implements RecipeDisplayItem, MachineProcessHolder<CraftingOperation> {
    private final MachineMenu machineMenu;
    protected final List<MachineRecipe> recipes;
    private final MachineProcessor<CraftingOperation> processor;

    public FinalMachine(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
        super(itemGroup, item, recipeType, recipe);
        this.recipes = new ArrayList();
        this.processor = new MachineProcessor(this);
        this.processor.setProgressBar(new CustomItemStack(Material.REDSTONE, "工作状态"));
        this.machineMenu = this.setMachineMenu();
        this.addItemHandler(this.onBlockBreak());
    }

    @Nonnull
    protected BlockBreakHandler onBlockBreak() {
        return new SimpleBlockBreakHandler() {
            public void onBlockBreak(Block b) {
                BlockMenu inv = BlockStorage.getInventory(b);
                if (inv != null) {
                    inv.dropItems(b.getLocation(), machineMenu.getInputSlots());
                    inv.dropItems(b.getLocation(), machineMenu.getOutputSlots());
                }

                FinalMachine.this.processor.endOperation(b);
            }
        };
    }

    protected abstract MachineMenu setMachineMenu();

    protected int[] getInputSlots() {
        return machineMenu.getInputSlots();
    }

    protected int[] getOutputSlots() {
        return machineMenu.getOutputSlots();
    }

    @Nonnull
    @Override
    public MachineProcessor<CraftingOperation> getMachineProcessor() {
        return this.processor;
    }

    @Override
    public void register(@Nonnull SlimefunAddon addon) {
        this.addon = addon;
        super.register(addon);
    }

    @Override
    public void preRegister() {
        super.preRegister();
        this.addItemHandler(new BlockTicker() {
            public void tick(Block b, SlimefunItem sf, Config data) {
                FinalMachine.this.tick(b);
            }

            public boolean isSynchronized() {
                return false;
            }
        });
        this.addItemHandler(new BlockPlaceHandler(false) {
            @Override
            public void onPlayerPlace(@Nonnull BlockPlaceEvent blockPlaceEvent) {
                BlockStorage.addBlockInfo(blockPlaceEvent.getBlock().getLocation(), FinalUtil.MACHINE_MAX_STACK, "0");
            }
        });
    }

    @Nonnull
    public abstract String getMachineIdentifier();

    public void registerRecipe(MachineRecipe recipe) {
        this.recipes.add(recipe);
    }

    public void registerRecipe(int seconds, ItemStack[] input, ItemStack[] output) {
        this.registerRecipe(new MachineRecipe(seconds, input, output));
    }

    public void registerRecipe(int seconds, ItemStack input, ItemStack output) {
        this.registerRecipe(new MachineRecipe(seconds, new ItemStack[]{input}, new ItemStack[]{output}));
    }

    @Nonnull
    @Override
    public List<ItemStack> getDisplayRecipes() {
        List<ItemStack> displayRecipes = new ArrayList(this.recipes.size() * 2);
        Iterator iterator = this.recipes.iterator();
        while(iterator.hasNext()) {
            MachineRecipe recipe = (MachineRecipe)iterator.next();
            Integer inputLength = recipe.getInput().length;
            Integer outputLength = recipe.getOutput().length;
            for(int i = 0; i < inputLength; i++) {
                displayRecipes.add(recipe.getInput()[i]);
                if(i < inputLength-1) {
                    displayRecipes.add(new ItemStack(Material.AIR));
                }
            }
            for(int i = 0; i < outputLength; i++) {
                if(i != 0) {
                    displayRecipes.add(new ItemStack(Material.AIR));
                }
                displayRecipes.add(recipe.getOutput()[i]);
            }
        }
        return displayRecipes;
    }

    protected abstract void tick(Block block);
}
