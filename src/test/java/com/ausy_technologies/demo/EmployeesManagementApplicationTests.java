package com.ausy_technologies.demo;

import org.junit.Test;
import org.junit.runner.RunWith;
import com.ausy_technologies.demo.Model.DAO.Department;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.restdocs.RestDocumentationContext;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.operation.preprocess.OperationResponsePreprocessor;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessRequest;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureRestDocs(outputDir = "target/generated-snippets")
public class EmployeesManagementApplicationTests {

    @Autowired
    private WebApplicationContext context;

    private RestDocumentationContextProvider restDocumentationProvider;

    private MockMvc mockMvc;

    List<Department> departments = null;

    @Before
    public void setUp(){//WebApplicationContext webApplicationContext,
                       //RestDocumentationContextProvider restDocumentation){

/*        this.mockMvc = MockMvcBuilders
                .webAppContextSetup(webApplicationContext)
                .apply(documentationConfiguration(restDocumentation))
                .build();*/

        mockMvc = MockMvcBuilders.webAppContextSetup(context)
                //.apply(documentationConfiguration(restDocumentationProvider))
                .build();

        departments = Stream.of(new Department(7,"IT")
                , new Department(3, "PR"))
                .collect(Collectors.toList());
    }

    @Test
    public void addDepartmentTest() throws Exception {
/*        Department department = new Department();
        department.setName("IT");
        department.setId(7);*/
        String departmentsJson = new ObjectMapper().writeValueAsString(departments);
        mockMvc.perform(post("/departments/addDepartment")
                .content(departmentsJson)
                .contentType("application/json")).andDo(print())
                .andExpect(status().isCreated())
                .andExpect(MockMvcResultMatchers.content().json(departmentsJson))
               // .andDo(document("{methodName}",
               //         preprocessRequest(prettyPrint()),
                //        (OperationResponsePreprocessor) preprocessRequest(prettyPrint())))
        ;
    }


}
