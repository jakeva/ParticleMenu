package com.jaaakee.particlemenu.utilities.commands;

import com.jaaakee.particlemenu.ParticleMenu;
import com.jaaakee.particlemenu.ParticleType;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

//The class will implement CommandExecutor.
public class CommandHandler implements CommandExecutor, TabExecutor {

    //This is where we will store the commands
    private static final HashMap<String, CommandInterface> commands = new HashMap<>();
    public ParticleMenu particleMenu;

    public CommandHandler(ParticleMenu particleMenu) {
        this.particleMenu = particleMenu;
    }

    //Register method. When we register commands in our onEnable() we will use this.
    public void register(String name, CommandInterface cmd) {

        //When we register the command, this is what actually will put the command in the hashmap.
        commands.put(name, cmd);
    }

    //This will be used to check if a string exists or not.
    public boolean exists(String name) {

        //To actually check if the string exists, we will return the hashmap
        return commands.containsKey(name);
    }

    //Getter method for the Executor.
    public CommandInterface getExecutor(String name) {

        //Returns a command in the hashmap of the same name.
        return commands.get(name);
    }

    //This will be a template. All commands will have this in common.
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {

        //For example, in each command, it will check if the sender is a player and if not, send an error message.
        if (sender instanceof Player) {

            //If there aren't any arguments, what is the command name going to be? For this example, we are going to call it /example.
            //This means that all commands will have the base of /example.
            if (args.length == 0) {
                try {
                    getExecutor("particlemenu").onCommand(sender, cmd, commandLabel, args);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                //If that argument exists in our registration in the onEnable();
                if (exists(args[0])) {
                    //Get The executor with the name of args[0]. With our example, the name of the executor will be args because in
                    //the command /example args, args is our args[0].
                    try {
                        getExecutor(args[0]).onCommand(sender, cmd, commandLabel, args);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    //We want to send a message to the sender if the command doesn't exist.
                    sender.sendMessage("This command doesn't exist!");
                }
            }
        } else {
            sender.sendMessage("[ParticleMenu] You must be a player to use this command.");
        }
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        if (command.getName().equalsIgnoreCase("particlemenu")) {
            if (args.length == 1) {
                ArrayList<String> arg1Commands = new ArrayList<>();
                arg1Commands.add("activate");
                return arg1Commands;
            } else if (args.length == 2) {
                ArrayList<String> arg2Commands = new ArrayList<>();
                for (ParticleType particleType : ParticleType.values()) {
                    arg2Commands.add(particleType.getParticleName(particleMenu));
                }

                return arg2Commands;
            }
        }

        return null;
    }
}