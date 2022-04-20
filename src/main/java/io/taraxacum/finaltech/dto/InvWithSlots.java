package io.taraxacum.finaltech.dto;

import org.bukkit.inventory.Inventory;

/**
 * @author Final_ROOT
 */
public class InvWithSlots {
    private Inventory inventory;
    private int[] slots;

    public InvWithSlots(Inventory inventory, int[] slots) {
        this.inventory = inventory;
        this.slots = slots;
    }

    public Inventory getInventory() {
        return inventory;
    }

    public int[] getSlots() {
        return slots;
    }
}
