package com.github.hank9999.BlockPlayerCommands;

import com.github.hank9999.BlockPlayerCommands.Command.bpc;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.ChatColor;
import com.github.hank9999.BlockPlayerCommands.Listener.CommandPreprocessEvent;

public final class BlockPlayerCommands extends JavaPlugin {

    public static BlockPlayerCommands plugin;

    //当插件被Load(加载)时执行
    @Override
    public void onLoad() {
        getLogger().info(ChatColor.BLUE + "玩家指令黑名单插件正在加载");
    }

    //当插件被Enable(开启)时执行
    @Override
    public void onEnable() {
        plugin = this;
        saveDefaultConfig();
        reloadConfig();
        getLogger().info(ChatColor.BLUE + "玩家指令黑名单插件已启用");
        getLogger().info(ChatColor.GOLD + "版本v1.0.0");
        getServer().getPluginManager().registerEvents(new CommandPreprocessEvent(), this);
        getServer().getPluginCommand("blockplayercommands").setExecutor(new bpc());
        getServer().getPluginCommand("blockplayercommands").setTabCompleter(new bpc());

    }


    //当插件被Disable(关闭)时执行
    @Override
    public void onDisable() {
        plugin = null;
        getLogger().info(ChatColor.BLUE + "玩家指令黑名单插件已停用");
    }
}