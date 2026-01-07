package com.equeue.config;

@Configuration
public class FirebaseConfig {

    @PostConstruct
    public void initialize() throws IOException {

        InputStream serviceAccount =
                new ClassPathResource(
                        "firebase/firebase-service-account.json"
                ).getInputStream();

        FirebaseOptions options = FirebaseOptions.builder()
                .setCredentials(
                        GoogleCredentials.fromStream(serviceAccount)
                )
                .build();

        if (FirebaseApp.getApps().isEmpty()) {
            FirebaseApp.initializeApp(options);
        }
    }
}

