package com.ausy_technologies.demo.Mapper;

import com.ausy_technologies.demo.Model.DAO.Employee;
import com.ausy_technologies.demo.Model.DTO.EmployeeDTO;
import com.ausy_technologies.demo.Repository.DepartmentRepository;
import com.ausy_technologies.demo.Repository.EmployeesRepository;
import com.ausy_technologies.demo.Repository.JobCategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class EmployeeMapper {

    @Autowired
    private EmployeesRepository employeesRepository;
    @Autowired
    private DepartmentRepository departmentRepository;
    @Autowired
    private JobCategoryRepository jobCategoryRepository;

    public static EmployeeDTO convertToEmployeeDto(Employee employee){
        EmployeeDTO employeeDTO = new EmployeeDTO();

        employeeDTO.setIdEmployee(employee.getId());
        employeeDTO.setFirstName(employee.getFirstName());
        employeeDTO.setLastName(employee.getLastName());
        employeeDTO.setJobCategoryName(employee.getJobCategory().getName());
        employeeDTO.setDepartmentName(employee.getDepartment().getName());
        employeeDTO.setManager(employee.isManager());
        employeeDTO.setStartDate(employee.getStartDate());
        employeeDTO.setEndDate(employee.getEndDate());
        employeeDTO.setActive(employee.isActive());

        return employeeDTO;
    }
}
