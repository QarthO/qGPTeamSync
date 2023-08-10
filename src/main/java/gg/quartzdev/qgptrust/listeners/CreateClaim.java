package gg.quartzdev.qgptrust.listeners;

import com.booksaw.betterTeams.PlayerRank;
import com.booksaw.betterTeams.Team;
import com.booksaw.betterTeams.TeamPlayer;
import com.booksaw.betterTeams.database.BetterTeamsDatabase;
import me.ryanhamshire.GriefPrevention.*;
import me.ryanhamshire.GriefPrevention.events.ClaimCreatedEvent;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.util.ArrayList;
import java.util.List;

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
        List<TeamPlayer> teammates = team.getStorage().getPlayerList();

        for(TeamPlayer teammate : teammates) {
//            gets the rank of each teammate
            PlayerRank rank = teammate.getRank();

//            if they're an admin/owner they become a manager
            if(rank.equals(PlayerRank.ADMIN) || rank.equals(PlayerRank.OWNER)) newClaim.setPermission(teammate.getPlayer().getUniqueId().toString(), ClaimPermission.Manage);
//            for every other rank, they become a builder (currently only other teams rank is DEFAULT)
            else newClaim.setPermission(teammate.getPlayer().getUniqueId().toString(), ClaimPermission.Build);
        }

//        Saves claim
        DataStore gpDataStore = GriefPrevention.instance.dataStore;
        gpDataStore.saveClaim(newClaim);



    }

}
