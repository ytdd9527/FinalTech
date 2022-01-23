package io.taraxacum.finaltech;

import io.github.thebusybiscuit.slimefun4.api.SlimefunAddon;
import io.github.thebusybiscuit.slimefun4.api.items.ItemHandler;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.implementation.setup.SlimefunItemSetup;
import io.github.thebusybiscuit.slimefun4.libraries.dough.config.Config;
import io.taraxacum.finaltech.cargo.DoubleLinkedBarrel;
import io.taraxacum.finaltech.cargo.DoubleNormalBarrel;
import io.taraxacum.finaltech.cargo.Pipe;
import io.taraxacum.finaltech.cargo.PipeGroup;
import io.taraxacum.finaltech.machine.*;
import io.taraxacum.finaltech.setup.FinalTechItems;
import io.taraxacum.finaltech.setup.FinalTechMenus;
import io.taraxacum.finaltech.setup.FinalTechRecipes;
import me.mrCookieSlime.Slimefun.Objects.handlers.BlockTicker;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

public class FinalTech extends JavaPlugin implements SlimefunAddon {

    @Override
    public void onEnable() {
        // Read something from your config.yml
        Config cfg = new Config(this);

        if (cfg.getBoolean("options.auto-update")) {
            // todo
            // You could start an Auto-Updater for example
        }

        FinalTechMenus.MAIN_MENU.register(this);

        // register items
        new SlimefunItem(FinalTechMenus.MENU_MATERIAL, FinalTechItems.QUANTITY_MODULE, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.QUANTITY_MODULE).register(this);
        new SlimefunItem(FinalTechMenus.MENU_MATERIAL, FinalTechItems.UNORDERED_DUST,  RecipeType.NULL, new ItemStack[] {}).register(this);

        // register machines
        new BasicCobbleFactory(FinalTechMenus.MENU_BASIC_MACHINE, FinalTechItems.BASIC_COBBLE_FACTORY, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.BASIC_COBBLE_FACTORY).register(this);
        new BasicOreFactory(FinalTechMenus.MENU_BASIC_MACHINE, FinalTechItems.BASIC_ORE_FACTORY, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.BASIC_ORE_FACTORY).register(this);
        new BasicDustFactory(FinalTechMenus.MENU_BASIC_MACHINE, FinalTechItems.BASIC_DUST_FACTORY, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.BASIC_DUST_FACTORY).register(this);
        new BasicFarmFactory(FinalTechMenus.MENU_BASIC_MACHINE, FinalTechItems.BASIC_FARM_FACTORY, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.BASIC_FARM_FACTORY).register(this);
        new UnOrderedDustFactory(FinalTechMenus.MENU_ADVANCED_MACHINE, FinalTechItems.UNORDERED_DUST_FACTORY, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.UNORDERED_DUST_FACTORY).register(this);

        // register advanced machines
        new AdvancedAutoDrier(FinalTechMenus.MENU_ADVANCED_MACHINE, FinalTechItems.ADVANCED_AUTO_DRIER, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.ADVANCED_AUTO_DRIER).register(this);
        new AdvancedCarbonPress(FinalTechMenus.MENU_ADVANCED_MACHINE, FinalTechItems.ADVANCED_CARBON_PRESS, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.ADVANCED_CARBON_PRESS).register(this);
        new AdvancedElectricCrucible(FinalTechMenus.MENU_ADVANCED_MACHINE, FinalTechItems.ADVANCED_ELECTRIC_CRUCIBLE, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.ADVANCED_ELECTRIC_CRUCIBLE).register(this);
        new AdvancedElectricDustWasher(FinalTechMenus.MENU_ADVANCED_MACHINE, FinalTechItems.ADVANCED_ELECTRIC_DUST_WASHER, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.ADVANCED_ELECTRIC_DUST_WASHER).register(this);
        new AdvancedElectricFurance(FinalTechMenus.MENU_ADVANCED_MACHINE, FinalTechItems.ADVANCED_ELECTRIC_FURANCE, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.ADVANCED_ELECTRIC_FURANCE).register(this);
        new AdvancedElectricIngotFactory(FinalTechMenus.MENU_ADVANCED_MACHINE, FinalTechItems.ADVANCED_ELECTRIC_INGOT_FACTORY, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.ADVANCED_ELECTRIC_INGOT_FACTORY).register(this);
        new AdvancedElectricIngotPulverizer(FinalTechMenus.MENU_ADVANCED_MACHINE, FinalTechItems.ADVANCED_ELECTRIC_INGOT_PULVERIZER, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.ADVANCED_ELECTRIC_INGOT_PULVERIZER).register(this);
        new AdvancedElectricOreGrinder(FinalTechMenus.MENU_ADVANCED_MACHINE, FinalTechItems.ADVANCED_ELECTRIC_ORE_GRINDER, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.ADVANCED_ELECTRIC_ORE_GRINDER).register(this);
        new AdvancedElectricPress(FinalTechMenus.MENU_ADVANCED_MACHINE, FinalTechItems.ADVANCED_ELECTRIC_PRESS, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.ADVANCED_ELECTRIC_PRESS).register(this);
        new AdvancedElectricSmeltery(FinalTechMenus.MENU_ADVANCED_MACHINE, FinalTechItems.ADVANCED_ELECTRIC_SMELTERY, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.ADVANCED_ELECTRIC_SMELTERY).register(this);
        new AdvancedFoodFactory(FinalTechMenus.MENU_ADVANCED_MACHINE, FinalTechItems.ADVANCED_FOOD_FACTORY, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.ADVANCED_FOOD_FACTORY).register(this);
        new AdvancedFreezer(FinalTechMenus.MENU_ADVANCED_MACHINE, FinalTechItems.ADVANCED_FREEZER, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.ADVANCED_FREEZER).register(this);
        new AdvancedGoldPan(FinalTechMenus.MENU_ADVANCED_MACHINE, FinalTechItems.ADVANCED_GOLD_PAN, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.ADVANCED_GOLD_PAN).register(this);
        new AdvancedHeatedPressureChamber(FinalTechMenus.MENU_ADVANCED_MACHINE, FinalTechItems.ADVANCED_HEATED_PRESSURE_CHAMBER, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.ADVANCED_HEATED_PRESSURE_CHAMBER).register(this);
        new AdvancedDustFactory(FinalTechMenus.MENU_ADVANCED_MACHINE, FinalTechItems.ADVANCED_DUST_FACTORY, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.ADVANCED_DUST_FACTORY).register(this);
        new AdvancedFarmFactory(FinalTechMenus.MENU_ADVANCED_MACHINE, FinalTechItems.ADVANCED_FARM_FACTORY, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.ADVANCED_FARM_FACTORY).register(this);

        // register final machines
        new AllCompression(FinalTechMenus.MENU_FINAL_MACHINE, FinalTechItems.ALL_COMPRESSION, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.ALL_COMPRESSION).register(this);
        new AllFactory(FinalTechMenus.MENU_FINAL_MACHINE, FinalTechItems.ALL_FACTORY, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.ALL_FACTORY).register(this);

        // register cargos
        new BasicFrameMachine(FinalTechMenus.MENU_CARGO, FinalTechItems.BASIC_FRAME_MACHINE, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.BASIC_FRAME_MACHINE).register(this);
        new Pipe(FinalTechMenus.MENU_CARGO, FinalTechItems.PIPE, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.PIPE).register(this);
        new PipeGroup(FinalTechMenus.MENU_CARGO, FinalTechItems.PIPE_GROUP, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.PIPE_GROUP).register(this);
        new DoubleNormalBarrel(FinalTechMenus.MENU_CARGO, FinalTechItems.DOUBLE_NORMAL_BARREL, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.DOUBLE_NORMAL_BARREL).register(this);
        new DoubleLinkedBarrel(FinalTechMenus.MENU_CARGO, FinalTechItems.DOUBLE_LINKED_BARREL, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.DOUBLE_LINKED_BARREL).register(this);

//        // Give your Category a unique id.
//        NamespacedKey itemGroupId = new NamespacedKey(this, "finalTech_category");
//        ItemGroup itemGroup = new ItemGroup(itemGroupId, itemGroupItem);
//
//        /*
//         * 2. Create a new SlimefunItemStack
//         * This class has many constructors, it is very important
//         * that you give each item a unique id.
//         */
//        SlimefunItemStack slimefunItem = new SlimefunItemStack("FINALTECH_TEST_ITEM", Material.DIAMOND, "&4test item", "&c+20% test");
//
//        /*
//         * 3. Creating a Recipe
//         * The Recipe is an ItemStack Array with a length of 9.
//         * It represents a Shaped Recipe in a 3x3 crafting grid.
//         * The machine in which this recipe is crafted in is specified
//         * further down as the RecipeType.
//         */
//        ItemStack[] recipe = {
//                new ItemStack(Material.EMERALD), null, new ItemStack(Material.EMERALD),
//                null, new ItemStack(Material.DIAMOND), null,
//                new ItemStack(Material.EMERALD), null, new ItemStack(Material.EMERALD)
//        };
//
//        /*
//         * 4. Registering the Item
//         * Now you just have to register the item.
//         * RecipeType.ENHANCED_CRAFTING_TABLE refers to the machine in
//         * which this item is crafted in.
//         * Recipe Types from Slimefun itself will automatically add the recipe to that machine.
//         */
//        SlimefunItem item = new SlimefunItem(itemGroup, slimefunItem, RecipeType.ENHANCED_CRAFTING_TABLE, recipe);
//        item.register(this);
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
