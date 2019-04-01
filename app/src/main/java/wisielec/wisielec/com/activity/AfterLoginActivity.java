package wisielec.wisielec.com.activity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.drawerlayout.widget.DrawerLayout;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import wisielec.wisielec.com.R;
import wisielec.wisielec.com.domain.User;
import wisielec.wisielec.com.services.RankService;
import wisielec.wisielec.com.services.UserService;


public class AfterLoginActivity extends MainActivity {
    private static final String MESSAGE_SUCCESSFUL_LOGOUT = "Wylogowano z aplikacji.";

    /**
     * ActivityLoginElements
     * TextViews
     */
    @BindView(R.id.usernameTextView)
    protected TextView username;
    @BindView(R.id.firstUserPositionTextView)
    protected TextView firstUserFromRanking;
    @BindView(R.id.firstUserPointsTextView)
    protected TextView firstUserPoints;
    @BindView(R.id.secondUserPositionTextView)
    protected TextView secondUserFromRanking;
    @BindView(R.id.secondUserPointsTextView)
    protected TextView secondUserPoints;
    @BindView(R.id.thirdUserPositionTextView)
    protected TextView thirdUserFromRanking;
    @BindView(R.id.thirdUserPointsTextView)
    protected TextView thirdUserPoints;

    /**
     * ActivityLoginElements
     * Images
     */
    @BindView(R.id.avatarImageView)
    protected ImageView avatar;

    /**
     * UserPanelBarElements
     * TextViews
     */
    @BindView(R.id.usernameTextViewBar)
    protected TextView usernameBar;
    @BindView(R.id.rankingPositionTextViewBar)
    protected TextView rankingPositionBar;
    @BindView(R.id.rankLevelTextViewBar)
    protected TextView rankLevelBar;
    @BindView(R.id.pointsAmountTextViewBar)
    protected TextView pointsAmountBar;

    /**
     * UserPanelBarElements
     * Images
     */
    @BindView(R.id.avatarImageViewBar)
    protected ImageView avatarBar;

    /**
     * UserService
     * Object used to do actions on current user.
     */
    private UserService userService;

    /**
     * RankService
     * Object used to get users ranking.
     */
    private RankService rankService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.app_bar_after_login);
        ButterKnife.bind(this);
        userService = UserService.getInstance();
        rankService = RankService.getInstance();

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        getCurrentUserData();
        getThreeBestUsersFromRanking();
    }

    private void getCurrentUserData(){
        userService.getUserData(this::bindingDataWithLayout);
    }

    private void getThreeBestUsersFromRanking(){
        rankService.getThreeBestUsersFromRanking(userList -> {
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
        });
    }

    private void bindingDataWithLayout(User user) {
        if (!user.getAvatarURL().isEmpty()) {
            Picasso.get().load(user.getAvatarURL()).into(avatar);
            Picasso.get().load(user.getAvatarURL()).into(avatarBar);
        }
        else {
            avatar.setImageResource(R.drawable.avatar);
            avatarBar.setImageResource(R.drawable.avatar);
        }

        username.setText(String.valueOf(user.getUserName()));
        usernameBar.setText(String.valueOf(user.getUserName()));
        rankLevelBar.setText(String.valueOf(user.getRank()));
        pointsAmountBar.setText(String.valueOf(user.getPoints() + " pkt"));
        bindUserRankingPosition();
    }

    private void bindUserRankingPosition(){
        rankService.getUserRankingPosition(userRankingPosition -> rankingPositionBar.setText(String.valueOf(userRankingPosition)));
    }

    @OnClick(R.id.howToPlayButton)
    public void onHowToPlayButtonClick() {
        Intent intentHowToPlay = new Intent(AfterLoginActivity.this, HowToPlayActivity.class);
        startActivity(intentHowToPlay);
    }

    @OnClick(R.id.playButton)
    public void onPlayGameButtonClick() {
        Intent  intentPlay = new Intent(AfterLoginActivity.this, ChooseCountOfRoundsActivity.class);
        startActivity(intentPlay);
    }

    @OnClick(R.id.toRankListButton)
    public void onToRankListButtonClick() {
        Intent intentRankList = new Intent(AfterLoginActivity.this, RankingActivity.class);
        startActivity(intentRankList);
    }

    @OnClick(R.id.settingsButton)
    public void onSettingsButtonClick() {
        Intent intentEditAccount = new Intent(AfterLoginActivity.this, EditAccountActivity.class);
        startActivity(intentEditAccount);
    }

    @OnClick(R.id.logoutButton)
    public void onLogoutButtonClick() {
        logoutOperations();
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder quitDialogBuilder = new AlertDialog.Builder(AfterLoginActivity.this);
        quitDialogBuilder.setTitle("Wyjście");
        quitDialogBuilder.setMessage("Czy chcesz się wylogować z aplikacji?");
        quitDialogBuilder.setPositiveButton("Tak", (dialog, which) -> logoutOperations());
        quitDialogBuilder.setNegativeButton("Cofnij", (dialog, which) -> dialog.dismiss());
        AlertDialog quitDialog = quitDialogBuilder.create();
        quitDialog.show();
    }

    private void logoutOperations(){
        Intent signInActivityIntent = new Intent(AfterLoginActivity.this,SignInActivity.class);
        userService.logOut(() -> {
            showToast(MESSAGE_SUCCESSFUL_LOGOUT,Toast.LENGTH_SHORT);
            startActivity(signInActivityIntent);
            finish();
        });
    }

    private void showToast(String message,int length){
        Toast.makeText(AfterLoginActivity.this,message,length).show();
    }
}
