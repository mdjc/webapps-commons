package com.github.mdjc.commons.webapps.angularjs;

import javax.servlet.http.HttpServletResponse;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.csrf.CsrfFilter;

import com.github.mdjc.commons.webapps.spring.security.CsrfCookieAppenderFilter;
import com.github.mdjc.commons.webapps.spring.security.StatusCodeOnlyLogoutSuccessHandler;

public interface AngularJsUtils {
	public static HttpSecurity configure(HttpSecurity http) throws Exception {
		http.httpBasic().and()
				.addFilterAfter(new CsrfCookieAppenderFilter("XSRF-TOKEN"), CsrfFilter.class)
				.logout()
				.logoutSuccessHandler(new StatusCodeOnlyLogoutSuccessHandler(HttpServletResponse.SC_RESET_CONTENT));
		return http;
	}
}