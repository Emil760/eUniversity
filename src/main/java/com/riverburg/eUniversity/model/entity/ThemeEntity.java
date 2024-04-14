package com.riverburg.eUniversity.model.entity;

import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "themes")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class ThemeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NonNull
    @ManyToOne
    @JoinColumn(name = "faculty_discipline_id")
    private FacultyDisciplineEntity facultyDisciplineEntity;

    @NonNull
    @ManyToOne
    @JoinColumn(name = "edu_process_id")
    private EduProcessEntity eduProcessEntity;

    @NonNull
    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "[order]")
    private Short order;

    @ManyToOne
    @JoinColumn(name = "file_id")
    private FileEntity fileEntity;

    @Column(name = "[from]")
    private Date from;

    @Column(name = "[to]")
    private Date to;
}
