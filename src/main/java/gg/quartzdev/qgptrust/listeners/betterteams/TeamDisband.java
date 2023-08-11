package gg.quartzdev.qgptrust.listeners.betterteams;

import com.booksaw.betterTeams.Team;
import com.booksaw.betterTeams.TeamPlayer;
import com.booksaw.betterTeams.customEvents.DisbandTeamEvent;
import me.ryanhamshire.GriefPrevention.Claim;
import me.ryanhamshire.GriefPrevention.DataStore;
import me.ryanhamshire.GriefPrevention.GriefPrevention;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.util.List;
import java.util.UUID;
import java.util.Vector;

public class TeamDisband implements Listener {

    @EventHandler
    public void onTeamDisband(DisbandTeamEvent event){
//        gets the team
        Team team = event.getTeam();
//        gets all the members in the team
        List<TeamPlayer> teamMembers = team.getStorage().getPlayerList();
//        gets GriefPrevention data
        DataStore gpDataStore = GriefPrevention.instance.dataStore;
//        loop through each team member
        for(TeamPlayer teamMember : teamMembers){

            UUID teamMemberID = teamMember.getPlayer().getUniqueId();
            Vector<Claim> claims = gpDataStore.getPlayerData(teamMemberID).getClaims();
            for(Claim claim : claims){
                claim.dropPermission(teamMemberID.toString());
            }
        }




    }
}
