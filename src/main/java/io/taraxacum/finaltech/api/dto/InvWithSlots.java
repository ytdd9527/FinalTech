package io.taraxacum.finaltech.api.dto;

import org.bukkit.inventory.Inventory;

/**
 * @author Final_ROOT
 * @since 1.0
 */
public record InvWithSlots(Inventory inventory, int[] slots) {
    public Inventory getInventory() {
        return inventory;
    }

    public int[] getSlots() {
        return slots;
    }
}
