package com.example.tomek.magicwizards;

import android.gesture.GestureStroke;
import android.support.v7.app.AppCompatActivity;;
import android.os.Bundle;
import android.gesture.GestureLibraries;
import android.gesture.GestureLibrary;
import android.gesture.GestureOverlayView;
import android.gesture.GestureOverlayView.OnGesturePerformedListener;
import android.gesture.Prediction;

import android.util.Log;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.widget.TextView;

import android.gesture.Gesture;
import java.util.ArrayList;
import java.util.List;


public class CustomGesturesActivity extends AppCompatActivity implements OnGesturePerformedListener
{
    private GestureLibrary gLibrary;
    private MyGLSurfaceView gLView;
    private TextView resultView;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        //Log.d("Test", "PRESTART");
        super.onCreate(savedInstanceState);
        //gLView = new MyGLSurfaceView(this);
        setContentView(R.layout.activity_main);
        //setContentView(gLView);
        //Log.d("Test", "START");
        gLView = findViewById(R.id.openGLOverlay);
        resultView = findViewById(R.id.resultText);
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
        //resultView.setText(Float.toString(gs.get(0).points[0]));
        String msg = gs.get(0).points.length + "|" + gs.get(0).points[0] + " " + gs.get(0).points[1];
        Log.d("test",msg);
        ArrayList<Prediction> predictions = gLibrary.recognize(gesture);

        // TODO Zależność między "czarem", a progiem rozpoznawania (prediction.score)
        if (predictions.size() > 0 && predictions.get(0).score > 1.0)
        {

            //String action = predictions.get(0).name + " " + predictions.get(0).score;
            Integer result = (int)(predictions.get(0).score*10);
            //resultView.setText(result.toString());
        }
        else
        {
            // TODO Zmienić na Integer
            //resultView.setText("0");
        }
    }

}
