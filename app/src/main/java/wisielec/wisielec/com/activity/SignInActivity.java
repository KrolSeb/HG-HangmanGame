package wisielec.wisielec.com.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import wisielec.wisielec.com.R;
import wisielec.wisielec.com.domain.User;
import wisielec.wisielec.com.interfaces.UserLoginCallback;
import wisielec.wisielec.com.services.EmailValidatorService;
import wisielec.wisielec.com.services.UserService;


public class SignInActivity extends AbstractAccessActivity {
    private static final String MESSAGE_LOGIN_SUCCESS = "Pomyślnie zalogowano";
    private static final String MESSAGE_LOGIN_FAILED = "Hasło i/lub e-mail niepoprawne, spróbuj ponownie.";
    private static final String MESSAGE_EMPTY_EMAIL_AND_PASSWORD_INPUT = "Brak podanego e-maila i hasła";
    private static final String MESSAGE_EMPTY_EMAIL_INPUT = "Brak podanego e-maila";
    private static final String MESSAGE_EMPTY_PASSWORD_INPUT = "Brak podanego hasła";

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
        loginUser();
    }

    @OnClick(R.id.buttonRegistration)
    public void onButtonRegistrationClick(){
        Intent intentRegistration = new Intent(SignInActivity.this, SignUpActivity.class);
        startActivity(intentRegistration);
    }

    public void loginUser() {
        String email = String.valueOf(emailInput.getText());
        String password = String.valueOf(passwordInput.getText());

        if (email.isEmpty() || password.isEmpty()){
            showNotificationIfEmptyInput(email,password);
        }
        else if(emailValidatorService.validate(emailInput)) {
            userService.loginUser(SignInActivity.this,
                    new User(email, password), new UserLoginCallback() {
                        @Override
                        public void onSuccess() {
                            changeToAfterLoginActivity();
                            showToast(MESSAGE_LOGIN_SUCCESS,Toast.LENGTH_LONG);
                        }

                        @Override
                        public void onFailed() {
                            showToast(MESSAGE_LOGIN_FAILED,Toast.LENGTH_LONG);
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
