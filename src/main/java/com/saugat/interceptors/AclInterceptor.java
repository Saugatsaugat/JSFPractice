package com.saugat.interceptors;

import Model.UserActionResourceCrud;
import com.saugat.beans.UserBean;
import java.io.Serializable;
import java.lang.reflect.Method;
import javax.annotation.Priority;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;

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

    @AroundInvoke
    public Object checkAcl(InvocationContext context) throws Exception {
        System.out.println("Interceptor class");
        
        Method m = context.getMethod();
        Acl aclCheckAnnotationData = m.getAnnotation(Acl.class);
        
        if (aclCheckAnnotationData == null) {
            return context.proceed();
        } else {
                String resource = aclCheckAnnotationData.resource_name();
            String action = aclCheckAnnotationData.action_name();

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
