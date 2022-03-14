package io.taraxacum.finaltech.item;

import io.github.thebusybiscuit.slimefun4.api.events.PlayerRightClickEvent;
import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.core.handlers.ItemUseHandler;
import io.taraxacum.finaltech.util.ItemStackUtil;
import io.taraxacum.finaltech.util.MachineUtil;
import org.bukkit.ChatColor;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public class CopyCardItem extends SlimefunItem {
    public static final String ITEM_LORE = "§x§f§f§0§0§0§0已" +
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
    public static final String ITEM_LORE_WITHOUT_COLOR = "已经由Final_ROOT签名认证并允许进行复制";
    public static final int DIFFICULTY = 16777216;
    public static final int SINGULARITY_DIFFICULTY = 256;

    public CopyCardItem(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
        super(itemGroup, item, recipeType, recipe);
        this.addItemHandler(MachineUtil.BLOCK_PLACE_HANDLER_DENY);
        this.addItemHandler((ItemUseHandler) PlayerRightClickEvent::cancel);
    }

    public static boolean isCopyCardItem(ItemStack itemStack) {
        if(ItemStackUtil.isItemNull(itemStack)) {
            return false;
        }
        ItemMeta itemMeta = itemStack.getItemMeta();
        List<String> lore = itemMeta.getLore();
        if(lore == null) {
            return false;
        }
        for(String l : lore) {
            if(CopyCardItem.ITEM_LORE_WITHOUT_COLOR.equals(ChatColor.stripColor(l))) {
                return true;
            }
        }
        return false;
    }
}
