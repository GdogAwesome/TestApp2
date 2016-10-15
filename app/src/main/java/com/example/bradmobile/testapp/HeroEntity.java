package com.example.bradmobile.testapp;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.media.AudioManager;
import android.media.SoundPool;
import android.util.DisplayMetrics;
import android.util.Log;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;









public abstract class HeroEntity {
	protected float x;
	protected float y;

	Sprite sprite ;
	protected int xPosCounter = 0;
    protected int yPosCounter = 0;
	int shootTime = 175;
	int fireSectionSize = Constants.SCREEN_HEIGHT / 8;
	
	int counter = 0;
	int jumpCounter = 0;
    int pastShotHeight = 0;
	int shotAngle = 90;
    float lastRotation = 0;
	float torsoAngle = 0;
	int r = 0;
	int b = 0;
	protected int[] fireStats = new int[6];
	public Long lastShot = 0L;
	public boolean canShoot = false;
	private float currentJumpPos = -12.2F * Constants.scale;
	static int FRAME_VARIANCE = Constants.FRAME_VARIANCE ;
	private static int FALL_SPEED = Constants.FALL_SPEED;
	private static int SHOT_SPEED= Constants.SHOT_SPEED;
	private float lastCount = FRAME_VARIANCE;
	private float lastJumpCount = FRAME_VARIANCE;
	protected boolean doneStart = false;
	protected boolean startJumping = true;
	private boolean tempWeapon = false;
	private int upgradeTime = 0;
	protected boolean running = false;
	private float currentJumpHeight = 0;
	protected boolean firstRun = true;
	public int shotType = Item.DEFAULT_VALUE;
	private float beginningPos = 0;
	MessageBox mBox ;
	/**
	 * set up dying flags
	 */
	public boolean dying = false;
	private boolean dead = false;
	private boolean justDied = false;

	/**
	 *
	 *
	 * setting dialogue box up
	 */
	private boolean drawHM = false;
	private int hDialogueH = 100;
	private int hDialogueW = 100;
	private int mCornerSq = 14;


	private int shotPower = 20;
	private int shotSpeed = 100;
    private int health = 100;
	private float heightDifference = 0;
	protected boolean contJump = false;
	protected boolean startFalling = true;
	protected boolean falling = false;
	private float fallMomentum = 0;
	private float momentum =0;
	private float heightCheck = 0;
	public int groundLevel;
	public int footLevel;
	String heroDialogue = null;
	public boolean facingForward = true;
	private boolean canMoveRight =false;
	private boolean canMoveLeft =false;
	static int CHARACTER_WIDTH = Constants.CHARACTER_WIDTH;
	static int CHARACTER_HEIGHT = Constants.CHARACTER_HEIGHT;
	static int CHARACTER_LEG_HEIGHT = Constants.CHARACTER_LEG_HEIGHT;
	static int CHARACTER_TORSO_HEIGHT = Constants.CHARACTER_TORSO_HEIGHT;
	static int RUN_SPEED = Constants.RUN_SPEED;
    private int lives = 3;

	private float screenX;
	private float screenY;
	private int ShotSize = CHARACTER_WIDTH / 6;
	protected int[] displayPosX = new int[]{0,300,600, 900, 1200};
    protected int[] reversePosX = new int[]{1200, 900, 600, 300, 0};
	protected int[] displayPosY = new int[]{0,150, 300, 450, 600, 750,900};
	private Canvas canvas;
	private Canvas rotateCanvas;
    private Matrix flipMatrix = new Matrix();
    private Matrix RotateTorso = new Matrix();
    private Bitmap torso;
    private Bitmap reverseLegs;
	private SoundPool sp;
	private int[] soundIds = new int[10];
    Paint paint;
    private Rect frameToDraw ;
    private Rect locationFrame;
    private RectF whereToDraw ;
	private RectF[] whereToDrawM = new RectF[9];
	private Rect[] frameToDrawM = new Rect[9];
    Bitmap reverseHero;
	BitmapFactory.Options options;
											

	
	long delta = 0;
	ArrayList<Sprite> sprites = new ArrayList();
	ArrayList<Sprite> reverseSprites = new ArrayList();

