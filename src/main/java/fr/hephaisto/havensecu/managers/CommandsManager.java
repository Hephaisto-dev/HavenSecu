package fr.hephaisto.havensecu.managers;


import fr.hephaisto.havensecu.HavenSecu;
import fr.hephaisto.havensecu.commands.CommandGiveKey;
import fr.hephaisto.havensecu.commands.CommandProtectDoor;

public class CommandsManager {
    public static void register(HavenSecu instance) {
        instance.getCommand("protectdoor").setExecutor(new CommandProtectDoor());
        instance.getCommand("givekey").setExecutor(new CommandGiveKey());
    }
}
