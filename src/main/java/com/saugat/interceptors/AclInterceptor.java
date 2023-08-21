package com.saugat.interceptors;

import Entities.User;
import Model.UserActionResourceCrud;
import Model.UserCrud;
import com.saugat.bean.enums.ActionType;
import com.saugat.bean.enums.ResourceType;
import com.saugat.beans.UserBean;
import com.saugat.messageGeneration.ValidationMessageGenerationUtil;
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
    private UserBean userBean;

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
            ResourceType resourceType = (ResourceType) aclCheckAnnotationData.resourceName();
            ActionType actionType = (ActionType) aclCheckAnnotationData.actionName();

            //Rough
            //start
            User user = userBean.getUser();

            //end
            //    HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
            if (user.getId() != null) {

                Boolean status = userActionResourceCrud.checkIfExistsByAclDetail(resourceType, user.getUsertype(),
                        actionType);
                if (status) {
                    return context.proceed();
                } else {
           //         ValidationMessageGenerationUtil.validationMessageGeneration("Permission Denied.", "error");
                    System.out.println("Permission Denied");
                    return null;
                }
            } else {
                System.out.println("Permission Denied");
            //    ValidationMessageGenerationUtil.validationMessageGeneration("Permission Denied.", "error");
                return null;
            }
        }

    }

}
