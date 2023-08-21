package Model;

import Entities.AclAction;
import Entities.Resource;
import Entities.UserActionResource;
import com.saugat.bean.enums.ActionType;
import com.saugat.bean.enums.ResourceType;
import com.saugat.bean.enums.UserType;
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

    public Boolean checkIfExistsByAclDetails(Resource resource, UserType userType, AclAction action, Boolean allow) {
        UserActionResource userActionResource = new UserActionResource();
        try {
            Query query = em.createQuery("SELECT u from UserActionResource u WHERE u.resource=:resource AND u.userType=:userType AND u.aclAction=:action AND u.isAllowed=:allow", UserActionResource.class);
            query.setParameter("resource", resource);
            query.setParameter("userType", userType);
            query.setParameter("action", action);
            query.setParameter("allow", allow);

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
            e.printStackTrace();
            return false;
        }
    }

    public Boolean checkIfExistsByAclDetail(ResourceType resource, UserType userType, ActionType action) {
        UserActionResource userActionResource = new UserActionResource();
        try {
            Query query = em.createQuery("SELECT u from UserActionResource u WHERE u.resource.resourceName=:resource AND u.userType=:userType AND u.aclAction.actionName=:action", UserActionResource.class);
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
            e.printStackTrace();
            return false;
        }
    }
}