	Bitmap heroImage;

	/**
	 * initialize character at starting pos x,  Image urly
	 *
	 * character width and height
	 * @param x
	 * @param y
	 */
	public HeroEntity(Context context, int x, int y){

		this.x = x;
		this.y = y;
		mBox = new MessageBox(context);
       // this.heroImage = heroImage;
		//this.canvas = canvas;

		BitmapFactory.Options options =new BitmapFactory.Options();
		options.inPreferredConfig = Bitmap.Config.RGB_565;
		options.inSampleSize = 1;

        whereToDraw = new RectF(x, y,CHARACTER_WIDTH, CHARACTER_HEIGHT);
        frameToDraw = new Rect(0,0, 300,250);
        locationFrame = new Rect(0, 0,0,0);


		heroImage = BitmapFactory.decodeResource(context.getResources(), R.drawable.pos3, options);
		//heroImage = Bitmap.createScaledBitmap(heroImage, 1200, 1250, true);

        flipMatrix.preScale(-1, 1);
        reverseLegs = Bitmap.createBitmap(heroImage,0, 0 , 1200, 1250, flipMatrix, true);

		sp = new SoundPool(10, AudioManager.STREAM_MUSIC, 0);
		soundIds[0] = sp.load(context,R.raw.pain,1);


		//torso = Bitmap.createBitmap(300, 110, heroImage.getConfig());
		//rotateCanvas = new Canvas(torso);
		rotateTorso(0);

		groundLevel = y + CHARACTER_HEIGHT;
        paint = new Paint();

		paint.setTextSize(50);



		//this.footLevel = this.yPosCounter - 350;
		//System.out.println("foot level: "+footLevel);
		
	}

	
	/**Initializes the entity, loading all of its images into an array from spritesheet that is passed to it.
	 * 
	 * @param source, pass the location of source image
	 */
	public void InitHero(String source, int width, int height){


		CHARACTER_HEIGHT = height;
		CHARACTER_WIDTH = width;
		//System.out.println("character height " +CHARACTER_HEIGHT);
	
		//this.groundLevel = (int)(this.y -250);

	
	}
	
