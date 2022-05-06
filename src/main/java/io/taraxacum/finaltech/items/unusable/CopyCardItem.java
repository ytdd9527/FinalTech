package io.taraxacum.finaltech.items.unusable;

import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.taraxacum.finaltech.setup.register.FinalTechItems;
import io.taraxacum.finaltech.util.ItemStackUtil;
import io.taraxacum.finaltech.util.StringItemUtil;
import org.bukkit.ChatColor;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

/**
 * @author Final_ROOT
 */
public class CopyCardItem extends UnusableSlimefunItem {
    public static final String ITEM_LORE = "§x§f§f§0§0§0§0⌫" +
            "§x§f§f§3§3§0§0⌧" +
            "§x§f§f§6§6§0§0⌧" +
            "§x§f§f§a§a§0§0⌧" +
            "§x§f§f§b§b§0§0⌧" +
            "§x§f§f§d§d§0§0⌧" +
            "§x§f§f§f§f§0§0⌧" +
            "§x§c§c§f§f§0§0⌧" +
            "§x§8§8§f§f§0§0⌧" +
            "§x§4§4§f§f§0§0⌧" +
            "§x§0§0§f§f§0§0⌧" +
            "§x§0§0§d§d§4§4⌧" +
            "§x§0§0§b§b§8§8⌧" +
            "§x§0§0§a§a§c§c⌧" +
            "§x§0§0§8§8§f§f⌧" +
            "§x§0§0§5§5§f§f⌧" +
            "§x§0§0§2§2§f§f⌧" +
            "§x§0§0§0§0§f§f⌧" +
            "§x§3§3§0§0§f§f⌧" +
            "§x§6§6§0§0§f§f⌧" +
            "§x§9§9§0§0§f§f⌦";
    public static final String ITEM_LORE_WITHOUT_COLOR = "⌫⌧⌧⌧⌧⌧⌧⌧⌧⌧⌧⌧⌧⌧⌧⌧⌧⌧⌧⌧⌦";
    //todo
    // 可配置化
    public static int COPY_CARD_DIFFICULTY = 16777216;
    public static int SINGULARITY_DIFFICULTY = 128;
    public static int SPIROCHETE_DIFFICULTY = 32;
    public static int DEFAULT_SINGULARITY_DIFFICULTY = 128;
    public static int DEFAULT_SPIROCHETE_DIFFICULTY = 32;

    public CopyCardItem(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
        super(itemGroup, item, recipeType, recipe);
    }

    public static void initDifficulty(int singularityDifficulty, int spirocheteDifficulty) {
        SINGULARITY_DIFFICULTY = singularityDifficulty;
        SPIROCHETE_DIFFICULTY = spirocheteDifficulty;
    }

    public static boolean isValid(@Nullable ItemStack itemStack) {
        if (ItemStackUtil.isItemNull(itemStack) || !itemStack.hasItemMeta()) {
            return false;
        }
        ItemMeta itemMeta = itemStack.getItemMeta();
        List<String> lore = itemMeta.getLore();
        if (lore == null) {
            return false;
        }
        for (String l : lore) {
            if (CopyCardItem.ITEM_LORE_WITHOUT_COLOR.equals(ChatColor.stripColor(l))) {
                return true;
            }
        }
        return false;
    }

    public static ItemStack newItem(@Nonnull ItemStack stringItem, @Nonnull String amount) {
        ItemStack result = new ItemStack(FinalTechItems.COPY_CARD);
        result.setAmount(1);
        StringItemUtil.setItemInCard(result, stringItem, amount);
        ItemStackUtil.setLore(result, CopyCardItem.ITEM_LORE, "§7物品= " + ItemStackUtil.getItemName(stringItem), "§7数量= " + amount);
        return result;
    }
}
