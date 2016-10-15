package com.example.bradmobile.testapp;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import java.lang.Math;

import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Debug;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.view.MotionEventCompat;
import android.util.AttributeSet;
import android.util.Base64;
import android.util.Log;
import android.view.Display;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.WindowManager;
import android.widget.AbsoluteLayout;
import android.widget.LinearLayout;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import java.io.InputStream;
import java.text.DecimalFormat;

class ImageRenderer extends SurfaceView implements Runnable, SurfaceHolder.Callback {
   // Bitmap mapImage = null;
    //Bitmap enemyImage = null;
   // Bitmap mapBackground = null;
    MediaPlayer mp;
    boolean playAd = true;
    //Bitmap heroImage = null;

    Bitmap reverseHero = null;
    public Bitmap shotImage;
    Bitmap overlay;
    BitmapDrawable temp = null;
    Thread gameThread;


    Paint paint;
    Canvas canvas = new Canvas();
    private long timeThisFrame;
    SurfaceHolder ourHolder;


    private boolean playing = false
            ;
  //  = new Thread(this);
    /** The stragey that allows us to use accelerate page flipping */
    /**
     * True if the game is currently "running", i.e. the game loop is looping
     */
    private boolean gameRunning = true;

    private boolean waitingForKeyPress = false;
    /**
     * True if the left cursor key is currently pressed
     */
    private boolean leftPressed = false;
    /**
     * True if the right cursor key is currently pressed
     */
    private boolean rightPressed = false;
    /**
     * True if we are firing
     */
    private boolean firePressed = false;
    private int updateCount = 0;


    private boolean shoot = false;
    /**
     * True if game logic needs to be applied this loop, normally as a result of a game event
     */
    private boolean logicRequiredThisLoop = false;
    private int[] obstacleRect = new int[4];
    private int[] obstacleRect1 = new int[4];
    private int heroShotDegree = 90;
    private HeroEntity hero;
    private EnemyContainer enemies;


    public Map1 Map;
    private ShotEntity shots;
    private double nsTime = 1000000000D / 120D;//120
    private double tickTime = 1000000000D / 120D;
    private double sin = 0;
    private int ticks = 0;
    private int frames = 0;
    private int arrowWidth = Constants.ARROW_WIDTH;
    private int arrowheight = Constants.ARROW_HEIGHT;
    private int circleDiameter = Constants.CIRCLE_DIAMETER;
    private int controlHeight = Constants.CONTROL_HEIGHT;
    private int middleSpace = Constants.MIDDLE_SPACE;
    private boolean canMoveRight = true;
    private boolean canMoveLeft = true;
    private boolean notMoving = true;
    private int playerCommand = 0;
    private int groundLevel = Constants.GROUND_LEVEL;
    private boolean startJumping = false;
    private boolean jumping = false;
    private int lastMove = 0;
    private int shotHeight = 0;
    private Bitmap scoreBMP;

    private double lastShot = 0d;
    private boolean canShoot = false;
    private int RUN_SPEED = Constants.RUN_SPEED;
    private boolean falling = false;

    private int screenWidth = Constants.SCREEN_WIDTH;
    private int screenHeight = Constants.SCREEN_HEIGHT;
    private int CHARACTER_WIDTH = Constants.CHARACTER_WIDTH;
    private int CHARACTER_HEIGHT = Constants.CHARACTER_HEIGHT;
    public int BLOCK_WIDTH = Constants.BLOCK_WIDTH;
    public int BLOCK_HEIGHT = Constants.BLOCK_HEIGHT;
    private int halfScreen = screenWidth / 2;
    public String map = null;

    private int heroCenter = 0;
    private int triangleA = 0;
    private int triangleB = 0;
    private int triangleC = 0;
    private int hRight = 0;
    private int hLeft = 0;
    private int hFooting = 0;
    Handler mainHandler;
    private int touchId1;
    private int touchId2;
    private int startX = Constants.START_X;
    public float mapPosX = 0f;
    private boolean interact = false;
    private Rect frameToDraw = new Rect(0, 0, 100, 60);
    private Rect whereToDraw = new Rect(200, 500, 300, 560);

