package wisielec.wisielec.com.services;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.MutableData;
import com.google.firebase.database.Transaction;

import android.util.Log;
import androidx.annotation.NonNull;
import wisielec.wisielec.com.domain.User;
import wisielec.wisielec.com.enums.Rank;

public class GameService {
    private static final String TAG = "GameService";
    private static GameService instance = null;
    private FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();

    private final static int PRIVATE_RANK_MIN_VALUE = 0;
    private final static int PRIVATE_RANK_MAX_VALUE = 149;
    private final static int CORPORAL_RANK_MAX_VALUE = 349;
    private final static int SERGEANT_RANK_MAX_VALUE = 699;
    private final static int WARRANT_OFFICER_RANK_MAX_VALUE = 1199;
    private final static int LIEUTENANT_RANK_MAX_VALUE = 1999;
    private final static int CAPTAIN_RANK_MAX_VALUE = 2999;
    private final static int MAJOR_RANK_MAX_VALUE = 4499;
    private final static int COLONEL_RANK_MAX_VALUE = 6999;

    public GameService() { }

    public static GameService getInstance() {
        if (instance == null) {
            instance = new GameService();
        }
        return instance;
    }

    public void updateUserDataAfterGame(int fullScore){
        final DatabaseReference databaseReference = firebaseDatabase.getReference().child("users").child(firebaseAuth.getCurrentUser().getUid());
        databaseReference.runTransaction(new Transaction.Handler() {
            @NonNull
            @Override
            public Transaction.Result doTransaction(@NonNull MutableData mutableData) {
                User user = mutableData.getValue(User.class);
                if (user == null) {
                    return Transaction.success(mutableData);
                }

                calculateNewPointsValue(mutableData,user,fullScore);
                updateUserRank(mutableData,user);
                return Transaction.success(mutableData);
            }

            @Override
            public void onComplete(DatabaseError databaseError, boolean b, DataSnapshot dataSnapshot) {
                Log.d(TAG, "postTransaction:onComplete:" + databaseError);
            }
        });
    }

    private void calculateNewPointsValue(MutableData mutableData,User user, int fullScore) {
        user.setPoints(user.getPoints() + fullScore);
        mutableData.setValue(user);
    }

    private void updateUserRank(MutableData mutableData, User user) {
        int points = user.getPoints();

        if (points < PRIVATE_RANK_MIN_VALUE) {
            user.setRank(Rank.LACK_OF_DEGREE.toString());
        }
        else if (points <= PRIVATE_RANK_MAX_VALUE) {
            user.setRank(Rank.PRIVATE.toString());
        }
        else if (points <= CORPORAL_RANK_MAX_VALUE) {
            user.setRank(Rank.CORPORAL.toString());
        }
        else if (points <= SERGEANT_RANK_MAX_VALUE) {
            user.setRank(Rank.SERGEANT.toString());
        }
        else if (points <= WARRANT_OFFICER_RANK_MAX_VALUE) {
            user.setRank(Rank.WARRANT_OFFICER.toString());
        }
        else if (points <= LIEUTENANT_RANK_MAX_VALUE) {
            user.setRank(Rank.LIEUTENANT.toString());
        }
        else if (points <= CAPTAIN_RANK_MAX_VALUE) {
            user.setRank(Rank.CAPTAIN.toString());
        }
        else if (points <= MAJOR_RANK_MAX_VALUE) {
            user.setRank(Rank.MAJOR.toString());
        }
        else if (points <= COLONEL_RANK_MAX_VALUE) {
            user.setRank(Rank.COLONEL.toString());
        }
        else {
            user.setRank(Rank.GENERAL.toString());
        }

        mutableData.setValue(user);
    }
}
