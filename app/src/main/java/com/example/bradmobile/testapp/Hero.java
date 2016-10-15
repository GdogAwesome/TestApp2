package com.example.bradmobile.testapp;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;



public class Hero extends HeroEntity {

	protected float x;
	protected float y;




	
	/**Creates a hero entity to hold main characters
	 * 
	 * pass the game this belongs to
	 * @param x pos of character cast as an int
	 * @param y pos of character cast as an int
	 */
	public Hero (Context context, Canvas canvas, int x, int y ){
		super(context,x, y);

		


		
	}



}



