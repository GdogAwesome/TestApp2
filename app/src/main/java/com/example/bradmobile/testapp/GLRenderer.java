package com.example.bradmobile.testapp;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.opengl.GLU;
import android.util.Log;



/**
 * Created by BradMobile on 12/6/2015.
 */
public class GLRenderer implements GLSurfaceView.Renderer {

    private static final String TAG = "GLRenderer";
    private final Context context;

    GLRenderer(Context context){
        this.context = context;
    }

    public void onSurfaceCreated(GL10 gl, EGLConfig config){

    }

    public void onSurfaceChanged(GL10 gl, int width, int height){
    }

    public void onDrawFrame(GL10 gl){

    }



}
