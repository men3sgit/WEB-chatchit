package vn.edu.nlu.web.chat.service;

import vn.edu.nlu.web.chat.dto.auth.LoginRequest;
import vn.edu.nlu.web.chat.dto.auth.request.ResetPasswordRequest;
import vn.edu.nlu.web.chat.dto.auth.response.LoginResponse;

public interface AuthenticationService {

    LoginResponse login(LoginRequest request);

    /**
     * Logs the currently authenticated user out of the chat system.
     */
    void logout();

    /**
     * Checks if a user is currently authenticated.
     *
     * @return true if a user is logged in, false otherwise
     */
    boolean isLoggedIn();

    /**
     * Retrieves the username of the currently authenticated user.
     *
     * @return the username if logged in, null otherwise
     */
    String getCurrentUsername();

    Long getCurrentUserId();

    void verifyNewUser(String token);

    void initiatePasswordResetProcess(String username);

    void resetPassword(ResetPasswordRequest request);
}
