package io.taraxacum.finaltech.machine.standard;

import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.libraries.dough.items.CustomItemStack;
import io.taraxacum.finaltech.machine.standard.AbstractStandardMachine;
import io.taraxacum.finaltech.menu.standard.AbstractStandardMachineMenu;
import io.taraxacum.finaltech.core.AllCompressionCraftingOperation;
import io.taraxacum.finaltech.menu.standard.AllFactoryMenu;
import io.taraxacum.finaltech.util.ItemStackUtil;
import me.mrCookieSlime.CSCoreLibPlugin.Configuration.Config;
import me.mrCookieSlime.Slimefun.api.BlockStorage;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

// todo
/**
 * @author Final_ROOT
 */
public class AllFactory extends AbstractStandardMachine {

    public AllFactory(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
        super(itemGroup, item, recipeType, recipe);
    }

    @Nonnull
    @Override
    protected AbstractStandardMachineMenu setMachineMenu() {
        return new AllFactoryMenu(this.getId(), this.getItemName(), this);
    }

    @Override
    protected void tick(@Nonnull Block block, @Nonnull SlimefunItem slimefunItem, @Nonnull Config config) {
        BlockMenu blockMenu = BlockStorage.getInventory(block);
        ItemStack item = blockMenu.getItemInSlot(this.getInputSlots()[0]);
        if (!ItemStackUtil.isItemNull(item) && item.hasItemMeta() && item.getItemMeta().hasLore()) {
            ItemStack output = new ItemStack(item);
            ItemMeta itemMeta = output.getItemMeta();
            List<String> lore = itemMeta.getLore();
            for(int i = 0; i < lore.size(); i++) {
                String l = lore.get(i);
                if(ChatColor.stripColor(l).equals(AllCompressionCraftingOperation.ALL_FACTORY_ITEM_LORE_WITHOUT_COLOR)) {
                    List<String> lore1 = new ArrayList<>();
                    for(int j = 0; j < lore.size(); j++) {
                        if(i != j) {
                            lore1.add(lore.get(j));
                        }
                    }
                    itemMeta.setLore(lore1);
                    output.setItemMeta(itemMeta);
                    blockMenu.pushItem(output, this.getOutputSlots());
                    return;
                }
            }
        }
    }

    @Override
    public void registerDefaultRecipes() {
        this.registerDescriptiveRecipe(new CustomItemStack(Material.BOOK, "&d使用方式",
                "",
                "&7中间放入带签名的物品",
                "&7输出不含签名的物品",
                "&f可以放入多个带签名的物品从而提升生产速率"));
    }
}
