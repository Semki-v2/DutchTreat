package org.semki.dutchtreat.config.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private AuthSuccess authSuccessHandler;
	
	@Autowired
	private AuthFailure authFailureHandler;
	
	@Autowired
	private EntryPointUnauthorizedHandler entryPointUnauthrizedHandler;
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable().exceptionHandling().authenticationEntryPoint(entryPointUnauthrizedHandler)
						.and()
						.formLogin().successHandler(authSuccessHandler).failureHandler(authFailureHandler)
						.and()
						.authorizeRequests()
						.antMatchers("/app/index.html",
									 "/app/login.html",
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
		builder.inMemoryAuthentication()
				.withUser("user").password("password").roles("USER");
	}
	
}
