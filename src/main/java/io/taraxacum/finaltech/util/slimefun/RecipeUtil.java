package io.taraxacum.finaltech.util.slimefun;

import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.core.attributes.RecipeDisplayItem;
import io.github.thebusybiscuit.slimefun4.implementation.Slimefun;
import io.taraxacum.finaltech.api.factory.LanguageManager;
import io.taraxacum.finaltech.api.interfaces.RecipeItem;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.abstractItems.MachineRecipe;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Locale;

/**
 * @author Final_ROOT
 * @since 2.0
 */
public class RecipeUtil {
    public static void registerRecipeBySlimefunId(@Nonnull RecipeItem recipeItem, @Nonnull String slimefunId) {
        final SlimefunItem slimefunItem = SlimefunItem.getById(slimefunId);
        try {
            Method method = slimefunItem.getClass().getDeclaredMethod("getMachineRecipes");
            List<MachineRecipe> recipes = (List<MachineRecipe>)method.invoke(slimefunItem);
            if (recipes != null) {
                for (MachineRecipe recipe : recipes) {
                    boolean disabled = false;
                    if(recipe.getOutput().length > 0) {
                        ItemStack itemStack = recipe.getOutput()[0];
                        SlimefunItem sfItem = SlimefunItem.getByItem(itemStack);
                        if(sfItem != null) {
                            disabled = sfItem.isDisabled();
                        }
                    }
                    if(!disabled) {
                        recipeItem.registerRecipeInCard(0, recipe.getInput(), recipe.getOutput());
                    }
                }
            }
        } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
            if (slimefunItem instanceof RecipeDisplayItem) {
                List<ItemStack> displayRecipes = ((RecipeDisplayItem) slimefunItem).getDisplayRecipes();
                RecipeUtil.registerRecipeBySimpleDisplayRecipe(recipeItem, displayRecipes);
            } else {
                e.printStackTrace();
            }
        }
    }

    public static void registerRecipeByRecipeType(@Nonnull RecipeItem recipeItem, @Nonnull RecipeType recipeType) {
        List<SlimefunItem> list = Slimefun.getRegistry().getEnabledSlimefunItems();
        for (SlimefunItem slimefunItem : list) {
            if (!slimefunItem.isDisabled() && recipeType.equals(slimefunItem.getRecipeType())) {
                recipeItem.registerRecipeInCard(0, slimefunItem);
            }
        }
    }

    public static void registerRecipeBySimpleDisplayRecipe(@Nonnull RecipeItem recipeItem, List<ItemStack> displayRecipes) {
        for (int i = 0; i < displayRecipes.size(); i+= 2) {
            boolean disabled = false;
            ItemStack itemStack = displayRecipes.get(i + 1);
            SlimefunItem sfItem = SlimefunItem.getByItem(itemStack);
            if(sfItem != null) {
                disabled = sfItem.isDisabled();
            }
            if(!disabled) {
                recipeItem.registerRecipeInCard(0, new ItemStack[] {displayRecipes.get(i)}, new ItemStack[] {displayRecipes.get(i+1)});
            }
        }
    }

    public static void registerDescriptiveRecipe(@Nonnull LanguageManager languageManager, @Nonnull RecipeItem recipeItem, String... strings) {
        String id = recipeItem.getId();
        int i = 1;
        while(true) {
            String index = String.valueOf(i++);
            if(languageManager.containPath("items", id, "info", index, "name")) {
                if(strings.length == 0) {
                    recipeItem.registerDescriptiveRecipe(languageManager.getString("items", id, "info", index, "name"), languageManager.getStringArray("items", id, "info", index, "lore"));
                } else {
                    recipeItem.registerDescriptiveRecipe(languageManager.getString("items", id, "info", index, "name"), languageManager.replaceStringArray(languageManager.getStringArray("items", id, "info", index, "lore"), strings));
                }
            } else {
                break;
            }
        }
    }
}
