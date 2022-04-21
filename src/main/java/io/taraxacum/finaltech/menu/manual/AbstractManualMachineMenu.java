package io.taraxacum.finaltech.menu.manual;

import io.taraxacum.finaltech.machine.AbstractMachine;
import io.taraxacum.finaltech.menu.AbstractMachineMenu;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import org.bukkit.block.Block;

import javax.annotation.Nonnull;

/**
 * @author Final_ROOT
 */
public abstract class AbstractManualMachineMenu extends AbstractMachineMenu {
    public AbstractManualMachineMenu(@Nonnull AbstractMachine machine) {
        super(machine);
    }

    /**
     * Update the menu
     * May be used in some specific machine
     *
     * @param blockMenu
     * @param block
     */
    @Override
    public abstract void updateMenu(@Nonnull BlockMenu blockMenu, @Nonnull Block block);
}
