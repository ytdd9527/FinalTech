package io.taraxacum.finaltech.core.items.machine.standard;

import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.libraries.dough.inventory.InvUtils;
import io.github.thebusybiscuit.slimefun4.libraries.dough.items.CustomItemStack;
import io.taraxacum.finaltech.FinalTech;
import io.taraxacum.finaltech.api.operation.ItemSerializationConstructorOperation;
import io.taraxacum.finaltech.core.items.unusable.CopyCardItem;
import io.taraxacum.finaltech.core.items.unusable.Singularity;
import io.taraxacum.finaltech.core.items.unusable.Spirochete;
import io.taraxacum.finaltech.core.menu.standard.AbstractStandardMachineMenu;
import io.taraxacum.finaltech.api.operation.ItemCopyCardOperation;
import io.taraxacum.finaltech.core.menu.standard.ItemSerializationConstructorMenu;
import io.taraxacum.finaltech.setup.FinalTechItems;
import io.taraxacum.finaltech.util.ItemStackUtil;
import io.taraxacum.finaltech.util.TextUtil;
import me.mrCookieSlime.CSCoreLibPlugin.Configuration.Config;
import me.mrCookieSlime.Slimefun.api.BlockStorage;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;

/**
 * This is a slimefun machine
 * it will be used in gameplay
 * It's not a function class!
 * @author Final_ROOT
 * @since 1.0
 */
public class ItemSerializationConstructor extends AbstractStandardMachine {
    public static final CustomItemStack NULL_INFO_ICON = new CustomItemStack(Material.REDSTONE, TextUtil.COLOR_NORMAL + "完成进度", TextUtil.COLOR_NORMAL + "暂未输入物品");
    private static final String BLOCK_STORAGE_ITEM_KEY = "item";
    private static final String BLOCK_STORAGE_AMOUNT_KEY = "amount";

    public ItemSerializationConstructor(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
        super(itemGroup, item, recipeType, recipe);
    }

    @Nonnull
    @Override
    protected AbstractStandardMachineMenu setMachineMenu() {
        return new ItemSerializationConstructorMenu(this);
    }

    @Override
    protected void tick(@Nonnull Block block, @Nonnull SlimefunItem slimefunItem, @Nonnull Config config) {
        BlockMenu inv = BlockStorage.getInventory(block);

        ItemSerializationConstructorOperation operation = (ItemSerializationConstructorOperation) this.getMachineProcessor().getOperation(block);

        if (operation == null && config.contains(BLOCK_STORAGE_ITEM_KEY)) {
            String itemString = config.getString(BLOCK_STORAGE_ITEM_KEY);
            ItemStack stringItem = ItemStackUtil.stringToItemStack(itemString);
            if (!ItemStackUtil.isItemNull(stringItem) && ItemSerializationConstructorOperation.getType(stringItem) == ItemSerializationConstructorOperation.COPY_CARD) {
                operation = ItemSerializationConstructorOperation.newInstance(stringItem);
                if (operation != null) {
                    this.getMachineProcessor().startOperation(block, operation);
                    int amount = (int) Double.parseDouble(config.getString(BLOCK_STORAGE_AMOUNT_KEY));
                    ((ItemCopyCardOperation)operation).setCount(amount);
                }
            }
        }

        for (int slot : this.getInputSlot()) {
            ItemStack inputItem = inv.getItemInSlot(slot);
            if (ItemStackUtil.isItemNull(inputItem)) {
                continue;
            }
            if (operation == null) {
                operation = ItemSerializationConstructorOperation.newInstance(inputItem);
                if (operation != null) {
                    this.getMachineProcessor().startOperation(block, operation);
                }
            } else {
                operation.addItem(inputItem);
            }
        }

        if (operation != null && operation.isFinished() && InvUtils.fits(inv.toInventory(), operation.getResult(), this.getOutputSlot())) {
            inv.pushItem(operation.getResult(), this.getOutputSlot());
            this.getMachineProcessor().endOperation(block);
            operation = null;
            BlockStorage.addBlockInfo(block.getLocation(), BLOCK_STORAGE_ITEM_KEY, null);
            BlockStorage.addBlockInfo(block.getLocation(), BLOCK_STORAGE_AMOUNT_KEY, null);
        }

        ItemStack showItem;
        if (operation != null) {
            operation.updateShowItem();
            showItem = operation.getShowItem();
            if (operation.getType() == ItemSerializationConstructorOperation.COPY_CARD) {
                BlockStorage.addBlockInfo(block.getLocation(), BLOCK_STORAGE_ITEM_KEY, ItemStackUtil.itemStackToString(((ItemCopyCardOperation)operation).getMatchItem()));
                BlockStorage.addBlockInfo(block.getLocation(), BLOCK_STORAGE_AMOUNT_KEY, String.valueOf((int)((ItemCopyCardOperation)operation).getCount()));
            }
        } else {
            showItem = NULL_INFO_ICON;
        }
        inv.replaceExistingItem(ItemSerializationConstructorMenu.STATUS_SLOT, showItem);
    }

