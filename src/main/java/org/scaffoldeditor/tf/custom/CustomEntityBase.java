package org.scaffoldeditor.tf.custom;

import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.plugin.Plugin;
import org.bukkit.util.Vector;

/**
 * A basic implementation of CustomEntity.
 * @author Sam54123
 */
public class CustomEntityBase implements CustomEntity {
    
    /**
     * The Minecraft entity this entity is using.
     */
    protected final Entity baseEntity;
    
    /**
     * The parent plugin of the entity.
     */
    protected final Plugin plugin;
    
    /**
     * Create a new instance.
     * @param baseEntity Minecraft entity to parent to.
     * @param The plugin to parent to.
     * @param entityNbtName The name of the NBT tag connecting the MC entity to this entity.
     */
    public CustomEntityBase(Entity baseEntity, Plugin plugin, String entityNbtName) {
        this.baseEntity = baseEntity;
        this.plugin = plugin;
        
        // Register with the entity manager.
        EntityManager.registerEntity(this);
        
    }

    @Override
    public EntityType getBaseEntityType() {
        return null;
    }

    @Override
    public Entity getBaseEntity() {
        return baseEntity;
    }

    @Override
    public Location getLocation() {
        return baseEntity.getLocation();
    }

    @Override
    public void teleport(Location location) {
        baseEntity.teleport(location);
    }

    @Override
    public Vector getVelocity() {
        return baseEntity.getVelocity();
    }

    @Override
    public void setVelocity(Vector velocity) {
        baseEntity.setVelocity(velocity);
    }
}
