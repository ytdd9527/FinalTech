package io.taraxacum.finaltech.core.factory;

import io.taraxacum.finaltech.api.dto.AdvancedMachineRecipe;
import org.bukkit.Location;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Final_ROOT
 * @since 2.0
 */
public class LocationRecipeRegistry {
    private static final Map<Location, AdvancedMachineRecipe> LOCATION_RECIPE_MAP = new HashMap<>();

    @Nullable
    public static AdvancedMachineRecipe getRecipe(@Nonnull Location location) {
        if (LOCATION_RECIPE_MAP.containsKey(location)) {
            return LOCATION_RECIPE_MAP.get(location);
        }
        return null;
    }

    public static void setRecipe(@Nonnull Location location, @Nullable AdvancedMachineRecipe advancedMachineRecipe) {
        LOCATION_RECIPE_MAP.put(location, advancedMachineRecipe);
    }
}
