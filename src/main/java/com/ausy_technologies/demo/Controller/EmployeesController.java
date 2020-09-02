package com.ausy_technologies.demo.Controller;

import com.ausy_technologies.demo.Error.ErrorResponse;
import com.ausy_technologies.demo.Model.DAO.Employee;
import com.ausy_technologies.demo.Mapper.EmployeeMapper;
import com.ausy_technologies.demo.Model.DTO.EmployeeDTO;
import com.ausy_technologies.demo.Service.EmployeesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/employees")
public class EmployeesController {

    @Autowired
    private EmployeesService service;


    @PostMapping("/addEmployee")
    public ResponseEntity<Object> saveEmployee(@RequestBody Employee employee) {
        Employee addedEmployee;
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Responded", "addEmployee");
        try{
            addedEmployee = this.service.saveEmployee(employee);
        }catch (ErrorResponse err){
            ErrorResponse.LogError(err);
            return ResponseEntity.status(err.getErrorId()).headers(httpHeaders).body(err.getErrorMessage());
        }
        return ResponseEntity.status(HttpStatus.OK).headers(httpHeaders).body(addedEmployee);
    }

    @PostMapping("/addEmployee/{departmentId}/{jobCategoryId}")
    public ResponseEntity<Object> saveEmployee2(@RequestBody Employee employee, @PathVariable int departmentId, @PathVariable int jobCategoryId){
        Employee addedEmployee;
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Responded", "addEmployee");
        try{
            addedEmployee = this.service.saveEmployee2(employee,departmentId,jobCategoryId);
        }catch (ErrorResponse err){
            ErrorResponse.LogError(err);
            return ResponseEntity.status(err.getErrorId()).headers(httpHeaders).body(err.getErrorMessage());
        }
        return ResponseEntity.status(HttpStatus.ACCEPTED).headers(httpHeaders).body(addedEmployee);
    }

    @GetMapping("/getEmployee/{id}")
    public ResponseEntity<Object> getEmployee(@PathVariable int id){
        Employee employee;
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Responded", "getEmployeeById");
        try{
            employee = this.service.findEmployee(id);
        }catch (ErrorResponse err){
            ErrorResponse.LogError(err);
            return ResponseEntity.status(err.getErrorId()).headers(httpHeaders).body(err.getErrorMessage());
        }
        return ResponseEntity.status(HttpStatus.FOUND).headers(httpHeaders).body(employee);
    }

    @GetMapping("/getAllEmployees")
    public ResponseEntity<Object> findAllEmployees(){
        List<Employee> employeeList;

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Responded", "findAllEmployees");

        employeeList = this.service.findAllEmployees();
        return ResponseEntity.status(HttpStatus.OK).headers(httpHeaders).body(employeeList);
    }

    @GetMapping("/getEmployeesByJob/{jobid}")
    public ResponseEntity<Object> findEmployeeByJob(@PathVariable int jobid){
        List<Employee> employeeList = new ArrayList<>();
        List<EmployeeDTO> employeeDTOList = new ArrayList<>();
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Responded", "findEmployeesByJob");
        try{
            employeeList = this.service.findEmployeeByJob(jobid);
        }catch (ErrorResponse err){
            ErrorResponse.LogError(err);
            return ResponseEntity.status(err.getErrorId()).headers(httpHeaders).body(err.getErrorMessage());
        }
        employeeList.stream().map(e -> EmployeeMapper.convertToEmployeeDto(e)).forEach(employeeDTOList::add);
        return ResponseEntity.status(HttpStatus.OK).headers(httpHeaders).body(employeeDTOList);
    }

    @GetMapping("/getEmployeesByDep/{departmentid}")
    public ResponseEntity<Object> findEmployeeByDep(@PathVariable int departmentid){
        List<Employee> employeeList = new ArrayList<>();
        List<EmployeeDTO> employeeDTOList = new ArrayList<>();
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Responded", "findEmployeesByDep");
        try{
            employeeList = this.service.findEmployeeByDepartment(departmentid);
        }catch (ErrorResponse err){
            ErrorResponse.LogError(err);
            return ResponseEntity.status(err.getErrorId()).headers(httpHeaders).body(err.getErrorMessage());
        }
        employeeList.stream().map(e -> EmployeeMapper.convertToEmployeeDto(e)).forEach(employeeDTOList::add);
        return ResponseEntity.status(HttpStatus.OK).headers(httpHeaders).body(employeeDTOList);
    }

