package org.scaffoldeditor.tf.custom;

import java.util.HashMap;
import java.util.Map;

/**
 * Static class that manages custom entities.
 * @author Sam54123
 */
public final class EntityManager {
    /**
     * A map of all registered entities with their hash values.
     */
    private static Map<Integer, CustomEntity> entities = new HashMap<Integer, CustomEntity>();
    
    /**
     * Register a custom entity object. All custom entity objects must be registered before they can be used.
     * @param entity Entity to register.
     */
    public static void registerEntity(CustomEntity entity) {
        entities.put(entity.hashCode(), entity);
    }
    
    /**
     * Unregister a custom entity object.
     * @param entity Entity to unregister.
     */
    public static void unregisterEntity(CustomEntity entity) {
        entities.remove(entity.hashCode());
    }
    
    /**
     * Unregister a custom entity object by its hash code.
     * @param hashCode Entity hash code.
     */
    public static void unregisterEntity(int hashCode) {
        entities.remove(hashCode);
    }
    
    /**
     * Get an entity by its hash code.
     * @param hashCode
     * @return Entity belonging to the hash code. Null if entity does not exist.
     */
    public static CustomEntity getEntity(int hashCode) {
        return entities.get(hashCode);
    }
    
}
