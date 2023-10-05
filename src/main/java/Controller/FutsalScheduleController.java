package Controller;

import Entities.BookingDetail;
import Entities.BookingInformation;
import Entities.Futsal;
import Entities.FutsalSchedule;
import Entities.SlotSchedule;
import Entities.User;
import Model.BookingDetailCrud;
import Model.BookingInformationCrud;
import Model.FutsalCrud;
import Model.FutsalScheduleCruds;
import Model.UserCrud;
import com.saugat.bean.enums.ActionType;
import com.saugat.bean.enums.ResourceType;
import com.saugat.bean.enums.SlotType;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.context.Flash;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpSession;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.ToggleSelectEvent;
import org.primefaces.event.UnselectEvent;
import com.saugat.interceptors.Acl;
import com.saugat.messageGeneration.ValidationMessageGenerationUtil;

/**
 *
 * @author saugat
 */
@Named
@ViewScoped
public class FutsalScheduleController implements Serializable {
//creating enum

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

    private List<FutsalSchedule> selectedSchedueList;
    private List<FutsalSchedule> newScheduleList;
    private SlotSchedule slotSchedule;
    private Futsal futsal;
    private Date newDate;
    private BookingInformation bookingInformation;
    private BookingDetail bookingDetail;
    private String uniqueId;

    private Date todayDate;

    public Date getTodayDate() {
        return new Date();
    }

    public String getUniqueId(FutsalSchedule obj) {
        if (obj != null) {
            uniqueId = obj.getScheduledate() + "-" + obj.getStarthour() + "-" + obj.getEndhour();
        }
        return uniqueId;
    }

    public List<FutsalSchedule> getNewScheduleList() {
        return newScheduleList;
    }

    public void setNewScheduleList(List<FutsalSchedule> newScheduleList) {
        this.newScheduleList = newScheduleList;
    }

    public List<FutsalSchedule> getSelectedSchedueList() {
        return selectedSchedueList;
    }

    public void setSelectedSchedueList(List<FutsalSchedule> selectedSchedueList) {
        this.selectedSchedueList = selectedSchedueList;
    }

    public void onRowSelect(SelectEvent event) {
        FutsalSchedule data = (FutsalSchedule) event.getObject();
        selectedSchedueList.add(data);
        System.out.println("");
    }

    public void onRowUnselect(UnselectEvent event) {
        if (selectedSchedueList != null) {
            selectedSchedueList.remove((FutsalSchedule) event.getObject());
        }
    }

    public void onRowSelectCheckbox(SelectEvent event) {
        FutsalSchedule data = (FutsalSchedule) event.getObject();
        selectedSchedueList.add(data);
        System.out.println("");
    }

    public void onRowUnselectCheckbox(UnselectEvent event) {
        if (selectedSchedueList != null) {
            FutsalSchedule data = (FutsalSchedule) event.getObject();
            selectedSchedueList.remove(data);
        }
    }

    public void onToggleSelect(ToggleSelectEvent event) {
        boolean selected = event.isSelected();
        if (selected) {
            selectedSchedueList = new ArrayList<>(newScheduleList);
        } else {
            selectedSchedueList = new ArrayList<>();
        }
    }

    public SlotSchedule getSlotSchedule() {
        return slotSchedule;
    }

    public void setSlotSchedule(SlotSchedule slotSchedule) {
        this.slotSchedule = slotSchedule;
    }

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
        selectedSchedueList = new ArrayList<>();
        newScheduleList = new ArrayList<>();
        slotSchedule = new SlotSchedule();
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

    public void updateNewScheduleList() {
        this.newScheduleList = new ArrayList<>();
    }

    public boolean checkIfSlotExists(Date scheduleDate, Date startHour, Date endHour, Futsal futsal) {
        try {

            if (fsc.checkIfRecordExistsByDateAndTime(scheduleDate, startHour, endHour, futsal)) {
                return true;
            }
        } catch (Exception e) {

        }
        return false;
    }

