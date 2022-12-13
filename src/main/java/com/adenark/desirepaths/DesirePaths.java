package com.adenark.desirepaths;

import java.util.List;
import org.bukkit.Bukkit;
import org.bukkit.event.HandlerList;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public class DesirePaths extends JavaPlugin {
    public static DesirePaths getInstance() {
        Plugin plugin = Bukkit.getServer().getPluginManager().getPlugin("DesirePaths");
        if (!(plugin instanceof DesirePaths)) {
            throw new RuntimeException("'DesirePaths' not found.");
        } else {
            return (DesirePaths)plugin;
        }
    }

    public static List<String> BIOMES_TO_GENERATE_GRAVEL;

    private void loadConfiguredVariables() {
        BIOMES_TO_GENERATE_GRAVEL = (List<String>) getConfig().getList("biomes-to-generate-gravel");
    }

    @Override
    public void onEnable() {
        saveDefaultConfig();
        loadConfiguredVariables();
        getServer().getPluginManager().registerEvents(new Paths(), this);
    }
    
    @Override
    public void onDisable() {
        HandlerList.unregisterAll(this);
    }
}
