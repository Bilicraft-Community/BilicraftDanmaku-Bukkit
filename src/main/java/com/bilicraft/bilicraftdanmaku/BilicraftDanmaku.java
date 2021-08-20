package com.bilicraft.bilicraftdanmaku;

import org.bukkit.Bukkit;
import org.bukkit.event.HandlerList;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Logger;

public final class BilicraftDanmaku extends JavaPlugin {

    //public static final String pluginName = "BilicraftDanmaku";
    public static Logger logger;
    public static BilicraftDanmaku Instance;
    public static final String channelName = "bilicraftclientui:bilicraftdanmaku";
    @Override
    public void onEnable() {
        // Plugin startup logic
        BilicraftDanmaku.logger = getLogger();
        BilicraftDanmaku.Instance = this;
        DanmakuListener listener = new DanmakuListener();
        Bukkit.getPluginManager().registerEvents(listener,this);
        Bukkit.getMessenger().registerIncomingPluginChannel(this, channelName, listener);
        Bukkit.getMessenger().registerOutgoingPluginChannel(this,channelName);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        HandlerList.unregisterAll(this);
        Bukkit.getMessenger().unregisterIncomingPluginChannel(this);
        Bukkit.getMessenger().unregisterOutgoingPluginChannel(this);
    }



}
