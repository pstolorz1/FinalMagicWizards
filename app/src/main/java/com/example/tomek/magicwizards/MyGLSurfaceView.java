package com.example.tomek.magicwizards;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;

/**  Klasa wyswietlajaca wzory
 * @see MYGLSurfaceView funkcja obslugujaca biblioteke OpenGl i wyswietlajaca na ekranie narysowanie wzor
 */
class MyGLSurfaceView extends GLSurfaceView
{
    public MyGLRenderer myRenderer;
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
