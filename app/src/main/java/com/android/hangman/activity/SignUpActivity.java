package com.android.hangman.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import com.android.hangman.R;
import com.android.hangman.domain.User;
import com.android.hangman.interfaces.UserRegisterCallback;
import com.android.hangman.services.EmailValidatorService;
import com.android.hangman.services.PasswordValidatorService;
import com.android.hangman.services.UserService;
import com.google.android.material.textfield.TextInputEditText;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class SignUpActivity extends AbstractAccessActivity {
    private static final String MESSAGE_REGISTER_SUCCESSFUL = "Pomyślnie zarejestrowano, logowanie...";
    private static final String MESSAGE_EMPTY_EMAIL_AND_PASSWORD_INPUT = "Brak podanego e-maila i hasła";
    private static final String MESSAGE_EMPTY_EMAIL_INPUT = "Brak podanego e-maila";
    private static final String MESSAGE_EMPTY_PASSWORD_INPUT = "Brak podanego hasła";
    private static final String MESSAGE_INCORRECT_EMAIL = "Niepoprawny adres e-mail.";
    private static final String MESSAGE_INCORRECT_PASSWORD = "Niepoprawne hasło - musi zawierać co najmniej";
    private static final String MESSAGE_INCORRECT_PASSWORD_DETAILS = "8 znaków, 1 wielką i małą literę, 1 cyfrę oraz 1 znak specjalny.";
    private static final String MESSAGE_INCORRECT_EMAIL_AND_PASSWORD = "Niepoprawny e-mail i/lub hasło.";

    private UserService userService;
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

        userService = UserService.getInstance();
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
        String email = String.valueOf(emailInput.getText());
        String password = String.valueOf(passwordInput.getText());

        if (email.isEmpty() || password.isEmpty()){
            showNotificationIfEmptyInput(email,password);
        }
        else if(emailValidatorService.validate(emailInput) && passwordValidatorService.validate(passwordInput)) {
            User user = new User(email, password);
            userService.registerNewUser(SignUpActivity.this, user, new UserRegisterCallback() {
                @Override
                public void onSuccess() {
                    changeToAfterLoginActivity();
                    showToast(MESSAGE_REGISTER_SUCCESSFUL,Toast.LENGTH_LONG);
                }

                @Override
                public void onFailed(String registerFailedMessage) {
                    showToast(registerFailedMessage,Toast.LENGTH_LONG);
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
