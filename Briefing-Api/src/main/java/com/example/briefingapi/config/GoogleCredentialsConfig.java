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
import software.amazon.awssdk.core.sync.ResponseTransformer;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.GeneralSecurityException;

@Component
public class GoogleCredentialsConfig {

    @Value("${subscription.google.keyfile.s3path}")
    private String s3GoogleKeyfilePath;

    private final S3Client s3Client;

    public GoogleCredentialsConfig() {
        this.s3Client = S3Client.builder()
                .region(Region.of("ap-northeast-2"))
                .build();
    }

    private String getGoogleKeyFileContentFromS3() {
        String bucketName = s3GoogleKeyfilePath.split("/")[2];
        String key = s3GoogleKeyfilePath.substring(s3GoogleKeyfilePath.indexOf("/", 5) + 1);

        GetObjectRequest getObjectRequest = GetObjectRequest.builder()
                .bucket(bucketName)
                .key(key)
                .build();

        return s3Client.getObject(getObjectRequest, ResponseTransformer.toBytes()).asUtf8String();
    }

    public AndroidPublisher androidPublisher() throws IOException, GeneralSecurityException {
        String googleAccountFileContent = getGoogleKeyFileContentFromS3();
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
