package com.riverburg.eUniversity.model.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "paid_faculties")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class PaidFacultyEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "faculty_id")
    private FacultyEntity facultyEntity;

    @ManyToOne
    @JoinColumn(name = "degree_id")
    private DegreeEntity degreeEntity;

    @Column(name = "year")
    private Short year;

    @Column(name = "free_count")
    private Short freeCount;
}
