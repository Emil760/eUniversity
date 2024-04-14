package com.riverburg.eUniversity.model.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "materials")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class MaterialEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Integer id;

    @Column(name = "description")
    private String description;

    @OneToOne
    @JoinColumn(name = "file_id")
    private FileEntity fileEntity;

    @ManyToOne
    @JoinColumn(name = "discipline_id")
    private DisciplineEntity disciplineEntity;

    @ManyToOne
    @JoinColumn(name = "sector_id")
    private SectorEntity sectorEntity;
}
