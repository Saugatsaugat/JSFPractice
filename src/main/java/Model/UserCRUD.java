/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

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
public class UserCRUD extends AbstractCRUD<User> {

    @PersistenceContext(name = "futsal")
    EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public User findByUsernameAndPassword(String username, String password) {
        User user = null;
        try {

            Query query = em.createQuery("Select u from User u where u.email=:em and u.userpassword=:pa", User.class);
            System.out.println(query);
            query.setParameter("em", username);
            query.setParameter("pa", password);
            user = (User) query.getSingleResult();
        } catch (Exception e) {
        }
        return user;
    }

    public boolean saveUser(User user) {
        try {

            em.persist(user);
            return true;

        } catch (Exception e) {
            
            return false;

        }
    }

}
