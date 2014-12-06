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
    
    private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
    
    public BCryptPasswordEncoder getEncoder() {
        return encoder;
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public User findOne(int id) {
        return userRepository.findOne(id);
    }

    public User save(User user) {
        user.setPassword(encoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    public User findOne(String email) {
        return userRepository.findByEmail(email);
    }
    
    public User updatePassword(String name, String password) {
        User user = findOne(name);
        user.setPassword(encoder.encode(password));
        userRepository.save(user);
        return user;
    }
    
    
    
}
