package se.lexicon.model;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import se.lexicon.exception.AuthenticationFailedException;

import java.security.SecureRandom;
import java.util.Random;

public class User {
    private String username;
    private String password;
    private boolean expired;


    //Registration
    public User(String username) {
        this.username = username;
    }

    //Authentication
    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public User(String username, String password, boolean expired) {
        this.username = username;
        this.password = password;
        this.expired = expired;
    }

    public String getUsername() {
        return username;
    }

    public boolean isExpired() {
        return expired;
    }

    public String userInfo() {
        return "username: " + username + " password: " + password;
    }

    public String getHashedPassword() {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
        return passwordEncoder.encode(this.password); // 1234hhgfhgmjhmjhj,hk,jk,jg,jh
    }

    //compare the hashed passcode to a raw passcode return boolean
    public void checkHash(String hashedPassword) throws AuthenticationFailedException {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
        boolean isEqual = passwordEncoder.matches(this.password, hashedPassword);
        if (!isEqual) {
            throw new AuthenticationFailedException("Authentication failed. Invalid credentials.");
        }
    }
    private String generateRandomPassword() {
        String allowedCharacters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        int passwordLength = 10;
        StringBuilder stringBuilder = new StringBuilder();

        Random random = new SecureRandom();

        for (int i = 0; i < passwordLength; i++) {
            int randomIndex = random.nextInt(allowedCharacters.length());
            char randomChar = allowedCharacters.charAt(randomIndex);
            stringBuilder.append(randomChar);
        }

        return stringBuilder.toString();
    }

    public void newPassword() {
        this.password = generateRandomPassword();
    }
}
