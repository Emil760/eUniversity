package com.riverburg.eUniversity.model.entity;
import lombok.*;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;

@Entity
@Table(name = "schedule")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class ScheduleEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NonNull
    @ManyToOne
    @JoinColumn(name = "group_id")
    private GroupEntity groupEntity;

    @NonNull
    @ManyToOne
    @JoinColumn(name = "faculty_discipline_id")
    private FacultyDisciplineEntity facultyDisciplineEntity;

    @NonNull
    @ManyToOne
    @JoinColumn(name = "edu_process_id")
    private EduProcessEntity eduProcessEntity;

    @NonNull
    @ManyToOne
    @JoinColumn(name = "teacher_id")
    private TeacherEntity teacherEntity;

    @Column(name = "begin_date")
    private Date beginDate;

    @Column(name = "end_date")
    private Date endDate;

    @NonNull
    @Column(name = "type")
    private Short type;

    @NonNull
    @Column(name = "[from]")
    @DateTimeFormat(pattern = "HH:mm")
    private LocalTime from;

    @NonNull
    @Column(name = "[to]")
    @DateTimeFormat(pattern = "HH:mm")
    private LocalTime to;

    @ManyToOne
    @JoinColumn(name = "room_id")
    private RoomEntity roomEntity;

    @NonNull
    @Builder.Default
    private Boolean isActive = true;

    @Transient
    private LocalDateTime nextDate;
}
