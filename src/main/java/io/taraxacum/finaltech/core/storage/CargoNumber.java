package io.taraxacum.finaltech.core.storage;

import io.github.thebusybiscuit.slimefun4.libraries.dough.items.CustomItemStack;
import io.taraxacum.finaltech.core.factory.BlockStorageHelper;
import io.taraxacum.finaltech.core.factory.BlockStorageIconHelper;
import io.taraxacum.finaltech.core.factory.BlockStorageLoreHelper;
import io.taraxacum.finaltech.util.TextUtil;
import me.mrCookieSlime.CSCoreLibPlugin.general.Inventory.ClickAction;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * @author Final_ROOT
 */
public final class CargoNumber {
    public static final String KEY = "cb";
    public static final String KEY_INPUT = "cni";
    public static final String KEY_OUTPUT = "cno";

    public static final ItemStack CARGO_NUMBER_ICON = new CustomItemStack(Material.TARGET, "&d数量限制", "&7当前数量限制: &f64");
    public static final ItemStack CARGO_NUMBER_ADD_ICON = new CustomItemStack(Material.GREEN_CONCRETE, "&6增加数量限制");
    public static final ItemStack CARGO_NUMBER_SUB_ICON = new CustomItemStack(Material.RED_CONCRETE, "&9减少数量限制");

    public static final BlockStorageIconHelper HELPER = BlockStorageIconHelper.newInstanceOrGet(BlockStorageHelper.ID_CARGO, KEY, new LinkedHashMap<>() {{
        for(int i = 1; i <= 64 * 54; i++) {
            this.put(String.valueOf(i), new CustomItemStack(Material.TARGET, TextUtil.colorRandomString("数量限制") + "§7" + TextUtil.colorRandomString(String.valueOf(i))));
        }
    }});

    public static final BlockStorageIconHelper INPUT_HELPER = BlockStorageIconHelper.newInstanceOrGet(BlockStorageHelper.ID_CARGO, KEY, new LinkedHashMap<>() {{
        for(int i = 1; i <= 64 * 54; i++) {
            this.put(String.valueOf(i), new CustomItemStack(Material.TARGET, TextUtil.colorRandomString("数量限制") + "§7" + TextUtil.colorRandomString(String.valueOf(i))));
        }
    }});

    public static final BlockStorageIconHelper OUTPUT_HELPER = BlockStorageIconHelper.newInstanceOrGet(BlockStorageHelper.ID_CARGO, KEY, new LinkedHashMap<>() {{
        for(int i = 1; i <= 64 * 54; i++) {
            this.put(String.valueOf(i), new CustomItemStack(Material.TARGET, TextUtil.colorRandomString("数量限制") + "§7" + TextUtil.colorRandomString(String.valueOf(i))));
        }
    }});

    public static final BlockStorageLoreHelper H = new BlockStorageLoreHelper(BlockStorageHelper.ID_CARGO, 0, new LinkedHashMap<>() {{
        this.put("0", List.of("number no limit"));
        for(int i = 1; i < 64 * 9; i++) {
            this.put(String.valueOf(i), List.of("number limit: " + i));
        }
    }}) {
        @Nonnull
        @Override
        public String getKey() {
            return KEY;
        }

        @Nonnull
        @Override
        public String defaultValue() {
            return "64";
        }

        @Override
        public String clickNextValue(@Nonnull String value, @Nonnull ClickAction clickAction) {
            if(!clickAction.isShiftClicked() && !clickAction.isRightClicked()) {
                return H.nextOrDefaultValue(value);
            } else if(clickAction.isShiftClicked() && !clickAction.isRightClicked()) {
                return H.offsetOrDefaultValue(value, 64);
            } else {
                return H.offsetOrDefaultValue(value, 8);
            }
        }

        @Override
        public String clickPreviousValue(@Nonnull String value, @Nonnull ClickAction clickAction) {
            if(!clickAction.isShiftClicked() && !clickAction.isRightClicked()) {
                return H.previousOrDefaultValue(value);
            } else if(clickAction.isShiftClicked() && !clickAction.isRightClicked()) {
                return H.offsetOrDefaultValue(value, -64);
            } else {
                return H.offsetOrDefaultValue(value, -8);
            }
        }
    };

