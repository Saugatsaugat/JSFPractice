/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

import Entities.FutsalDetail;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author saugat
 */
@Stateless
public class FutsalDetailCrud extends AbstractCrud<FutsalDetail>{

    @PersistenceContext(name="futsal")
    EntityManager em;

    public FutsalDetailCrud() {
        super(FutsalDetail.class);
    }
    
    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    
   
    
}
