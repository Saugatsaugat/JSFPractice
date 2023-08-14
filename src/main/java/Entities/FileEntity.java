package Entities;

import Model.AbstractEntity;
import Model.IAbstractEntity;
import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 *
 * @author saugat
 */
@Entity
@Table(name = "File")
public class FileEntity extends AbstractEntity<Futsal> implements IAbstractEntity, Serializable {

    private String filePath;

    public String getFilename() {
        return filePath;
    }

    public void setFilename(String filePath) {
        this.filePath = filePath;
    }

    @Override
    public String getTableName() {
        return "File";
    }

}