    public void slotType(String string) {
        if ("automatic".equals(string)) {
            this.slotSchedule.setSlotType(SlotType.automatic);
        } else if ("shift".equals(string)) {
            this.slotSchedule.setSlotType(SlotType.shift);
        } else if ("custom".equals(string)) {
            this.slotSchedule.setSlotType(SlotType.custom);
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

   synchronized public void bookFutsalSchedule(FutsalSchedule futsalSch) {
        this.futsalSchedule = futsalSch;
        if (session.getAttribute("userId") == null) {
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Login Required", "Login Required");
            FacesContext.getCurrentInstance().addMessage(null, message);
            externalContext = FacesContext.getCurrentInstance().getExternalContext();
            Flash flash = externalContext.getFlash();
            flash.setKeepMessages(true);
        } else {
            Long loggedInUser = (Long) session.getAttribute("userId");
            User user = userCrud.getDataById(loggedInUser);
            // for Booking Information Table
            bookingInformation.setEntrydate(new Date());
            bookingInformation.setAmount(futsalSchedule.getRate());
            bookingInformation.setFromdate(futsalSchedule.getScheduledate());
            bookingInformation.setTodate(futsalSchedule.getScheduledate());
            bookingInformation.setUser(user);

            FutsalSchedule futsalScheduleRef = fsc.getDataById(futsalSchedule.getId());
            String status = futsalScheduleRef.getStatus();
            if ((fsc.checkIfExits(futsalSchedule)) && status.matches("available")) {
                try {
                    if (fsc.saveBooking(bookingInformation, futsalSchedule)) {
                        ValidationMessageGenerationUtil.validationMessageGeneration("Booked", "informational");
                    }
                    ValidationMessageGenerationUtil.validationMessageGeneration("Booked Failed", "erro");

                } catch (Exception e) {

                }
            } else {

                ValidationMessageGenerationUtil.validationMessageGeneration("Slot Unavailable", "error");

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
                        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Updated Successfully", "Updated Successfully");
                        FacesContext.getCurrentInstance().addMessage(null, message);
                        externalContext = FacesContext.getCurrentInstance().getExternalContext();
                        Flash flash = externalContext.getFlash();
                        flash.setKeepMessages(true);
                        externalContext.redirect(externalContext.getRequestContextPath() + "/faces/view/FutsalSchedule/futsalScheduleTable.xhtml");
                    } catch (Exception e) {

                    }
                }
            }
        }

        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Update Failed", "Update Failed");
        context.addMessage(null, message);

    }

