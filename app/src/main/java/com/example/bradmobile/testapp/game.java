package com.example.bradmobile.testapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.widget.RelativeLayout;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;

public class game extends AppCompatActivity {
    ImageRenderer view;
    RelativeLayout surface;
    InterstitialAd mInterstitialAd;
    String selection = null;
    boolean fromAd = false;
    public boolean returned = false;
    int mapNo = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        selection = getIntent().getExtras().getString("selection");

        view = new ImageRenderer(this);

        surface = (RelativeLayout)findViewById(R.id.gameLayout);
        surface.setGravity(Gravity.TOP);
        //view = (ImageRenderer) findViewById(R.id.Renderer);
        // Log.d("hw", Boolean.toString(view.isHardwareAccelerated()));
        AdView mAdView = new AdView(this);//(AdView) findViewById(R.id.adView);
        mAdView.setAdUnitId("ca-app-pub-3940256099942544/6300978111");
        mAdView.setAdSize(AdSize.BANNER);

        surface.addView(mAdView);
        surface.addView(view);


        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .build();
        mAdView.loadAd(adRequest);
        mAdView.setBackgroundColor(Color.TRANSPARENT);


        /**
         *
         * set up add
         */
        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId("ca-app-pub-3940256099942544/1033173712");


        mInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                requestNewInterstitial();
                fromAd = true;

                launchMenu();
            }
                                          @Override
                                          public void onAdOpened() {
                                              fromAd = true;
                                              view.pause();
                                              view.sceneChange();
                                              //Log.e("ad Opened","true");



                                          }
            @Override
            public void onAdLeftApplication(){

                fromAd = true;
                //Log.e("left application", "true");
            }
        }


        );


        requestNewInterstitial();
        view.setAd(mInterstitialAd);

    }


    @Override
    protected void onPause(){

            super.onPause();
            view.pause();
            returned = true;
            selection = "paused";



    }
    public void launchMenu(){


        AlertDialog alertDialog = new AlertDialog.Builder(this).create();
        alertDialog.setTitle("You Have Died");
        alertDialog.setMessage("Do you want to Quit to Menu or Restart the map?");
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "Restart",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                      //  selection = "restart";


                        view.startNew(getApplicationContext(),mapNo,false);
                    }
                });
        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "Quit",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Intent i = new Intent(getApplicationContext(), MainActivity.class);
                        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        i.putExtra("selection", "first");
                        startActivity(i);
                    }
                });


        alertDialog.show();
        /*
        Intent i = new Intent(this, MainActivity.class);
        i.putExtra("selection", "first");
        startActivity(i);
        */

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
       // view.pause();

        inflater.inflate(R.menu.cont_menu, menu);


        return true;
    }


    @Override

    protected void onResume(){
        super.onResume();
        //Log.d("selection",selection);
        if(!fromAd) {
            if (selection.equals("new")) {
                view.startNew(this, 1, returned);
                //selection = "cont";
            } else if (selection.equals("cont")) {
                mapNo = getIntent().getExtras().getInt("mapNo");
                view.startNew(this, mapNo, returned);
            } else if (selection.equals("paused")) {

                view.resume();
            } else if (selection.equals("restart")) {
                view.startNew(this, mapNo, returned);
            }
        }else{
            fromAd = false;
        }

    }

    @Override
    public void onBackPressed() {
       // super.onBackPressed();
        view.pause();
        selection = "paused";

        AlertDialog alertDialog = new AlertDialog.Builder(this).create();
        alertDialog.setTitle("Quit?");
        //alertDialog.setIcon(R.drawable.lock);
        alertDialog.setMessage("Do you really want to quit the game?");
        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Resume",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                       // selection = "restart";

                        view.resume();

                        //view.startNew(getApplicationContext(),mapNo,false);
                    }
                });
        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "Quit",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        view.sceneChange();
                        game.super.onBackPressed();
                    }
                });


        alertDialog.show();

        //view.setZOrderOnTop(false);
        //view.setZ(2);
        /*
        AlertDialog alertDialog = new AlertDialog.Builder(this).create();
        alertDialog.setTitle("Alert");
        alertDialog.setMessage("Alert message to be shown");
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

        alertDialog.show();
        */


    }


    private void requestNewInterstitial() {
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .build();

        mInterstitialAd.loadAd(adRequest);
    }
}
