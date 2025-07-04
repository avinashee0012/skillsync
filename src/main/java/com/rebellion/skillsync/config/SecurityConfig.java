package com.rebellion.skillsync.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http.csrf(csrf -> csrf.disable())
				.authorizeHttpRequests((requests) -> requests
						.requestMatchers("/candidate/**").hasAnyRole("CANDIDATE", "ADMIN")
						.requestMatchers("/employer/**").hasAnyRole("EMPLOYER", "ADMIN")
						.requestMatchers("/admin/**").hasRole("ADMIN")
						.requestMatchers("/", "/register/**", "/login/**").permitAll()
						.anyRequest().authenticated())
				.formLogin((form) -> form.loginPage("/login").loginProcessingUrl("/login/auth")
						.successHandler((request, response, authentication) -> {
							String role = authentication.getAuthorities().iterator().next().getAuthority();
							if (role.equals("ROLE_CANDIDATE")) {
								response.sendRedirect("/candidate/dashboard");
							} else if(role.equals("ROLE_EMPLOYER")){
								response.sendRedirect("/employer/dashboard");
							} else if(role.equals("ROLE_ADMIN")) {
								response.sendRedirect("/admin/dashboard");
							} else {
								response.sendRedirect("/login");
							}
						}).permitAll())
				.logout((logout) -> logout.permitAll());

		return http.build();
	}

	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
		return config.getAuthenticationManager();
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}
