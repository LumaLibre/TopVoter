package dev.jsinco.topvoter.placeholderapi.placeholders;

import dev.jsinco.headsapi.HeadsAPI;
import dev.jsinco.topvoter.TopVoter;
import dev.jsinco.topvoter.VotersFile;
import dev.jsinco.topvoter.placeholderapi.Placeholder;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public class SpotHead64Texture implements Placeholder {


    @Override
    public @Nullable String onPlaceholderRequest(TopVoter plugin, @Nullable OfflinePlayer player, List<String> args) {
        Map<String, Integer> topVoters = VotersFile.getTopVoters();

        int num = Integer.parseInt(args.get(0)) - 1;
        if (topVoters.size() <= num) {
            return plugin.getConfig().getString("empty-spot");
        }

        String voterToGet = TopVoter.getPlayerUUIDIfCached(topVoters.keySet().toArray()[num].toString());
        UUID uuid;

        try {
            uuid = UUID.fromString(voterToGet);
        } catch (IllegalArgumentException e) {
            uuid = Bukkit.getOfflinePlayer(voterToGet).getUniqueId();
        }

        return HeadsAPI.getBase64(uuid);
    }
}
