package com.example.tomek.magicwizards;

import android.content.Context;
import android.graphics.Point;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.opengl.Matrix;
import android.os.SystemClock;
import android.util.Log;
import android.view.Display;
import android.view.WindowManager;

import java.util.ArrayList;
import java.util.List;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

public class MyGLRenderer implements GLSurfaceView.Renderer
{
    private Square mSquare,mSquare2;
    Triangle mTriangle;
    // vPMatrix is an abbreviation for "Model View Projection Matrix"
    private final float[] vPMatrix = new float[16];
    private final float[] projectionMatrix = new float[16];
    private final float[] viewMatrix = new float[16];
    private float[] rotationMatrix = new float[16];
    private final float[] mTranslationMatrix = new float[16];
    public static volatile float mAngle;
    public int screenHeight, screenWidth;
    //public float X, Y;
    //public float X2, Y2;
    int sqNumber = 6;
    public List<Vec2> trail = new ArrayList<>();
    public List<Square> symbols = new ArrayList<>();
    boolean showThem = false;
    public static float getAngle() {
        return mAngle;
    }

    public static void setAngle(float angle) {
        mAngle = angle;
    }

    public void NewTrail(float[] positions)
    {
        //trail.clear();
        int pointsNumber = positions.length;
        Log.d("wtf", pointsNumber + "");
        // dzielimy gest na 4 czesci
        int xPos = pointsNumber / sqNumber;
        if(xPos % 2 == 1) xPos--;
        for (int i = 0; i < sqNumber-1; i++)
        {
            Log.d("wtf", (xPos*i) + " " + (xPos*i+1));
            trail.get(i).Set(GetWorldCoords(new Vec2(positions[xPos*i], positions[xPos*i+1])));
            //Log.d("wtf", "przed: " + positions[xPos*i] + " " + positions[xPos*i+1]);
            //Log.d("wtf", "po: "+trail.get(i).X() + " " + trail.get(i).Y());
        }
        trail.get(sqNumber-1).Set(GetWorldCoords(new Vec2(positions[positions.length-2], positions[positions.length-1])));
        /*trail.get(1).Set(GetWorldCoords(new Vec2(positions[xPos], positions[xPos+1])));
        trail.get(2).Set(GetWorldCoords(new Vec2(positions[xPos*2], positions[xPos*2+1])));
        trail.get(3).Set(GetWorldCoords(new Vec2(positions[xPos*3], positions[xPos*3+1])));
        trail.get(4).Set(GetWorldCoords(new Vec2(positions[positions.length-2], positions[positions.length-1])));*/
        showThem = true;
    }

    public void onSurfaceCreated(GL10 unused, EGLConfig config) {
        // Set the background frame color
        GLES20.glClearColor(1.0f, 0.2f, 0.2f, 1.0f);
        //mSquare = new Square(0.5f);
        //mSquare2 = new Square(0.5f);
        //mSquare2.setColor(0.5f,0.3f,1);
        mTriangle = new Triangle();
        /*X = 0.2f;
        Y = 0.1f;
        X2 = -0.2f;
        Y2 = 0.1f;*/
        mSquare = new Square(0.3f);
        for (int i = 0; i < sqNumber; i++)
        {
            symbols.add(new Square(0.3f));
            //symbols.get(i).setColor(0,0.2f + (i * 0.1f),0);
            trail.add(new Vec2(0,0));
        }
        //symbols.get(0).setColor(0,0.2f + (0 * 0.1f),0);
    }

