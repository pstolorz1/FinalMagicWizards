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
    int instantiateNumber = 20;  //70
    int sqNumber = 20;
    public List<Vec2> trail = new ArrayList<>();
    public List<Square> symbols = new ArrayList<>();
    boolean showThem = false;
    public static float getAngle() {
        return mAngle;
    }

    public static void setAngle(float angle) {
        mAngle = angle;
    }

    public void NewTrail(float[] positions, float trailLength)
    {
        //sqNumber = 70;
        // obliczamy dlugosc po ktorej umeiszczamy kolejny efekt
        float distanceBetweenEffects = trailLength / sqNumber;
        Log.d("--WTF--","tl " + trailLength + "dbe " + distanceBetweenEffects);
        //trail.clear();
        trail.get(0).Set(GetWorldCoords(new Vec2(positions[0], positions[1]))); // początek

        float currUnusedLength = 0f;
        int effects = 1;
        for (int i = 2; i < positions.length; i=i+2)
        {
            // reszta odl. z poprzednich punktow + dystans miedzy tym pkt a poprzednim
            Vec2 A = new Vec2(positions[i-2],positions[i-1]);
            Vec2 B = new Vec2(positions[i],positions[i+1]);
            float distanceBetweenPoints = GetPointsDistance(A,B);
            currUnusedLength = currUnusedLength + distanceBetweenPoints;
            if(currUnusedLength > distanceBetweenEffects)
            {
                //  jest wiekszy od odleglosci miedzy kolejnymi efektami, oznacza to ze
                // juz nie czas na umieszczenie kolejnego efektu
                float d = distanceBetweenPoints - (currUnusedLength-distanceBetweenEffects);
                float Xac = d * (B.X()-A.X())/distanceBetweenPoints;
                float Yac = d * (B.Y()-A.Y())/distanceBetweenPoints;
                Log.d("WTF","PROCENT:   " + d);
                Log.d("WTF","A = X " + A.X() + "Y " + A.Y());
                Log.d("WTF","B = X " + B.X() + "Y " + B.Y());
                Log.d("WTF","C = X " + A.X()+Xac + "Y " + A.Y()+Yac);
                if(effects < instantiateNumber)trail.get(effects).Set(GetWorldCoords(new Vec2(A.X()+Xac, A.Y()+Yac)));
                else break;
                /*if(A.X() == B.X())
                {
                    if(A.Y() > B.Y()) trail.get(effects).Set(GetWorldCoords(new Vec2( A.X(), A.Y()+r)));
                    else trail.get(effects).Set(GetWorldCoords(new Vec2( A.X(), A.Y()-r)));
                }
                else
                {
                    float a = (B.Y() - A.Y()) / (B.X() - A.X());
                    float b = A.Y() - a * A.X();


                }*/
                // S(x1+x2/4,y1+y2/4)
                effects++;
                currUnusedLength = (currUnusedLength-distanceBetweenEffects);
            }
        }
        if(effects <= instantiateNumber)sqNumber = effects;
        else sqNumber = instantiateNumber;
        showThem = true;
        //trail.clear();
        /*int pointsNumber = positions.length;
        //Log.d("wtf", pointsNumber + "");
        // dzielimy gest na 4 czesci
        int xPos = pointsNumber / sqNumber;
        if(xPos % 2 == 1) xPos--;
        if(xPos == 0) xPos = 2;
        for (int i = 0; i < sqNumber-1; i++)
        {
            Log.d("wtf", (xPos*i) + " " + (xPos*i+1));
            if((xPos*i) >= pointsNumber || xPos*i+1 >= pointsNumber)
            {
                sqNumber = i + 1;
                break;
            }
            trail.get(i).Set(GetWorldCoords(new Vec2(positions[xPos*i], positions[xPos*i+1])));
            //Log.d("wtf", "przed: " + positions[xPos*i] + " " + positions[xPos*i+1]);
            //Log.d("wtf", "po: "+trail.get(i).X() + " " + trail.get(i).Y());
        }
        trail.get(sqNumber-1).Set(GetWorldCoords(new Vec2(positions[positions.length-2], positions[positions.length-1])));
        //trail.get(1).Set(GetWorldCoords(new Vec2(positions[xPos], positions[xPos+1])));
        //trail.get(2).Set(GetWorldCoords(new Vec2(positions[xPos*2], positions[xPos*2+1])));
        //trail.get(3).Set(GetWorldCoords(new Vec2(positions[xPos*3], positions[xPos*3+1])));
        //trail.get(4).Set(GetWorldCoords(new Vec2(positions[positions.length-2], positions[positions.length-1])));
        showThem = true;*/
    }
    public float GetPointsDistance(Vec2 a, Vec2 b)
    {
        return (float)Math.sqrt(Math.pow((b.X()-a.X()), 2) + Math.pow((b.Y()-a.Y()), 2));
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
        mSquare = new Square(0.1f);
        for (int i = 0; i < instantiateNumber; i++)
        {
            symbols.add(new Square(0.15f));
            symbols.get(i).setColor(0, (i * 0.015f),0);
            trail.add(new Vec2(0,0));
        }
        //symbols.get(0).setColor(0,0.2f + (0 * 0.1f),0);
    }

    public void onDrawFrame(GL10 gl)
    {
        float[] scratch = new float[16];
        // Redraw background color
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT);
        long time = SystemClock.uptimeMillis() % 4000L;
        float angle = 0.090f * ((int) time);
        if(showThem)
        {
            for (int i = 0; i < sqNumber; i++)
            {
                Matrix.setLookAtM(viewMatrix, 0, 0, 0, -3, 0f, 0f, 0f, 0f, 1.0f, 0.0f);
                Matrix.setRotateM(rotationMatrix, 0, angle, 0, 0, -1.0f);
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
        Vec2 worldPos = new Vec2(0,0);

        float screenW = screenWidth;
        float screenH = screenHeight;
        float ratio = screenW/screenH;

        worldPos.SetX(touch.X()/screenW * ratio);
        worldPos.SetY(touch.Y() / screenH);

        worldPos.Set(worldPos.X() * -2 + ratio,worldPos.Y() * -2 + 1 );

        return worldPos;
    }

}