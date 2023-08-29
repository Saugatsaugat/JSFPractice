package Filter;

import Entities.User;
import Model.UserCrud;
import com.saugat.beans.UserBean;
import com.saugat.services.ResponseMessage;
import com.saugat.services.TokenManager;
import java.io.IOException;
import javax.annotation.Priority;
import javax.inject.Inject;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;

/**
 *
 * @author saugat
 */
@Provider
@Priority(1)
public class AuthorizationFilter implements ContainerRequestFilter {

    @Inject
    private UserCrud userCrud;

    @Inject
    private UserBean userBean;

    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {
        String requestPath = requestContext.getUriInfo().getPath();
        String authHeader = requestContext.getHeaderString(HttpHeaders.AUTHORIZATION);
        if ((requestPath.equals("/login"))||(requestPath.contains("/khaltiPay"))) {
            return;
        } else {
            if (authHeader != null && authHeader.startsWith("Bearer ")) {
                String token = authHeader.substring("Bearer".length()).trim();
                try {
                    String userId = TokenManager.verifyToken(token);
                    if (userId != null) {
                        User user = userCrud.getDataById(Long.valueOf(userId));
                        userBean.setUser(user);
                        return;
                    } else {
                        ResponseMessage responseMessage = new ResponseMessage("UNAUTHORIZED", "401", "Login Required", "");
                        requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED)
                                .entity(responseMessage)
                                .build());
                    }
                } catch (Exception e) {
                    ResponseMessage responseMessage = new ResponseMessage("UNAUTHORIZED", "401", "Login Required", "");
                    requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED)
                            .entity(responseMessage)
                            .build());
                }
            }
        }
        ResponseMessage responseMessage = new ResponseMessage("UNAUTHORIZED", "401", "Login Required", "");
        requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED)
                .entity(responseMessage)
                .build());
    }
}
