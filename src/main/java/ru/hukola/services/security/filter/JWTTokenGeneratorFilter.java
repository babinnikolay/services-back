package ru.hukola.services.security.filter;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.FilterChainProxy;
import org.springframework.web.filter.OncePerRequestFilter;
import ru.hukola.services.model.User;
import ru.hukola.services.service.UserService;

import javax.crypto.SecretKey;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.UUID;

/**
 * @author Babin Nikolay
 */
@AllArgsConstructor
public class JWTTokenGeneratorFilter extends OncePerRequestFilter {

    private final UserService userService;
    private final String jwtKey;
    private final String applicationName;
    private final String tokenName;
    private final String jwtHeader;
    private final long tokenExpirationTime;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (null != authentication) {
            SecretKey key = Keys.hmacShaKeyFor(jwtKey.getBytes(StandardCharsets.UTF_8));
            User user = userService.findUserByName(authentication.getName());
            UUID uuid = user != null ? user.getUuid() : null;
            String email = user != null ? user.getEmail() : null;

            String jwt = Jwts.builder()
                    .issuer(applicationName)
                    .subject(tokenName)
                    .claim("username", authentication.getName())
                    .claim("email", email)
                    .claim("uuid", uuid)
                    .issuedAt(new Date())
                    .expiration(new Date((new Date()).getTime() + tokenExpirationTime))
                    .signWith(key).compact();
            response.setHeader(jwtHeader, jwt);
        }

        filterChain.doFilter(request, response);
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        return !request.getServletPath().equals("/login");
    }
}
