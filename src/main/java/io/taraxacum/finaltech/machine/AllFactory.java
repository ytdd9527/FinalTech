package io.taraxacum.finaltech.machine;

import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.taraxacum.finaltech.abstractItem.FinalMachine;
import io.taraxacum.finaltech.abstractItem.MachineMenu;
import io.taraxacum.finaltech.menu.AllFactoryMenu;
import me.mrCookieSlime.Slimefun.api.BlockStorage;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import org.bukkit.block.Block;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

public class AllFactory extends FinalMachine {

    public AllFactory(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
        super(itemGroup, item, recipeType, recipe);
    }

    @Override
    protected MachineMenu setMachineMenu() {
        return new AllFactoryMenu(this.getId(), this.getItemName(), this);
    }

    @Override
    protected void tick(Block b) {
        BlockMenu inv = BlockStorage.getInventory(b);
        ItemStack itemStack = inv.getItemInSlot(this.getInputSlots()[0]);
        if (itemStack != null) {
            ItemMeta itemMeta = itemStack.getItemMeta();
            List<String> lore = itemMeta.getLore();
            if(lore.remove("已经由Final_ROOT签名认证，运行进行复制")) {
                lore.add("§8已经由Final_ROOT签名认证并允许进行复制");
            }

            ItemStack output = new ItemStack(itemStack);
            ItemMeta itemMeta1 = output.getItemMeta();
            List<String> lore1 = itemMeta1.getLore();
            if(lore1 == null) {
                lore1 = new ArrayList<>();
            }
            if(lore1.remove("§8已经由Final_ROOT签名认证并允许进行复制")) {
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
}
