package org.scaffoldeditor.tf.weapons;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

/**
 * Weapon that raycasts in the fired direction, and hits entities along the way,
 * @author Sam54123
 *
 */
public abstract class RaycastWeapon extends Weapon {

    public RaycastWeapon(ItemStack item) {
        super(item);
    }

    @Override
    public boolean fire(Player player) {
        return false;
    }
    
    /**
     * Called when the weapon hits an entity.
     * @param entity Entity that was hit.
     * @param player Player who fired the weapon.
     */
    protected abstract void onHitEntity(Entity entity, Player player);

}
