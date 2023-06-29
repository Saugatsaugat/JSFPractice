package Model;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
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

    @SuppressWarnings("unchecked")
    private Class<T> getEntityClass() {
        Type type = getClass().getGenericSuperclass();
        ParameterizedType paramType = (ParameterizedType) type;
        return (Class<T>) paramType.getActualTypeArguments()[0];
    }

    public List<T> getAllData(Class<T> cla) {
        CriteriaBuilder criteriaBuilder = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<T> criteriaQuery = criteriaBuilder.createQuery(cla);
        Root<T> root = criteriaQuery.from(cla);
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
            t = getEntityManager().find(getEntityClass(), id);
            return t;
        } catch (Exception e) {

        }
        return t;
    }
    
    public boolean save(T obj){
        try{
            getEntityManager().persist(obj);
            return true;
        }catch(Exception e){
            
        }
        return false;
    }
    
    public boolean saveAll(List<T> obj){
        try{
            for(T item:obj){
                getEntityManager().persist(item);
            }
            return true;
            
        }catch(Exception e){
            
        }
        return false;
    }
    
    public boolean delete(Long id){
        try{
            T obj = getDataById(id);
            getEntityManager().remove(obj);
            return true;
            
        }catch(Exception e){
            
        }
        return false;
    }
    
    public boolean update(T obj, Long id){
        try{
            T existingObj = getEntityManager().find(getEntityClass(),id);
            if(existingObj!=null){
                getEntityManager().merge(obj);
                return true;
            }
        }catch(Exception e){
            
        }
        return false;
    }

}
