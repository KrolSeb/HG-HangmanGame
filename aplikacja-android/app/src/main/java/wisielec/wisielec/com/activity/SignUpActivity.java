package wisielec.wisielec.com.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.CommonStatusCodes;
import com.google.android.gms.safetynet.SafetyNet;
import com.google.android.gms.safetynet.SafetyNetApi;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;

import java.util.concurrent.Executor;

import wisielec.wisielec.com.R;

/**
 * Created by X on 2018-01-18.
 */

public class SignUpActivity extends MainActivity {
    private static final String TAG = "SignUpActivity";
    Button buttonCancel;
    Button buttonRegistration;
    Button buttonCaptcha;
    EditText emailInput;
    EditText passwordInput;
    EditText repeatPasswordInput;

    Activity thisActivity = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        buttonCaptcha = (Button) findViewById(R.id.buttonCaptcha);
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
                        System.err.println("Rejestracja przycisk");
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

                    case R.id.buttonCaptcha:

                        System.err.println("Przycisk działa");
                        SafetyNet.getClient(thisActivity).verifyWithRecaptcha("6Lc0WE4UAAAAAOr4I0fcH6d7AM7ia1YkOt1xZTEJ")
                                .addOnSuccessListener((Executor) thisActivity,
                                        new OnSuccessListener<SafetyNetApi.RecaptchaTokenResponse>() {
                                            @Override
                                            public void onSuccess(SafetyNetApi.RecaptchaTokenResponse response) {
                                                // Indicates communication with reCAPTCHA service was
                                                // successful.
                                                String userResponseToken = response.getTokenResult();
                                                if (!userResponseToken.isEmpty()) {
                                                    // Validate the user response token using the
                                                    // reCAPTCHA siteverify API.
                                                    System.out.println("VERIFY OK!");
                                                }
                                            }
                                        })
                                .addOnFailureListener((Executor) thisActivity, new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        if (e instanceof ApiException) {
                                            // An error occurred when communicating with the
                                            // reCAPTCHA service. Refer to the status code to
                                            // handle the error appropriately.
                                            ApiException apiException = (ApiException) e;
                                            int statusCode = apiException.getStatusCode();
                                            Log.d(TAG, "Error: " + CommonStatusCodes
                                                    .getStatusCodeString(statusCode));
                                        } else {
                                            // A different, unknown type of error occurred.
                                            Log.d(TAG, "Error: " + e.getMessage());
                                        }
                                    }
                                });
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
