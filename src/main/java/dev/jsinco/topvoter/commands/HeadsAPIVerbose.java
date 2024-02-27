package dev.jsinco.topvoter.commands;

import dev.jsinco.headsapi.HeadsAPI;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public class HeadsAPIVerbose implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        HeadsAPI.setVerbose(!HeadsAPI.isVerbose());
        sender.sendMessage("HeadsAPI verbosity is now " + (HeadsAPI.isVerbose() ? "enabled" : "disabled"));
        return true;
    }
}
