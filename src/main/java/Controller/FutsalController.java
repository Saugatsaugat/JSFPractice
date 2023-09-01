package Controller;

import Entities.Futsal;
import Entities.FutsalUserRelation;
import Entities.User;
import Model.FutsalCrud;
import Model.FutsalUserRelationCrud;
import Model.UserCrud;
import com.saugat.bean.enums.UserType;
import com.saugat.messageGeneration.ValidationMessageGenerationUtil;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.context.Flash;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

/**
 *
 * @author saugat
 */
@Named
@ViewScoped
public class FutsalController implements Serializable {

    FacesContext context;
    ExternalContext externalContext;
    HttpSession session;

    @Inject
    private FutsalCrud futsalCrud;

    @Inject
    private UserCrud userCrud;

    @Inject
    private FutsalUserRelationCrud futsalUserRelationCrud;

    List<Futsal> futsalList;
    private Futsal futsal;
    private FutsalUserRelation futsalUserRelation;
    private User user;
    private String registerHeaderName;

    public String getRegisterHeaderName() {
        return registerHeaderName;
    }

    public void setRegisterHeaderName(String string) {
        this.registerHeaderName = string;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Futsal> getFutsalList() {
        return futsalList;
    }

    public void setFutsalList(List<Futsal> futsalList) {
        this.futsalList = futsalList;
    }

    public Futsal getFutsal() {
        return futsal;
    }

    public void setFutsal(Futsal futsal) {
        this.futsal = futsal;
    }

    @PostConstruct
    public void init() {
        registerHeaderName = "";
        user = new User();
        futsal = new Futsal();
        futsalUserRelation = new FutsalUserRelation();
        futsalList = futsalCrud.getAllData();
        context = FacesContext.getCurrentInstance();
        externalContext = context.getExternalContext();
        session = (HttpSession) externalContext.getSession(true);
    }

    public void updateFutsal() {
        if (futsal.getId() != null) {
            if (futsalCrud.update(futsal, futsal.getId())) {
                try {
                    ValidationMessageGenerationUtil.validationMessageGeneration("Updated", "informational");
                    externalContext.redirect(externalContext.getRequestContextPath() + "/faces/view/AdminUI/Home/futsalTable.xhtml");
                } catch (Exception e) {
                }
            }
        }
        ValidationMessageGenerationUtil.validationMessageGeneration("Update Failed", "error");

    }

    public void saveFutsal() {
        if (futsal.getId() == null) {
            if (futsal.getOwnerid() != null) {
                user = userCrud.getDataById(futsal.getOwnerid());
                if (user.getUsertype().equals(UserType.user)) {
                    user.setUsertype(UserType.futsalowner);
                    userCrud.update(user, user.getId());
                }
                futsal.setMobile(user.getMobile());
                futsal.setOwnerid(user.getId());
            } else {
                if (session.getAttribute("userId") != null) {
                    Long userId = (Long) session.getAttribute("userId");
                    user = userCrud.getDataById(userId);
                    futsal.setMobile(user.getMobile());
                    futsal.setOwnerid(userId);
                }
            }

            if (futsalCrud.save(futsal)) {
                futsalList = futsalCrud.getAllData();
                ValidationMessageGenerationUtil.validationMessageGeneration("Created", "informational");
                return;
// try {

//                    if ((userCrud.getDataById(futsal.getOwnerid()).getUsertype() == UserType.admin)) {
//                        externalContext.redirect(externalContext.getRequestContextPath() + "/faces/view/AdminUI/Home/futsalTable.xhtml");
//                    } else if ((userCrud.getDataById(futsal.getOwnerid()).getUsertype() == UserType.futsalowner)) {
//                        session.setAttribute("futsalId", futsal.getId());
//                        externalContext.redirect(externalContext.getRequestContextPath() + "/faces/view/FutsalOwnerUI/Home/home.xhtml");
//                    }
//                } catch (Exception e) {
//                }
            }

        }
        ValidationMessageGenerationUtil.validationMessageGeneration("Failed", "error");
        return;
    }

    public void deleteFutsal() {
        Long futsalId = futsal.getId();
        if (futsalId != null) {
            futsal = futsalCrud.getDataById(futsalId);
            if ((futsal != null)) {
                boolean status = futsalCrud.deleteById(futsal.getId());
                if (status) {
                    try {
                        ValidationMessageGenerationUtil.validationMessageGeneration("Deleted", "informational");
                        externalContext.redirect(externalContext.getRequestContextPath() + "/faces/view/AdminUI/Home/futsalTable.xhtml");
                    } catch (Exception e) {
                    }
                } else {
                    ValidationMessageGenerationUtil.validationMessageGeneration("Deletion Failed", "error");

                }

            }

            ValidationMessageGenerationUtil.validationMessageGeneration("Deletion Failed", "error");

        }
    }

    public String getRandomImage() {
        Random random = new Random();
        int randomNumber = random.nextInt(10) + 1; // Generates a random number between 0 (inclusive) and 4 (exclusive), then adds 1
        return String.valueOf(randomNumber);
    }

    public void updateRegisterHeaderName(String string) {
        this.registerHeaderName = string;
    }

}
