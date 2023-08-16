package Model;

import Entities.UserActionResource;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author saugat
 */
public class UserActionResourceCrud extends AbstractCrud<UserActionResource> {

    @PersistenceContext(name = "futsal")
    EntityManager em;

    public UserActionResourceCrud() {
        super(UserActionResource.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
}
