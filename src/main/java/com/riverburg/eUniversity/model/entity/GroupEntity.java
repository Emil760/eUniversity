package com.riverburg.eUniversity.model.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "groups")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class GroupEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NonNull
    @Column(name = "name")
    private String name;

    @NonNull
    @Column(name = "start_date")
    private LocalDate startDate;

    @ManyToOne()
    @JoinColumn(name = "faculty_id")
    private FacultyEntity facultyEntity;

    @NonNull
    @ManyToOne()
    @JoinColumn(name = "sector_id")
    private SectorEntity sectorEntity;

    @NonNull
    @ManyToOne()
    @JoinColumn(name = "degree_id")
    private DegreeEntity degreeEntity;

    @Builder.Default
    @NonNull
    @Column(name = "semester")
    private Short semester = 1;
}
