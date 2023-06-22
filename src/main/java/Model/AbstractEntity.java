package Model;

import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

/**
 *
 * @author saugat
 */
@MappedSuperclass
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
