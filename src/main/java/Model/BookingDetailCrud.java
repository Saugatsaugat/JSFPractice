package Model;

import Entities.BookingDetail;
import Entities.Futsal;
import Entities.User;
import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author saugat
 */
@Stateless
public class BookingDetailCrud extends AbstractCrud<BookingDetail> {

    @PersistenceContext(name = "futsal")
    EntityManager em;

    public BookingDetailCrud() {
        super(BookingDetail.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public List<BookingDetail> bookingDetailOfCurrentDateAndHigher() {
        List<BookingDetail> bookingDetailList = null;
        try {
            Query query = em.createQuery("SELECT u from BookingDetail u where u.futsalschedule.scheduledate>=:date", BookingDetail.class);
            query.setParameter("date", new Date());
            bookingDetailList = query.getResultList();
            return bookingDetailList;
        } catch (Exception e) {

        }
        return bookingDetailList;
    }

    public List<BookingDetail> bookingDetailOfCurrentDateAndHigherByUser(User user) {
        List<BookingDetail> bookingDetailList = null;
        try {
            Query query = em.createQuery("SELECT u from BookingDetail u where u.bookinginformation.user=:user and u.futsalschedule.scheduledate>=:date", BookingDetail.class);
            query.setParameter("user", user);
            query.setParameter("date", new Date());
            bookingDetailList = query.getResultList();
            return bookingDetailList;
        } catch (Exception e) {

        }
        return bookingDetailList;
    }

    public List<BookingDetail> bookingDetailByFutsalOwner(Futsal futsal) {
        List<BookingDetail> bookingDetailList = null;
        try {
            Query query = em.createQuery("SELECT u from BookingDetail u where u.futsalschedule.futsal=:futsal and u.futsalschedule.scheduledate>=:date", BookingDetail.class);
            query.setParameter("date", new Date());
            query.setParameter("futsal", futsal);
            bookingDetailList = query.getResultList();
            return bookingDetailList;
        } catch (Exception e) {

        }
        return bookingDetailList;
    }

    public BookingDetail getBookingDetailInformationById(Long id) {
        BookingDetail bookingDetail = null;
        try {
            Query query = em.createQuery("SELECT u FROM BookingDetail u where u.id=:id", BookingDetail.class);
            query.setParameter("id", id);
            bookingDetail = (BookingDetail) query.getSingleResult();
            return bookingDetail;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bookingDetail;
    }
}
