package wisielec.wisielec.com.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import wisielec.wisielec.com.R;
import wisielec.wisielec.com.domain.User;
import wisielec.wisielec.com.services.GameService;
import wisielec.wisielec.com.services.UserService;


public class SummaryActivity extends MainActivity {
    private static final String MESSAGE_END_GAME_BUTTON = "Aby wyjść z gry, kliknij przycisk Zakończ rozgrywkę";
    private static final String INTENT_KEY_SCORE = "score";
    private static final String INTENT_KEY_BONUS_POINTS = "bonusPoints";

    @BindView(R.id.avatarImageView)
    protected ImageView avatar;
    @BindView(R.id.usernameTextView)
    protected TextView usernameTextView;

    @BindView(R.id.scoreValueTextView)
    protected TextView scoreValueTextView;
    @BindView(R.id.bonusValueTextView)
    protected TextView bonusValueTextView;
    @BindView(R.id.gameFinalScoreValueTextView)
    protected TextView finalScoreValueTextView;

    @BindView(R.id.userPointsBeforeGameTextView)
    protected TextView userPointsBeforeGameTextView;
    @BindView(R.id.userRankBeforeGameTextView)
    protected TextView userRankBeforeGameTextView;

    @BindView(R.id.userPointsAfterGameTextView)
    protected TextView userPointsAfterGameTextView;
    @BindView(R.id.userRankAfterGameTextView)
    protected TextView userRankAfterGameTextView;

    @BindView(R.id.endGameButton)
    protected Button endGameButton;

    private int score;
    private int bonusPoints;
    private int finalScore;

    private GameService gameService;
    private UserService userService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_summary);
        ButterKnife.bind(this);
        gameService = GameService.getInstance();
        userService = UserService.getInstance();

        getPreviousUserData();
        getDataFromIntent();
        updateUserData();
        getCurrentUserData();
    }

    private void getPreviousUserData() {
        userService.getUserDataOnce(this::bindPreviousUserData);
    }

    private void getDataFromIntent() {
        Intent playGameActivityIntent = getIntent();
        score = playGameActivityIntent.getIntExtra(INTENT_KEY_SCORE,0);
        bonusPoints = playGameActivityIntent.getIntExtra(INTENT_KEY_BONUS_POINTS,0);

        calculateFinalScore();
        bindDataFromIntent();
    }

    private void calculateFinalScore() {
        finalScore = score + bonusPoints;
    }

    private void updateUserData(){
        gameService.updateUserDataAfterGame(finalScore);
    }

    private void getCurrentUserData() {
        userService.getUserData(this::bindCurrentUserData);
    }

    private void bindPreviousUserData(User user) {
        if (!user.getAvatarURL().isEmpty()) {
            Picasso.get().load(user.getAvatarURL()).into(avatar);
        }
        else {
            avatar.setImageResource(R.drawable.avatar);
        }

        usernameTextView.setText(String.valueOf(user.getUserName()));
        userPointsBeforeGameTextView.setText(String.valueOf(user.getPoints()));
        userRankBeforeGameTextView.setText(String.valueOf(user.getRank()));
    }

    private void bindDataFromIntent() {
        scoreValueTextView.setText(String.valueOf(score));
        bonusValueTextView.setText(String.valueOf(bonusPoints));
        finalScoreValueTextView.setText(String.valueOf(finalScore));
    }

    private void bindCurrentUserData(User user) {
        userPointsAfterGameTextView.setText(String.valueOf(user.getPoints()));
        userRankAfterGameTextView.setText(String.valueOf(user.getRank()));
    }

    @OnClick(R.id.endGameButton)
    protected void onEndGameButtonClick(){
        Intent chooseCountOfRoundsIntent = new Intent(SummaryActivity.this,ChooseCountOfRoundsActivity.class);
        startActivity(chooseCountOfRoundsIntent);
        finish();
    }

    private void showToast() {
        Toast.makeText(SummaryActivity.this, MESSAGE_END_GAME_BUTTON, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onBackPressed() {
        showToast();
    }
}
