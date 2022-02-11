package io.taraxacum.finaltech.machine;

import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.taraxacum.finaltech.abstractItem.machine.AbstractMachine;
import io.taraxacum.finaltech.abstractItem.machine.AbstractStandardMachine;
import io.taraxacum.finaltech.abstractItem.menu.AbstractStandardMachineMenu;
import io.taraxacum.finaltech.core.AllCompressionCraftingOperation;
import io.taraxacum.finaltech.menu.AllFactoryMenu;
import io.taraxacum.finaltech.util.ItemStackUtil;
import me.mrCookieSlime.Slimefun.api.BlockStorage;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.block.Block;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

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

    @Override
    protected AbstractStandardMachineMenu setMachineMenu() {
        return new AllFactoryMenu(this.getId(), this.getItemName(), this);
    }

    @Override
    protected void tick(Block b) {
        BlockMenu inv = BlockStorage.getInventory(b);
        ItemStack itemStack = inv.getItemInSlot(this.getInputSlots()[0]);
        if (!ItemStackUtil.isItemNull(itemStack)) {
            ItemStack output = new ItemStack(itemStack);
            ItemMeta itemMeta = output.getItemMeta();
            List<String> lore = itemMeta.getLore();
            if(lore == null) {
                return;
            }
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
                    inv.pushItem(output, this.getOutputSlots());
                    return;
                }
            }
        }
    }

    @Override
    public void registerDefaultRecipes() { }
}
