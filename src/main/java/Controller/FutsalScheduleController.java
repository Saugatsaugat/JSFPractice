package Controller;

import Entities.BookingDetail;
import Entities.BookingInformation;
import Entities.Futsal;
import Entities.FutsalSchedule;
import Entities.User;
import Model.BookingDetailCrud;
import Model.BookingInformationCrud;
import Model.FutsalCrud;
import Model.FutsalScheduleCruds;
import Model.UserCrud;
import java.io.Serializable;
import java.util.ArrayList;
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
import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;

/**
 *
 * @author saugat
 */
@Named
@ViewScoped
public class FutsalScheduleController implements Serializable {

    @Inject
    private FutsalCrud futsalCrud;
    @Inject
    private FutsalScheduleCruds fsc;
    @Inject
    private BookingInformationCrud bookingInformationCrud;
    @Inject
    private BookingDetailCrud bookingDetailCrud;
    @Inject
    private UserCrud userCrud;

    FacesContext context;
    ExternalContext externalContext;
    HttpSession session;
    private Futsal futsal;
    private Date newDate;
    private BookingInformation bookingInformation;
    private BookingDetail bookingDetail;

    public BookingInformation getBookingInformation() {
        return bookingInformation;
    }

    public void setBookingInformation(BookingInformation bookingInformation) {
        this.bookingInformation = bookingInformation;
    }

    public BookingDetail getBookigDetail() {
        return bookingDetail;
    }

    public void setBookigDetail(BookingDetail bookingDetail) {
        this.bookingDetail = bookingDetail;
    }

    public String rowStyleClass(FutsalSchedule item) {
        if ("available".equalsIgnoreCase(item.getStatus())) {
            return "green";
        } else {
            return "red";
        }
    }

    public Date getNewDate() {
        return newDate;
    }

    public void setNewDate(Date newDate) {
        this.newDate = newDate;
    }

    private List<FutsalSchedule> futsalScheduleList;

    private FutsalSchedule futsalSchedule;

    public List<FutsalSchedule> getFutsalScheduleList() {
        return futsalScheduleList;
    }

    public void setFutsalScheduleList(List<FutsalSchedule> futsalScheduleList) {
        this.futsalScheduleList = futsalScheduleList;

    }

    public FutsalSchedule getFutsalSchedule() {
        return futsalSchedule;
    }

    public void setFutsalSchedule(FutsalSchedule futsalSchedule) {
        this.futsalSchedule = futsalSchedule;
    }

    public Futsal getFutsal() {
        return futsal;
    }

    public void setFutsal(Futsal futsal) {
        this.futsal = futsal;
    }

    @PostConstruct
    public void init() {
        bookingInformation = new BookingInformation();
        bookingDetail = new BookingDetail();
        futsalSchedule = new FutsalSchedule();
        context = FacesContext.getCurrentInstance();
        externalContext = context.getExternalContext();
        session = (HttpSession) externalContext.getSession(true);
        newDate = new Date();
        futsal = new Futsal();
        if (session.getAttribute("futsalId") != null) {
            Long futsalId = (Long) session.getAttribute("futsalId");
            futsalScheduleList = fsc.getCurrentDateDataByFutsal(futsalCrud.getDataById(futsalId));
            System.out.println("");
        } else {
            futsalScheduleList = fsc.getCurrentDateFutsalSchedule();
            System.out.println("");
        }

    }

    public void updateFutsalSchedule(Long futsalId) {
        if (futsalId != null) {
            futsalScheduleList = fsc.getCurrentDateDataByFutsal(futsalCrud.getDataById(futsalId));
        }
    }

    public void onDateSelect(SelectEvent event) {
        futsalScheduleList = null;
        newDate = (Date) event.getObject();
        futsalScheduleList = fsc.getFutsalScheduleByDateAndUserId(newDate, futsalCrud.getDataById(futsal.getId()));
    }

    public void futsalScheduleByFutsalId(Long futsalId) {
        if (futsalId != null) {
            try {
                futsalScheduleList = fsc.getCurrentDateDataByFutsal(futsalCrud.getDataById(futsalId));
            } catch (Exception e) {

            }
        }
    }

    public void showFutsalSchedule(FutsalSchedule futsalSchedule) {
        setFutsalSchedule(futsalSchedule);
//        this.futsalSchedule = futsalSchedule;
    }

