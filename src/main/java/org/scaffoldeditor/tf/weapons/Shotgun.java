package org.scaffoldeditor.tf.weapons;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.Effect;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.RayTraceResult;

public class Shotgun extends RaycastWeapon {
    
    // Keep track of the amount of times each entity has been hit so the proper amount of damage can be administered.
    private Map<LivingEntity, Integer> hitCount = new HashMap<LivingEntity, Integer>();

    public Shotgun(ItemStack item) {
        super(item);
    }
    
    @Override
    public boolean fire(Player player) {
        if (canFire()) {
            // Fire all the rays
            for (int i = 0; i < 10; i++) {
                performRaycast(player);
            }
            
            // Do the appropriate amount of damage.
            int damageAmount = 1;
            for (LivingEntity e : hitCount.keySet()) {
                e.damage(damageAmount * hitCount.get(e));
            }
            
            return true;
            
        } else {
            return false;
        }
    }

    @Override
    protected double getMaxFireDistance() {
        return 100;
    }

    @Override
    protected float getRandomSpread() {
        return 0;
    }

    @Override
    protected void onHitEntity(Entity entity, Player player, RayTraceResult rayTraceResult) {
        entity.getWorld().playEffect(rayTraceResult.getHitPosition().toLocation(entity.getWorld()), Effect.SMOKE, 31);
        
        try {
            LivingEntity livingEntity = (LivingEntity) entity;
            if (livingEntity != null) {
                // Keep track of how many times entity was hit for proper damage handling.
                if (hitCount.containsKey(entity)) {
                    hitCount.put(livingEntity, hitCount.get(entity) + 1);
                } else {
                    hitCount.put(livingEntity, 1);
                }
            }
        } catch (ClassCastException e) {} // Stop console from complaining.
        
    }

    @Override
    protected void onHitBlock(Block block, Player player, RayTraceResult rayTraceResult) {
        block.getWorld().playEffect(rayTraceResult.getHitPosition().toLocation(block.getWorld()), Effect.SMOKE, 31);
        
    }

    @Override
    public boolean canFire() {
        return true;
    }

}
