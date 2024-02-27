package dev.jsinco.topvoter.placeholderapi.placeholders;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import dev.jsinco.headsapi.HeadsAPI;
import dev.jsinco.topvoter.TopVoter;
import dev.jsinco.topvoter.VotersFile;
import dev.jsinco.topvoter.placeholderapi.Placeholder;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class SpotPlaceholder implements Placeholder {

    private final Gson gson = new Gson();

    @Override
    public @Nullable String onPlaceholderRequest(TopVoter plugin, @Nullable OfflinePlayer player, List<String> args) {

        Map<String, Integer> topVoters = VotersFile.getTopVoters();

        int num = Integer.parseInt(args.get(0)) - 1;
        if (topVoters.size() <= num) {
            if (args.size() > 1 && args.get(1).equals("votes")) {
                return "0";
            }

            return plugin.getConfig().getString("empty-spot");
        }

        String voterToGet = TopVoter.getPlayerUUIDIfCached(topVoters.keySet().toArray()[num].toString());

        if (args.size() < 2) {
            OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(UUID.fromString(voterToGet));
            if (offlinePlayer.getName() != null) {
                return offlinePlayer.getName();
            }
            StringBuilder content = null;
            try {
                content = HeadsAPI.getMinecraftProfile(voterToGet);
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (content == null) {
                return null;
            }
            JsonObject jsonObject = gson.fromJson(content.toString(), JsonObject.class);
            return jsonObject.get("name").getAsString();
        } else if (args.get(1).equals("votes")) {
            if (voterToGet.length() == 36) {
                return String.valueOf(topVoters.get(voterToGet));
            }
            return String.valueOf(topVoters.get(voterToGet));
        } else if (args.get(1).equals("uuid")) {
            return voterToGet;
        }

        return null;
    }
}
