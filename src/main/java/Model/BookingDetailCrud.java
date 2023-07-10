
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
    @PersistenceContext(name="futsal")
    EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    
    public List<BookingDetail> bookingDetailOfCurrentDateAndHigher(){
        List<BookingDetail> bookingDetailList = null;
        try{
            Query query= em.createQuery("SELECT u from BookingDetail u where u.bookinginformation.fromdate=:date",BookingDetail.class);
            query.setParameter("date", new Date());
            bookingDetailList = query.getResultList();
            return bookingDetailList;
        }catch(Exception e){
            
        }
        return bookingDetailList;
    }
     public List<BookingDetail> bookingDetailOfCurrentDateAndHigherByUser(User user){
        List<BookingDetail> bookingDetailList = null;
        try{
            Query query= em.createQuery("SELECT u from BookingDetail u where u.bookinginformation.user=:user",BookingDetail.class);
            query.setParameter("user", user);
            bookingDetailList = query.getResultList();
            return bookingDetailList;
        }catch(Exception e){
            
        }
        return bookingDetailList;
    }
     
     public List<BookingDetail> bookingDetailByFutsalOwner(Futsal futsal){
         List<BookingDetail> bookingDetailList = null;
         try{
             Query query = em.createQuery("SELECT u from BookingDetail u where u.futsalschedule.futsal=:futsal",BookingDetail.class);
             query.setParameter("futsal", futsal);
             bookingDetailList = query.getResultList();
             return bookingDetailList;
         }catch(Exception e){
             
         }
         return bookingDetailList;
     }
}
