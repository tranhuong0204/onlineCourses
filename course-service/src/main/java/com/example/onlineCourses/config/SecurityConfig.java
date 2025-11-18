package com.example.onlineCourses.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

//@EnableWebSecurity
@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth
                        .anyRequest().permitAll()
                )
                .csrf(csrf -> csrf.disable()); // ✅ cú pháp mới, không deprecated
        return http.build();
    }
//@Bean
//public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//    http
//            .csrf(csrf -> csrf.disable())
//            .authorizeHttpRequests(auth -> auth
//                    .requestMatchers("/auth/**", "/public/**").permitAll()
//                    .requestMatchers("/courses/**").hasAnyRole("USER","STAFF","MANAGER")
//                    .requestMatchers("/staff/**").hasAnyRole("STAFF","MANAGER")
//                    .requestMatchers("/manager/**").hasRole("MANAGER")
//                    .anyRequest().authenticated()
//            )
//            .formLogin(form -> form
//                    .loginPage("/auth/login").permitAll()
//                    .defaultSuccessUrl("/home", true)
//            )
//            .logout(logout -> logout.logoutUrl("/auth/logout").permitAll());
//    return http.build();
//}
//
//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder();
//    }
}