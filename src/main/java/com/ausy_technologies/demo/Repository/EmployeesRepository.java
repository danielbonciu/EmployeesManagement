package com.ausy_technologies.demo.Repository;

import com.ausy_technologies.demo.Model.DAO.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface EmployeesRepository extends JpaRepository<Employee, Integer> {

    @Override
    List<Employee> findAll();

    @Override
    Optional<Employee> findById(Integer integer);
}
