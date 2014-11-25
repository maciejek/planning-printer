package pl.wroc.pwr.agile.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import pl.wroc.pwr.agile.entity.User;
import pl.wroc.pwr.agile.repository.UserRepository;

@Service
@Transactional
public class UserService {
    
    @Autowired
    private UserRepository userRepository;
    
    public List<User> findAll() {
        return userRepository.findAll();
    }

    public User findOne(int id) {
        return userRepository.findOne(id);
    }

    public void save(User user) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        user.setPassword(encoder.encode(user.getPassword()));
        userRepository.save(user);
    }

    public User findOne(String name) {
        User user = userRepository.findByEmail(name);
        return findOne(user.getId());
    }
    
    public User updatePassword(String name, String password) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        User user = findOne(name);
        user.setPassword(encoder.encode(password));
        userRepository.save(user);
        return user;
    }
    
}
