package com.example.binder3u.popituserapp;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.gesture.Gesture;
import android.gesture.GestureLibraries;
import android.gesture.GestureLibrary;
import android.gesture.GestureOverlayView;
import android.gesture.Prediction;
import android.graphics.Rect;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class QuestionActivity extends Activity implements GestureOverlayView.OnGesturePerformedListener {
    private GestureLibrary mLibrary;
    private boolean nouvelleQuestion = true;
    private int numQuestion = 0;
    private Questionnaire questionnaire;
    private TextView TVnumQuestion;
    private TextView TvtotalQuestions;
    private TextView TVtexteQuestion;
    private ViewGroup mainLayout;
    private ImageView image1;
    private ImageView image2;
    private ImageView image3;
    private ImageView image4;
    private ImageView image5;
    private ImageView zone;

    private int xDelta;
    private int yDelta;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);

        mLibrary = GestureLibraries.fromRawResource(this, R.raw.gesture);
        if (!mLibrary.load()) {
            finish();
        }

//        GestureOverlayView gestures = (GestureOverlayView) findViewById(R.id.gestures);
//        gestures.addOnGesturePerformedListener(this);

        mainLayout = (RelativeLayout) findViewById(R.id.main_zone);

        zone = (ImageView) findViewById(R.id.goal);

        image1 = (ImageView) findViewById(R.id.pnt1);
        image1.setOnTouchListener(onTouchListener());

        image2 = (ImageView) findViewById(R.id.pnt2);
        image2.setOnTouchListener(onTouchListener());

        image3 = (ImageView) findViewById(R.id.pnt3);
        image3.setOnTouchListener(onTouchListener());

        image4 = (ImageView) findViewById(R.id.pnt4);
        image4.setOnTouchListener(onTouchListener());

        image5 = (ImageView) findViewById(R.id.pnt5);
        image5.setOnTouchListener(onTouchListener());
        
        
        questionnaire = new Questionnaire();

        Log.i("TAG", questionnaire.getListeQuestions().get(numQuestion).getTexte());

        TVnumQuestion = findViewById(R.id.textView_num_question);
        TvtotalQuestions = findViewById(R.id.textView_nb_total_questions);
        TvtotalQuestions.setText(Integer.toString(questionnaire.getListeQuestions().size()));
        TVtexteQuestion = findViewById(R.id.textView_texteQuestion);

        nouvelleQuestion(questionnaire.getListeQuestions().get(numQuestion));

    }

    private View.OnTouchListener onTouchListener() {
        return new View.OnTouchListener() {

            @SuppressLint("ClickableViewAccessibility")
            @Override
            public boolean onTouch(View view, MotionEvent event) {

                final int x = (int) event.getRawX();
                final int y = (int) event.getRawY();

                switch (event.getAction() & MotionEvent.ACTION_MASK) {

                    case MotionEvent.ACTION_DOWN:
                        RelativeLayout.LayoutParams lParams = (RelativeLayout.LayoutParams)
                                view.getLayoutParams();

                        xDelta = x - lParams.leftMargin;
                        yDelta = y - lParams.topMargin;
                        break;

                    case MotionEvent.ACTION_UP:
                        Rect rect1 = new Rect(image1.getLeft(),image1.getTop(),image1.getRight(),image1.getBottom());
                        //image1.getDrawingRect(rect1);
                        Rect rect2 = new Rect(image2.getLeft(),image2.getTop(),image2.getRight(),image2.getBottom());
                        //image2.getDrawingRect(rect2);
                        Rect rect3 = new Rect(image3.getLeft(),image3.getTop(),image3.getRight(),image3.getBottom());
                        //image3.getDrawingRect(rect3);
                        Rect rect4 = new Rect(image4.getLeft(),image4.getTop(),image4.getRight(),image4.getBottom());
                        //image4.getDrawingRect(rect4);
                        Rect rect5 = new Rect(image5.getLeft(),image5.getTop(),image5.getRight(),image5.getBottom());
                        //image5.getDrawingRect(rect5);
                        Rect rectZ = new Rect(zone.getLeft(),zone.getTop(),zone.getRight(),zone.getBottom());
                        //zone.getDrawingRect(rectZ);
                        int total = 0;
                        if(Rect.intersects(rect1,rectZ))
                            total++;
                        if(Rect.intersects(rect2,rectZ))
                            total++;
                        if(Rect.intersects(rect3,rectZ))
                            total++;
                        if(Rect.intersects(rect4,rectZ))
                            total++;
                        if(Rect.intersects(rect5,rectZ))
                            total++;

                        Toast.makeText(QuestionActivity.this,
                                Integer.toString(total), Toast.LENGTH_SHORT)
                                .show();
                        break;

                    case MotionEvent.ACTION_MOVE:
                        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) view
                                .getLayoutParams();
                        layoutParams.leftMargin = x - xDelta;
                        layoutParams.topMargin = y - yDelta;
                        layoutParams.rightMargin = 0;
                        layoutParams.bottomMargin = 0;
                        view.setLayoutParams(layoutParams);
                        break;
                }
                mainLayout.invalidate();
                return true;
            }
        };
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
