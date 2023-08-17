package com.saugat.interceptors;

import Entities.User;
import Model.UserActionResourceCrud;
import Model.UserCrud;
import com.saugat.messageGeneration.ValidationMessageGenerationUtil;
import java.io.Serializable;
import java.lang.reflect.Method;
import javax.annotation.Priority;
import javax.enterprise.context.Dependent;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;
import javax.servlet.http.HttpSession;

/**
 *
 * @author saugat
 */
@Interceptor
@Acl
@Dependent
@Priority(Interceptor.Priority.APPLICATION)
public class AclInterceptor implements Serializable {

    @Inject
    private UserActionResourceCrud userActionResourceCrud;
    @Inject
    private UserCrud userCrud;

    @AroundInvoke
    public Object checkAcl(InvocationContext context) throws Exception {
        System.out.println("Interceptor class");

        Method m = context.getMethod();
        Acl aclCheckAnnotationData = m.getAnnotation(Acl.class);

        if (aclCheckAnnotationData == null) {
            return context.proceed();
        } else {
            String resource = aclCheckAnnotationData.resourceName().getLabel();
            String action = aclCheckAnnotationData.actionName().getLabel();

            HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
            if (session.getAttribute("userId") != null) {
                User user = userCrud.getDataById((Long) session.getAttribute("userId"));
                if (user != null) {

                    Boolean status = userActionResourceCrud.checkIfExistsByAclDetails(resource, user.getUsertype().toString(),
                            action);
                    if (status) {
                        return context.proceed();
                    } else {
                        ValidationMessageGenerationUtil.validationMessageGeneration("Permission Denied.", "error");
                        return null;
                    }
                } else {
                    ValidationMessageGenerationUtil.validationMessageGeneration("Permission Denied.", "error");
                    return null;
                }
            } else {
                ValidationMessageGenerationUtil.validationMessageGeneration("Permission Denied.", "error");
                return null;

            }

        }

    }
}
