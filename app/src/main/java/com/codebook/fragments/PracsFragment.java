package com.codebook.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.codebook.R;
import com.codebook.adapters.PracsAdapter;
import com.codebook.models.Category;
import com.codebook.models.Level;
import com.codebook.models.Question;
import com.codebook.persistence.DatabaseHelper;


/**
 * Created by Narayan Acharya on 09/12/2015.
 */
public class PracsFragment extends Fragment implements PracsAdapter.PracsClickListner {

    PracsClickListener commander;

    @Override
    public void onClick(Question question) {
        commander.onClick(question);
    }

    public interface PracsClickListener {
        void onClick(Question level);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            commander = (PracsClickListener) context;
        } catch (ClassCastException e) {
            e.printStackTrace();
        }
    }

}
