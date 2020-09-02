package com.ausy_technologies.demo.Service;

import com.ausy_technologies.demo.Error.ErrorResponse;
import com.ausy_technologies.demo.Model.DAO.Department;
import com.ausy_technologies.demo.Model.DAO.Employee;
import com.ausy_technologies.demo.Model.DAO.JobCategory;
import com.ausy_technologies.demo.Repository.DepartmentRepository;
import com.ausy_technologies.demo.Repository.EmployeesRepository;
import com.ausy_technologies.demo.Repository.JobCategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class JobCategoryService {

    @Autowired
    private JobCategoryRepository jobCategoryRepository;

    @Autowired
    private EmployeesRepository employeesRepository;

    public JobCategory saveJobCategory(JobCategory jobCategory){
        if(jobCategory.getName().isEmpty())
            throw new ErrorResponse("JobCategory is empty", 204);
        return this.jobCategoryRepository.save(jobCategory);
    }

    public List<JobCategory> findAllJobCategories(){
        List<JobCategory> jobCategoryList =  this.jobCategoryRepository.findAll();
        return jobCategoryList;
    }

    public void deleteJobCategory(int id){
        JobCategory jobCategory;
        try {
            jobCategory = this.jobCategoryRepository.findById(id).get();
        }catch (RuntimeException e){
            throw new ErrorResponse(e.getMessage(), 404);
        }

        List<Employee> employeeList = this.employeesRepository.findAll();
        for(Employee e : employeeList){
            if(e.getJobCategory().getId() == jobCategory.getId())
                e.setJobCategory(null);
        }
        this.jobCategoryRepository.delete(jobCategory);
    }
}
