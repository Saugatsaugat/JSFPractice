
package Model;

/**
 *
 * @author saugat
 */
public interface IAbstractEntity {
    
    public abstract Long getId();
    public abstract void setId(Long id);
    public String getTableName();
    
}
