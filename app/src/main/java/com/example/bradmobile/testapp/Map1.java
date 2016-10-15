package com.example.bradmobile.testapp;







import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.Log;

import com.google.android.gms.ads.InterstitialAd;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;








public class Map1 {
	public float MapFinalPosX;
	public float MapFinalPosY;
	public float ViewX;
	public float prevX;
	public float viewX;
	public float prevY;
	public float deltaX;
	public float deltaY;
	public float ViewY;
	private movieScene currentScene = null;
	public boolean endMap = false;
	public boolean endLevel = true;
	private boolean displayFM = true;
	private boolean displaySM = true;
	private int startX = Constants.START_X;
	private boolean reverseAnim = false;
	private boolean[] bottomAnim  = new boolean[4];
	private boolean[] topAnim = new boolean[4];
	private boolean[] skyAnim = new boolean[4];
	private boolean hasScene = false;
	private int backgroundWidth = 2168;
	private int[] backgroundDraw = {0, backgroundWidth, backgroundWidth *2, backgroundWidth * 3};
	private int[] drawBackground = {0, 1 ,2  };
    private HeroEntity hero;
	Context context;


	
	private int timer = 0;

	private int framePos = 0;
	private int[] drawFrame = new int[4];

	public int backgroundPosX = 0;
	
	float dif =  (((float)Constants.SCREEN_WIDTH - 1366) / Constants.SCREEN_WIDTH) ;
	int backgroundW = (int)(3800 +(dif   * 3800));
	//public int[] topX = new int[]{ 0, 1, 2, 3, 4 };
	
	public int[] bottomX = new int[]{ 0, 1, 2, 3, 4 };
	
	int segmentWidth = 200;
	int segmentHeight = 150;
	
	
	public float currentData;
	public int[] currentBottom = new int[100];
	public int[] currentTop = new int[100];
	
	

	private Bitmap mapImage;
	private Bitmap mapBackground;
    private RectF whereToDraw = new RectF(0,0,0,0);
    private Rect frameToDraw = new Rect(0,0,0,0);
	public String ref;
	public int mapLength = 0;
	private Canvas canvas;
	public final static int BLOCK_WIDTH = Constants.BLOCK_WIDTH;
	public final static int BLOCK_HEIGHT = Constants.BLOCK_HEIGHT;
	public final static int SEGMENT_WIDTH = Constants.SEGMENT_WIDTH ;
	public final static int SEGMENT_HEIGHT = Constants.SEGMENT_HEIGHT ;
    public final static int drawWidth = Constants.SEGMENT_WIDTH+ 1;
    public final static int drawHeight= Constants.SEGMENT_HEIGHT + 1;
	public final static int SCREEN_WIDTH = Constants.SCREEN_WIDTH;
	public final static int SCREEN_HEIGHT = Constants.SCREEN_HEIGHT;
	public final static int RIGHT_BORDER = Constants.RIGHT_BORDER;
	public final static int LEFT_BORDER = Constants.LEFT_BORDER;
	public final static int RUN_SPEED = Constants.RUN_SPEED;
	public boolean cutScene = false;
	public mapBlock[] bottomBlocks ;
	public mapBlock[] topBlocks ;
	public mapBlock[] skyBlocks;
	public float[][] bottomSegmentList = new float[4][4];
	public float[][] topSegmentList = new float[4][4];
	public float[][] skySegmentList = new float[4][4];
	public boolean canMoveLeft = true;
	public boolean canMoveRight = true;
	public int[] readObstacle = new int[4];
	public float[][] topObstacleArray = new float[4][4];
	public float[][] bottomObstacleArray = new float[4][4];
	public float[][] skyObstacleArray = new float[4][4];
	BitmapFactory.Options options = new BitmapFactory.Options();
	public InputStream is;
    //private Context context;
    private Paint paint = new Paint();
	int totalPos = 0;
	int counter = 0;
	
	
	public float[] drawBottom = new float[100];
	public float[] drawTop = new float[100];
	public float[] drawSky = new float[100];
	
	
	private mapBlock bottomBlock;
	private mapBlock topBlock;
	private mapBlock skyBlock;
	
