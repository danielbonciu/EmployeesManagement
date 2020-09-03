package com.ausy_technologies.demo.Model.DAO;


import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Entity
@Table(name = "department")
public class Department {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;

    public Department() {

    }

    public Department(int id, String name) {
        this.id = id;
        this.name = name;
    }

    @OneToMany(mappedBy = "department", fetch = FetchType.LAZY, cascade = CascadeType.DETACH)
    private List<Employee> employeeList;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    @Override
    public String toString() {
        return "department{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
