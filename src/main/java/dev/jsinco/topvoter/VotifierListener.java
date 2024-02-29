package dev.jsinco.topvoter;

import com.vexsoftware.votifier.model.VotifierEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class VotifierListener implements Listener {

    @EventHandler
    public void onVoteReceived(VotifierEvent event) {
        if (event.getVote().getUsername() == null) return;

        String userName = TopVoter.getPlayerUUIDIfCached(event.getVote().getUsername());

        if (VotersFile.get().contains(userName)) {
            VotersFile.get().set(userName, VotersFile.get().getInt(userName) + 1);
        } else {
            VotersFile.get().set(userName, 1);
        }
        VotersFile.save();
    }
}
