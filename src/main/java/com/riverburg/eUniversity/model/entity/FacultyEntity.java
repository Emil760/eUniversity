package com.riverburg.eUniversity.model.entity;
import lombok.*;

import javax.persistence.*;
import java.time.Year;

@Entity
@Table(name = "faculties")
@Setter
@Getter
@NoArgsConstructor
public class FacultyEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name", unique = true)
    private String name;

    @Column(name = "short_name", unique = true)
    private String shortName;

    @Column(name = "semester_count")
    private Short semesterCount;

    @NonNull
    @Column(name = "year")
    private Short year = (short) Year.now().getValue();

    @NonNull
    @Column(name = "is_active")
    private Boolean isActive = true;

}
