package io.taraxacum.finaltech.core;

import io.github.thebusybiscuit.slimefun4.implementation.operations.CraftingOperation;
import io.github.thebusybiscuit.slimefun4.libraries.dough.items.CustomItemStack;
import io.github.thebusybiscuit.slimefun4.utils.SlimefunUtils;
import io.taraxacum.finaltech.machine.AllCompression;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.abstractItems.MachineRecipe;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class AllCompressionCraftingOperation extends CraftingOperation {

    private final ItemStack input;
    private final ItemStack output;
    private final MachineRecipe machineRecipe;

    private int inputCount = 0;

    public AllCompressionCraftingOperation(ItemStack item) {
        this(new MachineRecipe(0, new ItemStack[] {new CustomItemStack(item, 1)}, new ItemStack[] {item}));
        this.addItem(new CustomItemStack(item));
    }

    public AllCompressionCraftingOperation(MachineRecipe machineRecipe) {
        super(machineRecipe);
        this.machineRecipe = machineRecipe;
        this.input = new ItemStack(this.machineRecipe.getInput()[0]);
        ItemStack itemStack = new ItemStack(this.machineRecipe.getOutput()[0]);
        ItemMeta itemMeta = itemStack.getItemMeta();
        List<String> lore = itemMeta.getLore();
        if(lore == null) {
            lore = new ArrayList<>();
        }
        lore.add("§8已经由Final_ROOT签名认证并允许进行复制");
        itemMeta.setLore(lore);
        itemStack.setItemMeta(itemMeta);
        itemStack.setAmount(1);
        this.output = itemStack;
    }

    public boolean addItem(ItemStack item) {
        if(item == null) {
            return false;
        }
        if(SlimefunUtils.isItemSimilar(item, input, true, false)) {
            this.inputCount += item.getAmount();
            return true;
        }
        return false;
    }

    public ItemStack getInput() {
        return this.input;
    }

    public int getInputCount() {
        return this.inputCount;
    }

    public ItemStack getOutput() {
        return this.output;
    }

    @Override
    public boolean isFinished() {
        return (this.inputCount >= AllCompression.DIFFICULTY);
    }
}
