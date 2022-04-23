package io.taraxacum.finaltech.menu.standard.lock;

import io.github.thebusybiscuit.slimefun4.utils.ChestMenuUtils;
import io.taraxacum.finaltech.machine.AbstractMachine;
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
        blockMenu.addMenuClickHandler(RECIPE_LOCK_SLOT, ((player, i, itemStack, clickAction) -> {
            Config config = BlockStorage.getLocationInfo(block.getLocation());
            if(!config.contains(RecipeLockHelper.KEY)) {
                config.setValue(RecipeLockHelper.KEY, RecipeLockHelper.VALUE_UNLOCK);
            }
            if(!clickAction.isRightClicked()) {
                ItemStackUtil.setLore(blockMenu.getItemInSlot(RECIPE_LOCK_SLOT), "§8未锁定");
                BlockStorage.addBlockInfo(block.getLocation(), RecipeLockHelper.KEY, RecipeLockHelper.VALUE_UNLOCK);
            } else {
                ItemStackUtil.setLore(blockMenu.getItemInSlot(RECIPE_LOCK_SLOT), "§8禁用锁定");
                BlockStorage.addBlockInfo(block.getLocation(), RecipeLockHelper.KEY, RecipeLockHelper.VALUE_LOCK_OFF);
            }
            return false;
        }));
    }

    @Override
    protected void updateMenu(@Nonnull BlockMenu blockMenu, @Nonnull Block block) {
        super.updateMenu(blockMenu, block);
        Config config = BlockStorage.getLocationInfo(block.getLocation());
        if (config.getValue(RecipeLockHelper.KEY) == null) {
            config.setValue(RecipeLockHelper.KEY, RecipeLockHelper.VALUE_LOCK_OFF);
        }
        ItemStack item = blockMenu.getItemInSlot(RECIPE_LOCK_SLOT);
        int recipeLock = Integer.parseInt(config.getString(RecipeLockHelper.KEY));
        RecipeLockHelper.setIcon(item, recipeLock, this.getMachine());
    }
}
