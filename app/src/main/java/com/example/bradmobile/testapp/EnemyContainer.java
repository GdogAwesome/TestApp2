package com.example.bradmobile.testapp;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;


/**
 * Created by BradMobile on 8/7/2016.
 */
public class EnemyContainer {
    private FlyingEnemy enemy;
    ShotEntity shots;
    Bitmap enemyImage;
    private Paint paint;
    private EnemyEntity[] enemyList = new EnemyEntity[30];
    private boolean[] enemyActive = new boolean[30];

    private int screenWidth = Constants.SCREEN_WIDTH;

    public EnemyContainer(Context context,ShotEntity shotEntity){
        this.shots = shotEntity;


        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inPreferredConfig = Bitmap.Config.RGB_565;

        enemyImage = BitmapFactory.decodeResource(context.getResources(), R.drawable.ship, options);
        //enemyImage = Bitmap.createScaledBitmap(enemyImage, 800, 800, false);
        for(int i = 1; i < 30; i++) {

            enemy = new FlyingEnemy(context, i * 3000+1, 250);
            enemy.InitEnemy(200, 100,200, 100);
            enemyList[i] = enemy;

        }
        shots.addEnemies(enemyList, enemyActive);

    }

    public void updateEnemies(float heroX, float heroY, float mapPosX){
                    /*
            Check for active enemies

             */
        for (int e = 0; e < 30; e++) {

            if (enemyList[e] != null) {
                //Log.d("Active Enemy ", Integer.toString(e));
                if((enemyList[e].x - mapPosX) + EnemyEntity.CHARACTER_WIDTH > 0 && (enemyList[e].x - mapPosX) < screenWidth && !enemyList[e].isDead()) {
                    enemyActive[e] = true;
                }else{
                    enemyActive[e] = false;
                }
            }
        }

        for(int i = 0; i< 30; i ++){
            if(enemyActive[i] ) {
                enemyList[i].move(heroX, heroY);
                enemyList[i].updateView(mapPosX);
                if (enemyList[i].willShoot() ) {

                    shots.ShotFired(enemyList[i].fireStats(), false);
                    enemyList[i].canShoot = false;

                }
            }

        }



    }
    public void draw(Canvas canvas){

        for( int i = 0; i <30; i++){
            if(enemyActive[i]){
                canvas.drawBitmap(enemyImage, enemyList[i].getFrameToDraw(),enemyList[i].getWhereToDraw(),paint);

            }
        }



    }
    public void nullImage(){
        if(enemyImage != null) {
            enemyImage.recycle();
            enemyImage = null;

        }
    }
}
