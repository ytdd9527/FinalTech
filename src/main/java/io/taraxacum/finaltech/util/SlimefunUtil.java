package io.taraxacum.finaltech.util;

import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.core.attributes.RecipeDisplayItem;
import io.github.thebusybiscuit.slimefun4.implementation.Slimefun;
import io.github.thebusybiscuit.slimefun4.libraries.dough.protection.Interaction;
import io.taraxacum.finaltech.FinalTech;
import io.taraxacum.finaltech.api.interfaces.RecipeItem;
import io.taraxacum.common.util.StringNumberUtil;
import me.mrCookieSlime.CSCoreLibPlugin.Configuration.Config;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.abstractItems.MachineRecipe;
import me.mrCookieSlime.Slimefun.Objects.handlers.BlockTicker;
import me.mrCookieSlime.Slimefun.api.BlockStorage;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.UUID;

/**
 * @author Final_ROOT
 * @since 1.0
 */
public class SlimefunUtil {
    public static final String KEY_ID = "id";

    public static void registerRecipeBySlimefunId(@Nonnull RecipeItem recipeItem, @Nonnull String slimefunId) {
        final SlimefunItem slimefunItem = SlimefunItem.getById(slimefunId);
        try {
            Method method = slimefunItem.getClass().getMethod("getMachineRecipes");
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
            FinalTech.getInstance().getServer().getLogger().info("[FinalTECH]§e无法为本附属中ID为" + recipeItem.getId() + "的机器从ID为" + slimefunId + "的物品读取对应的方法以注册机器的工作配方");
            FinalTech.getInstance().getServer().getLogger().info("[FinalTECH]§b但是我们有备用方案!");
            if (slimefunItem instanceof RecipeDisplayItem) {
                List<ItemStack> displayRecipes = ((RecipeDisplayItem) slimefunItem).getDisplayRecipes();
                SlimefunUtil.registerRecipeBySimpleDisplayRecipe(recipeItem, displayRecipes);
                FinalTech.getInstance().getServer().getLogger().info("[FinalTECH]§a备用方案成功了!");
            } else {
                FinalTech.getInstance().getServer().getLogger().info("[FinalTECH]§c备用方案失败了!");
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

    public static String getCharge(@Nonnull Location location) {
        return BlockStorage.hasBlockInfo(location) ? SlimefunUtil.getCharge(BlockStorage.getLocationInfo(location)) : "0";
    }
    public static String getCharge(@Nonnull Config config) {
        return config.contains("energy-charge") ? config.getString("energy-charge") : StringNumberUtil.ZERO;
    }
    public static void setCharge(@Nonnull Location location, @Nonnull String energy) {
        BlockStorage.addBlockInfo(location, "energy-charge", energy);
    }
    public static void setCharge(@Nonnull Config config, @Nonnull String energy) {
        config.setValue("energy-charge", energy);
    }
    public static void setCharge(@Nonnull Config config, int energy) {
        config.setValue("energy-charge", String.valueOf(energy));
    }

    public static void runBlockTicker(@Nonnull BlockTicker blockTicker, @Nonnull Block block, @Nonnull SlimefunItem slimefunItem, @Nonnull Config config) {
        if (blockTicker.isSynchronized()) {
            FinalTech.getInstance().getServer().getScheduler().runTask(FinalTech.getInstance(), () -> blockTicker.tick(block, slimefunItem, config));
        } else {
            blockTicker.tick(block, slimefunItem, config);
        }
    }
    public static void runBlockTickerAsync(@Nonnull BlockTicker blockTicker, @Nonnull Block block, @Nonnull SlimefunItem slimefunItem, @Nonnull Config config) {
        if (blockTicker.isSynchronized()) {
            FinalTech.getInstance().getServer().getScheduler().runTask(FinalTech.getInstance(), () -> blockTicker.tick(block, slimefunItem, config));
        } else {
            FinalTech.getInstance().getServer().getScheduler().runTaskAsynchronously(FinalTech.getInstance(), () -> blockTicker.tick(block, slimefunItem, config));
        }
    }

    public static boolean hasPermission(@Nonnull String uuid, @Nonnull Block block, @Nonnull Interaction... interactions) {
        OfflinePlayer offlinePlayer = FinalTech.getInstance().getServer().getOfflinePlayer(UUID.fromString(uuid));
        for (Interaction interaction : interactions) {
            if (!Slimefun.getProtectionManager().hasPermission(offlinePlayer, block.getLocation(), interaction)) {
                return false;
            }
        }
        return true;
    }
    public static boolean hasPermission(@Nonnull String uuid, @Nonnull Entity entity, @Nonnull Interaction... interactions) {
        OfflinePlayer offlinePlayer = FinalTech.getInstance().getServer().getOfflinePlayer(UUID.fromString(uuid));
        for (Interaction interaction : interactions) {
            if (!Slimefun.getProtectionManager().hasPermission(offlinePlayer, entity.getLocation(), interaction)) {
                return false;
            }
        }
        return true;
    }
    public static boolean hasPermission(@Nonnull String uuid, @Nonnull Location location, @Nonnull Interaction... interactions) {
        OfflinePlayer offlinePlayer = FinalTech.getInstance().getServer().getOfflinePlayer(UUID.fromString(uuid));
        for (Interaction interaction : interactions) {
            if (!Slimefun.getProtectionManager().hasPermission(offlinePlayer, location, interaction)) {
                return false;
            }
        }
        return true;
    }
    public static boolean hasPermission(@Nonnull Player player, @Nonnull Location location, @Nonnull Interaction... interactions) {
        for (Interaction interaction : interactions) {
            if (!Slimefun.getProtectionManager().hasPermission(player, location, interaction)) {
                return false;
            }
        }
        return true;
    }
}
