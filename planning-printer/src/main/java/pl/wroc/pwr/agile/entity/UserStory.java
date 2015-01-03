package pl.wroc.pwr.agile.entity;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.Size;

@Entity
public class UserStory implements Comparable<UserStory>{

    @Id
    @GeneratedValue
    private Integer id;
    
    private String number;

	@Size(min = 1, message = "You must provide summary for user story!")
    private String summary;

	private String points;

	@ManyToOne
    private Workspace workspace;

    @OneToMany(targetEntity=Task.class, mappedBy="userStory", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<Task> tasks;

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
    
    public String getPoints() {
    	return points;
    }
    
    public void setPoints(String points) {
    	this.points = points;
    }

    public Workspace getWorkspace() {
        return workspace;
    }

    public void setWorkspace(Workspace workspace) {
        this.workspace = workspace;
    }
    
    public String getNumber() {
    	return number;
    }
    
    public void setNumber(String number) {
    	this.number = number;
    }

    public Set<Task> getTasks() {
        return tasks;
    }

    public void setTasks(Set<Task> tasks) {
        this.tasks = tasks;
    }

	@Override
	public int compareTo(UserStory o) {
		  return (this.id).compareTo(o.id);
	}

}
