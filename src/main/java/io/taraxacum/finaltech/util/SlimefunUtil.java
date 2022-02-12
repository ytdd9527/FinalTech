package io.taraxacum.finaltech.util;

import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.core.attributes.RecipeDisplayItem;
import io.github.thebusybiscuit.slimefun4.implementation.Slimefun;
import io.github.thebusybiscuit.slimefun4.libraries.dough.protection.Interaction;
import io.github.thebusybiscuit.slimefun4.utils.SlimefunUtils;
import io.taraxacum.finaltech.interfaces.RecipeItem;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.abstractItems.MachineRecipe;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @author Final_ROOT
 */
public class SlimefunUtil {
    /**
     * 从粘液科技本体注册的物品中搜索指定ID的机器
     * 读取其工作配方
     * 并注册到指定的机器中
     * @param machine 被注册工作配方的机器
     * @param slimefunId 粘液科技机器ID
     */
    public static void registerRecipeBySlimefunId(@Nonnull RecipeItem machine, @Nonnull String slimefunId) {
        final SlimefunItem slimefunItem = SlimefunItem.getById(slimefunId);
        try {
            Method method = slimefunItem.getClass().getMethod("getMachineRecipes");
            List<MachineRecipe> recipes = (List<MachineRecipe>)method.invoke(slimefunItem);
            if(recipes != null) {
                for(MachineRecipe recipe : recipes) {
                    machine.registerRecipe(new MachineRecipe(0, ItemStackUtil.mergeSameItem(recipe.getInput()), ItemStackUtil.mergeSameItem(recipe.getOutput())));
                }
            }
        } catch (NoSuchMethodException e) {
            Bukkit.getLogger().info("[FinalTECH]§e无法为本附属中ID为" + machine.getId() + "的机器从ID为" + slimefunId + "的物品读取对应的方法以注册机器的工作配方");
            Bukkit.getLogger().info("[FinalTECH]§b但是我们有备用方案!");
            if(slimefunItem instanceof RecipeDisplayItem) {
                List<ItemStack> displayRecipes = ((RecipeDisplayItem) slimefunItem).getDisplayRecipes();
                registerRecipeBySimpleDisplayRecipe(machine, displayRecipes);
                Bukkit.getLogger().info("[FinalTECH]§a备用方案成功了!");
            } else {
                Bukkit.getLogger().info("[FinalTECH]§c备用方案失败了!");
                e.printStackTrace();
            }
        } catch (InvocationTargetException | IllegalAccessException e) {
            Bukkit.getLogger().info("[FinalTECH]§e无法为本附属中ID为" + machine.getId() + "的机器从ID为" + slimefunId + "的物品读取对应的方法以注册机器的工作配方");
            Bukkit.getLogger().info("[FinalTECH]§b但是我们有备用方案!");
            if(slimefunItem instanceof RecipeDisplayItem) {
                List<ItemStack> displayRecipes = ((RecipeDisplayItem) slimefunItem).getDisplayRecipes();
                registerRecipeBySimpleDisplayRecipe(machine, displayRecipes);
                Bukkit.getLogger().info("[FinalTECH]§a备用方案成功了!");
            } else {
                Bukkit.getLogger().info("[FinalTECH]§c备用方案失败了!");
                e.printStackTrace();
            }
        }
    }

    public static void registerRecipeByRecipeType(@Nonnull RecipeItem item, @Nonnull RecipeType recipeType) {
        List<SlimefunItem> list = Slimefun.getRegistry().getEnabledSlimefunItems();
        for(SlimefunItem slimefunItem : list) {
            if(recipeType.equals(slimefunItem.getRecipeType())) {
                item.registerRecipe(0, ItemStackUtil.mergeSameItem(slimefunItem.getRecipe()), new ItemStack[] {slimefunItem.getRecipeOutput()});
            }
        }
    }

