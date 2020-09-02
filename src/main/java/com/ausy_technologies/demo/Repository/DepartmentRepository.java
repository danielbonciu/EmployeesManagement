package com.ausy_technologies.demo.Repository;

import com.ausy_technologies.demo.Model.DAO.Department;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface DepartmentRepository extends JpaRepository<Department, Integer> {

    @Override
    List<Department> findAll();

    @Override
    Optional<Department> findById(Integer integer);
}
