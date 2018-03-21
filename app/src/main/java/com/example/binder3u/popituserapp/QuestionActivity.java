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

public class QuestionActivity extends Activity {
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

    // Gestures
    private boolean oneFinger = false;
    private boolean twoFinger = false;

    private float posX1start;
    private float posY1start;
    private float posX2start;
    private float posY2start;

    private float posX1stop;
    private float posY1stop;
    private float posX2stop;
    private float posY2stop;

    private int xDelta;
    private int yDelta;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);


        mainLayout = (RelativeLayout) findViewById(R.id.main_zone);
        mainLayout.setOnTouchListener(
                new RelativeLayout.OnTouchListener() {
                    public boolean onTouch(View v,
                                           MotionEvent m) {
                        gestureListener(m);
                        return true;
                    }
                }
        );

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

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        if (extras != null) {
            if (extras.containsKey("numQuestion")) {
                numQuestion = extras.getInt("numQuestion");
            }
        }


        TVnumQuestion = findViewById(R.id.textView_num_question);
        TvtotalQuestions = findViewById(R.id.textView_nb_total_questions);
        TvtotalQuestions.setText(Integer.toString(questionnaire.getListeQuestions().size()));
        TVtexteQuestion = findViewById(R.id.textView_texteQuestion);

        afficherQuestion(questionnaire.getListeQuestions().get(numQuestion));

    }

    private void afficherQuestion(Question question) {
        TVnumQuestion.setText(Integer.toString(question.getNumero()));
        TVtexteQuestion.setText(question.getTexte());
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
                        oneFinger = false;
                        twoFinger = false;

                        Rect rect1 = new Rect(image1.getLeft(), image1.getTop(), image1.getRight(), image1.getBottom());
                        //image1.getDrawingRect(rect1);
                        Rect rect2 = new Rect(image2.getLeft(), image2.getTop(), image2.getRight(), image2.getBottom());
                        //image2.getDrawingRect(rect2);
                        Rect rect3 = new Rect(image3.getLeft(), image3.getTop(), image3.getRight(), image3.getBottom());
                        //image3.getDrawingRect(rect3);
                        Rect rect4 = new Rect(image4.getLeft(), image4.getTop(), image4.getRight(), image4.getBottom());
                        //image4.getDrawingRect(rect4);
                        Rect rect5 = new Rect(image5.getLeft(), image5.getTop(), image5.getRight(), image5.getBottom());
                        //image5.getDrawingRect(rect5);
                        Rect rectZ = new Rect(zone.getLeft(), zone.getTop(), zone.getRight(), zone.getBottom());
                        //zone.getDrawingRect(rectZ);
                        int total = 0;
                        if (Rect.intersects(rect1, rectZ))
                            total++;
                        if (Rect.intersects(rect2, rectZ))
                            total++;
                        if (Rect.intersects(rect3, rectZ))
                            total++;
                        if (Rect.intersects(rect4, rectZ))
                            total++;
                        if (Rect.intersects(rect5, rectZ))
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

    private void gestureListener(MotionEvent m) {
        int pointerCount = m.getPointerCount();

        for (int i = 0; i < pointerCount; i++) {
            int x = (int) m.getX(i);
            int y = (int) m.getY(i);
            int id = m.getPointerId(i);
            int action = m.getActionMasked();
            int actionIndex = m.getActionIndex();
            String actionString;


            switch (action) {
                case MotionEvent.ACTION_DOWN:
                    actionString = "DOWN";
                    if (id == 0) {
                        oneFinger = true;

                    }

                    break;
                case MotionEvent.ACTION_UP:
                    actionString = "UP";
                    break;
                case MotionEvent.ACTION_POINTER_DOWN:
                    if (id == 0) {
                        oneFinger = true;
                        posX1start = x;
                        posY1start = y;
                    }
                    if ((id == 1) && (oneFinger)) {
                        twoFinger = true;
                        posX2start = x;
                        posY2start = y;
                    }
                    actionString = "PNTR DOWN";
                    break;
                case MotionEvent.ACTION_POINTER_UP:
                    oneFinger = false;
                    twoFinger = false;
                    if (id == 0) {
                        oneFinger = true;
                        posX1stop = x;
                        posY1stop = y;
                    }
                    if (id == 1) {
                        twoFinger = true;
                        posX2stop = x;
                        posY2stop = y;
                        Log.i("TAG", Boolean.toString(twoFinger) + " pos X1:" + posX1start + " pos Y1" + posY1start + " pos X2:" + posX2start + " pos Y12" + posY2start);
                        Log.i("TAG", Boolean.toString(twoFinger) + " pos X1:" + posX1stop + " pos Y1" + posY1stop + " pos X2:" + posX2stop + " pos Y12" + posY2stop);
                        verifSlideGauche();
                    }

                    actionString = "PNTR UP";
                    break;
                case MotionEvent.ACTION_MOVE:
                    actionString = "MOVE";
                    break;
                default:
                    actionString = "";
            }

            //String touchStatus = "Action: " + actionString + " Index: " + actionIndex + " ID: " + id + " X: " + x + " Y: " + y;
            //Log.i("TAG",touchStatus);


        }

    }

    private void verifSlideGauche() {
        if ((posX1start > (posX1stop + 100)) && ((posX2start > (posX2stop + 100)))) {
            verifProchaineQuestion();
        }
    }

    private void verifProchaineQuestion() {
        if (numQuestion < questionnaire.getListeQuestions().size() - 1) {
            numQuestion++;
            nouvelleQuestion();
        } else {
            passageScore();
        }
    }


    public void nouvelleQuestion() {
        Intent intent = new Intent(QuestionActivity.this, QuestionActivity.class);
        intent.putExtra("numQuestion", numQuestion);
        startActivity(intent);

    }

    public void passageScore() {
        Intent intent = new Intent(QuestionActivity.this, ScoreActivity.class);
        startActivity(intent);
    }
}
