package pl.wroc.pwr.agile.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;

import pl.wroc.pwr.agile.annotation.UniqueEmail;

@Entity
@Table(name="app_user")
public class User {

    @Id
    @GeneratedValue
    private Integer id;
    
    @Email(message = "Invalid email!")
    @Size(min = 1, message = "You must provide an email address!")
    @Column(unique = true)
    @UniqueEmail(message = "Such email already exists!")
    private String email;
    
    @Size(min = 4, message = "Password must be at least 4 characters!")
    private String password;
    
    @Column
    private String jiraLogin;
    @Column
    private String jiraPassword;
    @Column
    private String jiraUrl;
    @Column
    private String jiraProject;
    
    @ManyToOne
    @JoinColumn(name="workspace_id")
    private Workspace workspace;
    
    @Column
    private UserType type;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Workspace getWorkspace() {
        return workspace;
    }

    public void setWorkspace(Workspace workspace) {
        this.workspace = workspace;
    }
    
    public UserType getType() {
        return type;
    }

    public void setType(UserType type) {
        this.type = type;
    }
    
    public String getJiraLogin() {
        return jiraLogin;
    }

    public void setJiraLogin(String jiraLogin) {
        this.jiraLogin = jiraLogin;
    }

    public String getJiraPassword() {
        return jiraPassword;
    }

    public void setJiraPassword(String jiraPassword) {
        this.jiraPassword = jiraPassword;
    }

    public String getJiraUrl() {
        return jiraUrl;
    }

    public void setJiraUrl(String jiraUrl) {
        this.jiraUrl = jiraUrl;
    }

    public String toString() {
        return email + ", " + password + ", " + workspace;
    }

    public void setJiraProject(String projectName) {
        this.jiraProject = projectName;
    }

    public String getJiraProject() {
        return jiraProject;
    }

}
