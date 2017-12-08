package com.wisielec.wisielec.repository;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.wisielec.wisielec.domain.User;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Emerex on 08/12/2017.
 */

@Repository
public class UserRepository {
    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();

    public void addNewUser(User user){
        DatabaseReference databaseReference = firebaseDatabase.getReference();
        databaseReference.child("users");

        Map<String, User> userMap = new HashMap<>();
        userMap.put("ref", user);

        databaseReference.setValueAsync(userMap);

    }
}
