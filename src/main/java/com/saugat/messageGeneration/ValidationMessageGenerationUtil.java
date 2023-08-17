
package com.saugat.messageGeneration;

import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.context.Flash;

/**
 *
 * @author saugat
 */
public class ValidationMessageGenerationUtil {
    //error Messages generation Function

    public static void validationMessageGeneration(String msg, String messageType) {
        FacesMessage message = null;
        if (messageType.equals("error")) {
            message = new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, msg);
        }
        if (messageType.equals("informational")) {
            message = new FacesMessage(FacesMessage.SEVERITY_INFO, msg, msg);
        }
        FacesContext.getCurrentInstance().addMessage(null, message);
        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
        externalContext = FacesContext.getCurrentInstance().getExternalContext();
        Flash flash = externalContext.getFlash();
        flash.setKeepMessages(true);
    }

}
