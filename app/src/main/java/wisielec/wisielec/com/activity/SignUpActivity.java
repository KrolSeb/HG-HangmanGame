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
import wisielec.wisielec.com.interfaces.UserRegisterCallback;
import wisielec.wisielec.com.services.EmailValidatorService;
import wisielec.wisielec.com.services.PasswordValidatorService;
import wisielec.wisielec.com.services.UserService;

public class SignUpActivity extends AbstractAccessActivity {
    private static final String TAG = "SignUpActivity";

    private static final String MESSAGE_EMPTY_EMAIL_AND_PASSWORD_INPUT = "Brak podanego e-maila i hasła";
    private static final String MESSAGE_EMPTY_EMAIL_INPUT = "Brak podanego e-maila";
    private static final String MESSAGE_EMPTY_PASSWORD_INPUT = "Brak podanego hasła";
    private static final String MESSAGE_INCORRECT_EMAIL = "Niepoprawny adres e-mail.";
    private static final String MESSAGE_INCORRECT_PASSWORD = "Niepoprawne hasło - musi zawierać co najmniej";
    private static final String MESSAGE_INCORRECT_PASSWORD_DETAILS = "8 znaków, 1 wielką i małą literę, 1 cyfrę oraz 1 znak specjalny.";
    private static final String MESSAGE_INCORRECT_EMAIL_AND_PASSWORD = "Niepoprawny e-mail i/lub hasło.";
    private static final String MESSAGE_SUCCESSFUL_REGISTER = "Pomyślnie utworzono konto.";

    private UserService userService = UserService.getInstance();
    private EmailValidatorService emailValidatorService;
    private PasswordValidatorService passwordValidatorService;

    @BindView(R.id.buttonRegistration)
    protected Button buttonRegistration;
    @BindView(R.id.emailInput)
    protected TextInputEditText emailInput;
    @BindView(R.id.passwordInput)
    protected TextInputEditText passwordInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        ButterKnife.bind(this);

        emailValidatorService = new EmailValidatorService();
        passwordValidatorService = new PasswordValidatorService();

        setInputCursorPositions();
    }

    private void setInputCursorPositions() {
        emailInput.setSelection(0);
        passwordInput.setSelection(0);
    }

    @OnClick(R.id.buttonLogin)
    public void onButtonLoginClick(){
        Intent intentLogin = new Intent(SignUpActivity.this, SignInActivity.class);
        startActivity(intentLogin);
    }

    @OnClick(R.id.buttonRegistration)
    public void onButtonRegistrationClick(){
        registerUser();
    }

    private void registerUser() {
        String email = emailInput.getText().toString();
        String password = passwordInput.getText().toString();

        if (email.isEmpty() || password.isEmpty()){
            showNotificationIfEmptyInput(email,password);
        }
        else if(emailValidatorService.validate(emailInput) && passwordValidatorService.validate(passwordInput)) {
            User user = new User(email, password);
            userService.registerNewUser(SignUpActivity.this, user, new UserRegisterCallback() {
                @Override
                public void onSuccess() {
                    showToast(MESSAGE_SUCCESSFUL_REGISTER,Toast.LENGTH_SHORT);
                    changeToAfterLoginActivity();
                }

                @Override
                public void onFailed(String registerFailedMessage) {
                    showToast(registerFailedMessage,Toast.LENGTH_SHORT);
                }
            });
        }
        else {
            showIncorrectDataNotification();
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

    private void showIncorrectDataNotification() {
        if(!emailValidatorService.validate(emailInput)){
            showToast(MESSAGE_INCORRECT_EMAIL,Toast.LENGTH_LONG);
        }
        else if(!passwordValidatorService.validate(passwordInput)){
            showToast(MESSAGE_INCORRECT_PASSWORD,Toast.LENGTH_SHORT);
            showToast(MESSAGE_INCORRECT_PASSWORD_DETAILS,Toast.LENGTH_LONG);
        }
        else {
            showToast(MESSAGE_INCORRECT_EMAIL_AND_PASSWORD,Toast.LENGTH_LONG);
        }
    }

    private void showToast(String message,int length){
        Toast.makeText(SignUpActivity.this,message,length).show();
    }

    @Override
    public void onBackPressed() {
        Intent intentHomeActivity = new Intent(SignUpActivity.this, HomeActivity.class);
        startActivity(intentHomeActivity);
        finish();
    }
}
