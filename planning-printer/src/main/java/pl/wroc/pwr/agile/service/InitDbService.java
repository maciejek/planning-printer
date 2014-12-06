package pl.wroc.pwr.agile.service;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import pl.wroc.pwr.agile.entity.User;
import pl.wroc.pwr.agile.entity.Workspace;
import pl.wroc.pwr.agile.repository.UserRepository;
import pl.wroc.pwr.agile.repository.WorkspaceRepository;

@Service
public class InitDbService {

    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private WorkspaceRepository workspaceRepository;
    
    @PostConstruct
    public void init() {
        User user1 = new User();
        user1.setEmail("bugi@pwr.edu.pl");
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        user1.setPassword(encoder.encode("bugi"));

        Workspace workspace = new Workspace();
        user1.setWorkspace(workspace);
        userRepository.save(user1);
    }
}
