package com.example.administrator.myapp.cls;

public class MeMenu {

    protected String item;
    private int profile;

    public MeMenu(String item, int profile) {
        this.item = item;
        this.profile = profile;
    }

    public int getProfile() {
        return profile;
    }
    public void setProfile(int profile) {
        this.profile = profile;
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }
}
