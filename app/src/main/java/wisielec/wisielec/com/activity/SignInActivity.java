package wisielec.wisielec.com.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import wisielec.wisielec.com.R;
import wisielec.wisielec.com.domain.User;
import wisielec.wisielec.com.interfaces.UserLoginCallback;
import wisielec.wisielec.com.services.EmailValidatorService;
import wisielec.wisielec.com.services.UserService;


public class SignInActivity extends AbstractAccessActivity {
    private static final String MESSAGE_EMPTY_EMAIL_AND_PASSWORD_INPUT = "Brak podanego e-maila i hasła";
    private static final String MESSAGE_EMPTY_EMAIL_INPUT = "Brak podanego e-maila";
    private static final String MESSAGE_EMPTY_PASSWORD_INPUT = "Brak podanego hasła";
    private static final String MESSAGE_SUCCESSFUL_LOGIN = "Pomyślnie zalogowano.";

    private UserService userService;
    private EmailValidatorService emailValidatorService;

    @BindView(R.id.emailInput)
    protected TextInputEditText emailInput;
    @BindView(R.id.passwordInput)
    protected TextInputEditText passwordInput;
    @BindView(R.id.buttonLogin)
    protected Button buttonLogin;
    @BindView(R.id.buttonRegistration)
    protected Button buttonRegistration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        ButterKnife.bind(this);

        userService = UserService.getInstance();
        emailValidatorService = new EmailValidatorService();
    }

    @OnClick(R.id.buttonLogin)
    public void onButtonLoginClick(){
        signIn();
    }

    @OnClick(R.id.buttonRegistration)
    public void onButtonRegistrationClick(){
        Intent intentRegistration = new Intent(SignInActivity.this, SignUpActivity.class);
        startActivity(intentRegistration);
    }

    public void signIn() {
        String email = emailInput.getText().toString();
        String password = passwordInput.getText().toString();

        if (email.isEmpty() || password.isEmpty()){
            showNotificationIfEmptyInput(email,password);
        }
        else if(emailValidatorService.validate(emailInput)) {
            userService.loginUser(SignInActivity.this,
                    new User(Objects.requireNonNull(emailInput.getText()).toString(), Objects.requireNonNull(passwordInput.getText()).toString()), new UserLoginCallback() {
                        @Override
                        public void onSuccess() {
                            showToast(MESSAGE_SUCCESSFUL_LOGIN,Toast.LENGTH_SHORT);
                            changeToAfterLoginActivity();
                        }

                        @Override
                        public void onFailed(String loginFailedMessage) {
                            showToast(loginFailedMessage,Toast.LENGTH_LONG);
                        }
                    });
        }
    }

    private void showNotificationIfEmptyInput(String email, String password){
        if(email.isEmpty() && password.isEmpty()){
            showToast(MESSAGE_EMPTY_EMAIL_AND_PASSWORD_INPUT, Toast.LENGTH_SHORT);
        }
        else if (email.isEmpty()){
            showToast(MESSAGE_EMPTY_EMAIL_INPUT, Toast.LENGTH_SHORT);
        }
        else if (password.isEmpty()) {
            showToast(MESSAGE_EMPTY_PASSWORD_INPUT, Toast.LENGTH_SHORT);
        }
    }

    private void showToast(String message,int length){
        Toast.makeText(SignInActivity.this,message,length).show();
    }

    @Override
    public void onBackPressed() {
        Intent intentHomeActivity = new Intent(SignInActivity.this, HomeActivity.class);
        startActivity(intentHomeActivity);
        finish();
    }
}
