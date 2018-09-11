package wisielec.wisielec.com.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import wisielec.wisielec.com.R;
import wisielec.wisielec.com.domain.User;
import wisielec.wisielec.com.interfaces.Callback;
import wisielec.wisielec.com.services.UserService;

/**
 * Created by X on 2018-01-18.
 */

public class SignInActivity extends AbstractAccessActivity {

    Button buttonLogin;
    Button buttonRegistration;
    EditText emailInput;
    EditText passwordInput;
    private UserService userService = UserService.getInstance();

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
                        signIn();
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

    public void signIn() {
        if (!isFormValid(emailInput)) return;
        userService.loginUser(SignInActivity.this, new User(emailInput.getText().toString(), passwordInput.getText().toString()), new Callback() {
            @Override
            public void event() {
                changeToAfterLoginActivity();
            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent intentHomeActivity = new Intent(SignInActivity.this, HomeActivity.class);
        startActivity(intentHomeActivity);
        finish();
    }
}
