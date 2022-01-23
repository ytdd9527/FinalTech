package io.taraxacum.finaltech.machine;

import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.implementation.Slimefun;
import io.github.thebusybiscuit.slimefun4.implementation.SlimefunItems;
import io.github.thebusybiscuit.slimefun4.libraries.dough.items.CustomItemStack;
import io.github.thebusybiscuit.slimefun4.libraries.dough.protection.Interaction;
import io.github.thebusybiscuit.slimefun4.utils.SlimefunUtils;
import io.taraxacum.finaltech.abstractItem.FinalAdvanceMachine;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenuPreset;
import me.mrCookieSlime.Slimefun.api.inventory.DirtyChestMenu;
import me.mrCookieSlime.Slimefun.api.item_transport.ItemTransportFlow;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;
import java.util.ArrayList;

public class AdvancedDustFactory extends FinalAdvanceMachine {

    public AdvancedDustFactory(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
        super(itemGroup, item, recipeType, recipe);
    }

    @Override
    public void registerDefaultRecipes() {
        registerRecipe(0, new ItemStack(Material.COBBLESTONE), new ItemStack(SlimefunItems.SIFTED_ORE));
        registerRecipe(0, new ItemStack[] {
                new ItemStack(SlimefunItems.SIFTED_ORE),
                new ItemStack(SlimefunItems.IRON_DUST)
        }, new ItemStack[] {
                new CustomItemStack(SlimefunItems.IRON_DUST, 2)
        });
        registerRecipe(0, new ItemStack[] {
                new ItemStack(SlimefunItems.SIFTED_ORE),
                new ItemStack(SlimefunItems.COPPER_DUST)
        }, new ItemStack[] {
                new CustomItemStack(SlimefunItems.COPPER_DUST, 2)
        });
        registerRecipe(0, new ItemStack[] {
                new ItemStack(SlimefunItems.SIFTED_ORE),
                new ItemStack(SlimefunItems.LEAD_DUST)
        }, new ItemStack[] {
                new CustomItemStack(SlimefunItems.LEAD_DUST, 2)
        });
        registerRecipe(0, new ItemStack[] {
                new ItemStack(SlimefunItems.SIFTED_ORE),
                new ItemStack(SlimefunItems.TIN_DUST)
        }, new ItemStack[] {
                new CustomItemStack(SlimefunItems.TIN_DUST, 2)
        });
        registerRecipe(0, new ItemStack[] {
                new ItemStack(SlimefunItems.SIFTED_ORE),
                new ItemStack(SlimefunItems.SILVER_DUST)
        }, new ItemStack[] {
                new CustomItemStack(SlimefunItems.SILVER_DUST, 2)
        });
        registerRecipe(0, new ItemStack[] {
                new ItemStack(SlimefunItems.SIFTED_ORE),
                new ItemStack(SlimefunItems.ALUMINUM_DUST)
        }, new ItemStack[] {
                new CustomItemStack(SlimefunItems.ALUMINUM_DUST, 2)
        });
        registerRecipe(0, new ItemStack[] {
                new ItemStack(SlimefunItems.SIFTED_ORE),
                new ItemStack(SlimefunItems.ZINC_DUST)
        }, new ItemStack[] {
                new CustomItemStack(SlimefunItems.ZINC_DUST, 2)
        });
        registerRecipe(0, new ItemStack[] {
                new ItemStack(SlimefunItems.SIFTED_ORE),
                new ItemStack(SlimefunItems.MAGNESIUM_DUST)
        }, new ItemStack[] {
                new CustomItemStack(SlimefunItems.MAGNESIUM_DUST, 2)
        });
        registerRecipe(0, new ItemStack[] {
                new ItemStack(SlimefunItems.SIFTED_ORE),
                new ItemStack(SlimefunItems.GOLD_DUST)
        }, new ItemStack[] {
                new CustomItemStack(SlimefunItems.GOLD_DUST, 2)
        });
        registerRecipe(0, new ItemStack[] {
                new CustomItemStack(SlimefunItems.SIFTED_ORE, 2)
        }, new ItemStack[] {
                new CustomItemStack(SlimefunItems.URANIUM, 1)
        });
    }

    @Nonnull
    @Override
    public String getMachineIdentifier() {
        return "FINALTECH_ADVANCED_DUST_FACTORY";
    }
}
