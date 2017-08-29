package be.mira.jongeren.mailinglist.service;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.Value;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.gmail.Gmail;
import com.google.api.services.gmail.GmailScopes;
import com.google.api.services.gmail.model.Label;
import com.google.api.services.gmail.model.ListLabelsResponse;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;

@Component
public class GoogleAuthenticator {
    /**
     * Application name.
     */
    private static final String APPLICATION_NAME = "Gmail API Java Quickstart";

    @Value("${credentials.location:}")
    private String credentialsLocation;

    @Value("${google.client-secrets:}")
    private String clientSecretsJsonString;


    /**
     * Global instance of the JSON factory.
     */
    private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();

    /**
     * Global instance of the HTTP transport.
     */
    private static HttpTransport HTTP_TRANSPORT;


    /**
     * Global instance of the scopes required by this quickstart.
     *
     * If modifying these scopes, delete your previously saved credentials
     * at ~/.credentials/gmail-java-quickstart
     */
    private static final List<String> SCOPES = Arrays.asList(GmailScopes.GMAIL_LABELS, GmailScopes.GMAIL_SEND);

    static {
        try {
            HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
        } catch (Throwable t) {
            t.printStackTrace();
            System.exit(1);
        }
    }

    /**
     * Creates an authorized Credential object.
     *
     * @return an authorized Credential object.
     * @throws IOException
     */
    private Credential authorize() throws IOException {
        // Directory to store user credentials for this application.
        java.io.File dataStoreDirectory = new java.io.File(credentialsLocation, ".credentials/mirajeugdkern-mailinglist");
        FileDataStoreFactory dataStoreFactory = new FileDataStoreFactory(dataStoreDirectory);

        InputStream in = GoogleMailSenderImpl.class.getResourceAsStream("client_secret.json");
        //GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(in));
        GoogleClientSecrets clientSecrets = JSON_FACTORY.fromString(clientSecretsJsonString, GoogleClientSecrets.class);

        // Build flow and trigger user authorization request.
        GoogleAuthorizationCodeFlow flow =
                new GoogleAuthorizationCodeFlow.Builder(
                        HTTP_TRANSPORT, JSON_FACTORY, clientSecrets, SCOPES)
                        .setDataStoreFactory(dataStoreFactory)
                        .setAccessType("offline")
                        .setApprovalPrompt("force") // Force refresh token.
                        .build();

        Credential credential = new AuthorizationCodeInstalledApp(flow, new LocalServerReceiver.Builder().setPort(8040).build())
                .authorize("user");
        System.out.println(
                "Credentials saved to " + dataStoreDirectory.getAbsolutePath());

        return credential;
    }

//    public Credential loadCredential(GoogleClientSecrets clientSecrets) throws IOException {
//        Credential credential = new Credential.Builder(BearerToken.authorizationHeaderAccessMethod())
//                .setTransport(HTTP_TRANSPORT)
//                .setJsonFactory(JSON_FACTORY)
//                .setTokenServerEncodedUrl(GoogleOAuthConstants.TOKEN_SERVER_URL)
//                .setClientAuthentication(
//                        new ClientParametersAuthentication(
//                                clientSecrets.getDetails().getClientId(),
//                                clientSecrets.getDetails().getClientSecret()
//                        ))
//                .setRequestInitializer(null)
//                .setClock(Clock.SYSTEM)
//                .addRefreshListener(new DataStoreCredentialRefreshListener("user", DATA_STORE_FACTORY))
//                .build();
//
//
//        DataStore<StoredCredential> dataStore = StoredCredential.getDefaultDataStore(DATA_STORE_FACTORY);
//        StoredCredential stored = dataStore.get("user");
//        if (stored == null) {
//            return null;
//        }
//        credential.setAccessToken(stored.getAccessToken());
//        credential.setRefreshToken(stored.getRefreshToken());
//        credential.setExpirationTimeMilliseconds(stored.getExpirationTimeMilliseconds());
//
//        System.out.println("Refresh Token: "+stored.getRefreshToken());
//
//        SimpleDateFormat sdf = new SimpleDateFormat("MMM dd,yyyy HH:mm");
//        Date now = new Date(Clock.SYSTEM.currentTimeMillis());
//        System.out.println("Now:" + sdf.format(now));
//
//        Date expiry = new Date(stored.getExpirationTimeMilliseconds());
//        System.out.println("Expiry: "+ sdf.format(expiry));
//
//        return credential;
//    }

    /**
     * Build and return an authorized Gmail client service.
     *
     * @return an authorized Gmail client service
     * @throws IOException
     */
    public Gmail getGmailService() throws IOException {
        Credential credential = authorize();
        return new Gmail.Builder(HTTP_TRANSPORT, JSON_FACTORY, credential)
                .setApplicationName(APPLICATION_NAME)
                .build();
    }

    public static void main(String[] args) throws IOException, MessagingException {
        GoogleAuthenticator authenticator = new GoogleAuthenticator();
        authenticator.clientSecretsJsonString = "";
        authenticator.credentialsLocation=System.getProperty("user.home");

        // Build a new authorized API client service.
        Gmail service = authenticator.getGmailService();

        // Print the labels in the user's account.
        String user = "me";
        ListLabelsResponse listResponse =
                service.users().labels().list(user).execute();
        List<Label> labels = listResponse.getLabels();
        if (labels.size() == 0) {
            System.out.println("No labels found.");
        } else {
            System.out.println("Labels:");
            for (Label label : labels) {
                System.out.printf("- %s\n", label.getName());
            }
        }
    }
}
