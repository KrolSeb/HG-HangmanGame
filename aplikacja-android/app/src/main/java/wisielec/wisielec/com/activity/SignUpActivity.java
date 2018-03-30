package wisielec.wisielec.com.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.UserManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import wisielec.wisielec.com.R;
import wisielec.wisielec.com.domain.User;
import wisielec.wisielec.com.repository.UserRepository;

/**
 * Created by X on 2018-01-18.
 */

public class SignUpActivity extends MainActivity {
    private static final String TAG = "SignUpActivity";
    Button buttonCancel;
    Button buttonRegistration;
    EditText emailInput;
    EditText passwordInput;
    EditText repeatPasswordInput;

    private UserRepository userRepository = new UserRepository();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        buttonCancel = (Button) findViewById(R.id.buttonCancel);
        buttonRegistration = (Button) findViewById(R.id.buttonRegistration);
        emailInput = (EditText) findViewById(R.id.emailInput);
        passwordInput = (EditText) findViewById(R.id.passwordInput);
        repeatPasswordInput = (EditText) findViewById(R.id.repeatPasswordInput);
        OnClickListeners();


    }

    private boolean isFormValid() {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(emailInput.getText().toString()).matches() && repeatPasswordInput.getText().toString().equals(passwordInput.getText().toString());
    }

    protected void OnClickListeners() {
        final View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.buttonRegistration:
                        User user = new User();

                        if(!isFormValid()) return;
                        user.setEmail(emailInput.getText().toString());
                        user.setPassword(passwordInput.getText().toString());
                        userRepository.registerNewUser(SignUpActivity.this, user);
                        break;
                    case R.id.buttonCancel:
                        Context contextCancel;
                        contextCancel = getApplicationContext();
                        Intent intentCancel = new Intent(contextCancel, HomeActivity.class);
                        startActivity(intentCancel);
                        break;

                    default:
                        break;
                }
            }
        };
        buttonRegistration.setOnClickListener(listener);
        buttonCancel.setOnClickListener(listener);
    }
}
