package com.codebook.activity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.codebook.R;
import com.codebook.adapters.PracsAdapter;
import com.codebook.adapters.PracsNewAdapter;
import com.codebook.fragments.PracsFragment;
import com.codebook.fragments.RecyclerItemClickListener;
import com.codebook.loaders.CustomFontLoader;
import com.codebook.models.Answer;
import com.codebook.models.Category;
import com.codebook.models.Level;
import com.codebook.models.Pracs;
import com.codebook.models.Question;
import com.codebook.persistence.DatabaseHelper;
import com.codebook.persistence.QuestionTable;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * Created by Narayan Acharya on 30/10/2015.
 */
public class PrepareQuestionsActivity extends AppCompatActivity{
    private static final String TAG = "Prepare Questions";
    RecyclerView mainRecyclerView;
    DatabaseHelper databaseHelper;
    PracsNewAdapter mainRecyclerViewAdapter;
    Category category;
    public static String x;
    Level level;
    ArrayList<Question> questions;
    ArrayList<Answer> answers;
    String session_start;
    Button start;
    TextView tvLevelName, tvLevelId, tvLevelPoints, tvLevelInstructions;

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent in = new Intent(this, CategoryActivity.class);
        startActivity(in);
        finish();
        return true;
    }

    @Override
    protected void onPause() {
        super.onPause();
        //closing transition animations
        overridePendingTransition(R.anim.activity_open_scale, R.anim.activity_close_translate);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prepare_questions);
        overridePendingTransition(R.anim.activity_open_translate, R.anim.activity_close_scale);
        databaseHelper = DatabaseHelper.getDbInstance(this);
        category = getIntent().getParcelableExtra(Category.TAG);
        mainRecyclerView = (RecyclerView) findViewById(R.id.mainRecyclerView);
        level = getIntent().getParcelableExtra(Level.TAG);
        mainRecyclerView.addOnItemTouchListener(new RecyclerItemClickListener(getApplicationContext(), new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        // TODO Handle item click
                        Intent in = new Intent(getApplicationContext(), PracsActivity.class);
                        in.putExtra("position",position+1);
                        in.putExtra("categoryid",category.getId());
                        in.putExtra("levelid",level.getLevelId());
                        startActivity(in);

                    }
                })
        );
        switch (category.getId()) {
            case 1:
                findViewById(R.id.prepareContainer).setBackgroundResource(R.drawable.basic_bg);
                x="Output:-\n";
                break;
            case 2:
                x="Note : \n";
                findViewById(R.id.prepareContainer).setBackgroundResource(R.drawable.history_bg);
                break;
            case 3:
                findViewById(R.id.prepareContainer).setBackgroundResource(R.drawable.economics_bg);
                break;
            }
        start = (Button) findViewById(R.id.bQuizStart);
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startButtonClick(category, level);
            }
        });
        start.setTypeface(CustomFontLoader.getTypeface(this, CustomFontLoader.RALEWAY_REGULAR));
        // Remove all caps from the button
        start.setTransformationMethod(null);

        tvLevelName = (TextView) findViewById(R.id.tvLevelName);
        tvLevelName.setText(level.getLevelName());
        tvLevelName.setTypeface(CustomFontLoader.getTypeface(this, CustomFontLoader.RALEWAY_LIGHT));

        tvLevelId = (TextView) findViewById(R.id.tvLevelId);
        tvLevelId.setText(level.getLevelId() + "");
        tvLevelId.setTypeface(CustomFontLoader.getTypeface(this, CustomFontLoader.RALEWAY_MEDIUM));

        tvLevelPoints = (TextView) findViewById(R.id.tvLevelPoints);
        tvLevelPoints.setText(databaseHelper.getPointsCount(category.getId(), level.getLevelId()) + "");
        tvLevelPoints.setTypeface(CustomFontLoader.getTypeface(this, CustomFontLoader.RALEWAY_BOLD));

        tvLevelInstructions = (TextView) findViewById(R.id.tvLevelInstructions);
        tvLevelInstructions.setTypeface(CustomFontLoader.getTypeface(this, CustomFontLoader.RALEWAY_LIGHT));
        LinearLayout list = (LinearLayout) findViewById(R.id.Layout);
