package com.programming.technie.springredditclone.service;

import com.programming.technie.springredditclone.dto.AuthenticationResponse;
import com.programming.technie.springredditclone.dto.LoginRequest;
import com.programming.technie.springredditclone.dto.RefreshTokenRequest;
import com.programming.technie.springredditclone.dto.RegisterRequest;
import com.programming.technie.springredditclone.exception.SpringRedditException;
import com.programming.technie.springredditclone.exception.UserNotFoundException;
import com.programming.technie.springredditclone.model.User;
import com.programming.technie.springredditclone.model.VerificationToken;
import com.programming.technie.springredditclone.repository.UserRepository;
import com.programming.technie.springredditclone.repository.VerificationTokenRepository;
import com.programming.technie.springredditclone.security.JWTProvider;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.security.oauth2.resource.OAuth2ResourceServerProperties;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.security.oauth2.jwt.Jwt;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

import static com.programming.technie.springredditclone.util.Constants.ACTIVATION_EMAIL;
import static java.time.Instant.now;

@Service
@AllArgsConstructor
@Slf4j
@Transactional
public class AuthService {
    private final UserRepository userRepository;
    private final VerificationTokenRepository verificationTokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final MailContentBuilder mailContentBuilder;
    private final MailService mailService;
    private final AuthenticationManager authenticationManager;
    private final JWTProvider jwtProvider;
    private final RefreshTokenService refreshTokenService;

    @Transactional
    public void signup(RegisterRequest registerRequest) {
        Optional<User> checkUser = userRepository.findByEmail(registerRequest.getEmail());

        if (checkUser.isPresent()) {
            throw new SpringRedditException("Email already used");
        }

        User user = new User();
        user.setUsername(registerRequest.getUsername());
        user.setEmail(registerRequest.getEmail());
        user.setPassword(encodedPassword(registerRequest.getPassword()));
        user.setEnabled(false);
        user.setCreated(now());

        userRepository.save(user);


        String token = generateVerificationToken(user);
        String message = mailContentBuilder.build("Thank you for signing up to  Spring Reddit, please click on the blow url to activate your account :" + ACTIVATION_EMAIL + "/" + token);

//        mailService.sendMail(new NotificationEmail("Please activate your account", user.getEmail(),message));

    }

    @Transactional(readOnly = true)
    User getCurrentUser() {
        Jwt principal = (Jwt) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return userRepository.findByUsername(principal.getSubject()).orElseThrow(() -> new UserNotFoundException(principal.getSubject()));
    }

    public AuthenticationResponse login(LoginRequest loginRequest) {
        System.out.println(loginRequest);
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String authenticationToken = jwtProvider.generateToken(authentication);
        System.out.println(authenticationToken);
        return AuthenticationResponse.builder().authenticationToken(authenticationToken).refreshToken(refreshTokenService.generateRefreshToken().getToken()).expireAt(Instant.now().plusMillis(jwtProvider.getJwtExpirationInMillis())).username(loginRequest.getUsername()).build();

    }


    private String encodedPassword(String password) {
        return passwordEncoder.encode(password);
    }

    public void verifyAccount(String token) {
        Optional<VerificationToken> verificationTokenOptional = verificationTokenRepository.findByToken(token);
        verificationTokenOptional.orElseThrow(() -> new SpringRedditException("Invalid token"));
        fetchUserAndEnable(verificationTokenOptional.get());

    }

    private String generateVerificationToken(User user) {
        String token = UUID.randomUUID().toString();
        VerificationToken verificationToken = new VerificationToken();
        verificationToken.setToken(token);
        verificationToken.setUser(user);
        verificationTokenRepository.save(verificationToken);

        return token;

    }


    @Transactional
    private void fetchUserAndEnable(VerificationToken verificationToken) {
        String username = verificationToken.getUser().getUsername();
        User user = userRepository.findByUsername(username).orElseThrow(() -> new SpringRedditException("username not found"));
        user.setEnabled(true);
        userRepository.save(user);
    }


    public AuthenticationResponse refreshToken(RefreshTokenRequest refreshTokenRequest) {
        refreshTokenService.validateRefreshToken(refreshTokenRequest.getRefreshToken());

        String token = jwtProvider.generateTokenWithUserName(refreshTokenRequest.getUsername());

        return AuthenticationResponse.builder().authenticationToken(token).expireAt(Instant.now().plusMillis(jwtProvider.getJwtExpirationInMillis())).username(refreshTokenRequest.getUsername()).refreshToken(refreshTokenRequest.getRefreshToken()).build();
    }
}
