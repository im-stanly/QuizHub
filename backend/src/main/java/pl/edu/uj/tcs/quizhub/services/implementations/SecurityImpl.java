package pl.edu.uj.tcs.quizhub.services.implementations;

import org.springframework.stereotype.Service;
import pl.edu.uj.tcs.quizhub.services.interfaces.Security;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@Service
public class SecurityImpl implements Security {
    public String encode(String password) {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
            byte[] passwordBytes = password.getBytes();
            byte[] passwordHash = messageDigest.digest(passwordBytes);
            StringBuilder encryptedPassword = new StringBuilder();
            for (byte b : passwordHash)
                encryptedPassword.append(String.format("%02x", b));
            return encryptedPassword.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }
}
