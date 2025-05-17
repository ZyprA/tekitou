package net.zypr.setHome;

import org.bukkit.Location;

import java.util.HashMap;
import java.util.UUID;

public class HomeMap {
    private HashMap<UUID, Location> locationHashMap = new HashMap<>();

    public void addPlayerLocation(UUID uuid, Location location) {
        locationHashMap.put(uuid, location);
    }


    public void setLocationHashMap(HashMap<UUID, Location> locationHashMap) {
        this.locationHashMap = locationHashMap;
    }

    public HashMap<UUID, Location> getLocationHashMap() {
        return this.locationHashMap;
    }

    public Location getPlayerLocation(UUID uuid) {
        return locationHashMap.get(uuid);
    }
}
