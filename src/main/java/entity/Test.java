package entity;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by User on 12.07.2017.
 */
@XmlRootElement(name = "test")
public class Test extends BaseEntity {
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
