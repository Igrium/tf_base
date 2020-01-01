package org.scaffoldeditor.tf.custom;

import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.plugin.Plugin;
import org.bukkit.util.Vector;

import de.tr7zw.changeme.nbtapi.NBTEntity;


/**
 * Interface for defining entities with custom behavior.
 * @author Sam54123
 */
public interface CustomEntity {
    
    /**
     * Get the type of Minecraft entity that this entity uses.
     * @return The required base entity type. (null if it doesn't matter)
     */
    public EntityType getBaseEntityType();
    
    /**
     * Get the Minecraft entity this custom entity is using.
     * @return The entity this custom entity is using.
     */
    public Entity getBaseEntity();
    
    /**
     * Get this entity's location.
     * @return The entity location.
     */
    public Location getLocation();
    
    /**
     * Teleport this entity to the target location.
     * @param location New location to teleport this entity to.
     */
    public void teleport(Location location);
    
    /**
     * Get the entity's current velocity.
     * @return Current traveling velocity of this entity.
     */
    public Vector getVelocity();
    
    /**
     * Set this entity's velocity.
     * @param velocity New velocity to travel with.
     */
    public void setVelocity(Vector velocity);
    
    /**
     * Get a CustomEntity from a Minecraft entity.
     * @param entity Minecraft entity to look in.
     * @param entityNbtName The name of the NBT tag connecting the MC entity to the custom entity.
     * @return CustomEntity the Minecraft entity houses. Null if non-existant.
     */
    public static CustomEntity fromMCEntity(Entity entity, String entityNbtName) {
        NBTEntity nbtEnt = new NBTEntity(entity);
        
        if (nbtEnt.hasKey(entityNbtName)) {
            Integer hashCode = nbtEnt.getInteger(entityNbtName);
            if (hashCode != null) {
                return EntityManager.getEntity(hashCode);
            }
        }
        
        return null;
    }
}
