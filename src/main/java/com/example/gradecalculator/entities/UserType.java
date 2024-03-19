package com.example.gradecalculator.entities;

import jakarta.persistence.*;
import org.apache.catalina.User;

import java.util.List;

@Entity
@Table( name = "user_type")
public class UserType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column( unique = true)
    private String name;

    private String description;
    public UserType(){
    }
    public UserType(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getId(){return id;}

    public void setId(Long id){this.id = id;}

    public String getName(){return name;}

    public void setName(String name){this.name = name;}
}