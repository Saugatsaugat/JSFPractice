package Filter;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author saugat
 */
@WebFilter(urlPatterns = {"/faces/view/*"})
public class LoginFilter implements Filter {

    private static final List<String> EXCLUDED_URL_PATTERNS = Arrays.asList(
            "/JSFProject-1.0/faces/view/UserUI/Home/home.xhtml",
            "/JSFProject-1.0/faces/view/FutsalSchedule/futsalScheduleDisplay.xhtml"
    );

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        String requestURI = req.getRequestURI();

        HttpServletResponse res = (HttpServletResponse) response;
        Long userId = (Long) req.getSession().getAttribute("userId");

        Boolean shouldApplyFilter = true;
        for (String excludedPattern : EXCLUDED_URL_PATTERNS) {
            if (requestURI.startsWith(excludedPattern)) {
                shouldApplyFilter = false;
                break;
            }
        }
        if (shouldApplyFilter) {

            if (userId != null) {
                chain.doFilter(request, response);

            } else {
                res.sendRedirect(req.getContextPath() + "/faces/view/UserUI/Home/home.xhtml");
            }
        } else {
            chain.doFilter(request, response);
        }

    }

    @Override
    public void destroy() {
    }

}
