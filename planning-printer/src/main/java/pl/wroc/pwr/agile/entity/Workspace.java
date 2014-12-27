package pl.wroc.pwr.agile.entity;

import java.util.LinkedList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import org.springframework.util.CollectionUtils;

@Entity
public class Workspace {

    @Id
    @GeneratedValue
    private Integer id;
    
    @OneToMany(targetEntity=User.class, mappedBy="workspace", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<User> users;

    @OneToMany(targetEntity=UserStory.class, mappedBy="workspace", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<UserStory> userStories;
    
    @OneToMany(mappedBy="workspace")
    private List<Employee> employees;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public User getDeputy() {
        for (User user : users) {
            if (user.getType() == UserType.DEPUTY) {
                return user;
            }
        }
        return null;
    }
    

    public User getScrumMaster() {
        for (User user : users) {
            if (user.getType() == UserType.SCRUM_MASTER) {
                return user;
            }
        }
        return null;
    }

    public void setDeputy(User deputy) {
        deputy.setType(UserType.DEPUTY);
        addUser(deputy);
    }
    
    public void setScrumMaster(User scrumMaster) {
        scrumMaster.setType(UserType.SCRUM_MASTER);
        addUser(scrumMaster);
    }

    private void addUser(User user) {
        if (CollectionUtils.isEmpty(users)) {
            users = new LinkedList<User>();
        }
        users.add(user);
    }
    
    public List<Employee> getEmployees() {
        return employees;
    }

    public void setEmployees(List<Employee> employees) {
        this.employees = employees;
    }

    public List<UserStory> getUserStories() {
        return userStories;
    }

    public void setUserStories(List<UserStory> userStories) {
        this.userStories = userStories;
    }

}
