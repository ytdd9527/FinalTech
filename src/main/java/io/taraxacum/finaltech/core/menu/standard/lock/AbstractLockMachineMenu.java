package io.taraxacum.finaltech.core.menu.standard.lock;

import io.github.thebusybiscuit.slimefun4.utils.ChestMenuUtils;
import io.taraxacum.finaltech.core.items.machine.AbstractMachine;
import io.taraxacum.finaltech.core.menu.standard.AbstractStandardMachineMenu;
import io.taraxacum.finaltech.core.helper.MachineRecipeLock;
import me.mrCookieSlime.Slimefun.api.BlockStorage;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import org.bukkit.block.Block;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;

/**
 * @author Final_ROOT
 * @since 2.0
 */
public abstract class AbstractLockMachineMenu extends AbstractStandardMachineMenu {
    public static final int RECIPE_LOCK_SLOT = 4;

    public AbstractLockMachineMenu(@Nonnull AbstractMachine abstractMachine) {
        super(abstractMachine);
    }

    @Override
    public void init() {
        super.init();
        this.addItem(this.getRecipeLockSlot(), MachineRecipeLock.ICON);
        this.addMenuClickHandler(this.getRecipeLockSlot(), ChestMenuUtils.getEmptyClickHandler());
    }

    @Override
    public void newInstance(@Nonnull BlockMenu blockMenu, @Nonnull Block block) {
        super.newInstance(blockMenu, block);
        blockMenu.addMenuClickHandler(this.getRecipeLockSlot(), MachineRecipeLock.HELPER.getHandler(blockMenu, block, this, this.getRecipeLockSlot()));
    }

    @Override
    protected void updateMenu(@Nonnull BlockMenu blockMenu, @Nonnull Block block) {
        super.updateMenu(blockMenu, block);
        MachineRecipeLock.HELPER.checkOrSetBlockStorage(block.getLocation());
        ItemStack item = blockMenu.getItemInSlot(this.getRecipeLockSlot());
        String recipeLock = BlockStorage.getLocationInfo(block.getLocation(), MachineRecipeLock.KEY);
        MachineRecipeLock.HELPER.setIcon(item, recipeLock, this.getMachine());
    }

    public int getRecipeLockSlot() {
        return RECIPE_LOCK_SLOT;
    }
}
