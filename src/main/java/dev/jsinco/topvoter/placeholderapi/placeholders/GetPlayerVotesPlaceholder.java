package dev.jsinco.topvoter.placeholderapi.placeholders;

import dev.jsinco.topvoter.TopVoter;
import dev.jsinco.topvoter.Util;
import dev.jsinco.topvoter.VotersFile;
import dev.jsinco.topvoter.placeholderapi.Placeholder;
import org.bukkit.OfflinePlayer;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class GetPlayerVotesPlaceholder implements Placeholder {
    @Override
    public @Nullable String onPlaceholderRequest(TopVoter plugin, @Nullable OfflinePlayer player, List<String> args) {
        if (args.isEmpty() && player != null) {
            return String.valueOf(VotersFile.get().getInt(Util.getPlayerUUIDOrName(player.getName())));
        }
        String uuid = Util.getPlayerUUIDOrName(args.get(0));
        return String.valueOf(VotersFile.get().getInt(uuid));
    }
}
