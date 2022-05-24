package io.taraxacum.finaltech.core.items.machine.standard;

import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.libraries.dough.items.CustomItemStack;
import io.taraxacum.finaltech.core.items.unusable.CopyCardItem;
import io.taraxacum.finaltech.core.menu.standard.AbstractStandardMachineMenu;
import io.taraxacum.finaltech.core.menu.standard.ItemDeserializeParserMenu;
import io.taraxacum.finaltech.util.ItemStackUtil;
import io.taraxacum.finaltech.util.StringItemUtil;
import me.mrCookieSlime.CSCoreLibPlugin.Configuration.Config;
import me.mrCookieSlime.Slimefun.api.BlockStorage;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;

// todo
/**
 * This is a slimefun machine
 * it will be used in gameplay
 * It's not a function class!
 * @author Final_ROOT
 */
public class ItemDeserializeParser extends AbstractStandardMachine {
    public ItemDeserializeParser(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
        super(itemGroup, item, recipeType, recipe);
        this.registerDefaultRecipes();
    }

    @Nonnull
    @Override
    protected AbstractStandardMachineMenu setMachineMenu() {
        return new ItemDeserializeParserMenu(this);
    }

    @Override
    protected void tick(@Nonnull Block block, @Nonnull SlimefunItem slimefunItem, @Nonnull Config config) {
        BlockMenu blockMenu = BlockStorage.getInventory(block);
        for (int slot : this.getInputSlot()) {
            ItemStack item = blockMenu.getItemInSlot(slot);
            if (CopyCardItem.isValid(item)) {
                ItemStack stringItem = StringItemUtil.parseItemInCard(item);
                if (!ItemStackUtil.isItemNull(stringItem)) {
                    stringItem.setAmount(item.getAmount());
                    blockMenu.pushItem(stringItem, this.getOutputSlot());
                }
            }
        }
    }

    @Override
    public void registerDefaultRecipes() {
        this.registerDescriptiveRecipe(new CustomItemStack(Material.BOOK, "&f复制物品",
                "",
                "&f中间放入复制卡",
                "&f输出该复制卡记录的物品",
                "&f可以放入堆叠的复制卡从而提升输出数量"));
    }
}
