package com.riverburg.eUniversity.service.room;

import com.riverburg.eUniversity.exception.RestException;
import com.riverburg.eUniversity.exception.constant.ErrorConstant;
import com.riverburg.eUniversity.mapper.DataMapper;
import com.riverburg.eUniversity.model.dto.request.base.PaginationRequest;
import com.riverburg.eUniversity.model.dto.request.post.AddRoomRequest;
import com.riverburg.eUniversity.model.dto.request.put.UpdateRoomRequest;
import com.riverburg.eUniversity.model.dto.response.RoomResponse;
import com.riverburg.eUniversity.model.dto.response.base.DDLResponse;
import com.riverburg.eUniversity.model.dto.response.base.PaginatedListResponse;
import com.riverburg.eUniversity.model.entity.RoomEntity;
import com.riverburg.eUniversity.repository.RoomRepository;
import com.riverburg.eUniversity.repository.ScheduleRepository;
import com.riverburg.eUniversity.service.misc.EduProcessService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class RoomServiceImpl implements RoomService {

    private final RoomRepository roomRepository;

    private final ScheduleRepository scheduleRepository;

    private final EduProcessService eduProcessService;

    private final DataMapper dataMapper;

    @Override
    public PaginatedListResponse<RoomResponse> getPaginatedRooms(PaginationRequest request, int active) {
        Pageable pageable = PageRequest.of(request.getPageIndex(), request.getPageSize());

        var pageRooms = roomRepository.findByName(pageable, request.getSearch(), active);

        var itemsCount = pageRooms.getTotalElements();

        var rooms = pageRooms
                .getContent()
                .stream()
                .map(dataMapper::roomEntityToRoomResponse)
                .toList();

        return new PaginatedListResponse<RoomResponse>(rooms, itemsCount);
    }

    @Override
    public List<DDLResponse<Integer>> getRoomsDDL() {
        return roomRepository.getRoomsDDL();
    }

    @Override
    public void addRoom(AddRoomRequest request) throws RestException {
        validateAddRoom(request.getNumber());

        var eduProcess = eduProcessService.findById(request.getEduProcessId());

        RoomEntity roomEntity = RoomEntity
                .builder()
                .number(request.getNumber())
                .eduProcessEntity(eduProcess)
                .deskCount(request.getDeskCount())
                .description(request.getDescription())
                .build();

        roomRepository.save(roomEntity);
    }

    @Override
    public void editRoom(UpdateRoomRequest request) throws RestException {
        validateUpdateRoom(request.getId(), request.getNumber());

        var room = findById(request.getId());

        var eduProcess = eduProcessService.findById(request.getEduProcessId());

        room.setNumber(request.getNumber());
        room.setDescription(request.getDescription());
        room.setDeskCount(request.getDeskCount());
        room.setEduProcessEntity(eduProcess);

        roomRepository.save(room);
    }

    @Override
    public void activateRoom(int id, boolean isActive) throws RestException {
        var room = findById(id);

        room.setIsActive(isActive);

        roomRepository.save(room);
    }

    @Override
    public void deleteRoom(int id) throws RestException {
        findById(id);

        if (!scheduleRepository.findByRoomId(id).isEmpty())
            throw RestException.of(ErrorConstant.ROOM_USED_IN_SCHEDULE);

        roomRepository.deleteById(id);
    }

    @Override
    public RoomEntity findById(int id) throws RestException {
        return roomRepository.findById(id)
                .orElseThrow(() -> {
                    throw RestException.of(ErrorConstant.ROOM_NOT_FOUND);
                });
    }

    private void validateAddRoom(String number) throws RestException {
        roomRepository.findByNumber(number)
                .ifPresent(f -> {
                    throw RestException.of(ErrorConstant.UNIQUE_VALIDATION);
                });
    }

    private void validateUpdateRoom(int id, String number) throws RestException {
        roomRepository.findByNumberExceptCurr(id, number)
                .ifPresent(f -> {
                    throw RestException.of(ErrorConstant.UNIQUE_VALIDATION);
                });
    }
}
