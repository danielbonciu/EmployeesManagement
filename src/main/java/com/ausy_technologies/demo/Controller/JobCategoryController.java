package com.ausy_technologies.demo.Controller;

import com.ausy_technologies.demo.Error.ErrorResponse;
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

    /**
     * POST /jobCategories/addJobCategory
     *
     * @return the ResponseEntity with status 200(OK) and with body the addedJobCategory
     */

    @PostMapping("/addJobCategory")
    public ResponseEntity<Object> saveJobCategory(@RequestBody JobCategory jobCategory){
        JobCategory addedJobCategory;

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Responded", "addJobCategory");
        try{
            addedJobCategory = this.service.saveJobCategory(jobCategory);
        }catch(ErrorResponse err) {
            ErrorResponse.LogError(err);
            return ResponseEntity.status(err.getErrorId()).headers(httpHeaders).body(err.getErrorMessage());
        }
        return ResponseEntity.status(HttpStatus.CREATED).headers(httpHeaders).body(addedJobCategory);
    }

    /**
     * GET /jobCategories/getAllJobCategories get all the JobCategories
     *
     * @return the ResponseEntity with status 200(OK) and with body the jobCategoriesList
     */

    @GetMapping("/getAllJobCategories")
    public ResponseEntity<Object> findAllCategories(){
        List<JobCategory> jobCategoryList;

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Responded", "findAllJobCategories");
        jobCategoryList = this.service.findAllJobCategories();
        return ResponseEntity.status(HttpStatus.OK).headers(httpHeaders).body(jobCategoryList);
    }

    /**
     * DELETE /jobCategories/deleteJobCategory/:id delete the "id" JobCategory
     *
     * @param id the id of the JobCategory to delete
     *
     * @return the ResponseEntity with status 200(OK) and with body "JobCategory deleted!"
     */

    @DeleteMapping("/deleteJobCategory/{id}")
    public ResponseEntity<Object> deleteJobCategory(@PathVariable int id){
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
