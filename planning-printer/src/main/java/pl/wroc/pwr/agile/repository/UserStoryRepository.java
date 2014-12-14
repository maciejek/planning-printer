package pl.wroc.pwr.agile.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import pl.wroc.pwr.agile.entity.UserStory;

public interface UserStoryRepository extends JpaRepository<UserStory, Integer> {

}
