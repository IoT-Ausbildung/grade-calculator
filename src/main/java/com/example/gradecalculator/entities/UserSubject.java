package com.example.gradecalculator.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.context.annotation.Lazy;

import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "user_subject")
@NoArgsConstructor
@Getter
@Setter
public class UserSubject implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    private String name;

    @ManyToOne
    @JoinColumn(name = "school_year_id", nullable = false)
    private SchoolYear schoolYear;

    @ManyToOne
    @JoinColumn(name = "subject_id", nullable = false)
    private Subject subject;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @OneToMany(mappedBy = "userSubject", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<UserGrade> userGrades;

    public UserSubject(User selectedUser, Subject selectedSubject, SchoolYear selectedYear) {
        this.user = selectedUser;
        this.subject = selectedSubject;
        this.schoolYear = selectedYear;
    }
}
