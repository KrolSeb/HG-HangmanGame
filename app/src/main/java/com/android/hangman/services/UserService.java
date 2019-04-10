package com.android.hangman.services;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.util.Log;

import com.android.hangman.domain.User;
import com.android.hangman.interfaces.GetUserDataCallback;
import com.android.hangman.interfaces.GetUserRankAndUsernameCallback;
import com.android.hangman.interfaces.UpdateUserAvatarCallback;
import com.android.hangman.interfaces.UpdateUsernameCallback;
import com.android.hangman.interfaces.UserLoginCallback;
import com.android.hangman.interfaces.UserLogoutCallback;
import com.android.hangman.interfaces.UserRegisterCallback;
import com.android.hangman.interfaces.UserRemoveCallback;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.Objects;

import androidx.annotation.NonNull;


public class UserService {
    private static final String TAG = "UserService";
    private static final String USERS_CHILD_REFERENCE = "users";
    private static final String USERS_USERNAME_REFERENCE = "userName";
    private static final String USERS_AVATAR_REFERENCE = "avatarURL";
    private static final String AVATARS_CHILD_REFERENCE = "avatars";
    private static final String AVATARS_IMAGE_REFERENCE = "avatar.jpg";
    private static final String PATH_SEPARATOR = "/";
    private static final String EMPTY_STRING = "";

    private static UserService instance = null;
    private final FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    private final FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();

    private UserService() { }

    public static UserService getInstance() {
        if (instance == null) {
            instance = new UserService();
        }
        return instance;
    }

    public void getUser(String UID, final GetUserDataCallback callback) {
        callback.onStart();
        DatabaseReference userReference = firebaseDatabase.getReference(USERS_CHILD_REFERENCE + PATH_SEPARATOR + UID);

        userReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                callback.onSuccess(dataSnapshot);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                callback.onFailed(databaseError);
            }
        });
    }

    public void registerNewUser(final Context context, final User user, final UserRegisterCallback callback) {
        final String MESSAGE_REGISTER_MAIL_EXISTS = "Dla podanego adresu e-mail konto już istnieje.";
        final String MESSAGE_REGISTER_FAILED = "Nie udało się zarejestrować, spróbuj ponownie.";

        firebaseAuth.createUserWithEmailAndPassword(user.getEmail(), user.getPassword())
                .addOnCompleteListener((Activity) context, task -> {
                    if (task.isSuccessful()) {
                        FirebaseUser authenticatedUser = firebaseAuth.getCurrentUser();
                        DatabaseReference databaseReference = firebaseDatabase.getReference();

                        user.setPassword(EMPTY_STRING);
                        user.setId(Objects.requireNonNull(authenticatedUser).getUid());

                        databaseReference.child(USERS_CHILD_REFERENCE).child(authenticatedUser.getUid()).setValue(user);
                        callback.onSuccess();
                    }
                    else {
                        try {
                            throw Objects.requireNonNull(task.getException());
                        }
                        catch (FirebaseAuthUserCollisionException existEmail) {
                            callback.onFailed(MESSAGE_REGISTER_MAIL_EXISTS);
                        }
                        catch (Exception e) {
                            callback.onFailed(MESSAGE_REGISTER_FAILED);
                            e.printStackTrace();
                        }
                    }
                });
    }

    public void loginUser(final Context context, final User user, final UserLoginCallback callback) {
        firebaseAuth.signInWithEmailAndPassword(user.getEmail(), user.getPassword())
                .addOnCompleteListener((Activity) context, task -> {
                    if (task.isSuccessful()) {
                        callback.onSuccess();
                    }
                    else {
                        callback.onFailed();
                    }
                });
    }

    public void logOut(UserLogoutCallback callback) {
        firebaseAuth.signOut();
        callback.onSuccess();
    }

    public void updateUserName(String username, final UpdateUsernameCallback callback) {
        final DatabaseReference databaseReference = firebaseDatabase.getReference().child(USERS_CHILD_REFERENCE);
        databaseReference.child(Objects.requireNonNull(firebaseAuth.getCurrentUser()).getUid())
                .child(USERS_USERNAME_REFERENCE).setValue(username)
                .addOnSuccessListener(aVoid -> callback.onSuccess())
                .addOnFailureListener(e -> callback.onFailed());
    }

    public void updateAvatar(Uri imageURI, final UpdateUserAvatarCallback callback) {
        final String userID = Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid();
        final String finalUploadPath = AVATARS_CHILD_REFERENCE + PATH_SEPARATOR + userID + PATH_SEPARATOR + AVATARS_IMAGE_REFERENCE;

        final StorageReference storageReference = FirebaseStorage.getInstance().getReference(finalUploadPath);
        final DatabaseReference databaseReference = firebaseDatabase.getReference().child(USERS_CHILD_REFERENCE);

        storageReference.putFile(imageURI).continueWithTask(task -> {
            if (!task.isSuccessful()) {
                throw Objects.requireNonNull(task.getException());
            }
            return storageReference.getDownloadUrl();
        }).addOnSuccessListener(downloadUri -> {
                databaseReference.child(userID).child(USERS_AVATAR_REFERENCE).setValue(downloadUri.toString());
                callback.onSuccess();
        }).addOnFailureListener(e -> callback.onFailed());
    }

    public void getUserData(final GetUserRankAndUsernameCallback callback) {
        final DatabaseReference databaseReference = firebaseDatabase.getReference().child(USERS_CHILD_REFERENCE);
        databaseReference.child(Objects.requireNonNull(firebaseAuth.getCurrentUser()).getUid())
                .addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                User user;
                if (dataSnapshot.exists()) {
                    user = dataSnapshot.getValue(User.class);
                }
                else {
                    user = new User();
                }
                callback.onSuccess(user);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.w(TAG, "getUserData:onCancelled", databaseError.toException());
            }
        });
    }

    public void getUserDataOnce(final GetUserRankAndUsernameCallback callback) {
        final DatabaseReference databaseReference = firebaseDatabase.getReference().child(USERS_CHILD_REFERENCE);
        databaseReference.child(Objects.requireNonNull(firebaseAuth.getCurrentUser()).getUid())
                .addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);
                callback.onSuccess(user);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.w(TAG, "getUserDataOnce:onCancelled", databaseError.toException());
            }
        });
    }

    public void removeUserAccount(final UserRemoveCallback callback) {
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        firebaseDatabase.getReference().child(USERS_CHILD_REFERENCE).child(Objects.requireNonNull(user).getUid())
                .removeValue()
                .addOnCompleteListener(removeUserDataTask -> {
                    if (removeUserDataTask.isSuccessful()) {
                        Objects.requireNonNull(user).delete().addOnCompleteListener(removeUserTask -> {
                            if (removeUserTask.isSuccessful()) {
                                callback.onSuccess();
                            }
                            else {
                                callback.onFailed();
                            }
                        });
                    }
                    else {
                        callback.onFailed();
                    }
                });
    }
}