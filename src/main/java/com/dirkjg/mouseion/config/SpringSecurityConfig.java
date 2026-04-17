package com.dirkjg.mouseion.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SpringSecurityConfig {

    private static final Logger log = LoggerFactory.getLogger(SpringSecurityConfig.class);

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // AUTHENTICATION (user-account)
    @Bean
    public AuthenticationManager authenticationManager(
            HttpSecurity http,
            PasswordEncoder passwordEncoder,
            UserDetailsService userDetailsService
    ) throws Exception {
        var auth = new DaoAuthenticationProvider();
        auth.setPasswordEncoder(passwordEncoder);
        auth.setUserDetailsService(userDetailsService);
        return new ProviderManager(auth);
    }

    // AUTHORIZATION (user-rights)
    @Bean
    protected SecurityFilterChain filter (
            HttpSecurity http
    ) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .httpBasic(basic -> basic.disable())
                .cors(Customizer.withDefaults())
                .authorizeHttpRequests(auth -> auth

                        // Login / registreren open
                        .requestMatchers("/auth/**").permitAll()

                        // STUDENT mag alleen downloaden
                        // ZOEKEN en FILTEREN mag door iedereen met login
                        .requestMatchers(HttpMethod.GET, "/paintings/**")
                        .hasAnyRole("STUDENT", "EDUCATOR", "ADMIN")

                        .requestMatchers(HttpMethod.GET, "/education-content/**")
                        .hasAnyRole("STUDENT", "EDUCATOR", "ADMIN")

                        .requestMatchers(HttpMethod.GET, "/characteristic-aspects/**")
                        .hasAnyRole("STUDENT", "EDUCATOR", "ADMIN")

                        // EDUCATOR mag uploaden
                        .requestMatchers(HttpMethod.POST, "/paintings/**")
                        .hasAnyRole("EDUCATOR", "ADMIN")

                        .requestMatchers(HttpMethod.POST, "/education-content/**")
                        .hasAnyRole("EDUCATOR", "ADMIN")

                        // EDUCATIE CONTENT beheren
                        .requestMatchers(HttpMethod.PUT, "/education-content/**")
                        .hasAnyRole("EDUCATOR", "ADMIN")

                        .requestMatchers(HttpMethod.DELETE, "/education-content/**")
                        .hasAnyRole("EDUCATOR", "ADMIN")

                        // ADMIN alles
                        .requestMatchers("/admin/**").hasRole("ADMIN")

                        // Anders alles dicht
                        .anyRequest().authenticated()
                )
                .httpBasic();

        return http.build();
    }
}
