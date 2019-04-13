package com.example.tomek.magicwizards;

import android.support.v7.app.AppCompatActivity;;
import android.os.Bundle;
import android.gesture.GestureLibraries;
import android.gesture.GestureLibrary;
import android.gesture.GestureOverlayView;
import android.gesture.GestureOverlayView.OnGesturePerformedListener;
import android.gesture.Prediction;

import android.widget.TextView;

import android.gesture.Gesture;
import java.util.ArrayList;


public class CustomGesturesActivity extends AppCompatActivity implements OnGesturePerformedListener
{
    private GestureLibrary gLibrary;
    private MyGLSurfaceView gLView;

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
        ArrayList<Prediction> predictions = gLibrary.recognize(gesture);
        TextView resultView = findViewById(R.id.resultText);
        // TODO Zależność między "czarem", a progiem rozpoznawania (prediction.score)
        if (predictions.size() > 0 && predictions.get(0).score > 1.0)
        {

            String action = predictions.get(0).name + " " + predictions.get(0).score;
            //Log.d("Test", action);
            //Toast.makeText(this, action, Toast.LENGTH_SHORT).show();
            Integer result = (int)(predictions.get(0).score*10);
            resultView.setText(result.toString());
        }
        else
        {
            //Log.d("Test", "Za slaby wyni kalbo nie ma gestow (" + predictions.size() + ")");
            // TODO Zmienić na Integer
            resultView.setText("0");
        }
    }


}
