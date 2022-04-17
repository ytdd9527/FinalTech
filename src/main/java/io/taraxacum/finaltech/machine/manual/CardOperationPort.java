package io.taraxacum.finaltech.machine.manual;

import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.core.handlers.BlockBreakHandler;
import io.github.thebusybiscuit.slimefun4.core.handlers.BlockPlaceHandler;
import io.taraxacum.finaltech.interfaces.RecipeItem;
import io.taraxacum.finaltech.menu.AbstractMachineMenu;
import io.taraxacum.finaltech.menu.CardOperationPortMenu;
import io.taraxacum.finaltech.util.MachineUtil;
import me.mrCookieSlime.CSCoreLibPlugin.Configuration.Config;
import me.mrCookieSlime.Slimefun.api.BlockStorage;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import org.bukkit.block.Block;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;

/**
 * @author Final_ROOT
 */
public class CardOperationPort extends AbstractManualMachine implements RecipeItem {
    public CardOperationPort(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
        super(itemGroup, item, recipeType, recipe);
        this.registerDefaultRecipes();
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

    @Override
    protected void tick(@Nonnull Block block, @Nonnull SlimefunItem slimefunItem, @Nonnull Config config) {
        BlockMenu blockMenu = BlockStorage.getInventory(block);
        if(!blockMenu.toInventory().getViewers().isEmpty()) {
            this.getMachineMenu().updateMenu(BlockStorage.getInventory(block.getLocation()), block);
        }
    }

    @Override
    protected AbstractMachineMenu newMachineMenu() {
        return new CardOperationPortMenu(this.getId(), this.getItemName(), this);
    }

    @Override
    public void registerDefaultRecipes() {
        this.registerDescriptiveRecipe("&f合并存储卡",
                "",
                "&f在左右两侧放置相同物品的存储卡",
                "&f点击合成",
                "&f将获得合并数量的复制卡");
        this.registerDescriptiveRecipe("&f制造复制卡",
                "",
                "&f在左右两侧分别放置存储卡和万物压缩器",
                "&f存储卡的物品数量需达到制作复制卡的数量",
                "&f点击合成",
                "&f将会获得该存储卡存储物品的复制卡");
        this.registerDescriptiveRecipe("&f制造壳",
                "",
                "&f在左右两侧分别放置奇点或螺旋体",
                "&f点击合成",
                "&f将会获得两个壳");
        this.registerDescriptiveRecipe("&f复制复制卡",
                "",
                "&f在左右两侧分别放置复制卡和壳",
                "&f点击合成",
                "&f将会获得一张相同的复制卡");
    }
}
