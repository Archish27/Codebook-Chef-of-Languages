package com.codebook.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.codebook.R;
import com.codebook.fragments.CategoryFragment;
import com.codebook.models.Category;
import com.codebook.persistence.DatabaseHelper;

import java.util.ArrayList;

public class CategoryActivity extends AppCompatActivity {
    private static final String TAG = "Main Activity";
    DatabaseHelper databaseHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            Window window = getWindow();
//            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
//            window.setStatusBarColor(getResources().getColor(R.color.dark_primary_color));
//        }
        setContentView(R.layout.activity_category);

        databaseHelper = DatabaseHelper.getDbInstance(this);
        //AsyncGetCategories getCategories = new AsyncGetCategories(this);
        //getCategories.execute();

        /*
        List<AsyncRandomQuestions> getRandomQuestions = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            getRandomQuestions.add(new AsyncRandomQuestions());
            getRandomQuestions.get(i).executeOnExecutor(AsyncTask.SERIAL_EXECUTOR);
        }
        */

        // AsyncGetQuestions getQuestions = new AsyncGetQuestions(this);
        // getQuestions.execute();
//        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
//        setSupportActionBar(myToolbar);
//        myToolbar.setTitleTextColor(getResources().getColor(R.color.text_color));

        attachCategoryFragment(databaseHelper.getAllCategories());
        Log.d(TAG, databaseHelper.getDatabaseName());
        Log.d(TAG, databaseHelper.getAllCategories().toString());

    }

    private void attachCategoryFragment(ArrayList<Category> categories) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.categoriesContainer, CategoryFragment.newInstance(categories))
                .commit();
    }

//    @Override
//    public boolean onPrepareOptionsMenu(final Menu menu) {
//        getMenuInflater().inflate(R.menu.menu_main, menu);
//        return super.onCreateOptionsMenu(menu);
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        switch (item.getItemId()) {
//            case R.id.action_stats:
//                // Remove dialog code and fire intent to StatsActivity
//
//                Intent in = new Intent(this, StatsActivity.class);
//                startActivity(in);
///*
//                // Create custom dialog object
//                final Dialog dialog = new Dialog(CategoryActivity.this);
//                dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
//                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//                dialog.setCancelable(true);
//
//                // Include dialog.xml file
//                dialog.setContentView(R.layout.stats_dialog);
//
//
//                //Grab the window of the dialog, and change the width
//                WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
//                Window window = dialog.getWindow();
//                lp.copyFrom(window.getAttributes());
//                //This makes the dialog take up the full width
//                lp.width = WindowManager.LayoutParams.MATCH_PARENT;
//                lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
//                window.setAttributes(lp);
//
//                // set values for custom dialog components - text, image and button
//                TextView attempted = (TextView) dialog.findViewById(R.id.tvAttempted);
//                TextView correct = (TextView) dialog.findViewById(R.id.tvCorrect);
//                TextView link = (TextView) dialog.findViewById(R.id.tvTechLink);
//
//                ((TextView) dialog.findViewById(R.id.tvStatsDialog)).setTypeface(CustomFontLoader.getTypeface(getApplicationContext(), CustomFontLoader.LATO_BOLD));
//                attempted.setTypeface(CustomFontLoader.getTypeface(getApplicationContext(), CustomFontLoader.LATO_BOLD));
//                correct.setTypeface(CustomFontLoader.getTypeface(getApplicationContext(), CustomFontLoader.LATO_BOLD));
//                ((TextView) dialog.findViewById(R.id.tvDD)).setTypeface(CustomFontLoader.getTypeface(getApplicationContext(), CustomFontLoader.LATO_REGULAR));
//                link.setTypeface(CustomFontLoader.getTypeface(getApplicationContext(), CustomFontLoader.LATO_REGULAR));
//
//                link.setMovementMethod(LinkMovementMethod.getInstance());
//                link.setText(Html.fromHtml(getResources().getString(R.string.link)));
//                attempted.setText("Attempted : " + databaseHelper.getAttemptedQuestionCount());
//                correct.setText("Correct : " + databaseHelper.getCorrectQuestionCount());
//
//                ImageView iv = (ImageView) dialog.findViewById(R.id.ivCloseDialog);
//                iv.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        dialog.dismiss();
//                    }
//                });
//                dialog.show();
//*/
//                return true;
//            case R.id.action_info:
//                Intent infoin = new Intent(this, InfoActivity.class);
//                startActivity(infoin);
//                return true;
//            case R.id.action_settings:
//                Intent settings = new Intent(this, SettingsActivity.class);
//                startActivity(settings);
//                return true;
//
//            default:
//                return super.onOptionsItemSelected(item);
//        }
//    }
}
