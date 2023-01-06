package io.taraxacum.finaltech.setup;

import io.taraxacum.finaltech.FinalTech;
import io.taraxacum.libs.plugin.dto.ConfigFileManager;
import io.taraxacum.libs.plugin.dto.LanguageManager;

import javax.annotation.Nonnull;
import java.util.*;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.logging.Logger;

public class Updater implements Consumer<FinalTech>{
    private final Map<String, UpdateFunction> versionMap = new HashMap<>();
    private boolean init = false;
    private static volatile Updater instance;
    public static final String LATEST_VERSION = "20221229";

    public void init() {
        LanguageManager languageManager = FinalTech.getLanguageManager();
        ConfigFileManager configManager = FinalTech.getConfigManager();
        Logger logger = FinalTech.logger();
        Boolean updateLanguage = configManager.getOrDefault(true, "update", "language");
        if(updateLanguage) {
            logger.info("You have enabled the config updater for language file");
        }


        versionMap.put("20220811", new UpdateFunction() {
            @Override
            protected String targetVersion() {
                return "20221204";
            }

            @Override
            protected void update(FinalTech finalTech) {
                if(updateLanguage) {
                    List<String> lore1 = new ArrayList<>();
                    lore1.add("{color:normal}Input items to get {id:FINALTECH_UNORDERED_DUST} {color:normal}or {id:FINALTECH_ORDERED_DUST}");
                    lore1.add("");
                    lore1.add("{color:normal}A pair of random number will be generated.");
                    lore1.add("{color:normal}They will be used to determine how to craft item.");

                    List<String> lore2 = new ArrayList<>();
                    lore2.add("{color:normal}Input at least the specified amount of items");
                    lore2.add("{color:normal}Input at least the specified quantity of different type of items");
                    lore2.add("{color:normal}Then it will generate one {id:FINALTECH_UNORDERED_DUST}");

                    List<String> lore3 = new ArrayList<>();
                    lore3.add("{color:normal}Input just the specified amount of items");
                    lore3.add("{color:normal}Input just the specified quantity of different type of items");
                    lore3.add("{color:normal}Then it will generate one {id:FINALTECH_ORDERED_DUST}");

                    languageManager.setValue(lore1, "items", "FINALTECH_ORDERED_DUST_FACTORY_DIRT", "info", "1", "lore");
                    languageManager.setValue(lore2, "items", "FINALTECH_ORDERED_DUST_FACTORY_DIRT", "info", "2", "lore");
                    languageManager.setValue(lore3, "items", "FINALTECH_ORDERED_DUST_FACTORY_DIRT", "info", "3", "lore");
                }
            }
        });

        versionMap.put("20221204", new UpdateFunction() {
            @Override
            protected String targetVersion() {
                return "20221229";
            }

            @Override
            protected void update(FinalTech finalTech) {
                List<String> allowedIdList = new ArrayList<>();
                allowedIdList.add("FINALTECH_ADVANCED_COMPOSTER");
                allowedIdList.add("FINALTECH_ADVANCED_JUICER");
                allowedIdList.add("FINALTECH_ADVANCED_FURNACE");
                allowedIdList.add("FINALTECH_ADVANCED_GOLD_PAN");
                allowedIdList.add("FINALTECH_ADVANCED_DUST_WASHER");
                allowedIdList.add("FINALTECH_ADVANCED_INGOT_FACTORY");
                allowedIdList.add("FINALTECH_ADVANCED_CRUCIBLE");
                allowedIdList.add("FINALTECH_ADVANCED_ORE_GRINDER");
                allowedIdList.add("FINALTECH_ADVANCED_HEATED_PRESSURE_CHAMBER");
                allowedIdList.add("FINALTECH_ADVANCED_INGOT_PULVERIZER");
                allowedIdList.add("FINALTECH_ADVANCED_AUTO_DRIER");
                allowedIdList.add("FINALTECH_ADVANCED_PRESS");
                allowedIdList.add("FINALTECH_ADVANCED_FOOD_FACTORY");
                allowedIdList.add("FINALTECH_ADVANCED_FREEZER");
                allowedIdList.add("FINALTECH_ADVANCED_CARBON_PRESS");
                allowedIdList.add("FINALTECH_ADVANCED_SMELTERY");
                allowedIdList.add("FINALTECH_ADVANCED_DUST_FACTORY");
                configManager.setValue(allowedIdList, "FINALTECH_MULTI_FRAME_MACHINE", "allowed-id");

                if(updateLanguage) {
                    languageManager.setValue("{color:prandom}Remote Accessor {color:conceal}- {color:positive}Consumable", "items", "FINALTECH_CONSUMABLE_REMOTE_ACCESSOR", "name");
                    languageManager.setValue("{color:prandom}Remote Accessor {color:conceal}- {color:positive}Configurable", "items", "FINALTECH_CONFIGURABLE_REMOTE_ACCESSOR", "name");
                    languageManager.setValue("{color:prandom}Expanded Remote Accessor {color:conceal}- {color:positive}Consumable", "items", "FINALTECH_EXPANDED_CONSUMABLE_REMOTE_ACCESSOR", "name");
                    languageManager.setValue("{color:prandom}Expanded Remote Accessor {color:conceal}- {color:positive}Configurable", "items", "FINALTECH_EXPANDED_CONFIGURABLE_REMOTE_ACCESSOR", "name");
                    languageManager.setValue("{color:prandom}Transporter", "items", "FINALTECH_TRANSPORTER", "name");
                    languageManager.setValue("{color:prandom}Transporter {color:conceal}- {color:positive}Consumable", "items", "FINALTECH_CONSUMABLE_TRANSPORTER", "name");
                    languageManager.setValue("{color:prandom}Transporter {color:conceal}- {color:positive}Configurable", "items", "FINALTECH_CONFIGURABLE_TRANSPORTER", "name");
                    languageManager.setValue("{color:prandom}Expanded Transporter {color:conceal}- {color:positive}Consumable", "items", "FINALTECH_EXPANDED_CONSUMABLE_TRANSPORTER", "name");
                    languageManager.setValue("{color:prandom}Expanded Transporter {color:conceal}- {color:positive}Configurable", "items", "FINALTECH_EXPANDED_CONFIGURABLE_TRANSPORTER", "name");

                    languageManager.setValue("{color:passive}Mechanism", "items", "FINALTECH_CONSUMABLE_REMOTE_ACCESSOR", "info", "1", "name");
                    languageManager.setValue("{color:passive}Mechanism", "items", "FINALTECH_CONFIGURABLE_REMOTE_ACCESSOR", "info", "1", "name");
                    languageManager.setValue("{color:passive}Mechanism", "items", "FINALTECH_EXPANDED_CONSUMABLE_REMOTE_ACCESSOR", "info", "1", "name");
                    languageManager.setValue("{color:passive}Mechanism", "items", "FINALTECH_EXPANDED_CONFIGURABLE_REMOTE_ACCESSOR", "info", "1", "name");
                    languageManager.setValue("{color:passive}Mechanism", "items", "FINALTECH_TRANSPORTER", "info", "1", "name");
                    languageManager.setValue("{color:passive}Mechanism", "items", "FINALTECH_CONSUMABLE_TRANSPORTER", "info", "1", "name");
                    languageManager.setValue("{color:passive}Mechanism", "items", "FINALTECH_CONFIGURABLE_TRANSPORTER", "info", "1", "name");
                    languageManager.setValue("{color:passive}Mechanism", "items", "FINALTECH_EXPANDED_CONSUMABLE_TRANSPORTER", "info", "1", "name");
                    languageManager.setValue("{color:passive}Mechanism", "items", "FINALTECH_EXPANDED_CONFIGURABLE_TRANSPORTER", "info", "1", "name");

                    languageManager.setValue("FINALTECH_DIGITAL_ZERO", "items", "FINALTECH_CONSUMABLE_REMOTE_ACCESSOR", "info", "1", "output");
                    languageManager.setValue("FINALTECH_DIGITAL_ZERO", "items", "FINALTECH_CONFIGURABLE_REMOTE_ACCESSOR", "info", "1", "output");
                    languageManager.setValue("FINALTECH_DIGITAL_ZERO", "items", "FINALTECH_EXPANDED_CONSUMABLE_REMOTE_ACCESSOR", "info", "1", "output");
                    languageManager.setValue("FINALTECH_DIGITAL_ZERO", "items", "FINALTECH_EXPANDED_CONFIGURABLE_REMOTE_ACCESSOR", "info", "1", "output");
                    languageManager.setValue("FINALTECH_DIGITAL_ZERO", "items", "FINALTECH_CONSUMABLE_TRANSPORTER", "info", "1", "output");
                    languageManager.setValue("FINALTECH_DIGITAL_ZERO", "items", "FINALTECH_CONFIGURABLE_TRANSPORTER", "info", "1", "output");
                    languageManager.setValue("FINALTECH_DIGITAL_ZERO", "items", "FINALTECH_EXPANDED_CONSUMABLE_TRANSPORTER", "info", "1", "output");
                    languageManager.setValue("FINALTECH_DIGITAL_ZERO", "items", "FINALTECH_EXPANDED_CONFIGURABLE_TRANSPORTER", "info", "1", "output");

                    List<String> lore1 = new ArrayList<>();
                    lore1.add("{color:normal}Right click it to config it");
                    lore1.add("{color:normal}Right click it to open slimefun machine it looking at");
                    languageManager.setValue(lore1, "items", "FINALTECH_CONSUMABLE_REMOTE_ACCESSOR", "info", "1", "lore");
                    languageManager.setValue(lore1, "items", "FINALTECH_CONFIGURABLE_REMOTE_ACCESSOR", "info", "1", "lore");
                    languageManager.setValue(lore1, "items", "FINALTECH_EXPANDED_CONSUMABLE_REMOTE_ACCESSOR", "info", "1", "lore");
                    languageManager.setValue(lore1, "items", "FINALTECH_EXPANDED_CONFIGURABLE_REMOTE_ACCESSOR", "info", "1", "lore");

                    List<String> lore2 = new ArrayList<>();
                    lore2.add("{color:normal}Right click it to teleport to the target place");
                    lore2.add("{color:normal}Range: {color:number}{1}");
                    languageManager.setValue(lore2, "items", "FINALTECH_TRANSPORTER", "info", "1", "lore");

                    List<String> lore3 = new ArrayList<>();
                    lore3.add("{color:normal}Right click it to config it");
                    lore3.add("{color:normal}Right click it to teleport to the target place");

                    languageManager.setValue(lore3, "items", "FINALTECH_CONSUMABLE_TRANSPORTER", "info", "1", "lore");
                    languageManager.setValue(lore3, "items", "FINALTECH_CONFIGURABLE_TRANSPORTER", "info", "1", "lore");
                    languageManager.setValue(lore3, "items", "FINALTECH_EXPANDED_CONSUMABLE_TRANSPORTER", "info", "1", "lore");
                    languageManager.setValue(lore3, "items", "FINALTECH_EXPANDED_CONFIGURABLE_TRANSPORTER", "info", "1", "lore");

                    languageManager.setValue("{color:prandom}Time Generator", "items", "FINALTECH_TIME_GENERATOR", "name");
                    languageManager.setValue("{color:passive}Mechanism", "items", "FINALTECH_TIME_GENERATOR", "info", "1", "name");

                    languageManager.setValue("{color:prandom}Time Capacitor", "items", "FINALTECH_TIME_CAPACITOR", "name");
                    languageManager.setValue("{color:passive}Mechanism", "items", "FINALTECH_TIME_CAPACITOR", "info", "1", "name");

                    List<String> lore4 = new ArrayList<>();
                    lore4.add("{color:normal}Generate energy 1J per {color:number}{1}s");
                    lore4.add("{color:normal}Can store {color:number}{2} J {color:normal}of energy");
                    lore4.add("{color:normal}Double stored energy with time going");
                    languageManager.setValue(lore4, "items", "FINALTECH_TIME_GENERATOR", "info", "1", "lore");

                    List<String> lore5 = new ArrayList<>();
                    lore5.add("{color:normal}Can store {color:number}{1} J {color:normal}of energy");
                    lore5.add("{color:normal}Double stored energy with time going");
                    languageManager.setValue(lore5, "items", "FINALTECH_TIME_CAPACITOR", "info", "1", "lore");

                    List<String> lore6 = new ArrayList<>();
                    lore6.add("{color:normal}Stored Energy: {color:number}{1} J");
                    languageManager.setValue(lore6, "items", "FINALTECH_TIME_GENERATOR", "status", "lore");
                    languageManager.setValue(lore6, "items", "FINALTECH_TIME_CAPACITOR", "status", "lore");

                    languageManager.setValue("{color:prandom}Multi Frame Machine", "items", "FINALTECH_MULTI_FRAME_MACHINE", "name");

                    languageManager.setValue("{color:random}Disc", "categories", "FINALTECH_MAIN_MENU_DISC", "name");
                    languageManager.setValue("{color:random}Final Item", "categories", "FINALTECH_SUB_MENU_FINAL_ITEM", "name");
                    languageManager.setValue("{color:random}Trophy", "categories", "FINALTECH_SUB_MENU_TROPHY", "name");
                    languageManager.setValue("{color:random}Deprecated", "categories", "FINALTECH_SUB_MENU_DEPRECATED", "name");

                    languageManager.setValue("{color:prandom}Digital Extraction", "items", "FINALTECH_DIGITAL_EXTRACTION", "name");

                    List<String> lore7 = new ArrayList<>();
                    lore7.add("{color:normal}Can store {color:number}{1} J {color:normal}of energy every stack");
                    lore7.add("{color:normal}Can store {color:number}{2} stack {color:normal}of energy");
                    lore7.add("{color:normal}Generate energy with the same amount of stored energy stack every {color:number}{3} s");

                    languageManager.setValue(lore7, "items", "FINALTECH_SMALL_EXPANDED_CAPACITOR", "info", "1", "lore");
                    languageManager.setValue(lore7, "items", "FINALTECH_MEDIUM_EXPANDED_CAPACITOR", "info", "1", "lore");
                    languageManager.setValue(lore7, "items", "FINALTECH_BIG_EXPANDED_CAPACITOR", "info", "1", "lore");
                    languageManager.setValue(lore7, "items", "FINALTECH_LARGE_EXPANDED_CAPACITOR", "info", "1", "lore");
                    languageManager.setValue(lore7, "items", "FINALTECH_CARBONADO_EXPANDED_CAPACITOR", "info", "1", "lore");
                    languageManager.setValue(lore7, "items", "FINALTECH_ENERGIZED_EXPANDED_CAPACITOR", "info", "1", "lore");
                    languageManager.setValue(lore7, "items", "FINALTECH_ENERGIZED_STACK_EXPANDED_CAPACITOR", "info", "1", "lore");
                    languageManager.setValue(lore7, "items", "FINALTECH_OVERLOADED_EXPANDED_CAPACITOR", "info", "1", "lore");
                    languageManager.setValue(lore7, "items", "FINALTECH_MATRIX_EXPANDED_CAPACITOR", "info", "1", "lore");

                    languageManager.setValue("", "items", "FINALTECH_NORMAL_ELECTRICITY_SHOOT_PILE", "info", "2");
                    languageManager.setValue("", "items", "FINALTECH_ENERGIZED_ELECTRICITY_SHOOT_PILE", "info", "2");
                    languageManager.setValue("", "items", "FINALTECH_OVERLOADED_ELECTRICITY_SHOOT_PILE", "info", "2");
                }
            }
        });
    }

