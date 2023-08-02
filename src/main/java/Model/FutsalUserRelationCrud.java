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

    public FutsalUserRelationCrud() {
        super(FutsalUserRelation.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    

    public List<Object> getAllDataFutsalUser(){
        List<Object> result = null;
        try{
            Query query = em.createNativeQuery("Select * from User");
            result = query.getResultList();
            return result;
            
        }catch(Exception e){
            
        }
        return result;
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
    

}
