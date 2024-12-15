package Christian.auca.rw.AssignmentSubmissionApp.Config;

import java.io.IOException;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

import Christian.auca.rw.AssignmentSubmissionApp.service.*;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private JwtService jwtService;

    @Autowired
    private UserService userService;

    private static final String[] WHITE_LIST_URL = {
      "/api/users/login",
      "/api/users/signup",
      "/api/cohorts/save",
      "/api/instructors",
      "/api/students",
      "/api/cohorts/**",
      "/5173/studentinfo",
      "5173/instructorinfo",
      "api/cohorts/findAll",
      "/api/users/saveUser",
      "/api/v1/user/add-user", 
          "/api/v1/user/login",
          "/swagger-ui/index.html/api/v1/user/logout",
          "/api/v1/user/search-user",
          "/api/v1/user/complete-activation",
          "/api/v1/user/session",
          "/v2/api-docs",
        "/v3/api-docs",
        "/v3/api-docs/**",
          "/swagger-ui/index.html",
          "/configuration/ui",
          "/configuration/security",
          "/swagger-ui/**",
          
    };

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String requestPath = request.getRequestURI();

        // Skip filter for whitelisted URLs
        if (isWhitelisted(requestPath)) {
            filterChain.doFilter(request, response);
            return;
        }

        try {
            String jwt = getTokenFromCookies(request);
            System.out.println("check token: "+ jwt);

            if (jwt == null) {
                sendUnauthorizedResponse(response, "JWT token is missing....");
                return;
            }

            String userEmail = jwtService.getUsername(jwt);
            if (userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                UserDetails userDetails = userService.loadUserByUsername(userEmail);

                if (jwtService.isTokenValid(jwt, userDetails)) {
                    UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userDetails,
                            null, userDetails.getAuthorities());
                    authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authToken);
                } else {
                    sendUnauthorizedResponse(response, "Invalid JWT token.");
                    return;
                }
            }
            filterChain.doFilter(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            sendUnauthorizedResponse(response, "Unauthorized:........" + e.getMessage());
        }
    }

    private boolean isWhitelisted(String requestPath) {
        return Arrays.stream(WHITE_LIST_URL)
                .anyMatch(url -> url.endsWith("/**")
                        ? requestPath.startsWith(url.substring(0, url.length() - 3))
                        : requestPath.equals(url));
    }

    private String getTokenFromCookies(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("token".equals(cookie.getName())) {
                    return cookie.getValue();
                }
            }
        }
        return null;
    }

    private void sendUnauthorizedResponse(HttpServletResponse response, String message) throws IOException {
        SecurityContextHolder.clearContext();
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json");
        response.getWriter().write("{\"error\": \"" + message + "\"}");
    }

}