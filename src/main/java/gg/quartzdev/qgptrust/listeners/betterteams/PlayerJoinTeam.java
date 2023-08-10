package gg.quartzdev.qgptrust.listeners.betterteams;

import com.booksaw.betterTeams.Team;
import com.booksaw.betterTeams.TeamPlayer;
import com.booksaw.betterTeams.customEvents.PlayerJoinTeamEvent;
import me.ryanhamshire.GriefPrevention.Claim;
import me.ryanhamshire.GriefPrevention.ClaimPermission;
import me.ryanhamshire.GriefPrevention.DataStore;
import me.ryanhamshire.GriefPrevention.GriefPrevention;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.util.List;
import java.util.Vector;

public class PlayerJoinTeam implements Listener {

    @EventHandler
    public void onPlayerJoinTeam(PlayerJoinTeamEvent event){

        Player newTeamMember = event.getPlayer().getPlayer();
        Team team = event.getTeam();
        List<TeamPlayer> teamMembers = team.getStorage().getPlayerList();

        DataStore gpDataStore = GriefPrevention.instance.dataStore;

//        for each existing team member, trust the new team member to all their claims
        for(TeamPlayer existingTeamMember : teamMembers) {
//            gets all the claims for each of the existing team members
            Vector<Claim> claims = gpDataStore.getPlayerData(existingTeamMember.getPlayer().getUniqueId()).getClaims();
//            trusts the new teammate to their claims
            for(Claim claim : claims){
                claim.setPermission(newTeamMember.getUniqueId().toString(), ClaimPermission.Build);
                gpDataStore.saveClaim(claim);
            }
        }
    }

}
