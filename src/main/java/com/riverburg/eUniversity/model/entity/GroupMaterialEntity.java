package com.riverburg.eUniversity.model.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "group_materials")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class GroupMaterialEntity {

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

    @Column(name = "description")
    private String description;

    @NonNull
    @ManyToOne
    @JoinColumn(name = "file_id")
    private FileEntity fileEntity;
}
