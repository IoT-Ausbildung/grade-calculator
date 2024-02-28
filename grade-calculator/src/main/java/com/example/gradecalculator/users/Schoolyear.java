package com.example.gradecalculator.users;

import jakarta.persistence.*;

@Entity
@Table( name = "schoolyear")
public class Schoolyear {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column( name = "id")
    private Long id;

    @Column( name = "name")
    private String name;

    public Schoolyear(){
    }
    public Schoolyear(Long id, String name){
        this.id = id;
        this.name = name;
    }

    public Long getId(){return id;}

    public void setId(Long id){this.id = id;}

    public String getName(){return name;}

    public void setName(String name){this.name = name;}
}
