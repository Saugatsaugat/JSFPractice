/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

import Entities.User;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

/**
 *
 * @author saugat
 * @param <T>
 */
public class AbstractCRUD<T extends IAbstractEntity> {
    
    EntityManagerFactory emf;
    EntityManager em;

    private T obj;

    public AbstractCRUD(T obj) {
        this.obj = obj;
        emf = Persistence.createEntityManagerFactory("tester");
        em = emf.createEntityManager();
    }

    //Get all Data from Table
    public List<User> getAllData() {
        TypedQuery<User> query = em.createQuery("Select e from "+obj.getTableName()+" e", User.class);
        List<User> list = query.getResultList();
        return list;     
    }
    
    
}
