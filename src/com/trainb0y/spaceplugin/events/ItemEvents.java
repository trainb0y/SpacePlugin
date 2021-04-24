package com.trainb0y.spaceplugin.events;

import com.trainb0y.spaceplugin.items.ItemManager;
import org.bukkit.*;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;



public class ItemEvents implements Listener {

    public ArrayList<UUID> jetpackers = new ArrayList<>();
    public HashMap<UUID, Integer> jetpackFuel = new HashMap<UUID, Integer>();
    public int maxFuel = 1000;

    public boolean isSameCustomItem(ItemMeta meta1, ItemMeta meta2){
        // checks if item metas are the same, by checking name and lore
        if (!meta1.getDisplayName().equals(meta2.getDisplayName())) {
            return false;
        }
        return meta1.getLore().equals(meta2.getLore());
    }

    @EventHandler
    public static void onRightClickBlock(PlayerInteractEvent event) {
        if (event.getAction() == Action.RIGHT_CLICK_BLOCK) {
            if (event.getItem() != null) {
                // Avoids nullpointerexeptions when referencing item


                if (event.getItem().getItemMeta().equals(ItemManager.wand.getItemMeta())) {
                    Player player = event.getPlayer();
                    player.getWorld().createExplosion(player.getLocation(), 10.0f);
                    player.sendMessage("§6BOOM");
                }


            }


        }
    }
    @EventHandler
    public void onShiftPressed(PlayerToggleSneakEvent event) {
        // jetpack toggle
        Player player = event.getPlayer();
        if (event.getPlayer().getInventory().getChestplate() != null) {
            if (isSameCustomItem(player.getInventory().getChestplate().getItemMeta(),ItemManager.jetpack.getItemMeta())) {
               // if the player is in the air (they jumped) enable jetpack for them
               if ( !this.jetpackers.contains(player.getUniqueId()) &&
                       (player.getLocation().subtract(0,1,0).getBlock().getType() == Material.AIR)) { //&&
                       //(player.isSneaking())){
                   // player.issneaking prevents unshifting in midair to start, which leads you to only fall WHILE SHIFTING
                   this.jetpackers.add(player.getUniqueId());
                   // Check if they have a jetpack fuel yet, if not, make one
                   if (!jetpackFuel.containsKey(player.getUniqueId())){
                       jetpackFuel.put(player.getUniqueId(),maxFuel);
                   }
               }
               else if (this.jetpackers.contains(player.getUniqueId())){
                   this.jetpackers.remove(player.getUniqueId());
               }
            }
        }
    }
    @EventHandler
    public void onMove(PlayerMoveEvent event){
        // jetpack jetpacking
        Player player = event.getPlayer();
        if (event.getPlayer().getInventory().getChestplate() != null) {
            if (player.isSneaking() && isSameCustomItem(player.getInventory().getChestplate().getItemMeta(),ItemManager.jetpack.getItemMeta()) && this.jetpackers.contains(player.getUniqueId())) {
                player.setVelocity(player.getLocation().getDirection().multiply(1.2).setY(0.5));
                player.getWorld().spawnParticle(Particle.SOUL_FIRE_FLAME, player.getLocation(), 0);
                player.getWorld().spawnParticle(Particle.SOUL_FIRE_FLAME, player.getLocation(), 0);
                player.getWorld().spawnParticle(Particle.CAMPFIRE_COSY_SMOKE, player.getLocation(), 0);
                player.getWorld().playSound(player.getLocation(), Sound.ENTITY_CAT_PURR,10,1);

                ItemMeta meta = player.getInventory().getChestplate().getItemMeta();

                int fuel = jetpackFuel.get(player.getUniqueId());
                //player.sendMessage("Old Fuel: "+Integer.toString(fuel));
                fuel -= 1;
                //player.sendMessage("New Fuel: "+Integer.toString(fuel));
                jetpackFuel.replace(player.getUniqueId(),fuel);
                //player.sendMessage("Fuel/Max Fuel: "+Double.toString((float)fuel/maxFuel));
                //player.sendMessage("New Durability: "+Integer.toString(Math.round(((float)fuel / maxFuel)*80)));
                //player.sendMessage("New Damage: "+Integer.toString(Math.round(80-(((float)fuel / maxFuel)*80))));
                ((Damageable) meta).setDamage(Math.round(80-(((float)fuel / maxFuel)*80)));
                player.getInventory().getChestplate().setItemMeta(meta);




            }
        }
    }
    @EventHandler
    public void onKicked(PlayerKickEvent event) {
        // Prevent fly kicking while jetpacking
        Player player = event.getPlayer();
        if (event.getPlayer().getInventory().getChestplate() != null) {
            if (isSameCustomItem(player.getInventory().getChestplate().getItemMeta(),ItemManager.jetpack.getItemMeta())) {
                if (event.getReason().contains("Flying")) {
                    event.setCancelled(true);
                }
            }
        }
    }