	/**
	 * 

	 * @param x
	 * @param y 
	 */
	
	public Map1(HeroEntity hero, float x,float y){


		//super(hero, x, y);

        this.hero = hero;



		options.inSampleSize = 2;



		/*
         this.mapImage = BitmapFactory.decodeResource(makeBitmap.context().getResources(), R.drawable.cyber_map, options);
		// mapImage = BitmapFactory.decodeResource(this.getResources(), R.drawable.test_map3);
		mapImage = Bitmap.createScaledBitmap(mapImage, 2400, 2400, false);


		options.inSampleSize = 2;
		mapBackground = BitmapFactory.decodeResource(makeBitmap.context().getResources(), R.drawable.futurecity, options);
		mapBackground = Bitmap.createScaledBitmap(mapBackground, 2168, 1080, false);
		*/

		
		this.MapFinalPosX = x;
		this.MapFinalPosY = y;

	}
	public void updateAnim(){
		timer += 1;
		
		if(timer >= 10){
			if(!reverseAnim){
				if(framePos < 3){
					framePos += 1;
				}else{
					reverseAnim = true;
				}
			}else if(reverseAnim){
				if(framePos > 0){
					framePos -=1;
				}else{
					reverseAnim = false;
				}
			}
			timer = 0;
			


		}
	}
	public void updateMap(int x, int y){
		this.ViewX = x;
		this.ViewY = y;

		
		bottomX[0] = 0;
		
		bottomX[1] = bottomX[0] + 1;
		bottomX[2] = bottomX[1] + 1;
		bottomX[3] = bottomX[2] + 1;
		bottomX[4] = bottomX[3] + 1;
		
		
		for(int i = 0; i < mapLength; i++){
			currentData = bottomBlocks[i].posX;
			
			if( ViewX >= currentData && ViewX <= (currentData + BLOCK_WIDTH)){
				

				
				bottomX[0] = i;
				
				bottomX[1] = bottomX[0] + 1;
				bottomX[2] = bottomX[1] + 1;
				bottomX[3] = bottomX[2] + 1;
				bottomX[4] = bottomX[3] + 1;
				

				i = 100;
			}
		}


        if(( drawBackground[0]- ViewX) <= 0 ){
            for(int i = 0; i < 3; i++) {


            }
        }


		
		
	}
	
