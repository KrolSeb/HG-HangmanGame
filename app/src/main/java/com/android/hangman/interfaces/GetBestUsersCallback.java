package com.android.hangman.interfaces;

import com.android.hangman.domain.User;

import java.util.ArrayList;


public interface GetBestUsersCallback {
    void onSuccess(ArrayList<User> userList);
}
