package com.jaaakee.particlemenu;

import com.jaaakee.particlemenu.commands.MainCommand;
import com.jaaakee.particlemenu.commands.ParticleCommand;
import com.jaaakee.particlemenu.listeners.JoinQuitListener;
import com.jaaakee.particlemenu.utilities.commands.CommandHandler;
import com.jaaakee.particlemenu.utilities.menus.MenuAPIManager;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

public class ParticleMenu extends JavaPlugin {

    FileConfiguration config = getConfig();

    @Override
    public void onEnable() {
        /* Load config.yml */
        config.options().copyDefaults(true);
        saveConfig();

        /* Register Listeners */
        getServer().getPluginManager().registerEvents(new JoinQuitListener(this), this);

        /* Register MenuAPI for InventoryClickEvent Handling */
        MenuAPIManager.register(this);

        /* Register Main Command */
        CommandHandler handler = new CommandHandler(this);
        handler.register("particlemenu", new MainCommand(this));
        Objects.requireNonNull(getCommand("particlemenu")).setExecutor(handler);
        Objects.requireNonNull(getCommand("particlemenu")).setTabCompleter(new CommandHandler(this));

        /* Register Particle Activate Sub-Command */
        handler.register("activate", new ParticleCommand(this));
    }

    @Override
    public void onDisable() {

    }
}