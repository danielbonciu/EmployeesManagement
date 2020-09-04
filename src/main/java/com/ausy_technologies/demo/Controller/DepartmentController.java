package com.ausy_technologies.demo.Controller;

import com.ausy_technologies.demo.Error.ApiError;
import com.ausy_technologies.demo.Error.ErrorResponse;
import com.ausy_technologies.demo.Model.DAO.Department;
import com.ausy_technologies.demo.Service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/departments")
public class DepartmentController {

    @Autowired
    private DepartmentService service;

    /**
     * POST /departments/addDepartment
     *
     * @return the ResponseEntity with status 200(OK) and with body the addedEmployee
     */

    @PostMapping("/addDepartment")
    public ResponseEntity<Object> saveDepartment(@RequestBody Department department){
        Department departmentAdded = null;

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Responded", "addNewDepartment");
        try{
            departmentAdded = this.service.saveDepartment(department);
        }catch(ErrorResponse err) {
            ErrorResponse.LogError(err);
            return ResponseEntity.status(err.getErrorId()).headers(httpHeaders).body(err.getErrorMessage());
        }
        return ResponseEntity.status(HttpStatus.CREATED).headers(httpHeaders).body(departmentAdded);
    }

    /**
     * GET /departments/getAllDepartments get all the departments
     *
     * @return the ResponseEntity with status 200(OK) and with body the departmentList
     */

    @GetMapping("/getAllDepartments")
    public ResponseEntity<Object> findAllDepartments(){
        List<Department> departmentList;

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Responded", "findAllDepartments");
        departmentList = this.service.findAllDepartments();
        return ResponseEntity.status(HttpStatus.OK).headers(httpHeaders).body(departmentList);
    }

    /**
     * DELETE /departments/deleteDepartment/:id delete the "id" department
     *
     * @param id the id of the department to delete
     *
     * @return the ResponseEntity with status 200(OK) and with body "Department deleted!"
     */

    @DeleteMapping("/deleteDepartment/{id}")
    public ResponseEntity<Object> deleteDepartment(@PathVariable int id){
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Responded", "deleteDepartment");
        try{
            this.service.deleteDepartment(id);
        }catch (ErrorResponse err){
            ErrorResponse.LogError(err);
            return ResponseEntity.status(err.getErrorId()).headers(httpHeaders).body(err.getErrorMessage());
        }
        return ResponseEntity.status(HttpStatus.OK).headers(httpHeaders).body("Department deleted!");
    }
}
