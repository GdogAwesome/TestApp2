package com.example.bradmobile.testapp;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.media.AudioManager;
import android.media.SoundPool;
import android.util.Log;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;



public class FlyingEnemy extends EnemyEntity {

	//protected float x;
	//protected float y;
	//Image image;


	
	/**Creates a hero entity to hold main characters
	 * 
	 * pass the game this belongs to
	 * @param x pos of character cast as an int
	 * @param y pos of character cast as an int
	 */
	public FlyingEnemy (Context context,
						int x, int y ){

		super(x, y);


		
	}
    @Override
	public void move(float heroX, float heroY){
		super.move(heroX, heroY);

				animCounterX++;
				if(!dying) {
					if (animCounterX >= 10) {
						if (!reverseAnim) {
							if (animFrameX < 3) {
								animFrameX += 1;
							} else {
								reverseAnim = true;
							}
						} else if (reverseAnim) {
							if (animFrameX > 0) {
								animFrameX -= 1;
							} else {
								reverseAnim = false;
							}
						}
						animCounterX = 0;


					}
					if((this.x - heroX) > 10){
						this.x -= moveSpeed;
						facingForward = false;
					}else if((this.x - heroX)< -10){
						this.x += moveSpeed;
						facingForward = true;

					}

					if(( this.y - (heroY + 25)) < -10){
						this.y += moveSpeed;
					}else if((this.y -(heroY + 25)) > 10){

						this.y -= moveSpeed;
					}


					if((this.y + 100) > (heroY + 60) && (this.y +100)< (heroY + 200)){
						tryToShoot();
					}

				}else{
					if(justDied){
						animCounterX = 0;
						//sp.play(soundIds[0],1, 1, 1, 0, 1.0f);
						animFrameX = 0;
						animFrameY = 1;

						justDied = false;
					}
					animCounterX++;
					if (animCounterX >= 20) {
						if (animFrameX < 3) {
							animFrameX += 1;
							animCounterX = 0;

						}else{
							dead = true;
						}
					}
				}

		
	}





}