//        Toolbar catToolbar = (Toolbar) findViewById(R.id.catToolbar);
//        setSupportActionBar(catToolbar);
//        getSupportActionBar().setTitle(category.getName());
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        final Drawable upArrow = ContextCompat.getDrawable(this, R.drawable.abc_ic_ab_back_mtrl_am_alpha);
//        upArrow.setColorFilter(getResources().getColor(R.color.text_color), PorterDuff.Mode.SRC_ATOP);
//        getSupportActionBar().setHomeAsUpIndicator(upArrow);
//        catToolbar.setTitleTextColor(getResources().getColor(R.color.text_color));


        switch (level.getLevelId()) {
            case 1:
                tvLevelName.setVisibility(View.INVISIBLE);
                tvLevelId.setVisibility(View.INVISIBLE);
                tvLevelPoints.setVisibility(View.INVISIBLE);
                tvLevelInstructions.setVisibility(View.INVISIBLE);
                start.setVisibility(View.INVISIBLE);
                list.setVisibility(View.VISIBLE);
                setMainRecyclerView();
                break;
            case 2:

                tvLevelName.setVisibility(View.INVISIBLE);
                tvLevelId.setVisibility(View.INVISIBLE);
                tvLevelPoints.setVisibility(View.INVISIBLE);
                tvLevelInstructions.setVisibility(View.INVISIBLE);
                start.setVisibility(View.INVISIBLE);
                list.setVisibility(View.VISIBLE);
                setMainRecyclerView();
                break;
            case 3:

                tvLevelName.setVisibility(View.VISIBLE);
                tvLevelId.setVisibility(View.VISIBLE);
                tvLevelPoints.setVisibility(View.VISIBLE);
                tvLevelInstructions.setVisibility(View.VISIBLE);
                start.setVisibility(View.VISIBLE);
                list.setVisibility(View.GONE);
                break;
        }
    }

    public void setMainRecyclerView() {
        mainRecyclerViewAdapter = new PracsNewAdapter(this,getData());
        mainRecyclerView.setHasFixedSize(true);
        mainRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mainRecyclerView.setAdapter(mainRecyclerViewAdapter);
    }

    public List<Pracs> getData() {
        List<Pracs> data = new ArrayList<>();
        Pracs mainInfo = null;
        String[] args = new String[]{String.valueOf(category.getId()), String.valueOf(level.getLevelId())};
        Log.d("PrintValues", String.valueOf(category.getId())+" "+" " +String.valueOf(level.getLevelId()));
        Cursor c = databaseHelper.getQuestionData(args);
        if (c != null) {
            while (c.moveToNext()) {
                int nameIndex = c.getColumnIndex(QuestionTable.Q_TEXT);
                String nameText = c.getString(nameIndex);
                mainInfo = new Pracs();
                mainInfo.setQuestionName(nameText);
                data.add(mainInfo);

            }
        }

        return data;
    }

    public void startButtonClick(Category category, Level level) {
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();
        session_start = dateFormat.format(date);

        questions = databaseHelper.getQuestionsOfLevelAndCategory(level.getLevelId(), category.getId());
        answers = new ArrayList<Answer>(questions.size());
        for (int i = 0; i < questions.size(); i++) {
            answers.add(databaseHelper.getAnswerOfQuestion(questions.get(i)));
        }
        Intent in = new Intent(PrepareQuestionsActivity.this, GamePlayActivity.class);
        in.putParcelableArrayListExtra("QUESTIONS", questions);
        in.putParcelableArrayListExtra("ANSWERS", answers);
        startActivity(in);
        finish();
    }

}
