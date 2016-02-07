package com.codebook.adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.codebook.R;
import com.codebook.fragments.LevelFragment;
import com.codebook.loaders.CustomFontLoader;
import com.codebook.models.Level;
import com.codebook.views.LevelItemView;

import java.util.ArrayList;


/**
 * Created by Narayan Acharya on 09/12/2015.
 */
public class LevelAdapter extends BaseAdapter {

    Context context;
    ArrayList<Level> levels;

    LevelClickListner commander;

    public interface LevelClickListner {
        void onClick(Level level);
    }

    public LevelAdapter(LevelFragment fragment, ArrayList<Level> levels) {
        this.context = fragment.getActivity();
        commander = (LevelClickListner) fragment;
        this.levels = levels;
        this.levels.add(0, new Level(-1, -1, "", -1));
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.level_item, parent, false);
            if (position == 0) {
                convertView.setVisibility(View.INVISIBLE);
                return convertView;
            }
        }
        // determine whether it's a left or a right triangle
        LevelItemView.Align align =
                (position & 1) == 0 ? LevelItemView.Align.LEFT : LevelItemView.Align.RIGHT;

        // setup the triangle
        LevelItemView triangleFrameLayout = (LevelItemView) convertView.findViewById(R.id.root_triangle);
        triangleFrameLayout.setTriangleAlignment(align);
        switch (getItem(position).getLevelId()) {
            case 1:
                triangleFrameLayout.setBackgroundColor(Color.parseColor("#FFFFFF"));
                break;
            case 2:
                triangleFrameLayout.setBackgroundColor(Color.parseColor("#EBEBEB"));
                break;
            case 3:
                triangleFrameLayout.setBackgroundColor(Color.parseColor("#D7D7D7"));
                break;

        }
        // triangleFrameLayout.setBackgroundColor(Color.argb(255, 0, (int) (Math.random() * 256), (int) (Math.random() * 256)));

        // setup the example TextView
        TextView textView = (TextView) convertView.findViewById(R.id.item_text);
        textView.setText(getItem(position).getLevelName());
        textView.setTypeface(CustomFontLoader.getTypeface(context, CustomFontLoader.RALEWAY_REGULAR));
        textView.setGravity((position & 1) == 0 ? Gravity.LEFT : Gravity.RIGHT);

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                commander.onClick(getItem(position));
            }
        });

        return convertView;
    }

    @Override
    public long getItemId(int position) {
        return levels.get(position).getLevelId();
    }

    @Override
    public Level getItem(int position) {
        return levels.get(position);
    }

    @Override
    public int getCount() {
        return levels.size();
    }

}
