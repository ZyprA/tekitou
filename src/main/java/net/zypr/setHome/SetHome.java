package net.zypr.setHome;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public final class SetHome extends JavaPlugin {
    private HomeMap homeMapManager;
    private static SetHome instance;

    @Override
    public void onEnable() {
        instance = this;
        // Plugin startup logic

        saveDefaultConfig();

        this.homeMapManager = new HomeMap();

        loadMapConfig();
        saveResource("locationData.yml", false);

        getCommand("sthome").setExecutor(new Commands());
        getCommand("home").setExecutor(new Commands());


    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        saveMapConfig();
    }

    private void loadMapConfig() {
        File file = new File(getDataFolder(), "locationData.yml");
        FileConfiguration config = YamlConfiguration.loadConfiguration(file);

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
        if (locationHashMap == null) return;
        File file = new File(getDataFolder(), "locationData.yml");
        FileConfiguration config = YamlConfiguration.loadConfiguration(file);

        for (Map.Entry<UUID, Location> entry : locationHashMap.entrySet()) {
            UUID uuid = entry.getKey();
            Location location = entry.getValue();
            String path = "player-locations." + uuid.toString();

            config.set(path, location);
        }

        try {
            config.save(file);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public static SetHome getInstance() {
        return instance;
    }
    public HomeMap getHomeMapManager() {
        return this.homeMapManager;
    }
}
