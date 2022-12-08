package io.taraxacum.finaltech.core.item.machine.template.advanced;

import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.implementation.SlimefunItems;
import io.github.thebusybiscuit.slimefun4.libraries.dough.items.CustomItemStack;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

/**
 * @author Final_ROOT
 * @since 1.0
 */
public class AdvancedDustFactory extends AbstractAdvanceMachine {
    public AdvancedDustFactory(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
        super(itemGroup, item, recipeType, recipe);
    }

    @Override
    public void registerDefaultRecipes() {
        this.registerRecipe(new ItemStack(Material.COBBLESTONE), new ItemStack(SlimefunItems.SIFTED_ORE));
        this.registerRecipe(new ItemStack[] {
                new ItemStack(SlimefunItems.SIFTED_ORE),
                new ItemStack(SlimefunItems.IRON_DUST)
        }, new ItemStack[] {
                new CustomItemStack(SlimefunItems.IRON_DUST, 2)
        });
        this.registerRecipe(new ItemStack[] {
                new ItemStack(SlimefunItems.SIFTED_ORE),
                new ItemStack(SlimefunItems.COPPER_DUST)
        }, new ItemStack[] {
                new CustomItemStack(SlimefunItems.COPPER_DUST, 2)
        });
        this.registerRecipe(new ItemStack[] {
                new ItemStack(SlimefunItems.SIFTED_ORE),
                new ItemStack(SlimefunItems.LEAD_DUST)
        }, new ItemStack[] {
                new CustomItemStack(SlimefunItems.LEAD_DUST, 2)
        });
        this.registerRecipe(new ItemStack[] {
                new ItemStack(SlimefunItems.SIFTED_ORE),
                new ItemStack(SlimefunItems.TIN_DUST)
        }, new ItemStack[] {
                new CustomItemStack(SlimefunItems.TIN_DUST, 2)
        });
        this.registerRecipe(new ItemStack[] {
                new ItemStack(SlimefunItems.SIFTED_ORE),
                new ItemStack(SlimefunItems.SILVER_DUST)
        }, new ItemStack[] {
                new CustomItemStack(SlimefunItems.SILVER_DUST, 2)
        });
        this.registerRecipe(new ItemStack[] {
                new ItemStack(SlimefunItems.SIFTED_ORE),
                new ItemStack(SlimefunItems.ALUMINUM_DUST)
        }, new ItemStack[] {
                new CustomItemStack(SlimefunItems.ALUMINUM_DUST, 2)
        });
        this.registerRecipe(new ItemStack[] {
                new ItemStack(SlimefunItems.SIFTED_ORE),
                new ItemStack(SlimefunItems.ZINC_DUST)
        }, new ItemStack[] {
                new CustomItemStack(SlimefunItems.ZINC_DUST, 2)
        });
        this.registerRecipe(new ItemStack[] {
                new ItemStack(SlimefunItems.SIFTED_ORE),
                new ItemStack(SlimefunItems.MAGNESIUM_DUST)
        }, new ItemStack[] {
                new CustomItemStack(SlimefunItems.MAGNESIUM_DUST, 2)
        });
        this.registerRecipe(new ItemStack[] {
                new ItemStack(SlimefunItems.SIFTED_ORE),
                new ItemStack(SlimefunItems.GOLD_DUST)
        }, new ItemStack[] {
                new CustomItemStack(SlimefunItems.GOLD_DUST, 2)
        });
    }
}