	public void initMap(Context context,int mapNo){
		this.context = context;
		
		for(int i =0; i <4; i++){
			drawFrame[i] = segmentWidth * i;
		}
		/**
		 * 
		 * 
		 * Set up Map Image
		 * 
		 * 
		 */
	
		
		try {
			/**
			 * decide what map to load
			 */

           options.inPreferredConfig = Bitmap.Config.RGB_565;

			switch(mapNo){

                case 1:
					is = context.getResources().openRawResource(R.raw.test9);
                    options.inSampleSize = 1;
					this.mapImage = BitmapFactory.decodeResource(context.getResources(), R.drawable.cyber_map, options);
					// mapImage = BitmapFactory.decodeResource(this.getResources(), R.drawable.test_map3);
					//mapImage = Bitmap.createScaledBitmap(mapImage, 2400, 2400, false);
					hasScene = true;

					options.inSampleSize = 1;
					mapBackground = BitmapFactory.decodeResource(context.getResources(), R.drawable.futurecity, options);
					//mapBackground = Bitmap.createScaledBitmap(mapBackground, 2168, 1080, false);

					break;
				case 2:
                    options.inSampleSize = 1;
                    is = context.getResources().openRawResource(R.raw.test6);
					this.mapImage = BitmapFactory.decodeResource(context.getResources(), R.drawable.test_map3, options);
					// mapImage = BitmapFactory.decodeResource(this.getResources(), R.drawable.test_map3);
					//mapImage = Bitmap.createScaledBitmap(mapImage, 2400, 2400, false);

					hasScene = false;
					options.inSampleSize = 1;
					mapBackground = BitmapFactory.decodeResource(context.getResources(), R.drawable.map_background, options);
					//mapBackground = Bitmap.createScaledBitmap(mapBackground, 2168, 1080, false);

					break;



			}
			//File file = new File(mapFile);
			InputStreamReader ir = new InputStreamReader(is);
			BufferedReader reader = new BufferedReader(ir);
			this.ref =  reader.readLine();

			mapLength = Integer.parseInt(reader.readLine());

			
			bottomBlocks = new mapBlock[mapLength];
			topBlocks =  new mapBlock[mapLength];
			skyBlocks = new mapBlock[mapLength];
			

			//System.out.println(mapLength);
			int[] xPosSegments = new int[4];
			int[] yPosSegments = new int[4];
			int[] DrawSegmentsX = new int[4];
			int[] DrawSegmentsY = new int[4];
			boolean[] hasO = new boolean[4];
			boolean[] hasAnim = new boolean[4];
			
			
			for(int i = 0; i< mapLength; i++){
				reader.readLine();
				reader.readLine();
				
				skyBlock = new mapBlock(BLOCK_WIDTH * i, 0, 0 );
				
				
				for(int y = 0; y < 2 ; y++){
					for(int x = 0; x < 2; x++){		
						totalPos = x + y;
						reader.readLine();
						reader.readLine();
			
					/*
					xPosSegments[k] = Integer.parseInt(reader.readLine());
					yPosSegments[k] = Integer.parseInt(reader.readLine());
					*/
						xPosSegments[counter] = ((i * BLOCK_WIDTH) + (x * SEGMENT_WIDTH) );
						
						yPosSegments[counter] = ((y * SEGMENT_HEIGHT) );
					
						DrawSegmentsX[counter] = Integer.parseInt(reader.readLine());
						DrawSegmentsY[counter] = Integer.parseInt(reader.readLine());
						hasO[counter] = Boolean.parseBoolean(reader.readLine());
						hasAnim[counter] = Boolean.parseBoolean(reader.readLine());
						for(int j = 0; j < 4; j++){
						
							//System.out.println("Obstacle: "+ k + " Obstacle #"+ j+ " Obstacle: "+readObstacle[j]);
							topObstacleArray[counter][j]= Float.parseFloat(reader.readLine());
							
						}
						counter ++;
					}
		
					
					
				}
				counter = 0;

				skyBlock.initSegments(xPosSegments, yPosSegments, DrawSegmentsX, DrawSegmentsY, topObstacleArray,hasO, hasAnim);
				skyBlocks[i] = skyBlock;
			}

			for(int i = 0; i< mapLength; i++){
				reader.readLine();
				reader.readLine();
				
				topBlock = new mapBlock(BLOCK_WIDTH * i, BLOCK_HEIGHT, 0 );
				
				
				for(int y = 0; y < 2 ; y++){
					for(int x = 0; x < 2; x++){		
						totalPos = x + y;
						reader.readLine();
						reader.readLine();
			
					/*
					xPosSegments[k] = Integer.parseInt(reader.readLine());
					yPosSegments[k] = Integer.parseInt(reader.readLine());
					*/
						xPosSegments[counter] = ((i * BLOCK_WIDTH) + (x * SEGMENT_WIDTH) );
						
						yPosSegments[counter] = ((y * SEGMENT_HEIGHT)+ BLOCK_HEIGHT );
					
						DrawSegmentsX[counter] = Integer.parseInt(reader.readLine());
						DrawSegmentsY[counter] = Integer.parseInt(reader.readLine());
						hasO[counter] = Boolean.parseBoolean(reader.readLine());
						hasAnim[counter] = Boolean.parseBoolean(reader.readLine());
						for(int j = 0; j < 4; j++){
						
							//System.out.println("Obstacle: "+ k + " Obstacle #"+ j+ " Obstacle: "+readObstacle[j]);
							topObstacleArray[counter][j]= Float.parseFloat(reader.readLine());
							
						}
						counter ++;
					}
		
					
					
				}
				counter = 0;
				/*
				for(int j = 0; j < 4; j++){
					for(int q = 0; q < 4; q++){

					System.out.println("Obstacle: "+ j + " Obstacle #"+ q+ " Obstacle: "+ bottomObstacleArray[j][q] );
					}
				}*/
				topBlock.initSegments(xPosSegments, yPosSegments, DrawSegmentsX, DrawSegmentsY, topObstacleArray,hasO, hasAnim);
				topBlocks[i] = topBlock;
			}
			for(int i = 0; i< mapLength; i++){
				reader.readLine();
				reader.readLine();
				
				bottomBlock = new mapBlock(BLOCK_WIDTH * i,( 2 *BLOCK_HEIGHT), 0 );
				for(int y = 0; y < 2 ; y++){
					for(int x = 0; x < 2; x++){
			
						totalPos = x + y;
						reader.readLine();
						reader.readLine();
			
					/*
					xPosSegments[k] = Integer.parseInt(reader.readLine());
					yPosSegments[k] = Integer.parseInt(reader.readLine());
					*/
						xPosSegments[counter] = ((i * BLOCK_WIDTH) + (x * SEGMENT_WIDTH) );
						
						//System.out.println("xPos: "+ xPosSegments[counter]);
						yPosSegments[counter] = ((y * SEGMENT_HEIGHT)+ (BLOCK_HEIGHT * 2));
					
						DrawSegmentsX[counter] = Integer.parseInt(reader.readLine());
						DrawSegmentsY[counter] = Integer.parseInt(reader.readLine());
						hasO[counter] = Boolean.parseBoolean(reader.readLine());
						hasAnim[counter] = Boolean.parseBoolean(reader.readLine());
						for(int j = 0; j < 4; j++){
						
							//System.out.println("Obstacle: "+ k + " Obstacle #"+ j+ " Obstacle: "+readObstacle[j]);
							bottomObstacleArray[counter][j]= Float.parseFloat(reader.readLine());
							
						}
						counter ++;
					}
		
					
					
				}
				counter = 0;
				bottomBlock.initSegments(xPosSegments, yPosSegments, DrawSegmentsX, DrawSegmentsY, bottomObstacleArray, hasO, hasAnim);
				bottomBlocks[i] = bottomBlock;
			}
            ir.close();
			reader.close();


		} catch (IOException e1) {

			e1.printStackTrace();
		}

		/**
		 * 
		 * 
		 * Set up Map Blocks
		 * 
		 * 
		 */
		
		for(int i=0; i < mapLength; i ++){
			
			currentTop[i] = i * BLOCK_WIDTH;
	
			currentBottom[i] = i * BLOCK_WIDTH;
			
		}

		
		
	}


	
	/**
	 * 
	 * Draw from Map from image based on block position
	 * 
	 */
	public void draw(Canvas canvas){

		for(int i=0; i < 3; i ++) {

			whereToDraw.left = backgroundDraw[drawBackground[i]];
			whereToDraw.top = 0;
			whereToDraw.right = backgroundDraw[drawBackground[i]] + backgroundWidth;
			whereToDraw.bottom = SCREEN_HEIGHT;

			frameToDraw.left = 0;
			frameToDraw.top = 0;
			frameToDraw.right = 1920;
			frameToDraw.bottom = 1080;
			canvas.drawBitmap(mapBackground, frameToDraw, whereToDraw, paint);
		}

		for(int i = 0; i < 5; i++){
			bottomSegmentList = bottomBlocks[bottomX[i]].renderMapBlock();
			topSegmentList = topBlocks[bottomX[i]].renderMapBlock();
			skySegmentList = skyBlocks[bottomX[i]].renderMapBlock();
			
			bottomAnim = bottomBlocks[bottomX[i]].getAnim();
			topAnim = topBlocks[bottomX[i]].getAnim();
			skyAnim = skyBlocks[bottomX[i]].getAnim();
			
			
			for(int k = 0; k < 4;k++){
				drawBottom = bottomSegmentList[k];
				drawTop = topSegmentList[k];
				drawSky = skySegmentList[k];
				
				if(bottomAnim[k]){
                    whereToDraw.left = drawBottom[0] - ViewX;
                    whereToDraw.top = drawBottom[1];
					whereToDraw.right = (drawBottom[0] + drawWidth)- ViewX ;
                    whereToDraw.bottom = drawBottom[1] + drawHeight;

                    frameToDraw.left = (int)(drawBottom[2]+ drawFrame[framePos]);
                    frameToDraw.top = (int)drawBottom[3];
                    frameToDraw.right = (int)(drawBottom[2] + drawFrame[framePos])+ segmentWidth;
                    frameToDraw.bottom = (int)(drawBottom[3] + segmentHeight);
					canvas.drawBitmap(mapImage,frameToDraw, whereToDraw, paint);

					//g.drawImage(mapImage,  (drawBottom[0]- ViewX)  , drawBottom[1], ((drawBottom[0] + SEGMENT_WIDTH)- ViewX), (drawBottom[1] + SEGMENT_HEIGHT), (drawBottom[2]+ drawFrame[framePos]), drawBottom[3], (drawBottom[2] + segmentWidth)+ drawFrame[framePos], (drawBottom[3] + segmentHeight), null);
					
				}else{
                    whereToDraw.left = drawBottom[0] - ViewX;
                    whereToDraw.top = drawBottom[1];
                    whereToDraw.right = (drawBottom[0] + drawWidth)-ViewX ;
                    whereToDraw.bottom = drawBottom[1] + drawHeight;

                    frameToDraw.left = (int)drawBottom[2];
                    frameToDraw.top = (int)drawBottom[3];
                    frameToDraw.right = (int)drawBottom[2]+ segmentWidth;
                    frameToDraw.bottom = (int)drawBottom[3] +segmentHeight;
					canvas.drawBitmap(mapImage,frameToDraw, whereToDraw, paint);
					//g.drawImage(mapImage,  (drawBottom[0]- ViewX)  , drawBottom[1], ((drawBottom[0] + SEGMENT_WIDTH)- ViewX), (drawBottom[1] + SEGMENT_HEIGHT), drawBottom[2], drawBottom[3], (drawBottom[2] + segmentWidth), (drawBottom[3] + segmentHeight), null);
				}


				if(topAnim[k]){
                    whereToDraw.left = drawTop[0] - ViewX;
                    whereToDraw.top = drawTop[1];
					whereToDraw.right = (drawTop[0] + drawWidth)- ViewX ;
                    whereToDraw.bottom = drawTop[1] + drawHeight;

                    frameToDraw.left = (int)( drawTop[2]+ drawFrame[framePos]);
                    frameToDraw.top = (int)drawTop[3];
                    frameToDraw.right = (int)(drawTop[2] + drawFrame[framePos])+ segmentWidth;
                    frameToDraw.bottom =(int) (drawTop[3] + segmentHeight);
					canvas.drawBitmap(mapImage,frameToDraw, whereToDraw, paint);
					//g.drawImage(mapImage, drawTop[0] - ViewX , drawTop[1], (drawTop[0] + SEGMENT_WIDTH) - ViewX, (drawTop[1] + SEGMENT_HEIGHT), (drawTop[2]+ drawFrame[framePos]), drawTop[3], ((drawTop[2] + segmentWidth)+ drawFrame[framePos]), (drawTop[3] + segmentHeight), null);
				}else{
                    whereToDraw.left = drawTop[0] - ViewX;
                    whereToDraw.top = drawTop[1];
                    whereToDraw.right = (drawTop[0] + drawWidth)- ViewX ;
                    whereToDraw.bottom = drawTop[1] + drawHeight;

                    frameToDraw.left = (int)drawTop[2];
                    frameToDraw.top = (int)drawTop[3];
                    frameToDraw.right = (int)drawTop[2]+ segmentWidth;
                    frameToDraw.bottom = (int)(drawTop[3] +segmentHeight);
					canvas.drawBitmap(mapImage,frameToDraw, whereToDraw, paint);
					//g.drawImage(mapImage, drawTop[0] - ViewX , drawTop[1], (drawTop[0] + SEGMENT_WIDTH) - ViewX, (drawTop[1] + SEGMENT_HEIGHT), drawTop[2], drawTop[3], (drawTop[2] + segmentWidth), (drawTop[3] + segmentHeight), null);
				}

				
				if(skyAnim[k]){
                    whereToDraw.left = drawSky[0] - ViewX;
                    whereToDraw.top = drawSky[1];
                    whereToDraw.right = (drawSky[0] + drawWidth)- ViewX ;
                    whereToDraw.bottom = drawSky[1] + drawHeight;

                    frameToDraw.left = (int)(drawSky[2]+ drawFrame[framePos]);
                    frameToDraw.top = (int)drawSky[3];
                    frameToDraw.right = (int)(drawSky[2] + drawFrame[framePos])+ segmentWidth;
                    frameToDraw.bottom =(int) (drawSky[3] + segmentHeight);
					canvas.drawBitmap(mapImage,frameToDraw, whereToDraw, paint);
					//g.drawImage(mapImage, drawSky[0] - ViewX , drawSky[1], (drawSky[0] + SEGMENT_WIDTH) - ViewX, (drawSky[1] + SEGMENT_HEIGHT), (drawSky[2]+ drawFrame[framePos]), drawSky[3], ((drawSky[2] + segmentWidth)+ drawFrame[framePos]), (drawSky[3] + segmentHeight), null);
				}else{
                    whereToDraw.left = drawSky[0] - ViewX;
                    whereToDraw.top = drawSky[1];
                    whereToDraw.right = (drawSky[0] + drawWidth)-ViewX;
                    whereToDraw.bottom = drawSky[1] + drawHeight;

                    frameToDraw.left = (int)drawSky[2];
                    frameToDraw.top = (int)drawSky[3];
                    frameToDraw.right = (int)drawSky[2]+ segmentWidth;
                    frameToDraw.bottom = (int)(drawSky[3] +segmentHeight);
					canvas.drawBitmap(mapImage,frameToDraw, whereToDraw, paint);
					
					//g.drawImage(mapImage, drawSky[0] - ViewX , drawSky[1], (drawSky[0] + SEGMENT_WIDTH) - ViewX, (drawSky[1] + SEGMENT_HEIGHT), drawSky[2], drawSky[3], (drawSky[2] + segmentWidth), (drawSky[3] + segmentHeight), null);
				}
			}
		}
	}
	public float moveMap(float heroX, float heroY,boolean canMoveLeft, boolean canMoveRight, int playerCommand){//}, float viewX){


			if (heroX >= 1000 && displayFM){
                hero.displayMessage(1);
				displayFM = false;
			}else if(heroX >= 5000 && displaySM){

				hero.displayMessage(2);
				displaySM = false;
			}
            if (canMoveRight && heroX >= viewX + RIGHT_BORDER && playerCommand == 1) {
                if ((backgroundDraw[0] + backgroundWidth) > 0) {
                    for (int i = 0; i < 3; i++) {
                        backgroundDraw[i] -= 2;

                    }
                } else if ((backgroundDraw[0] + backgroundWidth) <= 0) {
                    for (int i = 0; i < 3; i++) {
                        backgroundDraw[i] += backgroundWidth;
                    }
				}
				prevX = viewX;
                viewX = (heroX - RIGHT_BORDER);
				deltaX = viewX - prevX;


                if ((currentBottom[bottomX[0]] + (BLOCK_WIDTH)) >= 0) {
                    for (int i = 0; i < 5; i++) {
                        currentBottom[bottomX[i]] -= deltaX;

                        //bottomBlocks.get(bottomX[i]).updateBlock(currentBottom[bottomX[i]], (2* BLOCK_HEIGHT));
                        //bottomBlocks.get(bottomX[i]).updateObstacleHorizontal();

                        currentTop[bottomX[i]] -= deltaX;
                        //topBlocks.get(bottomX[i]).updateBlock(currentTop[bottomX[i]], BLOCK_HEIGHT);
                        //topBlocks.get(bottomX[i]).updateObstacleHorizontal();

                    }

                } else {


                    bottomX[0] += 1;
                    bottomX[1] += 1;
                    bottomX[2] += 1;
                    bottomX[3] += 1;
                    bottomX[4] += 1;


                    currentBottom[bottomX[4]] = currentBottom[bottomX[3]] + BLOCK_WIDTH;
                    //bottomBlocks.get(bottomX[2]).updateBlock((currentBottom[bottomX[2]] - BLOCK_WIDTH) , (2* BLOCK_HEIGHT));

                    currentTop[bottomX[4]] = currentTop[bottomX[3]] + BLOCK_WIDTH;
                    //topBlocks.get(bottomX[2]).updateBlock((currentTop[bottomX[2]] - BLOCK_WIDTH) , BLOCK_HEIGHT);


                    for (int i = 0; i < 5; i++) {
                        currentBottom[bottomX[i]] -= deltaX;

                        //bottomBlocks.get(bottomX[i]).updateBlock(currentBottom[bottomX[i]],(2* BLOCK_HEIGHT));

                        currentTop[bottomX[i]] -= deltaX;
                        //topBlocks.get(bottomX[i]).updateBlock(currentTop[bottomX[i]], BLOCK_HEIGHT);

                    }

                }
            } else if (canMoveLeft && heroX <= viewX + LEFT_BORDER && playerCommand == 2 ) {
                if ((backgroundDraw[1] + backgroundWidth) < SCREEN_WIDTH) {
                    for (int i = 0; i < 3; i++) {
                        backgroundDraw[i] += 2;

                    }
                } else if ((backgroundDraw[1] + backgroundWidth) >= SCREEN_WIDTH) {
                    for (int i = 0; i < 3; i++) {
                        backgroundDraw[i] -= backgroundWidth;
                    }
                }
				prevX = viewX;
                viewX = (heroX - LEFT_BORDER);

				deltaX = prevX - viewX;

                if (currentBottom[bottomX[4]] <= (SCREEN_WIDTH)) {
                    for (int i = 0; i < 5; i++) {
                        currentBottom[bottomX[i]] += deltaX;

                        //bottomBlocks.get(bottomX[i]).updateBlock(currentBottom[bottomX[i]], (2* BLOCK_HEIGHT));
                        //bottomBlocks.get(bottomX[i]).updateObstacleHorizontal();

                        currentTop[bottomX[i]] += deltaX;
                        //topBlocks.get(bottomX[i]).updateBlock(currentTop[bottomX[i]], BLOCK_HEIGHT);
                        //topBlocks.get(bottomX[i]).updateObstacleHorizontal();

                    }
                } else {

                    bottomX[0] -= 1;
                    bottomX[1] -= 1;
                    bottomX[2] -= 1;
                    bottomX[3] -= 1;
                    bottomX[4] -= 1;

                    currentBottom[bottomX[0]] = (currentBottom[bottomX[1]] - BLOCK_WIDTH);
                    //bottomBlocks.get(bottomX[0]).updateBlock((currentBottom[bottomX[0]] + BLOCK_WIDTH) ,(2* BLOCK_HEIGHT));

                    currentTop[bottomX[0]] = (currentTop[bottomX[1]] - BLOCK_WIDTH);
                    //topBlocks.get(bottomX[0]).updateBlock((currentTop[bottomX[0]] + BLOCK_WIDTH) , BLOCK_HEIGHT);

                    for (int i = 0; i < 5; i++) {
                        currentBottom[bottomX[i]] += deltaX;

                        //bottomBlocks.get(bottomX[i]).updateBlock(currentBottom[bottomX[i]], (2* BLOCK_HEIGHT));

                        currentTop[bottomX[i]] += deltaX;
                        //topBlocks.get(bottomX[i]).updateBlock(currentTop[bottomX[i]], BLOCK_HEIGHT);

                    }

                }
            }


			ViewX = viewX;
			return viewX;
		
		
	}
	public void addSegment(String shape, boolean obstacle, boolean anim, int xPos, int yPos, float[] bounds, int mouseX, int mouseY){
		int relativeX = 0;
		int relativeY = 0;
		//float relativeX = mouseX + ViewX;
		//float relativeY = mouseY + ViewY;
		//sdf

		for(int i = 0; i < 5 ; i++){

			if((mouseX + ViewX) > bottomBlocks[bottomX[i]].posX && 
				(mouseX + ViewX)< (bottomBlocks[bottomX[i]].posX + BLOCK_WIDTH) &&
				(mouseY + ViewY) > bottomBlocks[bottomX[i]].posY &&
				(mouseY + ViewY) < (bottomBlocks[bottomX[i]].posY + BLOCK_HEIGHT)){
				

				 
				 bottomBlocks[bottomX[i]].addSegment(shape, obstacle, anim,  xPos, yPos, bounds, relativeX, relativeY);

				
				
			}else if((mouseX + ViewX) > topBlocks[bottomX[i]].posX && 
					(mouseX + ViewX)< (topBlocks[bottomX[i]].posX + BLOCK_WIDTH) &&
					(mouseY + ViewY) > topBlocks[bottomX[i]].posY &&
					(mouseY + ViewY) < (topBlocks[bottomX[i]].posY + BLOCK_HEIGHT)){
					
				System.out.println("top");
					topBlocks[bottomX[i]].addSegment(shape, obstacle, anim,  xPos, yPos, bounds, relativeX, relativeY);
					
				}else if((mouseX + ViewX) > skyBlocks[bottomX[i]].posX && 
					(mouseX + ViewX)< (skyBlocks[bottomX[i]].posX + BLOCK_WIDTH) &&
					(mouseY + ViewY) > skyBlocks[bottomX[i]].posY &&
					(mouseY + ViewY) < (skyBlocks[bottomX[i]].posY + BLOCK_HEIGHT)){

					 skyBlocks[bottomX[i]].addSegment(shape, obstacle, anim,  xPos, yPos, bounds, relativeX, relativeY);

					
					
				}
			
		}
	}
	
