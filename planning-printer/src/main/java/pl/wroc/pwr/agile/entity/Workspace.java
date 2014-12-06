package pl.wroc.pwr.agile.entity;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;

@Entity
public class Workspace {

    @Id
    @GeneratedValue
    private Integer id;
    
    @OneToOne
    @PrimaryKeyJoinColumn
    User scrumMaster;

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
    
}