	public void draw(Canvas canvas)
	{
		//reverseSprites.get(b).draw(g, (int)x, (int)y);

    if(!dead) {
        if (!dying) {
            if (facingForward) {

                //Draw torso
                whereToDraw.left = x - screenX;
                whereToDraw.top = y;
                whereToDraw.right = x + CHARACTER_WIDTH - screenX;
                whereToDraw.bottom = y + CHARACTER_TORSO_HEIGHT + (int) (CHARACTER_TORSO_HEIGHT * .1);

                frameToDraw.left = 0;
                frameToDraw.top = 0;
                frameToDraw.right = 300;
                frameToDraw.bottom = 110;

                //frameToDraw = new Rect( 0, 0, 300, 110);

                canvas.drawBitmap(torso, frameToDraw, whereToDraw, paint);

                //Draw Legs
                whereToDraw.left = x - screenX;
                whereToDraw.top = y + CHARACTER_TORSO_HEIGHT;
                whereToDraw.right = x + CHARACTER_WIDTH - screenX;
                whereToDraw.bottom = y + CHARACTER_HEIGHT;

                frameToDraw.left = displayPosX[xPosCounter];
                frameToDraw.top = displayPosY[yPosCounter];
                frameToDraw.right = (displayPosX[xPosCounter] + 300);
                frameToDraw.bottom = (displayPosY[yPosCounter] + 150);

                // frameToDraw = new Rect( displayPosX[xPosCounter], displayPosY[yPosCounter],(displayPosX[xPosCounter]+ 300), (displayPosY[yPosCounter]+ 150)  );

                canvas.drawBitmap(heroImage, frameToDraw, whereToDraw, paint);


                //sprite.draw(g, (int)x, (int)y, (int)x + CHARACTER_WIDTH, (int)y + CHARACTER_HEIGHT, displayPosX[xPosCounter], displayPosY[yPosCounter], (displayPosX[xPosCounter]+ 300), displayPosY[yPosCounter] + 250);
            } else {
                //Draw torso
                whereToDraw.left = x - screenX;
                whereToDraw.top = y;
                whereToDraw.right = x + CHARACTER_WIDTH - screenX;
                whereToDraw.bottom = y + CHARACTER_TORSO_HEIGHT + (int) (CHARACTER_TORSO_HEIGHT * .1);

                frameToDraw.left = 0;
                frameToDraw.top = 0;
                frameToDraw.right = 300;
                frameToDraw.bottom = 110;

                //frameToDraw = new Rect( 0, 0, 300, 110);
                reverseImage(frameToDraw, torso);
                canvas.drawBitmap(reverseHero, frameToDraw, whereToDraw, paint);


                /**
                 *
                 * draw Legs
                 */
                whereToDraw.left = x - screenX;
                whereToDraw.top = y + CHARACTER_TORSO_HEIGHT;
                whereToDraw.right = x + CHARACTER_WIDTH - screenX;
                whereToDraw.bottom = y + CHARACTER_HEIGHT;

                locationFrame.left = reversePosX[xPosCounter] - 300;
                locationFrame.top = displayPosY[yPosCounter];
                locationFrame.right = (reversePosX[xPosCounter]);
                locationFrame.bottom = (displayPosY[yPosCounter] + 150);


                //locationFrame = new Rect( displayPosX[xPosCounter], displayPosY[yPosCounter],(displayPosX[xPosCounter]+ 300), (displayPosY[yPosCounter]+ 150)  );

                frameToDraw.left = 0;
                frameToDraw.top = 0;
                frameToDraw.right = 300;
                frameToDraw.bottom = 150;

                // frameToDraw = new Rect(0,0, 300, 150);
                //reverseImage(locationFrame,heroImage);
                canvas.drawBitmap(reverseLegs, locationFrame, whereToDraw, paint);
                //sprite.draw(g, (int)x, (int)y, (int)x + CHARACTER_WIDTH, (int)y + CHARACTER_HEIGHT, (displayPosX[xPosCounter]+ 300), displayPosY[yPosCounter], displayPosX[xPosCounter], displayPosY[yPosCounter] + 250);

            }
        } else {
            if (justDied) {
                sp.play(soundIds[0], 1, 1, 1, 0, 1);
                justDied = false;
                xPosCounter = 0;
                yPosCounter = 6;
                counter = 0;
                lives -= 1;

                //dying = true;

            } else {
                counter++;
                if (counter >= 20) {
                    if (xPosCounter < 3) {
                        xPosCounter++;
                        counter = 0;
                    } else {
                        if (lives >= 0) {
                            health = 100;
                        }
                        dying = false;
                        dead = true;
                    }
                }

            }
            whereToDraw.left = x - screenX;
            whereToDraw.top = y;
            whereToDraw.right = x + CHARACTER_WIDTH - screenX;
            whereToDraw.bottom = y + CHARACTER_HEIGHT;

            frameToDraw.left = displayPosX[xPosCounter];
            frameToDraw.top = displayPosY[yPosCounter];
            frameToDraw.right = (displayPosX[xPosCounter] + 300);
            frameToDraw.bottom = (displayPosY[yPosCounter] + 250);

            // frameToDraw = new Rect( displayPosX[xPosCounter], displayPosY[yPosCounter],(displayPosX[xPosCounter]+ 300), (displayPosY[yPosCounter]+ 150)  );

            canvas.drawBitmap(heroImage, frameToDraw, whereToDraw, paint);
        }
    }
		/**
		 *
		 * if message exists, draw it
		 */

		if(mBox.activeMessage){
			whereToDrawM = mBox.getWhereToDraw();
			frameToDrawM = mBox.getFrameToDraw();

			for(int i=0; i < 9; i++){

				canvas.drawBitmap(heroImage,frameToDrawM[i],whereToDrawM[i],paint);
			}
			if(mBox.drawText) {
				for (int i = 0; i < mBox.getText().length; i++) {
					canvas.drawText(mBox.getText()[i], mBox.getSX(), mBox.getSY(), mBox.getPaint());
					mBox.drawTY += mBox.getPaint().descent() - mBox.getPaint().ascent();
				}
			}
		}
	}
    public Bitmap reverseImage(Rect frameToDraw, Bitmap original){

        reverseHero = Bitmap.createBitmap(original,frameToDraw.left, frameToDraw.top,(frameToDraw.right - frameToDraw.left), (frameToDraw.bottom - frameToDraw.top), flipMatrix, true);
       // Log.d("imageWidth", Integer.toString((frameToDraw.right - frameToDraw.left)));
        return reverseHero;

    }
    public void rotateTorso(float angle){


		torso = Bitmap.createBitmap(300, 110, heroImage.getConfig());

		RotateTorso.setTranslate(-150f, -900f);

		RotateTorso.postRotate(angle, 0, 0);
		RotateTorso.postTranslate(150, 110);
		/**
		 *
		 * still usable
		 */
		this.rotateCanvas = new Canvas(torso);
		this.rotateCanvas.drawBitmap(heroImage, RotateTorso, paint);// new Paint( Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG | Paint.FILTER_BITMAP_FLAG ) );

        rotateCanvas = null;


    }
	
