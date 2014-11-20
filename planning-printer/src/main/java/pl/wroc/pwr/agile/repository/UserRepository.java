package pl.wroc.pwr.agile.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import pl.wroc.pwr.agile.entity.User;

public interface UserRepository extends JpaRepository<User, Integer>{
    
    User findByName(String name);

}
