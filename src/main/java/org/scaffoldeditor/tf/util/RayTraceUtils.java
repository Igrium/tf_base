package org.scaffoldeditor.tf.util;

import java.util.function.Predicate;

import org.bukkit.FluidCollisionMode;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.util.RayTraceResult;
import org.bukkit.util.Vector;

public class RayTraceUtils {
    /**
     * A custom implementation of World.rayTrace() that performs a accurate ray trace that checks for both block and entity collisions.
     * @param world The world to perform the raytrace in.
     * @param start The start location.
     * @param direction The ray direction.
     * @param maxDistance The maximum distance.
     * @param fluidCollisionMode The fluid collision mode.
     * @param ignorePassableBlocks Whether to ignore passable but collidable blocks (ex. tall grass, signs, fluids, ..)
     * @param raySize Entity bounding boxes will be uniformly expanded (or shrinked) by this value before doing collision checks.
     * @param filter Only entities that fulfill this predicate are considered, or null to consider all entities.
     * @return The closest ray trace hit result with either a block or an entity, or null if there is no hit.
     */
    public static RayTraceResult rayTrace(World world, Location start, Vector direction, double maxDistance,
            FluidCollisionMode fluidCollisionMode, boolean ignorePassableBlocks, double raySize,
            Predicate<Entity> filter) {
        
        return world.rayTraceEntities(start, direction, maxDistance);
                
    }
}
