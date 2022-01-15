package io.taraxacum.finaltech.machine;

import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.implementation.operations.CraftingOperation;
import io.taraxacum.finaltech.abstractItem.FinalBasicMachine;
import me.mrCookieSlime.Slimefun.api.BlockStorage;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import org.bukkit.block.Block;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

public class AllFactory extends FinalBasicMachine {

    public AllFactory(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
        super(itemGroup, item, recipeType, recipe);
    }

    @Override
    protected void tick(Block b) {
        BlockMenu inv = BlockStorage.getInventory(b);
        CraftingOperation currentOperation = this.getMachineProcessor().getOperation(b);

        ItemStack itemStack = inv.getItemInSlot(31);

        if (itemStack != null) {
            ItemMeta itemMeta = itemStack.getItemMeta();
            List<String> lore = itemMeta.getLore();
            ItemStack output = new ItemStack(itemStack);
            ItemMeta itemMeta1 = output.getItemMeta();
            List<String> lore1 = itemMeta1.getLore();
            if(lore1 == null) {
                lore1 = new ArrayList<>();
            }
            if(lore1.remove("已经由Final_ROOT签名认证，运行进行复制")) {
                itemMeta1.setLore(lore1);
                output.setItemMeta(itemMeta1);
                inv.pushItem(output, this.getOutputSlots());
            }

        }
    }

    @Nonnull
    @Override
    public String getMachineIdentifier() {
        return "FINALTECH_ALL_FACTORY";
    }

    @Override
    protected void tickBefore(Block block) {

    }

    @Override
    protected void tickAfter(Block block) {

    }
}
