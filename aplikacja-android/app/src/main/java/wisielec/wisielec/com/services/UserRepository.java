package wisielec.wisielec.com.repository;

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

import java.util.TreeMap;

import wisielec.wisielec.com.domain.User;
import wisielec.wisielec.com.interfaces.Callback;
import wisielec.wisielec.com.interfaces.FirebaseCallback;
import wisielec.wisielec.com.interfaces.OnGetDataListener;

/**
 * Created by Sebastian on 2018-01-17.
 */

public class UserRepository {
    private static final String TAG = "UserRepository";

    private FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();

    private static UserRepository instance = null;
    private TreeMap<String, Integer> bestUsersMap = new TreeMap<>();
    private UserRepository() { }

    public static UserRepository getInstance() {
        if (instance == null) instance = new UserRepository();
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

                            //set user paremateres before save to database
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

    public void getBestUsersFromRanking(final FirebaseCallback firebaseCallback){
        final DatabaseReference databaseReference = firebaseDatabase.getReference();
        Query queryRef = databaseReference.child("users").orderByChild("points");

        queryRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot ds: dataSnapshot.getChildren()) {
                    User user = ds.getValue(User.class);
                    bestUsersMap.put(user.getUserName(), user.getPoints());
                    //Log.d("Username " + user.getUserName(), "Points " + user.getPoints());
                }
                firebaseCallback.onCallback(bestUsersMap);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }




}
