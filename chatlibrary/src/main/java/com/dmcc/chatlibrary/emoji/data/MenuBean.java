package com.dmcc.chatlibrary.emoji.data;

public class MenuBean {

    private int id;
    private int icon;
    private String funcName;

    public int getIcon() {
        return icon;
    }

    public String getFuncName() {
        return funcName;
    }

    public int getId() {
        return id;
    }

    public MenuBean(int icon, String funcName){
        this.icon = icon;
        this.funcName = funcName;
    }
}
