/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

import Entities.FutsalUserRelation;
import Entities.User;
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
    public boolean create(FutsalUserRelation data){
        try{
            em.persist(data);
            return true;
        }
        catch(Exception e){
            
        }
        return false;
    }
    public boolean deleteById(Long userId) {
        String deleteJpql = "Delete from FutsalUserRelation e where e.id=:userid";
        Query query = em.createQuery(deleteJpql);
        query.setParameter("userid", userId);
        int deletedCount = query.executeUpdate();
        if(deletedCount>0){
            return true;
        }
        return false;
    }
    
    
    public FutsalUserRelation getFutsalUserRelationById(Long id) {
        FutsalUserRelation user = null;
        try{
        Query query = em.createQuery("SELECT u FROM FutsalUserRelation u WHERE u.id=:userId", User.class);
        query.setParameter("userId", id);
        user = (FutsalUserRelation) query.getSingleResult();
        return user;
        }
        catch(Exception e){
            
        }
        return user;
    }
    
    public FutsalUserRelation getFutsalUserRelationByUserId(Long id) {
        FutsalUserRelation user = null;
        try{
        Query query = em.createQuery("SELECT u FROM FutsalUserRelation u WHERE u.userid=:userId", FutsalUserRelation.class);
        query.setParameter("userId", id);
        user = (FutsalUserRelation) query.getSingleResult();
        return user;
        }
        catch(Exception e){
            
        }
        return user;
    }

    
}
