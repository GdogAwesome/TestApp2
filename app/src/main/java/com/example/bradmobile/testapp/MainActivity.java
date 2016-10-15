package com.example.bradmobile.testapp;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.RectF;

import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Debug;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.view.MotionEventCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.MenuInflater;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AbsoluteLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import java.io.InputStream;
import java.lang.Runnable;
import java.text.DecimalFormat;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;

import com.example.bradmobile.testapp.ImageRenderer;
import com.google.android.gms.ads.InterstitialAd;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    @Override
    protected void onCreate(Bundle SavedInstance){

        super.onCreate(SavedInstance);
        setContentView(R.layout.start_layout);

        View newButton = findViewById(R.id.btn_new);
        newButton.setOnClickListener(this);
        View contButton = findViewById(R.id.btn_cont);
        contButton.setOnClickListener(this);
        View exitButton = findViewById(R.id.btn_exit);
        exitButton.setOnClickListener(this);
    }
    public void onClick(View v){

        Intent i;

        switch(v.getId()){
            case R.id.btn_new:
                i =  new Intent(this, game.class);
                i.putExtra("selection", "new");

                startActivity(i);
                break;
            case R.id.btn_cont:
                i = new Intent(this, LevelSelect.class);
                i.putExtra("selection", "cont");

                startActivity(i);
                break;
            case R.id.btn_exit:

                finish();
                break;

        }

    }
    @Override
    public void onPause(){
        super.onPause();
       // onTrimMemory(TRIM_MEMORY_UI_HIDDEN);
    }
}