	/**
	 * 
	 * @param command, pass the command by way of int. 0 = not moving, 1 = moving right, 2 = moving left.
	 */
	public void move(int command, boolean canMoveLeft, boolean canMoveRight){

		this.canMoveLeft = canMoveLeft;
		this.canMoveRight = canMoveRight;

        if(!dying) {
            if (command == 1 && canMoveRight) {
                facingForward = true;
                running();
                running = true;
            } else if (command == 2 && canMoveLeft) {

                running();
                facingForward = false;
                running = true;

            } else if (command == 0 || !canMoveLeft || !canMoveRight) {

                Stop();
                running = false;

            } else if (contJump) {
                if (!doneStart) {

                }

            }
        }
		
	}
	public void jump(){
		

	if(!falling){
		if (firstRun){
			beginningPos = y;
			firstRun = false;
			lastJumpCount = FRAME_VARIANCE;
			currentJumpPos = -12.2F;
			contJump = true;
			momentum = 0;

	
		}else if(!firstRun){
			
			jumpCounter ++;

			if(jumpCounter >= lastJumpCount){
					
					if(contJump){
						
							heightCheck = heightDifference;
						
							heightDifference = ((-1* (currentJumpPos * currentJumpPos)) + CHARACTER_HEIGHT);			
							
	
							y = beginningPos - heightDifference;
							currentJumpPos ++;
							
	
							if(heightDifference <= heightCheck  ){//y >= beginningPos ){

								contJump = false;

								heightDifference = 0;
								jumpCounter = 0;
								
								
								firstRun = true;

							}

					}
					lastJumpCount += FRAME_VARIANCE;
				}
			}
		}
		
	}
	
	
	/**
	 * control what happens while use is running forward
	 */
	public void running(){
		if( doneStart && !contJump){
		counter++;
		if( momentum < 10){
			momentum ++;
		}
		
			if(counter >= lastCount){
			
				xPosCounter++;
				lastCount += FRAME_VARIANCE;
				if( xPosCounter >= (FRAME_VARIANCE * 2) && yPosCounter >= (FRAME_VARIANCE * 2)){
					
					counter = 0;
					lastCount = FRAME_VARIANCE;
					
				}
			
				if(xPosCounter >= 4){
					yPosCounter ++;
					xPosCounter = 0;
					
					if(yPosCounter >= 4 ){
						yPosCounter = 0;
					}
			    }
			}
		}else if ( !doneStart && !contJump)
		{
			counter ++;
			
			
			if(counter >= lastCount){
				xPosCounter ++;
				
				if(xPosCounter >= 4){
	
					xPosCounter = 0;
					yPosCounter = 0;
					counter = 0;
					lastCount = FRAME_VARIANCE;
					doneStart = true;
				}
					lastCount += FRAME_VARIANCE;
		
			}	
	    }
		
	}
	

	
	
