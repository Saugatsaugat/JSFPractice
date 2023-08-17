/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;

import Entities.BookingInformation;
import Entities.FutsalSchedule;
import Entities.User;
import Model.BookingInformationCrud;
import Model.FutsalCrud;
import Model.UserCrud;
import com.saugat.bean.enums.UserType;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpSession;
import com.saugat.interceptors.Acl;

/**
 *
 * @author saugat
 */
@Named
@ViewScoped
public class BookingInformationController implements Serializable {

    @Inject
    private BookingInformationCrud bookingInformationCrud;
    @Inject
    private UserCrud userCrud;
    @Inject
    private FutsalCrud futsalCrud;
    
    FacesContext context;
    ExternalContext externalContext;
    HttpSession session;

    private List<BookingInformation> bookingInformationList;
    private BookingInformation bookingInformation;
    private FutsalSchedule futsalSchdule;

    public List<BookingInformation> getBookingInformationList() {
        return bookingInformationList;
    }

    public void setBookingInformationList(List<BookingInformation> bookingInformationList) {
        this.bookingInformationList = bookingInformationList;
    }

    public BookingInformation getBookingInformation() {
        return bookingInformation;
    }

    public void setBookingInformation(BookingInformation bookingInformation) {
        this.bookingInformation = bookingInformation;
    }

    public FutsalSchedule getFutsalSchdule() {
        return futsalSchdule;
    }

    public void setFutsalSchdule(FutsalSchedule futsalSchdule) {
        this.futsalSchdule = futsalSchdule;
    }

    @PostConstruct
    public void init() {
        bookingInformation = new BookingInformation();
        context = FacesContext.getCurrentInstance();
        externalContext = context.getExternalContext();
        session = (HttpSession) externalContext.getSession(true);
        Long sessionUserId = (Long) session.getAttribute("userId");
        if (sessionUserId != null) {
            User user = userCrud.getDataById(sessionUserId);
            if (user != null) {
                if (user.getUsertype().equals(UserType.admin)) {
                    bookingInformationList = bookingInformationCrud.getCurrentAndFutureBookingInformation();

                } else {
                    bookingInformationList = bookingInformationCrud.getCurrentAndFutureBookingInformationByUser(user);
                }
            }
//else {
//                bookingInformationList = bookingInformationCrud.getCurrentAndFutureBookingInformation();
//
//            }

        }
    }

    public void bookingInformationSchedule(BookingInformation bookingInformation) {
        this.bookingInformation = bookingInformation;
    }

    public void setBookingInformationId(Long id) {
        this.bookingInformation.setId(id);
    }

    public void update() {
        if (bookingInformation.getId() != null) {
            if (session.getAttribute("userId") != null) {
                Long userId = (Long) session.getAttribute("userId");

                if (bookingInformationCrud.update(bookingInformation, bookingInformation.getId())) {
                    try {
                        externalContext.redirect(externalContext.getRequestContextPath() + "/faces/view/AdminUI/Home/bookingInformationTable.xhtml");
                    } catch (Exception e) {

                    }
                } else {

                    FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Add Failed", "Add Failed");
                    context.addMessage(null, message);

                }
            }
        }
    }

    public void save() {
        if (session.getAttribute("userId") != null) {
            Long userId = (Long) session.getAttribute("userId");
            bookingInformation.setUser(userCrud.getDataById(userId));
            bookingInformation.setEntrydate(new Date());
            if (bookingInformationCrud.save(bookingInformation)) {
                try {
                    externalContext.redirect(externalContext.getRequestContextPath() + "/faces/view/AdminUI/Home/bookingInformationTable.xhtml");
                } catch (Exception e) {

                }
            } else {

                FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Update Failed", "Update Failed");
                context.addMessage(null, message);

            }

        } else {
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Must Login to Continue", "Login to continue");
            context.addMessage(null, message);
        }
    }

    public void delete() {
        if (bookingInformation.getId() != null) {
            if (bookingInformationCrud.deleteById(bookingInformation.getId())) {
                try {
                    externalContext.redirect(externalContext.getRequestContextPath() + "/faces/view/AdminUI/Home/bookingInformationTable.xhtml");
                } catch (Exception e) {

                }
            } else {

                FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Deletion Failed", "Deletion Failed");
                context.addMessage(null, message);

            }
        }
    }

}