    @Override
    public void registerDefaultRecipes() {
        this.registerDescriptiveRecipe(TextUtil.COLOR_PASSIVE + "制造 " + FinalTechItems.COPY_CARD.getDisplayName(),
                "",
                TextUtil.COLOR_NORMAL + "输入 " + TextUtil.COLOR_NUMBER + CopyCardItem.DIFFICULTY + "个" + TextUtil.COLOR_NORMAL + " 相同物品",
                TextUtil.COLOR_NORMAL + "输出一个该物品的复制卡",
                TextUtil.COLOR_NEGATIVE + "无法制造 " + FinalTechItems.SINGULARITY.getDisplayName() + " " + FinalTechItems.SPIROCHETE.getDisplayName() + " " + FinalTechItems.PHONY.getDisplayName() + TextUtil.COLOR_NEGATIVE + " 的复制卡");
        this.registerDescriptiveRecipe(TextUtil.COLOR_PASSIVE + "制造 " + FinalTechItems.SINGULARITY.getDisplayName(),
                "",
                TextUtil.COLOR_NORMAL + "输入任意 " + TextUtil.COLOR_NUMBER + Singularity.SINGULARITY_DIFFICULTY + "个" + TextUtil.COLOR_NORMAL + " 任意物品的复制卡",
                TextUtil.COLOR_NORMAL + "输出一个 " + FinalTechItems.SINGULARITY.getDisplayName());
        this.registerDescriptiveRecipe(TextUtil.COLOR_PASSIVE + "制造 " + FinalTechItems.SPIROCHETE.getDisplayName(),
                "",
                TextUtil.COLOR_NORMAL + "输入任意 " + TextUtil.COLOR_NUMBER + Spirochete.SPIROCHETE_DIFFICULTY + "种" + TextUtil.COLOR_NORMAL + " 不同物品的复制卡",
                TextUtil.COLOR_NORMAL + "输出一个 " + FinalTechItems.SPIROCHETE.getDisplayName());
        this.registerDescriptiveRecipe(TextUtil.COLOR_PASSIVE + "制造 " + FinalTechItems.PHONY.getDisplayName(),
                "",
                TextUtil.COLOR_NORMAL + "输入的复制卡同时满足",
                TextUtil.COLOR_NORMAL + "制造 " + FinalTechItems.SINGULARITY.getDisplayName() + TextUtil.COLOR_NORMAL + " 和制造 " + FinalTechItems.SPIROCHETE.getDisplayName() + TextUtil.COLOR_NORMAL + " 的条件",
                TextUtil.COLOR_NORMAL + "输出一个 " + FinalTechItems.PHONY.getDisplayName());
        this.registerDescriptiveRecipe(TextUtil.COLOR_NEGATIVE + "反制",
                "",
                TextUtil.COLOR_NORMAL + "服务器性能会极大地影响制造复制卡效率",
                TextUtil.COLOR_NORMAL + "具体体现为 压缩进度变化值可能会远小于输入物品的数量");
        this.registerDescriptiveRecipe(TextUtil.COLOR_PASSIVE + "仿造物",
                "",
                TextUtil.COLOR_NORMAL + "可以使用 " + FinalTechItems.PHONY.getDisplayName() + TextUtil.COLOR_NORMAL + " 在压缩过程中代替任何物品制造复制卡",
                TextUtil.COLOR_NORMAL + "且此时不会受到 " + TextUtil.COLOR_NEGATIVE + "反制" + TextUtil.COLOR_NORMAL + " 效果影响");
    }
}
