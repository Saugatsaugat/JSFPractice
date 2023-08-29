package Model;

import Entities.Payment;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author saugat
 */
@Stateless
public class PaymentCrud extends AbstractCrud<Payment> {

    @PersistenceContext(name = "futsal")
    EntityManager em;

    public PaymentCrud() {
        super(Payment.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

}
