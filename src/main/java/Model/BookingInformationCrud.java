/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

import Entities.BookingInformation;
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
public class BookingInformationCrud extends AbstractCrud<BookingInformation>{
    @PersistenceContext(name="futsal")
    EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
       return em;
    }
    
    public boolean save(List<BookingInformation> data){
        try{
            em.persist(data);
            return true;
        }
        catch(Exception e){
            
        }
        return false;
    }
    
    public BookingInformation getDataById(Long id){
        BookingInformation bookingInformation = null;
        try{
            Query query = em.createQuery("SELECT u from BookingInformation u where u.id=:id",BookingInformation.class);
            query.setParameter("id", id);
            bookingInformation = (BookingInformation) query.getSingleResult();
            return bookingInformation;
        }catch(Exception e){
            
        }
        return bookingInformation;
    }
    
    public boolean deleteById(BookingInformation data,Long id){
        try{
            if(data.getId()==id){
                em.remove(data);
                return true;
            }
        }catch(Exception e){
            
        }
        return false;
    }
    
}
