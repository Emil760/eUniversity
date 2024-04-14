package com.riverburg.eUniversity.model.dto.response.student.discipline;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Getter
@Setter
@Builder
public class StudentDisciplineNextLessonResponse {

    private Integer disciplineId;

    @DateTimeFormat(pattern = "yyyy.mm.dd")
    private LocalDate nextLessonDate;
}
