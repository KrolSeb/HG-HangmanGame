package wisielec.wisielec.com.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import com.squareup.picasso.Picasso;

import wisielec.wisielec.com.R;
import wisielec.wisielec.com.domain.User;
import static wisielec.wisielec.com.repository.UserRepository.retrieveUserFromDatabase;

public class AfterLoginActivity extends MainActivity {
    protected ImageView avatar;
    protected TextView username;
    protected TextView rankLevel;
    protected TextView rankingPosition;
    protected TextView pointsAmount;
    protected Button settingsButton;
    protected Button logoutButton;
    protected Button reloadButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_after_login);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        avatar = findViewById(R.id.avatarImageView);
        username = findViewById(R.id.usernameTextView);
        rankLevel = findViewById(R.id.rankLevelTextView);
        rankingPosition = findViewById(R.id.rankingPositionTextView);
        pointsAmount = findViewById(R.id.pointsAmountTextView);
        settingsButton = findViewById(R.id.settingsButton);
        logoutButton = findViewById(R.id.logoutButton);
        reloadButton = findViewById(R.id.reloadButton);

        onSettingsButtonClick();
        onLogoutButtonClick();
        //directDataToTextViews();

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
                //Toast.makeText(getApplicationContext(),"Wylogowanie",Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void directDataToTextViews() {
        reloadButton.setSoundEffectsEnabled(false);
        reloadButton.setVisibility(View.GONE);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                reloadButton.performClick();
            }
        }, 1000);
        //getUserDataFromDatabase();
    }


    private void getUserDataFromDatabase() {
        final User retrievedUser = retrieveUserFromDatabase();
        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.reloadButton:
                        //Biblioteka do prostego dodawania obrazka z URL
                        Picasso.get().load(retrievedUser.getAvatarURL()).into(avatar);
                        username.setText(retrievedUser.getUsername());
                        rankingPosition.setText(Integer.toString(retrievedUser.getRankingPosition()));
                        rankLevel.setText(retrievedUser.getRank());
                        pointsAmount.setText(Long.toString(retrievedUser.getPoints()));
                        break;
                    default:
                        break;
                }
            }
        };
        reloadButton.setOnClickListener(listener);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }
        else {
            super.onBackPressed();
        }
    }
}
