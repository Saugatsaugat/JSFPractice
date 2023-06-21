
package Model;

import javax.persistence.Id;

/**
 *
 * @author saugat
 */
public abstract class AbstractEntity {
    @Id
    private Long id;
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
}
