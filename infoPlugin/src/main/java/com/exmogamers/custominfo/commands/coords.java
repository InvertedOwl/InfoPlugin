package com.exmogamers.custominfo.commands;

import com.exmogamers.custominfo.CustomInfo;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

public class coords implements CommandExecutor {
    /**
     * Executes the given command, returning its success.
     * <br>
     * If false is returned, then the "usage" plugin.yml entry for this command
     * (if defined) will be sent to the player.
     *
     * @param sender  Source of the command
     * @param command Command which was executed
     * @param label   Alias of the command which was used
     * @param args    Passed command arguments
     * @return true if a valid command, otherwise false
     */
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (label.equalsIgnoreCase("coords")) {

            FileConfiguration config = CustomInfo.getInstance().getConfiguration();

            config.set(((Player) sender).getUniqueId() + ".coords", !config.getBoolean(((Player) sender).getUniqueId() + ".coords"));
            CustomInfo.getInstance().saveCustomYml(config);

            ((Player) sender).sendMessage("Coords toggle now " + config.getBoolean(((Player) sender).getUniqueId() + ".coords"));

            return true;
        }
        return false;
    }
}