    InterstitialAd mInterstitialAd;

    private int previousPos = 0;
    private int currentPos = 0;
    private int blockCount = 0;
    public ImageRenderer ir = this;
    public Context context;

    private final static int MAX_FPS = 90;
    private final static int MAX_FRAME_SKIPS = 5;
    private final static int FRAME_PERIOD =  1000/MAX_FPS;



    public int[][][] obstacleList = new int[8][4][4];


    private int[] topObstacleRect = new int[4];

    private int[] bottomObstacleRect = new int[4];


    public ImageRenderer(Context context,AttributeSet attrs, int args) {


        super(context, attrs, args);
        mainHandler = new Handler(context.getMainLooper());
        ourHolder = getHolder();
        getHolder().addCallback(this);
        ourHolder.setKeepScreenOn(true);
        this.getHolder().setFormat(PixelFormat.RGBA_8888);//RGBA_8888
        setFocusable(true);
        setVisibility(View.VISIBLE);
        setZOrderOnTop(true);

        //this.setLayerType(View.LAYER_TYPE_HARDWARE, null);
        requestFocus();
        setConstants(context);


        //newMap();


    }

    public ImageRenderer(Context context,AttributeSet attrs) {


        super(context, attrs);
        mainHandler = new Handler(context.getMainLooper());
        ourHolder = getHolder();
        ourHolder.setKeepScreenOn(true);
        getHolder().addCallback(this);
        this.getHolder().setFormat(PixelFormat.RGBA_8888);//RGBA_8888
       // this.setLayerType(View.LAYER_TYPE_HARDWARE, null);
        setZOrderOnTop(true);
        setFocusable(true);
        setVisibility(View.VISIBLE);




      // newMap();



    }


    public ImageRenderer(Context context) {


        super(context);
        mainHandler = new Handler(context.getMainLooper());
        ourHolder = getHolder();
        getHolder().addCallback(this);
        ourHolder.setKeepScreenOn(true);
        this.getHolder().setFormat(PixelFormat.RGBA_8888);//RGBA_8888
        int v = Build.VERSION.SDK_INT;
        if(v >= 11) {
            this.setLayerType(View.LAYER_TYPE_HARDWARE, null);
        }
        setZOrderOnTop(true);
        requestFocus();
        this.context = context;
        setConstants(context);

        //setDrawingCacheEnabled(true);
        setFocusable(true);

        setVisibility(View.VISIBLE);


        //newMap();


    }




    public void setConstants(Context context) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        Point size = new Point();
        int v = Build.VERSION.SDK_INT;
        if(v >= 13) {
            display.getSize(size);
            Constants.setScreen(size.x, size.y);
        }else {
            Constants.setScreen(480, 320);
        }

