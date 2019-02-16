package wisielec.wisielec.com.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import wisielec.wisielec.com.R;
import wisielec.wisielec.com.domain.User;
import wisielec.wisielec.com.services.UserService;

public class AfterLoginActivity extends MainActivity {
    /**
     * ActivityLoginElements
     * TextViews
     */
    @BindView(R.id.usernameTextView)
    TextView username;
    @BindView(R.id.rankingPositionTextView)
    TextView rankingPosition;
    @BindView(R.id.firstUserPositionTextView)
    TextView firstUserFromRanking;
    @BindView(R.id.firstUserPointsTextView)
    TextView firstUserPoints;
    @BindView(R.id.secondUserPositionTextView)
    TextView secondUserFromRanking;
    @BindView(R.id.secondUserPointsTextView)
    TextView secondUserPoints;
    @BindView(R.id.thirdUserPositionTextView)
    TextView thirdUserFromRanking;
    @BindView(R.id.thirdUserPointsTextView)
    TextView thirdUserPoints;

    /**
     * ActivityLoginElements
     * Buttons
     */
    @BindView(R.id.playButton)
    Button playButton;
    @BindView(R.id.howToPlayButton)
    Button howToPlayButton;
    @BindView(R.id.toRankListButton)
    Button toRankListButton;

    /**
     * ActivityLoginElements
     * Images
     */
    @BindView(R.id.avatarImageView)
    ImageView avatar;

    /**
     * UserPanelBarElements
     * TextViews
     */
    @BindView(R.id.usernameTextViewBar)
    TextView usernameBar;
    @BindView(R.id.rankingPositionTextViewBar)
    TextView rankingPositionBar;
    @BindView(R.id.rankLevelTextViewBar)
    TextView rankLevelBar;
    @BindView(R.id.pointsAmountTextViewBar)
    TextView pointsAmountBar;

    /**
     * UserPanelBarElements
     * Buttons
     */
    @BindView(R.id.settingsButton)
    Button settingsButton;
    @BindView(R.id.logoutButton)
    Button logoutButton;

    /**
     * UserPanelBarElements
     * Images
     */
    @BindView(R.id.avatarImageViewBar)
    ImageView avatarBar;


    protected UserService userService = UserService.getInstance();
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_after_login);
        ButterKnife.bind(this);

        Intent intent = getIntent();
        user = (User) intent.getSerializableExtra("user");

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();


        bindingDataWithLayout();

        userService.getBestUsersFromRanking(new UserService.IBestUserCallback() {
            @Override
            public void onSuccess(List<User> userList) {
                User user;
                String userName;




                user = userList.get(0);
                userName = (user.getUserName().isEmpty()) ? user.getEmail() : user.getUserName();
                firstUserFromRanking.setText(String.valueOf(userName));
                firstUserPoints.setText(String.valueOf(user.getPoints()));

                user = userList.get(1);
                userName = (user.getUserName().isEmpty()) ? user.getEmail() : user.getUserName();
                secondUserFromRanking.setText(String.valueOf(userName));
                secondUserPoints.setText(String.valueOf(user.getPoints()));

                user = userList.get(2);
                userName = (user.getUserName().isEmpty()) ? user.getEmail() : user.getUserName();
                thirdUserFromRanking.setText(String.valueOf(userName));
                thirdUserPoints.setText(String.valueOf(user.getPoints()));
            }
        });
    }

    @OnClick(R.id.howToPlayButton)
    public void onHowToPlayButtonClick() {
        Intent intentHowToPlay = new Intent(getApplicationContext(), HowToPlayActivity.class);
        startActivity(intentHowToPlay);
    }

    @OnClick(R.id.playButton)
    public void onPlayGameButtonClick() {
        Intent intentPlay = new Intent(getApplicationContext(), ChooseCountOfRoundsActivity.class);
        startActivity(intentPlay);
    }

    @OnClick(R.id.toRankListButton)
    public void onToRankListButtonClick() {
        Intent intentRankList = new Intent(getApplicationContext(), RankingActivity.class);
        startActivity(intentRankList);
    }

    @OnClick(R.id.settingsButton)
    public void onSettingsButtonClick() {
        Intent intentEditAccount = new Intent(getApplicationContext(), EditAccountActivity.class);
        intentEditAccount.putExtra("user", user);
        startActivity(intentEditAccount);
    }

    @OnClick(R.id.logoutButton)
    public void onLogoutButtonClick() {
        startActivity(new Intent(AfterLoginActivity.this, SignInActivity.class));
        finish();
    }

    private void bindingDataWithLayout() {
        if (!user.getAvatarURL().isEmpty()) {
            Picasso.get().load(user.getAvatarURL()).into(avatar);
            Picasso.get().load(user.getAvatarURL()).into(avatarBar);
        }
        username.setText(user.getUserName());
        usernameBar.setText(user.getUserName());
        rankingPosition.setText(user.getRankingPosition() + "");
        rankingPositionBar.setText(user.getRankingPosition() + "");
        rankLevelBar.setText(user.getRank());
        pointsAmountBar.setText(user.getPoints() + " pkt");
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
}
