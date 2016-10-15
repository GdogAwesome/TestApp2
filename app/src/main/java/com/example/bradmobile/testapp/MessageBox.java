package com.example.bradmobile.testapp;

import android.content.Context;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Build;
import android.util.Log;

/**
 * Created by BradMobile on 9/9/2016.
 */
public class MessageBox {
    Paint paint = new Paint();
    private String Message = null;
    private float textSize;

    public boolean activeMessage = false;
    int timer =0;
    int length = 0;
    float posX = 0;
    float posY = 0;
    float scale = 1.6f;
    int blockSize = 14;
    public float drawTX = 0;
    public float drawTY = 0;
    private float targetW = 0;
    private float targetH = 0;
    private float[] widths ;
    public boolean expanding = true;
    public boolean drawText = false;
    private String[] values ;
    private int totalWidths = 0;
    private int textLength = 0;

    RectF [] whereToDraw = new RectF[9];
    RectF drawC1 = new RectF(0,0,0,0);
    RectF drawC2 = new RectF(0,0,0,0);
    RectF drawC3 = new RectF(0,0,0,0);
    RectF drawC4 = new RectF(0,0,0,0);
    RectF drawTop = new RectF(0,0,0,0);
    RectF drawBottom = new RectF(0,0,0,0);
    RectF drawCenter = new RectF(0,0,0,0);
    RectF drawLeft = new RectF(0,0,0,0);
    RectF drawRight = new RectF(0,0,0,0);

    Rect[] frameToDraw = new Rect[9];
    Rect c1 = new Rect(0,0,0,0);
    Rect c2 = new Rect(0,0,0,0);
    Rect c3= new Rect(0,0,0,0);
    Rect c4= new Rect(0,0,0,0);
    Rect top= new Rect(0,0,0,0);
    Rect bottom= new Rect(0,0,0,0);
    Rect center= new Rect(0,0,0,0);
    Rect left = new Rect(0,0,0,0);
    Rect right = new Rect(0,0,0,0);
    int version;


    int cornerWH = 14;

    float[] draw = new float[4];


    int messageH = 0;
    int messageW = 0;

