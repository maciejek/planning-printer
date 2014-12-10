package pl.wroc.pwr.agile.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import pl.wroc.pwr.agile.entity.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, Integer> {

}
