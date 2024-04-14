package com.riverburg.eUniversity.model.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "rooms")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class RoomEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NonNull
    @Column(name = "number")
    private String number;

    @Column(name = "desk_count")
    private Short deskCount;

    @ManyToOne
    @JoinColumn(name = "edu_process_id")
    private EduProcessEntity eduProcessEntity;

    @Column(name = "description")
    private String description;

    @Builder.Default
    @NonNull
    @Column(name = "is_active")
    private Boolean isActive = true;
}
