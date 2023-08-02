package Model;

import Entities.BookingDetail;
import Entities.BookingInformation;
import Entities.Futsal;
import Entities.FutsalSchedule;
import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TemporalType;
import javax.transaction.Transactional;

/**
 *
 * @author saugat
 */
@Stateless
@Transactional
public class FutsalScheduleCruds extends AbstractCrud<FutsalSchedule> {
    
    @Inject
    private BookingInformationCrud bookingInformationCrud;
    @Inject
    private BookingDetailCrud bookingDetailCrud;
        
    @PersistenceContext(name = "futsal")
    EntityManager em;

    public FutsalScheduleCruds() {
        super(FutsalSchedule.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
   
    public List<FutsalSchedule> getCurrentDateDataByFutsal(Futsal futsal) {
        List<FutsalSchedule> futsalSchedule = null;
        try {
            
            Query query = em.createQuery("SELECT u from FutsalSchedule u where u.futsal=:futsal and u.scheduledate=:date", FutsalSchedule.class);
            query.setParameter("futsal", futsal);
            query.setParameter("date", new Date());
            futsalSchedule = query.getResultList();
            return futsalSchedule;
        } catch (Exception e) {

        }
        return futsalSchedule;
    }

    public List<FutsalSchedule> getFutsalScheduleByDateAndUserId(java.util.Date date, Futsal futsal) {
        List<FutsalSchedule> futsalSchedule = null;
        try {
            java.sql.Date sqlDate = new java.sql.Date(date.getTime());
            Query query = em.createQuery("SELECT u from FutsalSchedule u where u.scheduledate=:date and u.futsal=:futsal", FutsalSchedule.class);
            query.setParameter("date", sqlDate);
            query.setParameter("futsal", futsal);
            futsalSchedule = query.getResultList();
            return futsalSchedule;
        } catch (Exception e) {
        }
        return futsalSchedule;
    }

    public List<FutsalSchedule> getCurrentDateFutsalSchedule() {
        List<FutsalSchedule> futsalSchedule = null;
        try {
            Query query = em.createQuery("SELECT u FROM FutsalSchedule u WHERE u.scheduledate = :currentDate", FutsalSchedule.class);
            query.setParameter("currentDate", new Date(), TemporalType.DATE);

            futsalSchedule = query.getResultList();
            return futsalSchedule;
        } catch (Exception e) {

        }
        return futsalSchedule;
    }
    
    public boolean saveBooking(BookingInformation bookingInformation, FutsalSchedule futsalSchedule){
        try{
            if(bookingInformationCrud.save(bookingInformation)){
                BookingDetail bookingDetail = new BookingDetail();
                bookingDetail.setBookinginformation(bookingInformation);
                bookingDetail.setFutsalschedule(futsalSchedule);
                bookingDetail.setPaymentstatus("incomplete");
                if(bookingDetailCrud.save(bookingDetail)){
                    futsalSchedule.setStatus("booked");
                    if(this.update(futsalSchedule, futsalSchedule.getId())){
                        return true;
                    }
                }
            }
            
        }catch(Exception e){
            
        }
        return false;
        
    }
    
    public boolean checkIfRecordExistsByDateAndTime(Date scheduleDate, Date startHour, Date endHour, Futsal futsal){
        try{
            Query query = em.createQuery("SELECT u from FutsalSchedule u where u.scheduledate=:scheduledate and u.starthour=:starthour and u.endhour=:endhour and u.futsal=:futsal");
            query.setParameter("scheduledate", scheduleDate);
            query.setParameter("starthour",startHour);
            query.setParameter("endhour",endHour);
            query.setParameter("futsal",futsal);
            FutsalSchedule futsalSchedule = (FutsalSchedule) query.getSingleResult();
            if(futsalSchedule!=null){
                return true;
            }
        }catch(Exception e){
            
        }
        return false;
    }
    
    

}
