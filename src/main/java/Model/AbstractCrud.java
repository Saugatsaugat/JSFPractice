package Model;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

/**
 *
 * @author saugat
 * @param <T>
 */
public abstract class AbstractCrud<T extends IAbstractEntity> {

    protected abstract EntityManager getEntityManager();
    
    private final Class<T> entityClass;

    public AbstractCrud(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

 

    public List<T> getAllData() {
        CriteriaBuilder criteriaBuilder = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<T> criteriaQuery = criteriaBuilder.createQuery(entityClass);
        Root<T> root = criteriaQuery.from(entityClass);
        criteriaQuery.select(root);

        TypedQuery<T> query = getEntityManager().createQuery(criteriaQuery);
        List<T> list = query.getResultList();
        if (list != null) {
            return list;
        }
        return null;
    }

    public T getDataById(Long id) {
        T t = null;
        try {
            t = getEntityManager().find(entityClass, id);
            return t;
        } catch (Exception e) {

        }
        return t;
    }

    public boolean save(T obj) {
        try {
            getEntityManager().persist(obj);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean saveAll(List<T> obj) {

        try {
            for (T item : obj) {
                getEntityManager().persist(item);
            }
            return true;

        } catch (Exception e) {
        }
        return false;
    }

    public boolean deleteById(Long id) {
        try {
            T obj = getDataById(id);
            getEntityManager().remove(obj);
            return true;

        } catch (Exception e) {

        }
        return false;
    }

    public boolean update(T obj, Long id) {
        try {
            T existingObj = getEntityManager().find(entityClass, id);
            if (existingObj != null) {
                getEntityManager().merge(obj);
                return true;
            }
        } catch (Exception e) {

        }
        return false;
    }

    public boolean checkIfExits(T obj) {
        try {
            T existingObj = getEntityManager().find(entityClass, obj.getId());
            if (existingObj.equals(obj)) {
                return true;
            }
        } catch (Exception e) {

        }
        return false;
    }

}