    @EventHandler
    public static void onRightClick(PlayerInteractEvent event) {
        // not else if or there would never be a right click block event
        if (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) {
            // Triggers on any right click

            if (event.getItem() != null) {
                // Avoids nullpointerexeptions when referencing item


                if (event.getItem().getItemMeta().equals(ItemManager.blaster.getItemMeta())) {
                    // Blaster event
                    Player player = event.getPlayer();

                    // First check if it is on cooldown


                    if (player.getCooldown(ItemManager.blaster.getType()) <= 0) {

                        player.setCooldown(ItemManager.blaster.getType(), 20); // cooldown in ticks

                        Location origin = player.getEyeLocation();
                        player.getWorld().playSound(origin, Sound.ENTITY_FIREWORK_ROCKET_BLAST, 10, 1); // 10 is volume, 1 is pitch
                        Vector direction = origin.getDirection();
                        direction.multiply(100 /* the range */);
                        Location destination = origin.clone().add(direction); // clone origin so we don't modify it directly
                        direction.normalize();
                        for (int i = 0; i < 100 /* range */; i++) {
                            Location loc = origin.add(direction);
                            for (Entity e : loc.getWorld().getNearbyEntities(loc, 0.5, 0.5, 0.5)) {
                                if (e instanceof LivingEntity && !e.equals(player)) {
                                    ((LivingEntity) e).damage(5);
                                    break; // can only damage 1 thing
                                }
                            }


                            if (player.getWorld().getBlockAt(loc).getType() != Material.AIR) {
                                break; // prevent it from going through blocks
                            }
                            loc.getWorld().spawnParticle(Particle.DRAGON_BREATH, loc, 0);
                            loc.getWorld().spawnParticle(Particle.DRAGON_BREATH, loc, 0);
                        }
                    }
                }
                else if (event.getItem().getItemMeta().equals(ItemManager.handcannon.getItemMeta())) {
                    // hand cannon event
                    Player player = event.getPlayer();

                    // First check if it is on cooldown


                    if (player.getCooldown(ItemManager.handcannon.getType()) <= 0) {

                        player.setCooldown(ItemManager.handcannon.getType(), 120); // cooldown in ticks

                        Location origin = player.getEyeLocation();
                        player.getWorld().playSound(origin, Sound.ENTITY_FIREWORK_ROCKET_LARGE_BLAST, 10, 1); // 10 is volume, 1 is pitch
                        Vector direction = origin.getDirection();
                        direction.multiply(100 /* the range */);
                        Location destination = origin.clone().add(direction); // clone origin so we don't modify it directly
                        direction.normalize();
                        for (int i = 0; i < 100 /* range */; i++) {
                            Location loc = origin.add(direction);
                            for (Entity e : loc.getWorld().getNearbyEntities(loc, 1, 1, 1)) {
                                if (e instanceof LivingEntity && !e.equals(player)) {
                                    player.getWorld().createExplosion(loc, 1.0f);
                                    break; // can only explode 1 thing
                                }
                            }


                            if (player.getWorld().getBlockAt(loc).getType() != Material.AIR) {
                                player.getWorld().createExplosion(loc, 4.0f);
                                break; // prevent it from going through blocks after explosion
                            }
                            loc.getWorld().spawnParticle(Particle.SQUID_INK, loc, 0);
                            loc.getWorld().spawnParticle(Particle.SQUID_INK, loc, 0);

                        }
                    }



                }

            }
        }
    }

}

