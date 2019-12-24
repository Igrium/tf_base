package org.scaffoldeditor.tf.raycasting;

import java.awt.geom.Rectangle2D;
import java.util.*;

import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;

/**
 * Utility that isolates entities along a line.
 * @author JRL1004
 * @author Zombie_Striker
 * @see <a href="https://bukkit.org/threads/using-rays-to-quickly-and-accurately-detect-hitbox-collisions.441877/">Raycasting in Bukkit</a>
 */
public class Ray {

    public double startX, startY, startZ, endX, endY, endZ;
    private Set<Entity> entitySet;
    private Class<?> CRAFT_ENTITY;
    private final String SERVER_VERSION;

    public Ray(double startX, double startY, double startZ, double endX, double endY, double endZ,
            List<Entity> entities) {
        String name = Bukkit.getServer().getClass().getName();
        SERVER_VERSION = name.substring(name.indexOf("craftbukkit.") + "craftbukkit.".length()).substring(0,
                name.indexOf("."));
        try {
            CRAFT_ENTITY = Class.forName("org.bukkit.craftbukkit." + SERVER_VERSION + ".entity.CraftEntity");
        } catch (Exception e) {
        }
        this.startX = startX;
        this.startY = startY;
        this.startZ = startZ;
        this.endX = endX;
        this.endY = endY;
        this.endZ = endZ;
        entitySet = new HashSet<Entity>(entities);
    }

    public void removeNonColliding(PlaneType type) {
        Iterator<Entity> entityIterator = entitySet.iterator();
        while (entityIterator.hasNext()) {
            try {
                Object handle = (CRAFT_ENTITY.cast(entityIterator.next())).getClass().getMethod("getHandle")
                        .invoke((CRAFT_ENTITY.cast(entityIterator.next())));
                Object aabb = handle.getClass().getMethod("getBoundingBox").invoke(handle);

                double rectX, rectXLength, rectZ, rectZLength;
                if (type == PlaneType.XZ || type == PlaneType.XY) {
                    rectX = aabb.getClass().getField("a").getDouble(aabb);
                    rectXLength = aabb.getClass().getField("d").getDouble(aabb) - rectX;
                } else {
                    rectX = aabb.getClass().getField("c").getDouble(aabb);
                    rectXLength = aabb.getClass().getField("f").getDouble(aabb) - rectX;
                }
                if (type == PlaneType.XY || type == PlaneType.YZ) {
                    rectZ = aabb.getClass().getField("b").getDouble(aabb);
                    rectZLength = aabb.getClass().getField("e").getDouble(aabb) - rectZ;
                } else {
                    rectZ = aabb.getClass().getField("c").getDouble(aabb);
                    rectZLength = aabb.getClass().getField("f").getDouble(aabb) - rectZ;
                }
                boolean collided = new Rectangle2D.Double(rectX, rectZ, rectXLength, rectZLength).intersectsLine(startX,
                        startZ, endX, endZ);
                if (!collided)
                    entityIterator.remove();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void removeAllNonColliding() {
        for (PlaneType p : PlaneType.values())
            removeNonColliding(p);
    }

    public Set<Entity> getEntities() {
        return entitySet;
    }
}

enum PlaneType {
    XY, XZ, YZ;
}
