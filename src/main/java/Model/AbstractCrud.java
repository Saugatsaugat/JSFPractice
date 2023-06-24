package Model;

import java.util.List;
import javax.persistence.EntityManager;
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
    private T obj;

    public List<T> getAllData(Class<T> cla) {
        CriteriaBuilder criteriaBuilder = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<T> criteriaQuery = criteriaBuilder.createQuery(cla);
        Root<T> root = criteriaQuery.from(cla);
        criteriaQuery.select(root);

        TypedQuery<T> query = getEntityManager().createQuery(criteriaQuery);
        List<T> list =  query.getResultList();
        if(list!=null){
            return list;
        }
        return null;
    }
    
    public T getDataById(Class<T> cla, Long id){
        return null;
    }
    
    

}
