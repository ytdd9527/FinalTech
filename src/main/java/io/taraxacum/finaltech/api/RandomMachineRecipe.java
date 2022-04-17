package io.taraxacum.finaltech.api;

import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.abstractItems.MachineRecipe;
import org.bukkit.inventory.ItemStack;

import java.util.Random;

/**
 * @author Final_ROOT
 */
@Deprecated
public class RandomMachineRecipe extends MachineRecipe {
    private final boolean randomOutput;
    private static final Random random = new Random();

    public RandomMachineRecipe(int seconds, ItemStack[] input, ItemStack[] output) {
        super(seconds, input, output);
        this.randomOutput = false;
    }

    public RandomMachineRecipe(int seconds, ItemStack[] input, ItemStack[] output, boolean randomOutput) {
        super(seconds, input, output);
        this.randomOutput = randomOutput;
    }

    @Override
    public ItemStack[] getOutput() {
        if(randomOutput) {
            return new ItemStack[] {super.getOutput()[random.nextInt(super.getOutput().length)]};
        } else {
            return super.getOutput();
        }
    }
}
