package wisielec.wisielec.com.services;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import wisielec.wisielec.com.domain.User;
import wisielec.wisielec.com.interfaces.Callback;
import wisielec.wisielec.com.interfaces.OnGetDataListener;

/**
 * Created by Sebastian on 2018-01-17.
 */

public class UserService {
    private static final String TAG = "UserService";

    private FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();

    private static UserService instance = null;

    private UserService() {
    }

    public static UserService getInstance() {
        if (instance == null) instance = new UserService();
        return instance;
    }

    public void getUser(String UID, final OnGetDataListener listener) {
        listener.onStart();
        DatabaseReference userReference = firebaseDatabase.getReference("users/" + UID);

        userReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                listener.onSuccess(dataSnapshot);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                listener.onFailed(databaseError);
            }
        });
    }

    public void registerNewUser(final Context context, final User user, final Callback onSuccess) {
        firebaseAuth.createUserWithEmailAndPassword(user.getEmail(), user.getPassword())
                .addOnCompleteListener((Activity) context, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser authenticatedUser = firebaseAuth.getCurrentUser();
                            DatabaseReference databaseReference = firebaseDatabase.getReference();

                            user.setPassword("");
                            user.setId(authenticatedUser.getUid());

                            databaseReference.child("users").child(authenticatedUser.getUid()).setValue(user);
                            onSuccess.event();
                        } else {
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                        }
                    }
                });
    }

    public void loginUser(final Context context, final User user, final Callback onSuccess) {
        firebaseAuth.signInWithEmailAndPassword(user.getEmail(), user.getPassword()).addOnCompleteListener((Activity) context, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    onSuccess.event();
                } else {
                    Log.w(TAG, "signInWithEmail:failure", task.getException());
                    Toast.makeText(context, "Logowanie użytkownika nie powiodło się: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void logOut(Callback onSuccess) {
        firebaseAuth.signOut();
        onSuccess.event();
    }

    public void getBestUsersFromRanking(final IBestUserCallback callback) {
        final DatabaseReference databaseReference = firebaseDatabase.getReference();
        Query queryRef = databaseReference.child("users").orderByChild("points");

        queryRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<User> userList = new ArrayList<>();
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    User user = ds.getValue(User.class);
                    userList.add(user);
                }

                Collections.sort(userList,new Comparator<User>() {
                    @Override
                    public int compare(User user1, User user2) {
                        return user2.getPoints() - user1.getPoints();
                    }
                });
                callback.onSuccess(userList);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }


    public interface IBestUserCallback {
        void onSuccess(List<User> userList);
    }
}
