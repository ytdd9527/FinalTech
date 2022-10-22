package io.taraxacum.finaltech.core.items.usable;

import io.github.thebusybiscuit.slimefun4.api.events.PlayerRightClickEvent;
import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.core.attributes.EnergyNetComponent;
import io.github.thebusybiscuit.slimefun4.core.networks.energy.EnergyNetComponentType;
import io.github.thebusybiscuit.slimefun4.libraries.dough.protection.Interaction;
import io.taraxacum.common.util.StringNumberUtil;
import io.taraxacum.finaltech.FinalTech;
import io.taraxacum.finaltech.api.interfaces.RecipeItem;
import io.taraxacum.finaltech.util.ItemStackUtil;
import io.taraxacum.finaltech.util.ParticleUtil;
import io.taraxacum.finaltech.util.slimefun.*;
import me.mrCookieSlime.CSCoreLibPlugin.Configuration.Config;
import me.mrCookieSlime.Slimefun.api.BlockStorage;
import org.bukkit.Location;
import org.bukkit.NamespacedKey;
import org.bukkit.Particle;
import org.bukkit.block.Block;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import javax.annotation.Nonnull;
import java.util.Optional;

/**
 * @author Final_ROOT
 * @since 2.0
 */
public class PortableEnergyStorage extends UsableSlimefunItem implements RecipeItem {
    private final NamespacedKey KEY = new NamespacedKey(FinalTech.getInstance(), this.getId());

    public PortableEnergyStorage(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
        super(itemGroup, item, recipeType, recipe);
    }

    /**
     * The function the item will do
     * while a player hold the item and right click.
     *
     * @param playerRightClickEvent
     */
    @Override
    protected void function(@Nonnull PlayerRightClickEvent playerRightClickEvent) {
        playerRightClickEvent.cancel();

        Optional<Block> clickedBlock = playerRightClickEvent.getClickedBlock();
        System.out.println(1);
        if(clickedBlock.isPresent()) {
            System.out.println(2);
            Block block = clickedBlock.get();
            Location location = block.getLocation();
            if(PermissionUtil.checkPermission(playerRightClickEvent.getPlayer(), location, Interaction.INTERACT_BLOCK, Interaction.PLACE_BLOCK, Interaction.BREAK_BLOCK) && BlockStorage.hasBlockInfo(location)) {
                System.out.println(3);
                Config config = BlockStorage.getLocationInfo(location);
                if(config.contains(ConstantTableUtil.CONFIG_ID)) {
                    System.out.println(4);
                    String itemId = config.getString(ConstantTableUtil.CONFIG_ID);
                    SlimefunItem slimefunItem = SlimefunItem.getById(itemId);
                    if(slimefunItem instanceof EnergyNetComponent energyNetComponent) {
                        System.out.println(5);
                        ItemStack item = playerRightClickEvent.getItem();

                        if(EnergyNetComponentType.CONSUMER.equals(energyNetComponent.getEnergyComponentType()) && energyNetComponent.getCapacity() > 0) {
                            System.out.println("6a");
                            // charge machine

                            int capacity = energyNetComponent.getCapacity();
                            String energyInMachine = EnergyUtil.getCharge(config);
                            String energyInItem = this.getEnergy(item);
                            String charge = StringNumberUtil.min(StringNumberUtil.sub(String.valueOf(capacity), energyInMachine), energyInItem);

                            EnergyUtil.setCharge(config, StringNumberUtil.add(energyInMachine, charge));
                            this.subEnergy(item, charge);

                            this.updateLore(item);

                            ParticleUtil.drawCubeByBlock(Particle.GLOW, 0, block);
                        } else if((EnergyNetComponentType.GENERATOR.equals(energyNetComponent.getEnergyComponentType()) || EnergyNetComponentType.CAPACITOR.equals(energyNetComponent.getEnergyComponentType()))
                                && energyNetComponent.getCapacity() > 0) {
                            System.out.println("6b");
                            // consume energy in machine, charge item

                            String energyInMachine = EnergyUtil.getCharge(config);

                            this.addEnergy(item, energyInMachine);
                            EnergyUtil.setCharge(config, StringNumberUtil.ZERO);

                            this.updateLore(item);

                            ParticleUtil.drawCubeByBlock(Particle.GLOW, 0, block);
                        }
                    }
                }
            }
        }
    }

    @Nonnull
    public String getEnergy(@Nonnull ItemStack itemStack) {
        if(itemStack.hasItemMeta()) {
            ItemMeta itemMeta = itemStack.getItemMeta();
            PersistentDataContainer persistentDataContainer = itemMeta.getPersistentDataContainer();
            if(persistentDataContainer.has(KEY, PersistentDataType.STRING)) {
                return persistentDataContainer.get(KEY, PersistentDataType.STRING);
            }
        }
        return StringNumberUtil.ZERO;
    }

    public void addEnergy(@Nonnull ItemStack itemStack, @Nonnull String energy) {
        if(itemStack.hasItemMeta()) {
            ItemMeta itemMeta = itemStack.getItemMeta();
            PersistentDataContainer persistentDataContainer = itemMeta.getPersistentDataContainer();
            String number = StringNumberUtil.ZERO;
            if(persistentDataContainer.has(KEY, PersistentDataType.STRING)) {
                number = persistentDataContainer.get(KEY, PersistentDataType.STRING);
            }
            number = StringNumberUtil.add(number, energy);
            persistentDataContainer.set(KEY, PersistentDataType.STRING, number);
            itemStack.setItemMeta(itemMeta);
        }
    }

    public void subEnergy(@Nonnull ItemStack itemStack, @Nonnull String energy) {
        if(itemStack.hasItemMeta()) {
            ItemMeta itemMeta = itemStack.getItemMeta();
            PersistentDataContainer persistentDataContainer = itemMeta.getPersistentDataContainer();
            String number = StringNumberUtil.ZERO;
            if(persistentDataContainer.has(KEY, PersistentDataType.STRING)) {
                number = persistentDataContainer.get(KEY, PersistentDataType.STRING);
            }
            number = StringNumberUtil.sub(number, energy);
            persistentDataContainer.set(KEY, PersistentDataType.STRING, number);
            itemStack.setItemMeta(itemMeta);
        }
    }

    public void updateLore(@Nonnull ItemStack itemStack) {
        ItemStackUtil.setLore(itemStack, ConfigUtil.getStatusMenuLore(FinalTech.getLanguageManager(), this,
                this.getEnergy(itemStack)));
    }

    @Override
    public void registerDefaultRecipes() {
        RecipeUtil.registerDescriptiveRecipe(FinalTech.getLanguageManager(), this);
    }
}
