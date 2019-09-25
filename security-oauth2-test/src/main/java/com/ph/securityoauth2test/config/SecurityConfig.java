package com.ph.securityoauth2test.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * @PACKAGE_NAME: com.ph.securityoauth2test.config
 * @NAME: SecurityConfig
 * @Description:
 * @Author ph
 * @DATE: 2019-08-23 10:29
 **/
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.inMemoryAuthentication()
				.passwordEncoder(passwordEncoder())
				.withUser("ph")
				.password(passwordEncoder().encode("123"))
				.roles("USER");
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf()
				.disable();
		http.requestMatchers()
				.antMatchers("/oauth/**", "/login/**", "/logout/**")
				.and()
				.authorizeRequests()
				.antMatchers("/oauth/**")
				.authenticated()
				.and()
				.formLogin()
				.permitAll();
	}

	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring()
				.antMatchers("/oauth/token");
	}

	//@Bean
	//@Override
	//protected UserDetailsService userDetailsService() {
	//	InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
	//	manager.createUser(User.withUsername("ph").password("123").authorities("USER").build());
	//	manager.createUser(User.withUsername("admin").password("$2a$10$0kEGM/4vMEqgup/LzPtbEeL2TiBaGiY5EoTyY.4oPvHIzzki0Ag5.").authorities("USER").build());
	//	return manager;
	//}

	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

	public static void main(String[] args) {
		String pwd = "123";
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		// 加密
		String encodedPassword = passwordEncoder.encode(pwd);
		System.out.println(encodedPassword);
	}
}











