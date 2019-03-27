package wisielec.wisielec.com.services;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.widget.Toast;

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
import wisielec.wisielec.com.interfaces.Callback;
import wisielec.wisielec.com.interfaces.OnGetDataListener;

/**
 * Created by Sebastian on 2018-01-17.
 */

public class UserService {
    private static final String TAG = "UserService";
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

    public void getUser(String UID, final OnGetDataListener listener) {
        listener.onStart();
        DatabaseReference userReference = firebaseDatabase.getReference("users/" + UID);

        userReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                listener.onSuccess(dataSnapshot);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                listener.onFailed(databaseError);
            }
        });
    }

    public void registerNewUser(final Context context, final User user, final Callback onSuccess) {
        firebaseAuth.createUserWithEmailAndPassword(user.getEmail(), user.getPassword())
                .addOnCompleteListener((Activity) context, task -> {
                    if (task.isSuccessful()) {
                        FirebaseUser authenticatedUser = firebaseAuth.getCurrentUser();
                        DatabaseReference databaseReference = firebaseDatabase.getReference();

                        user.setPassword("");
                        user.setId(authenticatedUser.getUid());

                        databaseReference.child("users").child(authenticatedUser.getUid()).setValue(user);
                        onSuccess.event();
                    }
                    else {
                        Log.w(TAG, "createUserWithEmail:failure", task.getException());
                    }
                });
    }

    public void loginUser(final Context context, final User user, final Callback onSuccess) {
        firebaseAuth.signInWithEmailAndPassword(user.getEmail(), user.getPassword())
                .addOnCompleteListener((Activity) context, task -> {
                    if (task.isSuccessful()) {
                        onSuccess.event();
                    }
                    else {
                        Log.w(TAG, "signInWithEmail:failure", task.getException());
                        Toast.makeText(context, "Logowanie użytkownika nie powiodło się: " + Objects.requireNonNull(task.getException()).getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    public void logOut(Callback onSuccess) {
        firebaseAuth.signOut();
        onSuccess.event();
    }

    public void updateUserName(String username) {
        final DatabaseReference databaseReference = firebaseDatabase.getReference().child("users");
        databaseReference.child(Objects.requireNonNull(firebaseAuth.getCurrentUser()).getUid()).child("userName").setValue(username);
    }

    public void updateAvatar(Uri imageURI,final IUsernameAndAvatarUpdateCallback callback) {
        String UID = FirebaseAuth.getInstance().getCurrentUser().getUid();
        String finalUploadPath = "avatars/" + UID + "/avatar.jpg";
        final StorageReference storageReference = FirebaseStorage.getInstance().getReference(finalUploadPath);
        final DatabaseReference databaseReference = firebaseDatabase.getReference().child("users");

        storageReference.putFile(imageURI).continueWithTask(task -> {
            if (!task.isSuccessful()) {
                throw task.getException();
            }
            return storageReference.getDownloadUrl();
        }).addOnSuccessListener(downloadUri -> {
            databaseReference.child(firebaseAuth.getCurrentUser().getUid()).child("avatarURL").setValue(downloadUri.toString());
            callback.onSuccess(true);
        });
    }

    public void getUserData(final IUsernameAndUserRankCallback callback) {
        final DatabaseReference databaseReference = firebaseDatabase.getReference().child("users");
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

    public void getUserDataOnce(final IUsernameAndUserRankCallback callback) {
        final DatabaseReference databaseReference = firebaseDatabase.getReference().child("users");
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

    public void removeUserAccount(final IRemoveUserCallback callback) {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        user.delete().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                callback.onSuccess();
            } else {
                callback.onFailed();
            }
        });
    }

    public interface IUsernameAndAvatarUpdateCallback {
        void onSuccess(boolean result);
    }

    public interface IUsernameAndUserRankCallback {
        void onSuccess(User user);
    }

    public interface IRemoveUserCallback {
        void onSuccess();
        void onFailed();
    }
}
