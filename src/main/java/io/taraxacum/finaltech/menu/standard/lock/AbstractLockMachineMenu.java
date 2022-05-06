package io.taraxacum.finaltech.menu.standard.lock;

import io.github.thebusybiscuit.slimefun4.utils.ChestMenuUtils;
import io.taraxacum.finaltech.items.machine.AbstractMachine;
import io.taraxacum.finaltech.menu.standard.AbstractStandardMachineMenu;
import io.taraxacum.finaltech.util.ItemStackUtil;
import io.taraxacum.finaltech.util.menu.RecipeLockHelper;
import me.mrCookieSlime.CSCoreLibPlugin.Configuration.Config;
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
        this.addItem(RECIPE_LOCK_SLOT, RecipeLockHelper.ICON);
        this.addMenuClickHandler(RECIPE_LOCK_SLOT, ChestMenuUtils.getEmptyClickHandler());
    }

    @Override
    public void newInstance(@Nonnull BlockMenu blockMenu, @Nonnull Block block) {
        super.newInstance(blockMenu, block);
        blockMenu.addMenuClickHandler(RECIPE_LOCK_SLOT, RecipeLockHelper.getHandler(blockMenu, block, this, RECIPE_LOCK_SLOT));
    }

    @Override
    protected void updateMenu(@Nonnull BlockMenu blockMenu, @Nonnull Block block) {
        super.updateMenu(blockMenu, block);
        RecipeLockHelper.HELPER.checkOrSetBlockStorage(block.getLocation());
        ItemStack item = blockMenu.getItemInSlot(RECIPE_LOCK_SLOT);
        String  recipeLock = BlockStorage.getLocationInfo(block.getLocation(), RecipeLockHelper.KEY);
        RecipeLockHelper.HELPER.setIcon(item, recipeLock, this.getMachine());
    }
}
