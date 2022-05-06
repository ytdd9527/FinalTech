package io.taraxacum.finaltech.items.machine;

import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.core.handlers.BlockBreakHandler;
import io.github.thebusybiscuit.slimefun4.core.handlers.BlockPlaceHandler;
import io.taraxacum.finaltech.api.interfaces.RecipeItem;
import io.taraxacum.finaltech.dto.ItemStackWithWrapper;
import io.taraxacum.finaltech.menu.AbstractMachineMenu;
import io.taraxacum.finaltech.menu.machine.OrderedDustFactoryV2Menu;
import io.taraxacum.finaltech.setup.register.FinalTechItems;
import io.taraxacum.finaltech.util.ItemStackUtil;
import io.taraxacum.finaltech.util.MachineUtil;
import me.mrCookieSlime.CSCoreLibPlugin.Configuration.Config;
import me.mrCookieSlime.Slimefun.api.BlockStorage;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import org.bukkit.block.Block;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Final_ROOT
 */
public class DustFactoryStone extends AbstractMachine implements RecipeItem {
    public DustFactoryStone(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
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
        return MachineUtil.simpleBlockBreakerHandler(this);
    }

    @Nonnull
    @Override
    protected AbstractMachineMenu setMachineMenu() {
        return new OrderedDustFactoryV2Menu(this);
    }

    @Override
    protected void tick(@Nonnull Block block, @Nonnull SlimefunItem slimefunItem, @Nonnull Config config) {
        BlockMenu blockMenu = BlockStorage.getInventory(block);
        List<Integer> amountList = new ArrayList<>(this.getInputSlot().length);
        List<ItemStackWithWrapper> itemList = new ArrayList<>(this.getInputSlot().length);
        for (int slot : this.getInputSlot()) {
            ItemStack item = blockMenu.getItemInSlot(slot);
            if (ItemStackUtil.isItemNull(item)) {
                return;
            }
            if (!amountList.contains(item.getAmount())) {
                amountList.add(item.getAmount());
            }
            boolean contain = false;
            for(ItemStackWithWrapper itemWithWrapper : itemList) {
                if(ItemStackUtil.isItemSimilar(itemWithWrapper, itemWithWrapper)) {
                    contain = true;
                    break;
                }
            }
            if(!contain && itemList.size() <= 1) {
                itemList.add(new ItemStackWithWrapper(item));
            }
        }
        for (int slot : this.getInputSlot()) {
            blockMenu.replaceExistingItem(slot, ItemStackUtil.AIR);
        }
        if (amountList.size() == this.getInputSlot().length && itemList.size() == 1) {
            blockMenu.pushItem(FinalTechItems.ORDERED_DUST.clone(), this.getOutputSlot());
        } else if(Math.random() < (double)(amountList.size()) / this.getInputSlot().length) {
            blockMenu.pushItem(FinalTechItems.UNORDERED_DUST.clone(), this.getOutputSlot());
        }
    }

    @Override
    protected boolean isSynchronized() {
        return false;
    }

    @Override
    public void registerDefaultRecipes() {
        this.registerDescriptiveRecipe("&f制造无序尘埃",
                "",
                "&f在机器界面左侧所有的" + this.getInputSlot().length + "格上都放有物品",
                "&f然后会消耗其上的所有物品",
                "&f并生产一个无序尘埃");
        this.registerDescriptiveRecipe("&f制造有序尘埃",
                "",
                "&f在机器界面左侧所有的" + this.getInputSlot().length + "格上都放有物品",
                "&f并且各个格子上的物品数量各不相同",
                "&f然后会消耗其上的所有物品",
                "&f并生产一个有序尘埃");
    }
}
