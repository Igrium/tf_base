package org.scaffoldeditor.tf.weapons;

import org.bukkit.FluidCollisionMode;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.RayTraceResult;

/**
 * Weapon that raycasts in the fired direction, and hits entities along the way,
 * @author Sam54123
 *
 */
public abstract class RaycastWeapon extends Weapon {
    
    /**
     * How much to offset the raycast from its fire location (so the player doesn't shoot themself).
     */
    protected double weaponStartOffset = 2;

    public RaycastWeapon(ItemStack item) {
        super(item);
    }

    @Override
    public boolean fire(Player player) {
        if (!canFire()) {
            return false;
        }
        
        performRaycast(player);
        return true;
    }
    
    protected void performRaycast(Player player) {
     // Offset the start location so the player doesn't shoot themself.
        Location startLocation = player.getEyeLocation().add(player.getEyeLocation().getDirection().multiply(weaponStartOffset));
        RayTraceResult result = player.getWorld().rayTrace(
                startLocation, player.getEyeLocation().getDirection(), getMaxFireDistance(),
                FluidCollisionMode.NEVER, true, 0.1, null);
        
        if (result.getHitEntity() != null) {
            onHitEntity(result.getHitEntity(), player, result);
        } else if (result.getHitBlock() != null) {
            onHitBlock(result.getHitBlock(), player, result);
        }
        
    }
    
    /**
     * Get the weapon's max fireing distance.
     * @return Max fire distance.
     */
    protected abstract double getMaxFireDistance();
    
    /**
     * Called when this weapon hits an entity.
     * @param entity The entity that was hit.
     * @param player The player who fired the weapon.
     * @param rayTraceResult Information about the raycast this weapon performed.
     */
    protected abstract void onHitEntity(Entity entity, Player player, RayTraceResult rayTraceResult);
    
    /**
     * Called when this weapon hits a block.
     * @param block The block that was hit.
     * @param player The player who fired the weapon.
     * @param rayTraceResult Information about the raycast this weapon performed.
     */
    protected abstract void onHitBlock(Block block, Player player, RayTraceResult rayTraceResult);

}
