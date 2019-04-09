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
    private static final String DIALOG_TITLE = "Wyjście";
    private static final String DIALOG_MESSAGE = "Czy chcesz się wylogować z aplikacji?";
    private static final String DIALOG_CONFIRM_ANSWER = "OK";
    private static final String DIALOG_DENY_ANSWER = "Cofnij";
    private static final String POINTS_ABBREVIATION = "pkt";
    private static final String SPACE = " ";

    @BindView(R.id.avatarImageView)
    protected ImageView avatar;
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

    @BindView(R.id.avatarImageViewBar)
    protected ImageView avatarBar;
    @BindView(R.id.usernameTextViewBar)
    protected TextView usernameBar;
    @BindView(R.id.rankingPositionTextViewBar)
    protected TextView rankingPositionBar;
    @BindView(R.id.rankLevelTextViewBar)
    protected TextView rankLevelBar;
    @BindView(R.id.pointsAmountTextViewBar)
    protected TextView pointsAmountBar;

    private UserService userService;
    private RankService rankService;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.app_bar_after_login);
        ButterKnife.bind(this);
        userService = UserService.getInstance();
        rankService = RankService.getInstance();

        setNavigationDrawerProperties();
        getCurrentUserData();
        getThreeBestUsersFromRanking();
    }

    private void setNavigationDrawerProperties() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
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
        pointsAmountBar.setText(String.valueOf(user.getPoints() + SPACE + POINTS_ABBREVIATION));
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
        quitDialogBuilder.setTitle(DIALOG_TITLE);
        quitDialogBuilder.setMessage(DIALOG_MESSAGE);
        quitDialogBuilder.setPositiveButton(DIALOG_CONFIRM_ANSWER, (dialog, which) -> logoutOperations());
        quitDialogBuilder.setNegativeButton(DIALOG_DENY_ANSWER, (dialog, which) -> dialog.dismiss());
        AlertDialog quitDialog = quitDialogBuilder.create();
        quitDialog.show();
    }

    private void logoutOperations(){
        Intent signInActivityIntent = new Intent(AfterLoginActivity.this,SignInActivity.class);
        userService.logOut(() -> {
            startActivity(signInActivityIntent);
            finish();
            showToast();
        });
    }

    private void showToast(){
        Toast.makeText(AfterLoginActivity.this, MESSAGE_SUCCESSFUL_LOGOUT, Toast.LENGTH_SHORT).show();
    }
}
