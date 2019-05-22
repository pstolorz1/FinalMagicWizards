package com.example.tomek.magicwizards;

import android.content.Intent;
import android.gesture.GestureStroke;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;;
import android.os.Bundle;
import android.gesture.GestureLibraries;
import android.gesture.GestureLibrary;
import android.gesture.GestureOverlayView;
import android.gesture.GestureOverlayView.OnGesturePerformedListener;
import android.gesture.Prediction;

import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import android.gesture.Gesture;
import java.util.ArrayList;
import java.util.List;


public class CustomGesturesActivity extends AppCompatActivity implements OnGesturePerformedListener
{
    private GestureLibrary gLibrary;
    private MyGLSurfaceView gLView;
    private TextView resultView;
    private TextView hpView;
    View test;
    int HP = 1000;
    private static long currTimeInMs = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        //Log.d("Test", "PRESTART");
        super.onCreate(savedInstanceState);
        //gLView = new MyGLSurfaceView(this);
        //requestWindowFeature(Window.FEATURE_NO_TITLE);
        //getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        //setContentView(gLView);
        //Log.d("Test", "START");
        gLView = findViewById(R.id.openGLOverlay);
        resultView = findViewById(R.id.resultText);
        test = findViewById(R.id.gOverlay);
        test.setOnTouchListener(new View.OnTouchListener()
        {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                // Interpret MotionEvent data
                /*if(System.currentTimeMillis() - currTimeInMs > 10000)
                {
                    //resultView.setText("CZAS: " + (System.currentTimeMillis() - currTimeInMs));
                    SetOpenGLCooldown();
                    gLView.myRenderer.AddNewEffect(new Vec2(event.getX(),event.getY()));
                }*/
                //resultView.setText("CZAS: " + (System.currentTimeMillis() - currTimeInMs));
                // Handle touch here
                //if()
                //resultView.setText("CZAS: " + SystemClock.uptimeMillis() % 4000L);
                gLView.myRenderer.ShowStar(event.getX(),event.getY());
                return true;

            }
        });
        gestureSetup();
    }
    @Override
    protected void onPause()
    {
        super.onPause();
        gLView.onPause();
    }
    @Override
    protected void onResume()
    {
        super.onResume();
        gLView.onResume();
    }

    private void gestureSetup() {
        gLibrary =
                GestureLibraries.fromRawResource(this,
                        R.raw.gestures);
        if (!gLibrary.load())
        {
            //Log.d("Test", "FINISH");
            finish();
        }
        //Log.d("Test", "DALEJ");
        GestureOverlayView gOverlay = findViewById(R.id.gOverlay);
        gOverlay.addOnGesturePerformedListener(this);
    }

    public void onGesturePerformed(GestureOverlayView overlay, Gesture gesture)
    {
        List<GestureStroke> gs = gesture.getStrokes();
        gLView.myRenderer.NewTrail(gs.get(0).points, gs.get(0).length);
        //resultView.setText(gs.get(0).length + " ");
        ArrayList<Prediction> predictions = gLibrary.recognize(gesture);

        // TODO Zależność między "czarem", a progiem rozpoznawania (prediction.score)
        if (predictions.size() > 0 && predictions.get(0).score > 1.0)
        {
            //gLView.myRenderer.NewTrail(gs.get(0).points, gs.get(0).length);
            //resultView.setText("TEST: " + gLView.myRenderer.GetPointsDistance(new Vec2(2,5),new Vec2(5,9)));
            //String action = predictions.get(0).name + " " + predictions.get(0).score;
            Integer result = (int)(predictions.get(0).score);
            if(predictions.get(0).name == "kwadrat") result *= 5;
            else if(predictions.get(0).name == "trojkat") result *= 4;
            else result *= 15;
            HP -= result;
            //hpView.setText(HP);
            //resultView.setText(result.toString());
        }
        else
        {
            // TODO Zmienić na Integer
            //resultView.setText("0");
        }
    }

    public static void SetOpenGLCooldown()
    {
        currTimeInMs = System.currentTimeMillis();
    }

}
