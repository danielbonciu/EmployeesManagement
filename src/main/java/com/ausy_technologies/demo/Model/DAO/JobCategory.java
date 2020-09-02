package com.ausy_technologies.demo.Model.DAO;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "jobCategory")
public class JobCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;

    @OneToMany(mappedBy = "jobCategory", fetch = FetchType.LAZY, cascade = CascadeType.DETACH)
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
        return "jobCategory{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
