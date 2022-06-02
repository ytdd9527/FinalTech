package io.taraxacum.finaltech.core.items.machine.standard.advanced;

import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.implementation.SlimefunItems;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

/**
 * @author Final_ROOT
 * @since 1.0
 */
public class AdvancedOreFactory extends AbstractAdvanceMachine {
    public AdvancedOreFactory(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
        super(itemGroup, item, recipeType, recipe);
    }

    @Override
    public void registerDefaultRecipes() {
        this.registerRecipe(0, new ItemStack[] {
                new ItemStack(Material.COAL_ORE), SlimefunItems.STONE_CHUNK, new ItemStack(Material.COAL, 3)
        }, new ItemStack[] {
                new ItemStack(Material.COAL_ORE, 2)
        });
        this.registerRecipe(0, new ItemStack[] {
                new ItemStack(Material.COAL_ORE), SlimefunItems.STONE_CHUNK
        }, new ItemStack[] {
                new ItemStack(Material.COAL, 4)
        });
        this.registerRecipe(0, new ItemStack[] {
                new ItemStack(Material.COAL_ORE)
        }, new ItemStack[] {
                new ItemStack(Material.COAL, 3), SlimefunItems.STONE_CHUNK
        });

        this.registerRecipe(0, new ItemStack[] {
                new ItemStack(Material.DEEPSLATE_COAL_ORE), SlimefunItems.STONE_CHUNK, new ItemStack(Material.COAL, 3)
        }, new ItemStack[] {
                new ItemStack(Material.DEEPSLATE_COAL_ORE, 2)
        });
        this.registerRecipe(0, new ItemStack[] {
                new ItemStack(Material.DEEPSLATE_COAL_ORE), SlimefunItems.STONE_CHUNK
        }, new ItemStack[] {
                new ItemStack(Material.COAL, 4)
        });
        this.registerRecipe(0, new ItemStack[] {
                new ItemStack(Material.DEEPSLATE_COAL_ORE)
        }, new ItemStack[] {
                new ItemStack(Material.COAL, 3), SlimefunItems.STONE_CHUNK
        });

        this.registerRecipe(0, new ItemStack[] {
                new ItemStack(Material.IRON_ORE), SlimefunItems.STONE_CHUNK, new ItemStack(Material.RAW_IRON, 3)
        }, new ItemStack[] {
                new ItemStack(Material.IRON_ORE, 2)
        });
        this.registerRecipe(0, new ItemStack[] {
                new ItemStack(Material.IRON_ORE), SlimefunItems.STONE_CHUNK
        }, new ItemStack[] {
                new ItemStack(Material.RAW_IRON, 4)
        });
        this.registerRecipe(0, new ItemStack[] {
                new ItemStack(Material.IRON_ORE)
        }, new ItemStack[] {
                new ItemStack(Material.RAW_IRON, 3), SlimefunItems.STONE_CHUNK
        });

        this.registerRecipe(0, new ItemStack[] {
                new ItemStack(Material.DEEPSLATE_IRON_ORE), SlimefunItems.STONE_CHUNK, new ItemStack(Material.RAW_IRON, 3)
        }, new ItemStack[] {
                new ItemStack(Material.DEEPSLATE_IRON_ORE, 2)
        });
        this.registerRecipe(0, new ItemStack[] {
                new ItemStack(Material.DEEPSLATE_IRON_ORE), SlimefunItems.STONE_CHUNK
        }, new ItemStack[] {
                new ItemStack(Material.RAW_IRON, 4)
        });
        this.registerRecipe(0, new ItemStack[] {
                new ItemStack(Material.DEEPSLATE_IRON_ORE)
        }, new ItemStack[] {
                new ItemStack(Material.RAW_IRON, 3), SlimefunItems.STONE_CHUNK
        });

        this.registerRecipe(0, new ItemStack[] {
                new ItemStack(Material.GOLD_ORE), SlimefunItems.STONE_CHUNK, new ItemStack(Material.RAW_GOLD, 3)
        }, new ItemStack[] {
                new ItemStack(Material.GOLD_ORE, 2)
        });
        this.registerRecipe(0, new ItemStack[] {
                new ItemStack(Material.GOLD_ORE), SlimefunItems.STONE_CHUNK
        }, new ItemStack[] {
                new ItemStack(Material.RAW_GOLD, 4)
        });
        this.registerRecipe(0, new ItemStack[] {
                new ItemStack(Material.GOLD_ORE)
        }, new ItemStack[] {
                new ItemStack(Material.RAW_GOLD, 3), SlimefunItems.STONE_CHUNK
        });

        this.registerRecipe(0, new ItemStack[] {
                new ItemStack(Material.DEEPSLATE_GOLD_ORE), SlimefunItems.STONE_CHUNK, new ItemStack(Material.RAW_GOLD, 3)
        }, new ItemStack[] {
                new ItemStack(Material.DEEPSLATE_GOLD_ORE, 2)
        });
        this.registerRecipe(0, new ItemStack[] {
                new ItemStack(Material.DEEPSLATE_GOLD_ORE), SlimefunItems.STONE_CHUNK
        }, new ItemStack[] {
                new ItemStack(Material.RAW_GOLD, 4)
        });
        this.registerRecipe(0, new ItemStack[] {
                new ItemStack(Material.DEEPSLATE_GOLD_ORE)
        }, new ItemStack[] {
                new ItemStack(Material.RAW_GOLD, 3), SlimefunItems.STONE_CHUNK
        });

        this.registerRecipe(0, new ItemStack[] {
                new ItemStack(Material.COPPER_ORE), SlimefunItems.STONE_CHUNK, new ItemStack(Material.RAW_COPPER, 3)
        }, new ItemStack[] {
                new ItemStack(Material.COPPER_ORE, 2)
        });
        this.registerRecipe(0, new ItemStack[] {
                new ItemStack(Material.COPPER_ORE), SlimefunItems.STONE_CHUNK
        }, new ItemStack[] {
                new ItemStack(Material.RAW_COPPER, 4)
        });
        this.registerRecipe(0, new ItemStack[] {
                new ItemStack(Material.COPPER_ORE)
        }, new ItemStack[] {
                new ItemStack(Material.RAW_COPPER, 3), SlimefunItems.STONE_CHUNK
        });

        this.registerRecipe(0, new ItemStack[] {
                new ItemStack(Material.DEEPSLATE_COPPER_ORE), SlimefunItems.STONE_CHUNK, new ItemStack(Material.RAW_COPPER, 3)
        }, new ItemStack[] {
                new ItemStack(Material.DEEPSLATE_COPPER_ORE, 2)
        });
        this.registerRecipe(0, new ItemStack[] {
                new ItemStack(Material.DEEPSLATE_COPPER_ORE), SlimefunItems.STONE_CHUNK
        }, new ItemStack[] {
                new ItemStack(Material.RAW_COPPER, 4)
        });
        this.registerRecipe(0, new ItemStack[] {
                new ItemStack(Material.DEEPSLATE_COPPER_ORE)
        }, new ItemStack[] {
                new ItemStack(Material.RAW_COPPER, 3), SlimefunItems.STONE_CHUNK
        });

        this.registerRecipe(0, new ItemStack[] {
                new ItemStack(Material.REDSTONE_ORE), SlimefunItems.STONE_CHUNK, new ItemStack(Material.REDSTONE, 3)
        }, new ItemStack[] {
                new ItemStack(Material.REDSTONE_ORE, 2)
        });
        this.registerRecipe(0, new ItemStack[] {
                new ItemStack(Material.REDSTONE_ORE), SlimefunItems.STONE_CHUNK
        }, new ItemStack[] {
                new ItemStack(Material.REDSTONE, 4)
        });
        this.registerRecipe(0, new ItemStack[] {
                new ItemStack(Material.REDSTONE_ORE)
        }, new ItemStack[] {
                new ItemStack(Material.REDSTONE, 3), SlimefunItems.STONE_CHUNK
        });

        this.registerRecipe(0, new ItemStack[] {
                new ItemStack(Material.DEEPSLATE_REDSTONE_ORE), SlimefunItems.STONE_CHUNK, new ItemStack(Material.REDSTONE, 3)
        }, new ItemStack[] {
                new ItemStack(Material.DEEPSLATE_REDSTONE_ORE, 2)
        });
        this.registerRecipe(0, new ItemStack[] {
                new ItemStack(Material.DEEPSLATE_REDSTONE_ORE), SlimefunItems.STONE_CHUNK
        }, new ItemStack[] {
                new ItemStack(Material.REDSTONE, 4)
        });
        this.registerRecipe(0, new ItemStack[] {
                new ItemStack(Material.DEEPSLATE_REDSTONE_ORE)
        }, new ItemStack[] {
                new ItemStack(Material.REDSTONE, 3), SlimefunItems.STONE_CHUNK
        });

        this.registerRecipe(0, new ItemStack[] {
                new ItemStack(Material.LAPIS_ORE), SlimefunItems.STONE_CHUNK, new ItemStack(Material.LAPIS_LAZULI, 3)
        }, new ItemStack[] {
                new ItemStack(Material.LAPIS_ORE, 2)
        });
        this.registerRecipe(0, new ItemStack[] {
                new ItemStack(Material.LAPIS_ORE), SlimefunItems.STONE_CHUNK
        }, new ItemStack[] {
                new ItemStack(Material.LAPIS_LAZULI, 4)
        });
        this.registerRecipe(0, new ItemStack[] {
                new ItemStack(Material.LAPIS_ORE)
        }, new ItemStack[] {
                new ItemStack(Material.LAPIS_LAZULI, 3), SlimefunItems.STONE_CHUNK
        });

        this.registerRecipe(0, new ItemStack[] {
                new ItemStack(Material.DEEPSLATE_LAPIS_ORE), SlimefunItems.STONE_CHUNK, new ItemStack(Material.LAPIS_LAZULI, 3)
        }, new ItemStack[] {
                new ItemStack(Material.DEEPSLATE_LAPIS_ORE, 2)
        });
        this.registerRecipe(0, new ItemStack[] {
                new ItemStack(Material.DEEPSLATE_LAPIS_ORE), SlimefunItems.STONE_CHUNK
        }, new ItemStack[] {
                new ItemStack(Material.LAPIS_LAZULI, 4)
        });
        this.registerRecipe(0, new ItemStack[] {
                new ItemStack(Material.DEEPSLATE_LAPIS_ORE)
        }, new ItemStack[] {
                new ItemStack(Material.LAPIS_LAZULI, 3), SlimefunItems.STONE_CHUNK
        });

        this.registerRecipe(0, new ItemStack[] {
                new ItemStack(Material.DIAMOND_ORE), SlimefunItems.STONE_CHUNK, new ItemStack(Material.DIAMOND, 3)
        }, new ItemStack[] {
                new ItemStack(Material.DIAMOND_ORE, 2)
        });
        this.registerRecipe(0, new ItemStack[] {
                new ItemStack(Material.DIAMOND_ORE), SlimefunItems.STONE_CHUNK
        }, new ItemStack[] {
                new ItemStack(Material.DIAMOND, 4)
        });
        this.registerRecipe(0, new ItemStack[] {
                new ItemStack(Material.DIAMOND_ORE)
        }, new ItemStack[] {
                new ItemStack(Material.DIAMOND, 3), SlimefunItems.STONE_CHUNK
        });

        this.registerRecipe(0, new ItemStack[] {
                new ItemStack(Material.DEEPSLATE_DIAMOND_ORE), SlimefunItems.STONE_CHUNK, new ItemStack(Material.DIAMOND, 3)
        }, new ItemStack[] {
                new ItemStack(Material.DEEPSLATE_DIAMOND_ORE, 2)
        });
        this.registerRecipe(0, new ItemStack[] {
                new ItemStack(Material.DEEPSLATE_DIAMOND_ORE), SlimefunItems.STONE_CHUNK
        }, new ItemStack[] {
                new ItemStack(Material.DIAMOND, 4)
        });
        this.registerRecipe(0, new ItemStack[] {
                new ItemStack(Material.DEEPSLATE_DIAMOND_ORE)
        }, new ItemStack[] {
                new ItemStack(Material.DIAMOND, 3), SlimefunItems.STONE_CHUNK
        });

        this.registerRecipe(0, new ItemStack[] {
                new ItemStack(Material.EMERALD_ORE), SlimefunItems.STONE_CHUNK, new ItemStack(Material.EMERALD, 3)
        }, new ItemStack[] {
                new ItemStack(Material.EMERALD_ORE, 2)
        });
        this.registerRecipe(0, new ItemStack[] {
                new ItemStack(Material.EMERALD_ORE), SlimefunItems.STONE_CHUNK
        }, new ItemStack[] {
                new ItemStack(Material.EMERALD, 4)
        });
        this.registerRecipe(0, new ItemStack[] {
                new ItemStack(Material.EMERALD_ORE)
        }, new ItemStack[] {
                new ItemStack(Material.EMERALD, 3), SlimefunItems.STONE_CHUNK
        });

        this.registerRecipe(0, new ItemStack[] {
                new ItemStack(Material.DEEPSLATE_EMERALD_ORE), SlimefunItems.STONE_CHUNK, new ItemStack(Material.EMERALD, 3)
        }, new ItemStack[] {
                new ItemStack(Material.DEEPSLATE_EMERALD_ORE, 2)
        });
        this.registerRecipe(0, new ItemStack[] {
                new ItemStack(Material.DEEPSLATE_EMERALD_ORE), SlimefunItems.STONE_CHUNK
        }, new ItemStack[] {
                new ItemStack(Material.EMERALD, 4)
        });
        this.registerRecipe(0, new ItemStack[] {
                new ItemStack(Material.DEEPSLATE_EMERALD_ORE)
        }, new ItemStack[] {
                new ItemStack(Material.EMERALD, 3), SlimefunItems.STONE_CHUNK
        });

        this.registerRecipe(0, new ItemStack[] {
                new ItemStack(Material.NETHER_GOLD_ORE), SlimefunItems.STONE_CHUNK, new ItemStack(Material.GOLD_NUGGET, 3)
        }, new ItemStack[] {
                new ItemStack(Material.NETHER_GOLD_ORE, 2)
        });
        this.registerRecipe(0, new ItemStack[] {
                new ItemStack(Material.NETHER_GOLD_ORE), SlimefunItems.STONE_CHUNK
        }, new ItemStack[] {
                new ItemStack(Material.GOLD_NUGGET, 4)
        });
        this.registerRecipe(0, new ItemStack[] {
                new ItemStack(Material.NETHER_GOLD_ORE)
        }, new ItemStack[] {
                new ItemStack(Material.GOLD_NUGGET, 3), SlimefunItems.STONE_CHUNK
        });

        this.registerRecipe(0, new ItemStack[] {
                new ItemStack(Material.NETHER_QUARTZ_ORE), SlimefunItems.STONE_CHUNK, new ItemStack(Material.QUARTZ, 3)
        }, new ItemStack[] {
                new ItemStack(Material.NETHER_QUARTZ_ORE, 2)
        });
        this.registerRecipe(0, new ItemStack[] {
                new ItemStack(Material.NETHER_QUARTZ_ORE), SlimefunItems.STONE_CHUNK
        }, new ItemStack[] {
                new ItemStack(Material.QUARTZ, 4)
        });
        this.registerRecipe(0, new ItemStack[] {
                new ItemStack(Material.NETHER_QUARTZ_ORE)
        }, new ItemStack[] {
                new ItemStack(Material.QUARTZ, 3), SlimefunItems.STONE_CHUNK
        });

        this.registerRecipe(0, new ItemStack[] {
                new ItemStack(Material.ANCIENT_DEBRIS), SlimefunItems.STONE_CHUNK, new ItemStack(Material.NETHERITE_SCRAP, 3)
        }, new ItemStack[] {
                new ItemStack(Material.ANCIENT_DEBRIS, 2)
        });
        this.registerRecipe(0, new ItemStack[] {
                new ItemStack(Material.ANCIENT_DEBRIS), SlimefunItems.STONE_CHUNK
        }, new ItemStack[] {
                new ItemStack(Material.NETHERITE_SCRAP, 4)
        });
        this.registerRecipe(0, new ItemStack[] {
                new ItemStack(Material.ANCIENT_DEBRIS)
        }, new ItemStack[] {
                new ItemStack(Material.NETHERITE_SCRAP, 3), SlimefunItems.STONE_CHUNK
        });

        this.registerRecipe(0, new ItemStack[] {
                new ItemStack(Material.AMETHYST_BLOCK), SlimefunItems.STONE_CHUNK, new ItemStack(Material.AMETHYST_SHARD, 3)
        }, new ItemStack[] {
                new ItemStack(Material.AMETHYST_BLOCK, 2)
        });
        this.registerRecipe(0, new ItemStack[] {
                new ItemStack(Material.AMETHYST_BLOCK), SlimefunItems.STONE_CHUNK
        }, new ItemStack[] {
                new ItemStack(Material.AMETHYST_SHARD, 4)
        });
        this.registerRecipe(0, new ItemStack[] {
                new ItemStack(Material.AMETHYST_BLOCK)
        }, new ItemStack[] {
                new ItemStack(Material.AMETHYST_SHARD, 3), SlimefunItems.STONE_CHUNK
        });
    }
}
