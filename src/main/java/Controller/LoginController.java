package Controller;

import Entities.Futsal;
import Entities.User;
import Login.Login;
import Model.FutsalCrud;
import Model.FutsalScheduleCruds;
import Model.UserCrud;
import com.saugat.beans.ActiveUsersBean;
import com.saugat.beans.UserBean;
import com.saugat.messageGeneration.ValidationMessageGenerationUtil;
import java.io.IOException;
import java.io.Serializable;
import java.sql.SQLException;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpSession;
import org.primefaces.context.RequestContext;

/**
 *
 * @author saugat
 */
@Named
@ViewScoped

public class LoginController implements Serializable {

    FacesContext context;
    ExternalContext externalContext;
    HttpSession session;

    private Login login;
    private Futsal futsal;

    @Inject
    private UserCrud userCrud;

    @Inject
    private FutsalCrud futsalCrud;

    @Inject
    private ActiveUsersBean activeUsersBean;

    @Inject
    private UserBean userBean;

    @Inject
    private FutsalScheduleCruds fsc;

    public Login getLogin() {
        return login;
    }

    public void setLogin(Login login) {
        this.login = login;
    }

    public Futsal getFutsal() {
        return futsal;
    }

    public void setFutsal(Futsal futsal) {
        this.futsal = futsal;
    }

    @PostConstruct
    public void init() {
        login = new Login();
        futsal = new Futsal();
        context = FacesContext.getCurrentInstance();
        externalContext = context.getExternalContext();
        session = (HttpSession) externalContext.getSession(true);

    }

    public void checkUser() throws SQLException, ClassNotFoundException, IOException {
        try {

            User userRecord = userCrud.findByUsernameAndPassword(login.getUsername(), login.getPassword());
            if (userRecord != null) {
                String userType = userRecord.getUsertype().toString();
                Long userid = userRecord.getId();

                if (("admin".equals(userType))) {
                    session.setAttribute("userId", userid);
                    userBean.setUser(userRecord);

                    activeUsersBean.incrementActiveUsersList(userRecord);

                    ValidationMessageGenerationUtil.validationMessageGeneration("Welcome " + userBean.getUser().getFirstname(),
                            "informational");

                    RequestContext contextReq = RequestContext.getCurrentInstance();
                    contextReq.execute("PF('notificationDialogForLoggedIn').show();");

                    externalContext = FacesContext.getCurrentInstance().getExternalContext();
                    externalContext.redirect(externalContext.getRequestContextPath() +
                            "/faces/view/AdminUI/Home/home.xhtml?showDialog=true");

                } else if (("user".equals(userType))) {
                    session.setAttribute("userId", userid);
                    activeUsersBean.incrementActiveUsersList(userRecord);
                    userBean.setUser(userRecord);

                    ValidationMessageGenerationUtil.validationMessageGeneration("Welcome " + userBean.getUser().getFirstname(),
                            "informational");
                    RequestContext contextReq = RequestContext.getCurrentInstance();
                    contextReq.execute("PF('notificationDialogForLoggedIn').show();");
                    externalContext.redirect(externalContext.getRequestContextPath() +
                            "/faces/view/UserUI/Home/home.xhtml?showDialog=true");

                } else if ("futsalowner".equals(userType)) {
                    userBean.setUser(userRecord);

                    if ((futsalCrud.checkIfFutsalRegistered(userid)) == null) {
                        session.setAttribute("userId", userid);
                        activeUsersBean.incrementActiveUsersList(userRecord);
                        ValidationMessageGenerationUtil.validationMessageGeneration("Welcome " + userBean.getUser().getFirstname(),
                                "informational");
                        RequestContext contextReq = RequestContext.getCurrentInstance();
                        contextReq.execute("PF('futsalRegisterDialog').show();");

                    } else {
                        Futsal futsalData = futsalCrud.checkIfFutsalRegistered(userid);
                        session.setAttribute("userId", userid);
                        session.setAttribute("futsalId", futsalData.getId());

                        externalContext.redirect(externalContext.getRequestContextPath() 
                                + "/faces/view/FutsalOwnerUI/Home/home.xhtml?showDialog=true");
                    }

                }

            } else {
                context = FacesContext.getCurrentInstance();
                FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Invalid Credentials", "Invalid Credentials");
                context.addMessage(null, message);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }

}
