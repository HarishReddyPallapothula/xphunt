package studio.contrarian.xphunt.auth.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import studio.contrarian.xphunt.auth.service.CustomUserDetailsService;
import studio.contrarian.xphunt.auth.service.JwtTokenProvider;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private static final Logger logger = LoggerFactory.getLogger(JwtAuthenticationFilter.class);

    private final JwtTokenProvider tokenProvider;
    private final CustomUserDetailsService customUserDetailsService;

    private static final List<String> PUBLIC_PATHS = Arrays.asList(
            "/api/auth/",
            "/swagger-ui",
            "/v3/api-docs"
    );

    public JwtAuthenticationFilter(JwtTokenProvider tokenProvider, CustomUserDetailsService customUserDetailsService) {
        this.tokenProvider = tokenProvider;
        this.customUserDetailsService = customUserDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        final String requestURI = request.getRequestURI();
        logger.info("Processing request to: {}", requestURI);

        boolean isPublicPath = PUBLIC_PATHS.stream().anyMatch(requestURI::startsWith);
        if (isPublicPath) {
            logger.debug("Request to public path '{}' - skipping JWT validation.", requestURI);
            filterChain.doFilter(request, response);
            return;
        }

        try {
            String jwt = getJwtFromRequest(request);

            if (StringUtils.hasText(jwt)) {
                logger.debug("JWT found in request header for protected path.");

                if (tokenProvider.validateToken(jwt)) {
                    logger.debug("JWT validation successful.");
                    Long userId = tokenProvider.getUserIdFromJWT(jwt);
                    logger.debug("User ID from JWT: {}", userId);

                    UserDetails userDetails = customUserDetailsService.loadUserById(userId);
                    logger.debug("UserDetails loaded for user: {}", userDetails.getUsername());

                    UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                            userDetails, null, userDetails.getAuthorities());

                    SecurityContextHolder.getContext().setAuthentication(authentication);
                    logger.info("Successfully authenticated user '{}' for request to '{}'", userDetails.getUsername(), requestURI);
                } else {
                    logger.warn("Invalid JWT token provided for request to '{}'", requestURI);
                }
            } else {
                logger.info("No JWT found in request header for protected path: {}", requestURI);
            }
        } catch (Exception ex) {
            logger.error("Could not set user authentication in security context", ex);
        }

        filterChain.doFilter(request, response);
    }

    private String getJwtFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }
}