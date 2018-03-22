package wisielec.wisielec.com.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.tasks.OnFailureListener;

import wisielec.wisielec.com.R;

/**
 * Created by X on 2018-01-18.
 */

public class SignUpActivity extends MainActivity {

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

                //reCaptcha
                SafetyNet.getClient(this).verifyWithRecaptcha("6Lc0WE4UAAAAAOr4I0fcH6d7AM7ia1YkOt1xZTEJ")
                        .addOnSuccessListener(this, new OnSuccessListener<SafetyNetApi.RecaptchaTokenResponse>() {
                            @Override
                            public void onSuccess(SafetyNetApi.RecaptchaTokenResponse response) {
                                if (!response.getTokenResult().isEmpty()) {
                                    handleSiteVerify(response.getTokenResult());
                                }
                            }
                        })
                        .addOnFailureListener(this, new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                if (e instanceof ApiException) {
                                    ApiException apiException = (ApiException) e;
                                    Log.d(TAG, "Error message: " +
                                            CommonStatusCodes.getStatusCodeString(apiException.getStatusCode()));
                                } else {
                                    Log.d(TAG, "Unknown type of error: " + e.getMessage());
                                }
                            }
                        });
            }
        };
        buttonRegistration.setOnClickListener(listener);
        buttonCancel.setOnClickListener(listener);
    }
}