     MessageBox(Context context){
         scale = Constants.scale;
         textSize = (25 * scale);
         paint.setTextSize(textSize);//12
         int paintC ;

         version = Build.VERSION.SDK_INT;

         if (version >= 23) {
             paintC = context.getResources().getColor(R.color.white, null);
         } else {
             paintC = context.getResources().getColor(R.color.white);
         }

         paint.setColor(paintC);
         paint.setTextAlign(Paint.Align.CENTER);

     }
    public void showMessage(String message, int time, boolean mutable){

        /**
         *
         *
         * alter string to fit box here
         */

        int widestLine = 0;
        values = message.split("/n");
        if(this.Message != message){
            this.Message = message;
            activeMessage = false;
            drawText = false;
            textLength = 0;
            messageH = 0;
            messageW = 0;
        }





        for (int i = 0; i< values.length; i++){
            if(i > 0){
                if(values[i].length() > values[i -1].length()){
                    widestLine = i;

                }
            }
        }
        widths = new float[values[widestLine].length()];
        totalWidths = paint.getTextWidths(values[widestLine],0, values[widestLine].length(),widths);

        for(int i =0; i < totalWidths; i++){

            textLength += widths[i];
        }

        targetW = textLength +(int)(2*(blockSize * scale));
        targetH = (int)((values.length * textSize) + ((values.length -1) * paint.descent()));


        //this.Message = Integer.toString(version);
        /**
         *
         * set up frame to draw from bitmap
         */
        c1.left = 1122;
        c1.top = 1211;
        c1.right = 1136;
        c1.bottom = 1225;

        c2.left = 1122;
        c2.top = 1236;
        c2.right = 1136;
        c2.bottom = 1250;

        left.left = c1.left;
        left.top = c1.bottom;
        left.right = c1.right;
        left.bottom = c2.top;

        c3.left =1186;
        c3.top = 1211;
        c3.right = 1200;
        c3.bottom = 1225;

        c4.left = 1186;
        c4.top = 1236;
        c4.right = 1200;
        c4.bottom = 1250;

        right.left = c3.left;
        right.top = c3.bottom;
        right.right = c3.right;
        right.bottom = c4.top;

        top.left = 1136;
        top.top = 1211;
        top.right = 1150;
        top.bottom = 1225;

        bottom.left = 1136;
        bottom.top = 1236;
        bottom.right = 1150;
        bottom.bottom =1250;

        center.left = 1136;
        center.top = 1225;
        center.right = 1150;
        center.bottom = 1239;

        frameToDraw[0] = c1;
        frameToDraw[1] = c2;
        frameToDraw[2] = c3;
        frameToDraw[3] = c4;
        frameToDraw[4] = top;
        frameToDraw[5] = bottom;
        frameToDraw[6] = left;
        frameToDraw[7] = right;
        frameToDraw[8] = center;




        timer = 0;
        this.length = time * 90;
        activeMessage = true;

    }
    public void updateMessage(float x, float y){
        this.posX = x - (messageW * .5f);// - messageW;
        this.posY = y - messageH - (blockSize * scale);

        drawTX = posX  + (.5f * messageW);
        drawTY = posY+ (blockSize * scale);// - messageH;//y -(messageH * .5f);

        /**
         *
         * set up where to draw on screen
         *
         *
         */
        drawC1.left = posX;
        drawC1.top = posY - (blockSize * scale);
        drawC1.right = posX + (blockSize * scale);
        drawC1.bottom = posY ;

        drawC3.left = posX + (messageW - ( blockSize * scale));
        drawC3.top = posY - (blockSize * scale);
        drawC3.right = posX + messageW;
        drawC3.bottom = posY;

        drawTop.left = drawC1.right;
        drawTop.top = drawC1.top;
        drawTop.right = drawC3.left;
        drawTop.bottom = drawC1.bottom;

        drawC2.left = posX;
        drawC2.top = posY + (messageH);
        drawC2.right = posX + (blockSize * scale);
        drawC2.bottom = posY + messageH + (blockSize * scale);

        drawLeft.left = drawC1.left;
        drawLeft.top = drawC1.bottom;
        drawLeft.right = drawC1.right;
        drawLeft.bottom = drawC2.top;

        drawC4.left = posX + (messageW - ( blockSize * scale));
        drawC4.top = drawC2.top;
        drawC4.right =  posX + messageW;
        drawC4.bottom = drawC2.bottom;

        drawRight.left = drawC3.left;
        drawRight.top = drawC3.bottom;
        drawRight.right = drawC3.right;
        drawRight.bottom = drawC4.top;

        drawBottom.left = drawC2.right;
        drawBottom.top = drawC4.top;
        drawBottom.right = drawC4.left;
        drawBottom.bottom = drawC4.bottom;

        drawCenter.left = drawC1.right;
        drawCenter.top = drawC1.bottom;
        drawCenter.right = drawC4.left;
        drawCenter.bottom = drawC4.top;

        whereToDraw[0] = drawC1;
        whereToDraw[1] = drawC2;
        whereToDraw[2] = drawC3;
        whereToDraw[3] = drawC4;
        whereToDraw[4] = drawTop;
        whereToDraw[5] = drawBottom;
        whereToDraw[6] = drawLeft;
        whereToDraw[7] = drawRight;
        whereToDraw[8] = drawCenter;



        if(drawText ) {
            timer++;
            if (timer >= length) {
                timer = 0;
                activeMessage = false;
                drawText = false;
                textLength = 0;
                messageH = 0;
                messageW = 0;
            }
        }else{
            if(messageW <= targetW){
                messageW += 5 * scale;
            }
            if(messageH <= targetH){
                messageH += 3 * scale;
            }
            if(messageH >= targetH && messageW >= targetW ){
                drawText = true;

            }
        }

    }
    public Rect[] getFrameToDraw(){


        return frameToDraw;

    }
    public RectF[] getWhereToDraw(){

        return whereToDraw;
    }
    public String[] getText(){
        return values;
    }
    public float getSX(){
        return drawTX;
    }
    public float getSY(){
        return drawTY;
    }
    public void setSY(float SY){
        drawTY = SY;

    }
    public Paint getPaint(){
        return paint;
    }
}
