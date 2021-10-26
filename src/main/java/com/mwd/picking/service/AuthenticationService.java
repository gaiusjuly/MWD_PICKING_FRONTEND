package com.mwd.picking.service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static java.util.Collections.emptyList;

public class AuthenticationService {
  static final long EXPIRATIONTIME = 864_000_00; // 1 day in milliseconds
  static final String SIGNINGKEY = "SecretKey";
  static final String PREFIX = "Bearer";

  static public void addToken(HttpServletResponse res, String username) {
   // System.err.println("addToken called");
//    res.setContentType("text/html;charset=UTF-8");
	String JwtToken = Jwts.builder().setSubject(username)
        .setExpiration(new Date(System.currentTimeMillis() + EXPIRATIONTIME))
        .signWith(SignatureAlgorithm.HS512, SIGNINGKEY)
        .compact();
//System.err.println("JwtToken "+ JwtToken);
//    res.setHeader("Content-Type", "text/plain");
//    res.setContentType("text/plain;charset=UTF-8");
//    res.setCharacterEncoding("UTF-8");
    res.addHeader("Authorization", PREFIX + " " + JwtToken);
    //Authorization
    res.addHeader("Access-Control-Expose-Headers", "*");
//    res.addHeader("username",username);


//    String jsonStr = "{\"username\": \""+username+"\"}";
//    res.getWriter().println(jsonStr);
//    res.getWriter().flush();
  }

  static public Authentication getAuthentication(HttpServletRequest request) {
   // System.err.println("getAuthentication");
    String token = request.getHeader("Authorization");
   // System.err.println("token "+token);
    if (token != null) {
      String user = Jwts.parser()
          .setSigningKey(SIGNINGKEY)
          .parseClaimsJws(token.replace(PREFIX, ""))
          .getBody()
          .getSubject();

      if (user != null) 
    	  return new UsernamePasswordAuthenticationToken(user, null, emptyList());
    }
    return null;
  }
}