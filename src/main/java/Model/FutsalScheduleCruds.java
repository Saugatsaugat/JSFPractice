package Model;

import Entities.FutsalSchedule;
import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TemporalType;

/**
 *
 * @author saugat
 */
@Stateless
public class FutsalScheduleCruds extends AbstractCrud<FutsalSchedule> {

    @PersistenceContext(name = "futsal")
    EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public List<FutsalSchedule> getFutsalScheduleByDateAndUserId(java.util.Date date, Long futsalId) {
        List<FutsalSchedule> futsalSchedule = null;
        try {
            java.sql.Date sqlDate = new java.sql.Date(date.getTime());
            Query query = em.createQuery("SELECT u from FutsalSchedule u where u.scheduledate=:date and u.futsalid=:futsalId", FutsalSchedule.class);
            query.setParameter("date", sqlDate);
            query.setParameter("futsalId", futsalId);
            futsalSchedule = query.getResultList();
            return futsalSchedule;
        } catch (Exception e) {
        }
        return futsalSchedule;
    }

    public List<FutsalSchedule> getCurrentDateFutsalSchedule() {
        List<FutsalSchedule> futsalSchedule = null;
        try {
            Query query = em.createQuery("SELECT u FROM FutsalSchedule u WHERE u.scheduledate = :currentDate", FutsalSchedule.class);
            query.setParameter("currentDate", new Date(), TemporalType.DATE);

            futsalSchedule = query.getResultList();
            return futsalSchedule;
        } catch (Exception e) {

        }
        return futsalSchedule;
    }

}
