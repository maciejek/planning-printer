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

    private void addUser(User user) {
        if (CollectionUtils.isEmpty(users)) {
            users = new LinkedList<User>();
        }
        users.add(user);
    }

}
