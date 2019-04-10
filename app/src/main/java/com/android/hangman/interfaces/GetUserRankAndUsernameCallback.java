package com.android.hangman.interfaces;

import com.android.hangman.domain.User;

public interface GetUserRankAndUsernameCallback {
    void onSuccess(User user);
}
