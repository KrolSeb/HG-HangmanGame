package com.android.hangman.activity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.hangman.R;
import com.android.hangman.adapters.UsersRankingAdapter;
import com.android.hangman.domain.User;
import com.android.hangman.services.RankService;
import com.android.hangman.services.UserService;
import com.squareup.picasso.Picasso;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;


public class RankingActivity extends MainActivity {
    private UserService userService;
    private RankService rankService;
    private RecyclerView recyclerView;
    private UsersRankingAdapter usersRankingAdapter;

    @BindView(R.id.avatarImageView)
    protected ImageView avatar;
    @BindView(R.id.usernameTextView)
    protected TextView username;
    @BindView(R.id.rankPlaceValueTextView)
    protected TextView rankPlaceValueTextView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ranking);
        ButterKnife.bind(this);

        userService = UserService.getInstance();
        rankService = RankService.getInstance();

        getCurrentUserData();
        getUserRankingPosition();
        setRecyclerViewProperties();
        getBestUsersFromRanking();
    }

    private void getCurrentUserData(){
        userService.getUserData(this::bindingDataWithLayout);
    }

    private void bindingDataWithLayout(User user) {
        username.setText(String.valueOf(user.getUserName()));
        if (!user.getAvatarURL().isEmpty()) {
            Picasso.get().load(user.getAvatarURL()).fit().centerInside().into(avatar);
        }
        else {
            avatar.setImageResource(R.drawable.avatar);
        }
    }

    private void setRecyclerViewProperties() {
        recyclerView = findViewById(R.id.recycler_view_best_users);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private void getBestUsersFromRanking() {
        rankService.getBestUsersFromRanking(userList -> {
            usersRankingAdapter = new UsersRankingAdapter(RankingActivity.this, userList);
            recyclerView.setAdapter(usersRankingAdapter);
        });
    }

    private void getUserRankingPosition(){
        rankService.getUserRankingPosition(userRankingPosition -> rankPlaceValueTextView.setText(String.valueOf(userRankingPosition)));
    }
}