package io.taraxacum.finaltech.core.menu.clicker;

import io.taraxacum.common.util.StringNumberUtil;
import io.taraxacum.finaltech.core.item.machine.AbstractMachine;
import io.taraxacum.finaltech.core.item.machine.clicker.AbstractClickerMachine;
import io.taraxacum.finaltech.core.menu.AbstractMachineMenu;
import me.mrCookieSlime.Slimefun.api.BlockStorage;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import javax.annotation.Nonnull;

/**
 * @author Final_ROOT
 * @since 2.2
 */
public abstract class AbstractAccessorMenu extends AbstractMachineMenu {
    public AbstractAccessorMenu(@Nonnull AbstractMachine machine) {
        super(machine);
    }

    @Override
    public void newInstance(@Nonnull BlockMenu blockMenu, @Nonnull Block block) {
        super.newInstance(blockMenu, block);

        blockMenu.addMenuOpeningHandler(p -> {
            Location location = block.getLocation();
            String value = BlockStorage.getLocationInfo(location, AbstractClickerMachine.KEY);
            if (value == null) {
                value = AbstractClickerMachine.THRESHOLD;
            }
            if (StringNumberUtil.compare(value, StringNumberUtil.ZERO) <= 0) {
                p.closeInventory();
                // TODO: waring info in console
                return;
            }
            BlockStorage.addBlockInfo(block, AbstractClickerMachine.KEY, StringNumberUtil.sub(value));

            if(p.getPlayer() != null) {
                AbstractAccessorMenu.this.doFunction(blockMenu, block, p.getPlayer());
            }
        });
    }

    abstract protected void doFunction(@Nonnull BlockMenu blockMenu, @Nonnull Block block, @Nonnull Player player);
}
