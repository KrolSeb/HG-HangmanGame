package com.android.hangman.interfaces;

import com.android.hangman.domain.User;

import java.util.List;


public interface GetBestThreeUsersCallback {
    void onSuccess(List<User> userList);
}
