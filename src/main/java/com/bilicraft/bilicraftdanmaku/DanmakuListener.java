package com.bilicraft.bilicraftdanmaku;

import com.bilicraft.bilicraftdanmaku.protocol.Packet;
import com.bilicraft.bilicraftdanmaku.protocol.client.ClientDanmakuPacket;
import com.bilicraft.bilicraftdanmaku.protocol.server.ServerDanmakuPacket;
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
    public void channelHandler(PlayerRegisterChannelEvent event) {
       // BilicraftDanmaku.logger.info(event.getChannel());
        if(event.getChannel().equals(BilicraftDanmaku.channelName)){
            BilicraftDanmaku.logger.info("玩家 "+event.getPlayer().getName()+" 的弹幕消息通道连接已建立");
        }
    }

    @Override
    public void onPluginMessageReceived(String channel, Player player, byte[] message) {

        if (!channel.equals(BilicraftDanmaku.channelName)) {
            return;
        }

        if (!player.hasPermission("bilicraftdanmaku.send")) {
            player.sendMessage("You are not allowed to send danmaku");
            return;
        }
        String buffString = new String(message, StandardCharsets.UTF_8);
        buffString = buffString.substring(buffString.indexOf("{"));
        try {
            ServerDanmakuPacket danmakuPacket = Packet.deserialize(buffString, ServerDanmakuPacket.class);
            BilicraftDanmaku.logger.info("收到弹幕: "+player.getDisplayName()+" -> "+danmakuPacket.getJsonText());
            switch (danmakuPacket.getType()) {
                case NORMAL:
                    if (!player.hasPermission("bilicraftdanmaku.send.normal")) {
                        player.sendMessage("You are not allowed to send a normal danmaku");
                        return;
                    }
                    break;
                case TOP:
                    if (!player.hasPermission("bilicraftdanmaku.send.top")) {
                        player.sendMessage("You are not allowed to send danmaku at top");
                        return;
                    }
                    break;
                case BOTTOM:
                    if (!player.hasPermission("bilicraftdanmaku.send.bottom")) {
                        player.sendMessage("You are not allowed to send danmaku at bottom ");
                        return;
                    }
                    break;
                case RESERVE:
                    if (!player.hasPermission("bilicraftdanmaku.send.reverse")) {
                        player.sendMessage("You are not allowed to send reverse danmaku");
                        return;
                    }
                    break;
                default:
                    player.sendMessage("No such danmaku mode");
                    return;
            }

            String danmakuContent = DanmakuText.getCheckableText(danmakuPacket.getJsonText());

            Bukkit.getScheduler().runTaskAsynchronously(BilicraftDanmaku.Instance, () -> {
                AsyncPlayerChatEvent event = new AsyncPlayerChatEvent(true, player, danmakuContent, new HashSet<>(Bukkit.getOnlinePlayers()));
                Bukkit.getServer().getPluginManager().callEvent(event);
                if(!event.getMessage().equals(danmakuContent)){
                    player.sendMessage("您的弹幕内容已被第三方插件更改，请检查并再试一次");
                    return;
                }
                if (!(event.isCancelled())) {
                    byte[] sendingDanmakuBytes = ClientDanmakuPacket
                            .builder()
                            .type(danmakuPacket.getType())
                            .sender(player.getUniqueId())
                            .playerName(player.getDisplayName())
                            .showName(ServerConfigs.showSenderNameOnComment)
                            .jsonText(danmakuPacket.getJsonText())
                            .lifespan(danmakuPacket.getLifespan())
                            .build().serializeBytes();
                    Bukkit.getOnlinePlayers().forEach(revicer -> revicer.sendPluginMessage(BilicraftDanmaku.Instance, "bilicraftclientui:bilicraftdanmaku", sendingDanmakuBytes));
                }
            });

        }catch (Exception exception){
            BilicraftDanmaku.logger.warning("无效数据包: "+buffString);
        }
    }
}
