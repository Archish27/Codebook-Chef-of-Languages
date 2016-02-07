package com.codebook.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.codebook.R;
import com.codebook.activity.PracsActivity;
import com.codebook.loaders.CustomFontLoader;
import com.codebook.models.Pracs;
import com.codebook.persistence.DatabaseHelper;

import java.util.List;

/**
 * Created by Droid King on 21/07/2015.
 */
public class PracsNewAdapter extends RecyclerView.Adapter<PracsNewAdapter.MyMainViewHolder> {


    private List<Pracs> mainInfo;
    DatabaseHelper db;
    Context context;
    public PracsNewAdapter(Activity context,List<Pracs> mainInfo) {
        this.context=context;
        this.mainInfo = mainInfo;
    }

    public class MyMainViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView groupNameTextView, groupNotesTextView;
        ImageView groupImgFull;
        LinearLayout linearLayout;

        public MyMainViewHolder(View itemView) {
            super(itemView);
            linearLayout = (LinearLayout) itemView.findViewById(R.id.mainLayout);
            groupNameTextView = (TextView) itemView.findViewById(R.id.group_name_TextView);
        }

        @Override
        public void onClick(View v) {
            Log.d("MyItemClick", "onClick " + getAdapterPosition() + " " );
        }
    }


    @Override
    public MyMainViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.main_single_raw, parent, false);
        MyMainViewHolder holder = new MyMainViewHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(final MyMainViewHolder holder, final int position) {
        holder.groupNameTextView.setText(mainInfo.get(position).getQuestionName());
        holder.groupNameTextView.setTypeface(CustomFontLoader.getTypeface(context, CustomFontLoader.QUICKSAND_BOLD));

    }



    @Override
    public int getItemCount() {
        return mainInfo.size();
    }


}