package com.example.briefingapi.config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Base64;

@Configuration
public class firebaseConfig {

    @Value("${fcm.key}")
    private String fcmJson;

    @PostConstruct
    public void initFirebase(){

        String base64String = fcmJson;

        byte[] decodedBytes = Base64.getDecoder().decode(base64String);

        InputStream credentialStream = new ByteArrayInputStream(decodedBytes);
        try {
            FirebaseOptions options = FirebaseOptions.builder()
                    .setCredentials(GoogleCredentials.fromStream(credentialStream))
                    .build();
            FirebaseApp.initializeApp(options);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
