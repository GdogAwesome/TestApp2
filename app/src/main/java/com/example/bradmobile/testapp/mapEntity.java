package com.example.bradmobile.testapp;

import android.support.v7.app.AppCompatActivity;
import android.view.SurfaceView;
import android.view.View;


public abstract class mapEntity extends AppCompatActivity {
	
	public float MapFinalPosX;
	public float MapFinalPosY;
	private mapBlock block;
	private mapBlock block1;
	
	
	public mapEntity( HeroEntity hero,float x, float y){
		this.MapFinalPosX = x;
		this.MapFinalPosY = y;
		
	}
	public void initMap(String ref){
		/*
		block = new mapBlock(MapFinalPosX + 600, MapFinalPosY + 300, 0, 0, 0, ref);
		block.setObstacleRect(MapFinalPosX + 148,MapFinalPosY + 123, 375, 150);
		
		block1 = new mapBlock(MapFinalPosX + 600, MapFinalPosY , 0, 0, 0, ref);
		*/
	}


}
 
