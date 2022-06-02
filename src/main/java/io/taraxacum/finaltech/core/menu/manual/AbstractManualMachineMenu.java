package io.taraxacum.finaltech.core.menu.manual;

import io.taraxacum.finaltech.core.items.machine.AbstractMachine;
import io.taraxacum.finaltech.core.menu.AbstractMachineMenu;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import org.bukkit.block.Block;

import javax.annotation.Nonnull;

/**
 * @author Final_ROOT
 * @since 2.0
 */
public abstract class AbstractManualMachineMenu extends AbstractMachineMenu {
    public AbstractManualMachineMenu(@Nonnull AbstractMachine machine) {
        super(machine);
    }

    @Override
    public abstract void updateMenu(@Nonnull BlockMenu blockMenu, @Nonnull Block block);
}
