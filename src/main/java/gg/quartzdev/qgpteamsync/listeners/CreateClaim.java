package gg.quartzdev.qgpteamsync.listeners;

import com.booksaw.betterTeams.PlayerRank;
import com.booksaw.betterTeams.Team;
import com.booksaw.betterTeams.TeamPlayer;
import gg.quartzdev.qgpteamsync.util.Util;
import me.ryanhamshire.GriefPrevention.*;
import me.ryanhamshire.GriefPrevention.events.ClaimCreatedEvent;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.util.List;
import java.util.UUID;

public class CreateClaim implements Listener {

    @EventHandler
    public void onClaimCreate(ClaimCreatedEvent event){
        CommandSender owner = event.getCreator();

//        Double-checks the owner is a player
        if(!(owner instanceof Player)) return;
        Player player = (Player) owner;

        Claim newClaim = event.getClaim();

//        Get the players team
        Team team = Team.getTeam(player);
//        Does nothing if they're not in a team
        if(team == null) return;

//        Gets all members in the team
        List<TeamPlayer> teamMembers = team.getStorage().getPlayerList();

//        Util.sendMessage(player, "<yellow>Adding <aqua>" + (teamMembers.size()-1) + " <yellow>player(s) to your new claim...");
        for(TeamPlayer teamMember : teamMembers) {
            UUID teamMemberID = teamMember.getPlayer().getUniqueId();
//            Prevents a team member from trusting themself to their own claim
            if(teamMemberID.equals(player.getUniqueId())) continue;
//            gets the rank of each teammate
            PlayerRank rank = teamMember.getRank();

//            if they're an admin/owner they become a manager
            if(rank.equals(PlayerRank.ADMIN) || rank.equals(PlayerRank.OWNER)) {
                newClaim.setPermission(teamMemberID.toString(), ClaimPermission.Manage);
            }
//            for every other rank, they become a builder (currently only other teams rank is DEFAULT)
            else {
                newClaim.setPermission(teamMemberID.toString(), ClaimPermission.Build);
            }
//            Util.sendMessage(player, "  <gray>- <yellow>Added <aqua>" + teamMember.getPlayer().getName());
        }

//        Saves claim
        DataStore gpDataStore = GriefPrevention.instance.dataStore;
        gpDataStore.saveClaim(newClaim);
//        Util.sendMessage(player, "<green>Complete");



    }

}
