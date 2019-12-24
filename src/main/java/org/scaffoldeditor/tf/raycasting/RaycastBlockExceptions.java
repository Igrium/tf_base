package org.scaffoldeditor.tf.raycasting;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;

/**
 * This class keeps track of all the blocks that rays can pass through.
 * @author Sam54123
 */
public class RaycastBlockExceptions {
    public static List<Material> blockExceptions;
    static {
        blockExceptions = new ArrayList<Material>();
        List<Material> b = blockExceptions;
        
        b.add(Material.AIR);
        b.add(Material.CAVE_AIR);
        b.add(Material.OAK_SAPLING);
        b.add(Material.SPRUCE_SAPLING);
        b.add(Material.BIRCH_SAPLING);
    }
}
