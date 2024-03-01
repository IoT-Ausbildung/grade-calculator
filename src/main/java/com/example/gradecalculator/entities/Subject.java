package com.example.gradecalculator.entities;

import jakarta.persistence.*;

@Entity
@Table( name = "subject")
public class Subject {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column( name = "id")
    private Long id;

    @Column( name = "name")
    private String name;

    public Subject(){
    }
    public Subject(Long id, String name){
        this.id = id;
        this.name = name;
    }

    public Long getId(){return id;}

    public void setId(Long id){this.id = id;}

    public String getName(){return name;}

    public void setName(String name){this.name = name;}
}
