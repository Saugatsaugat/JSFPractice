package Filter;

import Entities.User;
import Model.UserCrud;
import com.saugat.bean.enums.UserType;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import javax.inject.Inject;
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

    @Inject
    private UserCrud userCrud;

    private static final List<String> EXCLUDED_URL_PATTERNS = Arrays.asList(
            "/JSFProject-1.0/faces/view/UserUI/Home/home.xhtml",
            "/JSFProject-1.0/faces/view/FutsalSchedule/futsalScheduleDisplay.xhtml",
            "/JSFProject-1.0/faces/view/Error/errorPage.xhtml",
            "/JSFProject-1.0/faces/view/Error/errorPageMain.xhtml"
    );

    private static final List<String> USER_URL_PATTERNS = Arrays.asList(
            "/JSFProject-1.0/faces/view/UserUI/Home/home.xhtml",
            "/JSFProject-1.0/faces/view/AdminUI/Home/bookingInformationTable.xhtml",
            "/JSFProject-1.0/faces/view/ChatWebSocket/chatPage.xhtml"
    );

    private static final List<String> FUTSAL_OWNER_URL_PATTERNS = Arrays.asList(
            "/JSFProject-1.0/faces/view/FutsalOwnerUI/Home/home.xhtml",
            "/JSFProject-1.0/faces/view/FutsalSchedule/futsalScheduleTable.xhtml",
            "/JSFProject-1.0/faces/view/AdminUI/Home/bookingInformationTable.xhtml",
            "/JSFProject-1.0/faces/view/ChatWebSocket/chatPage.xhtml"
    );

    private static final List<String> ADMIN_URL_PATTERNS = Arrays.asList(
            "/JSFProject-1.0/faces/view/AdminUI/Home/home.xhtml",
            "/JSFProject-1.0/faces/view/AdminUI/Home/userTable.xhtml",
            "/JSFProject-1.0/faces/view/AdminUI/Home/futsalTable.xhtml",
            "/JSFProject-1.0/faces/view/AdminUI/Home/bookingInformationTable.xhtml",
            "/JSFProject-1.0/faces/view/AdminUI/Home/aclTable.xhtml",
            "/JSFProject-1.0/faces/view/ChatWebSocket/chatPage.xhtml"
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
                //Role Based Filter
                User user = userCrud.getDataById(userId);
                UserType userType = user.getUsertype();
                if (userType.equals(UserType.admin)) {
                    Boolean adminApplyFilter = true;
                    for (String adminPattern : ADMIN_URL_PATTERNS) {
                        if (requestURI.startsWith(adminPattern)) {
                            adminApplyFilter = false;
                            break;
                        }
                    }
                    if (adminApplyFilter) {
                        res.sendRedirect(req.getContextPath() + "/faces/view/Error/errorPage.xhtml");
                    } else {
                        chain.doFilter(request, response);
                    }

                } else if (userType.equals(UserType.futsalowner)) {
                    //start

                    Boolean futsalOwnerApplyFilter = true;
                    for (String futsalOwnerPattern : FUTSAL_OWNER_URL_PATTERNS) {
                        if (requestURI.startsWith(futsalOwnerPattern)) {
                            futsalOwnerApplyFilter = false;
                            break;
                        }
                    }
                    if (futsalOwnerApplyFilter) {
                        res.sendRedirect(req.getContextPath() + "/faces/view/Error/errorPage.xhtml");
                    } else {
                        chain.doFilter(request, response);
                    }

                    //end
                } else if (userType.equals(UserType.user)) {
                    //start
                    Boolean userApplyFilter = true;
                    for (String userPattern : USER_URL_PATTERNS) {
                        if (requestURI.startsWith(userPattern)) {
                            userApplyFilter = false;
                            break;
                        }
                    }
                    if (userApplyFilter) {
                        res.sendRedirect(req.getContextPath() + "/faces/view/Error/errorPage.xhtml");
                    } else {
                        chain.doFilter(request, response);
                    }
                    //end
                } else {

                }

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
