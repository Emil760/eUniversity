package com.riverburg.eUniversity.model.entity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;


import javax.persistence.*;
import java.time.Year;

@Entity
@Table(name = "disciplines")
@Setter
@Getter
@NoArgsConstructor
public class DisciplineEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name", unique = true)
    private String name;

    @Column(name = "short_name", unique = true)
    private String shortName;

    @NonNull
    @Column(name = "year")
    private Short year = (short) Year.now().getValue();

    @NonNull
    @Column(name = "is_active")
    private Boolean isActive = true;
}
