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

    @GetMapping("/findAllDepartments")
    public ResponseEntity<Object> findAllDepartments(){
        List<Department> allDepartments;

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Responded", "findAllDepartments");
        allDepartments = this.service.findAllDepartments();
        return ResponseEntity.status(HttpStatus.OK).headers(httpHeaders).body(allDepartments);
    }

    @DeleteMapping("/deleteDepartment")
    public ResponseEntity<Object> deleteDepartment(@RequestParam int id){
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
