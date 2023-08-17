package com.saugat.interceptors;

import com.saugat.bean.enums.ActionType;
import com.saugat.bean.enums.ResourceType;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import javax.enterprise.util.Nonbinding;
import javax.interceptor.InterceptorBinding;

/**
 *
 * @author sagat
 */
@InterceptorBinding
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.TYPE})
public @interface Acl {

    @Nonbinding
    ResourceType resourceName() default ResourceType.FUTSALSCHEDULE ;

    @Nonbinding
    ActionType actionName() default ActionType.READ;
}
