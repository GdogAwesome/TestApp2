package com.example.bradmobile.testapp;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;


public class BlockSegment {
	
	public float posX;
	public float posY;
	private int spritePosX;
	private int spritePosY;
	private int objects;
	public boolean hasObstacle = false;
	int segmentWidth = 200;
	int segmentHeight = 150;
	public final static int BLOCK_WIDTH = Constants.BLOCK_WIDTH;
	public final static int BLOCK_HEIGHT = Constants.BLOCK_HEIGHT;
	public final static int SEGMENT_WIDTH = Constants.SEGMENT_WIDTH;
	public final static int SEGMENT_HEIGHT = Constants.SEGMENT_HEIGHT;
	public float[] drawInfo = new float[4];
	private boolean Anim = false;
	int ViewX;
	int ViewY;

	
	
	//obstacle parameters

	private float[] rectObject = new float[4];
	private int[] finalRectObject = new int[4];


	/**
	 * 
	 * @param x
	 * @param y
	 * @param spritePosX
	 * @param spritePosY

	 */

	
	public BlockSegment(float x, float y, int spritePosX, int spritePosY){
		
		posX = x;
		posY = y;
		
		this.spritePosX = spritePosX;
		this.spritePosY = spritePosY;
		/*
		this.rectObject[0] = x1;
		this.rectObject[1] = y1;
		this.rectObject[2] = x2;
		this.rectObject[3] = y2;
		*/
		/*
		for(int i = 0; i <4; i++){
			System.out.println(rectObject[i]);
		}
		*/
	
		
	}
	
	public int getSpritePosX(){
		
		return this.spritePosX;
	}
	public int getSpritePosY(){
		
		return this.spritePosY;
	}
	

	
	public float[] renderSegment(){
		//sprite.draw(g,  posX, posY, posX + BLOCK_WIDTH ,posY + BLOCK_HEIGHT, 0, 0, BLOCK_WIDTH, BLOCK_HEIGHT);
		drawInfo[0] = posX ;
		drawInfo[1] = posY ;
		drawInfo[2] = spritePosX ;
		drawInfo[3] = spritePosY ;
		return drawInfo;
		
		
	}
	
	
	/**
	 * 
	 * @param hasO 
	 * @param x1
	 * @param y1
	 * @param x2
	 * @param y2
	 */
	public void setObstacleRect(boolean hasO, float x1, float y1, float x2, float y2){
		
		hasObstacle = hasO;

	
		
		
		if(hasO == true){
			//System.out.println("hosO is true");
			rectObject[0] = x1;
			rectObject[1] = y1;
			rectObject[2] = x2;
			rectObject[3] = y2;
			
		}else if(hasO == false){
			rectObject[0] = 1000;
			rectObject[1] = 1000;
			rectObject[2] = 1000;
			rectObject[3] = 1000;
		}
		finalRectObject[0] = (int)(this.posX + (rectObject[0] * SEGMENT_WIDTH));
		finalRectObject[1] = (int)(this.posY + (int)(rectObject[1] * SEGMENT_HEIGHT));
		finalRectObject[2] = (int)(this.posX + (int)( rectObject[2] * SEGMENT_WIDTH));
		finalRectObject[3] = (int)(this.posY + (int)(rectObject[3] * SEGMENT_WIDTH));
	}
	public void updateDrawInfo(int DrawX, int DrawY ){
		
		spritePosX = DrawX;
		spritePosY = DrawY;
		

		//System.out.println("Segment X pos "+this.posX+ " Segment Y pos "+this.posY);
		//System.out.println("Draw x  "+DrawX+ " Draw y "+DrawY);
	}
	public void setAnim(boolean a){
		this.Anim = a;
	}
	public boolean getAnim(){
		return Anim;
	}
	public void updateObstacleHorizontal(){
	
		finalRectObject[0] =(int)( this.posX + (rectObject[0] * SEGMENT_WIDTH));
		finalRectObject[1] = (int)(this.posY + (rectObject[1] * SEGMENT_HEIGHT));
		finalRectObject[2] = (int)(this.posX + ( rectObject[2] * SEGMENT_WIDTH));
		finalRectObject[3] = (int)(this.posY + (rectObject[3] * SEGMENT_WIDTH));
		
		
		
	}
	public int[] getObstacleRect(){
		//System.out.println("this segment PosY: " + posY);

		return finalRectObject;
		
	}
	/*public void updateSegment(int x, int y){
		
		this.posX = x;
		this.posY = y;
		
		updateObstacleHorizontal();

		
		
	}*/


}