package com.riverburg.eUniversity.service.teacher;

import com.riverburg.eUniversity.mapper.DataMapper;
import com.riverburg.eUniversity.model.dto.request.post.AddStudentJournalAttendance;
import com.riverburg.eUniversity.model.dto.request.post.AddStudentJournalGrade;
import com.riverburg.eUniversity.model.dto.response.teacher.*;
import com.riverburg.eUniversity.model.entity.JournalEntity;
import com.riverburg.eUniversity.model.entity.ScheduleEntity;
import com.riverburg.eUniversity.model.entity.StudentEntity;
import com.riverburg.eUniversity.repository.JournalRepository;
import com.riverburg.eUniversity.repository.ScheduleRepository;
import com.riverburg.eUniversity.service.group.GroupService;
import com.riverburg.eUniversity.service.journal.JournalService;
import com.riverburg.eUniversity.service.schedule.ScheduleService;
import com.riverburg.eUniversity.util.schedule.ScheduleUtil;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@AllArgsConstructor
public class TeacherScheduleServiceImpl implements TeacherScheduleService {

    private final ScheduleRepository scheduleRepository;

    private final JournalRepository journalRepository;

    private final ScheduleService scheduleService;

    private final JournalService journalService;

    private final GroupService groupService;

    private final DataMapper dataMapper;

    @Override
    public List<TeacherScheduleResponse> getTeacherSchedule(UUID accountId) {
        var schedules = scheduleRepository.getTeacherSchedules(accountId);

        return schedules
                .stream()
                .map(dataMapper::scheduleEntityToTeacherScheduleResponse)
                .toList();
    }

    @Override
    public TeacherScheduleDetailResponse getTeacherScheduleDetail(int scheduleId) {
        var schedule = scheduleService.findById(scheduleId);

        return dataMapper.scheduleEntityToTeacherScheduleDetailResponse(schedule);
    }

    @Override
    public List<TeacherTimeTabsResponse> getTeacherScheduleDDL(UUID accountId, int groupId, int disciplineId, Date date) {
        var schedules = scheduleRepository.getTeacherSchedules(accountId, groupId, disciplineId);

        var items = new ArrayList<TeacherTimeTabsResponse>();

        for (ScheduleEntity schedule : schedules) {
            var canActivate = ScheduleUtil.isLessonToday(schedule.getBeginDate(), schedule.getEndDate(), schedule.getType(), date);
            if (canActivate) {
                var item = new TeacherTimeTabsResponse(schedule.getId(), schedule.getFrom() + " - " + schedule.getTo());
                items.add(item);
            }
        }

        return items;
    }

    @Override
    public void tryActivateJournal(int scheduleId, Date date) {
        var schedule = scheduleService.findById(scheduleId);

        var isLessonNow = ScheduleUtil.isLessonNow(schedule.getBeginDate(), schedule.getEndDate(), schedule.getFrom(), schedule.getType(), date);

        if (!isLessonNow)
            return;

        var isJournalAlreadyActivated = journalAlreadyActivated(scheduleId, date);

        if (isJournalAlreadyActivated)
            return;

        activateJournal(scheduleId, date);
    }

    @Override
    public List<JournalStudentResponse> getActivatedJournalStudents(int scheduleId, Date date) {
        var students = journalRepository.getJournalActivatedStudents(scheduleId, date);

        return students
                .stream()
                .map(dataMapper::journalEntityToJournalStudentResponse_Activated)
                .toList();
    }

    @Override
    public List<JournalStudentResponse> getJournalStudents(int scheduleId, Date date) {
        var students = journalRepository.getJournalActivatedStudents(scheduleId, date);

        if (students.isEmpty()) {
            students = journalRepository.getJournalStudents(scheduleId);
            return students.stream().map(dataMapper::journalEntityToJournalStudentResponse_NotActivated).toList();
        } else {
            return students.stream().map(dataMapper::journalEntityToJournalStudentResponse_Activated).toList();
        }
    }

    @Override
    public void activateJournal(int scheduleId, Date date) {
        var schedule = scheduleService.findById(scheduleId);

        var students = groupService.getStudents(schedule.getGroupEntity().getId());

        var journalStudents = new ArrayList<JournalEntity>();

        for (StudentEntity student : students) {
            var journal = JournalEntity
                    .builder()
                    .scheduleEntity(schedule)
                    .studentEntity(student)
                    .date(date).build();

            journalStudents.add(journal);
        }

        journalRepository.saveAll(journalStudents);
    }

    private boolean journalAlreadyActivated(int scheduleId, Date date) {
        return !getActivatedJournalStudents(scheduleId, date).isEmpty();
    }

    @Override
    public void addGradeStudent(AddStudentJournalGrade request) {
        var journal = journalService.findById(request.getJournalId());

        journal.setGrade(request.getGrade());

        journalRepository.save(journal);
    }

    @Override
    public void addAttendanceStudent(AddStudentJournalAttendance request) {
        var journal = journalService.findById(request.getJournalId());

        journal.setAttendance(request.isAttendance());

        journalRepository.save(journal);
    }

}
