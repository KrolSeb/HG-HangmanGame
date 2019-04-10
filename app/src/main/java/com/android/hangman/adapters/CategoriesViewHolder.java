package com.android.hangman.adapters;

import android.view.View;
import android.widget.Button;

import com.android.hangman.R;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


class CategoriesViewHolder extends RecyclerView.ViewHolder  {
    private Button singleCategoryButton;

    CategoriesViewHolder(@NonNull View itemView) {
        super(itemView);
        singleCategoryButton= itemView.findViewById(R.id.singleCategoryButton);
    }

    Button getSingleCategoryButton() {
        return singleCategoryButton;
    }

}
