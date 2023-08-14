package gg.quartzdev.qgpteamsync.util;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class Util {
    private static String PREFIX = "<gray>[<red>q<aqua>GP<dark_aqua>TeamSync<gray>]<reset>";

    /**
     * Logs a general message
     * @param msg
     */
    public static void log(String msg){
        MiniMessage mm = MiniMessage.miniMessage();
        Component parse = mm.deserialize(PREFIX + " " + msg);
        Bukkit.getConsoleSender().sendMessage(parse);
    }

    /**
     * Logs a warning
     * @param msg
     */
    public static void warning(String msg){
        MiniMessage mm = MiniMessage.miniMessage();
        Component parse = mm.deserialize(PREFIX + " <yellow>" + msg);
        Bukkit.getConsoleSender().sendMessage(parse);
//        TODO: log warnings to a file
    }

    /**
     * Logs error
     * @param msg
     */
    public static void error(String msg){
        MiniMessage mm = MiniMessage.miniMessage();
        Component parse = mm.deserialize(PREFIX + " <red>" + msg);
        Bukkit.getConsoleSender().sendMessage(parse);
//        TODO: log errors to a file
    }

    /**
     * Sends a message to the player
     * Supports MiniMessage format
     * @param player The player to send the message to
     * @param msg The message to send to the player
     */
    public static void sendMessage(Player player, String msg){
        MiniMessage mm = MiniMessage.miniMessage();
        Component parse = mm.deserialize(PREFIX + " " + msg);
        player.sendMessage(parse);
    }

    /**
     * Sends a message to the player
     * Supports MiniMessage format
     * @param player The player to send the message to
     * @param msg The message to send to the player
     * @param prefix Whether the plugin prefix should be sent
     */
    public static void sendMessage(Player player, String msg, boolean prefix){
        MiniMessage mm = MiniMessage.miniMessage();
        Component parse = mm.deserialize((prefix ? PREFIX : "") + " " + msg);
        player.sendMessage(parse);
    }
}
