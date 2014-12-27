package pl.wroc.pwr.agile.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import pl.wroc.pwr.agile.entity.Task;

public interface TaskRepository extends JpaRepository<Task, Integer> {

}
