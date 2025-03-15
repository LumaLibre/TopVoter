package dev.jsinco.topvoter;

import com.vexsoftware.votifier.model.VotifierEvent;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class VotifierListener implements Listener {

    @EventHandler
    public void onVoteReceived(VotifierEvent event) {
        if (event.getVote().getUsername() == null) return;

        String userName = Util.getPlayerUUIDOrName(event.getVote().getUsername());

        FileConfiguration file = VotersFile.get();

        if (file.contains(userName)) {
            file.set(userName, file.getInt(userName) + 1);
        } else {
            file.set(userName, 1);
        }
        VotersFile.save();
    }
}
