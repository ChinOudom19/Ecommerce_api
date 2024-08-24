package co.oudom.ecommerce.features.auth;

import co.oudom.ecommerce.features.auth.dto.*;
import jakarta.mail.MessagingException;

public interface AuthService {

    void resetPassword(String email) throws MessagingException;

    AuthResponse refreshToken(RefreshTokenRequest refreshTokenRequest);

    AuthResponse login(LoginRequest loginRequest);

    void verify(VerificationRequest verificationRequest);

    void resendVerification(String email) throws MessagingException;

    void sendVerification(String email) throws MessagingException;

    RegisterResponse register(RegisterRequest registerRequest);

}
