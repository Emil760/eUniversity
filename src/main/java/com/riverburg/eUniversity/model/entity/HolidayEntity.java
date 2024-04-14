package com.riverburg.eUniversity.model.entity;
import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "holidays")
@Setter
@Getter
@NoArgsConstructor
@RequiredArgsConstructor
public class HolidayEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name", unique = true)
    private String name;

    @NonNull
    @Column(name = "[from]")
    private Date from;

    @NonNull
    @Column(name = "[to]")
    private Date to;
}
