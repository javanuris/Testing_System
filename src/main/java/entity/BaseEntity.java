package entity;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author Kalenov Nurislam
 */
public abstract class BaseEntity {
    private int id;
    public int getId() {
        return id;
    }public void setId(int id) {
        this.id = id;
    }
}