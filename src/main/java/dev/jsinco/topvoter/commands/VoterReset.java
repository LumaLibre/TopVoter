package dev.jsinco.topvoter.commands;

import dev.jsinco.topvoter.VotersFile;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public class VoterReset implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (args.length < 1) {
            sender.sendMessage("You must confirm this action by typing /voterreset confirm");
            return true;
        }
        if (args[0].equalsIgnoreCase("confirm")) {
            VotersFile.reset();
        } else {
            sender.sendMessage("You must confirm this action by typing /voterreset confirm");
        }
        return true;
    }
}
