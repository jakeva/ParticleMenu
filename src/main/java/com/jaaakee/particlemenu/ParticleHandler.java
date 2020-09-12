package com.jaaakee.particlemenu;

import com.jaaakee.particlemenu.utilities.ConfigurationManager;
import com.jaaakee.particlemenu.utilities.ItemBuilder;
import com.jaaakee.particlemenu.utilities.menus.MenuAPI;
import org.bukkit.*;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.IOException;
import java.util.HashMap;
import java.util.Objects;
import java.util.Random;

public class ParticleHandler {

    private static final HashMap<Player, Integer> player_particle = new HashMap<>();
    private final ParticleMenu particleMenu;

    public ConfigurationManager configurationManager = new ConfigurationManager();
    public FileConfiguration fileConfiguration;

    public ParticleHandler(ParticleMenu particleMenu) {
        this.particleMenu = particleMenu;
    }

    public void createMenu(Player player) {

        MenuAPI particlesMenu = new MenuAPI(configurationManager.getConfig("config", particleMenu).getInt("inventory-size"), ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(configurationManager.getConfig("config", particleMenu).getString("inventory-title"))));

        for (ParticleType particleType : ParticleType.values()) {
            if (particleType.isEnabled(particleMenu)) {
                if (player.hasPermission(particleType.getPermissionNode())) {

                    fileConfiguration = configurationManager.getConfig("player-data", particleMenu);

                    if (Objects.equals(fileConfiguration.getString(player.getUniqueId().toString()), particleType.getParticleEffect().toString())) {
                        /* Selected and have permission particle effect icons */
                        particlesMenu.setItem(particleType.getParticleSlot(particleMenu), new ItemBuilder(particleType.getParticleIcon(particleMenu)).name(ChatColor.AQUA + particleType.getParticleName(particleMenu) + " Particle Effect").lore(ChatColor.GREEN + "SELECTED!").enchant(Enchantment.LUCK, 1).flags(ItemFlag.HIDE_ENCHANTS, ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_POTION_EFFECTS).build(),
                                e -> {
                                    player.sendMessage(ChatColor.RED + "You already have this effect active.");
                                    player.playSound(player.getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT, 1F, 1F);
                                });
                    } else {
                        /* Unselected and have permission particle effect icons */
                        particlesMenu.setItem(particleType.getParticleSlot(particleMenu), new ItemBuilder(particleType.getParticleIcon(particleMenu)).name(ChatColor.AQUA + particleType.getParticleName(particleMenu) + " Particle Effect").lore(ChatColor.YELLOW + "Click to Select!").flags(ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_POTION_EFFECTS).build(),
                                e -> {
                                    try {
                                        setPlayerParticle(player, particleType.getParticleName(particleMenu), particleType.getParticleEffect(), true);
                                    } catch (IOException ioException) {
                                        ioException.printStackTrace();
                                    }
                                });
                    }
                } else {
                    /* Unselected and have permission particle effect icons */
                    particlesMenu.setItem(particleType.getParticleSlot(particleMenu), new ItemBuilder(Material.GRAY_DYE).name(ChatColor.AQUA + particleType.getParticleName(particleMenu) + " Particle Effect").lore(ChatColor.RED + particleType.getNoPermissionMessage(particleMenu)).build(),
                            e -> {
                                player.sendMessage(ChatColor.RED + particleType.getNoPermissionMessage(particleMenu));
                                player.playSound(player.getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT, 1F, 1F);
                            });
                }
            }
        }

        /* Clear Particles Icon */
        particlesMenu.setItem(40, new ItemBuilder(Material.BARRIER).name(ChatColor.RED + "Clear Particles").build(), e -> {
            if (playerHasParticleActive(player)) {
                clearPlayerParticle(player);

                player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_BASS, 1F, 1F);
                player.sendMessage(ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(configurationManager.getConfig("config", particleMenu).getString("clear-particle.chat-message"))));
                createMenu(player);
            }
        });

        particlesMenu.open(player);
    }

    public void setPlayerParticle(Player player, String particleName, Particle particle, boolean updateInventory) throws IOException {
        /* Begin particle effect runnable */
        runParticles(player, particle);

        fileConfiguration = configurationManager.getConfig("config", particleMenu);

        /* Load Player Particles */
        if (fileConfiguration.getBoolean("save-player-data")) {
            fileConfiguration.set(player.getUniqueId().toString(), particle.toString());
            configurationManager.saveConfig(fileConfiguration, "player-data", particleMenu);
        }

        player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 1F, 1F);
        player.sendMessage(ChatColor.GREEN + "You selected the " + particleName + " Particle Effect!");

        if (updateInventory)
            createMenu(player);
    }

    public void runParticles(Player player, Particle particle) {
        if (player_particle.containsKey(player))
            clearPlayerParticle(player);

        double radialsPerStep = Math.PI / 4;
        BukkitRunnable bukkitRunnable = new BukkitRunnable() {
            int step = 0;

            @Override
            public void run() {

                Random random = new Random();
                Location location = player.getLocation();

                location.add(Math.cos(radialsPerStep * step) * 0.5F, 2.1D, Math.sin(radialsPerStep * step) * 0.5F);

                for (Player players : Bukkit.getOnlinePlayers()) {
                    if (players.canSee(player))
                        if (particle.toString().equals("SLIME"))
                            players.spawnParticle(Particle.valueOf(particle.toString()), location, 8);
                        else if (particle.toString().equals("NOTE"))
                            players.spawnParticle(Particle.valueOf(particle.toString()), location, 0, 1 + (24 - 1) * random.nextDouble(), 0, 0, 1);
                        else if (particle.toString().equals("VILLAGER_ANGRY"))
                            players.spawnParticle(Particle.valueOf(particle.toString()), location.subtract(0, 0.4, 0), 0);
                        else if (particle.toString().equals("VILLAGER_HAPPY") || particle.toString().equals("DRIP_WATER") || particle.toString().equals("DRIP_LAVA") || particle.toString().equals("ENCHANTMENT_TABLE"))
                            players.spawnParticle(Particle.valueOf(particle.toString()), location.subtract(0, 0.1, 0), 0);
                        else
                            players.spawnParticle(Particle.valueOf(particle.toString()), location, 0);
                }
                step++;
            }
        };

        bukkitRunnable.runTaskTimerAsynchronously(particleMenu, 0L, 2L);
        player_particle.put(player, bukkitRunnable.getTaskId());
    }

    public void clearPlayerParticle(Player player) {
        Bukkit.getServer().getScheduler().cancelTask(player_particle.get(player));
        player_particle.remove(player);
    }

    public boolean playerHasParticleActive(Player player) {
        return player_particle.containsKey(player);
    }
}