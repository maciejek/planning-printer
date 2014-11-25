package pl.wroc.pwr.agile.entity;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
public class Workspace {

    @Id
    @GeneratedValue
    private Integer id;
    
    @OneToOne(mappedBy="workspace", cascade=CascadeType.ALL)
    User scrumMaster;
    
    @OneToOne(mappedBy="workspace", cascade=CascadeType.ALL)
    User deputy;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public User getScrumMaster() {
        return scrumMaster;
    }

    public void setScrumMaster(User scrumMaster) {
        this.scrumMaster = scrumMaster;
    }

    public User getDeputy() {
        return deputy;
    }

    public void setDeputy(User deputy) {
        this.deputy = deputy;
    }
    
}
