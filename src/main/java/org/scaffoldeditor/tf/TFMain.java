package org.scaffoldeditor.tf;

import org.bukkit.plugin.java.JavaPlugin;
import org.scaffoldeditor.tf.weapons.Shotgun;
import org.scaffoldeditor.tf.weapons.TestWeapon;
import org.scaffoldeditor.tf.weapons.Weapon;
import org.scaffoldeditor.tf.weapons.WeaponEvents;

public class TFMain extends JavaPlugin {

    @Override
    public void onEnable() {
        super.onEnable();
        saveDefaultConfig();
        
        // Setup weapons.
        String weaponNBTName = this.getConfig().getString("weapon_nbt_name");
        this.getServer().getPluginManager().registerEvents(new WeaponEvents(weaponNBTName), this);
        registerWeapons();
    }
    
    @Override
    public void onDisable() {
        super.onDisable();
    }
    
    public void registerWeapons() {
        Weapon.registerWeapon("tf:testweapon", TestWeapon.class);
        Weapon.registerWeapon("tf:shotgun", Shotgun.class);
    }
}
