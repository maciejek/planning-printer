package pl.wroc.pwr.agile.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;

import pl.wroc.pwr.agile.annotation.UniqueEmail;

@Entity
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
    
    @ManyToOne
    @JoinColumn(name="workspace_id")
    private Workspace workspace;
    
//    @OneToOne
//    private Workspace workspace;

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
    
    public String toString() {
        return email + ", " + password + ", " + workspace;
    }
    
    
}
