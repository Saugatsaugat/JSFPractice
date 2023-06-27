package Model;

import Entities.FutsalSchedule;
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
public class FutsalScheduleCrud extends AbstractCrud<FutsalSchedule> {

    @PersistenceContext(name = "futsal")
    EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    //Save
    public boolean saveFutsalSchedule(FutsalSchedule futsalSchedule) {
        try {
            em.persist(futsalSchedule);
            return true;

        } catch (Exception e) {

            return false;
        }
    }
    
    //get Data By Id
      public FutsalSchedule getDataById(Long id) {
        FutsalSchedule futsalSchedule = null;
        Query query = em.createQuery("SELECT u FROM FutsalSchedule u WHERE u.id=:Id", FutsalSchedule.class);
        query.setParameter("Id", id);
        futsalSchedule = (FutsalSchedule) query.getSingleResult();
        if (futsalSchedule != null) {
            return futsalSchedule;
        } else {
            return futsalSchedule;
        }
    }
      
      /*
      * Get all data
      */
      public List<FutsalSchedule> getAllData() {
        List<FutsalSchedule> futsalSchedule = null;
        Query query = em.createQuery("SELECT u FROM FutsalSchedule u", FutsalSchedule.class);
        futsalSchedule = (List<FutsalSchedule>) query.getResultList();
        if (futsalSchedule != null) {
            return futsalSchedule;
        } else {
            return null;
        }
    }
      
      /*
      * Delete Data by ID Function
      */
        public boolean deleteById(Long userId) {
        String deleteJpql = "Delete from FutsalSchedule e where e.id=:userid";
        Query query = em.createQuery(deleteJpql);
        query.setParameter("userid", userId);
        int deletedCount = query.executeUpdate();
        if (deletedCount > 0) {
            return true;
        } else {
            return false;
        }
    }
        
        /*
        * Update Function
        */
         public boolean update(FutsalSchedule futsalSchedule, Long Id) {
        String updateJpql = "UPDATE FutsalSchedule e SET e.starthour=:starthour,e.endhour=:endhour,e.scheduledate=:scheduledate,e.rate=:rate,e.status=:status WHERE e.id = :entityId";
        Query query = em.createQuery(updateJpql);
        query.setParameter("entityId",  Id);
        query.setParameter("starthour", futsalSchedule.getStarthour());
        query.setParameter("endhour", futsalSchedule.getEndhour());
        query.setParameter("scheduledate", futsalSchedule.getScheduledate());
        query.setParameter("rate", futsalSchedule.getRate());
        query.setParameter("status", futsalSchedule.getStatus());
        int updatedCount = query.executeUpdate();
        if (updatedCount > 0) {
            return true;
        } else {
            return false;
        }
    }
         
         public List<FutsalSchedule> getFutsalSchedule(){
             List<FutsalSchedule> futsalScheduleList =  null;
             try{
                 String getListQuery = "Select e from FutsalSchedule e where e.scheduledate>=CURRENT_DATE";
                 Query query = em.createQuery(getListQuery);
                 futsalScheduleList = (List<FutsalSchedule>) query.getResultList();
                 return futsalScheduleList;
             }
             catch(Exception e){
                 
             }
             return futsalScheduleList;
         }
   
}
