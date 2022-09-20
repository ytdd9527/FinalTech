package io.taraxacum.finaltech.util.slimefun;

import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.taraxacum.common.util.JavaUtil;
import io.taraxacum.finaltech.FinalTech;
import io.taraxacum.finaltech.api.factory.LanguageManager;
import org.bukkit.Material;

import javax.annotation.Nonnull;
import java.util.List;

/**
 * @author Final_ROOT
 * @since 2.0
 */
public class ConfigUtil {
    @Nonnull
    public static String getStatusMenuName(@Nonnull LanguageManager languageManager, @Nonnull SlimefunItem slimefunItem, String... strings) {
        return languageManager.replaceString(languageManager.getString("items", slimefunItem.getId(), "status", "name"), strings);
    }
    @Nonnull
    public static String getStatusMenuName(@Nonnull LanguageManager languageManager, @Nonnull String id, String... strings) {
        return languageManager.replaceString(languageManager.getString("items", id, "status", "name"), strings);
    }

    @Nonnull
    public static String[] getStatusMenuLore(@Nonnull LanguageManager languageManager, @Nonnull SlimefunItem slimefunItem, String... strings) {
        return languageManager.replaceStringArray(languageManager.getStringArray("items", slimefunItem.getId(), "status", "lore"), strings);
    }
    @Nonnull
    public static String[] getStatusMenuLore(@Nonnull LanguageManager languageManager, @Nonnull String id, String... strings) {
        return languageManager.replaceStringArray(languageManager.getStringArray("items", id, "status", "lore"), strings);
    }

    @Nonnull
    public static SlimefunItemStack getSlimefunItemStack(@Nonnull LanguageManager languageManager, @Nonnull String id, @Nonnull Material defaultMaterial, @Nonnull String defaultName) {
        Material material = Material.getMaterial(languageManager.getString("items", id, "material"));
        material = material == null ? defaultMaterial : material;
        String name = languageManager.containPath("items", id, "name") ? languageManager.getString("items", id, "name") : defaultName;
        return new SlimefunItemStack(id, material, name, languageManager.getStringArray("items", id, "lore"));
    }

    @Nonnull
    public static <T> T getOrDefaultItemSetting(@Nonnull T defaultValue, @Nonnull SlimefunItem slimefunItem, @Nonnull String... path) {
        return FinalTech.getItemManager().getOrDefault(defaultValue, JavaUtil.addToFirst(slimefunItem.getId(), path));
    }
    @Nonnull
    public static <T> T getOrDefaultItemSetting(@Nonnull T defaultValue, @Nonnull String id, @Nonnull String... path) {
        return FinalTech.getItemManager().getOrDefault(defaultValue, JavaUtil.addToFirst(id, path));
    }

    @Nonnull
    public static List<String> getItemStringList(@Nonnull SlimefunItem slimefunItem, @Nonnull String... path) {
        return FinalTech.getItemManager().getStringList(JavaUtil.addToFirst(slimefunItem.getId(), path));
    }

    @Nonnull
    public static <T> T getOrDefaultConfigSetting(@Nonnull T defaultValue, @Nonnull String... path) {
        return FinalTech.getConfigManager().getOrDefault(defaultValue, path);
    }

    @Nonnull
    public static <T> T getOrDefaultValueSetting(@Nonnull T defaultValue, @Nonnull String... path) {
        return FinalTech.getValueManager().getOrDefault(defaultValue, path);
    }

}
