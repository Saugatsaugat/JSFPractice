package Model;

import Entities.UserActionResource;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author saugat
 */
@Stateless
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

    public Boolean checkIfExistsByAclDetails(String resource, String userType, String action) {
        UserActionResource userActionResource = new UserActionResource();
        try {
            Query query = em.createQuery("SELECT u from UserActionResource u WHERE u.resource=:resource AND u.userType=:userType AND u.aclAction=:action", UserActionResource.class);
            query.setParameter("resource", resource);
            query.setParameter("userType", userType);
            query.setParameter("action", action);
            userActionResource = (UserActionResource) query.getSingleResult();
            if (userActionResource != null) {
                if (userActionResource.getIsAllowed()) {
                    return true;
                } else {
                    return false;
                }
            } else {
                return false;
            }
        } catch (Exception e) {
            return false;
        }
    }
}
