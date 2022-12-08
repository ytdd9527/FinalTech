package io.taraxacum.finaltech.core.item.machine.template.extraction;

import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.taraxacum.finaltech.api.interfaces.item.RecipeItem;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

/**
 * @author Final_ROOT
 * @since 2.0
 */
public class OreExtraction extends AbstractExtractionMachine implements RecipeItem {
    public OreExtraction(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
        super(itemGroup, item, recipeType, recipe);
    }

    @Override
    public void registerDefaultRecipes() {
        this.registerRecipe(Material.COAL_ORE, Material.COAL);
        this.registerRecipe(Material.DEEPSLATE_COAL_ORE, Material.COAL);
        this.registerRecipe(Material.COPPER_ORE, Material.RAW_COPPER);
        this.registerRecipe(Material.DEEPSLATE_COPPER_ORE, Material.RAW_COPPER);
        this.registerRecipe(Material.IRON_ORE, Material.RAW_IRON);
        this.registerRecipe(Material.DEEPSLATE_IRON_ORE, Material.RAW_IRON);
        this.registerRecipe(Material.GOLD_ORE, Material.RAW_GOLD);
        this.registerRecipe(Material.DEEPSLATE_GOLD_ORE, Material.RAW_GOLD);
        this.registerRecipe(Material.REDSTONE_ORE, Material.REDSTONE);
        this.registerRecipe(Material.DEEPSLATE_REDSTONE_ORE, Material.REDSTONE);
        this.registerRecipe(Material.LAPIS_ORE, Material.LAPIS_LAZULI);
        this.registerRecipe(Material.DEEPSLATE_LAPIS_ORE, Material.LAPIS_LAZULI);
        this.registerRecipe(Material.DIAMOND_ORE, Material.DIAMOND);
        this.registerRecipe(Material.DEEPSLATE_DIAMOND_ORE, Material.DIAMOND);
        this.registerRecipe(Material.EMERALD_ORE, Material.EMERALD);
        this.registerRecipe(Material.DEEPSLATE_EMERALD_ORE, Material.EMERALD);
        this.registerRecipe(Material.NETHER_GOLD_ORE, Material.GOLD_NUGGET);
        this.registerRecipe(Material.NETHER_QUARTZ_ORE, Material.QUARTZ);
        this.registerRecipe(Material.ANCIENT_DEBRIS, Material.NETHERITE_SCRAP);
        this.registerRecipe(Material.AMETHYST_CLUSTER, Material.AMETHYST_SHARD);
    }
}
