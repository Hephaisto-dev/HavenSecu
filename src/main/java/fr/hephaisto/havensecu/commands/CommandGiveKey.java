package fr.hephaisto.havensecu.commands;

import fr.hephaisto.havensecu.managers.Managers;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class CommandGiveKey implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(sender instanceof Player){
            Player player = (Player)sender;
            if (player.isOp()){
                if(args.length != 1){
                    player.sendMessage("§aExecution: /givekey <level> (1 à 5)");
                    return false;
                }
                int level = Integer.parseInt(args[0]);
                ItemStack stack = new ItemStack(Material.TRIPWIRE_HOOK);
                ItemMeta meta = stack.getItemMeta();
                meta.setDisplayName("Clé niveau "+level);
                meta.addEnchant(Enchantment.DURABILITY,level,true);
                meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
                stack.setItemMeta(meta);
                player.getInventory().addItem(stack);
                player.sendMessage(ChatColor.GREEN + "Don d'une clé de niveau "+level);
            }else{
                player.sendMessage("§cVous n'avez pas la permission d'executer cette commande !");
            } return false;
        }else{
            sender.sendMessage("Vous devez être un joueur pour éxecuter cette commande !");
        }
        return false;
    }
}
