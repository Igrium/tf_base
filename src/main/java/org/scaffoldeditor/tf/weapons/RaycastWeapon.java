package org.scaffoldeditor.tf.weapons;

import java.util.Random;

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
    
    /**
     * Perform a raycast with the weapon.
     * @param player Player who fired the weapon.
     */
    protected void performRaycast(Player player) {
        // Offset the start location so the player doesn't shoot themself.
        Location startLocation = player.getEyeLocation().add(player.getEyeLocation().getDirection().multiply(weaponStartOffset));
        
        // Do random spread.
        Location direction = player.getEyeLocation();
        float randomSpread = getRandomSpread();
        
        if (randomSpread > 0) {
            Random random = new Random();
            float spreadPitch = random.nextFloat() * randomSpread;
            direction.setPitch((float) (random.nextBoolean() ? direction.getPitch() - spreadPitch : direction.getPitch() + spreadPitch));
            
            double spreadYaw = random.nextDouble() * randomSpread;
            direction.setYaw((float) (random.nextBoolean() ? direction.getYaw() - spreadYaw : direction.getYaw() + spreadYaw));
        }
        
        RayTraceResult result = player.getWorld().rayTrace(
                startLocation, direction.getDirection(), getMaxFireDistance(),
                FluidCollisionMode.NEVER, true, 0.1, null);
        
        if (result == null) {
            return;
        }
        
        if (result.getHitEntity() != null) {
            onHitEntity(result.getHitEntity(), player, result);
        } else if (result.getHitBlock() != null) {
            onHitBlock(result.getHitBlock(), player, result);
        }
        
    }
    
    /**
     * Get the weapon's max firing distance.
     * @return Max fire distance.
     */
    protected abstract double getMaxFireDistance();
    
    /**
     * Get the weapon's max random spread.
     * @return Max random spread (in degrees)
     */
    protected abstract float getRandomSpread();
    
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
