package io.taraxacum.finaltech.cargo;

import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.core.handlers.BlockBreakHandler;
import io.taraxacum.finaltech.abstractItem.FinalCargoMachine;
import io.taraxacum.finaltech.menu.NormalBarrelMenu;
import me.mrCookieSlime.Slimefun.api.BlockStorage;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import org.bukkit.block.Block;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class DoubleNormalBarrel extends FinalCargoMachine {
    public DoubleNormalBarrel(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
        super(itemGroup, item, recipeType, recipe);
        new NormalBarrelMenu(this.getId(), this.getItemName());
    }

    @Override
    public void preRegister() {
        super.preRegister();
        this.addItemHandler(new BlockBreakHandler(false, false) {
            @Override
            public void onPlayerBreak(BlockBreakEvent blockBreakEvent, ItemStack itemStack, List<ItemStack> list) {
                BlockStorage.clearBlockInfo(blockBreakEvent.getBlock());
                if (BlockStorage.hasInventory(blockBreakEvent.getBlock())) {
                    BlockMenu blockMenu = BlockStorage.getInventory(blockBreakEvent.getBlock());
                    blockMenu.dropItems(blockMenu.getLocation(), NormalBarrelMenu.CONTAIN);
                }
            }
        });
    }

    @Override
    protected void tick(Block block) {

    }
}
