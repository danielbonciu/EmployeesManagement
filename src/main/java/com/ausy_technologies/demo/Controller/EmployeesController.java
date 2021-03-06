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

    /**
     * POST /employees/addEmployee
     *
     * @return the ResponseEntity with status 200(OK) and with body the addedEmployee
     */

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

    /**
     * POST /employees/getEmployee/:departmentId/:jobCategoryId
     * add employee with "departmentId" department and "jobCategoryId" job if they exists in the DB
     *
     * @param departmentId the id of the department to add
     * @param jobCategoryId the id of the jobCategory to add
     * @return the ResponseEntity with status 202(Accepted) and with body the addedEmployee
     */

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

    /**
     * GET /employees/getEmployee/:id get the "id" employee
     *
     * @param id the id of the employee to retrieve
     * @return the ResponseEntity with status 302(Found) and with body the employee
     */

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

    /**
     * GET /employees/getAllEmployee get all the employees
     *
     * @return the ResponseEntity with status 200(OK) and with body the employeeList
     */

    @GetMapping("/getAllEmployees")
    public ResponseEntity<Object> findAllEmployees(){
        List<Employee> employeeList;

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Responded", "findAllEmployees");

        employeeList = this.service.findAllEmployees();
        return ResponseEntity.status(HttpStatus.OK).headers(httpHeaders).body(employeeList);
    }

    /**
     * GET /employees/getEmployee/:jobId get all the employees with the "id" job
     *
     * @param jobid the id of the job
     *
     * @return the ResponseEntity with status 200(OK) and with body the employeeDTOList
     */

    @GetMapping("/getEmployeesByJob/{jobid}")
    public ResponseEntity<Object> findEmployeeByJob(@PathVariable int jobid){
        List<Employee> employeeList;
        List<EmployeeDTO> employeeDTOList = new ArrayList<>();
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Responded", "findEmployeesByJob");
        try{
            employeeList = this.service.findEmployeeByJob(jobid);
        }catch (ErrorResponse err){
            ErrorResponse.LogError(err);
            return ResponseEntity.status(err.getErrorId()).headers(httpHeaders).body(err.getErrorMessage());
        }
        employeeList.stream().map(EmployeeMapper::convertToEmployeeDto).forEach(employeeDTOList::add);
        return ResponseEntity.status(HttpStatus.OK).headers(httpHeaders).body(employeeDTOList);
    }

    /**
     * GET /employees/getEmployee/:departmentid get all the employees with the "id" department
     *
     * @param departmentid the id of the department
     *
     * @return the ResponseEntity with status 200(OK) and with body the employeeDTOList
     */

    @GetMapping("/getEmployeesByDep/{departmentid}")
    public ResponseEntity<Object> findEmployeeByDep(@PathVariable int departmentid){
        List<Employee> employeeList;
        List<EmployeeDTO> employeeDTOList = new ArrayList<>();
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Responded", "findEmployeesByDep");
        try{
            employeeList = this.service.findEmployeeByDepartment(departmentid);
        }catch (ErrorResponse err){
            ErrorResponse.LogError(err);
            return ResponseEntity.status(err.getErrorId()).headers(httpHeaders).body(err.getErrorMessage());
        }
        employeeList.stream().map(EmployeeMapper::convertToEmployeeDto).forEach(employeeDTOList::add);
        return ResponseEntity.status(HttpStatus.OK).headers(httpHeaders).body(employeeDTOList);
    }

    /**
     * GET /employees/getEmployee/:departmentid/:jobId
     * get all the employees with the "departmentid" department and "jobId" job
     *
     * @param departmentId the id of the department
     * @param jobId the id of the job
     *
     * @return the ResponseEntity with status 200(OK) and with body the employeeDTOList
     */

    @GetMapping("/getEmployeesDTOByDepAndJob/{departmentId}/{jobId}")
    public ResponseEntity<Object> findEmployeeByDep(@PathVariable int departmentId, @PathVariable int jobId){
        List<Employee> employeeList;
        List<EmployeeDTO> employeeDTOList = new ArrayList<>();
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Responded", "findEmployeesByDep");
        try{
            employeeList = this.service.findEmployeeByDepAndJob(departmentId,jobId);
        }catch (ErrorResponse err){
            ErrorResponse.LogError(err);
            return ResponseEntity.status(err.getErrorId()).headers(httpHeaders).body(err.getErrorMessage());
        }
        employeeList.stream().map(EmployeeMapper::convertToEmployeeDto).forEach(employeeDTOList::add);
        return ResponseEntity.status(HttpStatus.OK).headers(httpHeaders).body(employeeDTOList);
    }

    /**
     * DELETE /employees/deleteEmployee/:id delete the "id" employee
     *
     * @param id the id of the employee to delete
     *
     * @return the ResponseEntity with status 200(OK) and with body "Employee deleted!"
     */

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


    /**
     * GET /employees/getEmployeeDTO/:id get the "id" employee DTO
     *
     * @param id the id of the employee to retrieve
     *
     * @return the ResponseEntity with status 302(Found) and with body the employeeDTO
     */

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

    /**
     * GET /employees/getAllEmployeeDTO get all the employees DTO
     *
     * @return the ResponseEntity with status 200(OK) and with body the employeeDTOList
     */


    @GetMapping("/getAllEmployeesDTO")
    public ResponseEntity<Object> findAllEmployeesDTO(){
        List<Employee> employeeList;
        List<EmployeeDTO> employeeDTOList = new ArrayList<>();

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Responded", "findAllEmployeesDTO");

        try{
            employeeList = this.service.findAllEmployees();
        }catch(ErrorResponse e){
            ErrorResponse.LogError(e);
            return ResponseEntity.status(HttpStatus.OK).headers(httpHeaders).body(employeeDTOList);
        }
        employeeList.stream().map(EmployeeMapper::convertToEmployeeDto).forEach(employeeDTOList::add);
        return ResponseEntity.status(HttpStatus.OK).headers(httpHeaders).body(employeeDTOList);
    }

    /**
     * PUT /employees/updateEmployee/:id update employee with "id"
     *
     * @param id the id of employee to be updated
     *
     * @return the ResponseEntity with status 205(RESET_CONTENT) and with body the updatedEmployee
     */


    @PutMapping("/updateEmployee/{id}")
    public ResponseEntity<Object> updateEmployee(@RequestBody Employee employee,@PathVariable int id){
        Employee updatedEmployee;

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
