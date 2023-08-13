package gg.quartzdev.qgpteamsync.listeners.betterteams;

import com.booksaw.betterTeams.PlayerRank;
import com.booksaw.betterTeams.Team;
import com.booksaw.betterTeams.TeamPlayer;
import com.booksaw.betterTeams.customEvents.DemotePlayerEvent;
import me.ryanhamshire.GriefPrevention.Claim;
import me.ryanhamshire.GriefPrevention.ClaimPermission;
import me.ryanhamshire.GriefPrevention.DataStore;
import me.ryanhamshire.GriefPrevention.GriefPrevention;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.util.List;
import java.util.UUID;
import java.util.Vector;

public class TeamDemotePlayer implements Listener {

    @EventHandler
    public void onPlayerDemoted(DemotePlayerEvent event){
//        gets the team the demoted player is in
        Team team = event.getTeam();
//        gets all the team members
        List<TeamPlayer> teamMembers = team.getStorage().getPlayerList();
//        gets GriefPrevention data
        DataStore gpDataStore = GriefPrevention.instance.dataStore;
//        loops through all team members
        for(TeamPlayer teamMember : teamMembers) {
            UUID teamMemberID = teamMember.getPlayer().getUniqueId();
            Vector<Claim> claims = gpDataStore.getPlayerData(teamMemberID).getClaims();

//            TODO: use config to dictate what playerrank is what gp permission level,
//            ie: getrankfromconfig(event.getnewrank()) returns a claimpermission

            for(Claim claim : claims){
                if(event.getNewRank().equals(PlayerRank.OWNER)){
                    trustPlayer(claim, teamMemberID.toString(), ClaimPermission.Manage, gpDataStore);
                }
                if(event.getNewRank().equals(PlayerRank.ADMIN)){
                    trustPlayer(claim, teamMemberID.toString(), ClaimPermission.Manage, gpDataStore);
                }
                if(event.getNewRank().equals(PlayerRank.DEFAULT)){
                    trustPlayer(claim, teamMemberID.toString(), ClaimPermission.Build, gpDataStore);
                }
            }
        }
    }

    private void trustPlayer(Claim claim, String playerID, ClaimPermission permission, DataStore dataStore){
        claim.dropPermission(playerID);
        claim.setPermission(playerID, permission);
        dataStore.saveClaim(claim);
    }
}
