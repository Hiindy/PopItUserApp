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
import android.util.Log;
import android.widget.TextView;

import java.util.ArrayList;

public class QuestionActivity extends Activity implements GestureOverlayView.OnGesturePerformedListener {
    private GestureLibrary mLibrary;
    private boolean nouvelleQuestion = true;
    private int numQuestion = 0;
    private Questionnaire questionnaire;
    private TextView TVnumQuestion;
    private TextView TvtotalQuestions;
    private TextView TVtexteQuestion;


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

        questionnaire = new Questionnaire();

        Log.i("TAG", questionnaire.getListeQuestions().get(numQuestion).getTexte());

        TVnumQuestion = findViewById(R.id.textView_num_question);
        TvtotalQuestions = findViewById(R.id.textView_nb_total_questions);
        TvtotalQuestions.setText(Integer.toString(questionnaire.getListeQuestions().size()));
        TVtexteQuestion = findViewById(R.id.textView_texteQuestion);

        nouvelleQuestion(questionnaire.getListeQuestions().get(numQuestion));

    }

    public void nouvelleQuestion(Question question) {
        TVnumQuestion.setText(Integer.toString(question.getNumero()));
        TVtexteQuestion.setText(question.getTexte());

    }

    public void passageScore() {
        Intent intent = new Intent(QuestionActivity.this, ScoreActivity.class);
        startActivity(intent);
    }

    @Override
    public void onGesturePerformed(GestureOverlayView gestureOverlayView, Gesture gesture) {
        ArrayList<Prediction> predictions = mLibrary.recognize(gesture);

        if (predictions.size() > 0 && predictions.get(0).score > 1.0) {
            String result = predictions.get(0).name;

            if ("doubleSlide".equalsIgnoreCase(result)) {
                if(numQuestion < questionnaire.getListeQuestions().size()-1) {
                    numQuestion++;
                    nouvelleQuestion(questionnaire.getListeQuestions().get(numQuestion));
                }
                else
                {
                    passageScore();
                }
            }
        }

    }
}
