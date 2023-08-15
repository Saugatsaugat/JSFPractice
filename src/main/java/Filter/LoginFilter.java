package Filter;

import java.io.IOException;
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
@WebFilter(urlPatterns = {"/faces/view/AdminUI/*","/faces/view/FutsalOwnerUI/*"})
public class LoginFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
        Long userId = (Long) req.getSession().getAttribute("userId");
        if (userId != null) {
            chain.doFilter(request, response);

        } else {

            res.sendRedirect(req.getContextPath() + "/faces/view/UserUI/Home/home.xhtml");
        }
    }

    @Override
    public void destroy() {
    }

}
