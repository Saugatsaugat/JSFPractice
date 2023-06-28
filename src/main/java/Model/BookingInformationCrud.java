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
public class BookingInformationCrud extends AbstractCrud<BookingInformation> {

    @PersistenceContext(name = "futsal")
    EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public boolean save(List<BookingInformation> data) {
        try {
            em.persist(data);
            return true;
        } catch (Exception e) {

        }
        return false;
    }

    public BookingInformation getDataById(Long id) {
        BookingInformation bookingInformation = null;
        try {
            Query query = em.createQuery("SELECT u from BookingInformation u where u.id=:id", BookingInformation.class);
            query.setParameter("id", id);
            bookingInformation = (BookingInformation) query.getSingleResult();
            return bookingInformation;
        } catch (Exception e) {

        }
        return bookingInformation;
    }

    public boolean deleteById(Long id) {
        try {
            if (getDataById(id) != null) {
                BookingInformation data = getDataById(id);
                em.remove(data);
                return true;
            }
        } catch (Exception e) {

        }
        return false;
    }

    public boolean saveAll(List<BookingInformation> data) {
        try {
            for (BookingInformation obj : data) {
                em.persist(obj);
            }
            return true;
        } catch (Exception e) {

        }
        return false;
    }

    public boolean update(BookingInformation data, Long id) {
        try {
            java.sql.Date sqlEntryDate = new java.sql.Date(data.getEntrydate().getTime());
            java.sql.Date sqlFromDate = new java.sql.Date(data.getFromdate().getTime());
            java.sql.Date sqlToDate = new java.sql.Date(data.getTodate().getTime());
            
            Query query = em.createQuery("Update BookingInformation u set u.entrydate=:entrydate,u.fromdate=:fromdate,u.todate=:todate,u.amount=:amount,u.userid=:userid where u.id=:id", BookingInformation.class);
            query.setParameter("id", id);
            query.setParameter("entrydate", sqlEntryDate);
            query.setParameter("fromdate", sqlFromDate);
            query.setParameter("todate", sqlToDate);
            query.setParameter("amount",data.getAmount());
            query.setParameter("userid", data.getUserid());
            int updatedCount = query.executeUpdate();
            if(updatedCount>0){
                return true;
            }
            
        }catch(Exception e){
            
        }
        return false;
    }

}