    public void onDrawFrame(GL10 gl)
    {
        float[] scratch = new float[16];
        // Redraw background color
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT);
        /*long time = SystemClock.uptimeMillis() % 4000L;
        float angle = 0.090f * ((int) time);*/
        if(showThem)
        {
            for (int i = 0; i < symbols.size(); i++)
            {
                Matrix.setLookAtM(viewMatrix, 0, 0, 0, -3, 0f, 0f, 0f, 0f, 1.0f, 0.0f);
                Matrix.setRotateM(rotationMatrix, 0, 0, 0, 0, -1.0f);
                Matrix.translateM(viewMatrix, 0, trail.get(i).X(), trail.get(i).Y(), 0);
                Matrix.multiplyMM(vPMatrix, 0, projectionMatrix, 0, viewMatrix, 0);
                Matrix.multiplyMM(scratch, 0, vPMatrix, 0, rotationMatrix, 0);
                symbols.get(i).draw(scratch);
                //mSquare.draw(scratch);
            }
        }
    }

    public void onSurfaceChanged(GL10 unused, int width, int height)
    {
        GLES20.glViewport(0, 0, width, height);
        screenHeight = height;
        screenWidth = width;
        float ratio = (float) width / height;

        // this projection matrix is applied to object coordinates
        // in the onDrawFrame() method

        Matrix.frustumM(projectionMatrix, 0, -ratio, ratio, -1, 1, 3, 7);
    }
    public static int loadShader(int type, String shaderCode){

        // create a vertex shader type (GLES20.GL_VERTEX_SHADER)
        // or a fragment shader type (GLES20.GL_FRAGMENT_SHADER)
        int shader = GLES20.glCreateShader(type);

        // add the source code to the shader and compile it
        GLES20.glShaderSource(shader, shaderCode);
        GLES20.glCompileShader(shader);

        return shader;
    }

    public Vec2 GetWorldCoords( Vec2 touch)
    {
        //Log.d("World coords", "Hello There");
        // Initialize auxiliary variables.
        Vec2 worldPos = new Vec2(0,0);

        // SCREEN height & width (ej: 320 x 480)





        //touch.Set(0,160);
        float screenW = screenWidth;
        float screenH = screenHeight;

        /*Log.d("World coords", Float.toString(screenW));
        Log.d("World coords", Float.toString(screenH));
        // Auxiliary matrix and vectors
        // to deal with ogl.
        float[] invertedMatrix, transformMatrix,
                normalizedInPoint, outPoint;
        invertedMatrix = new float[16];
        transformMatrix = new float[16];
        normalizedInPoint = new float[4];
        outPoint = new float[4];

        // Invert y coordinate, as android uses
        // top-left, and ogl bottom-left.
        int oglTouchY = (int) (screenH - touch.Y());

       // Transform the screen point to clip
       //space in ogl (-1,1)
        normalizedInPoint[0] =
                (float) ((touch.X()) * 2.0f / screenW - 1.0);
        normalizedInPoint[1] =
                (float) ((oglTouchY) * 2.0f / screenH - 1.0);
        normalizedInPoint[2] = - 1.0f;
        normalizedInPoint[3] = 1.0f;
        Log.d("World coords", Float.toString(normalizedInPoint[0]));
        Log.d("World coords", Float.toString(normalizedInPoint[1]));
        Log.d("World coords", Float.toString(normalizedInPoint[2]));
        Log.d("World coords", Float.toString(normalizedInPoint[3]));
       //Obtain the transform matrix and
       //then the inverse.
        Matrix.multiplyMM(
                transformMatrix, 0,
                projectionMatrix, 0,
                viewMatrix, 0);
        Matrix.invertM(invertedMatrix, 0,
                transformMatrix, 0);
        Log.d("World coords", "Hello There");
        Matrix.multiplyMV(
                outPoint, 0,
                invertedMatrix, 0,
                normalizedInPoint, 0);
        Log.d("World coords", "jest blad???");
        if (outPoint[3] == 0.0)
        {
            // Avoid /0 error.
            Log.d("World coords", "ERROR!");
            return worldPos;
        }
        Log.d("World coords", Float.toString(outPoint[0]));
        Log.d("World coords", Float.toString(outPoint[1]));
        Log.d("World coords", Float.toString(outPoint[2]));
        Log.d("World coords", Float.toString(outPoint[3]));
        // Divide by the 3rd component to find
        // out the real position.
        worldPos.Set(
                outPoint[0] / outPoint[3],
                outPoint[1] / outPoint[3]);*/
        float ratio = screenW/screenH;
        worldPos.SetX(touch.X()/screenW * ratio);
        worldPos.SetY(touch.Y() / screenH);

        worldPos.Set(worldPos.X() * -2 + ratio,worldPos.Y() * -2 + 1 );
        //X = worldPos.X();
        //Y = worldPos.Y();
        return worldPos;


    }

}