package org.scaffoldeditor.tf.weapons;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import de.tr7zw.nbtapi.NBTItem;

/**
 * Class responsible for triggering weapon code.
 * @author Sam54123
 */
public class WeaponEvents implements Listener {
    
    String weaponNBTName;
    
    public WeaponEvents(String weaponNbtName) {
        this.weaponNBTName = weaponNbtName;
    }
    
    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent e) {
        Player player = e.getPlayer();
        
        if (e.getAction() == Action.LEFT_CLICK_BLOCK || e.getAction() == Action.LEFT_CLICK_AIR) {
            primaryFire(player);
        }
    }
    
    /**
     * Force the player to primary fire.
     */
    public void primaryFire(Player player) {
        ItemStack item = player.getInventory().getItemInMainHand();
        
        // Load weapon in hand.
        NBTItem nbti = new NBTItem(item);
        String weaponName = nbti.getString(weaponNBTName);
        
        // Only attempt fire if item is a TF weapon.
        if (weaponName != null && Weapon.exists(weaponName)) {
            Weapon weapon = Weapon.getWeapon(weaponName, item);
            weapon.fire(player);
        }
    }
}