    public void update(@Nonnull FinalTech finalTech) {
        if(!this.init) {
            this.init();
        }

        String currentVersion = "";

        try {
            currentVersion = FinalTech.getConfigManager().getOrDefault(LATEST_VERSION, "version");
        } catch (Exception e) {
            e.printStackTrace();
            finalTech.getLogger().warning("It seems you have set the wrong value type of version");
            Integer version = FinalTech.getConfigManager().getOrDefault(Integer.parseInt(LATEST_VERSION), "version");
            currentVersion = String.valueOf(version);
        }

        if(LATEST_VERSION.equals(currentVersion)) {
            FinalTech.logger().info("You are using the latest version. Good luck!");
            return;
        }

        UpdateFunction updateFunction = versionMap.get(currentVersion);
        if(updateFunction == null) {
            FinalTech.logger().info("You are using the unknown version. Version updater is disabled.");
        } else {
            String targetVersion = this.LATEST_VERSION;
            FinalTech.logger().info("Version " + currentVersion + " is detected! Version updater start to work...");
            while (updateFunction != null && !LATEST_VERSION.equals(currentVersion)) {
                targetVersion = updateFunction.apply(finalTech);
                FinalTech.logger().info("FinalTECH Updated: " + currentVersion + " -> " + targetVersion);
                currentVersion = targetVersion;
                updateFunction = versionMap.get(currentVersion);
            }
            FinalTech.getConfigManager().setValue(targetVersion, "version");
            FinalTech.logger().info("You are using the latest version now. Good luck!");
        }
    }

    public void destroy() {
        this.versionMap.clear();
    }

    @Override
    public void accept(@Nonnull FinalTech finalTech) {
        this.update(finalTech);
    }

    @Nonnull
    public static Updater getInstance() {
        if (instance == null) {
            synchronized (Updater.class) {
                if (instance == null) {
                    instance = new Updater();
                }
            }
        }
        return instance;
    }

    protected static abstract class UpdateFunction implements Function<FinalTech, String> {
        protected UpdateFunction() {

        }

        @Override
        public String apply(FinalTech finalTech) {
            this.update(finalTech);
            return this.targetVersion();
        }

        protected abstract void update(FinalTech finalTech);

        protected abstract String targetVersion();
    }
}
