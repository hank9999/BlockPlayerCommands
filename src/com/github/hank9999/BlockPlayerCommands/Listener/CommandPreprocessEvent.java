package com.github.hank9999.BlockPlayerCommands.Listener;

import com.github.hank9999.BlockPlayerCommands.BlockPlayerCommands;
import com.github.hank9999.BlockPlayerCommands.Libs.Lib;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

public class CommandPreprocessEvent implements Listener {

    @EventHandler(priority = EventPriority.LOWEST)
    public void onCommands(PlayerCommandPreprocessEvent e) {
        if (BlockPlayerCommands.plugin.getConfig().getBoolean("enable")) {
            Player p = e.getPlayer();
            String name = p.getName();
            for (String playername : BlockPlayerCommands.plugin.getConfig().getStringList("blacklist")) {
                if (playername.equalsIgnoreCase(name)) {
                    e.setCancelled(true);
                    String message = BlockPlayerCommands.plugin.getConfig().getString("message");
                    if (message == null) {
                        message = "&cYou are in command black list!";
                    }
                    p.sendMessage(Lib.color_translate(message));
                }
            }
        }
    }
}
