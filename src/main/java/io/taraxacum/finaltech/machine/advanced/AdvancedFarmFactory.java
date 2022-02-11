package io.taraxacum.finaltech.machine.advanced;

import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.taraxacum.finaltech.abstractItem.machine.AbstractAdvanceMachine;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class AdvancedFarmFactory extends AbstractAdvanceMachine {
    public AdvancedFarmFactory(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
        super(itemGroup, item, recipeType, recipe);
    }

    @Override
    public void registerDefaultRecipes() {
        registerRecipe(0, new ItemStack[] {
                new ItemStack(Material.OAK_SAPLING), new ItemStack(Material.BONE_MEAL), new ItemStack(Material.DIRT)
        }, new ItemStack[] {
                new ItemStack(Material.OAK_LOG, 2), new ItemStack(Material.OAK_LEAVES, 2)
        });
        registerRecipe(0, new ItemStack[] {
                new ItemStack(Material.OAK_LOG), new ItemStack(Material.BONE_MEAL)
        }, new ItemStack[] {
                new ItemStack(Material.APPLE), new ItemStack(Material.STICK)
        });
        registerRecipe(0, new ItemStack[] {
                new ItemStack(Material.OAK_LEAVES), new ItemStack(Material.APPLE)
        }, new ItemStack[] {
                new ItemStack(Material.OAK_SAPLING)
        });
        registerRecipe(0, new ItemStack[] {
                new ItemStack(Material.OAK_LEAVES), new ItemStack(Material.DIRT)
        }, new ItemStack[] {
                new ItemStack(Material.BONE_MEAL)
        });
        registerRecipe(0, new ItemStack[] {
                new ItemStack(Material.SPRUCE_SAPLING), new ItemStack(Material.BONE_MEAL), new ItemStack(Material.DIRT)
        }, new ItemStack[] {
                new ItemStack(Material.SPRUCE_LOG, 2), new ItemStack(Material.SPRUCE_LEAVES, 2)
        });
        registerRecipe(0, new ItemStack[] {
                new ItemStack(Material.SPRUCE_LOG), new ItemStack(Material.BONE_MEAL)
        }, new ItemStack[] {
                new ItemStack(Material.APPLE), new ItemStack(Material.STICK)
        });
        registerRecipe(0, new ItemStack[] {
                new ItemStack(Material.SPRUCE_LEAVES), new ItemStack(Material.APPLE)
        }, new ItemStack[] {
                new ItemStack(Material.SPRUCE_SAPLING)
        });
        registerRecipe(0, new ItemStack[] {
                new ItemStack(Material.SPRUCE_LEAVES), new ItemStack(Material.DIRT)
        }, new ItemStack[] {
                new ItemStack(Material.BONE_MEAL)
        });
        registerRecipe(0, new ItemStack[] {
                new ItemStack(Material.BIRCH_SAPLING), new ItemStack(Material.BONE_MEAL), new ItemStack(Material.DIRT)
        }, new ItemStack[] {
                new ItemStack(Material.BIRCH_LOG, 2), new ItemStack(Material.BIRCH_LEAVES, 2)
        });
        registerRecipe(0, new ItemStack[] {
                new ItemStack(Material.BIRCH_LOG), new ItemStack(Material.BONE_MEAL)
        }, new ItemStack[] {
                new ItemStack(Material.APPLE), new ItemStack(Material.STICK)
        });
        registerRecipe(0, new ItemStack[] {
                new ItemStack(Material.BIRCH_LEAVES), new ItemStack(Material.APPLE)
        }, new ItemStack[] {
                new ItemStack(Material.BIRCH_SAPLING)
        });
        registerRecipe(0, new ItemStack[] {
                new ItemStack(Material.BIRCH_LEAVES), new ItemStack(Material.DIRT)
        }, new ItemStack[] {
                new ItemStack(Material.BONE_MEAL)
        });
        registerRecipe(0, new ItemStack[] {
                new ItemStack(Material.JUNGLE_SAPLING), new ItemStack(Material.BONE_MEAL), new ItemStack(Material.DIRT)
        }, new ItemStack[] {
                new ItemStack(Material.JUNGLE_LOG, 2), new ItemStack(Material.JUNGLE_LEAVES, 2)
        });
        registerRecipe(0, new ItemStack[] {
                new ItemStack(Material.JUNGLE_LOG), new ItemStack(Material.BONE_MEAL)
        }, new ItemStack[] {
                new ItemStack(Material.APPLE), new ItemStack(Material.STICK)
        });
        registerRecipe(0, new ItemStack[] {
                new ItemStack(Material.JUNGLE_LEAVES), new ItemStack(Material.APPLE)
        }, new ItemStack[] {
                new ItemStack(Material.JUNGLE_SAPLING)
        });
        registerRecipe(0, new ItemStack[] {
                new ItemStack(Material.JUNGLE_LEAVES), new ItemStack(Material.DIRT)
        }, new ItemStack[] {
                new ItemStack(Material.BONE_MEAL)
        });
        registerRecipe(0, new ItemStack[] {
                new ItemStack(Material.ACACIA_SAPLING), new ItemStack(Material.BONE_MEAL), new ItemStack(Material.DIRT)
        }, new ItemStack[] {
                new ItemStack(Material.ACACIA_LOG, 2), new ItemStack(Material.ACACIA_LEAVES, 2)
        });
        registerRecipe(0, new ItemStack[] {
                new ItemStack(Material.ACACIA_LOG), new ItemStack(Material.BONE_MEAL)
        }, new ItemStack[] {
                new ItemStack(Material.APPLE), new ItemStack(Material.STICK)
        });
        registerRecipe(0, new ItemStack[] {
                new ItemStack(Material.ACACIA_LEAVES), new ItemStack(Material.APPLE)
        }, new ItemStack[] {
                new ItemStack(Material.ACACIA_SAPLING)
        });
        registerRecipe(0, new ItemStack[] {
                new ItemStack(Material.ACACIA_LEAVES), new ItemStack(Material.DIRT)
        }, new ItemStack[] {
                new ItemStack(Material.BONE_MEAL)
        });
        registerRecipe(0, new ItemStack[] {
                new ItemStack(Material.DARK_OAK_SAPLING), new ItemStack(Material.BONE_MEAL), new ItemStack(Material.DIRT)
        }, new ItemStack[] {
                new ItemStack(Material.DARK_OAK_LOG, 2), new ItemStack(Material.DARK_OAK_LEAVES, 2)
        });
        registerRecipe(0, new ItemStack[] {
                new ItemStack(Material.DARK_OAK_LOG), new ItemStack(Material.BONE_MEAL)
        }, new ItemStack[] {
                new ItemStack(Material.APPLE), new ItemStack(Material.STICK)
        });
        registerRecipe(0, new ItemStack[] {
                new ItemStack(Material.DARK_OAK_LEAVES), new ItemStack(Material.APPLE)
        }, new ItemStack[] {
                new ItemStack(Material.DARK_OAK_SAPLING)
        });
        registerRecipe(0, new ItemStack[] {
                new ItemStack(Material.DARK_OAK_LEAVES), new ItemStack(Material.DIRT)
        }, new ItemStack[] {
                new ItemStack(Material.BONE_MEAL)
        });
        registerRecipe(0, new ItemStack[] {
                new ItemStack(Material.WHEAT_SEEDS), new ItemStack(Material.BONE_MEAL), new ItemStack(Material.DIRT)
        }, new ItemStack[] {
                new ItemStack(Material.WHEAT, 2)
        });
        registerRecipe(0, new ItemStack[] {
                new ItemStack(Material.WHEAT), new ItemStack(Material.DIRT)
        }, new ItemStack[] {
                new ItemStack(Material.WHEAT_SEEDS), new ItemStack(Material.BONE_MEAL)
        });
        registerRecipe(0, new ItemStack[] {
                new ItemStack(Material.PUMPKIN_SEEDS), new ItemStack(Material.BONE_MEAL), new ItemStack(Material.DIRT)
        }, new ItemStack[] {
                new ItemStack(Material.PUMPKIN, 2)
        });
        registerRecipe(0, new ItemStack[] {
                new ItemStack(Material.PUMPKIN), new ItemStack(Material.DIRT)
        }, new ItemStack[] {
                new ItemStack(Material.PUMPKIN_SEEDS), new ItemStack(Material.BONE_MEAL)
        });
        registerRecipe(0, new ItemStack[] {
                new ItemStack(Material.MELON_SEEDS), new ItemStack(Material.BONE_MEAL), new ItemStack(Material.DIRT)
        }, new ItemStack[] {
                new ItemStack(Material.MELON, 2)
        });
        registerRecipe(0, new ItemStack[] {
                new ItemStack(Material.MELON), new ItemStack(Material.DIRT)
        }, new ItemStack[] {
                new ItemStack(Material.MELON_SEEDS), new ItemStack(Material.BONE_MEAL)
        });
        registerRecipe(0, new ItemStack[] {
                new ItemStack(Material.BEETROOT_SEEDS), new ItemStack(Material.BONE_MEAL), new ItemStack(Material.DIRT)
        }, new ItemStack[] {
                new ItemStack(Material.BEETROOT, 2)
        });
        registerRecipe(0, new ItemStack[] {
                new ItemStack(Material.BEETROOT), new ItemStack(Material.DIRT)
        }, new ItemStack[] {
                new ItemStack(Material.BEETROOT_SEEDS), new ItemStack(Material.BONE_MEAL)
        });
        registerRecipe(0, new ItemStack[] {
                new ItemStack(Material.COCOA_BEANS), new ItemStack(Material.BONE_MEAL)
        }, new ItemStack[] {
                new ItemStack(Material.COCOA_BEANS, 2)
        });
        registerRecipe(0, new ItemStack[] {
                new ItemStack(Material.COCOA_BEANS), new ItemStack(Material.DIRT)
        }, new ItemStack[] {
                new ItemStack(Material.BONE_MEAL)
        });
        registerRecipe(0, new ItemStack[] {
                new ItemStack(Material.SWEET_BERRIES), new ItemStack(Material.BONE_MEAL)
        }, new ItemStack[] {
                new ItemStack(Material.SWEET_BERRIES, 2)
        });
        registerRecipe(0, new ItemStack[] {
                new ItemStack(Material.SWEET_BERRIES), new ItemStack(Material.DIRT)
        }, new ItemStack[] {
                new ItemStack(Material.BONE_MEAL)
        });
        registerRecipe(0, new ItemStack[] {
                new ItemStack(Material.CARROT), new ItemStack(Material.BONE_MEAL)
        }, new ItemStack[] {
                new ItemStack(Material.CARROT, 2)
        });
        registerRecipe(0, new ItemStack[] {
                new ItemStack(Material.CARROT), new ItemStack(Material.DIRT)
        }, new ItemStack[] {
                new ItemStack(Material.BONE_MEAL)
        });
        registerRecipe(0, new ItemStack[] {
                new ItemStack(Material.POTATO), new ItemStack(Material.BONE_MEAL)
        }, new ItemStack[] {
                new ItemStack(Material.POTATO, 2)
        });
        registerRecipe(0, new ItemStack[] {
                new ItemStack(Material.POTATO), new ItemStack(Material.DIRT)
        }, new ItemStack[] {
                new ItemStack(Material.BONE_MEAL)
        });
        registerRecipe(0, new ItemStack[] {
                new ItemStack(Material.GLOW_BERRIES), new ItemStack(Material.BONE_MEAL)
        }, new ItemStack[] {
                new ItemStack(Material.GLOW_BERRIES, 2)
        });
        registerRecipe(0, new ItemStack[] {
                new ItemStack(Material.GLOW_BERRIES), new ItemStack(Material.DIRT)
        }, new ItemStack[] {
                new ItemStack(Material.BONE_MEAL)
        });
        registerRecipe(0, new ItemStack[] {

        }, new ItemStack[] {

        });
        registerRecipe(0, new ItemStack[] {

        }, new ItemStack[] {

        });
    }
}
