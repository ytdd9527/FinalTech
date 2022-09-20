package io.taraxacum.finaltech.core.operation;

import io.github.thebusybiscuit.slimefun4.libraries.dough.items.CustomItemStack;
import io.taraxacum.finaltech.FinalTech;
import io.taraxacum.finaltech.core.items.machine.operation.ItemSerializationConstructor;
import io.taraxacum.finaltech.core.items.unusable.CopyCardItem;
import io.taraxacum.finaltech.core.items.unusable.ItemPhony;
import io.taraxacum.finaltech.setup.FinalTechItems;
import io.taraxacum.finaltech.util.ItemStackUtil;
import io.taraxacum.finaltech.util.slimefun.ConfigUtil;
import io.taraxacum.finaltech.util.slimefun.ConstantTableUtil;
import io.taraxacum.finaltech.util.slimefun.RecipeUtil;
import io.taraxacum.finaltech.util.slimefun.SfItemUtil;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * @author Final_ROOT
 * @since 1.0
 */
public class ItemCopyCardOperation implements ItemSerializationConstructorOperation {
    private double count;
    private final int difficulty;
    private final ItemStack matchItem;
    private final ItemStack copyCardItem;
    private final ItemStack showItem;
    public static final double RATE = ConfigUtil.getOrDefaultItemSetting(0.1, FinalTechItems.ITEM_SERIALIZATION_CONSTRUCTOR.getItemId(), "rate");

    protected ItemCopyCardOperation(@Nonnull ItemStack item) {
        this.count = item.getAmount();
        this.difficulty = ConstantTableUtil.ITEM_COPY_CARD_AMOUNT;
        this.matchItem = item.clone();
        this.matchItem.setAmount(1);
        this.copyCardItem = CopyCardItem.newItem(this.matchItem, "1");
        this.showItem = new CustomItemStack(item.getType(), ConfigUtil.getStatusMenuName(FinalTech.getLanguageManager(), FinalTechItems.ITEM_SERIALIZATION_CONSTRUCTOR.getItemId()),
                ConfigUtil.getStatusMenuLore(FinalTech.getLanguageManager(), FinalTechItems.ITEM_SERIALIZATION_CONSTRUCTOR.getItemId(),
                        ItemStackUtil.getItemName(this.matchItem),
                        String.format("%.8f", this.count),
                        String.valueOf(this.difficulty)));
    }

    public double getCount() {
        return this.count;
    }

    public void setCount(double count) {
        this.count = count;
    }

    @Nonnull
    public ItemStack getMatchItem() {
        return this.matchItem;
    }

    @Override
    public int getType() {
        return ItemSerializationConstructorOperation.COPY_CARD;
    }

    @Nonnull
    @Override
    public ItemStack getShowItem() {
        return this.showItem;
    }

    @Override
    public void updateShowItem() {
        ItemStackUtil.setLore(this.showItem, ConfigUtil.getStatusMenuLore(FinalTech.getLanguageManager(), FinalTechItems.ITEM_SERIALIZATION_CONSTRUCTOR.getItemId(),
                ItemStackUtil.getItemName(this.matchItem),
                String.format("%.8f", this.count),
                String.valueOf(this.difficulty)));
    }

    @Override
    public int addItem(@Nullable ItemStack item) {
        if (!this.isFinished()) {
            if (ItemStackUtil.isItemSimilar(item, this.matchItem)) {
                double efficiency = Math.pow(RATE, 20.0 - FinalTech.getTps());
                efficiency = Math.min(efficiency, 1);
                if(item.getAmount() * efficiency + this.count < this.difficulty) {
                    int amount = item.getAmount();
                    item.setAmount(item.getAmount() - amount);
                    this.count += amount * efficiency;
                    return amount;
                } else {
                    int amount = (int) Math.ceil((this.difficulty - this.count) / efficiency);
                    item.setAmount(item.getAmount() - amount);
                    this.count = this.difficulty;
                    return amount;
                }
            } else if(ItemPhony.isValid(item)) {
                double amount = Math.min(item.getAmount(), this.difficulty - this.count);
                item.setAmount(item.getAmount() - (int) Math.ceil(amount));
                this.count += amount;
                return (int) Math.ceil(amount);
            }
        }
        return 0;
    }

    @Override
    public boolean isFinished() {
        return this.count >= this.difficulty;
    }

    @Nonnull
    @Override
    public ItemStack getResult() {
        return this.copyCardItem;
    }

    @Deprecated
    @Override
    public void addProgress(int i) {
        this.count += i;
    }

    @Deprecated
    @Override
    public int getProgress() {
        return (int) Math.floor(this.count);
    }

    @Deprecated
    @Override
    public int getTotalTicks() {
        return this.difficulty;
    }
}
