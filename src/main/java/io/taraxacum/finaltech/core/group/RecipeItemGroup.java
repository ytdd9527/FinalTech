package io.taraxacum.finaltech.core.group;

import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.api.items.groups.FlexItemGroup;
import io.github.thebusybiscuit.slimefun4.api.player.PlayerProfile;
import io.github.thebusybiscuit.slimefun4.core.guide.SlimefunGuideMode;
import io.taraxacum.finaltech.FinalTech;
import io.taraxacum.finaltech.core.menu.common.SlimefunItemBigRecipeMenu;
import io.taraxacum.finaltech.core.menu.common.SlimefunItemSmallRecipeMenu;
import io.taraxacum.finaltech.util.ItemStackUtil;
import me.mrCookieSlime.CSCoreLibPlugin.general.Inventory.ChestMenu;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Final_ROOT
 * @since 2.0
 */
public class RecipeItemGroup extends FlexItemGroup {
    private final String id;
    private final int page;

    private static final Map<String, RecipeItemGroup> ID_MAP = new HashMap<>();

    private static final int SMALL_LIMIT = 9;
    private static final int BIG_LIMIT = 36;

    public RecipeItemGroup(@Nonnull NamespacedKey key, @Nonnull SlimefunItem slimefunItem) {
        super(key, ItemStackUtil.cloneItem(slimefunItem.getItem()));
        this.id = slimefunItem.getId();
        this.page = 1;
    }

    public RecipeItemGroup(@Nonnull NamespacedKey key, @Nonnull SlimefunItem slimefunItem, int page) {
        super(key, ItemStackUtil.cloneItem(slimefunItem.getItem()));
        this.id = slimefunItem.getId();
        this.page = page;
    }

    @Override
    public boolean isVisible(@Nonnull Player player, @Nonnull PlayerProfile playerProfile, @Nonnull SlimefunGuideMode slimefunGuideMode) {
        return false;
    }

    @Override
    public void open(@Nonnull Player player, @Nonnull PlayerProfile playerProfile, @Nonnull SlimefunGuideMode slimefunGuideMode) {
        playerProfile.getGuideHistory().add(this, this.page);
        ChestMenu chestMenu = this.generateMenu(player, playerProfile, slimefunGuideMode);
        if(chestMenu != null) {
            chestMenu.open(player);
        }
    }

    @Nullable
    private ChestMenu generateMenu(@Nonnull Player player, @Nonnull PlayerProfile playerProfile, @Nonnull SlimefunGuideMode slimefunGuideMode) {
        SlimefunItem slimefunItem = SlimefunItem.getById(this.id);
        if (slimefunItem != null) {
            if(slimefunItem.getRecipe().length <= SMALL_LIMIT) {
                return new SlimefunItemSmallRecipeMenu(player, playerProfile, slimefunGuideMode, slimefunItem, this, this.page);
            } else if(slimefunItem.getRecipe().length <= BIG_LIMIT) {
                return new SlimefunItemBigRecipeMenu(player, playerProfile, slimefunGuideMode, slimefunItem, this, this.page);
            } else {
                // TODO support vary large recipe of slimefunItem
                return null;
            }
        } else {
            // TODO vanilla item recipe
        }
        return null;
    }

    @Nullable
    public static RecipeItemGroup getByItemStack(@Nonnull Player player, @Nonnull PlayerProfile playerProfile, @Nonnull SlimefunGuideMode slimefunGuideMode, @Nonnull ItemStack itemStack, int page) {
        SlimefunItem slimefunItem = SlimefunItem.getByItem(itemStack);
        if(slimefunItem != null) {
            if(page == 1) {
                if(ID_MAP.containsKey(slimefunItem.getId())) {
                    return ID_MAP.get(slimefunItem.getId());
                } else {
                    synchronized (ID_MAP) {
                        if(ID_MAP.containsKey(slimefunItem.getId())) {
                            return ID_MAP.get(slimefunItem.getId());
                        }
                        RecipeItemGroup recipeItemGroup = new RecipeItemGroup(new NamespacedKey(FinalTech.getInstance(), "SLIMEFUN_ITEM" + slimefunItem.getId() + "_" + page), slimefunItem);
                        ID_MAP.put(slimefunItem.getId(), recipeItemGroup);
                        return recipeItemGroup;
                    }
                }
            } else {
                return new RecipeItemGroup(new NamespacedKey(FinalTech.getInstance(), "SLIMEFUN_ITEM" + slimefunItem.getId()), slimefunItem, page);
            }
        } else {
            Material material = itemStack.getType();
            if(ItemStackUtil.isItemSimilar(itemStack, new ItemStack(material))) {
                // TODO vanilla item recipe
            } else {
                return null;
            }
        }
        return null;
    }

    @Nullable
    public static RecipeItemGroup getByItemStack(@Nonnull Player player, @Nonnull PlayerProfile playerProfile, @Nonnull SlimefunGuideMode slimefunGuideMode, @Nonnull ItemStack itemStack) {
        return RecipeItemGroup.getByItemStack(player, playerProfile, slimefunGuideMode, itemStack, 1);
    }
}