	/**
	 * What is done while player is not moving character
	 */
	private void Stop(){
		
		yPosCounter = 4;
		xPosCounter = 0;
		counter = 0;
		lastCount = FRAME_VARIANCE;
		doneStart = false;
	}
	public void move(float mapPosX, boolean mR,boolean mL){

        this.canMoveLeft = mL;
        this.canMoveRight = mR;
		if(!dying && !dead) {


			if (facingForward && canMoveRight) {
				x += RUN_SPEED;
				//x -= mapPosX;
				screenX = mapPosX;

			} else if (!facingForward && canMoveLeft) {
				x -= RUN_SPEED;
				screenX = mapPosX;
				//x -= mapPosX;

			}
		}


	}
	
	public void falling(){
		if(!contJump){
		
			if(startFalling){
			
				fallMomentum = .02F;
				startFalling = false;	
				falling = true;
			}else if(!startFalling){
				if(fallMomentum <= 1){
					fallMomentum +=.01;
			}
				this.y += (FALL_SPEED * fallMomentum);
					if((this.y + CHARACTER_HEIGHT) >= groundLevel){
						
						this.y = groundLevel - CHARACTER_HEIGHT ;
						startFalling = true;
						falling = false;
					}
			}
		
		}	
		
	}
	
	
	/**
	 * Return status of jump
	 * @return
	 */
	public void setGround(int ground){
		this.groundLevel = ground; 
		
		//this.y = groundLevel - 250;
		//System.out.println("hero ground level: " + groundLevel);
	}
	public void calcFooting(){
		if((this.y + CHARACTER_HEIGHT) < groundLevel && !contJump){
			falling();
		}

	}
	public void updateMessage(float mapPosX){
		if(mBox.activeMessage){mBox.updateMessage(this.x - mapPosX, y);}
	}
    public void setShotHeight(int height){


		//if(pastShotHeight != height) {

            if (height < (fireSectionSize * 7) && height > (fireSectionSize * 6)) {
                this.shotAngle = 1;
                torsoAngle = 0;
                if(facingForward) {

                    fireStats[0] = (int) this.x + (int) ((CHARACTER_WIDTH / 2) + (CHARACTER_WIDTH * .18));
                    fireStats[1] = (int) this.y + (CHARACTER_TORSO_HEIGHT / 2) - 20;
                }else{
                    fireStats[0] = (int) this.x + (int) ((CHARACTER_WIDTH / 2) - (CHARACTER_WIDTH * .25));
                    fireStats[1] = (int) this.y + (CHARACTER_TORSO_HEIGHT / 2) - 20;

                }

            } else if (height < (fireSectionSize * 4) && height > (fireSectionSize * 3)) {
                this.shotAngle = 4;
                torsoAngle = -35f;
                if(facingForward) {


                    fireStats[0] = (int) this.x + (int) ((CHARACTER_WIDTH / 2) + (CHARACTER_WIDTH * .05));
                    fireStats[1] = (int) this.y + (CHARACTER_TORSO_HEIGHT / 2) - (int) (CHARACTER_TORSO_HEIGHT * .48);
                }else{
                    fireStats[0] = (int)(this.x + (CHARACTER_WIDTH / 2) - (CHARACTER_WIDTH * .03));
                    fireStats[1] = (int) (this.y + (CHARACTER_TORSO_HEIGHT / 2) - (CHARACTER_TORSO_HEIGHT * .48));

                }
            } else if (height < (fireSectionSize * 5) && height > (fireSectionSize * 4)) {
                shotAngle = 3;
                torsoAngle = -22f;
                if(facingForward) {
                    fireStats[0] = (int) this.x + (int) ((CHARACTER_WIDTH / 2) + (CHARACTER_WIDTH * .08));
                    fireStats[1] = (int) this.y + (CHARACTER_TORSO_HEIGHT / 2) - (int) (CHARACTER_TORSO_HEIGHT * .43);
                }else{
                    fireStats[0] = (int) this.x + (int) ((CHARACTER_WIDTH / 2) - (CHARACTER_WIDTH * .18));
                    fireStats[1] = (int) this.y +(int) (CHARACTER_TORSO_HEIGHT * .45);

                }
            } else if (height < (fireSectionSize * 6) && height > (fireSectionSize * 5)) {
				shotAngle = 2;
				torsoAngle = -15f;
                if (facingForward) {


                    fireStats[0] = (int) this.x + (int) ((CHARACTER_WIDTH / 2) + (CHARACTER_WIDTH * .13));
                    fireStats[1] = (int) this.y + (CHARACTER_TORSO_HEIGHT / 2) - (int) (CHARACTER_TORSO_HEIGHT * .35);
                }else{
                    fireStats[0] = (int) this.x + (int) ((CHARACTER_WIDTH / 2) - (CHARACTER_WIDTH * .20));
                    fireStats[1] = (int) this.y + (CHARACTER_TORSO_HEIGHT / 2) - (int) (CHARACTER_TORSO_HEIGHT * .39);

                }
				pastShotHeight = shotAngle;
			}

            if(lastRotation != torsoAngle){
                rotateTorso(torsoAngle);
				lastRotation = torsoAngle;
            }


       //}



    }
	public int[] fireStats(){
		if(facingForward){

			shotSpeed = SHOT_SPEED;

		}else{
            //shotAngle = shotAngle * -1;

			shotSpeed = -1 * (SHOT_SPEED);
		}

		if(tempWeapon){
			if(upgradeTime >0){
				upgradeTime --;
			}else{
				tempWeapon = false;
				shotType = Item.DEFAULT_VALUE;
			}

		}
		
		

		fireStats[2] = shotSpeed;
		fireStats[3] = shotPower;
		fireStats[4] = shotAngle;
		fireStats[5] = shotType;

		return fireStats;
	}
	public void displayMessage(int num){
		switch(num){
			case 1:
				heroDialogue = "HELLO! /n Welcome To The Game!";
				//Log.d("dialogue length", ""+heroDialogue.length()+"");

				mBox.showMessage(heroDialogue, 4 ,false);
				//mBox.activeMessage = true;
				break;
			case 2:

				heroDialogue = "Still Playing?/n This is kind of boring don't you think?";
				mBox.showMessage(heroDialogue, 6, false);
		}

	}

