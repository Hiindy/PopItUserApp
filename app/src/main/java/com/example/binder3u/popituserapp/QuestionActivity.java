package com.example.binder3u.popituserapp;

import android.app.Activity;
import android.content.Intent;
import android.gesture.Gesture;
import android.gesture.GestureLibraries;
import android.gesture.GestureLibrary;
import android.gesture.GestureOverlayView;
import android.gesture.Prediction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.ArrayList;

public class QuestionActivity extends Activity implements GestureOverlayView.OnGesturePerformedListener {
    private GestureLibrary mLibrary;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);

        mLibrary = GestureLibraries.fromRawResource(this, R.raw.gesture);
        if (!mLibrary.load()) {
            finish();
        }

        GestureOverlayView gestures = (GestureOverlayView) findViewById(R.id.gestures);
        gestures.addOnGesturePerformedListener(this);
    }

    public void passageScore(){
        Intent intent = new Intent(QuestionActivity.this, ScoreActivity.class);
        startActivity(intent);
    }

    @Override
    public void onGesturePerformed(GestureOverlayView gestureOverlayView, Gesture gesture) {
        ArrayList<Prediction> predictions = mLibrary.recognize(gesture);

        if (predictions.size() > 0 && predictions.get(0).score > 1.0) {
            String result = predictions.get(0).name;

            if ("doubleSlide".equalsIgnoreCase(result)) {
                passageScore();
            }
        }

    }
}
