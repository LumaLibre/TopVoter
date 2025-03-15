package dev.jsinco.topvoter.placeholderapi.placeholders;

import com.destroystokyo.paper.profile.ProfileProperty;
import dev.jsinco.topvoter.TopVoter;
import dev.jsinco.topvoter.Util;
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

        String voterToGet = Util.getPlayerUUIDOrName(topVoters.keySet().toArray()[num].toString());
        UUID uuid = Util.uuidOrNull(voterToGet);
        OfflinePlayer voter = uuid != null ? Bukkit.getOfflinePlayer(uuid) : Bukkit.getOfflinePlayer(voterToGet);


        for (ProfileProperty profileProperty : voter.getPlayerProfile().getProperties()) {
            if (profileProperty.getName().equalsIgnoreCase("texture")) {
                return profileProperty.getValue();
            }
        }
        return Util.DEFAULT_TEXTURE;
    }
}
