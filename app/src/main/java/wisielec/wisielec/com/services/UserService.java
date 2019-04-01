package wisielec.wisielec.com.services;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;

import com.google.firebase.auth.FirebaseAuth;
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
import wisielec.wisielec.com.domain.User;
import wisielec.wisielec.com.interfaces.GetUserRankAndUsernameCallback;
import wisielec.wisielec.com.interfaces.UpdateUserAvatarCallback;
import wisielec.wisielec.com.interfaces.UpdateUsernameCallback;
import wisielec.wisielec.com.interfaces.GetUserDataCallback;
import wisielec.wisielec.com.interfaces.UserLoginCallback;
import wisielec.wisielec.com.interfaces.UserLogoutCallback;
import wisielec.wisielec.com.interfaces.UserRegisterCallback;
import wisielec.wisielec.com.interfaces.UserRemoveCallback;


public class UserService {
    private static final String TAG = "UserService";
    private static final String MESSAGE_LOGIN_FAILED = "Hasło i/lub e-mail niepoprawne, spróbuj ponownie.";
    private static final String MESSAGE_REGISTER_FAILED = "Nie udało się zarejestrować, spróbuj ponownie.";
    private static final String USERS_CHILD_REFERENCE = "users";
    private static final String USERS_USERNAME_REFERENCE = "userName";
    private static final String USERS_AVATAR_REFERENCE = "avatarURL";
    private static final String AVATARS_CHILD_REFERENCE = "avatars";
    private static final String AVATARS_IMAGE_REFERENCE = "avatar.jpg";
    private static final String PATH_SEPARATOR = "/";

    private static UserService instance = null;
    private FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();

    private UserService() { }

    public static UserService getInstance() {
        if (instance == null){
            instance = new UserService();
        }
        return instance;
    }

    public void getUser(String UID, final GetUserDataCallback callback) {
        callback.onStart();
        DatabaseReference userReference = firebaseDatabase.getReference( USERS_CHILD_REFERENCE + PATH_SEPARATOR + UID);

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
        firebaseAuth.createUserWithEmailAndPassword(user.getEmail(), user.getPassword())
                .addOnCompleteListener((Activity) context, task -> {
                    if (task.isSuccessful()) {
                        FirebaseUser authenticatedUser = firebaseAuth.getCurrentUser();
                        DatabaseReference databaseReference = firebaseDatabase.getReference();

                        user.setPassword("");
                        user.setId(authenticatedUser.getUid());
                        databaseReference.child(USERS_CHILD_REFERENCE).child(authenticatedUser.getUid()).setValue(user);

                        callback.onSuccess();
                    }
                    else {
                        callback.onFailed(MESSAGE_REGISTER_FAILED);
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
                        callback.onFailed(MESSAGE_LOGIN_FAILED);
                    }
                });
    }

    public void logOut(UserLogoutCallback callback) {
        firebaseAuth.signOut();
        callback.onSuccess();
    }

    public void updateUserName(String username, final UpdateUsernameCallback callback) {
        final DatabaseReference databaseReference = firebaseDatabase.getReference().child(USERS_CHILD_REFERENCE);
        databaseReference.child(Objects.requireNonNull(firebaseAuth.getCurrentUser()).getUid()).child(USERS_USERNAME_REFERENCE).setValue(username).
                addOnSuccessListener(aVoid -> callback.onSuccess())
                .addOnFailureListener(e -> callback.onFailed());
    }

    public void updateAvatar(Uri imageURI,final UpdateUserAvatarCallback callback) {
        String userID = FirebaseAuth.getInstance().getCurrentUser().getUid();
        String finalUploadPath = AVATARS_CHILD_REFERENCE + PATH_SEPARATOR + userID + PATH_SEPARATOR + AVATARS_IMAGE_REFERENCE;

        final StorageReference storageReference = FirebaseStorage.getInstance().getReference(finalUploadPath);
        final DatabaseReference databaseReference = firebaseDatabase.getReference().child(USERS_CHILD_REFERENCE);

        storageReference.putFile(imageURI).continueWithTask(task -> {
            if (!task.isSuccessful()) {
                throw task.getException();
            }
            return storageReference.getDownloadUrl();
        }).addOnSuccessListener(downloadUri -> {
            databaseReference.child(userID).child(USERS_AVATAR_REFERENCE).setValue(downloadUri.toString());
            callback.onSuccess();
        }).addOnFailureListener(e -> callback.onFailed());
    }

    public void getUserData(final GetUserRankAndUsernameCallback callback) {
        final DatabaseReference databaseReference = firebaseDatabase.getReference().child(USERS_CHILD_REFERENCE);
        databaseReference.child(Objects.requireNonNull(firebaseAuth.getCurrentUser()).getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);
                callback.onSuccess(user);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) { }
        });
    }

    public void getUserDataOnce(final GetUserRankAndUsernameCallback callback) {
        final DatabaseReference databaseReference = firebaseDatabase.getReference().child(USERS_CHILD_REFERENCE);
        databaseReference.child(Objects.requireNonNull(firebaseAuth.getCurrentUser()).getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);
                callback.onSuccess(user);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) { }
        });
    }

    public void removeUserAccount(final UserRemoveCallback callback) {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        user.delete().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                callback.onSuccess();
            }
            else {
                callback.onFailed();
            }
        });
    }
}
