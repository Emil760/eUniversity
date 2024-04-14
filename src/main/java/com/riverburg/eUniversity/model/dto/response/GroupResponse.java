package com.riverburg.eUniversity.model.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.lang.Nullable;

import java.time.LocalDate;

@Getter
@Setter
@ToString
@AllArgsConstructor
public class GroupResponse {

    private int id;

    private String name;

    private LocalDate startDate;

    private String facultyName;

    private String sectorName;

    @Nullable
    private long studentsCount;

    private short semester;
}
