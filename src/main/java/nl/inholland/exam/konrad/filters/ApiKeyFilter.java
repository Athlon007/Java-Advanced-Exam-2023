package nl.inholland.exam.konrad.filters;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class ApiKeyFilter extends OncePerRequestFilter {
    public static final String KEY = "12345";

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // API key goes through bearer token.
        // Retrieve it from the Authorization header.
        String apiKey = getToken(request);

        if (apiKey == null || !apiKey.equals(KEY)) {
            // If the API key is invalid, return a 403 Forbidden error.
            response.sendError(HttpServletResponse.SC_FORBIDDEN, "Invalid API key");
            return;
        }

        // Otherwise proceed with the request.
        filterChain.doFilter(request, response);
    }

    /**
     * Retrieves the token from the Authorization header.
     * @param request The request to retrieve the token from.
     * @return The token, or null if it was not found.
     */
    private String getToken(HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        if (token == null) {
            return null;
        }

        return token.replace("Bearer ", "");
    }
}
