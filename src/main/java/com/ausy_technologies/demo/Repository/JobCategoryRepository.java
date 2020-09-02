package com.ausy_technologies.demo.Repository;

import com.ausy_technologies.demo.Model.DAO.JobCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface JobCategoryRepository extends JpaRepository<JobCategory, Integer> {

    @Override
    List<JobCategory> findAll();

    @Override
    Optional<JobCategory> findById(Integer integer);
}
