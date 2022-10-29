package io.taraxacum.libs.slimefun.util;

import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.implementation.Slimefun;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import javax.annotation.Nonnull;

/**
 * @author Final_ROOT
 * @since 2.0
 */
public class SfItemUtil {
    private static final NamespacedKey SLIMEFUN_ITEM_KEY = new NamespacedKey(Slimefun.instance(), "slimefun_item");

    @Nonnull
    public static String getIdFormatName(@Nonnull Class<? extends SlimefunItem> clazz) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("FINALTECH_");
        boolean append = false;
        for (char c : clazz.getSimpleName().toCharArray()) {
            if (c >= 'A' && c <= 'Z') {
                if (append) {
                    stringBuilder.append("_");
                }
                stringBuilder.append(c);
                append = true;
            } else if (c >= 'a' && c <= 'z') {
                stringBuilder.append((char)(c - 32));
            } else {
                if (append) {
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

    public static void removeSlimefunId(@Nonnull ItemStack itemStack) {
        if (itemStack.hasItemMeta()) {
            ItemMeta itemMeta = itemStack.getItemMeta();
            PersistentDataContainer persistentDataContainer = itemMeta.getPersistentDataContainer();
            if (persistentDataContainer.has(SLIMEFUN_ITEM_KEY, PersistentDataType.STRING)) {
                persistentDataContainer.remove(SLIMEFUN_ITEM_KEY);
                itemStack.setItemMeta(itemMeta);
            }
        }
    }
}
