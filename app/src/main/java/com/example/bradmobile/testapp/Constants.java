package com.example.bradmobile.testapp;

import android.content.Context;
import android.util.Log;
import android.view.Display;
import android.view.WindowManager;

public  class Constants {


	public static int SCREEN_WIDTH = 1366;
	public static  int SCREEN_HEIGHT = 768;

	public static  int MIDDLE_SPACE = SCREEN_WIDTH - (int)(SCREEN_WIDTH * .27);
	public static  int GROUND_LEVEL = SCREEN_HEIGHT - ((int)(SCREEN_HEIGHT * .15));
	public static  int CONTROL_HEIGHT = SCREEN_HEIGHT - ((int)(SCREEN_HEIGHT * .35));
	public static  int ARROW_HEIGHT = SCREEN_HEIGHT / 10;
	public static  int ARROW_WIDTH = SCREEN_WIDTH / 10;
	public static  int CIRCLE_DIAMETER = SCREEN_HEIGHT / 5;
	public static  int BLOCK_HEIGHT = SCREEN_HEIGHT/ 3 ;
	public static  int BLOCK_WIDTH = SCREEN_WIDTH / 4 ;
	public static  int SEGMENT_WIDTH = (BLOCK_WIDTH / 2);
	public static  int SEGMENT_HEIGHT = (BLOCK_HEIGHT / 2);
	public static  int RUN_SPEED = SCREEN_WIDTH / 256;
	public static  int CHARACTER_WIDTH = BLOCK_WIDTH;
	public static  int CHARACTER_HEIGHT = (int)(CHARACTER_WIDTH * .8);
	public static  int START_X = SCREEN_WIDTH / 6;
	public static  int MAIN_CHAR_WIDTH = 300;
	public static  int MAIN_CHAR_HEIGHT = 250;
	public static  float scale = 1;
	public static boolean music =false;
	public static boolean sfx = true;
	public static  int FRAME_VARIANCE = 4;
	public static int CHARACTER_LEG_HEIGHT;
    public static int CHARACTER_TORSO_HEIGHT;
	public static  int FALL_SPEED = 90;
	public static  int SHOT_SPEED = RUN_SPEED * 3;
	public static int RIGHT_BORDER = (int)(SCREEN_WIDTH * .66);
	public static int LEFT_BORDER = (int)(SCREEN_WIDTH * .22);
	public static int H_BAR_WIDTH ;
	public static int H_BAR_HEIGHT;
	
	public static void setScreen(int w, int h){
		SCREEN_WIDTH = w;
		 SCREEN_HEIGHT = h;
		if(SCREEN_WIDTH >= 1600) {
			scale = 1f + (1 - (1600f / SCREEN_WIDTH));
		}else{
			scale = ( SCREEN_WIDTH / 1600f );
		}
		//Log.d("Scale", Float.toString(scale));
		//Log.d("screen width", Integer.toString(SCREEN_WIDTH));
		 MIDDLE_SPACE = SCREEN_WIDTH - (int)(SCREEN_WIDTH * .17);
		 GROUND_LEVEL = SCREEN_HEIGHT - ((int)(SCREEN_HEIGHT * .15));
		 CONTROL_HEIGHT = SCREEN_HEIGHT - ((int)(SCREEN_HEIGHT * .35));
		 ARROW_HEIGHT = SCREEN_HEIGHT / 10;
		 ARROW_WIDTH = SCREEN_WIDTH / 10;
		 CIRCLE_DIAMETER = SCREEN_HEIGHT / 5;
		 BLOCK_HEIGHT = SCREEN_HEIGHT/ 3 ;
		 BLOCK_WIDTH = SCREEN_WIDTH / 4 ;
		 SEGMENT_WIDTH = (BLOCK_WIDTH / 2);
		 SEGMENT_HEIGHT = (BLOCK_HEIGHT / 2);
		RIGHT_BORDER = (int)(SCREEN_WIDTH * .55);
		LEFT_BORDER = (int)(SCREEN_WIDTH * .26);
		H_BAR_WIDTH = (int)( SCREEN_WIDTH * .2f);
		H_BAR_HEIGHT = (int)(H_BAR_WIDTH * .125f);


		 CHARACTER_WIDTH = SCREEN_WIDTH / 5;//BLOCK_WIDTH;
		RUN_SPEED = CHARACTER_WIDTH / 48;// SCREEN_WIDTH / 256;
		 CHARACTER_HEIGHT = (int)(CHARACTER_WIDTH * .8);
		CHARACTER_LEG_HEIGHT = (int)(CHARACTER_HEIGHT * .6);
        CHARACTER_TORSO_HEIGHT = (int)(CHARACTER_HEIGHT * .4);
		 START_X = SCREEN_WIDTH / 6;
		 MAIN_CHAR_WIDTH = 300;
		 MAIN_CHAR_HEIGHT = 250;
		 FRAME_VARIANCE = 4;

		 FALL_SPEED = 90;
		 SHOT_SPEED = RUN_SPEED * 6;
		
	}
	public static boolean getMusicState(){
		return music;
	}
	public static boolean getSfxState(){
		return sfx;
	}

}

