package dev.jsinco.topvoter.commands;

import dev.jsinco.lumajda.api.CommandOption;
import dev.jsinco.lumajda.api.DiscordCommand;
import dev.jsinco.topvoter.TopVoter;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class VotesDiscord implements DiscordCommand {
    @Override
    public @NotNull String name() {
        return "votes";
    }

    @Override
    public String description() {
        return "View the the top voters!";
    }

    @Override
    public void execute(@NotNull SlashCommandInteractionEvent event) {
        event.reply("").setEphemeral(true).queue();
    }

    @Override
    public List<CommandOption> options() {
        return null;
    }

    @Override
    public Permission permission() {
        return null;
    }

    @Override
    public @Nullable Plugin plugin() {
        return TopVoter.getInstance();
    }
}
