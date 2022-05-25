package io.taraxacum.finaltech.core.menu.standard.lock;

import io.github.thebusybiscuit.slimefun4.utils.ChestMenuUtils;
import io.taraxacum.finaltech.core.items.machine.AbstractMachine;
import io.taraxacum.finaltech.core.menu.standard.AbstractStandardMachineMenu;
import io.taraxacum.finaltech.core.storage.MachineRecipeLock;
import me.mrCookieSlime.Slimefun.api.BlockStorage;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import org.bukkit.block.Block;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;

/**
 * @author Final_ROOT
 */
public abstract class AbstractLockMachineMenu extends AbstractStandardMachineMenu {
    public static final int RECIPE_LOCK_SLOT = 4;

    public AbstractLockMachineMenu(@Nonnull AbstractMachine abstractMachine) {
        super(abstractMachine);
    }

    @Override
    public void init() {
        super.init();
        this.addItem(RECIPE_LOCK_SLOT, MachineRecipeLock.ICON);
        this.addMenuClickHandler(RECIPE_LOCK_SLOT, ChestMenuUtils.getEmptyClickHandler());
    }

    @Override
    public void newInstance(@Nonnull BlockMenu blockMenu, @Nonnull Block block) {
        super.newInstance(blockMenu, block);
        blockMenu.addMenuClickHandler(RECIPE_LOCK_SLOT, MachineRecipeLock.getHandler(blockMenu, block, this, RECIPE_LOCK_SLOT));
    }

    @Override
    protected void updateMenu(@Nonnull BlockMenu blockMenu, @Nonnull Block block) {
        super.updateMenu(blockMenu, block);
        MachineRecipeLock.HELPER.checkOrSetBlockStorage(block.getLocation());
        ItemStack item = blockMenu.getItemInSlot(RECIPE_LOCK_SLOT);
        String  recipeLock = BlockStorage.getLocationInfo(block.getLocation(), MachineRecipeLock.KEY);
        MachineRecipeLock.HELPER.setIcon(item, recipeLock, this.getMachine());
    }
}
