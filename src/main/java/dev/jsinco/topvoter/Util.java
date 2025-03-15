package dev.jsinco.topvoter;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;

import java.util.UUID;

public class Util {
    public static final String DEFAULT_TEXTURE = "ewogICJ0aW1lc3RhbXAiIDogMTczNjM4Nzk1ODI2NSwKICAicHJvZmlsZUlkIiA6ICIxNDRjZTM5ZDMwMWI0MGE5OTc4ODBjYThjYjIzZGFmNCIsCiAgInByb2ZpbGVOYW1lIiA6ICJKc2luY28iLAogICJzaWduYXR1cmVSZXF1aXJlZCIgOiB0cnVlLAogICJ0ZXh0dXJlcyIgOiB7CiAgICAiU0tJTiIgOiB7CiAgICAgICJ1cmwiIDogImh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNDNiOTg5ZTEwOTg5MWFlMzgyNzg5ZmRhNDIzZTNhNTUxNDMwY2NkYzY5MmY3ZmRmODA0YjNmOTZkZDMxYjFhZCIsCiAgICAgICJtZXRhZGF0YSIgOiB7CiAgICAgICAgIm1vZGVsIiA6ICJzbGltIgogICAgICB9CiAgICB9LAogICAgIkNBUEUiIDogewogICAgICAidXJsIiA6ICJodHRwOi8vdGV4dHVyZXMubWluZWNyYWZ0Lm5ldC90ZXh0dXJlL2Y5YTc2NTM3NjQ3OTg5ZjlhMGI2ZDAwMWUzMjBkYWM1OTFjMzU5ZTllNjFhMzFmNGNlMTFjODhmMjA3ZjBhZDQiCiAgICB9CiAgfQp9";


    public static UUID uuidOrNull(String uuid) {
        try {
            return UUID.fromString(uuid);
        } catch (IllegalArgumentException e) {
            return null;
        }
    }

    public static String getPlayerUUIDOrName(String username) {
        OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayerIfCached(username);
        if (offlinePlayer != null) {
            return offlinePlayer.getUniqueId().toString();
        } else {
            return username;
        }
    }

    public static int intOrDefault(String s, int def) {
        try {
            return Integer.parseInt(s);
        } catch (NumberFormatException e) {
            return def;
        }
    }
}
