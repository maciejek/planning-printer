package pl.wroc.pwr.agile.service;

import java.util.List;

import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import pl.wroc.pwr.agile.entity.User;
import pl.wroc.pwr.agile.entity.Workspace;
import pl.wroc.pwr.agile.repository.UserRepository;

@Service
@Transactional
@PersistenceContext(type = PersistenceContextType.EXTENDED)
public class UserService {
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private WorkspaceService workspaceService;
    
    private BCryptPasswordEncoder encoder;
    
    public BCryptPasswordEncoder getEncoder() {
        if (encoder == null) {
            encoder = new BCryptPasswordEncoder();
        }
        return encoder;
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public User findOne(int id) {
        return userRepository.findOne(id);
    }

    public User findOne(String email) {
        return userRepository.findByEmail(email);
    }
    
    public User getLoggedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return findOne(authentication.getName());
    }

    public User registerUser(User user) {
        Workspace workspace = new Workspace();
        user.setWorkspace(workspace);
        user.setPassword(encryptPassword(user.getPassword()));
        workspace.setScrumMaster(user);
        workspaceService.save(workspace);
        return userRepository.save(user);
    }
    
    public User updatePassword(String name, String password) {
        User user = findOne(name);
        user.setPassword(encryptPassword(password));
        return userRepository.save(user);
    }
    
    public String encryptPassword(String plainPassword) {
        return getEncoder().encode(plainPassword);
    }
    
    public Boolean isLoggedUserPasswordCorrect(String password) {
        User loggedUser = getLoggedUser();
        return getEncoder().matches(password, loggedUser.getPassword());
    }
    
    public Boolean canChangePassword(String oldPassword, String newPassword, String confirmPassword) {
        return isLoggedUserPasswordCorrect(oldPassword) && newPassword.equals(confirmPassword);
    }
}
