package app.service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import javax.net.ssl.HttpsURLConnection;
import java.io.IOException;
import java.net.*;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class WebAuthenticationService implements AuthenticationService {
    @Override
    public Status authenticate(String username, String password) {
        try {
            HttpClient httpclient = HttpClient.newBuilder().build();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI("http://127.0.0.1:8000/auth"))
                    .header("Username", username)
                    .header("Password", password)
                    .GET()
                    .build();

            HttpResponse<String> response = httpclient.send(request, HttpResponse.BodyHandlers.ofString());

            Gson gson = new GsonBuilder().create();
            String status = gson.fromJson(response.body(), StatusWrapper.class).getStatus();

            return switch (status) {
                case "User not found" -> Status.USER_NOT_FOUND;
                case "Failed" -> Status.FAILED;
                case "Successful" -> Status.SUCCESSFUL;
                default -> Status.INVALID_REQUEST;
            };

        } catch (URISyntaxException  e) {
            return Status.INVALID_REQUEST;
        } catch (IOException | InterruptedException e) {
            return Status.SERVER_UNAVAILABLE;
        }
    }
}
