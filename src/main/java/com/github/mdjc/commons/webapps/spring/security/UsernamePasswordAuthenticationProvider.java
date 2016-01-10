package com.github.mdjc.commons.webapps.spring.security;

import java.util.Collections;
import java.util.function.Function;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

public class UsernamePasswordAuthenticationProvider implements AuthenticationProvider {
	private final Function<String, String> passwordFunction;

	public UsernamePasswordAuthenticationProvider(Function<String, String> passwordFunction) {
		this.passwordFunction = passwordFunction;
	}

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		UsernamePasswordAuthenticationToken token = (UsernamePasswordAuthenticationToken) authentication;
		String username = token.getName();
		String plainPassword = (String) token.getCredentials();

		PasswordEncoder encoder = new BCryptPasswordEncoder();

		if (!encoder.matches(plainPassword, passwordFunction.apply(username))) {
			reject();
		}

		return new UsernamePasswordAuthenticationToken(username, "", Collections.<GrantedAuthority> emptyList());
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
	}

	private static void reject() {
		throw new BadCredentialsException("Invalid username or password");
	}
}