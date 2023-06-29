
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
public class FutsalScheduleCruds extends AbstractCrud<FutsalSchedule> {
    @PersistenceContext(name="futsal")
    EntityManager em;
    
    @Override
    protected EntityManager getEntityManager(){
        return em;
    }
    
    public List<FutsalSchedule> getFutsalScheduleByDateAndUserId(java.util.Date date,Long futsalId){
        List<FutsalSchedule> futsalSchedule = null;
        try{
            java.sql.Date sqlDate = new java.sql.Date(date.getTime());
            Query query = em.createQuery("SELECT u from FutsalSchedule u where u.scheduledate=:date and u.futsalid=:futsalId", FutsalSchedule.class);
            query.setParameter("date", sqlDate);
            query.setParameter("futsalId", futsalId);
            futsalSchedule = query.getResultList();
            return futsalSchedule;
        }
        catch(Exception e){
        }
        return futsalSchedule;
    }
    
    public List<FutsalSchedule> getCurrentDateFutsalSchedule(){
        List<FutsalSchedule> futsalSchedule = null;
        try{
            Query query = em.createQuery("SELECT u from FutsalSchedule u", FutsalSchedule.class);
            futsalSchedule = query.getResultList();
            return futsalSchedule;
        }catch(Exception e){
            
        }
        return futsalSchedule;
    }
    
    
    public boolean save(FutsalSchedule data){
        try{
            em.persist(data);
            return true;
        }
        catch(Exception e){
            return false;
        }
        
    }
    public boolean saveAll(List<FutsalSchedule> dataList){
        try{
            for(FutsalSchedule obj:dataList){
                em.persist(obj);
            }
            return true;
        }
        catch(Exception e){
            
        }
        return false;
    }
    
//    public FutsalSchedule getDataById(Long id){
//        FutsalSchedule futsalSchedule = null;
//        try{
//            Query query = em.createQuery("SELECT u from FutsalSchedule u where u.id=:id",FutsalSchedule.class );
//            query.setParameter("id", id);
//            futsalSchedule = (FutsalSchedule)query.getSingleResult();
//            return futsalSchedule;
//        }catch(Exception e){
//            
//        }
//        return futsalSchedule;
//    }
    
    public boolean deleteById(Long id){
        try{
          Query query = em.createQuery("Delete from FutsalSchedule u where u.id=:id");
          query.setParameter("id",id);
          int deleteCounted = query.executeUpdate();
          if(deleteCounted>0){
              return true;
          }
        }catch(Exception e){
            
        }
        return false;
    }
    
    public boolean update(FutsalSchedule data, Long id){
        FutsalSchedule futsalSchedule = null;
        try{
            futsalSchedule = getDataById(id);
            if(futsalSchedule!=null){
                Query query = em.createQuery("UPDATE FutsalSchedule u set u.starthour=:starthour,u.endhour=:endhour,u.scheduledate=:scheduledate,u.rate=:rate,u.status=:status where u.id=:id", FutsalSchedule.class);
                query.setParameter("starthour", data.getStarthour());
                query.setParameter("endhour", data.getEndhour());
                query.setParameter("scheduledate", data.getScheduledate());
                query.setParameter("rate", data.getRate());
                query.setParameter("status", data.getStatus());
                query.setParameter("id", data.getId());
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
