package gg.quartzdev.qgptrust.listeners.betterteams;

import com.booksaw.betterTeams.Team;
import com.booksaw.betterTeams.TeamPlayer;
import com.booksaw.betterTeams.customEvents.PlayerLeaveTeamEvent;
import me.ryanhamshire.GriefPrevention.Claim;
import me.ryanhamshire.GriefPrevention.DataStore;
import me.ryanhamshire.GriefPrevention.GriefPrevention;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import java.util.List;
import java.util.UUID;
import java.util.Vector;

public class PlayerLeaveTeam implements Listener {

    @EventHandler
    public void onPlayerLeaveTeam(PlayerLeaveTeamEvent event){

//        gets the removed player
        Player removedPlayer = event.getPlayer().getPlayer();
//        gets the uuid of the removed player
        UUID removedPlayerID = removedPlayer.getUniqueId();
//        gets the team the player was removed from
        Team team = event.getTeam();
//        gets all members of the former team
        List<TeamPlayer> formerTeamMembers = team.getStorage().getPlayerList();

//        gets the griefprevention data
        DataStore gpDataStore = GriefPrevention.instance.dataStore;
//        gets all the claims of the removed player
        Vector<Claim> removedClaims = gpDataStore.getPlayerData(removedPlayerID).getClaims();

//        loops through every member of the former team
        for(TeamPlayer formerTeamMember : formerTeamMembers) {
//            gets the uuid for each of the former team members
            UUID formerTeamMemberID = formerTeamMember.getPlayer().getUniqueId();
//            gets all the claims for each of the former team members
            Vector<Claim> teamClaims = gpDataStore.getPlayerData(formerTeamMemberID).getClaims();
//            loops through every claim for each of the former team members
            for(Claim claim : teamClaims){
//                un-trust the removed player from each claim of the former team members
                claim.dropPermission(removedPlayerID.toString());
//                saves each of the claims
                gpDataStore.saveClaim(claim);
            }

//            loops through every claim of the removed player
            for(Claim claim : removedClaims){
//                un-trust each former team member from each of the removed player's claims
                claim.dropPermission(formerTeamMemberID.toString());
//                saves each of the claims
                gpDataStore.saveClaim(claim);
            }
        }
    }
}
