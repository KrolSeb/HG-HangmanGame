package com.android.hangman.interfaces;

public interface UserRegisterCallback {
    void onSuccess();
    void onFailed(String firebaseExceptionMessage);
}
