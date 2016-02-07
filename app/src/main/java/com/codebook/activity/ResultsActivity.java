package com.codebook.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


import com.codebook.R;
import com.codebook.loaders.CustomFontLoader;
import com.codebook.models.Answer;
import com.codebook.models.Category;
import com.codebook.models.Question;
import com.codebook.models.Score;
import com.codebook.persistence.DatabaseHelper;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


/**
 * Created by Narayan Acharya on 02/11/2015.
 */
public class ResultsActivity extends AppCompatActivity {

    ArrayList<Question> questions;
    ArrayList<Answer> answers;
    ArrayList<Integer> pointsList;

    String session_start;
    Button bPlayMore;
    Button bBackToCategories;
    public int attempted;
    LineChart lineChart;

    public int corrected;
    TextView scoreValue;
    TextView correctValue;
    TextView timerValue;
    TextView tvScoreMessage;
    int time;
    String session_end;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DatabaseHelper db = DatabaseHelper.getDbInstance(ResultsActivity.this);
        setContentView(R.layout.activity_results);
        initLayout();
        overridePendingTransition(R.anim.activity_open_translate, R.anim.activity_close_scale);
        questions = getIntent().getParcelableArrayListExtra(Question.TAG);
        answers = getIntent().getParcelableArrayListExtra(Answer.TAG);
        pointsList = getIntent().getIntegerArrayListExtra("POINTS");
        Log.d("Points", pointsList.toString());
        int points = getIntent().getExtras().getInt(Score.TAG);

        boolean allCorrect = true;

        Score newScore = new Score(answers.get(0).getCatId(), answers.get(0).getLevelId(), points, 1);
        db.setPointsCount(newScore);

        lineChart = (LineChart) findViewById(R.id.lineChart);
        lineChart.setDescription("");
        lineChart.setDrawGridBackground(false);
        lineChart.getLegend().setEnabled(false);
        lineChart.setTouchEnabled(false);

        XAxis xAxis = lineChart.getXAxis();
        xAxis.setTextColor(ContextCompat.getColor(this, R.color.text_color));
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);

        lineChart.getAxisRight().setEnabled(false);

        YAxis yAxis = lineChart.getAxisLeft();
        yAxis.setTextColor(ContextCompat.getColor(this, R.color.text_color));
        yAxis.setStartAtZero(true);
        yAxis.setDrawGridLines(false);

        ArrayList<String> xVals = new ArrayList<>();
        ArrayList<Entry> yValsCorrect = new ArrayList<>();

        for (int i = 0; i < questions.size(); i++) {
            xVals.add((i + 1) + "");
            db.setCorrectStatus(answers.get(i));
            if (answers.get(i).getCorrect() == 1) {
                Entry e = new Entry(pointsList.get(i), i);
                e.setData(questions.get(i));
                yValsCorrect.add(e);
                corrected++;
            } else {
                allCorrect = false;
            }
        }
        if (allCorrect) {
            db.unlockNextLevel(answers.get(0).getCatId(), answers.get(0).getLevelId());
        }
        attempted = 10;
        for (int i = 0; i < questions.size(); i++) {
            time = time + (int) (answers.get(i).getTime());
        }

        scoreValue.setText(points + "");
        correctValue.setText(corrected + "/" + attempted);
        timerValue.setText(time / 1000 + "s");

        LineDataSet lineDataSetCorrect = new LineDataSet(yValsCorrect, "");
        lineDataSetCorrect.setColor(ContextCompat.getColor(this, R.color.text_color));
        lineDataSetCorrect.setCircleColor(ContextCompat.getColor(this, R.color.text_color));
        lineDataSetCorrect.setDrawCircleHole(true);
        lineDataSetCorrect.setDrawValues(true);
        // lineDataSetCorrect.setDrawCubic(true);
        lineDataSetCorrect.setValueTextColor(ContextCompat.getColor(this, R.color.text_color));

        ArrayList<LineDataSet> dataSets = new ArrayList<>();
        dataSets.add(lineDataSetCorrect);
        LineData lineData = new LineData(xVals, dataSets);
        lineChart.setData(lineData);

        if (points >= 2500) {
            tvScoreMessage.setText("Excellent!");
        } else if (points >= 2000) {
            tvScoreMessage.setText("Great!");
        } else if (points >= 1500) {
            tvScoreMessage.setText("Good!");
        } else if (points >= 1000) {
            tvScoreMessage.setText("Not so bad!");
        } else if (points >= 500) {
            tvScoreMessage.setText("Can do better!");
        } else {
            tvScoreMessage.setText("Try Again!");
        }


    }
    @Override
    protected void onPause()
    {
        super.onPause();
        //closing transition animations
        overridePendingTransition(R.anim.activity_open_scale,R.anim.activity_close_translate);
    }
    private void initLayout() {
        tvScoreMessage = (TextView) findViewById(R.id.tvScoreMessage);
        scoreValue = (TextView) findViewById(R.id.tvScoreValue);
        timerValue = (TextView) findViewById(R.id.tvTimeValue);
        correctValue = (TextView) findViewById(R.id.tvCorrectValue);

        bPlayMore = (Button) findViewById(R.id.bPlayMore);
        bPlayMore.setTransformationMethod(null);
        bPlayMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(ResultsActivity.this, SelectActivity.class);
                Category category = DatabaseHelper.getDbInstance(ResultsActivity.this).getCategoryFromId(questions.get(0).getCategoryId());
                in.putExtra(Category.TAG, category);
                startActivity(in);
                finish();
            }
        });

        bBackToCategories = (Button) findViewById(R.id.bBackToCategories);
        bBackToCategories.setTransformationMethod(null);
        bBackToCategories.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(ResultsActivity.this, CategoryActivity.class);
                startActivity(in);
                finish();
            }
        });

        setFonts();
    }

    private void setFonts() {
        bPlayMore.setTypeface(CustomFontLoader.getTypeface(this, CustomFontLoader.RALEWAY_LIGHT));
        bBackToCategories.setTypeface(CustomFontLoader.getTypeface(this, CustomFontLoader.RALEWAY_LIGHT));

        ((TextView) findViewById(R.id.tvCorrect)).setTypeface(CustomFontLoader.getTypeface(this, CustomFontLoader.QUICKSAND_REGULAR));
        ((TextView) findViewById(R.id.tvTime)).setTypeface(CustomFontLoader.getTypeface(this, CustomFontLoader.QUICKSAND_REGULAR));
        ((TextView) findViewById(R.id.tvScore)).setTypeface(CustomFontLoader.getTypeface(this, CustomFontLoader.QUICKSAND_REGULAR));

        scoreValue.setTypeface(CustomFontLoader.getTypeface(this, CustomFontLoader.QUICKSAND_REGULAR));
        timerValue.setTypeface(CustomFontLoader.getTypeface(this, CustomFontLoader.QUICKSAND_REGULAR));
        correctValue.setTypeface(CustomFontLoader.getTypeface(this, CustomFontLoader.QUICKSAND_REGULAR));

        tvScoreMessage.setTypeface(CustomFontLoader.getTypeface(this, CustomFontLoader.QUICKSAND_BOLD));
    }
}
