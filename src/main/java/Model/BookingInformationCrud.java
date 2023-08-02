/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

import Entities.BookingInformation;
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
public class BookingInformationCrud extends AbstractCrud<BookingInformation> {

    @PersistenceContext(name = "futsal")
    EntityManager em;

    public BookingInformationCrud() {
        super(BookingInformation.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public List<BookingInformation> getCurrentAndFutureBookingInformation() {
        List<BookingInformation> bookingInformationList = null;
        try {
            Query query = em.createQuery("SELECT u from BookingInformation u WHERE u.fromdate>=:currentDate", BookingInformation.class);
            bookingInformationList = query.getResultList();
            return bookingInformationList;

        } catch (Exception e) {

        }
        return bookingInformationList;
    }

    public List<BookingInformation> getCurrentAndFutureBookingInformationByUser(User user) {
        List<BookingInformation> bookingInformationList = null;
        try {
            Query query = em.createQuery("SELECT u from BookingInformation u WHERE u.user=:user", BookingInformation.class);
            query.setParameter("user", user);
            bookingInformationList = query.getResultList();
            return bookingInformationList;

        } catch (Exception e) {

        }
        return bookingInformationList;
    }

    public BookingInformation getBookingInformationByDateAndUser(User user, Date fromDate) {
        BookingInformation obj = null;
        try {
            Query query = em.createQuery("SELECT u from BookingInformation u where u.user=:user and u.entrydate=:date and u.fromdate=:fromdate", BookingInformation.class);
            query.setParameter("user", user);
            query.setParameter("date", new Date());
            obj = (BookingInformation)query.getSingleResult();
            return obj;
        }catch(Exception e){
            
        }
        return obj;
    }

}
