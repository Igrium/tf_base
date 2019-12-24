package org.scaffoldeditor.tf.weapons;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class TestWeapon extends RaycastWeapon {

    public TestWeapon(ItemStack item) {
        super(item);
    }

    @Override
    protected void onHitEntity(Entity entity, Player player) {
        player.getServer().broadcastMessage(player.getName()+" shot entity "+entity.getEntityId());
        
    }
}
