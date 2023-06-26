/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;

import Entities.Futsal;
import Entities.User;
import Login.Login;
import Model.FutsalCrud;
import Model.UserCrud;
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
                String userType = userRecord.getUsertype();
                Long userid = userRecord.getId();

                if (("admin".equals(userType)) || ("user".equals(userType))) {

                    externalContext.redirect(externalContext.getRequestContextPath() + "/faces/view/Home/home.xhtml");
                } else if ("futsalowner".equals(userType)) {

                    futsal = futsalCrud.checkIfFutsalRegistered(userid);

                    if (futsal == null) {
                        RequestContext contextReq = RequestContext.getCurrentInstance();
                        contextReq.execute("PF('futsalRegisterDialog').show();");

                    } else {
                        
                        externalContext.redirect(externalContext.getRequestContextPath() + "/faces/view/Home/home.xhtml");
                    }

                }

            } else {
               
                FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Invalid Credentials", "Invalid Credentials");
                context.addMessage(null, message);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }
}
