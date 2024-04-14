package com.riverburg.eUniversity.service.schedule;

import com.riverburg.eUniversity.model.dto.request.post.AddScheduleRequest;
import com.riverburg.eUniversity.model.dto.request.put.UpdateScheduleRequest;
import com.riverburg.eUniversity.model.dto.response.base.DDLResponse;
import com.riverburg.eUniversity.model.dto.response.ScheduleResponse;
import com.riverburg.eUniversity.model.entity.ScheduleEntity;

import java.util.List;

public interface ScheduleService {

    List<DDLResponse<Integer>> getTypeDDL();

    List<ScheduleResponse> getScheduleList(int groupId, short semesterNumber);

    void addSchedule(AddScheduleRequest request);

    void editSchedule(UpdateScheduleRequest request);

    ScheduleEntity findById(int id);

}
