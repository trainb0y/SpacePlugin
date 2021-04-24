package com.trainb0y.spaceplugin.events;

import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerToggleFlightEvent;

public class DoubleJumpEvents implements Listener {

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        if ((player.getGameMode() != GameMode.CREATIVE)
            && (player.getLocation().subtract(0,1,0).getBlock().getType() != Material.AIR)
                && (!player.isFlying())) {
            player.setAllowFlight(true);
        }
    }
    @EventHandler
    public void onPlayerToggleFlight(PlayerToggleFlightEvent event) {
        Player player = event.getPlayer();
        if (player.getGameMode() == GameMode.CREATIVE) {
            return;
        }
        event.setCancelled(true);
        player.setAllowFlight(false);
        player.setFlying(false);
        player.setVelocity(player.getLocation().getDirection().multiply(1.2).setY(1));


    }
    @EventHandler
    public void onFallDamage(EntityDamageEvent event) {

    }

}
