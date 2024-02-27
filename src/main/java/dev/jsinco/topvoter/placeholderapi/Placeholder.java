package dev.jsinco.topvoter.placeholderapi;

import dev.jsinco.topvoter.TopVoter;
import org.bukkit.OfflinePlayer;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public interface Placeholder {

    @Nullable String onPlaceholderRequest(TopVoter plugin, @Nullable OfflinePlayer player, List<String> args);
}