    public static void registerRecipeBySimpleDisplayRecipe(@Nonnull RecipeItem item, List<ItemStack> displayRecipes) {
        for(int i = 0; i < displayRecipes.size(); i+= 2) {
            item.registerRecipe(0, new ItemStack(displayRecipes.get(i)), new ItemStack(displayRecipes.get(i+1)));
        }
    }

    public static void registerRecipeByDisplayRecipe(@Nonnull RecipeItem item, List<ItemStack> displayRecipes) {
        List<MachineRecipe> recipes = item.getMachineRecipes();
        List<ItemStack> inputs = new ArrayList<>();
        List<ItemStack> outputs = new ArrayList<>();
        ItemStack inputItem;
        ItemStack outputItem;
        int i = 0;
        do{
            inputItem = i*2 < displayRecipes.size() ? displayRecipes.get(i*2) : null;
            outputItem = i*2+1 < displayRecipes.size() ? displayRecipes.get(i*2+1) : null;
            if(ItemStackUtil.isItemNull(inputItem) && ItemStackUtil.isItemNull(outputItem)) {
                continue;
            }
            boolean sameInput = false;
            boolean sameOutput = false;
            if((inputs.size() > 0 || outputs.size() > 0) && i > 0) {
                sameInput = SlimefunUtils.isItemSimilar(displayRecipes.get(i*2 - 2), inputItem, true, false) && displayRecipes.get(i*2 - 2).getAmount() == inputItem.getAmount();
                sameOutput = (SlimefunUtils.isItemSimilar(displayRecipes.get(i*2 - 1), outputItem, true, false) && displayRecipes.get(i*2 - 1).getAmount() == outputItem.getAmount()) || (ItemStackUtil.isItemNull(outputItem) && ItemStackUtil.isItemNull(displayRecipes.get(i*2 - 1)));
            }
            boolean newRecipe = !sameInput && !sameOutput;
            if((inputs.size() > 0 || outputs.size() > 0) && i > 0) {
                if(ItemStackUtil.isItemNull(displayRecipes.get(i*2 - 2)) && !ItemStackUtil.isItemNull(displayRecipes.get(i*2 - 1)) && ItemStackUtil.isItemNull(inputItem) && !ItemStackUtil.isItemNull(outputItem)) {
                    newRecipe = false;
                }
                if(!ItemStackUtil.isItemNull(displayRecipes.get(i*2 - 2)) && ItemStackUtil.isItemNull(displayRecipes.get(i*2 - 1)) && !ItemStackUtil.isItemNull(inputItem)) {
                    newRecipe = false;
                }
                if(!ItemStackUtil.isItemNull(displayRecipes.get(i*2 - 2)) && !ItemStackUtil.isItemNull(displayRecipes.get(i*2 - 1)) && !ItemStackUtil.isItemNull(inputItem) && ItemStackUtil.isItemNull(outputItem)) {
                    newRecipe = true;
                }
            }
            if(newRecipe) {
                if(inputs.size() > 0 || outputs.size() > 0) {
                    recipes.add(ItemStackUtil.createRandomMachineRecipe(inputs, outputs));
                }
                inputs = new ArrayList<>();
                outputs = new ArrayList<>();
            }
            if(!sameInput && !ItemStackUtil.isItemNull(inputItem)) {
                inputs.add(inputItem);
            }
            if(!sameOutput && !ItemStackUtil.isItemNull(outputItem)) {
                outputs.add(outputItem);
            }
        } while (++i*2<displayRecipes.size());
        recipes.add(ItemStackUtil.createRandomMachineRecipe(inputs, outputs));
    }

    public static final boolean hasPermission(@Nonnull Block block, @Nonnull String uuid) {
        OfflinePlayer player = Bukkit.getOfflinePlayer(UUID.fromString(uuid));
        return Slimefun.getProtectionManager().hasPermission(player, block, Interaction.INTERACT_BLOCK);
    }
}
