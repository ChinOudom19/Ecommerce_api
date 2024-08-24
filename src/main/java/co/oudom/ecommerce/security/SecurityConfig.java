package co.oudom.ecommerce.security;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationProvider;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private final PasswordEncoder passwordEncoder;

    private final UserDetailsService userDetailsService;

    @Bean
    JwtAuthenticationProvider configJwtAuthenticationProvider(@Qualifier("refreshTokenJwtDecoder") JwtDecoder refreshTokenJwtDecoder) {
        return new JwtAuthenticationProvider(refreshTokenJwtDecoder);
    }

    @Bean
    DaoAuthenticationProvider configDaoAuthenticationProvider() {
        DaoAuthenticationProvider auth = new DaoAuthenticationProvider();
        auth.setUserDetailsService(userDetailsService);
        auth.setPasswordEncoder(passwordEncoder);
        return auth;
    }

    @Bean
    SecurityFilterChain configureApiSecurity(HttpSecurity http,
                                             @Qualifier("accessTokenJwtDecoder") JwtDecoder jwtDecoder
                                             ) throws Exception {

        // Endpoint security config
        http.authorizeHttpRequests(endpoint -> endpoint
                .requestMatchers("/api/v1/auth/**",
                        "/api/v1/upload/**",
                        "/upload/**")
                .permitAll()

                .requestMatchers(HttpMethod.POST, "/api/v1/address/**", "/api/v1/order-detail/**")
                .hasAuthority("SCOPE_CUSTOMER")

                .requestMatchers(HttpMethod.GET, "/api/v1/address/**", "/api/v1/order-detail/**")
                .hasAuthority("SCOPE_CUSTOMER")

                .requestMatchers(HttpMethod.PUT,"/api/v1/address/**")
                .hasAuthority("SCOPE_CUSTOMER")

                .requestMatchers(HttpMethod.PATCH,"/api/v1/order-detail/**")
                .hasAuthority("SCOPE_CUSTOMER")

                .requestMatchers(HttpMethod.DELETE,"/api/v1/order-detail/**","api/v1/address/**")
                .hasAuthority("SCOPE_CUSTOMER")

                .requestMatchers(HttpMethod.POST, "/api/v1/category/**", "/api/v1/product/**", "/api/v1/product-item/**")
                .hasAnyAuthority("SCOPE_MANAGER","SCOPE_ADMIN")

                .requestMatchers(HttpMethod.GET, "/api/v1/category/**", "/api/v1/product/**", "/api/v1/product-item/**")
                .hasAnyAuthority("SCOPE_CUSTOMER", "SCOPE_MANAGER", "SCOPE_ADMIN")

                .requestMatchers(HttpMethod.PUT,"/api/v1/category/**","/api/v1/product-item/**")
                .hasAnyAuthority("SCOPE_MANAGER","SCOPE_ADMIN")

                .requestMatchers(HttpMethod.PATCH,"/api/v1/category/**","/api/v1/product-item/**")
                .hasAnyAuthority("SCOPE_MANAGER","SCOPE_ADMIN")

                .requestMatchers(HttpMethod.PUT,"/api/v1/category/**","/api/v1/product/**","/api/v1/product-item/**")
                .hasAuthority("SCOPE_ADMIN")


                .anyRequest().authenticated()
        );

        // Security Mechanism (HTTP Basic Auth)

        // HTTP Basic Auth (Username & Password)
        // http.httpBasic(Customizer.withDefaults());

        // Security Mechanism (JWT)
        http.oauth2ResourceServer(jwt -> jwt
                .jwt(jwtConfigurer -> jwtConfigurer
                        .decoder(jwtDecoder))
        );


        // Disable CSRF (Cross Site Request Forgery) Token
        http.csrf(AbstractHttpConfigurer::disable);

        // Make Stateless Session
        http.sessionManagement(session -> session
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        return http.build();
    }

}
