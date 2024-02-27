package dev.jsinco.topvoter;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class VotersFile {
    private static final TopVoter plugin = TopVoter.getInstance();
    private static File file;
    private static YamlConfiguration votersFile;


    public static void setup() {
        file = new File(plugin.getDataFolder(), "voters.yml");
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        votersFile = YamlConfiguration.loadConfiguration(file);
    }

    public static FileConfiguration get() {
        return votersFile;
    }

    public static void save() {
        try {
            votersFile.save(file);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void reload() {
        votersFile = YamlConfiguration.loadConfiguration(file);
    }

    public static void reset() {
        File file = new File(plugin.getDataFolder(), "voters.yml");
        if (!file.renameTo(new File(plugin.getDataFolder(), "voters-old.yml"))) {
            plugin.getLogger().warning("Failed to rename voters.yml to voters-old.yml");
            return;
        }

        for (String key : votersFile.getKeys(false)) {
            votersFile.set(key, null);
        }
        save();
    }

    // We need to return a list of all the voters the plugin has recorded in order from most votes to least
    public static Map<String, Integer> getTopVoters() {
        Map<String, Integer> eventPlayers = new HashMap<>();
        for (String key : VotersFile.get().getKeys(false)) {
            eventPlayers.put(key, VotersFile.get().getInt(key));
        }

        return eventPlayers.entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (oldValue, newValue) -> oldValue,
                        LinkedHashMap::new
                ));
    }
}
