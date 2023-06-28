/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

import Entities.FutsalDetail;
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
public class FutsalDetailCrud extends AbstractCrud<FutsalDetail>{

    @PersistenceContext(name="futsal")
    EntityManager em;
    
    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    
    public List<FutsalDetail> getAllData(){
       List<FutsalDetail> futsalDetail=null;
       try{
           Query query = em.createQuery("SELECT u from FutsalDetail u",FutsalDetail.class);
           futsalDetail = query.getResultList();
           return futsalDetail;
       }catch(Exception e){
           
       }
       return futsalDetail;
    }
    
    public FutsalDetail getDataById(Long id){
        FutsalDetail futsalDetail  = null;
        try{
            Query query = em.createQuery("SELECT u from FutsalDetail u WHERE u.id=:id",FutsalDetail.class);
            futsalDetail = (FutsalDetail)query.getSingleResult();
            return futsalDetail;
        }catch(Exception e){
            
        }
        return futsalDetail;
    }
    
    public boolean save(FutsalDetail data){
        try{
            em.persist(data);
            return true;
        }catch(Exception e){
            
        }
        return false;
    }
    
    public boolean saveAll(List<FutsalDetail> dataList){
        try{
            for(FutsalDetail obj: dataList){
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
                FutsalDetail obj = getDataById(id);
                em.remove(obj);
                return true;
            }
        }catch(Exception e){
            
        }
        return false;
    }
    
    public boolean update(FutsalDetail data, Long id){
        try{
            if(Objects.equals(data.getId(), id)){
                Query query= em.createQuery("Update FutsalDetail u set u.length=:length,u.breadth=:breadth,u.dressingroom=:dressingroom,u.washroom=:washroom,u.futsalid=:futsalid WHERE u.id=:id",FutsalDetail.class);
                query.setParameter("id",id);
                query.setParameter("length", (float)data.getLength());
                query.setParameter("breadth",(float)data.getBreadth());
                query.setParameter("dressingroom",data.isDressingroom());
                query.setParameter("washroom",data.isWashroom());
                query.setParameter("futsalid",data.getFutsalid());
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
