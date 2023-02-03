package io.taraxacum.finaltech.core.item.machine.manual;

import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.core.handlers.BlockBreakHandler;
import io.github.thebusybiscuit.slimefun4.core.handlers.BlockPlaceHandler;
import io.taraxacum.common.util.StringNumberUtil;
import io.taraxacum.finaltech.core.menu.manual.AbstractManualMachineMenu;
import io.taraxacum.libs.slimefun.dto.RecipeTypeRegistry;
import io.taraxacum.finaltech.core.interfaces.RecipeItem;
import io.taraxacum.finaltech.core.menu.machine.ItemDismantleTableMenu;
import io.taraxacum.libs.plugin.util.ItemStackUtil;
import io.taraxacum.finaltech.util.MachineUtil;
import io.taraxacum.finaltech.util.ConfigUtil;
import io.taraxacum.libs.slimefun.util.SfItemUtil;
import me.mrCookieSlime.CSCoreLibPlugin.Configuration.Config;
import me.mrCookieSlime.Slimefun.api.BlockStorage;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import org.bukkit.block.Block;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;
import java.util.*;

/**
 * @author Final_ROOT
 * @since 2.0
 */
public class ItemDismantleTable extends AbstractManualMachine implements RecipeItem {
    public static final Set<String> ALLOWED_RECIPE_TYPE = new HashSet<>(ConfigUtil.getItemStringList(SfItemUtil.getIdFormatName(ItemDismantleTable.class), "allowed-recipe-type"));
    public static final Set<String> NOT_ALLOWED_ID = new HashSet<>(ConfigUtil.getItemStringList(SfItemUtil.getIdFormatName(ItemDismantleTable.class), "not-allowed-id"));

    public static final String KEY = "c";
    public static final String COUNT = ConfigUtil.getOrDefaultItemSetting("600", SfItemUtil.getIdFormatName(ItemDismantleTable.class), "count");
    public static final String LIMIT = ConfigUtil.getOrDefaultItemSetting("900", SfItemUtil.getIdFormatName(ItemDismantleTable.class), "limit");

    public ItemDismantleTable(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
        super(itemGroup, item, recipeType, recipe);
    }

    @Nonnull
    @Override
    protected BlockPlaceHandler onBlockPlace() {
        return MachineUtil.BLOCK_PLACE_HANDLER_PLACER_DENY;
    }

    @Nonnull
    @Override
    protected BlockBreakHandler onBlockBreak() {
        return MachineUtil.simpleBlockBreakerHandler(this);
    }

    @Nonnull
    @Override
    protected AbstractManualMachineMenu newMachineMenu() {
        return new ItemDismantleTableMenu(this);
    }

    @Override
    protected void tick(@Nonnull Block block, @Nonnull SlimefunItem slimefunItem, @Nonnull Config config) {
        String count = config.contains(KEY) ? config.getString(KEY) : StringNumberUtil.ZERO;
        if(StringNumberUtil.compare(count, LIMIT) < 0) {
            config.setValue(KEY, StringNumberUtil.add(count));
        }

        BlockMenu blockMenu = BlockStorage.getInventory(block);
        if(blockMenu != null && blockMenu.hasViewer()) {
            this.getMachineMenu().updateInventory(blockMenu.toInventory(), block.getLocation());
        }
    }

    @Override
    protected boolean isSynchronized() {
        return false;
    }

    @Override
    public void registerDefaultRecipes() {
        RecipeTypeRegistry.getInstance().reload();

        for (String id : ALLOWED_RECIPE_TYPE) {
            RecipeType recipeType = RecipeTypeRegistry.getInstance().getRecipeTypeById(id);
            if (recipeType != null && !ItemStackUtil.isItemNull(recipeType.toItem())) {
                this.registerDescriptiveRecipe(recipeType.toItem());
            }
        }
    }
}
