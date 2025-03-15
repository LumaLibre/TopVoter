package dev.jsinco.topvoter.webserver;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dev.jsinco.topvoter.TopVoter;
import dev.jsinco.topvoter.Util;
import dev.jsinco.topvoter.VotersFile;
import dev.jsinco.topvoter.obj.RecordedVoter;
import io.javalin.Javalin;
import io.javalin.json.JsonMapper;
import io.javalin.plugin.bundled.CorsPluginConfig;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Type;
import java.util.Collections;
import java.util.List;

public class JavalinServer {

    private final Javalin javalin;
    private final JsonMapper gsonMapper = new JsonMapper() {
        private final Gson gson = new GsonBuilder().setPrettyPrinting().create();

        @Override
        public <T> @NotNull T fromJsonString(@NotNull String json, @NotNull Type targetType) {
            return gson.fromJson(json, targetType);
        }

        @Override
        public @NotNull String toJsonString(@NotNull Object obj, @NotNull Type type) {
            return gson.toJson(obj);
        }
    };

    public JavalinServer() {
        javalin = Javalin.create(javalinConfig -> {
            javalinConfig.bundledPlugins.enableCors(cors -> {
                cors.addRule(CorsPluginConfig.CorsRule::anyHost);
            });
            javalinConfig.jsonMapper(gsonMapper);
        });
    }


    public void start() {
        FileConfiguration config = TopVoter.getInstance().getConfig();
        String host = config.getString("webserver.host", "0.0.0.0");
        int port = config.getInt("webserver.port", 7070);

        Bukkit.getScheduler().runTaskAsynchronously(TopVoter.getInstance(), () -> {
            javalin.get("/top/{pos}", ctx -> {
                        int pos = Util.intOrDefault(ctx.pathParam("pos"), 1);
                        List<RecordedVoter> recordedVoters = VotersFile.getTopRecordedVoters();
                        if (recordedVoters.size() < pos) {
                            ctx.json(RecordedVoter.DEFAULT_RECORDED_VOTER);
                        } else {
                            ctx.json(recordedVoters.get(pos - 1));
                        }
                    })
                    .get("/toplist/{start}-{end}", ctx -> {
                        int start = Util.intOrDefault(ctx.pathParam("start"), 1);
                        int end = Util.intOrDefault(ctx.pathParam("end"), start); // Default to single item if only one value is provided
                        List<RecordedVoter> recordedVoters = VotersFile.getTopRecordedVoters();

                        // Ensure valid indices
                        start = Math.max(1, start); // Ensure start is at least 1
                        end = Math.min(recordedVoters.size(), end); // Ensure end doesn't exceed list size

                        if (start > end || recordedVoters.isEmpty()) {
                            ctx.json(Collections.emptyList()); // Return empty list if range is invalid
                        } else {
                            ctx.json(recordedVoters.subList(start - 1, end)); // Extract the range
                        }
                    })
                    .start(host, port);
        });
    }

    public void stop() {
        javalin.stop();
    }

    public Javalin getJavalin() {
        return javalin;
    }
}
