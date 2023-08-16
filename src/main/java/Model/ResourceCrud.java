package Model;

import Entities.Resource;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author saugat
 */

@Stateless
public class ResourceCrud extends AbstractCrud<Resource> {

    @PersistenceContext(name = "futsal")
    EntityManager em;

    public ResourceCrud() {
        super(Resource.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
}
