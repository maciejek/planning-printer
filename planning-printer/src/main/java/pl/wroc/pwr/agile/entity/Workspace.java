package pl.wroc.pwr.agile.entity;

import java.util.List;
import java.util.Map;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Workspace {

    @Id
    @GeneratedValue
    private Integer id;
    
    @OneToMany(targetEntity=User.class, mappedBy="workspace")
    Map<UserType, User> users;
//    @OneToMany(mappedBy="workspace")
//    Collection<User> users;

    @OneToMany(mappedBy="workspace")
    List<UserStory> userStories;
    
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Map<UserType, User> getUsers() {
        return users;
    }

    public void setUsers(Map<UserType, User> users) {
        this.users = users;
    }
    
    public void setScrumMaster(User sMaster) {
        users.put(UserType.SCRUM_MASTER, sMaster);
    }
    
    public void setDeputy(User deputy) {
        users.put(UserType.DEPUTY, deputy);
    }
    
    public User getScrumMaster() {
        return users.get(UserType.SCRUM_MASTER);
    }
    
    public User getDeputy() {
        return users.get(UserType.DEPUTY);
    }

    public List<UserStory> getUserStories() {
        return userStories;
    }

    public void setUserStories(List<UserStory> userStories) {
        this.userStories = userStories;
    }
    
    

//    public Collection<User> getUsers() {
//        return users;
//    }
//
//    public void setUsers(Collection<User> users) {
//        this.users = users;
//    }

}
