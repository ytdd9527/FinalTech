package io.taraxacum.finaltech;

import io.github.thebusybiscuit.slimefun4.api.SlimefunAddon;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.libraries.dough.config.Config;
import io.github.thebusybiscuit.slimefun4.libraries.dough.items.CustomItemStack;
import io.taraxacum.finaltech.item.CopyCardItem;
import io.taraxacum.finaltech.item.StorageCardItem;
import io.taraxacum.finaltech.item.UnOrderSword;
import io.taraxacum.finaltech.machine.capacitor.expanded.*;
import io.taraxacum.finaltech.machine.cargo.*;
import io.taraxacum.finaltech.machine.capacitor.*;
import io.taraxacum.finaltech.machine.*;
import io.taraxacum.finaltech.machine.standard.advanced.*;
import io.taraxacum.finaltech.machine.standard.basic.*;
import io.taraxacum.finaltech.machine.manual.*;
import io.taraxacum.finaltech.machine.standard.AllCompression;
import io.taraxacum.finaltech.machine.standard.AllFactory;
import io.taraxacum.finaltech.machine.standard.BasicFrameMachine;
import io.taraxacum.finaltech.machine.standard.OrderedDustFactory;
import io.taraxacum.finaltech.setup.SetupUtil;
import io.taraxacum.finaltech.setup.register.FinalTechItems;
import io.taraxacum.finaltech.setup.register.FinalTechMenus;
import io.taraxacum.finaltech.setup.register.FinalTechRecipes;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * @author Final_ROOT
 */
public class FinalTech extends JavaPlugin implements SlimefunAddon {

    @Override
    public void onEnable() {
        Config cfg = new Config(this);

        if (cfg.getBoolean("options.auto-update")) {
            // todo
            // You could start an Auto-Updater for example
        }

        SetupUtil.setupMenus(this);
        SetupUtil.setupItems(this);
        SetupUtil.setupMachines(this);
    }

    @Override
    public void onDisable() {
        // Logic for disabling the plugin...
    }

    @Override
    public String getBugTrackerURL() {
        // You can return a link to your Bug Tracker instead of null here
        return null;
    }

    @Override
    public JavaPlugin getJavaPlugin() {
        /*
         * You will need to return a reference to your Plugin here.
         * If you are using your main class for this, simply return "this".
         */
        return this;
    }
}
