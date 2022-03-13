package io.taraxacum.finaltech.core;

import io.github.thebusybiscuit.slimefun4.implementation.operations.CraftingOperation;
import io.github.thebusybiscuit.slimefun4.libraries.dough.items.CustomItemStack;
import io.taraxacum.finaltech.machine.standard.AllCompression;
import io.taraxacum.finaltech.setup.FinalTechItems;
import io.taraxacum.finaltech.util.ItemStackUtil;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.abstractItems.MachineRecipe;
import org.bukkit.ChatColor;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

/**
 * @author Final_ROOT
 */
public class AllCompressionCraftingOperation extends CraftingOperation {
    public static final String ALL_FACTORY_ITEM_LORE = "§x§f§f§0§0§0§0已" +
            "§x§f§f§3§3§0§0经" +
            "§x§f§f§6§6§0§0由" +
            "§x§f§f§a§a§0§0Fi" +
            "§x§f§f§b§b§0§0na" +
            "§x§f§f§d§d§0§0l_" +
            "§x§f§f§f§f§0§0R" +
            "§x§c§c§f§f§0§0O" +
            "§x§8§8§f§f§0§0O" +
            "§x§4§4§f§f§0§0T" +
            "§x§0§0§f§f§0§0签" +
            "§x§0§0§d§d§4§4名" +
            "§x§0§0§b§b§8§8认" +
            "§x§0§0§a§a§c§c证" +
            "§x§0§0§8§8§f§f并" +
            "§x§0§0§5§5§f§f允" +
            "§x§0§0§2§2§f§f许" +
            "§x§0§0§0§0§f§f进" +
            "§x§3§3§0§0§f§f行" +
            "§x§6§6§0§0§f§f复" +
            "§x§9§9§0§0§f§f制";
    public static final String ALL_FACTORY_ITEM_LORE_WITHOUT_COLOR = "已经由Final_ROOT签名认证并允许进行复制";
    private static final ItemStack SINGULARITY_ICON = new CustomItemStack(FinalTechItems.SINGULARITY, "&f奇点", "");

    private final ItemStack input;
    private final ItemStack output;
    private final int difficulty;
    private final boolean singularity;

    private int count = 0;

    public AllCompressionCraftingOperation(ItemStack item) {
        this(new MachineRecipe(0, new ItemStack[] {new ItemStack(item)}, new ItemStack[] {new ItemStack(item)}));
        this.addItem(item);
    }

    private AllCompressionCraftingOperation(MachineRecipe machineRecipe) {
        super(machineRecipe);
        this.singularity = containLore(machineRecipe.getInput()[0]);
        if(this.singularity) {
            this.input = SINGULARITY_ICON;
            this.output = new ItemStack(FinalTechItems.SINGULARITY);
            this.difficulty = AllCompression.SINGULARITY_DIFFICULTY;
        } else {
            this.input = machineRecipe.getInput()[0];
            this.input.setAmount(1);
            this.output = machineRecipe.getOutput()[0];
            this.output.setAmount(1);
            ItemStackUtil.addLoreToLast(this.output, ALL_FACTORY_ITEM_LORE);
            this.difficulty = AllCompression.DIFFICULTY;
        }
    }

    public int addItem(ItemStack item) {
        if(this.isFinished()) {
            return 0;
        }
        if(this.singularity) {
            if(containLore(item)) {
                int count = Math.min(item.getAmount(), this.difficulty - this.count);
                this.count += count;
                item.setAmount(item.getAmount() - count);
                return count;
            }
        } else {
            if(ItemStackUtil.isItemNull(item)) {
                return 0;
            }
            if(ItemStackUtil.isItemSimilar(item, input)) {
                int count = Math.min(item.getAmount(), this.difficulty - this.count);
                this.count += count;
                item.setAmount(item.getAmount() - count);
                return count;
            } else if(ItemStackUtil.isItemSimilar(item, FinalTechItems.SINGULARITY)) {
                int count = Math.min(item.getAmount(), this.difficulty - this.count);
                count = Math.min(count, this.difficulty - 1 );
                this.count += count;
                item.setAmount(item.getAmount() - count);
                return count;
            }
        }
        return 0;
    }

    public ItemStack getInput() {
        return this.input;
    }

    public ItemStack getOutput() {
        return this.output;
    }

    public int getCount() {
        return this.count;
    }

    public int getDifficulty() {
        return this.difficulty;
    }

    @Override
    public boolean isFinished() {
        return (this.count >= this.difficulty);
    }

    private static boolean containLore(ItemStack itemStack) {
        if(itemStack == null) {
            return false;
        }
        ItemMeta itemMeta = itemStack.getItemMeta();
        List<String> lore = itemMeta.getLore();
        if(lore == null) {
            return false;
        }
        for(String l : lore) {
            if(ALL_FACTORY_ITEM_LORE_WITHOUT_COLOR.equals(ChatColor.stripColor(l))) {
                return true;
            }
        }
        return false;
    }
}
