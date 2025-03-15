package dev.jsinco.topvoter;

import dev.jsinco.topvoter.commands.VoterReset;
import dev.jsinco.topvoter.placeholderapi.PlaceholderManager;
import dev.jsinco.topvoter.webserver.JavalinServer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Calendar;

public final class TopVoter extends JavaPlugin {

    private static TopVoter plugin;
    private static boolean papiEnabled;
    private static PlaceholderManager placeholderManager;
    private static final Calendar calendar = Calendar.getInstance();
    private static JavalinServer javalinServer;

    @Override
    public void onEnable() {
        plugin = this;
        papiEnabled = Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null;
        getConfig().options().copyDefaults(true);
        saveDefaultConfig();
        VotersFile.setup();
        getCommand("voterreset").setExecutor(new VoterReset());

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

        Logger logger = (Logger) LogManager.getRootLogger();
        logger.addFilter(new LogFilter());
        javalinServer = new JavalinServer();
        javalinServer.start();
    }

    @Override
    public void onDisable() {
        if (papiEnabled) {
            placeholderManager.unregister();
        }
        javalinServer.stop();
    }

    public static TopVoter getInstance() {
        return plugin;
    }


}
