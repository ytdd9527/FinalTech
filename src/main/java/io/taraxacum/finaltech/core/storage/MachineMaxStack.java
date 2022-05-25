package io.taraxacum.finaltech.core.storage;

import io.github.thebusybiscuit.slimefun4.libraries.dough.items.CustomItemStack;
import io.taraxacum.finaltech.core.factory.BlockStorageHelper;
import io.taraxacum.finaltech.core.factory.BlockStorageLoreHelper;
import io.taraxacum.finaltech.core.menu.AbstractMachineMenu;
import me.mrCookieSlime.CSCoreLibPlugin.general.Inventory.ChestMenu;
import me.mrCookieSlime.Slimefun.api.BlockStorage;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @author Final_ROOT
 */
public final class MachineMaxStack {
    public static final String KEY = "mms";

    public static final ItemStack ICON = new CustomItemStack(Material.CHEST, "&7输入数量限制", "&7未限制");

    public static final BlockStorageLoreHelper HELPER = new BlockStorageLoreHelper(BlockStorageHelper.ID_CARGO, new LinkedHashMap<>() {{
        this.put("0", List.of("§7未限制"));
        for (int i = 1; i <= 54; i++) {
            this.put(String.valueOf(i), List.of("§7限制数量= " + i));
        }
    }}) {
        @Nonnull
        @Override
        public String getKey() {
            return KEY;
        }

        @Override
        public boolean setIcon(@Nonnull ItemStack iconItem, @Nullable String value) {
            if (Objects.equals(this.defaultValue(), value)) {
                iconItem.setType(Material.CHEST);
                iconItem.setAmount(1);
            } else if (value != null) {
                iconItem.setType(Material.HOPPER);
                iconItem.setAmount(Integer.parseInt(value));
            } else {
                return false;
            }
            return super.setIcon(iconItem, value);
        }

        @Nonnull
        @Override
        public String nextOrDefaultValue(@Nullable String value) {
            return this.defaultValue();
        }

        @Nonnull
        @Override
        public String previousOrDefaultValue(@Nullable String value) {
            return this.defaultValue();
        }

        @Nonnull
        @Override
        public ChestMenu.MenuClickHandler getHandler(@Nonnull BlockMenu blockMenu, @Nonnull Block block, @Nonnull AbstractMachineMenu abstractMachineMenu, int slot) {
            return (player, i, itemStack, clickAction) -> {
                int quantity = Integer.parseInt(BlockStorage.getLocationInfo(block.getLocation(), MachineMaxStack.KEY));
                if (clickAction.isShiftClicked()) {
                    quantity = 0;
                } else {
                    if (clickAction.isRightClicked()) {
                        quantity = (quantity - 1) % (abstractMachineMenu.getInputSlot().length + 1);
                    } else {
                        quantity = (quantity + 1) % (abstractMachineMenu.getInputSlot().length + 1);
                    }
                }
                MachineMaxStack.HELPER.setIcon(blockMenu.getItemInSlot(slot), String.valueOf(quantity));
                BlockStorage.addBlockInfo(block, MachineMaxStack.KEY, String.valueOf(quantity));
                return false;
            };
        }
    };
}
