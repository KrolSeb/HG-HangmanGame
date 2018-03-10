package wisielec.wisielec.com.repository;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import wisielec.wisielec.com.domain.User;

/**
 * Created by Sebastian on 2018-01-17.
 */

public class UserRepository{
    protected static User receivedUser;
    public static User retrieveUserFromDatabase(){
        receivedUser = new User();
        DatabaseReference mDatabaseStatic = FirebaseDatabase.getInstance().getReference("users");
        Query queryRef = mDatabaseStatic.orderByChild("actuallyLogged").equalTo(true);
        queryRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                receivedUser.setAvatarURL(dataSnapshot.getValue(User.class).getAvatarURL());
                receivedUser.setUsername(dataSnapshot.getValue(User.class).getUsername());
                receivedUser.setRankingPosition(dataSnapshot.getValue(User.class).getRankingPosition());
                receivedUser.setRank(dataSnapshot.getValue(User.class).getRank());
                receivedUser.setPoints(dataSnapshot.getValue(User.class).getPoints());
            }
            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                receivedUser.setAvatarURL(dataSnapshot.getValue(User.class).getAvatarURL());
                receivedUser.setUsername(dataSnapshot.getValue(User.class).getUsername());
                receivedUser.setRankingPosition(dataSnapshot.getValue(User.class).getRankingPosition());
                receivedUser.setRank(dataSnapshot.getValue(User.class).getRank());
                receivedUser.setPoints(dataSnapshot.getValue(User.class).getPoints());
            }
            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {}
            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {}
            @Override
            public void onCancelled(DatabaseError databaseError) {}
        });
        return receivedUser;
    }

    public void addUserToDatabase(User user){
        DatabaseReference mDatabase;
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mDatabase.child("users").push().setValue(user);
    }
}
