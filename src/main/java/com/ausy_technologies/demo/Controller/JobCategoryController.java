package com.ausy_technologies.demo.Controller;

import com.ausy_technologies.demo.Error.ErrorResponse;
import com.ausy_technologies.demo.Model.DAO.Department;
import com.ausy_technologies.demo.Model.DAO.JobCategory;
import com.ausy_technologies.demo.Service.JobCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/jobcategories")
public class JobCategoryController {

    @Autowired
    private JobCategoryService service;

    @PostMapping("/addJobCategory")
    public ResponseEntity<Object> saveJobCategory(@RequestBody JobCategory jobCategory){
        JobCategory jobCategoryAdded;

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Responded", "addJobCategory");
        try{
            jobCategoryAdded = this.service.saveJobCategory(jobCategory);
        }catch(ErrorResponse err) {
            ErrorResponse.LogError(err);
            return ResponseEntity.status(err.getErrorId()).headers(httpHeaders).body(err.getErrorMessage());
        }
        return ResponseEntity.status(HttpStatus.CREATED).headers(httpHeaders).body(jobCategoryAdded);
    }

    @GetMapping("/findAllJobCategories")
    public ResponseEntity<Object> findAllCategories(){
        List<JobCategory> allJobCategories;

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Responded", "findAllJobCategories");
        allJobCategories = this.service.findAllJobCategories();
        return ResponseEntity.status(HttpStatus.OK).headers(httpHeaders).body(allJobCategories);
    }

    @DeleteMapping("/deleteJobCategory")
    public ResponseEntity<Object> deleteJobCategory(@RequestParam int id){
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Responded", "deleteJobCategory");
        try{
            this.service.deleteJobCategory(id);
        }catch (ErrorResponse err){
            ErrorResponse.LogError(err);
            return ResponseEntity.status(err.getErrorId()).headers(httpHeaders).body(err.getErrorMessage());
        }
        return ResponseEntity.status(HttpStatus.OK).headers(httpHeaders).body("JobCategory deleted!");
    }
}
