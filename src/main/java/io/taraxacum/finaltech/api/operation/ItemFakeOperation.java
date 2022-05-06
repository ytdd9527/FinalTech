package io.taraxacum.finaltech.api.operation;

import io.github.thebusybiscuit.slimefun4.libraries.dough.items.CustomItemStack;
import io.github.thebusybiscuit.slimefun4.utils.itemstack.ItemStackWrapper;
import io.taraxacum.finaltech.items.unusable.CopyCardItem;
import io.taraxacum.finaltech.setup.register.FinalTechItems;
import io.taraxacum.finaltech.util.ItemStackUtil;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Final_ROOT
 */
public class ItemFakeOperation implements ItemSerializationConstructorOperation {
    private int itemTypeCount;
    private int itemAmountCount;
    private final int itemTypeDifficulty;
    private final int itemAmountDifficulty;

    private final ItemStack showItem;

    private List<ItemStackWrapper> itemTypeList = new ArrayList<>(CopyCardItem.SPIROCHETE_DIFFICULTY);

    protected ItemFakeOperation(@Nonnull ItemStack item) {
        this.itemTypeCount = 1;
        this.itemTypeList.add(ItemStackWrapper.wrap(item));
        this.itemAmountCount = item.getAmount();

        this.itemTypeDifficulty = CopyCardItem.SPIROCHETE_DIFFICULTY;
        this.itemAmountDifficulty = CopyCardItem.SINGULARITY_DIFFICULTY;

        this.showItem = new CustomItemStack(FinalTechItems.PHONY.getType(), "§f完成进度", "§f物品个数= " + this.itemAmountCount + "/" + this.itemAmountDifficulty, "§f物品种数= " + this.itemTypeCount + "/" + this.itemTypeDifficulty);
    }

    @Override
    public int getType() {
        return ItemSerializationConstructorOperation.ITEM_FAKE;
    }

    @Override
    public ItemStack getShowItem() {
        return this.showItem;
    }

    @Override
    public void updateShowItem() {
        ItemStackUtil.setLore(this.showItem, "§f物品个数= " + this.itemAmountCount + "/" + this.itemAmountDifficulty, "§f物品种数= " + this.itemTypeCount + "/" + this.itemTypeDifficulty);
    }

    @Override
    public int addItem(@Nullable ItemStack item) {
        if (ItemStackUtil.isItemNull(item) || !CopyCardItem.isValid(item)) {
            return 0;
        }

        this.itemTypeCount++;
        if (this.itemTypeCount <= this.itemTypeDifficulty) {
            ItemStackWrapper itemWrapper = ItemStackWrapper.wrap(item);
            for (ItemStackWrapper itemTypeWrapper : itemTypeList) {
                if (ItemStackUtil.isItemSimilar(itemWrapper, itemTypeWrapper)) {
                    this.itemTypeCount--;
                    break;
                }
            }
        }
        int amount = item.getAmount();
        this.itemAmountCount += amount;

        item.setAmount(item.getAmount() - amount);

        this.itemAmountCount = Math.min(this.itemAmountCount, this.itemAmountDifficulty);
        this.itemTypeCount = Math.min(this.itemTypeCount, this.itemTypeDifficulty);

        return amount;
    }

    @Override
    public boolean isFinished() {
        return this.itemAmountCount >= this.itemAmountDifficulty || this.itemTypeCount >= this.itemTypeDifficulty;
    }

    @Nonnull
    @Override
    public ItemStack getResult() {
        if (this.itemAmountCount >= this.itemAmountDifficulty && this.itemTypeCount >= this.itemTypeDifficulty) {
            return new ItemStack(FinalTechItems.PHONY);
        }
        if (this.itemAmountCount >= this.itemAmountDifficulty) {
            return new ItemStack(FinalTechItems.SINGULARITY);
        }
        if (this.itemTypeCount >= this.itemTypeDifficulty) {
            return new ItemStack(FinalTechItems.SPIROCHETE);
        }
        return ItemStackUtil.AIR;
    }

    public int getItemTypeCount() {
        return this.itemTypeCount;
    }
    public int getItemAmountCount() {
        return this.itemAmountCount;
    }
    public int getItemTypeDifficulty() {
        return this.itemTypeDifficulty;
    }
    public int getItemAmountDifficulty() {
        return this.itemAmountDifficulty;
    }

    @Deprecated
    @Override
    public void addProgress(int i) {

    }

    @Deprecated
    @Override
    public int getProgress() {
        return 0;
    }

    @Deprecated
    @Override
    public int getTotalTicks() {
        return 0;
    }
}
