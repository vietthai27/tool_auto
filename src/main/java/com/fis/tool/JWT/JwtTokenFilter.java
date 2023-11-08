package com.fis.tool.JWT;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Service;
import org.springframework.web.filter.OncePerRequestFilter;

import com.fis.tool.ServiceImp.UserDetailsServiceImplement;


@Service
public class JwtTokenFilter extends OncePerRequestFilter {

	@Autowired
	JwtUtil jwt;

	@Autowired
	UserDetailsServiceImplement userSrv;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		String authorHeader = request.getHeader("Authorization");
		if (authorHeader == null || authorHeader.isEmpty() || !authorHeader.startsWith("Bearer")) {
			filterChain.doFilter(request, response);
			return;
		}
		String token = authorHeader.split(" ")[1].trim();
		if (!jwt.validate(token)) {
			filterChain.doFilter(request, response);
			return;
		}
		String username = jwt.getUsername(token);
		UserDetails userInfo = userSrv.loadUserByUsername(username);
		UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(username, null,
				userInfo.getAuthorities());
		authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
		SecurityContextHolder.getContext().setAuthentication(authToken);
		filterChain.doFilter(request, response);
	}

}
