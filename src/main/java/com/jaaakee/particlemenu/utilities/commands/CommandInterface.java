package com.jaaakee.particlemenu.utilities.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import java.io.IOException;

//IMPORTANT: This is an interface, not a class.
public interface CommandInterface {

    //Every time I make a command, I will use this same method.
    boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) throws IOException;

}