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
import com.codebook.adapters.LevelAdapter;
import com.codebook.models.Category;
import com.codebook.models.Level;
import com.codebook.persistence.DatabaseHelper;


/**
 * Created by Narayan Acharya on 09/12/2015.
 */
public class LevelFragment extends Fragment implements LevelAdapter.LevelClickListner {

    Category category;
    LevelAdapter levelAdapter;
    DatabaseHelper databaseHelper;
    LevelClickListener commander;

    public interface LevelClickListener {
        void onClick(Level level);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            commander = (LevelClickListener) context;
        } catch (ClassCastException e) {
            e.printStackTrace();
        }
    }

    public static LevelFragment newInstance(Category category) {
        LevelFragment levelFragment = new LevelFragment();
        Bundle args = new Bundle();
        args.putParcelable(Level.TAG, category);
        levelFragment.setArguments(args);
        return levelFragment;
    }

    @Override
    public void onClick(Level level) {
        commander.onClick(level);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        category = getArguments().getParcelable(Level.TAG);
        return inflater.inflate(R.layout.fragment_levels, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        databaseHelper = DatabaseHelper.getDbInstance(getActivity());
        levelAdapter = new LevelAdapter(this, databaseHelper.getAllLevelsOfCategory(category.getId()));

        // populate the list
        LinearLayout list = (LinearLayout) view.findViewById(R.id.list);
        for (int i = 0; i < levelAdapter.getCount(); i++) {
            // generate the item View
            View item = levelAdapter.getView(i, null, list);
            list.addView(item);
        }
    }
}
