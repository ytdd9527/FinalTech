package io.taraxacum.finaltech.setup;

import io.taraxacum.finaltech.FinalTech;

import javax.annotation.Nonnull;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Function;

public class Updater implements Consumer<FinalTech>{
    private final Map<String, UpdateFunction> versionMap = new HashMap<>();
    private final String latestVersion = "20221204";
    private boolean init = false;
    private static volatile Updater instance;

    public void init() {
        versionMap.put("20220811", new UpdateFunction() {
            @Override
            protected String targetVersion() {
                return "20221204";
            }

            @Override
            protected void update(FinalTech finalTech) {
                // TODO: what to update?
            }
        });
    }

    public void update(@Nonnull FinalTech finalTech) {
        if(!this.init) {
            this.init();
        }

        String currentVersion = FinalTech.getConfigManager().getString("version");
        if(latestVersion.equals(currentVersion)) {
            FinalTech.logger().info("You are using the latest version. Good luck!");
            return;
        }

        UpdateFunction updateFunction = versionMap.get(currentVersion);
        if(updateFunction == null) {
            FinalTech.logger().info("You are using the unknown version. Version updater is disabled.");
        } else {
            String targetVersion = this.latestVersion;
            FinalTech.logger().info("Version " + currentVersion + " is detected! Version updater start to work...");
            while (updateFunction != null && !latestVersion.equals(currentVersion)) {
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
