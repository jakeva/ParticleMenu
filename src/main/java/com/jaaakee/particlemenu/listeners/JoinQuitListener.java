package com.jaaakee.particlemenu.listeners;

import com.jaaakee.particlemenu.ParticleHandler;
import com.jaaakee.particlemenu.ParticleMenu;
import com.jaaakee.particlemenu.utilities.ConfigurationManager;
import org.bukkit.Particle;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

public class JoinQuitListener implements Listener {

    private final ParticleMenu particleMenu;
    public ConfigurationManager configurationManager = new ConfigurationManager();

    public JoinQuitListener(ParticleMenu particleMenu) {
        this.particleMenu = particleMenu;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent playerJoinEvent) throws IOException {
        Player player = playerJoinEvent.getPlayer();

        FileConfiguration fileConfiguration = configurationManager.getConfig("config", particleMenu);

        /* Load Player Particles */
        if (fileConfiguration.getBoolean("save-player-data")) {
            /* Create player-data file if it does not exist */
            File locationData = new File(particleMenu.getDataFolder(), "player-data.yml");
            if (!locationData.exists()) {
                try {
                    configurationManager.createConfig("player-data", particleMenu);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            fileConfiguration = configurationManager.getConfig("player-data", particleMenu);

            /* Load Player Particles */
            if (fileConfiguration.getString(player.getUniqueId().toString()) == null) {
                fileConfiguration.set(player.getUniqueId().toString(), "NONE");
                configurationManager.saveConfig(fileConfiguration, "player-data", particleMenu);
            } else if (!Objects.equals(fileConfiguration.getString(player.getUniqueId().toString()), "NONE")) {
                new ParticleHandler(particleMenu).runParticles(player, Particle.valueOf(fileConfiguration.getString(player.getUniqueId().toString())));
            }
        }
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent playerQuitEvent) {
        Player player = playerQuitEvent.getPlayer();
        ParticleHandler particleHandler = new ParticleHandler(particleMenu);

        /* Remove particles from player */
        if (particleHandler.playerHasParticleActive(player)) {
            particleHandler.clearPlayerParticle(player);
        }
    }
}