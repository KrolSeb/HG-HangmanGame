package wisielec.wisielec.com.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import wisielec.wisielec.com.R;

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

    protected void OnClickListeners() {
        final View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.buttonRegistration:
                        Context contextRegistration;
                        contextRegistration = getApplicationContext();
                        //Intent intentLogin = new Intent(contextLogin, SignInActivity.class);
                        //startActivity(intentLogin);
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
