package fr.hephaisto.havensecu.managers;

import fr.hephaisto.havensecu.Door;
import fr.hephaisto.havensecu.HavenSecu;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.BlockState;
import org.bukkit.block.data.Openable;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.MaterialData;

import java.util.ArrayList;
import java.util.List;

public class Managers {
    private static HavenSecu instance;
    private static Managers managers;
    private List<Door> doors;

    public void load(HavenSecu instance) {
        Managers.instance = instance;
        managers = this;
        doors = new ArrayList<>();

        instance.saveDefaultConfig();

        loadDoors();

        Bukkit.getConsoleSender().sendMessage("§aLe plugin Haven Secu se lance !");

        CommandsManager.register(instance);
        EventsManager.register(instance);

    }

    public void unload() {
        Bukkit.getConsoleSender().sendMessage("§cLe plugin Haven Secu se ferme !");
    }

    public static Managers getManagers(){
        return managers;
    }

    private void loadDoors(){
        String path = "portes";
        for(int i = 0; i <= 100; i++) {
            if(instance.getConfig().getInt(path+".coordonnees_" + i + ".Y") != 0) {
                double X = instance.getConfig().getDouble(path + ".coordonnees_" + i + ".X");
                double Y = instance.getConfig().getDouble(path + ".coordonnees_" + i + ".Y");
                double Z = instance.getConfig().getDouble(path + ".coordonnees_" + i + ".Z");
                int level = instance.getConfig().getInt(path + ".coordonnees_" + i + ".Niveau");
                String world = instance.getConfig().getString(path + ".coordonnees_" + i + ".Monde");
                World world1 = Bukkit.getWorld("world");
                if (world != null) {
                    world1 = Bukkit.getWorld(world);
                }
                Location location = new Location(world1,X,Y,Z);
                Door door = new Door(location,i,level);
                doors.add(door);
            }
        }
    }
    public void addDoors(Player player, String path, Location loc, int level) {
        for(int i = 0; i <= 100; i++){
            if(instance.getConfig().getInt(path+".coordonnees_" + i + ".Y") == 0){
                double x = (int) loc.getX();
                double y = (int) loc.getY();
                double z = (int) loc.getZ();
                instance.getConfig().set(path+".coordonnees_" + i + ".X", x);
                instance.getConfig().set(path+".coordonnees_" + i + ".Y", y);
                instance.getConfig().set(path+".coordonnees_" + i + ".Z", z);
                instance.getConfig().set(path+".coordonnees_" + i + ".Monde", loc.getWorld().getName());
                instance.getConfig().set(path+".coordonnees_" + i + ".Niveau", level);
                instance.saveConfig();
                instance.reloadConfig();
                player.sendMessage("§aVotre nouvelle porte a été enregistrée avec succès en "+x+" "+y+""+z);
                Door door = new Door(new Location(loc.getWorld(),x,y,z),i,level);
                doors.add(door);
                break;
            }
        }
    }


    public boolean isDoorOpenNotAllowed (Location location, Player player){
        if (!containDoorLocation(location)){
            return false;
        }
        ItemStack stack = player.getInventory().getItemInMainHand();
        if (stack.getType() == Material.TRIPWIRE || stack.getType() == Material.TRIPWIRE_HOOK) {
            if (stack.getItemMeta().getEnchants().containsKey(Enchantment.DURABILITY)){
                int doorlevel = getDoorByLocation(location).getLevel();
                int tripwirelevel = stack.getItemMeta().getEnchantLevel(Enchantment.DURABILITY);
                return doorlevel >= tripwirelevel;
            }
        }
        else{
            return false;
        }
        return true;
    }

    public boolean containDoorLocation(Location location){
        for (Door door : doors){
            if (door.getLoc().getX() ==location.getX() && door.getLoc().getY() == location.getY() && door.getLoc().getZ() == location.getZ()){
                return true;
            }
        }
        return false;
    }

    public Door getDoorByLocation(Location location){
        for (Door door : doors){
            if (door.getLoc().getX() ==location.getX() && door.getLoc().getY() == location.getY() && door.getLoc().getZ() == location.getZ()){
                return door;
            }
        }
        return null;
    }

    public boolean checkAllowed(Location location, Player player1){
        if (isDoorOpenNotAllowed(location, player1)) {
            player1.sendMessage(ChatColor.RED + "Vous n'avez pas la bonne clef");
            return false;
        }
        return true;
    }

    public void openCloseDoor (Location location, boolean make){
        if (make) {
            Location location1 = new Location(location.getWorld(), location.getX(), location.getY() - 2, location.getZ());
            if (location1.getBlock().getType() == Material.REDSTONE_TORCH){
                location1.getBlock().setType(Material.DIRT);
            }
            else {
                location1.getBlock().setType(Material.REDSTONE_TORCH);
            }
        }
    }

}
