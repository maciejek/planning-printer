package pl.wroc.pwr.agile.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Size;

@Entity
public class UserStory {
    
    @Id
    @GeneratedValue
    private Integer id;
    
    @Size(min = 1, message = "You must provide summary for user story!")
    private String summary;
    
    private Integer storyPoints;
    
    @ManyToOne
    private Workspace workspace;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public Integer getStoryPoints() {
        return storyPoints;
    }

    public void setStoryPoints(Integer storyPoints) {
        this.storyPoints = storyPoints;
    }

    public Workspace getWorkspace() {
        return workspace;
    }

    public void setWorkspace(Workspace workspace) {
        this.workspace = workspace;
    }

}
