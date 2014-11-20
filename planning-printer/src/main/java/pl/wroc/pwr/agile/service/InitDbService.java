package pl.wroc.pwr.agile.service;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import pl.wroc.pwr.agile.entity.User;
import pl.wroc.pwr.agile.repository.UserRepository;

@Service
public class InitDbService {

    @Autowired
    private UserRepository userRepository;
    
    @PostConstruct
    public void init() {
        User user1 = new User();
        user1.setName("Bugi");
        user1.setEmail("bugi@pwr.edu.pl");
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        user1.setPassword(encoder.encode("bugi"));
        userRepository.save(user1);
    }
}
