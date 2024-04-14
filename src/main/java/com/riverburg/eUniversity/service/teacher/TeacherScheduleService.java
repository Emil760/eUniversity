package com.riverburg.eUniversity.service.teacher;

import com.riverburg.eUniversity.model.dto.request.post.AddStudentJournalAttendance;
import com.riverburg.eUniversity.model.dto.request.post.AddStudentJournalGrade;
import com.riverburg.eUniversity.model.dto.response.teacher.JournalStudentResponse;
import com.riverburg.eUniversity.model.dto.response.teacher.TeacherScheduleDetailResponse;
import com.riverburg.eUniversity.model.dto.response.teacher.TeacherScheduleResponse;
import com.riverburg.eUniversity.model.dto.response.teacher.TeacherTimeTabsResponse;

import java.util.Date;
import java.util.List;
import java.util.UUID;

public interface TeacherScheduleService {

    void tryActivateJournal(int scheduleId, Date date);

    List<TeacherTimeTabsResponse> getTeacherScheduleDDL(UUID accountId, int groupId, int disciplineId, Date date);

    List<TeacherScheduleResponse> getTeacherSchedule(UUID accountId);

    TeacherScheduleDetailResponse getTeacherScheduleDetail(int scheduleId);

    List<JournalStudentResponse> getActivatedJournalStudents(int scheduleId, Date date);

    List<JournalStudentResponse> getJournalStudents(int scheduleId, Date date);

    void activateJournal(int scheduleId, Date date);

    void addGradeStudent(AddStudentJournalGrade request);

    void addAttendanceStudent(AddStudentJournalAttendance request);
}
