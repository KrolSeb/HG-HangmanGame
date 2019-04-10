package com.android.hangman.adapters;

import android.view.View;
import android.widget.TextView;

import com.android.hangman.R;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


public class UsersViewHolder extends RecyclerView.ViewHolder {
    private TextView usernameTextView;
    private TextView pointsTextView;

    UsersViewHolder(@NonNull View itemView) {
        super(itemView);
        usernameTextView= itemView.findViewById(R.id.usernameTextView);
        pointsTextView = itemView.findViewById(R.id.pointsTextView);
    }

    public TextView getUsernameTextView() {
        return usernameTextView;
    }

    TextView getPointsTextView() {
        return pointsTextView;
    }

}
