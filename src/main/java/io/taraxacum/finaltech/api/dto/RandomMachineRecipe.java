package io.taraxacum.finaltech.api.dto;

import io.taraxacum.finaltech.util.ItemStackUtil;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.abstractItems.MachineRecipe;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * A {@link MachineRecipe} that will its output item is random.
 *
 * @author Final_ROOT
 * @since 2.0
 */
public class RandomMachineRecipe extends MachineRecipe {
    private final List<RandomOutput> randomOutputList;
    private int weightSum = 0;

    public RandomMachineRecipe(@Nonnull MachineRecipe machineRecipe, @Nonnull List<RandomOutput> randomOutputList) {
        super(machineRecipe.getTicks(), machineRecipe.getInput(), new ItemStack[0]);
        this.randomOutputList = randomOutputList;
        for (RandomOutput randomOutput : this.randomOutputList) {
            this.weightSum += randomOutput.weight;
        }
    }

    public RandomMachineRecipe(int ticks, @Nonnull ItemStack[] input, @Nonnull List<RandomOutput> randomOutputList) {
        super(ticks, input, new ItemStack[0]);
        this.randomOutputList = randomOutputList;
        for (RandomOutput randomOutput : this.randomOutputList) {
            this.weightSum += randomOutput.weight;
        }
    }

    @Nonnull
    @Override
    public ItemStack[] getOutput() {
        int r = (int)(Math.random() * this.weightSum);
        for (RandomOutput randomOutput : this.randomOutputList) {
            if (r > randomOutput.getWeight()) {
                r -= randomOutput.getWeight();
                continue;
            }
            return ItemStackUtil.getItemArray(randomOutput.getOutputItem());
        }
        return new ItemStack[0];
    }

    @Nonnull
    public ItemStack[] getAllOutput() {
        List<ItemStack> itemList = new ArrayList<>();
        for (RandomOutput randomOutput : this.randomOutputList) {
            itemList.addAll(randomOutput.outputItem);
        }
        return ItemStackUtil.getItemArray(itemList);
    }

    @Nonnull
    public List<RandomOutput> getRandomOutputList() {
        return randomOutputList;
    }

    /**
     * @author Final_ROOT
     * @since 2.0
     */
    public static class RandomOutput {
        private List<ItemStack> outputItem;
        private int weight;

        public RandomOutput(@Nonnull List<ItemStack> outputItem, int weight) {
            this.outputItem = outputItem;
            this.weight = weight;
        }

        public RandomOutput(@Nonnull ItemStack[] outputItem, int weight) {
            this.outputItem = Arrays.stream(outputItem).toList();
            this.weight = weight;
        }

        public RandomOutput(@Nonnull ItemStack outputItem, int weight) {
            this.outputItem = List.of(outputItem);
            this.weight = weight;
        }

        @Nonnull
        public List<ItemStack> getOutputItem() {
            return outputItem;
        }

        public void setOutputItem(@Nonnull List<ItemStack> outputItem) {
            this.outputItem = outputItem;
        }

        public int getWeight() {
            return weight;
        }

        public void setWeight(int weight) {
            this.weight = weight;
        }
    }
}
