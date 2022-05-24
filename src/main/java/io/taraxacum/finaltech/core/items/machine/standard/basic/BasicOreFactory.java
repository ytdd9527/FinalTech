package io.taraxacum.finaltech.core.items.machine.standard.basic;

import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

/**
 * @author Final_ROOT
 */
public class BasicOreFactory extends AbstractBasicMachine {
    public BasicOreFactory(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
        super(itemGroup, item, recipeType, recipe);
    }

    @Override
    public void registerDefaultRecipes() {
        this.registerRecipe(0, new ItemStack[] { new ItemStack(Material.COAL_ORE, 4), new ItemStack(Material.COAL, 16), new ItemStack(Material.STONE, 64)}, new ItemStack[] { new ItemStack(Material.COAL_ORE, 16)});
        this.registerRecipe(0, new ItemStack[] { new ItemStack(Material.DEEPSLATE_COAL_ORE, 4), new ItemStack(Material.COAL, 16), new ItemStack(Material.DEEPSLATE, 64)}, new ItemStack[] { new ItemStack(Material.DEEPSLATE_COAL_ORE, 16)});
        this.registerRecipe(0, new ItemStack[] { new ItemStack(Material.IRON_ORE, 4), new ItemStack(Material.RAW_IRON, 16), new ItemStack(Material.STONE, 64)}, new ItemStack[] { new ItemStack(Material.IRON_ORE, 16)});
        this.registerRecipe(0, new ItemStack[] { new ItemStack(Material.DEEPSLATE_IRON_ORE, 4), new ItemStack(Material.RAW_IRON, 16), new ItemStack(Material.DEEPSLATE, 64)}, new ItemStack[] { new ItemStack(Material.DEEPSLATE_IRON_ORE, 16)});
        this.registerRecipe(0, new ItemStack[] { new ItemStack(Material.COPPER_ORE, 4), new ItemStack(Material.RAW_COPPER, 16), new ItemStack(Material.STONE, 64)}, new ItemStack[] { new ItemStack(Material.COPPER_ORE, 16)});
        this.registerRecipe(0, new ItemStack[] { new ItemStack(Material.DEEPSLATE_COPPER_ORE, 4), new ItemStack(Material.RAW_COPPER, 16), new ItemStack(Material.DEEPSLATE, 64)}, new ItemStack[] { new ItemStack(Material.DEEPSLATE_COPPER_ORE, 16)});
        this.registerRecipe(0, new ItemStack[] { new ItemStack(Material.GOLD_ORE, 4), new ItemStack(Material.RAW_GOLD, 16), new ItemStack(Material.STONE, 64)}, new ItemStack[] { new ItemStack(Material.GOLD_ORE, 16)});
        this.registerRecipe(0, new ItemStack[] { new ItemStack(Material.DEEPSLATE_GOLD_ORE, 4), new ItemStack(Material.RAW_GOLD, 16), new ItemStack(Material.DEEPSLATE, 64)}, new ItemStack[] { new ItemStack(Material.DEEPSLATE_GOLD_ORE, 16)});
        this.registerRecipe(0, new ItemStack[] { new ItemStack(Material.REDSTONE_ORE, 4), new ItemStack(Material.REDSTONE, 16), new ItemStack(Material.STONE, 64)}, new ItemStack[] { new ItemStack(Material.REDSTONE_ORE, 16)});
        this.registerRecipe(0, new ItemStack[] { new ItemStack(Material.DEEPSLATE_REDSTONE_ORE, 4), new ItemStack(Material.REDSTONE, 16), new ItemStack(Material.DEEPSLATE, 64)}, new ItemStack[] { new ItemStack(Material.DEEPSLATE_REDSTONE_ORE, 16)});
        this.registerRecipe(0, new ItemStack[] { new ItemStack(Material.EMERALD_ORE, 4), new ItemStack(Material.EMERALD, 16), new ItemStack(Material.STONE, 64)}, new ItemStack[] { new ItemStack(Material.EMERALD_ORE, 16)});
        this.registerRecipe(0, new ItemStack[] { new ItemStack(Material.DEEPSLATE_EMERALD_ORE, 4), new ItemStack(Material.EMERALD, 16), new ItemStack(Material.DEEPSLATE, 64)}, new ItemStack[] { new ItemStack(Material.DEEPSLATE_EMERALD_ORE, 16)});
        this.registerRecipe(0, new ItemStack[] { new ItemStack(Material.LAPIS_ORE, 4), new ItemStack(Material.LAPIS_LAZULI, 16), new ItemStack(Material.STONE, 64)}, new ItemStack[] { new ItemStack(Material.LAPIS_ORE, 16)});
        this.registerRecipe(0, new ItemStack[] { new ItemStack(Material.DEEPSLATE_LAPIS_ORE, 4), new ItemStack(Material.LAPIS_LAZULI, 16), new ItemStack(Material.DEEPSLATE, 64)}, new ItemStack[] { new ItemStack(Material.DEEPSLATE_LAPIS_ORE, 16)});
        this.registerRecipe(0, new ItemStack[] { new ItemStack(Material.DIAMOND_ORE, 4), new ItemStack(Material.DIAMOND, 16), new ItemStack(Material.STONE, 64)}, new ItemStack[] { new ItemStack(Material.DIAMOND_ORE, 16)});
        this.registerRecipe(0, new ItemStack[] { new ItemStack(Material.DEEPSLATE_DIAMOND_ORE, 4), new ItemStack(Material.DIAMOND, 16), new ItemStack(Material.DEEPSLATE, 64)}, new ItemStack[] { new ItemStack(Material.DEEPSLATE_DIAMOND_ORE, 16)});
        this.registerRecipe(0, new ItemStack[] { new ItemStack(Material.NETHER_GOLD_ORE, 4), new ItemStack(Material.GOLD_NUGGET, 16), new ItemStack(Material.NETHERRACK, 64)}, new ItemStack[] { new ItemStack(Material.NETHER_GOLD_ORE, 16)});
        this.registerRecipe(0, new ItemStack[] { new ItemStack(Material.NETHER_QUARTZ_ORE, 4), new ItemStack(Material.QUARTZ, 16), new ItemStack(Material.NETHERRACK, 64)}, new ItemStack[] { new ItemStack(Material.NETHER_QUARTZ_ORE, 16)});
        this.registerRecipe(0, new ItemStack[] { new ItemStack(Material.ANCIENT_DEBRIS, 4), new ItemStack(Material.NETHERITE_SCRAP, 16), new ItemStack(Material.NETHERRACK, 64)}, new ItemStack[] { new ItemStack(Material.ANCIENT_DEBRIS, 16)});
        this.registerRecipe(0, new ItemStack[] {new ItemStack(Material.AMETHYST_BLOCK, 4), new ItemStack(Material.AMETHYST_SHARD, 16), new ItemStack(Material.CALCITE, 64)}, new ItemStack[] { new ItemStack(Material.AMETHYST_BLOCK, 16)});
    }
}
