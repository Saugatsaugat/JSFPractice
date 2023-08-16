package Interceptors;

import Model.UserActionResourceCrud;
import com.saugat.beans.UserBean;
import java.io.Serializable;
import javax.annotation.Priority;
import javax.inject.Inject;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;

/**
 *
 * @author saugat
 */
@AclCheck(action = "",resource = "")
@Interceptor
@Priority(Interceptor.Priority.APPLICATION)
public class AclInterceptor implements Serializable {

    @Inject
    private UserActionResourceCrud userActionResourceCrud;

    @AroundInvoke
    public Object checkAcl(InvocationContext context) throws Exception {
        AclCheck aclCheckAnnotationData = context.getMethod().getAnnotation(AclCheck.class);
        Object[] parameters = context.getParameters();
        if (aclCheckAnnotationData == null) {
            return context.proceed();
        } else {
            String resource = aclCheckAnnotationData.resource();
            String action = aclCheckAnnotationData.action();

            String userType = new UserBean().getUser().getUsertype().toString();

            Boolean status = userActionResourceCrud.checkIfExistsByAclDetails(resource, userType, action);
            if (status) {
                return context.proceed();
            } else {
                throw new SecurityException("Permission Denied");
            }
        }

    }
}