    public void bookFutsalSchedule(FutsalSchedule futsalSch) {
        this.futsalSchedule = futsalSch;
        if (session.getAttribute("userId") == null) {
            RequestContext contextReq = RequestContext.getCurrentInstance();
            contextReq.execute("PF('loginRequired').show();");
        } else {
            Long loggedInUser = (Long) session.getAttribute("userId");
            User user = userCrud.getDataById(loggedInUser);
            // for Booking Information Table
            bookingInformation.setEntrydate(new Date());
            bookingInformation.setAmount(futsalSchedule.getRate());
            bookingInformation.setFromdate(futsalSchedule.getScheduledate());
            bookingInformation.setTodate(futsalSchedule.getScheduledate());
            bookingInformation.setUser(user);

            // for BookingDetail Table
            bookingDetail.setFutsalschedule(futsalSchedule);
            FutsalSchedule futsalScheduleRef = fsc.getDataById(futsalSchedule.getId());
            String status = futsalScheduleRef.getStatus();
            if ((fsc.checkIfExits(futsalSchedule)) && status.matches("available") ){
                try {
                    if (bookingInformationCrud.save(bookingInformation)) {
                        Thread.sleep(10);
                        //bookingInformation = bookingInformationCrud.getBookingInformationByDateAndUser(user, futsalSchedule.getScheduledate());
                        bookingDetail.setBookinginformation(bookingInformation);
                        bookingDetail.setPaymentstatus("incomplete");
                        if (bookingDetailCrud.save(bookingDetail)) {
                            futsalSchedule.setStatus("booked");
                            if (fsc.update(futsalSchedule, futsalSchedule.getId())) {
                                externalContext.redirect(externalContext.getRequestContextPath() + "/faces/view/FutsalSchedule/futsalScheduleTable.xhtml");
                            }
                        }

                    }
                    FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Booking Failed", "Booking Failed");
                    context.addMessage(null, message);
                } catch (Exception e) {

                }
            } else {
                FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Slot Not Available", "Slot Not available");
                context.addMessage(null, message);
            }
        }

    }

    public void update() {
        if (futsalSchedule.getId() != null) {
            if (session.getAttribute("userId") != null) {
                Long userId = (Long) session.getAttribute("userId");
                futsal = futsalCrud.checkIfFutsalRegistered(userId);
                Long futsalId = futsal.getId();
                futsalSchedule.setFutsal(futsalCrud.getDataById(futsalId));
                if (futsalSchedule.getStatus() == null) {
                    futsalSchedule.setStatus("available");
                }
                if (fsc.update(futsalSchedule, futsalSchedule.getId())) {
                    try {

                        externalContext.redirect(externalContext.getRequestContextPath() + "/faces/view/FutsalSchedule/futsalScheduleTable.xhtml");
                    } catch (Exception e) {

                    }
                }
            }
        }

        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Update Failed", "Update Failed");
        context.addMessage(null, message);

    }

    public void save() {
        if (session.getAttribute("userId") != null) {
            Long userId = (Long) session.getAttribute("userId");
            futsal = futsalCrud.checkIfFutsalRegistered(userId);
            Long futsalId = futsal.getId();
            futsalSchedule.setFutsal(futsalCrud.getDataById(futsalId));
            if (futsalSchedule.getStatus() == null) {
                futsalSchedule.setStatus("available");
            }

            if (fsc.save(futsalSchedule)) {
                try {

                    externalContext.redirect(externalContext.getRequestContextPath() + "/faces/view/FutsalSchedule/futsalScheduleTable.xhtml");
                } catch (Exception e) {

                }
            }

        }
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Add Failed", "Add Failed");
        context.addMessage(null, message);

    }

    public boolean saveFutsal(Futsal futsal) {
        if (session.getAttribute("userId") == null) {

            context = FacesContext.getCurrentInstance();
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Login Required", "Must Login to Continue!");
            context.addMessage(null, message);
            return false;
        } else {
            this.futsal = futsal;
            this.futsalScheduleList = new ArrayList<>();
            return true;
        }

    }

    public void setFutsalScheduleId(Long id) {
        futsalSchedule.setId(id);
    }

    public void delete() {
        if (futsalSchedule.getId() != null) {
            if (fsc.deleteById(futsalSchedule.getId())) {
                futsalSchedule = fsc.getDataById(futsalSchedule.getId());

                try {

                    externalContext.redirect(externalContext.getRequestContextPath() + "/faces/view/FutsalSchedule/futsalScheduleTable.xhtml");
                } catch (Exception e) {

                }
            } else {

                FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Deletion Failed", "Deletion Failed");
                context.addMessage(null, message);

            }
        }
    }

}
