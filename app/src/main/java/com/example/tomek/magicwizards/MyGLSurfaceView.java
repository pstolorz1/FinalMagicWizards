package com.example.tomek.magicwizards;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;

/**  Klasa wyswietlajaca wzory
 *
 */
class MyGLSurfaceView extends GLSurfaceView
{
    public MyGLRenderer myRenderer;

/** \brief funkcja obslugujaca biblioteke OpenGl i wyswietlajaca na ekranie narysowanie wzor
 *
 */
    public MyGLSurfaceView(Context context){
        super(context);

        // Create an OpenGL ES 2.0 context
        setEGLContextClientVersion(2);
        setPreserveEGLContextOnPause(true);
        //setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);
        // Set the Renderer for drawing on the GLSurfaceView
        myRenderer = new MyGLRenderer();
        setRenderer(myRenderer);
    }
    /** \brief funkcja obslugujaca biblioteke OpenGl i wyswietlajaca na ekranie narysowanie wzor
     *
     */
    public MyGLSurfaceView(Context context, AttributeSet attrSet){
        super(context,attrSet);

        // Create an OpenGL ES 2.0 context
        setEGLContextClientVersion(2);
        setPreserveEGLContextOnPause(true);
        //setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);
        // Set the Renderer for drawing on the GLSurfaceView
        myRenderer = new MyGLRenderer();
        setRenderer(myRenderer);
    }


}
