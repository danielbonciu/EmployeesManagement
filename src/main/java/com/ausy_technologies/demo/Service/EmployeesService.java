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

import java.util.ArrayList;
import java.util.List;

@Service
public class EmployeesService {

    @Autowired
    private EmployeesRepository employeesRepository;

    @Autowired
    private DepartmentRepository departmentRepository;

    @Autowired
    private JobCategoryRepository jobCategoryRepository;


    public Employee saveEmployee(Employee employee){
        JobCategory jobCategory;
        try{
            jobCategory = this.jobCategoryRepository.findById(employee.getJobCategory().getId()).get();
        }catch(RuntimeException e){
            throw new ErrorResponse("JobCategory does not exist.", 404);
        }
        if(!jobCategory.getName().equals(employee.getJobCategory().getName()))
            throw new ErrorResponse("JobCategory name is incorrect.", 400);

        Department department;
        try{
            department = this.departmentRepository.findById(employee.getDepartment().getId()).get();
        }catch(RuntimeException e){
            throw new ErrorResponse("Department does not exist.", 404);
        }
        if(!department.getName().equals(employee.getDepartment().getName()))
            throw new ErrorResponse("Department name is incorrect.", 400);

        return this.employeesRepository.save(employee);
    }

    public Employee saveEmployee2(Employee employee, int departmentId, int jobCategoryId){
        Department department;
        try{
             department = this.departmentRepository.findById(departmentId).get();
        }catch(RuntimeException e){
            throw new ErrorResponse("Department does not exist.", 404);
        }
        JobCategory jobCategory;
        try{
            jobCategory = this.jobCategoryRepository.findById(jobCategoryId).get();
        }catch(RuntimeException e){
            throw new ErrorResponse("JobCategory does not exist.", 404);
        }

        employee.setDepartment(department);
        employee.setJobCategory(jobCategory);
        return this.employeesRepository.save(employee);
    }

    public Employee findEmployee(int id){
        Employee employee;
        try{
            employee = this.employeesRepository.findById(id).get();
        }catch (RuntimeException e) {
            throw new ErrorResponse("Employee with id " + id + " does not exist", 404);
        }
        return employee;
    }

    public List<Employee> findEmployeeByJob(int id){
        List<Employee> employee = new ArrayList<>();
        try{
            this.jobCategoryRepository.findById(id).get();
        }catch (RuntimeException e) {
            throw new ErrorResponse("JobCategory with id " + id + " does not exist", 404);
        }

        List<Employee> employeeList = this.employeesRepository.findAll();
        for(Employee e : employeeList){
            if(e.getJobCategory().getId() == id)
                employee.add(e);
        }
        return employee;
    }

    public List<Employee> findEmployeeByDepartment(int id){
        List<Employee> employee = new ArrayList<>();
        try{
            this.departmentRepository.findById(id).get();
        }catch (RuntimeException e) {
            throw new ErrorResponse("Department with id " + id + " does not exist", 404);
        }

        List<Employee> employeeList = this.employeesRepository.findAll();
        for(Employee e : employeeList){
            if(e.getDepartment().getId() == id)
                employee.add(e);
        }
        return employee;
    }

    public List<Employee> findEmployeeByDepAndJob(int idDepartment, int idJobCategory){
        List<Employee> employee = new ArrayList<>();
        try{
            this.departmentRepository.findById(idDepartment).get();
        }catch (RuntimeException e) {
            throw new ErrorResponse("Department with id " + idDepartment + " does not exist", 404);
        }
        try{
            this.jobCategoryRepository.findById(idJobCategory).get();
        }catch (RuntimeException e) {
            throw new ErrorResponse("JobCategory with id " + idJobCategory + " does not exist", 404);
        }

        List<Employee> employeeList = this.employeesRepository.findAll();
        for(Employee e : employeeList){
            if(e.getDepartment().getId() == idDepartment && e.getJobCategory().getId() == idJobCategory)
                employee.add(e);
        }
        return employee;
    }

    public List<Employee> findAllEmployees(){
        List<Employee> employeeList =  this.employeesRepository.findAll();
        return employeeList;
    }

    public void deleteEmployee(int id){
        Employee employee;
        try {
            employee = this.employeesRepository.findById(id).get();
        }catch (RuntimeException e){
            throw new ErrorResponse(e.getMessage(), 404);
        }
        this.employeesRepository.delete(employee);
    }

    public Employee updateEmployee(Employee employee, int id){
        Employee updatedEmployee;
        try{
            updatedEmployee = this.employeesRepository.findById(id).get();
        }catch (RuntimeException e){
            throw new ErrorResponse("User with id "+id+" does not exist.", 404);
        }
        JobCategory jobCategory;
        try{
            jobCategory = this.jobCategoryRepository.findById(employee.getJobCategory().getId()).get();
        }catch(RuntimeException e){
            throw new ErrorResponse("JobCategory does not exist.", 404);
        }
        if(!jobCategory.getName().equals(employee.getJobCategory().getName()))
            throw new ErrorResponse("JobCategory name is incorrect.", 400);
        Department department;
        try{
            department = this.departmentRepository.findById(employee.getDepartment().getId()).get();
        }catch(RuntimeException e){
            throw new ErrorResponse("Department does not exist.", 404);
        }
        if(!department.getName().equals(employee.getDepartment().getName()))
            throw new ErrorResponse("Department name is incorrect.", 400);

        updatedEmployee.setJobCategory(employee.getJobCategory());
        updatedEmployee.setDepartment(employee.getDepartment());
        updatedEmployee.setActive(employee.isActive());
        updatedEmployee.setAddress(employee.getAddress());
        updatedEmployee.setBirthday(employee.getBirthday());
        updatedEmployee.setCP(employee.getCP());
        updatedEmployee.setEmail(employee.getEmail());
        updatedEmployee.setEndDate(employee.getEndDate());
        updatedEmployee.setFirstName(employee.getFirstName());
        updatedEmployee.setHasDrivingLicense(employee.isHasDrivingLicense());
        updatedEmployee.setLastName(employee.getLastName());
        updatedEmployee.setManager(employee.isManager());
        updatedEmployee.setNoChildren(employee.getNoChildren());
        updatedEmployee.setSalary(employee.getSalary());
        updatedEmployee.setSocialSecurityNumber(employee.getSocialSecurityNumber());
        updatedEmployee.setStartDate(employee.getStartDate());
        updatedEmployee.setStudies(employee.getStudies());
        updatedEmployee.setTelephone(employee.getTelephone());

        return this.employeesRepository.save(updatedEmployee);
    }

}
