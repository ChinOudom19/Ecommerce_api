package co.oudom.ecommerce.features.auth;

import co.oudom.ecommerce.domain.Role;
import co.oudom.ecommerce.domain.User;
import co.oudom.ecommerce.domain.UserVerification;
import co.oudom.ecommerce.features.auth.dto.*;
import co.oudom.ecommerce.features.user.RoleRepository;
import co.oudom.ecommerce.features.user.UserRepository;
import co.oudom.ecommerce.mapper.UserMapper;
import co.oudom.ecommerce.util.RandomUtil;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.neo4j.Neo4jProperties;
import org.springframework.http.HttpStatus;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.security.oauth2.server.resource.authentication.BearerTokenAuthenticationToken;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationProvider;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthServiceImpl implements AuthService {

    private final UserMapper userMapper;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final UserVerificationRepository userVerificationRepository;
    private final PasswordEncoder passwordEncoder;

    private final DaoAuthenticationProvider daoAuthenticationProvider;
    private final JwtAuthenticationProvider jwtAuthenticationProvider;

    private final JavaMailSender javaMailSender;
    private final JwtEncoder accessTokenJwtEncoder;
    private final JwtEncoder refreshTokenJwtEncoder;

    @Value("${spring.mail.username}")
    private String adminEmail;

    private void sendVerificationEmail(String email, String verifiedCode) throws MessagingException {

        // Prepare email for sending
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);
        helper.setTo(email);
        helper.setFrom(adminEmail);
        helper.setSubject("User Verification");
        helper.setText(verifiedCode); // Consider improving the email content here

        javaMailSender.send(message);

    }

    @Override
    public void resetPassword(String email) throws MessagingException {

        User user = userRepository
                .findByEmail(email)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        UserVerification userVerification = new UserVerification();

    }

    @Override
    public AuthResponse refreshToken(RefreshTokenRequest refreshTokenRequest) {

        String refreshToken = refreshTokenRequest.refreshToken(); // Assuming refreshTokenRequest provides a method to get refresh token

        // Authenticate client with refresh token
        Authentication auth = new BearerTokenAuthenticationToken(refreshToken);
        auth = jwtAuthenticationProvider.authenticate(auth);

        log.info("Authenticated Principal: {}", auth.getPrincipal());

        Jwt jwt = (Jwt) auth.getPrincipal();

        Instant now = Instant.now();
        JwtClaimsSet jwtAccessClaimsSet = JwtClaimsSet.builder()
                .id(jwt.getId())
                .subject("Access APIs")
                .issuer(jwt.getId())
                .issuedAt(now)
                .expiresAt(now.plus(10, ChronoUnit.SECONDS))
                .build();

        String accessToken = accessTokenJwtEncoder
                .encode(JwtEncoderParameters.from(jwtAccessClaimsSet))
                .getTokenValue();

        // Check remaining days until refresh token expiry
        Instant refreshTokenExpiresAt = jwt.getExpiresAt();
        long remainingDays = Duration.between(now, refreshTokenExpiresAt).toDays();
        log.info("Remaining days until refresh token expiry: {}", remainingDays);

        // If refresh token expires within 1 day, generate a new one
        if (remainingDays <= 1) {
            JwtClaimsSet jwtRefreshClaimsSet = JwtClaimsSet.builder()
                    .id(auth.getName()) // Assuming auth.getName() provides a unique identifier for the user
                    .subject("Refresh Token")
                    .issuer(auth.getName())
                    .issuedAt(now)
                    .expiresAt(now.plus(7, ChronoUnit.DAYS))
                    .build();

            refreshToken = refreshTokenJwtEncoder
                    .encode(JwtEncoderParameters.from(jwtRefreshClaimsSet))
                    .getTokenValue();
        }

        return AuthResponse.builder()
                .tokenType("Bearer")
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    @Override
    public AuthResponse login(LoginRequest loginRequest) {
        log.info("Access Token Encoder: {}", accessTokenJwtEncoder);
        log.info("Refresh Token Encoder: {}", refreshTokenJwtEncoder);

        if(userRepository.existsByIsVerified(false)){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"User please verified");
        }

        // Authenticate client with username (phoneNumber) and password
        Authentication auth = new UsernamePasswordAuthenticationToken(loginRequest.email(), loginRequest.password());
        auth = daoAuthenticationProvider.authenticate(auth);

        log.info("Authenticated Principal: {}", auth.getPrincipal());

        // Generate scope based on user roles
        String scope = auth.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(" "));
        log.info("Generated Scope: {}", scope);

        Instant now = Instant.now();
        JwtClaimsSet jwtAccessClaimsSet = JwtClaimsSet.builder()
                .id(auth.getName()) // Assuming auth.getName() provides a unique identifier for the user
                .subject("Access Token")
                .issuer(auth.getName())
                .issuedAt(now)
                .expiresAt(now.plus(1, ChronoUnit.DAYS))
                .claim("scope", scope)
                .build();

        JwtClaimsSet jwtRefreshClaimsSet = JwtClaimsSet.builder()
                .id(auth.getName()) // Assuming auth.getName() provides a unique identifier for the user
                .subject("Refresh Token")
                .issuer(auth.getName())
                .issuedAt(now)
                .expiresAt(now.plus(1, ChronoUnit.DAYS))
                .claim("scope", scope)
                .build();

        // Generate tokens
        String accessToken = accessTokenJwtEncoder
                .encode(JwtEncoderParameters.from(jwtAccessClaimsSet))
                .getTokenValue();

        String refreshToken = refreshTokenJwtEncoder
                .encode(JwtEncoderParameters.from(jwtRefreshClaimsSet))
                .getTokenValue();

        log.info("Generated Access Token: {}", accessToken);
        log.info("Generated Refresh Token: {}", refreshToken);

        return AuthResponse.builder()
                .tokenType("Bearer")
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    // Verify
    @Override
    public void verify(VerificationRequest verificationRequest) {

        // Validate email
        User user = userRepository
                .findByEmail(verificationRequest.email())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "User has not been found"));

        // Validate verified code
        UserVerification userVerification = userVerificationRepository
                .findByUserAndVerifiedCode(user, verificationRequest.verifiedCode())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "User verification has not been found"));

        // Is verified code expired?
        if (LocalTime.now().isAfter(userVerification.getExpiryTime())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED,
                    "Verified code has expired");
        }

        user.setIsVerified(true);
        userRepository.save(user);

        userVerificationRepository.delete(userVerification);

    }

    // Resend Verification
    @Override
    public void resendVerification(String email) throws MessagingException {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User has not been found"));

        UserVerification userVerification = userVerificationRepository.findByUser(user)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User verification not found"));

        userVerification.setVerifiedCode(RandomUtil.random6Digits());
        userVerification.setExpiryTime(LocalTime.now().plusMinutes(1));
        userVerificationRepository.save(userVerification);

        sendVerificationEmail(email, userVerification.getVerifiedCode());
    }

    // Send Verification
    @Override
    public void sendVerification(String email) throws MessagingException {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User has not been found"));

        UserVerification userVerification = new UserVerification();
        userVerification.setUser(user);
        userVerification.setVerifiedCode(RandomUtil.random6Digits());
        userVerification.setExpiryTime(LocalTime.now().plusMinutes(1));
        userVerificationRepository.save(userVerification);

        sendVerificationEmail(email, userVerification.getVerifiedCode());
    }

    // Register
    @Override
    public RegisterResponse register(RegisterRequest registerRequest) {

        // Validate user's email
        if (userRepository.existsByEmail(registerRequest.email())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT,
                    "Email has already been used");
        }

        // Validate user's password
        if (!registerRequest.password().equals(registerRequest.confirmedPassword())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Password does not match");
        }

        // Validation term and policy
        if (!registerRequest.acceptTerm()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "You must accept the term");
        }

        User user = userMapper.fromRegisterRequest(registerRequest);
        user.setUuid(UUID.randomUUID().toString());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setProfileImage("image/profile.png");
        user.setIsVerified(false);

        Role roleUser = roleRepository.findRolesUser();
        Role roleCustomer = roleRepository.findRolesCustomer();
        user.setRoles(List.of(roleUser, roleCustomer));

        userRepository.save(user);

        return RegisterResponse.builder()
                .message("You have registered successfully")
                .email(user.getEmail())
                .build();
    }

}
