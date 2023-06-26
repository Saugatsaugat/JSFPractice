package Model;

import Controller.PasswordHashController;
import Entities.User;
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
public class UserCrud extends AbstractCrud<User> {

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

            String passHash = new PasswordHashController().getPasswordHash(password);
            query.setParameter("em", username);
            query.setParameter("pa", passHash);
            user = (User) query.getSingleResult();
            return user;
        } catch (Exception e) {
        }
        return user;
    }

    public boolean saveUser(User user) {
        String passHash = new PasswordHashController().getPasswordHash(user.getUserpassword());
        user.setUserpassword(passHash);
        try {
            em.persist(user);
            return true;

        } catch (Exception e) {

            return false;
        }
    }

    public User getUserById(Long id) {
        User user = null;
        Query query = em.createQuery("SELECT u FROM User u WHERE u.id=:userId", User.class);
        query.setParameter("userId", id);
        user = (User) query.getSingleResult();
        if (user != null) {
            return user;
        } else {
            return user;
        }
    }

    public List<User> getAllData() {
        List<User> user = null;
        Query query = em.createQuery("SELECT u FROM User u", User.class);
        user = (List<User>) query.getResultList();
        if (user != null) {
            return user;
        } else {
            return null;
        }
    }

    public boolean update(User user, Long userId) {
        String passHash = new PasswordHashController().getPasswordHash(user.getUserpassword());
        user.setUserpassword(passHash);
        String updateJpql = "UPDATE User e SET e.usertype=:userType,e.firstname=:firstname,e.midname=:midname,e.lastname=:lastname,e.email=:email,e.mobile=:mobile,e.userpassword=:password WHERE e.id = :entityId";
        Query query = em.createQuery(updateJpql);
        query.setParameter("entityId", userId);
        query.setParameter("userType", user.getUsertype());
        query.setParameter("firstname", user.getFirstname());
        query.setParameter("midname", user.getMidname());
        query.setParameter("lastname", user.getLastname());
        query.setParameter("email", user.getEmail());
        query.setParameter("mobile", user.getMobile());
        query.setParameter("password", passHash);

        int updatedCount = query.executeUpdate();
        if (updatedCount > 0) {
            return true;
        } else {
            return false;
        }
    }

    public boolean deleteById(Long userId) {
        String deleteJpql = "Delete from User e where e.id=:userid";
        Query query = em.createQuery(deleteJpql);
        query.setParameter("userid", userId);
        int deletedCount = query.executeUpdate();
        if (deletedCount > 0) {
            return true;
        } else {
            return false;
        }
    }

}
