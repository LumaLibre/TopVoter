package dev.jsinco.topvoter.placeholderapi;

import dev.jsinco.topvoter.TopVoter;
import dev.jsinco.topvoter.placeholderapi.placeholders.GetPlayerVotesPlaceholder;
import dev.jsinco.topvoter.placeholderapi.placeholders.SpotHead64Texture;
import dev.jsinco.topvoter.placeholderapi.placeholders.SpotPlaceholder;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.OfflinePlayer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PlaceholderManager extends PlaceholderExpansion {

    private final TopVoter plugin;
    private final Map<String, Placeholder> placeholderMap = new HashMap<>();

    public PlaceholderManager(TopVoter plugin) {
        this.plugin = plugin;
        placeholderMap.put("spot", new SpotPlaceholder());
        placeholderMap.put("get", new GetPlayerVotesPlaceholder());
        placeholderMap.put("head64", new SpotHead64Texture());
    }

    @Override
    public @NotNull String getIdentifier() {
        return "topvoter";
    }

    @Override
    public @NotNull String getAuthor() {
        return "Jsinco";
    }

    @Override
    public @NotNull String getVersion() {
        return "1.0.0";
    }

    @Override
    public @Nullable String onRequest(OfflinePlayer player, @NotNull String params) {
        List<String> args = new ArrayList<>(List.of(params.split("_")));

        if (placeholderMap.containsKey(args.get(0))) {
            Placeholder placeholder = placeholderMap.get(args.get(0));
            args.remove(0);
            return placeholder.onPlaceholderRequest(plugin, player, args);
        }
        return null;
    }
}