    @GetMapping("/getEmployeesDTOByDepAndJob/{departmentId}/{jobId}")
    public ResponseEntity<Object> findEmployeeByDep(@PathVariable int departmentId, @PathVariable int jobId){
        List<Employee> employeeList = new ArrayList<>();
        List<EmployeeDTO> employeeDTOList = new ArrayList<>();
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Responded", "findEmployeesByDep");
        try{
            employeeList = this.service.findEmployeeByDepAndJob(departmentId,jobId);
        }catch (ErrorResponse err){
            ErrorResponse.LogError(err);
            return ResponseEntity.status(err.getErrorId()).headers(httpHeaders).body(err.getErrorMessage());
        }
        employeeList.stream().map(e -> EmployeeMapper.convertToEmployeeDto(e)).forEach(employeeDTOList::add);
        return ResponseEntity.status(HttpStatus.OK).headers(httpHeaders).body(employeeDTOList);
    }

    @DeleteMapping("/deleteEmployee/{id}")
    public ResponseEntity<Object> deleteJobCategory(@PathVariable int id){
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Responded", "deleteEmployee");
        try{
            this.service.deleteEmployee(id);
        }catch (ErrorResponse err){
            ErrorResponse.LogError(err);
            return ResponseEntity.status(err.getErrorId()).headers(httpHeaders).body(err.getErrorMessage());
        }
        return ResponseEntity.status(HttpStatus.OK).headers(httpHeaders).body("Employee deleted!");
    }

    @GetMapping("/getEmployeeDTO/{id}")
    public ResponseEntity<Object> getEmployeeDTO(@PathVariable int id){
        Employee employee;
        EmployeeDTO employeeDTO;
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Responded", "getEmployeeById");
        try{
            employee = this.service.findEmployee(id);
            employeeDTO = EmployeeMapper.convertToEmployeeDto(employee);
        }catch (ErrorResponse err){
            ErrorResponse.LogError(err);
            return ResponseEntity.status(err.getErrorId()).headers(httpHeaders).body(err.getErrorMessage());
        }
        return ResponseEntity.status(HttpStatus.FOUND).headers(httpHeaders).body(employeeDTO);
    }

    @GetMapping("/getAllEmployeesDTO")
    public ResponseEntity<Object> findAllEmployeesDTO(){
        List<Employee> employeeList = null;
        List<EmployeeDTO> employeeDTOList = new ArrayList<>();

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Responded", "findAllEmployeesDTO");

        try{
            employeeList = this.service.findAllEmployees();
        }catch(ErrorResponse e){
            ErrorResponse.LogError(e);
            return ResponseEntity.status(HttpStatus.OK).headers(httpHeaders).body(employeeDTOList);
        }
        employeeList.stream().map(e -> EmployeeMapper.convertToEmployeeDto(e)).forEach(employeeDTOList::add);
        return ResponseEntity.status(HttpStatus.OK).headers(httpHeaders).body(employeeDTOList);
    }

    @PutMapping("/updateEmployee/{id}")
    public ResponseEntity<Object> updateEmployee(@RequestBody Employee employee,@PathVariable int id){
        Employee updatedEmployee = null;

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Responded", "UpdateEmployee");

        try{
            updatedEmployee = this.service.updateEmployee(employee,id);
        }catch(ErrorResponse err){
            ErrorResponse.LogError(err);
            return ResponseEntity.status(err.getErrorId()).headers(httpHeaders).body(err.getErrorMessage());
        }
        return ResponseEntity.status(HttpStatus.RESET_CONTENT).headers(httpHeaders).body(updatedEmployee);
    }
}
