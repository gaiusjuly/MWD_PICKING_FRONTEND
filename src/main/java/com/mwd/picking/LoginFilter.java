package com.mwd.picking;

import java.io.IOException;
import java.util.Collections;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mwd.picking.model.AccountCredentials;
import com.mwd.picking.service.AuthenticationService;

public class LoginFilter extends AbstractAuthenticationProcessingFilter {

  public LoginFilter(String url, AuthenticationManager authManager) {
    super(new AntPathRequestMatcher(url));
      //System.err.println("LoginFilter "+ url);
    setAuthenticationManager(authManager);
  }

  @Override
  public Authentication attemptAuthentication(
	HttpServletRequest req, HttpServletResponse res)
			throws AuthenticationException, IOException, ServletException {
      System.err.println("attemptAuthentication");
	AccountCredentials creds = new ObjectMapper()
        .readValue(req.getInputStream(), AccountCredentials.class);
	//System.err.println("creds userid "+creds.getUserId());
	//System.err.println("cres userpassword "+ creds.getPassword());
	return getAuthenticationManager().authenticate(
        new UsernamePasswordAuthenticationToken(
            creds.getUserId(),
            creds.getPassword(),
            Collections.emptyList()
        )
    );
  }

  @Override
  protected void successfulAuthentication(
      HttpServletRequest req,
      HttpServletResponse res, FilterChain chain,
      Authentication auth) throws IOException, ServletException {
      //System.err.println("sucessfulAuthentication");
	  AuthenticationService.addToken(res, auth.getName());
  }
}
