package com.android.hangman.activity;

import android.content.Intent;
import android.widget.Toast;

import com.android.hangman.interfaces.GetUserDataCallback;
import com.android.hangman.services.UserService;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;

import java.util.Objects;


public abstract class AbstractAccessActivity extends MainActivity {
    private static final String MESSAGE_AN_ERROR_OCCURED = "Wystąpił błąd: ";
    private final UserService userService = UserService.getInstance();

    void changeToAfterLoginActivity() {
        userService.getUser(Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid(), new GetUserDataCallback() {
            @Override
            public void onStart() { }

            @Override
            public void onSuccess(DataSnapshot data) {
                Intent intent = new Intent(AbstractAccessActivity.this, AfterLoginActivity.class);
                startActivity(intent);
                finish();
            }

            @Override
            public void onFailed(DatabaseError databaseError) {
                Toast.makeText(AbstractAccessActivity.this, MESSAGE_AN_ERROR_OCCURED + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
