package io.taraxacum.finaltech.core;

import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.abstractItems.MachineRecipe;
import org.bukkit.inventory.ItemStack;

import java.util.Map;

public class RandomMachineRecipeV2 extends MachineRecipe {
    private boolean random;
    private int count;
    private MachineRecipe machineRecipe;
    private Map<ItemStack, Integer> randomOutputMap;

    public RandomMachineRecipeV2(int seconds, ItemStack[] input, ItemStack[] output) {
        super(seconds, input, output);
    }

    public RandomMachineRecipeV2(MachineRecipe machineRecipe) {
        this(machineRecipe.getTicks(), machineRecipe.getInput(), machineRecipe.getOutput());
        this.machineRecipe = machineRecipe;
        this.random = false;
        this.count = 1;
    }

    public RandomMachineRecipeV2(MachineRecipe machineRecipe, int count) {
        this(machineRecipe.getTicks(), machineRecipe.getInput(), machineRecipe.getOutput());
        this.machineRecipe = machineRecipe;
        this.random = false;
        this.count = count;
    }

    public MachineRecipe getMachineRecipe() {
        return machineRecipe;
    }

    @Override
    public ItemStack[] getOutput() {
        return random ? this.machineRecipe.getOutput() : null;
    }

    public int getCount() {
        return this.count;
    }
}
