
package Model;

import Entities.Futsal;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author saugat
 */
@Stateless
public class FutsalCrud extends AbstractCrud<Futsal> {

    @PersistenceContext(name = "futsal")
    EntityManager em;

    public FutsalCrud() {
        super(Futsal.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public Futsal checkIfFutsalRegistered(Long userId) {
        Futsal futsal = null;
        try {
            Long id = userId;
            Query query = em.createQuery("Select u from Futsal u where u.ownerid=:id", Futsal.class);
            query.setParameter("id", userId);
            futsal = (Futsal) query.getSingleResult();
            return futsal;
        } catch (Exception e) {

        }
        return futsal;
    }
    

}
