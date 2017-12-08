package com.wisielec.wisielec.config;

import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.auth.FirebaseCredentials;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * Created by Emerex on 08/12/2017.
 */

@Configuration
@Component
public class FirebaseConfig {
    public FirebaseConfig() { }

    @PostConstruct
    public void init() {
        try {
            FileInputStream serviceAccount = new FileInputStream("gra-w-wisielca-firebase-adminsdk-5dmse-0db348bcd4.json");
            FirebaseOptions options = new FirebaseOptions.Builder()
                    .setCredential(FirebaseCredentials.fromCertificate(serviceAccount))
                    .setDatabaseUrl("https://gra-w-wisielca.firebaseio.com")
                    .build();

            FirebaseApp.initializeApp(options);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
