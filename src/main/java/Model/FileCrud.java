package Model;

import Entities.FileEntity;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author saugat
 */
@Stateless
public class FileCrud extends AbstractCrud<FileEntity> {

    @PersistenceContext(name = "futsal")
    EntityManager em;

    public FileCrud() {
        super(FileEntity.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
}
