package com.riverburg.eUniversity.service.student;

import com.riverburg.eUniversity.model.dto.response.student.journal.StudentJournalDisciplineGradeResponse;
import com.riverburg.eUniversity.model.dto.response.student.journal.StudentJournalTotalGradeResponse;

import java.util.Date;
import java.util.List;
import java.util.UUID;

public interface StudentJournalService {

    StudentJournalDisciplineGradeResponse getStudentJournalGrades(UUID accountId, int facultyDisciplineId);

    List<Short> getAllGradesByDiscipline(UUID accountId, int facultyDisciplineId);

    StudentJournalTotalGradeResponse getTotalGradesByDiscipline(UUID accountId, int facultyDisciplineId);
}
