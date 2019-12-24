package org.scaffoldeditor.tf.weapons;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class TestWeapon extends Weapon {

    public TestWeapon(ItemStack item) {
        super(item);
    }

    @Override
    public boolean fire(Player player) {
        player.getServer().broadcastMessage(player.getName() + " has fired a weapon!");
        System.out.println("Weapon fired!");
        return true;
    }

}
