package com.riverburg.eUniversity.model.entity;

import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "journal")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class JournalEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NonNull
    @ManyToOne
    @JoinColumn(name = "schedule_id")
    private ScheduleEntity scheduleEntity;

    @NonNull
    @ManyToOne
    @JoinColumn(name = "student_id")
    private StudentEntity studentEntity;

    @Builder.Default
    @NonNull
    @Column(name = "date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date date = new Date();

    @Column(name = "attendance")
    private Boolean attendance;

    @Column(name = "grade")
    private Short grade;

    @Builder.Default
    @NonNull
    @Column(name = "is_holiday")
    private Boolean isHoliday = false;

    @Builder.Default
    @NonNull
    @Column(name = "is_canceled")
    private Boolean isCanceled = false;
}
