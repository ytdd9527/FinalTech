package io.taraxacum.finaltech.core.helper;

import io.github.thebusybiscuit.slimefun4.libraries.dough.items.CustomItemStack;
import io.taraxacum.finaltech.core.factory.BlockStorageHelper;
import io.taraxacum.finaltech.core.factory.BlockStorageLoreHelper;
import io.taraxacum.finaltech.util.TextUtil;
import me.mrCookieSlime.CSCoreLibPlugin.general.Inventory.ClickAction;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * @author Final_ROOT
 */
public final class CargoNumber {
    public static final String KEY = "cb";
    public static final String KEY_INPUT = "cbi";
    public static final String KEY_OUTPUT = "cbo";

    public static final ItemStack CARGO_NUMBER_ICON = new CustomItemStack(Material.TARGET, TextUtil.colorPseudorandomString("传输数量限制"));
    public static final ItemStack CARGO_NUMBER_ADD_ICON = new CustomItemStack(Material.GREEN_CONCRETE, TextUtil.colorPseudorandomString("增加数量限制"));
    public static final ItemStack CARGO_NUMBER_SUB_ICON = new CustomItemStack(Material.RED_CONCRETE, TextUtil.colorPseudorandomString("减少数量限制"));

    public static final BlockStorageLoreHelper HELPER = new BlockStorageLoreHelper(BlockStorageHelper.ID_CARGO, 0, new LinkedHashMap<>() {{
//        this.put("0", List.of("number no limit"));
        for (int i = 1; i <= 64 * 9; i++) {
            this.put(String.valueOf(i), List.of(TextUtil.colorRandomString("数量限制 " + i)));
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
            if (!clickAction.isShiftClicked() && !clickAction.isRightClicked()) {
                return HELPER.nextOrDefaultValue(value);
            } else if (clickAction.isShiftClicked() && !clickAction.isRightClicked()) {
                return HELPER.offsetOrDefaultValue(value, 64);
            } else {
                return HELPER.offsetOrDefaultValue(value, 8);
            }
        }

        @Override
        public String clickPreviousValue(@Nonnull String value, @Nonnull ClickAction clickAction) {
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
        for (int i = 1; i < 64 * 9; i++) {
            this.put(String.valueOf(i), List.of(TextUtil.colorRandomString("数量限制: " + i)));
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
            if (!clickAction.isShiftClicked() && !clickAction.isRightClicked()) {
                return INPUT_HELPER.nextOrDefaultValue(value);
            } else if (clickAction.isShiftClicked() && !clickAction.isRightClicked()) {
                return INPUT_HELPER.offsetOrDefaultValue(value, 64);
            } else {
                return INPUT_HELPER.offsetOrDefaultValue(value, 8);
            }
        }

        @Override
        public String clickPreviousValue(@Nonnull String value, @Nonnull ClickAction clickAction) {
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
        this.put("0", List.of("number no limit"));
        for (int i = 1; i < 64 * 9; i++) {
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
            if (!clickAction.isShiftClicked() && !clickAction.isRightClicked()) {
                return OUTPUT_HELPER.nextOrDefaultValue(value);
            } else if (clickAction.isShiftClicked() && !clickAction.isRightClicked()) {
                return OUTPUT_HELPER.offsetOrDefaultValue(value, 64);
            } else {
                return OUTPUT_HELPER.offsetOrDefaultValue(value, 8);
            }
        }

        @Override
        public String clickPreviousValue(@Nonnull String value, @Nonnull ClickAction clickAction) {
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
