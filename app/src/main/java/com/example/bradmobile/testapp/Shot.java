package com.example.bradmobile.testapp;

public class Shot {
	
	private int xPos;
	private int yPos;
	private int angle;
	private int drawX = 0;
	private int drawY = 0;
	private int xAdv = 0;
	private int yAdv = 0;
	private int shotSpeed = 5;
	private int[] draw = new int[5];
	private int size;
	private boolean friendly = false;
	private boolean Dying = false;
	private boolean Impact = false;
	private int[] shotAnim = {0 ,50,100};
	private int timerCount = 0;
	private int shotCount = 0;
	private int shotStrength = 20;
	private boolean Dead = true;
	

	
	public Shot( ){
		

	}
	public void advanceShot(){
		
		if(!Impact && !Dying){
			xPos += xAdv;
			yPos += yAdv;

		
		}else if(Impact){
			Dying = true;
			Impact = false;
		}else if(Dying){
			
			timerCount += 2;

			switch(timerCount){
			case 12: shotCount = 1;
				break;
			case 24: shotCount = 2;
					
				break;
			case 32:
				Dying = false;
				Dead = true;
				timerCount = 0;
				break;
			
			}
			
		}
	}
	public void fireShot(int x, int y,int drawX, int drawY, int shotSize, int angle,int shotPower, int shotSpeed, boolean friendly){
		xPos = x;
		yPos = y;
		this.Dead = false;
		this.Impact = false;
		this.Dead = false;
		this.drawX = 0;
		this.drawY = 0;
		this.shotCount = 0;

		this.friendly = friendly;
		this.angle = angle;
		this.drawX = drawX;
		this.drawY = drawY;
		this.shotStrength = shotPower;
		this.shotSpeed = shotSpeed;
		size = shotSize;

		if(angle == 0){
			if(shotSpeed < 0 ) {
				xAdv = (int)(shotSpeed * .80);
				yAdv = -1 *(int)(shotSpeed * .20);
			}else{
				xAdv = (int)(shotSpeed * .80);
				yAdv = (int)(shotSpeed * .20);
			}


		}else if(angle == 1){
			xAdv= shotSpeed;
			yAdv = 0;
		}else if(angle == 4){
			if(shotSpeed < 0 ) {
				xAdv = (int)(shotSpeed * .5);
				yAdv = (int)(shotSpeed * .5);
			}else{
				xAdv = (int)(shotSpeed * .5);
				yAdv = -1 *(int)(shotSpeed * .5);
			}
		}else if(angle == 3){

			if(shotSpeed < 0 ) {
				xAdv = (int)(shotSpeed * .66);
				yAdv = (int)(shotSpeed * .34);
			}else{
				xAdv = (int)(shotSpeed * .66);
				yAdv = -1 *(int)(shotSpeed * .34);
			}

		}else if(angle == 2){

			if(shotSpeed < 0 ) {
				xAdv = (int)(shotSpeed * .80);
				yAdv = (int)(shotSpeed * .20);
			}else{
				xAdv = (int)(shotSpeed * .80);
				yAdv = -1 *(int)(shotSpeed * .20);
			}

		}else if(angle == 5){
			if(shotSpeed < 0 ) {
				xAdv = (int)(shotSpeed * .38);
				yAdv = (int)(shotSpeed * .62);
			}else{
				xAdv = (int)(shotSpeed * .38);
				yAdv = -1 *(int)(shotSpeed * .62);
			}

		}

	}
	public boolean dying()
	{
		return Dying;
	}
	public void impact(boolean i){
		
		Impact = i;
	}
	public boolean dead(){
		return Dead;
	}
	public int[] drawShot(){
		draw[0] = xPos;
		draw[1] = yPos;
		draw[2] = shotAnim[shotCount];
		draw[3] = drawY;
		draw[4] = size;
		
		return draw;
	}

	public int getShotStrength(){
		return shotStrength;
	}
	public boolean isFriendly(){
		return friendly;
	}

}
