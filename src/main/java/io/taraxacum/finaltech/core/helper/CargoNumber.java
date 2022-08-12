package io.taraxacum.finaltech.core.helper;

import io.github.thebusybiscuit.slimefun4.libraries.dough.items.CustomItemStack;
import io.taraxacum.finaltech.FinalTech;
import io.taraxacum.finaltech.api.factory.BlockStorageHelper;
import io.taraxacum.finaltech.api.factory.BlockStorageLoreHelper;
import io.taraxacum.finaltech.util.TextUtil;
import me.mrCookieSlime.CSCoreLibPlugin.general.Inventory.ClickAction;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * @author Final_ROOT
 */
public final class CargoNumber {
    public static final String KEY = "cb";
    public static final String KEY_INPUT = "cbi";
    public static final String KEY_OUTPUT = "cbo";

    public static final ItemStack CARGO_NUMBER_ICON = new CustomItemStack(Material.TARGET, FinalTech.getLanguageString("helper", "cargo-number", "icon", "name"), FinalTech.getLanguageStringArray("helper", "cargo-number", "icon", "lore"));
    public static final ItemStack CARGO_NUMBER_ADD_ICON = new CustomItemStack(Material.GREEN_CONCRETE, FinalTech.getLanguageString("helper", "cargo-number", "add-icon", "name"), FinalTech.getLanguageStringArray("helper", "cargo-number", "add-icon", "lore"));
    public static final ItemStack CARGO_NUMBER_SUB_ICON = new CustomItemStack(Material.RED_CONCRETE, FinalTech.getLanguageString("helper", "cargo-number", "sub-icon", "name"), FinalTech.getLanguageStringArray("helper", "cargo-number", "sub-icon", "lore"));

    public static final BlockStorageLoreHelper HELPER = new BlockStorageLoreHelper(BlockStorageHelper.ID_CARGO, 0, new LinkedHashMap<>() {{
//        this.put("0", List.of("number no limit"));
        for (int i = 1; i <= 64 * 9; i++) {
            this.put(String.valueOf(i), FinalTech.getLanguageManager().replaceStringList(FinalTech.getLanguageStringList("helper", "cargo-number", "icon", "lore0"), String.valueOf(i)));
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

        @Nonnull
        @Override
        public String clickNextValue(@Nullable String value, @Nonnull ClickAction clickAction) {
            if (!clickAction.isShiftClicked() && !clickAction.isRightClicked()) {
                return HELPER.nextOrDefaultValue(value);
            } else if (clickAction.isShiftClicked() && !clickAction.isRightClicked()) {
                return HELPER.offsetOrDefaultValue(value, 64);
            } else {
                return HELPER.offsetOrDefaultValue(value, 8);
            }
        }

        @Nonnull
        @Override
        public String clickPreviousValue(@Nullable String value, @Nonnull ClickAction clickAction) {
            if (!clickAction.isShiftClicked() && !clickAction.isRightClicked()) {
                return HELPER.previousOrDefaultValue(value);
            } else if (clickAction.isShiftClicked() && !clickAction.isRightClicked()) {
                return HELPER.offsetOrDefaultValue(value, -64);
            } else {
                return HELPER.offsetOrDefaultValue(value, -8);
            }
        }
    };
    public static final BlockStorageLoreHelper INPUT_HELPER = new BlockStorageLoreHelper(BlockStorageHelper.ID_CARGO, 0, new LinkedHashMap<>() {{
//        this.put("0", List.of("number no limit"));
        for (int i = 1; i <= 64 * 9; i++) {
            this.put(String.valueOf(i), FinalTech.getLanguageManager().replaceStringList(FinalTech.getLanguageStringList("helper", "cargo-number", "icon", "lore0"), String.valueOf(i)));
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

        @Nonnull
        @Override
        public String clickNextValue(@Nullable String value, @Nonnull ClickAction clickAction) {
            if (!clickAction.isShiftClicked() && !clickAction.isRightClicked()) {
                return INPUT_HELPER.nextOrDefaultValue(value);
            } else if (clickAction.isShiftClicked() && !clickAction.isRightClicked()) {
                return INPUT_HELPER.offsetOrDefaultValue(value, 64);
            } else {
                return INPUT_HELPER.offsetOrDefaultValue(value, 8);
            }
        }

        @Nonnull
        @Override
        public String clickPreviousValue(@Nullable String value, @Nonnull ClickAction clickAction) {
            if (!clickAction.isShiftClicked() && !clickAction.isRightClicked()) {
                return INPUT_HELPER.previousOrDefaultValue(value);
            } else if (clickAction.isShiftClicked() && !clickAction.isRightClicked()) {
                return INPUT_HELPER.offsetOrDefaultValue(value, -64);
            } else {
                return INPUT_HELPER.offsetOrDefaultValue(value, -8);
            }
        }
    };
    public static final BlockStorageLoreHelper OUTPUT_HELPER = new BlockStorageLoreHelper(BlockStorageHelper.ID_CARGO, 0, new LinkedHashMap<>() {{
//        this.put("0", List.of("number no limit"));
        for (int i = 1; i <= 64 * 9; i++) {
            this.put(String.valueOf(i), FinalTech.getLanguageManager().replaceStringList(FinalTech.getLanguageStringList("helper", "cargo-number", "icon", "lore0"), String.valueOf(i)));
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

        @Nonnull
        @Override
        public String clickNextValue(@Nullable String value, @Nonnull ClickAction clickAction) {
            if (!clickAction.isShiftClicked() && !clickAction.isRightClicked()) {
                return OUTPUT_HELPER.nextOrDefaultValue(value);
            } else if (clickAction.isShiftClicked() && !clickAction.isRightClicked()) {
                return OUTPUT_HELPER.offsetOrDefaultValue(value, 64);
            } else {
                return OUTPUT_HELPER.offsetOrDefaultValue(value, 8);
            }
        }

        @Nonnull
        @Override
        public String clickPreviousValue(@Nullable String value, @Nonnull ClickAction clickAction) {
            if (!clickAction.isShiftClicked() && !clickAction.isRightClicked()) {
                return OUTPUT_HELPER.previousOrDefaultValue(value);
            } else if (clickAction.isShiftClicked() && !clickAction.isRightClicked()) {
                return OUTPUT_HELPER.offsetOrDefaultValue(value, -64);
            } else {
                return OUTPUT_HELPER.offsetOrDefaultValue(value, -8);
            }
        }
    };
}
