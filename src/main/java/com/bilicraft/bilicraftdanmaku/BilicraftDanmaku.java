package com.bilicraft.bilicraftdanmaku;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Logger;

public final class BilicraftDanmaku extends JavaPlugin {

    public static final String pluginName = "BilicraftDanmaku";
    public static Logger logger;
    public static BilicraftDanmaku Instance;
    @Override
    public void onEnable() {
        // Plugin startup logic
        BilicraftDanmaku.logger = getLogger();
        BilicraftDanmaku.Instance = this;
        DanmakuListener listener = new DanmakuListener();
        Bukkit.getPluginManager().registerEvents(listener,this);
        Bukkit.getMessenger().registerIncomingPluginChannel(this, "bilicraftclientui:bilicraftdanmaku" , listener);
        Bukkit.getMessenger().registerOutgoingPluginChannel(this,"bilicraftclientui:bilicraftdanmaku" );
        logger.info(pluginName + "is loaded, Hello world!");
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }



}
