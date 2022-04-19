package io.taraxacum.finaltech.util;

import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.core.attributes.RecipeDisplayItem;
import io.github.thebusybiscuit.slimefun4.implementation.Slimefun;
import io.github.thebusybiscuit.slimefun4.libraries.dough.protection.Interaction;
import io.github.thebusybiscuit.slimefun4.utils.SlimefunUtils;
import io.github.thebusybiscuit.slimefun4.utils.itemstack.ItemStackWrapper;
import io.taraxacum.finaltech.interfaces.RecipeItem;
import me.mrCookieSlime.CSCoreLibPlugin.Configuration.Config;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.abstractItems.MachineRecipe;
import me.mrCookieSlime.Slimefun.Objects.handlers.BlockTicker;
import me.mrCookieSlime.Slimefun.api.BlockStorage;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.io.ObjectInputFilter;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @author Final_ROOT
 */
public class SlimefunUtil {
    public static final String KEY_ID = "id";

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
                    machine.registerRecipe(new MachineRecipe(0, ItemStackWrapper.wrapArray(ItemStackUtil.mergeSameItem(recipe.getInput())), ItemStackWrapper.wrapArray(recipe.getOutput())));
                }
            }
        } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
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
                item.registerRecipe(0, ItemStackWrapper.wrapArray(ItemStackUtil.mergeSameItem(slimefunItem.getRecipe())), new ItemStack[] {slimefunItem.getRecipeOutput()});
            }
        }
    }

    public static void registerRecipeBySimpleDisplayRecipe(@Nonnull RecipeItem item, List<ItemStack> displayRecipes) {
        for(int i = 0; i < displayRecipes.size(); i+= 2) {
            item.registerRecipe(0, ItemStackWrapper.wrap(displayRecipes.get(i)), ItemStackWrapper.wrap(displayRecipes.get(i+1)));
        }
    }

    public static String getCharge(@Nonnull Location location) {
        return BlockStorage.hasBlockInfo(location) ? getCharge(BlockStorage.getLocationInfo(location)) : "0";
    }

    public static String getCharge(@Nonnull Config config) {
        return config.contains("energy-charge") ? config.getString("energy-charge") : StringNumberUtil.ZERO;
    }

    public static void setCharge(@Nonnull Location location, String energy) {
        BlockStorage.addBlockInfo(location, "energy-charge", energy);
    }
    public static void setCharge(@Nonnull Config config, String energy) {
        config.setValue("energy-charge", energy);
    }
    public static void setCharge(@Nonnull Config config, int energy) {
        config.setValue("energy-charge", String.valueOf(energy));
    }

    public static void runBlockTicker(@Nonnull BlockTicker blockTicker, @Nonnull Block block, @Nonnull SlimefunItem slimefunItem, @Nonnull Config config) {
        if(blockTicker.isSynchronized()) {
            Slimefun.runSync(() -> blockTicker.tick(block, slimefunItem, config));
        } else {
            blockTicker.tick(block, slimefunItem, config);
        }
    }

    public static final boolean hasPermission(@Nonnull Block block, @Nonnull String uuid) {
        OfflinePlayer player = Bukkit.getOfflinePlayer(UUID.fromString(uuid));
        return player.isOnline() && Slimefun.getProtectionManager().hasPermission(player, block, Interaction.INTERACT_BLOCK);
    }

    public static boolean hasPermission(@Nonnull Entity entity, @Nonnull String uuid) {
        OfflinePlayer player = Bukkit.getOfflinePlayer(UUID.fromString(uuid));
        return hasPermission(entity, player);
    }

    public static boolean hasPermission(@Nonnull Entity entity, @Nullable OfflinePlayer player) {
        return player.isOnline() && Slimefun.getProtectionManager().hasPermission(player, entity.getLocation(), Interaction.ATTACK_ENTITY);
    }
}
