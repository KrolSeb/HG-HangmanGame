package wisielec.wisielec.com.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import wisielec.wisielec.com.R;
import wisielec.wisielec.com.domain.User;
import wisielec.wisielec.com.services.UserService;

/**
 * Created by X on 2018-01-18.
 */

public class SignUpActivity extends AbstractAccessActivity {
    private static final String TAG = "SignUpActivity";
    private Button buttonRegistration;
    private EditText emailInput;
    private EditText passwordInput;
    private EditText repeatPasswordInput;
    private UserService userService = UserService.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        buttonRegistration = (Button) findViewById(R.id.buttonRegistration);
        emailInput = (EditText) findViewById(R.id.emailInput);
        passwordInput = (EditText) findViewById(R.id.passwordInput);
        repeatPasswordInput = (EditText) findViewById(R.id.repeatPasswordInput);

        onClickListeners();
    }

    protected void onClickListeners() {
        final View.OnClickListener listener = v -> {
            switch (v.getId()) {
                case R.id.buttonRegistration:
                    User user = new User(emailInput.getText().toString(),passwordInput.getText().toString());
                    if(!(isFormValid(emailInput))) {
                        return;
                    }
                    userService.registerNewUser(SignUpActivity.this, user, this::changeToAfterLoginActivity);
                    break;
                default:
                    break;
            }
        };

        buttonRegistration.setOnClickListener(listener);
    }

    @Override
    public void onBackPressed() {
        Intent intentHomeActivity = new Intent(SignUpActivity.this, HomeActivity.class);
        startActivity(intentHomeActivity);
        finish();
    }
}
