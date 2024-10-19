package com.techtech.security;


import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;


public class AuthTokenFilter extends OncePerRequestFilter {
 
  private static final Logger logger = LoggerFactory.getLogger(AuthTokenFilter.class);
  
  @Autowired
  private UserDetailsService userDetailsService;

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
      throws ServletException, IOException {
		try {
			//Authorization = Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJqYWNrQGdtYWlsLmNvbSIsImNvbXBhbnkiOiJBYmNUZWNoIiwic2NvcGVzIjpbImN1c3RvbWVyIl0sImlhdCI6MTcyOTI5NTgyNiwiZXhwIjoxNzI5Mjk3NjI2fQ.nHqqH2CvdaYm-GWfS6dyhSpkJZ4KDsoFkdRsqGtBiiiq1h9YfrA2FDBFhUWliKForbg1Clbwlo3ZE1J7Blq7Pg
			//jwt =eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJqYWNrQGdtYWlsLmNvbSIsImNvbXBhbnkiOiJBYmNUZWNoIiwic2NvcGVzIjpbImN1c3RvbWVyIl0sImlhdCI6MTcyOTI5NTgyNiwiZXhwIjoxNzI5Mjk3NjI2fQ.nHqqH2CvdaYm-GWfS6dyhSpkJZ4KDsoFkdRsqGtBiiiq1h9YfrA2FDBFhUWliKForbg1Clbwlo3ZE1J7Blq7Pg
			String jwt = parseJwt(request);
			if (jwt != null && validateJwtToken(jwt)) {
				String email = getEmailFromJwtToken(jwt);
			   UserDetails userDetails=userDetailsService.loadUserByUsername(email);
        	   UsernamePasswordAuthenticationToken authentication =
        	            new UsernamePasswordAuthenticationToken(
        	                userDetails,
        	                null,
        	                null);
        	 ///SPRING SECURITY AUTHENTICATED YOU NOW GO AHEAD
        	 SecurityContextHolder.getContext().setAuthentication(authentication);
        }
    } catch (Exception e) {
      logger.error("Cannot set user authentication: {}", e);
    }

    filterChain.doFilter(request, response);
  }
  
  
  public String getEmailFromJwtToken(String token) {
		return Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody().getSubject();
	}

	public List<String> getRolesFromJwtToken(String token) {
		return (List<String>)Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody().get("scopes");
	}
  
	
   @Value("${jwt.secret.key:ABUE87%&$&##@)@(&@*^@^@@@}")
   private String jwtSecret;
  
  private boolean validateJwtToken(String token) {
	 //code to validate token
	  try {
			Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token);
			return true;
		} catch (SignatureException e) {
			logger.error("Invalid JWT signature: {}", e.getMessage());
		} catch (MalformedJwtException e) {
			logger.error("Invalid JWT token: {}", e.getMessage());
		} catch (ExpiredJwtException e) {
			logger.error("JWT token is expired: {}", e.getMessage());
		} catch (UnsupportedJwtException e) {
			logger.error("JWT token is unsupported: {}", e.getMessage());
		} catch (IllegalArgumentException e) {
			logger.error("JWT claims string is empty: {}", e.getMessage());
		}

		return false;
  }
  private String parseJwt(HttpServletRequest request) {
    String headerAuth = request.getHeader("Authorization");
    if (StringUtils.hasText(headerAuth) && headerAuth.startsWith("Bearer ")) {
      return headerAuth.substring(7, headerAuth.length());
    }
    return null;
  }
}