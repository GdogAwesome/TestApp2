package com.example.bradmobile.testapp;



import java.util.ArrayList;


public class mapBlock {
	
	public float posX;
	public float posY;
	private int spritePosX;
	private int spritePosY;
	private int objects;
	public final static int BLOCK_WIDTH = Constants.BLOCK_WIDTH;
	public final static int BLOCK_HEIGHT = Constants.BLOCK_HEIGHT;
	public final static int SEGMENT_WIDTH = Constants.SEGMENT_WIDTH;
	public final static int SEGMENT_HEIGHT = Constants.SEGMENT_HEIGHT;
	public float[] drawInfo = new float[4];
	public boolean[] animList = new boolean[4];
	int segmentWidth = 200;
	int segmentHeight = 150;
	
	BlockSegment segment;
	public BlockSegment[] segments= new BlockSegment[4];
	public ArrayList<int[]> drawArrays = new ArrayList<int[]>();
	public int[][] obstacles = new int[4][4];
	public int[] tempObstacle = new int[4];
	public float[][] tempRender = new float[4][4];

	
	
	//obstacle parameters

	private int[] rectObject = new int[4];

	/**
	 * 
	 * @param x
	 * @param y
	 * @param obsticles
	 */

	
	public mapBlock(float x, float y, int obsticles ){
		
		posX = x;
		posY = y;
		
		this.spritePosX = spritePosX;
		this.spritePosY = spritePosY;
		this.objects = obsticles;
		

		
	}
	public void initSegments(int[] XPositions, int[] YPositions, int[] DX, int[] DY, float[][] obstacleList, boolean[] hasO, boolean[] hasAnim){


		for(int i = 0; i< 4; i++){
			
				segment = new BlockSegment(XPositions[i],YPositions[i], DX[i], DY[i]);
				segment.setObstacleRect(hasO[i],obstacleList[i][0], obstacleList[i][1], obstacleList[i][2], obstacleList[i][3]);
				segment.setAnim(hasAnim[i]);
				segments[i] = segment;
			
		}
		
	}
	public void initNewSegments(){
		for(int i = 0; i < 2; i++){
			for(int k = 0; k < 2; k++){
				
			segment = new BlockSegment((k * SEGMENT_WIDTH)+ posX,(i * SEGMENT_HEIGHT)+ posY,0,0);
			segments[i] =segment;
			}
		}
		
	}

	
	public float[][] renderMapBlock(){
		for(int i = 0; i < 4; i++){
			tempRender[i] = segments[i].renderSegment();
		}
		return tempRender;

		
		
	}
	public boolean[] getAnim(){
		for(int i  = 0; i<4; i++){
			animList[i] = segments[i].getAnim();
		}
		return animList;
	}
	
	public int[][] getObstacleRect(){
		int[] tempObstacle = new int[4];
		for (int i = 0; i < 4; i++){
			tempObstacle = segments[i].getObstacleRect();
			obstacles[i][0] = tempObstacle[0];
			obstacles[i][1] = tempObstacle[1];
			obstacles[i][2] = tempObstacle[2];
			obstacles[i][3] = tempObstacle[3];
		}
		return obstacles;
		
	}
	public void updateBlock(int x, int y){
		
		this.posX = x;
		this.posY = y;
		int counter = 0;
		int Xcounter = 0;
		int Ycounter =0;

		for(int i = 0; i< 2; i++){
			for(int k = 0; k < 2; k++){

				//segments[counter].updateSegment(this.posX + (k * SEGMENT_WIDTH), this.posY + (i * SEGMENT_HEIGHT));
				counter ++;
			}
		}

		
		
	}
	
	public void addSegment(String shape, boolean obstacle,boolean anim, int xPos, int yPos, float[] bounds, int relativeX, int relativeY){
		
		for(int i = 0; i < 4; i++){
			if(relativeX > segments[i].posX && 
					relativeX < (segments[i].posX + segmentWidth) &&
					relativeY > segments[i].posY &&
					relativeY < (segments[i].posY + segmentHeight)){
				
						segments[i].updateDrawInfo(xPos, yPos);	
						segments[i].setObstacleRect(obstacle, bounds[0], bounds[1], bounds[2], bounds[3] );
						segments[i].setAnim(anim);
						//System.out.println("Draw x  "+xPos+ " Draw y "+yPos + " Segment Pos X " +segments[i].posX +" Segment Pos Y "+segments[i].posY);
						
					
				}
		}
		
	}
	public BlockSegment[] getSegments(){
		return segments;
	}


}
