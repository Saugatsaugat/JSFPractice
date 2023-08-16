package Model;

import Entities.AclAction;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author saugat
 */
public class AclActionCrud extends AbstractCrud<AclAction> {

    @PersistenceContext(name = "futsal")
    EntityManager em;

    public AclActionCrud() {
        super(AclAction.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
}
