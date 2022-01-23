package io.taraxacum.finaltech.util;

import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.implementation.Slimefun;
import io.github.thebusybiscuit.slimefun4.libraries.dough.items.CustomItemStack;
import io.github.thebusybiscuit.slimefun4.libraries.dough.protection.Interaction;
import io.taraxacum.finaltech.abstractItem.FinalBasicMachine;
import io.taraxacum.finaltech.abstractItem.FinalMachine;
import io.taraxacum.finaltech.abstractItem.MachineMenu;
import io.taraxacum.finaltech.menu.AdvancedMachineMenu;
import io.taraxacum.finaltech.menu.BasicMachineMenu;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.abstractItems.MachineRecipe;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

public class FinalUtil {

    public static final String MACHINE_MAX_STACK = "max-stack";

    public static final ItemStack MACHINE_MAX_STACK_ICON = new CustomItemStack(Material.CHEST, "&7输入数量限制");

    public static final ItemStack BORDER = new CustomItemStack(Material.GRAY_STAINED_GLASS_PANE, " ");
    public static final ItemStack INPUT_BORDER = new CustomItemStack(Material.BLUE_STAINED_GLASS_PANE, " ");
    public static final ItemStack OUTPUT_BORDER = new CustomItemStack(Material.ORANGE_STAINED_GLASS_PANE, " ");

    public static final ItemStack QUANTITY_MODULE_ICON = new CustomItemStack(Material.REDSTONE, "&f可升级模块", "&7该机器可以进行升级", "&7当前效率：1");

    public static void registerRecipeBySlimefunId(FinalMachine finalMachine, String SlimefunId) {
        final SlimefunItem slimefunItem = SlimefunItem.getById(SlimefunId);
        try {
            Method method = slimefunItem.getClass().getMethod("getMachineRecipes");
            List<MachineRecipe> recipes = (List<MachineRecipe>)method.invoke(slimefunItem);
            if(recipes != null) {
                for(MachineRecipe machineRecipe : recipes) {
                    finalMachine.registerRecipe(0, machineRecipe.getInput(), machineRecipe.getOutput());
                }
            }
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public static void reserve(int[] ints) {
        for(int i = 0; i < ints.length / 2; i++) {
            int k = ints[i];
            ints[i] = ints[ints.length - 1 - i];
            ints[ints.length - 1 - i] = k;
        }
    }

    public static void stock(BlockMenu inv, int[] slots) {
        ArrayList<ItemStack> itemStacks = new ArrayList<>();
        for(int slot : slots) {
            ItemStack i = inv.getItemInSlot(slot);
            if(i == null || i.getType() == Material.AIR) {
                continue;
            }
            for(int j = 0; j < itemStacks.size(); j++) {
                ItemStack item = itemStacks.get(j);
                if(item != null && item.isSimilar(i)) {
                    int consume = 0;
                    if(i.getAmount() + item.getAmount() <= item.getMaxStackSize()) {
                        consume = i.getAmount();
                        item.setAmount(item.getAmount() + i.getAmount());
                    } else {
                        int itemAmount = item.getAmount();
                        consume = item.getMaxStackSize() - itemAmount;
                        item.setAmount(item.getMaxStackSize());
                        itemStacks.set(j, null);
                    }
                    i.setAmount(i.getAmount() - consume);
                }
            }
            if(i.getAmount() > 0) {
                itemStacks.add(i);
            }
        }
    }

    public static String toLocal(ItemStack itemStack) {
        Map<String, Object> serialize = itemStack.serialize();
        String s = "";
        Iterator iterator = serialize.keySet().iterator();
        while (iterator.hasNext()) {
            String key = iterator.next().toString();
            String value = serialize.get(key).toString();
            s += key;
            s += "--key:value--";
            s += value;
            s += "Iterator:end";
        }
        return s;
    }

    public static ItemStack toItem(String local) {
        Map<String, Object> serialize = new LinkedHashMap<>();
        for(String s : local.split("Iterator:end")) {
            serialize.put(s.split("--key:value--")[0], s.split("--key:value--")[1]);
        }
        ItemStack itemStack = ItemStack.deserialize(serialize);
        return itemStack;
    }
}
