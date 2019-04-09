package wisielec.wisielec.com.activity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import wisielec.wisielec.com.R;
import wisielec.wisielec.com.adapters.UsersRankingAdapter;
import wisielec.wisielec.com.domain.User;
import wisielec.wisielec.com.services.RankService;
import wisielec.wisielec.com.services.UserService;


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
            Picasso.get().load(user.getAvatarURL()).into(avatar);
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