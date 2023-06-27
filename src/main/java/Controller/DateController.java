package Controller;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;
import javax.annotation.PostConstruct;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.servlet.http.HttpSession;
import org.primefaces.context.RequestContext;

/**
 *
 * @author saugat
 */
@Named
@ViewScoped
public class DateController implements Serializable {

    FacesContext context;
    ExternalContext externalContext;
    HttpSession session;

    private Date date;

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @PostConstruct
    public void init() {
        date = new Date();
        context = FacesContext.getCurrentInstance();
        externalContext = context.getExternalContext();
        session = (HttpSession) externalContext.getSession(true);
    }

    public void displayDialog() {
        RequestContext contextReq = RequestContext.getCurrentInstance();
        contextReq.execute("PF('bookNowDialog').show();");

    }

}
