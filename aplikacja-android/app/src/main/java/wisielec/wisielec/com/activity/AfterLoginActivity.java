package wisielec.wisielec.com.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import wisielec.wisielec.com.R;
import wisielec.wisielec.com.domain.User;
import wisielec.wisielec.com.interfaces.Callback;
import wisielec.wisielec.com.repository.UserRepository;

public class AfterLoginActivity extends MainActivity {
    protected ImageView avatar;
    protected ImageView avatarBar;
    protected TextView username;
    protected TextView usernameBar;
    protected TextView rankLevelBar;
    protected TextView rankingPosition;
    protected TextView rankingPositionBar;
    protected TextView pointsAmountBar;
    protected Button settingsButton;
    protected Button logoutButton;

    protected Button howToPlayButton;
    protected Button playButton;

    protected UserRepository userRepository = UserRepository.getInstance();

    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_after_login);

        Intent intent = getIntent();
        user =  (User) intent.getSerializableExtra("user");

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        howToPlayButton = findViewById(R.id.howToPlayButton);
        playButton = findViewById(R.id.playButton);

        avatar = findViewById(R.id.avatarImageView);
        avatarBar = findViewById(R.id.avatarImageViewBar);

        username = findViewById(R.id.usernameTextView);
        usernameBar = findViewById(R.id.usernameTextViewBar);

        rankLevelBar = findViewById(R.id.rankLevelTextViewBar);

        rankingPosition = findViewById(R.id.rankingPositionTextView);
        rankingPositionBar = findViewById(R.id.rankingPositionTextViewBar);

        pointsAmountBar = findViewById(R.id.pointsAmountTextViewBar);

        settingsButton = findViewById(R.id.settingsButton);
        logoutButton = findViewById(R.id.logoutButton);

        onSettingsButtonClick();
        onLogoutButtonClick();
        onClickListeners();

        bindingDataWithLayout();

    }

    private void onClickListeners() {
        final View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (view.getId()) {
                    case R.id.howToPlayButton:
                        Intent intentHowToPlay = new Intent(getApplicationContext(), HowToPlayActivity.class);
                        startActivity(intentHowToPlay);
                        break;
                    case R.id.playButton:
                        Intent intentPlay = new Intent(getApplicationContext(), ChooseCountOfRoundsActivity.class);
                        startActivity(intentPlay);
                        break;
                    default:
                        break;
                }
            }
        };
        howToPlayButton.setOnClickListener(listener);
        playButton.setOnClickListener(listener);
    }


    private void onSettingsButtonClick() {
        settingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentEditAccount = new Intent(getApplicationContext(), EditAccountActivity.class);
                startActivity(intentEditAccount);
            }
        });
    }

    private void onLogoutButtonClick() {
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userRepository.logOut(new Callback() {
                    @Override
                    public void event() {
                        //TODO implement save state of user on this place

                        startActivity(new Intent(AfterLoginActivity.this, SignInActivity.class));
                        finish();
                    }
                });
                Toast.makeText(getApplicationContext(),"Wylogowano", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void bindingDataWithLayout(){
        if(!user.getAvatarURL().isEmpty()) {
            Picasso.get().load(user.getAvatarURL()).into(avatar);
            Picasso.get().load(user.getAvatarURL()).into(avatarBar);
        }
        username.setText(user.getUserName());
        usernameBar.setText(user.getUserName());
        rankingPosition.setText(user.getRankingPosition()+"");
        rankingPositionBar.setText(user.getRankingPosition()+"");
        rankLevelBar.setText(user.getRank());
        pointsAmountBar.setText(user.getPoints()+" pkt");

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
