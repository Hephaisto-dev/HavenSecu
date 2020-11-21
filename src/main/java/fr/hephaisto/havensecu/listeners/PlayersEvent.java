package fr.hephaisto.havensecu.listeners;
import fr.hephaisto.havensecu.managers.Managers;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.data.type.Door;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;

public class PlayersEvent implements Listener {
    @EventHandler
    public void onDoorOpen (PlayerInteractEvent e){
        if (e.getHand() == EquipmentSlot.HAND && e.getAction()==Action.RIGHT_CLICK_BLOCK && e.getClickedBlock().getType() == Material.IRON_DOOR){
            Location location = e.getClickedBlock().getLocation();
            Location location1 = new Location(location.getWorld(),location.getX(),location.getY()-1,location.getZ());
            Managers m = Managers.getManagers();
            ItemStack stack = e.getPlayer().getInventory().getItemInMainHand();
            if(stack.getType().equals(Material.MAGENTA_DYE)) {
                if (location1.getBlock().getType() == Material.IRON_DOOR) {
                    m.openCloseDoor(location1, m.checkAllowed(location1, e.getPlayer()));
                } else if (location1.getBlock().getType() != Material.IRON_DOOR) {
                    m.openCloseDoor(location, m.checkAllowed(location, e.getPlayer()));
                }
            }
        }
    }

    @EventHandler
    public void onDestroy (BlockBreakEvent e){
        if (e.getPlayer().isOp()){
            return;
        }
        if (Managers.getManagers().containDoorLocation(e.getBlock().getLocation())
                || Managers.getManagers().containDoorLocation(e.getBlock().getLocation().add(0,-1,0))
                || Managers.getManagers().containDoorLocation(e.getBlock().getLocation().add(0,1,0))){
            e.getPlayer().sendMessage(ChatColor.RED + "Vous ne pouvez pas casser cette porte");
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onExplode(EntityExplodeEvent e) {
        for (Block b : e.blockList()) {
            if (b.getType() == Material.IRON_DOOR) {
                e.setCancelled(true);
            }
        }
    }
}
