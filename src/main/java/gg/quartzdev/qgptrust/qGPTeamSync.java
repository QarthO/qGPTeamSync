package gg.quartzdev.qgptrust;

import gg.quartzdev.qgptrust.listeners.CreateClaim;
import gg.quartzdev.qgptrust.listeners.betterteams.*;
import me.ryanhamshire.GriefPrevention.Claim;
import me.ryanhamshire.GriefPrevention.ClaimPermission;
import me.ryanhamshire.GriefPrevention.DataStore;
import org.bukkit.plugin.java.JavaPlugin;
import gg.quartzdev.qgptrust.metrics.Metrics;

public final class qGPTeamSync extends JavaPlugin {

    @Override
    public void onEnable() {
        // Plugin startup logic

//        bStats Metrics
        getLogger().info("Enabling bStats Metrics");
        int pluginId = 19448;
        Metrics metrics = new Metrics(this, pluginId);

//        register ClaimCreate event from grief prevention
        getLogger().info("Hooking into GriefPrevention");
        try {
            getServer().getPluginManager().registerEvents(new CreateClaim(), this);
        } catch(Exception e){
            getLogger().warning("Failed hooking into GriefPrevention: ");
            getLogger().warning(e.getMessage());
        }

        getLogger().info("Hooking into BetterTeams");
        try {
            getServer().getPluginManager().registerEvents(new PlayerJoinTeam(), this);
            getServer().getPluginManager().registerEvents(new PlayerLeaveTeam(), this);
            getServer().getPluginManager().registerEvents(new TeamPromotePlayer(), this);;
            getServer().getPluginManager().registerEvents(new TeamDemotePlayer(), this);
            getServer().getPluginManager().registerEvents(new TeamDisband(), this);
        } catch(Exception e){
            getLogger().warning("Failed hooking into GriefPrevention: ");
            getLogger().warning(e.getMessage());
        }


    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public void trustPlayer(Claim claim, String playerID, ClaimPermission permission, DataStore dataStore){
        claim.dropPermission(playerID);
        claim.setPermission(playerID, permission);
        dataStore.saveClaim(claim);
    }
}
