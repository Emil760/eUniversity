package com.riverburg.eUniversity.service.schedule;

import com.riverburg.eUniversity.exception.RestException;
import com.riverburg.eUniversity.exception.constant.ErrorConstant;
import com.riverburg.eUniversity.mapper.DataMapper;
import com.riverburg.eUniversity.model.constant.ScheduleType;
import com.riverburg.eUniversity.model.dto.request.post.AddScheduleRequest;
import com.riverburg.eUniversity.model.dto.request.put.UpdateScheduleRequest;
import com.riverburg.eUniversity.model.dto.response.base.DDLResponse;
import com.riverburg.eUniversity.model.dto.response.ScheduleResponse;
import com.riverburg.eUniversity.model.entity.ScheduleEntity;
import com.riverburg.eUniversity.repository.ScheduleRepository;
import com.riverburg.eUniversity.service.faculty.FacultyDisciplineService;
import com.riverburg.eUniversity.service.group.GroupService;
import com.riverburg.eUniversity.service.misc.EduProcessService;
import com.riverburg.eUniversity.service.room.RoomService;
import com.riverburg.eUniversity.service.teacher.TeacherService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@AllArgsConstructor
public class ScheduleServiceImpl implements ScheduleService {

    private final ScheduleRepository scheduleRepository;

    private final GroupService groupService;

    private final FacultyDisciplineService facultyDisciplineService;

    private final RoomService roomService;

    private final EduProcessService eduProcessService;

    private final TeacherService teacherService;
    
    @Override
    public List<DDLResponse<Integer>> getTypeDDL() {
        var list = new ArrayList<DDLResponse<Integer>>();

        list.add(new DDLResponse<Integer>(ScheduleType.OneTime.ordinal(), "one time"));
        list.add(new DDLResponse<Integer>(ScheduleType.OnceWeek.ordinal(), "once a week"));
        list.add(new DDLResponse<Integer>(ScheduleType.OnceTwoWeek.ordinal(), "once in two weeks"));

        return list;
    }

    @Override
    public List<ScheduleResponse> getScheduleList(int groupId, short semesterNumber) {
        return scheduleRepository.getScheduleList(groupId, semesterNumber);
    }

    //TODO add validation, can`t add two schedule at same time for same teacher
    @Override
    public void addSchedule(AddScheduleRequest request) {
        validateDate(request.getBeginDate(), request.getEndDate());

        var group = groupService.findById(request.getGroupId());

        var facultyDiscipline = facultyDisciplineService.findById(request.getFacultyDisciplineId());

        var eduProcess = eduProcessService.findById(request.getEduProcessId());

        var room = roomService.findById(request.getRoomId());

        var teacher = teacherService.findById(request.getTeacherId());

        ScheduleEntity scheduleEntity = ScheduleEntity
                .builder()
                .groupEntity(group)
                .facultyDisciplineEntity(facultyDiscipline)
                .eduProcessEntity(eduProcess)
                .teacherEntity(teacher)
                .roomEntity(room)
                .beginDate(request.getBeginDate())
                .endDate(request.getEndDate())
                .from(request.getFrom())
                .to(request.getTo())
                .type(request.getType())
                .build();

        scheduleRepository.save(scheduleEntity);
    }

    @Override
    public void editSchedule(UpdateScheduleRequest request) {
        validateDate(request.getBeginDate(), request.getEndDate());

        var schedule = findById(request.getId());
        schedule.setIsActive(false);
        scheduleRepository.save(schedule);

        var newSchedule = new AddScheduleRequest();
        newSchedule.setGroupId(schedule.getGroupEntity().getId());
        newSchedule.setFacultyDisciplineId(schedule.getFacultyDisciplineEntity().getId());
        newSchedule.setEduProcessId(request.getEduProcessId());
        newSchedule.setTeacherId(request.getTeacherId());
        newSchedule.setBeginDate(request.getBeginDate());
        newSchedule.setEndDate(request.getEndDate());
        newSchedule.setType(request.getType());
        newSchedule.setFrom(request.getFrom());
        newSchedule.setTo(request.getTo());
        newSchedule.setRoomId(request.getRoomId());

        addSchedule(newSchedule);
    }

    @Override
    public ScheduleEntity findById(int id) {
        return scheduleRepository.findById(id)
                .orElseThrow(() -> {
                    throw RestException.of(ErrorConstant.SCHEDULE_NOT_FOUND);
                });
    }

    private void validateDate(Date from, Date to) {
        if (from.after(to)) {
            throw RestException.of(ErrorConstant.INVALID_DATES);
        }
    }

}
