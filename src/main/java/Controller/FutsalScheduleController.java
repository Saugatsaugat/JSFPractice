
package Controller;

import Entities.FutsalSchedule;
import Model.FutsalScheduleCrud;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
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
public class FutsalScheduleController implements Serializable {

    FacesContext context;
    ExternalContext externalContext;
    HttpSession session;
   
    private List<FutsalSchedule> futsalSchedule;

    @Inject
    private FutsalScheduleCrud futsalScheduleCrud;

    @PostConstruct
    public void init() {
        futsalSchedule = futsalScheduleCrud.getFutsalSchedule();
        futsalScheduleCrud = new FutsalScheduleCrud();
        context = FacesContext.getCurrentInstance();
        externalContext = context.getExternalContext();
        session = (HttpSession) externalContext.getSession(true);
    }
    public void checkSchedule(){
       

    }

}
