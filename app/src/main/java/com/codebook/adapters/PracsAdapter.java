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
import com.codebook.fragments.PracsFragment;
import com.codebook.loaders.CustomFontLoader;
import com.codebook.models.Question;

import java.util.ArrayList;


/**
 * Created by Narayan Acharya on 09/12/2015.
 */
public class PracsAdapter extends BaseAdapter {

    Context context;
    ArrayList<Question> question;

    PracsClickListner commander;


    public interface PracsClickListner {
        void onClick(Question question);
    }

    public PracsAdapter(PracsFragment fragment, ArrayList<Question> question) {
        this.context = fragment.getActivity();
        commander = (PracsClickListner) fragment;
        this.question = question;
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

        // setup the triangle
        // triangleFrameLayout.setBackgroundColor(Color.argb(255, 0, (int) (Math.random() * 256), (int) (Math.random() * 256)));

        // setup the example TextView
        TextView textView = (TextView) convertView.findViewById(R.id.item_text);
        textView.setText(getItem(position).getQuestionText());
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
        return question.get(position).getQuestionId();
    }

    @Override
    public Question getItem(int position) {
        return question.get(position);
    }

    @Override
    public int getCount() {
        return question.size();
    }

}
