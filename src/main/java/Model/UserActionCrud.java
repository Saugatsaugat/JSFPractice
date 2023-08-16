package Model;

import Entities.UserAction;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author saugat
 */
public class UserActionCrud extends AbstractCrud<UserAction> {

    @PersistenceContext(name = "futsal")
    EntityManager em;

    public UserActionCrud() {
        super(UserAction.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
}
