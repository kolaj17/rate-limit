package al.lhind.tab.claim.filter;

import al.lhind.tab.claim.dto.ClaimError;
import al.lhind.tab.claim.util.ClaimIpValidator;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@Slf4j
public class ClaimFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        if (request.getRequestURI().startsWith("/api/v1/claims") &&
                "POST".equalsIgnoreCase(request.getMethod()) &&
                !ClaimIpValidator.validate(request.getRemoteAddr())) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                ObjectMapper objectMapper = new ObjectMapper();
                response.getWriter().write(objectMapper.writeValueAsString(ClaimError.of("Access denied for this IP")));
                response.getWriter().flush();
                return;
            }

        filterChain.doFilter(request, response);
    }
}
