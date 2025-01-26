package org.cschwabe.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(); // Sichere Passwortverschlüsselung
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // CSRF deaktivieren (nicht erforderlich für JWT-basierte Auth)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/auth/**").permitAll() // Auth-Endpunkte zulässig
                        .anyRequest().authenticated() // Sonst alles muss authentifiziert sein
                )
                .httpBasic(Customizer.withDefaults()); // HTTP Basic deaktivieren
                http.formLogin(Customizer.withDefaults()); // Kein Standard-Formular-Login (bei Bedarf deaktivieren)

        return http.build(); // Die Konfiguration abschließen
    }
    @Bean
    public static HttpSecurity httpSecurity(HttpSecurity http) throws Exception {
        return http;
    }
}