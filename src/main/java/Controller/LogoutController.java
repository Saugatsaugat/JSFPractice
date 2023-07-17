package Controller;

import Entities.User;
import com.saugat.beans.UserBean;
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

    private Long sessionUserId;

    public Long getSessionUserId() {
        return sessionUserId;
    }

    public void setSessionUserId(Long sessionUserId) {
        this.sessionUserId = sessionUserId;
    }

    @PostConstruct
    public void init() {
        context = FacesContext.getCurrentInstance();
        externalContext = context.getExternalContext();
        session = (HttpSession) externalContext.getSession(true);
        System.out.println("");

    }

    public boolean checkIfSessionExists() {
        session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        return session!=null && session.getAttribute("userId") != null;
          
    }

    public void logout() {
        if (session.getAttribute("userId") != null) {
            try {
                new UserBean().setUser(null);
                session.invalidate();
                externalContext.redirect(externalContext.getRequestContextPath() + "/faces/view/UserUI/Home/home.xhtml");
            } catch (Exception e) {

            }
        }
    }

}
