package gg.quartzdev.qgpteamsync.util;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Bukkit;

public class Util {
    private static String PREFIX = "<gray>[<aqua>q<green>GP<red>TeamSync<gray>]<reset>";

    public static void log(String msg){
        MiniMessage mm = MiniMessage.miniMessage();
        Component parse = mm.deserialize(PREFIX + " " + msg);
        Bukkit.getConsoleSender().sendMessage(parse);
    }
}
