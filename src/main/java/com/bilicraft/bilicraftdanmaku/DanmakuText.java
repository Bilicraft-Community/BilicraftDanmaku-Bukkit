package com.bilicraft.bilicraftdanmaku;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.util.ArrayList;
import java.util.List;

public class DanmakuText {
    private String string;
    private String code;
    private List<DanmakuText> subText;

    public DanmakuText() {
        this("");
    }

    public DanmakuText(String string, String code, List<DanmakuText> subText) {
        this.string = string;
        this.code = code;
        this.subText = subText;
    }

    public DanmakuText(String string, String code) {
        this(string,code,new ArrayList<>());
    }


    public DanmakuText(String string) {
        this(string,"");
    }


    public String getString() {
        return string;
    }

    public void setString(String string) {
        this.string = string;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setCode(List<String> formattings) {
        StringBuilder codes = new StringBuilder();
        for (String f:formattings) {
            codes.append(f);
        }
        this.code = codes.toString();
    }

    public List<DanmakuText> getSubText() {
        return subText;
    }

    public void setSubText(List<DanmakuText> subText) {
        this.subText = subText;
    }

    public void append(DanmakuText text){
        this.subText.add(text);
    }

    public void append(String text){
        this.append(new DanmakuText(text,"",new ArrayList<>()));
    }
    public static String getCheckableText(String textJson){
        JsonObject object = new JsonParser().parse(textJson).getAsJsonObject();
        StringBuilder result = new StringBuilder(object.get("string").getAsString());

        for (JsonElement sub:object.get("subText").getAsJsonArray()) {
            result.append(getCheckableText(sub.getAsJsonObject()));
        }

        return result.toString();
    }

    @Deprecated
    public static String getCheckableText(JsonObject object){
        StringBuilder result = new StringBuilder(object.get("string").getAsString());
        
        for (JsonElement sub:object.get("subText").getAsJsonArray()) {
            result.append(getCheckableText(sub.getAsJsonObject()));
        }

        return result.toString();
    }

}
