package fr.hephaisto.havensecu.managers;

import fr.hephaisto.havensecu.HavenSecu;
import fr.hephaisto.havensecu.listeners.PlayersEvent;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;

public class EventsManager {
    public static void register(HavenSecu instance) {
        PluginManager pm = Bukkit.getPluginManager();

        pm.registerEvents(new PlayersEvent(), instance);
    }
}
