package br.com.start.meupet.common.security;

import br.com.start.meupet.common.security.jwt.AuthEntryPointJwt;
import br.com.start.meupet.common.security.jwt.AuthFilterToken;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableMethodSecurity
public class WebSecurityConfig {

    private final AuthEntryPointJwt unauthorizedHandler;

    public WebSecurityConfig(AuthEntryPointJwt unauthorizedHandler) {
        this.unauthorizedHandler = unauthorizedHandler;
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration)
            throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public AuthFilterToken authFilterToken() {
        return new AuthFilterToken();
    }

    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.cors(Customizer.withDefaults());
        http.csrf(csrf -> csrf.disable())
                .exceptionHandling(exception -> exception.authenticationEntryPoint(unauthorizedHandler))
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(
                                "/auth/**", "/authenticable/**", "/authenticable/confirmAccount/**", "/authenticable/createAccount/**",
                                "/user/**",
                                "/partner/**",
                                "/templates/**",
                                "/confirmacaoConta.html",
                                "/v3/api-docs",
                                "/v3/api-docs/**",
                                "/swagger-ui/**",
                                "/swagger-ui.html",
                                "/authenticable/getuser",
                                "/user/upload-image/**",
                                "/user/image/**",
                                "/doc",
                                "/password-recovery/**",
                                "/agendamento/partner/**",
                                "/agendamento/user/**",
                                "/agendamento/atendimento/**",
                                "/agendamento/atendimento/user/**",
                                "/authenticable/changePassword/**"
                                    ).permitAll()
                        .requestMatchers("/agendamento/**").authenticated()
                        .anyRequest().authenticated());
        http.addFilterBefore(authFilterToken(), UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }
}
