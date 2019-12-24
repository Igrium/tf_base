package org.scaffoldeditor.tf.weapons;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

/**
 * Represents a Team Fortress weapon.
 * <br>
 * Note: Weapons do not properly retain instance variables. Store data in item NBT.
 * @author Sam54123
 */
public abstract class Weapon {
    
    /**
     * The ItemStack this weapon is paired with (for data storage).
     */
    protected final ItemStack item;
    
    /**
     * Create a weapon object.
     * @param item ItemStack to pair with (for data storage).
     */
    public Weapon(ItemStack item) {
        this.item = item;
    }
    
    /**
     * Called when the weapon is fired.
     * @param player Player who fired the weapon.
     * @return Was fire successful? (i.e. has enough ammo)
     */
    public abstract boolean fire(Player player);
    
    /**
     * Tests to see if the weapon can fire.
     * @return Can the weapon fire?
     */
    public abstract boolean canFire();
    
    /**
     * Check if this weapon has a secondary fire.
     * @return Has secondary fire?
     */
    public boolean hasSecondaryFire() { return false; };
    
    /**
     * Called when the weapon's secondary fire is activated.
     * @param player
     */
    public void secondaryFire(Player player) {};
    
    /**
     * Tests to see if the weapon's secondary fire is ready to be used.
     * @return Can secondary fire?
     */
    public boolean canSecondaryFire() { return false; };
    
    /**
     * Determine whether this weapon can be reloaded.
     * @return Can be reloaded?
     */
    public boolean canReload() { return false; };
    
    /**
     * Attempt to reload the weapon.
     */
    public void reload() {};
    
    /**
     * Get how much ammo the weapon currently has in its clip.
     * Returns 0 if weapon doesn't use ammo.
     * @return Amount of ammo.
     */
    public int getAmmo() { return 0; };
    
    /**
     * Get how much ammo the weapon can store in its clip.
     * Returns 0 if weapon doesn't use ammo.
     * @return Max ammo in clip.
     */
    public int getMaxAmmo() { return 0; };
    
    /**
     * Set the amount of ammo in the weapon's clip.
     * Should cap at max ammo.
     * @param ammo. Amount of ammo to set.
     */
    public void setAmmo(int ammo) {};
    
    /**
     * Get how much ammo this weapon has in storage (not in the clip).
     * Returns 0 if weapon doesn't use ammo.
     */
    public int getStorageAmmo() { return 0; };
    
    /**
     * Get how much ammo this weapon can store (not in the clip).
     * Returns 0 if weapon doesn't use ammo.
     * @return Max ammo.
     */
    public int getMaxStorageAmmo() { return 0; };
    
    /**
     * Set the amount of ammo in the weapon's storage.
     * @param ammo Amount of ammo to set.
     */
    public void setTotalAmmo(int ammo) {};
    
    /**
     * A hashmap storing all the weapon types with their namespaced names.
     */
    private static Map<String, Class<? extends Weapon>> weapons = new HashMap<String, Class<? extends Weapon>>();
    
    /**
     * Register a new weapon type.
     * @param name Namespaced name of the weapon.
     * @param type Weapon class.
     */
    public static void registerWeapon(String name, Class<? extends Weapon> type) {
        weapons.put(name, type);
        System.out.println("Registered weapon "+name);
    };
    
    /**
     * Remove a weapon type.
     * @param name Namespaced name of the weapon.
     */
    public static void unregisterWeapon(String name) {
        weapons.remove(name);
        System.out.println("Unregistered weapon "+name);
    }
    
    /**
     * Checks if a weapon with a name exists and is registered.
     * @param name Namespaced name of the weapon,.
     * @return Does weapon exist?
     */
    public static boolean exists(String name) {
        return weapons.containsKey(name);
    }
    
    /**
     * Get a weapon class by its namespaced name.
     * @param name Namespaced name.
     * @return Weapon class.
     */
    public static Class<? extends Weapon> getWeaponClass(String name) {
        return weapons.get(name);
    };
    
    /**
     * Create a weapon by its namespaced name.
     * @param name Namespaced name.
     * @param item Item to pair entity with.
     * @return Weapon.
     */
    public static Weapon getWeapon(String name, ItemStack item) {
        if (!weapons.containsKey(name)) {
            return null;
        }
        
        Class<? extends Weapon> weaponClass = weapons.get(name);
        
        try {
            Weapon weapon;
            weapon = weaponClass.getDeclaredConstructor(new Class[] {ItemStack.class}).newInstance(item);
            return weapon;
        } catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException
                | NoSuchMethodException | SecurityException e) {
            e.printStackTrace();
            return null;
        }
    }
}
