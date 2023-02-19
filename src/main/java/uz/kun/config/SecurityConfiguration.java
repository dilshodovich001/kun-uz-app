package uz.kun.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {

    private final JwtAuthenticationFilter jwtAuthFilter;
    private final AuthenticationProvider authenticationProvider;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeHttpRequests()
                .requestMatchers("/api/v1/auth/**").permitAll()
                .requestMatchers("/api/v1/article/public/**").permitAll()
                .requestMatchers("/api/v1/article/private/**").hasRole("ADMIN")
                .requestMatchers("/api/v1/article-type/public**", "/api/v1/article-type/public/**").permitAll()
                .requestMatchers("/api/v1/attach/public/**").permitAll()
                .requestMatchers("/api/v1/attach/private/**").hasRole("ADMIN")
                .requestMatchers("/api/v1/category/public/**").permitAll()
                .requestMatchers("/api/v1/category/private/**").hasRole("ADMIN")
                .requestMatchers("/api/v1/comment/public/**").permitAll()
                .requestMatchers("/api/v1/comment/private/**").hasRole("ADMIN")
                .requestMatchers("/api/v1/like/public/**").permitAll()
                .requestMatchers("/api/v1/like/private/**").hasRole("ADMIN")
                .requestMatchers("/api/v1/region/public/**").permitAll()
                .requestMatchers("/api/v1/region/private/**").hasRole("ADMIN")
                .requestMatchers("/api/v1/tag/**").hasRole("ADMIN")
                .requestMatchers("/api/v1/user/**").hasRole("ADMIN")
                .anyRequest().authenticated()
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

    /** =====
     * http
            .csrf()
            .disable()
            .authorizeHttpRequests()
            .anyRequest()
            .permitAll(); ===== */

        return http.build();
    }
}