	public boolean contJump(){
		return this.contJump;
	}
	public boolean isRunning(){
		return this.running;
	}
	public boolean isFalling(){
		return this.falling;
	}
	public int getY(){
		return (int)y;
	}
	public int hit(int hitStrength){


        if(!dying) {
            health -= hitStrength;

	        if (health <= 0) {
		        dying = true;
		        justDied = true;

	        }
        }
		return health;

	}
    public void reset(){
        this.dead = false;
        this.health = 100;
        this.facingForward = true;
        this.x -= CHARACTER_WIDTH;
        this.y = 50;
		Stop();
    }
	public void nullImage(){
		if(heroImage != null) {
			heroImage.recycle();
			heroImage = null;

			torso.recycle();
			torso = null;

			reverseLegs.recycle();
			reverseLegs = null;
		}
	}
	public void setUpgrade(Item item){

		switch(item.getUType()){
			case Item.WEAPON_UPGRADE_SPRAY:
				tempWeapon = true;
				upgradeTime = item.getUTime();
				shotType = item.getUType();

				break;

		}
	}
	public void tryToShoot(){

		if((System.currentTimeMillis() - lastShot)> shootTime){

			canShoot = true;

			// shots.ShotFired(fireStats(), false);
			lastShot = System.currentTimeMillis();


		}
	}

    public boolean getDead(){
        return dead;
    }
    public int getLives(){
        return lives;
    }
	public void dropImage(){
		heroImage = null;
	}

}
