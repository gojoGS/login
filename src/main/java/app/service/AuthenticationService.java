package app.service;

import java.net.MalformedURLException;

public interface AuthenticationService {
    Status authenticate(String username, String password) throws Exception;
}
