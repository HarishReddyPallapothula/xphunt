package studio.contrarian.xphunt.auth.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import studio.contrarian.xphunt.auth.service.CustomUserDetailsService;
import studio.contrarian.xphunt.auth.service.JwtTokenProvider;
import java.io.IOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    // --- ADD A LOGGER INSTANCE ---
    private static final Logger logger = LoggerFactory.getLogger(JwtAuthenticationFilter.class);

    private final JwtTokenProvider tokenProvider;
    private final CustomUserDetailsService customUserDetailsService;

    public JwtAuthenticationFilter(JwtTokenProvider tokenProvider, CustomUserDetailsService customUserDetailsService) {
        this.tokenProvider = tokenProvider;
        this.customUserDetailsService = customUserDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        logger.info("Processing request to: {}", request.getRequestURI());

        try {
            String jwt = getJwtFromRequest(request);

            // LOGGING POINT 1
            if (StringUtils.hasText(jwt)) {
                logger.info("JWT found in request header.");

                // LOGGING POINT 2
                if (tokenProvider.validateToken(jwt)) {
                    logger.info("JWT validation successful." +jwt);
                    Long userId = tokenProvider.getUserIdFromJWT(jwt);
                    logger.info("User ID from JWT: {}", userId);

                    UserDetails userDetails = customUserDetailsService.loadUserById(userId); // Assuming you add this method
                    logger.info("UserDetails loaded for user: {}", userDetails.getUsername());

                    UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                            userDetails, null, userDetails.getAuthorities());

                    SecurityContextHolder.getContext().setAuthentication(authentication);
                    logger.info("Authentication object set in SecurityContext.");
                } else {
                    logger.warn("Invalid JWT token: '"+jwt+"'");
                }
            } else {
                logger.info("No JWT found in request header.");
            }
        } catch (Exception ex) {
            // LOGGING POINT 3: This is critical
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