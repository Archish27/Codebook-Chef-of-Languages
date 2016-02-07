package com.codebook.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.AdapterViewFlipper;
import android.widget.TextView;

import com.codebook.R;
import com.codebook.adapters.QuestionAdapter;
import com.codebook.loaders.CustomFontLoader;
import com.codebook.models.Answer;
import com.codebook.models.Question;
import com.codebook.models.Score;
import com.codebook.persistence.DatabaseHelper;
import com.codebook.persistence.SharedPreferenceManager;
import com.mikhaellopez.circularprogressbar.CircularProgressBar;

import java.util.ArrayList;


/**
 * Created by Narayan Acharya on 01/11/2015.
 */
public class GamePlayActivity extends AppCompatActivity implements QuestionAdapter.AdapterActivityInterface {

    private ArrayList<Question> questions;
    private ArrayList<Answer> answers;
    private ArrayList<Integer> pointsList;

    private int points = 0;
    private int maxAttempted;
    private TextView tvPoints, tvTimer, tvCat, tvLev;
    private CircularProgressBar timer;
    private AdapterViewFlipper adapterViewFlipper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        questions = getIntent().getParcelableArrayListExtra("QUESTIONS");
        answers = getIntent().getParcelableArrayListExtra("ANSWERS");

        pointsList = new ArrayList<Integer>();
        maxAttempted = getMaxAttempts(answers) + 1;

        setContentView(R.layout.activity_gameplay);

        QuestionAdapter questionAdapter = new QuestionAdapter(this, questions, answers);
        adapterViewFlipper = (AdapterViewFlipper) findViewById(R.id.quiz_view);
        adapterViewFlipper.setAdapter(questionAdapter);
        adapterViewFlipper.setInAnimation(this, R.animator.slide_in_right);
        adapterViewFlipper.setOutAnimation(this, R.animator.slide_out_left);

        tvCat = (TextView) findViewById(R.id.tvGPCat);
        tvCat.setText(DatabaseHelper.getDbInstance(this).getCategoryFromId(questions.get(0).getCategoryId()).getName());
        tvCat.setTypeface(CustomFontLoader.getTypeface(this, CustomFontLoader.QUICKSAND_BOLD));

        tvLev = (TextView) findViewById(R.id.tvGPLev);
        tvLev.setText(questions.get(0).getLevelId() + "");
        tvLev.setTypeface(CustomFontLoader.getTypeface(this, CustomFontLoader.QUICKSAND_BOLD));

        tvPoints = (TextView) findViewById(R.id.tvPoints);
        tvPoints.setText(0 + "");
        tvPoints.setTypeface(CustomFontLoader.getTypeface(this, CustomFontLoader.QUICKSAND_BOLD));

        tvTimer = (TextView) findViewById(R.id.tvTimer);
        tvTimer.setText(0 + "");
        tvTimer.setTypeface(CustomFontLoader.getTypeface(this, CustomFontLoader.QUICKSAND_BOLD));

        timer = (CircularProgressBar) findViewById(R.id.timer);
        timer.setProgress(100);
        overridePendingTransition(R.anim.activity_open_translate,R.anim.activity_close_scale);
    }
    @Override
    protected void onPause()
    {
        super.onPause();
        //closing transition animations
        overridePendingTransition(R.anim.activity_open_scale,R.anim.activity_close_translate);
    }
    @Override
    protected void onStop() {
        super.onStop();
        SharedPreferenceManager sp = new SharedPreferenceManager(getApplicationContext());
        sp.removePreference();

    }

    private int getMaxAttempts(ArrayList<Answer> answers) {
        int max = 0;
        for (int i = 0; i < answers.size(); i++) {
            if (max < answers.get(i).getAttempted()) {
                max = answers.get(i).getAttempted();
            }
        }
        return max;
    }

    @Override
    public void updateTimer(int time) {
        tvTimer.setText((time / 1000) + "");
        timer.setProgressWithAnimation(((time * 100) / QuestionAdapter.QUESTION_TIME_OUT));
    }

    @Override
    public void showNextQuestion(float time, boolean wasThisCorrect) {
        int nowPoints = 0;
        if (wasThisCorrect) {
            if (maxAttempted < 5) {
                nowPoints = (int) ((10 - maxAttempted + 1) * (30 - time / 1000));
            } else {
                nowPoints = (int) ((10 - 5 + 1) * (30 - time / 1000));
            }
            points += nowPoints;
            tvPoints.setText(points + "");
        }
        pointsList.add(nowPoints);

        if (adapterViewFlipper == null) {
            return;
        }
        final int count = adapterViewFlipper.getAdapter().getCount();
        int nextItem = adapterViewFlipper.getDisplayedChild() + 1;
        if (nextItem <= count) {
            adapterViewFlipper.showNext();
            if (nextItem == questions.size()) {
                Intent in = new Intent(this, ResultsActivity.class);
                in.putParcelableArrayListExtra(Question.TAG, questions);
                in.putParcelableArrayListExtra(Answer.TAG, answers);
                in.putIntegerArrayListExtra("POINTS", pointsList);
                in.putExtra(Score.TAG, points);
                startActivity(in);
                finish();
            }
        }
    }

    @Override
    public void onDestroy() {
        adapterViewFlipper = null;
        super.onDestroy();
    }
}
