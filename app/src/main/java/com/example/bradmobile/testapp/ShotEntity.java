package com.example.bradmobile.testapp;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.media.AudioManager;
import android.media.SoundPool;
import android.util.Log;


import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;


public class ShotEntity {
	private Bitmap shotImage;

	
	private ArrayList<Shot> activeShots = new ArrayList<Shot>();
	private int[] tempDraw = new int[5];
	private boolean[] bulletState = new boolean[50];
	private boolean[] friendlyState = new boolean[50];
	private boolean[] enemyActive;


    private int heroHealth = 100;
    private int hBarFrame = 200;
	private int totalScore = 0;
    private int lowestLevel = 0;
    private String scoreString = null;

    private float originalHFrame = Constants.H_BAR_WIDTH;
    private float hBarHeight = Constants.H_BAR_HEIGHT;
	private float hBorderWidth = originalHFrame * 1.225f;
	private float barBorderWDiff = (hBorderWidth - originalHFrame) * .5f;
    private float hBorderHeight = hBarHeight * 2;
    private float barBorderHDiff = (hBorderHeight - hBarHeight) * .5f;
    private float hDrawFrame = originalHFrame;
    private float fullHealth = 100f;

	private Shot[] shotArray = new Shot[50];
	private EnemyEntity[] enemyList = new EnemyEntity[30];
	private Item[] items = new Item[10];
    private Item tempItem;
	private boolean[] itemActive = new boolean[10];
	private float xView = 0;
    private float hitAreaX;
    private float hitAreaY;
    private int hMarkerTimer = 0;
    private boolean drawHmarker = false;
	private int hitTalley = 0;
    private int lastHit = 0;
	Canvas canvas;
	private int[][][] obstacleList = new int[6][4][4];
	private static int SCREEN_WIDTH = Constants.SCREEN_WIDTH;
	private static int SCREEN_HEIGHT = Constants.SCREEN_HEIGHT;
	static int CHARACTER_WIDTH = Constants.CHARACTER_WIDTH;
	private int ShotSize = CHARACTER_WIDTH / 7;
	private RectF whereToDraw;
	private int[] soundIds = new int[10];
	private Rect frameToDraw;
	private SoundPool sp;
	Paint paint;
	
	Shot shot;
	HeroEntity hero;
	public ShotEntity(Context context){
		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inPreferredConfig = Bitmap.Config.RGB_565;

		this.shotImage = BitmapFactory.decodeResource(context.getResources(), R.drawable.test_shot2, null);
		//shotImage = Bitmap.createScaledBitmap(shotImage, 640, 400, false);

		whereToDraw = new RectF();
		frameToDraw = new Rect();
		paint = new Paint();
        paint.setTextSize(50);
        //int color = makeBitmap.context().getResources().getColor(R.color.red ,null);
        //paint.setColor(color);
		for(int i = 0; i < 50; i ++ ){
			shot = new Shot();
			shotArray[i] = shot;
		}
        for(int i = 0; i < items.length; i++){

            tempItem = new Item();
            items[i] = tempItem;
            itemActive[i] = false;
        }
		 sp = new SoundPool(15, AudioManager.STREAM_MUSIC, 0);
		soundIds[0] = sp.load(context,R.raw.laser,1);

	}

