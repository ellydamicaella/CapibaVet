package br.com.start.meupet.common.security.jwt;

import br.com.start.meupet.user.service.AuthenticableDetailsServiceImpl;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class AuthFilterToken extends OncePerRequestFilter {

    private static final Logger log = LoggerFactory.getLogger(AuthFilterToken.class);
    @Autowired
    private JwtUtils jwtUtils;
    @Autowired
    private AuthenticableDetailsServiceImpl authenticableDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            String jwt = getToken(request);
            if (request.getRequestURI().startsWith("partner")) {
                filterChain.doFilter(request, response);
            }
            if (!(jwt != null && jwtUtils.validateJwtToken(jwt))) {
                log.error("Token nulo ou invalido: {}", jwt);
            } else {
                String username = jwtUtils.getUsernameToken(jwt);
                log.info("username: {}", username);
                UserDetails userDetails = authenticableDetailsService.loadUserByUsername(username);
                UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                auth.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                SecurityContextHolder.getContext().setAuthentication(auth);
                log.info("Token validado: {}", jwt);
            }
        } catch (Exception e) {
            log.error("Ocorreu um erro ao processar o token");
        }
        filterChain.doFilter(request, response);
    }

    private String getToken(HttpServletRequest request) {
        String headerToken = request.getHeader("Authorization");
        if (StringUtils.hasText(headerToken) && headerToken.startsWith("Bearer")) {
            return headerToken.replace("Bearer ", "");
        }
        return null;
    }
}
