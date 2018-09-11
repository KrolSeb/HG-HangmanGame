package wisielec.wisielec.com.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import wisielec.wisielec.com.R;

/**
 * Created by X on 2018-01-17.
 */

public class HomeActivity extends MainActivity {

    Button buttonLogin;
    Button buttonRegistration;
    Button buttonExit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        buttonLogin = (Button) findViewById(R.id.buttonLogin);
        buttonRegistration = (Button) findViewById(R.id.buttonRegistration);
        buttonExit = (Button) findViewById(R.id.buttonExit);
        OnClickListeners();
    }

    protected void OnClickListeners() {
        final View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.buttonLogin:
                        Context contextLogin;
                        contextLogin = getApplicationContext();
                        Intent intentLogin = new Intent(contextLogin, SignInActivity.class);
                        startActivity(intentLogin);
                        finish();
                        break;
                    case R.id.buttonRegistration:
                        Context contextRegistration;
                        contextRegistration = getApplicationContext();
                        Intent intentRegistration = new Intent(contextRegistration, SignUpActivity.class);
                        startActivity(intentRegistration);
                        finish();
                        break;
                    case R.id.buttonExit:
                        Intent intentExit = new Intent(Intent.ACTION_MAIN);
                        intentExit.addCategory(Intent.CATEGORY_HOME);
                        intentExit.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intentExit);
                        finish();
                        break;
                    default:
                        break;
                }
            }
        };
        buttonLogin.setOnClickListener(listener);
        buttonRegistration.setOnClickListener(listener);
        buttonExit.setOnClickListener(listener);
    }

}
