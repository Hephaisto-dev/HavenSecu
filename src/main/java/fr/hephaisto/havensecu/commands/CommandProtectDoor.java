package fr.hephaisto.havensecu.commands;

import fr.hephaisto.havensecu.managers.Managers;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandProtectDoor implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(sender instanceof Player){
            Player player = (Player)sender;
            if (player.isOp()){
                if(args.length != 1){
                    player.sendMessage("§aExecution: /protectdoor <level> (1 à 5)");
                    return false;
                }
                int level = Integer.parseInt(args[0]);
                Location location = player.getTargetBlock(null, 4).getLocation();
                Managers.getManagers().addDoors(player,"portes",location,level);
            }else{
                player.sendMessage("§cVous n'avez pas la permission d'executer cette commande !");
            } return false;
        }else{
            sender.sendMessage("Vous devez être un joueur pour éxecuter cette commande !");
        }
        return false;
    }
}
