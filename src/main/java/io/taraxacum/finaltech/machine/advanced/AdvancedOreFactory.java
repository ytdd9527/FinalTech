package io.taraxacum.finaltech.machine.advanced;

import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.implementation.SlimefunItems;
import io.taraxacum.finaltech.abstractItem.machine.AbstractAdvanceMachine;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

/**
 * @author Final_ROOT
 */
public class AdvancedOreFactory extends AbstractAdvanceMachine {
    protected AdvancedOreFactory(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
        super(itemGroup, item, recipeType, recipe);
    }

    @Override
    public void registerDefaultRecipes() {
        this.registerRecipe(0, new ItemStack[] {
                new ItemStack(Material.COAL_ORE)
        }, new ItemStack[] {
                new ItemStack(Material.COAL, 3), SlimefunItems.STONE_CHUNK
        });
        this.registerRecipe(0, new ItemStack[] {
                new ItemStack(Material.COAL_ORE), SlimefunItems.STONE_CHUNK
        }, new ItemStack[] {
                new ItemStack(Material.COAL, 4)
        });
        this.registerRecipe(0, new ItemStack[] {
                new ItemStack(Material.COAL_ORE), SlimefunItems.STONE_CHUNK, new ItemStack(Material.COAL, 3)
        }, new ItemStack[] {
                new ItemStack(Material.COAL_ORE, 2)
        });
        this.registerRecipe(0, new ItemStack[] {
                new ItemStack(Material.DEEPSLATE_COAL_ORE)
        }, new ItemStack[] {
                new ItemStack(Material.COAL, 3), SlimefunItems.STONE_CHUNK
        });
        this.registerRecipe(0, new ItemStack[] {
                new ItemStack(Material.DEEPSLATE_COAL_ORE), SlimefunItems.STONE_CHUNK
        }, new ItemStack[] {
                new ItemStack(Material.COAL, 4)
        });
        this.registerRecipe(0, new ItemStack[] {
                new ItemStack(Material.DEEPSLATE_COAL_ORE), SlimefunItems.STONE_CHUNK, new ItemStack(Material.COAL, 3)
        }, new ItemStack[] {
                new ItemStack(Material.DEEPSLATE_COAL_ORE, 2)
        });
        this.registerRecipe(0, new ItemStack[] {
                new ItemStack(Material.IRON_ORE)
        }, new ItemStack[] {
                new ItemStack(Material.RAW_IRON, 3), SlimefunItems.STONE_CHUNK
        });
        this.registerRecipe(0, new ItemStack[] {
                new ItemStack(Material.IRON_ORE), SlimefunItems.STONE_CHUNK
        }, new ItemStack[] {
                new ItemStack(Material.RAW_IRON, 4)
        });
        this.registerRecipe(0, new ItemStack[] {
                new ItemStack(Material.IRON_ORE), SlimefunItems.STONE_CHUNK, new ItemStack(Material.RAW_IRON, 3)
        }, new ItemStack[] {
                new ItemStack(Material.IRON_ORE, 2)
        });
        this.registerRecipe(0, new ItemStack[] {
                new ItemStack(Material.DEEPSLATE_IRON_ORE)
        }, new ItemStack[] {
                new ItemStack(Material.RAW_IRON, 3), SlimefunItems.STONE_CHUNK
        });
        this.registerRecipe(0, new ItemStack[] {
                new ItemStack(Material.DEEPSLATE_IRON_ORE), SlimefunItems.STONE_CHUNK
        }, new ItemStack[] {
                new ItemStack(Material.RAW_IRON, 4)
        });
        this.registerRecipe(0, new ItemStack[] {
                new ItemStack(Material.DEEPSLATE_IRON_ORE), SlimefunItems.STONE_CHUNK, new ItemStack(Material.RAW_IRON, 3)
        }, new ItemStack[] {
                new ItemStack(Material.DEEPSLATE_IRON_ORE, 2)
        });
        this.registerRecipe(0, new ItemStack[] {
                new ItemStack(Material.GOLD_ORE)
        }, new ItemStack[] {
                new ItemStack(Material.RAW_GOLD, 3), SlimefunItems.STONE_CHUNK
        });
        this.registerRecipe(0, new ItemStack[] {
                new ItemStack(Material.GOLD_ORE), SlimefunItems.STONE_CHUNK
        }, new ItemStack[] {
                new ItemStack(Material.RAW_GOLD, 4)
        });
        this.registerRecipe(0, new ItemStack[] {
                new ItemStack(Material.GOLD_ORE), SlimefunItems.STONE_CHUNK, new ItemStack(Material.RAW_GOLD, 3)
        }, new ItemStack[] {
                new ItemStack(Material.GOLD_ORE, 2)
        });
        this.registerRecipe(0, new ItemStack[] {
                new ItemStack(Material.DEEPSLATE_GOLD_ORE)
        }, new ItemStack[] {
                new ItemStack(Material.RAW_GOLD, 3), SlimefunItems.STONE_CHUNK
        });
        this.registerRecipe(0, new ItemStack[] {
                new ItemStack(Material.DEEPSLATE_GOLD_ORE), SlimefunItems.STONE_CHUNK
        }, new ItemStack[] {
                new ItemStack(Material.RAW_GOLD, 4)
        });
        this.registerRecipe(0, new ItemStack[] {
                new ItemStack(Material.DEEPSLATE_GOLD_ORE), SlimefunItems.STONE_CHUNK, new ItemStack(Material.RAW_GOLD, 3)
        }, new ItemStack[] {
                new ItemStack(Material.DEEPSLATE_GOLD_ORE, 2)
        });
        this.registerRecipe(0, new ItemStack[] {
                new ItemStack(Material.COPPER_ORE)
        }, new ItemStack[] {
                new ItemStack(Material.RAW_COPPER, 3), SlimefunItems.STONE_CHUNK
        });
        this.registerRecipe(0, new ItemStack[] {
                new ItemStack(Material.COPPER_ORE), SlimefunItems.STONE_CHUNK
        }, new ItemStack[] {
                new ItemStack(Material.RAW_COPPER, 4)
        });
        this.registerRecipe(0, new ItemStack[] {
                new ItemStack(Material.COPPER_ORE), SlimefunItems.STONE_CHUNK, new ItemStack(Material.RAW_COPPER, 3)
        }, new ItemStack[] {
                new ItemStack(Material.COPPER_ORE, 2)
        });
        this.registerRecipe(0, new ItemStack[] {
                new ItemStack(Material.DEEPSLATE_COPPER_ORE)
        }, new ItemStack[] {
                new ItemStack(Material.RAW_COPPER, 3), SlimefunItems.STONE_CHUNK
        });
        this.registerRecipe(0, new ItemStack[] {
                new ItemStack(Material.DEEPSLATE_COPPER_ORE), SlimefunItems.STONE_CHUNK
        }, new ItemStack[] {
                new ItemStack(Material.RAW_COPPER, 4)
        });
        this.registerRecipe(0, new ItemStack[] {
                new ItemStack(Material.DEEPSLATE_COPPER_ORE), SlimefunItems.STONE_CHUNK, new ItemStack(Material.RAW_COPPER, 3)
        }, new ItemStack[] {
                new ItemStack(Material.DEEPSLATE_COPPER_ORE, 2)
        });
        this.registerRecipe(0, new ItemStack[] {
                new ItemStack(Material.COAL_ORE)
        }, new ItemStack[] {
                new ItemStack(Material.COAL, 3), SlimefunItems.STONE_CHUNK
        });
        this.registerRecipe(0, new ItemStack[] {
                new ItemStack(Material.COAL_ORE), SlimefunItems.STONE_CHUNK
        }, new ItemStack[] {
                new ItemStack(Material.COAL, 4)
        });
        this.registerRecipe(0, new ItemStack[] {
                new ItemStack(Material.COAL_ORE), SlimefunItems.STONE_CHUNK, new ItemStack(Material.COAL, 3)
        }, new ItemStack[] {
                new ItemStack(Material.COAL_ORE, 2)
        });
        this.registerRecipe(0, new ItemStack[] {
                new ItemStack(Material.COAL_ORE)
        }, new ItemStack[] {
                new ItemStack(Material.COAL, 3), SlimefunItems.STONE_CHUNK
        });
        this.registerRecipe(0, new ItemStack[] {
                new ItemStack(Material.COAL_ORE), SlimefunItems.STONE_CHUNK
        }, new ItemStack[] {
                new ItemStack(Material.COAL, 4)
        });
        this.registerRecipe(0, new ItemStack[] {
                new ItemStack(Material.COAL_ORE), SlimefunItems.STONE_CHUNK, new ItemStack(Material.COAL, 3)
        }, new ItemStack[] {
                new ItemStack(Material.COAL_ORE, 2)
        });
        this.registerRecipe(0, new ItemStack[] {
                new ItemStack(Material.COAL_ORE)
        }, new ItemStack[] {
                new ItemStack(Material.COAL, 3), SlimefunItems.STONE_CHUNK
        });
        this.registerRecipe(0, new ItemStack[] {
                new ItemStack(Material.COAL_ORE), SlimefunItems.STONE_CHUNK
        }, new ItemStack[] {
                new ItemStack(Material.COAL, 4)
        });
        this.registerRecipe(0, new ItemStack[] {
                new ItemStack(Material.COAL_ORE), SlimefunItems.STONE_CHUNK, new ItemStack(Material.COAL, 3)
        }, new ItemStack[] {
                new ItemStack(Material.COAL_ORE, 2)
        });
        this.registerRecipe(0, new ItemStack[] {
                new ItemStack(Material.COAL_ORE)
        }, new ItemStack[] {
                new ItemStack(Material.COAL, 3), SlimefunItems.STONE_CHUNK
        });
        this.registerRecipe(0, new ItemStack[] {
                new ItemStack(Material.COAL_ORE), SlimefunItems.STONE_CHUNK
        }, new ItemStack[] {
                new ItemStack(Material.COAL, 4)
        });
        this.registerRecipe(0, new ItemStack[] {
                new ItemStack(Material.COAL_ORE), SlimefunItems.STONE_CHUNK, new ItemStack(Material.COAL, 3)
        }, new ItemStack[] {
                new ItemStack(Material.COAL_ORE, 2)
        });
        this.registerRecipe(0, new ItemStack[] {
                new ItemStack(Material.COAL_ORE)
        }, new ItemStack[] {
                new ItemStack(Material.COAL, 3), SlimefunItems.STONE_CHUNK
        });
        this.registerRecipe(0, new ItemStack[] {
                new ItemStack(Material.COAL_ORE), SlimefunItems.STONE_CHUNK
        }, new ItemStack[] {
                new ItemStack(Material.COAL, 4)
        });
        this.registerRecipe(0, new ItemStack[] {
                new ItemStack(Material.COAL_ORE), SlimefunItems.STONE_CHUNK, new ItemStack(Material.COAL, 3)
        }, new ItemStack[] {
                new ItemStack(Material.COAL_ORE, 2)
        });
        this.registerRecipe(0, new ItemStack[] {
                new ItemStack(Material.COAL_ORE)
        }, new ItemStack[] {
                new ItemStack(Material.COAL, 3), SlimefunItems.STONE_CHUNK
        });
        this.registerRecipe(0, new ItemStack[] {
                new ItemStack(Material.COAL_ORE), SlimefunItems.STONE_CHUNK
        }, new ItemStack[] {
                new ItemStack(Material.COAL, 4)
        });
        this.registerRecipe(0, new ItemStack[] {
                new ItemStack(Material.COAL_ORE), SlimefunItems.STONE_CHUNK, new ItemStack(Material.COAL, 3)
        }, new ItemStack[] {
                new ItemStack(Material.COAL_ORE, 2)
        });
        this.registerRecipe(0, new ItemStack[] {
                new ItemStack(Material.COAL_ORE)
        }, new ItemStack[] {
                new ItemStack(Material.COAL, 3), SlimefunItems.STONE_CHUNK
        });
        this.registerRecipe(0, new ItemStack[] {
                new ItemStack(Material.COAL_ORE), SlimefunItems.STONE_CHUNK
        }, new ItemStack[] {
                new ItemStack(Material.COAL, 4)
        });
        this.registerRecipe(0, new ItemStack[] {
                new ItemStack(Material.COAL_ORE), SlimefunItems.STONE_CHUNK, new ItemStack(Material.COAL, 3)
        }, new ItemStack[] {
                new ItemStack(Material.COAL_ORE, 2)
        });
        this.registerRecipe(0, new ItemStack[] {
                new ItemStack(Material.COAL_ORE)
        }, new ItemStack[] {
                new ItemStack(Material.COAL, 3), SlimefunItems.STONE_CHUNK
        });
        this.registerRecipe(0, new ItemStack[] {
                new ItemStack(Material.COAL_ORE), SlimefunItems.STONE_CHUNK
        }, new ItemStack[] {
                new ItemStack(Material.COAL, 4)
        });
        this.registerRecipe(0, new ItemStack[] {
                new ItemStack(Material.COAL_ORE), SlimefunItems.STONE_CHUNK, new ItemStack(Material.COAL, 3)
        }, new ItemStack[] {
                new ItemStack(Material.COAL_ORE, 2)
        });
        this.registerRecipe(0, new ItemStack[] {
                new ItemStack(Material.COAL_ORE)
        }, new ItemStack[] {
                new ItemStack(Material.COAL, 3), SlimefunItems.STONE_CHUNK
        });
        this.registerRecipe(0, new ItemStack[] {
                new ItemStack(Material.COAL_ORE), SlimefunItems.STONE_CHUNK
        }, new ItemStack[] {
                new ItemStack(Material.COAL, 4)
        });
        this.registerRecipe(0, new ItemStack[] {
                new ItemStack(Material.COAL_ORE), SlimefunItems.STONE_CHUNK, new ItemStack(Material.COAL, 3)
        }, new ItemStack[] {
                new ItemStack(Material.COAL_ORE, 2)
        });
        this.registerRecipe(0, new ItemStack[] {
                new ItemStack(Material.COAL_ORE)
        }, new ItemStack[] {
                new ItemStack(Material.COAL, 3), SlimefunItems.STONE_CHUNK
        });
        this.registerRecipe(0, new ItemStack[] {
                new ItemStack(Material.COAL_ORE), SlimefunItems.STONE_CHUNK
        }, new ItemStack[] {
                new ItemStack(Material.COAL, 4)
        });
        this.registerRecipe(0, new ItemStack[] {
                new ItemStack(Material.COAL_ORE), SlimefunItems.STONE_CHUNK, new ItemStack(Material.COAL, 3)
        }, new ItemStack[] {
                new ItemStack(Material.COAL_ORE, 2)
        });
        this.registerRecipe(0, new ItemStack[] {
                new ItemStack(Material.COAL_ORE)
        }, new ItemStack[] {
                new ItemStack(Material.COAL, 3), SlimefunItems.STONE_CHUNK
        });
        this.registerRecipe(0, new ItemStack[] {
                new ItemStack(Material.COAL_ORE), SlimefunItems.STONE_CHUNK
        }, new ItemStack[] {
                new ItemStack(Material.COAL, 4)
        });
        this.registerRecipe(0, new ItemStack[] {
                new ItemStack(Material.COAL_ORE), SlimefunItems.STONE_CHUNK, new ItemStack(Material.COAL, 3)
        }, new ItemStack[] {
                new ItemStack(Material.COAL_ORE, 2)
        });
        this.registerRecipe(0, new ItemStack[] {
                new ItemStack(Material.COAL_ORE)
        }, new ItemStack[] {
                new ItemStack(Material.COAL, 3), SlimefunItems.STONE_CHUNK
        });
        this.registerRecipe(0, new ItemStack[] {
                new ItemStack(Material.COAL_ORE), SlimefunItems.STONE_CHUNK
        }, new ItemStack[] {
                new ItemStack(Material.COAL, 4)
        });
        this.registerRecipe(0, new ItemStack[] {
                new ItemStack(Material.COAL_ORE), SlimefunItems.STONE_CHUNK, new ItemStack(Material.COAL, 3)
        }, new ItemStack[] {
                new ItemStack(Material.COAL_ORE, 2)
        });
    }
}
