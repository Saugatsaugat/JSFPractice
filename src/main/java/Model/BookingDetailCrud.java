
package Model;

import Entities.BookingDetail;
import java.util.List;
import java.util.Objects;
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
    
    public List<BookingDetail> getAllData(){
        List<BookingDetail> bookingDetail = null;
        try{
            Query query= em.createQuery("SELECT u from BookingDetail u",BookingDetail.class);
            bookingDetail = query.getResultList();
            return bookingDetail;
        }catch(Exception e){
            
        }
        return bookingDetail;
    }
    
    public BookingDetail getDataById(Long id){
        BookingDetail bookingDetail = null;
        try{
            Query query = em.createQuery("SELECT u from BookingDetail u WHERE u.id=:id",BookingDetail.class);
            bookingDetail = (BookingDetail)query.getSingleResult();
            return bookingDetail;
        }catch(Exception e){
            
        }
        return bookingDetail;
    }
    
    public boolean save(BookingDetail data){
        try{
            em.persist(data);
            return true;
        }catch(Exception e){
            
        }
        return false;
    }
    
    
    public boolean saveAll(List<BookingDetail> dataList){
        try{
            for(BookingDetail obj:dataList){
                em.persist(obj);
            }
            return true;
        }catch(Exception e){
            
        }
        return false;
    }
    
    public boolean deleteById(Long id){
        try{
            if(getDataById(id)!=null){
                BookingDetail obj = getDataById(id);
                if(obj!=null){
                    em.remove(obj);
                    return true;
                }
            }
        }catch(Exception e){
            
        }
        return false;
    }
    
    public boolean update(BookingDetail data, Long id){
        try{
            if(Objects.equals(data.getId(), id)){
                Query query=em.createQuery("UPDATE BookingDetail u set u.paymentstatus=:paymentstatus,u.bookinginformationid=:bookinginformationid,u.futsalscheduleid=:futsalscheduleid where u.id=:id",BookingDetail.class);
                query.setParameter("id",id);
                query.setParameter("paymentstatus",data.getPaymentstatus());
                query.setParameter("bookinginformationid",data.getBookinginformationid());
                query.setParameter("futsalscheduleid",data.getFutsalscheduleid());
                int updatedCount = query.executeUpdate();
                if(updatedCount>0){
                    return true;
                }
            }
            
        }catch(Exception e){
            
        }
        return false;
    }
    
    
}
