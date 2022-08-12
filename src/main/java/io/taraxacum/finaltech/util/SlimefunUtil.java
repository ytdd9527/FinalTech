package io.taraxacum.finaltech.util;

import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.api.researches.Research;
import io.github.thebusybiscuit.slimefun4.core.attributes.RecipeDisplayItem;
import io.github.thebusybiscuit.slimefun4.implementation.Slimefun;
import io.github.thebusybiscuit.slimefun4.libraries.dough.protection.Interaction;
import io.taraxacum.finaltech.FinalTech;
import io.taraxacum.finaltech.api.factory.LanguageManager;
import io.taraxacum.finaltech.api.factory.ServerRunnableLockFactory;
import io.taraxacum.finaltech.api.interfaces.RecipeItem;
import io.taraxacum.common.util.StringNumberUtil;
import io.taraxacum.finaltech.core.helper.IgnorePermission;
import me.mrCookieSlime.CSCoreLibPlugin.Configuration.Config;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.abstractItems.MachineRecipe;
import me.mrCookieSlime.Slimefun.Objects.handlers.BlockTicker;
import me.mrCookieSlime.Slimefun.api.BlockStorage;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.NamespacedKey;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.plugin.java.JavaPlugin;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.UUID;
import java.util.concurrent.Future;

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

    public static void registerDescriptiveRecipe(@Nonnull LanguageManager languageManager, @Nonnull RecipeItem recipeItem, String... strings) {
        String id = recipeItem.getId().toLowerCase(Locale.ROOT).replace("_", "-");
        List<String> infoList = languageManager.getStringList("items", id, "info");
        for(String infoIndex : infoList) {
            if(languageManager.containPath("items", id, "info", infoIndex, "name")) {
                if(strings.length == 0) {
                    recipeItem.registerDescriptiveRecipe(languageManager.getString("items", id, "info", infoIndex, "name"), languageManager.getStringArray("items", id, "info", infoIndex, "lore"));
                } else {
                    recipeItem.registerDescriptiveRecipe(languageManager.getString("items", id, "info", infoIndex, "name"), languageManager.replaceStringArray(languageManager.getStringArray("items", id, "info", infoIndex, "lore"), strings));
                }
            }
        }
    }

    @Nonnull
    public static String[] updateMenuLore(@Nonnull LanguageManager languageManager, @Nonnull SlimefunItem slimefunItem, String... strings) {
        return languageManager.replaceStringArray(languageManager.getStringArray("items", SlimefunUtil.getIdFormatName(slimefunItem.getClass()), "status", "lore"), strings);
    }
    @Nonnull
    public static String[] updateMenuLore(@Nonnull LanguageManager languageManager, @Nonnull String id, String... strings) {
        return languageManager.replaceStringArray(languageManager.getStringArray("items", id, "status", "lore"), strings);
    }

    @Nullable
    public static Research setSingleResearch(@Nonnull ItemStack itemStack, int cost, boolean forceCost) {
        SlimefunItem slimefunItem = SlimefunItem.getByItem(itemStack);
        if(slimefunItem != null) {
            Research research = new Research(new NamespacedKey(slimefunItem.getAddon().getJavaPlugin(), slimefunItem.getId()), slimefunItem.getId().hashCode(), slimefunItem.getId(), cost);
            research.addItems(slimefunItem);
            research.register();
            if(forceCost) {
                research.setCost(cost);
            }
            return research;
        }
        return null;
    }

    @Nonnull
    public static Research setResearches(@Nonnull JavaPlugin javaPlugin, @Nonnull String key, int id, @Nonnull String defaultName, int defaultCost, boolean forceCost, @Nonnull ItemStack... itemStacks) {
        Research research = new Research(new NamespacedKey(javaPlugin, key), id, defaultName, defaultCost);
        research.addItems(itemStacks).register();
        if(forceCost) {
            research.setCost(defaultCost);
        }
        return research;
    }

    public static void setResearchBySlimefunItems(@Nonnull SlimefunItemStack slimefunItemStack1, @Nonnull SlimefunItemStack slimefunItemStack2) {
        SlimefunItem slimefunItem1 = SlimefunItem.getByItem(slimefunItemStack1);
        SlimefunItem slimefunItem2 = SlimefunItem.getByItem(slimefunItemStack2);
        if(slimefunItem1 != null && slimefunItem2 != null) {
            slimefunItem1.setResearch(slimefunItem2.getResearch());
        }
    }
    public static void setResearchBySlimefunItemId(@Nonnull SlimefunItemStack slimefunItemStack, @Nonnull String id) {
        SlimefunItem slimefunItem1 = SlimefunItem.getByItem(slimefunItemStack);
        SlimefunItem slimefunItem2 = SlimefunItem.getById(id);
        if(slimefunItem1 != null && slimefunItem2 != null) {
            slimefunItem1.setResearch(slimefunItem2.getResearch());
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

    public static void runBlockTickerLocal(@Nonnull JavaPlugin javaPlugin, @Nonnull BlockTicker blockTicker, @Nonnull Block block, @Nonnull SlimefunItem slimefunItem, @Nonnull Config config) {
        if(blockTicker.isSynchronized()) {
            Bukkit.getScheduler().runTask(javaPlugin, () -> blockTicker.tick(block, slimefunItem, config));
        } else {
            blockTicker.tick(block, slimefunItem, config);
        }
    }

    public static <T> void runBlockTicker(@Nonnull ServerRunnableLockFactory<T> serverRunnableLockFactory, @Nonnull BlockTicker blockTicker, @Nonnull Block block, @Nonnull SlimefunItem slimefunItem, @Nonnull Config config, @Nonnull T... locks) {
        if(blockTicker.isSynchronized()) {
            Bukkit.getScheduler().runTask(serverRunnableLockFactory.getPlugin(), () -> blockTicker.tick(block, slimefunItem, config));
        } else {
            serverRunnableLockFactory.waitThenRun(() -> blockTicker.tick(block, slimefunItem, config), locks);
        }
    }

    @Nonnull
    @SafeVarargs
    public static <T> Future<Void> runBlockTickerCallable(@Nonnull ServerRunnableLockFactory<T> serverRunnableLockFactory, @Nonnull BlockTicker blockTicker, @Nonnull Block block, @Nonnull SlimefunItem slimefunItem, @Nonnull Config config, @Nonnull T... locks) {
        if(blockTicker.isSynchronized()) {
            return Bukkit.getScheduler().callSyncMethod(serverRunnableLockFactory.getPlugin(), () -> {
                blockTicker.tick(block, slimefunItem, config);
                return null;
            });
        } else {
            return serverRunnableLockFactory.waitThenRun(() -> blockTicker.tick(block, slimefunItem, config), locks);
        }
    }

    public static boolean checkOfflinePermission(@Nonnull Location sourceLocation, @Nonnull ItemStack itemStack, @Nonnull Location... targetLocations) {
        if(!itemStack.hasItemMeta()) {
            return false;
        }
        ItemMeta itemMeta = itemStack.getItemMeta();
        String uuid = PlayerUtil.parseIdInItem(itemMeta);
        Boolean ignorePermission = PlayerUtil.parseIgnorePermissionInItem(itemMeta);
        Player player = Bukkit.getPlayer(uuid);
        if(player != null && player.isOnline()) {
            for(Location targetLocation : targetLocations) {
                if(!SlimefunUtil.checkPermission(player, targetLocation, Interaction.INTERACT_BLOCK)) {
                    if(ignorePermission) {
                        PlayerUtil.updateIgnorePermissionInItem(itemMeta, false);
                        itemStack.setItemMeta(itemMeta);
                    }
                    return false;
                }
            }
            if(!ignorePermission) {
                PlayerUtil.updateIgnorePermissionInItem(itemMeta, true);
                itemStack.setItemMeta(itemMeta);
            }
            return true;
        } else {
            return ignorePermission;
        }
    }
    public static boolean checkOfflinePermission(@Nonnull Location sourceLocation, @Nonnull Config config, @Nonnull Location... targetLocations) {
        String uuid = config.getString("UUID");
        Player player = Bukkit.getPlayer(uuid);
        if(player != null && player.isOnline()) {
            for(Location targetLocation : targetLocations) {
                if(!SlimefunUtil.checkPermission(player, targetLocation, Interaction.INTERACT_BLOCK)) {
                    IgnorePermission.HELPER.setOrClearValue(sourceLocation, IgnorePermission.VALUE_FALSE);
                    return false;
                }
            }
            IgnorePermission.HELPER.setOrClearValue(sourceLocation, IgnorePermission.VALUE_TRUE);
            return true;
        } else return IgnorePermission.VALUE_TRUE.equals(IgnorePermission.HELPER.getOrDefaultValue(config));
    }
    public static boolean checkOfflinePermission(@Nonnull Location sourceLocation, @Nonnull Location... targetLocations) {
        return SlimefunUtil.checkOfflinePermission(sourceLocation, BlockStorage.getLocationInfo(sourceLocation), targetLocations);
    }

    public static boolean checkPermission(@Nonnull String uuid, @Nonnull Block block, @Nonnull Interaction... interactions) {
        Player player = Bukkit.getOfflinePlayer(UUID.fromString(uuid)).getPlayer();
        if(player == null || player.isBanned()) {
            return false;
        }
        return SlimefunUtil.checkPermission(player, block.getLocation(), interactions);
    }
    public static boolean checkPermission(@Nonnull String uuid, @Nonnull Entity entity, @Nonnull Interaction... interactions) {
        Player player = Bukkit.getOfflinePlayer(UUID.fromString(uuid)).getPlayer();
        if(player == null || player.isBanned()) {
            return false;
        }
        return SlimefunUtil.checkPermission(player, entity.getLocation(), interactions);
    }
    public static boolean checkPermission(@Nonnull String uuid, @Nonnull Location location, @Nonnull Interaction... interactions) {
        Player player = Bukkit.getOfflinePlayer(UUID.fromString(uuid)).getPlayer();
        if(player == null || player.isBanned()) {
            return false;
        }
        return SlimefunUtil.checkPermission(player, location, interactions);
    }
    public static boolean checkPermission(@Nonnull Player player, @Nonnull Location location, @Nonnull Interaction... interactions) {
        for (Interaction interaction : interactions) {
            if (!Slimefun.getProtectionManager().hasPermission(player, location, interaction)) {
                return false;
            }
        }
        return true;
    }

    @Nonnull
    public static String getIdFormatName(@Nonnull Class<? extends SlimefunItem> clazz) {
        StringBuilder stringBuilder = new StringBuilder();
        boolean append = false;
        for(char c : clazz.getSimpleName().toCharArray()) {
            if(c >= 'A' && c <= 'Z') {
                if(append) {
                    stringBuilder.append("_");
                }
                stringBuilder.append(c);
                append = true;
            } else if (c >= 'a' && c <= 'z'){
                stringBuilder.append((char)(c - 32));
            } else {
                if(append) {
                    stringBuilder.append("_");
                    append = false;
                }
                stringBuilder.append(c);
            }
        }
        if (stringBuilder.indexOf("_") == 0) {
            stringBuilder.delete(0, 1);
        }
        return stringBuilder.toString();
    }
}
