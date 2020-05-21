package com.jaaakee.particlemenu.commands;

import com.jaaakee.particlemenu.ParticleHandler;
import com.jaaakee.particlemenu.ParticleMenu;
import com.jaaakee.particlemenu.utilities.commands.CommandInterface;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class MainCommand implements CommandInterface {

    private final ParticleMenu particleMenu;

    public MainCommand(ParticleMenu particleMenu) {
        this.particleMenu = particleMenu;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
        Player player = (Player) sender;

        new ParticleHandler(particleMenu).createMenu(player);
        return false;
    }
}