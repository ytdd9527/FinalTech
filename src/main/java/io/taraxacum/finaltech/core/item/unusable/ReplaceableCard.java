package io.taraxacum.finaltech.core.item.unusable;

import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.taraxacum.finaltech.core.interfaces.UnCopiableItem;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;

/**
 * targetMaterial = this.getItem() + sourceMaterial
 * example:
 *      WATER_BUCKET = WATER_CARD + BUCKET
 *      DIAMOND_PICKAXE = DIAMOND_PICKAXE_CARD + NULL
 * @author Final_ROOT
 * @since 2.4
 */
public class ReplaceableCard extends UnusableSlimefunItem implements UnCopiableItem {
    private static final Map<Material, ReplaceableCard> MATERIAL_SLIMEFUN_ITEM_MAP = new HashMap<>();

    private final Material targetMaterial;
    @Nullable
    private final Material extraSourceMaterial;

    public ReplaceableCard(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe, @Nonnull Material targetMaterial, @Nullable Material extraSourceMaterial) {
        super(itemGroup, item, recipeType, recipe);
        if(MATERIAL_SLIMEFUN_ITEM_MAP.containsKey(targetMaterial)) {
            throw new IllegalArgumentException("duplicated material while registering " + this.getId());
        }
        MATERIAL_SLIMEFUN_ITEM_MAP.put(targetMaterial, this);
        this.targetMaterial = targetMaterial;
        this.extraSourceMaterial = extraSourceMaterial;
    }

    @Nullable
    public static ReplaceableCard getByMaterial(@Nonnull Material material) {
        return MATERIAL_SLIMEFUN_ITEM_MAP.get(material);
    }

    public Material getTargetMaterial() {
        return targetMaterial;
    }

    @Nullable
    public Material getExtraSourceMaterial() {
        return extraSourceMaterial;
    }
}
