package com.example.bradmobile.testapp;

import android.opengl.GLSurfaceView;
import android.content.Context;
import android.opengl.GLSurfaceView;


/**
 * Created by BradMobile on 12/6/2015.
 */
public class GLView extends GLSurfaceView {

    private final GLRenderer renderer;

    GLView(Context context){
        super(context);

        renderer = new GLRenderer(context);
        setRenderer(renderer);
    }

}
