package io.github.trainb0y1.spaceplugin.items;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class ItemManager {
    public static ItemStack wand;
    public static ItemStack blaster;
    public static ItemStack handcannon;
    public static ItemStack jetpack;

    public static void init() {
        createWand();
        createBlaster();
        createHandCannon();
        createJetpack();

    }

    private static void createWand() {
        // Joke item, for figuring out how to do this
        ItemStack item = new ItemStack(Material.DIAMOND_HOE,1);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName("§6Stick of Truth (Tutorial Item)");
        List<String> lore = new ArrayList<>();
        lore.add("§7STICK GO BOOM");
        meta.setLore(lore);

        // luck means just shiny effect
        // False overrides minecraft enchant restrictions
        meta.addEnchant(Enchantment.LUCK, 1,false);
        // hide enchants
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);

        item.setItemMeta(meta);

        wand = item;

    }
    private static void createBlaster() {
        ItemStack item = new ItemStack(Material.DIAMOND_HOE,1);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName("§6Blaster");
        List<String> lore = new ArrayList<>();
        lore.add("§7Its... a blaster");
        lore.add("§7that should be obvious...");
        meta.setLore(lore);

        item.setItemMeta(meta);

        blaster = item;


    }
    private static void createHandCannon() {
        ItemStack item = new ItemStack(Material.DIAMOND_HOE,1);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName("§6Hand Cannon");
        List<String> lore = new ArrayList<>();
        lore.add("§7Don't shoot your eye out!");
        meta.setLore(lore);

        item.setItemMeta(meta);

        handcannon = item;


    }
    private static void createJetpack() {
        ItemStack item = new ItemStack(Material.LEATHER_CHESTPLATE,1);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName("§6Jetpack");
        List<String> lore = new ArrayList<>();
        lore.add("§7Consumes coal as fuel");
        meta.setLore(lore);
        item.setItemMeta(meta);


        jetpack = item;


    }

}
