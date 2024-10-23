package pl.edu.uj.tcs.quizhub.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import pl.edu.uj.tcs.quizhub.models.UserPermission;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class TokenUtils {
    private static final long EXPIRATION_TIME = 3600000; // 1 hour
    private static final Key SIGNING_KEY = Keys.secretKeyFor(SignatureAlgorithm.HS256);

    public static String generateToken(int id, String email, String username, String role) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("id", id);
        claims.put("email", email);
        claims.put("username", username);
        claims.put("role", role);

        long now = System.currentTimeMillis();
        Date expirationDate = new Date(now + EXPIRATION_TIME);

        return Jwts.builder().setClaims(claims).setExpiration(expirationDate).signWith(SIGNING_KEY, SignatureAlgorithm.HS256).compact();
    }

    public static boolean isTokenValid(String token) {
        try {
            Claims claims = getTokenClaims(token);
            if (claims == null) throw new RuntimeException("claims are null");
            Date expirationDate = claims.getExpiration();
            Date currentDate = new Date();

            return !expirationDate.before(currentDate);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    public static boolean isAdmin(String token) {
        Claims claims = getTokenClaims(token);
        if (claims == null) return false;

        String role = (String) claims.get("role");

        return role != null && claims.get("role").equals(UserPermission.ADMIN.name());
    }

    public static int getUserID(String token) {
        Claims claims = getTokenClaims(token);

        return claims != null ? (int) claims.get("id") : -1;
    }

    private static Claims getTokenClaims(String token) {
        try {
            if (token == null || token.isEmpty()) return null;

            return Jwts.parserBuilder().setSigningKey(SIGNING_KEY).build().parseClaimsJws(token).getBody();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }
}