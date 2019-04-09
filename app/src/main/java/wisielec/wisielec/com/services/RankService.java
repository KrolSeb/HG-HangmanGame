package wisielec.wisielec.com.services;

import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import androidx.annotation.NonNull;
import wisielec.wisielec.com.domain.User;
import wisielec.wisielec.com.interfaces.GetBestThreeUsersCallback;
import wisielec.wisielec.com.interfaces.GetBestUsersCallback;
import wisielec.wisielec.com.interfaces.UserRankingPositionCallback;


public class RankService {
    private static final String TAG = "RankService";
    private static final String USERS_CHILD_REFERENCE = "users";
    private static final String POINTS_CHILD_REFERENCE = "points";

    private static RankService instance = null;
    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();

    private RankService() { }

    public static RankService getInstance() {
        if (instance == null) {
            instance = new RankService();
        }
        return instance;
    }

    public void getUserRankingPosition(final UserRankingPositionCallback callback) {
        String currentUserUID =  FirebaseAuth.getInstance().getCurrentUser().getUid();

        final Query query = firebaseDatabase.getReference().child(USERS_CHILD_REFERENCE).orderByChild(POINTS_CHILD_REFERENCE).limitToFirst(50);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                int numberOfUsers = (int) dataSnapshot.getChildrenCount();
                int counter = 0;

                for (DataSnapshot childSnapshot : dataSnapshot.getChildren()) {
                    if (currentUserUID.equals(childSnapshot.getKey())) {
                        callback.onSuccess(numberOfUsers - counter);
                        break;
                    }
                    else{
                        counter++;
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.w(TAG, "getUserRankingPosition:onCancelled", databaseError.toException());
            }
        });
    }

    public void getBestUsersFromRanking(final GetBestUsersCallback callback) {
        final DatabaseReference databaseReference = firebaseDatabase.getReference().child(USERS_CHILD_REFERENCE);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ArrayList<User> userList = new ArrayList<>();
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    User user = ds.getValue(User.class);
                    userList.add(user);
                }

                Collections.sort(userList, (user1, user2) -> user2.getPoints() - user1.getPoints());
                callback.onSuccess(userList);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.w(TAG, "getBestUsersFromRanking:onCancelled", databaseError.toException());
            }
        });
    }
    
    public void getThreeBestUsersFromRanking(final GetBestThreeUsersCallback callback) {
        final DatabaseReference databaseReference = firebaseDatabase.getReference();
        Query queryRef = databaseReference.child(USERS_CHILD_REFERENCE).orderByChild(POINTS_CHILD_REFERENCE);

        queryRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<User> userList = new ArrayList<>();
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    User user = ds.getValue(User.class);
                    userList.add(user);
                }

                Collections.sort(userList, (user1, user2) -> user2.getPoints() - user1.getPoints());
                callback.onSuccess(userList);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.w(TAG, "getThreeBestUsersFromRanking:onCancelled", databaseError.toException());
            }
        });
    }

}