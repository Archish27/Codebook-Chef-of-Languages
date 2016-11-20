package com.codebook.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.codebook.MainActivity;
import com.codebook.activity.SelectActivity;
import com.codebook.adapters.CategoryAdapter;
import com.codebook.models.Category;
import com.codebook.views.InsetDecoration;

import java.util.ArrayList;

/**
 * Created by Archish on 30/10/2015.
 */
public class CategoryFragment extends RecyclerFragment {

    public static final String TAG = "Category Fragment";
    ArrayList<Category> categories;

    public static CategoryFragment newInstance(ArrayList<Category> categories) {
        CategoryFragment fragment = new CategoryFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList(Category.TAG, categories);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView");
        categories = getArguments().getParcelableArrayList(Category.TAG);
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    protected RecyclerView.LayoutManager getLayoutManager() {
        return new GridLayoutManager(getActivity(), 1, GridLayoutManager.VERTICAL, false);
    }

    @Override
    protected RecyclerView.ItemDecoration getItemDecoration() {
        // Current inset decoration has no padding, explicitly set to 0
        return new InsetDecoration(getActivity());
    }

    @Override
    protected CategoryAdapter getAdapter() {
        Log.d(TAG, "in getAdapter");
        return new CategoryAdapter(categories);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent in = new Intent(getActivity(), SelectActivity.class);
        in.putExtra(Category.TAG, categories.get(position));
        Log.d(TAG, categories.get(position).getId() + "");
        startActivity(in);
    }
}
