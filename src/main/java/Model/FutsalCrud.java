/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

import Controller.PasswordHashController;
import Entities.Futsal;
import Entities.User;
import java.util.List;
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

    public boolean saveFutsal(Futsal futsal) {
        try {
            em.persist(futsal);
            return true;

        } catch (Exception e) {

            return false;
        }
    }

    public Futsal getFutsalById(Long id) {
        Futsal futsal = null;
        Query query = em.createQuery("SELECT u FROM Futsal u WHERE u.id=:userId", Futsal.class);
        query.setParameter("userId", id);
        futsal = (Futsal) query.getSingleResult();
        if (futsal != null) {
            return futsal;
        } else {
            return futsal;
        }
    }

    public List<Futsal> getAllData() {
        List<Futsal> futsal = null;
        Query query = em.createQuery("SELECT u FROM Futsal u", Futsal.class);
        futsal = (List<Futsal>) query.getResultList();
        if (futsal != null) {
            return futsal;
        } else {
            return null;
        }
    }

    public boolean deleteById(Long userId) {
        String deleteJpql = "Delete from Futsal e where e.id=:userid";
        Query query = em.createQuery(deleteJpql);
        query.setParameter("userid", userId);
        int deletedCount = query.executeUpdate();
        if (deletedCount > 0) {
            return true;
        } else {
            return false;
        }
    }

    public boolean update(Futsal futsal, Long futsalId) {
        String updateJpql = "UPDATE Futsal e SET e.pan=:pan,e.name=:name,e.rate=:rate,e.address=:address WHERE e.id = :entityId";
        Query query = em.createQuery(updateJpql);
        query.setParameter("entityId", futsalId);
        query.setParameter("pan", futsal.getPan());
        query.setParameter("name", futsal.getName());
        query.setParameter("rate", futsal.getRate());
        query.setParameter("address", futsal.getAddress());
        int updatedCount = query.executeUpdate();
        if (updatedCount > 0) {
            return true;
        } else {
            return false;
        }
    }

}
