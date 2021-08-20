package com.bilicraft.bilicraftdanmaku;

import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;

public class Util {
    public static boolean isJson(String str) {
        try {
            new JsonParser().parse(str);
            return true;
        } catch (JsonParseException exception) {
            return false;
        }
    }
}
