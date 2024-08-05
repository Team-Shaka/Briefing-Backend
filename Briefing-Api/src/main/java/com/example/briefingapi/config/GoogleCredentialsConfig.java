package com.example.briefingapi.config;

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.androidpublisher.AndroidPublisher;
import com.google.api.services.androidpublisher.AndroidPublisherScopes;
import com.google.auth.http.HttpCredentialsAdapter;
import com.google.auth.oauth2.GoogleCredentials;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.GeneralSecurityException;

@Component
public class GoogleCredentialsConfig {

    @Value("${subscription.google.keyfile.content}")
    private String googleAccountFileContent;

    public AndroidPublisher androidPublisher() throws IOException, GeneralSecurityException {
        InputStream inputStream = new ByteArrayInputStream(googleAccountFileContent.getBytes());
        GoogleCredentials credentials = GoogleCredentials.fromStream(inputStream)
                .createScoped(AndroidPublisherScopes.ANDROIDPUBLISHER);

        JsonFactory jsonFactory = GsonFactory.getDefaultInstance();

        return new AndroidPublisher.Builder(
                GoogleNetHttpTransport.newTrustedTransport(),
                jsonFactory,
                new HttpCredentialsAdapter(credentials))
                .build();
    }
}
