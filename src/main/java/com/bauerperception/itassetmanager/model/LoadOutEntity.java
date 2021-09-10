package com.bauerperception.itassetmanager.model;

import java.util.ArrayList;

public class LoadOutEntity implements Entity{
    private int loadOutID;
    private String loudOutName;
    private String keyboard;
    private String mouse;
    private String mousePad;
    private String monitor;
    private String monitor2;
    private String monitorStand;
    private String deskPhone;
    private String webCam;
    private String headset;
    private String computer;

    @Override
    public int getID() {
        return loadOutID;
    }

    //TODO: Rigid implementation, should be able to add whatever you like to the load out.
}
