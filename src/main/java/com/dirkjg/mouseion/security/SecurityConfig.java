package com.dirkjg.mouseion.security;

import com.dirkjg.mouseion.Dtos.PaintingDto;
import com.dirkjg.mouseion.repositories.UserRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final JwtService jwtService;
    private final UserRepository userRepository;

    public SecurityConfig(JwtService jwtService, UserRepository userRepos) {
        this.jwtService = jwtService;
        this.userRepository = userRepos;
    }

    @Bean
    public AuthenticationManager authManager(
            HttpSecurity http,
            PasswordEncoder encoder,
            UserDetailsService udService
    ) throws Exception {
        return http.getSharedObject(AuthenticationManagerBuilder.class)
                .userDetailsService(udService)
                .passwordEncoder(encoder)
                .and()
                .build();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return new MyUserDetailsService(this.userRepository);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .httpBasic().disable()
                .authorizeHttpRequests()

                // Public endpoints
                .requestMatchers(HttpMethod.POST, "/users").permitAll()
                .requestMatchers(HttpMethod.POST, "/auth").permitAll()

                // DOWNLOAD: BY EVERYONE
                // All paintings
                .requestMatchers(HttpMethod.GET, "/paintings")
                .hasAnyRole("STUDENT", "EDUCATOR", "ADMIN")
                // Painting by ID
                .requestMatchers(HttpMethod.GET, "/paintings/**")
                .hasAnyRole("STUDENT", "EDUCATOR", "ADMIN")
                // All painters
                .requestMatchers(HttpMethod.GET, "/painters")
                .hasAnyRole("STUDENT", "EDUCATOR", "ADMIN")
                // Painter by ID
                .requestMatchers(HttpMethod.GET, "/painters/**")
                .hasAnyRole("STUDENT", "EDUCATOR", "ADMIN")
                // All educationcontents
                .requestMatchers(HttpMethod.GET, "/educationContents")
                .hasAnyRole("STUDENT", "EDUCATOR", "ADMIN")
                // Educationconent by ID
                .requestMatchers(HttpMethod.GET, "/educationContents/**")
                .hasAnyRole("STUDENT", "EDUCATOR", "ADMIN")
                // All historicalperiods
                .requestMatchers(HttpMethod.GET, "/historicalPeriods")
                .hasAnyRole("STUDENT", "EDUCATOR", "ADMIN")
                // Historicalperiod by ID
                .requestMatchers(HttpMethod.GET, "/historicalPeriods/**")
                .hasAnyRole("STUDENT", "EDUCATOR", "ADMIN")
                // All characteristicaspects
                .requestMatchers(HttpMethod.GET, "/characteristicAspects/**")
                .hasAnyRole("STUDENT", "EDUCATOR", "ADMIN")
                // Characteristicaspect by ID
                .requestMatchers(HttpMethod.GET, "/characteristicAspects/**")
                .hasAnyRole("STUDENT", "EDUCATOR", "ADMIN")

                // images downloaden
                .requestMatchers(HttpMethod.GET, "/paintings/*/image")
                .hasAnyRole("STUDENT", "EDUCATOR", "ADMIN")

                // UPLOAD: ONLY BY EDUCATOR AND ADMIN
                // Paintings upload
                .requestMatchers(HttpMethod.POST, "/paintings")
                .hasAnyRole("EDUCATOR", "ADMIN")

                // Painters upload
                .requestMatchers(HttpMethod.POST, "/painters")
                .hasAnyRole("EDUCATOR", "ADMIN")

                // EducationContent upload
                .requestMatchers(HttpMethod.POST, "/educationContents")
                .hasAnyRole("EDUCATOR", "ADMIN")

                // Assigns (painting relaties)
                .requestMatchers(HttpMethod.PUT, "/paintings/*/educationContent/*")
                .hasAnyRole("EDUCATOR", "ADMIN")

                .requestMatchers(HttpMethod.PUT, "/paintings/*/painter/*")
                .hasAnyRole("EDUCATOR", "ADMIN")

                .requestMatchers(HttpMethod.PUT, "/paintings/*/characteristicAspect/*")
                .hasAnyRole("EDUCATOR", "ADMIN")

                .requestMatchers(HttpMethod.POST, "/paintings/*/image")
                .hasAnyRole("EDUCATOR", "ADMIN")

                // ADMIN ONLY
                .requestMatchers(HttpMethod.POST, "/historicalPeriods")
                .hasRole("ADMIN")

                .requestMatchers(HttpMethod.PUT, "/historicalPeriods/**")
                .hasRole("ADMIN")

                .requestMatchers(HttpMethod.PATCH, "/historicalPeriods/**")
                .hasRole("ADMIN")

                .requestMatchers(HttpMethod.DELETE, "/historicalPeriods/**")
                .hasRole("ADMIN")

                .requestMatchers(HttpMethod.POST, "/characteristicAspects")
                .hasRole("ADMIN")

                .requestMatchers(HttpMethod.PUT, "/characteristicAspects/**")
                .hasRole("ADMIN")

                .requestMatchers(HttpMethod.PATCH, "/characteristicAspects/**")
                .hasRole("ADMIN")

                .requestMatchers(HttpMethod.DELETE, "/characteristicAspects/**")
                .hasRole("ADMIN")

//              Op non-actief omdat deze regel alle eerdere regels overschrijft.
//              .requestMatchers("/**").hasAnyRole("USER", "ADMIN")

                // Fallback
                .anyRequest().denyAll()

                .and()
                .addFilterBefore(
                        new JwtRequestFilter(jwtService, userDetailsService()),
                        UsernamePasswordAuthenticationFilter.class
                )
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        return http.build();
    }

}