        arrowWidth = Constants.ARROW_WIDTH;
        arrowheight = Constants.ARROW_HEIGHT;
        circleDiameter = Constants.CIRCLE_DIAMETER;
        controlHeight = Constants.CONTROL_HEIGHT;
        middleSpace = Constants.MIDDLE_SPACE;
        RUN_SPEED = Constants.RUN_SPEED;
        screenWidth = Constants.SCREEN_WIDTH;
        screenHeight = Constants.SCREEN_HEIGHT;
        CHARACTER_WIDTH = Constants.CHARACTER_WIDTH;
        CHARACTER_HEIGHT = Constants.CHARACTER_HEIGHT;
        BLOCK_WIDTH = Constants.BLOCK_WIDTH;
        BLOCK_HEIGHT = Constants.BLOCK_HEIGHT;
        halfScreen = screenWidth / 2;
    }

    @Override
    public void run() {


        long beginTime;
        long timeDiff;
        int sleepTime = 0;
        int framesSkipped;
        int frames = 0;
/**
 *
 * new render
 */
        /*

        while(playing){
            beginTime = System.currentTimeMillis();
            framesSkipped = 0;


            tick();
            if (shoot) {
                tryToShoot(hero.shootTime);
            }

            render();
            //draw(canvas);
            frames ++;

            timeDiff = System.currentTimeMillis() - beginTime;
            sleepTime = (int)(FRAME_PERIOD - timeDiff);
            //Log.d("time diff","" +timeDiff+"");

            if(sleepTime > 0){

                try{
                    Thread.sleep(sleepTime);
                }catch(InterruptedException e){
                    Thread.currentThread().interrupt();
                }
            }



            while(sleepTime < 0 && framesSkipped < MAX_FRAME_SKIPS){
                tick();
                sleepTime += FRAME_PERIOD;
                //Log.d("sleep time","" +sleepTime+"");
                framesSkipped++;
                //Log.d("frames skipped", ""+framesSkipped +"");
            }



        }
        */



        /**
         *
         * old frame rendering
         */

        long now;
        double deltaR = 0;
        long lastTime = System.nanoTime();
        boolean shouldRender = true;
        long lastTimer = System.currentTimeMillis();


        while (playing) {

            now = System.nanoTime();
            deltaR += (now - lastTime) / nsTime;

            lastTime = now;


            while (deltaR >= 1) {
                ticks++;
                tick();

                deltaR -= 1;
                shouldRender = true;
            }

            if (shouldRender) {
                frames++;
                render();
                shouldRender = false;
            }
            if (System.currentTimeMillis() - lastTimer >= 1000) {

                lastTimer += 1000;// 1000
                frames = 0;
                ticks = 0;


            }
            if (shoot) {
                hero.tryToShoot();
            }
            //System.out.println("active threads "+Thread.currentThread());

            try {
                Thread.sleep(0);
            } catch (Exception e) {
            }

        }


        /**
         * hybrid rendering
         *
         */
        /*
        long now;
        double deltaR = 0;
        long lastTime = System.nanoTime();
        boolean shouldRender = true;
        long lastTimer = System.currentTimeMillis();
        long beginTime;
        long timeDiff;
        int sleepTime;
        int framesSkipped;
        sleepTime = 0;

        while(playing){
            beginTime = System.currentTimeMillis();
            framesSkipped = 0;

            tick();
            render();

            timeDiff = System.currentTimeMillis() - beginTime;
            sleepTime = (int)(FRAME_PERIOD - timeDiff);

            now = System.nanoTime();
            deltaR += (now - lastTime) / nsTime;

            lastTime = now;


            while (deltaR >= 1) {
                ticks++;
                tick();

                deltaR -= 1;
                shouldRender = true;
            }

            if (shouldRender) {
                frames++;
                render();
                shouldRender = false;
            }
            if (System.currentTimeMillis() - lastTimer >= 1000) {
                Log.d("frames ", ""+frames+"");

                lastTimer += 1000;// 1000
                frames = 0;
                ticks = 0;


            }
            if (shoot) {
                tryToShoot(hero.shootTime);
            }
            //System.out.println("active threads "+Thread.currentThread());

            try {
                Thread.sleep(0);
            } catch (Exception e) {
            }

        }
        */

    }

   private void initEntities(Context context, int mapNo) {



        BitmapFactory.Options options = new BitmapFactory.Options();

        //options.inJustDecodeBounds = true;
        options.inSampleSize = 1;
        //options.inDither = true;
        //options.inScaled = true;
        options.inPreferredConfig = Bitmap.Config.RGB_565;
        //options.inPreferQualityOverSpeed = true;

        overlay = BitmapFactory.decodeResource(context.getResources(), R.drawable.button_layout_test, options);
       // overlay = Bitmap.createScaledBitmap(overlay, 400, 400, false);



        //ourHolder = getHolder();
        paint = new Paint();


        hero = new Hero(context, canvas, (halfScreen - (CHARACTER_WIDTH / 2)), 250);// y 225
        hero.InitHero("source/pos1.png", CHARACTER_WIDTH, CHARACTER_HEIGHT);
        heroCenter = (CHARACTER_WIDTH / 2) + (int) hero.x;
        hero.setGround(groundLevel);



        Map = new Map1(hero, mapPosX, 0);
        Map.initMap(context, mapNo);

        shots = new ShotEntity(context);
       // shots.initShots( 800, 600);
        //enemy = new enemy(this,)
        enemies = new EnemyContainer(context, shots);


        shots.addEntities(hero);

       mp.start();



   }

    public void render() {
        canvas = null;
        try{
            canvas = ourHolder.lockCanvas();
            synchronized (ourHolder){
                if(!Map.end()) {
                    Map.draw(canvas);


                    enemies.draw(canvas);
                    hero.draw(canvas);

                    shots.drawShots(canvas);
                    DrawOverlay(canvas);


                }else if(Map.CutScene()){

                    Map.drawScene(canvas);

                }


            }

        }catch(Exception e){
            Log.e("errors","error", e);

        }finally{
            if(canvas != null){
                ourHolder.unlockCanvasAndPost(canvas);
            }
        }

        /*
        if (ourHolder.getSurface().isValid()) {
            // Lock the canvas ready to draw

            canvas = ourHolder.lockCanvas();

            // Draw the background color
            canvas.drawColor(Color.argb(255, 26, 128, 132));

            if(!Map.end()) {
                Map.draw(canvas);

                hero.draw(canvas);
                enemies.draw(canvas);


                shots.drawShots(canvas);
                DrawOverlay(canvas);


            }else if(Map.CutScene()){
                
                Map.drawScene(canvas);

            }


            //canvas.drawBitmap(map, frameToDraw, whereToDraw,paint);
            //canvas.drawBitmap(map,200, 200, paint);


            ourHolder.unlockCanvasAndPost(canvas);
        }
        */

    }



    public void tick() {

        if(!Map.end()) {

            shots.updateShots(mapPosX);
            calcLogic();
            Map.updateAnim();
            hero.move(playerCommand, Map.canMoveLeft, Map.canMoveRight);
            enemies.updateEnemies(hero.x, hero.y, mapPosX);

        }else if(Map.endLevel){
            nextLevel();
        }


    }



    public void calcLogic() {

        heroCenter = (CHARACTER_WIDTH / 2) + (int) hero.x;
        hLeft = heroCenter - 5;//8
        hRight = heroCenter + 5;//10
        hFooting = ((int) hero.y + CHARACTER_HEIGHT) - 12;
        checkHState();

        if (hero.canShoot) {

            hero.setShotHeight(shotHeight);
            shots.ShotFired(hero.fireStats(), true);
            hero.canShoot = false;
        }
            /*
            Get map Obstacles

             */
        obstacleList[0] = Map.getLeftTopObstacle();
        obstacleList[3] = Map.getLeftBottomObstacle();
        //obstacleList[2] = Map.getCenterSkyObstacle();
        obstacleList[1] = Map.getCenterTopObstacle();
        obstacleList[4] = Map.getCenterBottomObstacle();
        //obstacleList[5] = Map.getRightSkyObstacle();
        obstacleList[2] = Map.getRightTopObstacle();
        obstacleList[5] = Map.getRightBottomObstacle();
        shots.updateObstacles(obstacleList);
        //System.out.println("O List " +obstacleList[0][0][0]);
        //System.out.println("original " + bottomCenterObstacle[0][0]);




		/*
		 *
		 * scan through obstacles
		 *
		 *
		 */


        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 4; j++) {


                if (hRight >= (obstacleList[i][j][0]  ) && hLeft <= (obstacleList[i][j][2]  )) {

                    canMoveRight = !((hRight - (obstacleList[i][j][0] )) >= 0 && (hRight - (obstacleList[i][j][0] )) < 5 && hFooting -2 > (obstacleList[i][j][1]));

                    canMoveLeft = !((hLeft - (obstacleList[i][j][2] )) <= 0 && (hLeft - (obstacleList[i][j][2] )) > -5 && hFooting -2 > obstacleList[i][j][1]);

                    if (canMoveRight && canMoveLeft) {

                        hero.setGround(obstacleList[i][j][1] + 8);

                    }
                    i = 6 ;
                    j = 4;



                } else {

                    canMoveLeft = true;
                    canMoveRight = true;
                /*
                    hero.setGround(groundLevel);
                 */
                }

            }
        }

        hero.updateMessage(mapPosX);

        if (mapPosX <= 0) {
            canMoveLeft = false;
        }
        if(hero.x >= 15000){

            sceneChange();
            Map.setEndMap(true);
        }

        if (hero.isRunning()) {
            hero.move(mapPosX, canMoveRight,canMoveLeft);
            if(hero.dying) {
                canMoveLeft = !hero.dying;
                canMoveRight = !hero.dying;
            }

           mapPosX = Map.moveMap(hero.x, hero.y, canMoveLeft, canMoveRight, playerCommand);

            //Log.e("mapPosX ", Float.toString(mapPosX));

        }
        if (startJumping) {
            if(!hero.dying) {
                jumping = true;
            }

        }
        if (jumping) {
            hero.jump();
        }
        if (hero.falling || hero.startFalling) {
            hero.calcFooting();
        }

        jumping = hero.contJump();
    }

    public void leftPlayerMove(float posX, float posY) {


        if (posX > 300 && posX < 300 + arrowWidth && posY > controlHeight && posY < controlHeight + arrowheight) {
            playerCommand = 1;

        } else if (posX > 100 && posX < 100 + arrowWidth && posY > controlHeight && posY < controlHeight + arrowheight) {

            playerCommand = 2;
        }

        if (posX > middleSpace && posX < middleSpace + circleDiameter && posY > controlHeight && posY < controlHeight + circleDiameter) {
            startJumping = true;
            //shoot = false;
        } else if (posX > (middleSpace - circleDiameter) && posX < middleSpace ){ //&& posY > controlHeight && posY < controlHeight + circleDiameter) {
           shotHeight = (int)posY;
            shoot = true;
        }

    }


    private void buttonReleaseLeft(float posX, float posY) {

        if (posX > 300 && posX < 300 + arrowWidth && posY > controlHeight && posY < controlHeight + arrowheight) {
            playerCommand = 0;
        } else if (posX > 100 && posX < 100 + arrowWidth && posY > controlHeight && posY < controlHeight + arrowheight) {

            playerCommand = 0;
        }

        if (posX > middleSpace && posX < middleSpace + circleDiameter && posY > controlHeight && posY < controlHeight + circleDiameter) {
            startJumping = false;
        } else if (posX > (middleSpace - circleDiameter) && posX < middleSpace){// && posY > controlHeight && posY < controlHeight + circleDiameter) {
            shoot = false;
        } else {
            startJumping = false;
        }
    }

    public void DrawOverlay(Canvas canvas) {
        //align right arrow
        frameToDraw.left = 0;
        frameToDraw.top = 0;
        frameToDraw.right = 100;
        frameToDraw.bottom = 60;

        whereToDraw.left = 300;
        whereToDraw.top = controlHeight;
        whereToDraw.right = 300 + arrowWidth;
        whereToDraw.bottom = controlHeight + arrowheight;


        canvas.drawBitmap(overlay, frameToDraw, whereToDraw, paint);
        //align left arrow
        frameToDraw.left = 100;
        frameToDraw.top = 0;
        frameToDraw.right = 200;
        frameToDraw.bottom = 60;

        whereToDraw.left = 100;
        whereToDraw.right = 100 + arrowWidth;

        canvas.drawBitmap(overlay, frameToDraw, whereToDraw, paint);
        //align jump button
        frameToDraw.left = 200;
        frameToDraw.top = 0;
        frameToDraw.right = 300;
        frameToDraw.bottom = 100;

        whereToDraw.left = middleSpace;
        whereToDraw.top = controlHeight;
        whereToDraw.right = middleSpace + circleDiameter;
        whereToDraw.bottom = controlHeight + circleDiameter;
        canvas.drawBitmap(overlay, frameToDraw, whereToDraw, paint);
        //align shoot button
        frameToDraw.left = 300;
        frameToDraw.top = 0;
        frameToDraw.right = 400;
        frameToDraw.bottom = 200;

        whereToDraw.left = middleSpace - circleDiameter;
        whereToDraw.top = (controlHeight - (int)(1.5* circleDiameter));
        whereToDraw.right = middleSpace;
        whereToDraw.bottom = controlHeight + circleDiameter ; //+ (int)(1.1 * circleDiameter);
        canvas.drawBitmap(overlay, frameToDraw, whereToDraw, paint);
        /*
        try {
           byte [] encodeByte = Base64.decode("1234346345sdgs", Base64.DEFAULT);
            scoreBMP = BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
        }catch(Exception e){
            e.getMessage();
        }
        frameToDraw.left = 0;
        frameToDraw.top = 0;
        frameToDraw.right = scoreBMP.getWidth();
        frameToDraw.bottom = scoreBMP.getHeight();

        whereToDraw.left = 0;
        whereToDraw.top = 0;
        whereToDraw.right = 200;
        whereToDraw.bottom = 100; //+ (int)(1.1 * circleDiameter);
        canvas.drawBitmap(scoreBMP, frameToDraw, whereToDraw, paint);
        */

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event){
        boolean handled = false;

        switch (keyCode){

            case KeyEvent.KEYCODE_DPAD_CENTER:
                playerCommand = 0;
                handled = true;
                break;

            case KeyEvent.KEYCODE_BUTTON_A:
                // ... handle selections
                handled = true;
                break;
            case KeyEvent.KEYCODE_DPAD_LEFT:
                // ... handle left action
                handled = true;
                break;
            case KeyEvent.KEYCODE_DPAD_RIGHT:
                // ... handle right action
                playerCommand = 1;
                handled = true;
                break;
        }
        return handled || super.onKeyDown(keyCode, event);
    }

    public boolean onTouchEvent(MotionEvent me) {

        int action = MotionEventCompat.getActionMasked(me);
        int pointerId = 0 ;
        int index = me.findPointerIndex(pointerId);
        int x = -1;
        int y = -1;

        //int index = MotionEventCompat.getActionIndex(me);





        switch (action) {

            case MotionEvent.ACTION_DOWN:
                interact = true;
                //playerCommand = 1;
                //leftPlayerMove(MotionEventCompat.getX(me, index), MotionEventCompat.getY(me, index));
                //leftPlayerMove(me.getX(), me.getY());
                pointerId= me.getPointerId(0);
                index = me.findPointerIndex(pointerId);
                x =  (int)me.getX(index);
                y = (int)me.getY(index);

                leftPlayerMove(me.getX(index), me.getY(index));

                break;
            case MotionEvent.ACTION_UP:
                //interact = false;
                playerCommand = 0;
               // buttonReleaseLeft(MotionEventCompat.getX(me, index), MotionEventCompat.getY(me, index));
                pointerId = me.getPointerId(0);
                index = me.findPointerIndex(pointerId);
                x =  (int)me.getX(index);
                y = (int)me.getY(index);

                buttonReleaseLeft(me.getX(index), me.getY(index));

                if(me.getPointerCount() > 1) {
                    pointerId = me.getPointerId(1);
                    index = me.findPointerIndex(pointerId);
                    x = (int) me.getX(index);
                    y = (int) me.getY(index);

                    buttonReleaseLeft(me.getX(index), me.getY(index));
                }

                break;
            case MotionEvent.ACTION_MOVE:

                //leftPlayerMove(MotionEventCompat.getX(me, index), MotionEventCompat.getY(me, index));
                //leftPlayerMove(me.getX(),me.getY());
                pointerId = me.getPointerId(0);
                index = me.findPointerIndex(pointerId);
                x =  (int)me.getX(index);
                y = (int)me.getY(index);

                leftPlayerMove(me.getX(index), me.getY(index));
                if( me.getPointerCount() > 1){
                    pointerId = me.getPointerId(1);
                    index = me.findPointerIndex(pointerId);
                    x =  (int)me.getX(index);
                    y = (int)me.getY(index);

                    leftPlayerMove(me.getX(index), me.getY(index));
                }
                break;

            case MotionEvent.ACTION_POINTER_DOWN:
                interact = true;
                //playerCommand = 1;
                //leftPlayerMove(MotionEventCompat.getX(me, index), MotionEventCompat.getY(me, index));
                //leftPlayerMove(me.getX(), me.getY());
                pointerId = me.getPointerId(0);
                index = me.findPointerIndex(pointerId);
                x =  (int)me.getX(index);
                y = (int)me.getY(index);

                leftPlayerMove(me.getX(index), me.getY(index));

                break;
            case MotionEvent.ACTION_POINTER_UP:
               // interact = false;
                playerCommand = 0;
                //buttonReleaseLeft(MotionEventCompat.getX(me, index), MotionEventCompat.getY(me, index));
                //buttonReleaseLeft(me.getX(), me.getY());
                //buttonReleaseRight(0, 0);
                pointerId = me.getPointerId(0);
                index = me.findPointerIndex(pointerId);
                x =  (int)me.getX(index);
                y = (int)me.getY(index);

                buttonReleaseLeft(me.getX(index), me.getY(index));


                pointerId = me.getPointerId(1);
                index = me.findPointerIndex(pointerId);
                x =  (int)me.getX(index);
                y = (int)me.getY(index);

                buttonReleaseLeft(me.getX(index), me.getY(index));
                break;



        }


        return true;

    }
    public void checkHState(){
        if(hero.getDead()  ){
            if(hero.getLives() <= 0){
                /**
                 *
                 * code for death
                 */
            if(playAd) {
                //requestNewInterstitial();
                playing = false;
              mainHandler.post(new Runnable() {
                  @Override
                  public void run() {
                      if (mInterstitialAd.isLoaded()) {
                          mInterstitialAd.show();
                      }

                  }
              });




                playAd = false;
            }

            }else{
                hero.reset();
                shots.resetHealth();



            }


        }
    }

    public void setAd(InterstitialAd ad){
        this.mInterstitialAd = ad;
    }
    public void newMap(Context context,int mapNo){
        //mp.setLooping(true);
        mp = MediaPlayer.create(context, R.raw.scifi);
        currentStats.currentMap = mapNo;



        initEntities(context,mapNo);
    }


    public void sceneChange(){
        /*
        shotImage.recycle();
        heroImage.recycle();
        enemyImage.recycle();
        mapImage.recycle();
        mapBackground.recycle();


        shotImage = null;
        heroImage= null;
        enemyImage = null;
        mapImage = null;
        mapBackground = null;
        //java.lang.System.gc();
        Log.d("Clear Images", "True");
        */
        hero.nullImage();
        Map.nullImage();
        enemies.nullImage();
        shots.nullImage();
        //System.gc();


    }


    public void pause() {
        //Log.d("is Paused", Boolean.toString(playing));
        playing = false;
        mp.pause();
        try {
            gameThread.join();
        } catch (InterruptedException e) {
            Log.getStackTraceString(e);

        }

    }




    public void resume() {
        //mp.start();
       // mp.reset();
       // mp.setLooping(true);

        //mp = MediaPlayer.create(context, R.raw.scifi);

        mp.start();
        playing = true;

            gameThread = new Thread(this);
            gameThread.start();


    }
    public void startNew(Context context, int mapNo, boolean returned){

        playAd = true;

        if(returned) {
            sceneChange();
        }
        newMap(context,mapNo);
        gameThread = new Thread(this);
        gameThread.start();
        playing = true;
        mp.start();
    }
    public void nextLevel(){
        playing = true;
        playAd = true;
        mp.release();
        playing = false;
       // Looper.prepare();


        newMap(context,currentStats.currentMap + 1);
        playing = true;

       // mp.start();
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
       // gameThread = new Thread(this);
        //gameThread.start();

       //this.resume();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
       /*
        try {
            gameThread.join();
        }catch(InterruptedException e){

        }
        */


    }


    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {

        /*
    mp.release();
        try{
        gameThread.join();
        }catch(InterruptedException e){

        }
        sceneChange();

        Log.d("surface destroyed", "destroyed");
    /*
       // this.pause();
        playing = false;
        try {
            gameThread.join();
        } catch (InterruptedException e) {

        }
       */

    }
    private void requestNewInterstitial() {
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .build();

        mInterstitialAd.loadAd(adRequest);
    }
}