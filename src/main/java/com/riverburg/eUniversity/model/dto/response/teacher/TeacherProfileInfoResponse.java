package com.riverburg.eUniversity.model.dto.response.teacher;

public interface TeacherProfileInfoResponse {

    String fullName = "";

    String academicDegreeName = "";

    Short age = 0;

    String disciplines = "";

    String sectors = "";

    String getFullName();

    String getAcademicDegreeName();

    Short getAge();

    String getDisciplines();

    String getSectors();
}
