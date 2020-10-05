package fr.hephaisto.havensecu;

import org.bukkit.Location;


public class Door {

    private Location loc;
    private int id;
    private int level;

    public Door(Location location, int id, int level) {
        this.loc = location;
        this.id = id;
        this.level = level;
    }

    public int getId() {
        return id;
    }

    public Location getLoc() {
        return loc;
    }

    public int getLevel() {
        return level;
    }
}
