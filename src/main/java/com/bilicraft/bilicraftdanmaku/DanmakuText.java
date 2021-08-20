package com.bilicraft.bilicraftdanmaku;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

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
        String codes = "";
        for (String f:formattings) {
            codes += f;
        }
        this.code = codes;
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

    public static String getCheckableText(JsonObject object){
        String result = new String(object.get("string").getAsString());

        for (JsonElement sub:object.get("subText").getAsJsonArray()) {
            result += getCheckableText(sub.getAsJsonObject());
        }

        return result;
    }

}