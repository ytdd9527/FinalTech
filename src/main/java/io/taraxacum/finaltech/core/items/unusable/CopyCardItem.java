package io.taraxacum.finaltech.core.items.unusable;

import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.implementation.Slimefun;
import io.taraxacum.finaltech.api.interfaces.RecipeItem;
import io.taraxacum.finaltech.setup.FinalTechItems;
import io.taraxacum.finaltech.util.ItemStackUtil;
import io.taraxacum.finaltech.util.StringItemUtil;
import io.taraxacum.finaltech.util.TextUtil;
import org.apache.commons.codec.language.bm.Rule;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Tag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

/**
 * @author Final_ROOT
 * @since 2.0
 */
public class CopyCardItem extends UnusableSlimefunItem implements RecipeItem {
    public static final String ITEM_LORE_WITHOUT_COLOR = "⌫⌧⌧⌧⌧⌧⌧⌧⌧⌧⌧⌧⌧⌧⌧⌧⌧⌧⌧⌧⌧⌧⌧⌦";
    public static final String ITEM_LORE = TextUtil.colorRandomString(ITEM_LORE_WITHOUT_COLOR);

    public static int DEFAULT_DIFFICULTY = 16777216;
    public static int DIFFICULTY = DEFAULT_DIFFICULTY;

    public CopyCardItem(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
        super(itemGroup, item, recipeType, recipe);
    }

    public static ItemStack newItem(@Nonnull ItemStack stringItem, @Nonnull String amount) {
        ItemStack result = new ItemStack(FinalTechItems.COPY_CARD);
        result.setAmount(1);
        StringItemUtil.setItemInCard(result, stringItem, amount);
        ItemStackUtil.setLore(result, CopyCardItem.ITEM_LORE, TextUtil.COLOR_NORMAL + "物品= §f" + ItemStackUtil.getItemName(stringItem), TextUtil.COLOR_NORMAL + "数量= " + TextUtil.COLOR_NUMBER + amount);
        return result;
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

    public static boolean copiableItem(@Nonnull ItemStack itemStack) {
        if(Singularity.isValid(itemStack) || Spirochete.isValid(itemStack) || ItemPhony.isValid(itemStack)) {
            return false;
        }

        if(Tag.SHULKER_BOXES.isTagged(itemStack.getType()) || Material.BUNDLE.equals(itemStack.getType())) {
            return false;
        }

        return true;
    }

    @Override
    public void registerDefaultRecipes() {
        this.registerDescriptiveRecipe(TextUtil.COLOR_POSITIVE + "说明",
                "",
                TextUtil.COLOR_NORMAL + "通过在 " + FinalTechItems.ITEM_SERIALIZATION_CONSTRUCTOR.getDisplayName() + TextUtil.COLOR_NORMAL + " 中压缩获得",
                "",
                TextUtil.COLOR_NORMAL + "放置在 " + FinalTechItems.ITEM_DESERIALIZE_PARSER.getDisplayName() + TextUtil.COLOR_NORMAL + " 中",
                TextUtil.COLOR_NORMAL + "每 " + TextUtil.COLOR_NUMBER + String.format("%.2f", Slimefun.getTickerTask().getTickRate() / 20.0) + "秒" + TextUtil.COLOR_NORMAL + " 复制一次该复制卡所记录的物品");
    }
}