	/*
	 * 
	 * get Various obstacles from segments
	 */
	public int[][] getObstacleRect(){
	
			return bottomBlocks[bottomX[0]].getObstacleRect();

	}
	public boolean CutScene(){
		return cutScene;
	}
	public void setEndMap(boolean end){
		this.endMap = end;

		if(hasScene){

			initScene(1);
			cutScene =true;


		}else{
			endLevel = true;
		}
	}
	/**
	 *
	 *
	 * Draw scene playing
	 */
	public void drawScene(Canvas canvas){

		if(currentScene.playing) {
			currentScene.draw(canvas);
		}else{
			currentScene.nullImage();
			endLevel = true;
		}
	}
	public void initScene(int sceneNumber){

		currentScene = new movieScene( context, sceneNumber);
		Log.d("Scene Number", Integer.toString(sceneNumber));
	}
	public void dropImage(){
		mapImage = null;
		mapBackground = null;
	}
	public void nullImage(){
		if(mapImage != null) {
			mapImage.recycle();
			mapImage = null;

			mapBackground.recycle();
			mapBackground = null;

		}
		if(cutScene){
			currentScene.nullImage();
		}

	}
	public boolean end(){
		return endMap;
	}
	public int[][] getCenterBottomObstacle(){
		return bottomBlocks[bottomX[2]].getObstacleRect();
	}
	public int[][] getCenterTopObstacle(){
		return topBlocks[bottomX[2]].getObstacleRect();
	}
	public int[][] getRightBottomObstacle(){
		return bottomBlocks[bottomX[3]].getObstacleRect();
	}
	public int[][] getRightTopObstacle(){
		return topBlocks[bottomX[3]].getObstacleRect();
	}
	public int[][] getLeftBottomObstacle(){
		return bottomBlocks[bottomX[1]].getObstacleRect();
	}
	public int[][] getLeftTopObstacle(){
		return topBlocks[bottomX[1]].getObstacleRect();
	}
	public int[][] getCenterSkyObstacle(){
		return skyBlocks[bottomX[1]].getObstacleRect();
	}
	public int[][] getRightSkyObstacle(){
		return skyBlocks[bottomX[2]].getObstacleRect();
	}

	

}
