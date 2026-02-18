package dev.jsinco.topvoter.obj;

import dev.jsinco.topvoter.Util;
import org.bukkit.Bukkit;

import java.util.UUID;

public class RecordedVoter {

    public static final RecordedVoter DEFAULT_RECORDED_VOTER = new RecordedVoter("3f605654-5a13-4ee3-9254-0a07cfe7d114", 0);

    private final String uuidOrName;
    private final int votes;

    private final UUID uuid;
    private final String name;

    public RecordedVoter(String uuidOrName, int votes) {
        this.uuidOrName = uuidOrName;
        this.votes = votes;

        this.uuid = Util.uuidOrNull(uuidOrName);
        if (uuid == null) {
            this.name = uuidOrName;
        } else {
            this.name = Bukkit.getOfflinePlayer(uuid).getName();
        }
    }

    public UUID getUuid() {
        return uuid;
    }

    public String getName() {
        return name;
    }

    public String getUuidOrName() {
        return uuidOrName;
    }

    public int getVotes() {
        return votes;
    }
}
