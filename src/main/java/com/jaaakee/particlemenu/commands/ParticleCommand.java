package com.jaaakee.particlemenu.commands;

import com.jaaakee.particlemenu.ParticleHandler;
import com.jaaakee.particlemenu.ParticleMenu;
import com.jaaakee.particlemenu.ParticleType;
import com.jaaakee.particlemenu.utilities.commands.CommandInterface;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.io.IOException;

public class ParticleCommand implements CommandInterface {

    private final ParticleMenu particleMenu;

    public ParticleCommand(ParticleMenu particleMenu) {
        this.particleMenu = particleMenu;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) throws IOException {
        Player player = (Player) sender;

        if (args.length == 1) {
            player.sendMessage("Invalid usage! Use: /particlemenu activate <particle>.\nUse /particlemenu list to see the available particles.");
        } else if (args.length >= 2) {
            String message = "";

            for (int i = 1; i < args.length; i++) {
                message += args[i].toLowerCase() + " ";
            }

            message = message.trim();

            for (ParticleType particleType : ParticleType.values()) {
                if (message.equals(particleType.getParticleName(particleMenu).toLowerCase())) {
                    new ParticleHandler(particleMenu).setPlayerParticle(player, particleType.getParticleName(particleMenu), particleType.getParticleEffect(), false);
                }
            }
        }

        return false;
    }
}