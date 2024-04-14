package com.riverburg.eUniversity.model.entity;
import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "students_works")
@Setter
@Getter
@NoArgsConstructor
@RequiredArgsConstructor
public class StudentWorkEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NonNull
    @ManyToOne
    @JoinColumn(name = "student_id")
    private StudentEntity studentEntity;

    @NonNull
    @ManyToOne
    @JoinColumn(name = "theme_id")
    private ThemeEntity themeEntity;

    @ManyToOne
    @JoinColumn(name = "file_id")
    @NonNull
    private FileEntity fileEntity;

    @Column(name = "date")
    private Date date = new Date();

    @Column(name = "grade")
    private Short grade;

    @Column(name = "feedback")
    private String feedback;
}
