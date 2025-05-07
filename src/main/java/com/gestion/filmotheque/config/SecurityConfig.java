package com.gestion.filmotheque.config;

import com.gestion.filmotheque.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final UserService userService;

    @Autowired
    public SecurityConfig(UserService userService) {
        this.userService = userService;
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider(BCryptPasswordEncoder passwordEncoder) {
        DaoAuthenticationProvider auth = new DaoAuthenticationProvider();
        auth.setUserDetailsService(userService);
        auth.setPasswordEncoder(passwordEncoder);
        return auth;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth
                        // Public resources - accessible to all
                        .requestMatchers(
                                "/",
                                "/film/all",
                                "/film/details/**",
                                "/film/search/**",
                                "/film/filter/**",
                                "/acteur/all",
                                "/categorie/all",
                                "/registration/**",
                                "/login/**",
                                "/js/**",
                                "/css/**",
                                "/img/**",
                                "/uploads/**",
                                "/webjars/**")
                        .permitAll()
                        // Admin-only operations
                        .requestMatchers(
                                "/film/new",
                                "/film/add",
                                "/film/edit/**",
                                "/film/update/**",
                                "/film/delete/**",
                                "/acteur/new",
                                "/acteur/add",
                                "/acteur/edit/**",
                                "/acteur/update/**",
                                "/acteur/delete/**",
                                "/categorie/new",
                                "/categorie/add",
                                "/categorie/edit/**",
                                "/categorie/update/**",
                                "/categorie/delete/**")
                        .hasRole("ADMIN")
                        // Dashboard - for authenticated users only
                        .requestMatchers("/dashboard/**")
                        .authenticated()
                        // Authenticated users can access other pages
                        .anyRequest().authenticated())
                .formLogin(form -> form
                        .loginPage("/login")
                        .defaultSuccessUrl("/dashboard", true)
                        .permitAll())
                .logout(logout -> logout
                        .invalidateHttpSession(true)
                        .clearAuthentication(true)
                        .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                        .logoutSuccessUrl("/login?logout")
                        .permitAll());

        return http.build();
    }
}