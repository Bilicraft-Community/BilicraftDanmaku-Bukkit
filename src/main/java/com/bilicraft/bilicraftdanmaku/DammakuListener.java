package com.bilicraft.bilicraftdanmaku;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerRegisterChannelEvent;
import org.bukkit.plugin.messaging.PluginMessageListener;

public class DammakuListener implements Listener, PluginMessageListener {

    @EventHandler
    public void channelHandler(PlayerRegisterChannelEvent event){
        BilicraftDanmaku.logger.info(event.getChannel());
    }

    @Override
    public void onPluginMessageReceived(String channel, Player player, byte[] message) {
        if(player.hasPermission("bilicraftdanmaku.denied")){
            player.sendMessage("You are not allowed to send danmaku");
            return;
        }
        String bukkitMessageString = new String(message);
        if(bukkitMessageString.indexOf("{") > 0 ){
            bukkitMessageString = bukkitMessageString.substring(bukkitMessageString.indexOf("{"));
        }
        JsonObject bukkitMessageObject = new JsonParser().parse(bukkitMessageString).getAsJsonObject();
        String danmakuString = bukkitMessageObject.get("text").getAsJsonPrimitive().getAsString();
        JsonObject danmakuJson = new JsonParser().parse(danmakuString).getAsJsonObject();
        danmakuJson.addProperty("showName", ServerConfigs.showSenderNameOnComment);
        danmakuJson.addProperty("sender",player.getDisplayName());
        byte[] finalMessage = danmakuJson.toString().getBytes();
        Bukkit.getOnlinePlayers().forEach(revicer -> {
            revicer.sendPluginMessage( BilicraftDanmaku.Instance,"bilicraftclientui:bilicraftdanmaku" , finalMessage );
        });
    }
}
