package com.bilicraft.bilicraftdanmaku;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerRegisterChannelEvent;
import org.bukkit.plugin.messaging.PluginMessageListener;

import java.nio.charset.StandardCharsets;
import java.util.HashSet;

public class DanmakuListener implements Listener, PluginMessageListener {

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

        String bukkitMessageString = new String(message, StandardCharsets.UTF_8);
        BilicraftDanmaku.logger.info(bukkitMessageString);
        if(bukkitMessageString.indexOf("{") > 0 ){
            bukkitMessageString = bukkitMessageString.substring(bukkitMessageString.indexOf("{"));
        }
        BilicraftDanmaku.logger.info(bukkitMessageString);
        JsonObject bukkitMessageObject = new JsonParser().parse(bukkitMessageString).getAsJsonObject();
        String danmakuString = bukkitMessageObject.get("text").getAsString();
        JsonObject danmakuJson = new JsonParser().parse(danmakuString).getAsJsonObject();

        int mode = bukkitMessageObject.get("mode").getAsInt();

        switch (mode){
            case 0:
                if(!player.hasPermission("bilicraftdanmaku.normal")){
                    player.sendMessage("You are not allowed to send a normal danmaku");
                    return;
                }
                break;
            case 1:
                if(!player.hasPermission("bilicraftdanmaku.top")){
                    player.sendMessage("You are not allowed to send danmaku at top");
                    return;
                }
                break;
            case 2:
                if(!player.hasPermission("bilicraftdanmaku.buttom")){
                    player.sendMessage("You are not allowed to send danmaku at buttom ");
                    return;
                }
                break;
            case 3:
                if(!player.hasPermission("bilicraftdanmaku.reverse")){
                    player.sendMessage("You are not allowed to send reverse danmaku");
                    return;
                }
                break;
            default:
                player.sendMessage("No such danmaku mode");
                return;
        }

        String danmakuContent = DanmakuText.getCheckableText(danmakuJson);

        AsyncPlayerChatEvent event = new AsyncPlayerChatEvent(false, player,danmakuContent, new HashSet<>(Bukkit.getOnlinePlayers()));
        Bukkit.getServer().getPluginManager().callEvent(event);

        if(event.getMessage().equals(danmakuContent)){
            bukkitMessageObject.addProperty("showName", ServerConfigs.showSenderNameOnComment);
            bukkitMessageObject.addProperty("sender",player.getDisplayName());
            byte[] finalMessage = bukkitMessageObject.toString().getBytes();
            Bukkit.getOnlinePlayers().forEach(revicer -> {
                revicer.sendPluginMessage( BilicraftDanmaku.Instance,"bilicraftclientui:bilicraftdanmaku" , finalMessage );
            });
        }

    }
}
