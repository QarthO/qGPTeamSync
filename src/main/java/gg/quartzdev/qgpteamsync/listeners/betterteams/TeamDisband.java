package gg.quartzdev.qgpteamsync.listeners.betterteams;

import com.booksaw.betterTeams.Team;
import com.booksaw.betterTeams.TeamPlayer;
import com.booksaw.betterTeams.customEvents.DisbandTeamEvent;
import me.ryanhamshire.GriefPrevention.Claim;
import me.ryanhamshire.GriefPrevention.DataStore;
import me.ryanhamshire.GriefPrevention.GriefPrevention;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import gg.quartzdev.qgpteamsync.util.Util;

import java.util.List;
import java.util.UUID;
import java.util.Vector;

public class TeamDisband implements Listener {

    @EventHandler
    public void onTeamDisband(DisbandTeamEvent event){
//        gets the team
        Team team = event.getTeam();
        String teamName = team.getName();

        Util.log("Team <blue>" + teamName + "<reset> disbanded");
//        gets all the members in the team
        List<TeamPlayer> teamMembers = team.getStorage().getPlayerList();
//        gets GriefPrevention data
        DataStore gpDataStore = GriefPrevention.instance.dataStore;
//        loop through each team member
        for(TeamPlayer teamMember : teamMembers){
            UUID teamMemberID = teamMember.getPlayer().getUniqueId();
            Vector<Claim> claims = gpDataStore.getPlayerData(teamMemberID).getClaims();
            Util.sendMessage(teamMember.getPlayer().getPlayer(), "<yellow>Removing <aqua>" + (teamMembers.size()-1) + " <yellow>player(s) from " + claims.size() + " claim(s)...");
            for(Claim claim : claims){
                for(TeamPlayer eachMember: teamMembers){
                    if(eachMember.equals(teamMember)) return;
                    Util.sendMessage(teamMember.getPlayer().getPlayer(), "  <gray>- <aqua>" + eachMember.getPlayer().getName() + "<yellow> removed from claim " + claim.getID());
                    claim.dropPermission(eachMember.toString());
                    gpDataStore.saveClaim(claim);
                }
            }
            Util.sendMessage(teamMember.getPlayer().getPlayer(), "<green>Complete");
        }




    }
}
