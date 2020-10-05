package fr.hephaisto.havensecu;

import fr.hephaisto.havensecu.managers.Managers;
import org.bukkit.plugin.java.JavaPlugin;

public final class HavenSecu extends JavaPlugin {
    Managers managers = new Managers();
    @Override
    public void onEnable() {
        managers.load(this);
    }

    @Override
    public void onDisable() {
        managers.unload();
    }
}
