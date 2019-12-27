package org.scaffoldeditor.tf.weapons;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.util.Vector;
import org.scaffoldeditor.tf.projectiles.Rocket;

public class RocketLauncher extends Weapon {
    
    protected double weaponStartOffset = 4;
    

    public RocketLauncher(ItemStack item, Plugin plugin) {
        super(item, plugin);
    }

    @Override
    public boolean fire(Player player) {
        Location startLocation = player.getEyeLocation().add(player.getEyeLocation().getDirection().multiply(weaponStartOffset));
        Vector velocity = startLocation.getDirection();
        
        Rocket.launch(startLocation, velocity, player, plugin);
        return true;
    }

    @Override
    public boolean canFire() {
        return true;
    }

}
