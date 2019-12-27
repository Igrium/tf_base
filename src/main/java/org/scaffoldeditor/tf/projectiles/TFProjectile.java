package org.scaffoldeditor.tf.projectiles;

import org.bukkit.entity.Projectile;
import org.bukkit.util.Vector;

/**
 * Represents a tf2 projectile, such as a rocket or a grenade.
 * @author Sam54123
 *
 */
public interface TFProjectile {
    
    /**
     * Get the entity representing this projectile.
     * @return The projectile's entity.
     */
    public Projectile getProjectileEntity();
    
    /**
     * Set the velocity of the projectile.
     * @param velocity The velocity to set (in m/s).
     */
    public void setVelocity(Vector velocity);
    
    /**
     * Get the velocity of the projectile.
     * @return The velocity of the projectile (in m/s).
     */
    public Vector getVelocity();
    
}
