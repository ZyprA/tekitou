package net.zypr.setHome;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public final class SetHome extends JavaPlugin {
    private HomeMap homeMapManager;

    @Override
    public void onEnable() {
        // Plugin startup logic

        saveDefaultConfig();

        this.homeMapManager = new HomeMap();

        loadMapConfig();


    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        saveMapConfig();
    }

    private void loadMapConfig() {
        FileConfiguration config = YamlConfiguration.loadConfiguration(new File("locationData.yml"));

        ConfigurationSection section = config.getConfigurationSection("player-locations");
        if (section == null) return;

        for (String key : section.getKeys(false))  {
            try {
                UUID uuid = UUID.fromString(key);
                String path = "player-locations." + key;

                Location location = config.getLocation(path);
                homeMapManager.addPlayerLocation(uuid,location);
            } catch (IllegalArgumentException e) {
                Bukkit.getLogger().warning("無効なUUIDキー: " + key);
            }
        }

    }

    private void saveMapConfig() {
        HashMap<UUID, Location> locationHashMap = homeMapManager.getLocationHashMap();
        FileConfiguration config = YamlConfiguration.loadConfiguration(new File("locationData.yml"));

        ConfigurationSection section = config.getConfigurationSection("player-locations");
        for (Map.Entry<UUID, Location> entry : homeMapManager.getLocationHashMap().entrySet()) {
            UUID uuid = entry.getKey();
            Location location = entry.getValue();
            String path = "player-locations." + uuid.toString();

            config.set(path + uuid, location);
        }
    }
}
