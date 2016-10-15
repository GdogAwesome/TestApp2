package com.example.bradmobile.testapp;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;

/**
 * Created by BradMobile on 4/23/2016.
 */
public class movieScene {
    private Bitmap background;
    private Bitmap image1;
    private Bitmap image2;
    private Bitmap image3;
    public boolean playing = true;

    Paint paint = new Paint();
    int i = 0;

    private RectF whereToDraw = new RectF(0,0,0,0);
    private Rect frameToDraw = new Rect(0,0,0,0);

    public movieScene(Context context, int sceneNum){

        background = BitmapFactory.decodeResource(context.getResources(),R.drawable.futurecity, null);
       // background = Bitmap.createScaledBitmap(background, 2168, 1080, false);
    }
    public void draw(Canvas canvas){

        if(background != null) {
            whereToDraw.left = i;
            whereToDraw.top = 0;
            whereToDraw.right = i + 1920;
            whereToDraw.bottom = 1080;

            frameToDraw.left = 0;
            frameToDraw.top = 0;
            frameToDraw.right = 2168;
            frameToDraw.bottom = 1080;
            canvas.drawBitmap(background, frameToDraw, whereToDraw, paint);
            i -= 1;
        }

    }
    public void nullImage(){
        if(background != null){
            background.recycle();
            background = null;
        }
    }


}
