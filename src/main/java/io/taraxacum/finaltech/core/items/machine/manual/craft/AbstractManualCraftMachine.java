package io.taraxacum.finaltech.core.items.machine.manual.craft;

import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.core.handlers.BlockBreakHandler;
import io.github.thebusybiscuit.slimefun4.core.handlers.BlockPlaceHandler;
import io.github.thebusybiscuit.slimefun4.implementation.Slimefun;
import io.taraxacum.finaltech.core.items.machine.manual.AbstractManualMachine;
import io.taraxacum.finaltech.api.interfaces.RecipeItem;
import io.taraxacum.finaltech.core.menu.manual.AbstractManualMachineMenu;
import io.taraxacum.finaltech.core.menu.manual.ManualCraftMachineMenu;
import io.taraxacum.finaltech.util.LocationUtil;
import io.taraxacum.finaltech.util.MachineUtil;
import me.mrCookieSlime.CSCoreLibPlugin.Configuration.Config;
import me.mrCookieSlime.Slimefun.api.BlockStorage;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.HumanEntity;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;

/**
 * @author Final_ROOT
 * @since 1.0
 */
public abstract class AbstractManualCraftMachine extends AbstractManualMachine implements RecipeItem {
    public static final String KEY_COUNT = "count";
    public static int COUNT_THRESHOLD = 40;

    public AbstractManualCraftMachine(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
        super(itemGroup, item, recipeType, recipe);
    }

    @Nonnull
    @Override
    protected BlockBreakHandler onBlockBreak() {
        return MachineUtil.simpleBlockBreakerHandler(this);
    }

    @Nonnull
    @Override
    protected BlockPlaceHandler onBlockPlace() {
        return new BlockPlaceHandler(false) {
            @Override
            public void onPlayerPlace(@Nonnull BlockPlaceEvent blockPlaceEvent) {
                BlockStorage.addBlockInfo(blockPlaceEvent.getBlock().getLocation(), ManualCraftMachineMenu.KEY, "0");
            }
        };
    }

    @Nonnull
    @Override
    protected AbstractManualMachineMenu newMachineMenu() {
        return new ManualCraftMachineMenu(this);
    }

    @Override
    protected void tick(@Nonnull Block block, @Nonnull SlimefunItem slimefunItem, @Nonnull Config config) {
        BlockMenu blockMenu = BlockStorage.getInventory(block);
        if (blockMenu.hasViewer()) {
            this.getMachineMenu().updateMenu(blockMenu, block);
        }
        int count = Integer.parseInt(LocationUtil.getNonNullStringNumber(config, AbstractManualCraftMachine.KEY_COUNT));
        if(count == 0) {
            return;
        }
        count -= Slimefun.getTickerTask().getTickRate() / 2;
        count = Math.max(count, 0);
        config.setValue(KEY_COUNT, String.valueOf(count));
        if(count > COUNT_THRESHOLD) {
            Location location = block.getLocation();
            this.getAddon().getJavaPlugin().getServer().getLogger().warning("[" + this.getAddon().getJavaPlugin().getName() + "]§c位于 " + location.getWorld().getName() + "(" + location.getBlockX() + "," + location.getBlockY() + "," + location.getBlockZ() + ")处的粘液科技机器 " + this.getItemName() + " §c正在被玩家高速点击，相关的玩家包括：");
            for (HumanEntity humanEntity : blockMenu.toInventory().getViewers()) {
                this.getAddon().getJavaPlugin().getServer().getLogger().warning("    " + humanEntity.getName());
            }
            if(!blockMenu.hasViewer()) {
            }
            this.getAddon().getJavaPlugin().getServer().getLogger().warning("§c请确定这是否是由玩家网络卡顿造成，如果不是，可能是其正尝试恶意卡服");
        }
    }
}
