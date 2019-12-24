package org.scaffoldeditor.tf.weapons;

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
        // TODO Auto-generated constructor stub
    }

    @Override
    public boolean fire(Player player) {
        return false;
    }

}
