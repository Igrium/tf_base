package org.scaffoldeditor.tf.raycasting;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.util.Vector;

/**
 * A utility class for performing raycasts.
 * @author Sam54123
 */
public class RaycastUtils {
    
    /**
     * A struct containing the data obtained by a raycast.
     * @author Sam54123
     */
    public static class RaycastReturnInfo {
        /**
         * The entity the raycast hit. Null if it hit a block.
         */
        public final Entity entityHit;
        
        /**
         * The block the raycast hit. Null if it hit an entity.
         */
        public final Block blockHit;
        
        /**
         * The location at which the raycast hit.
         */
        public final Location hitLocation;
        
        /**
         * The distance the raycast traveled.
         */
        public final double distance;
        
        /**
         * How many entities this raycast penetrated on the way here.
         */
        public final int penetrationCount;
        
        private RaycastReturnInfo(Entity entityHit, Block blockHit, Location hitLocation, double distance,
                int penetrationCount) {
            this.entityHit = entityHit;
            this.blockHit = blockHit;
            this.hitLocation = hitLocation;
            this.distance = distance;
            this.penetrationCount = penetrationCount;
        }
    }
    
    /**
     * An interface that supplies a method for a raycast to call every step.
     * @author Sam54123
     */
    public interface RaycastStep {
        /**
         * The method to call on each step.
         * @param stepNumber Which step this is.
         */
        public abstract void stepMethod(int stepNumber);
    }
    
    /**
     * The distance tolerance of the raycast.
     */
    private static double tolerance = 0.1;
    
    /**
     * Perform a raycast.
     * @param world The world to perform the raycast in.
     * @param start The location to start the raycast.
     * @param end The location to end the raycast.
     * @param entities The list of entities to consider hitting.
     * @param penetrateAmount How many entities should this raycast penetrate? (-1 = infinite)
     * @param stepDistance The distance to move between each step. Larger steps are more performant, but less accurate (may miss their target).
     * @param stepMethod A method to call each step.
     * @return The info of all the objects this raycast hit.
     */
    public static List<RaycastReturnInfo> Raycast(World world, Vector start, Vector end, List<Entity> entities, int penetrateAmount,
            double stepDistance, RaycastStep stepMethod) {
        
        Vector rayTriangle = new Vector(0,0,0).copy(end).subtract(start);
        
        // The the total proposed length of the raycast.
        double rayLength = rayTriangle.length();
        
        // The scale factor between the raycast triangle and the 
        double scaleFactor = rayLength / stepDistance;
        
        // The vector that the ray will use to step through the grid.
        Vector stepVector = new Vector(0,0,0).copy(rayTriangle).multiply(scaleFactor);
                
        /* Prepare to perform the raycast */
        List<RaycastReturnInfo> returnInfo = new ArrayList<RaycastReturnInfo>();
        Vector head = new Vector(0,0,0).copy(start);
        
        
        // Calculate the amount of steps required.
        int numSteps = (int) Math.ceil(rayLength / stepDistance);
        
        // Perform raycast
        int penetrationCount = 0;
        for (int step = 0; step < numSteps; step++) {
            head.add(stepVector);
            
            if (stepMethod != null) {
                stepMethod.stepMethod(step);
            }
            
            // Check for block
            Block b = head.toLocation(world).getBlock();
            if (b != null && b.getType() != Material.AIR) {
                returnInfo.add(new RaycastReturnInfo(null, b, head.toLocation(world), start.distance(head), penetrationCount));
                if (!b.isPassable() && !RaycastBlockExceptions.blockExceptions.contains(b.getType())) {
                    return returnInfo;
                }
            }
        }
        entities.get(1).getBoundingBox();
        
        return returnInfo;
    }
    
    /**
     * Perform a raycast.
     * @param world The world to perform the raycast in.
     * @param start The location to start the raycast.
     * @param end The location to end the raycast.
     * @param entities The list of entities to consider hitting.
     * @param penetrateAmount How many entities should this raycast penetrate? (-1 = infinite)
     * @return The info of all the objects this raycast hit.
     */
    public static List<RaycastReturnInfo> Raycast(World world, Vector start, Vector end, List<Entity> entities, int penetrateAmount) {
        return Raycast(world, start, end, entities, penetrateAmount, 0.05, null);
    }
}
