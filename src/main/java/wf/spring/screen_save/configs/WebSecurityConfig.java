package wf.spring.screen_save.configs;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.server.resource.web.authentication.BearerTokenAuthenticationFilter;
import org.springframework.security.web.SecurityFilterChain;
import wf.spring.screen_save.security.JwtAuthenticationManager;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfig {


    private final JwtAuthenticationManager jwtAuthenticationManager;



    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable)
                .sessionManagement(management -> management.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(registry -> registry
                        .requestMatchers("/api/screen/resource/*").permitAll()
                        .requestMatchers("/api/auth/**").permitAll()
                        .requestMatchers("/api/**").authenticated()
                        .anyRequest().denyAll())

                .addFilterBefore(jwtAuthenticationFilter(), BearerTokenAuthenticationFilter.class);


        return http.build();
    }

    @Bean
    public BearerTokenAuthenticationFilter jwtAuthenticationFilter() {
        return new BearerTokenAuthenticationFilter(jwtAuthenticationManager);
    }

    @Bean
    public static PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }


}
