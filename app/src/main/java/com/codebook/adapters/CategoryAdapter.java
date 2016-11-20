package com.codebook.adapters;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.codebook.R;
import com.codebook.loaders.CustomFontLoader;
import com.codebook.models.Category;

import java.util.ArrayList;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.VerticalItemHolder> {

    private ArrayList<Category> mItems;
    private AdapterView.OnItemClickListener mOnItemClickListener;

    public CategoryAdapter(ArrayList<Category> categories) {
        Log.d("CategoryAdapter", "Constructor");
        mItems = categories;
    }

    @Override
    public VerticalItemHolder onCreateViewHolder(ViewGroup container, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(container.getContext());
        View root = inflater.inflate(R.layout.adapter_category, container, false);
        return new VerticalItemHolder(root, this);
    }

    @Override
    public void onBindViewHolder(VerticalItemHolder itemHolder, int position) {
        Category item = mItems.get(position);
        itemHolder.setCategoryName(String.valueOf(item.getName()));
        setCategoryIconAndBackground(itemHolder, item);
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    private void setCategoryIconAndBackground(VerticalItemHolder itemHolder, Category item) {
        switch (item.getId()) {
            case 1:
                itemHolder.setCategoryIcon(R.drawable.c);
                itemHolder.setCategoryBackground(R.drawable.basic_bg);
                break;
            case 2:
                itemHolder.setCategoryIcon(R.drawable.cplusplus);
                itemHolder.setCategoryBackground(R.drawable.history_bg);
                break;
            case 3:
                itemHolder.setCategoryIcon(R.drawable.java);
                itemHolder.setCategoryBackground(R.drawable.economics_bg);
                break;
            case 4:
                itemHolder.setCategoryIcon(R.drawable.php);
                itemHolder.setCategoryBackground(R.drawable.literature_bg);
                break;

        }
    }

    public void setOnItemClickListener(AdapterView.OnItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }

    private void onItemHolderClick(VerticalItemHolder itemHolder) {
        if (mOnItemClickListener != null) {
            mOnItemClickListener.onItemClick(null, itemHolder.itemView,
                    itemHolder.getAdapterPosition(), itemHolder.getItemId());
        }
    }

    public static class VerticalItemHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        FrameLayout container;
        private TextView categoryName;
        private ImageView categoryIcon;
        private ImageView categoryBackground;

        private CategoryAdapter mAdapter;

        public VerticalItemHolder(View itemView, CategoryAdapter adapter) {
            super(itemView);
            itemView.setOnClickListener(this);

            mAdapter = adapter;
            container = (FrameLayout) itemView.findViewById(R.id.categoryAdapterContainer);
            categoryName = (TextView) itemView.findViewById(R.id.tvCatName);
            categoryName.setTypeface(CustomFontLoader.getTypeface(container.getContext(), CustomFontLoader.RALEWAY_REGULAR));
            categoryIcon = (ImageView) itemView.findViewById(R.id.ivIconChar);
            categoryBackground = (ImageView) itemView.findViewById(R.id.categoryBackground);
        }

        @Override
        public void onClick(View v) {
            mAdapter.onItemHolderClick(this);
        }

        public void setCategoryName(String name) {
            categoryName.setText(name);
        }

        public void setCategoryIcon(int iconId) {
            categoryIcon.setImageResource(iconId);
        }

        public void setCategoryBackground(int id) {
            categoryBackground.setBackgroundResource(id);
        }

    }

}
