package com.ausy_technologies.demo;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import com.ausy_technologies.demo.Model.DAO.Department;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.restdocs.JUnitRestDocumentation;
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

    @Rule
    public JUnitRestDocumentation restDocumentation = new JUnitRestDocumentation("target/generated-snippets");

    @Autowired
    private WebApplicationContext context;

    private MockMvc mockMvc;

    List<Department> departments = null;

    @Before
    public void setUp(){
        mockMvc = MockMvcBuilders.webAppContextSetup(context)
                .apply(documentationConfiguration(this.restDocumentation))
                .build();
    }

    @Test
    public void addDepartmentTest() throws Exception {
/*        departments = Stream.of(new Department(7,"IT")
                        , new Department(3, "PR"))
                        .collect(Collectors.toList());*/

        Department department = new Department();
        department.setName("HR");
        department.setId(8);

        String departmentsJson = new ObjectMapper().writeValueAsString(department);
        mockMvc.perform(post("/departments/addDepartment")
                .content(departmentsJson)
                .contentType("application/json")).andDo(print())
                .andExpect(status().isCreated())
                .andExpect(MockMvcResultMatchers.content().json(departmentsJson))
                .andDo(document("{methodName}",
                        preprocessRequest(prettyPrint())));
    }
}
