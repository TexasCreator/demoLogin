package org.cschwabe.util;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.io.Decoders;

import java.security.Key;
import java.util.Date;

public class JwtUtil {

    private static final String SECRET_KEY = "mySecretKey1234567890123456789012345"; // 256-Bit-Schlüssel zur Sicherheit

    /**
     * Erstellt einen Signierungsschlüssel aus dem Secret Key.
     */
    private static Key getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes); // HMAC-SHA Signierungsschlüssel erstellen
    }

    /**
     * Generiert ein JWT-Token mit Benutzerdaten.
     *
     * @param username Benutzername
     * @param role     Benutzerrolle
     * @return JWT-Token
     */
    public static String generateToken(String username, String role) {
        return Jwts.builder()
                .setSubject(username)
                .claim("role", role)
                .setIssuedAt(new Date()) // aktuelles Datum als Erstellung
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10)) // Ablaufzeit: 10 Stunden
                .signWith(getSigningKey()) // sichere Signierung
                .compact(); // Token in eine kompakte Form bringen
    }

    /**
     * Extrahiert den Benutzernamen aus einem JWT-Token.
     *
     * @param token JWT-Token
     * @return Benutzername
     */
    public static String getUsernameFromToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey()) // Sicherstellen, dass wir mit dem gültigen Schlüssel arbeiten
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    /**
     * Überprüft, ob ein JWT-Token valide ist.
     *
     * @param token JWT-Token
     * @return Token-Gültigkeit
     */
    public static boolean validateToken(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(getSigningKey()) // Schlüssel für Validierung
                    .build()
                    .parseClaimsJws(token); // Token wird geprüft
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}