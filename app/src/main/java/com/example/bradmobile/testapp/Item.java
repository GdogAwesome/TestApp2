package com.example.bradmobile.testapp;

import android.util.Log;

/**
 * Created by BradMobile on 10/8/2016.
 */


public class Item {

    public static final int WEAPON_UPGRADE_SPRAY = 1;
    public static final int HEALTH_UPGRADE = 2;
    public static final int POINTS_UPGRADE = 3;
    public static final int DEFAULT_VALUE = 4;

    public boolean droping = true;
    private int upgradeTime = 50;
    private int uType = 0;
    public boolean active = false;
    private int timer = 0;
    private int time = 0;
    private int totalTime = 10;
    private float[] drawStats = new float[4];
    public int posX = 0;
    public int posY = 0;
    public int g = 0;
    private int size = Constants.CHARACTER_WIDTH / 6;

    public int drawX = 0;
    public int drawY = 0;

    /*

        @Param set upgrade type
     */
    public Item(){

        time = 0;
        droping = true;
        active = true;



    }
    public void initItem(int x, int y, int upgradeType, int ground){
        this.uType = upgradeType;
        this.g = ground;
        this.posX = x;
        this.posY = y;
        time = 0;
        droping = true;
        active = true;

    }

    public void update(float viewX){
        drawX = (int)(posX - viewX);
        if(droping){
            if((posY + size) <= g){
                posY += 5;
                //Log.d("posx", Integer.toString(drawX));
            }else{
                droping = false;
            }

        }
        timer ++;
        if(timer > 90){
            time ++;
            if(time >= totalTime){
                active = false;
            }
            timer = 0;
        }


    }
    public float[] getDrawStats(){
        drawStats[0] = drawX;
        drawStats[1] = posY;
        drawStats[2] = drawX + size;
        drawStats[3] = posY + size;

        return drawStats;
    }


    public int getUType(){
        return uType;

    }
    public int getUTime(){
        return upgradeTime;
    }
}
