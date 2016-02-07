package com.codebook.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.codebook.MainActivity;
import com.codebook.R;
import com.codebook.fragments.LevelFragment;
import com.codebook.loaders.CustomFontLoader;
import com.codebook.models.Category;
import com.codebook.models.Level;
import com.codebook.views.ParallaxScrollView;

/**
 * Created by Archish on 1/3/2016.
 */
public class SelectActivity extends AppCompatActivity implements LevelFragment.LevelClickListener {
    // CardView cd1,cd2,cd3,cd4,cd5,cd6,cd7,cd8;
    ImageView categoryIcon;
    TextView categoryName;
    Category category;
    ParallaxScrollView scrollView;
    LinearLayout catInfoContainer;

    @Override
    public void onClick(Level level) {
        Intent in = new Intent(this, PrepareQuestionsActivity.class);
        in.putExtra(Category.TAG, category);
        in.putExtra(Level.TAG, level);
        startActivity(in);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_levels);
        scrollView = (ParallaxScrollView) findViewById(R.id.levelsScrollView);
        catInfoContainer = (LinearLayout) findViewById(R.id.scrollCatInfoContainer);
        scrollView.setOnScrollChangedListener(new ParallaxScrollView.OnScrollChangedListener() {
            @Override
            public void onScrollChanged(int l, int t, int oldl, int oldt) {
                int scrollY = scrollView.getScrollY();
                catInfoContainer.setTranslationY(scrollY * 0.7f);
            }
        });

        categoryIcon = (ImageView) findViewById(R.id.ivCatIcon);
        categoryName = (TextView) findViewById(R.id.tvCatName);
        categoryName.setTypeface(CustomFontLoader.getTypeface(this, CustomFontLoader.RALEWAY_LIGHT));

        category = getIntent().getParcelableExtra(Category.TAG);
        if (category != null) {
            switch (category.getId()){
                case 1:
                    categoryIcon.setBackgroundResource(R.drawable.c);
                    scrollView.setBackgroundResource(R.drawable.basic_bg);
                    break;
                case 2:
                    categoryIcon.setBackgroundResource(R.drawable.cplusplus);
                    scrollView.setBackgroundResource(R.drawable.history_bg);
                    break;
                case 3:
                    categoryIcon.setBackgroundResource(R.drawable.java);
                    scrollView.setBackgroundResource(R.drawable.economics_bg);
                    break;

            }
            attachLevelsFragment(category);
            categoryName.setText(category.getName());
        }

//        try {
//            Thread.sleep(1000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//        setContentView(R.layout.categories);
//        cd1=(CardView)findViewById(R.id.card_view1);
//        cd2=(CardView)findViewById(R.id.card_view2);
//        cd3=(CardView)findViewById(R.id.card_view3);
//        cd4=(CardView)findViewById(R.id.card_view4);
//        cd5=(CardView)findViewById(R.id.card_view5);
//        cd6=(CardView)findViewById(R.id.card_view6);
//        cd7=(CardView)findViewById(R.id.card_view7);
//        cd8=(CardView)findViewById(R.id.card_view8);
//
//
//        YoYo.with(Techniques.SlideInLeft)
//                .duration(2100)
//                .playOn(findViewById(R.id.card_view));
//        YoYo.with(Techniques.SlideInRight)
//                .duration(2100)
//                .playOn(findViewById(R.id.card_view1));
//        YoYo.with(Techniques.SlideInRight)
//                .duration(2100)
//                .playOn(findViewById(R.id.card_view2));
//        YoYo.with(Techniques.ZoomInDown)
//                .duration(2100)
//                .playOn(findViewById(R.id.card_view3));
//        YoYo.with(Techniques.ZoomInLeft)
//                .duration(2100)
//                .playOn(findViewById(R.id.card_view4));
//        YoYo.with(Techniques.ZoomInLeft)
//                .duration(2100)
//                .playOn(findViewById(R.id.card_view5));
//        YoYo.with(Techniques.RotateInUpRight)
//                .duration(2100)
//                .playOn(findViewById(R.id.card_view6));
//        YoYo.with(Techniques.SlideInDown)
//                .duration(2100)
//                .playOn(findViewById(R.id.card_view7));
//        YoYo.with(Techniques.SlideInDown)
//                .duration(2100)
//                .playOn(findViewById(R.id.card_view8));
//
//        cd1.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                YoYo.with(Techniques.Tada)
//                        .duration(700)
//                        .playOn(findViewById(R.id.card_view1));
//
//
//            }
//        });
//        cd2.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                YoYo.with(Techniques.Tada)
//                        .duration(700)
//                        .playOn(findViewById(R.id.card_view2));
//
//
//            }
//        });
//        cd3.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                YoYo.with(Techniques.Tada)
//                        .duration(700)
//                        .playOn(findViewById(R.id.card_view3));
//
//
//            }
//        });
//        cd4.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                YoYo.with(Techniques.Tada)
//                        .duration(700)
//                        .playOn(findViewById(R.id.card_view4));
//
//
//            }
//        });
//        cd5.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                YoYo.with(Techniques.Tada)
//                        .duration(700)
//                        .playOn(findViewById(R.id.card_view5));
//
//
//            }
//        });
//        cd6.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                YoYo.with(Techniques.Tada)
//                        .duration(700)
//                        .playOn(findViewById(R.id.card_view6));
//
//
//            }
//        });
//        cd7.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                YoYo.with(Techniques.Tada)
//                        .duration(700)
//                        .playOn(findViewById(R.id.card_view7));
//
//
//            }
//        });
//        cd8.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                YoYo.with(Techniques.Tada)
//                        .duration(700)
//                        .playOn(findViewById(R.id.card_view8));
//
//
//            }
//        });

    }

    private void attachLevelsFragment(Category category) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.levels_container, LevelFragment.newInstance(category))
                .commit();
    }
}