    public static final BlockStorageLoreHelper INPUT_H = new BlockStorageLoreHelper(BlockStorageHelper.ID_CARGO, 0, new LinkedHashMap<>() {{
        this.put("0", List.of("number no limit"));
        for(int i = 1; i < 64 * 9; i++) {
            this.put(String.valueOf(i), List.of("number limit: " + i));
        }
    }}) {
        @Nonnull
        @Override
        public String getKey() {
            return KEY_INPUT;
        }

        @Nonnull
        @Override
        public String defaultValue() {
            return "64";
        }

        @Override
        public String clickNextValue(@Nonnull String value, @Nonnull ClickAction clickAction) {
            if(!clickAction.isShiftClicked() && !clickAction.isRightClicked()) {
                return H.nextOrDefaultValue(value);
            } else if(clickAction.isShiftClicked() && !clickAction.isRightClicked()) {
                return H.offsetOrDefaultValue(value, 64);
            } else {
                return H.offsetOrDefaultValue(value, 8);
            }
        }

        @Override
        public String clickPreviousValue(@Nonnull String value, @Nonnull ClickAction clickAction) {
            if(!clickAction.isShiftClicked() && !clickAction.isRightClicked()) {
                return H.previousOrDefaultValue(value);
            } else if(clickAction.isShiftClicked() && !clickAction.isRightClicked()) {
                return H.offsetOrDefaultValue(value, -64);
            } else {
                return H.offsetOrDefaultValue(value, -8);
            }
        }
    };

    public static final BlockStorageLoreHelper OUTPUT_H = new BlockStorageLoreHelper(BlockStorageHelper.ID_CARGO, 0, new LinkedHashMap<>() {{
        this.put("0", List.of("number no limit"));
        for(int i = 1; i < 64 * 9; i++) {
            this.put(String.valueOf(i), List.of("number limit: " + i));
        }
    }}) {
        @Nonnull
        @Override
        public String getKey() {
            return KEY_OUTPUT;
        }

        @Nonnull
        @Override
        public String defaultValue() {
            return "64";
        }

        @Override
        public String clickNextValue(@Nonnull String value, @Nonnull ClickAction clickAction) {
            if(!clickAction.isShiftClicked() && !clickAction.isRightClicked()) {
                return H.nextOrDefaultValue(value);
            } else if(clickAction.isShiftClicked() && !clickAction.isRightClicked()) {
                return H.offsetOrDefaultValue(value, 64);
            } else {
                return H.offsetOrDefaultValue(value, 8);
            }
        }

        @Override
        public String clickPreviousValue(@Nonnull String value, @Nonnull ClickAction clickAction) {
            if(!clickAction.isShiftClicked() && !clickAction.isRightClicked()) {
                return H.previousOrDefaultValue(value);
            } else if(clickAction.isShiftClicked() && !clickAction.isRightClicked()) {
                return H.offsetOrDefaultValue(value, -64);
            } else {
                return H.offsetOrDefaultValue(value, -8);
            }
        }
    };





















    @Deprecated
    public static final void setIcon(ItemStack item, int amount) {
        if (item == null) {
            return;
        }
        if (amount <= item.getMaxStackSize()) {
            item.setAmount(amount);
        } else {
            item.setAmount(item.getMaxStackSize());
        }
        ItemMeta itemMeta = item.getItemMeta();
        List<String> lore = itemMeta.getLore();
        if (lore == null || lore.size() < 1) {
            lore = new ArrayList<>();
            lore.add(0, "§7当前数量限制: §f" + amount);
        }
        lore.set(0, "§7当前数量限制: §f" + amount);
        itemMeta.setLore(lore);
        item.setItemMeta(itemMeta);
    }
}
