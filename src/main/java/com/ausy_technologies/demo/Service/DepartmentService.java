package com.ausy_technologies.demo.Service;

import com.ausy_technologies.demo.Error.ErrorResponse;
import com.ausy_technologies.demo.Model.DAO.Department;
import com.ausy_technologies.demo.Model.DAO.Employee;
import com.ausy_technologies.demo.Repository.DepartmentRepository;
import com.ausy_technologies.demo.Repository.EmployeesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DepartmentService {

    @Autowired
    private DepartmentRepository departmentRepository;

    @Autowired
    private EmployeesRepository employeesRepository;

    public Department saveDepartment(Department department){
        if(department.getName().isEmpty())
            throw new ErrorResponse("Department is empty", 400);
        return this.departmentRepository.save(department);
    }

    public List<Department> findAllDepartments(){
        List<Department> departmentList =  this.departmentRepository.findAll();
        return departmentList;
    }

    public void deleteDepartment(int id){
        Department department;
        try {
            department = this.departmentRepository.findById(id).get();
        }catch (RuntimeException e){
            throw new ErrorResponse(e.getMessage(), 404);
        }

        List<Employee> employeeList = this.employeesRepository.findAll();
        for(Employee e : employeeList){
            if(e.getDepartment().getId() == department.getId())
                e.setDepartment(null);
        }
        this.departmentRepository.delete(department);

    }
}
