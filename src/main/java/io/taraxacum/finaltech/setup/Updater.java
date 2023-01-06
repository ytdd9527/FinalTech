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
    public static final String LATEST_VERSION = "20221204";

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
