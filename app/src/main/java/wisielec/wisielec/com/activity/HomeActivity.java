package wisielec.wisielec.com.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import wisielec.wisielec.com.R;


public class HomeActivity extends AppCompatActivity {
    @BindView(R.id.buttonLogin)
    protected Button buttonLogin;
    @BindView(R.id.buttonRegistration)
    protected Button buttonRegistration;
    @BindView(R.id.buttonApplicationInformation)
    protected Button buttonApplicationInformation;
    @BindView(R.id.buttonExit)
    protected Button buttonExit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.buttonLogin)
    public void onButtonLoginClick() {
        Intent intentLogin = new Intent(HomeActivity.this, SignInActivity.class);
        startActivity(intentLogin);
        finish();
    }

    @OnClick(R.id.buttonRegistration)
    public void onButtonRegistrationClick() {
        Intent intentRegistration = new Intent(HomeActivity.this, SignUpActivity.class);
        startActivity(intentRegistration);
        finish();
    }

    @OnClick(R.id.buttonApplicationInformation)
    public void onButtonApplicationInformationClick() {
        Intent intentApplicationInformation = new Intent(HomeActivity.this, ApplicationInformationActivity.class);
        startActivity(intentApplicationInformation);
        finish();
    }

    private void closeApp() {
        Intent homeIntent = new Intent(Intent.ACTION_MAIN);
        homeIntent.addCategory(Intent.CATEGORY_HOME);
        homeIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(homeIntent);
    }

    @OnClick(R.id.buttonExit)
    public void onButtonExitClick() {
        closeApp();
    }

    @Override
    public void onBackPressed() {
        closeApp();
    }
    
}