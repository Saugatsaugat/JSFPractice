
package Model;

import Entities.BookingDetail;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author saugat
 */

@Stateless
public class BookingDetailCrud extends AbstractCrud<BookingDetail> {
    @PersistenceContext(name="futsal")
    EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
}
