package wisielec.wisielec.com.services;

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

public class RankService {
    private static final String TAG = "RankService";
    private static RankService instance = null;
    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();

    private RankService() { }

    public static RankService getInstance() {
        if (instance == null) {
            instance = new RankService();
        }
        return instance;
    }

    public void getUserRankingPosition(final IUserRankingPositionCallback callback) {
        String currentUserUID =  FirebaseAuth.getInstance().getCurrentUser().getUid();

        final Query query = firebaseDatabase.getReference().child("users").orderByChild("points").limitToFirst(50);
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
            public void onCancelled(DatabaseError databaseError) { }
        });
    }


    public void getBestUsersFromRanking(final IBestUsersCallback callback) {
        final DatabaseReference databaseReference = firebaseDatabase.getReference().child("users");
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
            }
        });
    }


    public void getThreeBestUsersFromRanking(final IThreeBestUsersCallback callback) {
        final DatabaseReference databaseReference = firebaseDatabase.getReference();
        Query queryRef = databaseReference.child("users").orderByChild("points");

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
            }
        });
    }

    public interface IUserRankingPositionCallback {
        void onSuccess(int userRankingPosition);
    }

    public interface IBestUsersCallback {
        void onSuccess(ArrayList<User> userList);
    }

    public interface IThreeBestUsersCallback {
        void onSuccess(List<User> userList);
    }

}