	public void ShotFired(int[] shotStats, boolean friendly){

		//activeShots.add(shot);
		if(shotStats[5] == Item.DEFAULT_VALUE) {

			for (int i = 0; i < 50; i++) {

				if (!bulletState[i]) {
					sp.play(soundIds[0], 1, 1, 1, 0, 1.0f);
					shotArray[i].fireShot(shotStats[0], shotStats[1], 0, 0, ShotSize, shotStats[4], shotStats[3], shotStats[2], friendly);
					bulletState[i] = true;
					friendlyState[i] = friendly;
					i = 50;

				}

			}
		}else if(shotStats[5] == Item.WEAPON_UPGRADE_SPRAY){
			int shotsFired = -1;
			for (int i = 0; i < 50; i++) {


				if (!bulletState[i]) {


					shotArray[i].fireShot(shotStats[0], shotStats[1], 0, 0, ShotSize, (shotStats[4] + shotsFired), shotStats[3], shotStats[2], friendly);
					bulletState[i] = true;
					friendlyState[i] = friendly;
					shotsFired ++;
					if(shotsFired > 1) {
						sp.play(soundIds[0], 1, 1, 1, 0, 1.0f);
						i = 50;
					}

				}

			}


		}
		

		
	}
	public void updateObstacles(int ob[][][]){
		this.obstacleList = ob;
	}
	public void addEntities(HeroEntity hero){
		this.hero = hero;
		
	}
	public void addEnemies(EnemyEntity[] enemies, boolean[] activeEnemies ){
		this.enemyList = enemies;
		this.enemyActive = activeEnemies;
	}
	public void updateShots(float xView){
			this.xView = xView;
			for(int i =0; i < 50; i++){

				if(bulletState[i]){
					//System.out.println("Bullet number "  + i);
					shotArray[i].advanceShot();
					
					if(shotArray[i].dead()){
						
						bulletState[i] = false;
					}else if(shotArray[i].drawShot()[0] > SCREEN_WIDTH + xView || shotArray[i].drawShot()[0]< 0 + xView){
						bulletState[i] = false;
						
					}else if(!shotArray[i].isFriendly()){
						
						if(shotArray[i].drawShot()[0] <= (hero.x + 175) && shotArray[i].drawShot()[0] >= (hero.x  + 100) && shotArray[i].drawShot()[1] >= hero.y && shotArray[i].drawShot()[1] <= (hero.y + 300)){
							//bulletState[i] = false;
							if(!shotArray[i].dying()){
								heroHealth = hero.hit(shotArray[i].getShotStrength());
								shotArray[i].impact(true);
                                hBarFrame = 200 - (int)((fullHealth - heroHealth) * 2);
                                hDrawFrame = originalHFrame - (((fullHealth - heroHealth) / fullHealth)* originalHFrame);

                               // Log.d("hBar", Float.toString(hDrawFrame));

							}
						}

					}else if(shotArray[i].isFriendly()){
						
						for(int k =0; k < 30; k++){
							if(enemyActive[k]) {
								if (shotArray[i].drawShot()[0] <= (enemyList[k].x +enemyList[k].getWidth()) && shotArray[i].drawShot()[0] >= (enemyList[k].x) && shotArray[i].drawShot()[1] >= enemyList[k].y && shotArray[i].drawShot()[1] <= (enemyList[k].y + enemyList[k].getHeight())) {
									//bulletState[i] = false;

									if (!shotArray[i].dying()) {
										lastHit = enemyList[k].hit(shotArray[i].getShotStrength());

										if(enemyList[k].justDied && enemyList[k].dying){
											for(int j = 0; j < items.length; j++){

												if(!itemActive[j]){

													itemActive[j] = true;
													/*

													get ground for obstacle to drop on
													 */
													for(int o = 0; o < 6; o++) {
                                                        for (int p = 0; p < 4; p++) {
                                                            if (enemyList[k].x >= (obstacleList[o][p][0]) && enemyList[k].y <= obstacleList[o][p][2]) {
                                                                if(obstacleList[o][p][1] > lowestLevel){
                                                                    lowestLevel = obstacleList[o][p][1];
                                                                }

                                                            }


                                                        }

                                                    }

                                                    items[j].initItem(enemyList[k].getX(), enemyList[k].getY(),Item.WEAPON_UPGRADE_SPRAY, lowestLevel);
                                                    lowestLevel = 0;
                                                    j = 10;
												}

											}

										}
                                        totalScore += lastHit;
										shotArray[i].impact(true);
                                        hitAreaX = shotArray[i].drawShot()[0];
                                        hitAreaY = shotArray[i].drawShot()[1];
                                        hMarkerTimer = 0;
                                        drawHmarker = true;

                                    }
								}
							}
						}
					}
					
					if(!shotArray[i].dying()){
						for(int o = 0; o < 6; o++){
							for(int j = 0; j < 4; j++){
								if((shotArray[i].drawShot()[0]+ shotArray[i].drawShot()[4]) >= (obstacleList[o][j][0] ) && shotArray[i].drawShot()[0] <= obstacleList[o][j][2])
								{

									
									if( (shotArray[i].drawShot()[1]  + shotArray[i].drawShot()[4] ) > obstacleList[o][j][1] && shotArray[i].drawShot()[1] < obstacleList[o][j][3]){
										
									
										shotArray[i].impact(true);
										o = 6;
										j = 4;
									}

									
								}
								
							
						}
					}
					
				}

			}
			}



        if(hMarkerTimer >= 100){
            drawHmarker = false;
        }else{
            hMarkerTimer ++;
        }
        for(int i = 0; i < 10; i++){
            if (itemActive[i]) {
                if(items[i].active) {
                    items[i].update(xView);
                    if((hero.x - xView) <= items[i].getDrawStats()[0] && (hero.x + hero.CHARACTER_WIDTH) - xView >= items[i].getDrawStats()[2]
                            && hero.y <= items[i].getDrawStats()[1] && hero.y + hero.CHARACTER_HEIGHT >= items[i].getDrawStats()[3])
                    {

                        hero.setUpgrade(items[i]);
                        itemActive[i] = false;
                    }
                }else{
                    itemActive[i] = false;
                }
            }
        }

		
	}

	
	public void drawShots(Canvas canvas){
		
			for(int i =0; i < 50; i++){
				if(bulletState[i]){
					tempDraw = shotArray[i].drawShot();

					whereToDraw.left = (tempDraw[0] - xView);
					whereToDraw.top = tempDraw[1];
					whereToDraw.right = ((tempDraw[0]+ tempDraw[4])- xView);
					whereToDraw.bottom = (tempDraw[1] + tempDraw[4]);

					frameToDraw.left = tempDraw[2];
					frameToDraw.top = tempDraw[3];
					frameToDraw.right = tempDraw[2]+50;
					frameToDraw.bottom = tempDraw[3]+50;

					canvas.drawBitmap(shotImage,frameToDraw, whereToDraw, paint);
					//g.drawImage(shotImage, (tempDraw[0] - xView), tempDraw[1], ((tempDraw[0]+ tempDraw[4])- xView),(tempDraw[1] + tempDraw[4]),  tempDraw[2], tempDraw[3], (tempDraw[2]+50), (tempDraw[3]+50), null);
			
				}
			}

        /**
         * draw hit marker
         */

        if(drawHmarker){

            whereToDraw.left = hitAreaX - xView;
            whereToDraw.top = hitAreaY;
            whereToDraw.right = (hitAreaX + 100) - xView;
            whereToDraw.bottom = (hitAreaY + 50);

            frameToDraw.left = 0;
            frameToDraw.top = 50;
            frameToDraw.right = 100;
            frameToDraw.bottom = 100;

            canvas.drawBitmap(shotImage,frameToDraw, whereToDraw, paint);

        }
        /**
         *
         * draw health border
         */
        whereToDraw.left = 0;
        whereToDraw.top = 0;
        whereToDraw.right = originalHFrame + 2 * barBorderWDiff;
        whereToDraw.bottom = hBarHeight + (2 * barBorderHDiff);

        frameToDraw.left = 200;
        frameToDraw.top = 100;
        frameToDraw.right = 425;
        frameToDraw.bottom = 150;

        canvas.drawBitmap(shotImage,frameToDraw, whereToDraw, paint);

        /**
         *
         * draw health bar
         */

        whereToDraw.left = barBorderWDiff;
        whereToDraw.top = 0 + barBorderHDiff;
        whereToDraw.right = hDrawFrame + barBorderWDiff;
        whereToDraw.bottom = 0 + barBorderHDiff + hBarHeight;

        frameToDraw.left = 0;
        frameToDraw.top = 100;
        frameToDraw.right = hBarFrame;
        frameToDraw.bottom = 125;

        canvas.drawBitmap(shotImage,frameToDraw, whereToDraw, paint);
        /**
         *
         * draw items
         */
        frameToDraw.left = 150;
        frameToDraw.top = 0 ;
        frameToDraw.right = 250;
        frameToDraw.bottom = 50;
        for(int i = 0; i< 10; i++){
            if(itemActive[i]){

                whereToDraw.left = items[i].getDrawStats()[0];
                whereToDraw.top = items[i].getDrawStats()[1];
                whereToDraw.right = items[i].getDrawStats()[2];
                whereToDraw.bottom = items[i].getDrawStats()[3];


                canvas.drawBitmap(shotImage,frameToDraw, whereToDraw, paint);

            }

        }


        /**
         *
         *
         * drawScore
         */
        scoreString = Integer.toString(totalScore);

       // canvas.drawText(scoreString,SCREEN_WIDTH - 200, 0,paint);

    }
	public void resetHealth(){
		this.heroHealth = 100;
		hBarFrame = 200 - (int)((fullHealth - heroHealth) * 2);
		hDrawFrame = originalHFrame - (((fullHealth - heroHealth) / fullHealth)* originalHFrame);
	}
    public void nullImage(){
		if(shotImage != null) {
			shotImage.recycle();
			shotImage = null;
		}
	}

	

}
