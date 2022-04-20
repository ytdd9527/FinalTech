package io.taraxacum.finaltech.machine.standard;

import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.libraries.dough.inventory.InvUtils;
import io.github.thebusybiscuit.slimefun4.libraries.dough.items.CustomItemStack;
import io.taraxacum.finaltech.api.operation.ItemSerializationConstructorOperation;
import io.taraxacum.finaltech.item.unusable.CopyCardItem;
import io.taraxacum.finaltech.menu.standard.AbstractStandardMachineMenu;
import io.taraxacum.finaltech.api.operation.ItemCopyCardOperation;
import io.taraxacum.finaltech.menu.standard.ItemSerializationConstructorMenu;
import io.taraxacum.finaltech.util.ItemStackUtil;
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
 *
 * It's not a function class!
 * @author Final_ROOT
 */
public class ItemSerializationConstructor extends AbstractStandardMachine {
    public static final CustomItemStack NULL_INFO_ICON = new CustomItemStack(Material.REDSTONE, "&f完成进度", "&7暂未输入物品");
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
                    int amount = Integer.parseInt(config.getString(BLOCK_STORAGE_AMOUNT_KEY));
                    ((ItemCopyCardOperation)operation).setCount(amount);
                }
            }
        }

        for (int slot : this.getInputSlots()) {
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

        if (operation != null && operation.isFinished() && InvUtils.fits(inv.toInventory(), operation.getResult(), this.getOutputSlots())) {
            inv.pushItem(operation.getResult(), this.getOutputSlots());
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
                BlockStorage.addBlockInfo(block.getLocation(), BLOCK_STORAGE_AMOUNT_KEY, String.valueOf(((ItemCopyCardOperation)operation).getCount()));
            }
        } else {
            showItem = NULL_INFO_ICON;
        }
        inv.replaceExistingItem(ItemSerializationConstructorMenu.PROGRESS_SLOT, showItem);
    }

    @Override
    public void registerDefaultRecipes() {
        this.registerDescriptiveRecipe("&f制造[复制卡]",
                "",
                "&f输入" + CopyCardItem.COPY_CARD_DIFFICULTY + "个相同物品",
                "&f输出一个该物品的[复制卡]",
                "&f无法制造 奇点、螺旋体、伪物 的复制卡");
        this.registerDescriptiveRecipe("&f制造[奇点]",
                "",
                "&f输入任意" + CopyCardItem.SINGULARITY_DIFFICULTY + "个[复制卡]",
                "&f输出一个[奇点]",
                "",
                "&f制造[奇点]所需[复制卡]的基础数量为128",
                "&f然后再加上总共的粘液科技物品个数取根号后的数字",
                "&f即为最终所需要的[复制卡]的个数");
        this.registerDescriptiveRecipe("&f制造[螺旋体]",
                "",
                "&f输入任意" + CopyCardItem.SPIROCHETE_DIFFICULTY + "种不同的[复制卡]",
                "&f输出一个[螺旋体]",
                "",
                "&f制造[螺旋体]所需[复制卡]的基础种数为32",
                "&f每个已有的粘液科技附属使其+1");
        this.registerDescriptiveRecipe("&f制造[伪物]",
                "",
                "&f输入的复制卡同时满足",
                "&f制造[奇点]和制造[螺旋体]的条件",
                "&f输出一个[伪物]");
        this.registerDescriptiveRecipe("&f提示",
                "",
                "&f[伪物]可以在压缩过程中代替任何物品制造[复制卡]");
        this.registerDescriptiveRecipe("&f提示",
                "",
                "&f也可以在增强型工作台",
                "&f使用一个[奇点]和一个[螺旋体]合成[伪物]");
    }
}
