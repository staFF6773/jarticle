package tech.execsuroot.jarticle;

import lombok.Getter;
import org.bukkit.plugin.java.JavaPlugin;
import tech.execsuroot.jarticle.command.CommandFeature;
import tech.execsuroot.jarticle.config.ConfigFeature;
import tech.execsuroot.jarticle.feature.FeatureManager;
import tech.execsuroot.jarticle.feature.FeatureManagerImpl;
import tech.execsuroot.jarticle.feature.PluginFeature;
import tech.execsuroot.jarticle.hook.HookFeature;
import tech.execsuroot.jarticle.util.Log;

/**
 * This class represents the main class of the plugin.
 */
public class JarticlePlugin extends JavaPlugin {

    /**
     * The instance of the plugin.
     * Used to access the plugin.
     */
    @Getter
    private static JarticlePlugin instance;
    /**
     * The feature manager of the plugin.
     * Used to get access to the features.
     */
    @Getter
    private final FeatureManager featureManager;

    public JarticlePlugin() {
        super();
        instance = this;
        Log.setLogger(getLogger());
        this.featureManager = new FeatureManagerImpl(this, getFeatures());
    }

    private PluginFeature[] getFeatures() {
        return new PluginFeature[]{
                new CommandFeature(),
                new ConfigFeature(),
                new HookFeature()
        };
    }

    @Override
    public void onLoad() {
        Log.info("Loading plugin features...");
        boolean loadedFeatures = featureManager.loadFeatures();
        if (loadedFeatures) {
            Log.info("✓ All features loaded successfully.");
        } else {
            Log.warning("✗ An error occurred while loading features. Some functionalities may not be available.");
        }
    }

    @Override
    public void onEnable() {
        Log.info("Enabling plugin features...");
        boolean enabledFeatures = featureManager.enableFeatures();
        if (enabledFeatures) {
            Log.info("✓ All features enabled successfully.");
        } else {
            Log.severe("✗ An error occurred while enabling features. The plugin will be disabled...");
            getServer().getPluginManager().disablePlugin(this);
        }
    }

    @Override
    public void onDisable() {
        Log.info("Disabling plugin features...");
        boolean disabledFeatures = featureManager.disableFeatures();
        if (disabledFeatures) {
            Log.info("✓ All features disabled successfully.");
        } else {
            Log.warning("✗ An error occurred while disabling features. Some resources may not have been released properly.");
        }
    }
}
