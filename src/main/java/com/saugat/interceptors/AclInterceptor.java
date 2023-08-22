package com.saugat.interceptors;

import Entities.User;
import Model.UserActionResourceCrud;
import Model.UserCrud;
import com.saugat.bean.enums.ActionType;
import com.saugat.bean.enums.ResourceType;
import com.saugat.beans.UserBean;
import com.saugat.messageGeneration.ValidationMessageGenerationUtil;
import com.saugat.services.ResponseMessage;
import java.io.Serializable;
import java.lang.reflect.Method;
import javax.annotation.Priority;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;
import javax.ws.rs.core.Response;

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

            User user = userBean.getUser();

            if (user.getId() != null) {

                Boolean status = userActionResourceCrud.checkIfExistsByAclDetail(resourceType, user.getUsertype(),
                        actionType);
                if (status) {
                    return context.proceed();
                } else {
                    try {
                        ValidationMessageGenerationUtil.validationMessageGeneration("Permission Denied.", "error");

                    } catch (Exception e) {
                        ResponseMessage responseMessage = new ResponseMessage("FORBIDDEN", "403", "Permission Denied", "");
                        return Response.status(Response.Status.FORBIDDEN)
                                .entity(responseMessage)
                                .build();
                    }

                }
            } else {
                try {
                    ValidationMessageGenerationUtil.validationMessageGeneration("Permission Denied.", "error");

                } catch (Exception e) {
                    ResponseMessage responseMessage = new ResponseMessage("FORBIDDEN", "403", "Permission Denied", "");
                    return Response.status(Response.Status.FORBIDDEN)
                            .entity(responseMessage)
                            .build();
                }
            }
        }
        return null;
    }

//    public Response returnResponseForWebService() {
//        ResponseMessage responseMessage = new ResponseMessage();
//        responseMessage.setCode("403");
//        responseMessage.setMessage("Permission Denied");
//        responseMessage.setResult("");
//        responseMessage.setStatus("FORBIDDEN");
//        return Response.status(Response.Status.FORBIDDEN)
//                .entity(responseMessage)
//                .build();
//    }
}
