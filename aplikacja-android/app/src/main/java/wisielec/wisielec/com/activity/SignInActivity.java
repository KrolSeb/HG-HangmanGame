package wisielec.wisielec.com.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import wisielec.wisielec.com.R;
import wisielec.wisielec.com.domain.User;
import wisielec.wisielec.com.repository.UserRepository;

/**
 * Created by X on 2018-01-18.
 */

public class SignInActivity extends MainActivity {

    Button buttonLogin;
    Button buttonRegistration;
    EditText emailInput;
    EditText passwordInput;
    private UserRepository userRepository = new UserRepository();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        buttonLogin = (Button) findViewById(R.id.buttonLogin);
        buttonRegistration = (Button) findViewById(R.id.buttonRegistration);
        emailInput = (EditText) findViewById(R.id.emailInput);
        passwordInput = (EditText) findViewById(R.id.passwordInput);
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
                        Intent intentLogin = new Intent(contextLogin, AfterLoginActivity.class);
                        startActivity(intentLogin);
                        break;
                    case R.id.buttonRegistration:
                        Context contextRegistration;
                        contextRegistration = getApplicationContext();
                        Intent intentRegistration = new Intent(contextRegistration, SignUpActivity.class);
                        startActivity(intentRegistration);
                        break;
                    default:
                        break;
                }
            }
        };
        buttonLogin.setOnClickListener(listener);
        buttonRegistration.setOnClickListener(listener);
    }
}
