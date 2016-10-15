package com.example.bradmobile.testapp;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.media.AudioManager;
import android.media.SoundPool;
import android.util.Log;


import java.util.ArrayList;

public class EnemyEntity {
	protected float x;
	protected float y;
	Sprite sprite ;
	protected int xPosCounter = 0;
	protected int yPosCounter = 0;
	int shootTime = 300;
	private int health = 100;
	public int dSound = 0;

    int counter = 0;
	int shotSpeed = 20;
	int shotPower = 10;
	int jumpCounter = 0;
	int r = 0;
	int b = 0;
	float xView = 0f ;
	int yView = 0;
	int moveSpeed = 1;
	int shotAngle = 1;
	float currentJumpPos = -12.2F;
	static int FRAME_VARIANCE = 2;
	float lastCount = FRAME_VARIANCE;
	float lastJumpCount = FRAME_VARIANCE;
	protected boolean doneStart = false;
	protected boolean startJumping = true;
	protected boolean running = false;
	private int[] fireStats = new int[6];
	boolean willShoot = false;
	Long lastShot = 0L;
	public boolean hasItem = false;
	boolean canShoot = true;
	float  currentJumpHeight = 0;
	protected boolean firstRun = true;
	float beginningPos = 0;
	float heightDifference = 0;
	protected boolean contJump = false;
	protected boolean startFalling = true;
	protected boolean falling = false;
	float fallMomentum = 0;
	float momentum =0;
	float shotTime = 600f;
	float heightCheck = 0;
	public int groundLevel;
	public int footLevel;
	public int hitPoints = 100;
	public boolean facingForward = true;
	static int CHARACTER_WIDTH = Constants.CHARACTER_WIDTH;
	static int CHARACTER_HEIGHT = Constants.CHARACTER_HEIGHT;
	private static int FALL_SPEED = 120;
	public int animCounterX = 0;
	public int animCounterY = 0;
	public int animFrameX = 0;
	public int animFrameY = 0;
	public boolean justDied = true;
    public int sampleFrameW = 0;
    public int sampleFrameH =0;

	public boolean dying = false;
	public boolean dead = false;
	public int shotType = Item.DEFAULT_VALUE;
	public boolean reverseAnim = false;
	public int[] soundIds = new int[10];
	public SoundPool sp;
	Canvas canvas;
    Paint paint;
	//ShotEntity shots;
    private RectF whereToDraw;
    private Rect frameToDraw;
	protected int[] displayPosX = new int[4];
	protected int[] displayPosY = new int[4];

	long delta = 0;
	ArrayList<Sprite> sprites = new ArrayList();
	ArrayList<Sprite> reverseSprites = new ArrayList();

	Bitmap enemyImage;

	/**
	 * initialize character at starting pos x, y
	 * Image url
	 * character width and height
	 * @param x
	 * @param y
	 */
	public EnemyEntity(int x, int y){

        paint = new Paint();
        whereToDraw = new RectF();
        frameToDraw = new Rect();

        this.enemyImage = enemyImage;
		this.x = x;
		this.y = y;
		this.canvas = canvas;
		groundLevel = y + CHARACTER_HEIGHT;
		//this.footLevel = this.yPosCounter - 350;
		//System.out.println("foot level: "+footLevel);
		
	}
	
	/**
	 * 

     * @param width
	 * @param height
	 * 
	 */
	public void InitEnemy( int width, int height,int FrameW, int FrameH ) {

		//this.shots = shots;
        CHARACTER_HEIGHT = height;
        CHARACTER_WIDTH = width;
        this.sampleFrameW = FrameW;
        this.sampleFrameH = FrameH;
        for(int i =0; i <4; i++){
            displayPosX[i] = i * sampleFrameW;
            displayPosY[i] = i * sampleFrameH;
        }
        //System.out.println("character height " +CHARACTER_HEIGHT);

        //this.groundLevel = (int)(this.y -250);

    }
	public RectF getWhereToDraw()
	{

		if(facingForward){

			whereToDraw.left = xView;
			whereToDraw.top = y;
			whereToDraw.right = xView+ CHARACTER_WIDTH;
			whereToDraw.bottom = y +CHARACTER_HEIGHT;


			//sprite.draw(g, xView, (int)y, xView + CHARACTER_WIDTH, (int)y + CHARACTER_HEIGHT, 400, 0, 0, 200);
		}else{
			whereToDraw.left = xView;
			whereToDraw.top = y;
			whereToDraw.right = xView+ CHARACTER_WIDTH;
			whereToDraw.bottom = y +CHARACTER_HEIGHT;

			
		}
		return whereToDraw;

	}
	public Rect getFrameToDraw(){


		frameToDraw.left = displayPosX[animFrameX];
		frameToDraw.top = displayPosY[animFrameY];
		frameToDraw.right = displayPosX[animFrameX]+ sampleFrameW;
		frameToDraw.bottom = displayPosY[animFrameY] + sampleFrameH;
		//reverseSprites.get(b).draw(g, (int)x, (int)y);
		return frameToDraw;
	}
	

	public void tryToShoot(){

		if((System.currentTimeMillis() - lastShot)> shootTime){
			
			canShoot = true;

           // shots.ShotFired(fireStats(), false);
			lastShot = System.currentTimeMillis();
			

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
							
	
							//y = beginningPos - heightDifference;
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
				if( xPosCounter >= 4 && yPosCounter >= 4){
					
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
	public int dyingSound(){
		return dSound;
	}
	public int[] fireStats(){
		
		if(facingForward){
			shotSpeed = 12;
		}else{
			shotSpeed = -12;
		}
		
		fireStats[0] = (int)(this.x+ ((CHARACTER_WIDTH / 2) + 10));
		fireStats[1] = (int)this.y + (CHARACTER_HEIGHT / 2);
		fireStats[2] = shotSpeed;
		fireStats[3] = shotPower;
        fireStats[4] = shotAngle;
		fireStats[5] = shotType;
		return fireStats;
	}
	
	public boolean willShoot(){

		
		return canShoot;

		
		
	}
	public void move(float heroX, float heroY){

	}
	public void updateView(float x){
		
		xView = this.x - x;


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
	public boolean isDead() {return this.dead;}
	public boolean isDying() {return this.dying;}
	
	public int getY(){
		return (int)y;
	}
	public int getWidth(){
		return CHARACTER_WIDTH;
	}
	public int getHeight(){
		return CHARACTER_HEIGHT;
	}
	public int getX(){
		return (int)x;
	}
	public int hit(int hitStrength){

		this.health -= hitStrength;

		if(health <= 0 ){
			dying = true;
		}
		//Log.d("enemy health", Integer.toString(health));

		return hitPoints;
	}
	public void dropImage(){
		enemyImage = null;
	}

}
