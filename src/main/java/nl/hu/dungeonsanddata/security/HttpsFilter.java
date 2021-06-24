package nl.hu.dungeonsanddata.security;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter(urlPatterns = "/*")
public class HttpsFilter extends HttpFilter {
    public static final String X_FORWARDED_PROTO = "X-Forwarded-Proto";
    public static final String HSTS = "Strict-Transport-Security";

    @Override
    public void doFilter(HttpServletRequest request, HttpServletResponse response,
                         FilterChain filterChain) throws IOException, ServletException {

        if (request.getHeader(X_FORWARDED_PROTO) != null) {
            if (request.getHeader(X_FORWARDED_PROTO).equals("http")) {
                String requestedURL = request.getRequestURL().toString();
                response.sendRedirect(requestedURL.replace("http://", "https://"));
            } else {
                response.setHeader(HSTS, "max-age=31536000; includeSubDomains");
            }
        }

        filterChain.doFilter(request, response);
    }
}
