package io.taraxacum.finaltech.core.items.machine.manual;

import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.core.handlers.BlockBreakHandler;
import io.github.thebusybiscuit.slimefun4.core.handlers.BlockPlaceHandler;
import io.taraxacum.finaltech.api.interfaces.RecipeItem;
import io.taraxacum.finaltech.core.menu.manual.CardOperationPortMenu;
import io.taraxacum.finaltech.core.menu.manual.AbstractManualMachineMenu;
import io.taraxacum.finaltech.setup.FinalTechItems;
import io.taraxacum.finaltech.util.MachineUtil;
import io.taraxacum.finaltech.util.TextUtil;
import me.mrCookieSlime.CSCoreLibPlugin.Configuration.Config;
import me.mrCookieSlime.Slimefun.api.BlockStorage;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import org.bukkit.block.Block;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;
import java.time.temporal.Temporal;

/**
 * @author Final_ROOT
 */
public class CardOperationTable extends AbstractManualMachine implements RecipeItem {
    public CardOperationTable(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
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

    @Override
    protected void tick(@Nonnull Block block, @Nonnull SlimefunItem slimefunItem, @Nonnull Config config) {
        BlockMenu blockMenu = BlockStorage.getInventory(block);
        if (!blockMenu.toInventory().getViewers().isEmpty()) {
            this.getMachineMenu().updateMenu(BlockStorage.getInventory(block.getLocation()), block);
        }
    }

    @Nonnull
    @Override
    protected AbstractManualMachineMenu newMachineMenu() {
        return new CardOperationPortMenu(this);
    }

    @Override
    public void registerDefaultRecipes() {
        this.registerDescriptiveRecipe(TextUtil.COLOR_PASSIVE + "合并存储卡",
                "",
                TextUtil.COLOR_NORMAL + "在左右两侧放置相同物品的存储卡",
                TextUtil.COLOR_NORMAL + "点击合成",
                TextUtil.COLOR_NORMAL + "将获得一个 合并数量的存储卡");
        this.registerDescriptiveRecipe(TextUtil.COLOR_PASSIVE + "制造 " + FinalTechItems.ANNULAR.getDisplayName(),
                "",
                TextUtil.COLOR_NORMAL + "在左侧或右侧放置一个存储卡",
                TextUtil.COLOR_NORMAL + "存储卡的物品数量需达到制作复制卡的数量",
                TextUtil.COLOR_NORMAL + "点击合成",
                TextUtil.COLOR_NORMAL + "将会获得一个 " + FinalTechItems.ANNULAR.getDisplayName());
        this.registerDescriptiveRecipe(TextUtil.COLOR_PASSIVE + "制造 " + FinalTechItems.PHONY.getDisplayName(),
                "",
                TextUtil.COLOR_NORMAL + "在左右两侧分别放置 " + FinalTechItems.SINGULARITY.getDisplayName() + TextUtil.COLOR_NORMAL + " 与 " + FinalTechItems.SPIROCHETE.getDisplayName(),
                TextUtil.COLOR_NORMAL + "点击合成",
                TextUtil.COLOR_NORMAL + "将会获得一个 " + FinalTechItems.PHONY.getDisplayName());
        this.registerDescriptiveRecipe(TextUtil.COLOR_PASSIVE + "复制复制卡",
                "",
                TextUtil.COLOR_NORMAL + "在左右两侧分别放置复制卡和 " + FinalTechItems.SHELL.getDisplayName(),
                TextUtil.COLOR_NORMAL + "点击合成",
                TextUtil.COLOR_NORMAL + "将会获得一张相同的复制卡");
        this.registerDescriptiveRecipe(TextUtil.COLOR_PASSIVE + "制造 " + FinalTechItems.SHELL.getDisplayName(),
                "",
                TextUtil.COLOR_NORMAL + "在左侧或右侧放置一个 " + FinalTechItems.SINGULARITY.getDisplayName() + TextUtil.COLOR_NORMAL + " 或 " + FinalTechItems.SPIROCHETE.getDisplayName(),
                TextUtil.COLOR_NORMAL + "点击合成",
                TextUtil.COLOR_NORMAL + "将会获得一个 " + FinalTechItems.SHELL.getDisplayName());
        this.registerDescriptiveRecipe(TextUtil.COLOR_PASSIVE + "制造 " + FinalTechItems.ANNULAR.getDisplayName(),
                "",
                TextUtil.COLOR_NORMAL + "在左侧或右侧放置一个复制卡",
                TextUtil.COLOR_NORMAL + "点击合成",
                TextUtil.COLOR_NORMAL + "将会获得一个 " + FinalTechItems.ANNULAR.getDisplayName());
    }
}
