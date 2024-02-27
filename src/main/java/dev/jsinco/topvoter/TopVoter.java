package dev.jsinco.topvoter;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import dev.jsinco.headsapi.HeadsAPI;
import dev.jsinco.topvoter.commands.HeadsAPIVerbose;
import dev.jsinco.topvoter.commands.VoterReset;
import dev.jsinco.topvoter.placeholderapi.PlaceholderManager;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;
import java.util.Calendar;
import java.util.LinkedHashMap;
import java.util.Map;

public final class TopVoter extends JavaPlugin {

    private static TopVoter plugin;
    private static boolean papiEnabled;
    private static PlaceholderManager placeholderManager;
    private static final Calendar calendar = Calendar.getInstance();
    private static final Map<String, JsonObject> topPlayersCache = new LinkedHashMap<>();
    private static final Gson gson = new Gson();

    @Override
    public void onEnable() {
        plugin = this;
        papiEnabled = Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null;
        getConfig().options().copyDefaults(true);
        saveDefaultConfig();
        VotersFile.setup();
        getCommand("voterreset").setExecutor(new VoterReset());
        getCommand("headsapiverbosity").setExecutor(new HeadsAPIVerbose());

        getServer().getPluginManager().registerEvents(new VotifierListener(), this);
        if (papiEnabled) {
            placeholderManager = new PlaceholderManager(this);
            placeholderManager.register();
        }


        if (calendar.get(Calendar.DAY_OF_MONTH) == plugin.getConfig().getInt("reset-day") && !plugin.getConfig().getBoolean("already-reset-day")) {
            VotersFile.reset();
            plugin.getConfig().set("already-reset-day", true);
            plugin.saveConfig();
            Bukkit.getScheduler().scheduleSyncDelayedTask(this, () ->
                    getLogger().info(plugin.getConfig().getString("reset-log")), 100L);
        } else if (calendar.get(Calendar.DAY_OF_MONTH) != plugin.getConfig().getInt("reset-day") && plugin.getConfig().getBoolean("already-reset-day")) {
            plugin.getConfig().set("already-reset-day", false);
            plugin.saveConfig();
        }
    }

    @Override
    public void onDisable() {
        if (papiEnabled) {
            placeholderManager.unregister();
        }
    }

    public static TopVoter getInstance() {
        return plugin;
    }

    public static String getPlayerUUIDIfCached(String playerName) {
        return Bukkit.getOfflinePlayerIfCached(playerName) != null ? Bukkit.getOfflinePlayerIfCached(playerName).getUniqueId().toString() : playerName;
    }

    // Unused rn
    public static Map<String, JsonObject> getTopPlayersCache() {
        for (String key : VotersFile.getTopVoters().keySet()) {
            if (topPlayersCache.containsKey(key)) {
                continue;
            }
            try {
                cacheTopPlayers(3);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return topPlayersCache;
    }

    private static void cacheTopPlayers(int amount) throws IOException {
        topPlayersCache.clear();
        for (String key : VotersFile.getTopVoters().keySet()) {
            if (topPlayersCache.size() >= amount) {
                break;
            }

            topPlayersCache.put(key, gson.fromJson(String.valueOf(HeadsAPI.getMinecraftProfile(key)), JsonObject.class));
        }
    }
}
