package com.bilicraft.bilicraftdanmaku;

import com.bilicraft.bilicraftdanmaku.protocol.CommonDanmakuType;
import com.google.common.collect.Sets;

import java.io.File;
import java.util.Set;

public class ServerConfigs {
    // private static Configuration config;

    public static String allowedMode = "NORMAL;TOP;BOTTOM;RESERVE";
    public static int minLifespan = 10;
    public static int maxLifespan = 100;
    public static int commentInterval = 100;
    public static boolean whitelistMode = true;
    public static boolean appendUsername = true;
    public static boolean allowLANServer = false;
    public static boolean showSenderNameOnComment = true;

    private static final Set<CommonDanmakuType> sModes = Sets.newHashSet();
    private static int hModes = 0;

    public static boolean isModeAllowed(String mode){
       return true;
    }

    public static void load(File configFile){
//        config = new Configuration(configFile);
//        sync();
    }

    public static void reload(){
//        config.load();
//        sync();
    }

//    protected static void sync(){
//        allowedMode = config.get(Configuration.CATEGORY_GENERAL, "allowedMode", "0;1;2").getString();
//        minLifespan = config.get(Configuration.CATEGORY_GENERAL, "minLifespan", 40).getInt(40);
//        maxLifespan = config.get(Configuration.CATEGORY_GENERAL, "maxLifespan", 400).getInt(400);
//        commentInterval = config.get(Configuration.CATEGORY_GENERAL, "commentInterval", 100).getInt(100);
//        whitelistMode = config.get(Configuration.CATEGORY_GENERAL, "whitelistMode", true).getBoolean(true);
//        appendUsername = config.get(Configuration.CATEGORY_GENERAL, "appendUsername", true).getBoolean(true);
//        allowLANServer = config.get(Configuration.CATEGORY_GENERAL, "allowLANServer", false).getBoolean(false);
//
//        if (config.hasChanged())
//            config.save();
//    }

}
