package com.riverburg.eUniversity.model.entity;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "configuration")
@Setter
@Getter
@NoArgsConstructor
public class ConfigurationEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "[name]", unique = true)
    private String name;

    @Column(name = "[value]")
    private String value;
}