    public Calendar getDateTimeFunc(int time) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, time);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar;
    }

    //Futsal Schedule Generator Function
    public void futsalScheduleGenerator() {
        if (session.getAttribute("userId") != null) {
            try {
                Futsal futsal = futsalCrud.checkIfFutsalRegistered((Long) session.getAttribute("userId"));

                // schedule generator for automatic slot type
                if (slotSchedule.getSlotType() == SlotType.automatic) {

                    // created new instance of ArrayList of FutsalSchedule
                    newScheduleList = new ArrayList<>();
                    /* creating if FutsalSchedule instance list for storing booked schedules from generated
                    * schedules
                     */
                    List<FutsalSchedule> bookedScheduleList = new ArrayList<>();

                    if ((slotSchedule.getDateFrom() != null) && (slotSchedule.getDateTo()) != null) {
                        LocalDate startDate = slotSchedule.getDateFrom().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                        LocalDate endDate = slotSchedule.getDateTo().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                        while (!startDate.isAfter(endDate)) {
                            Calendar calendar = Calendar.getInstance();
                            calendar.setTime(slotSchedule.getTimeFrom());

                            Calendar calendarBreakTimeFrom = Calendar.getInstance();
                            calendarBreakTimeFrom.setTime(slotSchedule.getBreakTimeFrom());
                            Date breakTimeStart = calendarBreakTimeFrom.getTime();

                            Calendar calendarBreakTimeTo = Calendar.getInstance();
                            calendarBreakTimeTo.setTime(slotSchedule.getBreakTimeTo());
                            Date breakTimeEnd = calendarBreakTimeTo.getTime();

                            while (!calendar.getTime().after(slotSchedule.getTimeTo())) {
                                Date currentTime = calendar.getTime();
                                calendar.add(Calendar.HOUR, 1);
                                Date nextTime = calendar.getTime();
                                if ((!currentTime.equals(breakTimeStart)) && (!nextTime.equals(breakTimeEnd))) {
                                    FutsalSchedule newFutsalSchedule = new FutsalSchedule();
                                    newFutsalSchedule.setFutsal(futsal);
                                    newFutsalSchedule.setRate(slotSchedule.getNormalRate());
                                    newFutsalSchedule.setScheduledate(java.sql.Date.valueOf(startDate));
                                    newFutsalSchedule.setStarthour(currentTime);
                                    newFutsalSchedule.setEndhour(nextTime);
                                    newFutsalSchedule.setStatus("available");

                                    boolean status = checkIfSlotExists(java.sql.Date.valueOf(startDate), currentTime, nextTime, futsal);
                                    if (status) {
                                        bookedScheduleList.add(newFutsalSchedule);
                                    } else {
                                        newScheduleList.add(newFutsalSchedule);
                                    }
                                }
                            }
                            startDate = startDate.plus(1, ChronoUnit.DAYS);
                        }

                    }

                } else if (slotSchedule.getSlotType() == SlotType.shift) {
                    newScheduleList = new ArrayList<>();
                    List<FutsalSchedule> bookedScheduleList = new ArrayList<>();

                    if ((slotSchedule.getDateFrom() != null) && (slotSchedule.getDateTo()) != null) {
                        LocalDate startDate = slotSchedule.getDateFrom().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                        LocalDate endDate = slotSchedule.getDateTo().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                        while (!startDate.isAfter(endDate)) {
                            for (int start = 5; start < 10; start++) {
                                FutsalSchedule newFutsalSchedule = new FutsalSchedule();
                                newFutsalSchedule.setScheduledate(java.sql.Date.valueOf(startDate));
                                newFutsalSchedule.setFutsal(futsal);
                                newFutsalSchedule.setRate(slotSchedule.getMorningShiftRate());
                                newFutsalSchedule.setStarthour(getDateTimeFunc(start).getTime());
                                newFutsalSchedule.setEndhour(getDateTimeFunc(start + 1).getTime());
                                newFutsalSchedule.setStatus("available");
                                boolean status = checkIfSlotExists(java.sql.Date.valueOf(startDate), getDateTimeFunc(start).getTime(), getDateTimeFunc(start + 1).getTime(), futsal);
                                if (status) {
                                    bookedScheduleList.add(newFutsalSchedule);

                                } else {
                                    newScheduleList.add(newFutsalSchedule);
                                }

                            }
                            for (int start = 10; start < 17; start++) {
                                FutsalSchedule newFutsalSchedule = new FutsalSchedule();
                                newFutsalSchedule.setScheduledate(java.sql.Date.valueOf(startDate));
                                newFutsalSchedule.setFutsal(futsal);
                                newFutsalSchedule.setRate(slotSchedule.getDayShiftRate());
                                newFutsalSchedule.setStarthour(getDateTimeFunc(start).getTime());
                                newFutsalSchedule.setEndhour(getDateTimeFunc(start + 1).getTime());
                                newFutsalSchedule.setStatus("available");
                                boolean status = checkIfSlotExists(java.sql.Date.valueOf(startDate), getDateTimeFunc(start).getTime(), getDateTimeFunc(start + 1).getTime(), futsal);
                                if (status) {
                                    bookedScheduleList.add(newFutsalSchedule);

                                } else {
                                    newScheduleList.add(newFutsalSchedule);
                                }
                            }
                            for (int start = 17; start < 20; start++) {
                                FutsalSchedule newFutsalSchedule = new FutsalSchedule();
                                newFutsalSchedule.setScheduledate(java.sql.Date.valueOf(startDate));
                                newFutsalSchedule.setFutsal(futsal);
                                newFutsalSchedule.setRate(slotSchedule.getEveningShiftRate());
                                newFutsalSchedule.setStarthour(getDateTimeFunc(start).getTime());
                                newFutsalSchedule.setEndhour(getDateTimeFunc(start + 1).getTime());
                                newFutsalSchedule.setStatus("available");
                                boolean status = checkIfSlotExists(java.sql.Date.valueOf(startDate), getDateTimeFunc(start).getTime(), getDateTimeFunc(start + 1).getTime(), futsal);
                                if (status) {
                                    bookedScheduleList.add(newFutsalSchedule);

                                } else {
                                    newScheduleList.add(newFutsalSchedule);
                                }
                            }
                            for (int start = 20; start < 23; start++) {
                                FutsalSchedule newFutsalSchedule = new FutsalSchedule();
                                newFutsalSchedule.setScheduledate(java.sql.Date.valueOf(startDate));
                                newFutsalSchedule.setFutsal(futsal);
                                newFutsalSchedule.setRate(slotSchedule.getNightShiftRate());
                                newFutsalSchedule.setStarthour(getDateTimeFunc(start).getTime());
                                newFutsalSchedule.setEndhour(getDateTimeFunc(start + 1).getTime());
                                newFutsalSchedule.setStatus("available");
                                boolean status = checkIfSlotExists(java.sql.Date.valueOf(startDate), getDateTimeFunc(start).getTime(), getDateTimeFunc(start + 1).getTime(), futsal);
                                if (status) {
                                    bookedScheduleList.add(newFutsalSchedule);

                                } else {
                                    newScheduleList.add(newFutsalSchedule);
                                }
                            }
                            for (int start = 1; start < 4; start++) {
                                FutsalSchedule newFutsalSchedule = new FutsalSchedule();
                                newFutsalSchedule.setScheduledate(java.sql.Date.valueOf(startDate));
                                newFutsalSchedule.setFutsal(futsal);
                                newFutsalSchedule.setRate(slotSchedule.getNightShiftRate());
                                newFutsalSchedule.setStarthour(getDateTimeFunc(start).getTime());
                                newFutsalSchedule.setEndhour(getDateTimeFunc(start + 1).getTime());
                                newFutsalSchedule.setStatus("available");
                                boolean status = checkIfSlotExists(java.sql.Date.valueOf(startDate), getDateTimeFunc(start).getTime(), getDateTimeFunc(start + 1).getTime(), futsal);
                                if (status) {
                                } else {
                                    newScheduleList.add(newFutsalSchedule);
                                }
                            }

                            startDate = startDate.plus(1, ChronoUnit.DAYS);
                        }

                    }

                } else if (slotSchedule.getSlotType() == SlotType.custom) {
                    List<FutsalSchedule> bookedScheduleList = new ArrayList<>();
                    newScheduleList = new ArrayList<>();
                    FutsalSchedule newSchedule = new FutsalSchedule();
                    newSchedule.setScheduledate(slotSchedule.getDateFrom());
                    newSchedule.setFutsal(futsal);
                    newSchedule.setRate(slotSchedule.getNormalRate());
                    newSchedule.setStarthour(slotSchedule.getTimeFrom());
                    newSchedule.setEndhour(slotSchedule.getTimeTo());
                    newSchedule.setStatus("available");
                    boolean status = checkIfSlotExists(slotSchedule.getDateFrom(), slotSchedule.getTimeFrom(), slotSchedule.getTimeTo(), futsal);
                    if (status) {
                        bookedScheduleList.add(newSchedule);
                    } else {

                        newScheduleList.add(newSchedule);

                    }
                }
            } catch (Exception e) {

            }
        } else {
            context = FacesContext.getCurrentInstance();
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Login Required", "Login Required");
            context.addMessage(null, message);
        }
    }

    // saving Futsal Data
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

    //delete function
    public void delete() {
        if (futsalSchedule.getId() != null) {
            if (fsc.deleteById(futsalSchedule.getId())) {
                futsalSchedule = fsc.getDataById(futsalSchedule.getId());

                try {
                    FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Deleted Successfully", "Deleted Successfully");
                    FacesContext.getCurrentInstance().addMessage(null, message);
                    externalContext = FacesContext.getCurrentInstance().getExternalContext();
                    Flash flash = externalContext.getFlash();
                    flash.setKeepMessages(true);
                    externalContext.redirect(externalContext.getRequestContextPath() + "/faces/view/FutsalSchedule/futsalScheduleTable.xhtml");
                } catch (Exception e) {

                }
            } else {

                FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Deletion Failed", "Deletion Failed");
                context.addMessage(null, message);

            }
        }
    }

    @Acl(resourceName = ResourceType.FUTSALSCHEDULE, actionName = ActionType.CREATE)
    public void saveGeneratedSchedules() {
        if (selectedSchedueList != null) {
            try {
                if (fsc.saveAll(selectedSchedueList)) {
                    FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Schedules Saved Successfully", "Schedules Saved Successfully");
                    FacesContext.getCurrentInstance().addMessage(null, message);
                    externalContext = FacesContext.getCurrentInstance().getExternalContext();
                    Flash flash = externalContext.getFlash();
                    flash.setKeepMessages(true);
                    externalContext.redirect(externalContext.getRequestContextPath() + "/faces/view/FutsalSchedule/futsalScheduleTable.xhtml");

                } else {
                    FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Addition Failed", "Addition Failed");
                    context.addMessage(null, message);
                }
            } catch (Exception e) {

            }

        }
    }

}
