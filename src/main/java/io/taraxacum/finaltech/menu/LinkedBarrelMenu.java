package io.taraxacum.finaltech.menu;

import io.github.thebusybiscuit.slimefun4.implementation.Slimefun;
import io.github.thebusybiscuit.slimefun4.libraries.dough.protection.Interaction;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenuPreset;
import me.mrCookieSlime.Slimefun.api.item_transport.ItemTransportFlow;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import javax.annotation.Nonnull;

public class LinkedBarrelMenu extends BlockMenuPreset {
    public static final int[] CONTAIN = new int[]{
            0,  1,  2,  3,  4,  5,  6,  7,  8,
            9, 10, 11, 12, 13, 14, 15, 16, 17,
            18, 19, 20, 21, 22, 23, 24, 25, 26,
            27, 28, 29, 30, 31, 32, 33, 34, 35,
            36, 37, 38, 39, 40, 41, 42, 43, 44,
            45, 46, 47, 48, 49, 50, 51, 52, 53
    };

    public static final int[] INPUTS = new int[]{
            0,  1,  2,  3,  4,  5,  6,  7,  8,
            9, 10, 11, 12, 13, 14, 15, 16, 17,
            18, 19, 20, 21, 22, 23, 24, 25, 26
    };
    public static final int[] OUTPUTS = new int[]{
            27, 28, 29, 30, 31, 32, 33, 34, 35,
            36, 37, 38, 39, 40, 41, 42, 43, 44,
            45, 46, 47, 48, 49, 50, 51, 52, 53
    };

    public LinkedBarrelMenu(@Nonnull String id, @Nonnull String title) {
        super(id, title);
    }

    @Override
    public void init() {
        this.setSize(54);
    }

    @Override
    public boolean canOpen(@Nonnull Block block, @Nonnull Player player) {
        return Slimefun.getProtectionManager().hasPermission(player, block.getLocation(), Interaction.INTERACT_BLOCK);
    }

    @Override
    public int[] getSlotsAccessedByItemTransport(ItemTransportFlow itemTransportFlow) {
        if(itemTransportFlow == null) {
            return new int[0];
        }
        switch (itemTransportFlow) {
            case INSERT:
                return INPUTS;
            case WITHDRAW:
                return OUTPUTS;
            default:
                return CONTAIN;
        }
    }
}
