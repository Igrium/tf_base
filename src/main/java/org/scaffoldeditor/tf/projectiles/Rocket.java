package org.scaffoldeditor.tf.projectiles;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Egg;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.projectiles.ProjectileSource;
import org.bukkit.util.Vector;

/**
 * Represents a rocket from a Soldier's rocket launcher.
 * <br>
 * Velocity is all artificially stored and calculated for accuracy.
 * @author Sam54123
 */
public class Rocket implements TFProjectile, Listener {
    
    /**
     * The power of a rocket.
     */
    public final float POWER = 2;
    
    private Egg entity;
    
    // The current position of the rocket.
    private Location position;
    
    private Vector velocity = new Vector(0,0,0);
    
    // Used for calculating delta tick time.
    private long lastTick = System.currentTimeMillis();
    
    private int tickId;
    
    /**
     * Create a Rocket projectile.
     * @param entity Entity to create it on.
     * @param plugin The main plugin (used for server tick events)
     */
    public Rocket(Egg entity, Plugin plugin) {
        this.entity = entity;
        this.position = entity.getLocation();
        entity.setGravity(false);

        if (plugin != null) {
            tickId = entity.getServer().getScheduler().scheduleSyncRepeatingTask(plugin, new Runnable() {
                @Override
                public void run() { tick(); };
            }, 1 , 1);
        }  
    }

    @Override
    public Projectile getProjectileEntity() {
        return entity;
    }

    @Override
    public void setVelocity(Vector velocity) {
        this.velocity = velocity;
    }

    @Override
    public Vector getVelocity() {
       return velocity;
    }
    
    /**
     * Called every tick.
     */
    protected void tick() {
        // Store the time since the last tick, in seconds.
        double deltaTime = (System.currentTimeMillis() - lastTick)/1000;
        lastTick = System.nanoTime();
        
        if (!entity.isValid()) {
            explode();
        }
        
        position.add(velocity.clone().multiply(deltaTime));
        entity.teleport(position);
    }
    
    @EventHandler
    public void onProjectileHit(ProjectileHitEvent e) {
        System.out.println("hit!");
        if (e.getEntity().getEntityId() == entity.getEntityId()) {
            explode();
        }
    }
    
    /**
     * Force the rocket to explode.
     */
    public void explode() {
        entity.getServer().getScheduler().cancelTask(tickId);
        
        Entity shooter = (entity.getShooter() instanceof Entity) ? (Entity) entity.getShooter() : null;
        entity.getWorld().createExplosion(position, POWER, false, false, shooter);
        
        entity.remove();
    }
    
    /**
     * Launch a new rocket.
     * @param position Position at which to launch from.
     * @param velocity Velocity at which to launch at (in m/s).
     * @param owner The owner of the rocket (for kill count).
     * @param plugin Plugin to register the tick function with.
     * @return The new rocket.
     */
    public static Rocket launch(Location position, Vector velocity, ProjectileSource owner, Plugin plugin) {
        World world = position.getWorld();
        Egg entity = (Egg) world.spawnEntity(position, EntityType.EGG);
        System.out.println(velocity);
        Rocket rocket = new Rocket(entity, plugin);
        rocket.setVelocity(velocity);
        
        return null;
    }

}
