package Controller;

import Entities.Futsal;
import Entities.FutsalUserRelation;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

/**
 *
 * @author saugat
 */
@Named
@ViewScoped
public class LogoutController implements Serializable {

    FacesContext context;
    ExternalContext externalContext;
    HttpSession session;
    
    private String sessionUserId;

    public String getSessionUserId() {
        return sessionUserId;
    }

    public void setSessionUserId(String sessionUserId) {
        this.sessionUserId = sessionUserId;
    }

    @PostConstruct
    public void init() {
        context = FacesContext.getCurrentInstance();
        externalContext = context.getExternalContext();
        session = (HttpSession) externalContext.getSession(true);
        
    }

    public void logout() {
        if (session.getAttribute("userId") != null) {
            try {
                session.invalidate();
                externalContext.redirect(externalContext.getRequestContextPath() + "/faces/view/Home/home.xhtml");
            } catch (Exception e) {

            }
        }
    }

}
