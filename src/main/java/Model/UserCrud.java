package Model;

import Controller.PasswordHashController;
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
public class UserCrud extends AbstractCrud<User> {

    @PersistenceContext(name = "futsal")
    EntityManager em;

    public UserCrud() {
        super(User.class);
    }

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

}
