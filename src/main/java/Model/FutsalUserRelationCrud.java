/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

import Entities.FutsalUserRelation;
import Entities.User;
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
public class FutsalUserRelationCrud extends AbstractCrud<FutsalUserRelation> {

    @PersistenceContext(name = "futsal")
    EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public List<FutsalUserRelation> getAllData() {
        List<FutsalUserRelation> futsalUserRelation = null;
        try {
            Query query = em.createQuery("SELECT u from FutsalUserRelation u", FutsalUserRelation.class);
            futsalUserRelation = query.getResultList();
            return futsalUserRelation;
        } catch (Exception e) {

        }
        return futsalUserRelation;
    }

    public boolean create(FutsalUserRelation data) {
        try {
            em.persist(data);
            return true;
        } catch (Exception e) {

        }
        return false;
    }

    public FutsalUserRelation getFutsalUserRelationById(Long id) {
        FutsalUserRelation user = null;
        try {
            Query query = em.createQuery("SELECT u FROM FutsalUserRelation u WHERE u.id=:userId", User.class);
            query.setParameter("userId", id);
            user = (FutsalUserRelation) query.getSingleResult();
            return user;
        } catch (Exception e) {

        }
        return user;
    }

    public FutsalUserRelation getFutsalUserRelationByUserId(Long id) {
        FutsalUserRelation user = null;
        try {
            Query query = em.createQuery("SELECT u FROM FutsalUserRelation u WHERE u.userid=:userId", FutsalUserRelation.class);
            query.setParameter("userId", id);
            user = (FutsalUserRelation) query.getSingleResult();
            return user;
        } catch (Exception e) {

        }
        return user;
    }
    
    public boolean save(FutsalUserRelation data){
        try{
            
            em.persist(data);
            return true;
        }catch(Exception e){
            
        }
        return false;
    }
    
    public boolean saveAll(List<FutsalUserRelation> dataList){
        try{
            for(FutsalUserRelation obj:dataList){
                em.persist(obj);
            }
            return true;
        }catch(Exception e){
            
        }
        return false;
    }
    
    public boolean update(FutsalUserRelation data, Long id){
        try{
            if(Objects.equals(data.getId(), id)){
                Query query= em.createQuery("UPDATE FutsalUserRelation u set u.entrydate=:entrydate,u.futsalid=:futsalid,u.userid=:userid WHERE u.id=:id");
                query.setParameter("entrydate",data.getEntrydate());
                query.setParameter("futsalid",data.getFutsalid());
                query.setParameter("userid",data.getUserid());
                query.setParameter("id",id);
                int updatedCount = query.executeUpdate();
                if(updatedCount>0){
                    return true;
                }
            }
        }catch(Exception e){
            
        }
        return false;
    }

    public boolean deleteById(Long id) {
        try{
            Query query=em.createQuery("Delete from FutsalUserRelation u where u.id=:id");
            query.setParameter("id",id);
            int deletedCount = query.executeUpdate();
            if(deletedCount>0){
                return true;
            }
        }catch(Exception e){
            
        }
        return false;
     
    }

}
