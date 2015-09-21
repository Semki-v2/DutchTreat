package org.semki.dutchtreat.config.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetails;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private AuthSuccess authSuccessHandler;
	
	@Autowired
	private AuthFailure authFailureHandler;
	
	@Autowired
	private EntryPointUnauthorizedHandler entryPointUnauthrizedHandler;
	
	@Autowired
	private UserDetailsServiceImpl userDetailsService;
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable().exceptionHandling().authenticationEntryPoint(entryPointUnauthrizedHandler)
						.and()
						.formLogin().loginPage("/app/auth/login").successHandler(authSuccessHandler).failureHandler(authFailureHandler)
						.and()
						.authorizeRequests()
						.antMatchers("/app/index.html",
									 "/app/events/**",
									 "/app/auth/login",
									 "/app/auth/registration",
									 "/app/bower_components/**",
									 "/app/components/**",
									 "/app/views/**",
									 "/app/styles/**",
									 "/app/app.js",
									 "/app/app.css",
									 "/app/favicon.ico").permitAll()
						.anyRequest().authenticated();
	}
	
	@Autowired
	public void configurationBuilder(AuthenticationManagerBuilder builder) throws Exception
	{
		builder.userDetailsService(userDetailsService);
	}
	
}
